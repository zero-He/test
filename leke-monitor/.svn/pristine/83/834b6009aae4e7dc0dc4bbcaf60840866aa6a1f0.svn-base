<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>学校使用趋势</title>
<%@ include file="/pages/common/meta.jsp"%>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css" type="text/css">
<link rel="stylesheet"
      type="text/css"
      href="https://static.leke.cn/styles/common/global.css">
<link rel="stylesheet"
      href="https://static.leke.cn/styles/lesson-monitor/lesson-monitor-pc.css">
<link rel="stylesheet" href="../../../../scripts/monitor/component/weekpicker.css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>

		<div class="m-search-box">
            <div class="item">
					<span>学校名称：<em id="">${query.schoolName}</em></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span><em id="schoolId" style="display:none;">${query.schoolId}</em></span>
					<span>统计时间：<em id="startDate">${query.startDate}</em></span>
					<span>~ <em id="endDate">${query.endDate}</em></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span>资源类型：<em id="">${query.resTypeName}</em></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span><em id="resType" style="display:none;">${query.resType}</em></span>
					<span>资源来源：<em id="">${query.resResourceName}</em></span>
					<span><em id="resResource" style="display:none;">${query.resResource}</em></span>
			</div> 
			<div class="chartWrap">
				<h3 class="c-monitor-tit"><B>学校使用趋势</B></h3>
				<div class="chartComt" id="barSta"></div>
            </div>
        </div>
		
	</div>
</body>
<script type="text/javascript">
	seajs.use('monitor/pages/common/resource/schoolUsedTrend');
</script>
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>

</html>
