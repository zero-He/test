define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var dialog = require('dialog');
	var Mustache = require('mustache');
	var WdatePicker = require('date');
	var tree = require('tree');
	var Widget = require('common/base/widget');
	var Bar = require('question/util/queSearchBar');
	var ResearcherQuestionService = require('question/service/researcherQuestionService');
	var DFN = require('homework/sheet.dfn');

	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	var _loadTpl = $('#loading_tpl').html();
	var _rTpl = $('#rejection_tpl').html();
	
	var QueList = Widget.extend({
		tpl: '.examination',
		init: function() {
			var self = this;
			self.attr(self.$el.data());
			var param = self.attrs();
			
			self.$qc = self.$('.quesCutContent');
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
			
			self.attrBindDom(['[name=schoolName]', '[name=creatorName]']);
			
			self.on('change:materialId', function() {
				self.attr('materialNodeId', null);
				self._initTree();
			});
			
			self.doSearch();
			self._initTree();
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
			'click .j-btn-tchshare-pass': 'doPass',
			'click .j-btn-tchshare-reject': 'doReject',
			'change .j-select-all': 'changeSelectAll',
			'click .j-btn-verify': 'doVerify'
		},
		doSearch: function() {
			var self = this;
			self.attr('curPage', 1);
			self.load();
		},
		load: function() {
			var self = this,
				params = self.attrs();
			var request = $.ajax({
				type: 'post',
				url: window.ctx + '/auth/researcher/question/teacherShare/details.htm',
				data: _.cleanup(params),
				dataType: 'html'
			});
			self.$qc.html(_loadTpl);
			request.done(function(resp) {
				self.$qc.html(resp);
				DFN.init();
			});
		},
		doPass: function(evt) {
			var self = this;
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			ResearcherQuestionService.checkPassTeacherShare({
				questionId: qid
			}).then(function() {
				utils.Notice.alert('题目审核通过操作操作！');
				self.removeQue($q);
			}, function(msg) {
				utils.Notice.alert(msg || '题目审核通过操作失败！');
			});
		},
		doReject: function(evt) {
			var self = this;
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			var $ed = $(_rTpl);
			var dlg = dialog.open({
				title: '退回原因',
				tpl: $ed,
				size: 'mini',
				btns: [{
					className: 'btn-ok',
					text: '确定',
					cb: function() {
						var content = $ed.find('[name=rejectionContent]').val();
						if(!content || !content.length || content.length > 150) {
							utils.Notice.alert('请输入 150 个字以内的退回原因！');
							return false;
						}
						ResearcherQuestionService.rejectTeacherShare({
							questionId: qid,
							rejectionContent: content
						}).then(function() {
							utils.Notice.alert('习题审核退回操作成功！');
							self.removeQue($q);
							dlg.close();
						}, function(msg) {
							utils.Notice.alert(msg || '习题审核退回操作失败！');
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
		changeSelectAll: function(evt) {
			var self = this;
			var checked = $(evt.target).prop('checked');
			self.$qc.find('.q-checkbox :checkbox').prop('checked', checked);
		},
		doVerify: function() {
			var self = this;
			var $qs = self.$qc.find('.q-checkbox :checked');
			if(!$qs.length) {
				utils.Notice.alert('请选择需审核通过的题目！');
				return false;
			}
			var qids = [];
			$qs.each(function() {
				var $q = $(this).closest('.j-que-que-warp');
				var qid = $q.data('questionId');
				qids.push(qid);
			});
			ResearcherQuestionService.batchCheckPassTeacherShare({
				questionIds: qids
			}).then(function(datas) {
				if(datas && datas.msg) {
					utils.Notice.alert(datas.msg || '题目审核操作成功！');
				}
				self.doSearch();
			}, function(msg) {
				utils.Notice.alert(msg || '题目审核操作失败！');
			});
		}
	});
	
	new QueList();
});