!function e(t,n,r){function a(l,c){if(!n[l]){if(!t[l]){var s="function"==typeof require&&require;if(!c&&s)return s(l,!0);if(o)return o(l,!0);var i=new Error("Cannot find module '"+l+"'");throw i.code="MODULE_NOT_FOUND",i}var u=n[l]={exports:{}};t[l][0].call(u.exports,function(e){var n=t[l][1][e];return a(n||e)},u,u.exports,e,t,n,r)}return n[l].exports}for(var o="function"==typeof require&&require,l=0;l<r.length;l++)a(r[l]);return a}({1:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=function(e){function t(e){r(this,t);var n=a(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.state={display:!1},n.handleToggle=n.handleToggle.bind(n),n}return o(t,e),l(t,[{key:"handleToggle",value:function(){var e=this.state.display;this.setState({display:!e})}},{key:"handleScrollTo",value:function(e,t){t.stopPropagation();var n=$("#"+e.link),r=$(".c-analyse"),a=$(document.body),o=n.offset().top-(parseInt(r.css("padding-top"))||0);a.scrollTop(o)}},{key:"render",value:function(){var e=this;return React.createElement("nav",{className:"c-anchor",onClick:this.handleToggle},e.renderItems())}},{key:"renderItems",value:function(){var e=this,t=this.state.display,n=this.props.items;return 0==t?null:(n=n.filter(function(e){return $("#"+e.link).length>0}),React.createElement("div",{className:"anchor"},React.createElement("ul",null,n.map(function(t){return React.createElement("li",{onClick:e.handleScrollTo.bind(e,t)},t.title)})),React.createElement("i",{className:"arrow"})))}}]),t}(React.Component);t.exports=c},{}],2:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"render",value:function(){return React.createElement("div",{className:"c-load"},React.createElement("i",{className:"loader"}),React.createElement("div",null,"数据加载中"))}}]),t}(React.Component);t.exports=c},{}],3:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"componentDidMount",value:function(){this.renderChartDom()}},{key:"componentDidUpdate",value:function(){this.renderChartDom()}},{key:"componentWillUnmount",value:function(){try{echarts.dispose(this.refs.chartDom)}catch(e){console.log("echart error: "+e)}}},{key:"renderChartDom",value:function(){var e=this.getChartInstance();if(!e)return null;var t=this.props,n=t.option,r=t.notMerge,a=t.lazyUpdate,o=t.showLoading;return e.setOption(n,r||!1,a||!1),o&&e.showLoading(),e}},{key:"getChartInstance",value:function(){var e=this.refs.chartDom,t=this.props.theme||"default";try{return echarts.getInstanceByDom(e)||echarts.init(e,t)}catch(e){return console.log("echart error: "+e),null}}},{key:"render",value:function(){return React.createElement("div",{ref:"chartDom",className:this.props.className,style:this.props.style})}}]),t}(React.Component);c.propTypes={option:React.PropTypes.object.isRequired,style:React.PropTypes.object,className:React.PropTypes.string,theme:React.PropTypes.string,showLoading:React.PropTypes.bool,notMerge:React.PropTypes.bool,lazyUpdate:React.PropTypes.bool},t.exports=c},{}],4:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}function l(e,t,n,r){var a,o=void 0===e?"undefined":s(e);return a="string"===o?t[e]:"function"===o?e(t,n):"",null==a||void 0==a?r||"":a}var c=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),s="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},i=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),c(t,[{key:"render",value:function(){var e=this.props.defVal||"",t=this.props,n=t.columns,r=t.datas,a=t.caption,o=t.showHead,c=null;o&&(c=React.createElement("thead",null,React.createElement("tr",null,n.map(function(e){return React.createElement("th",{style:e.width?{width:e.width}:{}},e.title)}))));var s=r.map(function(t,r){return React.createElement("tr",null,n.map(function(n){return React.createElement("td",{style:n.width?{width:n.width}:{}},l(n.field,t,r,e))}))});return React.createElement("table",null,a,c,React.createElement("tbody",null,s))}}]),t}(React.Component);i.propTypes={columns:React.PropTypes.array,datas:React.PropTypes.array,showHead:React.PropTypes.bool},t.exports=i},{}],5:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"render",value:function(){return React.createElement("div",{className:"m-tips"},React.createElement("span",{className:"img"}),React.createElement("p",{className:"msg"},this.props.message||this.props.children))}}]),t}(React.Component);t.exports=c},{}],6:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}function l(e){var t=[],n=e.filter(function(e){return!(e.subs&&e.subs.length)});return n.length>0&&t.push({subs:n}),e.filter(function(e){return e.subs&&e.subs.length}).forEach(function(e){return t.push(e)}),t}var c=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),s=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),c(t,[{key:"render",value:function(){return null}}]),t}(React.Component);s.propTypes={options:React.PropTypes.array};var i=function(e){function t(e){r(this,t);var n=a(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.state={childs:[],showIdx:null},n}return o(t,e),c(t,[{key:"componentWillMount",value:function(){var e=this.props.children,t=[];e.length?e.filter(function(e){return null!=e}).forEach(function(e){return t.push(e.props)}):t.push(e.props),this.selectFirstItem(t),this.setState({childs:t})}},{key:"selectFirstItem",value:function(e){e.forEach(function(e){var t=e.options[0];t.subs&&t.subs.length&&(t=t.subs[0]),t.selected=!0,e.label=t.label,e.value=t.value})}},{key:"handleClick",value:function(e){var t=this.state.showIdx;this.setState({showIdx:t!==e?e:null})}},{key:"handleChange",value:function(e){var t=this.state,n=t.childs,r=t.showIdx,a=n[r];if(a.value==e)return void this.setState({showIdx:null});a.options.forEach(function(t){t.subs&&t.subs.length?t.subs.forEach(function(t){t.selected=t.value===e,t.selected&&(a.label=t.label,a.value=t.value)}):(t.selected=t.value===e,t.selected&&(a.label=t.label,a.value=t.value))}),this.setState({childs:n,showIdx:null}),a.onChange&&a.onChange(a.value,a.label)}},{key:"componentDidUpdate",value:function(){var e=this.state.showIdx;$(document.body).css("overflow",null!=e?"hidden":"auto")}},{key:"render",value:function(){var e=this,t=this,n=this.state,r=n.childs,a=n.showIdx;return React.createElement("section",{className:"c-search"},React.createElement("ul",{className:4==r.length?"title four":"title"},r.map(function(t,n){return React.createElement("li",{className:a==n?"cur":"",onClick:e.handleClick.bind(e,n)},React.createElement("span",null,t.label))})),null!=a?t.renderOptions(r[a].options,r[a].inlineBlock):null)}},{key:"renderOptions",value:function(e,t){var n=this,r=l(e),a=[];return t=t||!1,r.forEach(function(e){e.label&&a.push(React.createElement("h5",null,e.label,"："));var r=e.subs.map(function(e){var r=t?"line ":"";return r+=e.selected?"cur":"",React.createElement("li",{className:r,onClick:n.handleChange.bind(n,e.value)},React.createElement("span",null,e.label))});a.push(React.createElement("ul",null,r))}),React.createElement("div",{className:"selects"},React.createElement("div",{className:"cover",onClick:this.handleClick.bind(this,null)}),React.createElement("div",{className:"classname"},a))}}]),t}(React.Component);i.Item=s,t.exports=i},{}],7:[function(e,t,n){"use strict";function r(e){return{normal:{color:e,label:{show:!0,position:"center",formatter:"{b}",textStyle:{color:"#333",fontSize:18,baseline:"bottom"}},labelLine:{show:!1}}}}function a(e){return{normal:{color:"#d7d7d7",label:{show:!0,position:"center",textStyle:{color:e,fontSize:18}},labelLine:{show:!1}}}}var o=e("../../../utils/number"),l=o.toFixed,c={submit:["#619eed","#ffd270","#ff6e6e"],score:["#2fbd68","#74d64b","#5cc0ff","#ffcd61","#ff6661"]},s={submit:["正常提交","延迟提交","未提交"],score:["优秀","良好","及格","较差","危险"],cycles:{0:["周","月","学期","学年"],1:["上周","本周"],2:["上月","本月"],3:["上学期","本学期"],4:["上学年","本学年"]}},i={Colors:c,Labels:s};i.buildSubmitPie=function(e,t,n,r){r=r||"人";var a=arguments,o=s.submit.map(function(e,t){return{name:e,value:a[t]}}),l=e+t+n;return{color:c.submit,legend:{x:"left",orient:"vertical",selectedMode:!1,data:s.submit},tooltip:{formatter:"应交"+r+"数："+l+"<br />{b}：{c}（{d}%）"},series:[{type:"pie",center:["60%","50%"],radius:["40%","70%"],avoidLabelOverlap:!1,label:{normal:{show:!1,position:"center"},emphasis:{show:!0,textStyle:{fontSize:16,fontWeight:"bold"}}},labelLine:{normal:{show:!1}},data:o}]}},i.buildScorePie=function(e,t,n,r,a,o){o=o||"人";var l=arguments,i=s.score.map(function(e,t){return{name:e,value:l[t]}}),u=e+t+n+r+a;return{color:c.score,legend:{x:"left",orient:"vertical",selectedMode:!1,data:s.score},tooltip:{formatter:"有成绩总"+o+"数："+u+"<br />{b}"+o+"数：{c}（{d}%）"},series:[{type:"pie",center:["60%","50%"],radius:["40%","70%"],avoidLabelOverlap:!1,label:{normal:{show:!1,position:"center"},emphasis:{show:!0,formatter:"{b}:{c}"+o+",{d}%",textStyle:{fontSize:12,fontWeight:"bold"}}},labelLine:{normal:{show:!1}},data:i}]}},i.buildClassScoreBar=function(e,t,n){return{color:["#5b9bd5","#8bc036","#ffbe54"],tooltip:{trigger:"axis",axisPointer:{type:"shadow"}},legend:{data:["最高分","最低分"]},grid:{top:70,bottom:0,containLabel:!0},xAxis:[{type:"category",data:[""]}],yAxis:[{type:"value",max:100,splitNumber:10,axisLabel:{formatter:"{value} 分"}}],series:[{name:"平均分",type:"bar",barWidth:60,data:[l(e,1)]},{name:"最高分",type:"line",data:[l(t,1)],symbolSize:10,markPoint:{silent:!0,data:[{type:"max",name:"最高分"}]}},{name:"最低分",type:"line",data:[l(n,1)],symbolSize:10,markPoint:{silent:!0,data:[{type:"min",name:"最低分"}]}}]}},i.buildScoreCompBar=function(e,t,n,r){var a=s.cycles[e];return{color:["#5b9bd5","#ed7d31"],tooltip:{trigger:"axis"},legend:{data:a},grid:{top:30,bottom:30,left:50,right:40},xAxis:[{type:"category",data:t}],yAxis:[{type:"value",min:0,max:100,interval:10,axisLabel:{formatter:"{value} 分"}}],series:[{name:a[0],type:"bar",barMaxWidth:30,data:r,markLine:{data:[{type:"average",name:"平均分"}]}},{name:a[1],type:"bar",barMaxWidth:30,data:n,markLine:{data:[{type:"average",name:"平均分"}]}}]}},i.buildOverallScore=function(e,t){var n=[{type:"pie",center:["25%","50%"],radius:["60%","65%"],x:"0%",itemStyle:{normal:{label:{formatter:function(t){return e},textStyle:{baseline:"top"}}}},data:[{name:"综合得分",value:e,itemStyle:r("#00bb9c")},{name:"other",value:l(100-e,1),itemStyle:a("#f86b4f")}]}];return t&&n.push({type:"pie",center:["75%","50%"],radius:["60%","65%"],x:"10%",itemStyle:{normal:{label:{formatter:function(e){return t+"%"},textStyle:{baseline:"top"}}}},data:[{name:"领先",value:t,itemStyle:r("#66ccff")},{name:"other",value:l(100-t,1),itemStyle:a("#66ccff")}]}),{series:n}},i.buildScoreTrend=function(e,t,n){var r=[],a=[],o=[];return t&&(t=t.map(function(e){return null!=e?e:"-"}),a.push("#ed7d31"),o.push("我的得分"),r.push({name:"我的得分",type:"line",data:t,showAllSymbol:!0})),n&&(n=n.map(function(e){return null!=e?e:"-"}),a.push("#5b9bd5"),o.push("班级得分"),r.push({name:"班级得分",type:"line",data:n,showAllSymbol:!0})),{color:a,tooltip:{trigger:"axis"},legend:{data:o},grid:{top:30,bottom:30,left:50,right:10},dataZoom:[{type:"inside"}],xAxis:{type:"category",data:e},yAxis:{type:"value",max:100,interval:10,axisLabel:{formatter:"{value} 分"}},series:r}},i.buildScoreSectionBar=function(e,t){return{color:["#3398DB"],tooltip:{trigger:"axis",formatter:"{b}分{c}人",axisPointer:{type:"shadow"}},grid:{top:10,left:10,right:10,bottom:10,containLabel:!0},xAxis:[{type:"category",data:e,axisTick:{alignWithLabel:!0},axisLabel:{interval:0},splitLine:{show:!0,lineStyle:{color:["#ddd"],type:"dashed"}}}],yAxis:[{type:"value",axisLabel:{formatter:"{value}人"},splitLine:{show:!0,lineStyle:{type:"dashed"}}}],series:[{name:"",type:"bar",barWidth:"90%",data:t}]}},t.exports=i},{"../../../utils/number":15}],8:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=e("../../../utils/number"),s=(c.toFixed,c.toRate,e("./analyze-base")),i=s.toLevel,u=s.levelNames,p=e("../../../react/ReactChart"),f=e("./ScoreChartConfig"),m=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"render",value:function(){var e=this.props,t=e.subjScores,n=e.rptType;if(null==t)return null;var r=0,a=0,o=0,l=[],c=f.Labels.cycles[n];t.forEach(function(e){if(e.self&&e.prev){e.self-3>e.prev?a++:e.self+3<e.prev?o++:r++;var t=i(e.self),n=i(e.prev);t<=n-2?(l.push(React.createElement("span",null,e.label,React.createElement("span",{className:"fc-green"},"上升劲猛"),"，")),l.push(React.createElement("span",null,"从",u[n-1],"升上",u[t-1],"；"))):t>=n+2&&(l.push(React.createElement("span",null,e.label,React.createElement("span",{className:"fc-orange"},"下降严重"),"，")),l.push(React.createElement("span",null,"从",u[n-1],"跌入",u[t-1],"；")))}}),l.length>0&&l.unshift(React.createElement("span",null,"2、相较于",c[0],"，",c[1]));var s=t.map(function(e){return e.label}),m=t.map(function(e){return null!=e.self?e.self:"-"}),h=t.map(function(e){return null!=e.prev?e.prev:"-"});return React.createElement("section",{id:this.props.id,className:"ana-module"},React.createElement("div",{className:"title"},this.props.title),React.createElement(p,{className:"maps",style:{height:180},option:f.buildScoreCompBar(n,s,m,h)}),React.createElement("div",{className:"texts"},React.createElement("p",{className:"paragraph"},"1、相较于",c[0],"，",c[1],"有",React.createElement("span",{className:"fc-orange"},o,"门"),"功课呈",React.createElement("span",{className:"fc-orange"},"下降趋势"),"；",React.createElement("span",{className:"fc-green"},a,"门"),"呈",React.createElement("span",{className:"fc-green"},"上升趋势"),"；",r,"门功课持平"),React.createElement("p",{className:"paragraph"},l)))}}]),t}(React.Component);t.exports=m},{"../../../react/ReactChart":3,"../../../utils/number":15,"./ScoreChartConfig":7,"./analyze-base":13}],9:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=e("../../../utils/number"),s=c.toFixed,i=(c.toRate,e("./analyze-base")),u=(i.toLevel,e("../../../react/SimpleTable")),p=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"markRankIndex",value:function(e,t){for(var n=1e4,r=0,a=0;a<e.length;a++){var o=e[a][t];null!=o?(o<n&&(r++,n=o),e[a].index=r):e[a].index="--"}}},{key:"render",value:function(){var e=this.props,t=e.scoreRanks,n=e.subjId;e.userId;if(void 0==t||null==t)return null;this.markRankIndex(t,n),t=t.filter(function(e,t){return t<10||e.userId==ReportCst.userId});var r=[{title:"名次",width:"15%",field:"index"},{title:"姓名",width:"60%",field:function(e,t){return React.createElement("span",{className:e.userId==ReportCst.userId?"fc-orange":""},e.userName)}},{title:"成绩",width:"25%",field:function(e,t){return s(e[n],1)}}];return React.createElement("section",{id:this.props.id,className:"ana-module"},React.createElement("div",{className:"title"},this.props.title),React.createElement("div",{className:"c-table"},React.createElement(u,{columns:r,datas:t,showHead:!0,defVal:"--"})))}}]),t}(React.Component);t.exports=p},{"../../../react/SimpleTable":4,"../../../utils/number":15,"./analyze-base":13}],10:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=e("../../../react/ReactChart"),s=e("./ScoreChartConfig"),i=function(e){function t(e){r(this,t);var n=a(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.state={idx:0},n}return o(t,e),l(t,[{key:"handleChangeType",value:function(e){this.setState({idx:e})}},{key:"renderTabs",value:function(e){if(!(e&&e.length>1))return null;var t=this,n=this.state.idx;return React.createElement("div",{className:"switch"},e.map(function(e,r){return React.createElement("span",{className:n==r?"cur":"",onClick:t.handleChangeType.bind(t,r)},e.label)}))}},{key:"render",value:function(){var e=this,t=this.props,n=t.trends,r=t.title;if(!n||!n.length)return null;var a=n[this.state.idx],o=a.names,l=a.selfs,i=a.klass;return React.createElement("section",{id:this.props.id,className:"ana-module"},React.createElement("div",{className:"title"},r,e.renderTabs(n)),React.createElement(c,{className:"maps",style:{height:180},option:s.buildScoreTrend(o,l,i)}))}}]),t}(React.Component);t.exports=i},{"../../../react/ReactChart":3,"./ScoreChartConfig":7}],11:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}function l(e){for(var t=0;t<e.length;t++)e[t]=null!=e[t]?e[t]:"-";return e}function c(e,t,n){var r=e.slice(0);if(e.length<3)for(var a=e.length;a<3;a++)e.push(""),t.push(0),n&&n.push(0);var o=e.map(function(e){return{text:e,max:100}}),c=[];return n&&c.push({value:l(n),name:"班级平均分",itemStyle:{normal:{areaStyle:{type:"default"}}}}),c.push({value:l(t),name:"个人得分"}),{legend:{x:"left",orient:"vertical",data:["班级平均分","个人得分"]},tooltip:{formatter:function(e){return e.name+r.map(function(t,n){return"<br />"+t+"："+e.value[n]}).join("")}},polar:[{indicator:o,splitNumber:5,radius:"65%",axisLabel:{show:!0,textStyle:{color:"#ccc"}},splitArea:{areaStyle:{color:["#ffffff","#ffffff"]}},splitLine:{lineStyle:{width:2,color:"#e9eaea"}}}],color:["#a9cfef","#fe6910"],series:[{type:"radar",data:c}]}}var s=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),i=e("../../../utils/number"),u=i.toFixed,p=i.toRate,f=e("./analyze-base"),m=(f.toLevel,f.toLevelName),h=e("../../../react/ReactChart"),d=e("./ScoreChartConfig"),b=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),s(t,[{key:"render",value:function(){var e=this.props,t=e.overall,n=e.subjScores,r=e.isUnit;if(null==t)return null;var a=t.score,o=t.rank,l=t.total,s=t.maxes1,i=t.maxes2,f=t.mines1,b=t.mines2,y=p(l-o,l,1),v=n.map(function(e){return e.label}),g=n.map(function(e){return e.self}),R=n.map(function(e){return e.klass});return React.createElement("section",{id:this.props.id,className:"ana-module"},React.createElement("div",{className:"title"},"学科优劣势分析"),React.createElement(h,{className:"maps",option:c(v,g,r?null:R)}),React.createElement(h,{className:"maps",style:{height:150},option:d.buildOverallScore(a,r?null:y)}),React.createElement("div",{className:"texts"},React.createElement("p",{className:"paragraph"},"您的全科综合得分为",React.createElement("span",{className:"fc-green"},u(a,1),"分"),"，表现",React.createElement("span",{className:"fc-green"},m(a)),"；",r?null:React.createElement("span",null,"班级排名第",React.createElement("span",{className:"fc-green"},o),"，领先全班",React.createElement("span",{className:"fc-green"},y,"%"),"的同学；")),React.createElement("p",{className:"paragraph"},"相比自己综合得分短板学科为",React.createElement("span",{className:"fc-orange"},f),"，长板学科为",React.createElement("span",{className:"fc-green"},s),"；",r?null:React.createElement("span",null,"相比班级各科得分劣势学科为",React.createElement("span",{className:"fc-orange"},b),"，相对优势学科为",React.createElement("span",{className:"fc-green"},i),"；"),r?React.createElement("span",null,"请加强短板学科的学习训练，弥补不足，齐头并进。"):React.createElement("span",null,"请加强短板、劣势学科的学习训练，弥补不足，齐头并进。"))))}}]),t}(React.Component);t.exports=b},{"../../../react/ReactChart":3,"../../../utils/number":15,"./ScoreChartConfig":7,"./analyze-base":13}],12:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=e("../../../utils/number"),s=c.toFixed,i=c.toRate,u=e("./analyze-base"),p=(u.toLevel,u.toLevelName,e("../../../react/ReactChart")),f=e("./ScoreChartConfig"),m=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"render",value:function(){var e=this.props,t=e.summary,n=e.userName,r=e.myself,a=t.totalNum,o=t.submitNum,l=t.delayNum,c=t.correctNum,u=t.levelA,m=t.levelB,h=t.levelC,d=t.levelD,b=t.levelE,y=t.queNum,v=t.errNum;return React.createElement("section",{id:this.props.id,className:"ana-module"},React.createElement("div",{className:"title"},"成绩分析"),React.createElement("div",{className:"texts"},r?React.createElement("section",null,"亲爱的",React.createElement("span",{className:"fc-green"},n),"同学："):null,React.createElement("p",{className:"paragraph"},React.createElement("span",null,"在此期间内，",r?"您":React.createElement("span",{className:"fc-green"},n,"同学"),"应交作业",React.createElement("span",{className:"fc-green"},a),"份，"),React.createElement("span",null,"正常提交作业",React.createElement("span",{className:"fc-green"},o-l),"份，占比",React.createElement("span",{className:"fc-green"},i(o-l,a,1),"%"),"，"),React.createElement("span",null,"延交作业",React.createElement("span",{className:"fc-orange"},l),"份，占比",React.createElement("span",{className:"fc-orange"},i(l,a,1),"%"),"，"),React.createElement("span",null,"未交作业",React.createElement("span",{className:"fc-orange"},a-o),"份，占比",React.createElement("span",{className:"fc-orange"},i(a-o,a,1),"%"),"；"),React.createElement("span",null,"已批改作业",React.createElement("span",{className:"fc-green"},c),"份，"),React.createElement("span",null,"其中成绩优秀",React.createElement("span",{className:"fc-green"},u),"份，占比",React.createElement("span",{className:"fc-green"},i(u,c,1),"%"),"，"),React.createElement("span",null,"良好",React.createElement("span",{className:"fc-green"},m),"份，占比",React.createElement("span",{className:"fc-green"},i(m,c,1),"%"),"，"),React.createElement("span",null,"及格",React.createElement("span",{className:"fc-green"},h),"份，占比",React.createElement("span",{className:"fc-green"},i(h,c,1),"%"),"，"),React.createElement("span",null,"较差",React.createElement("span",{className:"fc-orange"},d),"份，占比",React.createElement("span",{className:"fc-orange"},i(d,c,1),"%"),"，"),React.createElement("span",null,"危险",React.createElement("span",{className:"fc-orange"},b),"份，占比",React.createElement("span",{className:"fc-orange"},i(b,c,1),"%"),"；"),React.createElement("span",null,"共做题",React.createElement("span",{className:"fc-green"},y),"道，错题",React.createElement("span",{className:"fc-orange"},s(v,1)),"道，"),React.createElement("span",null,"得分率",React.createElement("span",{className:"fc-green"},i(y-v,y,1),"%"),"。"))),React.createElement(p,{className:"maps",option:f.buildSubmitPie(o-l,l,a-o,"份")}),React.createElement(p,{className:"maps",option:f.buildScorePie(u,m,h,d,b,"份")}))}}]),t}(React.Component);t.exports=m},{"../../../react/ReactChart":3,"../../../utils/number":15,"./ScoreChartConfig":7,"./analyze-base":13}],13:[function(e,t,n){"use strict";var r={toSecond:function(e){return e<60?1:toFixed(e/60,1)},toLevel:function(e){return void 0==e||null==e?6:e>=85?1:e>=70?2:e>=60?3:e>=45?4:5},toLevelName:function(e){var t=r.toLevel(e);return r.levelNames[t-1]},levelNames:["优秀","良好","及格","较差","危险",null]};t.exports=r},{}],14:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t)
;e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=e("../../react/Anchor"),s=e("../../react/Loading"),i=e("../../react/ToolTips"),u=e("./components/FilterBox"),p=e("./components/StudentScoreSummary"),f=e("./components/StudentOverallInfo"),m=e("./components/ScoreCompared"),h=e("./components/ScoreTrendInfo"),d=e("./components/ScoreTop10RankInfo"),b=ReportCst,y=b.childs,v=(b.subjs,b.cycles),g=[{title:"成绩分析",link:"ach_summary"},{title:"学科优劣势分析",link:"ach_overall"},{title:"综合成绩走势分析",link:"ach_trend"},{title:"学科成绩对比分析",link:"ach_scorecomp"},{title:"班级综合成绩TOP10",link:"ach_scorerank"}];ReportCst.userId=y[0].value,ReportCst.userName=y[0].label;var R=function(e){function t(e){r(this,t);var n=a(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.state={loading:!0,userId:ReportCst.userId,subjId:0,cycleId:v[0].subs[0].value},n.handleChangeChild=n.handleChangeChild.bind(n),n.handleChangeCycleId=n.handleChangeCycleId.bind(n),n}return o(t,e),l(t,[{key:"handleQuery",value:function(e,t,n){this.setState({userId:e,subjId:t,cycleId:n,loading:!0}),$.post("data.htm",{studentId:e,cycleId:n,subjectId:t}).done(function(e){this.setState({loading:!1,view:e.datas.view})}.bind(this))}},{key:"handleChangeChild",value:function(e,t){ReportCst.userId=e,ReportCst.userName=t;var n=this.state,r=n.subjId,a=n.cycleId;this.handleQuery(e,r,a)}},{key:"handleChangeCycleId",value:function(e){var t=this.state,n=t.userId,r=t.subjId;this.handleQuery(n,r,e)}},{key:"componentDidMount",value:function(){var e=this.state,t=e.userId,n=e.subjId,r=e.cycleId;this.handleQuery(t,n,r)}},{key:"render",value:function(){this.state.subjId;return React.createElement("div",{className:"c-analyse"},React.createElement("nav",{className:"c-nav"},React.createElement("ul",null,React.createElement("li",{className:"cur"},React.createElement("a",null,"综合分析报告")),React.createElement("li",null,React.createElement("a",{href:"./subject.htm"},"学科分析报告")),React.createElement("li",null,React.createElement("a",{href:"../behavior/index.htm"},"行为分析报告")))),React.createElement(u,null,y.length>1?React.createElement(u.Item,{options:y,onChange:this.handleChangeChild}):null,React.createElement(u.Item,{options:v,onChange:this.handleChangeCycleId})),this.renderBody())}},{key:"renderBody",value:function(){var e=this.state,t=e.loading,n=e.subjId,r=e.view;return null==r||1==t?React.createElement(s,null):0==r.success?React.createElement(i,null,r.message):React.createElement(E,{view:r,subjId:n})}}]),t}(React.Component),E=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"render",value:function(){var e=this.props,t=e.view,n=(e.subjId,[]);return n.push(React.createElement(p,{id:"ach_summary",summary:t.summary,userName:ReportCst.userName,myself:!0})),t.summary.correctNum>0&&(n.push(React.createElement(f,{id:"ach_overall",overall:t.overall,subjScores:t.subjScores,isUnit:t.isUnit})),n.push(React.createElement(h,{id:"ach_trend",title:"综合成绩走势分析",trends:t.trends,subjId:0})),n.push(React.createElement(m,{id:"ach_scorecomp",title:"学科成绩对比分析",subjScores:t.subjScores,rptType:t.rptType})),n.push(React.createElement(d,{id:"ach_scorerank",title:"班级综合成绩TOP10",scoreRanks:t.scoreRanks,subjId:0})),n.push(React.createElement(c,{items:g}))),React.createElement("section",{className:"c-detail"},n)}}]),t}(React.Component),w=document.getElementById("app");ReactDOM.render(React.createElement(R,null),w)},{"../../react/Anchor":1,"../../react/Loading":2,"../../react/ToolTips":5,"./components/FilterBox":6,"./components/ScoreCompared":8,"./components/ScoreTop10RankInfo":9,"./components/ScoreTrendInfo":10,"./components/StudentOverallInfo":11,"./components/StudentScoreSummary":12}],15:[function(e,t,n){"use strict";function r(e,t){if(void 0===e||null===e||""===e||isNaN(+e)||!t)return e;var n,r,a,o,l,c,s,i,u,p,f=t.length,m=t.search(/[0-9\-\+#]/),h=m>0?t.substring(0,m):"",d=t.split("").reverse().join(""),b=d.search(/[0-9\-\+#]/),y=f-b,v=y+("."===t.charAt(y)?1:0),g=b>0?t.substring(v,f):"";if(t=t.substring(m,v),e="-"===t.charAt(0)?-e:+e,n=e<0?e=-e:0,r=t.match(/[^\d\-\+#]/g),a=r&&r[r.length-1]||".",o=r&&r[1]&&r[0]||",",t=t.split(a),e=e.toFixed(t[1]&&t[1].length),e=+e+"",c=t[1]&&t[1].lastIndexOf("0"),i=e.split("."),(!i[1]||i[1]&&i[1].length<=c)&&(e=(+e).toFixed(c+1)),u=t[0].split(o),t[0]=u.join(""),(l=t[0]&&t[0].indexOf("0"))>-1)for(;i[0].length<t[0].length-l;)i[0]="0"+i[0];else 0==+i[0]&&(i[0]="");if(e=e.split("."),e[0]=i[0],s=u[1]&&u[u.length-1].length){for(p=e[0],d="",y=p.length%s,f=p.length,v=0;v<f;v++)d+=p.charAt(v),!((v-y+1)%s)&&v<f-s&&(d+=o);e[0]=d}return e[1]=t[1]&&e[1]?a+e[1]:"",r=e.join(""),"0"!==r&&""!==r||(n=!1),h+(n?"-":"")+r+g}function a(e,t){var n=parseFloat(e);if(isNaN(n))return e;if((t=t||0)<1)return Math.round(n);var r=Math.pow(10,t);return Math.round(n*r)/r}function o(e,t,n){return 0==t?0:a(100*e/t,n||0)}t.exports={format:r,toFixed:a,toRate:o}},{}]},{},[14]);