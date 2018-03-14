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
				<label class="title">校对日期：</label>
				<input type="text" name="startDate" value="${startDate}"  class="Wdate u-ipt u-ipt-nm"  id="startDate" />
				- <input type="text" name="endDate" value="${endDate}"  class="Wdate u-ipt u-ipt-nm"  id="endDate" />
				<label  class="title">校对人:</label>
				<input type="text" class="u-ipt u-ipt-nm" id="userName"  name="userName">
				<input class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询" id="bSearch">
			</div>
		</div>
		<div class="base_view1">
				<div class="m-table">
					<table>
						<thead>
							<tr>
								<th width="10%">教研员</th>
								<th width="15%">校对量</th>
								<th width="15%">未校对量</th>
								<th width="15%">纠错量</th>
								<th width="15%">修正量</th>
								<th width="15%">未修正量</th>
								<th width="15%">有效量</th>
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
		<input type="hidden" id="{{userId}}" value = "{{userId}}">
		 <td><a href="${ctx}/auth/admin/questionStatis/proofreading/userProofreadingAmount.htm?userId={{userId}}&userName={{userName}}" class="s-c-turquoise">{{userName}}</a></td> 
		 <td>{{proofreadingAmount}}</td> 
		 <td>{{unProofreadingAmount}}</td> 
		 <td>{{errorCorrectionAmount}}</td> 
		 <td>{{correctAmount}}</td> 
		 <td>{{unCorrectAmount}}</td> 
		 <td>{{effectiveAmount}}</td> 
	</tr> 
	{{/inputStatisList}}
	<tr class="s-c-orange">
		<td>合计</td> 
		 <td id="proofreadingTotalAmount"></td> 
		 <td id="unProofreadingTotalAmount"></td> 
		 <td id="errorCorrectionTotalAmount"></td> 
		 <td id="correctTotalAmount"></td> 
		 <td id="unCorrectTotalAmount"></td> 
		 <td id="effectiveTotalAmount"></td> 
	</tr>
</textarea>
</form>

<script type="text/javascript">
	seajs.use('question/questionStatis/proofreadingAmountList');
</script>

</body>
</html>