<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>作业提交详情</title>
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
					${studentName}&nbsp;${startDate}——${endDate}&nbsp;作业提交详情
				</div>
				<div class="m-table m-table-center m-table-fixed">
					<table>
						<thead>
						<tr>
							<th width="6%">序号</th>
							<th width="15%">作业名称</th>
							<th width="8%">学科<i></i></th>
							<th width="8%">老师<i></i></th>
							<th width="8%">类型<i></i></th>
							<th width="13%" id="closeTime" class="m-sorting m-sorting-asc">截止时间<i></i></th>
							<th width="13%" id="submitTime" class="m-sorting">上交时间<i></i></th>
							<th width="10%" id="submitStatus" class="m-sorting">提交状态<i></i></th>
							<th width="10%" id="correctStatus" class="m-sorting">批改状态<i></i></th>
							<th width="9%" id="score" class="m-sorting">得分率<i></i></th>
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
    	<td class="operation">
           <%--<a href="${initParam.homeworkServerName}/auth/teacher/homework/homeworkDetail.htm?homeworkId={{homeworkId}}" target="_blank">{{homeworkName}}</a>--%>
           {{homeworkName}}
        </td>
    	<td>{{subjectName}}</td>
    	<td>{{teacherName}}</td>
    	<td>
    	    {{#cif 'this.homeworkType == 3'}}
				课时作业
    	    {{else cif 'this.homeworkType == 4'}}
				在线考试试卷
    	    {{else cif 'this.homeworkType == 5'}}
				点播作业
    	    {{else cif 'this.homeworkType == 6'}}
    	        推送作业
    	    {{else cif 'this.homeworkType == 6'}}
    	        寒暑假作业
    	    {{else}}
    	        其他
    	    {{/cif}}
        </td>
    	<td>
    	    {{closeTimeStr}}
    	</td>
    	<td>
    	    {{submitTimeStr}}
    	</td>
    	<td>
    	    {{#cif 'this.submitStatus == 0'}}
				未提交
			{{else cif 'this.submitStatus == 1'}}
				按时提交
			{{else cif 'this.submitStatus == 2'}}
				延后补交
			{{/cif}}
    	</td>
    	<td>
    	    {{#cif 'this.correctTime == null && this.score == null'}}
				未批改
    	    {{/cif}}
    	    {{#cif 'this.submitStatus != 0 && this.correctTime == null && this.score >= 0'}}
				部分批改
    	    {{/cif}}
    	    {{#cif 'this.correctTime != null'}}
				已批改
    	    {{/cif}}
    	</td>
    	<td>
    	    {{#cif 'this.score != null'}}
    	        {{score}}%
    	    {{else}}
    	        --
    	    {{/cif}}
    	</td>
    </tr>
{{/dataList}}
</script>

<script type="text/javascript">
	seajs.use('diag/studentMonitor/stuHomeworkDetailPage');
</script>
</html>