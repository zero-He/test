<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>客户经理首页 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/crm/ruzhu.css">

</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn z-personal-mn">
			<div class="z-account-manager">
				<p class="welcome">
					<span class="p1">Hello!</span>
					<span class="p2">${userName }</span>
					客户经理，欢迎使用乐课网。
				</p>
				<ul class="manager-list f-clearfix">
					<li class="manager-li f-clearfix f-fl teacher">
						<div class="f-fl">
							<p class="tit">客户管理</p>
							<p class="num">
								总客户数:
								<span class="p1"><a href="${ctx}/auth/scs/customer/list.htm">${seller.customerNum}</a></span>
							</p>
						</div>
						<div class="logo f-fr"></div>
					</li>
					<li class="manager-li f-clearfix f-fl">
						<div class="f-fl">
							<p class="tit">提成管理</p>
							<p class="num">
								本月提成:
								<span class="p1">
									<fmt:formatNumber value="${seller.monthAmount}" pattern="#,##0.00" />
									元
								</span>
							</p>
						</div>
						<div class="logo f-fr"></div>
					</li>
					<li class="manager-li f-clearfix f-fl f-mr0">
						<div class="f-fl">
							<p class="tit">提成管理</p>
							<p class="num">
								历史提成:
								<span class="p1">
									<fmt:formatNumber value="${seller.totalAmount}" pattern="#,##0.00" />
									元
								</span>
							</p>
						</div>
						<div class="logo f-fr"></div>
					</li>
				</ul>
			</div>
			<form id="jPageForm" action="" method="post" class="m-search-box f-mt30">
				<label class="title">查询月份：</label>
				<input type="text" id="jMonth" name="month" class="u-ipt u-ipt-nm Wdate" value="${month}">
				<label class="title">教师姓名：</label>
				<input type="text" name="customerName" class="u-ipt u-ipt-nm">
				<input type="hidden" id="jPageOrder" name="order" value="" />
				<input type="hidden" id="jPageSort" name="sort" value="" />
				<input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise" id="jPageSearch" value="查询">
			</form>
			<div class="m-table m-table-center">
				<table>
					<thead id="jPageHead">
						<tr>
							<th>教师姓名</th>
							<th>绑定时长</th>
							<th>充值金额(元)</th>
							<th class="m-sort-leke" data-column="commAmount">提成金额(元)</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="jPageBody"></tbody>
				</table>
				<div style="position: relative; min-height: 50px;">
					<div id="jPageFoot" class="page"></div>
					<div style="position: absolute; bottom: 0; padding: 5px;">提成总额：<span id="jPageTotal"></span> 元</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script id="jPageTpl" type="text/handlebars">
{{#dataList}}
<tr>
	<td>{{customerName}}</td>
	<td>第{{bindYear}}年</td>
	<td>{{money tradeAmount}}</td>
	<td>{{money commAmount}}</td>
	<td class="operation">
		<a href="${ctx}/auth/scs/commission/recordlist.htm?customerId={{customerId}}&month={{../month}}" target="_blank">详情</a>
	</td>
</tr>
{{/dataList}}
</script>
<script type="text/javascript">
	seajs.use('scs/seller/index');
</script>
</html>