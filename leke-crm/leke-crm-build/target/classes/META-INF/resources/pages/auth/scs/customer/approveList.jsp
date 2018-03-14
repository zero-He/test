<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>待审核列表 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/moveinto/moveinto.css?t=${_t}">
<link rel="stylesheet" href="${assets}/styles/pay/common/new.css?t=${_t}" type="text/css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn z-gr-moveinto-customer">
		<div class="m-tab">
			<ul>
				<li><a href="${ctx}/auth/scs/customer/schoolList.htm">机构学校</a></li>
				<li><a href="javascript:;"  class="active" >个人学校</a></li>
			</ul>
		</div>
		<div class="m-table">
				<table >
					<thead>
						<tr>
							<th width="20%">请求绑定时间</th>
							<th width="15%">个人学校名称</th>
							<th width="10%">个人老师</th>
							<th width="15%">乐号</th>
							
							<th width="10%">手机号</th>
							<th width="10%">状态</th>
							<th width="20%">操作区</th>
						</tr>
					</thead>
					<tbody id="j_bodyComments"></tbody>

					</tbody>
				</table>
				<div class="page" id="divPage"></div>
			</div>	
		</div>
	</div>

			
				

<script id="j_tempComments" type="x-mustache">
{{#dataList}}
			<tr>
				<td>{{createOnStr}}</td>
				<td>{{schoolName}}</td>
				<td>{{customerName}}</td>
				<td>{{lekeLoginName}}</td>
				<td>{{phone}}</td>
				<td>{{statusStr}}</td>
				<td class="operation"><a href="javascript:void(0);" class="confrim">确认绑定</a> <a href="javascript:void(0);" class="refuse" >拒绝绑定</a>
				<input type="hidden" name="customerId" value="{{customerId}}" />
				<input type="hidden" name="lekeId" value="{{lekeId}}" /></td> 
			</tr>
{{/dataList}}
	</script>
	<script type="text/javascript">
	seajs.use('scs/customer/approvelist');
	</script>
</body>


</html>