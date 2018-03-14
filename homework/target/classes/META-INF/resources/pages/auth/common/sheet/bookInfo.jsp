<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>答题卡上传记录 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/homework/abnormal.css?t=20171115" type="text/css">
<style type="text/css">
.c-sheet-show-a3{float:none !important; width:auto !important; height:878px !important;;}
.c-sheet-show-a3 .list li{width:100%; height:878px !important;}
.c-sheet-show-a3 .list img{float:left; width:50% !important;}
</style>
</head>
<body>
	<div class="g-bd">
		<div class="g-mn">
			<div class="c-sheet-show c-sheet-show-a3">
				<div id="imgview"></div>
			</div>
		</div>
	</div>
</body>
<script>
var imgs = ${imgs};
seajs.use([ 'common/react/react', 'common/react/react-dom', 'homework/sheet/imgview2' ], function(React, ReactDOM,
		ImgView) {
	ReactDOM.render(React.createElement(ImgView, {
		imgs : imgs
	}), document.getElementById('imgview'));
});
</script>
</html>
