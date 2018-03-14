var mySwipe = Swipe(document.querySelector('.m-swipe'), {
	startSlide: 0,
    continuous: false,
    disableScroll: true,
    stopPropagation: false,
    callback: function(index, element) {},
    transitionEnd: function(index, element) {}
    });

//基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('jChart'));

$("menu span").click(function(){
	$("menu span").removeClass("cur");
	$(this).addClass("cur");
	loadChart();
});

$("#jToDetail").click(function(){
	var gradeId = $("menu .cur").data("i");
	window.location.href = Leke.domain.homeworkServerName+"/auth/m/provost/homework/manager/detail.htm?gradeId="+gradeId;
});
loadChart();
function loadChart(){
	var submit = $("menu .cur").data("submit");
	var delay = $("menu .cur").data("delay");
	var undone = $("menu .cur").data("undone");
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
}