import ReactChart from './ReactChart';
import {toRate} from '../utils/number';

class ReactPieChart extends React.Component {
    static defaultProps = {
        unit: '',
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
        		radius : ['50%', '85%'],
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
        const {datas, color, summary, formatter, unit} = this.props;
        let total = 0;
        datas.forEach(v => total += v.value);
        return (
            <div className="maps">
                <ReactChart className="left-pic" option={this.buildOptions(datas, color)} />
                <div className="right-txt">
                    {summary ? <div className="sum" dangerouslySetInnerHTML={{__html: summary}}></div> : null}
                    {datas.map((item, i) => {
                        const a = item.name, b = item.value, c = toRate(b, total, 1);
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
