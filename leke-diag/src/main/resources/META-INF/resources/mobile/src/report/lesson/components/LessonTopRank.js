var SimpleTable = require('../../../react/SimpleTable');

var columns = [
	{ title: '排名', width: '12%', field: (data, index) => index + 1 },
	{ title: '姓名', width: '28%', field: 'userName' },
	{ title: '预习', width: '12%', field: 'review' },
	{ title: '互动', width: '12%', field: 'interact' },
	{ title: '随堂', width: '12%', field: 'testing' },
	{ title: '奖惩', width: '12%', field: 'reward' },
	{ title: '得分', width: '12%', field: 'score' }
];

// 金榜
class LessonTopRank extends React.Component {
	render() {
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">课堂金榜</div>
				<div className="c-table">
					<SimpleTable columns={columns} datas={this.props.datas} showHead={true} defVal="--" />
				</div>
				<div className="fl-right">
					<a className="fc-green" href={`../behavior/${ReportCst.lessonId}.htm`}>学生课堂行为列表&gt;&gt;<i></i></a>
				</div>
			</section>
		);
	}
}

module.exports = LessonTopRank;