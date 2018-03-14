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
			StudentBehaviorComm.exportClick("/auth/provost/studentMonitor/activeLearningAnalyse/exportActiveLearningDtlData.htm");
			CommEvent.bindEvent();
			this.bindEvent();
			StudentBehaviorComm.clearBrowerCache();
			Utils.Notice.mask.create();
		},

		bindEvent: function () {
			//走势下拉
			$("#trendSelect").change(function () {
				Statistics.loadTrendData(StudentBehaviorComm.getParam());
			});
			//对比下拉
			$("#compareSelect").change(function () {
				Statistics.loadCompData(StudentBehaviorComm.getParam());
			});

		},

		//走势、对比数据获取
		handleBarLineData: function (dataList, chartType, compType, selectType) {
			var xData = [];
			var seriesData = [];
			var avgLearningNumData = [];
			var avgQuestionNumData = [];
			for (var i = 0; i < dataList.length; i++) {
				if (compType === "clazz") {
					xData.push(dataList[i].className);
				} else {
					xData.push(dataList[i].dateStr);
				}
				avgLearningNumData.push(dataList[i].avgLearningNum);
				avgQuestionNumData.push(dataList[i].avgQuestionNum);
			}

			if (chartType === "line") {
				if (selectType === "1") {
					seriesData.push({name: "人均学习知识点数", type: chartType, data: avgLearningNumData});
					RenderEchart.renderBarLine('trendInfo', seriesData, ["人均学习知识点数"], xData, chartType, '{b} <br/>{a} : {c}', '', ['#619eed']);
				} else {
					seriesData.push({name: "人均刷题数", type: chartType, data: avgQuestionNumData});
					RenderEchart.renderBarLine('trendInfo', seriesData, ["人均刷题数"], xData, chartType, '{b} <br/>{a} : {c}', '', ['#619eed']);
				}
			} else {
				if (selectType === "1") {
					seriesData.push({name: "人均学习知识点数", type: chartType, stack: '1', data: avgLearningNumData, markLine: {data: [{type: 'average', name: '平均值'}]}});
					RenderEchart.renderBarLine('compInfo', seriesData, ["人均学习知识点数"], xData, chartType, '{b} <br/>{a} : {c}', '', ['#619eed']);
				} else {
					seriesData.push({name: "人均刷题数", type: chartType, data: avgQuestionNumData, markLine: {data: [{type: 'average', name: '平均值'}]}});
					RenderEchart.renderBarLine('compInfo', seriesData, ["人均刷题数"], xData, chartType, '{b} <br/>{a} : {c}', '', ['#619eed']);
				}
			}
		},

		//首次数据渲染
		loadStatData: function (param) {
			$.post("/auth/provost/studentMonitor/activeLearningAnalyse/queryActiveLearningAnalysePageData.htm", param, function (json) {
				var data = json.datas.resultBean;
				var countBean = data.countBean;
				var trendList = data.trendList;
				var compList = data.compareBeanList;
				if (data) {
					//走势
					Statistics.handleBarLineData(trendList, "line", '', param.trendSelect);
					//对比
					Statistics.handleBarLineData(compList, "bar", param.compType, param.compareSelect);

					Handlebars.render("#statInfoTpl", data, "#statInfoBody");
					Handlebars.render("#topFiveTpl", data, "#topFiveBody");
					Handlebars.render("#lastFiveTpl", data, "#lastFiveBody");
					Handlebars.render("#topFiveTplByQuestion", data, "#topFiveBodyByQuestion");
					Handlebars.render("#lastFiveTplByQuestion", data, "#lastFiveBodyByQuestion");

				}
				Utils.Notice.mask.close();
			});
		},

		//明细分页请求
		loadDtlDataPage: function (param) {
			var url = "/auth/provost/studentMonitor/activeLearningAnalyse/queryActiveLearningDetailDataPage.htm?startDate=" + param.startDate + "&endDate=" + param.endDate +
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
			$.post("/auth/provost/studentMonitor/activeLearningAnalyse/queryActiveLearningTrendData.htm", param, function (json) {
				var data = json.datas.resultBean;
				var trendList = data.trendList;
				if (data) {
					//处理日期趋势数据
					Statistics.handleBarLineData(trendList, "line", '', param.trendSelect);
				}
			});
		},

		//对比数据获取
		loadCompData: function (param) {
			$.post("/auth/provost/studentMonitor/activeLearningAnalyse/queryActiveLearningCompareData.htm", param, function (json) {
				var data = json.datas.resultBean;
				var compList = data.compareBeanList;
				if (data) {
					//处理横向对比数据
					Statistics.handleBarLineData(compList, "bar", param.compType, param.compareSelect);
				}
			});
		}

	};

	Statistics.init();
});
