define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
	var I18n = require('i18n');
	I18n.init('diag');

	var s_text = $.i18n.prop('diag.common.echarts.option.text');
	var s_topscore = '课程最高分';
	var s_lowestscore = '课程最低分';
	var s_avgscore = '课程平均分';
	var s_average = $.i18n.prop('diag.common.echarts.option.average');
	var s_formatter = $.i18n.prop('diag.common.echarts.option.formatter');
	var score='我的得分';

	var AchievementLine = $.extend({}, Chart, {
		chartId : "achievementStat",
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
				data : [s_topscore, s_lowestscore, s_avgscore,score]
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
				data : []
			},{
				name : score,
				type : 'line',
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
			if (data.dataList && data.dataList.length == 0) {
				$('#jAchievementMes').hide();
			} else {
				$('#jAchievementMes').show();
			}
		}
	});

	module.exports = AchievementLine;
});
