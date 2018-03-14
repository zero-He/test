var LekeDate = require('../../../utils/date');
var {toFixed, toRate} = require('../../../utils/number');
var SimpleTable = require('../../../react/SimpleTable');

// 快速提问
class LessonQuickInfo extends React.Component {
	render() {
		var datas = this.props.datas;
		if (datas == null || datas.length == 0) {
			return null;
		}
		function index(data, index) {
			return index + 1;
		}
		function startTime(data, index) {
			return LekeDate.format(data.start, 'HH:mm');
		}
		function done(data, index) {
			var rate = toRate(data.done, data.total);
			return `${data.done}(${rate}%)`;
		}
		var columns = [
			{ title: '序号', width: '15%', field: index },
			{ title: '发起时间', width: '20%', field: startTime },
			{ title: '类型', width: '25%', field: 'topic' },
			{ title: '在线人数', width: '20%', field: 'total' },
			{ title: '提交人数(比率)', width: '20%', field: done }
		];
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">快速提问</div>
				<div className="c-table">
					<SimpleTable columns={columns} datas={this.props.datas} showHead={true} defVal="--" />
				</div>
			</section>
		);
	}
}

module.exports = LessonQuickInfo;