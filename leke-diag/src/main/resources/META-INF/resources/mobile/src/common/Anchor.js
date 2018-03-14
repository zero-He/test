import React, {Component, PropTypes} from 'react';

export default class Anchor extends Component {
	constructor(props) {
		super(props);
		this.state = {
			display: false
		};
	}
	handleToggle = (e) => {
		e.stopPropagation();
		let display = this.state.display;
		this.setState({display: !display});
	}
	handleScrollTo(item, e) {
		e.stopPropagation();
		var elem = $('#' + item.link), analyse = $('.c-analyse'), body = $(document.body);
		var scrollTop = elem.offset().top - (parseInt(analyse.css('padding-top')) || 0);
		body.scrollTop(scrollTop);
	}
	render() {
		let that = this;
		if (this.state.display == false) {
			return <nav className="c-anchor" onClick={this.handleToggle}></nav>
		}
		let items = this.props.items;
		items = items.filter(v => $('#' + v.link).length > 0);
		return (
            <nav className="c-anchor" onClick={this.handleToggle}>
    			<div className="anchor">
    				<ul>{items.map(item => <li key={item.link} onClick={that.handleScrollTo.bind(that, item)}>{item.title}</li>)}</ul>
    				<i className="arrow"></i>
    			</div>
            </nav>
		);
	}
}

Anchor.propTypes = {
	items: PropTypes.array.isRequired
};
