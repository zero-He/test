<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<div class="m-tab">
		<ul>
			<li><a href="/auth/teacher/homework/commentText.htm?text=${text1}">文字</a></li>
			<li><a href="/auth/teacher/homework/commentAudio.htm?text=${text1}">语音</a></li>
			<li class="active"><a>微课</a></li>
		</ul>
	</div>
	<div>
		<form id="jMicrocourseForm" class="m-search-box">
			<label class="title">范围：</label>
			<select id="jShareScope" name="shareScope" class="u-select u-select-nm">
				<option value="1">我的微课</option>
				<option value="4">我的收藏</option>
			</select>
			<label class="title">微课名称：</label>
			<input type="text" class="u-ipt u-ipt-nm" name="microcourseName">
			<a id="jMicrocourseSearch" class="u-btn u-btn-nm u-btn-bg-turquoise">查询</a>
			<div class="operation">
				<a class="u-btn u-btn-nm u-btn-bg-orange" href="${initParam.beikeServerName}/auth/common/microcourse/record.htm"
					target="_blank">录制微课</a>
			</div>
		</form>
		<div class="m-table">
			<table>
				<thead>
					<tr>
						<th width="8%">选择框</th>
						<th width="10%">学段学科</th>
						<th width="30%">微课名称</th>
						<th width="10%">微课时长</th>
						<th width="10%">录制人</th>
						<th width="12%">录制时间</th>
						<th width="10%">引用次数</th>
						<th width="10%">操作区</th>
					</tr>
				</thead>
				<tbody id="jMicrocourseBody"></tbody>
			</table>
			<div class="page" id="pagination"></div>
		</div>
		<div class="f-tac">
			<input type="button" value="提交" id="fBtnSave" class="u-btn u-btn-nm u-btn-bg-turquoise">
			<input type="button" value="取消" id="fBtnCancel" class="u-btn u-btn-nm u-btn-bg-gray">
		</div>
	</div>

</body>
<script id="jMicrocourseListItemTpl" type="text/handlebars">
{{#dataList}}
<tr>
	<td><input type="checkbox" class="j-microcourseId" value="${initParam.beikeServerName}/auth/common/microcourse/preview.htm?microcourseId={{microcourseId}}" data-name="{{microcourseName}}" /></td>
	<td>{{schoolStageName}}{{subjectName}}</td>
	<td>{{microcourseName}}</td>
	<td>{{timeStr}}</td>
	<td>{{createdName}}</td>
	<td>{{fmt createdOn 'yyyy-MM-dd'}}</td>
	<td>{{usedCount}}</td>
	<td class="operation">
		<a href="${initParam.beikeServerName}/auth/common/microcourse/preview.htm?microcourseId={{microcourseId}}" target="_blank">预览</a>
	</td>
</tr>
{{/dataList}}
</script>

<script type="text/javascript">
	seajs.use('homework/work/comment/commentMicro');
</script>
</html>
