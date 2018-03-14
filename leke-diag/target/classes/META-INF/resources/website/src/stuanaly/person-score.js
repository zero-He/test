var React = require('common/react/react');
var ReactDOM = require('common/react/react-dom');
var $ = require('jquery');
var {cx} = require('common/react/react-utils');
var ReactChart = require('common/react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');
var KnoGraspTable = require('../common/KnoGraspTable');
var MicroView = require('../common/MicroView');
var {ScoreReportQuery, ScoreTrendChart} = require('./ScoreComponents');
var {toFixed, toRate, toLevel, levelNames, toLevelName} = require('./helper');
var {toFixed, toRate} = require('../utils/number');
var Section = require('../common/Section');

class PersonSummary extends React.Component {
	renderExplain(summary, userName, myself) {
		let {totalNum, submitNum, delayNum, correctNum, levelA, levelB, levelC, levelD, levelE, queNum, errNum} = summary;
		return (
			<p className="details">
				在此期间内，{myself ? '您' : <span className="colored">{userName}同学</span>}应交作业<span className="colored">{totalNum}</span>份，
				正常提交作业<span className="colored">{submitNum - delayNum}</span>份，占比<span className="colored">{toRate(submitNum - delayNum, totalNum, 1)}%</span>，
				延交作业<span className="pointed">{delayNum}</span>份，占比<span className="pointed">{toRate(delayNum, totalNum, 1)}%</span>，
				未交作业<span className="pointed">{totalNum - submitNum}</span>份，占比<span className="pointed">{toRate(totalNum - submitNum, totalNum, 1)}%</span>；
				已批改作业<span className="colored">{correctNum}</span>份，
				其中成绩优秀<span className="colored">{levelA}</span>份，占比<span className="colored">{toRate(levelA, correctNum, 1)}%</span>，
				良好<span className="colored">{levelB}</span>份，占比<span className="colored">{toRate(levelB, correctNum, 1)}%</span>，
				及格<span className="colored">{levelC}</span>份，占比<span className="colored">{toRate(levelC, correctNum, 1)}%</span>，
				较差<span className="pointed">{levelD}</span>份，占比<span className="pointed">{toRate(levelD, correctNum, 1)}%</span>，
				危险<span className="pointed">{levelE}</span>份，占比<span className="pointed">{toRate(levelE, correctNum, 1)}%</span>；
				共做题<span className="colored">{queNum}</span>道，错题<span className="pointed">{toFixed(errNum, 1)}</span>道，
				得分率<span className="colored">{toRate(queNum - errNum, queNum, 1)}%</span>。
			</p>
		);
	}
	render() {
		var {summary, userName, myself} = this.props;
		let {submitNum, delayNum, totalNum, levelA, levelB, levelC, levelD, levelE} = summary;
		return (
			<div className="summary">
				<p className="tittle">成绩分析</p>
				{myself ? (<p>亲爱的<span className="colored">{userName}</span>同学：</p>) : null}
				{this.renderExplain(summary, userName, myself)}
				<div className="maps">
					<div className="mapcontainer">
						<ReactChart className="mapitem left2" option={ScoreChartConfig.buildSubmitPie(submitNum - delayNum, delayNum, totalNum - submitNum, '份')} />
						<ReactChart className="mapitem right2" option={ScoreChartConfig.buildScorePie(levelA, levelB, levelC, levelD, levelE, '份')} />
					</div>
				</div>
			</div>
		);
	}
}

class OverallInfo extends React.Component {
	render() {
		let {overall, subjScores, isUnit} = this.props;
		if (overall == null) {
			return null;
		}
		let {score, rank, total, maxes1, maxes2, mines1, mines2} = overall;
		let rate = toRate(total - rank, total, 1);
		let names = subjScores.map(subj => subj.label);
		let selfs = subjScores.map(subj => subj.self);
		let klasses = subjScores.map(subj => subj.klass);
		return (
			<div className="subject-ana">
				<p className="tittle">学科优劣势分析</p>
				<div className="maps">
					<div className="mapcontainer">
						<ReactChart className="mapitem left2" option={ScoreChartConfig.buildScoreRadar(names, selfs, isUnit ? null : klasses)} />
						<div className="mapitem right2">
							<ReactChart className="inner" option={ScoreChartConfig.buildOverallScore(score, isUnit ? null : rate)} />
							<p>
								您的全科综合得分为<span className="colored">{toFixed(score, 1)}分</span>，表现<span className="colored">{toLevelName(score)}</span>；
								{isUnit ? null : (<span>班级排名第<span className="colored">{rank}</span>，领先全班<span className="colored">{rate}%</span>的同学；</span>)}
							</p>
							<p>
								相比自己综合得分短板学科为<span className="pointed">{mines1}</span>，长板学科为<span className="colored">{maxes1}</span>；
								{isUnit ? null : (<span>相比班级各科得分劣势学科为<span className="pointed">{mines2}</span>，相对优势学科为<span className="colored">{maxes2}</span>；</span>)}
								{isUnit ? (<span>请加强短板学科的学习训练，弥补不足，齐头并进。</span>) : (<span>请加强短板、劣势学科的学习训练，弥补不足，齐头并进。</span>)}
							</p>
						</div>
					</div>
				</div>
			</div>
		);
	}
}

