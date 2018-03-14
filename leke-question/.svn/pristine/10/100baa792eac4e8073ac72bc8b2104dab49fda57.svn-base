<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>习题校对 - 乐课网</title>
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
				<div class="m-table">
					<table>
						<thead>
							<tr>
								<th width="20%">年级</th>
								<th width="20%">科目</th>
								<th width="20%">总题量</th>
								<th width="20%">未校对</th>
								<th width="20%">操作区</th>
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
	{{#verifyStatis}}
	<tr> 
		 <td>{{gradeName}}</td>
		 <td>{{subjectName}}</td> 
		 <td>{{total}}</td> 
		 <td>{{unverified}}</td>
		 <td><a href="${ctx}/auth/researcher/question/verify/index.htm?subjectId={{subjectId}}&schoolStageId={{schoolStageId}}" class="link">校对</a></td> 
	</tr> 
	{{/verifyStatis}}
</textarea>
</form>

<script type="text/javascript">
	seajs.use('question/questionStatis/questionProofreading');
</script>
</body>
</html>