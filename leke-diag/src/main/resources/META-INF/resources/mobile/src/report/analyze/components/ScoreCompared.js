var {toFixed, toRate} = require('../../../utils/number');
var {toLevel, levelNames} = require('./analyze-base');
var ReactChart = require('../../../react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');

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
					remark2.push(<span>{subj.label}<span className="fc-green">上升劲猛</span>，</span>);
					remark2.push(<span>从{levelNames[prevLevel - 1]}升上{levelNames[currLevel - 1]}；</span>);
				} else if (currLevel >= prevLevel + 2) {
					remark2.push(<span>{subj.label}<span className="fc-orange">下降严重</span>，</span>);
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
			<section id={this.props.id} className="ana-module">
				<div className="title">{this.props.title}</div>
				<ReactChart className="maps" style={{height: 180}} option={ScoreChartConfig.buildScoreCompBar(rptType, names, currs, prevs)} />
				<div className="texts">
					<p className="paragraph">1、相较于{labels[0]}，{labels[1]}有<span className="fc-orange">{down}门</span>功课呈<span className="fc-orange">下降趋势</span>；
					<span className="fc-green">{rise}门</span>呈<span className="fc-green">上升趋势</span>；{fixed}门功课持平</p>
					<p className="paragraph">{remark2}</p>
				</div>
			</section>
		);
	}
}

module.exports = ScoreCompared;