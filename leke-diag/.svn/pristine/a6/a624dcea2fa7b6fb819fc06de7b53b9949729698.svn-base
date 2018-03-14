var SimpleTable = require('../../../react/SimpleTable');

// 预/复习详情
class ReviewFinished extends React.Component {
	render() {
		let {phase, subNames, details} = this.props;
		var state2 = details.filter(item => item.state == 2);
		var state1 = details.filter(item => item.state == 1);
		var state0 = details.filter(item => item.state == 0);
		let i = 1;
		state2.forEach(v => v.index = i++);
		state1.forEach(v => v.index = i++);
		state0.forEach(v => v.index = i++);

		let title = phase == 1 ? '预习' : '复习';
		let icons = ['i-2', 'i-3', 'i-1'];
		let columns;
		if (phase == 1) {
			columns = [
				{ title: '序号', width: '15%', field: 'index' },
				{ title: '姓名', width: '20%', field: 'userName' },
				{ title: subNames[0], width: '15%', field: (item, index) => <i className={icons[item.state1 + 1]}></i> },
				{ title: subNames[1], width: '15%', field: (item, index) => <i className={icons[item.state2 + 1]}></i> },
				{ title: subNames[2], width: '15%', field: (item, index) => <i className={icons[item.state3 + 1]}></i> },
				{ title: '困惑\\结论', width: '20%', field: (item, index) => {
					if (item.hasIssue) {
						return <a className="s-c-turquoise" href={`${Leke.domain.beikeServerName}/auth/common/m/review/confuse.htm?_newtab=1&courseSingleId=${ReportCst.lessonId}&studentId=${item.userId}`}>查看</a>;
					}
					return '未提交';
				} }
			];
		} else {
			columns = [
				{ title: '序号', width: '15%', field: 'index' },
				{ title: '姓名', width: '40%', field: 'userName' },
				{ title: subNames[0], width: '15%', field: (item, index) => <i className={icons[item.state1 + 1]}></i> },
				{ title: subNames[1], width: '15%', field: (item, index) => <i className={icons[item.state2 + 1]}></i> },
				{ title: subNames[2], width: '15%', field: (item, index) => <i className={icons[item.state3 + 1]}></i> }
			];
		}
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">{title}完成详情</div>
				<div className="c-table">
					<SimpleTable columns={columns} datas={[]} showHead={true} />
					<SimpleTable caption={<caption className="r-1">{`完成(${state2.length}人)`}</caption>} columns={columns} datas={state2} />
					<SimpleTable caption={<caption className="r-4">{`部分完成(${state1.length}人)`}</caption>} columns={columns} datas={state1} />
					<SimpleTable caption={<caption className="r-5">{`未完成(${state0.length}人)`}</caption>} columns={columns} datas={state0} />
				</div>
			</section>
		);
	}
}

module.exports = ReviewFinished;
