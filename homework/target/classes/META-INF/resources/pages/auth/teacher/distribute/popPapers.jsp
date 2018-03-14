<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html class="f-h100pre">
<head>
<title><locale:message code="homework.homework.selectpapers" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/styles/homework/assign.css?t=20171115" type="text/css">
</head>
<body class="f-h100pre">
	<div class="f-h100pre f-pr">
		<form action="#" id="fSearch" class="m-search-box">
			<div class="item">
				<label class="title"><locale:message code="homework.homework.sharedscope" />：</label>
				<select id="shareScope" name="shareScope" class="u-select u-select-nm">
					<option value="1">我的试卷库</option>
					<option value="4">我的收藏库</option>
				</select>
				<label class="title">分组：</label>
				<select id="userResGroupId" name="userResGroupId" class="u-select u-select-nm">
				</select>
				<label class="title"><locale:message code="homework.homework.schoolstagesubject" />：</label>
				<select id="jStageSubject" class="u-select u-select-nm"></select>
				<input type="hidden" id="jSubjectId" name="subjectId">
				<input type="hidden" id="jSchoolStageId" name="schoolStageId">
			</div>
			<div class="item">
				<label class="title"><locale:message code="homework.homework.papertitle" />：</label>
				<input type="text" class="u-ipt u-ipt-nm" id="title" name="title">
				<input id="iSearchPaper" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="<locale:message code="homework.common.search" />">
				<div class="operation">
					<a class="c-assign-create-paper" href="${initParam.paperServerName}/auth/common/paper/add/index.htm" target="_black"><i></i><locale:message code="homework.homework.addpaper" /></a>
				</div>
			</div>
		</form>
		<div class="m-table" style="height:312px;overflow-y:scroll;overflow-x:hidden;">
			<table>
				<thead>
					<tr>
						<th width="4%"></th>
						<th width="9%"><locale:message code="homework.homework.subject" /></th>
						<th width="26%"><locale:message code="homework.homework.papertitle" /></th>
						<th width="8%"><locale:message code="homework.homework.grouppeople" /></th>
						<th width="14%"><locale:message code="homework.homework.paperstype" /></th>
						<th width="8%"><locale:message code="homework.homework.subjective" /></th>
						<th width="8%"><locale:message code="homework.homework.writetopic" /></th>
						<th width="10%"><locale:message code="homework.homework.quotenum" /></th>
						<th width="13%"><locale:message code="homework.homework.operatingarea" /></th>
					</tr>
				</thead>
				<tbody id="papersPage"></tbody>
			</table>
			<div class="dPageStyle">
				<div id="pageStyle"></div>
			</div>
		</div>
		<div class="f-tac">
			<input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise" id="jConfirm" value="<locale:message code="homework.common.confirm" />">
			<input type="button" class="u-btn u-btn-nm u-btn-bg-gray" id="jCancel" value="<locale:message code="homework.common.cancel" />">
		</div>
		<input type="hidden" id="currentpage" name="currentpage" value="${page.currentpage}" />
		<input type="hidden" id="totalpage" name="totalpage" value="${page.totalpage}" />
		<input type="hidden" id="jPaperIds" value="${paperIds }" />
	</div>

	<textarea id="papersPage_tpl" style="display: none;">
		{{#dataList}}
		<tr>
			<td><input type="checkbox" name="checkName" {{isChecked}} data-subjectid="{{subjectId}}" data-paperId="{{paperId}}" data-subjective="{{subjective}}" data-title="{{title}}"></td>
			<td>{{subjectName}}</td>
			<td>{{title}}</td>
			<td>{{creatorName}}</td>
			<td>{{{paperTypeString}}}</td>
			<td>{{{isSubjective}}}</td>
			<td>{{{isHandwrite}}}</td>
			<td>{{usedCount}}</td>
			<td class="operation"><a href="${initParam.paperServerName}/auth/common/paper/view.htm?paperId={{paperId}}" target="_blank"><locale:message code="homework.common.preview" /></a></td>
		</tr>
		{{/dataList}}
	</textarea>

	<leke:pref />
	<script type="text/javascript">
		seajs.use('homework/distribute/popPapers');
	</script>
</body>
</html>
