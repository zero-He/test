<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="que.title.question.mine"></locale:message></title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="z-resource">
		<div class="top" data-bind="component:{
				name: 'top-user-res-group',
				params: {resType: resType}
			}"></div>
		<div class="resourcecontent">
			<div class="left" data-bind="component:{
				name: 'left-user-res-group',
				params: {resType: resType,shareScope: shareScope,userResGroupId: userResGroupId,delUserResGroup: delUserResGroup}
			}"></div>
			<div class="right"  data-bind="component: {
				name: 'right-resource-list',
				params: {resType: resType,shareScope: shareScope,userResGroupId: userResGroupId}
			}"></div>
		</div>
	</div>
</div>
<div data-bind="component: 'que-cart'"></div>
<leke:pref/>
<script>
document.domain = '${initParam.mainDomain}';
seajs.use('question/pages/schoolResearcher/question/personal/list');
</script>
</body>
</html>