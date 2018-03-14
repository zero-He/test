<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>学生考勤-每日缺勤</title>
	<%@ include file="/pages/common/meta.jsp"%>
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">
	<link rel="stylesheet" href="${assets}/styles/home/personal-center.css">
</head>
<body>
	<form id="form" action="/auth/provost/studentMonitor/studentAttend/exportStudentAttendData.htm">
	<%@ include file="/pages/header/header.jsp"%>
	<div class="m-bg"></div>
	<div class="c-analyse15">
		<div class="m-search-box c-analyse15__homework-search">
			<select class="u-select u-select-nm c-analyse15__homework-select" id="gradeId" name="gradeId">
			</select>
			<select class="u-select u-select-nm c-analyse15__homework-select" id="classId" name="classId">
			</select>
			<span class="c-dateswitch__pre" id="less">&lt;</span>
			<input id="startDate" name="startDate" type="text" class="u-ipt u-ipt-mn Wdate" readonly="">
			<span class="c-dateswitch__next" style="color:#0ba29a" id="great">&gt;</span>
			<input id="query" type="button" value="查询" style="margin-left: 20px" class="u-btn u-btn-at u-btn-bg-turquoise">
			<input id="endDate" name="endDate" type="hidden" class="u-ipt u-ipt-mn Wdate" readonly="">
            <span id="icon" class="iconfont c-analyse15__attendance-help">&#xf00b8;</span>
		</div>
		<div class="c-analyse15__homework-list c-analyse15__walkerclearfix">
			<a class="c-analyse15__homework-item c-analyse15__homework-border lessonItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=lesson">课堂维度</a>
			<a class="c-analyse15__homework-item c-analyse15__homework-border studentItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=student">学生维度</a>
			<a class="c-analyse15__homework-item c-analyse15__homework-border teacherItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=teacher">老师维度</a>
			<a class="c-analyse15__homework-item c-analyse15__homework-border classItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=class">班级维度</a>
			<a class="c-analyse15__homework-item c-analyse15__homework-border subjectItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=subject">学科维度</a>
			<a class="c-analyse15__homework-item dayItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=day">每日缺勤</a>
		</div>
		<input type="hidden" id="queryType" name="queryType" value="day"/>
		<input type="hidden" id="orderAttr" name="orderAttr" value="absentCount"/>
		<input type="hidden" id="orderType" name="orderType" value="desc"/>
		<div class="c-analyse__con">
			<div class="title">
                <div class="c-analyse__operate">
                   	<input id="export" type="submit" value="导出" class="u-btn u-btn-nm u-btn-bg-orange">
                </div>
            </div>
			<div class="m-table m-table-center">
				<table>
					<thead>
						<tr>
							<th>序号</th>
							<th>班级</th>
							<th>姓名</th>
							<th class="m-sorting c-analyse15__sorting c-analyse15__sorting-down" id="absentCount">当日缺勤次数<i></i></th>
						</tr>
					</thead>
					<tbody id="jtbodyData"></tbody>
				</table>
			</div>
			<div id="jTablePage"/>
		</div>
	</div>
	</form>
</body>

<script id="gradeTpl" type="x-handlebars">
{{#gradeList}}
	<option value="{{gradeId}}">{{gradeName}}</option>
{{/gradeList}}
</script>

<script id="classTpl" type="x-handlebars">
{{#classList}}
	<option value="{{classId}}">{{className}}</option>
{{/classList}}
</script>

<script id="attendTpl" type="x-handlebars">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td>{{className}}</td>
    	<td>{{studentName}}</td>
    	<td>{{absentCount}}</td>
    </tr>
{{/dataList}}
</script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/studentMonitor/studentAttendData');
</script>
</html>