<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<div class="m-tab">
		<ul>
			<li><a href="/auth/teacher/homework/commentText.htm?text=${text1}">文字</a></li>
			<li class="active"><a>语音</a></li>
			<li><a href="/auth/teacher/homework/commentMicro.htm?text=${text1}">微课</a></li>
		</ul>
	</div>
	<div>
		<div class="f-mt30 f-mb40 f-tac">
			<dfn class="j-dfn" data-type="recorder" data-elementid="audioUrl"></dfn>
			<input type="hidden" id="audioUrl" value="">
		</div>
		<div class="f-tac">
			<input type="button" value="提交" id="fBtnSave" class="u-btn u-btn-nm u-btn-bg-turquoise">
			<input type="button" value="取消" id="fBtnCancel" class="u-btn u-btn-nm u-btn-bg-gray">
		</div>
	</div>

</body>
<script type="text/javascript">
	seajs.use('homework/work/comment/commentAudio');
</script>
</html>
