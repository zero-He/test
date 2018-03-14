define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');

	var AllAnalysisBar = $.extend({}, Chart, {
		chartId : "stuAnalysisBar",
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
				data : ['同学最高平均分','班级平均分','我的平均分']
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
						str += "<br/>" + param[i][0] + "：" + param[i][2] ;
					}
					return str;
				}
			},
			yAxis : [{
				type : 'value',
				axisLabel : {
					formatter : '{value}'
				}
			}],
			xAxis : [{
				type : 'category',
				data : []
			}],
			series : [{
				name : '同学最高平均分',
				type : 'bar',
				data : []
			}, {
				name : '班级平均分',
				type : 'bar',
				data : []
			}, {
				name : '我的平均分',
				type : 'bar',
				data : []
			}]
		}
	});

	module.exports = AllAnalysisBar;

});
