<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins" %>
<!DOCTYPE html>
<html>
<head>
	<title>按题批改 - <locale:message code="common.page.header.leke"/></title>
	<%@ include file="/pages/common/meta.jsp" %>
	<link type="text/css" rel="stylesheet" href="${assets}/styles/homework/homework.css?t=${_t}"/>
	<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/correctHomework.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="m-tab">
		<ul>
			<li><a href="/auth/teacher/exam/teaOnlineExamDetail.htm?homeworkId=${homework.homeworkId }">按人批改</a></li>
			<li class="active"><a>按题批改</a></li>
		</ul>
	</div>

	<div class="c-teachcheckwork">
		<div class="c-teachcheckwork-trbq">
			<div class="head-infor">
				<span class="work-name">试卷标题：
					<a href="${initParam.paperServerName}/auth/common/paper/view.htm?paperId=${homework.paperId}" target="_blank">
						${homework.homeworkName}（${homeworkTypeStr}）
					</a>
				</span>
				<span class="class">班级：<span>${homework.className}</span></span>
				<c:if test="${!homework.isOpenAnswer && homework.openAnswerTime !=null }">
					<span>该份作业已设于【<fmt:formatDate value="${homework.openAnswerTime}" pattern="yyyy-MM-dd HH:mm:ss" />】后公布答案</span>
				</c:if>
				<c:if test="${homework.isOpenAnswer }">
					<span>已公布答案</span>
				</c:if>
			</div>
			<div class="p-sheet">
				<div class="m-correct-question">
					<c:if test="${paperType eq 2 && !empty paperAttachement }">
						<div class="m-correct-question-left">
							<h5>试卷区域</h5>
							<dfn class="j-dfn" data-type="document" data-width="780"
							     data-height="1060"
							     data-param="index=1&serverIp=onlineclass.leke.cn&taskId=${paperAttachement.taskId }&transType=${paperAttachement.destExt }
							     &filePath=${paperAttachement.destPath }&count=${paperAttachement.destPageCount}"></dfn>
						</div>
					</c:if>
					<div class="m-correct-question-right full scrollbar-outer j-card-scrollbar">
						<c:if test="${paperType eq 2 && !empty paperAttachement }">
							<h6 class="extends">答题卡 <i class="btn"></i></h6>
						</c:if>
						<ul>
							<c:forEach items="${groups}" var="item">
								<li>
									<div class="p-group-title f-clearfix" data-subjective="false">
										<i class="p-arrow j-arrow"></i><dfn>${item.groupTitle }</dfn><em>（总分${item.score }分）</em>
									</div>
									<div class="p-group-detail">
										<ul class="m-correct-question-list">
											<c:forEach items="${item.questions }" var="question">
												<li class="m-question-item">
													<div class="item-info j-progress1 nobottom">
														<div class="subject">
															<span class="subject-num">${question.ord }</span>
															<span class="progress-bar-info">已批改/已提交：
																<em class="proportion-text">
																	<c:if test="${empty progressMaps }">0</c:if>
																	${progressMaps[question.questionId.toString()]}/${finishNum }
																</em>
															</span>
														</div>
														<div class="operate">
															<c:if test="${!homework.isSelfCheck && question.subjective && homework.finishNum gt 0}">
																<a href="/auth/teacher/batch/workbench.htm?homeworkId=${homeworkId}&questionId=${question.questionId}&isExam=true">批改</a>
															</c:if>
															<c:if test="${homework.isSelfCheck || !question.subjective || empty progressMaps }">
																<a href="javascript:;" class="s-c-gray-c">批改</a>
															</c:if>
														</div>
														<div class="progress-wrap">
															<div class="progress-bar">
																<div class="bar" style="width: ${100 * progressMaps[question.questionId.toString()] /finishNum}%;"></div>
															</div>
														</div>
													</div>
													<div class="subject-detail j-subject-detail f-hide"></div>
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
			<p class="remark">备注：按题批改模式下不能添加对试卷的评语</p>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	seajs.use('homework/exam/batchByQuestions');
</script>
</html>