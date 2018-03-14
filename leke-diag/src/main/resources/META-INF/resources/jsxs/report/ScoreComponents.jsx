define(function(require, exports, module){
var React = require('common/react/react');
var {cx} = require('common/react/react-utils');
var ReactChart = require('common/react/ReactChart');
var ScoreChartConfig = require('./ScoreChartConfig');

class ScoreReportQuery extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			typeIdx: 0,
			zoneIdx: 0,
			cycleId: props.types[0].list[0].id
		};
		this.handleChangeCycle = this.handleChangeCycle.bind(this);
		this.handleChangeZone = this.handleChangeZone.bind(this);
	}
	handleClickType(typeIdx) {
		if (this.state.typeIdx != typeIdx) {
			let cycleId = this.props.types[typeIdx].list[0].id;
			this.setState({
				typeIdx: typeIdx,
				cycleId: cycleId
			});
			this.handleChange(this.state.zoneIdx, cycleId);
		}
	}
	handleChangeCycle(e) {
		let cycleId = parseInt(e.target.value);
		this.setState({ cycleId: cycleId });
		this.handleChange(this.state.zoneIdx, cycleId);
	}
	handleChangeZone(e) {
		let zoneIdx = parseInt(e.target.value);
		this.setState({ zoneIdx: zoneIdx });
		this.handleChange(zoneIdx, this.state.cycleId);
	}
	handleChange(zoneIdx, cycleId) {
		if (this.props.onChange) {
			let zone = this.props.zones[zoneIdx];
			console.log(`condition: ${zone.classId}, ${zone.subjectId}, ${cycleId}`);
			this.props.onChange(zone.classId, zone.subjectId, cycleId);
		}
	}
	componentDidMount() {
		let {zoneIdx, cycleId} = this.state;
		this.handleChange(zoneIdx, cycleId);
	}
	render() {
		let that = this;
		let {typeIdx, cycleId} = this.state;
		let {types, zones, hideZone} = this.props;
		return (
			<div className="m-search-box">
				<div className="f-fl">
					<ul>
					{types.map((rt, i) => {
						let handler = that.handleClickType.bind(that, i);
						return <li key={i} className={cx({"active": i == typeIdx})}><a onClick={handler}>{rt.label}</a></li>;
					})}
					</ul>
				</div>
				<div className="f-fr">
					<select className="u-select u-select-lg" onChange={this.handleChangeCycle}>
						{types[typeIdx].list.map((c, i) => { return <option key={i} value={c.id} selected={c.id == cycleId}>{c.label}</option>; })}
					</select>
					<select className={cx("u-select u-select-lg", {"f-dn": hideZone || false})} onChange={this.handleChangeZone}>
						{zones.map((c, i) => { return <option key={i} value={i}>{c.label}</option>; })}
					</select>
				</div>
			</div>
		);
	}
}

class ScoreTrendChart extends React.Component {
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
			<div className="maps-tab">
				<ul>
				{trends.map((trend, i) => {
					return <li className={cx({active: idx == i})} onClick={that.handleChangeType.bind(that, i)}>{trend.label}</li>
				})}
				</ul>
			</div>
		);
	}
	render() {
		let that = this;
		let trends = this.props.trends;
		let {names, selfs, klass} = trends[this.state.idx];
		return (
			<div className="maps">
				{that.renderTabs(trends)}
				<ReactChart className="single" option={ScoreChartConfig.buildScoreTrend(names, selfs, klass)} />
			</div>
		);
	}
}

module.exports = {
	ScoreReportQuery: ScoreReportQuery,
	ScoreTrendChart: ScoreTrendChart
};
});
