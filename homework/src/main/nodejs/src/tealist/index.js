import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import ListView from '../common/ListView';
import {fmtCount} from '../common/fmt';
import WorkItem from './workitem';

// 搜索框
class SearchBox extends Component {
    constructor(props) {
        super(props);
        this.state = {
            del: false
        };
    }
    handleSearch = () => {
        const {onKeywordSearch} = this.props;
        const keyword = this.refs.text.value;
        if (onKeywordSearch) {
            onKeywordSearch(keyword);
        }
    }
    handleDelete = () => {
        this.setState({del: false});
        this.refs.text.value = '';
        this.refs.text.focus();
    }
    handleKeyUp = (evt) => {
        if (evt.keyCode == 13) {
            evt.target.blur();
            this.handleSearch();
        }
        this.setState({del: evt.target.value != ''});
    }
    componentDidMount() {
        $(this.refs.form).submit(function() {
            return false;
        });
    }
    render() {
        let {del} = this.state;
        return (
            <nav className="c-search-box">
                <div className="input-wrapper">
                    <form ref="form">
                    <input ref="text" type="search" onKeyUp={this.handleKeyUp} placeholder="请输入作业标题" />
                    </form>
                </div>
                <i className="search"></i>
                {del ? <i className="delete" onClick={this.handleDelete}></i> : null}
            </nav>
        );
    }
}

// 过滤选项
class SearchFilter extends Component {
    handleSearch(status) {
        const {onStatusSearch} = this.props;
        if (onStatusSearch) {
            onStatusSearch(status);
        }
    }
    render() {
        const {query} = this.props;
        const unfinish = fmtCount(query.stats.unfinish);
        return (
            <div className="c-search-filter">
                <ul>
                    {query.state === 0 ? <li className="current">全部</li> : <li onClick={this.handleSearch.bind(this, 0)}>全部</li>}
                    {query.state === 1 ? <li className="current">待批改{unfinish}</li> : <li onClick={this.handleSearch.bind(this, 1)}>待批改{unfinish}</li>}
                </ul>
            </div>
        );
    }
}

export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
	        teacherId: Csts.teacherId,
            keyword: '',
            state: 0,
            counter: true,
            pageSize: 10,
            stats: {
                total: 0,
                unfinish: 0,
                unbugfix: 0
            }
        }
    }
    handleKeywordSearch = (keyword) => {
        this.setState({
            ...this.state,
            keyword,
            counter: true
        }, () => {
            this.refs.listview.reload();
        });
    }
    handleStatusSearch = (status) => {
        this.setState({
            ...this.state,
            state: status
        }, () => {
            this.refs.listview.reload();
        });
    }
    handleRefresh = (index, callback) => {
        let state = this.state;
        let query = {
            ...state,
            curPage : index
        };
        webapi.fetchDatas(query).done(json => {
            let {list, count: stats} = json.datas;
            if (stats) {
                this.setState({stats});
            }
            callback(list);
        });
    }
    render() {
        return (
            <div className="c-worklist">
                <SearchBox query={this.state} onKeywordSearch={this.handleKeywordSearch} />
                <SearchFilter query={this.state} onStatusSearch={this.handleStatusSearch} />
                <div className="c-scroll-box">
                    <ListView ref="listview" onRefresh={this.handleRefresh} onRender={(datas) => {
                        return <div>{datas.map((data, i) => <WorkItem key={i} data={data} />)}</div>;
                    }} />
                </div>
            </div>
        );
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('app')
);
