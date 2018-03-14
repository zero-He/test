<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>教材上传电子书 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/resource/resource.css?t=${_t}" />
<style>
.m-form .center{text-align:center;}
.m-form .center>*{vertical-align:middle;}
</style>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd z-resource">
		<div class="z-resource-upload">
			<ul>
				<li class="form-item">
					<div class="con">
						<div id="jDivSwfupload" class="addcourseware">
							<span id="jSpanButtonPlaceholder"></span>
						</div>
						<div id="jMaterialUpload"></div>
						<div class="normalinfo">
							上传电子教材，请上传300M以内文件，支持格式PDF
						</div>
					</div>
				</li>
			</ul>
				<div class="submit">
					<div class="c-bottombtn">
						<button class="u-btn u-btn-lg u-btn-bg-turquoise"
							data-bind="click: doSave,visible: pending">确定</button>
						<button class="u-btn u-btn-lg u-btn-bg-gray"
							data-bind="click: doCancel">取消</button>
					</div>
				</div>
		</div>
	</div>
</body>
<script>
	window.materialCtx = {
			materialId: ${materialId}
	};
	seajs.use('question/view/material/materialUpload');
</script>
</html>
