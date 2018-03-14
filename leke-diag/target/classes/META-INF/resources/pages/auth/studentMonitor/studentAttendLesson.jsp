<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>学生考勤-课堂</title>
	<%@ include file="/pages/common/meta.jsp"%>
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">
</head>
<body>
	<form id="form" action="/auth/provost/studentMonitor/studentAttend/exportStudentAttendData.htm">
	<%@ include file="/pages/header/header.jsp"%>
	<div class="m-bg"></div>
	<div class="c-analyse15">
		<%@ include file="./studentAttendComm.jsp"%>
		<input type="hidden" id="queryType" name="queryType" value="lesson"/>
		<input type="hidden" id="orderAttr" name="orderAttr" value="attendCountRate"/>
		<input type="hidden" id="orderType" name="orderType" value="asc"/>
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
							<th style="white-space: nowrap">序号</th>
							<th style="white-space: nowrap">上课时间</th>
							<th style="white-space: nowrap">课堂时长</th>
							<th style="white-space: nowrap">班级</th>
							<th style="white-space: nowrap">学科</th>
							<th style="white-space: nowrap">上课老师</th>
							<th class="m-sorting c-analyse15__sorting c-analyse15__sorting-up" id="attendCountRate">到课率（实到/应到人次）<i></i></th>
							<th style="white-space: nowrap" class="m-sorting c-analyse15__sorting" id="normalCount">全勤<i></i></th>
							<th style="white-space: nowrap" class="m-sorting c-analyse15__sorting" id="lateCount">迟到<i></i></th>
							<th style="white-space: nowrap" class="m-sorting c-analyse15__sorting" id="earlyCount">早退<i></i></th>
							<th style="white-space: nowrap" class="m-sorting c-analyse15__sorting" id="lateAndEarlyCount">迟到且早退<i></i></th>
							<th style="white-space: nowrap" class="m-sorting c-analyse15__sorting" id="absentCount">缺勤<i></i></th>
							<th style="white-space: nowrap">操作</th>
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
		{{#if startTime}}
    	  <td>{{index}}</td>
    	  <td>{{startTime}}</td>
    	  <td>{{duration}}</td>
    	  <td>{{className}}</td>
    	  <td>{{subjectName}}</td>
    	  <td>{{teacherName}}</td>
		{{else}}
    	  <td>合计</td>
    	  <td>--</td>
    	  <td>--</td>
    	  <td>--</td>
    	  <td>--</td>
    	  <td>--</td>
		{{/if}}
    	<td>{{attendCountRate}}%/（{{realCount}}/{{totalCount}}）</td>
    	<td>{{normalCount}}</td>
    	<td>{{lateCount}}</td>
    	<td>{{earlyCount}}</td>
    	<td>{{lateAndEarlyCount}}</td>
    	<td>{{absentCount}}</td>
		{{#if startTime}}
    	  <td style="white-space: nowrap" class="operation"><a class="lessonDetail" data-csAttenId="{{csAttenId}}" data-startDate="{{startTime}}" data-className="{{className}}" data-subjectName="{{subjectName}}" data-teacherName="{{teacherName}}">详情</a></td>
		{{else}}
          <td>--</td>
        {{/if}}
    </tr>
{{/dataList}}
</script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/studentMonitor/studentAttendData');
</script>
</html>