define(function(require, exports, module) {
	var $ = require('jquery');
	var dialog = require('dialog');
	var utils = require('utils');
	var Pages = require('common/base/pages');
	var DFN = require('homework/sheet.dfn');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	var QuestionFeedbackList = {
			fInit : function() {
				this.fBindEvent();
				this.fLoadList();
			},
			
			fBindEvent:function() {
				var self = this;
				$('#quesCutContent').on('click', '.btnQueIgnore', $.proxy(self, 'fIgnore'));
				$('#quesCutContent').on('click', '.btnQueDisable', $.proxy(self, 'fDisable'));
				$('#quesCutContent').on('click', '.btnQueEdit', $.proxy(self, 'fEdit'));
				$('#quesCutContent').on('click', '.btnQueIgnoreFeedback', $.proxy(self, 'fIgnoreFeedback'));
				
				$('#stageSubject').stgGrdSbjSelect({
					type: 'stg_sbj',
					data: {},
					onChange: function(selection) {
						if(selection) {
							self.schoolStageId = selection.schoolStageId;
							self.subjectId = selection.subjectId;
							self.fLoadList();
						} else {
							self.schoolStageId = null;
							self.subjectId = null;
						}
					}
				});
				self.schoolStageId = null;
				self.subjectId = null;
				self.curPage = 1;
				self.$el = $('#quesCutContent');
				Pages.bind(self.$el, {
					ns: '.quePage',
					load: function(page) {
						self.curPage = page;
						self.fLoadList();
					}
				});
			},
			
			fIgnore : function(evt) {
				var self = this;
				dialog.confirm('确定要忽略纠错吗？').done(function(sure){
					if(sure) {
						var $el = $(evt.target);
						var nQuestionId = $el.data('id');
						$.ajax({
							type : 'post',
							url : ctx + '/auth/common/questionFeedback/ignore.htm',
							data : {
								questionId:nQuestionId
							},
							dataType: 'json',
							success: function(json) {
								var msg = json && json.message;
								if(json && json.success) {
									utils.Notice.alert('纠错忽略成功！');
									self.fLoadList();
								} else {
									utils.Notice.alert(msg || '纠错忽略失败');
								}
							}
						});
					}
				});
			},
			
			fIgnoreFeedback : function(evt) {
				var self = this;
				dialog.confirm('确定要忽略该条纠错吗？').done(function(sure){
					if(sure) {
						var $el = $(evt.target);
						var nQuestionId = $el.data('id');
						var nFeedbackId = $el.data('questionFeedbackId');
						$.ajax({
							type : 'post',
							url : ctx + '/auth/common/questionFeedback/ignore.htm',
							data : {
								questionId : nQuestionId,
								questionFeedbackId : nFeedbackId
							},
							dataType: 'json',
							success: function(json) {
								var msg = json && json.message;
								if(json && json.success) {
									utils.Notice.alert('该条纠错忽略成功！');
									self.fLoadList();
								} else {
									utils.Notice.alert(msg || '该条纠错忽略失败');
								}
							}
						});
					}
				});
			},
			
			fDisable : function(evt) {
				var self = this;
				dialog.confirm('确定要禁用吗？').done(function(sure){
					if(sure) {
						var $el = $(evt.target);
						var nQuestionId = $el.data('id');
						$.ajax({
							type : 'post',
							url : ctx + '/auth/common/userQue/disable.htm',
							data : {
								questionId:nQuestionId
							},
							dataType: 'json',
							success: function(json) {
								var msg = json && json.message;
								if(json && json.success) {
									utils.Notice.alert('禁用成功！');
									self.fLoadList();
								} else {
									utils.Notice.alert(msg || '禁用失败');
								}
							}
						});
					}
				});
			},
			
			fEdit : function(evt) {
				var self = this;
				var $el = $(evt.target);
				var nQuestionId = $el.data('id');
				dialog.open({
					id: 'questionEditDialog',
					title: '习题编辑',
					url: window.ctx + '/auth/researcher/question/check/editChecked.htm?curScope=7&action=correct&questionId=' + nQuestionId,
					size: 'full',
					onClose: function(type, newId) {
						if(type === 'success') {
							utils.Notice.alert('习题编辑成功！');
							self.fLoadList();
						}
					}
				});
			},
			
			fLoadList : _.debounce(function() {
				var self = this;
				var request = $.ajax({
					type : 'post',
					url : window.ctx + '/auth/researcher/questionFeedback/questionFeedbackListDetails.htm',
					data : {schoolStageId:self.schoolStageId, subjectId:self.subjectId,curPage: self.curPage},
					dataType: 'html'
				});
				$('.quesCutContent').empty();
				request.done(function(resp) {
					$('.quesCutContent').html(resp);
					DFN.init();
				});
			},300)
			
	};
	
	QuestionFeedbackList.fInit();
});