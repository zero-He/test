var {toFixed, toRate} = require('../../../utils/number');
var ReactChart = require('../../../react/ReactChart');
var BehaviorChartConfig = require('./BehaviorChartConfig');

class ViewFinishAnalyInfo extends React.Component {
	render() {
		let vfa = this.props.viewFinishAnaly;
		var labels1 = [{name: '课件', max: vfa.totalNum}, {name: '微课', max: vfa.totalNum}, {name: '试卷', max: vfa.totalNum}];
		var labels2 = [{name: '试卷', max: vfa.totalNum}, {name: '课件', max: vfa.totalNum}, {name: '笔记', max: vfa.totalNum}];
		var datas1 = [{name: '应预习', value: vfa.previews1}, {name: '实预习', value: vfa.previews2}];
		var datas2 = [{name: '应复习', value: vfa.reviews1}, {name: '实复习', value: vfa.reviews2}];
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">预复习情况</div>
				<ReactChart className="maps" option={BehaviorChartConfig.buildGeneralRadar(labels1, datas1)} />
				<ReactChart className="maps" option={BehaviorChartConfig.buildGeneralRadar(labels2, datas2)} />
				<div className="c-table">
					<table>
						<tbody>
							<tr>
								<th style={{width: '20%'}}>总课堂数</th>
								<th style={{width: '20%'}}>可预习</th>
								<th style={{width: '20%'}}>实预习(比率)</th>
								<th style={{width: '20%'}}>可复习</th>
								<th style={{width: '20%'}}>实复习(比率)</th>
							</tr>
							<tr>
								<td>{vfa.totalNum}</td>
								<td>{vfa.preview1}</td>
								<td>{vfa.preview2}({toRate(vfa.preview2, vfa.preview1)}%)</td>
								<td>{vfa.review1}</td>
								<td>{vfa.review2}({toRate(vfa.review2, vfa.review1)}%)</td>
							</tr>
						</tbody>
					</table>
				</div>
			</section>
		);
	}
}

module.exports = ViewFinishAnalyInfo;