<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ include file="/pages/common/cordova.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>寒暑假作业</title>
<meta id="homework-meta" content="width=1920px, initial-scale=0.5, maximum-scale=0.5, user-scalable=0.5" name="viewport">

<link rel="stylesheet" href="${assets}/styles/mobile/global.css" />
<link rel="stylesheet"
	href="${assets}/styles/mobile/homework/homeworkpad.css" />
<style>
.homework-bd-work {
	position: static !important;
} 
</style>
</head>
<body>

	<div class="homework-hd-work">
		<div class="homework-hd-work-bd">
			<div class="homework-hd-work-item task homework-hd-work-item-on">作业</div>
			<div class="homework-hd-work-item lesson">微课</div>
		</div>
	</div>
	<div class="homework-bd-work">
		<div id="jzy" class="homework-bd-work-task homework-bd-work-item">
		
			<ul id="jZYbody" >
				<!--作业-->
					
			</ul>
		</div>
		<div id="jwk" class="homework-bd-work-lesson homework-bd-work-item">
			<ul id="jWKbody">
				<!--微课-->
					
			</ul>
		</div>
	</div>

	<textarea id="jZYtpl" class="f-dn" style="display: none">
		{{#dataList}}
		<li class="task-li">
			<div class="task-li-logo task-li-logo-cover{{subjectId}}"></div>
			<div class="task-li-tit ">
				<h3 class="task-li-tit-h">{{year}}年{{holiday}}作业&nbsp;·&nbsp;{{subjectName}}</h3>
				<p class="task-li-tit-p">作业数:<i>{{total}}</i>
					</p>
			</div>
			<div class="task-li-progress">
				<span class="task-li-progress-bar"><i class="progress-bar" style="width:{{finishRate}}%"></i></span><span
					class="task-li-progress-percentage">{{finishRate}}%</span>
				<span class="task-li-progress-remark"><i class="remark-ic"></i><i
					class="remark-time">{{startMsg}}</i></span>
			</div>
			<div class="task-li-btn lesson-btn-study {{btnClass}} answer"
				data-url="{{{url}}}">
				{{btnNam}}
			</div>
		</li>
	   {{/dataList}}
</textarea>

	<textarea id="jWKtpl" class="f-dn" style="display: none">
	     {{#dataList}}
			<li class="task-li">
			<div class="task-li-logo task-li-logo-cover{{subjectId}}"></div>
				<div class="task-li-tit ">
					<h3  class="task-li-tit-h">{{year}}年{{holiday}}作业&nbsp;·&nbsp;{{subjectName}}</h3>
					<p class="task-li-tit-p">
						微课数:<i>{{total}}</i>
					</p>
				</div>
				<div class="task-li-publish">
					<p class="task-li-publish-p">{{bookName}}·{{matVersionName}}</p>
				</div>
				
				<div class="task-li-progress">
					<span class="task-li-progress-bar"><i class="progress-bar" style="width:{{finishRate}}%" ></i></span><span
					class="task-li-progress-percentage">{{finishRate}}%</span> <span
					class="task-li-progress-remark"><i class="remark-ic"></i><i
					class="remark-time">{{startMsg}}</i></span>
				</div>
				<div
				class="task-li-btn  lesson-btn-study   {{btnClass}} study"
				data-url="{{{url}}}">{{btnNam}}</div>
			</li>
			{{/dataList}}
		
</textarea>

</body>
<script src="${assets}/scripts/common/mobile/common.js"></script>
<script src="/scripts/hd/homework/vacationWork/trans.js"></script> 
<script src="/scripts/hd/homework/vacationWork/dropBind.js"></script>
<script src="/scripts/hd/homework/vacationWork/StuWorkList.js"></script>
</html>
