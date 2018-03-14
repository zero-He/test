define(function (require, exports, module) {
	var $ = require('jquery');
	var _ = require('underscore');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var Utils = require("utils");
	var Dialog = require('dialog');
	var WdatePicker = require('date');
	var LekeDate = require('common/base/date');
	var I18n = require('homework/common/i18n');
	var JSON = require('json');

	var s_confirm = $.i18n.prop('homework.homework.message.confirm');
	var s_cancel = $.i18n.prop('homework.homework.message.cancel');

	var homeworkEditTpl = $('#jHomeworkEditTpl').html();

	var HomeworkList = {
		page: null,
		init: function () {
			var _this = this;
			this.initLoadPage();
			this.bindEvent();
		},
		initLoadPage: function () {
			this.page = Page.create({
				id: 'divPage',
				url: ctx + '/auth/teacher/exam/queryTeaOnlineExamListData.htm',
				curPage: 1,
				pageSize: 10,
				pageCount: 10,
				buttonId: 'bHomeworkList',
				formId: 'formPage',
				tipsId: 'f_emptyDataContainer',
				callback: function (datas) {
					var page = datas.page;
					$.each(page.dataList, function () {
						var self = this;
						self.avgScore = Utils.Number.toFixed(self.avgScore, 1);
						var studentGroups = eval(self.studentGroupsJson);
						var className = self.className == null ? "" : self.className;
						if (studentGroups) {
							_.each(studentGroups, function (n) {
								className += ' ' + n.groupName;
							});
						}
						self.className = className;
						self.classNameStr = className.length > 10 ? (className.substring(0, 10) + '...') : className;

						self.examTime = Math.abs(self.closeTime - self.startTime) / (1000 * 60);

						if (new Date().getTime() < self.startTime) {
							self.examStatus = 1;
						} else if (self.closeTime < new Date().getTime()) {
							self.examStatus = 2;
						} else {
							self.examStatus = 3;
						}

					});
					Handlebars.render("#homeworkContext_tpl", page, '#homeworkListContext');
				}
			});
			var _this = this;
		},
		reloadPage: function () {
			arguments.length == 0 ? this.page.options.curPage = 1 : this.page.options.curPage = arguments[0];
			this.page.loadData();
		},
		bindEvent: function () {
			/*公布答案*/
			$(document).on('click', '.j_openAnswer', function () {
				var homeworkId = $(this).data('homeworkid');
				var _this = this;
				Dialog.confirm('公布答案后，学生可以看到正确答案和解析，确定吗？').done(function (sure) {
					if (sure) {
						var url = "/auth/teacher/homework/openAnswer.htm";
						$.post(url, {'homeworkId': homeworkId}, function (data) {
							if (data.success) {
								Utils.Notice.alert('操作成功');
								$(_this).replaceWith('<span class="u-state-label clicked">已公布答案</span>');
							} else {
								Utils.Notice.alert('操作失败');
							}
						});
					}
				});
			});
			/*修改时间*/
			$('#homeworkListContext').on('click', '.jEdit', function () {
				HomeworkEdit.edit($(this).data('id'), $(this).data('starttime'));
			});
			/*作废*/
			$('#homeworkListContext').on('click', '.jCancel', function () {
				var nHomeworkId = $(this).data('id');
				Dialog.confirm("确认要作废此份试卷吗？").done(function (sure) {
					if (sure) {
						$.post('/auth/teacher/homework/homeworkInvalid.htm', {homeworkId: nHomeworkId}, function (message) {
							HomeworkList.reloadPage();
						});
					}
				});
			});
		}
	};

	/**
	 * 修改时间
	 */
	var HomeworkEdit = {
		homeworkId: null,
		startTime: null,
		edit: function (homeworkId, startTime) {
			if (LekeDate.of(startTime).toDate() <= new Date()) {
				Utils.Notice.alert("考试时间不能修改");
				return;
			}

			var _this = this;
			_this.homeworkId = homeworkId;
			_this.startTime = startTime;
			Dialog.open({
				title: "修改试卷",
				tpl: homeworkEditTpl,
				size: 'nm',
				btns: [{
					className: 'btn-ok',
					text: s_confirm,
					cb: function () {
						if (_this.doComfirm()) {
							this.close(false);
						}
					}
				}, {
					className: 'btn-cancel',
					text: s_cancel,
					cb: function () {
						this.close(false);
					}
				}]
			});
			_this.bindEvent();
		},
		bindEvent: function () {
			var FMT = 'yyyy-MM-dd HH:mm:ss';
			$('#jEditStartTime').val(LekeDate.format(this.startTime, FMT));

			if (LekeDate.of(this.startTime).toDate() > new Date()) {
				$('#jEditStartTime').addClass('Wdata').on('click', function () {
					WdatePicker({
						dateFmt: 'yyyy-MM-dd HH:mm:00',
						readOnly: true
					});
				});
			} else {
				$('#jEditStartTime').prop('disabled', true);
			}
		},
		doComfirm: function () {
			var _this = this;
			var FMT = 'yyyy-MM-dd HH:mm:ss';
			var startTime = $('#jEditStartTime').val();
			if (startTime == '') {
				Utils.Notice.alert($.i18n.prop('homework.distribute.startTime.isnull'));
				return false;
			}
			var data = {
				homeworkId: this.homeworkId,
				startTime: startTime,
			};

			$.post('/auth/teacher/exam/updateOnlineExamDate.htm', data, function (res) {
				if (res.success) {
					Utils.Notice.alert(res.message);
					Dialog.close();
					HomeworkList.reloadPage();
				}
			}, 'json');

		}
	};

	HomeworkList.init();

});
