
var Achieves = [
	[1, '全勤'],
	[2, '提前预习'],
	[3, '专心听讲'],
	[4, '勤思好问'],
	[5, '活跃分子'],
	[11, '榜上有名'],
	[6, '手有余香'],
	[7, '及时改错'],
	[8, '不磨蹭'],
	[9, '练习达人'],
	[10, '温故知新']
];

class AchievementInfo extends React.Component {
	render() {
		let {types} = this.props.view.achievement;
		return (
			<section id={this.props.id} className="ana-module">
				<div className="title">成就<a href={`${Leke.domain.staticServerName}/pages/help-center/diag/stu-behavior.html?_newtab=1`}><i className="c-help"></i></a></div>
				<div className="achievement">
					<ul>{Achieves.map(v => <li className={types.indexOf(v[0]) >= 0 ? 'light' : ''}><i></i><span>{v[1]}</span></li>)}</ul>
				</div>
			</section>
		);
	}
}

module.exports = AchievementInfo;
