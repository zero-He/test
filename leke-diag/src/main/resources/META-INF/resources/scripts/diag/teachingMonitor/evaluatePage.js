define(function (require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Page = require("page");
	var Utils = require('utils');
	var Handlebars = require('common/handlebars');
	var RenderEchart = require("./RenderEchart");
	var HandleCommParam = require("./HandleCommParam");
	var CommEvent = require("./CommEvent");

	var Statistics = {

		init: function () {
			HandleCommParam.loadQueryParam(Statistics.loadStatData);
			HandleCommParam.initDateSelect();
			CommEvent.bindEvent();
			this.bindEvent();
			Utils.Notice.mask.create();
		},

		bindEvent: function () {

			//年级改变事件
			$("#gradeId").change(function () {
				$('#graId').val($('#gradeId').val());
				if ($(this).val() == "") {
					$("#compAll").show();
					$("#compGrade").hide();
					$("#compType").val("grade");
					$("#grade").addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');
				} else {
					$("#compGrade").show();
					$("#compAll").hide();
					$("#compType").val("clazz");
					$("#clazz").addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');
				}

				if ($("#stat").hasClass("c-analyse__sitem--cur")) {
					Statistics.loadStatData(HandleCommParam.getParam());
				} else {
					Statistics.loadDtlDataPage(HandleCommParam.getParam());
				}
			});

			//学科改变事件
			$("#subjectId").change(function () {
				$('#subId').val($('#subjectId').val());
				if ($("#stat").hasClass("c-analyse__sitem--cur")) {
					Statistics.loadStatData(HandleCommParam.getParam());
				} else {
					Statistics.loadDtlDataPage(HandleCommParam.getParam());
				}
			});

			//概览
			$("#stat").on("click", function () {
				$(this).addClass("c-analyse__sitem--cur").siblings().removeClass("c-analyse__sitem--cur");
				$("#statData").show();
				$("#dtlData").hide();
				Statistics.loadStatData(HandleCommParam.getParam());
			});

			//明细
			$("#detail").on("click", function () {
				$(this).addClass("c-analyse__sitem--cur").siblings().removeClass("c-analyse__sitem--cur");
				$("#dtlData").show();
				$("#statData").hide();
				Statistics.loadDtlDataPage(HandleCommParam.getParam());
			});

			//本周、本月、本学期
			$("#ulDate").on("click", "li", function () {
				var startEndDate = HandleCommParam.getStartEndDate();
				if ("month" == $(this).attr("id")) {
					$("#startDate").val(startEndDate.monthStartDate);
					$("#endDate").val(startEndDate.monthEndDate);
					$("#dateText").html($(this).html());
					Statistics.loadStatOrDtlData();
				} else if ("week" == $(this).attr("id")) {
					$("#startDate").val(startEndDate.weekStartDate);
					$("#endDate").val(startEndDate.weekEndDate);
					$("#dateText").html($(this).html());
					Statistics.loadStatOrDtlData();
				} else if ("term" == $(this).attr("id")) {
					$("#startDate").val(startEndDate.termStartDate);
					$("#endDate").val(startEndDate.termEndDate);
					$("#dateText").html($(this).html());
					Statistics.loadStatOrDtlData();
				}
			});

			//自定义时间确定触发
			$("#submit").on("click",function(){
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

			//点击日周月切换走势
			$("#trend").on("click", "a", function () {
				if ("byDay" == $(this).attr("id")) {
					$("#trendType").val("day");
				} else if ("byWeek" == $(this).attr("id")) {
					$("#trendType").val("week");
				} else if ("byMonth" == $(this).attr("id")) {
					$("#trendType").val("month");
				}
				$(this).addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');
				Statistics.loadTrendData(HandleCommParam.getParam());
			});

			//对比切换事件（年级|学科）
			$("#compAll").on("click", "a", function () {
				if ("grade" == $(this).attr("id")) {
					$("#compType").val("grade");
				} else if ("all_subject" == $(this).attr("id")) {
					$("#compType").val("all_subject");
				}
				$(this).addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');
				Statistics.loadCompData(HandleCommParam.getParam());
			});

			//对比切换事件（班级|学科）
			$("#compGrade").on("click", "a", function () {
				if ("clazz" == $(this).attr("id")) {
					$("#compType").val("clazz");
				} else if ("grade_subject" == $(this).attr("id")) {
					$("#compType").val("grade_subject");
				}
				$(this).addClass('c-timeswitch__item--cur').siblings().removeClass('c-timeswitch__item--cur');
				Statistics.loadCompData(HandleCommParam.getParam());
			});

			//排序点击事件
			$(".m-sorting").on("click", function () {
				if ($(this).hasClass("m-sorting-desc")) {
					$(this).removeClass("m-sorting-desc").addClass("m-sorting-asc");
					$("#orderType").val("asc");
				} else {
					$(this).removeClass("m-sorting-asc").addClass("m-sorting-desc");
					$("#orderType").val("desc");
				}
				$("#orderAttr").val($(this).attr("id"));
				$(this).siblings().removeClass("m-sorting-asc").removeClass("m-sorting-desc");
				Statistics.loadDtlDataPage(HandleCommParam.getParam());
			});

			//导出
			$("#export").on("click", function () {
				var param = HandleCommParam.getParam();
				var form = $("form");
				form.attr("action", "/auth/provost/teachingMonitor/evaluate/exportEvaluateDtlData.htm");
				form.submit();
			});
		},

		//概览或明细数据获取
		loadStatOrDtlData : function(){
			if($("#stat").hasClass("c-analyse__sitem--cur")){
				Statistics.loadStatData(HandleCommParam.getParam());
			}
			if($("#detail").hasClass("c-analyse__sitem--cur")){
				Statistics.loadDtlDataPage(HandleCommParam.getParam());
			}
		},

		//走势、对比数据获取
		handleBarLineData: function (dataList, chartType, compType) {
			var xData = [];
			var seriesData = [];
			var totalLevelData = [];
			for (var i = 0; i < dataList.length; i++) {
				if (compType == "grade") {
					xData.push(dataList[i].gradeName);
				} else if (compType == "clazz") {
					xData.push(dataList[i].className);
				} else if (compType == "all_subject" || compType == "grade_subject") {
					xData.push(dataList[i].subjectName);
				} else {
					xData.push(dataList[i].dateStr);
				}
				totalLevelData.push(dataList[i].avgTotal);
			}

			if (chartType === "line") {
				seriesData.push({name: "评分", type: chartType, data: totalLevelData});
				RenderEchart.renderBarLine('trendInfo', seriesData, [""], xData, chartType, '{b} <br/>{a} : {c}', '', ['#619eed']);
			} else {
				seriesData.push({name: "评分", type: chartType, data: totalLevelData});
				RenderEchart.renderBarLine('compInfo', seriesData, [""], xData, chartType, '{b} <br/>{a} : {c}', '', ['#619eed']);
			}
		},

		//首次数据渲染
		loadStatData: function (param) {
			$.post("/auth/provost/teachingMonitor/evaluate/queryEvaluatePageData.htm", param, function (json) {
				var data = json.datas.resultBean;
				var countBean = data.countBean;
				var trendList = data.trendList;
				var compList = data.compareBeanList;
				if (data) {

					//走势
					Statistics.handleBarLineData(trendList, "line", '');
					//对比
					Statistics.handleBarLineData(compList, "bar", param.compType);

					Handlebars.render("#statInfoTpl", data, "#statInfoBody");
					Handlebars.render("#topFiveTpl", data, "#topFiveBody");
					Handlebars.render("#lastFiveTpl", data, "#lastFiveBody");
				}
				Utils.Notice.mask.close();
			});
		},

		//明细分页请求
		loadDtlDataPage: function (param) {
			var url = "/auth/provost/teachingMonitor/evaluate/queryEvaluateDetailDataPage.htm?startDate=" + param.startDate + "&endDate=" + param.endDate +
				"&gradeId=" + param.gradeId + "&subjectId=" + param.subjectId + "&orderAttr=" + param.orderAttr + "&orderType=" + param.orderType;
			Page.create({
				id: 'jTablePage',
				url: url,
				formId: '',
				curPage: 1,
				pageSize: 15,
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
		},

		//走势数据获取
		loadTrendData: function (param) {
			$.post("/auth/provost/teachingMonitor/evaluate/queryEvaluatePageData.htm", param, function (json) {
				var data = json.datas.resultBean;
				var trendList = data.trendList;
				if (data) {
					//处理日期趋势数据
					Statistics.handleBarLineData(trendList, "line", '');
				}
			});
		},

		//对比数据获取
		loadCompData: function (param) {
			$.post("/auth/provost/teachingMonitor/evaluate/queryEvaluatePageData.htm", param, function (json) {
				var data = json.datas.resultBean;
				var compList = data.compareBeanList;
				if (data) {
					//处理横向对比数据
					Statistics.handleBarLineData(compList, "bar", param.compType);
				}
			});
		}


	};

	Statistics.init();
});
