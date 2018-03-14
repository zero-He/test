define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
	var I18n = require('i18n');
	I18n.init('diag');

	var s_text = $.i18n.prop('diag.common.echarts.option.text');
	var s_avgscore = $.i18n.prop('diag.common.echarts.option.avgscore');
	var s_average = $.i18n.prop('diag.common.echarts.option.average');
	var s_formatter = $.i18n.prop('diag.common.echarts.option.formatter');

	var AchievementLine = $.extend({}, Chart, {
		chartId : "gradeScoreStat",
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
				data : [s_avgscore]
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
						type : ['bar', 'line']
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
				data : []
			}],
			yAxis : [{
				type : 'value',
				axisLabel : {
					formatter : '{value} ' + s_formatter
				}
			}],
			series : [{
				name : s_avgscore,
				type : 'bar',
				data : [],
				markLine : {
					data : [{
						type : 'average',
						name : s_average
					}]
				}
			}]
		},
		rawDataCallback : function(data) {
			if (data.dataList.length == 0) {
				$('#jAchievementMes').hide();
			} else {
				$('#jAchievementMes').show();
			}
		}
	});

	module.exports = AchievementLine;
});
