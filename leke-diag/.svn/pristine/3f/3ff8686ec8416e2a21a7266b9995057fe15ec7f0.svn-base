var {toFixed, toRate} = require('../../../utils/number');
var {toSecond, toLevel, toLevelName} = require('./homework-utils');

class SummaryInfo extends React.Component {
	render() {
		let {summary, totalScore, userName, className} = this.props;
		var {homeworkId, homeworkName, submitNum, delayNum, totalNum, correctNum} = this.props.summary;
		var normalNum = submitNum - delayNum;
		var undoneNum = totalNum - submitNum;
		let correctInfo = null;
		if (submitNum > correctNum) {
			correctInfo = <span>有<span className="fc-orange">{submitNum - correctNum}</span>位学生作业未批改，建议批改完全后，再查看报告更准确；</span>;
		}
		return (
			<section id={this.props.id} className="ana-module">
				<section className="ana-title">{className}<span className="fc-green">《{homeworkName}》</span>作业分析报告</section>
				<article className="texts">
					<section><span>敬爱的<span className="fc-green">{userName}</span>老师：</span></section>
					<p className="paragraph">
						<span>您好，本次作业应交<span className="fc-green">{totalNum}</span>人，</span>
						<span>实交<span className="fc-green">{submitNum}</span>人，</span>
						<span>上交率<span className="fc-green">{toRate(submitNum, totalNum, 1)}%</span>；</span>
						<span>{correctInfo}</span>
						<span>作业总分<span className="fc-green">{toFixed(totalScore)}</span>分，</span>
						<span>班级平均分<span className="fc-green">{toFixed(summary.avgScore, 1)}分</span>，</span>
						<span>最高分<span className="fc-green">{toFixed(summary.maxScore, 1)}分</span>，</span>
						<span>最低分<span className="fc-orange">{toFixed(summary.minScore, 1)}分</span>，</span>
						<span>平均用时<span className="fc-green">{toSecond(summary.avgUsedTime)}分钟</span>，</span>
						<span>班级整体表现<span className="fc-green">{toLevelName(toRate(summary.avgScore, totalScore, 1))}</span>。</span>
					</p>
				</article>
			</section>
		);
	}
}

module.exports = SummaryInfo;
