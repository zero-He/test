<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>个人主页 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd"> <%@ include file="/pages/navigate/navigate.jsp"%>

	<div class="g-mn">
		<div class="detail">
			<div class="userMes" style="margin-right:10px">
				<img src="" id="photo" width="75px" height="75px">
				<dl class="quesimform">
					<dd><em class="name" id="userName"></em><span id="roleName"></span>你好！欢迎进入乐课网个人中心</dd>
					<dd>账号：<em id="userId"></em></dd>
				</dl>
			</div>
			<div class="userMes">
				<p>最近登录日期：<em>2014-03-11 16：40</em></p>
			</div>
			<div class="userMes ques" style="margin-right:10px">
				<dl class="quesLoad">
					<dd>总共录入</dd>
					<dd class="questotalnum" style="padding-top:10px"><i style="font-size:30px" id="inputAmount"></i></dd>
					<dd><a href="${ctx}/auth/inputer/question/input/questionAdd.htm" class="quesTest">录入习题<em>&gt;&gt;</em></a></dd>
				</dl>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	seajs.use('question/questionStatis/inputerIndex');
</script>
</body>
</html>