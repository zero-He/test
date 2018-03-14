<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="homework.homework.checkhomework" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>

<link type="text/css" rel="stylesheet" href="${assets}/styles/homework/homework.css?t=20171115" />

</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="c-teachcheckwork">
			<div class="c-teachcheckwork-trbp">
				<div class="head-infor">
					<span class="work-name">作业标题：
						<c:if test="${homework.paperId != null }">
							<a class="u-link"
							href="${initParam.paperServerName}/auth/common/paper/view.htm?paperId=${homework.paperId}&editBtn=1"
							target="_blank">${homework.homeworkName}（${homeworkTypeStr}）</a>
						</c:if>
						<c:if test="${homework.paperId == null }">
						${homework.homeworkName}（${homeworkTypeStr}）
						</c:if>
					</span> 
					<span class="class">班级：<span>${homework.className}</span></span>
					<c:if test="${!homework.isOpenAnswer && homework.openAnswerTime !=null }">
						<span>该份作业已设于【<fmt:formatDate value="${homework.openAnswerTime}" pattern="yyyy-MM-dd HH:mm:ss" />】后公布答案</span>
					</c:if>
					<c:if test="${homework.isOpenAnswer }">
					  <span>已公布答案</span>
					</c:if>
			        
				</div>
				<div class="m-table">
					<table>
						<thead>
							<tr>
								<th><locale:message code="homework.homework.studentname" /></th>
								<th><locale:message code="homework.homework.score" /> 
								<a class="iconfont icon help" title="分数+，分数指作业中客观题的分数，+意味着老师还未批完主观题。如20+，指该作业客观题得分为20，主观题老师还没批完。">
										󰅃
								</a>
								<span class="m-sorting"><i></i></span></th>
								<th>作业批改进度</th>
								<th><locale:message code="homework.homework.submittime" /></th>
								<c:if test="${homework.usePhase != 2}">
									<th>作业用时</th>
								</c:if>
								<th>批改人</th>
								<th><locale:message code="homework.homework.operatingarea" /></th>
							</tr>
						</thead>
						<tbody id="homeworkDtlListContext">

						</tbody>
					</table>
					<div class="m-tips j-no-data f-hide">
						<i></i><span>对不起，没有您要查询的数据</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" id="j_questionNum" value="${questionNum }" />
	<input type="hidden" id="j_homeworkId" value="${homework.homeworkId }" />
	<input type="hidden" id="j_homework_classId" value="${homework.classId }" />

<script id="homeworkDtlContext_tpl" type="x-mustache">
	{{#dataList}} 
	<tr{{trStyle}} data-hwdtlid="{{homeworkDtlId}}"> 
		<td class="c-table-name" >{{studentName}}</td>
		<td>{{score}}{{correctStatusStr}}</td>
		<td>
		已批改/总数：
		<span class="mann" >
			<span>{{correctCount}}</span>/<span>{{paperQuestionNum }}</span>
		</span>
        <span class="rate-back">
            <span class="rate-front" style="width: {{progressRate}}%;"></span>
        </span>
        <span class="rate">{{progressRate}}%</span>
		</td>
		<td>{{{timsHtmlStr}}}  <span class="handin-time">{{{chijiao}}}</span></td>
		<c:if test="${homework.usePhase != 2}">
			<td>
				{{#cif 'this.submitTime != null'}}
					{{usedTimeStr}}
				{{/cif}}
			</td>
		</c:if>
		<td class="operation  {{#correctTime}} name-p {{/correctTime}} {{^correctTime}} name-sp {{/correctTime}}">
			<p>
			{{#correctUserName}}
		 		{{.}}
			{{/correctUserName}}
			{{^correctUserName}}
		 		${homework.teacherName}
			{{/correctUserName}}
			</p>
		</td>
		<td class="operation">
			{{#cif 'this.submitTime != null'}}
				<a href="viewWork.htm?homeworkDtlId={{homeworkDtlId}}" target="_blank" class="link">查看</a>
			{{/cif}}
		</td>
	</tr> 
	{{/dataList}}
</script>

	<script type="text/javascript">
		seajs.use('homework/homework/homeworkDetail_View');
	</script>
</body>
</html>