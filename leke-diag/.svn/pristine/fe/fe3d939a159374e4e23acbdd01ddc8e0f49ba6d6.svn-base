import React, {Component} from 'react';

const navs = [
    {title: '班级成绩分析', viewName: 'klass-score'},
    {title: '班级行为分析', viewName: 'klass-behavior'},
    {title: '教学行为分析', viewName: 'teach-behavior'},
]

export default class AnalyseNavTab extends Component {
    render() {
        const {store, actions} = this.props;
        return (
            <nav className="c-nav">
                <ul>
                {navs.map(nav => {
                    const className = nav.viewName === store.control.viewName ? 'cur' : '';
                    const handleClick = () => actions.onChangeView(nav.viewName);
                    return <li key={nav.viewName} className={className} onClick={handleClick}>{nav.title}</li>;
                })}
                </ul>
            </nav>
        );
    }
}
