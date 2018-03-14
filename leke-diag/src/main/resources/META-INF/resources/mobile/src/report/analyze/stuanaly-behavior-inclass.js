let DropLoad = require('../../react/DropLoad');
let LekeDate = require('../../utils/date');

class BehaviorDetail extends React.Component {
	render() {
		let data = this.props.data;
		let student = data.students[0];
		let topName = '--';
		if (student.rank == 1) {
			topName = '状元';
		} else if (student.rank == 2) {
			topName = '榜眼';
		} else if (student.rank == 3) {
			topName = '探花';
		}
		return (
			<div className="c-table c-table-fixed c-classdetail">
				<div className="date">
					<div>{LekeDate.format(data.start, 'HH:mm')}~{LekeDate.format(data.end, 'HH:mm')}</div>
					<div>{LekeDate.format(data.start, 'yyyy/MM/dd')}</div>
				</div>
				<table>
					<caption>
						<span className="subject">{data.subjectName}</span>
						<span className="studentname">{data.teacherName}</span>
						《<span className="classname">{data.courseSingleName}</span>》
					</caption>
					<tbody>
						<tr className="title">
							<td>金榜题名</td>
							<td>点到</td>
							<td>举手</td>
							<td>被授权</td>
							<td>课堂笔记</td>
						</tr>
						<tr>
							<td>{topName}</td>
							<td>{student.called}/{data.callNum}</td>
							<td>{student.raised}</td>
							<td>{student.authed}</td>
							<td>{student.noteNum}</td>
						</tr>
						<tr className="title">
							<td>随堂作业</td>
							<td>分组讨论</td>
							<td>快速问答</td>
							<td>献花</td>
							<td>评价</td>
						</tr>
						<tr>
							<td>{student.examNum}/{data.examNum}</td>
							<td>{student.discuNum}/{data.discuNum}</td>
							<td>{student.quickNum}/{data.quickNum}</td>
							<td>{student.flower}</td>
							<td>{student.isEval ? '有' : '无'}</td>
						</tr>
					</tbody>
				</table>
			</div>
		);
	}
}

class App extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			datas: []
		};
	}
	handleRender(datas, curPage) {
		if (curPage > 1) {
			datas = this.state.datas.concat(datas);
		}
		this.setState({datas: datas});
	}
	render() {
		let datas = this.state.datas;
		let opts = {
			url : 'inclass2.htm'
		};
		let params = {
			userId : ReportCst.userId,
			cycleId : ReportCst.cycleId
		}
		return (
			<div>
				<section className="c-top-title">
					<span>{ReportCst.cycleName}</span>
				</section>
				<DropLoad opts={opts} params={params} onRender={this.handleRender.bind(this)}>
					{datas.map(data => <BehaviorDetail data={data} />)}
				</DropLoad>
			</div>
		);
	}
}

let app = document.getElementById('app');
ReactDOM.render(<App />, app);
