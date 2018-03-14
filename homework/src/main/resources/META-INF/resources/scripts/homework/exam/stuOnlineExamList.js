define(function (require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Utils = require('utils');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	var Handlebars = require("common/handlebars");

	Handlebars.registerHelper("examStatusStr", function (obj) {
		/*1.未开始、2.已结束、3.正在考试*/
		var examStatusStr = "";
		if (new Date().getTime() < obj.startTime) {
			examStatusStr = "未开始";
		} else if (obj.closeTime < new Date().getTime()) {
			examStatusStr = "缺考";
		} else {
			examStatusStr = "正在考试";
		}
		return examStatusStr;
	});
	Handlebars.registerHelper("examTime", function (obj) {
		return Math.abs(obj.closeTime - obj.startTime) / (1000 * 60);
	});

	var MyHomeworkList = {
		page: null,
		init: function () {
			this.loadMyHomeworkList();
			this.binEvent();
		},
		loadMyHomeworkList: function () {
			this.page = Page.create({
				id: 'page',
				url: ctx + '/auth/student/exam/queryStuOnlineExamListData.htm',
				pageSize: 10,
				buttonId: '',
				formId: 'myHomeworkForm',
				tips: '您暂无考试哦~',
				tipsId: 'f_emptyDataContainer',
				callback: function (datas) {
					Handlebars.render("#myHomeworkContext_tpl", datas.page, "#myHomeworkContext");
				}
			});
		},
		binEvent: function () {
			var _this = this;

			//分析click事件
			$(document).on('click', '.J_analysis', function () {
				var homeworkId = $(this).data('homeworkid');
				var _this = this;
				$.ajax({
					url: Leke.domain.homeworkServerName + '/auth/teacher/homework/validateAnalysis.htm?homeworkId=' + homeworkId,
					async: false,
					success: function (data) {
						if (!data.success) {
							Utils.Notice.alert(data.message);
							isAllow = false;
						} else {
							window.open($(_this).data('href'));
						}
					}
				});
			});

			/*标题点击事件*/
			$(document).on('click', '.toExam', function () {
				var startTime = $(this).data('starttime');
				var closeTime = $(this).data('closetime');
				var time = new Date().getTime();
				if (time < startTime) {
					Utils.Notice.alert("考试时间还未开始！");
				} else if (closeTime < time) {
					var id = $(this).data('id');
					var href = Leke.domain.homeworkServerName + '/auth/student/homework/viewWork.htm?homeworkDtlId=' + id;
					window.open(href);
				} else {
					var submittime = $(this).data('submittime');
					if (submittime != '') {
						var id = $(this).data('id');
						var href = Leke.domain.homeworkServerName + '/auth/student/homework/viewWork.htm?homeworkDtlId=' + id;
						window.open(href);
					} else {
						Utils.Notice.alert("请移步至平板端进行考试！");
					}

				}
			});
		},
		reloadPage: function () {
			this.page.options.curPage = 1;
			this.page.loadData();
		},
		changeStatusActive: function (status) {
			$('#jCorrectFlag li').removeClass('cur');
			$('#jCorrectFlag li[data-status=' + status + ']').addClass('cur');
		}
	};
	MyHomeworkList.init();

});
