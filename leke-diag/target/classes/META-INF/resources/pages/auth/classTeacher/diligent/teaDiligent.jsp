<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<title>作业勤奋报告 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
	<c:if test="${empty clazzList}">
		<div class="m-tips"><i></i><span>您暂无行政班级，此处仅显示行政班级数据！</span></div>
	</c:if>
	<c:if test="${not empty clazzList}">
		<div>
			<ul class="m-tab jtabClass">
				<c:forEach var="clazz" items="${clazzList }" varStatus="status">
					<li>
						<a <c:if test="${status.index eq 0 }">class="active"</c:if>
							 data-classid="${clazz.classId }">
							${clazz.className }
						</a>
					</li>
				</c:forEach>
			</ul>
			<c:if test = "${ clazzList.size() eq 0 }">
				你还没有管辖的班级~
			</c:if>
		</div>
		<form id="jSearchForm">
		<div class="m-search-box">
			<label class="title">学生姓名：</label>
			<input type="text" class="u-ipt u-ipt-sm" id="jStudentName" name="studentName" />
			<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
			<div class="operation">
				<input type="hidden" name="startDate" value="${startDate }"  /> 
				<input type="hidden" name="finishDate" value="${finishDate }" />
				<span> 时间：${startDate } ~  ${finishDate }</span>
			</div>
		</div>
		</form>
				<div class="m-table">
			<table >
				<thead>
					<tr>
						<th>学生姓名</th>
						<th>作业数</th>
						<th>提交率</th>
						<th>延迟率</th>
						<th>未交率</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="jtbodyData">

				</tbody>
			</table>
			<div class="page" id="jPageFoot">
				<ul></ul>
			</div>
			<div class="m-tips" style="display: none;" id="jEmptyData"><i></i><span>对不起，没有您要查询的数据</span></div>
			<input type="hidden" id="jDataList" />
			<input type="hidden" id="jClassId" />
		</div>
	</c:if>
	</div>

<c:if test="${not empty clazzList}">
	<script id="jStudentsTemplate" type="text/handlebars">
		{{#.}}
			<tr>
				<td>{{userName}}</td>
				<td>{{statNum}}</td>
				<td>{{num submitRate '#0.##%' '--'}}</td>
				<td>{{num delayRate '#0.##%' '--'}}</td>
				<td>{{num undoneRate '#0.##%' '--'}}</td>
				<td class="operation">
					<a class="jDetailsStudent" target="_blank"  
						data-href="${ctx}/auth/common/stuHomeworkInfo.htm?stuId={{userId}}">查看详情</a>
				</td>
			</tr>
		{{/.}}
	</script>

</div>
<script type="text/javascript">
	seajs.use('diag/classTeacher/teaDiligent');
</script>
</c:if>
</body>
</html>