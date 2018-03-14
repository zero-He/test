define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
	var I18n = require('i18n');
	I18n.init('diag');

	var s_text = $.i18n.prop('diag.common.echarts.option.text');
	var s_topscore = $.i18n.prop('diag.common.echarts.option.topscore');
	var s_lowestscore = $.i18n.prop('diag.common.echarts.option.lowestscore');
	var s_avgscore = $.i18n.prop('diag.common.echarts.option.avgscore');
	var s_average = $.i18n.prop('diag.common.echarts.option.average');
	var s_formatter = $.i18n.prop('diag.common.echarts.option.formatter');

	var AchievementLine = $.extend({}, Chart, {
		chartId : "achievementStatTeacher",
		formId : 'searchForm',
		option : {
			title : {
				text : s_text,
				x : 'center'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				x : 'left',
				data : [s_topscore, s_lowestscore, s_avgscore]
			},
			dataZoom : {
				show : true,
				realtime : true
			},
			toolbox : {
				show : true,
				feature : {
					magicType : {
						show : true,
						type : ['line', 'bar']
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			xAxis : [{
				type : 'category',
				boundaryGap : false,
				data : []
			}],
			yAxis : [{
				type : 'value',
				axisLabel : {
					formatter : '{value} ' + s_formatter
				}
			}],
			series : [{
				name : s_topscore,
				type : 'line',
				data : []
			}, {
				name : s_lowestscore,
				type : 'line',
				data : []
			}, {
				name : s_avgscore,
				type : 'line',
				markLine : {
					data : [{type : 'average', name : '平均值'}]
				},
				data : []
			}]
		},
		rawDataCallback : function(data) {
			if (data.dataList && data.dataList.length == 0) {
				$('#jAchievementMes').hide();
			} else {
				$('#jAchievementMes').show();
			}
		}
	});

	module.exports = AchievementLine;
});
