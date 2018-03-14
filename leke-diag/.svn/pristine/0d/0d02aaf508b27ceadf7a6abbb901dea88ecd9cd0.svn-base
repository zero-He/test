define(function(require, exports, module){
var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var $ = require('jquery');
var JSON = require('json');
var Utils = require('utils');
var {cx} = require('common/react/react-utils');
var ReactChart = require('common/react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');
var KnoGraspTable = require('./KnoGraspTable');
var {ScoreReportQuery, ScoreTrendChart} = require('./ScoreComponents');
var {toFixed, toRate, toLevel, toLevelName} = require('./helper');

class ClassSummary extends React.Component {
	renderExplain(summary) {
		let {avgScore, maxScore, minScore, totalNum, otherNum, levelA, levelB, levelC, levelD, levelE} = this.props.summary;
		let total = totalNum - otherNum;
		return (
			<p className="details">
				您好，在此期间，本班级的学科平均分为<span className="colored">{toFixed(avgScore, 1)}</span>分，
				最高分<span className="colored">{toFixed(maxScore, 1)}</span>分，
				最低分<span className="pointed">{toFixed(minScore, 1)}</span>分，
				班级整体表现<span className="pointed">{toLevelName(avgScore)}</span>；
				本班共有学生<span className="colored">{totalNum}</span>人，
				其中成绩优秀<span className="colored">{levelA}</span>人，
				占比<span className="colored">{toRate(levelA, total, 1)}%</span>，
				良好<span className="colored">{levelB}</span>人，
				占比<span className="colored">{toRate(levelB, total, 1)}%</span>，
				及格<span className="colored">{levelC}</span>人，
				占比<span className="colored">{toRate(levelC, total, 1)}%</span>，
				较差<span className="pointed">{levelD}</span>人，
				占比<span className="pointed">{toRate(levelD, total, 1)}%</span>，
				危险<span className="pointed">{levelE}</span>人，
				占比<span className="pointed">{toRate(levelE, total, 1)}%</span>。
			</p>
		);
	}
	render() {
		var {levelA, levelB, levelC, levelD, levelE, avgScore, maxScore, minScore} = this.props.summary;
		return (
			<div className="summary">
				<p className="tittle">成绩分析</p>
				<p>敬爱的<span className="colored">{Leke.user.currentUserName}</span>老师：</p>
				{this.renderExplain()}
				<div className="maps">
					<div className="mapcontainer">
						<ReactChart className="mapitem left2" option={ScoreChartConfig.buildClassScoreBar(avgScore, maxScore, minScore)} />
						<ReactChart className="mapitem right2" option={ScoreChartConfig.buildScorePie(levelA, levelB, levelC, levelD, levelE)} />
					</div>
				</div>
			</div>
		);
	}
}

class ClassRankInfo extends React.Component {
	buildLevel(name, list, level) {
		let scope = this.props.scope;
		list = list.filter(v => v.level == level);
		return list.map((rank, i) => {
			return (
				<tr>
					{i == 0 ? <td className={`rankclass-${level}`} rowSpan={list.length}>{name}({list.length}人)</td> : null}
					<td>{rank.index}</td>
					<td className="operation">
					{level <= 5 ? <a href={`./klass-score-detail/${scope}-${rank.userId}.htm`} target="_blank">{rank.userName}</a> : <span>{rank.userName}</span>}
					</td>
					<td>{rank.score != null ? toFixed(rank.score, 1) : '--'}</td>
				</tr>
			);
		});
	}
	render() {
		let that = this;
		let levelNames = ['优秀', '良好', '及格', '较差', '危险', '无成绩'];
		let list = this.props.scoreRanks;
		list.forEach((v, i) => {
			v.score = toFixed(v.score, 1);
			v.level = toLevel(v.score);
		});
		var prev = 10000, index = 0;
		for (var i = 0; i < list.length; i++) {
			var rank = list[i];
			if (rank.score != null) {
				if (rank.score < prev) {
					index++;
					prev = rank.score;
				}
				rank.index = index;
			} else {
				rank.index = '--';
			}
		}
		return (
			<div className="classrank">
				<p className="tittle">班级成绩排行</p>
				<div className="m-table m-table-center">
					<table>
						<thead>
							<tr>
								<th style={{width: 150}}>层级</th>
								<th>排名</th>
								<th>姓名</th>
								<th>分数</th>
							</tr>
						</thead>
						<tbody>
							{levelNames.map((name, i) => that.buildLevel(name, list, i + 1))}
						</tbody>
					</table>
				</div>
				<span className="tips">注：点击学生姓名可查看该生学习分析报告</span>
			</div>
		);
	}
}

class ScoreTrendInfo extends React.Component {
	render() {
		let trends = this.props.trends;
		if (!(trends && trends.length)) {
			return null;
		}
		return (
			<div className="syn-trend">
				<p className="tittle">班级成长走势</p>
				<ScoreTrendChart trends={this.props.trends} />
			</div>
		);
	}
}

class KnoGraspInfo extends React.Component {
	markLevels(knoRates) {
		knoRates.forEach(item => {
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
		let {knoRates, classId, subjectId} = this.props;
		if (!(knoRates && knoRates.length)) {
			// 没有知识点分析，不做处理直接返回
			return null;
		}
		this.markLevels(knoRates);
		return (
			<div className="knowledgepoint">
				<p className="tittle">知识点掌握程度</p>
				<KnoGraspTable knoRates={knoRates} columns="name,klassRate,level" increase={ReportCst.device != 'hd'} classId={classId} subjectId={subjectId} />
			</div>
		);
	}
}

class ClassAnalysis extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			loading: false,
			scope: null,
			view: null
		};
		this.handleChangeQuery = this.handleChangeQuery.bind(this);
	}
	handleChangeQuery(classId, subjectId, cycleId) {
		this.setState({loading: true});
		$.post('klass-score-data.htm', {
			classId: classId,
			subjectId: subjectId,
			cycleId: cycleId
		}).done(function(json) {
			this.setState({
				loading: false,
				scope: [classId, subjectId, cycleId].join('-'),
				view: json.datas.view,
				classId: classId,
				subjectId: subjectId
			});
		}.bind(this));
	}
	renderBody() {
		let {loading, view, classId, subjectId} = this.state;
		if (view == null || loading == true) {
			return <div className="m-mask"><div className="con"><i></i><div>数据加载中</div></div></div>;
		} else if (view.success == false) {
			return <div className="m-tips"><i></i><span>{view.message}</span></div>;
		}
		return (
			<div className="c-syn-subj">
				<ClassSummary summary={view.summary} />
				<ScoreTrendInfo trends={view.trends} />
				<ClassRankInfo scoreRanks={view.scoreRanks} scope={this.state.scope} />
				<KnoGraspInfo knoRates={view.knoRates} classId={classId} subjectId={subjectId} />
			</div>
		);
	}
	render() {
		let {types, zones} = ReportCst;
		if (!(zones && zones.length)) {
			return <div className="m-tips"><i></i><span>您暂无关联班级，无数据可供分析！</span></div>;
		}
		let display = this.state.loading ? 'block' : 'none';
		return (
			<div className="analysis">
				<ScoreReportQuery types={types} zones={zones} onChange={this.handleChangeQuery} />
				{this.renderBody()}
				<div className="m-mask" style={{display: display}}><div className="con"><i></i><div>数据加载中</div></div></div>
			</div>
		);
	}
}

ReactDOM.render(<ClassAnalysis />, document.getElementById('container'));
});
