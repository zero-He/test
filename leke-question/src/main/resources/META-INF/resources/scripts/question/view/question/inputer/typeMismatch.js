define(function(require, exports, module) {
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var Mustache = require('mustache');
	var utils = require('utils');
	var dialog = require('dialog');
	var Widget = require('common/base/widget');
	var Bar = require('question/util/queSearchBar');
	var ComService = require('question/service/commonService');
	var QueService = require('question/service/questionService');
	
	var _loadTpl = $('#loading_tpl').html();
	var _chTypeTpl = $('#changeType_tpl').html();
	
	var QueList = Widget.extend({
		tpl: '.examination',
		init: function() {
			var self = this;
			
			self.$qc = self.$('.quesCutContent');
			
			Bar.init.call(self);
			
			self.doSearch();
		},
		events: {
			'click .btnSearch': 'doSearch',
			'click .btnQueDel': 'doDel',
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
				url: window.ctx + '/auth/inputer/question/typeMismatchDetails.htm',
				data: params,
				dataType: 'html'
			});
			self.$qc.html(_loadTpl);
			request.done(function(resp) {
				self.$qc.html(resp);
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
								self.removeQue($q);
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