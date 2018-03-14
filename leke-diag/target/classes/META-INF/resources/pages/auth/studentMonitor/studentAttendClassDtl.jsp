<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>班级考勤详情</title>
	<%@ include file="/pages/common/meta.jsp"%>
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">
</head>
<body>
	<form id="form" action="/auth/provost/studentMonitor/studentAttend/exportStudentAttendData.htm">
	<div class="m-bg"></div>
	<div class="c-analyse15 c-analyse15__homesubdetail">
		<div class="c-analyse15__homesubdetail-title">
			<h3 class="c-analyse15__homesubdetail-titleword">班级学生考勤详情</h3>
		</div>
		<div class="c-analyse15__attendance-classdetail">
			<span>${className}</span>
			<span>${teacherName}</span>
			<input type="hidden" id="classId" name="classId" value="${classId}"/>
			<input type="hidden" id="className" name="className" value="${className}"/>
			<input type="hidden" id="teacherName" name="teacherName" value="${teacherName}"/>
			<input type="hidden" id="queryType" name="queryType" value="class_dtl"/>
			<input type="hidden" id="startDate" name="startDate" value="${startDate}"/>
			<input type="hidden" id="endDate" name="endDate" value="${endDate}"/>
			<input type="hidden" id="orderAttr" name="orderAttr" value="attendCount"/>
		    <input type="hidden" id="orderType" name="orderType" value="asc"/>
            <input id="export" type="submit" value="导出" class="u-btn u-btn-nm u-btn-bg-orange f-fr">
		</div>
		<div class="m-table m-table-center m-table-fixed c-analyse15__homesubdetail-table">
			<table>
				<thead>
					<tr>
						<th width="10%">序号</th>
						<th width="10%">姓名</th>
						<th width="18%" class="m-sorting c-analyse15__sorting c-analyse15__sorting-up" id="attendCount">到课率（实上/应上课堂数））<i></i></th>
						<th width="10%" class="m-sorting c-analyse15__sorting" id="normalCount">全勤（比率）<i></i></th>
						<th width="10%" class="m-sorting c-analyse15__sorting" id="lateCount">迟到（比率）<i></i></th>
						<th width="16%" class="m-sorting c-analyse15__sorting" id="earlyCount">早退（比率）<i></i></th>
						<th width="16%" class="m-sorting c-analyse15__sorting" id="lateAndEarlyCount">迟到且早退（比率）<i></i></th>
						<th width="16%" class="m-sorting c-analyse15__sorting" id="absentCount">缺勤（比率）<i></i></th>
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
    	<td>{{attendCountRate}}%/（{{attendCount}}/{{lessonCount}}）</td>
    	<td>{{normalCount}}（{{normalCountRate}}%）</td>
    	<td>{{lateCount}}（{{lateCountRate}}%）</td>
    	<td>{{earlyCount}}（{{earlyCountRate}}%）</td>
    	<td>{{lateAndEarlyCount}}（{{lateAndEarlyCountRate}}%）</td>
    	<td>{{absentCount}}（{{absentCountRate}}%）</td>
    </tr>
{{/dataList}}
</script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/studentMonitor/studentAttendDataDtl');
</script>
</html>