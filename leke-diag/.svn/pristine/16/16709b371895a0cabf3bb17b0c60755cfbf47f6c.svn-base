var {toFixed, toRate} = require('../../../utils/number');
var SimpleTable = require('../../../react/SimpleTable');

let columns = [
	{ title: '排名', width: '20%', field: 'index' },
	{ title: '姓名', width: '50%', field: (item, index) => {
		if (item.level <= 5) {
			return <a href={`../detail/${ReportCst.homeworkId}-${item.userId}.htm`}>{item.userName}</a>;
		}
		return item.userName;
	} },
	{ title: '分数', width: '30%', field: (item, index) => <span>{toFixed(item.score, 1)}{item.level == 6 ? '+' : ''}{item.level == 7 ? '--' : ''}</span> }
];

class ClassRankInfo extends React.Component {
	buildLevel(list, name, level) {
		list = list.filter(v => v.level == level);
		if (list.length == 0) {
			return null;
		}
		let caption = <caption className={`r-${level}`}>{`${name}（${list.length}人）`}</caption>;
		return <SimpleTable caption={caption} columns={columns} datas={list} defVal="--" />;
	}
	resolveIndex(list) {
		var prev = 10000, index = 0;
		for (var i = 0; i < list.length; i++) {
			var rank = list[i];
			if (rank.level <= 5) {
				if (rank.score < prev) {
					index++;
					prev = rank.score;
				}
				rank.index = index;
			} else {
				rank.index = '--';
			}
		}
	}
	render() {
		let that = this;
		let list = this.props.scoreRanks;
		this.resolveIndex(list);
		let names = ['优秀', '良好', '及格', '较差', '危险', '未批改', '未提交'];
		return (
			<section id={this.props.id} className="ana-module">
            	<div className="title">班级成绩排行</div>
            	<div className="c-table c-table-fixed">
					<SimpleTable columns={columns} datas={[]} showHead={true} />
					{names.map((name, i) => that.buildLevel(list, name, i + 1))}
				</div>
				<div className="tips">注：点击学生姓名可查看该生作业分析报告</div>
			</section>
		);
	}
}

module.exports = ClassRankInfo;
