import React, {Component, PropTypes} from 'react';
import {parseImgUrls} from './htmlparser';
import Modal from './modal';

export default class RichView extends Component {
    constructor(props) {
        super(props);
        this.state = {
            imgs: parseImgUrls(props.html)
        };
    }
    componentWillReceiveProps(nextProps) {
        if (nextProps.html != this.props.html) {
            this.setState({
                imgs: parseImgUrls(nextProps.html)
            });
        }
    }
    componentDidMount() {
        const that = this;
        $(this.refs.box).on('click', 'img', function(e) {
            e.stopPropagation();
            const {imgs} = that.state;
            const src = $(this).attr('src');
            const index = imgs.indexOf(src);
            Modal.openModal(<ImageZoom imgs={imgs} index={index} />);
        });
    }
    render() {
        const {html} = this.props;
        return <div ref="box" dangerouslySetInnerHTML={{__html: html}} onClick={this.handleClick}></div>;
    }
}

function parseTransform(el) {
    const transform = $(el).css('transform');
    const matchs = transform.match(/translate\((-?\d+)px, (-?\d+)px\) scale\((\d)\)/);
    if (matchs) {
        const transX = parseFloat(matchs[1]);
        const transY = parseFloat(matchs[2]);
        const scale = parseFloat(matchs[3]);
        return {transX, transY, scale};
    }
    return {};
}

const ZOOM_MIN_VAL = 1.01;
const SWIPE_THRESHOLD = 25;

class ImageZoom extends Component {
    constructor(props) {
        super(props);
        this.state = {
            index: props.index
        };
    }
    componentDidMount() {
        const that = this;
        const myScroll = new IScroll('#imagezoom', {
            zoom: true,
            zoomMin: ZOOM_MIN_VAL,
            startZoom: ZOOM_MIN_VAL,
            scrollX: true,
            scrollY: true,
            mouseWheel: true,
            wheelAction: 'zoom',
            disablePointer: true,
            disableMouse: true,
            disableTouch: false
        });
        let timer = null;
        myScroll.on('scrollStart', () => {
            if (timer == null) {
                timer = setInterval(() => {
                    const {x, scale, maxScrollX} = myScroll;
                    const threshold = scale > 1.01 ? SWIPE_THRESHOLD : 0;
                    console.log(`x: ${x}, scale: ${scale}, maxScrollX: ${maxScrollX}, threshold: ${threshold}, ${x > threshold}, ${x < (maxScrollX - threshold)}`);
                    if (x >= threshold) {
                        clearInterval(timer);
                        that.handleSwipePrev();
                    } else if (x <= (maxScrollX - threshold)) {
                        clearInterval(timer);
                        that.handleSwipeNext();
                    }
                }, 30);
            }
        });
        myScroll.on('scrollEnd', () => {
            if (timer) {
                clearInterval(timer);
                timer = null;
            }
        });
        this.myScroll = myScroll;
    }
    handleSwipeNext() {
        const {imgs} = this.props;
        const {index} = this.state;
        if (index < imgs.length - 1) {
            this.setState({index: index + 1});
            this.myScroll.zoom(ZOOM_MIN_VAL);
        }
    }
    handleSwipePrev() {
        const {imgs} = this.props;
        const {index} = this.state;
        if (index > 0) {
            this.setState({index: index - 1});
            this.myScroll.zoom(ZOOM_MIN_VAL);
        }
    }
    componentWillUnmount() {
        if (this.myScroll) {
            this.myScroll.destroy();
        }
    }
    handleClick = () => {
        Modal.closeModal();
    }
    render() {
        const {index} = this.state;
        const {imgs} = this.props;
        return (
            <div ref="body">
                <div className="c-image-zoom" id="imagezoom">
                    <div>
                        {imgs.map((url, i) => <img style={{display: i === index ? 'block' : 'none'}} src={url} />)}
                    </div>
                    <i className="close" onTouchStart={this.handleClick}></i>
                </div>
            </div>
        )
    }
}
