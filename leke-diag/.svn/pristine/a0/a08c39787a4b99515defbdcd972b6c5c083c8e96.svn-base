<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>作业勤奋报告 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form id="searchForm" class="m-search-box">
			<label class="title">学生姓名：</label>${stuDiligentDtlForm.studentName}
			<label class="title">学科：</label>${stuDiligentDtlForm.subjectName}
			<c:if test="${stuDiligentDtlForm.classType == 1}">
				<label class="title">时间：</label>${stuDiligentDtlForm.startTime} 至 ${stuDiligentDtlForm.endTime}
			</c:if>
			<input type="hidden" id="studentId" name="studentId" value="${stuDiligentDtlForm.studentId}"/>
			<input type="hidden" id="classId" name="classId" value="${stuDiligentDtlForm.classId}"/>
			<input type="hidden" id="classType" name="classType" value="${stuDiligentDtlForm.classType}"/>
			<input type="hidden" id="subjectId" name="subjectId" value="${stuDiligentDtlForm.subjectId}"/>
			<input type="hidden" id="startTime" name="startTime" value="${stuDiligentDtlForm.startTime}"/>
			<input type="hidden" id="endTime" name="endTime" value="${stuDiligentDtlForm.endTime}"/>
		</form>	
		<div class="u-chart">
			<div id="submitState" style="height: 500px;"></div>
		</div>
    	<div class="m-table">
	    	<table>
	    		<thead>
		    		<tr>
		    			<th>作业名称</th>
		    			<th>提交状态</th>
		    			<th>提交时间</th>
		    			<th>截至时间</th>
		    		</tr>
	    		</thead>
	    		<tbody id="stuSubmitStateBody"></tbody>
			</table>
		</div>
	</div>
</div>
<script id="stuSubmitState_tpl" type="x-mustache">
<tr>
<td>{{homeworkName }}</td>
<td>{{submitStatus }}</td>
<td>{{submitTime }}</td>
<td>{{closeTime }}</td>
</tr>
</script>
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/common/SubmitStatePie', function(SubmitStatePie) {
		SubmitStatePie.init();
	});
</script>
</body>
</html>