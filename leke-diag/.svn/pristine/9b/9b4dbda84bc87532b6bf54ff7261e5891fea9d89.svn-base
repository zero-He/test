var {toFixed, toRate} = require('../../../utils/number');
var ReactChart = require('../../../react/ReactChart');
var BehaviorChartConfig = require('./BehaviorChartConfig');

class OtherBehaviorInfo extends React.Component {
	render() {
		let lekeVal = this.props.lekeVal;
		let {practiceNum, killWrongNum, writeNoteNum, doubtNum} = this.props.otherWiseInfo;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">其它学习行为汇总</div>
				<div className="c-table">
					<table>
						<tbody>
							<tr><th style={{width: '40%'}}>项目</th><th style={{width: '30%'}}>数目</th><th style={{width: '30%'}}>乐豆</th></tr>
							<tr><td>自主练习</td><td>{practiceNum}题</td><td>{BehaviorChartConfig.renderLekeVal(lekeVal, '11301')}</td></tr>
							<tr><td>消灭错题</td><td>{killWrongNum}题</td><td>{BehaviorChartConfig.renderLekeVal(lekeVal, '11304')}</td>	</tr>
							<tr><td>课外笔记</td><td>{writeNoteNum}份</td><td>--</td></tr>
							<tr><td>提问</td><td>{doubtNum}次</td><td>{BehaviorChartConfig.renderLekeVal(lekeVal, '11302')}</td></tr>
						</tbody>
					</table>
				</div>
			</section>
		);
	}
}

module.exports = OtherBehaviorInfo;