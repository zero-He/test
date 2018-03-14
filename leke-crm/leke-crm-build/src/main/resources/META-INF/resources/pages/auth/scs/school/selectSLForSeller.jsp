<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>选择学校 - 乐课网</title>

<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/pay/common/layout.css?t=${_t}">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-sd">
			<%@ include file="/pages/navigate/navigate.jsp"%>
		</div>
		<!-- 4、客户经理的学校订单 -->
		
		<div class="g-mn">
			<c:if test="${type == 4}">
				<div class="m-tab">
					<ul>
						<li><a href="${initParam.payServerName}/auth/common/order/orderListForUser.htm">我的订单</a></li>
						<li><a class="active" >学校订单</a></li>
					</ul>
				</div>
			</c:if>
			<form id="jPageForm" class="m-search-box">
				<div class="m-box-title j-search-box">
<!-- 				urlType: 1课程发布、2排课、3上课名单  4客户经理的学校订单-->
					<input type="hidden" id="urlType" value='${type}'>
					<input type="hidden" id="tutorServerName" value="${initParam.tutorServerName}">
					<input type="hidden" id="payServerName" value="${initParam.payServerName}">
					<input type="hidden" id="schoolId" value="">
					
					<span class="name">学校名称：</span>
					<input type="text" placeholder="请输入学校名称" id="schoolName" name="customerName" class="u-ipt u-ipt-lg f-mr15 j-school-select" style="width:240px">
					<input type="button" id="btn_search" class="u-btn u-btn-nm u-btn-bg-turquoise" value="查询">
					
					<span class="tips">若输入学校名称后，输入框下没有响应结果，说明你还没有关联该学校。</span>
					<a href="${ctx}/auth/scs/customer/addSchoolCustomer.htm" type="button"  class="related-school-btn">关联学校</a>
					
					
				</div>
				
			</form>
			
			<div class="m-table">
				<table >
					<thead>
						<tr>
							<th width="40%">学校名称</th>
							<th width="20%">学校编码</th>
							<th width="20%">学校类型</th>
							<th width="20%">操作</th>
						</tr>
					</thead>
					<tbody id="tbodySh">

					</tbody>
				</table>
				<div class="page" id="divPage"></div>
			</div>
		</div>
	</div>
	
	<textarea id="schoolList" style="display: none">
		{{#dataList}}
			<tr id="schoolCustomer{{customerId}}">
   				<td>{{customerName}}</td>
   				<td>{{lekeLoginName}}</td>
   				<td>行政学校</td> <!-- 个人学校未查询，暂时只有行政学校 -->
   				<td class="operation">
   					{{{operate}}}
   				</td>
			</tr>
		{{/dataList}}
	</textarea>
	
	<script type="text/javascript">
		seajs.use('scs/school/selectSLForSeller');
	</script>
</body>

</html>