var React = require('common/react/react');
var ReactChart = require('common/react/ReactChart');
var Dialog = require('dialog');

class Section extends React.Component {
    handleClickHelp = () => {
        Dialog.open({
			title : '帮助',
			url : this.props.help
		});
    }
    render() {
        let {id, title, help, detail} = this.props;
        return (
            <section id={id} className="c-zoom">
                <div className="title">
                    <span>{title}</span>
                    {detail ? <a href={detail} target="_blank" className="detail">详情</a> : null}
                    {help ? <i className="help" onClick={this.handleClickHelp}></i> : null}
                </div>
                {this.props.children}
            </section>
        );
    }
}

module.exports = Section;
