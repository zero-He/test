let Anchor = require('../../react/Anchor');
var OverallSummaryInfo = require('./components/OverallSummaryInfo');
var OverallFinishedInfo = require('./components/OverallFinishedInfo');
var OverallScoreStatInfo = require('./components/OverallScoreStatInfo');
var OverallQueScoreInfo = require('./components/OverallQueScoreInfo');
var OverallKnoScoreInfo = require('./components/OverallKnoScoreInfo');
var OverallClassRankInfo = require('./components/OverallClassRankInfo');

let anchors = [
	{title: '回到顶部', link: 'ach_summary'},
	{title: '作业完成概况', link: 'ach_finished'},
	{title: '成绩人数分布', link: 'ach_scorestat'},
	{title: '大题得分分析', link: 'ach_quescore'},
	{title: '知识点分析', link: 'ach_knoscore'},
	{title: '班级成绩排行', link: 'ach_classrank'}
];

class ReportView extends React.Component {
	render() {
		let {homeworkId, view} = ReportCst;
		let {summary, totalScore, scoreRanks, queGroups, knoGroups} = view;
		return (
			<article className="c-detail">
				<OverallSummaryInfo id="ach_summary" summary={summary} totalScore={totalScore} userName={ReportCst.userName} />
				<OverallFinishedInfo id="ach_finished" summary={summary} totalScore={totalScore} />
				<OverallScoreStatInfo id="ach_scorestat" scoreRanks={scoreRanks} totalScore={totalScore} />
				<OverallQueScoreInfo id="ach_quescore" queGroups={queGroups} />
				<OverallKnoScoreInfo id="ach_knoscore" knoGroups={knoGroups} />
				<OverallClassRankInfo id="ach_classrank" scoreRanks={scoreRanks} homeworkId={homeworkId} />
				<Anchor items={anchors} />
			</article>
		);
	}
}

var app = document.getElementById('app');
ReactDOM.render(<ReportView />, app);
