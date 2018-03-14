define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var Mustache = require('mustache');
	var utils = require('utils');
	var dialog = require('dialog');
	var WdatePicker = require('date');
	var Widget = require('common/base/widget');
	var Bar = require('question/util/queSearchBar');
	var ComService = require('question/service/commonService');
	var QueService = require('question/service/questionService');
	var DFN = require('homework/sheet.dfn');
	var _loadTpl = $('#loading_tpl').html();
	var _chTypeTpl = $('#changeType_tpl').html();
	
	var QueList = Widget.extend({
		tpl: '.examination',
		init: function() {
			var self = this;
			self.attr(self.$el.data());
			self._initStatus();
			
			self.$qc = self.$('.quesCutContent');
			
			Bar.init.call(self);
			
			var $day = self.$('[name=inputDate]');
			$day.on('click', function() {
				WdatePicker({
					dateFmt: 'yyyy-MM-dd',
					readOnly: true,
					onpicked: function() {
						$day.trigger('change');
					},
					oncleared: function() {
						$day.trigger('change');
					}
				});
			});
			
			self.attrBind($day);
			
			self.on('change:statusType', function() {
				self.doSearch();
			});
			
			self.doSearch();
		},
		_initStatus: function() {
			var self = this;
			var $stat;
			var stat = self.attr('statusType');
			if(stat) {
				$stat = self.$('[data-status="' + stat + '"]');
			}
			if(!$stat || !$stat.length) {
				$stat = self.$('.j-que-tab:first');
			}
			$stat.addClass('active');
			self.attr('statusType', $stat.data('status'));
		},
		events: {
			'click .btnSearch': 'doSearch',
			'click .btnQueEdit': 'doEdit',
			'click .btnQueDel': 'doDel',
			'click .j-que-tab': 'changeStatus',
			'click .btnChangeQueType': 'doChangeQueType'
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
				url: window.ctx + '/auth/inputer/question/questionListDetails.htm',
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
				title: '题目修改',
				url: window.ctx + '/auth/inputer/question/questionEdit.htm?curScope=7&action=create&questionId=' + qid,
				size: 'full',
				onClose: function(type) {
					if(type === 'success') {
						utils.Notice.alert('题目修改成功！');
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
			var status = self.attr('statusType');
			var request = $.ajax({
				type: 'post',
				url: window.ctx + '/auth/inputer/question/questionDetail.htm',
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
						utils.Notice.alert('题目删除成功！');
						self.removeQue($q);
					}, function(msg) {
						utils.Notice.alert(msg || '题目删除失败！');
					});
				}
			});
		},
		changeStatus: function(evt) {
			var self = this;
			var $li = $(evt.target).closest('.j-que-tab');
			$li.addClass('active').siblings().removeClass('active');
			self.attr('statusType', $li.data('status'));
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
		doChangeQueType: function(evt) {
			var self = this;
			var $a = $(evt.target);
			var $q = $a.closest('.j-que-que-warp');
			var que = $a.data();
			ComService.similarQueTypes(que).done(function(resp) {
				var $ed = $(Mustache.render(_chTypeTpl, resp));
				var $s = $ed.find('[name=questionTypeId]');
				$s.val(_.str(que.questionTypeId));
				var dlg = dialog.open({
					title: '修改题型',
					tpl: $ed,
					size: 'mini',
					btns: [{
						className: 'btn-ok',
						text: '确定',
						cb: function() {
							var newId = $s.val();
							if(!newId || !newId.length) {
								utils.Notice.alert('请选择题型！');
								return false;
							}
							QueService.setQuestionType({
								questionId: que.questionId,
								questionTypeId: newId
							}).then(function() {
								utils.Notice.alert('题型修改成功！');
								dlg.close();
							}, function(msg) {
								utils.Notice.alert(msg || '题型修改失败！');
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
			});
		}
	});
	
	new QueList();
});