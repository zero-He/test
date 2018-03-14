define(function (require, exports, module) {
"use strict";function fillNull(e){for(var t=0;t<e.length;t++)e[t]=null!=e[t]?e[t]:"-";return e}var _require=require("../utils/number"),toFixed=_require.toFixed,toRate=_require.toRate,CC={};CC.buildOverallFinishPie=function(e,t,a){return{color:["#92d050","#ffcc00","#cc3300"],legend:{bottom:"bottom",selectedMode:!1,data:["已完成","部分完成","未完成"]},tooltip:{formatter:"{b}：{c}人<br>比率：{d}%"},series:[{name:"完成情况",type:"pie",radius:"75%",center:["50%","47%"],label:{normal:{show:!1}},labelLine:{normal:{show:!1}},data:[{value:e,name:"已完成"},{value:t,name:"部分完成"},{value:a,name:"未完成"}]}]}},CC.buildCategoryFinishRadar=function(e,t){var a=[];return{tooltip:{},polar:[{indicator:t.map(function(t){return a.push(t.value),{name:t.name,max:e}}),center:["50%","57%"],radius:"80%",axisLabel:{show:!0,textStyle:{color:"#ccc"}},splitArea:{areaStyle:{color:["#ffffff","#ffffff"]}},splitLine:{lineStyle:{width:2,color:"#e9eaea"}}}],color:["#a9cfef"],series:[{type:"radar",data:[{value:a,name:"分类完成情况"}]}]}},CC.buildBehaviorTimeline=function(e,t){for(var a=[],i=[],r=0;r<=t;r++)e[r]?(i.push([r,0]),a.push(e[r][0])):a.push("");return{grid:{top:20,left:50,right:50,bottom:80},dataZoom:[{show:!0,realtime:!0},{type:"inside",realtime:!0}],tooltip:{position:"top",formatter:function(t){return e[t.value[0]].join("<br />")}},xAxis:{type:"category",data:a,boundaryGap:!1,axisLine:{lineStyle:{color:"#0ba299",width:8}},axisTick:{show:!1},axisLabel:{interval:function(t,a){return e[t]}}},yAxis:{type:"category",data:[""],axisLine:{show:!1},axisTick:{show:!1},axisLabel:{show:!1}},series:[{type:"scatter",symbolSize:15,data:i,animationDelay:function(e){return 5*e}}]}},module.exports=CC;
});