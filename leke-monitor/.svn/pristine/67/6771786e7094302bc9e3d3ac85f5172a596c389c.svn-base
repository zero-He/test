define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var My97 = require('date');
	var Echart = require('echart');
	var Chart = require('chart');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	function FormVM(params) {
		var self = this;
		self.fLoadSsForTimeChart();
	}
	FormVM.prototype.fLoadSsForTimeChart = function() {
		var _this = this;
		$.ajax({
			type : 'get',
			url : ctx + '/auth/common/resource/getDataForChart.htm',
			data : {
				startDate : $('#startDate').text(),
				endDate : $('#endDate').text(),
				resResource: $('#resResource').text(),
				resType :$('#resType').text(),
				schoolId :$('#schoolId').text()
			},
			dataType : 'json',
			async : true,
			success : function(datas) {
				var dateArr = datas.datas.dateArr;
				var usedNumArr = datas.datas.usedNumArr;
				if (dateArr != null && dateArr != '') {
					Echart.init($('#barSta')[0]).setOption({

					    tooltip : {
							trigger : 'axis',
							axisPointer : {
								type : 'shadow'
							}	
						},
						color : Chart.Color.SCORE,
						toolbox : {
							show : true,
							orient : 'vertical',
							y : 'center',
							feature : {
//								magicType : {
//									show : true,
//									type : ['line', 'stack']
//								},
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
					        realtime: true,

						},
					    xAxis : [{
					    	type : 'category',
					    	data : dateArr
					    }],
					    yAxis : [{
							type : 'value'
						}],
						 series : [{name:"使用量", type:'line', data:usedNumArr}
					    ]
					});
			
			}else {
				Echart.init($('#barSta')[0]).setOption({});
				$('#barSta').html("<div class='eval-rate-pictab m-tips f-block' style='padding-top:107px;'><i></i> <span>对不起，没有您要查询的数据</span></div>")
				}
			}
		});
	}, 

	ko.applyBindings(new FormVM());
});