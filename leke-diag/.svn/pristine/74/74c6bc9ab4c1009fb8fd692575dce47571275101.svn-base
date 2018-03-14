define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');

	var ContributeBar = $.extend({}, Chart, {
		chartId : "contribute",
		formId : 'searchForm',
		option : {
			title : {
				text : '教师题库贡献统计'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : ['贡献题目数', '被引用次数']
			},
			dataZoom : {
				show : true,
				realtime : true
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
			xAxis : [{
				type : 'category',
				data : []
			}],
			yAxis : [{
				type : 'value',
				name : '贡献题目数'
			}, {
				type : 'value',
				name : '被引用次数'
			}],
			series : [{
				name : '贡献题目数',
				type : 'bar',
				data : [],
				markLine : {
					symbol : ['none', 'none'],
					itemStyle : {
						normal : {
							label : {
								position : 'left'
							}
						}
					},
					data : [{
						type : 'average',
						name : '平均值'
					}]
				}
			}, {
				name : '被引用次数',
				type : 'bar',
				yAxisIndex : 1,
				data : [],
				markLine : {
					symbol : ['none', 'none'],
					data : [{
						type : 'average',
						name : '平均值'
					}]
				}
			}]
		}
	});

	module.exports = ContributeBar;

});
