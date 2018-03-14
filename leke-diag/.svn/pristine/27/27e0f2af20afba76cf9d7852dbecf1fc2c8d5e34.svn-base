let DropLoad = require('../../react/DropLoad');
let SimpleTable = require('../../react/SimpleTable');
let LekeDate = require('../../utils/date');

let queryParams = {
	submitStatus : 1,
	userId : ReportCst.userId,
	cycleId : ReportCst.cycleId
}

let columns = {
	1: [
		{ title: '作业名称', width: '50%', field: 'homeworkName' },
		{ title: '截止时间', width: '25%', field: (data, index) => LekeDate.format(data.closeTime, 'yyyy-MM-dd HH:mm') },
		{ title: '提交时间', width: '25%', field: (data, index) => LekeDate.format(data.submitTime, 'yyyy-MM-dd HH:mm') }
	],
	2: [
		{ title: '作业名称', width: '50%', field: 'homeworkName' },
		{ title: '截止时间', width: '25%', field: (data, index) => LekeDate.format(data.closeTime, 'yyyy-MM-dd HH:mm') },
		{ title: '延交时间', width: '25%', field: (data, index) => LekeDate.format(data.submitTime, 'yyyy-MM-dd HH:mm') }
	],
	0: [
		{ title: '作业名称', width: '70%', field: 'homeworkName' },
		{ title: '截止时间', width: '30%', field: (data, index) => LekeDate.format(data.closeTime, 'yyyy-MM-dd HH:mm') }
	]
}

class App extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			submitStatus : 1,
			datas: []
		};
	}
	handleRender(datas, curPage) {
		if (curPage > 1) {
			datas = this.state.datas.concat(datas);
		}
		this.setState({datas: datas});
	}
	handleChangeStatus(submitStatus) {
		this.setState({submitStatus: submitStatus, datas: []});
		queryParams.submitStatus = submitStatus;
		this.refs.dropload.reload();
	}
	render() {
		let {submitStatus, datas} = this.state;
		let opts = {
			url : 'homework2.htm',
			onParams : function() {
				return queryParams;
			}
		};
		return (
			<div>
				<section className="c-top-title">
					<span>{ReportCst.cycleName}</span>
				</section>
				<nav className="c-nav">
					<ul>
						<li className={submitStatus == 1 ? 'cur' : ''} onClick={this.handleChangeStatus.bind(this, 1)}>正常提交</li>
						<li className={submitStatus == 2 ? 'cur' : ''} onClick={this.handleChangeStatus.bind(this, 2)}>延迟提交</li>
						<li className={submitStatus == 0 ? 'cur' : ''} onClick={this.handleChangeStatus.bind(this, 0)}>未提交</li>
					</ul>
				</nav>
				<DropLoad ref="dropload" opts={opts} onRender={this.handleRender.bind(this)}>
					<div className="c-table">
						<SimpleTable columns={columns[submitStatus]} datas={datas} showHead={true} />
					</div>
				</DropLoad>
			</div>
		);
	}
}

let app = document.getElementById('app');
ReactDOM.render(<App />, app);
