<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学生考勤 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<form id="jPageForm" action="" method="post" class="m-search-box">
				<label class="title">课程名称：</label>
				<span>${teachAttend.courseName }</span>
				<label class="title">学科：</label>
				<span>${teachAttend.courseSetName }</span>
				<label class="title">计划上课时间：</label>
				<span>
					<fmt:formatDate value="${teachAttend.planStartTime}" pattern="yyyy-MM-dd HH:mm" />
					<fmt:formatDate value="${teachAttend.planEndTime}" pattern="HH:mm" />
				</span>
				<label class="title">考勤状态：</label>
				<select name="status" class="u-select u-select-nm">
					<option value="">请选择</option>
					<option value="0">非全勤</option>
					<option value="1">全勤</option>
					<option value="2">未上课</option>
				</select>
				<label class="title">学生姓名：</label>
				<input type="text" name="studentName" class="u-ipt u-ipt-nm">
				<input type="hidden" name="csAttendId" value="${teachAttend.csAttendId}" />
				<input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise" id="jPageSearch" value="查询">
			</form>
			<div class="m-table m-table-center">
				<table>
					<thead id="jPageHead">
						<tr>
							<th>学生乐号</th>
							<th>学生姓名</th>
							<th>进入时间</th>
							<th>退出时间</th>
							<th>进入次数</th>
							<th>单节时长</th>
							<th>在线时长</th>
							<th>考勤状态</th>
						</tr>
					</thead>
					<tbody id="jPageBody"></tbody>
				</table>
				<div id="jPageFoot" class="page"></div>
			</div>
		</div>
	</div>
</body>
<script id="jPageTpl" type="text/handlebars">
{{#dataList}}
<tr>
	<td>{{loginName}}</td>
	<td>{{nick}}</td>
	<td>{{fmt attendTime 'HH:mm'}}</td>
	<td>{{fmt exitTime 'HH:mm'}}</td>
	<td>{{count}}</td>
	<td>{{duration}}</td>
	<td>{{online}}</td>
	<td>{{map status '["非全勤", "全勤", "未上课"]'}}</td>
</tr>
{{/dataList}}
</script>
<leke:pref />
<script type="text/javascript">
	seajs.use('scs/attendance/student');
</script>
</html>