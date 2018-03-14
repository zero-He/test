let Anchor = require('../../react/Anchor');
let Loading = require('../../react/Loading');
let ToolTips = require('../../react/ToolTips');
let FilterBox = require('./components/FilterBox');
let DiligentInfo = require('./components/DiligentInfo');
let AchievementInfo = require('./components/AchievementInfo');
let AttendStatInfo = require('./components/AttendStatInfo');
let ClassStuationInfo = require('./components/ClassStuationInfo');
let ViewFinishAnalyInfo = require('./components/ViewFinishAnalyInfo');
let WorkFinishAnalyInfo = require('./components/WorkFinishAnalyInfo');
let StudentBehaviorSummary = require('./components/StudentBehaviorSummary');
let OtherBehaviorInfo = require('./components/OtherBehaviorInfo');

let {childs, subjs, cycles} = ReportCst;

let anchors = [
	{title: '回到顶部', link: 'ach_summary'},
	{title: '成就', link: 'ach_achieve'},
	{title: '上课情况', link: 'ach_stuation'},
	{title: '预复习情况', link: 'ach_viewfinish'},
	{title: '作业情况', link: 'ach_workfinish'},
	{title: '其它学习行为汇总', link: 'ach_otherinfo'}
];

ReportCst.userId = childs[0].value;
ReportCst.userName = childs[0].label;
class ReportView extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			loading: true,
			userId: ReportCst.userId,
			cycleId: cycles[0].subs[0].value
		};
		this.handleChangeChild = this.handleChangeChild.bind(this);
		this.handleChangeCycleId = this.handleChangeCycleId.bind(this);
	}
	handleQuery(userId, cycleId) {
		this.setState({userId: userId, cycleId: cycleId, loading: true});
		$.post('data.htm', {
			studentId: userId,
			cycleId: cycleId
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
		let {cycleId} = this.state;
		this.handleQuery(userId, cycleId);
	}
	handleChangeCycleId(cycleId) {
		let {userId} = this.state;
		this.handleQuery(userId, cycleId);
	}
	componentDidMount() {
		let {userId, cycleId} = this.state;
		this.handleQuery(userId, cycleId);
	}
	render() {
		let {subjId} = this.state;
		return (
			<div className="c-analyse">
				<nav className="c-nav">
					<ul>
						<li><a href="../score/combined.htm">综合分析报告</a></li>
						<li><a href="../score/subject.htm">学科分析报告</a></li>
						<li className="cur">行为分析报告</li>
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
		let {cycleId, loading, view} = this.state;
		if (view == null || loading == true) {
			return <Loading />;
		} else if (view.success == false) {
			return <ToolTips>{view.message}</ToolTips>;
		}
		return (
			<section className="c-detail">
				<StudentBehaviorSummary id="ach_summary" view={view} userName={ReportCst.userName} />
				<DiligentInfo id="ach_diligent" view={view} />
				<AchievementInfo id="ach_achieve" view={view} />
				<AttendStatInfo id="ach_attend" view={view} />
				<ClassStuationInfo id="ach_stuation" view={view} cycleId={cycleId} />
				<ViewFinishAnalyInfo id="ach_viewfinish" viewFinishAnaly={view.viewFinishAnaly} />
				<WorkFinishAnalyInfo id="ach_workfinish" workFinishAnaly={view.workFinishAnaly} cycleId={cycleId} />
				<OtherBehaviorInfo id="ach_otherinfo" otherWiseInfo={view.otherWiseInfo} lekeVal={view.lekeVal} />
				<Anchor items={anchors} />
			</section>
		);
	}
}

let app = document.getElementById('app');
ReactDOM.render(<ReportView />, app);
