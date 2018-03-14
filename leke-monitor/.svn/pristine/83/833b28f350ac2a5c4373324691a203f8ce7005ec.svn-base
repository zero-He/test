<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>在线用户监控- 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/monitor/monitor.css" type="text/css">
</head>

<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd"> 
<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
			<form action="" method="post" id="jFormPage">
				<div class="m-search-box">
					<select id="numberType" name="numberType" class="u-select u-select-nm">
						<option value="0">乐号</option>
						<option value="1">账号</option>
					</select>
					<input type="text" id="loginName" name="loginName" class="u-ipt u-ipt-nm">
					<input type="text" id="oldLoginName" name="oldLoginName" class="u-ipt u-ipt-nm" style="display:none">
					<label for class="title">姓名：</label>
					<input type="text" id="userName" name="userName" class="u-ipt u-ipt-nm"/>
					<label for class="title">所属学校：</label>
					<input type="text" id="schoolName" name="schoolName" class="u-ipt u-ipt-nm"/>
					<input type="submit" id="btn_search" class="u-btn u-btn-nm u-btn-bg-turquoise" value="查询">
				</div>
				<div class="f-bfc f-pt10 f-pb10">
					<span class="f-fl">当前在线总用户数：<span class="s-c-turquoise" id="total"></span>人</span>
				</div>
				<div class="m-table">
					<table>
							<tr>
								<th width="20%">乐号(账号)</th>
								<th width="10%">姓名</th>
								<th width="20%">所属学校</th>
								<th width="10%">IP</th>
								<th width="15%">运营商</th>
								<th width="15%">浏览器(版本)</th>
								<th width="10%">操作系统</th>
							</tr>
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

<textarea id="jSuListTpl" class="f-dn">
	{{#dataList}} 
		<tr> 
			<td>{{loginName}}({{oldLoginName}})</td> 
			<td>{{userName}}</td>
			<td>{{schoolName}}</td>
			<td>{{ip}}</td> 
			<td>{{networkOperator}}</td> 
			<td>{{navigate}}({{version}})</td> 
			<td>{{os}}</td>  
		</tr> 
	{{/dataList}}
</textarea>

<script>
	seajs.use("monitor/pages/technicalSupport/online/userListByOnline");
</script>

</body>
</html>