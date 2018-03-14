<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<li onclick="location.href='clsAnalysis.htm'"><a>选修班级</a></li>
				<li class="active"><a>行政班级</a></li>
			</ul>
		</div>
		<form id="searchForm" class="m-search-box">
			<input type="hidden" id="jClassType" name="classType" value="1" />
			<label class="title">年级学科：</label>
			<select id="jGradeSubject" class="u-select u-select-nm"></select>
			<input type="hidden" id="jGradeId" name="gradeId"/>
			<input type="hidden" id="jSubjectId" name="subjectId"/>
			<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
		</form>
		
		<div class="u-chart">
			<div id="allAnalysis" style="height: 500px; margin: 20px"></div>
		</div>
	</div>
</div>
<leke:pref />
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/provost/allAnalysis');
</script>
</body>
</html>