<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${homeworkName}</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" href="${assets}/styles/mobile/global.css?_t=${_t}">
<link rel="stylesheet" href="${assets}/styles/mobile/homework/sheetphone.css?_t=${_ts}">
<%@ include file="/pages/common/cordova.jsp"%>
<leke:context />
</head>
<body>
<div id="app">
<div class="m-init"><i></i></div>
</div>
<script type="text/javascript">window.Csts = ${viewWorkModel};</script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/zepto.min.js?_t=${_t}"></script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/iscroll-zoom.min.js?_t=${_t}"></script>
<script type="text/javascript" src="${ctx}/scripts/m/homework/person/student-dowork.webapi.js?_t=${_ts}"></script>
<script type="text/javascript" src="${ctx}/scripts/m/homework/person/student-dowork.bundle.js?_t=${_ts}"></script>
</body>
</html>
