<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>学生考勤-班级</title>
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
		<input type="hidden" id="queryType" name="queryType" value="class"/>
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
							<th style="white-space: nowrap">班级</th>
							<th style="white-space: nowrap">班主任</th>
							<th style="white-space: nowrap">班级人数</th>
							<th style="white-space: nowrap" class="m-sorting c-analyse15__sorting" id="lessonCount">上课堂数</th>
							<th class="m-sorting c-analyse15__sorting c-analyse15__sorting-up" id="attendCountRate">到课率（实到/应到人次）<i></i></th>
							<th class="m-sorting c-analyse15__sorting" id="normalCount">全勤人次（比率）<i></i></th>
							<th class="m-sorting c-analyse15__sorting" id="lateCount">迟到人次（比率）<i></i></th>
							<th class="m-sorting c-analyse15__sorting" id="earlyCount">早退人次（比率）<i></i></th>
							<th class="m-sorting c-analyse15__sorting" id="lateAndEarlyCount">迟到且早退人次（比率）<i></i></th>
							<th class="m-sorting c-analyse15__sorting" id="absentCount">缺勤人次（比率）<i></i></th>
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
		{{#if className}}
    	  <td>{{index}}</td>
    	  <td>{{className}}</td>
    	  <td>{{teacherName}}</td>
		{{else}}
    	  <td>合计</td>
    	  <td>--</td>
    	  <td>--</td>
		{{/if}}
    	<td>{{studentCount}}</td>
    	<td>{{lessonCount}}</td>
    	<td>{{attendCountRate}}%/（{{realCount}}/{{totalCount}}）</td>
    	<td>{{normalCount}}（{{normalCountRate}}%）</td>
    	<td>{{lateCount}}（{{lateCountRate}}%）</td>
    	<td>{{earlyCount}}（{{earlyCountRate}}%）</td>
    	<td>{{lateAndEarlyCount}}（{{lateAndEarlyCountRate}}%）</td>
    	<td>{{absentCount}}（{{absentCountRate}}%）</td>
		{{#if className}}
    	  <td style="white-space: nowrap" class="operation"><a class="classDetail" data-classId="{{classId}}" data-className="{{className}}" data-teacherName="{{teacherName}}">详情</a></td>
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