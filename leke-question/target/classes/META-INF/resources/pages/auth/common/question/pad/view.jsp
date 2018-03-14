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
</head>
<body>
	<div class="main">
        <div class="z-exercisesdetails">
            <div class="exercisesdetails " id="j-question-content">
            </div>
            <div class="c-operationmenu">
                <ul>
                    <li class="item addexercise" id="j-to-paper-add">
                        <i></i><p>加入习题篮</p>
                    </li>
                    <li class="item addexercisecur" id="j-to-paper-del" style="display: none;">
                        <i></i><p>已加入习题篮</p>
                    </li>
                    <li class="item ${isFavorite? 'collectionscur' : 'collections'}  j-collection j-favorite"><i></i><p>收藏</p></li>
	                <li class="item fabulous j-praise"><i></i><p>点赞</p></li>
	                <li class="item errorcorrection j-question-feedback"><i></i><p>纠错</p></li>
                </ul>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
window.viewCtx = {
		questionId: ${questionId}
};
</script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/zepto.min.js"></script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/common.js"></script>
<script type="text/javascript" src="/scripts/question/pad/questionService.js"></script>
<script type="text/javascript" src="/scripts/question/pad/question.cart.js"></script>
<script type="text/javascript" src="/scripts/question/pad/leke.question.feedback.js"></script>
<script type="text/javascript" src="${ctx}/scripts/common/mobile/ui/ui-coursewarePreview/ui-coursewarePreview.js"></script>
<script type="text/javascript" src="/scripts/core-business-pad/question/que-audio.js"></script>
<script type="text/javascript" src="/scripts/question/pages/common/question/pad/view.js"></script>
</html>