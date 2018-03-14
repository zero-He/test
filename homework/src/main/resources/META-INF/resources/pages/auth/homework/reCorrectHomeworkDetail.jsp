<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>复批作业 - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>

<link type="text/css" rel="stylesheet" href="${assets}/styles/homework/homework.css?t=${_t}" />
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<form action="" method="post" id="formPage" autocomplete="off">
				<input type="hidden" id="homeworkId" name="homeworkId" value="${homework.homeworkId}">
				<div class="homework-test">
				 	<div class="head-infor">
					<span class="work-name">
						<c:if test="${isExam == true}">试卷标题：</c:if>
						<c:if test="${isExam == false}">作业标题：</c:if>
					<c:if test="${homework.homeworkType !=6 }">
					<a class="u-link" href="${initParam.paperServerName}/auth/common/paper/view.htm?paperId=${homework.paperId}"
						target="_blank">${homework.homeworkName}（${homeworkTypeStr}）</a>
					</c:if>
					<c:if test="${homework.homeworkType ==6 }">
						${homework.homeworkName}（${homeworkTypeStr}）
					</c:if>
					</span> <span
					class="class">班级：<span>${homework.className}</span></span>
					</div>
					<c:if test="${ homework.bugFixNum - homework.reviewNum >0 }">
					<input class="u-btn u-btn-nm u-btn-bg-turquoise f-fr j-batchCorrect" type="button" value="全部通过">
					<input class="u-btn u-btn-nm u-btn-bg-gray f-fr <c:if test="${homework.bugFixNum - homework.reviewNum >0 }">f-hide</c:if> j_diabledBtn"  type="button" value="全部通过">
					<div class="tips">
						备注：点击全部通过后，当前页面中已经提交的学生的
						<c:if test="${isExam == true}">试卷</c:if>
						<c:if test="${isExam == false}">作业</c:if>
						将默认为全部通过订正。
					</div>
					</c:if>
				</div>
				<div class="m-table">
					<table class="z-correct-progress">
						<thead>
							<tr>
								<th width="15%"><locale:message code="homework.homework.studentname" /></th>
								<th width="20%">订正次数</th>
								<th width="20%">总错题数</th>
								<th width="35%">最后一次订正时间</th>
								<th width="10%">操作区</th>
							</tr>
						</thead>
						<tbody id="homeworkDtlListContext"></tbody>
					</table>
					<div class="m-tips j-no-data f-hide"><i></i><span>对不起，没有您要查询的数据</span></div>
				</div>
			</form>
		</div>
	</div>

	<script id="homeworkDtlContext_tpl" type="text/handlebars">
	{{#dataList}} 
	<tr> 
		<td>{{studentName}}</td>
		<td>
			{{#cif 'this.bugFixCount >0 '}}					 		{{bugFixCount}}次 		{{/cif}}
			{{#cif 'this.scoreRate >=1 && this.bugFixCount ==0 '}}	满分作业					{{/cif}}
			{{#cif 'this.scoreRate <1 & this.bugFixCount == 0'}}	/						{{/cif}}
		</td>
		<td>
			{{#cif 'this.errorTotal == null '}}	 /		{{/cif}}
			{{#cif 'this.errorTotal != null '}}	{{errorTotal}}	{{/cif}}
		</td>
		<td>
			{{#cif 'this.bugFixTime != null'}}	{{fmtTime this.bugFixTime}} 
				{{else}} /	
			{{/cif}}
		</td>
		<td class="operation">
		{{#cif 'this.bugFixTime != null'}}
			{{#cif 'this.bugFixStage == 2'}}
			<a href="/auth/teacher/homework/reviewWork.htm?homeworkDtlId={{homeworkDtlId}}" target="_blank">复批</a>
			{{/cif}}
		{{/cif}}
		<a href="/auth/teacher/homework/viewWork.htm?homeworkDtlId={{homeworkDtlId}}" target="_blank">查看</a>
		</td>
	</tr> 
	{{/dataList}}
</script>

<script type="text/javascript">
		var Ctx = {
			userId : '${user.id}',
			roleId : '${user.currentRole.id}'
		};
		seajs.use('homework/homework/reCorrectHomeworkDetail');
</script>
</body>
</html>