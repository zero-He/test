define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Page = require("page");
	var Utils = require('utils');
	var StudentAttendComm = require("./studentAttendComm");
	var Handlebars = require('common/handlebars');
	
	var Statistics = {

			init : function() {
				Statistics.loadDtlDataPage(StudentAttendComm.getParam());
				StudentAttendComm.bindOrderEvent(Statistics.loadDtlDataPage);
			},
			
			loadDtlDataPage : function(param){
				var url;
				if(param.queryType == "lesson_dtl"){
					url = "/auth/provost/studentMonitor/studentAttend/findStudentAttendDtl.htm?orderAttr="+param.orderAttr+"&orderType="+param.orderType+"&queryType="+param.queryType+"&csAttenId="+param.csAttenId;
				}else if(param.queryType == "class_dtl"){
					url = "/auth/provost/studentMonitor/studentAttend/findStudentAttendDtl.htm?startDate="+param.startDate+"&endDate="+param.endDate+
					"&classId="+param.classId+"&orderAttr="+param.orderAttr+"&orderType="+param.orderType+"&queryType="+param.queryType;
				}
				Page.create({
					id : 'jTablePage',
					url : url,
					formId : '',
					curPage : 1,
					pageSize : 10,
					pageCount : 10,// 分页栏上显示的分页数
					tips : '暂无数据！',
					callback : function(datas) {
						var data = datas.page;
						if (data) {
							Handlebars.render("#attendTpl", data, "#jtbodyData");
						} else {
							$('#jtbodyData').html('');
						}
					}
				})},
		};

		Statistics.init();
});
