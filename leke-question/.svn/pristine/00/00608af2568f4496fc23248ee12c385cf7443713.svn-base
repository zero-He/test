<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>电子教材  乐课网</title>
<meta name="viewport"   content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="${assets}/styles/mobile/global.css"></link>
<link rel="stylesheet" href="${assets}/styles/mobile/learningmaterialpad.css" type="text/css">
<script type="text/javascript" src="${assets}/scripts/common/mobile/common.js"></script>
</head>
<body>
<body>
	<nav class="elect-seclect-nav">
		<div class="elect-seclect-item selected-stg-sbj">
			<span class="con-stg-sbj">${material.schoolStageName}${material.subjectName}</span>
			<div class="elect-seclect-line"></div>
		</div>
		<div class="elect-seclect-item selected-press">
			<span class="con-press">${material.pressName}</span>
		</div>
	</nav>
	<div class="elect-seclect-section select-stg-sbj" style="display: none;">
		<ul id="stg_sbj">
		</ul>
	</div>
	<div class="elect-seclect-section select-press" style="display: none;">
		<ul id="press">
		<c:forEach  items="${currentPress}" var="press">
			<li class="elect-seclect-section-li" data-press-id="${press.pressId}" data-press-name="${press.pressName}">${press.pressName}</li>
		</c:forEach>
		</ul>
	</div>
	<article class="elect-seclect-article">
		<ul id="material">
		</ul>
		<input type="button" value="确定加入" class="add-btn j-save">
		<p class="prompt-p">
			只可绑定一个，无法解绑
		</p>
	</article>
<div class="elect-cover"></div>
</body>

<script id="j_stg_sbj" type="x-mustache">
{{#optgroups}}
	<li class="elect-seclect-section-li" 
		data-school-stage-id="{{schoolStageId}}" data-school-stage-name="{{schoolStageName}}"
		data-subject-id = "{{subjectId}}" data-subject-name="{{subjectName}}">
		{{schoolStageName}}{{subjectName}}
	</li>
{{/optgroups}}
</script>

<script id="j_press" type="x-mustache">
{{#presses}}
	<li class="elect-seclect-section-li" data-press-id="{{pressId}}" data-press-name="{{pressName}}">{{pressName}}</li>
{{/presses}}
</script>

<script id="j_material" type="x-mustache">
{{#materials}}
	<li class="elec-course-name" data-material-id="{{materialId}}" data-cur-page="{{curPage}}" data-material-name="{{materialName}}"
		data-material-file-id="{{materialFileId}}">
		<span class="tit">{{materialName}}</span>
		<span class="pages-now">{{curPage}}</span>/<span class="pages-total">{{pageCount}}</span>
	</li>
{{/materials}}
</script>
<script type="text/javascript">
	 window.materialCtx = {
			 material: ${materialJson},
			 schoolStages: ${schoolStages}
	 };
	window.Leke = {
			ctx: '${initParam.questionServerName}'
	}
</script>
<script type="text/javascript" src="/scripts/question/pages/teacher/material/callMaterial.js"></script>
</html>