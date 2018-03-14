<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>习题册库 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header-repo.jsp" %>
<div class="f-bgwhite" data-bind="component: {name: 'workbook-list',params: config}"></div>
<leke:pref/>
<script>
seajs.use('question/pages/schoolResearcher/leke/workbook/list');
</script>
</body>
</html>