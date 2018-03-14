define(function (require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Page = require("page");
	var Utils = require('utils');
	var Dialog = require('dialog');
	var Handlebars = require('common/handlebars');
	var RenderEchart = require("diag/teachingMonitor/RenderEchart");
	var StudentBehaviorComm = require("./studentBehaviorComm");
	var CommEvent = require("diag/teachingMonitor/CommEvent");

	var dialogHelp = [
		'<p>下面四个统计项目均以在选定时间内产生的数据进行统计分析，其中：</p>',
		'<p>1.课堂录像只统计有生成录像的课堂，学生观看时长2分钟及以上定为课堂录像查看行为发生。</p>',
		'<p>2.人均查看课堂录像数=每个学生查看课堂录像数之和/学生总人数，同一堂课多次查看只计数一个。</p>',
		'<p>3.人均每次查看录像时长=每个学生查看课堂录像总时长之和/每个学生查看次数之和，查看次数包括同一课堂多次查看。</p>',
		'<p>4.人均课堂录像查看率=每个学生课堂录像查看率之和/学生总人数。</p>'
	].join("");

	var Statistics = {

			init: function () {
				StudentBehaviorComm.initDateSelect();
				StudentBehaviorComm.loadQueryParamPro(Statistics.loadStatData);
				StudentBehaviorComm.quickQuerySelect(Statistics.loadStatData, Statistics.loadDtlDataPage);
				StudentBehaviorComm.statClick(Statistics.loadStatData);
				StudentBehaviorComm.detailClick(Statistics.loadDtlDataPage);
				StudentBehaviorComm.exportClick("/auth/provost/studentMonitor/otherAnalyse/exportOtherDtlData.htm");
				CommEvent.bindEvent();
				this.bindEvent();
				StudentBehaviorComm.clearBrowerCache();
				Utils.Notice.mask.create();
			},

			bindEvent: function () {

				$(document).on("click", "#j_iconfont", function () {
					Dialog.open({
						title: "帮助",
						tpl: dialogHelp,
						size: 'nm',
						btns: [{
							className: 'btn-green',
							text: "关闭",
							cb: function () {
								Dialog.close();
							}
						}]
					});
				});

			},

			//首次数据渲染
			loadStatData: function (param) {
				$.post("/auth/provost/studentMonitor/otherAnalyse/queryOtherAnalysePageData.htm", param, function (json) {
					var data = json.datas;
					if (data) {
						Handlebars.render("#oneFiveTpl", data, "#oneFiveBody");
					}
					Utils.Notice.mask.close();
				});
			}
			,

			//明细分页请求
			loadDtlDataPage: function (param) {
				var url = "/auth/provost/studentMonitor/otherAnalyse/queryOtherDetailDataPage.htm?startDate=" + param.startDate + "&endDate=" + param.endDate +
					"&gradeId=" + param.gradeId + "&classId=" + param.classId + "&orderAttr=" + param.orderAttr + "&orderType=" + param.orderType;
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
			}

		};

	Statistics.init();
});
