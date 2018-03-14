var React = require('common/react/react');
var ReactChart = require('common/react/ReactChart');
var ReactPieChart = require('../common/ReactPieChart');
var {toFixed, toRate} = require('../utils/number');
var Dialog = require('dialog');
var Utils = require('utils');
var Section = require('../common/Section');
var KnoGraspTable = require('../common/KnoGraspTable');

const level_names = ["优秀", "良好", "及格", "较差", "危险"];
const level_mins = [85, 70, 60, 45, 0];

function scoreToLevel(score) {
    if (score == null || score == undefined) {
        return 5;
    }
    for (var i = 0; i < 5; i++) {
        if (score >= level_mins[i]) {
            return i;
        }
    }
    return 4;
}

class AnalyseResult extends React.Component {
    render() {
        const {query, view} = this.props;
        if (view == null) {
			return <div className="m-mask"><div className="con"><i></i><div>数据加载中</div></div></div>;
		} else if (view.success == false) {
			return <div className="m-tips"><i></i><span>{view.message}</span></div>;
		}
        return (
            <div className="c-analayse">
                <SummaryInfo id="ach_summary" view={view} />
                <ScoreTrendInfo id="ach_scoretrend" view={view} query={query} />
                <ClassRankInfo id="ach_classrank" view={view} query={query} />
                <KnoGraspInfo id="ach_knograsp" view={view} query={query} />
            </div>
        );
    }
}
/*
<DiligentInfo id="ach_diligent" view={view} />
<AchieveInfo id="ach_achieve" view={view} />
<AttendInfo id="ach_attend" view={view} query={query} />
<HomeworkInfo id="ach_homework" view={view} query={query} />
<AchieveRank id="ach_achieverank" view={view} />*/

function buildClassScoreBar(avg, max, min) {
	return {
		color : ['#5b9bd5', '#8bc036', '#ffbe54'],
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			}
		},
		legend : {
            selectedMode: false,
			data : ['最高分', '最低分']
		},
		grid : {
			top : 60,
			left : 120,
			right : 120,
			bottom : 0,
			containLabel : true
		},
		xAxis : [{
			type : 'category',
			data : ['']
		}],
		yAxis : [{
			type : 'value',
			max : 100,
			splitNumber : 10,
			axisLabel : {
				formatter : '{value} 分'
			}
		}],
		series : [{
			name : '平均分',
			type : 'bar',
			barWidth : '40%',
			data : [toFixed(avg, 1)]
		}, {
			name : '最高分',
			type : 'line',
			data : [toFixed(max, 1)],
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
			data : [toFixed(min, 1)],
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

function buildScorePie(a, b, c, d, e) {
	var args = arguments;
	var data = level_names.map(function(label, i) {
		return {
			name : label,
			value : args[i]
		}
	});
	var total = a + b + c + d + e;
	return {
		color : ['#2fbd68', '#74d64b', '#5cc0ff', '#ffcd61', '#ff6661'],
		legend : {
			bottom : 20,
			data : level_names
		},
		tooltip : {
			formatter : '有成绩总人数：' + total + '<br />{b}人数：{c}（{d}%）'
		},
		series : [{
			type : 'pie',
			center : ['50%', '43%'],
			radius : ['40%', '70%'],
			avoidLabelOverlap : false,
			label : {
				normal : {
					show : false,
					position : 'center'
				},
				emphasis : {
					show : true,
					formatter : '{b}:{c}人,{d}%',
					textStyle : {
						fontSize : 16,
						fontWeight : 'bold'
					}
				}
			},
			labelLine : {
				normal : {
					show : false
				}
			},
			data : data
		}]
	};
}

function buildScoreTrend(names, selfs, klass) {
	var series = [], color = [], legendData = [];
	if (selfs) {
		selfs = selfs.map(function(v) {
			return v != null ? v : '-';
		});
		color.push('#ed7d31');
		legendData.push('我的得分');
		series.push({
			name : '我的得分',
			type : 'line',
			data : selfs,
			showAllSymbol : true
		});
	}
	if (klass) {
		klass = klass.map(function(v) {
			return v != null ? v : '-';
		});
		color.push('#5b9bd5');
		legendData.push('班级得分');
		series.push({
			name : '班级得分',
			type : 'line',
			data : klass,
			showAllSymbol : true
		});
	}
	return {
		color : color,
		tooltip : {
			trigger : 'axis'
		},
		legend : {
            selectedMode: false,
			data : legendData
		},
		dataZoom : [{
			type : 'inside'
		}, {}],
		xAxis : {
			type : 'category',
			data : names
		},
		yAxis : {
			type : 'value',
			max : 100,
			interval : 10,
			axisLabel : {
				formatter : '{value} 分'
			}
		},
		series : series
	};
}

class SummaryInfo extends React.Component {
    render() {
        const {view} = this.props;
		const {avgScore, maxScore, minScore, totalNum, otherNum, levelA, levelB, levelC, levelD, levelE} = view.summary;
		const total = totalNum - otherNum;
        let dear = `敬爱的<span class="fc-green">${view.userName}</span>老师：`;
        let text = `在此期间，本班级的学科平均分为<span class="fc-green">${toFixed(avgScore, 1)}</span>分，`;
        text += `最高分<span class="fc-green">${toFixed(maxScore, 1)}</span>分，`;
        text += `最低分<span class="fc-orange">${toFixed(minScore, 1)}</span>分，`;
        text += `班级整体表现<span class="fc-orange">${level_names[scoreToLevel(avgScore)]}</span>；`;
        text += `本班共有学生<span class="fc-green">${totalNum}</span>人，`;
        text += `其中成绩优秀<span class="fc-green">${levelA}</span>人，`;
        text += `占比<span class="fc-green">${toRate(levelA, total, 1)}%</span>，`;
        text += `良好<span class="fc-green">${levelB}</span>人，`;
        text += `占比<span class="fc-green">${toRate(levelB, total, 1)}%</span>，`;
        text += `及格<span class="fc-green">${levelC}</span>人，`;
        text += `占比<span class="fc-green">${toRate(levelC, total, 1)}%</span>，`;
        text += `较差<span class="fc-orange">${levelD}</span>人，`;
        text += `占比<span class="fc-orange">${toRate(levelD, total, 1)}%</span>，`;
        text += `危险<span class="fc-orange">${levelE}</span>人，`;
        text += `占比<span class="fc-orange">${toRate(levelE, total, 1)}%</span>。`;
        return (
            <section id={this.props.id} className="c-paragraph">
                <section className="title" dangerouslySetInnerHTML={{__html: dear}}></section>
                <section className="con" dangerouslySetInnerHTML={{__html: text}}></section>
                <div className="c-layout" style={{marginTop: 35}}>
                    <div className="left" style={{width: '40%'}}>
                        <ReactChart className="maps" option={buildClassScoreBar(avgScore, maxScore, minScore)} />
                    </div>
                    <div className="right" style={{width: '50%'}}>
                        <ReactChart className="maps" option={buildScorePie(levelA, levelB, levelC, levelD, levelE)} />
                    </div>
                </div>
            </section>
        );
    }
}

class ScoreTrendInfo extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			idx: 0
		};
	}
	handleChangeType = (idx) => {
		this.setState({idx: idx});
	}
	renderTabs = (trends) => {
		if (!(trends && trends.length > 1)) {
			return null;
		}
		let idx = this.state.idx;
		return (
			<div className="maps-tab">
				<ul>{trends.map((trend, i) => <li key={i} className={idx == i ? 'active' : ''} onClick={() => this.handleChangeType(i)}>{trend.label}</li>)}</ul>
			</div>
		);
	}
	render() {
		let trends = this.props.view.trends;
		if (!(trends && trends.length)) {
			return null;
		}
		let {names, selfs, klass} = trends[this.state.idx];
		return (
            <Section title="班级成长走势">
                {this.renderTabs(trends)}
                <ReactChart className="maps" option={buildScoreTrend(names, selfs, klass)} />
			</Section>
		);
	}
}

