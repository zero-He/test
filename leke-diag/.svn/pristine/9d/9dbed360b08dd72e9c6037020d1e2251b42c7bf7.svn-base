define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');

	var GradeDiligentPie = $.extend({}, Chart, {
		chartId : "gradeDiligent",
		initLoad : false,
		option : {
			title : {
				text : '勤奋报告',
				x : 'center'
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				y : 'center',
				data : ['正常提交', '延迟提交', '未提交']
			},
			color : Chart.Color.SUBMIT,
			series : [{
				type : 'pie',
				radius : '50%',
				center : ['55%', '55%'],
				itemStyle : {
					normal : {
						label : {
							position : 'outer',
							formatter : '{b}'
						},
						labelLine : {
							show : true
						}
					},
					emphasis : {
						label : {
							show : true,
							position : 'inner',
							formatter : "{b}\n {d}% ({c}次)"
						}
					}
				}
			}]
		}
	});

	module.exports = GradeDiligentPie;
});
