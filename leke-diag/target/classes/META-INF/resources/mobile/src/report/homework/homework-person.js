let Anchor = require('../../react/Anchor');
var PersonSummaryInfo = require('./components/PersonSummaryInfo');
var PersonFinishedInfo = require('./components/PersonFinishedInfo');
var PersonQueScoreInfo = require('./components/PersonQueScoreInfo');
var PersonKnoScoreInfo = require('./components/PersonKnoScoreInfo');

let anchors = [
	{title: '回到顶部', link: 'ach_summary'},
	{title: '作业完成概况', link: 'ach_finished'},
	{title: '大题得分分析', link: 'ach_quescore'},
	{title: '知识点分析', link: 'ach_knoscore'}
];

class ReportView extends React.Component {
	render() {
		let {userName, myself} = ReportCst;
		let {summary, totalScore, scoreRanks, queGroups, knoGroups} = ReportCst.view;
		return (
			<article className="c-detail">
				<PersonSummaryInfo id="ach_summary" summary={summary} totalScore={totalScore} userName={userName} myself={myself} />
				<PersonFinishedInfo id="ach_finished" summary={summary} scoreRanks={scoreRanks} />
				<PersonQueScoreInfo id="ach_quescore" queGroups={queGroups} />
				<PersonKnoScoreInfo id="ach_knoscore" knoGroups={knoGroups} />
				<Anchor items={anchors} />
			</article>
		);
	}
}

var app = document.getElementById('app');
ReactDOM.render(<ReportView />, app);
