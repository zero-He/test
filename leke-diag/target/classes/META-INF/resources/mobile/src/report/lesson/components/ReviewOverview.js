var {toFixed, toRate} = require('../../../utils/number');
var ReactChart = require('../../../react/ReactChart');
var SimpleTable = require('../../../react/SimpleTable');

var ChartConfig = {
	buildOverallFinishPie : function(done, part, undo) {
		return {
			color : ['#92d050', '#ffcc00', '#cc3300'],
			legend : {
				x : 'left',
				orient : 'vertical',
				selectedMode : false,
				data : ['已完成', '部分完成', '未完成']
			},
			tooltip : {
				formatter : '{b}：{c}人<br>比率：{d}%'
			},
			series : [{
				name : '完成情况',
				type : 'pie',
				center : ['60%', '50%'],
				radius : ['40%', '70%'],
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
				data : [{
					value : done,
					name : '已完成'
				}, {
					value : part,
					name : '部分完成'
				}, {
					value : undo,
					name : '未完成'
				}]
			}]
		};
	},
	buildCategoryFinishRadar : function(total, items) {
		var datas = [];
		var indicator = items.map(function(item) {
			datas.push(item.value);
			return {
				name : item.name,
				max : total
			};
		});
		return {
			tooltip : {},
			polar : [{
				indicator : indicator,
				center : ['50%', '60%'],
				radius : '90%',
				axisLabel : {
					show : true,
					textStyle : {
						color : '#ccc'
					}
				},
				splitArea : {
					areaStyle : {
						color : ['#ffffff', '#ffffff']
					}
				},
				splitLine : {
					lineStyle : {
						width : 2,
						color : '#e9eaea'
					}
				}
			}],
			color : ['#a9cfef'],
			series : [{
				type : 'radar',
				data : [{
					value : datas,
					name : '分类完成情况'
				}]
			}]
		};
	}
}


// 预/复习概览
class ReviewOverview extends React.Component {
	renderWork(phase, exams) {
		if (!(exams && exams.length > 0)) {
			return null;
		}
		var columns = [
			{ title: '作业名称', width: '80%', field: function(data, index) {
				if (data.correctNum > 0) {
					return <a className="taskname s-c-turquoise" href={`../../homework/overall/${data.homeworkId}.htm`} title={data.homeworkName}>{data.homeworkName}</a>;
				}
				return <span className="taskname" title={data.homeworkName}>{data.homeworkName}</span>;
			}},
			{ title: '平均分', width: '20%', field: function(data, index) {
				return toFixed(data.avgScore, 1);
			}}
		];
		return (
			<div>
				<div className="inner-title">{phase == 1 ? '预习' : '课后'}作业班级平均分</div>
				<div className="c-table">
					<SimpleTable columns={columns} datas={exams.slice(0, 5)} defVal="--" showHead={true} />
				</div>
				<div className="tips">点击作业名称可查看班级作业分析报告。</div>
			</div>
		);
	}
	render() {
		let {phase, subNames, overall, exams} = this.props;
		let {total, finish, portion, finish1, finish2, finish3} = overall;
		let unfinish = total - finish - portion;
		let title = phase == 1 ? '预习' : '复习';
		var items = [
			{ name : subNames[0], value : finish1 },
			{ name : subNames[1], value : finish2 },
			{ name : subNames[2], value : finish3 }
		];
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">{title}情况概览</div>
				<div className="inner-title">{title}总体完成情况</div>
				<ReactChart className="maps" option={ChartConfig.buildOverallFinishPie(finish, portion, unfinish)} />
				<article className="texts">
					<p className="paragraph">
						<span>本次{title}任务应完成人数<span className="fc-green">{total}</span>，</span>
						<span>已完成人数<span className="fc-green">{finish}</span>，比率<span className="fc-green">{toRate(finish, total, 1)}%</span>，</span>
						<span>部分完成人数<span className="fc-green">{portion}</span>，比率<span className="fc-green">{toRate(portion, total, 1)}%</span>，</span>
						<span>未完成人数<span className="fc-orange">{unfinish}</span>，比率<span className="fc-orange">{toRate(unfinish, total, 1)}%</span>。</span>
					</p>
				</article>
				<div className="inner-title">{title}分类完成情况</div>
				<ReactChart className="maps" option={ChartConfig.buildCategoryFinishRadar(total, items)} />
				<div className="sum-text">
					<p>{subNames[0]}{title}人数<span className="fc-green">{finish1}</span>，比率<span className="fc-green">{toRate(finish1, total, 1)}%</span>；</p>
					<p>{subNames[1]}{title}人数<span className="fc-green">{finish2}</span>，比率<span className="fc-green">{toRate(finish2, total, 1)}%</span>；</p>
					<p>{subNames[2]}{title}人数<span className="fc-green">{finish3}</span>，比率<span className="fc-green">{toRate(finish3, total, 1)}%</span>。</p>
				</div>
				{this.renderWork(phase, exams)}
			</section>
		);
	}
}

module.exports = ReviewOverview;
