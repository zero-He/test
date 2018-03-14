var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var $ = require('jquery');
var {cx} = require('common/react/react-utils');
var ReactChart = require('common/react/ReactChart');
var ReactPieChart = require('../common/ReactPieChart');
var Section = require('../common/Section');
var BehaviorChartConfig = require('./BehaviorChartConfig');
var {toFixed, toRate} = require('../utils/number');
var Dialog = require('dialog');

function renderLekeVal(lekeVal, typeId1, typeId2) {
	var val1 = lekeVal[typeId1] ? lekeVal[typeId1] : 0;
	var val2 = lekeVal[typeId2] ? lekeVal[typeId2] : 0;
	return val1 + val2;
}

class ScoreReportQuery extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			typeIdx: 0,
			zoneIdx: 0,
			cycleId: props.types[0].list[0].id
		};
		this.handleChangeCycle = this.handleChangeCycle.bind(this);
		this.handleChangeZone = this.handleChangeZone.bind(this);
	}
	handleClickType(typeIdx) {
		if (this.state.typeIdx != typeIdx) {
			let cycleId = this.props.types[typeIdx].list[0].id;
			this.setState({
				typeIdx: typeIdx,
				cycleId: cycleId
			});
			this.handleChange(this.state.zoneIdx, cycleId);
		}
	}
	handleChangeCycle(e) {
		let cycleId = parseInt(e.target.value);
		this.setState({ cycleId: cycleId });
		this.handleChange(this.state.zoneIdx, cycleId);
	}
	handleChangeZone(e) {
		let zoneIdx = parseInt(e.target.value);
		this.setState({ zoneIdx: zoneIdx });
		this.handleChange(zoneIdx, this.state.cycleId);
	}
	handleChange(zoneIdx, cycleId) {
		if (this.props.onChange) {
			let zone = this.props.zones[zoneIdx];
			this.props.onChange(zone.classId, zone.subjectId, cycleId);
		}
	}
	componentDidMount() {
		let {zoneIdx, cycleId} = this.state;
		this.handleChange(zoneIdx, cycleId);
	}
	render() {
		let that = this;
		let {typeIdx, cycleId} = this.state;
		let {types, zones, hideZone} = this.props;
		return (
			<div className="m-search-box">
				<div className="f-fl">
					<ul>
					{types.map((rt, i) => {
						let handler = that.handleClickType.bind(that, i);
						return <li key={i} className={cx({"active": i == typeIdx})}><a onClick={handler}>{rt.label}</a></li>;
					})}
					</ul>
				</div>
				<div className="f-fr">
					<select className="u-select u-select-lg" onChange={this.handleChangeCycle} value={cycleId}>
						{types[typeIdx].list.map((c, i) => { return <option key={i} value={c.id}>{c.label}</option>; })}
					</select>
					<select className={cx("u-select u-select-lg", {"f-dn": hideZone || false})} onChange={this.handleChangeZone}>
						{zones.map((c, i) => { return <option key={i} value={i}>{c.label}</option>; })}
					</select>
				</div>
			</div>
		);
	}
}

function colored(value) {
	return (<span className="colored">{value}</span>);
}

class PersonSummary extends React.Component {
	render() {
		let {userName, summaryInfo} = this.props;
		let {totalNum, attendRate, previewRate, reviewRate, submitRate, bugFixRate, achieveNum, lekeNum} = summaryInfo;
		return (
			<div className="summary">
				<p>亲爱的<span className="colored">{userName}</span>同学：</p>
				<p className="details">
					在此期间内，您应上课堂{colored(totalNum)}节，出勤率{colored(attendRate + '%')}；
					预/复习比率分别为{colored(previewRate + '%')}，{colored(reviewRate + '%')}；
					作业提交率{colored(submitRate + '%')}，订正率{colored(bugFixRate + '%')}；
					共取得{colored(achieveNum)}项成就，获得乐豆{colored(lekeNum)}；
					请努力学习，有付出就有收获。
				</p>
			</div>
		);
	}
}

