define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');

	var labelMap = {
		'1' : '语文',
		'2' : '数学',
		'3' : '英语',
		'4' : '物理',
		'5' : '化数',
		'6' : '生物',
		'7' : '政治',
		'8' : '历史',
		'9' : '地理'
	};
	var idx = 1;
	var ClsAnalysisPie = $.extend({}, Chart, {
		chartId : "clsAnalysis",
		formId : 'searchForm',
		option : {
			timeline : {
				autoPlay : true,
				type : 'number',
				data : ['1'],
				label : {
					formatter : function(s) {
						return labelMap[s];
					}
				}
			},
			options : [{
				tooltip : {
					trigger : 'item',
					formatter : "{b}<br/>{c}人 ({d}%)"
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					y : 'center',
					data : ['A：85-100分', 'B：70-85分', 'C：60-70分', 'D：0-60分']
				},
				color : Chart.Color.SCORE,
				toolbox : {
					show : true,
					feature : {
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				series : [{
					type : 'pie',
					center : ['55%', '45%'],
					radius : '60%',
					itemStyle : {
						normal : {
							label : {
								formatter : function(a, b, c, d) {
									return b.substring(0, 1) + "：" + d + "%";
								}
							}
						}
					},
					data : []
				}]
			}]
		},
		rawDataCallback : function(data) {
			labelMap = data.labelMap;
		}
	});

	module.exports = ClsAnalysisPie;

});
