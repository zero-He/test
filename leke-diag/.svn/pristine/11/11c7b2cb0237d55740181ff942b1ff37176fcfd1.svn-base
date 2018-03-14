define(function(require, exports, module) {
	var $ = require('jquery');
	var echart = require('echart');
	var theme = require('common/ui/ui-echarts/chart-theme');
	var SingleChoice = require('./SingleChoice');
	var I18n = require('diag/common/i18n');
	
	var Punctuate = {
		option : SingleChoice.option,
		render : function(domNode, data) {
			var legendData = [];
			$.each(data, function(i, el) {
				if (el.name == 'FULL') {
					el.name = $.i18n.prop('diag.punctuate.render.full');
				}
				if (el.name == 'LESS') {
					el.name = $.i18n.prop('diag.punctuate.render.not');
				}
				legendData.push(el.name);
			});
			var opts = $.extend(true, {}, this.option, {
				color : ['#f8856f', '#99bf6c'],
				legend : {
					data : legendData
				},
				series : [{
					data : data
				}]
			});
			var chart = echart.init(domNode, theme);
			chart.setOption(opts, true);
		}
	}

	module.exports = Punctuate;
});
