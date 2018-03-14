<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>答题卡上传记录 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/homework/abnormal.css?t=20171115" type="text/css">
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
						<span>学生：${sheetBook.userName}</span>
					</div>
					<div class="reason">
						<i class="icon-reason"></i>
						<span>异常原因：缺页异常</span>
					</div>
					<div class="m-tiptext m-tiptext-text m-tiptext-warning">
						<i class="iconfont icon">&#xf0142; </i>
						<div class="msg">提示：请在客户端上传缺失答题卡。</div>
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
	seajs.use([ 'common/react/react', 'common/react/react-dom', 'homework/sheet/imgview' ], function(React, ReactDOM,
			ImgView) {
		ReactDOM.render(React.createElement(ImgView, {
			imgs : imgs
		}), document.getElementById('imgview'));
	});
</script>
</html>
