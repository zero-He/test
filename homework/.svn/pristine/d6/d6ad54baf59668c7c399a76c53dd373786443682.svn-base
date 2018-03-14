<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>答题卡上传记录 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<form id="jPageForm" method="post" class="m-search-box">
				<label class="title">上传时间：</label>
				<input type="text" class="u-ipt u-ipt-nm Wdate" id="jStartDate" name="startDate" />
				-
				<input type="text" class="u-ipt u-ipt-nm Wdate" id="jEndDate" name="endDate" />
				<input class="u-btn u-btn-nm u-btn-bg-turquoise" id="jPageSearch" type="button" value="查询">
			</form>
			<div class="m-table">
				<table>
					<thead>
						<tr>
							<th>上传时间</th>
							<th>识别份数</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="jPageBody"></tbody>
				</table>
				<div id="jPageFoot" class="page"></div>
			</div>
		</div>
	</div>
	<leke:pref />
</body>
<script id="jPageTpl" type="text/handlebars">
{{#dataList}}
<tr>
	<td>{{fmt createdOn 'yyyy-MM-dd HH:mm:ss'}}</td>
	<td>{{validBookNum}}</td>
	<td class="operation">
		<a href="taskInfo.htm?taskId={{id}}<c:if test="${superuser}">&superuser=true</c:if>" target="_blank">查看</a>
	</td>
</tr>
{{/dataList}}
</script>
<script>
	seajs.use('homework/sheet/taskList');
</script>
</html>
