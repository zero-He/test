define(function (require, exports, module) {
"use strict";function _classCallCheck(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function _possibleConstructorReturn(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function _inherits(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}function showAchieveUserNames(e,t){if(!e||!e.length)return void Utils.Notice.alert("还没有学生获取"+t+"成就");var a='<div class="names">'+e.map(function(e){return"<span>"+e+"</span>"}).join("")+"</div>";Dialog.open({size:"nm",title:t+"成就获得名单",tpl:a})}var _extends=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var a=arguments[t];for(var n in a)Object.prototype.hasOwnProperty.call(a,n)&&(e[n]=a[n])}return e},_class2,_temp2,_class3,_temp3,_createClass=function(){function e(e,t){for(var a=0;a<t.length;a++){var n=t[a];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,n.key,n)}}return function(t,a,n){return a&&e(t.prototype,a),n&&e(t,n),t}}(),React=require("common/react/react"),ReactChart=require("common/react/ReactChart"),ReactPieChart=require("../common/ReactPieChart"),_require=require("../utils/number"),toFixed=_require.toFixed,toRate=_require.toRate,Dialog=require("dialog"),Utils=require("utils"),Section=require("../common/Section"),AnalyseResult=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=this.props,t=e.query,a=e.view;return null==a?React.createElement("div",{className:"m-mask"},React.createElement("div",{className:"con"},React.createElement("i",null),React.createElement("div",null,"数据加载中"))):0==a.success?React.createElement("div",{className:"m-tips"},React.createElement("i",null),React.createElement("span",null,a.message)):React.createElement("div",{className:"c-analayse"},React.createElement(SummaryInfo,{id:"ach_summary",view:a}),React.createElement(DiligentInfo,{id:"ach_diligent",view:a}),React.createElement(AchieveInfo,{id:"ach_achieve",view:a}),React.createElement(AttendInfo,{id:"ach_attend",view:a,query:t}),React.createElement(HomeworkInfo,{id:"ach_homework",view:a,query:t}),React.createElement(AchieveRank,{id:"ach_achieverank",view:a}))}}]),t}(React.Component),SummaryInfo=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=this.props.view,t=e.achieveScore,a=e.achieveNum,n=e.attendInfo,r=n.lessonNum,c=n.normalNum,l=n.usersNum,i=DiligentInfo.achieveLevels[Math.floor(t/20)],s='敬爱的<span class="fc-green">'+e.userName+"</span>老师：",o='在此期间，您共上课<span class="fc-green">'+r+'</span>堂，班级全勤率<span class="fc-green">'+toRate(c,l)+"%</span>；";return o+='班级共取得成就<span class="fc-green">'+a+'</span>项，学科勤奋指数为<span class="fc-green">'+t+"</span>，",o+='勤奋层级为<span class="fc-green">'+i+"</span>；",o+="请及时掌握学情，督促学生养成良好的学习习惯，教学轻松更有效。",React.createElement("section",{id:this.props.id,className:"c-paragraph"},React.createElement("section",{className:"title",dangerouslySetInnerHTML:{__html:s}}),React.createElement("section",{className:"con",dangerouslySetInnerHTML:{__html:o}}))}}]),t}(React.Component),HelpLink=function(e){function t(){var e,a,n,r;_classCallCheck(this,t);for(var c=arguments.length,l=Array(c),i=0;i<c;i++)l[i]=arguments[i];return a=n=_possibleConstructorReturn(this,(e=t.__proto__||Object.getPrototypeOf(t)).call.apply(e,[this].concat(l))),n.handleClick=function(){var e=n.props.href;Dialog.open({title:"帮助",url:e})},r=a,_possibleConstructorReturn(n,r)}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){return React.createElement("i",{className:"help",onClick:this.handleClick})}}]),t}(React.Component),DiligentInfo=(_temp2=_class2=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"buildGaugeOption",value:function(e){var a=Math.floor(e/20);return{series:[{type:"gauge",radius:"90%",data:[{name:t.achieveLevels[a],value:e}],axisLine:{lineStyle:{color:[[.2,"#7bb57b"],[.8,"#85c6f4"],[1,"#ffac8e"]]}}}]}}},{key:"buildLineOptions",value:function(e){return{color:["#76b3a1"],title:{text:"勤奋指数走势",left:"center",textStyle:{fontWeight:"normal"}},tooltip:{trigger:"item",formatter:"{b}：{c}"},grid:{left:"3%",right:"3%",bottom:"8%",containLabel:!0},xAxis:{type:"category",boundaryGap:!1,data:e.map(function(e){return e.name.replace(/（\S*）/,"")})},yAxis:{type:"value"},series:[{type:"line",areaStyle:{normal:{}},data:e.map(function(e){return e.value})}]}}},{key:"render",value:function(){var e=this.props.view,t=(e.achieveInfo,e.trends),a=e.achieveScore;return React.createElement(Section,{id:this.props.id,title:"勤奋指数",help:Leke.domain.staticServerName+"/pages/help-center/diag/diligence-index.html"},React.createElement("div",{className:"c-layout"},React.createElement("div",{className:"left",style:{width:"40%"}},React.createElement(ReactChart,{className:"maps",option:this.buildGaugeOption(a)})),React.createElement("div",{className:"right",style:{width:"50%"}},React.createElement(ReactChart,{className:"maps",option:this.buildLineOptions(t)}))),React.createElement("div",{className:"tips"},"勤奋指数共有5个层级分别是：[80-100]为非常勤奋，[60-80)为很勤奋，[40-60)为较勤奋，[20-40)为不够勤奋，[0-20)为不勤奋。"))}}]),t}(React.Component),_class2.achieveLevels=["不勤奋","不够勤奋","较勤奋","很勤奋","非常勤奋","非常勤奋"],_temp2),achieveSettings=[{color:"#cf960b",title:"全勤"},{color:"#cfbe0c",title:"提前预习"},{color:"#d07a0d",title:"专心听讲"},{color:"#0dadcf",title:"勤思好问"},{color:"#83cf0c",title:"活跃分子"},{color:"#cf550c",title:"手有余香"},{color:"#0bbfce",title:"及时改错"},{color:"#11cf6f",title:"不磨蹭"},{color:"#cfa511",title:"练习达人"},{color:"#1090cf",title:"温故知新"}],AchieveInfo=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=this.props.view,t=e.userNum,a=e.achieves;a.forEach(function(e,a){e.total=t,e.value=e.userIds.length,e.title=achieveSettings[a].title,e.color=toRate(e.value,e.total)>=70?achieveSettings[a].color:"#aeaeae"});var n=supportCanvas?AchieveBall:AchieveBallIE8;return React.createElement(Section,{id:this.props.id,title:"班级行为成就",help:Leke.domain.staticServerName+"/pages/help-center/diag/class-behavior.html"},React.createElement("div",{className:"con"},React.createElement("div",{className:"achievements"},a.map(function(e){return React.createElement(n,_extends({key:e.title},e))}))))}}]),t}(React.Component),supportCanvas=function(){try{return!!document.createElement("canvas").getContext}catch(e){return!1}}();window.requestAnimationFrame=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(e){window.setTimeout(e,100)}}();var AchieveBall=function(e){function t(e){_classCallCheck(this,t);var a=_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).call(this,e));return a.running=!0,a}return _inherits(t,e),_createClass(t,[{key:"componentDidMount",value:function(){function e(){o++;var a=80*i,n=o*Math.PI/180,r=3*Math.sin(n),l=3*Math.cos(n);s.clearRect(0,0,80,80),s.fillStyle=c,s.beginPath(),s.moveTo(0,a+r),s.bezierCurveTo(40,a+r-3,40,a+l-3,80,a+l),s.lineTo(80,80),s.lineTo(0,80),s.lineTo(0,a+r),s.closePath(),s.fill(),t.running&&requestAnimationFrame(e)}var t=this,a=this.props,n=a.value,r=a.total,c=a.color,l=toRate(n,r),i=(100-l)/100,s=this.refs.cvs.getContext("2d"),o=1;e()}},{key:"componentWillUnmount",value:function(){this.running=!1}},{key:"render",value:function(){var e=this.props,t=e.title,a=e.total,n=e.value,r=e.userNames,c=toRate(n,a);return React.createElement("div",null,React.createElement("div",{className:"c-wavebg",onClick:function(){return showAchieveUserNames(r,t)}},React.createElement(AchieveTips,{title:t,total:a,value:n,score:c}),React.createElement("div",{className:"wv-text"},t),React.createElement("canvas",{ref:"cvs",height:"80",width:"80"})))}}]),t}(React.Component),AchieveBallIE8=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=this.props,t=e.title,a=e.value,n=e.total,r=e.color,c=e.userNames,l=toRate(a,n);return React.createElement("div",null,React.createElement("div",{className:"c-wavebg-ie8",onClick:function(){return showAchieveUserNames(c,t)}},React.createElement(AchieveTips,{title:t,total:n,value:a,score:l}),React.createElement("div",{className:"wv-progress",style:{height:l+"%",background:r}}),React.createElement("div",{className:"wv-text"},t)))}}]),t}(React.Component),AchieveTips=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=this.props,t=(e.title,e.value),a=e.total,n=e.score;return React.createElement("div",{className:"pop"},React.createElement("span",null,"班级人数：",a,"人",React.createElement("br",null),"获得人数：",t,"人",React.createElement("br",null),"获得比率：",n,"%"),React.createElement("div",{className:"arrow"},React.createElement("em",null),React.createElement("i",null)))}}]),t}(React.Component),AttendInfo=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=this.props,t=e.view,a=e.query,n=t.attendInfo,r=n.lessonNum,c=n.usersNum,l=n.normalNum,i=n.belateNum,s=n.earlyNum,o=n.exceptNum,u=n.absentNum,m=[{value:l,name:"全勤"},{value:i,name:"迟到"},{value:s,name:"早退"},{value:o,name:"迟到且早退"},{value:u,name:"缺勤"}],p=["#83cf0b","#619eed","#70cfff","#ffd270","#ff6d6e"],h='班级共<span class="green-txt">'+t.userNum+"</span>人，";return h+='总课堂数<span class="green-txt">'+r+"</span>堂，",h+='考勤统计<span class="green-txt">'+c+"</span>人次。",React.createElement(Section,{id:this.props.id,title:"班级考勤",help:Leke.domain.staticServerName+"/pages/help-center/diag/kq-gz-xs.html",detail:"./klass-behavior-attend/"+a.klassId+"-"+a.cycleId+".htm?_newtab=1"},React.createElement(ReactPieChart,{datas:m,summary:h,color:p,chartTitle:"attend",unit:"人次"}))}}]),t}(React.Component),HomeworkInfo=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"render",value:function(){var e=this.props,t=e.view,a=e.query,n=t.homeworkCount,r=n.studentTotalNum,c=n.classHomeworkNum,l=n.submitNum,i=n.lateSubmitNum,s=n.noSubmitNum,o=n.submitPro,u=n.lateSubmitPro,m=n.noSubmitPro,p=[{value:l,name:"按时提交",rate:o},{value:i,name:"延后补交",rate:u},{value:s,name:"未提交",rate:m}],h='共学生<span class="green-txt">'+r+'</span>人，共需完成班级作业<span class="green-txt">'+c+"</span>份";return React.createElement(Section,{id:this.props.id,title:"作业提交统计",detail:"./klass-behavior-work/"+a.klassId+"-"+a.cycleId+".htm?_newtab=1"},React.createElement(ReactPieChart,{datas:p,summary:h,chartTitle:"homework",unit:"人份"}))}}]),t}(React.Component),AchieveRank=(_temp3=_class3=function(e){function t(){return _classCallCheck(this,t),_possibleConstructorReturn(this,(t.__proto__||Object.getPrototypeOf(t)).apply(this,arguments))}return _inherits(t,e),_createClass(t,[{key:"toLevel",value:function(e){if(null!=e){var t=4-Math.floor(e/20);return t<0?0:t}return 5}},{key:"markRankIndex",value:function(e){for(var t=1e4,a=0,n=0;n<e.length;n++){var r=e[n].score;null!=r?(r<t&&(a++,t=r),e[n].index=a):e[n].index="--",e[n].level=this.toLevel(r)}}},{key:"render",value:function(){var e=this.props.view.userRanks;if(!e||!e.length)return null;this.markRankIndex(e);return React.createElement(Section,{id:this.props.id,title:"学生行为成就排行榜"},React.createElement("div",{className:"con"},React.createElement("div",{className:"m-table m-table-center ranklist"},React.createElement("table",null,React.createElement("thead",null,React.createElement("tr",null,React.createElement("th",null,"层级"),React.createElement("th",null,"排名"),React.createElement("th",null,"姓名"),React.createElement("th",null,"勤奋指数"),React.createElement("th",null,"获得成就数"))),React.createElement("tbody",null,t.achieveLevels.map(function(t,a){var n=e.filter(function(e){return e.level==a}),r=React.createElement("td",{rowSpan:""+n.length,className:"lv-"+(a+1)},t+"("+n.length+"人)");return n.map(function(e,t){return React.createElement("tr",null,0===t?r:null,React.createElement("td",null,e.index),React.createElement("td",null,e.userName),React.createElement("td",null,e.score),React.createElement("td",null,e.achieveNum))})}))))))}}]),t}(React.Component),_class3.achieveLevels=["非常勤奋","很勤奋","较勤奋","不够勤奋","不勤奋","无数据"],_temp3);module.exports=AnalyseResult;
});