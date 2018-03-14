var ReactChart = require('../../../react/ReactChart');

class DiligentInfo extends React.Component {
	static achieveLevels = ['不勤奋', '不够勤奋', '较勤奋', '很勤奋', '非常勤奋', '非常勤奋'];
    buildGaugeOption(value) {
		const idx = Math.floor(value / 20);
		const name = DiligentInfo.achieveLevels[idx];
        return {
			series: [{
				type: 'gauge',
				radius: '90%',
				data: [{
					name: name,
					value: value
				}],
				axisLine: {
					lineStyle: {
						color: [[0.20, '#7bb57b'],[0.80, '#85c6f4'],[1, '#ffac8e']]
					}
				}
			}]
		};
    }
    buildLineOptions(datas) {
        const labels = datas.map(v => v.name.replace(/（\S*）/, ''));
        const values = datas.map(v => v.value);
        return {
			color: ['#76b3a1'],
        	title: {
        		text: '勤奋指数走势',
        		left: 'center',
				textStyle: {
		            fontWeight:'normal'
		        }
        	},
        	tooltip: {
        		trigger: 'item',
        		formatter: '{b}：{c}'
        	},
        	grid: {
        		left: '5%',
        		right: '6%',
        		bottom: '5%',
        		containLabel: true
        	},
        	xAxis: {
        		type: 'category',
				boundaryGap: false,
        		data: labels
        	},
        	yAxis: {
        		type: 'value'
        	},
        	series: [{
        		type: 'line',
				areaStyle: {normal: {}},
        		data: values
        	}]
        };
    }
	render() {
		const {trends, score} = this.props.view.achievement;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">勤奋指数<HelpLink href={`${Leke.domain.staticServerName}/pages/help-center/diag/kq-zs.html?_newtab=1`} /></div>
                <ReactChart className="maps" option={this.buildGaugeOption(score)} />
				<div className="tips">勤奋指数共有5个层级分别是：[80-100]为非常勤奋，[60-80]为很勤奋，[40-60]为较勤奋，[20-40]为不够勤奋，[0-20]为不勤奋。</div>
                <ReactChart className="maps" option={this.buildLineOptions(trends)} />
			</section>
		);
	}
}

class HelpLink extends React.Component {
    render() {
		const {href} = this.props;
        return (
			<a href={href}>
				<i className="c-help"></i>
			</a>
		);
    }
}

module.exports = DiligentInfo;
