<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${assets }/styles/homework/homework.css">

<title>练习报告-<locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">

		<div class="c-selftrain">
			<div class="c-selftrain-report">
				<h1>
					【${exercise.subjectName}】 <span>${exerciseTitle }</span>
				</h1>
				<div class="suggestion">
					<div class="title">
						<span class="left"><i></i>学习建议</span>
					</div>
					<div class="remarks">
						<c:choose>
							<c:when test="${exercise.accuracy ==100 || exercise.accuracy >= 60 && (firstExercise || exercise.growth >= 0)}">
									<i class="tips tips-good"></i>
							</c:when>
							<c:when test="${exercise.accuracy < 60 && (firstExercise || exercise.growth < 0 )}">
									<i class="tips tips-sad"></i>
							</c:when>
							<c:otherwise>
									<i class="tips tips-cheerup"></i>
							</c:otherwise>
						</c:choose>
						<div class="description">
							<div class="head">
								<c:if test="${!firstExercise && exercise.growth < 0}">
								<div class="trend trend-down"></div>
								</c:if>
								<c:if test="${!firstExercise && exercise.growth > 0}">
								<div class="trend trend-up"></div>
								</c:if>
								
								<c:if test="${firstExercise && exercise.accuracy >= 60 }">
								<span class="manner manner-good"></span>
								</c:if>
								<c:if test="${firstExercise && exercise.accuracy == 100}">
								<span class="manner manner-prase"></span>
								</c:if>
								<c:if test="${firstExercise && exercise.accuracy < 60 }">
								<!-- <span class="manner manner-notgood"></span> -->
								</c:if>
								<span
									<c:if test="${exercise.accuracy >= 60 }">
								 		class="colored"
									</c:if>
									<c:if test="${exercise.accuracy < 60 }">
								 		class="warned"
									</c:if>>正确率<span>${exercise.accuracy }%</span>
								</span>
							</div>
							<p>
								此次练习你答对<span class="colored">${exercise.rightNum }</span>题，答错<span class="warned">${exercise.totalNum - exercise.rightNum }</span>题，正确率<span
									class="colored">${exercise.accuracy}%</span>。
							</p>
							<c:choose>
								<c:when test="${exercise.accuracy == 100}">
									<p>
									<c:if test="${exercise.exerciseType eq 1 }">章节</c:if><c:if test="${exercise.exerciseType eq 2 }">知识点</c:if>掌握的很棒，加油！
			                       	</p>
								</c:when>
								<c:when test="${exercise.accuracy >= 60 && exercise.growth <= 0 }">
										<p>练习时需要专心仔细，才能更好的掌握知识点哦~</p>
								</c:when>
								<c:when test="${exercise.accuracy >= 60 && (firstExercise || exercise.growth > 0) || exercise.accuracy < 60 && exercise.growth > 0 }">
										<p>小乐为不懈努力的你点赞，再接再厉哦~</p>
								</c:when>
								<c:otherwise>
									<p>你的练习结果不佳，需要多多练习提升自己哦~</p>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
				<div class="details">
					<div class="title">
						<span class="left"><i class="iconfont">󰂽</i>练习详情</span> <a target="_blank"
							href="/auth/student/exercise/viewWork.htm?id=${exercise.exerciseId }" class="right">查看练习<i class="iconfont">󰌁</i></a>
					</div>
					<c:if test="${directReport != null && directReport.size() > 0}">
					<div class="m-table">
						<table>
							<thead>
								<tr>
									<th width="48%">知识点</th>
									<th>练习情况</th>
									<th>掌握情况</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="j-body-direct-knowledge">
							<c:forEach  items="${directReport }" var="item" >
								<tr>
									<td>${item.knowledgeName}</td>
									<td>正确率为${item.accuracy}%</td>
									<td><span class="ismastered 
										<c:if test="${item.accuracy < 50}">totalnone</c:if>
										<c:if test="${item.accuracy >= 50 && item.accuracy < 85}">half</c:if>
										<c:if test="${item.accuracy >= 85}">already</c:if>
									"></span></td>
									<td class="operation">
									<a target="_blank" href="/auth/student/exercise/viewWorkForKnowledge.htm?id=${exercise.exerciseId}&knowledgeId=${item.knowledgeId}">详情</a>
									<a target="_blank" href="${initParam.wrongtopicServerName }/auth/student/strengthen/list.htm?knowledgeId=${item.knowledgeId}">强化练习</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					</c:if>
					<c:if test="${relationReport != null && relationReport.size() > 0}">
					<div class="knoledgepoint">根据练习情况，系统推测出相关知识点掌握情况如下：</div>
					<div class="m-table">
						<table>
							<thead>
								<tr>
									<th width="48%">知识点</th>
									<th>练习情况</th>
									<th>掌握情况</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="j-body-realtion-knowledge">
							<c:forEach  items="${relationReport }" var="item" >
								<tr>
									<td>${item.knowledgeName}</td>
									<td>正确率为${item.accuracy}%</td>
									<td><span class="ismastered 
										<c:if test="${item.accuracy < 50}">totalnone</c:if>
										<c:if test="${item.accuracy >= 50 && item.accuracy < 80}">half</c:if>
										<c:if test="${item.accuracy >= 80}">already</c:if>
									"></span></td>
									<td class="operation">
									<a target="_blank" href="/auth/student/exercise/viewWorkForKnowledge.htm?id=${exercise.exerciseId}&knowledgeId=${item.knowledgeId}">详情</a>
									<a target="_blank" href="${initParam.wrongtopicServerName }/auth/student/strengthen/list.htm?knowledgeId=${item.knowledgeId}">强化练习</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					</c:if>
					<span class="remarks"><i class="iconfont">󰅂</i>备注：报告仅针对本次练习。</span>
				</div>

			</div>
		</div>
	</div>
</body>
</html>