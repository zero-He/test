var KnoFilterBox = require('../../../react/KnoFilterBox');
var SimpleTable = require('../../../react/SimpleTable');
var {toFixed, toRate} = require('../../../utils/number');
import Section from '../../../common/Section';

let columns = [
	{ title: '涉题数', width: '22%', field: 'totalNum' },
	{ title: '得分率', width: '25%', field: (item, index) => {return toFixed(item.selfRate, 1) + '%';} },
	{ title: '班级得分率', width: '28%', field: (item, index) => {return toFixed(item.classRate, 1) + '%';} },
	{ title: '掌握程度', width: '25%', field: (item, index) => {return <span className={`l-${item.level}`}></span>;} }
];

class KnoScoreInfo extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			levels : [1, 2, 3]
		};
		this.handleChangeLevel = this.handleChangeLevel.bind(this);
	}
	resolveKnoRates(knoRates) {
		knoRates.forEach(item => {
			if (item.selfRate >= 85) {
				item.level = 1;
			} else if (item.selfRate >= 50) {
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
		let {knoRates} = this.props;
		if (!(knoRates && knoRates.length)) {
			return null;
		}
		this.resolveKnoRates(knoRates);
		knoRates = knoRates.filter(v => levels.indexOf(v.level) >= 0);
		return (
			<Section id={this.props.id} title={this.props.title} help={`${Leke.domain.staticServerName}/pages/help-center/diag/zw-cd.html?_newtab=1`}>
				<KnoFilterBox levels={levels} onChange={this.handleChangeLevel} />
				<div className="c-table c-table-fixed">
					<SimpleTable caption={<caption>知识点名称</caption>} columns={columns} datas={[]} showHead={true} />
					{knoRates.map(data => <SimpleTable caption={<caption>{data.name}</caption>} columns={columns} datas={[data]} defVal="--" />)}
				</div>
			</Section>
		);
	}
}

module.exports = KnoScoreInfo;
