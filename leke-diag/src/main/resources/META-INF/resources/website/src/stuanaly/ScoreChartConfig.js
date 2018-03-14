var {toFixed, toRate} = require('../utils/number');

function fillNull(data) {
	for (var i = 0; i < data.length; i++) {
		data[i] = data[i] != null ? data[i] : '-';
	}
	return data;
}

var Colors = {
	submit : ['#619eed', '#ffd270', '#ff6e6e'],
	score : ['#2fbd68', '#74d64b', '#5cc0ff', '#ffcd61', '#ff6661']
}

var Labels = {
	submit : ["正常提交", "延迟提交", "未提交"],
	score : ["优秀", "良好", "及格", "较差", "危险"],
	cycles : {
		0 : ['周', '月', '学期', '学年'],
		1 : ["上周", "本周"],
		2 : ["上月", "本月"],
		3 : ["上学期", "本学期"],
		4 : ["上学年", "本学年"]
	}
}

var CC = {
	Colors : Colors,
	Labels : Labels
}

CC.buildSubmitPie = function(normal, delay, undone, units) {
	units = units || '人';
	var args = arguments;
	var data = Labels.submit.map(function(label, i) {
		return {
			name : label,
			value : args[i]
		}
	});
	var total = normal + delay + undone;
	return {
		color : Colors.submit,
		legend : {
			bottom : 20,
			data : Labels.submit
		},
		tooltip : {
			formatter : '应交' + units + '数：' + total + '<br />{b}：{c}（{d}%）'
		},
		series : [{
			type : 'pie',
			center : ['50%', '43%'],
			radius : ['40%', '70%'],
			avoidLabelOverlap : false,
			label : {
				normal : {
					show : false,
					position : 'center'
				},
				emphasis : {
					show : true,
					textStyle : {
						fontSize : 16,
						fontWeight : 'bold'
					}
				}
			},
			labelLine : {
				normal : {
					show : false
				}
			},
			data : data
		}]
	};
}

CC.buildScorePie = function(a, b, c, d, e, units) {
	units = units || '人';
	var args = arguments;
	var data = Labels.score.map(function(label, i) {
		return {
			name : label,
			value : args[i]
		}
	});
	var total = a + b + c + d + e;
	return {
		color : Colors.score,
		legend : {
			bottom : 20,
			data : Labels.score
		},
		tooltip : {
			formatter : '有成绩总' + units + '数：' + total + '<br />{b}' + units + '数：{c}（{d}%）'
		},
		series : [{
			type : 'pie',
			center : ['50%', '43%'],
			radius : ['40%', '70%'],
			avoidLabelOverlap : false,
			label : {
				normal : {
					show : false,
					position : 'center'
				},
				emphasis : {
					show : true,
					formatter : '{b}:{c}' + units + ',{d}%',
					textStyle : {
						fontSize : 16,
						fontWeight : 'bold'
					}
				}
			},
			labelLine : {
				normal : {
					show : false
				}
			},
			data : data
		}]
	};
}

CC.buildClassScoreBar = function(avg, max, min) {
	return {
		color : ['#5b9bd5', '#8bc036', '#ffbe54'],
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			}
		},
		legend : {
			data : ['最高分', '最低分']
		},
		grid : {
			top : 60,
			left : 120,
			right : 120,
			bottom : 0,
			containLabel : true
		},
		xAxis : [{
			type : 'category',
			data : ['']
		}],
		yAxis : [{
			type : 'value',
			max : 100,
			splitNumber : 10,
			axisLabel : {
				formatter : '{value} 分'
			}
		}],
		series : [{
			name : '平均分',
			type : 'bar',
			barWidth : '40%',
			data : [toFixed(avg, 1)]
		}, {
			name : '最高分',
			type : 'line',
			data : [toFixed(max, 1)],
			symbolSize : 10,
			markPoint : {
				silent : true,
				data : [{
					type : "max",
					name : "最高分"
				}]
			}
		}, {
			name : '最低分',
			type : 'line',
			data : [toFixed(min, 1)],
			symbolSize : 10,
			markPoint : {
				silent : true,
				data : [{
					type : "min",
					name : "最低分"
				}]
			}
		}]
	};
}

CC.buildScoreCompBar = function(type, names, currs, prevs) {
	var clbs = Labels.cycles[type];
	var series = [{
		name : clbs[0],
		type : 'bar',
		barMaxWidth : 60,
		data : prevs,
		markLine : {
			data : [{
				type : 'average',
				name : '平均分'
			}]
		}
	}, {
		name : clbs[1],
		type : 'bar',
		barMaxWidth : 60,
		data : currs,
		markLine : {
			data : [{
				type : 'average',
				name : '平均分'
			}]
		}
	}];
	return {
		color : ['#5b9bd5', '#ed7d31'],
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : clbs
		},
		xAxis : [{
			type : 'category',
			data : names
		}],
		yAxis : [{
			type : 'value',
			min : 0,
			max : 100,
			interval : 10,
			axisLabel : {
				formatter : '{value} 分'
			}
		}],
		series : series
	};
}

