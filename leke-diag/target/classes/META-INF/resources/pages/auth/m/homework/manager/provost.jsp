<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>作业管家</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=640">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${assets}/styles/mobile/v1.3.css?_t=20171115">
    <script src="${assets}/scripts/common/mobile/utils.js?_t=20171115"></script>
    <leke:context />
</head>

<body>
	<section class="c-checkwork">
		<menu class="c-topswipe m-swipe">
        <ul class="swipe-wrap">
           	<c:forEach items="${dataList}" var="data" varStatus="status">
           		<c:if test="${status.index%4==0 and !status.first and !status.last}"></li></c:if>
           		<c:if test="${status.index%4==0}"><li></c:if>
           		<span class="item <c:if test="${status.index==0}">cur</c:if>" data-i="${data.gradeId}" data-submit="${data.submitRate}" data-delay="${data.delayRate}" data-undone="${data.undoneRate}">${data.gradeName}</span>
           		<c:if test="${status.last}"></li></c:if>
    		</c:forEach>
        </ul>
        </menu>
        <div class="daterange">
            <span class="startdate">${startDate}</span>
            ~
            <span class="stopdate">${finishDate}</span>
        </div>
        <div id="jChart" class="chart"></div>
        <div class="workgrade">
            <a id="jToDetail">查看作业完成情况及成绩<i class="jump"></i></a>
        </div>
    </section>
    <script src="${assets}/scripts/common/mobile/zepto.min.js?_t=20171115"></script>
    <script src="/scripts/m/diag/common/echarts.simple.min.js?_t=20171115"></script>
    <script src="/scripts/m/diag/common/swipe.min.js?_t=20171115"></script>
    <script src="/scripts/m/diag/homework/manager/provost.js?_tt=20171115"></script>
</body>
</html>