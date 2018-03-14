var SimpleTable = require('../../../react/SimpleTable');

var columns = [
	{ title: '应到人数', width: '25%', field: 'planNum' },
	{ title: '实到人数', width: '25%', field: 'realNum' },
	{ title: '非全勤人数', width: '25%', field: (data, index) => data.partNum > 0 ? <a href={`../attendnames/${ReportCst.lessonId}-0.htm`}>{data.partNum}</a> : data.partNum },
	{ title: '缺勤人数', width: '25%', field: (data, index) => data.missNum > 0 ? <a href={`../attendnames/${ReportCst.lessonId}-2.htm`}>{data.missNum}</a> : data.missNum }
];

// 课堂考勤
class LessonAttendance extends React.Component {
	handleShowNames(status) {
	}
	render() {
		var attend = this.props.attend;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">课堂考勤</div>
				<div className="c-table">
					<SimpleTable columns={columns} datas={[attend]} showHead={true} defVal="--" />
				</div>
			</section>
		);
	}
}

module.exports = LessonAttendance;