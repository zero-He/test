define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
    var stuAvg = '学生平均分';
    if(Leke.user.currentRoleId == 100){
    	stuAvg = '我的平均分'
    }
	var SubjectScoreBar = $.extend({}, Chart, {
		chartId : "subjectScore",
		formId : 'searchForm',
		option : {
			title : {
				text : '学科优劣分析',
				x : 'center'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				x : 'left',
				data : [ '班级最高平均分', '班级平均分', stuAvg]
			},
			toolbox : {
				show : true,
				feature : {
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
		    dataZoom : {
		        show : true,
		        realtime : true,
		        start : 0,
		        end : 100
		    },
			xAxis : [ {
				type : 'category',
				data : []
			} ],
			yAxis : [ {
				type : 'value',
				axisLabel : {
					formatter : '{value} 分'
				}
			} ],
			series : [ {
				name : '班级最高平均分',
				type : 'bar',
				barGap : '10%'
			}, {
				name : '班级平均分',
				type : 'bar',
				barGap : '10%'
			}, {
				name : stuAvg,
				type : 'bar',
				barGap : '10%',
				itemStyle : {
					normal : {
						label : {
							show : true,
							position : 'inside',
							formatter : function(a, b, c) {
								if (c >= 85) {
									return 'A';
								} else if (c >= 70) {
									return 'B';
								} else if (c >= 60) {
									return 'C';
								} else {
									return 'D';
								}
							}
						}
					}
				}
			} ]
		}
	});

	module.exports = SubjectScoreBar;
});
