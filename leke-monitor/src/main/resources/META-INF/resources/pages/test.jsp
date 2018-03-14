<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试页面</title>
<%@ include file="/pages/common/meta.jsp" %>
<style type="text/css">
dl { font-size: 20px; line-height: 28px; }
dt { float: left; width: 100px; }
dd { overflow: hidden; }
dd a {color: blue;text-decoration: underline;}
dd a:visited {color: purple;}
</style>
</head>
<body>

<dl>
	<dt>运维人员</dt>
	<dd>
		<ul>
			<li><a target="_blank" href="${ctx}/auth/operationStaff/domain/index.htm">域名管理</a></li>
			<li><a target="_blank" href="${ctx}/auth/operationStaff/access/daily.htm">访问统计</a></li>
			<li><a target="_blank" href="${ctx}/auth/operationStaff/sql/daily.htm">SQL统计</a></li>
			<li><a target="_blank" href="${ctx}/auth/operationStaff/exception/stat.htm">系统异常</a></li>
			<li><a target="_blank" href="${ctx}/auth/operationStaff/mle/stat.htm">消息异常</a></li>
			<li><a target="_blank" href="${ctx}/auth/operationStaff/jobex/stat.htm">任务异常</a></li>
		</ul>
	</dd>
	<dt>技术支持</dt>
	<dd>
		<ul>
			<li><a target="_blank" href="${ctx}/auth/technicalSupport/course/technicalIndex.htm">个人首页</a></li>
			<li><a target="_blank" href="${ctx}/auth/technicalSupport/course/courseTablePage.htm">平台课表</a></li>
			<li><a target="_blank" href="${ctx}/auth/technicalSupport/course/clazzroomView.htm">课堂用户监控</a></li>
			<li><a target="_blank" href="${ctx}/auth/technicalSupport/online/userListByOnline.htm">在线用户监控</a></li>
			<li><a target="_blank" href="${ctx}/auth/technicalSupport/course/classPeopleTotal.htm">上课人数统计</a></li>
			<li><a target="_blank" href="${ctx}/auth/technicalSupport/course/courseUseTotal.htm">课堂使用统计</a></li>
			<li><a target="_blank" href="${ctx}/auth/technicalSupport/course/schoolStatisForChart.htm">入驻学校统计</a></li>
			<li><a target="_blank" href="${ctx}/auth/technicalSupport/course/historyCourse.htm">历史数据统计</a></li>
		</ul>
	</dd>
	<dt>客户经理</dt>
	<dd>
		<ul>
			<li><a target="_blank" href="${ctx}/auth/seller/course/courseTablePage.htm">监控课堂</a></li>
		</ul>
	</dd>
	<dt>校长</dt>
	<dd>
		<ul>
			<li><a target="_blank" href="${ctx}/auth/president/course/clazzroomView.htm">课堂用户监控</a></li>
			<li><a target="_blank" href="${ctx}/auth/president/course/classPeopleTotal.htm">上课人数统计</a></li>
		</ul>
	</dd>
	<dt>教育局长</dt>
	<dd>
		<ul>
			<li><a target="_blank" href="${ctx}/auth/educationDirector/course/clazzroomView.htm">课堂用户监控</a></li>
			<li><a target="_blank" href="${ctx}/auth/educationDirector/course/classPeopleTotal.htm">上课人数统计</a></li>
			<li><a target="_blank" href="${ctx}/auth/educationDirector/school/schoolStatisForEduChart.htm">入驻学校统计</a></li>
		</ul>
	</dd>
</dl>
</body>
</html>