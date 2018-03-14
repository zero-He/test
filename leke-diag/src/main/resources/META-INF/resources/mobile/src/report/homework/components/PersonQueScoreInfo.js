var {toFixed, toRate} = require('../../../utils/number');
var SimpleTable = require('../../../react/SimpleTable');

let columns = [
	{ title: '总题数', field: 'queNum' },
	{ title: '总分值',  field: 'totalScore' },
	{ title: '正确题数', field: 'rightNum' },
	{ title: '得分',  field: 'selfScore' },
	{ title: '得分率',  field: 'selfScoreRate' },
	{ title: '班级得分率', field: 'classScoreRate' },
];

class QueScoreInfo extends React.Component {
	buildAll(queGroups) {
		let length = 0, rightNum = 0, totalScore = 0, classScore = 0, selfScore = 0;
		queGroups.forEach(item => {
			length += item.qids.length;
			rightNum += item.rightNum;
			totalScore += item.totalScore;
			classScore += item.classScore;
			selfScore += item.selfScore;
		});
		return {
			name : '全部',
			queNum : length,
			totalScore : toFixed(totalScore, 1),
			rightNum : toFixed(rightNum, 1),
			selfScore : toFixed(selfScore, 1),
			selfScoreRate : toRate(selfScore, totalScore, 1) + '%',
			classScoreRate : toRate(classScore, totalScore, 1) + '%'
		};
	}
	render() {
		let queGroups = this.props.queGroups;
		let datas = queGroups.map(item => {
			return {
				name : item.name,
				queNum : item.qids.length,
				totalScore : toFixed(item.totalScore, 1),
				rightNum : toFixed(item.rightNum, 1),
				selfScore : toFixed(item.selfScore, 1),
				selfScoreRate : toRate(item.selfScore, item.totalScore, 1) + '%',
				classScoreRate : toRate(item.classScore, item.totalScore, 1) + '%'
			};
		});
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">大题得分分析</div>
				<div className="c-table c-table-fixed">
					<SimpleTable caption={<caption>大题</caption>} columns={columns} datas={[]} showHead={true} />
					{datas.map(data => {
						return <SimpleTable caption={<caption>{data.name}</caption>} columns={columns} datas={[data]} defVal="--" />;
					})}
				</div>
			</section>
		);
	}
}

module.exports = QueScoreInfo;
