!function e(t,n,r){function a(l,c){if(!n[l]){if(!t[l]){var i="function"==typeof require&&require;if(!c&&i)return i(l,!0);if(o)return o(l,!0);var s=new Error("Cannot find module '"+l+"'");throw s.code="MODULE_NOT_FOUND",s}var u=n[l]={exports:{}};t[l][0].call(u.exports,function(e){var n=t[l][1][e];return a(n||e)},u,u.exports,e,t,n,r)}return n[l].exports}for(var o="function"==typeof require&&require,l=0;l<r.length;l++)a(r[l]);return a}({1:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=function(e){function t(e){r(this,t);var n=a(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.state={display:!1},n.handleToggle=n.handleToggle.bind(n),n}return o(t,e),l(t,[{key:"handleToggle",value:function(){var e=this.state.display;this.setState({display:!e})}},{key:"handleScrollTo",value:function(e,t){t.stopPropagation();var n=$("#"+e.link),r=$(".c-analyse"),a=$(document.body),o=n.offset().top-(parseInt(r.css("padding-top"))||0);a.scrollTop(o)}},{key:"render",value:function(){var e=this;return React.createElement("nav",{className:"c-anchor",onClick:this.handleToggle},e.renderItems())}},{key:"renderItems",value:function(){var e=this,t=this.state.display,n=this.props.items;return 0==t?null:(n=n.filter(function(e){return $("#"+e.link).length>0}),React.createElement("div",{className:"anchor"},React.createElement("ul",null,n.map(function(t){return React.createElement("li",{onClick:e.handleScrollTo.bind(e,t)},t.title)})),React.createElement("i",{className:"arrow"})))}}]),t}(React.Component);t.exports=c},{}],2:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=function(e){function t(e){r(this,t);var n=a(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.state={levels:e.levels||[1,2,3]},n}return o(t,e),l(t,[{key:"handleChange",value:function(e){var t=this.state.levels;t.indexOf(e)>=0?t=t.filter(function(t){return t!=e}):t.push(e),this.setState({levels:t}),this.props.onChange&&this.props.onChange(t)}},{key:"render",value:function(){var e=this.state.levels;return React.createElement("ul",{className:"filter"},React.createElement("li",{className:e.indexOf(1)>=0?"selected":"",onClick:this.handleChange.bind(this,1)},React.createElement("i",{className:"icon"}),React.createElement("span",{className:"l-1"})," 已掌握"),React.createElement("li",{className:e.indexOf(2)>=0?"selected":"",onClick:this.handleChange.bind(this,2)},React.createElement("i",{className:"icon"}),React.createElement("span",{className:"l-2"})," 掌握不牢"),React.createElement("li",{className:e.indexOf(3)>=0?"selected":"",onClick:this.handleChange.bind(this,3)},React.createElement("i",{className:"icon"}),React.createElement("span",{className:"l-3"})," 未掌握"))}}]),t}(React.Component);t.exports=c},{}],3:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"render",value:function(){return React.createElement("div",{className:"c-load"},React.createElement("i",{className:"loader"}),React.createElement("div",null,"数据加载中"))}}]),t}(React.Component);t.exports=c},{}],4:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"componentDidMount",value:function(){this.renderChartDom()}},{key:"componentDidUpdate",value:function(){this.renderChartDom()}},{key:"componentWillUnmount",value:function(){try{echarts.dispose(this.refs.chartDom)}catch(e){console.log("echart error: "+e)}}},{key:"renderChartDom",value:function(){var e=this.getChartInstance();if(!e)return null;var t=this.props,n=t.option,r=t.notMerge,a=t.lazyUpdate,o=t.showLoading;return e.setOption(n,r||!1,a||!1),o&&e.showLoading(),e}},{key:"getChartInstance",value:function(){var e=this.refs.chartDom,t=this.props.theme||"default";try{return echarts.getInstanceByDom(e)||echarts.init(e,t)}catch(e){return console.log("echart error: "+e),null}}},{key:"render",value:function(){return React.createElement("div",{ref:"chartDom",className:this.props.className,style:this.props.style})}}]),t}(React.Component);c.propTypes={option:React.PropTypes.object.isRequired,style:React.PropTypes.object,className:React.PropTypes.string,theme:React.PropTypes.string,showLoading:React.PropTypes.bool,notMerge:React.PropTypes.bool,lazyUpdate:React.PropTypes.bool},t.exports=c},{}],5:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}function l(e,t,n,r){var a,o=void 0===e?"undefined":i(e);return a="string"===o?t[e]:"function"===o?e(t,n):"",null==a||void 0==a?r||"":a}var c=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),i="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},s=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),c(t,[{key:"render",value:function(){var e=this.props.defVal||"",t=this.props,n=t.columns,r=t.datas,a=t.caption,o=t.showHead,c=null;o&&(c=React.createElement("thead",null,React.createElement("tr",null,n.map(function(e){return React.createElement("th",{style:e.width?{width:e.width}:{}},e.title)}))));var i=r.map(function(t,r){return React.createElement("tr",null,n.map(function(n){return React.createElement("td",{style:n.width?{width:n.width}:{}},l(n.field,t,r,e))}))});return React.createElement("table",null,a,c,React.createElement("tbody",null,i))}}]),t}(React.Component);s.propTypes={columns:React.PropTypes.array,datas:React.PropTypes.array,showHead:React.PropTypes.bool},t.exports=s},{}],6:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"render",value:function(){return React.createElement("div",{className:"m-tips"},React.createElement("span",{className:"img"}),React.createElement("p",{className:"msg"},this.props.message||this.props.children))}}]),t}(React.Component);t.exports=c},{}],7:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}function l(e){var t=[],n=e.filter(function(e){return!(e.subs&&e.subs.length)});return n.length>0&&t.push({subs:n}),e.filter(function(e){return e.subs&&e.subs.length}).forEach(function(e){return t.push(e)}),t}var c=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),i=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),c(t,[{key:"render",value:function(){return null}}]),t}(React.Component);i.propTypes={options:React.PropTypes.array};var s=function(e){function t(e){r(this,t);var n=a(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.state={childs:[],showIdx:null},n}return o(t,e),c(t,[{key:"componentWillMount",value:function(){var e=this.props.children,t=[];e.length?e.filter(function(e){return null!=e}).forEach(function(e){return t.push(e.props)}):t.push(e.props),this.selectFirstItem(t),this.setState({childs:t})}},{key:"selectFirstItem",value:function(e){e.forEach(function(e){var t=e.options[0];t.subs&&t.subs.length&&(t=t.subs[0]),t.selected=!0,e.label=t.label,e.value=t.value})}},{key:"handleClick",value:function(e){var t=this.state.showIdx;this.setState({showIdx:t!==e?e:null})}},{key:"handleChange",value:function(e){var t=this.state,n=t.childs,r=t.showIdx,a=n[r];if(a.value==e)return void this.setState({showIdx:null});a.options.forEach(function(t){t.subs&&t.subs.length?t.subs.forEach(function(t){t.selected=t.value===e,t.selected&&(a.label=t.label,a.value=t.value)}):(t.selected=t.value===e,t.selected&&(a.label=t.label,a.value=t.value))}),this.setState({childs:n,showIdx:null}),a.onChange&&a.onChange(a.value,a.label)}},{key:"componentDidUpdate",value:function(){var e=this.state.showIdx;$(document.body).css("overflow",null!=e?"hidden":"auto")}},{key:"render",value:function(){var e=this,t=this,n=this.state,r=n.childs,a=n.showIdx;return React.createElement("section",{className:"c-search"},React.createElement("ul",{className:4==r.length?"title four":"title"},r.map(function(t,n){return React.createElement("li",{className:a==n?"cur":"",onClick:e.handleClick.bind(e,n)},React.createElement("span",null,t.label))})),null!=a?t.renderOptions(r[a].options,r[a].inlineBlock):null)}},{key:"renderOptions",value:function(e,t){var n=this,r=l(e),a=[];return t=t||!1,r.forEach(function(e){e.label&&a.push(React.createElement("h5",null,e.label,"："));var r=e.subs.map(function(e){var r=t?"line ":"";return r+=e.selected?"cur":"",React.createElement("li",{className:r,onClick:n.handleChange.bind(n,e.value)},React.createElement("span",null,e.label))});a.push(React.createElement("ul",null,r))}),React.createElement("div",{className:"selects"},React.createElement("div",{className:"cover",onClick:this.handleClick.bind(this,null)}),React.createElement("div",{className:"classname"},a))}}]),t}(React.Component);s.Item=i,t.exports=s},{}],8:[function(e,t,n){"use strict";function r(e){return{normal:{color:e,label:{show:!0,position:"center",formatter:"{b}",textStyle:{color:"#333",fontSize:18,baseline:"bottom"}},labelLine:{show:!1}}}}function a(e){return{normal:{color:"#d7d7d7",label:{show:!0,position:"center",textStyle:{color:e,fontSize:18}},labelLine:{show:!1}}}}var o=e("../../../utils/number"),l=o.toFixed,c={submit:["#619eed","#ffd270","#ff6e6e"],score:["#2fbd68","#74d64b","#5cc0ff","#ffcd61","#ff6661"]},i={submit:["正常提交","延迟提交","未提交"],score:["优秀","良好","及格","较差","危险"],cycles:{0:["周","月","学期","学年"],1:["上周","本周"],2:["上月","本月"],3:["上学期","本学期"],4:["上学年","本学年"]}},s={Colors:c,Labels:i};s.buildSubmitPie=function(e,t,n,r){r=r||"人";var a=arguments,o=i.submit.map(function(e,t){return{name:e,value:a[t]}}),l=e+t+n;return{color:c.submit,legend:{x:"left",orient:"vertical",selectedMode:!1,data:i.submit},tooltip:{formatter:"应交"+r+"数："+l+"<br />{b}：{c}（{d}%）"},series:[{type:"pie",center:["60%","50%"],radius:["40%","70%"],avoidLabelOverlap:!1,label:{normal:{show:!1,position:"center"},emphasis:{show:!0,textStyle:{fontSize:16,fontWeight:"bold"}}},labelLine:{normal:{show:!1}},data:o}]}},s.buildScorePie=function(e,t,n,r,a,o){o=o||"人";var l=arguments,s=i.score.map(function(e,t){return{name:e,value:l[t]}}),u=e+t+n+r+a;return{color:c.score,legend:{x:"left",orient:"vertical",selectedMode:!1,data:i.score},tooltip:{formatter:"有成绩总"+o+"数："+u+"<br />{b}"+o+"数：{c}（{d}%）"},series:[{type:"pie",center:["60%","50%"],radius:["40%","70%"],avoidLabelOverlap:!1,label:{normal:{show:!1,position:"center"},emphasis:{show:!0,formatter:"{b}:{c}"+o+",{d}%",textStyle:{fontSize:12,fontWeight:"bold"}}},labelLine:{normal:{show:!1}},data:s}]}},s.buildClassScoreBar=function(e,t,n){return{color:["#5b9bd5","#8bc036","#ffbe54"],tooltip:{trigger:"axis",axisPointer:{type:"shadow"}},legend:{data:["最高分","最低分"]},grid:{top:70,bottom:0,containLabel:!0},xAxis:[{type:"category",data:[""]}],yAxis:[{type:"value",max:100,splitNumber:10,axisLabel:{formatter:"{value} 分"}}],series:[{name:"平均分",type:"bar",barWidth:60,data:[l(e,1)]},{name:"最高分",type:"line",data:[l(t,1)],symbolSize:10,markPoint:{silent:!0,data:[{type:"max",name:"最高分"}]}},{name:"最低分",type:"line",data:[l(n,1)],symbolSize:10,markPoint:{silent:!0,data:[{type:"min",name:"最低分"}]}}]}},s.buildScoreCompBar=function(e,t,n,r){var a=i.cycles[e];return{color:["#5b9bd5","#ed7d31"],tooltip:{trigger:"axis"},legend:{data:a},grid:{top:30,bottom:30,left:50,right:40},xAxis:[{type:"category",data:t}],yAxis:[{type:"value",min:0,max:100,interval:10,axisLabel:{formatter:"{value} 分"}}],series:[{name:a[0],type:"bar",barMaxWidth:30,data:r,markLine:{data:[{type:"average",name:"平均分"}]}},{name:a[1],type:"bar",barMaxWidth:30,data:n,markLine:{data:[{type:"average",name:"平均分"}]}}]}},s.buildOverallScore=function(e,t){var n=[{type:"pie",center:["25%","50%"],radius:["60%","65%"],x:"0%",itemStyle:{normal:{label:{formatter:function(t){return e},textStyle:{baseline:"top"}}}},data:[{name:"综合得分",value:e,itemStyle:r("#00bb9c")},{name:"other",value:l(100-e,1),itemStyle:a("#f86b4f")}]}];return t&&n.push({type:"pie",center:["75%","50%"],radius:["60%","65%"],x:"10%",itemStyle:{normal:{label:{formatter:function(e){return t+"%"},textStyle:{baseline:"top"}}}},data:[{name:"领先",value:t,itemStyle:r("#66ccff")},{name:"other",value:l(100-t,1),itemStyle:a("#66ccff")}]}),{series:n}},s.buildScoreTrend=function(e,t,n){var r=[],a=[],o=[];return t&&(t=t.map(function(e){return null!=e?e:"-"}),a.push("#ed7d31"),o.push("我的得分"),r.push({name:"我的得分",type:"line",data:t,showAllSymbol:!0})),n&&(n=n.map(function(e){return null!=e?e:"-"}),a.push("#5b9bd5"),o.push("班级得分"),r.push({name:"班级得分",type:"line",data:n,showAllSymbol:!0})),{color:a,tooltip:{trigger:"axis"},legend:{data:o},grid:{top:30,bottom:30,left:50,right:10},dataZoom:[{type:"inside"}],xAxis:{type:"category",data:e},yAxis:{type:"value",max:100,interval:10,axisLabel:{formatter:"{value} 分"}},series:r}},s.buildScoreSectionBar=function(e,t){return{color:["#3398DB"],tooltip:{trigger:"axis",formatter:"{b}分{c}人",axisPointer:{type:"shadow"}},grid:{top:10,left:10,right:10,bottom:10,containLabel:!0},xAxis:[{type:"category",data:e,axisTick:{alignWithLabel:!0},axisLabel:{interval:0},splitLine:{show:!0,lineStyle:{color:["#ddd"],type:"dashed"}}}],yAxis:[{type:"value",axisLabel:{formatter:"{value}人"},splitLine:{show:!0,lineStyle:{type:"dashed"}}}],series:[{name:"",type:"bar",barWidth:"90%",data:t}]}},t.exports=s},{"../../../utils/number":15}],9:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=e("../../../utils/number"),i=c.toFixed,s=(c.toRate,e("./analyze-base")),u=s.toLevel,f=e("../../../react/SimpleTable"),p=["优秀","良好","及格","较差","危险","无成绩"],h=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"markRankIndex",value:function(e){for(var t=1e4,n=0,r=0;r<e.length;r++){var a=e[r].score;null!=a?(a<t&&(n++,t=a),e[r].index=n):e[r].index="--",e[r].level=u(a)}}},{key:"render",value:function(){var e=this.props,t=e.scoreRanks,n=e.scope;if(void 0==t||null==t)return null;this.markRankIndex(t);var r=[{title:"名次",width:"15%",field:"index"},{title:"姓名",width:"60%",field:function(e,t){return e.level<=5?React.createElement("a",{href:"detail/"+n+"-"+e.userId+".htm"},e.userName):e.userName}},{title:"成绩",width:"25%",field:function(e,t){return i(e.score,1)}}];return React.createElement("section",{id:this.props.id,className:"ana-module"},React.createElement("div",{className:"title"},"班级成绩排行"),React.createElement("div",{className:"c-table"},React.createElement(f,{columns:r,datas:[],showHead:!0}),p.map(function(e,n){var a=t.filter(function(e){return e.level==n+1}),o=React.createElement("caption",{className:"r-"+(n+1)},e+"("+a.length+"人)");return React.createElement(f,{caption:o,columns:r,datas:a,defVal:"--"})})))}}]),t}(React.Component);t.exports=h},{"../../../react/SimpleTable":5,"../../../utils/number":15,"./analyze-base":13}],10:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=e("../../../react/ReactChart"),i=e("./ScoreChartConfig"),s=function(e){function t(e){r(this,t);var n=a(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.state={idx:0},n}return o(t,e),l(t,[{key:"handleChangeType",value:function(e){this.setState({idx:e})}},{key:"renderTabs",value:function(e){if(!(e&&e.length>1))return null;var t=this,n=this.state.idx;return React.createElement("div",{className:"switch"},e.map(function(e,r){return React.createElement("span",{className:n==r?"cur":"",onClick:t.handleChangeType.bind(t,r)},e.label)}))}},{key:"render",value:function(){var e=this,t=this.props,n=t.trends,r=t.title;if(!n||!n.length)return null;var a=n[this.state.idx],o=a.names,l=a.selfs,s=a.klass;return React.createElement("section",{id:this.props.id,className:"ana-module"},React.createElement("div",{className:"title"},r,e.renderTabs(n)),React.createElement(c,{className:"maps",style:{height:180},option:i.buildScoreTrend(o,l,s)}))}}]),t}(React.Component);t.exports=s},{"../../../react/ReactChart":4,"./ScoreChartConfig":8}],11:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=e("../../../react/KnoFilterBox"),i=e("../../../react/SimpleTable"),s=e("../../../utils/number"),u=s.toFixed,f=(s.toRate,[{title:"班级得分率",width:"50%",field:function(e,t){return u(e.classRate,1)+"%"}},{title:"掌握程度",width:"50%",field:function(e,t){return React.createElement("span",{className:"l-"+e.level})}}]),p=function(e){function t(e){r(this,t);var n=a(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.state={levels:[1,2,3]},n.handleChangeLevel=n.handleChangeLevel.bind(n),n}return o(t,e),l(t,[{key:"resolveKnoRates",value:function(e){e.forEach(function(e){e.classRate>=85?e.level=1:e.classRate>=50?e.level=2:e.level=3})}},{key:"handleChangeLevel",value:function(e){this.setState({levels:e})}},{key:"render",value:function(){var e=this.state.levels,t=this.props.knoRates;return t&&t.length?(this.resolveKnoRates(t),t=t.filter(function(t){return e.indexOf(t.level)>=0}),React.createElement("section",{id:this.props.id,className:"ana-module"},React.createElement("div",{className:"title"},"知识点掌握程度"),React.createElement(c,{levels:e,onChange:this.handleChangeLevel}),React.createElement("div",{className:"c-table c-table-fixed"},React.createElement(i,{caption:React.createElement("caption",null,"知识点名称"),columns:f,datas:[],showHead:!0}),t.map(function(e){return React.createElement(i,{caption:React.createElement("caption",null,e.name),columns:f,datas:[e],defVal:"--"})})))):null}}]),t}(React.Component);t.exports=p},{"../../../react/KnoFilterBox":2,"../../../react/SimpleTable":5,"../../../utils/number":15}],12:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=e("../../../utils/number"),i=c.toFixed,s=c.toRate,u=e("./analyze-base"),f=(u.toLevel,u.toLevelName),p=e("../../../react/ReactChart"),h=e("./ScoreChartConfig"),m=function(e){function t(){return r(this,t),a(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return o(t,e),l(t,[{key:"render",value:function(){var e=this.props.summary,t=e.avgScore,n=e.maxScore,r=e.minScore,a=e.totalNum,o=e.otherNum,l=e.levelA,c=e.levelB,u=e.levelC,m=e.levelD,d=e.levelE,b=a-o;return React.createElement("section",{id:this.props.id,className:"ana-module"},React.createElement("div",{className:"title"},"成绩分析"),React.createElement("div",{className:"texts"},React.createElement("section",null,"敬爱的",React.createElement("span",{className:"fc-green"},ReportCst.userName),"老师："),React.createElement("section",{className:"txt-idt"},"您好，在此期间，本班级的学科平均分为",React.createElement("span",{className:"fc-green"},i(t,1)),"分，最高分",React.createElement("span",{className:"fc-green"},i(n,1)),"分， 最低分",React.createElement("span",{className:"fc-orange"},i(r,1)),"分，班级整体表现",React.createElement("span",{className:"fc-orange"},f(t)),"； 本班共有学生",React.createElement("span",{className:"fc-green"},a),"人， 其中成绩优秀",React.createElement("span",{className:"fc-green"},l),"人，占比",React.createElement("span",{className:"fc-green"},s(l,b,1),"%"),"， 良好",React.createElement("span",{className:"fc-green"},c),"人，占比",React.createElement("span",{className:"fc-green"},s(c,b,1),"%"),"， 及格",React.createElement("span",{className:"fc-green"},u),"人，占比",React.createElement("span",{className:"fc-green"},s(u,b,1),"%"),"， 较差",React.createElement("span",{className:"fc-orange"},m),"人，占比",React.createElement("span",{className:"fc-orange"},s(m,b,1),"%"),"， 危险",React.createElement("span",{className:"fc-orange"},d),"人，占比",React.createElement("span",{className:"fc-orange"},s(d,b,1),"%"),"。"),React.createElement(p,{className:"maps",option:h.buildClassScoreBar(t,n,r)}),React.createElement(p,{className:"maps",option:h.buildScorePie(l,c,u,m,d)})))}}]),t}(React.Component);t.exports=m},{"../../../react/ReactChart":4,"../../../utils/number":15,"./ScoreChartConfig":8,"./analyze-base":13}],13:[function(e,t,n){"use strict";var r={toSecond:function(e){return e<60?1:toFixed(e/60,1)},toLevel:function(e){return void 0==e||null==e?6:e>=85?1:e>=70?2:e>=60?3:e>=45?4:5},toLevelName:function(e){var t=r.toLevel(e);return r.levelNames[t-1]},levelNames:["优秀","良好","及格","较差","危险",null]};t.exports=r},{}],14:[function(e,t,n){"use strict";function r(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function o(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var l=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),c=e("../../react/Anchor"),i=e("../../react/Loading"),s=e("../../react/ToolTips"),u=e("./components/FilterBox"),f=e("./components/TeacherClassSummary"),p=e("./components/ScoreTrendInfo"),h=e("./components/ScoreRankListInfo"),m=e("./components/SubjectKnoRateInfo"),d=[{title:"成绩分析",link:"ach_summary"},{title:"班级成长走势",link:"ach_trend"},{title:"班级成绩排行",link:"ach_ranklist"},{title:"知识点掌握程度",link:"ach_knorate"}],b=ReportCst,y=b.klasses,v=b.cycles,g=function(e){function t(e){r(this,t);var n=a(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.state={loading:!0,classSubjId:y[0].value,cycleId:v[0].subs[0].value},n.handleChangeCycleId=n.handleChangeCycleId.bind(n),n.handleChangeClassSubjId=n.handleChangeClassSubjId.bind(n),n}return o(t,e),l(t,[{key:"handleQuery",value:function(e,t){this.setState({loading:!0}),$.post("data/"+e+".htm",{cycleId:t}).done(function(n){this.setState({loading:!1,scope:[e,t].join("-"),view:n.datas.view})}.bind(this))}},{key:"handleChangeCycleId",value:function(e){this.setState({cycleId:e});var t=this.state.classSubjId;this.handleQuery(t,e)}},{key:"handleChangeClassSubjId",value:function(e){this.setState({classSubjId:e});var t=this.state.cycleId;this.handleQuery(e,t)}},{key:"componentDidMount",value:function(){var e=this.state,t=e.classSubjId,n=e.cycleId;this.handleQuery(t,n)}},{key:"render",value:function(){return React.createElement("div",{className:"c-analyse"},React.createElement("nav",{className:"c-nav"},React.createElement("ul",null,React.createElement("li",{className:"cur"},"班级成绩分析"))),React.createElement(u,null,React.createElement(u.Item,{options:y,onChange:this.handleChangeClassSubjId}),React.createElement(u.Item,{options:v,onChange:this.handleChangeCycleId})),this.renderBody())}},{key:"renderBody",value:function(){var e=this.state,t=e.loading,n=e.view;return null==n||1==t?React.createElement(i,null):0==n.success?React.createElement(s,null,n.message):React.createElement("section",{className:"c-detail"},React.createElement(f,{id:"ach_summary",summary:n.summary}),React.createElement(p,{id:"ach_trend",trends:n.trends,title:"班级成长走势"}),React.createElement(h,{id:"ach_ranklist",scoreRanks:n.scoreRanks,scope:this.state.scope}),React.createElement(m,{id:"ach_knorate",knoRates:n.knoRates}),React.createElement(c,{items:d}))}}]),t
}(React.Component),R=document.getElementById("app");y&&y.length?ReactDOM.render(React.createElement(g,null),R):ReactDOM.render(React.createElement(s,null,"您暂无关联班级，无数据可供分析！"),R)},{"../../react/Anchor":1,"../../react/Loading":3,"../../react/ToolTips":6,"./components/FilterBox":7,"./components/ScoreRankListInfo":9,"./components/ScoreTrendInfo":10,"./components/SubjectKnoRateInfo":11,"./components/TeacherClassSummary":12}],15:[function(e,t,n){"use strict";function r(e,t){if(void 0===e||null===e||""===e||isNaN(+e)||!t)return e;var n,r,a,o,l,c,i,s,u,f,p=t.length,h=t.search(/[0-9\-\+#]/),m=h>0?t.substring(0,h):"",d=t.split("").reverse().join(""),b=d.search(/[0-9\-\+#]/),y=p-b,v=y+("."===t.charAt(y)?1:0),g=b>0?t.substring(v,p):"";if(t=t.substring(h,v),e="-"===t.charAt(0)?-e:+e,n=e<0?e=-e:0,r=t.match(/[^\d\-\+#]/g),a=r&&r[r.length-1]||".",o=r&&r[1]&&r[0]||",",t=t.split(a),e=e.toFixed(t[1]&&t[1].length),e=+e+"",c=t[1]&&t[1].lastIndexOf("0"),s=e.split("."),(!s[1]||s[1]&&s[1].length<=c)&&(e=(+e).toFixed(c+1)),u=t[0].split(o),t[0]=u.join(""),(l=t[0]&&t[0].indexOf("0"))>-1)for(;s[0].length<t[0].length-l;)s[0]="0"+s[0];else 0==+s[0]&&(s[0]="");if(e=e.split("."),e[0]=s[0],i=u[1]&&u[u.length-1].length){for(f=e[0],d="",y=f.length%i,p=f.length,v=0;v<p;v++)d+=f.charAt(v),!((v-y+1)%i)&&v<p-i&&(d+=o);e[0]=d}return e[1]=t[1]&&e[1]?a+e[1]:"",r=e.join(""),"0"!==r&&""!==r||(n=!1),m+(n?"-":"")+r+g}function a(e,t){var n=parseFloat(e);if(isNaN(n))return e;if((t=t||0)<1)return Math.round(n);var r=Math.pow(10,t);return Math.round(n*r)/r}function o(e,t,n){return 0==t?0:a(100*e/t,n||0)}t.exports={format:r,toFixed:a,toRate:o}},{}]},{},[14]);