define(function(require, exports, module) {
	var $ = require('jquery');
	var json = require('json');

	var RenderEchart = {
			buildStatPieData : function(num, nameArr, valueArr){
				var arr = [];
				for(var i = 0; i < num; i++){
					arr.push({name : nameArr[i], value : valueArr[i]});
				}
				return arr;
			},
				
			renderStatInfoPie : function(chartDivId, seriesData, colorData){
				var color = ['#ff6666', '#619eed', '#ffcb6b', '#ff9900', '#1fb5ab', '#24bdee'];
				if(colorData){
					color = colorData;
				}
				option = {
					color: color,
					tooltip: {
					    trigger: 'item',
					    formatter: '{b} : {c}({d}%)'
				    },
				    series : [
				              {
				                  name : '',
				                  type : 'pie',
				                  radius : ['40%', '70%'],
				                  center: ['45%', '50%'],
				                  itemStyle : {
				                      normal : {
				                          label : {
				                              show : true,
				                              position : 'outer'
				                          },
				                          labelLine : {
				                              show : true,
				                              length : 15
				                          }
				                      },
				                      emphasis : {
				                          label : {
				                              show : true,
				                              position : 'center',
				                              textStyle : {
				                                  fontSize : '20',
				                                  fontWeight : 'bold'
				                              }
				                          }
				                      }
				                  },
				                  data : seriesData
				              }
				      ]
		        };
				var chartDiv = echarts.init(document.getElementById(chartDivId));
		    	chartDiv.clear();
		    	chartDiv.setOption(option);
		    },
		    
		    renderStatInfoPieInnerPie : function(chartDivId, innerSeriesData, outerSeriesData){
				option = {
					color: ['#ff6666', '#619eed', '#ffcb6b', '#ff9900', '#1fb5ab', '#24bdee'],
					tooltip: {
					    trigger: 'item',
					    formatter: '{b} : {c}'
				    },
				    series : [
								{
								    name : '',
								    type : 'pie',
								    center : ['50%', '50%'],
								    radius : 70,
								    selectedMode: 'single',
								    itemStyle : {
					                      normal : {
					                          label : {
					                              show : true,
					                              position : 'inner'
					                          },
					                          labelLine : {
					                              show : false
					                          }
					                      },
					                      emphasis : {
					                          label : {
					                              show : true,
					                              position : 'center',
					                              textStyle : {
					                                  fontSize : '10',
					                                  fontWeight : 'normal'
					                              }
					                          }
					                      }
					                  },
								    data : innerSeriesData
								},
				                {
				                   name : '',
				                   type : 'pie',
				                   radius : ['60%', '80%'],
				                   itemStyle : {
					                      normal : {
					                          label : {
					                              show : true,
					                              position : 'outer'
					                          },
					                          labelLine : {
					                              show : true
					                          }
					                      },
					                      emphasis : {
					                          label : {
					                              show : true,
					                              position : 'center',
					                              textStyle : {
					                                  fontSize : '20',
					                                  fontWeight : 'bold'
					                              }
					                          }
					                      }
					                  },
				                   data : outerSeriesData
				                }
				      ]
		        };
				var chartDiv = echarts.init(document.getElementById(chartDivId));
		    	chartDiv.clear();
		    	chartDiv.setOption(option);
		    },
			
		    renderBarLine : function(chartDivId, seriesData, legendData, xData, chartType, tipFmt, yFmt, colorData){
		    	var color = ['#ff6666', '#619eed', '#ffcb6b', '#ff9900', '#1fb5ab', '#24bdee'];
				if(colorData){
					color = colorData;
				}
		    	if (chartType === "line") {
				    option = {
					    color: color,
					    tooltip: {
						    trigger: 'axis'
					    },
					    legend: {
						    data: legendData,
						    y: "250"
					    },
					    grid: {
					    	x: 40,
						    x2: 10,
						    y: 10,
						    y2: 100
					    },
					    dataZoom: [
						    {
							    show: true,
							    start: 0,
							    end: 100
						    },
						    {
							    type: 'inside',
							    start: 94,
							    end: 100
						    },
						    {
							    show: false,
							    yAxisIndex: 0,
							    filterMode: 'empty',
							    width: 30,
							    height: '80%',
							    showDataShadow: true,
							    left: '93%'
						    }
						],
					    xAxis: [{
						    type: 'category',
						    data: xData
					    }],
					    yAxis: [{
						    type: 'value',
						    axisLabel: {
							    formatter: '{value}' + yFmt
						    }
					    }],
					    series: seriesData
				    };
			    } else {
				    option = {
					    color: color,
					    tooltip: {
						    trigger: 'axis'
					    },
					    legend: {
						    data: legendData,
						    y: "250"
					    },
					    grid: {
						    x: 40,
						    x2: 10,
						    y: 10,
						    y2: 100
					    },
					    dataZoom: [
						    {
							    show: true,
							    start: 0,
							    end: 100
						    },
						    {
							    type: 'inside',
							    start: 94,
							    end: 100
						    },
						    {
							    show: false,
							    yAxisIndex: 0,
							    filterMode: 'empty',
							    width: 30,
							    height: '80%',
							    showDataShadow: true,
							    left: '93%'
						    }
					    ],
					    xAxis: [{
						    type: 'category',
						    data: xData
					    }],
					    yAxis: [{
						    type: 'value',
						    axisLabel: {
							    formatter: '{value}' + yFmt
						    }
					    }],
					    series: seriesData
				    };
			    }
		    	var chartDiv = echarts.init(document.getElementById(chartDivId));
		    	chartDiv.clear();
		    	chartDiv.setOption(option);
		    },
		    
		    renderMixBarLine : function(chartDivId, seriesData, legendData, xData, tipFmt, yFmt1, yFmt2, yName1, yName2, colorData){
		    	var color = ['#ff6666', '#619eed', '#ffcb6b', '#ff9900', '#1fb5ab', '#24bdee'];
				if(colorData){
					color = colorData;
				}
		    	option = {
		    			color: color,
		    		    tooltip : {
		    		        trigger: 'axis'
		    		    },
		    		    legend: {
		    		        data : legendData,
		    		        y: "330"
		    		    },
		    		    grid: {
		    		    	x: 60,
						    x2: 50,
						    y: 35,
						    y2: 100
					    },
					    dataZoom: [
						    {
							    show: true,
							    start: 0,
							    end: 100
						    },
						    {
							    type: 'inside',
							    start: 94,
							    end: 100
						    },
						    {
							    show: false,
							    yAxisIndex: 0,
							    filterMode: 'empty',
							    width: 30,
							    height: '80%',
							    showDataShadow: true,
							    left: '93%'
						    }
						],
		    		    xAxis : [
		    		        {
		    		            type : 'category',
		    		            data: xData
		    		        }
		    		    ],
		    		    yAxis : [
		    		        {
		    		            type : 'value',
		    		            name : yName1,
		    		            splitLine : {
		    		            	show : true,
		    		            	lineStyle:{
		    		            		 type:'solid',
		    		            	     width: 1
		    		            	}
		    		            },
		    		            axisLabel : {
		    		                formatter: '{value} '
		    		            }
		    		        },
		    		        {
		    		            type : 'value',
		    		            name : yName2,
		    		            splitLine : {
		    		            	show : true,
		    		            	lineStyle:{
		    		            	     type:'dotted',
		    		            	     width: 1
		    		            	}
		    		            },
		    		            axisLabel : {
		    		                formatter: '{value} ' + yFmt2
		    		            }
		    		        }
		    		    ],
		    		    series : seriesData
		    		};
		    		                    
		    	var chartDiv = echarts.init(document.getElementById(chartDivId));
		    	chartDiv.clear();
		    	chartDiv.setOption(option);
		    }
		};

	module.exports = RenderEchart;
});
