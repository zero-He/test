let Anchor = require('../../react/Anchor');
let ToolTips = require('../../react/ToolTips');
let LessonInfo = require('./components/LessonInfo');
let BehaviorTimeline = require('./components/BehaviorTimeline');
let LessonTopRank = require('./components/LessonTopRank');
let LessonEvaluate = require('./components/LessonEvaluate');
let LessonAttendance = require('./components/LessonAttendance');
let LessonHomework = require('./components/LessonHomework');
let LessonCallInfo = require('./components/LessonCallInfo');
let LessonQuickInfo = require('./components/LessonQuickInfo');

let anchors = [
	{title: '课堂行为', link: 'ach_timeline'},
	{title: '课堂评价', link: 'ach_evaluate'},
	{title: '课堂金榜', link: 'ach_toprank'},
	{title: '随堂作业', link: 'ach_homework'},
	{title: '课堂考勤', link: 'ach_attendance'},
	{title: '课堂点名', link: 'ach_callinfo'},
	{title: '快速提问', link: 'ach_quickinfo'}
];

class ReportView extends React.Component {
	render() {
		if (ReportCst.middleInfo == null || ReportCst.middleInfo.success == false) {
			return <ToolTips>{ReportCst.middleInfo.message}</ToolTips>;
		}
		let lesson = ReportCst.lesson;
		let {flower, lessonEval, teacherEval, interacts, tops, exams, attendStatInfo, calleds, quicks} = ReportCst.middleInfo;
		return (
			<div>
				<LessonInfo lesson={lesson} />
				<div className="c-detail">
					<BehaviorTimeline id="ach_timeline" datas={interacts} />
					<LessonEvaluate id="ach_evaluate" lessonEval={lessonEval} teacherEval={teacherEval} flower={flower} />
					<LessonTopRank id="ach_toprank" datas={tops} />
					<LessonHomework id="ach_homework" datas={exams} />
					<LessonAttendance id="ach_attendance" attend={attendStatInfo} />
					<LessonCallInfo id="ach_callinfo" datas={calleds} />
					<LessonQuickInfo id="ach_quickinfo" datas={quicks} />
				</div>
				<Anchor items={anchors} />
			</div>
		);
	}
}

let app = document.getElementById('app');
ReactDOM.render(<ReportView />, app);