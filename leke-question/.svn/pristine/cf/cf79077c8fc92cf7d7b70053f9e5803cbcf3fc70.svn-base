<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>未审核 - 乐课网</title>
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
   			<div class="base_view1">
				<div class="m-able">
					<table>
						<thead>
							<tr>
								<th width="20%">学校阶段</th>
								<th width="20%">年级</th>
								<th width="20%">科目</th>
								<th width="20%">总题量</th>
								<th width="10%">昨日新增</th>
								<th width="10%">操作区</th>
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
	{{#inputStatisList}}
	<tr> 
		 <td>{{schoolStageName}}</td>
		 <td>{{gradeName}}</td> 
		 <td>{{subjectName}}</td> 
		 <td>{{amount}}</td> 
		 <td>{{yesterdayAmount}}</td> 
		 <td class="operation"><a href="${ctx}/auth/admin/question/draft/draftQueList.htm?subjectId={{subjectId}}&schoolStageId={{schoolStageId}}" class="s-c-turquoise">查看</a></td> 
	</tr> 
	{{/inputStatisList}}
</textarea>
</form>

<script type="text/javascript">
	seajs.use('question/questionStatis/draftAmountList');
</script>
</body>
</html>