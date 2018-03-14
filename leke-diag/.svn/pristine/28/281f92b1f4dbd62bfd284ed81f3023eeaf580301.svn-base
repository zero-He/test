define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Mustache = require('mustache');
	var Page = require("page");
	var Utils = require('utils');
	var RenderEchart = require("./RenderEchart");
	var HandleCommParam = require("./HandleCommParam");
	var CommEvent = require("./CommEvent");
	
	var Statistics = {

			init : function() {
				HandleCommParam.loadQueryParam(Statistics.loadStatData);
				HandleCommParam.initDateSelect();
				CommEvent.bindEvent();
				this.bindEvent();
				Utils.Notice.mask.create();
			},
			
			bindEvent : function(){
				
				$(document).on("change", "#gradeId", function(){
					if($(this).val() == ""){
						$("#compAll").show();
						$("#compGrade").hide();
						$("#compType").val("grade");
						$("#grade").addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');
					}else{
						$("#compGrade").show();
						$("#compAll").hide();
						$("#compType").val("clazz");
						$("#clazz").addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');
					}
					Statistics.loadStatOrDtlData();
				});
				
				$(document).on("change", "#subjectId", function(){
					Statistics.loadStatOrDtlData();
				});
				
				$(document).on("click", "#stat", function(){
					$(this).addClass("c-analyse__sitem--cur").siblings().removeClass("c-analyse__sitem--cur");
					$("#statData").show();
					$("#dtlData").hide();
					Statistics.loadStatData(HandleCommParam.getParam());
				});
				
				$(document).on("click", "#detail", function(){
					$(this).addClass("c-analyse__sitem--cur").siblings().removeClass("c-analyse__sitem--cur");
					$("#dtlData").show();
					$("#statData").hide();
					Statistics.loadDtlDataPage(HandleCommParam.getParam());
				});
				
				$(document).on("click", "#ulDate li", function(){
					var startEndDate = HandleCommParam.getStartEndDate();
					if("month" == $(this).attr("id")){
						$("#startDate").val(startEndDate.monthStartDate);
						$("#endDate").val(startEndDate.monthEndDate);
						$("#dateText").html($(this).html());
						Statistics.loadStatOrDtlData();
					}else if("week" == $(this).attr("id")){
						$("#startDate").val(startEndDate.weekStartDate);
						$("#endDate").val(startEndDate.weekEndDate);
						$("#dateText").html($(this).html());
						Statistics.loadStatOrDtlData();
					}else if("term" == $(this).attr("id")){
						$("#startDate").val(startEndDate.termStartDate);
						$("#endDate").val(startEndDate.termEndDate);
						$("#dateText").html($(this).html());
						Statistics.loadStatOrDtlData();
					}
				});
				
				$(document).on("click", "#submit", function(){
					var startTime = $('#customStart').val();
					var endTime = $("#customEnd").val();
					
					if(!startTime || !endTime){
						Utils.Notice.alert("开始日期、结束日期都不能为空!");
						return false;
					}else if(new Date(startTime) > new Date(endTime)){
						Utils.Notice.alert("开始日期不能大于结束日期!");
						return false;
					}
					
					$("#startDate").val(startTime);
					$("#endDate").val(endTime);
					$("#dateText").html(startTime + "--" + endTime);
					Statistics.loadStatOrDtlData();
				});
				
				$(document).on("click", "#trend a", function(){
					if("byDay" == $(this).attr("id")){
						$("#trendType").val("day");
					}else if("byWeek" == $(this).attr("id")){
						$("#trendType").val("week");
					}else if("byMonth" == $(this).attr("id")){
						$("#trendType").val("month");
					}
					$(this).addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');
					Statistics.loadTrendData(HandleCommParam.getParam());
				});
				
				$(document).on("click", "#compAll a", function(){
					if("grade" == $(this).attr("id")){
						$("#compType").val("grade");
					}else if("all_subject" == $(this).attr("id")){
						$("#compType").val("all_subject");
					}
					$(this).addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');
					Statistics.loadCompData(HandleCommParam.getParam());
				});
				
				$(document).on("click", "#compGrade a", function(){
					if("clazz" == $(this).attr("id")){
						$("#compType").val("clazz");
					}else if("grade_subject" == $(this).attr("id")){
						$("#compType").val("grade_subject");
					}
					$(this).addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');
					Statistics.loadCompData(HandleCommParam.getParam());
				});
				
				$(document).on("click", ".m-sorting", function(){
					if($(this).hasClass("m-sorting-desc")){
						$(this).removeClass("m-sorting-desc").addClass("m-sorting-asc");
						$("#orderType").val("asc");
					}else{
						$(this).removeClass("m-sorting-asc").addClass("m-sorting-desc");
						$("#orderType").val("desc");
					}
					$("#orderAttr").val($(this).attr("id"));
					$(this).siblings().removeClass("m-sorting-asc").removeClass("m-sorting-desc");
					Statistics.loadDtlDataPage(HandleCommParam.getParam());
				});
				
			},
			
			loadStatOrDtlData : function(){
				if($("#stat").hasClass("c-analyse__sitem--cur")){
					Statistics.loadStatData(HandleCommParam.getParam());
				}
				if($("#detail").hasClass("c-analyse__sitem--cur")){
					Statistics.loadDtlDataPage(HandleCommParam.getParam());
				}
			},
			
			handleBarLineData : function(dataList, chartType, compType){
				var xData = [];
				var seriesData = [];
				var resolveData = [];
				var in24HourData = [];
				var out24HourData = [];
				for(var i = 0; i < dataList.length; i++){
					if(compType == "grade"){
						xData.push(dataList[i].gradeName);
					}else if(compType == "clazz"){
						xData.push(dataList[i].className);
					}else if(compType == "all_subject" || compType == "grade_subject"){
						xData.push(dataList[i].subjectName);
					}else{
						xData.push(dataList[i].dateStr);
					}
					resolveData.push(dataList[i].resolveDoubt);
					in24HourData.push(dataList[i].in24HourResolveDoubt);
					out24HourData.push(dataList[i].out24HourResolveDoubt);
				}
				
				if(chartType == "line"){
					seriesData.push({name:"解答总数", type:chartType, data:resolveData});
					seriesData.push({name:"24小时内解答数", type:chartType, data:in24HourData});
					seriesData.push({name:"24小时外解答数", type:chartType, data:out24HourData});
					RenderEchart.renderBarLine('trendInfo', seriesData, ["解答总数","24小时内解答数","24小时外解答数"], xData, chartType, '{b} <br/>{a} : {c}<br/>{a1} : {c1}<br/>{a2} : {c2}', '', ["#ff6666","#619eed","#ffcb6b"]);
				}else{
					seriesData.push({name:"24小时内解答数", type:chartType, stack:'解答数', data:in24HourData});
					seriesData.push({name:"24小时外解答数", type:chartType, stack:'解答数', data:out24HourData});
					RenderEchart.renderBarLine('compInfo', seriesData, ["24小时内解答数","24小时外解答数"], xData, chartType, '{b} <br/>{a} : {c}<br/>{a1} : {c1}', '', ["#619eed","#ffcb6b"]);
				}
			},
			
			loadDtlDataPage : function(param){
				var url = "/auth/provost/teachingMonitor/doubt/findResolveDoubtDtl.htm?startDate="+param.startDate+"&endDate="+param.endDate+
				"&gradeId="+param.gradeId+"&subjectId="+param.subjectId+"&orderAttr="+param.orderAttr+"&orderType="+param.orderType;
				Page.create({
					id : 'jTablePage',
					url : url,
					formId : '',
					curPage : 1,
					pageSize : 15,
					pageCount : 10,// 分页栏上显示的分页数
					tips : '暂无数据！',
					callback : function(datas) {
						var data = datas.page;
						if (data) {
							var output = Mustache.render($("#dtlTpl").html(), data);
							$('#jtbodyData').html(output);
						} else {
							$('#jtbodyData').html('');
						}
					}
				})},
			
			loadDtlData : function(param){
				$.post("/auth/provost/teachingMonitor/doubt/findResolveDoubtDtl.htm", param, function(json){
					var data = json.datas;
					if (data) {
						var beikeRateDtlList = Mustache.render($("#dtlTpl").html(), data);
						$("#jtbodyData").html(beikeRateDtlList);
					}
				});
			},
			
			loadDtlOrderData : function(param){
				$.post("/auth/provost/teachingMonitor/doubt/findResolveDoubtDtlOrder.htm", param, function(json){
					var data = json.datas;
					if (data) {
						var beikeRateDtlList = Mustache.render($("#dtlTpl").html(), data);
						$("#jtbodyData").html(beikeRateDtlList);
					}
				});
			},
			
			loadStatData : function(param){
				$.post("/auth/provost/teachingMonitor/doubt/findAllResolveDoubtStatInfo.htm", param, function(json){
					var data = json.datas;
					var stat = data.statInfo;
					var trendList = data.trendList;
					var compList = data.compList;
					if (data) {
						var pieData = RenderEchart.buildStatPieData(3, ["24小时内解答", "24小时外解答", "待解答"], [stat.in24HourResolveDoubt, stat.out24HourResolveDoubt, stat.notResolveDoubt]);
						RenderEchart.renderStatInfoPie("statInfo", pieData, ["#619eed", "#ffcb6b", "#ff6666"]);
						
						//处理日期趋势数据
						Statistics.handleBarLineData(trendList, "line", '');
						
						//处理横向对比数据
						Statistics.handleBarLineData(compList, "bar", param.compType);
						
						var statInfo = Mustache.render($("#statInfoTpl").html(), data);
						$("#statInfoBody").html(statInfo);
						
						var topFive = Mustache.render($("#topFiveTpl").html(), data);
						$("#topFiveBody").html(topFive);
						
						var lastFive = Mustache.render($("#lastFiveTpl").html(), data);
						$("#lastFiveBody").html(lastFive);

					}
					Utils.Notice.mask.close();
				});
			},
			
			loadTrendData : function(param){
				$.post("/auth/provost/teachingMonitor/doubt/findResolveDoubtByTrendType.htm", param, function(json){
					var data = json.datas;
					var trendList = data.resolveDoubtTrendList;
					if (data) {
						//处理日期趋势数据
						Statistics.handleBarLineData(trendList, "line", '');
					}
				});
			},
			
			loadCompData : function(param){
				$.post("/auth/provost/teachingMonitor/doubt/findResolveDoubtByCompType.htm", param, function(json){
					var data = json.datas;
					var compList = data.resolveDoubtCompList;
					if (data) {
						//处理横向对比数据
						Statistics.handleBarLineData(compList, "bar", param.compType);
					}
				});
			},
			
		};

		Statistics.init();
});
