  <%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>详情页面- 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/monitor/monitor.css" type="text/css">
<link rel="stylesheet" href="${assets}/styles/home/personal-center.css" type="text/css">
</head>

<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd"> 
  <%@ include file="/pages/navigate/navigate.jsp"%>
  	<input type="hidden" id="day" name="day" value="${day}" />
  	<input type="hidden" id="schoolName" name="schoolName" value="${schoolName}" />
  	<input type="hidden" id="schoolId" name="schoolId" value="${schoolId}" />
  <div class="g-mn" id="g-mn" data-bind="component: {
		name: 'class-people-total-view',
		params: {
			options:{
				schoolId: ${schoolId},
				postUrl:'/auth/educationDirector/course/findCourseSingleByQuery.htm',
				exportUrl:'/auth/educationDirector/course/exportCourseSingleByQuery.htm'
			}
		}
	}">	
	</div>
</div>
<script>
  	seajs.use('monitor/pages/educationDirector/course/classPeopleView');
</script>
</body>
</html>