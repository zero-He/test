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
			var url = "/auth/provost/studentMonitor/homeworkAnalyse/queryStuHomeworkDetailData.htm?startDate=" + param.startDate + "&endDate=" + param.endDate +
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
						for (var i = 0; i < data.dataList.length; i++) {
							data.dataList[i].closeTimeStr = new Date(data.dataList[i].closeTime).format("yyyy-MM-dd hh:mm");
							if(data.dataList[i].submitTime !== null){
								data.dataList[i].submitTimeStr = new Date(data.dataList[i].submitTime).format("yyyy-MM-dd hh:mm");
							} else {
								data.dataList[i].submitTimeStr = "--";
							}
						}
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


