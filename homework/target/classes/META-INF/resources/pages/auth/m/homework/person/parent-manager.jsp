<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>作业管家</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0,minimum-scale=1.0,maximum-scale=1.0">
<link rel="stylesheet" href="${assets}/styles/mobile/global.css?_t=20171115">
<link rel="stylesheet" href="${assets}/styles/mobile/homework/homeworkphone.css?_t=${_ts}">
<leke:context />
</head>
<body>
<div id="app">
<div class="m-init"><i></i></div>
</div>
<script type="text/javascript">window.Csts = ${csts};</script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/zepto.min.js?_t=20171115"></script>
<script type="text/javascript" src="${ctx}/scripts/m/homework/person/parent-manager.webapi.js?_t=${_ts}"></script>
<script type="text/javascript" src="${ctx}/scripts/m/homework/person/parent-manager.bundle.js?_t=${_ts}"></script>
</body>
</html>