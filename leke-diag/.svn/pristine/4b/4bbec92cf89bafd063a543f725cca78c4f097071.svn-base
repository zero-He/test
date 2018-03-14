define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');

	var AllAnalysisBar = $.extend({}, Chart, {
		chartId : "allAnalysis",
		formId : 'searchForm',
		option : {
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				}
			},
			grid : {
				y : 30
			},
			legend : {
				data : ['A：85-100分', 'B：70-85分', 'C：60-70分', 'D：0-60分']
			},
			dataZoom : {
				show : true,
				realtime : true
			},
			color : Chart.Color.SCORE,
			toolbox : {
				show : true,
				orient : 'vertical',
				y : 'center',
				feature : {
					magicType : {
						show : true,
						type : ['stack', 'tiled']
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				},
				formatter : function(param) {
					var str = param[0][1];
					for (var i = 0; i < param.length; i++) {
						str += "<br/>" + param[i][0] + "：" + param[i][2] + "%";
					}
					return str;
				}
			},
			yAxis : [{
				type : 'value',
				axisLabel : {
					formatter : '{value}%'
				}
			}],
			xAxis : [{
				type : 'category',
				data : []
			}],
			series : [{
				name : 'A：85-100分',
				type : 'bar',
				stack : '总量',
				data : []
			}, {
				name : 'B：70-85分',
				type : 'bar',
				stack : '总量',
				data : []
			}, {
				name : 'C：60-70分',
				type : 'bar',
				stack : '总量',
				data : []
			}, {
				name : 'D：0-60分',
				type : 'bar',
				stack : '总量',
				data : []
			}]
		}
	});

	module.exports = AllAnalysisBar;

});
