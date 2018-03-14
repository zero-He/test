<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学生行为列表<c:if test="${device != 'hd'}"> - 乐课网</c:if></title>
<meta name="viewport" content="width=1280">
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/diag/analyse.css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div class="z-classreviewreport">
				<h3 class="tips">
					<i class="date"></i>
					<fmt:formatDate value="${lesson.startTime}" pattern="yyyy-MM-dd HH:mm" />
					-
					<fmt:formatDate value="${lesson.endTime}" pattern="HH:mm" />
					<span class="f-ml20">${lesson.subjectName}</span>
					<span class="f-ml20">${lesson.courseSingleName}</span>
				</h3>
				<h3 class="evaluationtitle">学生课堂行为列表</h3>
				<div class="m-table m-table-center">
					<table>
						<thead>
							<tr>
								<th>序号</th>
								<th>学生姓名</th>
								<th>考勤</th>
								<th>点到</th>
								<th>举手</th>
								<th>被授权</th>
								<th>快速问答</th>
								<th>分组讨论</th>
								<th>随堂作业</th>
								<th>课堂笔记</th>
								<th>聊天条数</th>
								<th>献花次数</th>
								<th>课堂中评价</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="behavior" items="${behaviors}" varStatus="state">
								<tr>
									<td>${state.index + 1}</td>
									<td>${behavior.studentName}</td>
									<td>
										<c:if test="${behavior.attend == 1}">全勤</c:if>
										<c:if test="${behavior.attend == 0}"><span class="s-c-orange">非全勤</span></c:if>
										<c:if test="${behavior.attend == 2}"><span class="s-c-r">缺勤</span></c:if>
									</td>
									<td>${behavior.called}</td>
									<td>${behavior.raised}</td>
									<td>${behavior.authed}</td>
									<td>${behavior.quickNum}</td>
									<td>${behavior.discuNum}</td>
									<td>${behavior.examNum}</td>
									<td>${behavior.noteNum}</td>
									<td>${behavior.chatNum}</td>
									<td>${behavior.flower}</td>
									<td>${behavior.isEval ? '有' : '无'}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:if test="${empty behaviors}">
						<div class="m-tips"><i></i><span>对不起，没有您要查询的数据。</span></div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>