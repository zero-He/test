<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${subject.subjectName}作业</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" href="${assets}/styles/mobile/global.css?_t=20171115">
<link rel="stylesheet" href="${assets}/styles/mobile/homework/homeworkphone.css?_t=${_ts}">
<leke:context />
</head>
<body>
<div id="app">
<div class="m-init"><i></i></div>
</div>
<script type="text/javascript">window.Csts = ${csts}</script>
<script type="text/javascript">
window.Links = {
	work: Leke.ctx + '/auth/m/student/homework/dowork.htm?_newtab=1&homeworkDtlId={{homeworkDtlId}}',
	bugfix: Leke.ctx + '/auth/m/student/homework/bugfix.htm?_newtab=1&homeworkDtlId={{homeworkDtlId}}',
	view: Leke.ctx + '/auth/m/person/homework/viewwork.htm?_newtab=1&homeworkDtlId={{homeworkDtlId}}',
	learning: '/auth/m/student/homework/learning.htm?learnType=0&_newtab=1&homeworkDtlId={{homeworkDtlId}}',
	review: '/auth/m/student/homework/learning.htm?learnType=1&_newtab=1&homeworkDtlId={{homeworkDtlId}}',
	analyse: Leke.domain.diagServerName + '/auth/m/report/homework/person/{{homeworkId}}-{{homeworkDtlId}}.htm?_newtab=1'
};
</script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/zepto.min.js?_t=20171115"></script>
<script type="text/javascript" src="${ctx}/scripts/m/homework/person/person-worklist.webapi.js?_t=${_ts}"></script>
<script type="text/javascript" src="${ctx}/scripts/m/homework/person/person-worklist.bundle.js?_t=${_ts}"></script>
</body>
</html>
