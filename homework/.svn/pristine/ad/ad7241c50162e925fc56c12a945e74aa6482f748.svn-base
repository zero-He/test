import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import Modal from './modal';

class Loading extends Component {
    render() {
        let {text} = this.props;
        return (
            <div className="c-toast">
                <div className="message">{text}</div>
            </div>
        );
    }
}

export default const Loading = {
    start: function(text) {
        Modal.openModal(<Loading text={text} />);
    },
    stop: function() {
        Modal.closeModal();
    }
}
