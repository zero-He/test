var {toFixed, toRate} = require('../../../utils/number');
var {toLevel} = require('./analyze-base');
var SimpleTable = require('../../../react/SimpleTable');

class ScoreRankInfo extends React.Component {
	markRankIndex(scoreRanks, subjId) {
	    let prev = 10000, index = 0;
	    for (var i = 0; i < scoreRanks.length; i++) {
	        let score = scoreRanks[i][subjId];
	        if (score != null) {
				if (score < prev) {
					index++;
					prev = score;
				}
				scoreRanks[i].index = index;
			} else {
				scoreRanks[i].index = '--';
			}
	    }
	}
	render() {
		let {scoreRanks, subjId, userId} = this.props;
		if (scoreRanks == undefined || scoreRanks == null) {
			return null;
		}
		this.markRankIndex(scoreRanks, subjId);
		scoreRanks = scoreRanks.filter((data, index) => (index < 10 || data.userId == ReportCst.userId));
		let columns = [
			{ title: '名次', width: '15%', field: 'index' },
			{ title: '姓名', width: '60%', field: (data, index) => <span className={data.userId == ReportCst.userId ? 'fc-orange' : ''}>{data.userName}</span> },
			{ title: '成绩', width: '25%', field: (data, index) => toFixed(data[subjId], 1) }
		];
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">{this.props.title}</div>
				<div className="c-table">
					<SimpleTable columns={columns} datas={scoreRanks} showHead={true} defVal="--" />
				</div>
			</section>
		);
	}
}

module.exports = ScoreRankInfo;