var NumberUtils = require('../../../utils/number');

var toFixed = NumberUtils.toFixed;

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
			x: 'left',
			orient: 'vertical',
			selectedMode : false,
			data : Labels.submit
		},
		tooltip : {
			formatter : '应交' + units + '数：' + total + '<br />{b}：{c}（{d}%）'
		},
		series : [{
			type : 'pie',
			center : ['60%', '50%'],
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
						fontSize : 12,
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
			x: 'left',
			orient: 'vertical',
			selectedMode : false,
			data : Labels.score
		},
		tooltip : {
			formatter : '有成绩总' + units + '数：' + total + '<br />{b}' + units + '数：{c}（{d}%）'
		},
		series : [{
			type : 'pie',
			center : ['60%', '50%'],
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
						fontSize : 12,
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
			bottom : 20,
			containLabel : true
		},
		xAxis : [{
			type : 'category',
			data : labels,
			axisTick : {
				alignWithLabel : true
			},
			axisLabel : {
				interval : 0,
				rotate : 30
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