function labelTop(color) {
	return {
		normal : {
			color : color,
			label : {
				show : true,
				position : 'center',
				formatter : '{b}',
				textStyle : {
					color : '#333',
					fontSize : 18,
					baseline : 'bottom'
				}
			},
			labelLine : {
				show : false
			}
		}
	};
}
function labelBottom(color) {
	return {
		normal : {
			color : '#d7d7d7',
			label : {
				show : true,
				position : 'center',
				textStyle : {
					color : color,
					fontSize : 20
				}
			},
			labelLine : {
				show : false
			}
		}
	};
}

CC.buildOverallScore = function(score, rate) {
	var series = [{
		type : 'pie',
		center : ['25%', '50%'],
		radius : [47, 52],
		x : '0%',
		itemStyle : {
			normal : {
				label : {
					formatter : function(params) {
						return score;
					},
					textStyle : {
						baseline : 'top'
					}
				}
			}
		},
		data : [{
			name : '综合得分',
			value : score,
			itemStyle : labelTop('#00bb9c')
		}, {
			name : 'other',
			value : toFixed(100 - score, 1),
			itemStyle : labelBottom('#f86b4f')
		}]
	}];
	if (rate) {
		series.push({
			type : 'pie',
			center : ['75%', '50%'],
			radius : [47, 52],
			x : '10%',
			itemStyle : {
				normal : {
					label : {
						formatter : function(params) {
							return rate + '%';
						},
						textStyle : {
							baseline : 'top'
						}
					}
				}
			},
			data : [{
				name : '领先',
				value : rate,
				itemStyle : labelTop('#66ccff')
			}, {
				name : 'other',
				value : toFixed(100 - rate, 1),
				itemStyle : labelBottom('#66ccff')
			}]
		});
	}
	return {
		series : series
	};
}

CC.buildScoreRadar = function(names, selfs, klass) {
	var subjNames = names.slice(0);
	if (names.length < 3) {
		for (var i = names.length; i < 3; i++) {
			names.push('');
			selfs.push(0);
			if (klass) {
				klass.push(0);
			}
		}
	}
	var indicator = names.map(function(name) {
		return {
			text : name,
			max : 100
		};
	});
	var seriesData = [];
	if (klass) {
		seriesData.push({
			value : fillNull(klass),
			name : '班级平均分',
			itemStyle : {
				normal : {
					areaStyle : {
						type : 'default'
					}
				}
			}
		});
	}
	seriesData.push({
		value : fillNull(selfs),
		name : '个人得分'
	});
	return {
		legend : {
			x : 'left',
			orient : 'vertical',
			data : ['班级平均分', '个人得分']
		},
		tooltip : {
			formatter : function(radar) {
				return radar.name + subjNames.map(function(name, i) {
					return '<br />' + name + '：' + radar.value[i];
				}).join('');
			}
		},
		polar : [{
			indicator : indicator,
			splitNumber : 5,
			center : ['50%', '55%'],
			radius : 115,
			name : {
				textStyle : {
					color: '#333'
				}
			},
			axisLabel : {
				show : true,
				textStyle : {
					color : '#ccc'
				}
			},
			splitArea : {
				areaStyle : {
					color : ['#ffffff', '#ffffff']
				}
			},
			splitLine : {
				lineStyle : {
					width : 2,
					color : '#e9eaea'
				}
			}
		}],
		color : ['#a9cfef', '#fe6910'],
		series : [{
			type : 'radar',
			data : seriesData
		}]
	};
}

CC.buildScoreTrend = function(names, selfs, klass) {
	var series = [], color = [], legendData = [];
	if (selfs) {
		selfs = selfs.map(function(v) {
			return v != null ? v : '-';
		});
		color.push('#ed7d31');
		legendData.push('我的得分');
		series.push({
			name : '我的得分',
			type : 'line',
			data : selfs,
			showAllSymbol : true
		});
	}
	if (klass) {
		klass = klass.map(function(v) {
			return v != null ? v : '-';
		});
		color.push('#5b9bd5');
		legendData.push('班级得分');
		series.push({
			name : '班级得分',
			type : 'line',
			data : klass,
			showAllSymbol : true
		});
	}
	return {
		color : color,
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : legendData
		},
		dataZoom : [{
			type : 'inside'
		}, {}],
		xAxis : {
			type : 'category',
			data : names
		},
		yAxis : {
			type : 'value',
			max : 100,
			interval : 10,
			axisLabel : {
				formatter : '{value} 分'
			}
		},
		series : series
	};
}

CC.buildScoreSectionBar = function(labels, values) {
	return {
		color : ['#3398DB'],
		tooltip : {
			trigger : 'axis',
			formatter : '{b}分{c}人',
			axisPointer : {
				type : 'shadow'
			}
		},
		grid : {
			top : 10,
			left : 10,
			right : 10,
			bottom : 10,
			containLabel : true
		},
		xAxis : [{
			type : 'category',
			data : labels,
			axisTick : {
				alignWithLabel : true
			},
			axisLabel : {
				interval : 0
			},
			splitLine : {
				show : true,
				lineStyle : {
					color : ['#ddd'],
					type : 'dashed'
				}
			}
		}],
		yAxis : [{
			type : 'value',
			axisLabel : {
				formatter : '{value}人'
			},
			splitLine : {
				show : true,
				lineStyle : {
					type : 'dashed'
				}
			}
		}],
		series : [{
			name : '',
			type : 'bar',
			barWidth : '90%',
			data : values
		}]
	};
}

module.exports = CC;
