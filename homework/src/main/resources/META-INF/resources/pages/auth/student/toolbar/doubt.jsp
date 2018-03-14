<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<div style="overflow: auto; height: 450px;">
		<form name="doubtForm" method="post" class="m-form" style="padding:0;">
			<ul>
				<li class="form-item" style="padding-top:0;">
					<label class="title"><span class="require">*</span>学科：</label>
					<div class="con">
						<select id="jSubjectCombo" class="u-select u-select-nm"></select>
					</div>
				</li>
				<li class="form-item" style="padding-top:0;">
					<label class="title"><span class="require">*</span>老师：</label>
					<div class="con">
						<select id="jTeacherCombo" name="teacherId" class="u-select u-select-nm"></select>
					</div>
				</li>
				<li class="form-item">
					<label class="title"><span class="require">*</span>内容：</label>
					<div class="con">
						<textarea id="jRemark" name="doubtContent" style="width: 500px; height: 150px;"></textarea>
						<p><span class="word_surplus"></span></p>
					</div>
				</li>
				<li class="form-item">
					<label class="title">录音：</label>
					<div class="con">
						<dfn class="j-dfn" data-type="recorder" data-elementid="audioUrl"></dfn>
					</div>
				</li>
				
			</ul>
		</form>
	</div>
	<div class="f-tac f-mt5">
		<input type="button" name="button" value="提交" id="jSubmitBtn" class="u-btn u-btn-nm u-btn-bg-turquoise">
		<input type="button" name="button" value="取消" id="jCancelBtn" class="u-btn u-btn-nm u-btn-bg-gray">
	</div>

	<leke:pref/>
	<c:if test="${cross == 1}">
	<script>
		document.domain = '${initParam.mainDomain}';
	</script>
	</c:if>
	<script>
		seajs.use("homework/toolbar/doubt");
	</script>
</body>
</html>