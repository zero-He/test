define(function(require, exports, module) {
	var $ = require('jquery');
	var echart = require('echart');
	var theme = require('common/ui/ui-echarts/chart-theme');
	var I18n = require('diag/common/i18n');

	var Default = {
		labelRight : {
			normal : {
				label : {
					show : true,
					position : 'center',
					formatter : '{b}\n' + $.i18n.prop('diag.default.labelright.normal.formatter', '{c}'),
					textStyle : {
						baseline : 'bottom'
					}
				},
				labelLine : {
					show : false
				}
			}
		},
		labelWrong : {
			normal : {
				color : '#ccc',
				label : {
					show : true,
					position : 'center',
					formatter : $.i18n.prop('diag.default.labelwrong.normal.formatter', '{c}'),
					textStyle : {
						baseline : 'top'
					}
				},
				labelLine : {
					show : false
				}
			},
			emphasis : {
				color : 'rgba(0,0,0,0)'
			}
		},
		radius : [40, 55]
	};

	var FillBlank = {
		render : function(domNode, data) {
			var serieData = [];
			$.each(data, function(i, el) {
				var x = 70 + (i % 4) * 120;
				var y = 60 + Math.floor(i / 4) * 120;
				serieData.push({
					type : 'pie',
					center : [x, y],
					radius : Default.radius,
					data : [{
						name : 'wrong',
						value : el.wrong,
						itemStyle : Default.labelWrong
					}, {
						name : $.i18n.prop('diag.default.fillblank.data.name', (i + 1)),
						value : el.right,
						itemStyle : Default.labelRight
					}]
				});
			});
			var height = Math.ceil(data.length / 4) * 120;
			domNode.style.height = height + "px";
			var opts = {
				color : ['#f8856f', '#99bf6c'],
				series : serieData
			};
			var chart = echart.init(domNode, theme);
			chart.setOption(opts, true);
		}
	}

	module.exports = FillBlank;
});
