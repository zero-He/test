define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
	var Mustache = require('mustache');
	
	var StatisticalLine = $.extend({}, Chart, {
		chartId : "selfStatistical",
		formId : 'searchForm',
		option : {
			title : {
				text : '个人成长曲线',
				x : 'center'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				x : 'left',
				data : ['班级最高分', '班级平均分', '个人得分']
			},
			dataZoom : {
				show : true,
				realtime : true
			},
			toolbox : {
				show : true,
				feature : {
					magicType : {
						show : true,
						type : ['line', 'bar']
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			xAxis : [{
				type : 'category',
				boundaryGap : false,
				data : []
			}],
			yAxis : [{
				type : 'value',
				axisLabel : {
					formatter : '{value} 分'
				}
			}],
			series : [{
				name : '班级最高分',
				type : 'line',
				data : []
			}, {
				name : '班级平均分',
				type : 'line',
				data : []
			}, {
				name : '个人得分',
				type : 'line',
				data : [],
				markLine : {
	                data : [
	                    // 纵轴，默认
	                    {type : 'average', name : '我的平均分', itemStyle:{normal:{color:'#dc143c'}}},
	                    
	                ]
	            }
			}]
		}
	});

	module.exports = StatisticalLine;
});