class DiligentInfo extends React.Component {
	static achieveLevels = ['不勤奋', '不够勤奋', '较勤奋', '很勤奋', '非常勤奋', '非常勤奋'];
    buildGaugeOption(value) {
		const idx = Math.floor(value / 20);
		const name = DiligentInfo.achieveLevels[idx];
        return {
			series: [{
				type: 'gauge',
				radius: '90%',
				data: [{
					name: name,
					value: value
				}],
				axisLine: {
					lineStyle: {
						color: [[0.20, '#7bb57b'],[0.80, '#85c6f4'],[1, '#ffac8e']]
					}
				}
			}]
		};
    }
    buildLineOptions(datas) {
        const labels = datas.map(v => v.name.replace(/（\S*）/, ''));
        const values = datas.map(v => v.value);
        return {
			color: ['#76b3a1'],
        	title: {
        		text: '勤奋指数走势',
        		left: 'center',
				textStyle: {
		            fontWeight:'normal'
		        }
        	},
        	tooltip: {
        		trigger: 'item',
        		formatter: '{b}：{c}'
        	},
        	grid: {
        		left: '3%',
        		right: '3%',
        		bottom: '8%',
        		containLabel: true
        	},
        	xAxis: {
        		type: 'category',
				boundaryGap: false,
        		data: labels
        	},
        	yAxis: {
        		type: 'value'
        	},
        	series: [{
        		type: 'line',
				areaStyle: {normal: {}},
        		data: values
        	}]
        };
    }
	render() {
		const {trends, score} = this.props.view.achievement;
		return (
            <Section id={this.props.id} title="勤奋指数" help={`${Leke.domain.staticServerName}/pages/help-center/diag/kq-zs.html`}>
                <div className="c-layout">
                    <div className="left" style={{width: '40%'}}>
                        <ReactChart className="maps" option={this.buildGaugeOption(score)} />
                    </div>
                    <div className="right" style={{width: '50%'}}>
                        <ReactChart className="maps" option={this.buildLineOptions(trends)} />
                    </div>
                </div>
				<div className="tips">勤奋指数共有5个层级分别是：[80-100]为非常勤奋，[60-80)为很勤奋，[40-60)为较勤奋，[20-40)为不够勤奋，[0-20)为不勤奋。</div>
			</Section>
		);
	}
}

var Achieves = [
	[1, 'allthework', '全勤'],
	[2, 'preview', '提前预习'],
	[3, 'listen', '专心听讲'],
	[4, 'hardworking', '勤思好问'],
	[5, 'active', '活跃分子'],
	[11, 'succeed', '榜上有名'],
	[6, 'help f-ml50 f-pl50', '手有余香'],
	[7, 'earnest', '及时改错'],
	[8, 'positive', '不磨蹭'],
	[9, 'intelligent', '练习达人'],
	[10, 'study', '温故知新']
];

class AchievementInfo extends React.Component {
	handleHelp() {
		Dialog.open({
			title : '帮助',
			url : Leke.domain.staticServerName + '/pages/help-center/diag/stu-behavior.html'
		});
	}
	render() {
		let {types} = this.props.achievement;
		return (
			<div>
				<p className="tittle">
					<span>成就</span>
					<span className="achievementtips" style={{fontWeight: 'normal'}}><i>*</i> 点亮表示已获得此成就，灰色表示暂未获得此成就 </span>
					<i className="help" onClick={this.handleHelp}></i>
				</p>
				<div className="achievement">
					<ul>
						{Achieves.map((v, i) => {
							let lighten = types.indexOf(v[0]) >= 0 ? '' : 'lighten'
							return <li key={i} className={`${v[1]} ${lighten}`}><i></i>{v[2]}</li>;
						})}
					</ul>
				</div>
			</div>
		);
	}
}


class AttendStatInfo extends React.Component {
	render() {
		var attend = this.props.attendStatInfo;
        const {lessonNum, realNum, normalNum, belateNum, earlyNum, exceptNum, absentNum} = attend;
        let datas = [
            { value : normalNum, name : '全勤' },
            { value : belateNum, name : '迟到' },
            { value : earlyNum, name : '早退' },
            { value : exceptNum, name : '迟到且早退' },
            { value : absentNum, name : '缺勤' }
        ];
        let color = ['#83cf0b', '#619eed', '#70cfff', '#ffd270', '#ff6d6e'];
        let summary = `应上课<span class="green-txt">${lessonNum}</span>堂，`;
		summary += `实上<span class="green-txt">${realNum}</span>堂，`;
		summary += `上课率<span class="green-txt">${toRate(realNum, lessonNum)}%</span>。`;
		return (
            <Section id={this.props.id} title="考勤" help={`${Leke.domain.staticServerName}/pages/help-center/diag/kq-sk.html`}>
				<ReactPieChart datas={datas} summary={summary} color={color} unit="堂" />
			</Section>
		);
	}
}

