<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>课点管理 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<form id="jPageForm" action="" method="post" class="m-search-box">
				<label class="title">客户经理：</label>
				<input type="text" name="sellerName" class="u-ipt u-ipt-nm">
				<label class="title">姓名：</label>
				<input type="text" name="ownerName" class="u-ipt u-ipt-nm">
				<label class="title">乐号：</label>
				<input type="text" name="loginName" class="u-ipt u-ipt-nm">
				<input type="hidden" id="jPageOrder" name="order" value="" />
				<input type="hidden" id="jPageSort" name="sort" value="" />
				<input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise" id="jPageSearch" value="查询">
				<div class="operation">
					<a class="u-btn u-btn-auto u-btn-bg-orange" href="${initParam.payServerName}/auth/platformFinance/account/pointprice.htm">课点单价设置</a>
					<a class="u-btn u-btn-auto u-btn-bg-orange" href="bestowal.htm">赠送点设置</a>
				</div>
			</form>
			<div class="m-table m-table-center">
				<table>
					<thead id="jPageHead">
						<tr>
							<th>姓名</th>
							<th>乐号</th>
							<th>联系电话</th>
							<th class="m-sort-leke" data-column="total">总点数</th>
							<th class="m-sort-leke" data-column="usable">可用点数</th>
							<th class="m-sort-leke" data-column="bestowal">赠送点数</th>
							<th>客户经理</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="jPageBody"></tbody>
				</table>
				<div id="jPageFoot" class="page"></div>
			</div>
		</div>
	</div>
</body>
<script id="jPageTpl" type="text/handlebars">
{{#dataList}}
<tr>
	<td>{{ownerName}}</td>
	<td>{{loginName}}</td>
	<td>{{phone}}</td>
	<td>{{total}}</td>
	<td>{{usable}}</td>
	<td>{{bestowal}}</td>
	<td>{{sellerName}}</td>
	<td class="operation">
		<a href="${initParam.payServerName}/auth/platformFinance/account/point.htm?accountId={{accountId}}">详情</a>
		<a href="${initParam.payServerName}/auth/platformFinance/account/bestowal.htm?accountId={{accountId}}">赠送</a>
	</td>
</tr>
{{/dataList}}
</script>
<leke:pref />
<script type="text/javascript">
	seajs.use('scs/account/pointlist');
</script>
<c:if test="${not empty message }">
	<script type="text/javascript">
		seajs.use('utils', function(Utils) {
			Utils.Notice.alert("${message}");
		});
	</script>
</c:if>
</html>