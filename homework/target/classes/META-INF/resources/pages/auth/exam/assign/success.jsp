<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>发布考试 - <locale:message code="common.page.header.leke"/></title>
	<%@ include file="/pages/common/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=20171115">
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/homework/homework.css">
	<link rel="stylesheet" href="${assets}/styles/homework/assign-exam.css">
</head>
<body class="m-middlepage">
<%@ include file="/pages/header/header.jsp" %>
<div class="m-bg">
</div>
<div class="g-bd">
	<div class="g-mn">
		<div class="c-transfer-box">
			<h3 class="tr-tit">考试发布成功！</h3>
			<p class="tr-notes tr-notes-sm">
				学生没有平板，无法进行在线考试，试试下载线下答题卡，真实还原线下考试！
				<a class="quick-link" href="${assets}/pages/help/help.html?class=how-to-arrangement-work">点击了解更多</a>
			</p>
			<p class="tr-tips">
				<a href="${initParam.homeworkServerName}/auth/teacher/exam/assign/index.htm" class="item">继续发布</a><span class="item-line"></span>
				<c:if test="${not empty homeworkId }">
					<c:if test="${resType==3 }">
						<a href="${initParam.paperServerName}/auth/common/sheet/download/index.htm?paperId=${paperId}" class="item">预览线下答题卡</a><span class="item-line"></span>
						<a href="${initParam.paperServerName}/auth/common/paper/view.htm?paperId=${paperId}" class="item">查看试卷</a><span class="item-line"></span>
					</c:if>
					<a href="${initParam.homeworkServerName }/auth/teacher/exam/teaOnlineExamDetail.htm?homeworkId=${homeworkId}" class="item">查看学生</a><span class="item-line"></span>
				</c:if>
				<a href="${initParam.lessonServerName }/auth/teacher/home/teacher.htm" class="item">返回我的乐课</a>
			</p>
		</div>
	</div>
</div>
<div class="g-foot">
	<%@ include file="/pages/common/foot.jsp" %>
</div>
</body>
</html>