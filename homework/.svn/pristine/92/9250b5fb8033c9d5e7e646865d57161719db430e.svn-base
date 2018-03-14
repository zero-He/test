define(function(require, exports, module){
var React = require('common/react/react');
var $ = require('jquery');
var Utils = require('utils');
let {Component} = React;

function compareWith(current, next) {
  if (current.index != next.index || current.imgs.length != next.imgs.length) {
    return true;
  }
  if (current.imgs.length > 0 && current.imgs[0] != next.imgs[0]) {
    return true;
  }
  return false;
}

class ImgView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      index: props.index || 0,
      imgs : props.imgs,
      zone: null
    };
    this.handleNext = this.handleNext.bind(this);
    this.handlePrev = this.handlePrev.bind(this);
    this.handleDraw = this.handleDraw.bind(this);
  }
  componentWillReceiveProps(nextProps) {
    if (compareWith(this.props, nextProps)) {
      this.setState({
        index: nextProps.index || 0,
        imgs: nextProps.imgs,
        zone: null
      });
    }
  }
  handleNext() {
    let {index, imgs} = this.state;
    if (index >= imgs.length - 1) {
      Utils.Notice.alert('当前已经是最后一页');
      return;
    } else {
      index++;
    }
    this.setState({index: index, zone: null});
  }
  handlePrev() {
    let {index, imgs} = this.state;
    if (index < 1) {
      Utils.Notice.alert('当前已经是第一页');
      return;
    } else {
      index--;
    }
    this.setState({index: index, zone: null});
  }
  handleDraw(zone) {
    if (zone && zone.idx) {
      this.setState({zone: zone, index: zone.idx - 1});
      this.forceUpdate();
    }
  }
  shouldComponentUpdate(nextProps, nextState) {
    return compareWith(this.state, nextState);
  }
  componentDidUpdate() {
    let {index, zone} = this.state;
    if (!(zone && zone.idx)) {
      $(this.refs.img).hide().fadeIn();
    }
  }
  render() {
    let {index, imgs, zone} = this.state;
    let img = imgs[index];
  	return (
  	  <div className="preview">
  	    <div className="box">
  	      <ul className="list">
  	          <li key={img} ref="img" style={{display: 'list-item'}} >
   	            <div>
  	              <img src={img} />
  	            </div>
  	          </li>
  	      </ul>
  	    </div>
  	    <div className="plus" onClick={this.handleNext}></div>
  	    <div className="minus" onClick={this.handlePrev}></div>
  	    <ImgView.Position zone={zone} />
  	  </div>
  	);
  }
}

class Position extends Component {
  render() {
    let zone = this.props.zone;
    if (!(zone && zone.x)) {
      return null;
    }
    let {x, y, w, h} = zone;
    let styles = {
      position: 'absolute',
      border: '2px #f00 dashed',
      boxSizing: 'border-box',
      left: x * 768 / 1060,
      top: y * 768 / 1060,
      width: w * 768 / 1060,
      height: h * 768 / 1060
    };
  	return <div style={styles}></div>;
  }
}

ImgView.Position = Position;

module.exports = ImgView;
});
