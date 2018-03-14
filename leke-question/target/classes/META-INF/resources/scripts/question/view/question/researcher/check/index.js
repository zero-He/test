define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var dialog = require('dialog');
	var Mustache = require('mustache');
	var WdatePicker = require('date');
	var tree = require('tree');
	var Widget = require('common/base/widget');
	var TreeUtil = require('question/util/treeUtil');
	var Bar = require('question/util/queSearchBar');
	var BasicInfoService = require('core-business/service/basicInfoService');
	var QueService = require('question/service/questionService');
	var DFN = require('homework/sheet.dfn');

	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	require('core-business/widget/jquery.leke.knowledgeSelect');
	
	var _tsTpl = $('#tagSelect_tpl').html();
	var _tTpl = $('#tagItem_tpl').html();
	var _kTpl = $('#knowledgeItem_tpl').html();
	var _loadTpl = $('#loading_tpl').html();
	var _rTpl = $('#reject_tpl').html();
	
	var QueList = Widget.extend({
		tpl: '.examination',
		init: function() {
			var self = this;
			self.attr(self.$el.data());
			self._initStatus();
			var param = self.attrs();

			$('#stageSubject').stgGrdSbjSelect({
				type: 'stg_sbj',
				data: {},
				onChange: function(selection) {
					if(selection) {
						self.attr('schoolStageId', selection.schoolStageId);
						self.attr('subjectId', selection.subjectId);
						self.doSearch();
					} else {
						self.attr('schoolStageId', null);
						self.attr('subjectId', null);
					}
				}
			});
			
			self.$qc = self.$('.quesCutContent');
			
			Bar.init.call(self);
			
			var $minDate = self.$('[name=minInputDate]');
			var $maxDate = self.$('[name=maxInputDate]');
			var _dateCfg = {
				dateFmt: 'yyyy-MM-dd',
				readOnly: true,
				onpicked: function() {
					$(this).trigger('change');
				},
				oncleared: function() {
					$(this).trigger('change');
				}
			};
			$minDate.on('click', function() {
				var cfg = $.extend({}, _dateCfg);
				var maxDate = $maxDate.val();
				if(maxDate) {
					cfg.maxDate = maxDate;
				}
				WdatePicker(cfg);
			});
			$maxDate.on('click', function() {
				var cfg = $.extend({}, _dateCfg);
				var minDate = $minDate.val();
				if(minDate) {
					cfg.minDate = minDate;
				}
				WdatePicker(cfg);
			});
			
			self.attrBind($minDate);
			self.attrBind($maxDate);
			
			self.on('change:statusType', function() {
				self.doSearch();
			});
			self.on('change:materialId', function() {
				self.attr('materialNodeId', null);
				self._initTree();
			});
			
			self.doSearch();
			self._initTree();
		},
		_initStatus: function() {
			var self = this;
			var $stat;
			var stat = self.attr('statusType');
			if(stat) {
				$stat = self.$('[data-status="' + stat + '"]');
			}
			if(!$stat || !$stat.length) {
				$stat = self.$('.quesTab:first');
			}
			$stat.addClass('quesTotalon');
			self.attr('statusType', $stat.data('status'));
		},
		_initTree: function() {
			var self = this, ops = self.options;
			if(self._tree) {
				self._tree.destroy();
				self._tree = null;
			}
			var mid = self.attr('materialId');
			if(mid) {
				self._tree = tree.createTree('#sectionTree', {
					async: {
						service : 'materialTreeDataService',
						autoParam: ['materialNodeId=parentId'],
						otherParam: {
							materialId: mid,
							showMode: 'section',
							visitMode: 'child'
						}
					},
					view: {
						selectedMulti: false
					},
					data : {
						key : {
							name : 'materialNodeName'
						},
						simpleData: {
							enable: true,
							idKey: "materialNodeId",
							pIdKey: "parentId",
							rootPId: 0
						}
					},
					callback : {
						onClick : function(e, treeId, treeNode, flag) {
							self.attr('materialNodeId', treeNode.materialNodeId);
							self.doSearch();
						}
					}
				});
			}
		},
		events: {
			'click .j-btn-search': 'doSearch',
			'click .j-btn-edit': 'doEdit',
			'click .j-btn-edit-checked': 'doEditChecked',
			'click .j-btn-reject': 'doReject',
			'click .j-btn-del': 'doDel',
			'click .j-btn-disable': 'doDisable',
			'click .quesTab': 'changeStatus',
			'change .j-select-all': 'changeSelectAll',
			'click .j-btn-check': 'doCheck',
			'click .q-propterty-d-n-edit,.q-propterty-d-y-edit': 'setDifficulty',
			'click .q-propterty-k-edit': 'openKnowledgeDialog',
			'click .q-propterty-k-delete': 'doDelKnowledge',
			'click .q-propterty-t-edit': 'openTagDialog',
			'click .q-propterty-t-delete': 'doDelTag'
		},
		doSearch: function() {
			var self = this;
			self.attr('curPage', 1);
			self.load();
		},
		load: function() {
			var self = this,
				params = self.attrs();
			if(!params.schoolStageId || !params.subjectId) {
				self.$qc.html('<p>请先选择学段科目，再进行查询！</p>');
				return false;
			}
			
			var request = $.ajax({
				type: 'post',
				url: window.ctx + '/auth/researcher/question/check/details.htm',
				data: params,
				dataType: 'html'
			});
			self.$qc.html(_loadTpl);
			request.done(function(resp) {
				self.$qc.html(resp);
				DFN.init();
			});
		},
		doEdit: function(evt) {
			var self = this;
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			dialog.open({
				id: 'questionEditDialog',
				title: '习题编辑',
				url: window.ctx + '/auth/researcher/question/check/edit.htm?questionId=' + qid,
				size: 'full',
				onClose: function(type) {
					if(type === 'success') {
						utils.Notice.alert('习题编辑成功！');
						self.refreshQue($q, qid);
					}
				}
			});
		},
		doEditChecked: function(evt) {
			var self = this;
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			dialog.open({
				id: 'questionEditDialog',
				title: '习题编辑',
				url: window.ctx + '/auth/researcher/question/check/editChecked.htm?questionId=' + qid,
				size: 'full',
				onClose: function(type, newId) {
					if(type === 'success') {
						utils.Notice.alert('习题编辑成功！');
						self.refreshQue($q, newId);
					}
				}
			});
		},
		refreshQue: function($q, qid) {
			var self = this;
			if(!$q || !$q.length || !qid) {
				return false;
			}
			var status = self.attr('statusType');
			var request = $.ajax({
				type: 'post',
				url: window.ctx + '/auth/researcher/question/check/detail.htm',
				data: {
					questionId: qid,
					statusType: status
				},
				dataType: 'html'
			});
			request.done(function(resp) {
				$q.replaceWith(resp);
				DFN.init();
			});
		},
		doReject: function(evt) {
			var self = this;
			var $a = $(evt.target);
			var tag = $a.data();
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			var $ed = $(_rTpl);
			var dlg = dialog.open({
				title: '习题退回',
				tpl: $ed,
				size: 'mini',
				btns: [{
					className: 'btn-ok',
					text: '确定',
					cb: function() {
						var data = $ed.serialize();
						var content = $ed.find('[name=rejectReason]').val();
						if(!content || !content.length || content.length > 150) {
							utils.Notice.alert('请输入 150 个字以内的退回原因！');
							return false;
						}
						QueService.reject({
							questionId: qid,
							rejectReason: content
						}).then(function() {
							dlg.close();
							utils.Notice.alert('习题退回操作成功！');
							self.removeQue($q);
						}, function(msg) {
							utils.Notice.alert(msg || '习题退回操作失败！');
						});
					}
				}, {
					className: 'btn-cancel',
					text: '取消',
					cb: function() {
						dlg.close();
					}
				}]
			});
		},
		doDel: function(evt) {
			var self = this;
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			dialog.confirm('确定删除该习题吗？').done(function(sure) {
				if(sure) {
					QueService.del({
						questionId: qid
					}).then(function() {
						utils.Notice.alert('习题删除成功！');
						self.removeQue($q);
					}, function(msg) {
						utils.Notice.alert(msg || '习题删除失败！');
					});
				}
			});
		},
		doDisable: function(evt) {
			var self = this;
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			dialog.confirm('确定禁用该习题吗？').done(function(sure) {
				if(sure) {
					QueService.disable({
						questionId: qid
					}).then(function() {
						utils.Notice.alert('习题禁用成功！');
						self.removeQue($q);
					}, function(msg) {
						utils.Notice.alert(msg || '习题禁用失败！');
					});
				}
			});
		},
		removeQue: function($q) {
			var self = this;
			$q.slideUp('normal', function() {
				$q.remove();
				
				var $left = self.$qc.find('.j-que-que-warp');
				if(!$left.length) {
					self.load();
				}
			});
		},
		changeStatus: function(evt) {
			var self = this;
			var $li = $(evt.target).closest('.quesTab');
			$li.addClass('quesTotalon').siblings().removeClass('quesTotalon');
			self.attr('statusType', $li.data('status'));
		},
		changeSelectAll: function(evt) {
			var self = this;
			var checked = $(evt.target).prop('checked');
			self.$qc.find('.q-checkbox :checkbox').prop('checked', checked);
		},
		doCheck: function() {
			var self = this;
			var $qs = self.$qc.find('.q-checkbox :checked');
			if(!$qs.length) {
				utils.Notice.alert('请选择需审核通过的习题！');
				return false;
			}
			var qids = [];
			$qs.each(function() {
				var $q = $(this).closest('.j-que-que-warp');
				var qid = $q.data('questionId');
				qids.push(qid);
			});
			QueService.check({
				questionIds: qids
			}).then(function(datas) {
				if(datas && datas.msg) {
					utils.Notice.alert(datas.msg || '习题审核操作成功！');
				}
				
				// 滚动到顶部
				$('html,body').animate({
					scrollTop : 0
				}, 1000);
				
				self.doSearch();
			}, function(msg) {
				utils.Notice.alert(msg || '习题审核操作失败！');
			});
		},
		setDifficulty: function(evt) {
			var $s = $(evt.target);
			var qid = $s.closest('.q-container').attr('qid');
			var yEd = 'q-propterty-d-y-edit', nEd = 'q-propterty-d-n-edit';
			var hadVal = $s.hasClass(yEd);
			var diff = _.num($s.index() * 0.2 + 0.1, {fixed: 1});
			QueService.setDifficulty({
				questionId: qid,
				difficulty: diff
			}).then(function() {
				$s.prevAll().removeClass(nEd).addClass(yEd);
				$s.nextAll().removeClass(yEd).addClass(nEd);
				$s.removeClass(nEd).addClass(yEd);
			}, function(msg) {
				utils.Notice.alert(msg || '题目设置难度值失败！');
			});
		},
		openKnowledgeDialog: function(evt) {
			evt.preventDefault();
			var self = this;
			var $k = $(evt.target);
			var $ke = $k.closest('.q-propterty-k-e');
			var $q = $k.closest('.j-que-que-warp');
			var que = $q.data();
			$k.knowledgeSelect({
				data: {
					schoolStageId: que.schoolStageId,
					subjectId: que.subjectId
				},
				multi: true,
				onSelect: function(knowledges) {
					QueService.attachKnowledges({
						questionId: que.qid,
						knowledges: knowledges
					}).then(function(datas) {
						$ke.next('.q-propterty-k').remove();
						var contents = Mustache.render(_kTpl, datas);
						$ke.after(contents);
					}, function(msg) {
						utils.Notice.alert(msg || '设置关联知识点失败！');
					});
				}
			});
			return false;
		},
		doDelKnowledge: function(evt) {
			evt.preventDefault();
			var $t = $(evt.target);
			var kid = $t.attr('kid');
			QueService.detachKnowledge({
				quesKnowledgeId: kid
			}).then(function() {
				$t.closest('.q-propterty-k-item').remove();
			}, function(msg) {
				utils.Notice.alert(msg || '移除知识点失败！');
			});
			return false;
		},
		openTagDialog: function(evt) {
			evt.preventDefault();
			var $t = $(evt.target);
			var $te = $t.closest('.q-propterty-t-e');
			var $q = $t.closest('.j-que-que-warp');
			var qid = $q.data('questionId');
			BasicInfoService.officialTags().done(function(tags) {
				var contents = Mustache.render(_tsTpl, {
					tags: tags
				});
				var $d = $(contents);
				var dlg = dialog.open({
					title: '选择题库标签',
					tpl: $d,
					size: 'mini',
					btns: [{
						className: 'btn-ok',
						text: '确定',
						cb: function() {
							var $ts = $d.find(':checked');
							if(!$ts.length) {
								utils.Notice.alert('请先选择题库标签！');
								return false;
							}
							var tags = [];
							$ts.each(function() {
								tags.push({
									questionId: qid,
									officialTagId: $(this).val()
								});
							});
							QueService.attachTags({
								questionId: qid,
								tags: tags
							}).then(function(datas) {
								$te.next('.q-propterty-t').remove();
								var contents = Mustache.render(_tTpl, datas);
								$te.after(contents);
								dlg.close();
							}, function(msg) {
								utils.Notice.alert(msg || '设置题库标签失败！');
							});
						}
					}, {
						className: 'btn-cancel',
						text: '取消',
						cb: function() {
							this.close();
						}
					}]
				});
			});
			return false;
		},
		doDelTag: function(evt) {
			evt.preventDefault();
			var $t = $(evt.target);
			var tid = $t.attr('tid');
			QueService.detachTag({
				quesOfficialTagId: tid
			}).then(function() {
				$t.closest('.q-propterty-t-item').remove();
			}, function(msg) {
				utils.Notice.alert(msg || '移除题库标签失败！');
			});
			return false;
		}
	});
	
	new QueList();
});