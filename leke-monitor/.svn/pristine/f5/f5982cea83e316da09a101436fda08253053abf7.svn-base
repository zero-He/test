<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>上课人数统计- 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/monitor/monitor.css" type="text/css">
</head>

<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd"> 
<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" id="g-mn">
	<div data-bind="component: {
		name: 'class-people-total-echart',
		params: {
			options: {
				roleId: Leke.user.currentRoleId,
				postUrl:'/auth/educationDirector/course/dailyCourseStats.htm',
				urlView:'/auth/educationDirector/course/classPeopleView.htm',
				exportUrl:'/auth/educationDirector/course/exportDailyCourseStats.htm'
			}
		}
	}"></div>
	</div>
</div>
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script>
	seajs.use('monitor/pages/educationDirector/course/classPeopleTotal');
</script>
</body>
</html>