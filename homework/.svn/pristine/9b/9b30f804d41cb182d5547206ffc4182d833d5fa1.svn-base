<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>查看微课 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<style>
.z-resource-review object{display:block !important; width:800px !important; height:500px !important; margin:0 auto !important;}
</style>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div data-bind="component: 'scope-title'"></div>
		<div class="g-mn">
			<div class="z-resource-review">
				<div class="content">
					<div class="courseware">
						<div style="width:800px;" id="jPreviewDiv"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		window.Csts = ${Csts};
		seajs.use('homework/micro/viewMicro');
	</script>
</body>
</html>