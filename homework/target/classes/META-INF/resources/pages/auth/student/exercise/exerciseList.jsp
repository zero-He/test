<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${flag eq '1'}">
	<c:set var="thName"><locale:message code="homework.common.section" /></c:set>
	<c:set var="exerciseFlag" ><locale:message code="homework.common.studyskills" /></c:set>
</c:if>
<c:if test="${flag eq '2'}">
	<c:set var="thName" ><locale:message code="homework.common.knowledgepoint" /></c:set>
	<c:set var="exerciseFlag" ><locale:message code="homework.common.knowledtest" /></c:set>
</c:if>
<title>${exerciseFlag }-<locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel='stylesheet' href='${ctx}/scripts/common/ui/ui-tree/skin/default/ui-tree.css?t=20171115' type='text/css'>
<link rel="stylesheet" href="${assets}/styles/question/question-sheet.css?t=20171115">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="m-search-box">
				<form id="exerciseForm" method="post" autocomplete="off">
				<div class="tab">
                    <ul id="jCorrectFlag">
                        <li class="${flag eq '1' ? 'cur' : ''}"><a href="${ctx}/auth/student/exercise/syn/exerciseList.htm">章节模式</a></li>
                        <li class="${flag eq '2' ? 'cur' : ''}"><a href="${ctx}/auth/student/exercise/kno/knowledgeList.htm">知识点模式</a></li>
                    </ul>
                </div>
					<input type="hidden" id="flag" value="${flag }">
					<input type="hidden" name="schoolId" value="${schoolId }">
					<input type="hidden" id="jSubjectId" name="subjectId" />
					<label class="title"><locale:message code="homework.homework.subject" />：</label>
					<select id="jSubject" class="u-select u-select-nm"></select>
					<label class="title">${thName }：</label>
					<input type="text" class="u-ipt u-ipt-nm" id="exerciseName" name="exerciseName" />
					<label class="title"><locale:message code="homework.homework.form.search.testtime" />：</label>
					<input name="startTime" id="startTime" class="u-ipt u-ipt-nm Wdate" />
					<locale:message code="homework.homework.form.search.to" />
					<input name="submitTime" id="submitTime" class="u-ipt u-ipt-nm Wdate" />
					<input class="u-btn u-btn-nm u-btn-bg-turquoise" id="ButExercise" type="button" value="<locale:message code="homework.common.search" />">
					<div class="operation">
						<input class="u-btn u-btn-nm u-btn-bg-orange" type="button" id="butBegin" value="<locale:message code="homework.homework.form.search.starttest" />" />
					</div>
				</form>
			</div>
			<div class="p-exam-exercise">
                <div class="left">
					<div class="m-table">
						<table>
							<thead>
								<tr>
									<th>${thName }</th>
									<th><locale:message code="homework.homework.subject" /></th>
									<th><locale:message code="homework.homework.form.search.testtime" /></th>
									<th><locale:message code="homework.homework.form.search.accuracy" /></th>
									<th><locale:message code="homework.homework.operatingarea" /></th>
								</tr>
							</thead>
							<tbody id="exerciseListContext"></tbody>
						</table>
						<div class="page" id="page"></div>
						<div class="m-tiptext m-tiptext-text m-tiptext-normal">
			                <i class="iconfont icon">󰅂 </i> 
			                <div class="msg">
			                	<a href="${initParam.staticServerName}/pages/help/help.html?class=how-to-rating" target="_blank">小乐提醒您：做题目不但可以获得乐豆还可以提升等级成为学霸!</a>
			                </div>
			            </div>
					</div>
            	</div>
            	<div class="right">
                    <h6>最近30天答题正确数排行榜</h6>
                    <ul id="jRightRank">
                        <li class="active"><a data-id="1">乐课网</a></li>
                        <li><a data-id="2">全校</a></li>
                        <li><a data-id="3">全班</a></li>
                    </ul>
                    <table id="jRightRankBody">
                    </table>
                </div>
            </div>
		</div>
	</div>
	<form id="ekform" action="${cxt }/auth/student/exercise/doWork.htm" method="post" target="_blank">
		<input type="hidden" id="exerciseType" name="exerciseType">
		<input type="hidden" id="exerciseName" name="exerciseName">
		<input type="hidden" id="relId" name="relId">
		<input type="hidden" id="schoolStageId" name="schoolStageId">
		<input type="hidden" id="schoolStageName" name="schoolStageName">
		<input type="hidden" id="subjectId" name="subjectId">
		<input type="hidden" id="subjectName" name="subjectName">
	</form>
	<script id="exerciseContext_tpl" type="x-mustache">
	<tr>
		<td>{{exerciseName}}</td>
		<td>{{subjectName}}</td>
		<td>{{startTime}}</td>
		<td>{{accuracy}}%</td>
		<td class="operation"><a href="${ctx}/auth/student/exercise/viewWork.htm?exerciseId={{exerciseId}}" target="_blank" class="link"><locale:message code="homework.common.toview" /></a></td>
	</tr>
	</script>
	<script id="jRightRankBody_tpl" type="x-mustache">
	{{#items}}
	<tr>
        <td>{{rank}}. {{userName}}</td>
        <td>{{value}}</td>
    </tr>
	{{/items}}
	</script>
	<script>
		seajs.use('homework/exercise/exerciseList');
	</script>

</body>
</html>