class ClassInteractInfo extends React.Component {
	render() {
		var bi = this.props.behaviorInfo;
		var attend = this.props.attendStatInfo;
		var lekeVal = this.props.lekeVal;
		return (
			<Section id={this.props.id} title="课堂行为" detail={'person-behavior-interact.htm?cycleId=' + this.props.cycleId}>
				<div className="m-table m-table-center">
					<table>
						<tbody>
							<tr><th>互动行为</th><th>数量</th><th>获得乐币</th></tr>
							<tr><td>金榜题名</td><td>{bi.rank1}/{bi.rank2}/{bi.rank3} {Leke.device == 'hd' ? null : <i className="c-help f-csp" title={`”a/b/c“分别代表获得状元、榜眼、探花的课堂数`}></i>}</td><td>{renderLekeVal(lekeVal, '12302')}</td></tr>
							<tr><td>点到</td><td>{bi.callNum1}/{bi.callNum2}</td><td>{renderLekeVal(lekeVal, '12306')}</td></tr>
							<tr><td>举手</td><td>{bi.raised}</td><td>{renderLekeVal(lekeVal, '12103')}</td></tr>
							<tr><td>被授权</td><td>{bi.authed}</td><td>--</td></tr>
							<tr><td>课堂笔记</td><td>{bi.noteNum}</td><td>{renderLekeVal(lekeVal, '12105')}</td></tr>
							<tr><td>随堂作业</td><td>{bi.examNum1}/{bi.examNum2}</td><td>--</td></tr>
							<tr><td>快速问答</td><td>{bi.quickNum1}/{bi.quickNum2}</td><td>--</td></tr>
							<tr><td>分组讨论</td><td>{bi.discuNum1}/{bi.discuNum2}</td><td>{renderLekeVal(lekeVal, '12307', '12308')}</td></tr>
							<tr><td>献花</td><td>{bi.flowerNum}</td><td>{renderLekeVal(lekeVal, '12107')}</td></tr>
							<tr><td>评价</td><td>{bi.evalNum}/{attend.realNum}</td><td>{renderLekeVal(lekeVal, '12104')}</td></tr>
						</tbody>
					</table>
				</div>
			</Section>
		);
	}
}

class ViewFinishAnalyInfo extends React.Component {
	render() {
		let vfa = this.props.viewFinishAnaly;
		var labels1 = [{name: '课件', max: vfa.totalNum}, {name: '微课', max: vfa.totalNum}, {name: '试卷', max: vfa.totalNum}];
		var labels2 = [{name: '试卷', max: vfa.totalNum}, {name: '课件', max: vfa.totalNum}, {name: '笔记', max: vfa.totalNum}];
		var datas1 = [{name: '应预习', value: vfa.previews1}, {name: '实预习', value: vfa.previews2}];
		var datas2 = [{name: '应复习', value: vfa.reviews1}, {name: '实复习', value: vfa.reviews2}];
		return (
			<div className="classsituation">
				<p className="tittle">预复习情况</p>
				<div className="reportform f-pr">
					<ReactChart style={{height: 360}} option={BehaviorChartConfig.buildGeneralRadar(labels1, datas1)} />
				</div>
				<div className="reportform">
					<ReactChart style={{height: 360}} option={BehaviorChartConfig.buildGeneralRadar(labels2, datas2)} />
				</div>
				<div className="m-table m-table-center f-cb">
					<table>
						<tbody>
							<tr>
								<th>总课堂数</th>
								<th>可预习课堂数</th>
								<th>实预习(比率)</th>
								<th>可复习课堂数</th>
								<th>实复习(比率)</th>
							</tr>
							<tr>
								<td>{vfa.totalNum}</td>
								<td>{vfa.preview1}</td>
								<td>{vfa.preview2}（{toRate(vfa.preview2, vfa.preview1)}%）</td>
								<td>{vfa.review1}</td>
								<td>{vfa.review2}（{toRate(vfa.review2, vfa.review1)}%）</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		);
	}
}

