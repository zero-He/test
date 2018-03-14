<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<div class="g-bd" style="padding-top: 200px;">
		<input type="text" id="ipt_1" value="">
	</div>

	<script type="text/javascript">
		seajs.use('/scripts/common/ui/ui-htmleditor/ui-richtext', function(RichText) {
			RichText.create("#ipt_1", {
				width : 200
			});
		});
	</script>
</body>
</html>