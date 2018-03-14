<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>习题收回 - 乐课网</title>
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
			<div class="m-search-box">
			<label class="title">审核人:</label><input type="text" class="u-ipt u-ipt-nm" id="userName"  name="userName">
   				<input class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询" id="bSearch">
   			</div>
   			<div class="base_view1">
				<div class="m-table">
					<table>
						<thead>
							<tr>
								<th width="25%">审核人</th>
								<th width="15%">领取题量剩余</th>
								<th width="15%">操作区</th>
							</tr>
						</thead>
						<tbody id="listContext">
							
						</tbody>
						
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<textarea id="listContext_tpl" style="display:none;">
	{{#qtList}}
	<tr> 
		 <td>{{userName}}</td> 
		 <td>{{taskCount}}</td> 
		 <td class="operation"><a href="javascript:void(0)" data-id="{{userId}}" class="link">收回</a></td> 
		
	</tr> 
	{{/qtList}}
	<tr>
		<td>总计</td> 
		 <td id="taskTotalCount"></td> 
		 <td class="operation"><a href="javascript:void(0)" class="link">全部收回</a></td>
	</tr>
</textarea>
</form>


<script type="text/javascript">
	seajs.use('question/questionTask/recycleQuestionTask');
</script>
</body>
</html>