class WorkFinishAnalyInfo extends React.Component {
	render() {
		let {totalNum, normalNum, delayNum, doneBugFixNum, undoBugFixNum} = this.props.workFinishAnaly;
		let unsubmitNum = totalNum - normalNum - delayNum, bugFixNum = doneBugFixNum + undoBugFixNum;
		var title1 = `应交作业：${totalNum}`;
		var datas1 = [{name: '正常提交', value: normalNum}, {name: '延迟提交', value: delayNum}, {name: '未提交', value: unsubmitNum}];
		var title2 = `应订正作业：${bugFixNum}`;
		var datas2 = [{name: '已订正', value: doneBugFixNum}, {name: '未订正', value: undoBugFixNum}];
		return (
			<div className="classsituation">
				<p className="tittle">作业情况</p>
				<div className="reportform">
					<h3 className="title f-tac f-pr">完成情况 <a href={'person-behavior-homework.htm?cycleId=' + this.props.cycleId} target="_blank" className="detailslable">详情<i></i></a></h3>
					<div className="f-pr">
						<ReactChart style={{height: 360}} option={BehaviorChartConfig.buildGeneralPie(title1, datas1)} />
					</div>
				</div>
				<div className="reportform">
					<p className="title f-tac">订正情况</p>
					<div className="f-pr">
						<ReactChart style={{height: 360}} option={BehaviorChartConfig.buildGeneralPie(title2, datas2)} />
					</div>
				</div>
			</div>
		);
	}
}

class OtherBehaviorInfo extends React.Component {
	render() {
		let lekeVal = this.props.lekeVal;
		let {practiceNum, killWrongNum, writeNoteNum, doubtNum} = this.props.otherWiseInfo;
		return (
			<div>
				<p className="tittle">其它学习行为汇总</p>
				<div className="m-table m-table-center">
					<table>
						<tbody>
							<tr>
								<th>项目</th>
								<th>自主练习</th>
								<th>消灭错题</th>
								<th>课外笔记</th>
								<th>提问</th>
							</tr>
							<tr>
								<td>数量</td>
								<td>{practiceNum}题</td>
								<td>{killWrongNum}题</td>
								<td>{writeNoteNum}份</td>
								<td>{doubtNum}次</td>
							</tr>
							<tr>
								<td>获得乐豆</td>
								<td>{renderLekeVal(lekeVal, '11301')}</td>
								<td>{renderLekeVal(lekeVal, '11304')}</td>
								<td>--</td>
								<td>{renderLekeVal(lekeVal, '11302')}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		);
	}
}

class PersonBehavior extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			loading: false,
			cycleId: null,
			view: null
		};
		this.handleChangeQuery = this.handleChangeQuery.bind(this);
	}
	handleChangeQuery(classId, subjectId, cycleId) {
		this.setState({loading: true});
		$.post('person-behavior-data.htm', {
			classId: classId,
			subjectId: subjectId,
			cycleId: cycleId
		}).done(function(json) {
			this.setState({
				loading: false,
				cycleId: cycleId,
				view: json.datas.view
			});
		}.bind(this));
	}
	renderBody() {
		let {cycleId, loading, view} = this.state;
		if (view == null || loading == true) {
			return <div className="m-mask"><div className="con"><i></i><div>数据加载中</div></div></div>;
		} else if (view.success == false) {
			return <div className="m-tips"><i></i><span>{view.message}</span></div>;
		}
		return (
			<div className="c-syn-subj">
				<PersonSummary summaryInfo={view.summaryInfo} userName={Csts.userName} />
				<DiligentInfo view={view} />
				<AchievementInfo achievement={view.achievement} lekeVal={view.lekeVal} />
				<AttendStatInfo attendStatInfo={view.attendStatInfo} />
				<ClassInteractInfo behaviorInfo={view.behaviorInfo} attendStatInfo={view.attendStatInfo} lekeVal={view.lekeVal} cycleId={cycleId} />
				<ViewFinishAnalyInfo viewFinishAnaly={view.viewFinishAnaly} />
				<WorkFinishAnalyInfo workFinishAnaly={view.workFinishAnaly} cycleId={cycleId} />
				<OtherBehaviorInfo otherWiseInfo={view.otherWiseInfo} lekeVal={view.lekeVal} />
			</div>
		);
	}
	render() {
		let {types, zones, mode, view, userName} = Csts;
		return (
			<div className="analysis">
				<ScoreReportQuery types={types} zones={zones} hideZone={true} onChange={this.handleChangeQuery} />
				{this.renderBody()}
			</div>
		);
	}
}

ReactDOM.render(<PersonBehavior />, document.getElementById('app'));
