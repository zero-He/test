"use strict";function _classCallCheck(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function _possibleConstructorReturn(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function _inherits(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var _extends=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var s in n)Object.prototype.hasOwnProperty.call(n,s)&&(e[s]=n[s])}return e},_createClass=function(){function e(e,t){for(var n=0;n<t.length;n++){var s=t[n];s.enumerable=s.enumerable||!1,s.configurable=!0,"value"in s&&(s.writable=!0),Object.defineProperty(e,s.key,s)}}return function(t,n,s){return n&&e(t.prototype,n),s&&e(t,s),t}}();define(function(require,exports,module){function e(e,t,n){if(/^\d+\.?\d{0,2}$/.test(e)){var s=parseFloat(e);return s>=t&&s<=n}return!1}var t=require("common/react/react"),n=require("common/react/react-dom"),s=require("common/react/react-utils"),a=s.cx,r=require("./imgview"),o=require("jquery"),l=(require("utils"),require("dialog")),i=t.Component,c=t.PropTypes,u=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"],h=["√","X"],m=function(e){function n(e){_classCallCheck(this,n);var t=_possibleConstructorReturn(this,(n.__proto__||Object.getPrototypeOf(n)).call(this,e));return t.state={answers:[]},t.handlePosition=t.handlePosition.bind(t),t}return _inherits(n,e),_createClass(n,[{key:"componentWillReceiveProps",value:function(e){this.setState({answers:[]})}},{key:"handleChange",value:function(e){var t=this.state.answers,n=t.indexOf(e);this.props.multi?n>=0?t.splice(n,1):t.push(e):t=n>=0?[]:[e],this.setState({answers:t}),this.props.result&&(this.props.result.answers=t)}},{key:"handlePosition",value:function(){this.props.onPosition&&this.props.onPosition(this.props.zone)}},{key:"render",value:function(){var e=this,n=this,s=this.props.judge?h:u,r=this.props,o=r.qno,l=r.answers,i=this.state.answers;return t.createElement("li",null,t.createElement("dfn",null,o),l.map(function(r,o){var l=i.indexOf(r)>=0;return t.createElement("span",{key:r,className:a("item",{active:l}),onClick:e.handleChange.bind(n,r)},s[o])}),t.createElement("span",{key:"pos",className:"pos",onClick:this.handlePosition}))}}]),n}(i);m.propTypes={no:c.string,judge:c.bool,multi:c.bool};var p=function(n){function s(e){_classCallCheck(this,s);var t=_possibleConstructorReturn(this,(s.__proto__||Object.getPrototypeOf(s)).call(this,e));return e.items.forEach(function(t){return t.checked=t.value==e.value}),t.state={value:e.value,items:e.items,showItems:!1},t.handleInputChange=t.handleInputChange.bind(t),t}return _inherits(s,n),_createClass(s,[{key:"handleInputChange",value:function(t){var n=t.target.value,s=this.props,a=s.minVal,r=s.maxVal,o=s.onChange;if(""==n||e(n,a,r)){var l=this.state.items;l.forEach(function(e){return e.checked=!1}),this.setState({value:n,items:l}),o&&o(n)}}},{key:"handleItemClick",value:function(e){var t=this.state,n=t.value,s=t.items;s.forEach(function(t,s){t.checked=s==e,t.checked&&(n=t.value)}),this.setState({value:n,items:s,showItems:!1});var a=this.props.onChange;a&&a(n)}},{key:"componentDidMount",value:function(){var e=this,t=this.refs.input;o(t).click(function(t){var n=e.state.showItems;e.setState({showItems:!n})}),o(document).click(function(n){n.target!=t&&e.setState({showItems:!1})})}},{key:"render",value:function(){var e=this,n=this.state,s=n.value,a=n.items,r=n.showItems;return t.createElement("div",{className:"u-custom-select"},t.createElement("input",{ref:"input",type:"text",className:"u-ipt u-ipt-sm",value:s,onChange:this.handleInputChange}),t.createElement("ul",{className:"select-list",style:{display:r?"block":"none"}},a.map(function(n,s){var a=n.checked?"select-item select-item-on":"select-item";return t.createElement("li",{key:s,className:a,value:n.value,onClick:e.handleItemClick.bind(e,s)},n.label)})))}}]),s}(i),d=function(e){function n(e){_classCallCheck(this,n);var t=_possibleConstructorReturn(this,(n.__proto__||Object.getPrototypeOf(n)).call(this,e));return t.state={score:null},t.handleChange=t.handleChange.bind(t),t.handlePosition=t.handlePosition.bind(t),t}return _inherits(n,e),_createClass(n,[{key:"componentWillReceiveProps",value:function(e){this.setState({score:null}),this.refs.select.value=""}},{key:"handleChange",value:function(e){var t=e||"0";this.setState({score:t}),this.props.result&&(this.props.result.score=parseFloat(t))}},{key:"handlePosition",value:function(){this.props.onPosition&&this.props.onPosition(this.props.zone)}},{key:"render",value:function(){for(var e=this.props,n=e.qno,s=e.maxScore,a=[],r=Math.floor(s),o=0;o<=r;o++)a.push({value:""+o,label:""+o});return r<s&&a.push({value:""+s,label:""+s}),t.createElement("li",null,t.createElement("dfn",null,n),t.createElement(p,{value:"0",items:a,minVal:0,maxVal:s,onChange:this.handleChange}),t.createElement("span",{key:"pos",className:"pos",onClick:this.handlePosition}))}}]),n}(i),f=function(e){function n(){return _classCallCheck(this,n),_possibleConstructorReturn(this,(n.__proto__||Object.getPrototypeOf(n)).apply(this,arguments))}return _inherits(n,e),_createClass(n,[{key:"render",value:function(){return t.createElement("div",{className:"student"},t.createElement("i",{className:"icon-student"}),t.createElement("span",null,"学生：",this.props.children))}}]),n}(i),v=function(e){function n(){return _classCallCheck(this,n),_possibleConstructorReturn(this,(n.__proto__||Object.getPrototypeOf(n)).apply(this,arguments))}return _inherits(n,e),_createClass(n,[{key:"render",value:function(){return t.createElement("div",{className:"reason"},t.createElement("i",{className:"icon-reason"}),t.createElement("span",null,"异常原因：",this.props.children))}}]),n}(i),b=function(e){function n(e){return _classCallCheck(this,n),_possibleConstructorReturn(this,(n.__proto__||Object.getPrototypeOf(n)).call(this,e))}return _inherits(n,e),_createClass(n,[{key:"render",value:function(){this.props.rules;return t.createElement("div",{className:"c-abnormal-fill"},t.createElement("ul",{className:"lists"},this.props.children))}}]),n}(i),k=function(e){function n(e){_classCallCheck(this,n);var t=_possibleConstructorReturn(this,(n.__proto__||Object.getPrototypeOf(n)).call(this,e));return t.state={bookId:e.bookId,imgs:[],rules:[],userName:"",errorNo:"",results:[],zone:null},t.fillCurrBook(e.bookId),t.handleJump=t.handleJump.bind(t),t.handleSave=t.handleSave.bind(t),t.handlePosition=t.handlePosition.bind(t),t}return _inherits(n,e),_createClass(n,[{key:"fillCurrBook",value:function(e){o.post("get.htm",{bookId:e}).done(function(e){e.success&&this.setState(e.datas)}.bind(this))}},{key:"doNextBook",value:function(e){e?this.fillCurrBook(e):l.alert("已经是最后一个").done(function(){window.close()})}},{key:"handleJump",value:function(){var e=this.state.bookId;o.post("next.htm",{bookId:e}).done(function(e){this.doNextBook(e.datas.nextBookId)}.bind(this))}},{key:"handleSave",value:function(){var e=this.state,t=e.bookId,n=e.results,s=JSON.stringify(n);o.post("save.htm",{bookId:t,dataJson:s}).done(function(e){e.success&&this.doNextBook(e.datas.nextBookId)}.bind(this))}},{key:"handlePosition",value:function(e){this.refs.imgview&&this.refs.imgview.handleDraw&&this.refs.imgview.handleDraw(e)}},{key:"render",value:function(){var e=this,n=this,s=this.state,a=s.bookId,o=s.imgs,l=s.rules,i=s.userName,c=s.errorNo,u=s.zone,h=void 0,p=void 0;return"20302"===c?(h="",p=t.createElement("div",{className:"c-abnormal-match"},t.createElement("p",null,"1. 学生",t.createElement("span",{className:"s-c-orange"},"填涂客观题答案超过设置的正确答案数量"),"，如单选题填涂多个答案；"),t.createElement("p",null,"2. 学生",t.createElement("span",{className:"s-c-orange"},"未在规定区域答题"),"，如超出答题区等；"),t.createElement("p",null,"3. 老师",t.createElement("span",{className:"s-c-orange"},"未在主观题打分框打分"),"，或者打分不规范；"))):(h="",p=t.createElement("div",{className:"c-abnormal-match"},t.createElement("p",null,"题目",t.createElement("span",{className:"s-c-orange"},"得分为0分，疑似异常"),"；"),t.createElement("p",null,"异常原因：学生填涂不规范，",t.createElement("span",{className:"s-c-orange"},"如未填涂，填涂过淡，填涂区域小"),"。"))),t.createElement("div",null,t.createElement("div",{className:"c-sheet-show"},t.createElement(r,{ref:"imgview",imgs:o,index:u&&u.idx?u.idx-1:null})),t.createElement("div",{className:"c-sheet-operation"},t.createElement("h5",{className:"head"},"异常处理"),t.createElement("div",{className:"con"},t.createElement(f,null,i),t.createElement(v,null,h),p,t.createElement(b,null,l.map(function(s,r){var o=n.state.results.filter(function(e){return e.questionId==s.qid})[0];return"1"==s.rangeType?t.createElement(m,_extends({key:a+r},s,{result:o,onPosition:e.handlePosition})):"11"==s.rangeType?(o.score=0,t.createElement(d,_extends({key:a+r},s,{result:o,onPosition:e.handlePosition}))):t.createElement("li",{key:a+r},s.title)})),t.createElement("div",{className:"btns"},t.createElement("button",{className:"u-btn u-btn-nm u-btn-bg-turquoise",onClick:this.handleSave},"提交"),t.createElement("button",{className:"u-btn u-btn-nm u-btn-bg-gray",onClick:this.handleJump},"暂不处理")))))}}]),n}(i);n.render(t.createElement(k,{bookId:firstBookId}),document.getElementById("container"))});