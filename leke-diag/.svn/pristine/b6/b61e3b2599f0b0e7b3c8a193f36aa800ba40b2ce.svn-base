var {toFixed, toRate} = require('../../../utils/number');
var {toLevel, toLevelName} = require('./analyze-base');
var ReactChart = require('../../../react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');

function fillNull(data) {
	for (var i = 0; i < data.length; i++) {
		data[i] = data[i] != null ? data[i] : '-';
	}
	return data;
}

function buildScoreRadar(names, selfs, klass) {
	var subjNames = names.slice(0);
	if (names.length < 3) {
		for (var i = names.length; i < 3; i++) {
			names.push('');
			selfs.push(0);
			if (klass) {
				klass.push(0);
			}
		}
	}
	var indicator = names.map(function(name) {
		return {
			text : name,
			max : 100
		};
	});
	var seriesData = [];
	if (klass) {
		seriesData.push({
			value : fillNull(klass),
			name : '班级平均分',
			itemStyle : {
				normal : {
					areaStyle : {
						type : 'default'
					}
				}
			}
		});
	}
	seriesData.push({
		value : fillNull(selfs),
		name : '个人得分'
	});
	return {
		legend : {
			x : 'left',
			orient : 'vertical',
			data : ['班级平均分', '个人得分']
		},
		tooltip : {
			formatter : function(radar) {
				return radar.name + subjNames.map(function(name, i) {
					return '<br />' + name + '：' + radar.value[i];
				}).join('');
			}
		},
		polar : [{
			indicator : indicator,
			splitNumber : 5,
			radius : '65%',
			axisLabel : {
				show : true,
				textStyle : {
					color : '#ccc'
				}
			},
			splitArea : {
				areaStyle : {
					color : ['#ffffff', '#ffffff']
				}
			},
			splitLine : {
				lineStyle : {
					width : 2,
					color : '#e9eaea'
				}
			}
		}],
		color : ['#a9cfef', '#fe6910'],
		series : [{
			type : 'radar',
			data : seriesData
		}]
	};
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
			<section id={this.props.id} className="ana-module">
				<div className="title">学科优劣势分析</div>
				<ReactChart className="maps" option={buildScoreRadar(names, selfs, isUnit ? null : klasses)} />
				<ReactChart className="maps" style={{height: 150}} option={ScoreChartConfig.buildOverallScore(score, isUnit ? null : rate)} />
				<div className="texts">
					<p className="paragraph">
						您的全科综合得分为<span className="fc-green">{toFixed(score, 1)}分</span>，表现<span className="fc-green">{toLevelName(score)}</span>；
						{isUnit ? null : (<span>班级排名第<span className="fc-green">{rank}</span>，领先全班<span className="fc-green">{rate}%</span>的同学；</span>)}
					</p>
					<p className="paragraph">
						相比自己综合得分短板学科为<span className="fc-orange">{mines1}</span>，长板学科为<span className="fc-green">{maxes1}</span>；
						{isUnit ? null : (<span>相比班级各科得分劣势学科为<span className="fc-orange">{mines2}</span>，相对优势学科为<span className="fc-green">{maxes2}</span>；</span>)}
						{isUnit ? (<span>请加强短板学科的学习训练，弥补不足，齐头并进。</span>) : (<span>请加强短板、劣势学科的学习训练，弥补不足，齐头并进。</span>)}
					</p>
				</div>
			</section>
		);
	}
}

module.exports = OverallInfo;