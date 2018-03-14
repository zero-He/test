define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Page = require("page");
	var Utils = require('utils');
	var StudentAttendComm = require("./studentAttendComm");
	var Handlebars = require('common/handlebars');
	
	var Statistics = {

			init : function() {
				StudentAttendComm.initDateSelect();
				StudentAttendComm.loadQueryParam(Statistics.loadDataPage);
				StudentAttendComm.bindEvent();
				StudentAttendComm.bindQueryEvent(Statistics.loadDataPage);
				StudentAttendComm.bindOrderEvent(Statistics.loadDataPage);
				StudentAttendComm.bindQuickSelectEvent(Statistics.loadDataPage);
				StudentAttendComm.bindPreviousDayEvent();
				StudentAttendComm.bindNextDayEvent();
				Statistics.bindEvent();
				Utils.Notice.mask.create();
			},
			
			bindEvent : function(){
				$(document).on("click", ".lessonDetail", function(){
					window.open("/auth/provost/studentMonitor/studentAttend/toShowStudentAttendDtlByLesson.htm?csAttenId="+$(this).data("csattenid")+"&startDate="+$(this).data("startdate")+"&teacherName="+$(this).data("teachername")+"&className="+$(this).data("classname")+"&subjectName="+$(this).data("subjectname")+"&duration="+$(this).data("duration"));
				});
				
				$(document).on("click", ".classDetail", function(){
					var param = StudentAttendComm.getParam();
					window.open("/auth/provost/studentMonitor/studentAttend/toShowStudentAttendDtlByClass.htm?teacherName="+$(this).data("teachername")+"&className="+$(this).data("classname")+"&classId="+$(this).data("classid")+"&startDate="+param.startDate+"&endDate="+param.endDate);
				});
				
				$(document).on("click", ".search", function(){
					Statistics.loadDataPage(StudentAttendComm.getParam());
				});
			},
			
			loadDataPage : function(param){
				var url = "/auth/provost/studentMonitor/studentAttend/findStudentAttend.htm?startDate="+param.startDate+"&endDate="+param.endDate+
				"&gradeId="+param.gradeId+"&classId="+param.classId+"&orderAttr="+param.orderAttr+"&orderType="+param.orderType+"&queryType="+param.queryType+"&studentName="+param.studentName+"&teacherName="+param.teacherName;
				Page.create({
					id : 'jTablePage',
					url : encodeURI(url),
					formId : '',
					curPage : 1,
					pageSize : 10,
					pageCount : 10,// 分页栏上显示的分页数
					tips : '暂无数据！',
					callback : function(datas) {
						var data = datas.page;
						$("."+param.queryType+"Item").addClass("active");
						if (data) {
							Handlebars.render("#attendTpl", data, "#jtbodyData");
						} else {
							$('#jtbodyData').html('');
						}
						Utils.Notice.mask.close();
					}
				})},
				
		};

		Statistics.init();
});
