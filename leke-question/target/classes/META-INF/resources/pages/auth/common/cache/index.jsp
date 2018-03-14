<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>删除缓存</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div class="g-mn">
		<form id="j-form-del" class="m-form f-mt20" action="del.htm">
			<ul class="form-wrp">
			<li>
				<label class="title">
					<span class="require">*</span>
					分组键：
				</label>
				<div class="con">
					<input type="text" class="u-ipt u-ipt-lg" name="prefix"/>
				</div>
			</li>
			<li>
				<label class="title">
					<span class="require">*</span>
					明细键：
				</label>
				<div class="con">
					<input type="text" class="u-ipt u-ipt-lg" name="key"/>
				</div>
			</li>
			<li>
				<div class="submit">
					<button id="j-btn-del" class="u-btn u-btn-nm u-btn-bg-turquoise">删除</button>
				</div>
			</li>
			</ul>
		</form>
		
		<form id="j-form-load" class="m-form f-mt20" action="">
			<ul class="form-wrp">
			<li>
				<label class="title">
					<span class="require">*</span>
					模块：
				</label>
				<div class="con">
					<input type="text" class="u-ipt u-ipt-lg" name="module" id="section"/>
				</div>
			</li>
			<li>
				<div class="submit">
					<button id="j-btn-load" class="u-btn u-btn-nm u-btn-bg-turquoise">加载</button>
				</div>
			</li>
			</ul>
		</form>
	</div>
</div>

<script type="text/javascript">
seajs.use('question/cache');
</script>

</body>
</html>