var LekeDate = require('../../../utils/date');
var SimpleTable = require('../../../react/SimpleTable');


class LessonCallInfo extends React.Component {
	handleShowNames(names) {
		var tpl = '<div class="names">' + names.map(n => `<span>${n}</span>`).join('') + '</div>';
		console.log(tpl);
	}
	render() {
		var that = this;
		var datas = this.props.datas;
		if (datas == null || datas.length == 0) {
			return null;
		}
		function startTime(data, index) {
			return LekeDate.format(data.start, 'HH:mm');
		}
		function startUser(data, index) {
			return data.type == 401 ? '老师' : '系统';
		}
		function undone(data, index) {
			var undo = data.total - data.done;
			if (undo == 0) {
				return undo;
			}
			return <a href={`../callnames/${ReportCst.lessonId}-${index}.htm`}>{undo}</a>;
		}
		var columns = [
			{ title: '点名', width: '25%', field: 'topic' },
			{ title: '发起者', width: '15%', field: startUser },
			{ title: '时间', width: '14%', field: startTime },
			{ title: '应到', width: '12%', field: 'total' },
			{ title: '实到', width: '12%', field: 'done' },
			{ title: '未到', width: '12%', field: undone }
		];
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">课堂点名</div>
				<div className="c-table">
					<SimpleTable columns={columns} datas={datas} showHead={true} defVal="--" />
				</div>
			</section>
		);
	}
}

module.exports = LessonCallInfo;