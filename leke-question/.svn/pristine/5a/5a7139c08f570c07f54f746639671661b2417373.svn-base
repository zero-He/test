<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学段学科出版社设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/pressList.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
			<div class="m-search-box">
                <label>学段：</label>
				<select name="schoolStageId" class="u-select u-select-sm"
					data-bind="options: schoolStages,
							   optionsText: 'schoolStageName',
							   optionsValue: 'schoolStageId',
							   optionsCaption: '==请选择==',
							   value: schoolStageId,
							   valueAllowUnset: true"></select>
				<label >学科：</label>
				<select name="subjectId" class="u-select u-select-sm"
					data-bind="options: subjects,
							   optionsText: 'subjectName',
							   optionsValue: 'subjectId',
							   optionsCaption: '==请选择==',
							   value: subjectId,
							   valueAllowUnset: true"></select>
		       <input type="submit" class="u-btn u-btn-nm u-btn-bg-turquoise" data-bind="click: doSearch" value="查询">
            </div>
			<div class="m-table">
				<table>
					<thead>
						<tr>
							<th class="publish-name-th" width="15%">学段学科</th>
							<th class="publish-name-th" width="75%">教材版本</th>
							<th width="10%">操作区</th>
						</tr>
					</thead>
					<tbody id="pressListContext">
						<!-- ko foreach: stages -->
						<!-- ko foreach: subjects -->
						<tr data-bind="attr:{'data-schoolStageId': $parent.schoolStageId,'data-subjectId': subjectId}">
							<td data-bind="text: $parent.schoolStageName + subjectName"></td>
							<td data-bind="foreach: presses">
								<span data-bind="text: pressName" class="f-dib f-mr20"></span>
							</td>
							<td><a data-bind="click: function(){ $root.openAddDialog($parent.schoolStageId,subjectId,presses);}">编辑</a></td>
						</tr>
						<!-- /ko -->
						<!-- /ko -->
					</tbody>
					
				</table>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	seajs.use('question/view/press/pressSet');
</script>
</body>