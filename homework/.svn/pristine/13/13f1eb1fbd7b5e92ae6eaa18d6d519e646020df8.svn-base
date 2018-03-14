import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import Modal from './modal';

class Confirm extends Component {
    render() {
        let {text, onSubmit, onCancel} = this.props;
        return (
            <div className="c-confirm">
                <div className="head">
                    <div className="message">{text}</div>
                </div>
                <div className="foot">
                    <button className="btn btn-cancel" onClick={onCancel}>取消</button>
                    <button className="btn btn-submit" onClick={onSubmit}>确认</button>
                </div>
            </div>
        );
    }
}

export default function confirm(text) {
    var deferred = $.Deferred();
    function onSubmit() {
        Modal.closeModal();
        deferred.resolve();
    }
    function onCancel() {
        Modal.closeModal();
        deferred.reject();
    }
    Modal.openModal(<Confirm text={text} onSubmit={onSubmit} onCancel={onCancel} />);
    return deferred;
}
