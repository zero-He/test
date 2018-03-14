<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>习题审核 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/examination.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/quedit.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
			<div class="examination" data-subject-id="${query.subjectId}">
				<div class="quesContent f-bfc">
					<div class="m-search-box">
						<div class="item">
							<label class="title">学段科目：</label>
							<select id="stageSubject" class="u-select u-select-nm"></select>
							<label class="u-title f-w60">教材章节：</label>
							<input type="text" name="section" class="u-ipt u-ipt-lg" 
								style="width:350px;" readonly="readonly"/>
							<a class="f-csp f-fwb s-c-r j-btn-del-section">×</a>
						</div>
						<div class="item">
							<label class="title">录入日期：</label>
							<input name="minInputDate" type="text" class="Wdate u-ipt u-ipt-nm"/>
							-
							<input name="maxInputDate" type="text" class="Wdate u-ipt u-ipt-nm"/>
							<label class="title">录入人：</label>
							<input name="creatorName" type="text" class="u-ipt u-ipt-sm"/>
						</div>
						<div class="item">
							<label class="title">题型：</label>
							<select name="questionTypeId" class="u-select u-select-nm"></select>
							<label class="title">题号：</label>
							<input name="questionId" type="text" class="u-ipt u-ipt-nm f-w100"/>
							<input name="content" type="text" class="u-ipt u-ipt-nm" style="width: 192px;" placeholder="输入文本..."/>
							<button class="u-btn u-btn-nm u-btn-bg-turquoise j-btn-search">查询</button>
						</div>
					</div>
					<div class="quesStyleCut">
						<div class="quesTotal">
							<ul>
								<li class="quesTab" data-status="96"><a>待审核</a></li>
								<li class="quesTab" data-status="97"><a>已审核</a></li>
							</ul>
						</div>
						<div class="quesCutContent">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<leke:pref />
<script id="tagSelect_tpl" type="x-mustache">
<div class="tag-select-dialog">
{{#tags}}
	<label>
		<input name="tag" type="checkbox" value="{{officialTagId}}"/>
		{{officialTagName}}
	</label>
{{/tags}}
</div>
</script>

<script id="tagItem_tpl" type="x-mustache">
<div class="q-propterty-t">
{{#tags}}
<span class="q-propterty-t-item">
	<em>{{officialTagName}}</em>
	<span tid="{{quesOfficialTagId}}" title="删除" class="q-propterty-t-delete"> x </span>
</span>
{{/tags}}
</div>
</script>

<script id="knowledgeItem_tpl" type="x-mustache">
<div class="q-propterty-k">
{{#knowledges}}
<span class="q-propterty-k-item">
	<em>{{path}}</em>
	<span kid="{{quesKnowledgeId}}" title="删除" class="q-propterty-k-delete"> x </span>
</span>
{{/knowledges}}
</div>
</script>

<script id="loading_tpl" type="x-mustache">
<div style="margin-top: 30px;">
	<div style="margin: 0 auto;" class="loading"></div>
	<div style="margin: 0 auto; text-align: center;">加载中，请稍候...</div>
</div>
</script>

<script id="reject_tpl" type="x-mustache">
<form>
<ul>
	<li class="f-bfc">
		<label for="" class="f-fl f-w60 f-tar">退回原因：</label>
		<textarea rows="3" cols="20" name="rejectReason" style="width:300px;height:60px;"></textarea>
	</li>
</ul>
</form>
</script>

<leke:pref/>
<script>
document.domain = '${initParam.mainDomain}';
seajs.use('question/view/question/researcher/check/index');
</script>
</body>
</html>