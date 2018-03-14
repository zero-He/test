<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>习题列表 - 乐课网</title>
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
			<div class="examination" data-status-type="${query.statusType}">
				<div class="m-search-box">
					<div class="item">
						<label class="title">学段科目：</label>
						<select name="stageSubject" class="u-select u-select-sm"></select>
						<label class="title">教材章节：</label>
						<input type="text" name="section" class="u-ipt u-ipt-lg" 
							style="width:350px;" readonly="readonly"/>
						<a class="f-csp f-fwb s-c-r j-btn-del-section">×</a>
					</div>
					<div class="item">
						<label class="title">录入日期：</label>
						<input name="inputDate" type="text" class="Wdate u-ipt u-ipt-sm"/>
						<label class="title">题型：</label>
						<select name="questionTypeId" class="u-select u-select-sm"></select>
						<label class="title">题号：</label>
						<input name="questionId" type="text" class="u-ipt u-ipt-nm f-w100"/>
						<input name="content" type="text" class="u-ipt u-ipt-nm" style="width: 192px;" placeholder="输入文本..."/>
						<button class="u-btn u-btn-nm u-btn-bg-turquoise btnSearch">查询</button>
					</div>
				</div>
					<div class="m-tab">
						<ul>
							<li class="j-que-tab" data-status="1"><a>未审核</a></li>
							<li class="j-que-tab" data-status="2"><a>被退回</a></li>
						</ul>
					</div>
					<div class="quesCutContent">
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
<form style="margin-top:20px; text-align:left;">
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

<leke:pref />
<script>
seajs.use('question/view/question/inputer/questionList');
</script>
</body>
</html>