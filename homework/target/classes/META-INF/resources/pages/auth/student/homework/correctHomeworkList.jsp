<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>批改作业 - <locale:message code="common.page.header.leke" /> </title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=20171115">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
    <div class="g-mn">
        <div class="z-homework-grade z-homework-grade-stu">
            <ul id="j-body-homework">
                
            </ul>
            <div id="j_emptyData" class="tips f-tac f-hide" ><div class="m-tips"><i></i><span>您暂无待批改的作业哦~</span></div></div>
        </div>
    </div>
</div>

<script id="j-tmpl-homework" type="x-mustache">
{{#dataList}}
<li class="item paper-item">
	<div class="title">
		<a class="name jHomeworkTitle">[{{resTypeName}}] {{homeworkName}}</a>
		<div class="paper-duration">同学{{studentName}}</div>
		<div class="c-btns">
			{{#cif 'this.submitTime != null'}}
				<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/homework/correctWork.htm?homeworkDtlId={{homeworkDtlId}}" >批改</a>
			{{else}}
				<a class="u-btn u-btn-nm u-btn-bg-gray" >批改</a>
			{{/cif}}
		</div>
	</div>
	<div class="tips">
		<span><i class="iconfont">󰃊</i> {{subjectName}}</span>
		<span><i class="iconfont">󰃬</i> 老师：{{teacherName}}</span>
		<span><i class="iconfont"></i></span>
		<p class="time">提交时间：{{fmtSubmitTime}}</p>
		<div class="state">
		</div>
	</div>
</li>
{{/dataList}}
</script>
</body>
<script>
	seajs.use('homework/homework/correctHomeworkList');
</script>
</html>