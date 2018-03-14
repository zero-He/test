<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>习题册添加 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
</head>
<body>	
	<div class="m-form">
		<input  type="hidden" id="oldSchoolStageId" name="oldSchoolStageId" value="${workbook.schoolStageId}"/>
		<input  type="hidden" id="oldSubjectId" name="oldSubjectId" value="${workbook.subjectId}"/>
		<input  type="hidden" id="oldPressId" name="oldPressId" value="${workbook.pressId}"/>
		<input  type="hidden" id="oldMaterialId" name="oldMaterialId" value="${workbook.materialId}"/>
		<form id="form1">
		<input type="hidden" id="workbookId" name="workbookId" value="${workbook.workbookId}"/>
		<input type="hidden" id="oldWorkbookName" name="oldWorkbookName" value="${workbook.workbookName}"/>
			<ul>
				<li class="form-item">
					<label class="title"><span class="require">*</span>习题册：</label>
					<div class="con"><input id="workbookName" name="workbookName" data-bind="value: model.workbookName"  class="u-ipt  u-ipt-lg" />
					<div class="checkinfo "></div>
					</div>
				</li>
				<li class="form-item">
					<label class="title"><span class="require">*</span><span data-bind="i18n: 'que.question.searchItem.schoolStage'">学段</span></label>
					<div class="con">
					<select name="schoolStageId" id="schoolStageId" class="u-select u-select-sm"  disabled="disabled"
						data-bind="options: schoolStages,
						optionsText: 'schoolStageName',
						optionsValue: 'schoolStageId',
						optionsCaption: '==请选择==',
						value: model.schoolStageId,
						valueAllowUnset: true"></select>
						<input type="hidden" id="schoolStageName" name="schoolStageName" value="${workbook.schoolStageName}" />
					<div class="checkinfo "></div></div>
				</li>
				<li class="form-item">
					<label class="title"><span class="require">*</span><span data-bind="i18n: 'que.question.searchItem.subject'">科目</span></label>
					<div class="con"><select name="subjectId" id="subjectId" class="u-select u-select-sm"  disabled="disabled"
						data-bind="options: subjects,
				   		optionsText: 'subjectName',
				   		optionsValue: 'subjectId',
				   		optionsCaption: '==请选择==',
				   		value: model.subjectId,
				   		valueAllowUnset: true">
				   		</select>
				   		<input type="hidden" id="subjectName" name="subjectName" value="${workbook.subjectName}"/>
					<div class="checkinfo "></div></div>
				</li>
				<li class="form-item">
					<label class="title"><span class="require" data-bind="visible: Leke.user.currentRoleId == 402">*</span>
					教材版本:</label>
					<div class="con"><select name="pressId" id="pressId" class="u-select u-select-sm" 
						data-bind="options: presses,
				   		optionsText: 'pressName',
				  		optionsValue: 'pressId',
				   		optionsCaption: '==请选择==',
				   		value: model.pressId"></select>
				   		<input type="hidden" id="pressName" name="pressName" value="${workbook.pressName}" />
					<div class="checkinfo "></div></div>
				</li>
				<li class="form-item">
					<label class="title"><span class="require" data-bind="visible: Leke.user.currentRoleId == 402">*</span>
					年级或课本:</label>
					<div class="con">
						<select name="materialId"  id="materialId" class="u-select u-select-sm" 
							data-bind="options: materials,
					   		optionsText: 'materialName',
					  		optionsValue: 'materialId',
					   		optionsCaption: '==请选择==',
					   		value: model.materialId"></select>
					   		<input type="hidden" id="materialName" name="materialName"  value="${workbook.materialName}"  />
					   		<div class="checkinfo "></div>
					</div>
				</li>
			</ul>
			<div class="submit">
				<input type="button" value="保存" id="saveBtn" class="u-btn u-btn-nm u-btn-bg-turquoise" data-bind="click: doSave">
				<input type="button" value="取消" id="closeBtn"  class="u-btn u-btn-nm u-btn-bg-gray" data-bind="click: doClose">
			</div>
		</form>
	</div>
<c:if test="${crossDomain}">
<script>document.domain = 'leke.cn';</script>
</c:if>
<script>
	seajs.use('question/view/workbook/workbookEdit.js');
</script>
</body>
</html>