define(function(require, exports, module) {
	var $ = require('jquery');
	var Chart = require('chart');
	var Mustache = require('mustache');
	var TeacherList = require('diag/common/TeacherList');
	var PeriodDate = require('diag/common/PeriodDate');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var CorrectStatPie = $.extend({}, Chart, {
		chartId : "correctStat",
		formId : 'searchForm',
		option : {
			title : {
				text : '作业批改统计'
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				y : 'center',
				data : ['全部批改', '部分批改', '未批改']
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
			color : ['#4E7FBB', '#7E63A0', '#BE4F4C'],
			series : [{
				type : 'pie',
				radius : '65%',
				center : ['50%', '55%'],
				selectedMode : 'single',
				itemStyle : {
					normal : {
						label : {
							formatter : "{b}({c}次)"
						}
					},
					emphasis : {
						label : {
							show : true,
							position : 'inner',
							formatter : "{b}\n {d}% ({c}次)"
						}
					}
				}
			}]
		},
		rawDataCallback : function(data) {
			CorrectStat.listShow(data.dataList);
		}
	});

	var CorrectStat = {
		init : function() {
			TeacherList.init('jTeacherId', $('#jSubjectId').val());
			PeriodDate.init('jStartTime', 'jEndTime');
			CorrectStatPie.init();
			this.bindEvent();
		},
		bindEvent : function() {
			$('#jSearch').click(function() {
				CorrectStatPie.load();
			});
			$('#jStageSubject').stgGrdSbjSelect({
				type : 'sbj',
				caption : false,
				onChange : function(selection) {
					$('#jSubjectId').val(selection.subjectId);
					TeacherList.change(selection.subjectId);
				}
			});
		},
		listShow : function(dataList) {
			if (dataList == null || dataList.length == 0) {
				$('#correctStatList').hide();
			} else {
				$('#correctStatList').show();
			}
			$.each(dataList, function(index, item) {
				item.index = index + 1;
				if (item.correctNum >= item.finishNum) {
					item.all = '√';
				} else if (item.correctNum > 0) {
					item.part = '√';
				} else {
					item.not = '√';
				}
			});
			var output = Mustache.render($("#correctStatList_tpl").html(), {
				dataList : dataList
			});
			$('#correctStatListBody').html(output);
		}
	};

	CorrectStat.init();

});
