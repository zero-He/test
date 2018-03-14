import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import Modal from './modal';
import {uploadImg} from './file';

if (!window.getSelection) {
    window.getSelection = function() {
        return window.selection;
    }
}
if (Selection && !Selection.prototype.createRange) {
    Selection.prototype.createRange = function() {
        if (this.rangeCount) {
            return this.getRangeAt(0);
        }
        return null;
    }
}

function TextEditor(el, canEnter = true) {
    var that = this;
    that.el = el;
    that.el.onblur = function() {
        that.remember();
    }
    that.el.onclick = function() {
        that.remember();
    }
    that.el.onkeyup = function() {
        that.remember();
    }
    if (canEnter == false) {
        let value = "";
        that.el.onkeydown = function(e) {
            value = e.target.innerHTML;
            return e.keyCode != 13;
        }
        that.el.onkeyup = function(e) {
            const newValue = e.target.innerHTML;
            if (newValue.indexOf('<div>') >= 0) {
                e.target.innerHTML = value;
            }
        }
    }
}

TextEditor.prototype = {
    remember : function() {
        const _range = getSelection().createRange();
        if (_range) {
            this.range = _range;
        }
    },
    focus: function() {
        this.el.focus();
        if (!this.range) {
            this.range = document.createRange();
            this.range.selectNodeContents(this.el);
            this.range.collapse(false);
        }
        var select = getSelection();
        select.removeAllRanges();
        select.addRange(this.range);
    },
    insert: function(html) {
        this.focus();
        var selection = getSelection();
        var range = selection.createRange();
        var anchorNode = selection.anchorNode;
        if (!anchorNode) {
            return;
        }
        range.deleteContents();
        var fragment = range.createContextualFragment(html);
        var lastNode = fragment.lastChild;
        range.insertNode(fragment);
        if (lastNode) {
            range = range.cloneRange();
            range.setStartAfter(lastNode);
            range.collapse(true);
            selection.removeAllRanges();
            selection.addRange(range);
        }
    }
}

function createTextEditor(el, canEnter) {
    return new TextEditor(el, canEnter);
}

function getPhoto(sourceType = 0) {
    var deferred = $.Deferred();
    function onSuccess(data) {
        deferred.resolve(data);
    }
    function onFailed(err) {
        deferred.reject(err);
    }
    let options = {
        sourceType: sourceType,
        correctOrientation: true,
        destinationType: Camera.DestinationType.DATA_URL
    };
    navigator.camera.getPicture(onSuccess, onFailed, options);
    return deferred;
}

class RichTextUI extends Component {
    handlePlugin(plugin) {
        const {editor} = this;
        editor.remember();
        if (plugin === 'image' || plugin === 'camera') {
            const sourceType = plugin === 'image' ? 0 : 1;
            Modal.showLoading();
            getPhoto(sourceType).done((dataUrl) => {
                uploadImg(dataUrl).done(json => {
                    Modal.hideLoading();
                    if (json.success) {
                        const url = json.datas.url1;
                        editor.insert('<img src="' + url + '" />');
                    } else {
                        Modal.toast(json.message);
                    }
                }).fail((xhr) => {
                    Modal.hideLoading();
                    Modal.toast('图片上传失败');
                });
            }).fail((err) => {
                console.log(err);
                Modal.hideLoading();
            });
        } else {
            console.log('unknown plugin ' + plugin);
        }
    }
    componentDidMount() {
        const {canEnter, subjective} = this.props;
        if (subjective) {
            this.editor = createTextEditor(this.refs.text, canEnter);
            this.editor.focus();
        } else {
            this.refs.text.value = this.props.value;

            let value = "";
            this.refs.text.onkeydown = function(e) {
                value = e.target.value;
                return e.keyCode != 13;
            };
            this.refs.text.onkeyup = function(e) {
                const newValue = e.target.value;
                if (newValue.indexOf('\n') >= 0) {
                    e.target.value = value;
                }
            }

            this.refs.text.focus();
        }
    }
    componentWillUnmount() {
        const {onSuccess, subjective, canEnter} = this.props;
        let value;
        if (subjective) {
            value = this.refs.text.innerHTML;
            if (canEnter == false) {
                value = valueAfterCodeing(value);
            }
        } else {
            value = this.refs.text.value;
        }
        onSuccess(value);
    }
    render() {
        const {value, subjective} = this.props;
        const plugins = [];
        if (subjective) {
            plugins.push(<span className="btn btn-image" onClick={this.handlePlugin.bind(this, 'image')}></span>);
            plugins.push(<span className="btn btn-camera" onClick={this.handlePlugin.bind(this, 'camera')}></span>)
        }
        plugins.push(<span className="btn btn-ok" onClick={() => Modal.closeModal()}></span>)
        return (
            <div className="c-richtext">
                <div className="head">
                    {plugins}
                </div>
                <div className="body">
                    {subjective
                        ? <div ref="text" className="textarea" contentEditable dangerouslySetInnerHTML={{__html: value}}></div>
                        : <textarea ref="text">{value}</textarea>
                    }
                </div>
            </div>
        );
    }
}

function valueAfterCodeing(text) {
    return text.replace(/&nbsp; /gm, '').replace(/&nbsp;/gm, '');
}

export default function richtext(value, options) {
    var deferred = $.Deferred();
    function onSuccess(data) {
        deferred.resolve(data);
        Modal.closeModal();
    }
    Modal.openModal(<RichTextUI value={value} {...options} onSuccess={onSuccess} />);
    return deferred;
}
