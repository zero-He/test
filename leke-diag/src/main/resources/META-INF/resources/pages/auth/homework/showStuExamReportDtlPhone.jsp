<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="${assets}/styles/mobile/global.css" />
<link rel="stylesheet" href="${assets}/styles/mobile/homework/ts-phone.css">
<leke:context />
<title>成绩单</title>
</head>
<body class="ts-paper">
	<article class="ts-wrap">
		<div class="ts-name">
			<span id="studentName" class="green">${studentName}</span>&nbsp;同学：
		</div>
		<div id="greatings" class="ts-talk">${greatings}</div>
		<div class="ts-tab">
			<table>
				<thead>
					<tr>
						<th width="20%">学科</th>
						<th width="20%">成绩</th>
						<th width="20%">平均分</th>
						<th width="20%">最高分</th>
						<th width="20%">最低分</th>
					</tr>
				</thead>
				<tbody id="jtbodyData">
				<c:forEach var="dtl" items="${list}">
					<tr>
						<td>${dtl.subjectName}</td>
						<td><fmt:formatNumber value="${dtl.score}" pattern="0.#" /></td>
						<td><fmt:formatNumber value="${dtl.avgScore}" pattern="0.#" /></td>
						<td><fmt:formatNumber value="${dtl.maxScore}" pattern="0.#" /></td>
						<td><fmt:formatNumber value="${dtl.minScore}" pattern="0.#" /></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="ts-tip">
			<span class="tip-key">备注：</span> <span class="tip-val">平均分、最高分、最低分为班级维度统计。如有疑问请联系班主任。</span>
		</div>

		<div class="ts-bottom">
			<div class="ts-sch-tag">
				<div id="schoolName" class="shc-name">${schoolName}</div>
			</div>
			<div id="createdOn" class="ts-time">${createdOn}</div>
		</div>

		<div class="ts-tag"></div>
	</article>
</body>
</html>

