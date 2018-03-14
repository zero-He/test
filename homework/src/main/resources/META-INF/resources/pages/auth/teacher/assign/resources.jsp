<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<div>
		<form action="#" id="fSearch" class="m-search-box">
			<div class="item">
				<label class="title">资源库：</label>
				<select id="shareScope" name="shareScope" class="u-select u-select-nm">
					<option value="1">个人库</option>
					<option value="4">收藏库</option>
				</select>
				<input type="hidden" name="resType" value="${resType}">
				<input id="iSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
			</div>
		</form>
		<div class="m-table">
			<table>
				<thead>
					<tr>
						<th width="8%"></th>
						<th>名称</th>
					</tr>
				</thead>
				<tbody id="body"></tbody>
			</table>
		</div>
		<div class="f-tac">
			<input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise" id="jConfirm" value="确定">
			<input type="button" class="u-btn u-btn-nm u-btn-bg-gray" id="jCancel" value="取消">
		</div>
	</div>

	<textarea id="papersPage_tpl" style="display: none;">
		{{#.}}
		<tr>
			<td><input type="checkbox" name="checkName" data-id="{{resId}}" data-type="{{resType}}" data-name="{{resName}}"></td>
			<td>{{resName}}</td>
		</tr>
		{{/.}}
	</textarea>

	<leke:pref />
	<script type="text/javascript">
		seajs.use('homework/assign/resources');
	</script>
</body>
</html>
