import React, {Component, PropTypes} from 'react';
import Modal from './modal';

export default class Image extends Component {
    handleClick = (evt) => {
        evt.preventDefault();
        const {src} = this.props;
        Modal.openModal(<ImageZoom src={src} />);
    }
    render() {
        const {src} = this.props;
        return (
            <img src={src} onClick={this.handleClick} />
        );
    }
}

class ImageZoom extends Component {
    render() {
        const {src} = this.props;
        return (
            <div className="c-image-zoom">
                <img src={src} style={{maxWidth: '100%', maxHeight: '100%'}} />
            </div>
        )
    }
}
