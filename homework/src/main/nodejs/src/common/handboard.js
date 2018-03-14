import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import {uploadImg} from './file';
import CanvasPolyfill from './handboard-canvas-polyfill';
import Modal from './modal';

function getPosition(evt, target) {
    const rect = target.getBoundingClientRect();
    const x = evt.touches[0].pageX - rect.left;
    const y = evt.touches[0].pageY - rect.top;
    return {x, y};
}

function getBoxSize(bgImg, width, height) {
    let w = bgImg.width, h = bgImg.height;
    if (w > width || h > height) {
        var scale = Math.max(w / width, h / height);
        w = w / scale, h = h / scale;
    }
    return {w, h};
}

class HandBoardUI extends Component {
    constructor(props) {
        super(props);
        this.state = {
            width: 0,
            height: 0
        };
    }
    componentWillMount() {
        const width = Math.floor($(window).width() - 32);
        const height = Math.floor($(window).height() / 2);
        this.setState({width, height});
        Modal.showLoading();// 图片加载完成后关闭
    }
    componentDidMount() {
        this.ctx2d = this.refs.canvas.getContext('2d');
        this.ctx2d.lineWidth = 2;
        this.ctx2d.strokeStyle = '#000';
        this.ctx2d.fillStyle = '#fff';
    }
    componentWillUnmount() {
        this.ctx2d = null;
    }
    handleTouchStart = (evt) => {
        var pos = getPosition(evt, this.refs.canvas);
        this.ctx2d.beginPath();
        this.ctx2d.moveTo(pos.x, pos.y);
        this.touching = true;
    }
    handleTouchMove = (evt) => {
        evt.preventDefault();
        if (this.touching == false) {
            this.handleTouchStart(evt);
        }
        var pos = getPosition(evt, this.refs.canvas);
        this.ctx2d.lineTo(pos.x, pos.y);
        this.ctx2d.stroke();
    }
    handleTouchEnd = (evt) => {
        this.touching = false;
    }
    handlePluginClean = () => {
        this.ctx2d.fillRect(0, 0, this.state.width, this.state.height);
        const bgImg = this.refs.bgImg, ratio = this.ctx2d._ratio;
        const boxSize = getBoxSize(bgImg, this.state.width, this.state.height);
        this.ctx2d.drawImage(bgImg, 0, 0, boxSize.w * ratio, boxSize.h * ratio);
        Modal.hideLoading();
    }
    handlePluginSubmit = () => {
        const {onSuccess, onFailed} = this.props;
        Modal.showLoading();
        let dataUrl = this.refs.canvas.toDataURL('image/jpeg');
        dataUrl = dataUrl.substring('data:image/jpeg;base64,'.length);
        uploadImg(dataUrl).done(json => {
            if (json.success) {
                const url = json.datas.url1;
                onSuccess(url);
            } else {
                Modal.toast(json.message);
                onFailed();
            }
            Modal.hideLoading();
        }).fail(() => {
            Modal.hideLoading();
            Modal.toast('图片上传失败，请检查你的网络');
            onFailed();
        });
    }
    handlePluginCancel = () => {
        Modal.closeModal();
    }
    handleImgError = () => {
        Modal.hideLoading();
        Modal.toast('图片加载失败，请检查你的网络');
        Modal.closeModal();
    }
    render() {
        const {imgUrl} = this.props;
        const {width, height} = this.state;
        return (
            <div className="c-handboard"
                onTouchStart={this.handleTouchStart}
                onTouchMove={this.handleTouchMove}
                onTouchEnd={this.handleTouchEnd}>
                <div className="tools">
                    <span className="cancel" onClick={this.handlePluginCancel}></span>
                    <span className="clean" onClick={this.handlePluginClean}></span>
                    <span className="submit" onClick={this.handlePluginSubmit}></span>
                </div>
                <div className="board" style={{height, width}}>
                    <img ref="bgImg" crossOrigin="anonymous" src={imgUrl} onLoad={this.handlePluginClean} onError={this.handleImgError}
                             style={{maxHeight: height, maxWidth: width, display: 'none'}} />
                    <canvas ref="canvas"
                            width={width}
                            height={height}>
                    </canvas>
                </div>
            </div>
        );
    }
}

export default function handboard(imgUrl) {
    var deferred = $.Deferred();
    function onSuccess(url) {
        deferred.resolve(url);
        Modal.closeModal();
    }
    function onFailed() {
        deferred.reject();
    }
    Modal.openModal(<HandBoardUI imgUrl={imgUrl} onSuccess={onSuccess} onFailed={onFailed} />);
    return deferred;
}
