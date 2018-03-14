define(function (require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Handlebars = require('common/handlebars');
	var Page = require("page");
	var StudentBehaviorComm = require("./studentBehaviorComm");
	var format = require('diag/common/DateFormat');

	var Statistics = {

		init: function () {
			this.loadDtlDataPage(StudentBehaviorComm.getParam());
			StudentBehaviorComm.detailClick(Statistics.loadDtlDataPage);
		},

		loadDtlDataPage: function (param) {
			var url = "/auth/provost/studentMonitor/readyAnalyse/queryStuReadyDetailData.htm?startDate=" + param.startDate + "&endDate=" + param.endDate +
				"&studentId=" + param.studentId + "&orderAttr=" + param.orderAttr + "&orderType=" + param.orderType + "&classId=" + param.classId + "&subjectId=" + param.subjectId;
			Page.create({
				id: 'jTablePage',
				url: url,
				formId: '',
				curPage: 1,
				pageSize: 10,
				pageCount: 10,// 分页栏上显示的分页数
				tips: '暂无数据！',
				callback: function (datas) {
					var data = datas.page;
					if (data) {
						Handlebars.render("#dtlTpl", data, "#jtbodyData");
					} else {
						$('#jtbodyData').html('');
					}
				}
			})
		}

	};

	Statistics.init();

});


