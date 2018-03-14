// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('jChart'));

var submit = $("#jChart").data("submit");
var delay = $("#jChart").data("delay");
var undone = $("#jChart").data("undone");
// 指定图表的配置项和数据
option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{b}: {d}%"
	    },
	    
	    color : ['#0ba299','#fcb322',"#f50"],
		legend: {
		    orient: 'horizontal',
			bottom: 'bottom',
		    data: ['正常提交','延迟提交','未提交']
		},
		textStyle : {
				fontSize : 24
			},
	    series: [
	        {
	            name:'访问来源',
	            type:'pie',
	            radius: ['50%', '70%'],
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                },
	                emphasis: {
	                    show: true,
	                    textStyle: {
	                        fontSize: '30',
	                        fontWeight: 'bold'
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:[
	                {value:submit-delay, name:'正常提交'},
	                {value:delay, name:'延迟提交'},
	                {value:undone, name:'未提交'}
	            ]
	        }
	    ]
	};
// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
