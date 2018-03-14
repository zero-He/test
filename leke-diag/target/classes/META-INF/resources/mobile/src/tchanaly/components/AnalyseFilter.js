import React, {Component} from 'react';

export default class AnalyseFilter extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeIdx: null
        };
    }
    handleClickTitle(index) {
        const {activeIdx} = this.state;
        if (activeIdx === index) {
            this.setState({activeIdx: null});
        } else {
            this.setState({activeIdx: index});
        }
    }
    handleClickItem(item) {
        const {activeIdx} = this.state;
        const {options, onChangeOptions} = this.props;
        options[activeIdx].current = item;
        this.setState({
            activeIdx: null
        });
        if (onChangeOptions) {
            onChangeOptions(options);
        }
    }
    handleClickCover = () => {
        this.setState({activeIdx: null});
    }
    renderItems(option, items, key) {
        const that = this;
        const nodes = items.map((item, idx) => {
            let className = '', handleClick = () => that.handleClickItem(item);
            if (item.value === option.current.value) {
                className = 'cur';
                handleClick = null;
            }
            return <li key={idx} className={className} onClick={handleClick}><span>{item.label}</span></li>;
        });
        return <ul key={key}>{nodes}</ul>;
    }
    renderOptions(option) {
        const nodes = [];
        if (option.hasGroup) {
            option.items.forEach(item => {
                nodes.push(<h5 key={`h${item.value}`}>{item.label}</h5>);
                nodes.push(this.renderItems(option, item.subs, item.value));
            });
        } else {
            nodes.push(this.renderItems(option, option.items, ''));
        }
        return nodes;
    }
    render() {
        const that = this;
        const {activeIdx} = this.state;
        const {options, filters} = this.props;
        return (
            <section className="c-search">
                <ul className="title">
                {options.map((option, idx) => {
                    if (filters.indexOf(option.field) < 0) {
                        return null;
                    }
                    return (
                        <li key={idx} className={idx === activeIdx ? 'cur' : ''} onClick={that.handleClickTitle.bind(that, idx)}>
                            <span>{option.current.label}</span>
                        </li>
                    );
                })}
                </ul>
                {activeIdx != null ?
                <div className="selects">
                    <div className="cover" onClick={this.handleClickCover}></div>
                    <div className="classname">
                        {this.renderOptions(options[activeIdx])}
                    </div>
                </div>
                : null
                }
            </section>
        );
    }
}
