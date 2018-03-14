define(function(require, exports, module) {
	var $ = require('jquery');
	var echart = require('echart');
	var theme = require('common/ui/ui-echarts/chart-theme');
	var I18n = require('diag/common/i18n');
	
	var Openend = {
		option : {
			grid : {
				x : 40,
				y : 25,
				x2 : 20,
				y2 : 40
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				},
				formatter : function(params) {
					return params[1][1] + params[1][0] + ' : ' + params[1][2] + "人";
				}
			},
			color : ['rgba(0,0,0,0)', '#99bf6c'],
			xAxis : [{
				type : 'category',
				splitLine : {
					show : false
				},
				data : []
			}],
			yAxis : [{
				type : 'value',
				axisLabel : {
					formatter : '{value}' + $.i18n.prop('diag.common.echarts.option.person')
				}
			}],
			series : [{
				name : $.i18n.prop('diag.openend.series.name'),
				type : 'bar',
				stack : $.i18n.prop('diag.common.echarts.option.gross'),
				itemStyle : {
					normal : {
						borderColor : 'rgba(0,0,0,0)',
						color : 'rgba(0,0,0,0)'
					},
					emphasis : {
						borderColor : 'rgba(0,0,0,0)',
						color : 'rgba(0,0,0,0)'
					}
				},
				data : []
			}, {
				name : $.i18n.prop('diag.openend.series.num'),
				type : 'bar',
				stack : $.i18n.prop('diag.common.echarts.option.gross'),
				itemStyle : {
					normal : {
						label : {
							show : true,
							position : 'top',
							formatter : '{c}' + $.i18n.prop('diag.common.echarts.option.person')
						}
					}
				},
				data : []
			}]
		},
		render : function(domNode, data) {
			var xAxisData = [];
			var serieData1 = [];
			var serieData2 = [];
			var sumVal = 0;
			$.each(data, function(i, el) {
				xAxisData.push(el.name + $.i18n.prop('diag.common.echarts.data.mark'));
				serieData1.push(sumVal);
				sumVal += el.value;
				serieData2.push(el.value);
			});
			xAxisData.push($.i18n.prop('diag.common.echarts.data.total'));
			serieData1.push(0);
			serieData2.push(sumVal);
			var opts = $.extend(true, {}, this.option, {
				xAxis : [{
					data : xAxisData.reverse()
				}],
				series : [{
					data : serieData1.reverse()
				}, {
					data : serieData2.reverse()
				}]
			});
			var chart = echart.init(domNode, theme);
			chart.setOption(opts, true);
		}
	}

	module.exports = Openend;
});
