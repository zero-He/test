<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>sql统计</title>
<%@ include file="/pages/common/meta.jsp"%>
<style type="text/css">
pre {
  word-break: break-word;
}
</style>
</head>

<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd"> 

	<div class="g-mn">

<div class="m-form">
<form>
<ul>
	<li class="form-item">
		<label for="" class="title">SQL ID：</label>
		<div class="con">
			<span>${info.sqlId}</span>
		</div>
	</li>
	<li class="form-item">
		<label for="" class="title">类名：</label>
		<div class="con">
			<span>${info.className}</span>
		</div>
	</li>
	<li class="form-item">
		<label for="" class="title">SQL 语句</label>
		<div class="con">
			<pre>${info.sql}</pre>
		</div>
	</li>
</ul>
</form>
</div>

	</div>
</div>
</body>
</html>