var KnoFilterBox = require('../../../react/KnoFilterBox');
var SimpleTable = require('../../../react/SimpleTable');
var {toFixed, toRate} = require('../../../utils/number');
import Section from '../../../common/Section';

let columns = [
	{ title: '涉题数', width: '15%', field: 'totalNum' },
	{ title: '正确题数', width: '13%', field: (item, index) => toFixed(item.rightNum, 1) },
	{ title: '分值', width: '14%', field: (item, index) => toFixed(item.totalScore, 1) },
	{ title: '得分', width: '14%', field: (item, index) => toFixed(item.selfScore, 1) },
	{ title: '得分率', width: '14%', field: (item, index) => toFixed(item.selfRate, 1) + '%' },
	{ title: '班级得分率', width: '16%', field: (item, index) => toFixed(item.classRate, 1) + '%' },
	{ title: '掌握程度', width: '14%', field: (item, index) => <span className={`l-${item.level}`}></span> }
];

class KnoScoreInfo extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			levels : [1, 2, 3]
		};
		this.handleChangeLevel = this.handleChangeLevel.bind(this);
	}
	resolveKnoRates(knoGroups) {
		knoGroups.forEach(item => {
			item.totalNum = item.qids.length;
			item.selfRate = toRate(item.selfScore, item.totalScore, 1);
			item.classRate = toFixed(item.classScore * 100 / item.totalScore, 1);
			if (item.classRate >= 85) {
				item.level = 1;
			} else if (item.classRate >= 50) {
				item.level = 2;
			} else {
				item.level = 3;
			}
		});
	}
	handleChangeLevel(levels) {
		this.setState({levels: levels});
	}
	render() {
		let levels = this.state.levels;
		let {knoGroups, classId, subjectId} = this.props;
		if (!(knoGroups && knoGroups.length)) {
			return null;
		}
		this.resolveKnoRates(knoGroups);
		knoGroups = knoGroups.filter(v => levels.indexOf(v.level) >= 0);
		return (
			<Section id={this.props.id} title="知识点分析" help={`${Leke.domain.staticServerName}/pages/help-center/diag/zw-cd.html?_newtab=1`}>
				<KnoFilterBox levels={levels} onChange={this.handleChangeLevel} />
				<div className="c-table c-table-fixed">
					<SimpleTable caption={<caption>知识点名称</caption>} columns={columns} datas={[]} showHead={true} />
					{knoGroups.map(data => <SimpleTable caption={<caption>{data.name}</caption>} columns={columns} datas={[data]} defVal="--" />)}
				</div>
			</Section>
		);
	}
}

module.exports = KnoScoreInfo;
