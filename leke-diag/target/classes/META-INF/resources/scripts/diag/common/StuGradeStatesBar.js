define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
	var avgScore = '平均分';
	var AllAnalysisBar = $.extend({}, Chart, {
		chartId : "stuGradeStates",
		formId : 'searchForm',
		option : {
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				}
			},
			title :{
				text : '学科优劣分析',
				x : 'center'
			},
			grid : {
				
			},
			legend : {
				data : [avgScore],
				x : 'left'
			},
			dataZoom : {
				show : true
			},
			color : Chart.Color.SCORE,
			toolbox : {
				show : true,
				orient : 'vertical',
				y : 'center',
				feature : {
					magicType : {
						show : true,
						type : ['bar', 'stack']
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
						str += "<br/>" + param[i][0] + "：" + param[i][2] + "分";
					}
					return str;
				}
			},
			yAxis : [{
				type : 'value',
				min : 0,
				max : 100,
				axisLabel : {
					formatter : '{value}分'
				}
			}],
			xAxis : [{
				type : 'category',
				data : []
			}],
			series : [{
				name : avgScore,
				type : 'bar',
				itemStyle : {
					normal : {
						label : {
							show : true,
							position : 'inside',
							formatter : function(a, b, c) {
								if (c >= 85) {
									return 'A';
								} else if (c >= 70) {
									return 'B';
								} else if (c >= 60) {
									return 'C';
								} else {
									return 'D';
								}
							}
						}
					}
				}
			}]
		}
	});

	module.exports = AllAnalysisBar;

});
