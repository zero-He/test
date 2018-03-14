<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>教材大纲设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<style type="text/css">
.c-form>ul>li .title {
	width: 100px;
	background-color: #fff;
	height: 28px;
}
</style>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" data-bind="component: 'mat-list'"></div>
</div>

<leke:pref/>
<script>
seajs.use('question/syllabus/outline/outlineList');
</script>
</body>
</html>