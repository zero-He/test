var LekeDate = require('../../../utils/date');
var SimpleTable = require('../../../react/SimpleTable');

function timeString(data, index) {
	var str = LekeDate.format(data.start, 'HH:mm:ss');
	if (data.end != null && data.end > 0) {
		str += ' - ' + LekeDate.format(data.end, 'HH:mm:ss');
	}
	return str;
}
function timeLength(data, index) {
	if (data.end != null && data.end > 0) {
		var len = Math.floor(data.end / 1000) - Math.floor(data.start / 1000);
		var second = len % 60, minute = Math.floor(len / 60);
		return `${minute}分${second}秒`;
	}
	return '--';
}

// 时间轴
class BehaviorTimeline extends React.Component {
	render() {
		var columns = [
			{ title: '序号', width: '14%', field: (data, index) => index + 1 },
			{ title: '互动项目', width: '26%', field: 'typeName' },
			{ title: '起止时间', width: '38%', field: timeString },
			{ title: '时长', width: '22%', field: timeLength }
		];
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">课堂行为</div>
				<div className="c-table">
					<SimpleTable columns={columns} datas={this.props.datas} defVal="--" showHead={true} />
				</div>
			</section>
		);
	}
}

module.exports = BehaviorTimeline;