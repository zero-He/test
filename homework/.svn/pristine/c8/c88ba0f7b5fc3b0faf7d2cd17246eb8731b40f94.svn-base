import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import Modal from './modal';
import {downloadAsCached} from './file';
import padStart from 'lodash/padStart';
import {fmtTime} from './fmt';

export const PlayState = {
    playing: false,
    play: function() {
        if (this.playing) {
            Modal.toast('正在播放/录音中...');
            return false;
        } else {
            this.playing = true;
            return true;
        }
    },
    stop: function() {
        this.playing = false;
    }
};

const STATUS_INIT = 'init';
const STATUS_PLAY = 'play';
const STATUS_PAUSE = 'pause';

function stop(e) {
    e.stopPropagation();
}

export default class Player extends Component {
    constructor(props) {
        super(props);
        this.state = {
            status: STATUS_INIT,
            duration: 0,
            position: 0,
            percent: null
        };
    }
    componentWillMount() {
        const that = this;
        const {source} = this.props;
        downloadAsCached(source).done(filePath => {
            that.initMedia(filePath);
            that.setState({status: STATUS_PAUSE});
        }).fail(() => Modal.toast('文件下载失败'));
    }
    initMedia = (filePath) => {
        const that = this;
        that.media = new Media(filePath, function(data) {
        }, function(err) {
            PlayState.stop();
            that.setState({status: STATUS_PAUSE});
            Modal.toast('语音播放出错');
        }, function(code) {
            if (code === 2) {
                that.bindTimer();
            }
            if (code == 3 || code === 4) {
                PlayState.stop();
                that.unbindTimer();
                that.setState({status: STATUS_PAUSE});
            }
        });
    }
    bindTimer() {
        const that = this;
        that.unbindTimer();
        that.timer = setInterval(() => {
            this.media.getCurrentPosition(position => {
                if (position > 0) {
                    that.setState({position});
                }
                const duration = that.media.getDuration();
                if (duration > 0) {
                    that.setState({duration});
                }
            })
        }, 100);
    }
    unbindTimer() {
        if (this.timer) {
            clearInterval(this.timer);
        }
    }
    handleToggle = () => {
        let {status} = this.state;
        if (status === STATUS_INIT) {
            Modal.toast('文件正在下载中');
        } else if (status === STATUS_PAUSE) {
            if (PlayState.play()) {
                this.setState({status: STATUS_PLAY});
                this.media.play();
            }
        } else if (status === STATUS_PLAY) {
            PlayState.stop();
            this.setState({status: STATUS_PAUSE});
            this.media.pause();
        }
    }
    componentDidMount() {
        const that = this;
        const {player, bar, point} = this.refs, maxWidth = $(bar).width();
        let startX, startP;
        $(point).on('touchstart', function(e) {
            if (that.state.status == STATUS_PLAY) {
                startP = parseFloat($(point).css('left'));
                startX = e.touches[0].clientX;
                e.preventDefault();
            }
        }).on('touchmove', function(e) {
            if (startX && startP) {
                let percent = startP + (e.touches[0].clientX - startX) * 100 / maxWidth;
                percent = percent > 100 ? 100 : percent;
                percent = percent < 0 ? 0 : percent;
                that.setState({percent: percent});
                e.preventDefault();
            }
        }).on('touchend', function(e) {
            if (startX && startP) {
                startX = null; startP = null;
                const {percent, duration} = that.state;
                let position = duration * percent / 100;
                position = percent == 0 ? 1 : position;
                position = percent == 100 ? position - 1 : position;
                that.media.seekTo(position * 1000);
                that.setState({
                    position: position,
                    percent: null
                });
            }
        });
        $(player).on('touchstart', stop).on('touchmove', stop).on('touchend', stop);
    }
    componentWillUnmount() {
        if (this.state.status == STATUS_PLAY) {
            PlayState.stop();
        }
        if (this.timer) {
            clearInterval(this.timer);
        }
        if (this.media) {
            this.media.release();
        }
    }
    render() {
        let {status, duration, position, percent} = this.state;
        if (percent == null) {
            percent = position * 100 / duration;
        }
        status = (status == STATUS_INIT ? STATUS_PAUSE : status);
        return (
            <div ref="player" className="c-player">
                <button className={`control control-${status}`} onClick={this.handleToggle}></button>
                <div className="c-player-progress">
                    <div ref="bar" className="bar">
                        <span style={{width: `${percent}%`}}></span>
                    </div>
                    <div ref="point" className="point" style={{left: `${percent}%`}}>
                        <span></span>
                    </div>
                </div>
                <div className="c-player-time">
                    <span className="current">{fmtTime(position)}</span>
                    <span>/</span>
                    <span className="total">{fmtTime(duration)}</span>
                </div>
            </div>
        );
    }
}

// https://file.leke.cn/group1/M00/10/20/wKgURFjDyaeAea6hAAfo_xfq_Ks510.mp3
// https://file.leke.cn/group1/M00/10/20/wKgURFjDyNmAFzy5AEAaEmqVzrA723.mp3
// 线上转码后：https://file.leke.cn/group1/M00/10/21/wKgURFjFBZ2AKdzfAAfnzbyDZHk238.mp3
