<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学情分析</title>
<%@ include file="/pages/common/mobile-meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/mobile/analyse.css">
</head>
<body>
<div id="app">
<div class="m-init"><i></i></div>
</div>
<script>var Csts = ${Csts};</script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/zepto.min.js?_t=20171115"></script>
<script type="text/javascript" src="${ctx}/scripts/m/diag/lib/echarts.bundle.js"></script>
<script type="text/javascript" src="${ctx}/scripts/m/diag/report/tchanaly-teach-behavior.webapi.js"></script>
<script type="text/javascript" src="${ctx}/scripts/m/diag/report/tchanaly-teach-behavior.bundle.js"></script>
</body>
</html>