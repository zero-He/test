!function e(t,r,n){function o(c,i){if(!r[c]){if(!t[c]){var u="function"==typeof require&&require;if(!i&&u)return u(c,!0);if(a)return a(c,!0);var s=new Error("Cannot find module '"+c+"'");throw s.code="MODULE_NOT_FOUND",s}var l=r[c]={exports:{}};t[c][0].call(l.exports,function(e){var r=t[c][1][e];return o(r||e)},l,l.exports,e,t,r,n)}return r[c].exports}for(var a="function"==typeof require&&require,c=0;c<n.length;c++)o(n[c]);return o}({1:[function(e,t,r){"use strict";function n(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function o(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function a(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var c=function(){function e(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}return function(t,r,n){return r&&e(t.prototype,r),n&&e(t,n),t}}(),i=function(e){function t(){return n(this,t),o(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return a(t,e),c(t,[{key:"componentDidMount",value:function(){this.renderChartDom()}},{key:"componentDidUpdate",value:function(){this.renderChartDom()}},{key:"componentWillUnmount",value:function(){try{echarts.dispose(this.refs.chartDom)}catch(e){console.log("echart error: "+e)}}},{key:"renderChartDom",value:function(){var e=this.getChartInstance();if(!e)return null;var t=this.props,r=t.option,n=t.notMerge,o=t.lazyUpdate,a=t.showLoading;return e.setOption(r,n||!1,o||!1),a&&e.showLoading(),e}},{key:"getChartInstance",value:function(){var e=this.refs.chartDom,t=this.props.theme||"default";try{return echarts.getInstanceByDom(e)||echarts.init(e,t)}catch(e){return console.log("echart error: "+e),null}}},{key:"render",value:function(){return React.createElement("div",{ref:"chartDom",className:this.props.className,style:this.props.style})}}]),t}(React.Component);i.propTypes={option:React.PropTypes.object.isRequired,style:React.PropTypes.object,className:React.PropTypes.string,theme:React.PropTypes.string,showLoading:React.PropTypes.bool,notMerge:React.PropTypes.bool,lazyUpdate:React.PropTypes.bool},t.exports=i},{}],2:[function(e,t,r){"use strict";function n(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function o(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function a(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var c=function(){function e(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}return function(t,r,n){return r&&e(t.prototype,r),n&&e(t,n),t}}(),i=e("../../react/ReactChart"),u={color:["#92d050","#ffcc00","#cc3300"],legend:{x:"left",orient:"vertical",selectedMode:!1,data:["已完成","部分完成","未完成"]},tooltip:{formatter:"{b}：{c}人<br>比率：{d}%"},series:[{name:"完成情况",type:"pie",radius:"80%",label:{normal:{show:!1}},labelLine:{normal:{show:!1}},data:[{value:100,name:"已完成"},{value:20,name:"部分完成"},{value:5,name:"未完成"}]}]},s=function(e){function t(){return n(this,t),o(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return a(t,e),c(t,[{key:"render",value:function(){return React.createElement(i,{style:{height:250},option:u})}}]),t}(React.Component),l=document.getElementById("app");ReactDOM.render(React.createElement(s,null),l)},{"../../react/ReactChart":1}]},{},[2]);