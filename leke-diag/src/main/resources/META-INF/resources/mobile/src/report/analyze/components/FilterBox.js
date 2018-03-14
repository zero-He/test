
function groupByOptions(options) {
	let groups = [];
	let options1 = options.filter(v => !(v.subs && v.subs.length));
	if (options1.length > 0) {
		groups.push({ subs: options1 });
	}
	options.filter(v => (v.subs && v.subs.length)).forEach(v => groups.push(v));
	return groups;
}

class FilterItem extends React.Component {
	render() {
		return null;
	}
}

FilterItem.propTypes = {
	options : React.PropTypes.array
};

class FilterBox extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			childs : [],
			showIdx : null
		};
	}
	componentWillMount() {
		let children = this.props.children;
		let childs = [];
		if (children.length) {
			children.filter(v => v != null).forEach(v => childs.push(v.props));
		} else {
			childs.push(children.props);
		}
		this.selectFirstItem(childs);
		this.setState({childs: childs});
	}
	selectFirstItem(childs) {
		childs.forEach(item => {
			let firstItem = item.options[0];
			if (firstItem.subs && firstItem.subs.length) {
				firstItem = firstItem.subs[0];
			}
			firstItem.selected = true;
			item.label = firstItem.label;
			item.value = firstItem.value;
		});
	}
	handleClick(index) {
		let showIdx = this.state.showIdx;
		this.setState({
			showIdx: showIdx !== index ? index : null
		});
	}
	handleChange(value) {
		let {childs, showIdx} = this.state;
		let child = childs[showIdx];
		if (child.value == value) {
			this.setState({
				showIdx : null
			});
			return;
		}
		child.options.forEach(item => {
			if (item.subs && item.subs.length) {
				item.subs.forEach(item2 => {
					item2.selected = item2.value === value;
					if (item2.selected) {
						child.label = item2.label;
						child.value = item2.value;
					}
				});
			} else {
				item.selected = item.value === value;
				if (item.selected) {
					child.label = item.label;
					child.value = item.value;
				}
			}
		});
		this.setState({
			childs : childs,
			showIdx : null
		});
		if (child.onChange) {
			child.onChange(child.value, child.label);
		}
	}
	componentDidUpdate() {
		let showIdx = this.state.showIdx;
		$(document.body).css('overflow', showIdx != null ? 'hidden' : 'auto');
	}
	render() {
		let that = this;
		let {childs, showIdx} = this.state;
		return (
			<section className="c-search">
				<ul className={childs.length == 4 ? 'title four' : 'title'}>
					{childs.map((item, i) => <li className={showIdx == i ? 'cur' : ''} onClick={this.handleClick.bind(this, i)}><span>{item.label}</span></li>)}
				</ul>
				{showIdx != null ? that.renderOptions(childs[showIdx].options, childs[showIdx].inlineBlock) : null}
			</section>
		);
	}
	renderOptions(options, inlineBlock) {
		let that = this;
		let groups = groupByOptions(options);
		var htmls = [];
		inlineBlock = inlineBlock || false;
		groups.forEach(group => {
			if (group.label) {
				htmls.push(<h5>{group.label}：</h5>);
			}
			let subs = group.subs.map(option => {
				let className = inlineBlock ? 'line ' : '';
				className += option.selected ? 'cur' : '';
				return <li className={className} onClick={that.handleChange.bind(that, option.value)}><span>{option.label}</span></li>;
			});
			htmls.push(<ul>{subs}</ul>)
		});
		return (
			<div className="selects">
				<div className="cover" onClick={this.handleClick.bind(this, null)}></div>
				<div className="classname">{htmls}</div>
			</div>
		);
	}
}

FilterBox.Item = FilterItem;

module.exports = FilterBox;