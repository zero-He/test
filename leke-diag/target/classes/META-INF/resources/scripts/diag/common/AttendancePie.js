define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
	var echart = require('echart');

	var idx = 1;
	var AttendancePie = $.extend({}, Chart, {
		chartId : "attendance",
		formId : 'searchForm',
		option : {
			title : {},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : ['全勤', '非全勤', '未上课']
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
			calculable : true,
			series : [{
				name : '访问来源',
				type : 'pie',
				radius : '55%',
				center : ['50%', '60%'],
				itemStyle : {
					normal : {
						label : {
							position : 'outer',
							formatter : '{b}({d}%)'
						},
						labelLine : {
							show : true
						}
					}
				},
				data : []
			}]
		},
		bindEvent : function() {
			this.chartNode.on(echart.config.EVENT.CLICK, function(param) {
				var sDomailStr = Leke.domain.lessonServerName;
				var nUserId = $('#jStudentId').val();
				var dStartTimeStr = $('#startTime').val();
				var dEndTimeStr = $('#endTime').val();
				var courseId = $('#jClazzId').val();
				var courseName =$('#jClazzSelect').val();
				var url = sDomailStr + '/auth/student/data/stuPie/enterMyAttendance.htm?startTimeStr=' + dStartTimeStr
						+ '&endTimeStr=' + dEndTimeStr
						+ '&courseId=' + courseId 
						+ '&courseName=' + courseName
						+ '&userId=' + nUserId;
				if (Leke.user.currentRoleId == 102) {
					url = sDomailStr + '/auth/parent/data/stuPie/enterMyAttendance.htm?userId=' + nUserId
							+ '&startTimeStr=' + dStartTimeStr + '&endTimeStr=' + dEndTimeStr
							+ '&courseId=' + courseId + '&courseName=' + courseName;;
				}
				if (param.dataIndex == 0) {
					url = url + "&status=1";
				} else if (param.dataIndex == 1) {
					url = url + "&status=0";
				} else {
					url = url + "&status=2";
				}
				window.open(url);
			});
		}
	});

	module.exports = AttendancePie;

});
