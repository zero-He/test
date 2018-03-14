var {toFixed, toRate} = require('../../../utils/number');
var {toLevel, toLevelName} = require('./analyze-base');
var ReactChart = require('../../../react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');

class PersonSummary extends React.Component {
	render() {
		var {summary, userName, myself} = this.props;
		let {totalNum, submitNum, delayNum, correctNum, levelA, levelB, levelC, levelD, levelE, queNum, errNum} = summary;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">成绩分析</div>
				<div className="texts">
					{myself ? (<section>亲爱的<span className="fc-green">{userName}</span>同学：</section>) : null}
					<p className="paragraph">
						<span>在此期间内，{myself ? '您' : <span className="fc-green">{userName}同学</span>}应交作业<span className="fc-green">{totalNum}</span>份，</span>
						<span>正常提交作业<span className="fc-green">{submitNum - delayNum}</span>份，占比<span className="fc-green">{toRate(submitNum - delayNum, totalNum, 1)}%</span>，</span>
						<span>延交作业<span className="fc-orange">{delayNum}</span>份，占比<span className="fc-orange">{toRate(delayNum, totalNum, 1)}%</span>，</span>
						<span>未交作业<span className="fc-orange">{totalNum - submitNum}</span>份，占比<span className="fc-orange">{toRate(totalNum - submitNum, totalNum, 1)}%</span>；</span>
						<span>已批改作业<span className="fc-green">{correctNum}</span>份，</span>
						<span>其中成绩优秀<span className="fc-green">{levelA}</span>份，占比<span className="fc-green">{toRate(levelA, correctNum, 1)}%</span>，</span>
						<span>良好<span className="fc-green">{levelB}</span>份，占比<span className="fc-green">{toRate(levelB, correctNum, 1)}%</span>，</span>
						<span>及格<span className="fc-green">{levelC}</span>份，占比<span className="fc-green">{toRate(levelC, correctNum, 1)}%</span>，</span>
						<span>较差<span className="fc-orange">{levelD}</span>份，占比<span className="fc-orange">{toRate(levelD, correctNum, 1)}%</span>，</span>
						<span>危险<span className="fc-orange">{levelE}</span>份，占比<span className="fc-orange">{toRate(levelE, correctNum, 1)}%</span>；</span>
						<span>共做题<span className="fc-green">{queNum}</span>道，错题<span className="fc-orange">{toFixed(errNum, 1)}</span>道，</span>
						<span>得分率<span className="fc-green">{toRate(queNum - errNum, queNum, 1)}%</span>。</span>
					</p>
				</div>
				<ReactChart className="maps" option={ScoreChartConfig.buildSubmitPie(submitNum - delayNum, delayNum, totalNum - submitNum, '份')} />
				<ReactChart className="maps" option={ScoreChartConfig.buildScorePie(levelA, levelB, levelC, levelD, levelE, '份')} />
			</section>
		);
	}
}

module.exports = PersonSummary;
