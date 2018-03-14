var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var {cx} = require('common/react/react-utils');
var ReactChart = require('common/react/ReactChart');
var ReactDataGrid = require('../common/ReactDataGrid');
var KnoGraspTable = require('../common/KnoGraspTable');
var ScoreChartConfig = require('./ScoreChartConfig');
var {toFixed, toRate, toSecond, toLevel, toLevelName} = require('./helper');
var Dialog = require('dialog');

class SummaryInfo extends React.Component {
	render() {
		let {summary, totalScore, userName} = this.props;
		let correctInfo = null;
		if (summary.submitNum > summary.correctNum) {
			correctInfo = (
				<span>
					有<span className="pointed">{summary.submitNum - summary.correctNum}</span>位学生作业未批改，
					建议批改完全后，再查看报告更准确；
				</span>
			);
		}
		return (
			<div className="summary">
				<p>亲爱的<span className="colored">{userName}</span>老师：</p>
				<p className="details">
					您好，本次作业应交<span className="colored">{summary.totalNum}</span>人，
					实交<span className="colored">{summary.submitNum}</span>人，
					上交率<span className="colored">{toRate(summary.submitNum, summary.totalNum, 1)}%</span>；
					{correctInfo}
					作业总分<span className="colored">{toFixed(totalScore)}</span>分，
					班级平均分<span className="colored">{toFixed(summary.avgScore, 1)}分</span>，
					最高分<span className="colored">{toFixed(summary.maxScore, 1)}分</span>，
					最低分<span className="pointed">{toFixed(summary.minScore, 1)}分</span>，
					平均用时<span className="colored">{toSecond(summary.avgUsedTime)}分钟</span>，
					班级整体表现<span className="colored">{toLevelName(toRate(summary.avgScore, totalScore, 1))}</span>。
				</p>
			</div>
		);
	}
}

class FinishInfo extends React.Component {
	buildScoreBar() {
		var summary = this.props.summary;
		let totalScore = this.props.totalScore;
		return {
			color : ['#5b9bd5', '#8bc036', '#ffbe54'],
			legend : {
				selectedMode: false,
				data : ['最高分', '最低分']
			},
			tooltip : {
				trigger : 'axis',
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
				max: totalScore,
				splitNumber : 10,
				axisLabel : {
					formatter : '{value} 分'
				}
			}],
			series : [{
				name : '平均分',
				type : 'bar',
				barWidth: 60,
				data : [toFixed(summary.avgScore, 1)]
			}, {
				name : '最高分',
				type : 'line',
				data : [toFixed(summary.maxScore, 1)],
				symbolSize : 10,
				markPoint : {
					silent : true,
					data : [{
						type : "max",
						name : "最高分"
					}]
				}
			}, {
				name : '最低分',
				type : 'line',
				data : [toFixed(summary.minScore, 1)],
				symbolSize : 10,
				markPoint : {
					silent : true,
					data : [{
						type : "min",
						name : "最低分"
					}]
				}
			}]
		};
	}
	buildUsedTimeBar() {
		var summary = this.props.summary;
		let usedTime = toSecond(summary.avgUsedTime);
		let maxValue = toFixed(usedTime * 1.2, 1);
		return {
			color : ['#33c8af', '#ffbe54'],
			tooltip : {
				trigger : 'axis',
				formatter : '{a}{c}分钟',
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
				max: maxValue,
				splitNumber : 10,
				axisLabel : {
					formatter : '{value} 分钟'
				}
			}],
			series : [{
				name : '平均用时',
				type : 'bar',
				barWidth: 60,
				data : [usedTime]
			}]
		};
	}
	render() {
		var {submitNum, delayNum, totalNum} = this.props.summary;
		var normalNum = submitNum - delayNum;
		var undoneNum = totalNum - submitNum;
		return (
			<div className="donework">
				<span className="tittle"></span>
				<div className="maps">
					<div className="mapcontainer">
						<ReactChart className="mapitem left3" option={this.buildScoreBar()} />
						<ReactChart className="mapitem center3" option={this.buildUsedTimeBar()} />
						<ReactChart className="mapitem right3" option={ScoreChartConfig.buildSubmitPie(normalNum, delayNum, undoneNum)} />
					</div>
				</div>
			</div>
		);
	}
}

class ScoreStatInfo extends React.Component {
	buildScoreBar() {
		let totalScore = this.props.totalScore;
		let scoreRanks = this.props.scoreRanks;
		let labels = [], values = [];
		for (var i = 0; i < 10; i++) {
			let minRate = 10 * i, maxRate = minRate + 10;
			let min = toFixed(totalScore * minRate / 100, 0);
			let max = toFixed(totalScore * maxRate / 100, 0);
			labels.push(`${min}~${max}`);
			values.push(0);
		}
		scoreRanks.filter(v => v.level <= 5).forEach(rank => {
			let idx = parseInt(rank.scoreRate * 10);
			if (idx >= 10) {
				idx = 9;
			}
			values[idx]++;
		});
		return ScoreChartConfig.buildScoreSectionBar(labels, values);
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
			<div className="gradedistribution">
				<span className="tittle"></span>
				<div className="maps">
					<div className="mapcontainer">
						<ReactChart className="mapitem left1third" option={ScoreChartConfig.buildScorePie(vs[0], vs[1], vs[2], vs[3], vs[4])} />
						<ReactChart className="mapitem right2thirds" option={this.buildScoreBar()} />
					</div>
					<div className="tips">等级计算方法： 满分100分作业，85分以上为优秀，[70-85)分为良好，[60-70)分为及格，[45-60)分为较差，45分以下为危险，满分不为100分各等级按相应比例折算。</div>
				</div>
			</div>
		);
	}
}

