var {toFixed, toRate} = require('../../../utils/number');

function colored(value) {
	return (<span className="fc-green">{value}</span>);
}

class PersonSummary extends React.Component {
	render() {
		let {userName, view} = this.props;
		let {totalNum, attendRate, previewRate, reviewRate, submitRate, bugFixRate, achieveNum, lekeNum} = view.summaryInfo;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="texts">
					<div>亲爱的{colored(userName)}同学：</div>
					<p className="paragraph">
						在此期间内，您应上课堂{colored(totalNum)}节，出勤率{colored(attendRate + '%')}；
						预/复习比率分别为{colored(previewRate + '%')}，{colored(reviewRate + '%')}；
						作业提交率{colored(submitRate + '%')}，订正率{colored(bugFixRate + '%')}；
						共取得{colored(achieveNum)}项成就，获得乐豆{colored(lekeNum)}；
						请努力学习，有付出就有收获。
					</p>
				</div>
			</section>
		);
	}
}

module.exports = PersonSummary;
