var {toFixed, toRate} = require('../../../utils/number');
var {toLevel, toLevelName} = require('./analyze-base');
var ReactChart = require('../../../react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');

class ClassSummary extends React.Component {
	render() {
		let {avgScore, maxScore, minScore, totalNum, otherNum, levelA, levelB, levelC, levelD, levelE} = this.props.summary;
		let total = totalNum - otherNum;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">成绩分析</div>
				<div className="texts">
					<section>敬爱的<span className="fc-green">{ReportCst.userName}</span>老师：</section>
					<section className="txt-idt">
						您好，在此期间，本班级的学科平均分为<span className="fc-green">{toFixed(avgScore, 1)}</span>分，最高分<span className="fc-green">{toFixed(maxScore, 1)}</span>分，
						最低分<span className="fc-orange">{toFixed(minScore, 1)}</span>分，班级整体表现<span className="fc-orange">{toLevelName(avgScore)}</span>；
						本班共有学生<span className="fc-green">{totalNum}</span>人，
						其中成绩优秀<span className="fc-green">{levelA}</span>人，占比<span className="fc-green">{toRate(levelA, total, 1)}%</span>，
						良好<span className="fc-green">{levelB}</span>人，占比<span className="fc-green">{toRate(levelB, total, 1)}%</span>，
						及格<span className="fc-green">{levelC}</span>人，占比<span className="fc-green">{toRate(levelC, total, 1)}%</span>，
						较差<span className="fc-orange">{levelD}</span>人，占比<span className="fc-orange">{toRate(levelD, total, 1)}%</span>，
						危险<span className="fc-orange">{levelE}</span>人，占比<span className="fc-orange">{toRate(levelE, total, 1)}%</span>。
					</section>
					<ReactChart className="maps" option={ScoreChartConfig.buildClassScoreBar(avgScore, maxScore, minScore)} />
					<ReactChart className="maps" option={ScoreChartConfig.buildScorePie(levelA, levelB, levelC, levelD, levelE)} />
				</div>
			</section>
		);
	}
}

module.exports = ClassSummary;