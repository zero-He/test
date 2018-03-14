define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Mustache = require('mustache');
	var Page = require("page");
	var Utils = require('utils');
	var Dialog = require('dialog');
	var RenderEchart = require("./RenderEchart");
	var HandleCommParam = require("./HandleCommParam");
	var CommEvent = require("./CommEvent");
	var Handlebars = require('common/handlebars');
	
	var dialogHelp = ['<p>批改率=（系统批改人份+老师批改人份+学生互批人份）/总应批改作业人份。</p>',
	                  '<p>系统批改作业为纯客观题作业，含主观题作业都属老师应批改作业。</p>'
	                  ].join("");
	
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
				
				$(document).on("click", "#j_iconfont", function(){
					Dialog.open({
						title: "帮助",
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
				
			},
			
			loadStatOrDtlData : function(){
				if($("#stat").hasClass("c-analyse__sitem--cur")){
					Statistics.loadStatData(HandleCommParam.getParam());
				}
				if($("#detail").hasClass("c-analyse__sitem--cur")){
					Statistics.loadDtlDataPage(HandleCommParam.getParam());
				}
			},
			
			handleBarLineData : function(chartDivId, dataList, compType){
				var xData = [];
				var seriesData = [];
				var correctRateData = [];
				var shouldCorrectNumData = [];
				var actualCorrectNumData = [];
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
					correctRateData.push(dataList[i].correctNumRate);
					shouldCorrectNumData.push(dataList[i].shouldCorrectNum);
					actualCorrectNumData.push(dataList[i].actualCorrectNum);
				}
				
				seriesData.push({name:"应批人份", type:"bar", data:shouldCorrectNumData});
				seriesData.push({name:"实批人份", type:"bar", data:actualCorrectNumData});
				seriesData.push({name:"老师批改率", type:"line", yAxisIndex: 1, data:correctRateData});
				RenderEchart.renderMixBarLine(chartDivId, seriesData, ["应批人份","实批人份","老师批改率"], xData, '{b} <br/>{a} : {c}<br/>{a1} : {c1}<br/>{a2} : {c2}%', '', '%', '作业(人份)', '批改率', ["#ff9900","#619eed","#24bdee"]);
			},
			
			loadDtlDataPage : function(param){
				var url = "/auth/provost/teachingMonitor/homework/findCorrectHWDtl.htm?startDate="+param.startDate+"&endDate="+param.endDate+
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
				$.post("/auth/provost/teachingMonitor/homework/findCorrectHWDtl.htm", param, function(json){
					var data = json.datas;
					if (data) {
						var correctHWDtlList = Mustache.render($("#dtlTpl").html(), data);
						$("#jtbodyData").html(correctHWDtlList);
					}
				});
			},
			
			loadDtlOrderData : function(param){
				$.post("/auth/provost/teachingMonitor/homework/findCorrectHWDtlOrder.htm", param, function(json){
					var data = json.datas;
					if (data) {
						var correctHWDtlList = Mustache.render($("#dtlTpl").html(), data);
						$("#jtbodyData").html(correctHWDtlList);
					}
				});
			},
			
			loadStatData : function(param){
				$.post("/auth/provost/teachingMonitor/homework/findAllCorrectHWStatInfo.htm", param, function(json){
					var data = json.datas;
					var stat = data.statInfo;
					var trendList = data.trendList;
					var compList = data.compList;
					if (data) {
						var assignPieData = RenderEchart.buildStatPieData(2, ["纯客观题", "含主观题"], [stat.objectiveNum, stat.subjectiveNum]);
						var correctPieData = RenderEchart.buildStatPieData(4, ["老师批改", "学生互批", "系统批改", "未批改"], [stat.teacherCorrectNum, stat.studentCorrectNum, stat.autoCorrectNum, stat.notCorrectNum]);
						RenderEchart.renderStatInfoPie("assignInfo", assignPieData, ["#1fb5ab", "#24bdee"]);
						RenderEchart.renderStatInfoPie("correctInfo", correctPieData, ["#619eed", "#ffcb6b", "#1fb5ab", "#ff6666"]);
						
						//处理日期趋势数据
						Statistics.handleBarLineData("trendInfo", trendList, '');
						
						//处理横向对比数据
						Statistics.handleBarLineData("compInfo", compList, param.compType);
						
						var assignInfo = Mustache.render($("#assignInfoTpl").html(), data);
						$("#assignInfoBody").html(assignInfo);
						
						var correctInfo = Mustache.render($("#correctInfoTpl").html(), data);
						$("#correctInfoBody").html(correctInfo);
						
						Handlebars.render("#topFiveTpl", data, "#topFiveBody");
						
						Handlebars.render("#lastFiveTpl", data, "#lastFiveBody");
						
						Handlebars.render("#topFiveNumTpl", data, "#topFiveNumBody");
						
						Handlebars.render("#lastFiveNumTpl", data, "#lastFiveNumBody");

					}
					Utils.Notice.mask.close();
				});
			},
			
			loadTrendData : function(param){
				$.post("/auth/provost/teachingMonitor/homework/findCorrectHWByTrendType.htm", param, function(json){
					var data = json.datas;
					var trendList = data.correctHWTrendList;
					if (data) {
						//处理日期趋势数据
						Statistics.handleBarLineData("trendInfo", trendList, '');
					}
				});
			},
			
			loadCompData : function(param){
				$.post("/auth/provost/teachingMonitor/homework/findCorrectHWByCompType.htm", param, function(json){
					var data = json.datas;
					var compList = data.correctHWCompList;
					if (data) {
						//处理横向对比数据
						Statistics.handleBarLineData("compInfo", compList, param.compType);
					}
				});
			},
			
		};

		Statistics.init();
});
