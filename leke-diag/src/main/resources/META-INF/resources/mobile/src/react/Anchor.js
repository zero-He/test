
class Anchor extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			display: false
		};
		this.handleToggle = this.handleToggle.bind(this);
	}
	handleToggle() {
		let display = this.state.display;
		this.setState({display: !display});
	}
	handleScrollTo(item, e) {
		e.stopPropagation();
		// this.setState({display: false});
		var elem = $('#' + item.link), analyse = $('.c-analyse'), body = $(document.body);
		var scrollTop = elem.offset().top - (parseInt(analyse.css('padding-top')) || 0);
		body.scrollTop(scrollTop);
	}
	render() {
		let that = this;
		return (
			<nav className="c-anchor" onClick={this.handleToggle}>
				{that.renderItems()}
			</nav>
		);
	}
	renderItems() {
		let that = this;
		let display = this.state.display;
		let items = this.props.items;
		if (display == false) {
			return null;
		}
		items = items.filter(v => $('#' + v.link).length > 0);
		return (
			<div className="anchor">
				<ul>{items.map(item => <li onClick={that.handleScrollTo.bind(that, item)}>{item.title}</li>)}</ul>
				<i className="arrow"></i>
			</div>
		);
	}
}

module.exports = Anchor;

