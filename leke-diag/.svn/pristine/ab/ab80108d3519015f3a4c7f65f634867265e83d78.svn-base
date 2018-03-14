import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';

export default class ReactChart extends Component {
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
	option: PropTypes.object.isRequired,
	style: PropTypes.object,
	className: PropTypes.string,
	theme: PropTypes.string,
	showLoading: PropTypes.bool,
	notMerge: PropTypes.bool,
	lazyUpdate: PropTypes.bool
};
