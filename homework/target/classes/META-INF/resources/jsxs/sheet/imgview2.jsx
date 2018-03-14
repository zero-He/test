define(function(require, exports, module){
var React = require('common/react/react');
var $ = require('jquery');
var Utils = require('utils');
let {Component} = React;

class ImgView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      index: props.index || 0,
      imgs : props.imgs
    };
    this.handleNext = this.handleNext.bind(this);
    this.handlePrev = this.handlePrev.bind(this);
  }
  handleNext() {
    let {index, imgs} = this.state;
    if (index >= imgs.length - 1) {
      Utils.Notice.alert('当前已经是最后一页');
      return;
    } else {
      index++;
    }
    this.setState({index: index});
  }
  handlePrev() {
    let {index, imgs} = this.state;
    if (index < 1) {
      Utils.Notice.alert('当前已经是第一页');
      return;
    } else {
      index--;
    }
    this.setState({index: index});
  }
  componentDidMount(){
    var that = this;
    document.addEventListener('keydown', function (e) {
      if (e.keyCode == 37) {
        that.handlePrev();
      } else if (e.keyCode == 39) {
        that.handleNext();
      }
    }, false);
  }
  componentDidUpdate() {
    $(this.refs.img).hide().fadeIn();
  }
  render() {
    let {index, imgs} = this.state;
    let img1 = imgs[index][0], img2 = imgs[index][1];
  	return (
  	  <div className="preview">
  	    <div className="box">
  	      <ul className="list">
  	          <li key={img1} ref="img1" style={{display: 'list-item'}} >
  	            <img src={img1} />
  	            {img2 ? <img src={img2} /> : ''}
  	          </li>
  	      </ul>
  	    </div>
  	    <div className="plus" onClick={this.handleNext}></div>
  	    <div className="minus" onClick={this.handlePrev}></div>
  	  </div>
  	);
  }
}

module.exports = ImgView;
});
