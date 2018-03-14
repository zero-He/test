<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>自主练习-<locale:message code="common.page.header.leke" /></title>
<link rel="stylesheet" type="text/css" href="${assets }/styles/homework/homework.css">
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
    <div class="m-bg"></div>
    <div class="g-bd">

        <div class="c-selftrain">
            <div class="c-selftrain-rank">
                <div class="ranklist">
                    <div class="left">
                        <div class="title">
                            <p>第<span>${weekOfYear}</span>周前50名排行榜，排行数据截止到周日晚12点</p>
                        </div>
                        <div class="switch-tab">
                            <ul class="j-ul-week">
                                <li class="active"><a data-id="${weekOfYear}"  class="hasdivision">本周</a></li>
                                <li><a data-id="${weekOfYear -1 }">上周</a></li>
                                <input type="hidden" id="j-weekOfYear" value="${weekOfYear}" />
                            </ul>
                        </div>
                        <div class="with-infor j-show-container">
	                        <div class="dynamicinfor " >
	                            <span class="inform"></span>
	                            <span id="j-self-rank-tips"></span>
	                        </div>
	                        <div class="weekrank">
	                            <div class="header">
	                                <ul>
	                                    <li class="rank">排名</li>
	                                    <li>姓名</li>
	                                    <li>学校</li>
	                                    <li>正确数</li>
	                                </ul>
	                            </div>
	                            <div class="body">
	                                <ul id="j-body-week-rank">
	                                   
	                                </ul>
	                            </div>
	                        </div>
	                        <img src="${assets }/images/homework/wait4u.gif" alt="" class="wait4u-left ">
                        </div>
                        <div class="without-infor f-hide" id="j-no-week-rank">
                            <img src="${assets }/images/homework/wait4u-center.gif" alt="" class="wait4u-center">
                        </div>
                    </div>
                    <div class="right">
                        <img src="${assets }/images/homework/today-rank.png" alt="" class="title">
                        <div class="mit-infom j-show-container">
	                        <div class="lists">
	                            <ul id="j-body-today-rank">
	                               
	                            </ul>
	                        </div>
                        </div>
                        <div class="ohne-infom f-hide " id="j-no-today-rank">
                            <div class="m-tips">
                                <i></i>
                                <p>今天的动态榜等着你的到来~</p>
                            </div>
                        </div>
                        <input type="button" value="开始练习" class="startexe" id="j-start-exercise">
                    </div>
                </div>
                <div class="m-search-area j-search-param">
                    <div class="classified">
                        <div class="left">学　　科：</div>
                        <div class="right">
                            <ul class="j-ul-subjectId" data-deflen="10">
                            </ul>
                            <i class="toggle plus j-toggle"></i>
                        </div>
                    </div>
                    <div class="classified">
                        <div class="left">练习时间：</div>
                        <div class="right">
       						 <ul>
                                <li class="on"><a data-param="timeRangeDays" >全部</a></li>
                                <li><a data-param="timeRangeDays" data-v="1">最近一天</a></li>
                                <li><a data-param="timeRangeDays" data-v="3">最近三天</a></li>
                                <li><a data-param="timeRangeDays" data-v="7">最近一周 </a></li>
                                <li><a data-param="timeRangeDays" data-v="30">最近一个月</a></li>
                                <li><a data-param="timeRangeDays" data-def="1" >自定义</a></li>
                            </ul>
	                           <span class="j-def-SFTime f-hide">
	                            <input type="text" id="startDate" class="u-ipt u-ipt-nm Wdate" />
								到
								<input type="text" id="endDate" class="u-ipt u-ipt-nm Wdate" />
								</span>
                        </div>
                    </div>
                    <div class="classified">
                        <div class="left">练习模式：</div>
                        <div class="right">
                            <ul >
                                <li class="on"><a data-param="exerciseType" data-v = "" >全部</a></li>
                                <li><a data-param="exerciseType" data-v="1">章节</a></li>
                                <li><a data-param="exerciseType" data-v="2">知识点</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="exelists">
                    <div class="m-table">
                        <table>
                        	<thead>
                        	<tr>
                                <th width="10%">学科</th>
                             <!--    <th width="10%">难度</th>
                                <td>{{fmtDiffLevelName}}</td> -->
                                <th width="18%">提交时间</th>
                                <th width="32%">章节/知识点</th>
                                <th width="15%">正确率</th>
                                <th width="15%">操作</th>
                            </tr>
                        	</thead>
                            <tbody id="j-body-exercises">
                           
                           
                       		 </tbody>
                        </table>
                        <div class="page" id="page"></div>
						<div class="m-tips f-block" id="tips">
							<i></i>
							<span>
								没有你要查询的数据~
							</span>
						</div>
                    </div>
                </div>
            </div>
        
        	<!-- 用户详情 -->
			<div id="j_userDetail_body" class="userdetails" style="position:absolute;"></div>		
        	
        </div>

    </div>
    <leke:pref />
	</body>


<script type="x-mustache" id="j-tmpl-today-rank">
{{#.}}
<li>
    <img src="{{photo}}" alt="">
    <span>
        <em class="f-fl name">{{userName}}</em>
        <em class="f-fl color-gray">{{schoolName}}</em>
    </span>
    <span>
        <em class="f-fr color-gray">{{submitTime}}</em>
        <em class="f-fr">【{{subjectName}}】答对
            <i class="bold">{{rightNum}}</i>
            题
        </em>
    </span>
</li>
{{/.}}
</script>
<script type="x-mustache" id="j-tmpl-week-rank">
{{#.}}
<li class="
		{{#rankTop}}top3 {{/rankTop}} 
		{{rankTop}}">
    <i>{{rankNo}}</i>
    <span>
        <img src="{{photo}}" alt="" class="j-user-photo user-detail-show f-csp" data-uid= {{userId}}>
     	   {{userName}}
    </span>
    <span>{{schoolName}}</span>
    <span><label> {{total}} </label></span>
</li>
{{/.}}
</script>
<script type="x-mustache" id="j-tmpl-exercises">
{{#.}}
 <tr>
     <td>{{subjectName}}</td>
     <td>{{fmtSubmitTime}} </td>
     <td>
         <span>{{exerciseName}}</span>
     </td>
     <td>
		{{{fmtAccuracy}}}
     </td>
     <td class="operation">
		{{^submitTime}}
         <a target="_blank" href="/auth/student/exercise/doExerciseWork.htm?id={{exerciseId}}">继续练习</a>
		{{/submitTime}}
		{{#submitTime}}
         <a target="_blank" href="/auth/student/exercise/report.htm?id={{exerciseId}}">练习报告</a>
		{{/submitTime}}
     </td>
 </tr>
{{/.}}
</script>

<script id="j_userDetail_tmpl" type="x-tmpl-mustache">
{{#.}}
<div class="pic">
	<img src="{{user.photo}}" />
</div>
<div class="m-percenter-center">
	<p>{{user.userName}}({{extra.honor}})</p>
	<i class="vip-{{extra.levelVal}}"></i>
	<p>乐豆：{{extra.lekeVal}}</p>
</div>
<div class="medal">
	<div class="m-scroll m-scroll-horizontal">
			<div class="box">
				<ul class="list-wrap">
					{{#extra.medalList}}
					<li class="list"><a><img src="{{medalIcon}}" title="{{medalName}}"></a></li>
					{{/extra.medalList}}
				</ul>
			</div>
	</div>
</div>
</div>
{{/.}}
</script>
<script>
	seajs.use('homework/exercise/list');
</script>
</html>
