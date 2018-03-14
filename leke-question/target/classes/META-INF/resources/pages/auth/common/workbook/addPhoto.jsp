<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/tutor/user/myDetail.css?t=${_t}" type="text/css" />
</head>
<body>
<div class="userCon content mestop f-h-auto f-pt40 f-pl40">
	<div class="detail winput">
		<div class="portrait">
			<input type="hidden" id="jPhotoUrl">
			<ul class="f-clearfix">
				<li class="tou f-fl f-mr100"><div class="toupic"><img src="${photoUrl}" id="jPreview" /></div></li>
				<li class="f-fl">
					<p>
						<input type="hidden" id="jTicket" value="${ticket}">
						<input type="hidden" id="jWorkbookId" value="${workbookId}">
						<div id="progressFile" class="f-dn"></div>
						<br>
						<a href="javascript:void(0)" id="jUploadPhoto" ></a>
					<!-- 	<br/><br><locale:message code="que.workbook.pi.resolution"></locale:message>:120*120 -->
					</p>
				</li>
			</ul>
		</div>
		<div class="f-cb f-tac f-mt15">
			<input class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" id="jUpdatePhoto" value="<locale:message code="que.workbook.general.save"></locale:message>">
			<input class="u-btn u-btn-nm u-btn-bg-gray" type="button" id="jCancelPhoto" value="<locale:message code="que.workbook.general.cancel"></locale:message>">
		</div>
			
	</div>
</div>

<script type="text/javascript">
	seajs.use('question/view/workbook/addPhoto');
</script>

</body>
</html>