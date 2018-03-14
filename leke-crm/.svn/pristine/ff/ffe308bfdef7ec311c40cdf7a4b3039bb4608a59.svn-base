<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>客户管理 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/moveinto/moveinto.css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn z-gr-moveinto-customer">
		<div class="m-tab">
			<ul>
				<li><a href="${ctx}/auth/scs/customer/schoolList.htm" >机构学校</a></li>
				<li><a href="javascript:;" class="active">个人学校</a></li>
			</ul>
		</div>
			<form id="jPageForm" action="" method="post" class="m-search-box">
				<div class="item">
                    <label class="title">来源：</label>
					<select name="origin" class="u-select u-select-nm">
						<option value="0">全部</option>
						<option value="1">申请</option>
						<option value="2">注册</option>
					</select>
					<label class="title">姓名：</label>
					<input type="text" name="customerName" class="u-ipt u-ipt-sm">
					<label class="title">乐号：</label>
					<input type="text" name="loginName" class="u-ipt u-ipt-sm">
					<label class="title">手机：</label>
					<input type="text" name="phone" class="u-ipt u-ipt-sm">
	              </div>
					<div class="item">
		                  <label class="title">绑定时间：</label>
						<input type="text" id="jStartTime" name="startTime" class="u-ipt u-ipt-nm Wdate" style="width:100px;">
						-
						<input type="text" id="jEndTime" name="endTime" class="u-ipt u-ipt-nm Wdate" style="width:100px;">
						<input type="hidden" id="jPageOrder" name="order" value="">
						<input type="hidden" id="jPageSort" name="sort" value="">
						<input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise" id="jPageSearch" value="查询">
						<div class="operation">
							<a href="/auth/scs/customer/approveList.htm" class="u-btn u-btn-nm u-btn-bg-orange">待审核</a>
						</div>			
	              </div>
			</form>
			<div class="customer-manager">
				<div class="title">
					<div>客户管理</div>
					<div class="sort" id="jPageHead">
						<span class="m-sort-leke" data-column="balance">可用点数</span>
						<span class="m-sort-leke" data-column="total">总点数</span>
					</div>
				</div>
				<div id="jPageBody"></div>
				<div id="jPageFoot" class="page"></div>
			</div>
		</div>
	</div>
</body>
<script id="jPageTpl" type="text/handlebars">
{{#dataList}}
<table class="customer-item">
	<tr>
		<th colspan="2" width="50%">
			<span>姓名：<a target="_blank" class="customer-name" href="info.htm?customerId={{customerId}}">{{customerName}}</a></span>
			<span>乐号：{{lekeLoginName}}</span>
			<span>手机：{{phone}}</span>
		</th>
		<th width="50%">备注</th>
	</tr>
	<tr>
		<td>
			<p>绑定时间：{{fmt bindTime 'yyyy-MM-dd HH:mm'}}</p>
			<p>首次消费时间：{{fmt consumeTime 'yyyy-MM-dd HH:mm' '未消费'}}</p>
		</td>
		<td>
			<p><label for="">可用点数：</label>{{num balance '#,###' '0'}}</p>
			<p><label for="">总点数：</label>{{num total '#,###' '0'}}</p>
		</td>
		<td>
			<div class="remarks jRemarks">
				<div class="con">{{{remark}}}</div>
				<div class="m-tippop m-tippop-bt-left c-tippop">
					<div class="msg">{{{remark}}}</div>
					<div class="arrow"><em></em><i></i></div>
				</div> 
				<i class="iconfont edit j-edit" data-id="{{customerId}}" data-remark="{{remark}}">&#xf014f;</i>
			</div>
		</td>
	</tr>
</table>
{{/dataList}}
</script>
<leke:pref />
<script type="text/javascript"> 
	seajs.use('scs/customer/list');
</script>
</html>