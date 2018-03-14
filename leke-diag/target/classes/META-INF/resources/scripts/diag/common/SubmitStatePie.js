define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Chart = require('chart');
	var Mustache = require('mustache');
	var format = require('diag/common/DateFormat');
	
	var StuSubmitStatePie = $.extend({}, Chart, {
		chartId : "submitState",
		formId : 'searchForm',
		option : {
			title : {
				text : '',
				x : 'center'
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				y : 'top',
				data : ['正常提交', '延迟提交', '未提交']
			},
			color : Chart.Color.SUBMIT,
			series : [{
				type : 'pie',
				radius : '60%',
				center : ['40%', '55%'],
				itemStyle : {
					normal : {
						label : {
							position : 'outer',
							formatter : '{d}%'
						},
						labelLine : {
							show : true
						}
					},
					emphasis : {
						label : {
							show : true,
							position : 'inner',
							formatter : "{b}\n {d}% ({c}次)"
						}
					}
				},
				data:[]
			}]
		},
		rawDataCallback : function(data) {
			if (data.dataList.length == 0) {
				$('.table').hide();
			} else {
				$('.table').show();
			}
			var $target = $('#stuSubmitStateBody');
			var rTpl = $('#stuSubmitState_tpl').html();
			var contents = '';
			$.each(data.dataList, function(index, item) {
				var status = '';
				var submitTime = '--';
				if(item.submitStatus == '0'){
					status = '未提交';
				}else if(item.submitStatus == '1'){
					status = '正常提交';
				}else{
					status = '延迟提交';
				}
				
				if(item.submitTime!=null){
					submitTime = new Date(item.submitTime).format("yyyy-MM-dd hh:mm:ss");
				}
				var rowHtml = Mustache.render(rTpl, {
					homeworkName: item.homeworkName,
					submitStatus: status,
					submitTime: submitTime,
					closeTime: new Date(item.closeTime).format("yyyy-MM-dd hh:mm:ss")
				});
				contents += rowHtml;
			});
			$target.empty();
			$target.append(contents);
		}
	});
	
	module.exports = StuSubmitStatePie;
});
