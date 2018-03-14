var LekeDate = require('../../../utils/date');
// 课堂信息
class LessonInfo extends React.Component {
	render() {
		let {courseSingleName, startTime, endTime, className, subjectName} = this.props.lesson;
		return (
			<section className="c-top-title">
				<span>{LekeDate.format(startTime, 'yyyy-MM-dd HH:mm')} - {LekeDate.format(endTime, 'HH:mm')}</span>
				<span>{subjectName}</span>
				<span>{courseSingleName}</span>
			</section>
		);
	}
}

module.exports = LessonInfo;
