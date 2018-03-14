<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>寒暑假作业 - <locale:message code="common.page.header.leke"/></title>
	<%@ include file="/pages/common/meta.jsp" %>

	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/homework/homework.css">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="m-bg"></div>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp" %>
	<input type="hidden" name="isTecher" value="${isTecher}"/>

	<div class="g-mn">
		<c:if test="${isTecher }">
			<div class="m-tab">
				<ul>
					<li><a href="${initParam.homeworkServerName}/auth/teacher/homework/homeworkList.htm">作业</a></li>
					<li><a href="${initParam.homeworkServerName}/auth/teacher/exam/teaOnlineExamList.htm?spm=101003">考试列表</a></li>
					<%--<li><a href="${initParam.beikeServerName}/auth/teacher/sendmc/sendmcRecord.htm?spm=101003">微课</a></li>--%>
					<li class="active"><a>寒暑假作业</a></li>
					<c:if test="${!empty isEnglish}">
						<li><a href="${initParam.voiceServerName}/auth/teacher/pc/successAssignedHomworkList.htm?spm=101003">英语口语</a></li>
					</c:if>
				</ul>
			</div>
		</c:if>

		<%--学科--%>
		<div class="m-tab">
			<ul class="corr">
				<c:choose>
					<c:when test="${isTecher}">
						<c:forEach var="ts" items="${teachSubj}" varStatus="status">
							<c:choose>
								<c:when test="${ts.subjectId==subjectId}">
									<li class="active" data-name="subjectId" data-subjectId="${ts.subjectId}"><a>${ts.subjectName }</a></li>
								</c:when>
								<c:otherwise>
									<li data-name="subjectId" data-subjectId="${ts.subjectId}"><a>${ts.subjectName }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>

					<c:otherwise>
						<c:forEach var="ts" items="${classSubjectList}" varStatus="status">
							<c:choose>
								<c:when test="${ts.classId==classId}">
									<li class="active" data-name="classId" data-subjectId="${ts.classId}"><a>${ts.className }</a></li>
								</c:when>
								<c:otherwise>
									<li data-name="classId" data-subjectId="${ts.classId}"><a>${ts.className }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:otherwise>
				</c:choose>

			</ul>
		</div>

		<%--班级和内容--%>
		<div class="c-homework-teacherdetail">
			<%--班级--%>
			<div class="m-tab">
				<ul class="corr">
					<c:choose>
						<%--是老师的情况--%>
						<c:when test="${isTecher}">
							<div class="m-search-box" style="border: none">
								<label for class="title">班级：</label>
								<select id="jClasses" name="classes" class="u-select u-select-mn">
									<%--<option value="">全部</option>--%>
									<c:forEach var="ts" items="${teachSubj}" varStatus="status">
										<c:forEach var="cz" items="${ts.clazzList}" varStatus="czStatus">
											<c:choose>
												<c:when test="${ts.subjectId==subjectId}">
													<option value="${cz.classId}">${cz.className}</option>
												</c:when>
												<c:otherwise>
													<option style="display:none" value="${cz.classId}">${cz.className}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:forEach>
								</select>
								<label for class="title">寒暑假时间：</label>
								<select id="jYearHoliday" name="yearHoliday" class="u-select u-select-mn">
									<%--<option value="">全部</option>--%>
									<c:forEach var="yh" items="${yearHoliday}">
										<option value="${yh.yearId}">${yh.yearName}</option>
									</c:forEach>
								</select>
							</div>
						</c:when>
						<%--不是老师的情况--%>
						<c:otherwise>
							<c:forEach var="ts" items="${classSubjectList}" varStatus="status">
								<c:forEach var="cz" items="${ts.subjectList}" varStatus="czStatus">
									<c:choose>
										<c:when test="${ts.classId==classId}">
											<c:if test="${cz.subjectId== subjectId}">
												<li class="active" data-name="subjectId" data-classId="${ts.classId}" data-subjectId="${cz.subjectId}"><a>${cz.subjectName }</a></li>
											</c:if>
											<c:if test="${cz.subjectId!=subjectId}">
												<li data-name="subjectId" data-classId="${ts.classId}" data-subjectId="${cz.subjectId}"><a>${cz.subjectName }</a></li>
											</c:if>
										</c:when>
										<c:otherwise>
											<li style="display:none" data-name="subjectId" data-index=${czStatus.index} data-classId="${ts.classId}" data-subjectId="${cz.subjectId}">
												<a>${cz.subjectName }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:forEach>
						</c:otherwise>

					</c:choose>

				</ul>
				<c:if test="${isTecher }">
					<div class="operation">
						<a class="u-btn u-btn-nm u-btn-bg-orange">催交作业</a>
					</div>
				</c:if>
			</div>
			<%--内容--%>
			<form id="myHomeworkForm" method="post" autocomplete="off">
				<input type="hidden" name="classId" value="${classId}"/>
				<input type="hidden" name="subjectId" value="${subjectId}"/>
				<input type="hidden" name="year" value="${year}"/>
				<input type="hidden" name="holiday" value="${holiday}"/>
				<div class="m-table">
					<table>
						<tr>
							<th>学生姓名</th>
							<th>类型</th>
							<th>进度</th>
							<th>开始学习时间</th>
							<th>最近一次学习时间</th>
							<th>操作区</th>
						</tr>
						<tbody>
						<!-- ko foreach:{data:dataList,as:'d'}  -->
						<tr>
							<td><!--ko text:d.userName --><!--/ko--></td>
							<td><!--ko text:d.typeName --><!--/ko--></td>
							<td>
			                            <span class="homework-teacherdetail-progress-progress f-fl">
			                            	<span class="homework-teacherdetail-progress-bar" data-bind="style: { width: d.p }"></span>
			                            </span>
								<span class="homework-teacherdetail-progress-state f-fl"><!--ko text:d.p --><!--/ko--></span>
							</td>
							<td><!--ko text:d.firstTime --><!--/ko--></td>
							<td><!--ko text:d.lastTime --><!--/ko--></td>
							<td class="operation"><a data-bind="attr: { href: d.viewUrl}">查看详情</a></td>
						</tr>
						<!-- /ko -->
						</tbody>

					</table>
					<div class="page" id="page">
						<div class="f-hide tips f-tac" id="emptyTip">对不起，没有您要查询的数据</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<leke:pref/>
<script>
	seajs.use('homework/homework/vacationHomeworkTList');
</script>
</body>
</html>
