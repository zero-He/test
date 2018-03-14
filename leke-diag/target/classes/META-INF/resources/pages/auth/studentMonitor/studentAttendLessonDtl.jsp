<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>课堂考勤详情</title>
	<%@ include file="/pages/common/meta.jsp"%>
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">
</head>
<body>
	<form id="form" action="/auth/provost/studentMonitor/studentAttend/exportStudentAttendData.htm">
	<div class="m-bg"></div>
	<div class="c-analyse15 c-analyse15__homesubdetail">
		<div class="c-analyse15__homesubdetail-title">
			<h3 class="c-analyse15__homesubdetail-titleword">课堂考勤详情</h3>
		</div>
		<div class="c-analyse15__attendance-classdetail">
			<span>${startTime}</span>
			<span>${className}</span>
			<span>${subjectName}</span>
			<span>${teacherName}</span>
			<input type="hidden" id="csAttenId" name="csAttenId" value="${csAttenId}"/>
			<input type="hidden" id="timeStr" name="timeStr" value="${startTime}"/>
			<input type="hidden" id="className" name="className" value="${className}"/>
			<input type="hidden" id="subjectName" name="subjectName" value="${subjectName}"/>
			<input type="hidden" id="teacherName" name="teacherName" value="${teacherName}"/>
			<input type="hidden" id="queryType" name="queryType" value="lesson_dtl"/>
			<input type="hidden" id="orderAttr" name="orderAttr" value="duration"/>
		    <input type="hidden" id="orderType" name="orderType" value="asc"/>
		    <input id="export" type="submit" value="导出" class="u-btn u-btn-nm u-btn-bg-orange f-fr">
		</div>
		<div class="m-table m-table-center m-table-fixed c-analyse15__homesubdetail-table">
			<table>
				<thead>
					<tr>
						<th width="14%">序号</th>
						<th width="14%">姓名</th>
						<th width="14%">班级</th>
						<th width="16%">首次进入—最后退出时间</th>
						<th width="14%" class="m-sorting c-analyse15__sorting c-analyse15__sorting-up" id="duration">在线时长（分钟）<i></i></th>
						<th width="14%" class="m-sorting c-analyse15__sorting" id="attendState">考勤状态<i></i></th>
						<th width="14%">实点/应点到次数</th>
					</tr>
				</thead>
				<tbody id="jtbodyData"></tbody>
			</table>
		</div>
		<div id="jTablePage"/>
	</div>
	</form>
</body>

<script id="attendTpl" type="x-handlebars">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td>{{studentName}}</td>
    	<td>{{className}}</td>
    	<td>{{startTime}}</td>
    	<td>{{duration}}</td>
    	<td>{{attendState}}</td>
    	<td>{{calledNum}}/{{totalCalled}}</td>
    </tr>
{{/dataList}}
</script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/studentMonitor/studentAttendDataDtl');
</script>
</html>