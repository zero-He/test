define(function(require, exports, module) {
	var $ = require('jquery');
	require('common/ui/ui-mask/jquery.mask');
	var JSON = require('json');
	var Utils = require('utils');
	var Dialog = require('dialog');
	var SheetFlash = require('./sheet/sheet.flash');
	var PaperViewer = require('./sheet/paper.viewer');
	var PaperCorrect = require('./sheet/paper.correct');
	var PaperExtend = require('./sheet/paper.extend');

	var Workbench = {
		state : {
			index : 0,
			doneNum : 0,
			totalNum : 0,
			homeworkDtlId : 0,
			studentName : '',
			homeworkId : 0,
			questionId : 0,
			cutDate : 0,
			questionIds : [],
			isExam : false,
			submitHwDtls : [],
			//默认还有为批改的题目
			isCorrectAll : 0
		},
		setState : function(state) {
			$.extend(this.state, state || {});
			$('#jDoneNum').text(this.state.doneNum);
			$('#jTotalNum').text(this.state.totalNum);
			$('#jStudentName').text(this.state.studentName);
			$('#jStudentName').attr('href',
					Leke.ctx + '/auth/teacher/homework/viewWork.htm?homeworkDtlId=' + this.state.homeworkDtlId);
		},
		init : function() {
			var that = this;
			this.setState({
				index : context.index,
				totalNum : context.homework.finishNum,
				homeworkId : context.homework.homeworkId,
				paperId : context.homework.paperId,
				questionId : context.questionIds[context.index],
				questionIds : context.questionIds,
				isExam : context.isExam,
				cutDate : context.cutDate
			});
			this.bind();
			this.setSubmitHwDtls();
			this.setHotKeys();
			//	this.setGradingScoreHandle();
		},
		bind : function() {
			var that = this;
			$('#j_nextStu').click(function() {
				that.handleSaveCorrect('下一个', true).then(function() {
					that.gotoNext();
				});
			});
			$('#j_firstStu').click(function() {
				that.handleSaveCorrect('第一个', false).then(function() {
					return that.validateFirst(1);
				}).then(function() {
					that.doNextUser(that.state.submitHwDtls[0]);
				});
			});
			$('#j_prevStu').click(function() {
				that.handleSaveCorrect('上一个', false).then(function() {
					return that.validateFirst(2);
				}).then(function() {
					var index = that.state.submitHwDtls.indexOf(that.state.homeworkDtlId);
					var prevHomeworkDtlId = that.state.submitHwDtls[index - 1];
					that.doNextUser(prevHomeworkDtlId);
				});
			});
		},
		gotoNext : function() {
			var that = this;
			var index = that.state.submitHwDtls.indexOf(that.state.homeworkDtlId);
			if (index == that.state.submitHwDtls.length - 1) {
				that.backList();//返回题目列表
			} else {
				that.doNextUser(that.state.submitHwDtls[index + 1]);
			}
		},
		backList : function() {
			var that = this;
			Utils.Notice.alert('该题已经批改完成，即将回到题目列表！');
			setTimeout(function() {
				that.state.isExam == true
						? (window.location.href = '/auth/teacher/exam/batchByQuestions.htm?homeworkId='
								+ that.state.homeworkId)
						: (window.location.href = 'batchQuestions.htm?homeworkId=' + that.state.homeworkId);
			}, 2000);
		},
		/*
		 * 1:代表操作 第一个 按钮
		 * 2:代表操作 上一个 按钮
		 * */
		validateFirst : function(type) {
			var dfd = $.Deferred();
			if (this.state.submitHwDtls.indexOf(this.state.homeworkDtlId) == 0) {
				if (type == 1) {
					Utils.Notice.alert('已经是第一个了');
				} else {
					Utils.Notice.alert('没有上一个了');
				}
				$('#j_firstStu,#j_prevStu,#j_nextStu').unlock();
				return dfd.reject();
			} else {
				return dfd.resolve();
			}
			return dfd.promise();
		},
		handleSaveCorrect : function(btnName, isNext) {
			var that = this;
			var message = "当前题目未批改，未批改将按0分处理，确定要进入" + btnName + "吗？";
			var questionResult = PaperCorrect.parseSheet();
			var dfd = $.Deferred();
			return this.doConfirm(questionResult.unDoneNum > 0, message).then(function() {
				$('#j_firstStu,#j_prevStu,#j_nextStu').lock();
				$.post('save.htm', {
					homeworkDtlId : that.state.homeworkDtlId,
					questionResultJson : questionResult.correctJson
				}, function(json) {
					if (json.success) {
						dfd.resolve();
					} else {
						dfd.reject();
					}
				});
				return dfd.promise();
			});
		},
		doConfirm : function(flag, message) {
			var dfd = $.Deferred();
			if (flag) {
				Dialog.confirm(message).done(function(sure) {
					if (sure) {
						dfd.resolve();
					} else {
						dfd.reject();
					}
				});
			} else {
				dfd.resolve();
			}
			return dfd.promise();
		},
		doNextUser : function(nextHwDtld) {
			var that = this;
			var dfd = $.Deferred();
			$.post('next.htm', {
				homeworkId : this.state.homeworkId,
				paperId : this.state.paperId,
				questionId : this.state.questionId,
				cutDate : this.state.cutDate,
				homeworkDtlId : nextHwDtld
			}, function(resp) {
				if (resp.datas.workDetail) {
					Workbench.setState({
						doneNum : resp.datas.doneNum,
						studentName : resp.datas.workDetail.studentName,
						homeworkDtlId : resp.datas.workDetail.homeworkDtlId
					});
					that.renderQuestion(resp.datas.html);
					that.resetScroll();
					$('#j_firstStu,#j_prevStu,#j_nextStu').unlock();
					dfd.reject();
				} else {
					dfd.resolve();
				}
			});
			return dfd.promise();
		},
		resetScroll : function(callback) {
			window.scrollTo(0, 0);
			if (typeof callback == 'function') {
				callback();
			}
		},
		renderQuestion : function(html) {
			$('#jQuestionBody').html(html);
			PaperViewer.renderSheet();
			PaperCorrect.renderSheet();
			SheetFlash.scan();
			PaperExtend.bindFinder($('.q-paper-que>.q-que'));
			this.setGroupTitle();
		},
		setGroupTitle : function() {
			var _this = this;
			var quesNo = $('.que-queno').text();
			var index = '&nbsp;&nbsp;&nbsp;第' + quesNo.substr(0,quesNo.length - 1) + '题';
			$.post('/auth/teacher/batch/groupTitle.htm', {
				paperId : _this.state.paperId,
				questionId : _this.state.questionId
			}, function(json) {
				var text = json.datas.groupTitle + index;
				$('#jIndex').html(text);
			})
		},
		setSubmitHwDtls : function() {
			var _this = this;
			_this.doPostSubmitHwDtls().then(function() {
				return _this.doNextUser(null);
			}).done(function() {
				_this.doNextUser(_this.state.submitHwDtls[0]);
			});
		},
		doPostSubmitHwDtls : function() {
			var dfd = $.Deferred();
			var _this = this;
			$.post('/auth/teacher/batch/submitHwDtls.htm', {
				paperId : _this.state.paperId,
				homeworkId : _this.state.homeworkId,
				questionId : _this.state.questionId,
				cutDate : _this.state.cutDate
			}, function(json) {
				_this.state.submitHwDtls = json.datas.submitHwDtls;
				dfd.resolve();
			})
			return dfd.promise();
		},
		/*
		 * 快捷键
		 *上一个  ：<-，下一个  ： ->
		 * */
		setHotKeys : function() {
			var _this = this;
			$(document).keyup(function(event) {
				var elementType = event.currentTarget.activeElement.type;
				if (event.keyCode == '37' && isUseHotKeys(elementType)) {
					//上一个
					$('#j_prevStu').click();
				} else if (event.keyCode == '39' && isUseHotKeys(elementType)) {
					//下一个	
					$('#j_nextStu').click();
				}
			});
			function isUseHotKeys(elementType) {
				//是否有弹出层
				var isShowDiag = $(document).find('.m-dialog').length > 0 || $('.m-imgpreview').length > 0;
				return !(isShowDiag || elementType == 'text' || elementType == 'textarea');
			}
		},
		/*
		 * 设置打分后 保存并下一个
		 * */
		setGradingScoreHandle : function() {
			var that = this;
			Tools.setGradingScoreHandle(function() {
				//全部作答了
				var questionResult = PaperCorrect.fParseQuestion($('#jQuestionBody'));
				if (!questionResult.hasNull && questionResult.questionResultList[0].subs == undefined) {
					$('#j_nextStu').click();
				}
			});
		}
	};

	Workbench.init();
});