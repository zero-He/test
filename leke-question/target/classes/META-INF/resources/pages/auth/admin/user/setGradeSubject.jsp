<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>设置年级科目 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
</head>
<body>
<form>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail addGrSub">
			<div id="gradeId">
				<span>选择年级：</span>
					<c:forEach items="${gradeList}" var="grade">
						<label><input value="${grade.gradeId}" type="checkbox" data-gnm="${grade.gradeName}"/> ${grade.gradeName}</label>
					</c:forEach>
			</div>
			<div id="subjectId">
				<span>选择科目：</span>
					<c:forEach items="${subjectList}" var="subject">
						<label><input value="${subject.subjectId}" type="checkbox" data-snm="${subject.subjectName}" /> ${subject.subjectName}</label>
					</c:forEach>
			</div>
			<input id="bAdd" class="u-btn u-btn-nm u-btn-bg-orange u-btn-auto f-mt10" type="button" value="添加年级科目"/>
			<div id="resultId">
				<span>已选任教年级科目：</span>
				
			</div>
			<input type="hidden" id="id" value=${id } />
		</div>
		<div class="bSave"><input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise" id="bSave" value="保存"/></div>
	</div>
</div>
</form>


<script type="text/javascript">
	seajs.use('question/user/setGradeSubject');
</script>
</body>
</html>