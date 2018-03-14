<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>题型- 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
    		<div class="base_view1">
    			<div class="m-table m-table-center">
					<table>
						<thead>
							<tr>
								<th width="80%" align="center">题型名称</th>
								<th width="20%" align="center">操作</th>
							</tr>
						</thead>
						<tbody id="tbRoots" data-bind="foreach: qTypes">
							<tr>
								<td data-bind="text: questionTypeName"></td>
								<td class="operation"><a href="#" data-bind="click: function($data){$parent.open($data);}">编辑</a></td></tr>
						</tbody>
					</table>
				</div>
    		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
seajs.use('question/questionType/list')
</script>
</body>
</html>