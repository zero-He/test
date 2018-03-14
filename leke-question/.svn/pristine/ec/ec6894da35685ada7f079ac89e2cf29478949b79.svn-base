<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>权限设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
</head>
<body>
<form>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
			<ul class="m-tab">
				<li class="active"><a href="javascript:void(0);">设置审核员</a></li>
				<li><a href="${ctx}/auth/admin/user/setResearcherRange.htm">设置教研员</a></li>
			</ul>
			<div class="m-search-box">
				<label class="title">姓名：</label>
				<input type="text" class="u-ipt u-ipt-nm" id="userName">
				
				<label class="title">年级科目：</label>
				<select id="gradeSubject" class="u-select u-select-nm"></select>
				<input id="gradeId" name="gradeId" type="hidden"/>
				<input id="subjectId" name="subjectId" type="hidden"/>
				
   				<input class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询" id="bSearch">
   			</div>
   			<div class="base_view1">
				<div class="m-table">
					<table>
						<thead>
							<tr>
							<th width="20%">乐号</th>
							<th width="10%">姓名</th>
							<th width="20%">角色</th>
							<th width="30%">年级科目</th>
							<th width="20%">操作区</th>
						</tr>
						</thead>
						<tbody id="listContext">
							
						</tbody>
						<tfoot>
							<tr>
								<td colspan="8">
									<div class="page" id="divPage">
										<ul></ul>
									</div>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<input type="hidden" id="totalpage" name="pvUser.totalpage" value="${pvUser.totalpage}" />
<input type="hidden" id="currentpage" name="pvUser.currentpage" value="${pvUser.currentpage}" />
<textarea id="listContext_tpl" style="display:none;">
	{{#userList}} 
	<tr> 
		 <td>{{loginName}}</td> 
		 <td>{{userName}}</td> 
		 <td > 
		 	{{#roleList}} 
		 		{{name}}<br/>
		 	{{/roleList}} 
		 </td> 
		 <td>
		 	{{#gradeSubjects}}
		 		{{gradeName}}{{subjectName}}<br/>
		 	{{/gradeSubjects}}
		 </td>
		 <td class="operation"> 
		 	<a href="${ctx }/auth/admin/user/setGradeSubject.htm?id={{id}}" class="link">设置年级科目</a> 
		 </td> 
	</tr> 
	{{/userList}}
	
</textarea>
</form>


<script type="text/javascript">
	seajs.use('question/user/setCheckRange');
</script>
</body>
</html>