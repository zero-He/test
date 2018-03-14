import React, {Component, PropTypes} from 'react';

function swipeDirection(x1, x2, y1, y2) {
    return Math.abs(x1 - x2) >= Math.abs(y1 - y2) ? (x1 - x2 > 0 ? 'Left' : 'Right') : (y1 - y2 > 0 ? 'Up' : 'Down');
}

function emptyFn() {}

export default class Touch extends Component {
    static defaultProps = {
        className: '',
        onSwipe: emptyFn,
        onSwipeLeft: emptyFn,
        onSwipeRight: emptyFn,
        onSwipeUp: emptyFn,
        onSwipeDown: emptyFn
    }
    static propTypes = {
        className: PropTypes.string,
        onSwipe: PropTypes.func,
        onSwipeLeft: PropTypes.func,
        onSwipeRight: PropTypes.func,
        onSwipeUp: PropTypes.func,
        onSwipeDown: PropTypes.func
    }
    componentDidMount() {
        var touch;
        $(this.refs.box).on('touchstart', function(e) {
            if (e.touches[0]) {
                touch = {
                    t : Date.now(),
                    x1 : e.touches[0].clientX,
                    y1 : e.touches[0].clientY
                };
            }
        }).on('touchmove', function(e) {
            if (touch && e.touches[0]) {
                touch.x2 = e.touches[0].clientX;
                touch.y2 = e.touches[0].clientY;
                let {x1, x2, y1, y2} = touch;
                if (Math.abs(x1 - x2) <= 5 || Math.abs(y1 - y2) <= 5) {
                    e.preventDefault();
                }
            }
        }).on('touchend', function(e) {
            if (touch && (touch.x2 || touch.y2)) {
                triggerEvent(touch);
                touch = null;
            }
        });
        let props = this.props;
        function triggerEvent(touch) {
            let {x1, x2, y1, y2} = touch;
            if ((Math.abs(x1 - x2) > 30 || Math.abs(y1 - y2) > 30) && (Date.now() - touch.t < 300)) {
                props.onSwipe(touch);
                (props['onSwipe'+ swipeDirection(x1, x2, y1, y2)])();
            }
        }
    }
    componentWillUnmount() {
    }
    render() {
        return (
            <div ref="box" className={this.props.className}>
                {this.props.children}
            </div>
        );
    }
}
