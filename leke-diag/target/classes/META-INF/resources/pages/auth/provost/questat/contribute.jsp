<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>题库贡献统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form id="searchForm" class="m-search-box">
			<label class="title">时间：</label>
			<input id="jStartTime" name="startTime" class="u-ipt u-ipt-nm Wdate"/> —
			<input id="jEndTime" name="endTime" class="u-ipt u-ipt-nm Wdate"/>
			<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
		</form>
		
		<div class="u-chart">
			<div id="contribute" style="height: 400px; margin: 20px"></div>
		</div>
	</div>
</div>
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/provost/contribute');
</script>

</body>
</html>