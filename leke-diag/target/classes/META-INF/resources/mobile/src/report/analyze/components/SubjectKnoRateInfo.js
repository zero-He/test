var KnoFilterBox = require('../../../react/KnoFilterBox');
var SimpleTable = require('../../../react/SimpleTable');
var {toFixed, toRate} = require('../../../utils/number');

let columns = [
	{ title: '班级得分率', width: '50%', field: (item, index) => {return toFixed(item.classRate, 1) + '%';} },
	{ title: '掌握程度', width: '50%', field: (item, index) => {return <span className={`l-${item.level}`}></span>;} }
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
		let {knoRates} = this.props;
		if (!(knoRates && knoRates.length)) {
			return null;
		}
		this.resolveKnoRates(knoRates);
		knoRates = knoRates.filter(v => levels.indexOf(v.level) >= 0);
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">知识点掌握程度</div>
				<KnoFilterBox levels={levels} onChange={this.handleChangeLevel} />
				<div className="c-table c-table-fixed">
					<SimpleTable caption={<caption>知识点名称</caption>} columns={columns} datas={[]} showHead={true} />
					{knoRates.map(data => <SimpleTable caption={<caption>{data.name}</caption>} columns={columns} datas={[data]} defVal="--" />)}
				</div>
			</section>
		);
	}
}

module.exports = KnoScoreInfo;