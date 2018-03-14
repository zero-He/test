var NumberUtils = require('../../../utils/number');

var toFixed = NumberUtils.toFixed;

function fillNull(data) {
	for (var i = 0; i < data.length; i++) {
		data[i] = data[i] != null ? data[i] : '-';
	}
	return data;
}

var CC = {}

// title: String
// datas: [{ name: String, value: Number }]
CC.buildGeneralPie = function(title, datas) {
	var labels = datas.map(function(v) {
		return v.name;
	});
	return {
		color : ['#619eed', '#ffd270', '#ff6e6e'],
		legend : {
			x : 'left',
			orient : 'vertical',
			selectedMode : false,
			data : labels
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		series : [{
			name : title,
			type : 'pie',
			center : ['60%', '50%'],
			radius : ['40%', '70%'],
			label : {
				normal : {
					show : false
				}
			},
			labelLine : {
				normal : {
					show : false
				}
			},
			data : datas
		}]
	};
}

// labels: [{ name: String, max: Number, min: Number }]
// datas: [{ name: String, value: Number[] }]
CC.buildGeneralRadar = function(indicator, datas) {
	var legendData = [];
	if (datas.length > 1) {
		legendData = datas.map(function(v) {
			return v.name;
		});
	}
	return {
		tooltip : {},
		legend : {
			top : 20,
			selectedMode : false,
			data : legendData
		},
		polar : [{
			indicator : indicator,
			center : ['50%', '65%'],
			radius : '75%',
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
			data : datas
		}]
	};
}

CC.renderLekeVal = function(lekeVal, typeId1, typeId2) {
	var val1 = lekeVal[typeId1] ? lekeVal[typeId1] : 0;
	var val2 = lekeVal[typeId2] ? lekeVal[typeId2] : 0;
	return val1 + val2;
}

module.exports = CC;
