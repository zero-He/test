define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');
	var Mustache = require('mustache');
	var DateUtil = require('common/base/date');
	
	var HandleCommParam = {
			
			fmt : "yyyy-MM-dd",
			
			saveGlobalParam : function(gradeId, subjectId, startDate, endDate, dateText){
				if(gradeId != null){
					store.set("globalGradeId", gradeId);
				}
				if(subjectId != null){
					store.set("globalSubjectId", subjectId);
				}
				if(startDate != null){
					store.set("globalStartDate", startDate);
				}
				if(endDate != null){
					store.set("globalEndDate", endDate);
				}
				if(dateText != null){
					store.set("globalDateText", dateText);
				}
			},
			
			getParam : function(){
				var param = {
						       gradeId : $("#gradeId").val(),
							   subjectId : $("#subjectId").val(),
							   startDate : $("#startDate").val(),
							   endDate : $("#endDate").val(),
							   trendType : $("#trendType").val(),
							   compType : $("#compType").val(),
							   orderAttr : $("#orderAttr").val(),
							   orderType : $("#orderType").val(),
							   teacherId : $("#teacherId").val(),
							   teacherName : $("#teacherName").val(),
							   classId : $("#classId").val(),
							   isPrepared : $("#isPrepared").val(),
							   lessonStatus : $("#lessonStatus").val(),
							 }
				return param;
			},
			
			getMonthDays : function(nowYear, myMonth){
		        var monthStartDate = new Date(nowYear, myMonth, 1);
		        var monthEndDate = new Date(nowYear, myMonth + 1, 1);
		        var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);
		        return days;
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
			    var weekEndDate = HandleCommParam.getDateByDelta(now, -1);

			    //获得本月的开始、结束日期
			    var monthStartDate = DateUtil.format(new Date(nowYear, nowMonth, 1), this.fmt);
			    /*var monthEndDate = DateUtil.format(new Date(nowYear, nowMonth, this.getMonthDays(nowYear,nowMonth)), this.fmt);*/
			    var monthEndDate = HandleCommParam.getDateByDelta(now, -1);

			    //获取本学期的开始、结束日期
			    var termUpstartDate = "-02-01";
			    /*var termUpEndDate = "-07-31";*/
			    var termDownstartDate = "-08-01";
			    /*var termDownEndDate = "-01-31";*/
			    
			    var termStartDate;
			    var termEndDate;
			    if(nowMonth >= 2 && nowMonth <= 6){
			    	termStartDate = nowYear + termUpstartDate;
			    	/*termEndDate = nowYear + termUpEndDate;*/
			    	termEndDate = HandleCommParam.getDateByDelta(now, -1);
			    }else{
			    	termStartDate = nowYear + termDownstartDate;
			    	/*termEndDate = nextYear + termDownEndDate;*/
				    termEndDate = HandleCommParam.getDateByDelta(now, -1);
			    }
				
			    starEndDate = {
			    	weekStartDate : weekStartDate,
			    	weekEndDate : weekEndDate,
			    	monthStartDate : monthStartDate,
			    	monthEndDate : monthEndDate,
			    	termStartDate : termStartDate,
			    	termEndDate : termEndDate,
			    };
			    
				return starEndDate;
			},
			
			initDateSelect : function(){
				var startEndDate = this.getStartEndDate();
			    
				if(this.getCurrentDate() == startEndDate.termStartDate){
					$("#term").hide(); 
					$("#startDate").val("9999-12-01");
					$("#endDate").val("9999-12-31");
					$("#dateText").html("本学期（学期第一天无历史数据）");
				}else{
					$("#term").show(); 
					$("#term").html("本学期（" + startEndDate.termStartDate + "--" + startEndDate.termEndDate + "）");
					$("#startDate").val(startEndDate.termStartDate);
					$("#endDate").val(startEndDate.termEndDate);
					$("#dateText").html($("#term").html());
				}
				
				if(this.getCurrentDate() == startEndDate.monthStartDate){
					$("#month").hide(); 
				}else{
					$("#month").show();
					$("#month").html("本月（" + startEndDate.monthStartDate + "--" + startEndDate.monthEndDate + "）");
				}
				
				if(this.getCurrentDate() == startEndDate.weekStartDate){
					$("#week").hide();
				}else{
					$("#week").show();
					$("#week").html("本周（" + startEndDate.weekStartDate + "--" + startEndDate.weekEndDate + "）");
				}
				
				var reg = /toShowStatSum.htm/;
				if(store.get("globalStartDate") && store.get("globalEndDate") && store.get("globalDateText") && !reg.test(window.location.href)){
					$("#startDate").val(store.get("globalStartDate"));
					$("#endDate").val(store.get("globalEndDate"));
					$("#dateText").html(store.get("globalDateText"));
				}
			},
			
			loadQueryParam : function(callBack){
				var that = this;
				$.post("/auth/provost/teachingMonitor/comm/loadQueryParam.htm", {}, function(json){
					var data = json.datas;
					var reg = /toShowHomePage.htm/;
					if (data) {
						var grade = Mustache.render($("#gradeTpl").html(), data);
						$("#gradeId").html(grade);
						if(store.get("globalGradeId") && !reg.test(window.location.href)){
							$("#gradeId").val(store.get("globalGradeId"));
						}else{
							$("#gradeId").val("");
						}
						
						var subject = Mustache.render($("#subjectTpl").html(), data);
						$("#subjectId").html(subject);
						if(store.get("globalSubjectId") && !reg.test(window.location.href)){
							$("#subjectId").val(store.get("globalSubjectId"));
						}else{
							$("#subjectId").val("");
						}
						
						if($("#classId")){
							var clazz = Mustache.render($("#classTpl").html(), data);
							$("#classId").html(clazz);
						}
						
						//记住上次查询条件后初始化横向对比页面
						if($("#gradeId").val() == ""){
							$("#compAll").show();
							$("#compGrade").hide();
							$("#compType").val("grade");
						}else{
							$("#compGrade").show();
							$("#compAll").hide();
							$("#compType").val("clazz");
						}
						$("."+$("#queryType").val()).addClass("active");
						if(callBack){
							callBack(that.getParam());
						}
					}
				});
			}
		};

	module.exports = HandleCommParam;
});
