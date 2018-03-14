<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>答题卡上传记录 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/homework/abnormal.css?t=${_t}" type="text/css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<div class="c-sheet-show">
				<div id="imgview"></div>
			</div>

			<div class="c-sheet-operation">
				<h5 class="head">异常查看</h5>
				
				<div class="con">
					<div class="student">
						<i class="icon-student"></i>
						<span>学生：未知</span>
					</div>
					<div class="reason">
						<i class="icon-reason"></i>
						<span>
							异常原因：
							<c:if test="${sheetPage.errorNo == 10101}">二维码异常</c:if>
							<c:if test="${sheetPage.errorNo == 10102}">识别异常</c:if>
							<c:if test="${sheetPage.errorNo == 10201}">身份异常</c:if>
						</span>
					</div>
					<div class="m-tiptext m-tiptext-text m-tiptext-warning">
						<i class="iconfont icon">&#xf0142; </i>
						<div class="msg">
							小乐提醒您：
							<c:if test="${sheetPage.errorNo == 10101 || sheetPage.errorNo == 10102}">为了能采集作业数据，请提醒学生保存卷面整洁。</c:if>
							<c:if test="${sheetPage.errorNo == 10201}">为了能匹配作业数据，请提醒学生认真填涂乐号。</c:if>
						</div>
					</div>

					<div class="btns">
						<a class="u-btn u-btn-nm u-btn-bg-gray" href="javascript:window.close();">关闭</a>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
<script>
var imgs = ${imgs};
seajs.use(['common/react/react', 'common/react/react-dom', 'homework/sheet/imgview'], function(React, ReactDOM, ImgView) {
	ReactDOM.render(React.createElement(ImgView, { imgs: imgs }), document.getElementById('imgview'));
});
</script>
</html>
