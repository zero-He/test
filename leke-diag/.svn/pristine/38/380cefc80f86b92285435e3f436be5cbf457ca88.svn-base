<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><locale:message code="diag.common.checking-in.name"/> - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/tutor/clazz/select.css?t=${_t}">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<c:if test ="${currentRoleId eq 102 }">
				<div>
					<ul id="jStudentList" class="m-tab">
						<c:forEach var="student" items="${studentList }" varStatus="status">
							<li data-stuid="${student.id}" <c:if test = "${status.index eq 0 }">class="active"</c:if> ><a>${student.userName}</a></li>
						</c:forEach>
					</ul>
					<c:if test ="${studentList.size() == 0 }">
						您还没有关联子女~
					</c:if>
				</div>
			</c:if>
			<form id="searchForm">
				<div class="m-search-box f-ov">
					<label class="title">课程名称：</label>
					<div class="z-select-pd-menu">
						<input type="text" value="<locale:message code="diag.common.text.allclass"/>" id="jClazzSelect" >
						<input type="hidden" value="" name="classId" id="jClazzId">
				        <div class="pd-con" style="display : none" id="jClazzOption">
				        	<ul>
			        		</ul>
				            <p><a href="#" class="en-pd-lt" id="jPre"></a><a href="#" class="pd-rt" id="jNext"></a></p>
				        </div>
				   </div>
					<label class="title"><locale:message code="diag.common.search.stlcycle"/>：</label>
					<input id="startTime" name="startTime" class="u-ipt u-ipt-nm Wdate" />
					—
					<input id="endTime" name="endTime" class="u-ipt u-ipt-nm Wdate" />
					<input type="hidden" id="jStudentId" name="studentId" value="${studentId }"/>
					<input type="hidden" id="jDomainName" value="${initParam.tutorServerName }"/>
					<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="<locale:message code="diag.common.search.query"/>">
				</div>
			</form>
			
			<div class="u-chart">
				<div id="attendance" style="height: 500px;"></div>
			</div>
			<textarea id="jClazz_tpl" style="display:none;">
				{{#dataList}}
					<li data-id="{{classId}}">{{className}}</li>
				{{/dataList}}
			</textarea>
		</div>
	</div>
	<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
	<script type="text/javascript">
		seajs.use('diag/student/stuAttendance');
	</script>
</body>
</html>