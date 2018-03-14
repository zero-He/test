import React, {Component, PropTypes} from 'react';

let lastTopHeight;

export class FrameSet extends Component {
    componentDidMount() {
        let frameset = $(this.refs.frameset),
            frametop = $(this.refs.frametop),
            framebottom = $(this.refs.framebottom),
            drag = $(this.refs.drag);
        let height = frameset.height();
        let topHeight = Math.floor(height * 0.4);
        if (lastTopHeight) {
            topHeight = lastTopHeight;
        }
        frametop.height(topHeight);
        framebottom.css({height: height - topHeight, top: topHeight});

        let start;
        drag.on('touchstart', function(e) {
            var touch = e.touches[0];
            start = touch.clientY;
            topHeight = frametop.height();
            return false;
        }).on('touchmove', function(e) {
            var touch = e.touches[0];
            let current = touch.clientY;
            let top = topHeight - (start - current);
            if (top < 18) {
                top = 18;
            } else if (top > height) {
                top = height;
            }
            frametop.height(top);
            framebottom.css({height: height - top, top: top});
            return false;
        }).on('touchend', function(e) {
            lastTopHeight = frametop.height();
            return false;
        });
    }
    render() {
        let {children} = this.props;
        return (
            <div ref="frameset" className="c-frameset">
                <div ref="frametop" className="c-frame-top">
                    <div className="c-frame-body">{children[0]}</div>
                </div>
                <div ref="framebottom" className="c-frame-bottom">
                    <div className="c-frame-body">{children[1]}</div>
                    <button ref="drag" className="c-frame-drag"></button>
                </div>
            </div>
        );
    }
}

export class FrameTop extends Component {
    render() {
        return <div>{this.props.children}</div>;
    }
}

export class FrameBottom extends Component {
    render() {
        return <div>{this.props.children}</div>;
    }
}
