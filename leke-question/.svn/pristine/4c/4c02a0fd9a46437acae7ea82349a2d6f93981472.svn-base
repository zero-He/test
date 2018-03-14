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
				<li><a href="${ctx}/auth/admin/user/setCheckRange.htm">设置审核员</a></li>
				<li class="active"><a href="javascript:void(0);">设置教研员</a></li>
			</ul>
			<div class="m-search-box">
			<label class="title">姓名:</label><input type="text" class="u-ipt u-ipt-nm" id="userName">
			<label class="title">学段:</label><select id="schoolStageId" class="u-select u-select-nm">
					<option value="0">请选择</option>
					<c:forEach items="${schoolStageList}" var="schoolStage">
						<option value="${schoolStage.schoolStageId}">${schoolStage.schoolStageName}</option>
					</c:forEach>
				</select>
			<label  class="title">科目:</label><select id="subjectId" class="u-select u-select-nm">
					<option value="0">请选择</option>
					<c:forEach items="${subjectList}" var="subject">
						<option value="${subject.subjectId}">${subject.subjectName}</option>
					</c:forEach>
				</select>
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
		 	{{#schoolStageSubjects}}
		 		{{schoolStageName}}{{subjectName}}<br/>
		 	{{/schoolStageSubjects}}
		 </td>
		 <td class="operation "> 
		 	<a href="${ctx }/auth/admin/user/setSchoolStageSubject.htm?id={{id}}" class="link">设置学段科目</a> 
		 </td> 
	</tr> 
	{{/userList}}
	
</textarea>
</form>


<script type="text/javascript">
	seajs.use('question/user/setResearcherRange');
</script>
</body>
</html>