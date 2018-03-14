<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>大纲选择教材 - 乐课网</title>
    <%@ include file="/pages/common/meta.jsp" %>
</head>
<body>
<div class="m-search-box">
	<label class="title" data-bind="i18n: 'que.question.searchItem.schoolStage'" >学段：</label>
	<select name="schoolStageId" id="schoolStageId" disabled="disabled" class="u-select u-select-sm"
		data-bind="options: schoolStages,
		optionsText: 'schoolStageName',
		optionsValue: 'schoolStageId',
		optionsCaption: '==请选择==',
		value: model.schoolStageId,
		valueAllowUnset: true"></select>
   <input type="hidden" id="oldSchoolStageId" name="oldSchoolStageId" value="${material.schoolStageId}" />
   <label class="title" data-bind="i18n: 'que.question.searchItem.subject'">科目：</label>
   <select name="subjectId" id="subjectId" disabled="disabled" class="u-select u-select-sm"
   		data-bind="options: subjects,
   		optionsText: 'subjectName',
   		optionsValue: 'subjectId',
   		optionsCaption: '==请选择==',
   		value: model.subjectId,
   		valueAllowUnset: true"></select>
	<input type="hidden" id="oldSubjectId" name="oldSubjectId" value="${material.subjectId}" />
	<label class="title" data-bind="i18n: 'que.question.searchItem.press'">出版社：</label>
	<select name="pressId" id="pressId" class="u-select u-select-sm"
		data-bind="options: presses,
		optionsText: 'pressName',
		optionsValue: 'pressId',
		optionsCaption: '==请选择==',
		value: model.pressId,
		valueAllowUnset: true"></select>
		<input type="hidden" id="oldPressId" name="oldPressId" value="${material.pressId}" />
    <div class="operation"><input type="button" class="f-fr u-btn u-btn-nm u-btn-bg-orange" id="butSave" data-bind="click: doSave" value="添加"> </div>
</div>  
<div class="m-form">
	<div class="m-table m-table-center">
		<table >
			<thead>
				<tr><th width="20%"></th><th width="80%">教材名称</th></tr>
            </thead>
            <!-- ko if: materials().length == 0-->
            <tbody>
            	<tr><td colspan="2">暂无数据</td></tr>
            </tbody>
            <!-- /ko -->
            <tbody data-bind="foreach: materials">
                <tr>
                    <td><input type="radio"   name="rMaterial" data-bind="click: function(data){ return $parent.doMaterial(data);}" /></td>
                    <td data-bind="text: $data.materialName"></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div data-bind="component: {
    		name: 'leke-page',
    		params: { 
    		curPage: model.curPage,
    		totalSize: totalSize
    	}
	}"></div> 
</div>
<c:if test="${crossDomain}">
<script>document.domain = 'leke.cn';</script>
</c:if>
<script>
seajs.use('question/syllabus/outline/materialView.js');
</script>
</body>
</html>