<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<title>学生成长曲线 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form id="searchForm" class="m-search-box">
			<ul id="jULid" class="tab">
				<li class="jtabItem cur" data-id="2"><a>我的课程</a></li>
				<li class="jtabItem" data-id="1"><a>任教班级</a></li>
			</ul>
			<label class="title"><span class="jClassLabel">课程</span>：</label>
			<select id="jClassId" name="classId" class="u-select u-select-lg">
				<c:forEach items="${classes2 }" var="clazz">
				<option value="${clazz.classId }">${clazz.className }</option>
				</c:forEach>
			</select>
			<div class="f-hide jClasses">
				<c:forEach items="${classes1 }" var="clazz">
				<option value="${clazz.classId }">${clazz.className }</option>
				</c:forEach>
			</div>
			<div class="f-hide jCourseSets">
				<c:forEach items="${classes2 }" var="clazz">
				<option value="${clazz.classId }">${clazz.className }</option>
				</c:forEach>
			</div>
			<label class="title">学生姓名：</label>
			<input id="jUserName" type="text" class="u-ipt u-inp-nm" name="userName" />
			<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
			<div class="operation">
				<input type="hidden" name="fileName" id="j_fileName" />
				<input class="u-btn u-btn-nm u-btn-bg-orange" type="button" id="jExportData" value="导出" title="导出全班成绩">
			</div>
			
		</form>
		<div class="m-table">
			<table >
				<thead>
					<tr>
						<td class="f-tac" width="10%">序号</td>
						<th width="60%">姓名 </th>
						<th width="30%">操作</th>
					</tr>
				</thead>
				<tbody id="jtbodyData">

				</tbody>
			</table>
			<div class="m-tips" style="display: none;" id="jEmptyData"><i></i><span>对不起，没有您要查询的数据</span></div>
		</div>
	</div>
</div>

<script id="jtemplateData" type="text/handlebars">
		{{#.}}
			<tr>
				<td class="f-tac">{{ordNum @index }} </td>
				<td>{{userName}}</td>
				<td class="operation">
					<a class="detailsStudent" target="_blank"  
						href="${parameterService.diagServerName}/auth/teacher/data/line/stuStatistical.htm?studentId={{userId}}">查看</a>
				</td>
			</tr>
		{{/.}}
</script>
<script type="text/javascript">
	seajs.use('diag/teacher/studentList');
</script>
</body>
</html>