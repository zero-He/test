<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>入驻学校统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" href="${assets}/styles/monitor/monitor.css" type="text/css">
</head>

<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd"> 
<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
			<form action="" method="post" id="jFormPage">
				<div class="m-search-box">
					<label>入驻时间：${timeStr}</label>
					<input type="hidden" name="startTime" value="${startTime}"/>
					<input type="hidden" name="endTime" value="${endTime}"/>
					<input type="hidden" name="schoolIdsStr" value="${schoolIdsStr}"/>
					<input type="hidden" name="roleId" value="${roleId}"/>
				</div>
				<div class="table">
					<table class="tab">
						<thead>
							<tr>
								<th width="40%"><c:if test="${roleId==100}">学生姓名</c:if><c:if test="${roleId==101}">教师姓名</c:if></th>
								<th width="30%">所属学校</th>
								<th width="30%">入驻时间</th>
							</tr>
						</thead>
						<tbody id="jSuListTbody">
							
						</tbody>
					</table>
					<tfoot>
						<tr>
							<td colspan="3">
								<div class="page" id="jDivPage"><ul></ul></div>
							</td>
						</tr>
					</tfoot>
				</div>
			</form>
		</div>
	</div>
</div>


<textarea id="jSuListTpl" class="f-dn">
	{{#dataList}} 
		<tr> 
			<td>{{userName}}</td> 
			<td>
				{{#schoolList}}
					{{name}} 
				{{/schoolList}}
			</td>
			<td>{{createdDate}}</td> 
		</tr> 
	{{/dataList}}
</textarea>

<script>
	seajs.use("monitor/school/schoolUserListByTime");
</script>

</body>
</html>