<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>作业批改统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form id="searchForm" class="m-search-box">
			<label class="title">学科：</label>
			<select id="jStageSubject" class="u-select u-select-nm"></select>
			<input type="hidden" id="jSubjectId" name="subjectId">
			<label class="title">教师：</label>
			<select id="jTeacherId" name="teacherId" class="u-select u-select-nm"></select>
			<label class="title">时间：</label>
			<input id="jStartTime" name="startTime" class="u-ipt u-ipt-nm Wdate"/> —
			<input id="jEndTime" name="endTime" class="u-ipt u-ipt-nm Wdate"/>
			<input name="schoolId" type="hidden" value="${schoolId}"/>
			<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
		</form>

		<div class="u-chart">
			<div id="correctStat" style="height: 400px; margin: 20px"></div>
		</div>
		<div id="correctStatList" class="m-table">
			<table>
				<thead>
					<tr>
						<th class="width_70">序号</th>
						<th>作业标题</th>
						<th>班级名称</th>
						<th class="width_120">全部批改</th>
						<th class="width_120">部分批改</th>
						<th class="width_120">未批改</th>
					</tr>
				</thead>
				<tbody id="correctStatListBody"></tbody>
			</table>
		</div>
	</div>
</div>

<script id="correctStatList_tpl" type="x-mustache">
{{#dataList}}
<tr>
	<td>{{index}}</td>
	<td>{{homeworkName}}</td>
	<td>{{className}}</td>
	<td><font class="f-fwb">{{all}}</font></td>
	<td><font class="f-fwb">{{part}}</font></td>
	<td><font class="f-fwb">{{not}}</font></td>
</tr>
{{/dataList}}
</script>

<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script type="text/javascript">
	seajs.use('diag/provost/correctStat');
</script>

</body>
</html>