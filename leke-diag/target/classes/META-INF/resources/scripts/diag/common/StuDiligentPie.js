define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
	
	var I18n = require('i18n');
	I18n.init('diag');
	
	var s_normalsubmit = $.i18n.prop('diag.common.echarts.option.normalsubmit');
	var s_delaysubmit = $.i18n.prop('diag.common.echarts.option.delaysubmit');
	var s_notsubmit = $.i18n.prop('diag.common.echarts.option.notsubmit');

	var StuDiligentPie = $.extend({}, Chart, {
		chartId : "stuDiligent",
		formId : 'searchForm',
		initLoad : false,
		option : {
			title : {
				text : '勤奋报告',
				x : 'center'
			},
			legend : {
				orient : 'vertical',
				x : 'right',
				y : 'center',
				data : [s_normalsubmit, s_delaysubmit, s_notsubmit]
			},
			color : Chart.Color.SUBMIT,
			series : [{
				type : 'pie',
				radius : '50%',
				center : ['45%', '55%'],
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

	module.exports = StuDiligentPie;
});
