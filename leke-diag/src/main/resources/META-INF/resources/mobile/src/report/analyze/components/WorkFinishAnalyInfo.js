var {toFixed, toRate} = require('../../../utils/number');
var ReactChart = require('../../../react/ReactChart');
var BehaviorChartConfig = require('./BehaviorChartConfig');

class WorkFinishAnalyInfo extends React.Component {
	render() {
		let {totalNum, normalNum, delayNum, doneBugFixNum, undoBugFixNum} = this.props.workFinishAnaly;
		let unsubmitNum = totalNum - normalNum - delayNum, bugFixNum = doneBugFixNum + undoBugFixNum;
		var title1 = `应交作业：${totalNum}`;
		var datas1 = [{name: '正常提交', value: normalNum}, {name: '延迟提交', value: delayNum}, {name: '未提交', value: unsubmitNum}];
		var title2 = `应订正作业：${bugFixNum}`;
		var datas2 = [{name: '已订正', value: doneBugFixNum}, {name: '未订正', value: undoBugFixNum}];
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">作业情况</div>
				<div className="inner-title">完成情况 <a href={`homework.htm?cycleId=${this.props.cycleId}&userId=${ReportCst.userId}`} className="more">详情</a></div>
				<ReactChart className="maps" option={BehaviorChartConfig.buildGeneralPie(title1, datas1)} />
				<div className="inner-title">订正情况</div>
				<ReactChart className="maps" option={BehaviorChartConfig.buildGeneralPie(title2, datas2)} />
			</section>
		);
	}
}

module.exports = WorkFinishAnalyInfo;