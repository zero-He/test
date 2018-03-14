<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title>按题批改 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/sheet/batch-correct.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/sheet/sheet-${batchContext.paperType}.css?t=${_t}">
</head>
<body>

	<div class="m-correct-batch-notice">
		<div class="switch-model">
			您已进入按题批改模式
			<c:if test="${batchContext.isExam == true}">
				<a href="/auth/teacher/exam/batchByQuestions.htm?homeworkId=${batchContext.homework.homeworkId}" class="exit">返回至题目列表</a>
			</c:if>
			<c:if test="${batchContext.isExam == false}">
				<a href="batchQuestions.htm?homeworkId=${batchContext.homework.homeworkId}" class="exit">返回至题目列表</a>
			</c:if>
		</div>
		<h1>${batchContext.homework.homeworkName}</h1>
		<div class="head">
			<div class="info">
				<div class="f-ellipsis f-fl" style="width:400px;text-align:left;"><label>批改题目：</label> <label id="jIndex">第0题 </label> </div> 
				<label>批改学生： <a class="u-link s-c-turquoise" id="jStudentName" target="_blank"></a> </label>
				<label>已批改/已提交：<span id="jDoneNum" class="done">0</span>/<span id="jTotalNum"
						class="total">0</span></label>
			</div>
			<div class="btns">
				<button class="u-btn u-btn-sm u-btn-bg-turquoise" id="j_firstStu" data-index="1">第一个</button>
				<button class="u-btn u-btn-sm u-btn-bg-turquoise" id="j_prevStu">上一个</button>
				<button class="u-btn u-btn-sm u-btn-bg-turquoise" id="j_nextStu">下一个</button>
			</div>
		</div>
	</div>
	<div class="m-correct-batch-workbench p-exam">
		<div id="jQuestionBody" class="" style="min-height: 300px;"></div>
	</div>
</body>
<script type="text/javascript">
	var context = ${batchContextJson};
	seajs.use('homework/work/workbench');
</script>
</html>