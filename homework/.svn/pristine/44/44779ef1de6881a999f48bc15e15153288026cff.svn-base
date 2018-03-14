import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import {uploadImg} from '../common/file';

function getPosition(evt) {
    const rect = evt.target.getBoundingClientRect();
    const x = evt.touches[0].pageX - rect.left;
    const y = evt.touches[0].pageY - rect.top;
    return {x, y};
}

class HandBoard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            inited: false
        };
        this.ctx2d = null;
        this.last = null;
    }
    handleClean = () => {
        const {canvas} = this.refs;
        this.ctx2d.clearRect(0, 0, canvas.width, canvas.height);
    }
    handleSubmit = () => {
        const {canvas} = this.refs;
        var dataUrl = canvas.toDataURL();
        dataUrl = dataUrl.substring(dataUrl.indexOf(';base64,') + 8);
        uploadImg(dataUrl).then((json) => {
            console.log(json);
        });
    }
    handleInit = () => {
        const {baseImg, canvas} = this.refs;
        canvas.width = baseImg.offsetWidth;
        canvas.height = baseImg.offsetHeight;
        this.ctx2d = canvas.getContext('2d');
        this.ctx2d.lineWidth = 2;
        this.ctx2d.strokeStyle = '#000';
        this.setState({ inited: true });
    }
    handleTouchStart = (evt) => {
        const pos = getPosition(evt);
        this.ctx2d.beginPath();
        this.ctx2d.moveTo(pos.x, pos.y);
        evt.preventDefault();
    }
    handleTouchMove = (evt) => {
        const pos = getPosition(evt);
        if (this.last) {
            this.ctx2d.lineTo(pos.x, pos.y);
            this.ctx2d.stroke();
        }
        this.last = pos;
        evt.preventDefault();
    }
    handleTouchEnd = (evt) => {
        this.last = null;
        evt.preventDefault();
    }
    componentDidMount() {
    }
    componentWillUnmount() {
        this.ctx2d = null;
        this.last = null;
    }
    render() {
        const {img} = this.props;
        return (
            <div className="c-handwrite">
                <div className="tools">
                    <span className="clear" onClick={this.handleClean}>CN</span>
                    <span className="submit" onClick={this.handleSubmit}>OK</span>
                </div>
                <div ref="board" className="board">
                    <img ref="baseImg" src={img} onLoad={this.handleInit} />
                    <canvas ref="canvas"
                        onTouchStart={this.handleTouchStart}
                        onTouchMove={this.handleTouchMove}
                        onTouchEnd={this.handleTouchEnd}>
                    </canvas>
                </div>
            </div>
        );
    }
}

ReactDOM.render(<HandBoard img="https://static.leke.cn/images/mobile/homework/paper.png" />, document.getElementById('app'));
