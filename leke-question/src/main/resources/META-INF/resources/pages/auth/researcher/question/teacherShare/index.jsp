<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>审核学校习题 - 乐课网</title>
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
				<div class="quesContent f-bfc s-border-nobd">
					<div class="m-search-box">
						<div class="item">
							<label class="title">学段科目：</label>
							<select id="stageSubject" class="u-select u-select-nm"></select>
							<label class="title">教材章节：</label>
							<input type="text" name="section" class="u-ipt u-ipt-lg" 
								style="width:350px;" readonly="readonly"/>
							<a class="f-csp f-fwb s-c-r j-btn-del-section">×</a>
						</div>
						<div class="item">
							<label class="title">题型：</label>
							<select name="questionTypeId" class="u-select u-select-nm"></select>
							<label class="title">题号：</label>
							<input name="questionId" type="text" class="u-ipt u-ipt-nm f-w100"/>
							<label class="title">关键字：</label>
							<input name="content" type="text" class="u-ipt u-ipt-nm" style="width: 192px;" placeholder="输入文本..."/>
							<label class="title">学校：</label>
							<input name="schoolName" type="text" class="u-ipt u-ipt-nm"/>
						</div>
						<div class="item">
							<label class="title">录入日期：</label>
							<input name="minInputDate" type="text" class="Wdate u-ipt u-ipt-nm"/>-
							<input name="maxInputDate" type="text" class="Wdate u-ipt u-ipt-nm"/>
							<label class="title">录入人：</label>
							<input name="creatorName" type="text" class="u-ipt u-ipt-nm f-w80"/>
							<button class="u-btn u-btn-nm u-btn-bg-turquoise j-btn-search">查询</button>
						</div>
					</div>
					<div class="quesStyleCut">
						<div class="quesCutContent">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<leke:pref />
<script id="loading_tpl" type="x-mustache">
<div style="margin-top: 30px;">
	<div style="margin: 0 auto;" class="loading"></div>
	<div style="margin: 0 auto; text-align: center;">加载中，请稍候...</div>
</div>
</script>

<script id="rejection_tpl" type="x-mustache">
<form>
		<div class="f-tac">
		<textarea rows="3" cols="20" name="rejectionContent" style="width:250px;height:58px;"></textarea>
		</div>
</form>
</script>

<script>
seajs.use('question/view/question/researcher/teacherShare/index');
</script>
</body>
</html>