class QueScoreInfo extends React.Component {
	buildTotal = (datas) => {
		let length = 0, totalScore = 0, classScore = 0;
		datas.forEach(item => {
			length += item.qids.length;
			totalScore += item.totalScore;
			classScore += item.classScore;
		});
		let classRate = toRate(classScore, totalScore, 1);
		return {id: -1, name: '全部', length, totalScore, classScore, classRate};
	}
	render() {
		let datas = this.props.queGroups;
		datas.forEach(data => data.classRate = toRate(data.classScore, data.totalScore, 1));
		let total = this.buildTotal(datas);
		let columns = [
			{title: '大题', width: '40%', field: 'name'},
			{title: '总题数', width: '15%', field: 'length', render: (value, item, index) => value || item.qids.length},
			{title: '总分数', width: '15%', field: 'totalScore', render: (value, item, index) => toFixed(value, 1)},
			{title: '平均得分', width: '15%', field: 'classScore', render: (value, item, index) => toFixed(value, 1)},
			{title: '得分率', width: '15%', field: 'classRate', render: (value, item, index) => toFixed(value, 1) + '%', onSort: true}
		];
		return (
			<div className="mainquestion">
				<span className="tittle"></span>
				<ReactDataGrid columns={columns} datas={datas} total={total} />
				<a href={`../../homework/analysis2.htm?homeworkId=${ReportCst.homeworkId}`} target="_blank" className="check-rate">查看试题得分率 &gt;&gt;</a>
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
			item.classRate = toFixed(item.classScore * 100 / item.totalScore, 1);
			if (item.classRate >= 85) {
				item.level = 1;
			} else if (item.classRate >= 50) {
				item.level = 2;
			} else {
				item.level = 3;
			}
		});
	}
	render() {
		let {knoGroups, classId, subjectId} = this.props;
		if (!(knoGroups && knoGroups.length)) {
			// 没有知识点分析，不做处理直接返回
			return null;
		}
		let knoRates = this.resolveKnoRates(knoGroups);
		let columns = 'name,totalNum,totalScore,klassRate,level';
		return (
			<div className="knowledgepoint">
				<span className="tittle"></span>
				<i className="help" onClick={this.handleHelpClick}></i>
				<KnoGraspTable knoRates={knoGroups} columns={columns} increase={ReportCst.device != 'hd' && Leke.user.currentRoleId == 101} classId={classId} subjectId={subjectId} />
			</div>
		);
	}
}

class ClassRankInfo extends React.Component {
	buildLevel(list, name, level) {
		let homeworkId = this.props.homeworkId;
		list = list.filter(v => v.level == level);
		return list.map((rank, i) => {
			return (
				<tr>
					{i == 0 ? <td className={`rankclass-${level}`} rowSpan={list.length}>{name}({list.length}人)</td> : null}
					<td>{rank.index}</td>
					<td className="operation">
					{level <= 5 ? <a href={`./detail/${homeworkId}-${rank.userId}.htm`} target="_blank">{rank.userName}</a> : `${rank.userName}`}
					</td>
					<td>{toFixed(rank.score, 1)}{level == 6 ? '+' : ''}{level == 7 ? '--' : ''}</td>
				</tr>
			);
		});
	}
	render() {
		let that = this;
		let list = this.props.scoreRanks;
		let homeworkId = this.props.homeworkId;
		var prev = 10000, index = 0;
		for (var i = 0; i < list.length; i++) {
			var rank = list[i];
			if (rank.level <= 5) {
				if (rank.score < prev) {
					index++;
					prev = rank.score;
				}
				rank.index = index;
			} else {
				rank.index = '--';
			}
		}
		let names = ['优秀', '良好', '及格', '较差', '危险', '未批改', '未提交'];
		var exportBtn = (
			<div style={{textAlign: 'right'}}>
				<a href={`export.htm?homeworkId=${homeworkId}`} className="u-btn u-btn-nm u-btn-bg-turquoise" target="_blank">导出</a>
			</div>
		);
		return (
			<div className="classrank">
				<span className="tittle"></span>
				{ReportCst.device != 'hd' ? exportBtn : null}
				<div className="m-table m-table-center">
					<table>
						<thead>
							<tr>
								<th style={{width: '15%'}}>层级</th>
								<th style={{width: '25%'}}>排名</th>
								<th style={{width: '30%'}}>姓名</th>
								<th style={{width: '30%'}}>分数</th>
							</tr>
						</thead>
						<tbody>
							{names.map((name, i) => that.buildLevel(list, name, i + 1))}
						</tbody>
					</table>
				</div>
				<span className="tips">注：点击学生姓名可查看该生学习分析报告</span>
			</div>
		);
	}
}

class HomeworkAnalysis extends React.Component {
	render() {
		let {homeworkId, classId, subjectId} = ReportCst;
		let {summary, totalScore, scoreRanks, queGroups, knoGroups} = ReportCst.view;
		let {homeworkName, scoreRank} = summary;
		return (
			<div className="analysis">
				<div className="c-singlepage">
					<div className="header">
						<h1><span className="colored">《{homeworkName}》</span>作业分析报告</h1>
					</div>
					<SummaryInfo summary={summary} totalScore={totalScore} userName={ReportCst.userName} />
					<FinishInfo summary={summary} totalScore={totalScore} />
					<ScoreStatInfo scoreRanks={scoreRanks} totalScore={totalScore} />
					<QueScoreInfo queGroups={queGroups} />
					<KnoScoreInfo knoGroups={knoGroups} classId={classId} subjectId={subjectId} />
					<ClassRankInfo scoreRanks={scoreRanks} homeworkId={homeworkId} />
				</div>
			</div>
		);
	}
}

ReactDOM.render(<HomeworkAnalysis />, document.getElementById('container'));
