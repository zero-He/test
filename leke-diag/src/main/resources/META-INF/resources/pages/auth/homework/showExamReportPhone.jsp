<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="${assets}/styles/mobile/global.css" />
<link rel="stylesheet" href="${assets}/styles/mobile/homework/ts-phone.css">
<leke:context />

<title>成绩单</title>
<style>
.m-page-tips{background-color: #f7f7f7;}
</style>
</head>
<body class="ts-paper" >
    <input type="hidden" id="roleId" name="roleId" value="${roleId}"/>
	<article>
		<header id="childrenHeader" class="ts-child-nav">
	        <ul id="ulbodyData">
	        </ul>
        </header>
        <div id="jtbodyData"></div>
	</article>

</body>
<script id="chidrenTpl" type="x-mustache">
{{#childrenList}}
	<li data-id="{{studentId}}">{{studentName}}</li>
{{/childrenList}}
</script>

<script id="examReportListPhoneTpl" type="x-mustache">
{{#dataList}}
    <section class="ts-paper-item"  id="viewDtl" data-id="{{examReportId}}">
        <h4>{{examReportName}}</h4>
        <p class="ts-paper-time">{{modifiedOn}}</p>
        <div class="ts-paper-link">
          	查看详情
          <i class="ts-right-arrow"></i>
        </div>
    </section>
	<span style="display:none">{{studentId}}</span>
{{/dataList}}
</script>

	<script src="${assets}/scripts/common/mobile/common.js"></script>
	<script src="/scripts/diag/homework/showExamReportPhone.js"></script>

</html>
