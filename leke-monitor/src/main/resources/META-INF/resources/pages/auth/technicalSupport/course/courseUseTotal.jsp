<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html >
<html>
<head>
<title>课堂使用统计 - 乐课网</title>

<%@ include file="/pages/common/meta.jsp" %>

</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd"> <%@ include file="/pages/navigate/navigate.jsp"%>

    <div class="g-mn">
    	<div id="jFlash">
		</div>
    </div>
</div>
<script>
seajs.use(['swfobject'], function(swfobject) {
	var user = Leke.user;
	swfobject.embedSWF(Leke.domain.staticServerName + '/flashs/common/ui/ui-videoclass/monitor/MonitorClient.swf',
			'jFlash', '910px', '625px', "11.1.0", "expressInstall.swf", {
				userId: user.userId,
				roleId: user.currentRoleId
			}, {
				wmode : "transparent",
				menu : "false"
			}, null);
});
</script>
</body>
</html>