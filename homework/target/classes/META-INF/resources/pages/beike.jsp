<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pages/common/meta.jsp"%>
<style>
.g-mn>div {
	padding-top: 10px;
	vertical-align: middle;
}
</style>
</head>
<body>
	<div class="g-bd">
		<div class="g-mn">
			<div>
				<span>课堂：</span>
				<input id="lessonId" class="u-ipt u-ipt-lg">
			</div>
			<div>
				<span style="vertical-align: top;">资源：</span>
				<textarea id="json" rows="6" cols="80"></textarea>
				<div style="padding-left: 50px;">
					<div>示例：</div>
					<div style="padding-left: 32px;">新增：[{"cmd":1,"guid":null,"type":3,"resId":1234,"resName":"资源名称","phase":2}]</div>
					<div style="padding-left: 32px;">编辑：[{"cmd":2,"guid":"","phase":2}]</div>
					<div style="padding-left: 32px;">删除：[{"cmd":3,"guid":""}]</div>
				</div>
			</div>
			<br>
			<input id="submit" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="提交">
		</div>
	</div>
	<script type="text/javascript">
		seajs.use([ 'jquery', 'utils' ], function($, Utils) {
			$('#submit').click(function() {
				var lessonId = $('#lessonId').val();
				var data = $('#json').val();
				if (lessonId == '') {
					Utils.Notice.alert('课堂为空');
					return;
				}
				if (data == '') {
					Utils.Notice.alert('数据为空');
					return;
				}
				$.post('/test/beike.htm', {
					lessonId : lessonId,
					data : data
				}).done(function(json) {
					Utils.Notice.alert(json.message);
				})
			});
		});
	</script>
</body>
</html>