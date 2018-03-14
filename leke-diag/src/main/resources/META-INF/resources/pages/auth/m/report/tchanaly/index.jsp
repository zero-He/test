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
<script type="text/javascript" src="${assets}/scripts/common/mobile/zepto.min.js?_t=${_t}"></script>
<script type="text/javascript" src="${ctx}/scripts/m/diag/lib/echarts.bundle.js?t=${_t}"></script>
<script type="text/javascript" src="${ctx}/scripts/m/diag/report/tchanaly/tchanaly.webapi.js?t=${_t}"></script>
<script type="text/javascript" src="${ctx}/scripts/m/diag/report/tchanaly/tchanaly.bundle.js?t=${_t}"></script>
</body>
</html>