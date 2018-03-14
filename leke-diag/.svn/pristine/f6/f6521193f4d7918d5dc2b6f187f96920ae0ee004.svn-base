define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
	var I18n = require('i18n');
	I18n.init('diag');
	
	var s_normalsubmit = '提交';
	var s_delaysubmit ='延迟'; 
	var s_notsubmit = '未提交';
	var AllAnalysisBar = $.extend({}, Chart, {
		chartId : "gradeStates",
		formId : 'searchForm',
		option : {
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				}
			},
			grid : {
				
			},
			legend : {
				data : [s_normalsubmit,s_delaysubmit,s_notsubmit],
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
						str += "<br/>" + param[i][0] + "：" + param[i][2] + "%";
					}
					return str;
				}
			},
			yAxis : [{
				type : 'value',
				axisLabel : {
					formatter : '{value}%'
				}
			}],
			xAxis : [{
				type : 'category',
				data : []
			}],
			series : [{
				name : s_normalsubmit,
				type : 'bar',
				data : []
			}, {
				name : s_delaysubmit,
				type : 'bar',
				data : []
			}, {
				name : s_notsubmit,
				type : 'bar',
				data : []
			}]
		}
	});

	module.exports = AllAnalysisBar;

});
