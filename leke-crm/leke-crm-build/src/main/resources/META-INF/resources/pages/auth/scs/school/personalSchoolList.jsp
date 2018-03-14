<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>个人学校管理 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="m-tab">
				<ul>
					<li><a href="${initParam.tutorServerName}/auth/user/school/schoolList.htm">机构学校</a></li>
					<li><a class="active" >个人学校</a></li>
				</ul>
			</div>			
			<form id="jPageForm" action="" method="post" class="m-search-box">
				<div class="item">
					<select class="u-select u-select-mn" id="jNameOrPhoneSelect" name="">
						<option value="loginName">乐号</option>
						<option value="phone">手机</option>
					</select> 
					<input type="text" class="u-ipt u-ipt-nm" id="jNameOrPhone" name="loginName"> 
					<label class="title" for="">来源：</label>
					<select class="u-select u-select-nm" name="origin" id="jOrigin">
						<option value="">全部</option>
						<option value="2">注册</option>
						<option value="1">学校教师申请</option>
					</select> 
					<label class="title" for="">创建日期：</label>
					<input type="text" class="u-ipt u-ipt-nm Wdate" name="createdOn" id="jCreatedOn">至 
					<input type="text" class="u-ipt u-ipt-nm Wdate" name="createdOnR" id="jCreatedOnR">
				</div>
				<div class="item">
					<label class="title" for="">有无客户经理：</label> 
					<select class="u-select u-select-mn" name="hasSeller" id="jHasSeller">
						<option value="">所有</option>
						<option value="1">有</option>
						<option value="0">无</option>
						<option value="2">待审核</option> 
					</select> 
					<input type="submit" id="jPageSearch" value="查询" class="u-btn u-btn-nm u-btn-bg-turquoise"> 
					<div class="operation">
						<input type="button" id="jExport" value="导出" class="u-btn u-btn-nm u-btn-bg-orange">
					</div>
				</div>
			</form>
			<div class="m-table m-table-center">
				<table>
					<thead>
						<tr>
							<th>个人学校</th>
							<th>个人教师</th>
							<th>乐号</th>
							<th>手机</th>
							<th>客户经理</th>
							<th>创建时间</th>
							<th>操作区</th>
						</tr>
					</thead>
					<tbody id="jPageBody">
					</tbody>
				</table>
				<div id="jPageFoot" class="page"></div>
			</div>
		</div>
	</div>
	<iframe id="jFrames" name="jFrames" style="display: none"></iframe>
	<textarea id="jPageTpl" class="f-dn">
		{{#dataList}}
			<tr>
	    		<td>{{schoolName}}</td>
	    		<td>{{userName}}</td>
	    		<td>{{loginName}}</td>
				<td>{{phone}}</td>
	    		<td>{{sellerName}}</td>
	    		<td>{{createdOnStr}}</td>
	    		<td class="operation"><a href="${initParam.indexServerName}/auth/portal/manager/template/addSchoolTemplate.htm?schoolId={{schoolId}}">自定义模版</a>
	    		<a href="${initParam.userServerName}/auth/platformAdmin/unitSchool/edit.htm?schoolId={{schoolId}}">修改</a></td>
			</tr>
		{{/dataList}}
	</textarea>
	<script type="text/javascript">
		seajs.use('scs/school/personalSchoolList');
	</script>
</body>
</html>