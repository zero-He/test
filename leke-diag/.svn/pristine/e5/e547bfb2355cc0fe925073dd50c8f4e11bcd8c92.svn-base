define(function (require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Page = require("page");
	var Utils = require('utils');
	var Handlebars = require('common/handlebars');
	var RenderEchart = require("diag/teachingMonitor/RenderEchart");
	var StudentBehaviorComm = require("./studentBehaviorComm");
	var CommEvent = require("diag/teachingMonitor/CommEvent");

	var Statistics = {

		init: function () {
			StudentBehaviorComm.initDateSelect();
			StudentBehaviorComm.loadQueryParamPro(Statistics.loadStatData);
			StudentBehaviorComm.quickQuerySelect(Statistics.loadStatData, Statistics.loadDtlDataPage);
			StudentBehaviorComm.statClick(Statistics.loadStatData);
			StudentBehaviorComm.detailClick(Statistics.loadDtlDataPage);
			StudentBehaviorComm.trendClick(Statistics.loadTrendData);
			StudentBehaviorComm.exportClick("/auth/provost/studentMonitor/readyAnalyse/exportReadyDtlData.htm");
			CommEvent.bindEvent();
			this.bindEvent();
			StudentBehaviorComm.clearBrowerCache();
			Utils.Notice.mask.create();
		},

		bindEvent: function () {
			$(document).on("click", "#operation", function(){
				var studentId = $(this).data("id");
				var studentName = $(this).data("name");
				var param = StudentBehaviorComm.getParam();
				param.studentId = $(this).data("id");
				param.studentName = $(this).data("name");
				var url = "/auth/provost/studentMonitor/readyAnalyse/stuReadyDetailPage.htm?startDate=" + param.startDate + "&endDate=" + param.endDate + "&classId=" + param.classId
					+ "&studentId=" + param.studentId + "&studentName=" + param.studentName + "&subjectId=" + param.subjectId;
				window.open(url);
			});
		},

		//走势、对比数据获取
		handleBarLineData: function (dataList, chartType, compType) {
			var xData = [];
			var seriesData = [];
			var allReadyProData = [];
			var noReadyProData = [];
			var partReadyProData = [];
			for (var i = 0; i < dataList.length; i++) {
				if (compType === "clazz") {
					xData.push(dataList[i].className);
				}  else {
					xData.push(dataList[i].dateStr);
				}
				allReadyProData.push(dataList[i].allReadyPro);
				partReadyProData.push(dataList[i].partReadyPro);
				noReadyProData.push(dataList[i].noReadyPro);
			}

			if (chartType === "line") {
				seriesData.push({name: "全面预习率", type: chartType, data: allReadyProData});
				seriesData.push({name: "部分预习率", type: chartType, data: partReadyProData});
				seriesData.push({name: "未预习率", type: chartType, data: noReadyProData});
				RenderEchart.renderBarLine('trendInfo', seriesData, ["全面预习率", "部分预习率", "未预习率"], xData, chartType, '{b} <br/>{a} : {c}<br/>{a1} : {c1}<br/>{a2} : {c2}', '', ['#619eed', '#ffcb6b', '#ff6666']);
			} else {
				seriesData.push({name: "全面预习率", type: chartType, stack: '1', data: allReadyProData});
				seriesData.push({name: "部分预习率", type: chartType, stack: '1', data: partReadyProData});
				RenderEchart.renderBarLine('compInfo', seriesData, ["全面预习率", "部分预习率"], xData, chartType, '{b} <br/>{a} : {c}<br/>{a1} : {c1}', '', ['#619eed', '#ffcb6b']);
			}
		},

		//首次数据渲染
		loadStatData: function (param) {
			$.post("/auth/provost/studentMonitor/readyAnalyse/queryReadyAnalysePageData.htm", param, function (json) {
				var data = json.datas.resultBean;
				var countBean = data.countBean;
				var trendList = data.trendList;
				var compList = data.compareBeanList;
				if (data) {
					var pieData = RenderEchart.buildStatPieData(3, ["全面预习", "部分预习", "未预习"], [countBean.allReadyPro, countBean.partReadyPro, countBean.noReadyPro]);
					RenderEchart.renderStatInfoPie("statInfo", pieData, ['#619eed', '#ffcb6b', '#ff6666']);
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
			var url = "/auth/provost/studentMonitor/readyAnalyse/queryReadyDetailDataPage.htm?startDate=" + param.startDate + "&endDate=" + param.endDate +
				"&gradeId=" + param.gradeId + "&classId=" + param.classId + "&subjectId=" + param.subjectId + "&orderAttr=" + param.orderAttr + "&orderType=" + param.orderType;
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
					Utils.Notice.mask.close();
				}

			})
		},


		//走势数据获取
		loadTrendData: function (param) {
			$.post("/auth/provost/studentMonitor/readyAnalyse/queryReadyTrendData.htm", param, function (json) {
				var data = json.datas.resultBean;
				var trendList = data.trendList;
				if (data) {
					//处理日期趋势数据
					Statistics.handleBarLineData(trendList, "line", '');
				}
			});
		}

	};

	Statistics.init();
});
