import React, {Component, PropTypes} from 'react';
import Modal from './modal';

function fetchFileInfo(fileId) {
    let url = `/auth/global/fs/file/access/data.htm?id=${fileId}`;
    const dfd = $.Deferred();
    $.post(url).done((json) => {
        if (json && json.success) {
            dfd.resolve(json.datas.file)
        } else {
            dfd.reject(json.message);
        }
    }).fail(dfd.reject);
    return dfd;
}

export default class DocView extends Component {
    constructor(props) {
        super(props);
        this.state = {
            file: null,
            message: '数据加载中...'
        };
    }
    loadFileInfo() {
        const {attach} = this.props;
        fetchFileInfo(attach.fileId).done(file => {
            let message = '附件加载中...';
            if (file && file.status == -2) {
                message = '附件初始化...';
            } else if (file.status < 0) {
                message = `附件转换中 ${file.progress} / ${file.pageCount}`;
            } else if (file.status == 0) {
                message = `附件转换失败`;
            }
            this.setState({file, message});
            if (file.status < 0) {
                setTimeout(() => this.loadFileInfo(), 3000);
            }
        }).fail(err => {
            this.setState({message: err});
        });
    }
    componentDidMount() {
        this.loadFileInfo();
        this.myScroll = new IScroll(this.refs.box, {
            zoom: true,
            scrollX: true,
            scrollY: true,
            mouseWheel: true,
            wheelAction: 'zoom',
            disablePointer: true,
            disableMouse: true,
            disableTouch: false
        });
        $(this.refs.box).on('touchstart', function(e) {
            e.stopPropagation();
        });
    }
    handleLoadImg = () => {
        // 解决不能上下滚动的问题
        // 原理：图片没有加载成功时初始化IScroll，因为页面没有内容，它会认为不需要上下滚动。
        this.myScroll.refresh();
    }
    componentWillUnmount() {
        if (this.myScroll) {
            this.myScroll.destroy();
        }
        if (this.refs.box) {
            $(this.refs.box).off('touchstart');
        }
    }
    renderBody() {
        const {file, message} = this.state;
        if (file && file.status > 0) {
            const images = file.pages.map((page) => {
                if (page.status > 0) {
                    return <img key={page.index} src={page.paths[0]} onLoad={this.handleLoadImg} />
                } else {
                    return <div style={{height: 160, paddingTop: 60, textAlign: 'center'}}>该页转换失败</div>;
                }
            });
            return images;
        } else {
            return <div style={{paddingTop: 60, textAlign: 'center'}}>{message}</div>;
        }
    }
    render() {
        return (
            <div ref="box" className="c-docview" id="docview">
                <div>{this.renderBody()}</div>
            </div>
        );
    }
}
