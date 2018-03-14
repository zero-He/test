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
					<label>区域：${areaName}</label>
					<input type="hidden" name="areasStr" value='${areasStr}'/>
					<input type="hidden" name="areaId" value="${areaId}"/>
				</div>
				<div class="table">
					<table class="tab">
						<thead>
							<tr>
								<th width="40%">学校名称</th>
								<th width="30%">学校编号</th>
								<th width="30%">入驻时间</th>
							</tr>
						</thead>
						<tbody id="jSchoolListTbody">
							
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


<textarea id="jSchoolListTpl" class="f-dn">
	{{#dataList}} 
		<tr> 
			<td>{{name}}</td> 
			<td>{{code}}</td>
			<td>{{createdDate}}</td> 
		</tr> 
	{{/dataList}}
</textarea>

<script>
	seajs.use("monitor/school/schoolListByArea");
</script>

</body>
</html>