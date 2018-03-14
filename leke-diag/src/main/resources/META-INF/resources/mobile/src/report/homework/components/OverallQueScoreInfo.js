var {toFixed, toRate} = require('../../../utils/number');
var SimpleTable = require('../../../react/SimpleTable');

class QueScoreInfo extends React.Component {
	buildAll(queGroups) {
		let length = 0, totalScore = 0, classScore = 0;
		queGroups.forEach(item => {
			length += item.qids.length;
			totalScore += item.totalScore;
			classScore += item.classScore;
		});
		return {
			name : '全部',
			queNum : length,
			totalScore : toFixed(totalScore, 1),
			avgScore : toFixed(classScore, 1),
			avgScoreRate : toRate(classScore, totalScore, 1) + '%'
		};
	}
	render() {
		let queGroups = this.props.queGroups;
		let datas = queGroups.map(item => {
			return {
				name : item.name,
				queNum : item.qids.length,
				totalScore : toFixed(item.totalScore, 1),
				avgScore : toFixed(item.classScore, 1),
				avgScoreRate : toRate(item.classScore, item.totalScore, 1) + '%'
			};
		});
		let columns = [
			{ title: '总题数', width: '25%', field: 'queNum' },
			{ title: '总分数', width: '25%', field: 'totalScore' },
			{ title: '平均得分', width: '25%', field: 'avgScore' },
			{ title: '得分率', width: '25%', field: 'avgScoreRate' }
		];
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">大题得分分析</div>
				<div className="c-table c-table-fixed">
					<SimpleTable caption={<caption>题型</caption>} columns={columns} datas={[]} showHead={true} />
					{datas.map(data => {
						return <SimpleTable caption={<caption>{data.name}</caption>} columns={columns} datas={[data]} defVal="--" />;
					})}
				</div>
			</section>
		);
	}
}

module.exports = QueScoreInfo;