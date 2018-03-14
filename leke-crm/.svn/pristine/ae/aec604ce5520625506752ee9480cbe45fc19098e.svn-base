<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>客户管理 - 乐课网</title>
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
				<li><a href="javascript:;" class="active" >机构学校</a></li>
				<li><a href="${ctx}/auth/scs/customer/list.htm" >个人学校</a></li>
			</ul>
		</div>
		<div class="detail">
			<form id="jPageForm" action="" method="post" class="m-search-box">
				<div class="item">
                  
					
					<span class="name">学校名称：</span>
					<input type="text" id="schoolName" name="customerName" class="u-ipt u-ipt-lg f-mr15 ">
					<span class="name">学校编码：</span>
					<input type="text" name="code" class="u-ipt u-ipt-lg f-mr15">
					
					<input type="button" id="btn_search" class="u-btn u-btn-nm u-btn-bg-turquoise" value="查询">
					
					<div class="operation">
						<a href="${ctx}/auth/scs/customer/addSchoolCustomer.htm" class="u-btn u-btn-nm u-btn-bg-orange">关联新学校</a> 
						<!-- <a href="javascript:void(0);" class="u-btn u-btn-nm u-btn-bg-orange" id="relaSchool">关联新学校</a> -->
					</div>
					
	            </div>
			</form>
		</div>
		<div class="m-table">
				<table >
					<thead>
						<tr>
							<th width="20%">学校名称</th>
							<th width="10%">学校编码</th>
							<th width="15%">学校阶段</th>
							<th width="15%">注册邮箱</th>
							
							<th width="10%">关联</th>
							<th width="20%">乐课账户</th>
							<th width="10%">入账统计</th>
						</tr>
					</thead>
					<tbody id="tbodySh">

					</tbody>
				</table>
				<div class="page" id="divPage"></div>
			</div>
				
			<input type="hidden" id="currentpage" name="pv.currentpage" value="${pv.currentpage}" />
			<input type="hidden" id="totalpage" name="pv.totalpage" value="${pv.totalpage}" />
		</div>
	</div>

			
				
	<textarea id="schoolList" style="display: none">
		{{#dataList}}
			<tr id="schoolCustomer{{customerId}}">
   				<td>{{customerName}}</td>
   				<td>{{lekeLoginName}}</td>
   				<td>{{schoolStageNames}}</td>
   				<td>{{schoolEmail}}</td>
   				
   				<td class="operation">
   				{{{operate}}}
 					<!-- <a class="" data-id="{{lekeId}}">上课名单</a>
 					<a class="" data-id="{{lekeId}}">线上订单</a> -->
   				</td>
   				<td class="operation">{{{_statusStr}}}</td>
   				<td class="operation">{{{statusStrs}}}</td>
   				
			</tr>
		{{/dataList}}
	</textarea>
	<script id="presenterTemp" type="x-mustache">
	
	<div class="f-mt30 f-ml50 ">
        <p class="f-pl40"><lable> </lable> 学校：{{schoolName}}</p>
        <div class="f-mt25 f-pl40"><lable style="color:red"> * </lable>金额：
            <input class="u-ipt u-ipt-lg" id="amount" placeholder="请输入非0整数" type="text">元
	
</div>
        <div class="f-mt25 f-pl40">
            <input value="线下支付" class="u-btn u-btn-nm u-btn-bg-turquoise f-mr50" type="button" data-id="{{schoolId}}" data-name="{{schoolName}}">
            <input value="直接支付" class="u-btn u-btn-nm u-btn-bg-turquoise f-mr50 f-ml10" type="button" data-id="{{schoolId}}">
            <input value="取消" class="u-btn u-btn-nm u-btn-bg-gray" type="button" >
        </div>
        <p class="f-mt15 s-c-gray-9 f-ml20"><span class="f-mr15"> 线下转账给财务账号</span> </p>
    </div>
	</script>
	<script type="text/javascript">
	seajs.use('scs/customer/schoolList');
	</script>
</body>


</html>