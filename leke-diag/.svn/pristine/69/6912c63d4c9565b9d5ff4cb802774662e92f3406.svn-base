define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
	var Mustache = require('mustache');
	var I18n = require('i18n');
	I18n.init('diag');

	var s_formatter = $.i18n.prop('diag.common.echarts.option.formatter');
	var s_person = $.i18n.prop('diag.common.echarts.option.person');
	var SubAnalysisPie = $.extend({}, Chart, {
		chartId : "subAnalysis",
		formId : 'searchForm',
		option : {
			title : {},
			legend : {
				orient : 'vertical',
				x : 'left',
				y : 'center',
				data : ['A：85-100' + s_formatter, 'B：70-85' + s_formatter, 'C：60-70' + s_formatter,
						'D：0-60' + s_formatter]
			},
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
			color : Chart.Color.SCORE,
			series : [{
				type : 'pie',
				radius : '65%',
				center : ['55%', '55%'],
				selectedMode : 'single',
				itemStyle : {
					normal : {
						label : {
							position : 'outer',
							formatter : "{b}({c}" + s_person + ")"
						}
					},
					emphasis : {
						label : {
							show : true,
							position : 'inner',
							formatter : "{b}\n {d}% ({c}" + s_person + ")"
						}
					}
				}
			}]
		},
		rawDataCallback : function(data) {
			if (data.dataList.length == 0) {
				$('.table').hide();
			} else {
				$('.table').show();
			}
			$.each(data.dataList, function(index, item) {
				item.index = index + 1;
				item.avgScoreView = function() {
					return this.avgScore != null ? this.avgScore + "" + s_formatter : "--";
				}
			});
			var output = Mustache.render($("#subAnalysisList_tpl").html(), data);
			$('#subAnalysisListBody').html(output);
		}
	});

	module.exports = SubAnalysisPie;

});
