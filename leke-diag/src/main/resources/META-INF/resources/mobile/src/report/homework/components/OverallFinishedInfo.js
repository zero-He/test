var {toFixed, toRate} = require('../../../utils/number');
var {toSecond, toLevel, toLevelName} = require('./homework-utils');
var ReactChart = require('../../../react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');

function buildScoreBar(summary, totalScore) {
	return {
		color : ['#5b9bd5', '#8bc036', '#ffbe54'],
		legend : {
			selectedMode: false,
			data : ['最高分', '最低分']
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			}
		},
		grid : {
			top : 70,
			bottom : 0,
			containLabel : true
		},
		xAxis : [{
			type : 'category',
			data : ['']
		}],
		yAxis : [{
			type : 'value',
			max: totalScore,
			splitNumber : 10,
			axisLabel : {
				formatter : '{value} 分'
			}
		}],
		series : [{
			name : '平均分',
			type : 'bar',
			barWidth: 60,
			data : [toFixed(summary.avgScore, 1)]
		}, {
			name : '最高分',
			type : 'line',
			data : [toFixed(summary.maxScore, 1)],
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
			data : [toFixed(summary.minScore, 1)],
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

function buildUsedTimeBar(summary) {
	let usedTime = toSecond(summary.avgUsedTime);
	let maxValue = toFixed(usedTime * 1.2, 1);
	return {
		color : ['#33c8af', '#ffbe54'],
		tooltip : {
			trigger : 'axis',
			formatter : '{a}{c}分钟',
			axisPointer : {
				type : 'shadow'
			}
		},
		grid : {
			top : 50,
			bottom : 0,
			containLabel : true
		},
		xAxis : [{
			type : 'category',
			data : ['']
		}],
		yAxis : [{
			type : 'value',
			max: maxValue,
			splitNumber : 10,
			axisLabel : {
				formatter : '{value} 分钟'
			}
		}],
		series : [{
			name : '平均用时',
			type : 'bar',
			barWidth: 60,
			data : [usedTime]
		}]
	};
}

class FinishedInfo extends React.Component {
	render() {
		let {summary, totalScore} = this.props;
		var {submitNum, delayNum, totalNum} = this.props.summary;
		var normalNum = submitNum - delayNum;
		var undoneNum = totalNum - submitNum;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">作业完成概况</div>
				<ReactChart className="maps" option={buildScoreBar(summary, totalScore)} />
				<ReactChart className="maps" option={buildUsedTimeBar(summary)} />
				<ReactChart className="maps" option={ScoreChartConfig.buildSubmitPie(normalNum, delayNum, undoneNum)} />
			</section>
		);
	}
}

module.exports = FinishedInfo;
