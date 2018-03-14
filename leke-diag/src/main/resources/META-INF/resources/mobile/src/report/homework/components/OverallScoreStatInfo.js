var {toFixed, toRate} = require('../../../utils/number');
var {toLevel} = require('./homework-utils');
var ReactChart = require('../../../react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');

function buildScoreBar(scoreRanks, totalScore) {
	let labels = [], values = [];
	for (var i = 0; i < 10; i++) {
		let minRate = 10 * i, maxRate = minRate + 10;
		let min = toFixed(totalScore * minRate / 100, 0);
		let max = toFixed(totalScore * maxRate / 100, 0);
		labels.push(`${min}~${max}`);
		values.push(0);
	}
	scoreRanks.filter(v => v.level <= 5).forEach(rank => {
		let idx = parseInt(rank.scoreRate * 10);
		if (idx >= 10) {
			idx = 9;
		}
		values[idx]++;
	});
	return ScoreChartConfig.buildScoreSectionBar(labels, values);
}

class ScoreStatInfo extends React.Component {
	render() {
		let {scoreRanks, totalScore} = this.props;
		let vs = [0, 0, 0, 0, 0];
		scoreRanks.filter(v => v.level <= 5).forEach(scoreRank => {
			var rate = toFixed(scoreRank.scoreRate * 100, 1);
			var level = toLevel(rate);
			vs[level - 1]++;
		});
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">成绩人数分布</div>
				<ReactChart className="maps" option={ScoreChartConfig.buildScorePie(vs[0], vs[1], vs[2], vs[3], vs[4])} />
				<div className="tips">等级计算方法： 满分100分作业，85分以上为优秀，[70-85)分为良好，[60-70)分为及格，[45-60)分为较差，45分以下为危险，满分不为100分各等级按相应比例折算。</div>
				<ReactChart className="maps" style={{height: 180}} option={buildScoreBar(scoreRanks, totalScore)} />
			</section>
		);
	}
}

module.exports = ScoreStatInfo;