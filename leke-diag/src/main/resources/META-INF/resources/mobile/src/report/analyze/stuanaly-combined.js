let Anchor = require('../../react/Anchor');
let Loading = require('../../react/Loading');
let ToolTips = require('../../react/ToolTips');
let FilterBox = require('./components/FilterBox');
let StudentScoreSummary = require('./components/StudentScoreSummary');
let StudentOverallInfo = require('./components/StudentOverallInfo');
let ScoreCompared = require('./components/ScoreCompared');
let ScoreTrendInfo = require('./components/ScoreTrendInfo');
let ScoreTop10RankInfo = require('./components/ScoreTop10RankInfo');

let {childs, subjs, cycles} = ReportCst;

let anchors = [
	{title: '成绩分析', link: 'ach_summary'},
	{title: '学科优劣势分析', link: 'ach_overall'},
	{title: '综合成绩走势分析', link: 'ach_trend'},
	{title: '学科成绩对比分析', link: 'ach_scorecomp'},
	{title: '班级综合成绩TOP10', link: 'ach_scorerank'}
];

ReportCst.userId = childs[0].value;
ReportCst.userName = childs[0].label;
class ReportView extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			loading: true,
			userId: ReportCst.userId,
			subjId: 0,
			cycleId: cycles[0].subs[0].value
		};
		this.handleChangeChild = this.handleChangeChild.bind(this);
		this.handleChangeCycleId = this.handleChangeCycleId.bind(this);
	}
	handleQuery(userId, subjId, cycleId) {
		this.setState({userId: userId, subjId: subjId, cycleId: cycleId, loading: true});
		$.post('data.htm', {
			studentId: userId,
			cycleId: cycleId,
			subjectId : subjId
		}).done(function(json) {
			this.setState({
				loading: false,
				view: json.datas.view
			});
		}.bind(this));
	}
	handleChangeChild(userId, userName) {
		ReportCst.userId = userId;
		ReportCst.userName = userName;
		let {subjId, cycleId} = this.state;
		this.handleQuery(userId, subjId, cycleId);
	}
	handleChangeCycleId(cycleId) {
		let {userId, subjId} = this.state;
		this.handleQuery(userId, subjId, cycleId);
	}
	componentDidMount() {
		let {userId, subjId, cycleId} = this.state;
		this.handleQuery(userId, subjId, cycleId);
	}
	render() {
		let {subjId} = this.state;
		return (
			<div className="c-analyse">
				<nav className="c-nav">
					<ul>
						<li className="cur"><a>综合分析报告</a></li>
						<li><a href="./subject.htm">学科分析报告</a></li>
						<li><a href="../behavior/index.htm">行为分析报告</a></li>
					</ul>
				</nav>
				<FilterBox>
					{childs.length > 1 ? <FilterBox.Item options={childs} onChange={this.handleChangeChild} /> : null}
					<FilterBox.Item options={cycles} onChange={this.handleChangeCycleId} />
				</FilterBox>
				{this.renderBody()}
			</div>
		);
	}
	renderBody() {
		let {loading, subjId, view} = this.state;
		if (view == null || loading == true) {
			return <Loading />;
		} else if (view.success == false) {
			return <ToolTips>{view.message}</ToolTips>;
		}
		return <ReportDetailView view={view} subjId={subjId} />;
	}
}

class ReportDetailView extends React.Component {
	render() {
		let {view, subjId} = this.props;
		let items = [];
		items.push(<StudentScoreSummary id="ach_summary" summary={view.summary} userName={ReportCst.userName} myself={true} />);
		if (view.summary.correctNum > 0) {
			items.push(<StudentOverallInfo id="ach_overall" overall={view.overall} subjScores={view.subjScores} isUnit={view.isUnit} />);
			items.push(<ScoreTrendInfo id="ach_trend" title="综合成绩走势分析" trends={view.trends} subjId={0} />);
			items.push(<ScoreCompared id="ach_scorecomp" title="学科成绩对比分析" subjScores={view.subjScores} rptType={view.rptType} />);
			items.push(<ScoreTop10RankInfo id="ach_scorerank" title="班级综合成绩TOP10" scoreRanks={view.scoreRanks} subjId={0} />);
			items.push(<Anchor items={anchors} />);
		}
		return (
			<section className="c-detail">{items}</section>
		);
	}
}

let app = document.getElementById('app');
ReactDOM.render(<ReportView />, app);
