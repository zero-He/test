let Anchor = require('../../react/Anchor');
let LessonInfo = require('./components/LessonInfo');
let ReviewOverview = require('./components/ReviewOverview');
let ReviewFinished = require('./components/ReviewFinished');

let anchors = [
	{title: '复习情况概览', link: 'ach_overview'},
	{title: '复习完成详情', link: 'ach_finished'}
];

class ReportView extends React.Component {
	render() {
		let subNames = ['课件', '试卷', '笔记'];
		let lesson = ReportCst.lesson;
		let {overall, details, exams} = ReportCst.reviewInfo;
		return (
			<div>
				<LessonInfo lesson={lesson} />
				<div className="c-detail">
					<ReviewOverview id="ach_overview" phase={3} subNames={subNames} overall={overall} exams={exams} />
					<ReviewFinished id="ach_finished" phase={3} subNames={subNames} details={details} exams={exams} />
				</div>
				<Anchor items={anchors} />
			</div>
		);
	}
}

let app = document.getElementById('app');
ReactDOM.render(<ReportView />, app);