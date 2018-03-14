<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>寒暑假作业管理 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${assets}/styles/pay/common/new.css" />
<link rel="stylesheet" type="text/css"
	href="${assets}/styles/union/union.css">
<link type="text/css" rel="stylesheet"
	href="${assets}/styles/pay/common/layout.css?t=20171115">


</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<form action="" method="post" id="jFormPage">
				<div class="z-pay-virtualcurrency ">
					<h3 class="accountname">


						<a class="u-btn  u-btn-at  u-btn-bg-orange f-fr f-mt10 f-mr25 w50"
							href="${ctx}/auth/platformAdmin/homeworkConfig/AddHomeworkConfig.htm">添加</a>

					</h3>
				</div>

				<div class="m-table ">
					<table>
						<tr>
							<th>添加时间</th>
							<th>年份</th>
							<th>类型</th>
							<th>寒暑假作业开始/截止时间</th>
							<th>寒暑假作业推送周期</th>
						<tr>
						<tbody id="jApplyListTbody">

						</tbody>
					</table>
					<div class="page" id="jDivPage"></div>
				</div>

			</form>
		</div>
	</div>
	<textarea id="jApplyListTpl" class="f-dn">
	{{#dataList}}
		     <tr>
		            <td>{{createdOn}}</td>
					<td>{{year}}</td>
					<td>{{type}}</td>
					<td>{{work_begintime}}{{work_endtime}}</td>
					<td>{{cycle_begintime}}{{cycle_endtime}}</td>
					
				</tr>
	{{/dataList}}
</textarea>

	<script>
	seajs.use("homework/homework/platformAdmin/HomeworkConfigList");
</script>

</body>
</html>