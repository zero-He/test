<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>纠错列表 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/examination.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/user-que.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
			<div class="examination" data-status-type="${query.statusType}"
				data-school-stage-id="${query.schoolStageId}"
				data-subject-id="${query.subjectId}"
				data-operate-date="<fmt:formatDate value='${query.operateDate}' pattern='yyyy-MM-dd'/>"
				data-operator-id="${query.operatorId}" data-operate-type="${query.operateType}" >
				
				<div class="quesStyleCut">
					<div class="quesTotal">
						<label class="title">学段科目：</label>
							<select id="stageSubject" class="u-select u-select-nm"></select>
					</div>
					<div class="quesCutContent" id="quesCutContent">
					
					
					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script id="edit_tpl" type="x-mustache">
<form style="margin-top:20px; text-align:left;">
	<div class="pf_item">
		<div class="label">评论：</div>
		<div class="con">
			<input type="hidden" id="questionId" name="questionId">
			<textarea rows="3" cols="20" id="comment" name="comment"></textarea>
		</div>
	</div>
</form>
</script>

<script id="feedback_tpl" type="x-mustache">
<form>
<ul>
	<li class="f-bfc">
		<label for="" class="f-fl f-w60 f-tar">备注：</label>
		<textarea rows="3" cols="20" name="feedbackContent" style="width:300px;height:60px;"></textarea>
	</li>
</ul>
</form>
</script>

<script>
seajs.use('question/questionFeedback/questionFeedbackList');

document.domain = '${initParam.mainDomain}';
</script>
</body>
</html>