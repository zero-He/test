<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>类型修正 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/examination.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
			<div class="examination">
				<div class="m-search-box">
					<label class="title">学段科目：</label>
						<select name="stageSubject" class="u-select u-select-sm"></select>
					<label class="u-title f-w60">&nbsp;</label>
					<button class="u-btn u-btn-nm u-btn-bg-turquoise btnSearch">查询</button>
				</div>
				<div class="quesContent f-bfc">
					<div class="quesStyleCut">
						<div class="quesCutContent">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script id="loading_tpl" type="x-mustache">
<div style="margin-top: 30px;">
	<div style="margin: 0 auto;" class="loading"></div>
	<div style="margin: 0 auto; text-align: center;">加载中，请稍候...</div>
</div>
</script>

<script id="changeType_tpl" type="x-mustache">
<form>
	<div class="pf_item">
		<div class="label">题型：</div>
		<div class="con">
			<select name="questionTypeId">
				<option value="">=请选择=</option>
{{#types}}
				<option value="{{questionTypeId}}">{{questionTypeName}}</option>
{{/types}}
			</select>
		</div>
	</div>
</form>
</script>

<leke:pref/>
<script>
seajs.use('question/view/question/inputer/typeMismatch');
</script>
</body>
</html>