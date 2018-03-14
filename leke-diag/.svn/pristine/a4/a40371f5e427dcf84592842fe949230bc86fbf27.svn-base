define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var DateUtil = require('common/base/date');
	var Dialog = require('dialog');
	var WdatePicker = require('date');
	var Utils = require('utils');
	
	var dialogHelp = ['<p>迟到：上线时间晚于开课时间2分钟及以上</p>',
	                  '<p>早退：在上课时间内最后一次离开课堂的时间早于下课时间</p>',
	                  '<p>迟到且早退：即迟到又早退</p>',
	                  '<p>缺勤：当堂课在线总时长短于课堂时间70%及以上</p>',
	                  '<p>全勤：以上四现象都没发生</p>',
	                  '<p>到课：本堂课非缺勤即定为到课</p>'
	                  ].join("");
	
	var StudentAttendComm = {
			
			fmt : "yyyy-MM-dd",
			
			saveGlobalParam : function(gradeId, classId, quickSelect, startDate, endDate){
				if(gradeId != null){
					store.set("attendGradeId", gradeId);
				}
				if(classId != null){
					store.set("attendClassId", classId);
				}
				if(quickSelect){
					store.set("attendQuickSelect", quickSelect);
				}else{
					store.remove("attendQuickSelect");
				}
				if(startDate != null){
					store.set("attendStartDate", startDate);
				}
				if(endDate != null){
					store.set("attendEndDate", endDate);
				}
			},
			
			getParam : function(){
				var param = {
						       gradeId : $("#gradeId").val(),
						       subjectId : -1,
							   classId : $("#classId").val(),
							   className : $("#classId option:selected").text(),
							   startDate : $("#startDate").val(),
							   endDate : $("#endDate").val(),
							   orderAttr : $("#orderAttr").val(),
							   orderType : $("#orderType").val(),
							   teacherName : $("#teacherName").val(),
							   studentName : $("#studentName").val(),
							   queryType : $("#queryType").val(),
							   csAttenId : $("#csAttenId").val()
							 }
				return param;
			},
			
			getCurrentDate : function(){
				var now = new Date();
				var	nowDay = now.getDate(); //当前日
				var	nowMonth = now.getMonth(); //当前月
				var	nowYear = now.getFullYear(); //当前年
				return DateUtil.format(new Date(nowYear, nowMonth, nowDay), this.fmt);
			},
			
			getDateByDelta : function(srcDate, delta){
				var now = new Date(srcDate);
				now.setDate(now.getDate() + delta);
				var	nowDay = now.getDate(); 
				var	nowMonth = now.getMonth(); 
				var	nowYear = now.getFullYear(); 
				return DateUtil.format(new Date(nowYear, nowMonth, nowDay), this.fmt);
			},
			
			getStartEndDate : function(){
				var now = new Date();
				var nowDayOfWeek = now.getDay() || 7;  //今天是本周的第几天
				var	nowDay = now.getDate(); //当前日
				var	nowMonth = now.getMonth(); //当前月
				var	nowYear = now.getFullYear(); //当前年
				var	nextYear = now.getFullYear() + 1; //下一年
				
				//获得本周的开始、结束日期
				var weekStartDate = DateUtil.format(new Date(nowYear, nowMonth, nowDay - nowDayOfWeek + 1), this.fmt);
			    /*var weekEndDate = DateUtil.format(new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek) + 1), this.fmt);*/
			    var weekEndDate = StudentAttendComm.getDateByDelta(now, -1);

			    //获得本月的开始、结束日期
			    var monthStartDate = DateUtil.format(new Date(nowYear, nowMonth, 1), this.fmt);
			    /*var monthEndDate = DateUtil.format(new Date(nowYear, nowMonth, this.getMonthDays(nowYear,nowMonth)), this.fmt);*/
			    var monthEndDate = StudentAttendComm.getDateByDelta(now, -1);

			    starEndDate = {
			    	weekStartDate : weekStartDate,
			    	weekEndDate : weekEndDate,
			    	monthStartDate : monthStartDate,
			    	monthEndDate : monthEndDate
			    };
			    
				return starEndDate;
			},
			
			initDateSelect : function(){
				if(store.get("attendStartDate")){
					$("#startDate").val(store.get("attendStartDate"));
				}else{
					$("#startDate").val(StudentAttendComm.getCurrentDate());
				}
				
				if(store.get("attendEndDate")){
					$("#endDate").val(store.get("attendEndDate"));
				}else{
					$("#endDate").val(StudentAttendComm.getCurrentDate());
				}
				
				if(store.get("attendQuickSelect")){
					$("#"+store.get("attendQuickSelect")).addClass("active").siblings().removeClass("active");
				}
			},
			
			loadClassByGrade : function(gradeId){
				$.post("/auth/provost/teachingMonitor/comm/loadClassByGrade.htm", {gradeId:gradeId}, function(json){
					var data = json.datas;
					if (data) {
						Handlebars.render("#classTpl", data, "#classId");
					}
				});
			},
			
			loadQueryParam : function(callBack){
				$.post("/auth/provost/teachingMonitor/comm/loadQueryParamPro.htm", {gradeId : store.get("attendGradeId"), classType : false}, function(json){
					var data = json.datas;
					if (data) {
						Handlebars.render("#gradeTpl", data, "#gradeId");
						Handlebars.render("#classTpl", data, "#classId");
						
						if(store.get("attendGradeId")){
							$("#gradeId").val(store.get("attendGradeId"));
						}
						
						if(store.get("attendClassId")){
							$("#classId").val(store.get("attendClassId"));
						}
						
						if(callBack){
							callBack(StudentAttendComm.getParam());
						}
					}
				});
			},
			
			bindQueryEvent : function(callBack){
				$(document).on("click", "#query", function(){
					
					var startTime = $('#startDate').val();
					if($("#queryType").val() != "day"){
						var endTime = $("#endDate").val();
						if(!startTime || !endTime){
							Utils.Notice.alert("开始日期、结束日期都不能为空!");
							return false;
						}else if(new Date(startTime) > new Date(endTime)){
							Utils.Notice.alert("开始日期不能大于结束日期!");
							return false;
						}
						$(".quickSelect").removeClass("active");
					}
					
					if(callBack){
						callBack(StudentAttendComm.getParam());
					}
				});
			},
			
			bindOrderEvent : function(callBack){
				$(document).on("click", ".m-sorting", function(){
					if($(this).hasClass("c-analyse15__sorting-down")){
						$(this).removeClass("c-analyse15__sorting-down").addClass("c-analyse15__sorting-up");
						$("#orderType").val("asc");
					}else{
						$(this).removeClass("c-analyse15__sorting-up").addClass("c-analyse15__sorting-down");
						$("#orderType").val("desc");
					}
					$("#orderAttr").val($(this).attr("id"));
					$(this).siblings().removeClass("c-analyse15__sorting-up").removeClass("c-analyse15__sorting-down");
					
					if(callBack){
						callBack(StudentAttendComm.getParam());
					}
				});
			},
			
			bindQuickSelectEvent : function(callBack){
				$(document).on("click", ".quickSelect", function(){
					if($(this).attr("id") == "today"){
						$(this).addClass("active").siblings().removeClass("active");
						$("#startDate").val(StudentAttendComm.getCurrentDate());
						$("#endDate").val(StudentAttendComm.getCurrentDate());
					}else if($(this).attr("id") == "yesterday"){
						$(this).addClass("active").siblings().removeClass("active");
						$("#startDate").val(StudentAttendComm.getDateByDelta(new Date(), -1));
						$("#endDate").val(StudentAttendComm.getDateByDelta(new Date(), -1));
					}else if($(this).attr("id") == "thisWeek"){
						$(this).addClass("active").siblings().removeClass("active");
						$("#startDate").val(StudentAttendComm.getStartEndDate().weekStartDate);
						$("#endDate").val(StudentAttendComm.getStartEndDate().weekEndDate);
					}else if($(this).attr("id") == "thisMonth"){
						$(this).addClass("active").siblings().removeClass("active");
						$("#startDate").val(StudentAttendComm.getStartEndDate().monthStartDate);
						$("#endDate").val(StudentAttendComm.getStartEndDate().monthEndDate);
					}
					
					if(callBack){
						callBack(StudentAttendComm.getParam());
					}
				});
			},
			
			bindPreviousDayEvent : function(callBack){
				$(document).on("click", "#less",function(){
					$("#startDate").val(StudentAttendComm.getDateByDelta($("#startDate").val(), -1));
					
					if(callBack){
						callBack(StudentAttendComm.getParam());
					}
				});
			},
			
			bindNextDayEvent : function(callBack){
				$(document).on("click", "#great",function(){
					if($("#startDate").val() == StudentAttendComm.getCurrentDate()){
						return false;
					}
					$("#startDate").val(StudentAttendComm.getDateByDelta($("#startDate").val(), 1));
					
					if(callBack){
						callBack(StudentAttendComm.getParam());
					}
				});
			},
			
			bindEvent : function(){
				
				$(document).on("change", "#gradeId", function(){
					StudentAttendComm.loadClassByGrade($(this).val());
				});
				
				$(document).on("click", ".c-analyse15__homework-item", function(){
					StudentAttendComm.saveGlobalParam($("#gradeId").val(), $("#classId").val(), 
							$(".c-analyse15__attendance-time").find(".active").attr("id"), $("#startDate").val(), $("#endDate").val());
				});
				
				$(document).on("click", "#icon", function(){
					Dialog.open({
						title: "学生考勤规则",
						tpl: dialogHelp,
						size: 'nm',
						btns: [{
							className: 'btn-green',
							text: "关闭",
							cb: function() {
								Dialog.close();
							}
						}]
					});
				});
				
				$(document).on("click", "#startDate",function(){
					WdatePicker({
						dateFmt : "yyyy-MM-dd",
						readOnly : true
					});
				});
				
				$(document).on("click", "#endDate",function(){
					WdatePicker({
						dateFmt : 'yyyy-MM-dd',
						readOnly : true
					});
				});
			}
		};

	module.exports = StudentAttendComm;
});
