<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>
	<c:if test="${query.operateType == 1}">
	录入量统计
	</c:if>
	<c:if test="${query.operateType == 4}">
	审核量统计
	</c:if>
	<c:if test="${query.operateType == 7}">
	校对量统计
	</c:if>
	- 乐课网
</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/examination.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
			<div class="examination" data-status-type="${query.statusType}"
				data-school-stage-id="${query.schoolStageId}"
				data-subject-id="${query.subjectId}"
				data-operate-date="<fmt:formatDate value='${query.operateDate}' pattern='yyyy-MM-dd'/>"
				data-operator-id="${query.operatorId}" data-operate-type="${query.operateType}" >
				<div class="m-search-box">
					<div class="item">
						<label class="title">出版社：</label>
						<select name="pressId" class="u-select u-select-nm"></select>
						<label class="title">学段：</label>
						<select name="schoolStageId" class="u-select u-select-nm"></select>
						<label class="title">年级：</label>
						<select name="gradeId" class="u-select u-select-nm"></select>
						<label class="title">科目：</label>
						<select name="subjectId" class="u-select u-select-nm"></select>
						<label class="title">教材：</label>
						<select name="materialId" class="u-select u-select-nm"></select>
					</div>
					<div class="item">
						<c:if test="${query.operateType == 1}">
						<label class="title">录入日期：</label>
						</c:if>
						<c:if test="${query.operateType == 4}">
						<label class="title">审核日期：</label>
						</c:if>
						<c:if test="${query.operateType == 7}">
						<label class="title">校对日期：</label>
						</c:if>
	   					<input name="operateDate" type="text" class="Wdate u-ipt u-ipt-sm" style="width:100px;">
						<label class="title">题型：</label>
	   					<select name="questionTypeId" class="u-select u-select-nm"></select>
	   					<label class="title">题号：</label>
	   					<input name="questionId" type="text" class="u-ipt u-ipt-nm">
	   					<input name="content" type="text" class="u-ipt u-ipt-nm" placeholder="输入文本..."/>
	   					<button class="u-btn u-btn-nm u-btn-bg-turquoise btnSearch">查询</button>
					</div>
				</div>
				<div class="quesContent f-bfc">
					<div class="quesStyleCut">
						<div class="quesTotal">
							<ul>
								<c:if test="${query.operateType == 1}">
								<li class="quesTab" data-status="1"><a>录入量</a></li>
								<li class="quesTab" data-status="2"><a>退回量</a></li>
								<li class="quesTab" data-status="3"><a>修正量</a></li>
								</c:if>
								<c:if test="${query.operateType == 4}">
								<li class="quesTab" data-status="4"><a>审核量</a></li>
								<li class="quesTab" data-status="5"><a>纠错量</a></li>
								</c:if>
								<c:if test="${query.operateType == 7}">
								<li class="quesTab" data-status="7"><a>校对量</a></li>
								</c:if>
							</ul>
						</div>
						<div class="quesCutContent">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script id="loading_tpl" type="x-mustache">
<div style="margin-top: 30px;">
	<div style="margin: 0 auto;" class="loading"></div>
	<div style="margin: 0 auto; text-align: center;">加载中，请稍候...</div>
</div>
</script>

<leke:pref/>
<script>
seajs.use('question/view/question/common/statisQueList', function(QueList) {
	new QueList({
		durl: window.ctx + '/auth/common/question/statisQueListDetails.htm'
	});
});
</script>
</body>
</html>