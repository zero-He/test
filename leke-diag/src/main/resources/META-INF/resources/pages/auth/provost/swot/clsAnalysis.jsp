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
		<div>
			<ul class="m-tab">
				<li class="active"><a>选修班级</a></li>
				<li onclick="location.href='allAnalysis.htm'"><a>行政班级</a></li>
			</ul>
		</div>
		<form id="searchForm" class="m-search-box">
			<input type="hidden" id="jClassType" name="classType" value="2" />
			<label class="title">年级：</label>
			<select id="jStageGrade" class="u-select u-select-nm"></select>
			<input type="hidden" id="jGradeId" name="gradeId">
			<label class="title">班级：</label>
			<select id="jClassId" name="classId" class="u-select u-select-nm">
				<c:choose>
					<c:when test="${fn:length(clazzList) == 0}">
						<option value="0">没有对应班级</option>
					</c:when>
					<c:otherwise>
						<c:forEach var="clazz" items="${clazzList }">
							<option value="${clazz.classId }">${clazz.className }</option>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</select>
			<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
		</form>
		
		<div class="u-chart">
			<div id="clsAnalysis" style="height: 500px; margin: 20px"></div>
		</div>
	</div>
</div>
<leke:pref />
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain-2.0.2.js"></script>
<script type="text/javascript">
	seajs.use('diag/provost/clsAnalysis');
</script>
</body>
</html>