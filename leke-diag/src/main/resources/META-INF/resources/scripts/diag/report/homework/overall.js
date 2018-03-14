define(function (require, exports, module) {
"use strict";function _classCallCheck(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function _possibleConstructorReturn(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function _inherits(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}var _createClass=function(){function e(e,t){for(var a=0;a<t.length;a++){var r=t[a];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,a,r){return a&&e(t.prototype,a),r&&e(t,r),t}}(),React=require("common/react/react"),ReactDOM=require("common/react/react-dom"),_require=require("common/react/react-utils"),cx=_require.cx,ReactChart=require("common/react/ReactChart"),ReactDataGrid=require("../common/ReactDataGrid"),KnoGraspTable=require("../common/KnoGraspTable"),ScoreChartConfig=require("./ScoreChartConfig"),_require2=require("./helper"),toFixed=_require2.toFixed,toRate=_require2.toRate,toSecond=_require2.toSecond,toLevel=_require2.toLevel,toLevelName=_require2.toLevelName,Dialog=require("dialog"),SummaryInfo=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=this.props,t=e.summary,a=e.totalScore,r=e.userName,n=null;return t.submitNum>t.correctNum&&(n=React.createElement("span",null,"有",React.createElement("span",{className:"pointed"},t.submitNum-t.correctNum),"位学生作业未批改， 建议批改完全后，再查看报告更准确；")),React.createElement("div",{className:"summary"},React.createElement("p",null,"亲爱的",React.createElement("span",{className:"colored"},r),"老师："),React.createElement("p",{className:"details"},"您好，本次作业应交",React.createElement("span",{className:"colored"},t.totalNum),"人， 实交",React.createElement("span",{className:"colored"},t.submitNum),"人， 上交率",React.createElement("span",{className:"colored"},toRate(t.submitNum,t.totalNum,1),"%"),"；",n,"作业总分",React.createElement("span",{className:"colored"},toFixed(a)),"分， 班级平均分",React.createElement("span",{className:"colored"},toFixed(t.avgScore,1),"分"),"， 最高分",React.createElement("span",{className:"colored"},toFixed(t.maxScore,1),"分"),"， 最低分",React.createElement("span",{className:"pointed"},toFixed(t.minScore,1),"分"),"， 平均用时",React.createElement("span",{className:"colored"},toSecond(t.avgUsedTime),"分钟"),"， 班级整体表现",React.createElement("span",{className:"colored"},toLevelName(toRate(t.avgScore,a,1))),"。"))}}]),t}(React.Component),FinishInfo=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"buildScoreBar",value:function(){var e=this.props.summary;return{color:["#5b9bd5","#8bc036","#ffbe54"],legend:{selectedMode:!1,data:["最高分","最低分"]},tooltip:{trigger:"axis",axisPointer:{type:"shadow"}},grid:{top:60,left:60,right:60,bottom:0,containLabel:!0},xAxis:[{type:"category",data:[""]}],yAxis:[{type:"value",max:this.props.totalScore,splitNumber:10,axisLabel:{formatter:"{value} 分"}}],series:[{name:"平均分",type:"bar",barWidth:60,data:[toFixed(e.avgScore,1)]},{name:"最高分",type:"line",data:[toFixed(e.maxScore,1)],symbolSize:10,markPoint:{silent:!0,data:[{type:"max",name:"最高分"}]}},{name:"最低分",type:"line",data:[toFixed(e.minScore,1)],symbolSize:10,markPoint:{silent:!0,data:[{type:"min",name:"最低分"}]}}]}}},{key:"buildUsedTimeBar",value:function(){var e=this.props.summary,t=toSecond(e.avgUsedTime);return{color:["#33c8af","#ffbe54"],tooltip:{trigger:"axis",formatter:"{a}{c}分钟",axisPointer:{type:"shadow"}},grid:{top:60,left:60,right:60,bottom:0,containLabel:!0},xAxis:[{type:"category",data:[""]}],yAxis:[{type:"value",max:toFixed(1.2*t,1),splitNumber:10,axisLabel:{formatter:"{value} 分钟"}}],series:[{name:"平均用时",type:"bar",barWidth:60,data:[t]}]}}},{key:"render",value:function(){var e=this.props.summary,t=e.submitNum,a=e.delayNum,r=e.totalNum,n=t-a,o=r-t;return React.createElement("div",{className:"donework"},React.createElement("span",{className:"tittle"}),React.createElement("div",{className:"maps"},React.createElement("div",{className:"mapcontainer"},React.createElement(ReactChart,{className:"mapitem left3",option:this.buildScoreBar()}),React.createElement(ReactChart,{className:"mapitem center3",option:this.buildUsedTimeBar()}),React.createElement(ReactChart,{className:"mapitem right3",option:ScoreChartConfig.buildSubmitPie(n,a,o)}))))}}]),t}(React.Component),ScoreStatInfo=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"buildScoreBar",value:function(){for(var e=this.props.totalScore,t=this.props.scoreRanks,a=[],r=[],n=0;n<10;n++){var o=10*n,c=o+10,l=toFixed(e*o/100,0),s=toFixed(e*c/100,0);a.push(l+"~"+s),r.push(0)}return t.filter(function(e){return e.level<=5}).forEach(function(e){var t=parseInt(10*e.scoreRate);t>=10&&(t=9),r[t]++}),ScoreChartConfig.buildScoreSectionBar(a,r)}},{key:"render",value:function(){var e=this.props.scoreRanks,t=[0,0,0,0,0];return e.filter(function(e){return e.level<=5}).forEach(function(e){var a=toFixed(100*e.scoreRate,1),r=toLevel(a);t[r-1]++}),React.createElement("div",{className:"gradedistribution"},React.createElement("span",{className:"tittle"}),React.createElement("div",{className:"maps"},React.createElement("div",{className:"mapcontainer"},React.createElement(ReactChart,{className:"mapitem left1third",option:ScoreChartConfig.buildScorePie(t[0],t[1],t[2],t[3],t[4])}),React.createElement(ReactChart,{className:"mapitem right2thirds",option:this.buildScoreBar()})),React.createElement("div",{className:"tips"},"等级计算方法： 满分100分作业，85分以上为优秀，[70-85)分为良好，[60-70)分为及格，[45-60)分为较差，45分以下为危险，满分不为100分各等级按相应比例折算。")))}}]),t}(React.Component),QueScoreInfo=function(e){function t(){var e,a,r,n;_classCallCheck(this,t);for(var o=arguments.length,c=Array(o),l=0;l<o;l++)c[l]=arguments[l];return a=r=_possibleConstructorReturn(this,(e=t.__proto__||Object.getPrototypeOf(t)).call.apply(e,[this].concat(c))),r.buildTotal=function(e){var t=0,a=0,r=0;e.forEach(function(e){t+=e.qids.length,a+=e.totalScore,r+=e.classScore});var n=toRate(r,a,1);return{id:-1,name:"全部",length:t,totalScore:a,classScore:r,classRate:n}},n=a,_possibleConstructorReturn(r,n)}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=this.props.queGroups;e.forEach(function(e){return e.classRate=toRate(e.classScore,e.totalScore,1)});var t=this.buildTotal(e),a=[{title:"大题",width:"40%",field:"name"},{title:"总题数",width:"15%",field:"length",render:function(e,t,a){return e||t.qids.length}},{title:"总分数",width:"15%",field:"totalScore",render:function(e,t,a){return toFixed(e,1)}},{title:"平均得分",width:"15%",field:"classScore",render:function(e,t,a){return toFixed(e,1)}},{title:"得分率",width:"15%",field:"classRate",render:function(e,t,a){return toFixed(e,1)+"%"},onSort:!0}];return React.createElement("div",{className:"mainquestion"},React.createElement("span",{className:"tittle"}),React.createElement(ReactDataGrid,{columns:a,datas:e,total:t}),React.createElement("a",{href:"../../homework/analysis2.htm?homeworkId="+ReportCst.homeworkId,target:"_blank",className:"check-rate"},"查看试题得分率 >>"))}}]),t}(React.Component),KnoScoreInfo=function(e){function t(){var e,a,r,n;_classCallCheck(this,t);for(var o=arguments.length,c=Array(o),l=0;l<o;l++)c[l]=arguments[l];return a=r=_possibleConstructorReturn(this,(e=t.__proto__||Object.getPrototypeOf(t)).call.apply(e,[this].concat(c))),r.handleHelpClick=function(){Dialog.open({title:"帮助",url:Leke.domain.staticServerName+"/pages/help-center/diag/zw-cd.html"})},n=a,_possibleConstructorReturn(r,n)}return _inherits(t,e),_createClass(t,[{key:"resolveKnoRates",value:function(e){e.forEach(function(e){e.totalNum=e.qids.length,e.classRate=toFixed(100*e.classScore/e.totalScore,1),e.classRate>=85?e.level=1:e.classRate>=50?e.level=2:e.level=3})}},{key:"render",value:function(){var e=this.props,t=e.knoGroups,a=e.classId,r=e.subjectId;if(!t||!t.length)return null;this.resolveKnoRates(t);return React.createElement("div",{className:"knowledgepoint"},React.createElement("span",{className:"tittle"}),React.createElement("i",{className:"help",onClick:this.handleHelpClick}),React.createElement(KnoGraspTable,{knoRates:t,columns:"name,totalNum,totalScore,klassRate,level",increase:"hd"!=ReportCst.device&&101==Leke.user.currentRoleId,classId:a,subjectId:r}))}}]),t}(React.Component),ClassRankInfo=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"buildLevel",value:function(e,t,a){var r=this.props.homeworkId;return e=e.filter(function(e){return e.level==a}),e.map(function(n,o){return React.createElement("tr",null,0==o?React.createElement("td",{className:"rankclass-"+a,rowSpan:e.length},t,"(",e.length,"人)"):null,React.createElement("td",null,n.index),React.createElement("td",{className:"operation"},a<=5?React.createElement("a",{href:"./detail/"+r+"-"+n.userId+".htm",target:"_blank"},n.userName):""+n.userName),React.createElement("td",null,toFixed(n.score,1),6==a?"+":"",7==a?"--":""))})}},{key:"render",value:function(){for(var e=this,t=this.props.scoreRanks,a=this.props.homeworkId,r=1e4,n=0,o=0;o<t.length;o++){var c=t[o];c.level<=5?(c.score<r&&(n++,r=c.score),c.index=n):c.index="--"}var l=["优秀","良好","及格","较差","危险","未批改","未提交"],s=React.createElement("div",{style:{textAlign:"right"}},React.createElement("a",{href:"export.htm?homeworkId="+a,className:"u-btn u-btn-nm u-btn-bg-turquoise",target:"_blank"},"导出"));return React.createElement("div",{className:"classrank"},React.createElement("span",{className:"tittle"}),"hd"!=ReportCst.device?s:null,React.createElement("div",{className:"m-table m-table-center"},React.createElement("table",null,React.createElement("thead",null,React.createElement("tr",null,React.createElement("th",{style:{width:"15%"}},"层级"),React.createElement("th",{style:{width:"25%"}},"排名"),React.createElement("th",{style:{width:"30%"}},"姓名"),React.createElement("th",{style:{width:"30%"}},"分数"))),React.createElement("tbody",null,l.map(function(a,r){return e.buildLevel(t,a,r+1)})))),React.createElement("span",{className:"tips"},"注：点击学生姓名可查看该生学习分析报告"))}}]),t}(React.Component),HomeworkAnalysis=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=ReportCst,t=e.homeworkId,a=e.classId,r=e.subjectId,n=ReportCst.view,o=n.summary,c=n.totalScore,l=n.scoreRanks,s=n.queGroups,i=n.knoGroups,m=o.homeworkName;o.scoreRank;return React.createElement("div",{className:"analysis"},React.createElement("div",{className:"c-singlepage"},React.createElement("div",{className:"header"},React.createElement("h1",null,React.createElement("span",{className:"colored"},"《",m,"》"),"作业分析报告")),React.createElement(SummaryInfo,{summary:o,totalScore:c,userName:ReportCst.userName}),React.createElement(FinishInfo,{summary:o,totalScore:c}),React.createElement(ScoreStatInfo,{scoreRanks:l,totalScore:c}),React.createElement(QueScoreInfo,{queGroups:s}),React.createElement(KnoScoreInfo,{knoGroups:i,classId:a,subjectId:r}),React.createElement(ClassRankInfo,{scoreRanks:l,homeworkId:t})))}}]),t}(React.Component);ReactDOM.render(React.createElement(HomeworkAnalysis,null),document.getElementById("container"));
});