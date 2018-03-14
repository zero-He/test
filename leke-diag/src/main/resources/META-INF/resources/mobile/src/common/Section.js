import React, {Component, PropTypes} from 'react';

export default class Section extends Component {
    render() {
        let {id, title, help, detail} = this.props;
        return (
            <section id={id} className="ana-module">
                <div className="title">
                    <span>{title}</span>
                    {detail ? <a href={detail} className="fc-green m-seedetail">详情</a> : null}
                    {help ? <a href={help}><i className="c-help"></i></a> : null}
                </div>
                {this.props.children}
            </section>
        );
    }
}
