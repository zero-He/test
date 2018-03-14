<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>大纲添加 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>
</head>
<body>	
	<div class="m-form">
		<form id="form1">
			<ul>
				<li class="form-item">
					<label class="title"><span class="require">*</span>大纲名称</label>
					<div class="con">
						<input id="outlineName" name="outlineName" placeholder="请输入大纲简称,如“七年级上”" class="u-ipt  u-ipt-lg" />
						<div class="checkinfo "></div>
						<%-- <input type="hidden" name="shareSchool" value="${outline.shareSchool}"/>
						<input type="hidden" name="sharePlatform" value="${outline.sharePlatform}"/>
						<input type="hidden" name="status" value="${outline.status}"/> --%>
					</div>
				</li>
				<li class="form-item">
					<label class="title">
						<span class="require">*</span><span data-bind="i18n: 'que.question.searchItem.grade'">年级</span>
					</label>
					<div class="con">
					<select name="gradeId" id="gradeId" class="u-select u-select-sm"
						data-bind="options: grades,
						optionsText: 'gradeName',
						optionsValue: 'gradeId',
						optionsCaption: '==请选择==',
						value: model.gradeId,
						event:{change: clean}"></select>
						<input type="hidden" id="gradeName" name="gradeName" />
					<div class="checkinfo "></div></div>
				</li>
				<li class="form-item">
					<label class="title">
						<span class="require">*</span><span data-bind="i18n: 'que.question.searchItem.subject'">科目</span>
					</label>
					<div class="con"><select name="subjectId" id="subjectId" class="u-select u-select-sm"
						data-bind="options: subjects,
				   		optionsText: 'subjectName',
				   		optionsValue: 'subjectId',
				   		optionsCaption: '==请选择==',
				   		value: model.subjectId,
				   		event:{change: clean}">
				   		</select>
				   		<input type="hidden" id="subjectName" name="subjectName" />
					<div class="checkinfo "></div></div>
				</li>
				<li class="form-item">
					<label class="title" data-bind="i18n: 'que.question.searchItem.press'">出版社</label>
					<div class="con"><select name="pressId"  id="pressId" class="u-select u-select-sm"
						data-bind="options: presses,
				   		optionsText: 'pressName',
				  		optionsValue: 'pressId',
				   		optionsCaption: '==请选择==',
				   		value: model.pressId,
				   		valueAllowUnset: true,
				   		event:{change: clean}"></select>
				   		<input type="hidden" id="pressName" name="pressName" />
					<div class="checkinfo "></div></div>
				</li>
				<li class="form-item">
					<label class="title">指定教材</label>
					<div class="con">
						<input type="button" id="addMaterial" class="u-btn u-btn-nm u-btn-bg-orange" value="教材" /><span id="materialHtml"></span>
						<div>
							<p>
								<label ><input type="checkbox" id="isCopyMaterial" name="isCopyMaterial" />是否使用教材章节</label>
								<i class="iconfont icon" title="勾选后，大纲使用指定教材的章节">&#xf0142;</i>
							</p>
                        </div>
						<input type="hidden" id="materialId" name="materialId" class="u-ipt  u-ipt-lg" />
						<input type="hidden" id="materialName" name="materialName" />
					</div>
				</li>
			</ul>
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
	seajs.use('question/syllabus/outline/outlineAdd.js');
</script>
</body>
</html>