<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/cordova.jsp"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>寒暑假作业</title>
	<meta id="homework-meta" content="width=1920px, initial-scale=0.5, maximum-scale=0.5, user-scalable=0.5" name="viewport">
	<link rel="stylesheet" href="${assets}/styles/mobile/global.css"/>
	<link rel="stylesheet" href="${assets}/styles/mobile/homework/homeworkpad.css"/>
	<style>
		.vacation-section {
			position: static !important;
		}
	</style>
</head>
<body>

<div class="workhome-body-bc">
	<nav class="vacation-nav">
		<div class="vacation-nav-content">
			<div id="jXKbody" class="assign-bd-nav "></div>
			<span class="assign-bd-nav-line"></span>
			<div id="jBJbody" class="assign-bd-nav"></div>
			<span class="assign-bd-nav-line"></span>
			<div id="jHSJbody" class="assign-bd-nav"></div>
		</div>
	</nav>
	<section id="jbody" class="vacation-section">
		<ul id="jLstbody"></ul>
	</section>
</div>

<%--学科--%>
<textarea id="jXKtpl" class="f-dn" style="display: none">
		<i class="assign-bd-nav-text vacation-nav-content-text" id="XK" data-id="{{select_id}}">{{select_name}}</i>
		<i class="assign-bd-nav-pull"></i>
		<ul class="assign-testpaper-bank">
	    {{#dataList}}
			<li class="assign-testpaper-bank-li  vacation-select" data-clazz="{{Clazz}}" data-id="{{subjectId}}">{{subjectName}}</li>
		{{/dataList}}
		</ul>
</textarea>

<%--班级--%>
<textarea id="jBJtpl" class="f-dn" style="display: none">
		<i class="assign-bd-nav-text vacation-nav-content-text" id="BJ" data-id="{{select_id}}">{{select_name}}</i>
		<i class="assign-bd-nav-pull"></i>
		<ul class="assign-testpaper-bank">
	    {{#clazzList}}
			<li class="assign-testpaper-bank-li  vacation-select" data-id="{{classId}}">{{className}}</li>
	    {{/clazzList}}
		</ul>
</textarea>

<%--寒暑假--%>
<textarea id="jHSJtpl" class="f-dn" style="display: none">
		<i class="assign-bd-nav-text vacation-nav-content-text" id="HSJ" data-yearid="{{year_id}}" data-holiday="{{holiday_id}}">{{year_name}}</i>
		<i class="assign-bd-nav-pull"></i>
		<ul class="assign-testpaper-bank">
	    {{#yearList}}
			<li class="assign-testpaper-bank-li  vacation-select" data-yearid="{{yearId}}" data-holiday="{{holiday}}">{{yearName}}</li>
	    {{/yearList}}
		</ul>
</textarea>

<textarea id=jLsttpl class="f-dn" style="display: none">
		 {{#dataList}}	
		<li class="vacation-section-item">
				<dl class="vacation-section-item-dl">
					<dt class="vacation-section-item-por">
						<!-- <div class="task-li-logo task-li-logo-cover{{subjectId}}"></div> -->
						<img src="{{src}}" class="vacation-section-pro-img">
						 <span class="vacation-section-pro-text f-ellipsis">{{userName}}</span>
					</dt>
					<dd>
						<ul>
							<li class="vacation-detail-tiny vacation-detail-li">
							<span class="vacation-detail-li-name f-fl"> {{type}}</span>
							<span class="task-li-progress-bar">
								<i class="progress-bar vacation-progress-bar" style="width: {{finishRate}}%"></i>
							</span>
							<span class="task-li-progress-percentage vacation-progress-percentage">{{finishRate}}%</span>
							<span class="vacation-detail-li-start">开始学习:<i class="vacation-detail-li-time">{{firstTime}}</i></span>
						    <span class="vacation-detail-li-end">最近学习:<i class="vacation-detail-li-time">{{lastTime}}</i></span>
							<button class="task-li-btn vacation-task-li-btn review" data-url={{url}}>查看详情</button></li>
						</ul>
					</dd>
				</dl>
			</li>
	 {{/dataList}}
</textarea>

</body>
<script src="${assets}/scripts/common/mobile/common.js"></script>
<script src="/scripts/hd/homework/vacationWork/trans.js"></script>
<script src="/scripts/hd/homework/vacationWork/dropBind.js"></script>
<script src="/scripts/hd/homework/vacationWork/TchWorkList.js"></script>
</html>