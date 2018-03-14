<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>作业 - <locale:message code="common.page.header.leke" /></title>
	<%@ include file="/pages/common/meta.jsp" %>
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/homework/homework.css">
	<link rel="stylesheet" href="${assets}/styles/homework/assign.css">
</head>
<body class="m-middlepage">
	<%@ include file="/pages/header/header.jsp" %>
	<div class="g-bd">
		<div class="g-mn">
			<div class="c-transfer-box">
				<h3 class="tr-tit">作业提交成功！</h3>
				<p class="tr-notes">
					您是全班第<span class="cl-orange">${count}</span>位提交作业的学生，用时<span class="cl-orange">${homeworkDtl.usedTime }分钟</span>！</br>
					<c:if test="${homeworkDtl.correctTime != null}">
						作业得分：<span><fmt:formatNumber value="${homeworkDtl.score }" pattern="0.#" />分</span>&nbsp;&nbsp;&nbsp;
						<c:if test="${leke!= null && leke gt 0}">乐豆+${leke}</c:if>
						<%--<c:if test="${exp != null && exp gt 0}">，经验+${exp}</c:if>--%>
					</c:if>
				</p>

				<p class="tr-tips">
					<a class="item" href="${initParam.homeworkServerName }/auth/student/homework/viewWork.htm?homeworkDtlId=${homeworkDtl.homeworkDtlId}">查看作业</a><span class="item-line"></span>
					<c:if test="${isShow }">
						<a class="item" href="${initParam.homeworkServerName }/auth/student/exercise/homework/myHomework.htm?ord=2">我的作业</a><span class="item-line"></span>
					</c:if>
					<a class="item" href="${initParam.lessonServerName }/auth/student/home/student.htm">返回我的乐课</a>
				</p>
			</div>
		</div>
	</div>
	<div class="g-foot">
		<%@ include file="/pages/common/foot.jsp" %>
	</div>
</body>
</html>