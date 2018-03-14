<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>校对量统计 - 乐课网</title>
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
				<div class="item">
					<input type="hidden" value="${ userId}" id = "userId">
					<label class="title">教研员：</label>${userName}
				</div>
				<div class="item">
				<label class="title">校对日期：</label>
				<input type="text" name="startDate" value="${startDate}"  class="Wdate u-ipt u-ipt-nm"  id="startDate" />
				- <input type="text" name="endDate" value="${endDate}" class="Wdate u-ipt u-ipt-nm"  id="endDate" />
				<input class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询" id="bSearch">
				</div>
			</div>
			<div class="base_view1">
				<div class="m-table">
					<table>
						<thead>
							<tr>
								<th width="80%">校对日期</th>
								<th width="20%">校对量</th>
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
		 <td>{{createdDateStr}}</td>
		 <td>
		 <a href="${ctx}/auth/admin/questionStatis/statisQueList.htm?operatorId=${userId}&operateType=7&operateDate={{createdDateStr}}&statusType=7" class="s-c-turquoise">
		 {{proofreadingAmount}}
		 </a>
		 </td> 
	</tr> 
	{{/inputStatisList}}
</textarea>
</form>

<script type="text/javascript">
	seajs.use('question/questionStatis/userProofreadingAmount');
</script>
</body>
</html>