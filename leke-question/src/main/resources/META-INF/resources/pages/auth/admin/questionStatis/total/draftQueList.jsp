<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>未审核习题统计 - 乐课网</title>
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
			<div class="examination" 
				data-school-stage-id="${query.schoolStageId}"
				data-subject-id="${query.subjectId}">
				<div class="m-search-box">
					<div class="item">
						<label class="title">出版社：</label>
	   					<select name="pressId" class="u-select u-select-nm"></select>
	   					<label class="title">学段：</label>
	   					<select name="schoolStageId" class="u-select u-select-nm"></select>
						<label class="title">年级：</label>
						<select name="gradeId" class="u-select u-select-nm"></select>
						<label class="title">科目：</label>
						<select name="subjectId" class="u-select u-select-nm"></select>
						<label class="title">教材：</label>
						<select name="materialId" class="u-select u-select-nm"></select>
					</div>
					<div class="item">
						<label class="title">录入日期：</label>
	   					<input name="minInputDate" type="text" class="Wdate u-ipt u-ipt-sm">
	   					<span style="width:30px;">-</span>
	   					<input name="maxInputDate" type="text" class="Wdate u-ipt u-ipt-sm">
	   					<label class="title">录入人：</label>
	   					<input name="inputerName" type="text" class="u-ipt u-ipt-nm" style="width:80px;">
	   					<label class="title">题型：</label>
	   					<select name="questionTypeId" class="u-select u-select-nm"></select>
	   					<!-- 
	   					<span>题号：</span>
	   					<input name="questionId" type="text" class="ipt_text" style="width:80px;">
	   					 -->
						<input name="content" type="text" class="u-ipt u-ipt-nm" placeholder="输入文本..."/>
	   					<button class="u-btn u-btn-nm u-btn-bg-turquoise btnSearch">查询</button>
					</div>
				</div>
				<div>
					<div class="quesTreeleft j-book">
						<ul id="sectionTree" class="ztree">
						</ul>
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
</div>

<script id="loading_tpl" type="x-mustache">
<div style="margin-top: 30px;">
	<div style="margin: 0 auto;" class="loading"></div>
	<div style="margin: 0 auto; text-align: center;">加载中，请稍候...</div>
</div>
</script>

<script>
seajs.use('question/view/question/admin/draftQueList');
</script>
</body>
</html>