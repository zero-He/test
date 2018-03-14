<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>习题册添加 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${initParam.staticServerName}/styles/resource/resource.css?t=">
</head>
<body>	
	<div class="m-form">
		<form id="form1">
			<ul>
				<li class="form-item">
					<label class="title"><span class="require">*</span>习题册</label>
					<div class="con">
						<input id="workbookName" name="workbookName" class="u-ipt  u-ipt-lg" />
						<div class="checkinfo "></div>
						<input type="hidden" name="shareSchool" value="${workbook.shareSchool}"/>
						<input type="hidden" name="sharePlatform" value="${workbook.sharePlatform}"/>
						<input type="hidden" name="status" value="${workbook.status}"/>
					</div>
				</li>
				<li class="form-item">
					<label class="title">
						<span class="require">*</span><span data-bind="i18n: 'que.question.searchItem.schoolStage'">学段:</span>
					</label>
					<div class="con">
					<select name="schoolStageId" id="schoolStageId" class="u-select u-select-sm"
						data-bind="options: schoolStages,
						optionsText: 'schoolStageName',
						optionsValue: 'schoolStageId',
						optionsCaption: '==请选择==',
						value: model.schoolStageId"></select>
						<input type="hidden" id="schoolStageName" name="schoolStageName" />
					<div class="checkinfo "></div></div>
				</li>
				<li class="form-item">
					<label class="title">
						<span class="require">*</span><span data-bind="i18n: 'que.question.searchItem.subject'">科目:</span>
					</label>
					<div class="con"><select name="subjectId" id="subjectId" class="u-select u-select-sm"
						data-bind="options: subjects,
				   		optionsText: 'subjectName',
				   		optionsValue: 'subjectId',
				   		optionsCaption: '==请选择==',
				   		value: model.subjectId">
				   		</select>
				   		<input type="hidden" id="subjectName" name="subjectName" />
					<div class="checkinfo "></div></div>
				</li>
				<li class="form-item">
					<label class="title">
					<span class="require" data-bind="visible: Leke.user.currentRoleId == 402">*</span>
					教材版本:</label>
					<div class="con">
						<select name="pressId"  id="pressId" class="u-select u-select-sm"
							data-bind="options: presses,
					   		optionsText: 'pressName',
					  		optionsValue: 'pressId',
					   		optionsCaption: '==请选择==',
					   		value: model.pressId"></select>
				   		<input type="hidden" id="pressName" name="pressName" />
						<div class="checkinfo "></div>
					</div>
				</li>
				<li class="form-item">
					<label class="title">
						<span class="require" data-bind="visible: Leke.user.currentRoleId == 402">*</span>
						年级或课本:</label>
					<div class="con">
						<select name="materialId"  id="materialId" class="u-select u-select-sm"
							data-bind="options: materials,
					   		optionsText: 'materialName',
					  		optionsValue: 'materialId',
					   		optionsCaption: '==请选择==',
					   		value: model.materialId"></select>
					   		<input type="hidden" id="materialName" name="materialName" />
					   		<div class="checkinfo "></div>
						<div>
							<p>
								<label ><input type="checkbox" id="isCopyMaterial" name="isCopyMaterial" />是否使用教材章节</label>
								<i class="iconfont icon" title="勾选后，习题册使用指定教材的章节">&#xf0142;</i>
							</p>
                        </div>
					</div>
				</li>
			</ul>
			<input type="hidden" id="photoUrl" name="photoUrl"  data-bind="value: photoUrl"/>
			<div class="c-upload-cover">
				<img alt="" class="thumbnail" id="imgSurface" data-bind="attr: {src: photoPath}, click: workbookUpload" src="${initParam.staticServerName}/images/resource/suoluetu.png">
				<span class="guide">上传习题册封面</span>
			</div>
			<div class="submit">
				<input type="button" value="保存" id="saveBtn" class="u-btn u-btn-nm u-btn-bg-turquoise">
				<input type="button" value="取消" id="closeBtn"  class="u-btn u-btn-nm u-btn-bg-gray">
			</div>
		</form>
	</div>
<c:if test="${crossDomain}">
<script>document.domain = 'leke.cn';</script>
</c:if>
<script>
	window.crossDomain = '${crossDomain}';
	seajs.use('question/view/workbook/workbookAdd.js');
</script>
</body>
</html>