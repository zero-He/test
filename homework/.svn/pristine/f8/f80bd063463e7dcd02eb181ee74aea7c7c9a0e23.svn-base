<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>学生端-我的作业</title>
	<meta name="viewport" content="width=1920">
	<script src="${assets}/scripts/common/mobile/rem.js"></script>
	<link rel="stylesheet" href="${assets}/styles/mobile/global.css"/>
	<link rel="stylesheet" href="${assets}/styles/mobile/homework/homeworkpad.css"/>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<!--作业学科列表-->
<article class="c-student-work-list">
	<ul class="my-work">
		<div class="template-context"></div>
		<script id="myHomeworkContext_tpl" type="text/x-handlebars-template">
			{{#each this}}
			<a href="${initParam.homeworkServerName}/auth/hd/queryStudentHomeworkList.htm?subjectId={{subjectId}}">
				<li class="busy-work">
					<h3 class="work-name">{{subjectName}}</h3>
					<p class="work-num-p">
						未完成<span class="num">{{submitNum}}</span>份
					</p>
					<p class="work-num-p">
						待订正<span class="num">{{bugFixNum}}</span>份
					</p>
				</li>
			</a>
			{{/each}}
		</script>
	</ul>
</article>
</body>
<leke:pref/>
<script src="${assets}/scripts/common/mobile/common.js"></script>
<script src="${assets}/scripts/common/handlebars.min.js"></script>
<script src="/scripts/homework/homework/student/subjectList.js"></script>
</html>