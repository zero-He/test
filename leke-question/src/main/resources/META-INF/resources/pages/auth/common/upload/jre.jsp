<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>JRE 下载</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="g-mn">
		<div class="crumbs"></div>
		<div class="detail">
			<input type="text" id="filepath" style="width: 200px;">
			
			<div>
				<a class="btn btn-greed" id="btnSubmit">确定</a>
			</div>
		</div>
	</div>
</div>

<script id="applet_tpl" type="x-mustache">
<div class="que-ed-applet-wrap">
	<applet id="{{appid}}" code="cn.leke.applets.localupload.LocalUpload"
		codebase="{{ctx}}/applet"  archive="localupload-0.0.5.jar"
		width="1" height="1">
		<param name="permissions" value="all-permissions">
		<param name="java_status_events" value="true">
	<applet>
</div>
</script>

<script type="text/javascript">
window.EdCtx = {
	domain: '${initParam.questionServerName}',
	ticket: '${ticket}',
	wimgEnabled: true
};
seajs.use('question/jre');
</script>

</body>
</html>