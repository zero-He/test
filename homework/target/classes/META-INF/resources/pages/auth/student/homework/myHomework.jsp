<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<title>我的作业 - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/globalnote.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/book.css?t=20171115">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-sd">
			<%@ include file="/pages/navigate/navigate.jsp"%>
		</div>
		<div class="g-mn">
            <div class="z-homework-classify">
            	<h4 class="z-homework-title">我的作业 <input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise j_allHomework" value="全部作业"></h4>
	            <div class="z-book">
				<ul class="z-book-retake" id="myHomeworkContext">
					
				</ul>
				<div class="m-tips f-block f-hide" id="j_emptyData">
						<i></i>
			        <span class="msg">对不起，暂无待完成和待订正的作业，点击<a class="s-c-turquoise j_allHomework"  href="javascript:;">全部作业</a>，可查看我的所有作业。</span>
			    </div>
			</div>
		</div>
	</div>
	<script id="myHomeworkContext_tpl" type="text/handlebars">
{{#.}}
	<li>
		<a href="/auth/student/exercise/homework/myHomework.htm?subjectId={{subjectId}}&ord=2" target="_blank" >
			<dl>
				<dt>
					{{subjectName}}
					</dt>
				<dd class="z-book-retake-num">待完成：<label>{{unfinishNum}}</label>份</dd>
				<dd>待订正：<label>{{bugFixNum}}</label>份</dd>
			</dl>
		</a>
	</li>
{{/.}}
	</script>
<script>
	seajs.use('homework/homework/myHomework');
</script>
</body>
</html>