import React, {Component, PropTypes} from 'react';
import PullToRefresh from './PullToRefresh';

export default class ListView extends Component {
    static defaultProps = {
        onRender: function(datas) {
            // return react element.
        },
        onRefresh: function(index, callback) {
            // index: this's page index.
            // callback: load success callback.
        },
        onNoData: function() {
            return <div className="scroll-tips"><span className="letalk"></span><span>搜索结果为空</span></div>;
            return <div key="nodata" className="ptr--tips">没有数据</div>;
        }
    }
    static propTypes = {
        onRender: PropTypes.func.isRequired,
        onRefresh: PropTypes.func.isRequired,

    }
    constructor(props) {
        super(props);
        this.state = {
            datas: [],
            index: 1,
            fetching: false,
            locked: false
        };
    }
    reload = () => {
        this.fetchData(true);
    }
    fetchData(first, onDone) {
        let {index, datas} = this.state;
        index = first ? 1 : index + 1;
        this.setState({
            index: index,
            fetching: true
        });
        this.props.onRefresh(index, (list) => {
            if (list && list.length > 0) {
                this.setState({
                    datas: first ? list : datas.concat(list),
                    index: index,
                    fetching: false,
                    locked: false
                });
            } else {
                this.setState({
                    datas: first ? list : datas,
                    fetching: false,
                    locked: true
                });
            }
            if (onDone) {
                onDone();
            }
        });
    }
    componentDidMount() {
        let that = this;
        this.ptr = PullToRefresh.init({
            mainElement: this.refs.ptrbox,
            triggerElement: this.refs.ptrbox,
            onInit: function() {
                that.fetchData(true);
            },
            onRefresh: function(callback) {
                that.fetchData(true, function() {
                    callback();
                });
            }
        });
    }
    componentDidUpdate() {
        if (this.state.index === 1) {
            $(this.refs.ptrbox).scrollTop(0);
        }
    }
    componentWillUnmount() {
        if (this.ptr) {
            this.ptr.destory();
        }
    }
    handleScroll = (e) => {
        e.preventDefault();
        const {fetching, locked} = this.state;
        if (!fetching && !locked) {
            const {ptrbox, databox} = this.refs;
            let scrollTop = $(ptrbox).scrollTop();
            let boxHeight = $(ptrbox).height();
            let cntHeight = $(databox).height();
            if (cntHeight - 30 <= boxHeight + scrollTop) {
                this.fetchData(false);
            }
        }
    }
    renderInfo() {
        let {datas, index, fetching, locked} = this.state;
        let {onNoData} = this.props;
        let doms = [];
        if (fetching && index > 1 && datas.length > 0) {
            doms.push(<div key="fetching" className="ptr--tips">努力加载中......</div>);
        }
        if (locked) {
            if (datas && index > 1 && datas.length) {
                doms.push(<div key="locked" className="ptr--tips">没有更多数据啦</div>);
            } else {
                doms.push(onNoData());
            }
        }
        return doms;
    }
    render() {
        let {datas} = this.state;
        let {onRender} = this.props;
        return (
            <div ref="ptrbox" className="ptr--main" onScroll={this.handleScroll}>
                <div ref="databox">{onRender(datas)}</div>
                <div ref="infobox">{this.renderInfo()}</div>
            </div>
        );
    }
}
