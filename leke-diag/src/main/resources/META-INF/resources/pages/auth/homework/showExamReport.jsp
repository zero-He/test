<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${assets}/styles/common/global.css">
    <link rel="stylesheet" href="${assets}/styles/homework/ts-pc.css">
    
    <title>成绩单</title>
    <%@ include file="/pages/common/meta.jsp"%>
  </head>
  <body>
  	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<c:if test="${currentRoleId == 102}">
		    <div id="childrenDiv" class="m-tab">
	          <ul id="ulbodyData">
	          </ul>
	        </div>
        </c:if>
        
		<div class="ts-tab-wrap">
			<c:if test="${currentRoleId == 104 || currentRoleId == 101 || currentRoleId == 103}">
		    	<button type="button" class="u-btn u-btn-nm u-btn-bg-orange f-mb20" id="createExamReport" name="createExamReport">创建</button>
		    </c:if>
		    <div class="m-table m-table-center" id="examReportDiv">
	            <table>
	              <thead>
	              	 <c:if test="${currentRoleId == 104}"> <!-- 教务 -->
						<tr>
							<th width="40%">标题 </th>
							<th width="20%">创建时间 </th>
							<th width="10%">创建人</th>
							<th width="10%">状态</th>
							<th width="20%">操作</th>
						</tr>
					 </c:if>	
	              	 <c:if test="${currentRoleId == 101 || currentRoleId == 103}"> <!-- 班主任，老师 -->
						<tr>
							<th width="50%">标题 </th>
							<th width="20%">创建时间 </th>
							<th width="10%">状态</th>
							<th width="20%">操作</th>
						</tr>
					 </c:if>	
	              	 <c:if test="${currentRoleId == 100 || currentRoleId == 102}"> <!-- 学生，家长 -->
						<tr>
							<th width="60%">标题 </th>
							<th width="20%">发布时间 </th>
							<th width="20%">操作</th>
						</tr>
					 </c:if>	
				  </thead>
	              <tbody id="jtbodyData">
				  </tbody>
	            </table>
            </div>
		<div id="jTablePage"></div>
		</div>
	</div>
</div>

<script id="chidrenTpl" type="x-mustache">
{{#childrenList}}
	<li id="stuLi" data-id="{{studentId}}"><a>{{studentName}}</a></li>
{{/childrenList}}
</script>

<script id="examReportListTpl" type="x-mustache">
{{#dataList}}
<tr>
	<td>{{examReportName}}</td>
	{{#createdOn}}<td>{{createdOn}}</td>{{/createdOn}}
	{{#modifiedOn}}<td>{{modifiedOn}}</td>{{/modifiedOn}}
	{{#createdUser}}<td>{{createdUser}}</td>{{/createdUser}}
	{{#status}}<td>{{status}}</td>{{/status}}
	{{#status}}
		<td class="operation f-tal f-pl50">
			<a target="_blank" class="jView" href="/auth/roleName/examReport/toShowExamReportDtl.htm?examReportId={{examReportId}}&isCreatedBySelf={{isCreatedBySelf}}&statusCd={{statusCd}}">查看</a>
			{{#isCreatedBySelf}}<a id="jDiscardExamReport" target="{{examReportId}}">作废</a>{{/isCreatedBySelf}}
	    </td>
	{{/status}}
	{{^status}}
		<td class="operation f-tal f-pl50">
			<a target="_blank" class="jView" href="/auth/roleName/examReport/toShowStuExamReport.htm?examReportId={{examReportId}}&studentId={{studentId}}">查看</a>
	    </td>
	{{/status}}
</tr>
{{/dataList}}
</script>

<script type="text/javascript">
	seajs.use('diag/homework/showExamReport');
</script>

</body>
</html>