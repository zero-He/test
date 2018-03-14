import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import Modal from './modal';
import {PlayState} from './player';
import {uploadAudio, uploadAudioFile, downloadAsCached, readFileAsB64, copyFileToCache, parseFileName} from './file';
import padStart from 'lodash/padStart';
import {fmtTime} from './fmt';

let fileIdx = 1;
const isIos = /iphone|ipod|ipad/gi.test(navigator.userAgent);
const suffix = isIos ? '.m4a' : '.wav';
function randomFileName() {
    fileIdx = fileIdx == 0 ? 1 : 0;
    return `letalk_hw_record_${fileIdx}${suffix}`;
}

const maxLength = isIos ? 180 : 420;

export default class Recorder extends Component {
    constructor(props) {
        super(props);
        this.state = {
            record: false,
            progress: null,
            filePath: null
        };
        this.locked = false;
    }
    startRecord = () => {
        const that = this;
        const {onAnswerOral} = this.props;
        if (this.locked === false && PlayState.play()) {
            this.locked = true;
            let tooshort = true;
            const filePath = randomFileName();
            that.media = new Media(filePath, () => {
                if (tooshort) {
                    Modal.toast('录音时间太短');
                    return;
                }
                let path = (isIos ? cordova.file.tempDirectory : cordova.file.externalRootDirectory) + filePath;
                Modal.showLoading();
                uploadAudioFile(path).done((json) => {
                    if (json.success) {
                        if (isIos) {
                            that.setState({filePath});
                            onAnswerOral(json.datas.url1);
                            Modal.hideLoading();
                            return;
                        }
                        copyFileToCache(path, json.datas.url1).done(() => {
                        //    that.setState({progress: null});
                            onAnswerOral(json.datas.url1);
                            Modal.hideLoading();
                        }).fail((err) => {
                            console.log(err);
                        //    that.setState({progress: null});
                            onAnswerOral(json.datas.url1);
                            Modal.hideLoading();
                        });
                    } else {
                    //    that.setState({progress: null});
                        Modal.hideLoading();
                        Modal.toast('文件上传失败，请重新录音');
                    }
                }).progress(e => {
                //    that.setState({progress: Math.round(e.loaded * 100 / e.total)});
                //    that.setState({progress: e.loaded + '  /  ' + e.total});
                }).fail(err => {
                    console.log(err);
                //    that.setState({progress: null});
                    Modal.hideLoading();
                    Modal.toast('文件上传失败，请重新录音');
                });
            }, err => {
                console.log(err);
                PlayState.stop();
                Modal.toast('录音失败');
            }, code => {
                if (code == 2) {
                    that.setState({record: true, filePath});
                    that.locked = false;
                    that.timer = setTimeout(() => {
                        // 少于1秒太短
                        tooshort = false;
                        that.timer = setTimeout(() => {
                            // 长于420秒太长
                            that.timer = null;
                            Modal.toast(`录音时长不能超过${maxLength}秒`);
                            that.stopRecord();
                        }, (maxLength - 1) * 1000);
                    }, 1000);
                }
            });
            that.media.startRecord();
        }
    }
    stopRecord = () => {
        if (this.state.record) {
            PlayState.stop();
            this.setState({record: false});
            this.release();
        }
    }
    release() {
        if (this.timer) {
            clearTimeout(this.timer);
        }
        if (this.media) {
            this.media.stopRecord();
            this.media = null;
        }
    }
    componentWillUnmount() {
        this.release();
        if (this.state.record === true) {
            PlayState.stop();
        }
    }
    render() {
        const {record, filePath, progress} = this.state;
        const {fileUrl} = this.props;
        return (
            <div className="c-recorder">
                <div className="record-tips">{progress}</div>
                {record
                    ? <div className="record-on" onClick={this.stopRecord}><Volume /></div>
                    : <div className="record-off" onClick={this.startRecord}></div>
                }
                <PlayerUI fileUrl={fileUrl} filePath={filePath} locked={record} />
            </div>
        );
    }
}

class Volume extends Component {
    render() {
        return (
            <ul className="volume volume-lg">
                <li className="eins"></li>
                <li className="zwei"></li>
                <li className="drei"></li>
                <li className="vier"></li>
                <li className="fuenf"></li>
            </ul>
        );
    }
}

class PlayerUI extends Component {
    constructor(props) {
        super(props);
        this.state = {
            locked: false,
            play: false
        };
    }
    componentWillReceiveProps(nextProps) {
        if (nextProps.locked === true && this.props.locked === false) {
            this.pause();
        }
        if (nextProps.fileUrl != this.props.fileUrl) {
            this.release();
        }
    }
    play = () => {
        if (PlayState.play()) {
            this.setState({play: true});
            if (this.media == null) {
                const that = this;
                const {fileUrl, filePath} = this.props;
                if (filePath) {
                    that._initAndPlay(filePath);
                    return;
                }
                downloadAsCached(fileUrl).done((filePath) => {
                    that._initAndPlay(filePath);
                });
            } else {
                this.media.play();
                this.setState({play: true});
            }
        }
    }
    _initAndPlay(filePath) {
        const that = this;
        that.media = new Media(filePath, null, err => {
            console.log(err);
            PlayState.stop();
            that.setState({play: false});
            Modal.toast('播放失败');
        }, code => {
            if (code == 4) {
                PlayState.stop();
                that.setState({play: false});
            }
        });
        that.setState({play: true});
        that.media.play();
    }
    pause = () => {
        if (this.media && this.state.play) {
            PlayState.stop();
            this.media.pause();
            this.setState({play: false});
        }
    }
    componentWillUnmount() {
        if (this.state.play) {
            PlayState.stop();
        }
        this.release();
    }
    release() {
        if (this.media) {
            this.media.release();
            this.media = null;
        }
    }
    render() {
        const {fileUrl, filePath, locked} = this.props;
        if (locked || !(fileUrl || filePath)) {
            return null;
        }
        const {play} = this.state;
        if (play) {
            return <div className="player-on" onClick={this.pause}></div>;
        } else {
            return <div className="player-off" onClick={this.play}></div>;
        }
    }
}
