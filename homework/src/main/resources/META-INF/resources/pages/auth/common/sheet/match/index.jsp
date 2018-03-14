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
						<span>学生：<c:out value="${sheetBook.userName}" default="未知" /></span>
					</div>
					<div class="reason">
						<i class="icon-reason"></i>
						<span>
							异常原因：
							<c:if test="${sheetBook.errorNo == 20102}">作业匹配异常</c:if>
							<c:if test="${sheetBook.errorNo == 20103}">作业重复写入</c:if>
						</span>
					</div>
					<div class="m-tiptext m-tiptext-text m-tiptext-warning">
						<i class="iconfont icon">&#xf0142; </i>
						<div class="msg">可能原因：</div>
					</div>
					<div class="c-abnormal-match">
						<p>1). 该学生已<span class="s-c-orange">通过电脑或者平板完成了此份作业</span>；</p>
						<p>2). 该学生的此份作业的<span class="s-c-orange">线下答题卡已上传过</span>；</p>
						<p>3). 该学生<span class="s-c-orange">不在布置学生列表中</span>；</p>
						<p>请按上述原因，进行检查。</p>
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
