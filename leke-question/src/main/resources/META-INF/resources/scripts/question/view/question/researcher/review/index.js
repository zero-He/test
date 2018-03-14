define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	var dialog = require('dialog');
	var Mustache = require('mustache');
	var WdatePicker = require('date');
	var tree = require('tree');
	var Widget = require('common/base/widget');
	var Bar = require('question/util/queSearchBar');
	var QueService = require('question/service/questionService');
	var DFN = require('homework/sheet.dfn');
	var GradeSubjectSelect = require('question/widgets/gradeSubjectSelect');
	
	var _loadTpl = $('#loading_tpl').html();
	
	var QueList = Widget.extend({
		tpl: '.examination',
		init: function() {
			var self = this;
			self.attr(self.$el.data());
			self._initStatus();
			var gid = self.attr('gradeId');
			var sid = self.attr('subjectId');
			if(gid && sid) {
				self.attr('gradeSubjectId', gid + '_' + sid);
			}
			var param = self.attrs();
			
			self.$qc = self.$('.quesCutContent');
			
			Bar.init.call(self);
			var _gs = new GradeSubjectSelect({
				data: param,
				tpl: self.$('[name=gradeSubjectId]')
			});
			
			self.attrLink(_gs, ['gradeId', 'subjectId']);
			
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
			
			self.attrBindDom(['[name=creatorName]']);
			
			self.on('change:reviewed', function() {
				self.doSearch();
			});
			self.on('change:materialId change:reviewed', function() {
				self.attr('materialNodeId', null);
				self._initTree();
			});
			
			self.doSearch();
			self._initTree();
		},
		_initStatus: function() {
			var self = this;
			var $stat;
			var stat = self.attr('reviewed');
			if(stat) {
				$stat = self.$('[data-reviewed="' + stat + '"]');
			}
			if(!$stat || !$stat.length) {
				$stat = self.$('.quesTab:first');
			}
			$stat.addClass('quesTotalon');
			self.attr('reviewed', $stat.data('reviewed'));
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
			'click .j-btn-review-edit': 'doReviewEdit',
			'click .quesTab': 'changeStatus',
			'change .j-select-all': 'changeSelectAll',
			'click .j-btn-review': 'doReview'
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
				url: window.ctx + '/auth/researcher/question/review/details.htm',
				data: params,
				dataType: 'html'
			});
			self.$qc.html(_loadTpl);
			request.done(function(resp) {
				self.$qc.html(resp);
				DFN.init();
			});
		},
		doReviewEdit: function(evt) {
			var self = this;
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var qid = $a.data('questionId');
			dialog.open({
				id: 'questionEditDialog',
				title: '习题编辑',
				url: window.ctx + '/auth/researcher/question/review/edit.htm?questionId=' + qid,
				size: 'full',
				onClose: function(type) {
					if(type === 'success') {
						utils.Notice.alert('习题编辑成功！');
						self.refreshQue($q);
					}
				}
			});
		},
		refreshQue: function($q) {
			var self = this;
			if(!$q || !$q.length) {
				return false;
			}
			var qid = $q.data('questionId');
			var request = $.ajax({
				type: 'post',
				url: window.ctx + '/auth/researcher/question/review/detail.htm',
				data: {
					questionId: qid,
					reviewed: self.attr('reviewed')
				},
				dataType: 'html'
			});
			request.done(function(resp) {
				$q.replaceWith(resp);
				DFN.init();
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
			self.attr('reviewed', $li.data('reviewed'));
		},
		changeSelectAll: function(evt) {
			var self = this;
			var checked = $(evt.target).prop('checked');
			self.$qc.find('.q-checkbox :checkbox').prop('checked', checked);
		},
		doReview: function() {
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
			QueService.review({
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