<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>课堂预习详情</title>
	<%@ include file="/pages/common/meta.jsp" %>
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">
</head>
<body>
<form id="form">
	<%@ include file="/pages/header/header.jsp" %>
	<div class="m-bg"></div>
	<div class="g-bd">
		<div class="g-mn">
			<div class="c-analyse__con">
				<div class="title">
					${studentName}&nbsp;${startDate}——${endDate}&nbsp;课堂预习详情
				</div>
				<div class="m-table m-table-center m-table-fixed">
					<table>
						<thead>
						<tr>
							<th width="8%">序号</th>
							<th width="22%">课堂名称</th>
							<th width="10%">学科<i></i></th>
							<th width="12%">老师<i></i></th>
							<th width="12%">预习状态<i></i></th>
							<th width="12%">课件<i></i></th>
							<th width="12%">微课<i></i></th>
							<th width="12%">作业<i></i></th>
						</tr>
						</thead>
						<tbody id="jtbodyData"></tbody>
					</table>
				</div>
				<div id="jTablePage"></div>
			</div>

		</div>
	</div>
	</div>
	<input type="hidden" id="startDate" name="startDate" value="${startDate}"/>
	<input type="hidden" id="endDate" name="endDate" value="${endDate}"/>
	<input type="hidden" id="studentId" name="studentId" value="${studentId}"/>
	<input type="hidden" id="studentName" name="studentName" value="${studentName}"/>
	<input type="hidden" id="classId" name="classId" value="${classId}"/>
	<input type="hidden" id="subjectId" name="subjectId" value="${subjectId}"/>
	<input type="hidden" id="orderAttr" name="orderAttr"/>
	<input type="hidden" id="orderType" name="orderType"/>
</form>
</body>

<script id="dtlTpl" type="x-handlebars">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td>{{singleName}}</td>
    	<td>{{subjectName}}</td>
    	<td>{{teacherName}}</td>
    	<td>
    	    {{#cif 'this.readyStatus == 1'}}
				全面预习
    	    {{else cif 'this.readyStatus == 2'}}
				部分预习
    	    {{else}}
    	        未预习
    	    {{/cif}}
        </td>
    	<td>
    	    {{course}}
    	</td>
    	<td>
    	    {{micro}}
    	</td>
    	<td>
    	    {{homework}}
    	</td>
    </tr>
{{/dataList}}
</script>

<script type="text/javascript">
	seajs.use('diag/studentMonitor/stuReadyDetailPage');
</script>
</html>