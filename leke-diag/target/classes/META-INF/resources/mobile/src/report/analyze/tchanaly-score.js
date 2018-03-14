let Anchor = require('../../react/Anchor');
let Loading = require('../../react/Loading');
let ToolTips = require('../../react/ToolTips');
let FilterBox = require('./components/FilterBox');
let TeacherClassSummary = require('./components/TeacherClassSummary');
let ScoreTrendInfo = require('./components/ScoreTrendInfo');
let ScoreRankListInfo = require('./components/ScoreRankListInfo');
let SubjectKnoRateInfo = require('./components/SubjectKnoRateInfo');

let anchors = [
	{title: '成绩分析', link: 'ach_summary'},
	{title: '班级成长走势', link: 'ach_trend'},
	{title: '班级成绩排行', link: 'ach_ranklist'},
	{title: '知识点掌握程度', link: 'ach_knorate'}
];

let {klasses, cycles} = ReportCst;

class ReportView extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			loading: true,
			classSubjId: klasses[0].value,
			cycleId: cycles[0].subs[0].value
		};
		this.handleChangeCycleId = this.handleChangeCycleId.bind(this);
		this.handleChangeClassSubjId = this.handleChangeClassSubjId.bind(this);
	}
	handleQuery(classSubjId, cycleId) {
		this.setState({loading: true});
		$.post(`data/${classSubjId}.htm`, {
			cycleId: cycleId
		}).done(function(json) {
			this.setState({
				loading: false,
				scope: [classSubjId, cycleId].join('-'),
				view: json.datas.view
			});
		}.bind(this));
	}
	handleChangeCycleId(cycleId) {
		this.setState({ cycleId: cycleId });
		let {classSubjId} = this.state;
		this.handleQuery(classSubjId, cycleId);
	}
	handleChangeClassSubjId(classSubjId) {
		this.setState({ classSubjId: classSubjId });
		let {cycleId} = this.state;
		this.handleQuery(classSubjId, cycleId);
	}
	componentDidMount() {
		let {classSubjId, cycleId} = this.state;
		this.handleQuery(classSubjId, cycleId);
	}
	render() {
		return (
			<div className="c-analyse">
				<nav className="c-nav">
					<ul>
						<li className="cur">班级成绩分析</li>
					</ul>
				</nav>
				<FilterBox>
					<FilterBox.Item options={klasses} onChange={this.handleChangeClassSubjId} />
					<FilterBox.Item options={cycles} onChange={this.handleChangeCycleId} />
				</FilterBox>
				{this.renderBody()}
			</div>
		);
	}
	renderBody() {
		let {loading, view} = this.state;
		if (view == null || loading == true) {
			return <Loading />;
		} else if (view.success == false) {
			return <ToolTips>{view.message}</ToolTips>;
		}
		return (
			<section className="c-detail">
				<TeacherClassSummary id="ach_summary" summary={view.summary} />
				<ScoreTrendInfo id="ach_trend" trends={view.trends} title="班级成长走势" />
				<ScoreRankListInfo id="ach_ranklist" scoreRanks={view.scoreRanks} scope={this.state.scope} />
				<SubjectKnoRateInfo id="ach_knorate" knoRates={view.knoRates} />
				<Anchor items={anchors} />
			</section>
		);
	}
}

let app = document.getElementById('app');
if (!(klasses && klasses.length)) {
	ReactDOM.render(<ToolTips>您暂无关联班级，无数据可供分析！</ToolTips>, app);
} else {
	ReactDOM.render(<ReportView />, app);
}
