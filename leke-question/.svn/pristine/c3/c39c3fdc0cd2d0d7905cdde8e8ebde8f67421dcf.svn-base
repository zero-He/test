<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>出版社设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/pressList.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>

	<div class="g-mn">
		<div class="detail">
			<div class="search align-right">
				<input class="u-btn u-btn-nm u-btn-bg-orange" type="button" value="添加" id="bPreAdd"/>
			</div>
			<div class="m-table">
				<table>
					<thead>
						<tr>
							<th class="publish-name-th">出版社名称</th>
							<th class="publish-name-th">简称</th>
							<th>操作区</th>
						</tr>
					</thead>
					<tbody id="pressListContext">
						
					</tbody>
					
				</table>
			</div>
		</div>
	</div>
</div>
<textarea id="pressContext_tpl" style="display:none;">
	{{#pressList}} 
	<tr> 
		 <td><input type="text" name="pressName" value="{{pressName}}"  disabled="disabled" class="u-ipt-nm u-bd-none u-bg-color-white s-c-gray-3"/></td> 
		 <td><input type="text" name="nickName" value="{{nickName}}"  disabled="disabled" class="u-ipt-nm u-bd-none u-bg-color-white s-c-gray-3"/></td> 

		 <td class="operation "> 
		 	<a href="javascript:void(0)" data-press-id="{{pressId}}" class="link aEdit" name="aEdit">编辑</a> 
		 	<a href="javascript:void(0)" data-press-id="{{pressId}}" class="link aDelete">删除</a>
		 </td> 
	</tr> 
	{{/pressList}}
</textarea>
<textarea id="pressInput_tpl" style="display:none;">
<tr>
		 <td><input type="text" name="pressName" class="u-ipt-nm u-bd-color-green" maxlength="30"/></td> 
		 <td><input type="text" name="nickName" class="u-ipt-nm u-bd-color-green" maxlength="5"/></td> 
		 <td class="operation "> 
		 	<a href="javascript:void(0)" class="link aAdd" name="aAdd">保存</a> 
		 	<a href="javascript:void(0)" class="link aDelete" name="aDelete">删除</a>
		 </td>
</tr>
</textarea>

<script type="text/javascript">
	seajs.use('question/view/press/pressList');
</script>
</body>