class ClassRankInfo extends React.Component {
	buildLevel(name, list, level) {
		let {query} = this.props;
        let urlPrefix = `./klass-score-detail/${query.klassId}-${query.cycleId}`;
		list = list.filter(v => v.level == level);
		return list.map((rank, i) => {
			return (
				<tr>
					{i == 0 ? <td className={`lv-${level}`} rowSpan={list.length}>{name}({list.length}人)</td> : null}
					<td>{rank.index}</td>
					<td className="operation">
					{level <= 5 ? <a href={`${urlPrefix}-${rank.userId}.htm`} target="_blank">{rank.userName}</a> : <span>{rank.userName}</span>}
					</td>
					<td>{rank.score != null ? toFixed(rank.score, 1) : '--'}</td>
				</tr>
			);
		});
	}
	render() {
		let that = this;
		let list = this.props.view.scoreRanks;
		list.forEach((v, i) => {
			v.score = toFixed(v.score, 1);
			v.level = scoreToLevel(v.score) + 1;
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
		let levelNames = ['优秀', '良好', '及格', '较差', '危险', '无成绩'];
		return (
            <Section title="班级成绩排行">
                <div className="ranklist">
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
                </div>
				<span className="tips">注：点击学生姓名可查看该生学习分析报告</span>
			</Section>
		);
	}
}

class KnoGraspInfo extends React.Component {
	markLevels = (datas) => {
        datas.filter(v => v.classRate >= 85).forEach(v => v.level = 1);
        datas.filter(v => v.classRate < 50).forEach(v => v.level = 3);
        datas.filter(v => !(v.level)).forEach(v =>v.level = 2);
	}
	render() {
		// let {knoRates, classId, subjectId} = this.props;
        let {query} = this.props;
        let classSubjIds = query.klassId.split('-');
        let classId = classSubjIds[0], subjectId = classSubjIds[1];
        let {view} = this.props;
        let knoRates = view.knoRates;
		if (!(knoRates && knoRates.length)) {
			// 没有知识点分析，不做处理直接返回
			return null;
		}
		this.markLevels(knoRates);
		return (
            <Section id={this.props.id} title="知识点掌握程度" help={`${Leke.domain.staticServerName}/pages/help-center/diag/zw-cd.html`}>
                <KnoGraspTable knoRates={knoRates} columns="name,klassRate,level" increase={Leke.device != 'hd' && Leke.user.currentRoleId == 101} classId={classId} subjectId={subjectId} />
			</Section>
		);
	}
}

module.exports = AnalyseResult;
