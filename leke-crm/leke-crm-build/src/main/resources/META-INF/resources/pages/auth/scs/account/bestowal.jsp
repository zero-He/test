<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>赠送点设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<form id="jParametersForm" action="bestowal.htm" method="post" class="m-form">
				<ul>
					<li>
						<label class="title" style="width: 104px;">完善信息：</label>
						<div class="con">
							<input type="text" id="jInformation" name="information" value="${bestowalSetting.information }">
						</div>
					</li>
					<li>
						<label class="title" style="width: 104px;">首次绑定客户经理赠送点数限额：</label>
						<div class="con">
							<input type="text" id="jRelationSale" name="relationSale" value="${bestowalSetting.relationSale }">
						</div>
					</li>
				</ul>
				<div class="submit">
					<input type="submit" value="保存" class="u-btn u-btn-nm u-btn-bg-turquoise f-ml40">
					<a href="javascript:history.back();" class="u-btn u-btn-nm u-btn-bg-gray">返回</a>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	seajs.use('scs/account/bestowal');
</script>
</html>