
class KnoFilterBox extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			levels: props.levels || [1, 2, 3]
		};
	}
	handleChange(val) {
		let levels = this.state.levels;
		if (levels.indexOf(val) >= 0) {
			levels = levels.filter(v => v != val);
		} else {
			levels.push(val);
		}
		this.setState({ levels: levels });
		if (this.props.onChange) {
			this.props.onChange(levels);
		}
	}
	render() {
		let levels = this.state.levels;
		return (
			<ul className="filter">
				<li className={levels.indexOf(1) >= 0 ? 'selected' : ''} onClick={this.handleChange.bind(this, 1)}><i className="icon"></i><span className="l-1"></span> 已掌握</li>
				<li className={levels.indexOf(2) >= 0 ? 'selected' : ''} onClick={this.handleChange.bind(this, 2)}><i className="icon"></i><span className="l-2"></span> 掌握不牢</li>
				<li className={levels.indexOf(3) >= 0 ? 'selected' : ''} onClick={this.handleChange.bind(this, 3)}><i className="icon"></i><span className="l-3"></span> 未掌握</li>
			</ul>
		);
	}
}

module.exports = KnoFilterBox;