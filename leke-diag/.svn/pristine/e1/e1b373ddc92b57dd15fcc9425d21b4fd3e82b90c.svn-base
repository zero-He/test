<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>课堂行为列表</title>
<%@ include file="/pages/common/mobile-meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/mobile/analyse.css">
</head>
<body>
	<section class="c-top-title">
        <span><fmt:formatDate value="${lesson.startTime}" pattern="yyyy-MM-dd HH:mm" /> - <fmt:formatDate value="${lesson.endTime}" pattern="HH:mm" /></span>
		<span>${lesson.subjectName}</span>
		<span>${lesson.courseSingleName}</span>
    </section>
    <c:forEach var="behavior" items="${behaviors}" varStatus="state">
    	<div class="c-table c-table-fixed">
			<table>
				<caption>${state.index + 1}、${behavior.studentName}</caption>
				<tbody>
					<tr>
						<th style="width: 16%;">考勤</th>
						<th style="width: 16%;">点到</th>
						<th style="width: 17%;">举手</th>
						<th style="width: 17%;">被授权</th>
						<th style="width: 17%;">快速问答</th>
						<th style="width: 17%;">分组讨论</th>
					</tr>
					<tr>
						<td style="width: 16%;">
							<c:if test="${behavior.attend == 1}">全勤</c:if>
							<c:if test="${behavior.attend == 0}"><span class="s-c-orange">非全勤</span></c:if>
							<c:if test="${behavior.attend == 2}"><span class="s-c-r">缺勤</span></c:if>
						</td>
						<td style="width: 16%;">${behavior.called}</td>
						<td style="width: 17%;">${behavior.raised}</td>
						<td style="width: 17%;">${behavior.authed}</td>
						<td style="width: 17%;">${behavior.quickNum}</td>
						<td style="width: 17%;">${behavior.discuNum}</td>
					</tr>
				</tbody>
			</table>
			<table>
				<tbody>
					<tr>
						<th>随堂作业</th>
						<th>课堂笔记</th>
						<th>聊天条数</th>
						<th>献花次数</th>
						<th>课堂中评价</th>
						<th></th>
					</tr>
					<tr>
						<td>${behavior.examNum}</td>
						<td>${behavior.noteNum}</td>
						<td>${behavior.chatNum}</td>
						<td>${behavior.flower}</td>
						<td>${behavior.isEval ? '有' : '无'}</td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
    </c:forEach>
    
    
</body>
</html>