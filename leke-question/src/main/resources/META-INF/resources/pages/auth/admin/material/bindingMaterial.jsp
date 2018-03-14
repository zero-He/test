<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>绑定教材 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" href="${assets}/styles/resource/resource.css?t=${_t}"/>

</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" data-bind="component:{name: 'mat-binding',params: config}">
	</div>
</div>

<script>
window.materialCtx = {
		material: ${material}
};
seajs.use('question/view/material/bindingMaterial');
</script>
</body>
</html>