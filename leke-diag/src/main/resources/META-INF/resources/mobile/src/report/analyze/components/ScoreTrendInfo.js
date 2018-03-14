var ReactChart = require('../../../react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');

class ScoreTrendInfo extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			idx: 0
		};
	}
	handleChangeType(idx) {
		this.setState({idx: idx});
	}
	renderTabs(trends) {
		if (!(trends && trends.length > 1)) {
			return null;
		}
		let that = this;
		let idx = this.state.idx;
		return (
			<div className="switch">
			{trends.map((trend, i) => {
				return <span className={idx == i ? 'cur' : ''} onClick={that.handleChangeType.bind(that, i)}>{trend.label}</span>
			})}
			</div>
		);
	}
	render() {
		let that = this;
		let {trends, title} = this.props;
		if (!(trends && trends.length)) {
			return null;
		}
		let {names, selfs, klass} = trends[this.state.idx];
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">{title}{that.renderTabs(trends)}</div>
				<ReactChart className="maps" style={{height: 180}} option={ScoreChartConfig.buildScoreTrend(names, selfs, klass)} />
			</section>
		);
	}
}

module.exports = ScoreTrendInfo;