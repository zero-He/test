define(function(require, exports, module) {
	var $ = require('jquery');
	var echart = require('echart');
	var I18n = require('diag/common/i18n');
	var theme = require('common/ui/ui-echarts/chart-theme');

	var s_mygoalaverage = $.i18n.prop('diag.common.echarts.option.legend.mygoalaverage');
	var s_goalaverage = $.i18n.prop('diag.common.echarts.option.legend.goalaverage');
	//预定义
	var legendData = [s_goalaverage];
	var obj_serise = [{
		name : s_goalaverage,
		type : 'bar',
		itemStyle : {
			normal : {
				label : {
					show : true,
					position : 'inside',
					formatter : '{c0}%'
				}
			}
		},
		data : []
	}];
	var formatterStr = "{b0}<br>{a0}：{c0}%";
	//如果是学生
	if (ReportCst.roleId == 100) {
		formatterStr = "{b0}<br>{a0}：{c0}%<br>{a1}：{c1}%";
		legendData = [s_goalaverage, s_mygoalaverage];
		obj_serise = [{
			name : s_goalaverage,
			type : 'bar',
			itemStyle : {
				normal : {
					label : {
						show : true,
						position : 'inside',
						formatter : '{c0}%'
					}
				}
			},
			data : []
		}, {
			name : s_mygoalaverage,
			type : 'bar',
			itemStyle : {
				normal : {
					label : {
						show : true,
						position : 'inside',
						formatter : '{c0}%'
					}
				}
			},
			data : []
		}]
	}
	var AnalysisPaper = {
		option : {
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				},
				formatter : formatterStr
			},
			legend : {
				data : legendData
			},
			dataZoom : {
				show : true
			},
			xAxis : [{
				type : 'category',
				data : []
			}],
			yAxis : [{
				type : 'value',
				axisLabel : {
					formatter : '{value}%'
				}
			}],
			series : obj_serise
		},
		render : function(domNode, data) {
			var xAxisData = [];
			var scoreData = [];
			var stuData = [];
			var questionIds = [];
			$.each(data, function(i, el) {
				xAxisData.push(el.label);
				questionIds.push(el.questionId);
				scoreData.push(el.scoreRate)
				stuData.push(el.stuRate);
			});
			var seriesDatas = [{
				data : scoreData
			}];
			if (ReportCst.roleId == 100) {
				seriesDatas = [{
					data : scoreData
				}, {
					data : stuData
				}];
			}
			var opts = $.extend(true, {}, this.option, {
				xAxis : [{
					data : xAxisData,
					triggerEvent : true
				}],
				series : seriesDatas
			});
			var chart = echart.init(domNode, theme);
			chart.setOption(opts, true);
			chart.on('click', function (params) {
			    var linkId = 'index_'+ questionIds[params.dataIndex];
			    if($('#'+linkId).length > 0){
			    	$('#'+linkId)[0].click();
			    }
			});
		}
	}

	module.exports = AnalysisPaper;
});