class ScoreCompared extends React.Component {
	render() {
		let {subjScores, rptType} = this.props;
		if (subjScores == null) {
			return null;
		}
		let fixed = 0, rise = 0, down = 0;
		let remark2 = [];
		let labels = ScoreChartConfig.Labels.cycles[rptType];
		subjScores.forEach(subj => {
			if (subj.self && subj.prev) {
				if (subj.self - 3 > subj.prev) {
					rise++;
				} else if (subj.self + 3 < subj.prev) {
					down++;
				} else {
					fixed++;
				}
				let currLevel = toLevel(subj.self);
				let prevLevel = toLevel(subj.prev);
				if (currLevel <= prevLevel - 2) {
					remark2.push(<span>{subj.label}<span className="colored">上升劲猛</span>，</span>);
					remark2.push(<span>从{levelNames[prevLevel - 1]}升上{levelNames[currLevel - 1]}；</span>);
				} else if (currLevel >= prevLevel + 2) {
					remark2.push(<span>{subj.label}<span className="pointed">下降严重</span>，</span>);
					remark2.push(<span>从{levelNames[prevLevel - 1]}跌入{levelNames[currLevel - 1]}；</span>);
				}
			}
		});
		if (remark2.length > 0) {
			remark2.unshift(<span>2、相较于{labels[0]}，{labels[1]}</span>)
		}
		let names = subjScores.map(subj => subj.label);
		let currs = subjScores.map(subj => subj.self != null ? subj.self : '-');
		let prevs = subjScores.map(subj => subj.prev != null ? subj.prev : '-');
		return (
			<div className="subject-compare">
				<p className="tittle">学科成绩对比分析</p>
				<div className="maps">
					<ReactChart className="single" option={ScoreChartConfig.buildScoreCompBar(rptType, names, currs, prevs)} />
					<p>1、相较于{labels[0]}，{labels[1]}有<span className="pointed">{down}门</span>功课呈<span className="pointed">下降趋势</span>；
					<span className="colored">{rise}门</span>呈<span className="colored">上升趋势</span>；{fixed}门功课持平</p>
					<p>{remark2}</p>
				</div>
			</div>
		);
	}
}

class ScoreTrendInfo extends React.Component {
	render() {
		let {trends, subjId} = this.props;
		if (!(trends && trends.length)) {
			return null;
		}
		return (
			<div className="syn-trend">
				<p className="tittle">{subjId > 0 ? '学科成长走势' : '综合成绩走势分析'}</p>
				<ScoreTrendChart trends={this.props.trends} />
			</div>
		);
	}
}

class ComboScoreRank extends React.Component {
	markRankIndex(scoreRanks, subjId) {
	    let prev = 10000, index = 0;
	    for (var i = 0; i < scoreRanks.length; i++) {
	        let score = scoreRanks[i][subjId];
            if (score < prev) {
                index++;
                prev = score;
            }
            scoreRanks[i].index = index;
	    }
	}
	renderHead(subjs, subjId) {
		if (subjId > 0) {
			let subj = subjs.filter(v => v.id == subjId)[0];
			return (
				<tr>
					<th key="index">名次</th>
					<th key="userName">姓名</th>
					<th key="score">{subj.name}成绩</th>
				</tr>
			);
		}
		return (
			<tr>
				<th key="index">名次</th>
				<th key="userName">姓名</th>
				<th key="0" className={cx({chosen: subjId == 0})}>综合</th>
				{subjs.map(subj => {
					return <th key={subj.id} className={cx({chosen: subj.id == subjId})}>{subj.name}</th>;
				})}
			</tr>
		);
	}
	renderBody(scoreRanks, subjs, subjId, userId) {
		var that = this;
		return scoreRanks.map((scoreRank, i) => {
			if (i >= 10 && scoreRank.userId != userId) {
				return null;
			}
			if (subjId > 0) {
				return (
					<tr key={scoreRank.userId}>
						<td key="index">{scoreRank.index}</td>
						<td key="userName" className={cx({"self": scoreRank.userId == userId})}>{scoreRank.userName}</td>
						<td key="score">{toFixed(scoreRank[subjId], 1)}</td>
					</tr>
				);
			}
			return (
				<tr key={scoreRank.userId}>
					<td key="index">{scoreRank.index}</td>
					<td key="userName" className={cx({"self": scoreRank.userId == userId})}>{scoreRank.userName}</td>
					<td key="0" className={cx({"chosen": subjId == 0})}>{toFixed(scoreRank.score, 1)}</td>
					{subjs.map(subj => {
						let score = scoreRank[subj.id];
						if (score == null || score == undefined) {
							score = '--';
						} else {
							score = toFixed(score, 1);
						}
						return <td key={subj.id} className={cx({chosen: subj.id == subjId})}>{score}</td>;
					})}
				</tr>
			);
		})
	}
	render() {
		let {scoreRanks, subjs, subjId, userId} = this.props;
		if (scoreRanks == undefined || scoreRanks == null) {
			return null;
		}
		this.markRankIndex(scoreRanks, subjId);
		let head = this.renderHead(subjs, subjId);
		let body = this.renderBody(scoreRanks, subjs, subjId, userId);
		return (
			<div className="class-rank">
				<p className="tittle">班级{subjId > 0 ? '' : '综合'}成绩TOP10</p>
				<div className="m-table m-table-center">
					<table>
						<thead>{head}</thead>
						<tbody>{body}</tbody>
					</table>
				</div>
			</div>
		);
	}
}

