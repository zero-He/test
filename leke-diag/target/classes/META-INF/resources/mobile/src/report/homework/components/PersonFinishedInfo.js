var {toFixed, toRate} = require('../../../utils/number');
var {toSecond, toLevel, toLevelName} = require('./homework-utils');
var ReactChart = require('../../../react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');

function buildScoreBar(summary) {
	let {selfScore, maxScore, avgScore, scoreRank} = summary;
	return {
		color : ['#5b9bd5', '#8bc036', '#ffbe54'],
		legend : {
			selectedMode: false,
			data : ['班级最高分', '班级平均分']
		},
		tooltip : {
			trigger : 'axis',
			formatter : `{a0}：{c0}<br />我的排名：${scoreRank}<br />{a1}：{c1}<br />{a2}：{c2}`,
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
			splitNumber : 10,
			axisLabel : {
				formatter : '{value} 分'
			}
		}],
		series : [{
			name : '我的得分',
			type : 'bar',
			barWidth: 60,
			data : [toFixed(selfScore, 1)]
		}, {
			name : '班级最高分',
			type : 'line',
			data : [toFixed(maxScore, 1)],
			symbolSize : 10,
			markPoint : {
				silent : true,
				data : [{
					type : "max",
					name : "班级最高分"
				}]
			}
		}, {
			name : '班级平均分',
			type : 'line',
			data : [toFixed(avgScore, 1)],
			symbolSize : 10,
			markPoint : {
				silent : true,
				data : [{
					type : "min",
					name : "班级平均分"
				}]
			}
		}]
	};
}

function buildUsedTimeBar(summary) {
	let {avgUsedTime, selfUsedTime} = summary;
	avgUsedTime = toSecond(avgUsedTime);
	selfUsedTime = toSecond(selfUsedTime);
	let maxValue = Math.max(avgUsedTime, selfUsedTime);
	maxValue = toFixed(maxValue * 1.2, 1);
	return {
		color : [avgUsedTime < selfUsedTime ? '#d0d0d0' : '#33c8af', '#ffbe54'],
		tooltip : {
			trigger : 'axis',
			formatter : '{a0}：{c0}分钟<br />{a1}：{c1}分钟',
			axisPointer : {
				type : 'shadow'
			}
		},
		legend : {
			selectedMode: false,
			data : ['班级平均用时']
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
			max: maxValue,
			splitNumber : 10,
			axisLabel : {
				formatter : '{value} 分钟'
			}
		}],
		series : [{
			name : '我的用时',
			type : 'bar',
			barWidth: 60,
			data : [selfUsedTime]
		}, {
			name : '班级平均用时',
			type : 'line',
			data : [avgUsedTime],
			symbolSize : 10,
			markPoint : {
				silent : true,
				data : [{
					type : "max",
					name : "班级平均用时"
				}]
			}
		}]
	};
}

class FinishedInfo extends React.Component {
	render() {
		let {summary, scoreRanks} = this.props;
		let vs = [0, 0, 0, 0, 0];
		scoreRanks.filter(v => v.level <= 5).forEach(scoreRank => {
			var rate = toFixed(scoreRank.scoreRate * 100, 1);
			var level = toLevel(rate);
			vs[level - 1]++;
		});
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">作业完成概况</div>
				<ReactChart className="maps" option={buildScoreBar(summary)} />
				<ReactChart className="maps" option={buildUsedTimeBar(summary)} />
				<ReactChart className="maps" option={ScoreChartConfig.buildScorePie(vs[0], vs[1], vs[2], vs[3], vs[4], '人')} />
				<div className="tips">等级计算方法： 满分100分作业，85分以上为优秀，[70-85)分为良好，[60-70)分为及格，[45-60)分为较差，45分以下为危险，满分不为100分各等级按相应比例折算。</div>
			</section>
		);
	}
}

module.exports = FinishedInfo;
