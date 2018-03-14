<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<!DOCTYPE html>
<html>
<head>
<title>测试页</title>
<%@ include file="/pages/common/meta.jsp"%>
<style type="text/css">
dl { font-size: 20px; line-height: 28px; }
dt { float: left; width: 100px; }
dd { overflow: hidden; }
dd a {color: blue;text-decoration: underline;}
dd a:visited {color: purple;}
</style>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">

<dl>
	<dt>教研员</dt>
	<dd>
		<ul>
			<li><a href="${ctx}/auth/researcher/workbook/workbookList.htm" target="_blank">习题册管理</a></li>
		</ul>
	</dd>
	<dt>教师</dt>
	<dd>
		<ul>
			<li><a href="${ctx}/auth/teacher/workbook/favorite/list.htm" target="_blank">我的收藏 - 习题册</a></li>
			<li><a href="${ctx}/auth/teacher/leke/workbook/list.htm" target="_blank">乐课习题册</a></li>
		</ul>
	</dd>
</dl>
	</div>
</div>
</body>
</html>