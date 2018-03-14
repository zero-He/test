import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';

const overlayStyles = {
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    position: 'fixed',
    backgroundColor: 'rgba(0, 0, 0, 0.2)'
};

class Modal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modalDom: null,
            overlay: true
        };
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
    }
    open(modalDom, overlay) {
        this.setState({modalDom, overlay});
    }
    close() {
        this.setState({modalDom: null});
    }
    render() {
        let {modalDom, overlay} = this.state;
        if (modalDom == null) {
            return null;
        }
        return (
            <div>
                {overlay ? <div style={overlayStyles}></div> : null}
                {modalDom}
            </div>
        );
    }
}

class Toast extends Component {
    render() {
        let {text} = this.props;
        return (
            <div className="c-toast">
                <div className="message">{text}</div>
            </div>
        );
    }
}

class Loading extends Component {
    render() {
        const {text} =  this.props;
        return (
            <div className="c-loading">
                <div className="m-init"><i></i></div>
                <div className="message">{text}</div>
            </div>
        );
    }
}

function createReactModal() {
    const htmlNode = document.createElement('div');
    document.body.appendChild(htmlNode);
    return ReactDOM.render(<Modal />, htmlNode);
}

const modalNode = createReactModal();
const toastNode = createReactModal();
const loadingNode = createReactModal();
let timer = null;

export function toast(text) {
    toastNode.open(<Toast text={text} />, false);
    if (timer) {
        clearTimeout(timer);
    }
    timer = setTimeout(function() {
        toastNode.close();
        timer = null;
    }, 2000);
}

export function showLoading(text) {
    loadingNode.open(<Loading text={text} />, true);
}

export function hideLoading() {
    loadingNode.close();
}

export default {
    toast,
    showLoading,
    hideLoading,
    openModal : function(modalDom, overlay = true) {
        modalNode.open(modalDom, overlay);
    },
    closeModal : function() {
        modalNode.close();
    }
}
