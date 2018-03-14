<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>作业管家</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0,minimum-scale=1.0,maximum-scale=1.0">
	<link rel="stylesheet" href="${assets}/scripts/common/mobile/ui/ui-coursewarePreview/skin/ui-coursewarePreview.css?_t=20171115">
	<script></script>
	<leke:context/>
</head>
<body>
<input type="hidden" name="homeworkDtlId" id="homeworkDtlId" value="${homeworkDtlId}"/>
<input type="hidden" name="learnType" id="learnType" value="${learnType}"/>
<input type="hidden" id="jOnlineServerName" value="${initParam.onlineServerName }" />
<div class="video"></div>
<div class="audio"></div>
<div class="picviewer"></div>
<script>

</script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/zepto.min.js?_t=20171115"></script>
<script src="${assets}/scripts/common/mobile/ui/ui-coursewarePreview/ui-coursewarePreview.js?_t=20171115"></script>
<script src="/scripts/m/homework/person/studentLearning.js?_t=20171115"></script>
</body>
</html>
