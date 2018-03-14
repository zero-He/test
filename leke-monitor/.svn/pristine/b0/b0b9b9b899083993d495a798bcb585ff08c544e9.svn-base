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
					<input type="hidden" id="areas" name="areasStr" value='${areas}'/>
					<label>学校：</label>
					<select class="u-select u-select-nm" id="jSchoolId" name="schoolId">
						<option value="">全部</option>
						<c:forEach items="${schoolList}" var="schoolId">
							<option value="${schoolId.id}">${schoolId.name}</option>
						</c:forEach>
					</select>
					<label>入驻时间：</label>
					<input type="text" id="jStartTime" name="startTime" class="u-ipt u-ipt-sm Wdate"><label> - </label>
				    <input type="text" id="jEndTime" name="endTime" class="u-ipt u-ipt-sm Wdate">
					<input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button" value="查询" id="jSsListSelect" />
					
				</div>
				
				<div class="table">
					<table class="tab">
						<thead>
							<tr>
								<th width="20%">学校名称</th>
								<th width="20%">学校编号</th>
								<th width="20%"><span class="m-sort" id="jTchSort">老师数量  <i class="m-sort-default"></i></span></th>
								<th width="20%"><span class="m-sort" id="jStuSort">学生数量  <i class="m-sort-default"></i></span></th>
								<th width="20%">入驻时间</th>
							</tr>
						</thead>
						<tbody id="jSsListTbody">
							
						</tbody>
						<input type="hidden" id="jSort" name="nSort"/>
					</table>
					<tfoot>
						<tr>
							<td colspan="5">
								<div class="page" id="jDivPage"><ul></ul></div>
							</td>
						</tr>
					</tfoot>
				</div>
			</form>
		</div>
	</div>
</div>

<textarea id="jSsListTpl" class="f-dn">
	{{#dataList}} 
		<tr> 
			<td>{{schoolName}}</td> 
			<td>{{schoolCode}}</td>
			<td>{{tchCount}}</td> 
			<td>{{stuCount}}</td> 
			<td>{{createdOnStr}}</td> 
		</tr> 
	{{/dataList}}
</textarea>

<script>
	seajs.use("monitor/school/schoolStatisListForEdu");
</script>

</body>
</html>