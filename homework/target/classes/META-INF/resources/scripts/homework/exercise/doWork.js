define(function(require, exports, module) {
	require('common/forbidpaste');
	var $ = require('jquery');
	var json = require('json');
	var utils = require('utils');
	var dialog = require('dialog');
	require('homework/common/sheet.extend');
	var Answer = require('homework/answer');
	var Render = require('homework/sheet.render');
	var I18n = require('homework/common/i18n');
	
	var Work = {
		subjectId : null,
		doubtUrl : ctx + '/auth/student/myDoubt/doubtList/doubtForQuestion.htm',
		init : function() {
			this.subjectId = $('#subjectId').val();
			this.bindEvent();
			this.setTimeout();
			this.initSerializeObject();
		},
		// 事件注册
		bindEvent : function() {
			var _this = this;
			$('#btnSubmit').click(function() {
				_this.saveExercise(false);
			});
			$('#btnSaveSnapshot').click(function(){
				_this.saveExercise(true);
			});
		},
		setTimeout: function(){
			var _this =this;
			window.setInterval(function(){
				_this.saveExercise(true);
			}, 180000); 
		},
		saveExercise: function(isSnapshot){
			var _this = this;
			var noAnswerNum = _this.parseExerciseAnswer();
			if(isSnapshot){
				var datas = $('#exerciseForm').serializeObject();
				console.log(datas);
				$.post('saveSnapshot.htm',datas,function(data){
					if(data.success){
						utils.Notice.alert('保存成功');
					}
				});
			}else{
				var msgTips = '你还有{{num}}题未完成，确认提交吗？';
				if (noAnswerNum == 0) {
					msgTips = '练习已全部完成，确认提交吗？';
				}else{
					msgTips = msgTips.replace('{{num}}',noAnswerNum);
				}
				dialog.confirm(msgTips).done(function(sure) {
					if (sure) {
						$('#exerciseForm').submit();
					}
				});
			}
		},
		parseExerciseAnswer : function() {
			var questionList = $('.p-exam-body>.j-question');
			var answerInfoList = [];
			var noAnswerNum = 0;
			questionList.each(function() {
				var question = $(this);
				var qid = question.attr('qid');
				var hasSub = question.attr('hassub');
				if (hasSub == 'true') {
					// 有子题
					var answerInfo = {
						questionId : parseInt(qid),
						subs : []
					};
					var isSum = 0;
					question.find('.q-container').each(function() {
						var cquestion = $(this);
						var cqid = cquestion.attr('qid');
						var answer = Answer.getAnswer(cquestion);
						if (answer.hasNull) {
							hasNull = true;
							if(isSum == 0){
								noAnswerNum++;
								isSum = 1;
							}
							
						}
						answerInfo.subs.push({
							questionId : parseInt(cqid),
							answerContent : json.stringify(answer.content)
						});
					});
					answerInfoList.push(answerInfo);
				} else {
					// 无子题
					var answer = Answer.getAnswer(question);
					if (answer.hasNull) {
						hasNull = true;
						noAnswerNum++;
					}
					answerInfoList.push({
						questionId : parseInt(qid),
						answerContent : json.stringify(answer.content)
					});
				}
			});
			var answerJson = json.stringify(answerInfoList);
			$('#answerJson').val(answerJson);
			return noAnswerNum;
		},initSerializeObject : function(){
			$.fn.serializeObject = function()    
			{    
			   var o = {};    
			   var a = this.serializeArray();    
			   $.each(a, function() {    
			       if (o[this.name]) {    
			           if (!o[this.name].push) {    
			               o[this.name] = [o[this.name]];    
			           }    
			           o[this.name].push(this.value || '');    
			       } else {    
			           o[this.name] = this.value || '';    
			       }    
			   });    
			   return o;    
			}; 
		}
	};

	Work.init();

});