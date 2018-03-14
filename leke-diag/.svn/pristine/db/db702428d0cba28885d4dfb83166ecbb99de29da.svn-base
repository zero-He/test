var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var {cx} = require('common/react/react-utils');
var ReactChart = require('common/react/ReactChart');
var ReactDataGrid = require('../common/ReactDataGrid');
var KnoGraspTable = require('../common/KnoGraspTable');
var MicroView = require('../common/MicroView');
var ScoreChartConfig = require('./ScoreChartConfig');
var {toFixed, toRate, toSecond, toLevel, toLevelName} = require('./helper');
var Dialog = require('dialog');

class SummaryInfo extends React.Component {
	render() {
		let {summary, userName, totalScore, myself} = this.props;
		let {totalNum, selfUsedTime, avgUsedTime, submitRank, selfScore, scoreRank} = summary;
		totalScore = toFixed(totalScore);
		var usedTimeComps = ['短于', '等于', '长于'];
		var usedTimeIdx = 1;
		if (selfUsedTime > avgUsedTime) {
			usedTimeIdx = 2;
		} else if (selfUsedTime < avgUsedTime) {
			usedTimeIdx = 0;
		}
		if (myself) {
			return (
				<div className="summary">
					<p>亲爱的<span className="colored">{userName}</span>同学：</p>
					<p className="details">
						您好，本次作业共有<span className="colored">{totalNum}</span>位同学参加，
						您的作业用时<span className="colored">{toSecond(selfUsedTime)}</span>分钟，
						<span className="colored">{usedTimeComps[usedTimeIdx]}</span>班级平均用时，
						您第<span className="colored">{submitRank}</span>个提交，
						早于<span className="colored">{toRate(totalNum - submitRank, totalNum, 1)}</span>%的同学，
						得分<span className="colored">{toFixed(selfScore, 1)}</span>分（满分{toFixed(totalScore)}分），
						排名第<span className="colored">{scoreRank}</span>，
						表现<span className="colored">{toLevelName(toRate(selfScore, totalScore, 1))}</span>，
						领先全班<span className="colored">{toRate(totalNum - scoreRank, totalNum, 1)}</span>%的同学；
						请努力学习，争取更上一层楼。
					</p>
				</div>
			);
		} else {
			return (
				<div className="summary">
					<p className="details">
						<span className="colored">{userName}</span>同学作业用时<span className="colored">{toSecond(selfUsedTime)}</span>分钟，
						<span className="colored">{usedTimeComps[usedTimeIdx]}</span>班级平均用时，
						第<span className="colored">{submitRank}</span>个提交，
						早于<span className="colored">{toRate(totalNum - submitRank, totalNum, 1)}</span>%的同学，
						得分<span className="colored">{toFixed(selfScore, 1)}</span>分（满分{toFixed(totalScore)}分），
						排名第<span className="colored">{scoreRank}</span>，
						表现<span className="colored">{toLevelName(toRate(selfScore, totalScore, 1))}</span>，
						领先全班<span className="colored">{toRate(totalNum - scoreRank, totalNum, 1)}</span>%的同学；
					</p>
				</div>
			);
		}
	}
}

