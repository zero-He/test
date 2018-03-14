
class ToolTips extends React.Component {
	render() {
		return (
			<div className="m-tips">
				<span className="img"></span>
				<p className="msg">{this.props.message || this.props.children}</p>
			</div>
		);
	}
}

module.exports = ToolTips;

