<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>作业管家</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0,minimum-scale=1.0,maximum-scale=1.0">
<link rel="stylesheet" href="${assets}/styles/mobile/global.css?_t=20171115">
<link rel="stylesheet" href="${assets}/styles/mobile/homework/homeworkphone.css?_t=20171115">
<leke:context />
</head>
<body>
	<article class="c-kemu-body">
		<c:if test="${empty subjects}">
			<div class="c-tips">
                <div class="msg">暂无数据~~</div>
            </div>
		</c:if>
	    <ul class="c-kemu">
	    	<c:forEach var="subject" items="${subjects}">
    		<a href="${ctx}/auth/m/person/homework/worklist.htm?subjectId=${subject.subjectId}">
    			<li>
	    			<span class="title title${subject.subjectId}">${subject.subjectName}</span>
					<span class="unfinish-num">${subject.unfinishNum <= 99 ? subject.unfinishNum : '99+'}</span>
					<span class="unfinish">待完成</span>
					<span class="icon icon${subject.subjectId}"></span>
	    		</li>
    		</a>
	    	</c:forEach>
	    </ul>
    </article>
    <script src="${assets}/scripts/common/mobile/zepto.min.js?_t=20171115"></script>
    <script src="/scripts/m/homework/person/student-manager.js?_t=20171115"></script>
</body>
</html>
