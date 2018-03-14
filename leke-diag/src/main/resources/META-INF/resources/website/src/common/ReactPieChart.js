var React = require('common/react/react');
var ReactChart = require('common/react/ReactChart');
var {toFixed, toRate} = require('../utils/number');

class ReactPieChart extends React.Component {
    static defaultProps = {
        unit: '',
        vertical: false,
        color: ['#619eed', '#ffd270', '#ff6d6e', '#70cfff', '#83cf0b', '#ff9571'],
        formatter: (a, b, c, d, e) => {
            return `${a}<span class="green-txt">${b}</span>${e}，占比<span class="green-txt">${c}%</span>`;
        }
    };
    buildOptions(datas, color) {
        return {
            color: color,
        	series : [{
        		type : 'pie',
        		center : ['50%', '50%'],
        		radius : ['40%', '85%'],
        		avoidLabelOverlap : false,
                silent : true,
        		label : {
        			normal : {
        				show : false
        			}
        		},
        		labelLine : {
        			normal : {
        				show : false
        			}
        		},
        		data : datas
        	}]
        };
    }
    render() {
        const {datas, color, summary, formatter, unit, vertical, chartTitle} = this.props;
        let total = 0;
        datas.forEach(v => total += v.value);
        return (
            <div className="c-piechart">
                <ReactChart className={vertical ? 'vertical-chart' : 'horizontal-chart'} option={this.buildOptions(datas, color)} />
                <div className="legend">
                    {summary ? <p dangerouslySetInnerHTML={{__html: summary}}></p> : null}
                    {datas.map((item, i) => {
                        const a = item.name, b = item.value, c = (chartTitle == "homework" ? item.rate : toRate(b, total, 1));
                        const icon = `<i style="background: ${color[i]}"></i>`;
                        const html = icon + formatter(a, b, c, datas, unit);
                        return <p key={item.name} dangerouslySetInnerHTML={{__html: html}}></p>;
                    })}
                    <p></p>
                </div>
            </div>
        );
    }
}

module.exports = ReactPieChart;
