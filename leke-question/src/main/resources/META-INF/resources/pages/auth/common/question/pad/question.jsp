<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>习题详情</title>
<leke:context />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="${assets}/styles/mobile/global.css">
<link rel="stylesheet" href="${assets}/styles/mobile/resource-pad.css">
<link rel="stylesheet" href="${ctx}/scripts/common/mobile/ui/ui-coursewarePreview/skin/ui-coursewarePreview.css">
<style type="text/css">
	.queOperation{
		display: flex;height: 40px;line-height: 40px; -webkit-box-pack: center;justify-content: center;-webkit-box-align: center;
		align-items: center;font-size: 14px;background: #fff;margin-bottom:5px;
	}
</style>
</head>
<body>
	<div class="main">
        <div class="z-exercisesdetails">
            <div class="exercisesdetails " id="j-question-content">
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
window.viewCtx = {
		questionId: ${questionId}
};
</script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/common.js"></script>
<script type="text/javascript" src="/scripts/question/pages/common/question/pad/question.js"></script>
</html>