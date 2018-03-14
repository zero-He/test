define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');

	var idx = 1;
	var StuAnalysisBar = $.extend({}, Chart, {
		chartId : "stuAnalysis",
		formId : 'searchForm',
		initLoad : false,
		option : {
			title : {
				text : '学科优劣分析'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				y : 'bottom',
				data : ['学生平均成绩', '班级最高分', '班级平均分', '班级最低分']
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
				data : []
			}],
			yAxis : [{
				type : 'value'
			}],
			series : [{
				name : '张三平均成绩',
				type : 'line',
				data : []
			}, {
				name : '班级最高分',
				type : 'bar',
				data : []
			}, {
				name : '班级平均分',
				type : 'bar',
				data : []
			}, {
				name : '班级最低分',
				type : 'bar',
				data : []
			}]
		}
	});

	module.exports = StuAnalysisBar;

});
