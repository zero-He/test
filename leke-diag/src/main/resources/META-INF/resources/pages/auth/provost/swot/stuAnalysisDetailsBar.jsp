<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<title>学科优劣分析 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form id="searchForm" class="m-search-box">
			<div class = "f-fl">
			 <label class ="title">年级：${gradeName } &nbsp;</label>
			 <label class ="title">学科：${subjectName } &nbsp;</label>
			</div>
			<div class="f-fr">
				<input type ="hidden" name="schoolId" value="${schoolId }" />
				<input type ="hidden" name="subjectId" value="${subjectId }" />
				<input type="hidden" name="gradeId" value="${gradeId }" />
				<input type="hidden" name="startDate" value="${startDate }"  /> 
				<span> 时间：${startDate } ~  ${finishDate }</span>
			</div>
		</form>
		
		<div class="u-chart">
			<div id="stuGradeStates" style="height: 500px;"></div>
		</div>
	</div>
</div>
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/provost/stuAnalysisDetailsBar');
</script>
</body>
</html>