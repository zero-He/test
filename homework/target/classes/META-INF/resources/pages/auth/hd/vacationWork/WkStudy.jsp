<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ include file="/pages/common/cordova.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${assets}/styles/mobile/global.css" />
<title>微课学习</title>

</head>
<body>
    <input name="id" type="hidden" value="cn.strong:leke-homework:war:2.6.8-SNAPSHOT" />
    <input type="hidden" id="j-onlineServerName" value="${initParam.onlineServerName }" />
	<input name="microId" type="hidden" value="${microId}" />
	<div class="playerBox" style="width:900px;height:450px;position:absolute;top:50%;left:50%;margin:-225px 0 0 -450px;">
	 	
	</div>
</body>


	<script >
		window.Csts = ${Csts};
		window.currentRoleId = ${currentRoleId};
	</script>
<script src="${assets}/scripts/common/mobile/common.js"></script>
<script src="https://static.leke.cn/scripts/common/mobile/ui/ui-fileplayer/ui-fileplayer-pad.js"></script>
<script src="/scripts/hd/homework/vacationWork/WkStudy.js" ></script>
</html>