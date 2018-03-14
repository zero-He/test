define(function (require, exports, module) {
"use strict";function _classCallCheck(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function _possibleConstructorReturn(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function _inherits(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}function toFixedValue(e){return toFixed(e)}function toFixedRate(e){return toFixed(e)+"%"}var _createClass=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),React=require("common/react/react"),JSON=require("json"),Utils=require("utils"),_require=require("../utils/number"),toFixed=_require.toFixed,toRate=_require.toRate,ReactDataGrid=require("./ReactDataGrid"),KnoGraspFilter=function(e){function t(e){_classCallCheck(this,t);var n=_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.handleChange=n.handleChange.bind(n),n}return _inherits(t,e),_createClass(t,[{key:"handleChange",value:function(){var e=this.refs,t=[1,2,3].filter(function(t){return e["zw"+t].checked});this.props.onChange&&this.props.onChange(t)}},{key:"render",value:function(){return React.createElement("ul",null,React.createElement("li",null,React.createElement("input",{ref:"zw1",type:"checkbox",id:"zw_1",defaultChecked:!0,onChange:this.handleChange}),React.createElement("label",{htmlFor:"zw_1"},"已掌握")),React.createElement("li",null,React.createElement("input",{ref:"zw2",type:"checkbox",id:"zw_2",defaultChecked:!0,onChange:this.handleChange}),React.createElement("label",{htmlFor:"zw_2"},"掌握不牢")),React.createElement("li",null,React.createElement("input",{ref:"zw3",type:"checkbox",id:"zw_3",defaultChecked:!0,onChange:this.handleChange}),React.createElement("label",{htmlFor:"zw_3"},"未掌握")))}}]),t}(React.Component),VIEW_ERR_QUE_URL=Leke.domain.wrongtopicServerName+"/auth/student/wrong/wrongList.htm",EXERCISE_QUE_URL=Leke.domain.homeworkServerName+"/auth/student/exercise/doWork2.htm",TIPS={1:"得分率在85%以上",2:"班级得分率在[50%~85%)",3:"得分率在50%以下"},KnoGraspTableColumnDefs={name:{title:"知识点名称",field:"name",width:"25%"},totalNum:{title:"涉题数",field:"totalNum",render:toFixedValue,onSort:!0},rightNum:{title:"正确题数",field:"rightNum",render:toFixedValue},totalScore:{title:"分值",field:"totalScore",render:toFixedValue,onSort:!0},selfScore:{title:"得分",field:"selfScore",render:toFixedValue},selfRate:{title:"得分率",field:"selfRate",onSort:!0,render:toFixedRate},klassRate:{title:"班级得分率",field:"classRate",render:toFixedRate,onSort:!0},level:{title:"掌握程度",field:"level",render:function(e,t,n){return React.createElement("i",{className:"ismastered ismastered-"+e,title:TIPS[e]})}},wrong:{title:"查看错题",field:"id",render:function(e,t,n){return React.createElement("a",{href:VIEW_ERR_QUE_URL+"?knowledgeId="+e,className:"s-c-turquoise",target:"_blank"},"去查看")}},exercise:{title:"提升训练",field:"id",render:function(e,t,n){return React.createElement("a",{href:EXERCISE_QUE_URL+"?knoId="+e,className:"s-c-turquoise",target:"_blank"},"去训练")}}},KnoGraspTable=function(e){function t(e){_classCallCheck(this,t);var n=_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return n.handleChange=function(e){var t=n.state.knoIds;if(t.length){var r=n.filterLevels(n.props.knoRates,e),a=r.map(function(e){return e.id}).filter(function(e){return t.indexOf(e)>=0});n.setState({levels:e,knoIds:a})}else n.setState({levels:e})},n.handleCheckOne2=function(e){var t=n.state.knoIds,r=parseInt(e.target.value);if(e.target.checked)t.push(r);else{var a=t.indexOf(r);t.splice(a,1)}n.setState({knoIds:t})},n.handleCheckOne=function(e){var t=n.state.knoIds,r=t.indexOf(e);r>=0?t.splice(r,1):t.push(e),n.setState({knoIds:t})},n.handleCheckAll=function(e){if(e.target.checked){var t=n.filterLevels(n.props.knoRates,n.state.levels).map(function(e){return e.id});n.setState({knoIds:t})}else n.setState({knoIds:[]})},n.handleClickWork=function(e){var t=n.state.knoIds;if(t.length>0&&t.length<=30){var r=n.props.knoRates.filter(function(e){return t.indexOf(e.id)>=0}).map(function(e){return{knoId:e.id,diff:6-e.level}}),a=n.props,l=a.classId,i=a.subjectId,o={homeworkQuestionRule:r,classId:l,subjectId:i},c=Leke.ctx+"/auth/teacher/quick/assign.htm?data=";e.target.href=c+JSON.stringify(o)}else t.length>30&&Utils.Notice.alert("抱歉，单份提升作业勾选知识点数不能超过30个，建议分成多份作业布置"),e.target.href="javascript:void(0);",e.stopPropagation(),e.preventDefault()},n.filterLevels=function(e,t){return e.filter(function(e){return t.indexOf(e.level)>=0})},n.state={levels:[1,2,3],knoIds:[]},n}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=this,t=this.state,n=t.levels,r=t.knoIds,a=this.props,l=a.knoRates,i=a.columns,o=a.increase;return 0==l.length?null:(i=i.split(",").map(function(e){return KnoGraspTableColumnDefs[e]}),o&&(l.forEach(function(e){return e.checked=r.indexOf(e.id)>=0}),i.unshift({title:React.createElement("input",{type:"checkbox",onClick:e.handleCheckAll}),field:"id",render:function(t,n,r){return React.createElement("input",{type:"checkbox",key:t,checked:n.checked,onClick:function(){return e.handleCheckOne(t)}})}})),l=this.filterLevels(l,n),React.createElement("div",{className:"m-knoanaly"},React.createElement("div",{className:"clear"},o?React.createElement("span",{className:"tips"},"勾选知识点后，点击”布置提升作业“按钮可快速布置针对提升作业"):null,React.createElement(KnoGraspFilter,{onChange:this.handleChange})),React.createElement(ReactDataGrid,{columns:i,datas:l}),o?React.createElement("a",{target:"_blank",onClick:e.handleClickWork,className:"u-btn u-btn-auto u-btn-bg-"+(r.length?"turquoise":"gray")},"布置提升作业"):null))}}]),t}(React.Component);module.exports=KnoGraspTable;
});