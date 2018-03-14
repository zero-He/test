define(function (require, exports, module) {
	var $ = require('jquery');
	var Utils = require("utils");
	var Handlebars = require("common/handlebars");
	var I18n = require('homework/common/i18n');
	var Dialog = require('dialog');
	var Homework = {
		init: function () {
			this.loadHomeworkDtlList(null);
			this.bindEvent();
		},
		bindEvent: function () {
			var _this = this;
			/*点击得分触发*/
			$('.m-sorting').click(function () {
				var className = $(this).attr('class');
				var sort = -1;
				if (className.indexOf('m-sorting-asc') > -1) {
					$(this).addClass('m-sorting-desc').removeClass('m-sorting-asc');
				} else if (className.indexOf('m-sorting-desc') > -1) {
					$(this).addClass('m-sorting-asc').removeClass('m-sorting-desc');
					sort = 1;
				} else {
					$(this).addClass('m-sorting-desc').removeClass('m-sorting-asc');
				}
				_this.loadHomeworkDtlList(sort);
			});
		},
		loadHomeworkDtlList: function (sort) {
			var data = {
				homeworkId: $('#j_homeworkId').val(),
				studentName: '',
				sort: sort,
				questionNum: $('#j_questionNum').val()
			};
			$.post('/auth/teacher/exam/queryTeaOnlineExamDetailData.htm', data, function (json) {
				var dataLists = json.datas.list;
				if (json.success && dataLists && dataLists.length > 0) {
					var page = {
						dataList: dataLists,
						trStyle: function () {
							return this.submitStatus == 2 ? " style=background:#f9f4eb" : "";
						}
					}
					$.each(page.dataList, function () {
						this.score = Utils.Number.toFixed(this.score, 1);
						if (Leke.user.currentRoleId == 101 && this.correctTime == null) {
							this.buttonName = $.i18n.prop('homework.homework.check.correct');
							this.buttonAction = 'correctWork';
						} else {
							this.buttonName = $.i18n.prop('homework.homework.check.view');
							this.buttonAction = 'viewWork';
						}

						var closeTime = $('#j_closeTime').val();
						var time = new Date().getTime();
						if (closeTime < time) {
							this.isNotExam = true;
						} else {
							this.isNotExam = false;
						}

						this.progressRate = function () {
							return Utils.Number.toFixed(Utils.Number.toFixed(this.correctCount / this.paperQuestionNum, 2) * 100, 2);
						};
					});
					Handlebars.render("#homeworkDtlContext_tpl", page, '#homeworkDtlListContext');
					$('.j-no-data').hide();
				} else {
					$('#homeworkDtlListContext').html('');
					$('.j-no-data').show();
				}
			});
		}
	};
	Homework.init();
});
