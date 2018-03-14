define(function(require, exports, module) {
	'use strict';
	var $ = require('jquery');
	var Mustache = require('mustache');
	var utils = require('utils');
	var dialog = require('dialog');
	var DFN = require('homework/sheet.dfn');
	var QuestionFeedbackList = {
			fInit : function() {
				this.fBindEvent();
				this.fLoadList();
			},
			
			fBindEvent:function() {
				var self = this;
				$('#quesCutContent').on('click', '.btnQueIgnore', $.proxy(self, 'fIgnore'));
				$('#quesCutContent').on('click', '.btnQueDisable', $.proxy(self, 'fDisable'));
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
			
			fLoadList : function() {
				var request = $.ajax({
					type : 'post',
					url : window.ctx + '/auth/teacher/questionFeedback/userQuestionFeedbackListDetails.htm',
					data : {},
					dataType: 'html'
				});
				$('.quesCutContent').empty();
				request.done(function(resp) {
					$('.quesCutContent').html(resp);
					DFN.init();
				});
			}
	};
	
	QuestionFeedbackList.fInit();
});