class KnoGraspInfo extends React.Component {
	markLevels(knoRates) {
		knoRates.forEach(item => {
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
		let {knoRates, myself, isUnit} = this.props;
		if (!(knoRates && knoRates.length)) {
			// 没有知识点分析，不做处理直接返回
			return null;
		}
		this.markLevels(knoRates);
		let columns = 'name,totalNum,selfRate,klassRate,level';
		if (myself && Csts.device != 'hd') {
			columns = 'name,totalNum,selfRate,klassRate,level,wrong,exercise';
		}
		if (isUnit) {
			columns = columns.replace('klassRate,', '');
		}
		return (
			<Section id={this.props.id} title="知识点掌握程度" help={`${Leke.domain.staticServerName}/pages/help-center/diag/zw-cd.html`}>
			<div className="knowledgepoint">
				<KnoGraspTable knoRates={knoRates} columns={columns} />
			</div>
			</Section>
		);
	}
}

class MicroRecommend extends React.Component {
	render() {
		if (Csts.device == 'hd') {
			return null;
		}
		let datas = this.props.datas;
		if (datas == null || datas == undefined || datas.length == 0) {
			return null;
		}
		return (
			<div className="courserecommend">
				<span className="tittle">微课推荐</span>
				<MicroView datas={datas} />
			</div>
		);
	}
}

class PersonAnalysis extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			loading: false,
			scope: null,
			subjId: null,
			view: null
		};
		this.handleChangeQuery = this.handleChangeQuery.bind(this);
	}
	handleChangeQuery(classId, subjectId, cycleId) {
		this.setState({loading: true});
		$.post('person-score-data.htm', {
			classId: classId,
			subjectId: subjectId,
			cycleId: cycleId
		}).done(function(json) {
			this.setState({
				loading: false,
				subjId: subjectId,
				view: json.datas.view
			});
		}.bind(this));
	}
	renderBody() {
		let {subjId, loading, view} = this.state;
		if (view == null || loading == true) {
			return <div className="m-mask"><div className="con"><i></i><div>数据加载中</div></div></div>;
		} else if (view.success == false) {
			return <div className="m-tips"><i></i><span>{view.message}</span></div>;
		}
		if (view.summary.correctNum == 0) {
			return <PersonSummary summary={view.summary} userName={Csts.userName} myself={true} />
		} else {
			return [
					<PersonSummary summary={view.summary} userName={Csts.userName} myself={true} />,
					<OverallInfo overall={view.overall} subjScores={view.subjScores} isUnit={view.isUnit} />,
					<ScoreTrendInfo trends={view.trends} subjId={subjId} />,
					<ScoreCompared subjScores={view.subjScores} rptType={view.rptType} />,
					<ComboScoreRank scoreRanks={view.scoreRanks} subjs={view.subjs} subjId={subjId} userId={Leke.user.userId} />,
					<KnoGraspInfo knoRates={view.knoRates} myself={true} isUnit={view.isUnit} />,
					subjId > 0 ? <MicroRecommend datas={view.micros} /> : null
			];
		}
	}
	render() {
		let {types, zones, mode, view, userName} = Csts;
		return (
			<div className="analysis">
				<ScoreReportQuery types={types} zones={zones} hideZone={mode == 1} onChange={this.handleChangeQuery} />
				<div className="c-syn-subj">
				{this.renderBody()}
				</div>
			</div>
		);
	}
}

class DetailAnalysis extends React.Component {
	renderBody() {
		let {subjId, view} = Csts;
		if (view.success == false) {
			return <div className="m-tips"><i></i><span>{view.message}</span></div>;
		}
		if (view.summary.correctNum == 0) {
			return <PersonSummary summary={view.summary} userName={Csts.userName} myself={false} />
		} else {
			return (
				<div>
					<PersonSummary summary={view.summary} userName={Csts.userName} myself={false} />
					<ScoreTrendInfo trends={view.trends} subjId={subjId} />
					<KnoGraspInfo knoRates={view.knoRates} myself={false} isUnit={false} />
				</div>
			);
		}
	}
	render() {
		return (
			<div className="analysis">
				<div className="c-syn-subj">
				{this.renderBody()}
				</div>
			</div>
		);
	}
}

let conatiner = document.getElementById('container');
if (Csts.view) {
	ReactDOM.render(<DetailAnalysis />, container);
} else {
	ReactDOM.render(<PersonAnalysis />, container);
}
