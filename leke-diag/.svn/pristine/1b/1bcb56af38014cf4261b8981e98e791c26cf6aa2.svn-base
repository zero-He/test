var {toFixed, toRate} = require('../../../utils/number');
var ReactChart = require('../../../react/ReactChart');
var ReactPieChart = require('../../../react/ReactPieChart');
var BehaviorChartConfig = require('./BehaviorChartConfig');

class ClassStuationInfo extends React.Component {
	render() {
		let {attendStatInfo: attend, behaviorInfo: bi, lekeVal} = this.props.view;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">
					<span>课堂互动</span>
					<a href={`inclass.htm?cycleId=${this.props.cycleId}&userId=${ReportCst.userId}&_newtab=1`} className="fc-green m-seedetail">详情 <i></i></a>
				</div>
				<div className="c-table">
					<table>
						<tbody>
							<tr><th style={{width: '40%'}}>项目</th><th style={{width: '30%'}}>数量</th><th style={{width: '30%'}}>获得乐币</th></tr>
							<tr><td>金榜题名</td><td>{bi.rank1}/{bi.rank2}/{bi.rank3}</td><td>{BehaviorChartConfig.renderLekeVal(lekeVal, '12302')}</td></tr>
							<tr><td>点到</td><td>{bi.callNum1}/{bi.callNum2}</td><td>{BehaviorChartConfig.renderLekeVal(lekeVal, '12306')}</td></tr>
							<tr><td>举手</td><td>{bi.raised}</td><td>{BehaviorChartConfig.renderLekeVal(lekeVal, '12103')}</td></tr>
							<tr><td>被授权</td><td>{bi.authed}</td><td>--</td></tr>
							<tr><td>课堂笔记</td><td>{bi.noteNum}</td><td>{BehaviorChartConfig.renderLekeVal(lekeVal, '12105')}</td></tr>
							<tr><td>随堂作业</td><td>{bi.examNum1}/{bi.examNum2}</td><td>--</td></tr>
							<tr><td>快速问答</td><td>{bi.quickNum1}/{bi.quickNum2}</td><td>--</td></tr>
							<tr><td>分组讨论</td><td>{bi.discuNum1}/{bi.discuNum2}</td><td>{BehaviorChartConfig.renderLekeVal(lekeVal, '12307', '12308')}</td></tr>
							<tr><td>献花</td><td>{bi.flowerNum}</td><td>{BehaviorChartConfig.renderLekeVal(lekeVal, '12107')}</td></tr>
							<tr><td>评价</td><td>{bi.evalNum}/{attend.realNum}</td><td>{BehaviorChartConfig.renderLekeVal(lekeVal, '12104')}</td></tr>
						</tbody>
					</table>
				</div>
			</section>
		);
	}
}

module.exports = ClassStuationInfo;
