<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${title}</title>
<meta id="homework-meta" content="width=1920px, initial-scale=0.5, maximum-scale=0.5, user-scalable=0.5" name="viewport">

<link rel="stylesheet"
	href="${assets}/styles/mobile/global.css" />
 <link rel="stylesheet" href="${assets}/styles/mobile/homework/homeworkpad.css"/>
<style type="text/css">
 		.workhome-body-bc{position:static;}
 		.work-bd{position:static;}
 	</style>
</head>
<body>
<div id="jzy" class="workhome-body-bc">
    <input name="id" type="hidden" value="${workId}"/>
    <nav class="work-nav">
    
	<div id ="jLstbody" class="work-hd">
	  <!--汇总-->
	</div>
	</nav>
	<div id="jbody" class="work-bd">
		<ul id ="jDLstbody" class="work-bd-ul">
		
		   <!--明细-->
		
		</ul>
	</div>
	</div>
	<textarea id="jLsttpl" class="f-dn" style="display:none">
	   	<div class="work-hd-progress">
			<div class="circleProgress_wrapper">
				<div class="work-wrapper work-wrapper-left">
					<div class="work-wrapper-circleProgress work-wrapper-leftcircle"></div>
				</div>
				<div class="work-wrapper work-wrapper-right">
					<div class="work-wrapper-circleProgress work-wrapper-rightcircle"></div>
				</div>
				<div class="work-wrapper-mask">
					<span class="work-hd-progress-name">作业进度</span> <span
						class="circlePercent work-hd-progress-detail">{{finishRate}}</span>%
				</div>
			</div>
		</div>
		<ul class="work-hd-content">
			<li class="work-hd-content-item">
				<p class="work-hd-content-count">作业总数</p>
				<p class="work-hd-content-num">{{total}}</p>
			</li>
			<li class="work-hd-content-item">
				<p class="work-hd-content-count">剩余未答作业</p>
				<p class="work-hd-content-num work-hd-content-surplus">{{relay}}</p>
			</li>
			<li class="work-hd-content-item">
				<p class="work-hd-content-count">开始答题时间</p>
				<p class="work-hd-content-num">{{firstTime}}</p>
			</li>
		</ul>
</textarea>
<textarea id="jDLsttpl" class="f-dn" style="display:none">
	     {{#dataList}}
			<li class="work-bd-item">
				<div class="holiday-work-name">
					<i class="holiday-work-name-ico holiday-winter"></i> <span
						class="holiday-work-name-detail">{{homeworkName}}</span>
				</div>
				<div class="holiday-work-fix-time">
					<h3 class="task-li-tit-h">来源：乐课网</h3>
					<p class="task-li-tit-p">{{createdOn}}</p>
				</div>
				<div class="holiday-work-sub-time">
					<h3 class="task-li-tit-h">提交时间:{{submitTime}}{{{islate}}}</h3>
					<p class="task-li-tit-p">{{endStr}}</p>
				</div>
				<div class="holiday-work-grade">
					{{{grade}}}
				</div>
				<div class="task-li-btn {{btnClass}}" data-msg="{{msg}}" data-state="{{submitStatus}}" data-hwid="{{homeworkId}}" data-hdid="{{homeworkDtlId}}">{{btnName}}</div>
				
			</li>
			{{/dataList}}
		
</textarea>
</body>

<script src="${assets}/scripts/common/mobile/common.js"></script>
 <script src="/scripts/hd/homework/vacationWork/trans.js"></script> 
<script src="/scripts/hd/homework/vacationWork/dropBind.js"></script>	
<script src="/scripts/hd/homework/vacationWork/DecList.js"></script>



</html>

