<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<title>班级成绩分析 - 乐课网</title>
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
		<ul class="m-tab jtabClass">
			<c:forEach var="clazz" items="${clazzList }" varStatus="status">
				<li>
					<a <c:if test="${status.index eq 0 }">class="active"</c:if>
						data-gradeid="${clazz.gradeId }" data-classid="${clazz.classId }">
						${clazz.className }
					</a>
				</li>
			</c:forEach>
		</ul>
		<form id="searchForm" class="m-search-box">
			<div class="f-fl">
			</div>
			<div class="f-fr">
				<label class="title">时间：${startDate } ~ ${finishDate }</label>
				<input type="hidden" id="jGradeId" />
				<input type="hidden" id="jClassId" />
			</div>
		</form>
		<div class="m-table">
			<table>
				<thead>
					<tr>
						<th>学科</th>
						<th>统计人数</th>
						<th>平均分</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="jBody"></tbody>
			</table>
			<div id="divPage" class="m-page" >
				<div class="m-tips" style="display: none;" id="jEmptyData"><i></i><span>对不起，没有您要查询的数据</span></div>
			</div>
		</div>
	</c:if>
	</div>
</div>
<c:if test="${not empty clazzList}">
	<script id="jTemplate" type="text/handlebars">
	{{#.}}
		<tr>
			<td>{{subjectName}}</td>
			<td>{{statNum}}</td>
			<td>{{num avgScore '#0.#' '--'}}</td>
			<td class="operation">
				<a class="jdetails" target="_blank" data-subjectid="{{subjectId}}" data-subjectname="{{subjectName}}" >查看详情</a>
			</td>
		</tr>
	{{/.}}
</script>
<script type="text/javascript">
	seajs.use('diag/classTeacher/achievement');
</script>
</c:if>
</body>
</html>