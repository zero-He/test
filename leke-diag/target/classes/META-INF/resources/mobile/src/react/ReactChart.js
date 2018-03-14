class ReactChart extends React.Component {
	// first add
	componentDidMount() {
		this.renderChartDom();
	}
	// update
	componentDidUpdate() {
		this.renderChartDom();
	}
	// remove
	componentWillUnmount() {
		try {
			echarts.dispose(this.refs.chartDom);
		} catch (e) {
			console.log('echart error: ' + e);
		}
	}
    // render the dom
	renderChartDom() {
		let chartObj = this.getChartInstance();
		if (!chartObj) {
			return null;
		}
		let {option, notMerge, lazyUpdate, showLoading} = this.props;
		chartObj.setOption(option, notMerge || false, lazyUpdate || false);
		if (showLoading) {
			chartObj.showLoading();
		}
		return chartObj;
	}
	getChartInstance() {
		let chartDom = this.refs.chartDom;
		let theme = this.props.theme || 'default';
		try {
			return echarts.getInstanceByDom(chartDom) || echarts.init(chartDom, theme);
		} catch (e) {
			console.log('echart error: ' + e);
			return null;
		}
	}
	render() {
		return <div ref="chartDom" className={this.props.className} style={this.props.style} />;
	}
}

ReactChart.propTypes = {
	option: React.PropTypes.object.isRequired,
	style: React.PropTypes.object,
	className: React.PropTypes.string,
	theme: React.PropTypes.string,
	showLoading: React.PropTypes.bool,
	notMerge: React.PropTypes.bool,
	lazyUpdate: React.PropTypes.bool
};

module.exports = ReactChart;
