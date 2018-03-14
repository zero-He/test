<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${subjStats.subjectName}作业情况</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=640">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${assets}/styles/mobile/v1.3.css?_t=20171115">
    <script src="${assets}/scripts/common/mobile/utils.js?_t=20171115"></script>
    <leke:context />
</head>

<body>
	<section class="c-checkwork">
        <div class="daterange">
            <span class="startdate">${startDate}</span>
            ~
            <span class="stopdate">${finishDate}</span>
        </div>
        <div id="jChart" class="chart" data-submit="${subjStats.submitRate}" data-delay="${subjStats.delayRate}" data-undone="${subjStats.undoneRate}"></div>
        <ul>
            <li class="stuhomework">未完成的作业：<span>${submitNum}</span>份</li>
            <li class="stuhomework">待订正的作业：<span>${bugFixNum}</span>份</li>
            <li class="workgrade">
                <a href="${initParam.homeworkServerName}/auth/m/parent/homework/manager/detail.htm?subjectId=${subjectId}&studentId=${studentId}">查看作业成绩<i class="jump"></i></a>
            </li>
        </ul>
    </section>
    <script src="${assets}/scripts/common/mobile/zepto.min.js?_t=20171115"></script>
    <script src="/scripts/m/diag/common/echarts.simple.min.js?_t=20171115"></script>
    <script src="/scripts/m/diag/homework/manager/parent.js?_tt=20171115"></script>
</body>
</html>