class FinishInfo extends React.Component {
	buildScoreBar() {
		var {selfScore, maxScore, avgScore, scoreRank} = this.props.summary;
		return {
			color : ['#5b9bd5', '#8bc036', '#ffbe54'],
			legend : {
				selectedMode: false,
				data : ['班级最高分', '班级平均分']
			},
			tooltip : {
				trigger : 'axis',
				formatter : `{a0}：{c0}<br />我的排名：${scoreRank}<br />{a1}：{c1}<br />{a2}：{c2}`,
				axisPointer : {
					type : 'shadow'
				}
			},
			grid : {
				top : 60,
				left: 60,
				right: 60,
				bottom : 0,
				containLabel : true
			},
			xAxis : [{
				type : 'category',
				data : ['']
			}],
			yAxis : [{
				type : 'value',
				splitNumber : 10,
				axisLabel : {
					formatter : '{value} 分'
				}
			}],
			series : [{
				name : '我的得分',
				type : 'bar',
				barWidth: 60,
				data : [toFixed(selfScore, 1)]
			}, {
				name : '班级最高分',
				type : 'line',
				data : [toFixed(maxScore, 1)],
				symbolSize : 10,
				markPoint : {
					silent : true,
					data : [{
						type : "max",
						name : "班级最高分"
					}]
				}
			}, {
				name : '班级平均分',
				type : 'line',
				data : [toFixed(avgScore, 1)],
				symbolSize : 10,
				markPoint : {
					silent : true,
					data : [{
						type : "min",
						name : "班级平均分"
					}]
				}
			}]
		};
	}
	buildUsedTimeBar() {
		let {avgUsedTime, selfUsedTime} = this.props.summary;
		avgUsedTime = toSecond(avgUsedTime);
		selfUsedTime = toSecond(selfUsedTime);
		let maxValue = Math.max(avgUsedTime, selfUsedTime);
		maxValue = toFixed(maxValue * 1.2, 1);
		return {
			color : [avgUsedTime < selfUsedTime ? '#d0d0d0' : '#33c8af', '#ffbe54'],
			tooltip : {
				trigger : 'axis',
				formatter : '{a0}：{c0}分钟<br />{a1}：{c1}分钟',
				axisPointer : {
					type : 'shadow'
				}
			},
			legend : {
				selectedMode: false,
				data : ['班级平均用时']
			},
			grid : {
				top : 60,
				left: 60,
				right: 60,
				bottom : 0,
				containLabel : true
			},
			xAxis : [{
				type : 'category',
				data : ['']
			}],
			yAxis : [{
				type : 'value',
				max: maxValue,
				splitNumber : 10,
				axisLabel : {
					formatter : '{value} 分钟'
				}
			}],
			series : [{
				name : '我的用时',
				type : 'bar',
				barWidth: 60,
				data : [selfUsedTime]
			}, {
				name : '班级平均用时',
				type : 'line',
				data : [avgUsedTime],
				symbolSize : 10,
				markPoint : {
					silent : true,
					data : [{
						type : "max",
						name : "班级平均用时"
					}]
				}
			}]
		};
	}
	render() {
		let scoreRanks = this.props.scoreRanks;
		let vs = [0, 0, 0, 0, 0];
		scoreRanks.filter(v => v.level <= 5).forEach(scoreRank => {
			var rate = toFixed(scoreRank.scoreRate * 100, 1);
			var level = toLevel(rate);
			vs[level - 1]++;
		});
		return (
			<div className="donework">
				<span className="tittle"></span>
				<div className="maps">
					<div className="mapcontainer">
						<ReactChart className="mapitem left3" option={this.buildScoreBar()} />
						<ReactChart className="mapitem center3" option={this.buildUsedTimeBar()} />
						<ReactChart className="mapitem right3" option={ScoreChartConfig.buildScorePie(vs[0], vs[1], vs[2], vs[3], vs[4], '人')} />
					</div>
					<div className="tips">等级计算方法： 满分100分作业，85分以上为优秀，[70-85)分为良好，[60-70)分为及格，[45-60)分为较差，45分以下为危险，满分不为100分各等级按相应比例折算。</div>
				</div>
			</div>
		);
	}
}

