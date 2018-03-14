<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>学科优劣分析 - 乐课网</title>
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
			<ul id="jULid" class="m-tab jTabClass">
				<c:forEach var="clazz" items="${clazzList }" varStatus="status">
					<li data-id="${clazz.classId }" <c:if test="${status.index eq 0 }">class="active"</c:if>  >
						<a>${clazz.className }</a>
					</li>
				</c:forEach>
			</ul>
			<c:if test = "${ clazzList.size() eq 0 }">
				你还没有管辖的班级~
			</c:if>
		</div>
		<form id="jsearchForm" class="m-search-box">
			<div class="f-fl">
				<input type="hidden" id ="jClassId" />
			</div>
			<div class="f-fr">
				<input type ="hidden" name="startDate" value="${startDate }" />
				<label class="title">时间：${startDate } ~ ${finishDate} </label>
			</div>
		</form>
		<div id="stuAnalysisList" class="m-table">
			<table>
				<thead>
					<tr>
						<th width="65%">学生姓名</th>
						<th width="35%">操作</th>
					</tr>
				</thead>
				<tbody id="jstuAnalysisListBody"></tbody>
			</table>
			<div class="m-tips" style="display: none;" id="jEmptyData"><i></i><span>对不起，没有您要查询的数据</span></div>
		</div>
	</c:if>
	</div>
</div>
<c:if test="${not empty clazzList}">
<textarea id="jstuAnalysisList_tpl" style="display:none;">
{{#.}}
	<tr>
		<td>{{userName}}</td>
		<td class="operation">
			<a class="jDetails" data-href="${ctx }/auth/classTeacher/swot/stuSubjectAnalysis.htm?stuId={{id}}"
			 target="_blank">查看详情</a>
		</td>
	</tr>
{{/.}}
</textarea>
<script type="text/javascript">
	seajs.use('diag/classTeacher/stuAnalysis');
</script>
</c:if>
</body>
</html>