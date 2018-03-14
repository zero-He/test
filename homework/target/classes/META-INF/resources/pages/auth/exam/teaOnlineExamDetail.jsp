<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>按人批改 - <locale:message code="common.page.header.leke"/></title>
	<%@ include file="/pages/common/meta.jsp" %>
	<link type="text/css" rel="stylesheet" href="${assets}/styles/homework/homework.css?t=20171115"/>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="m-tab">
		<ul>
			<li class="active"><a>按人批改</a></li>
			<c:if test="${homework.subjective && !homework.isSelfCheck}">
				<li><a href="/auth/teacher/exam/batchByQuestions.htm?homeworkId=${homework.homeworkId }&paperId=${homework.paperId}">按题批改</a></li>
			</c:if>
		</ul>
	</div>
	<div class="c-teachcheckwork">
		<div class="c-teachcheckwork-trbp">
			<div class="head-infor">
				<span class="work-name">试卷标题：
					<c:if test="${homework.paperId != null }">
						<a class="u-link" href="${initParam.paperServerName}/auth/common/paper/view.htm?paperId=${homework.paperId}&editBtn=1" target="_blank">
								${homework.homeworkName}
						</a>
					</c:if>
					<c:if test="${homework.paperId == null }">
						${homework.homeworkName}（${homeworkTypeStr}）
					</c:if>
				</span>
				<span class="class">班级：<span>${homework.className}</span></span>
				<c:if test="${!homework.isOpenAnswer && homework.openAnswerTime !=null }">
					<span>该份试卷已设于【<fmt:formatDate value="${homework.openAnswerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>】后公布答案</span>
				</c:if>
				<c:if test="${homework.isOpenAnswer }">
					  <span>已公布答案</span>
				</c:if>
			</div>
			<div class="m-table">
				<table>
					<thead>
					<tr>
						<th>学生姓名</th>
						<th>得分<span class="m-sorting"><i></i></span></th>
						<th>试卷批改进度</th>
						<th>提交时间</th>
						<th>操作区</th>
					</tr>
					</thead>
					<tbody id="homeworkDtlListContext"></tbody>
				</table>
				<div class="m-tips j-no-data f-hide">
					<i></i><span>对不起，没有您要查询的数据</span>
				</div>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="j_questionNum" value="${questionNum }"/>
<input type="hidden" id="j_homeworkId" value="${homework.homeworkId }"/>
<input type="hidden" id="j_closeTime" value="${closeTime}"/>

<script id="homeworkDtlContext_tpl" type="text/x-handlebars">
	{{#dataList}}
	<tr {{trStyle}}>
		<td>{{studentName}}</td>
		<td>{{score}}</td>
		<td>
			已批改/总数：
			<span class="mann"><span>{{correctCount}}</span>/<span>{{paperQuestionNum }}</span></span>
			<span class="rate-back"><span class="rate-front" style="width: {{progressRate}}%;"></span></span><span class="rate">{{progressRate}}%</span>
		</td>
		<td>
			{{#cif 'this.submitTime != null'}}
			<span>{{submitTimeStr}}</span>
			{{else}}
				{{#cif 'this.isNotExam == true'}}
					<span class="s-c-orange">缺考</span>
				{{/cif}}
			{{/cif}}
		</td>
		<td class="operation">
			{{#cif 'this.submitTime != null'}}
			<c:if test="${homework.isSelfCheck }">
				<a href="/auth/teacher/homework/viewWork.htm?homeworkDtlId={{homeworkDtlId}}" target="_blank" class="link">查看</a>
			</c:if>
			<c:if test="${!homework.isSelfCheck }">
				{{#cif 'this.buttonAction == "viewWork"'}}
					<a href="/auth/teacher/homework/viewWork.htm?homeworkDtlId={{homeworkDtlId}}" target="_blank" class="link">{{buttonName}}</a>
				{{else}}
					<a href="/auth/teacher/homework/{{buttonAction}}.htm?homeworkDtlId={{homeworkDtlId}}" target="_blank" class="link">{{buttonName}}</a>
				{{/cif}}
			</c:if>
			<c:if test="${homework.subjective && currentRoleId eq 101}">
				{{#cif 'this.correctTime != null'}}
				<a href="/auth/teacher/homework/correctWork.htm?homeworkDtlId={{homeworkDtlId}}&isAgain=1" target="_blank" class="link">重批</a>
				{{/cif}}
			</c:if>
			{{/cif}}
		</td>
	</tr>
	{{/dataList}}
</script>

<script type="text/javascript">
	seajs.use('homework/exam/teaOnlineExamDetail');
</script>
</body>
</html>