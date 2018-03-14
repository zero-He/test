<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>查看试卷 - 乐课网</title>

<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/question/question-paper.css?t=${_t}">
<link rel="stylesheet" href="${assets}/styles/question/question-sheet.css?t=${_t}">
<link rel="stylesheet" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" href="${assets}/styles/resource/resource.css?t=${_t}">

</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div data-bind="component: 'scope-title'"></div>
		<div class="g-mn p-exam">
			<div class="p-exam-head">
				<h1>${paperDto.title}</h1>
				<div class="p-props">
					<label>姓名：</label><span class="p-name">${stuentName }</span>
					<label>学科：</label><span>${paperDto.subjectName}</span>
					<label>题量：</label><span>${paperDto.detail.questionNum}题</span>
					<label>总分：</label><span>${totalScore}分</span>
				</div>
				<hr>
			</div>
			<div class="j-sheet p-sheet" data-paper-id="${paperDto.paperId}" data-paper-type="1" data-attachment="false" data-view-mode="0">
				<ul>
					<c:forEach items="${paperDto.detail.groups}" var="grp">
					<li>
						<div class="p-group-title" data-subjective="false">
							<i class="j-arrow p-arrow"></i><dfn>${grp.groupTitle}</dfn><em>（总分${grp.score}分）</em>
						</div>
						<div class="p-group-detail">
							<ul>
							<c:forEach items="${grp.questions}" var="que">
								<li class="p-group-body">
									<leke:question questionId="${que.questionId}" sequence="${que.ord}" showCheckbox="false" showSequence="true" 
											showExplain="true" showProperty="false" showPropertyEdit="false" showRightAnswer="${isShowAnswer }"/>
								</li>
							</c:forEach>
							</ul>
						</div>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
<script type="text/javascript">
seajs.use('homework/sheet.struct');
</script>
</body>
</html>