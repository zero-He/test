<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>退回习题 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

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
			<div class="examination" >
				
				<div class="quesContent f-bfc">
					<div class="quesStyleCut">
						<div class="quesTotal">
							
						</div>
						<div class="quesCutContent" id="quesCutContent">
						
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<leke:pref/>
<script>
seajs.use('question/view/question/teacher/userQuestionRejectionList');
</script>
</body>
</html>