class QueScoreInfo extends React.Component {
	buildTotal = (datas) => {
		let length = 0, rightNum = 0, totalScore = 0, classScore = 0, selfScore = 0;
		datas.forEach(item => {
			length += item.qids.length;
			rightNum += item.rightNum;
			totalScore += item.totalScore;
			classScore += item.classScore;
			selfScore += item.selfScore;
		});
		let selfRate = toRate(selfScore, totalScore, 1), classRate = toRate(classScore, totalScore, 1);
		return {id: -1, name: '全部', length, rightNum, totalScore, selfScore, classScore, selfRate, classRate};
	}
	render() {
		let datas = this.props.queGroups;
		datas.forEach(data => {
			data.selfRate = toRate(data.selfScore, data.totalScore, 1);
			data.classRate = toRate(data.classScore, data.totalScore, 1);
		});
		let total = this.buildTotal(datas);
		let columns = [
			{title: '大题', width: '38%', field: 'name'},
			{title: '题数', width: '10%', field: 'length', render: (value, item, index) => value || item.qids.length},
			{title: '分值', width: '10%', field: 'totalScore', render: (value, item, index) => toFixed(value, 1)},
			{title: '正确题数', width: '10%', field: 'rightNum', render: (value, item, index) => toFixed(value, 1)},
			{title: '得分', width: '10%', field: 'selfScore', render: (value, item, index) => toFixed(value, 1)},
			{title: '得分率', width: '10%', field: 'selfRate', render: (value, item, index) => toFixed(value, 1) + '%', onSort: true},
			{title: '班级得分率', width: '12%', field: 'classRate', render: (value, item, index) => toFixed(value, 1) + '%', onSort: true}
		];
		return (
			<div className="mainquestion">
				<span className="tittle"></span>
				<ReactDataGrid columns={columns} datas={datas} total={total} />
				<a href={`../../../homework/analysis2.htm?homeworkId=${ReportCst.homeworkId}&studentId=${ReportCst.userId}`} target="_blank" className="check-rate">查看试题得分率 &gt;&gt;</a>
			</div>
		);
	}
}

class KnoScoreInfo extends React.Component {
	handleHelpClick = () => {
		Dialog.open({
			title : '帮助',
			url : `${Leke.domain.staticServerName}/pages/help-center/diag/zw-cd.html`
		});
	}
	resolveKnoRates(knoGroups) {
		knoGroups.forEach(item => {
			item.totalNum = item.qids.length;
			item.selfRate = toRate(item.selfScore, item.totalScore, 1)
			item.classRate = toRate(item.classScore, item.totalScore, 1);
			if (item.selfRate >= 85) {
				item.level = 1;
			} else if (item.selfRate >= 50) {
				item.level = 2;
			} else {
				item.level = 3;
			}
		});
	}
	render() {
		let {knoGroups, myself} = this.props;
		if (!(knoGroups && knoGroups.length)) {
			// 没有知识点分析，不做处理直接返回
			return null;
		}
		let knoRates = this.resolveKnoRates(knoGroups);
		let columns = 'name,totalNum,rightNum,totalScore,selfScore,selfRate,klassRate,level';
		columns += myself && ReportCst.device != 'hd' ? ',wrong,exercise' : '';
		return (
			<div className="knowledgepoint">
				<span className="tittle"></span>
				<i className="help" onClick={this.handleHelpClick}></i>
				<KnoGraspTable knoRates={knoGroups} columns={columns} />
			</div>
		);
	}
}

class CourseRecommend extends React.Component {
	render() {
		if (ReportCst.device == 'hd') {
			return null;
		}
		let datas = this.props.datas;
		if (datas == null || datas == undefined || datas.length == 0) {
			return null;
		}
		return (
			<div className="courserecommend">
				<span className="tittle"></span>
				<MicroView datas={datas} />
			</div>
		);
	}
}

class HomeworkAnalysis extends React.Component {
	render() {
		var view = ReportCst.view;
		let {homeworkName, scoreRank} = view.summary;
		return (
			<div className="analysis">
				<div className="c-singlepage">
					<div className="header">
						<h1>{ReportCst.myself ? '' : ReportCst.userName}<span className="colored">《{homeworkName}》</span>作业分析报告</h1>
						{scoreRank <= 3 ? (<span className={`rankpos rankpos-${scoreRank}`}></span>) : null}
					</div>
					<SummaryInfo summary={view.summary} totalScore={view.totalScore} userName={ReportCst.userName} myself={ReportCst.myself} />
					<FinishInfo summary={view.summary} scoreRanks={view.scoreRanks} />
					<QueScoreInfo queGroups={view.queGroups} />
					<KnoScoreInfo knoGroups={view.knoGroups} myself={ReportCst.myself} />
					<CourseRecommend datas={view.micros} />
				</div>
			</div>
		);
	}
}

ReactDOM.render(<HomeworkAnalysis />, document.getElementById('container'));
