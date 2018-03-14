<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>校对习题 - 乐课网</title>
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
			<div class="examination" data-subject-id="${query.subjectId}"
				data-status-type="${query.statusType}">
				<div class="quesTreeleft j-book">
					<ul id="sectionTree" class="ztree">
					</ul>
				</div>
				<div class="quesContent f-bfc">
					<div class="m-search-box">
						<div class="item">
							<label class="title">出版社：</label>
							<select name="pressId" class="u-select u-select-sm"></select>
							<label class="title">年级科目：</label>
							<select name="gradeSubjectId" class="u-select u-select-sm"></select>
							<label class="title">教材：</label>
							<select name="materialId" class="u-select u-select-sm"></select>
						</div>
						<div class="item">
							<label class="title">审核日期：</label>
							<input name="minCheckDate" type="text" class="Wdate u-ipt u-ipt-sm"/>
							<label class="title">-</label>
							<input name="maxCheckDate" type="text" class="Wdate u-ipt u-ipt-sm"/>
						</div>
						<div class="item">
							<label class="title">题型：</label>
							<select name="questionTypeId" class="u-select u-select-sm"></select>
							<label class="title">题号：</label>
							<input name="questionId" type="text" class="u-ipt u-ipt-nm f-w100"/>
							<input name="content" type="text" class="u-ipt u-ipt-nm" style="width: 192px;" placeholder="输入文本..."/>
							<button class="u-btn u-btn-nm u-btn-bg-turquoise j-btn-search">查询</button>
						</div>
					</div>
					<div class="quesStyleCut">
						<div class="quesTotal">
							<ul>
								<li class="quesTab" data-status="4"><a>未校对</a></li>
								<li class="quesTab" data-status="7"><a>已校对</a></li>
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

<script id="loading_tpl" type="x-mustache">
<div style="margin-top: 30px;">
	<div style="margin: 0 auto;" class="loading"></div>
	<div style="margin: 0 auto; text-align: center;">加载中，请稍候...</div>
</div>
</script>

<leke:pref/>
<script>
seajs.use('question/view/question/researcher/verify/index');
</script>
</body>
</html>