"use strict";function _classCallCheck(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function _possibleConstructorReturn(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function _inherits(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var _createClass=function(){function e(e,t){for(var n=0;n<t.length;n++){var i=t[n];i.enumerable=i.enumerable||!1,i.configurable=!0,"value"in i&&(i.writable=!0),Object.defineProperty(e,i.key,i)}}return function(t,n,i){return n&&e(t.prototype,n),i&&e(t,i),t}}();define(function(require,exports,module){function e(e,t){return e.index!=t.index||e.imgs.length!=t.imgs.length||e.imgs.length>0&&e.imgs[0]!=t.imgs[0]}var t=require("common/react/react"),n=require("jquery"),i=require("utils"),r=t.Component,o=function(r){function o(e){_classCallCheck(this,o);var t=_possibleConstructorReturn(this,(o.__proto__||Object.getPrototypeOf(o)).call(this,e));return t.state={index:e.index||0,imgs:e.imgs,zone:null},t.handleNext=t.handleNext.bind(t),t.handlePrev=t.handlePrev.bind(t),t.handleDraw=t.handleDraw.bind(t),t}return _inherits(o,r),_createClass(o,[{key:"componentWillReceiveProps",value:function(t){e(this.props,t)&&this.setState({index:t.index||0,imgs:t.imgs,zone:null})}},{key:"handleNext",value:function(){var e=this.state,t=e.index;if(t>=e.imgs.length-1)return void i.Notice.alert("当前已经是最后一页");t++,this.setState({index:t,zone:null})}},{key:"handlePrev",value:function(){var e=this.state,t=e.index;e.imgs;if(t<1)return void i.Notice.alert("当前已经是第一页");t--,this.setState({index:t,zone:null})}},{key:"handleDraw",value:function(e){e&&e.idx&&(this.setState({zone:e,index:e.idx-1}),this.forceUpdate())}},{key:"shouldComponentUpdate",value:function(t,n){return e(this.state,n)}},{key:"componentDidUpdate",value:function(){var e=this.state,t=(e.index,e.zone);t&&t.idx||n(this.refs.img).hide().fadeIn()}},{key:"render",value:function(){var e=this.state,n=e.index,i=e.imgs,r=e.zone,s=i[n];return t.createElement("div",{className:"preview"},t.createElement("div",{className:"box"},t.createElement("ul",{className:"list"},t.createElement("li",{key:s,ref:"img",style:{display:"list-item"}},t.createElement("div",null,t.createElement("img",{src:s}))))),t.createElement("div",{className:"plus",onClick:this.handleNext}),t.createElement("div",{className:"minus",onClick:this.handlePrev}),t.createElement(o.Position,{zone:r}))}}]),o}(r),s=function(e){function n(){return _classCallCheck(this,n),_possibleConstructorReturn(this,(n.__proto__||Object.getPrototypeOf(n)).apply(this,arguments))}return _inherits(n,e),_createClass(n,[{key:"render",value:function(){var e=this.props.zone;if(!e||!e.x)return null;var n=e.x,i=e.y,r=e.w,o=e.h,s={position:"absolute",border:"2px #f00 dashed",boxSizing:"border-box",left:768*n/1060,top:768*i/1060,width:768*r/1060,height:768*o/1060};return t.createElement("div",{style:s})}}]),n}(r);o.Position=s,module.exports=o});