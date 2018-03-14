<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Word 习题导入</title>
<%@ include file="/pages/common/meta.jsp"%>

<style type="text/css">
.c-form {margin-top: 40px;}
.c-form>ul>li .title {width: 130px;margin-right: 10px;}
.c-form .submit{padding-left: 140px;}
</style>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	
	<div class="g-mn">
		<div data-bind="component: {
		name: 'que-word-page',
		params: {
			ticket: '${ticket}'
		}
	}"></div>
	</div>

</div>


<leke:pref/>

<script type="text/javascript">
seajs.use('question/word/index');
</script>

</body>
</html>