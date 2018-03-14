import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import isFunction from 'lodash/isFunction';
import partialRight from 'lodash/partialRight';
import forEach from 'lodash/forEach';

export default class ReactAudio extends Component {
    static propTypes = {
        src: PropTypes.string,
        onTimeupdate: PropTypes.func,
        onError: PropTypes.func,
        onProgress: PropTypes.func,
        onEnded: PropTypes.func
    };
    static defaultProps = {
        src: "",
        onTimeupdate: null,
        onError: null,
        onProgress: null,
        onEnded: null
    };
    constructor(props) {
        super(props);
        this.state = {
            listeners: []
        };
    }
    handler(e, func) {
        if (isFunction(func)) {
            func(e);
        }
    }
    addListener = (event, func) => {
        var audio = ReactDOM.findDOMNode(this.refs.audio);
        audio.addEventListener(event, partialRight(this.handler, func));
        this.state.listeners.push({event: event, func: func});
    }
    removeAllListeners = () => {
        var audio = ReactDOM.findDOMNode(this.refs.audio);
        forEach(this.state.listeners, (obj) => {
            audio.removeEventListener(obj.event, obj.func);
        });
        this.state.listeners = [];
    }
    componentDidMount() {
        this.addListener('timeupdate', this.props.onTimeupdate);
        this.addListener('progress', this.props.onProgress);
        this.addListener('error', this.props.onError);
        this.addListener('ended', this.props.onEnded);
        this.addListener('loadeddata', this.props.onLoadedData);
    }
    componentWillUnmount() {
        this.removeAllListeners();
    }
    componentWillReceiveProps(nextProps) {
        if (nextProps.autoplay === true && this.props.autoplay === false) {
            this.refs.audio.play();
        }
    }
    togglePlay = () => {
        let that = this;
        let onPlayProgress = this.props.onPlayProgress;
        let audio = this.refs.audio;
        if (audio.paused) {
            audio.play();
            if (onPlayProgress) {
                that.timer = setInterval(function() {
                    onPlayProgress(audio.currentTime, audio.duration);
                }, 100);
            }
        } else {
            audio.pause();
            if (onPlayProgress) {
                clearInterval(that.timer);
                that.timer = null;
            }
        }
    }
    setPlayPosition(position) {
        this.refs.audio.currentTime = position;
    }
    setPlayPercent(percent) {
        this.refs.audio.currentTime = percent * this.refs.audio.duration;
    }
    getAudioDom = () => {
        return ReactDOM.findDOMNode(this.refs.audio);
    }
    render() {
        return <audio ref="audio" crossOrigin="anonymous" controls={false} autoPlay={false} src={this.props.src} />;
    }
}
