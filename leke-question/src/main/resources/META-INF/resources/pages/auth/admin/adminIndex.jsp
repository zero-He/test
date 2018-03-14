<%-- <%@ page pageEncoding="UTF-8"%>
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
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
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
			<dl class="quesOntest">
				<dd>草稿题库有<i id="draftAmount"></i>道习题等着你去核查</dd>
				<dd><a href="${ctx}/auth/admin/questionStatis/total/totalStatis.htm" class="quesTest">习题核查<em>&gt;&gt;</em></a></dd>
			</dl>
			</div>
			<div class="userMes ques">
				<dl class="quesOntest">
					<dd>正式题库有<i id="formalAmount">${inputStatis.formalAmount }</i>道习题等着你去核查</dd>
					<dd><a href="${ctx}/auth/admin/questionStatis/total/totalStatis.htm" class="quesTest">习题核查<em>&gt;&gt;</em></a></dd>
				</dl>
			</div>
		</div>
		<div class="quesSettings">
			<dl>
				<dd class="settings">标签设置</dd>
				<dd style=" margin-bottom:42px"><img src="${ctx}/images/question/setting1.png"></dd>
				<dd><a href="${ctx}/auth/admin/officialTag/officialTagList.htm" class="quesTest">点击进入<em>&gt;&gt;</em></a></dd>
			</dl>
		</div>
		<div class="quesSettings">
			<dl>
				<dd class="settings">教材章节设置</dd>
				<dd style=" margin-bottom:50px"><img src="${ctx}/images/question/setting2.png"></dd>
				<dd><a href="${ctx}/auth/admin/material/materialList.htm" class="quesTest">点击进入<em>&gt;&gt;</em></a></dd>
				</dl>
		</div>
		<div class="quesSettings">
			<dl>
				<dd class="settings">知识点科目设置</dd>
				<dd style=" margin-bottom:27px"><img src="${ctx}/images/question/setting3.png"></dd>
				<dd><a href="${ctx}/auth/admin/knowledge/knowledgeList.htm" class="quesTest">点击进入<em>&gt;&gt;</em></a></dd>
			</dl>
		</div>
		<div class="quesSettings" style="margin-right:0">
		<dl>
			<dd class="settings">习题领取量设置</dd>
			<dd style=" margin-bottom:20px"><img src="${ctx}/images/question/setting4.png"></dd>
			<dd><a href="${ctx}/auth/admin/questionTaskConfig/taskConfig.htm" class="quesTest">点击进入<em>&gt;&gt;</em></a></dd>
			</dl>
		</div>
	</div>
</div>

<script type="text/javascript">
	seajs.use('question/questionStatis/adminIndex');
</script>
</body> 

</html> --%>
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>个人主页 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" href="${assets}/styles/resource/resource.css?t=${_t}"/>
</head>
<body>
<form>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn" data-bind="component: {
			name: 'que-home-amount-list',
			params: {}
		}">
	</div>
</div>

<script type="text/javascript">
	seajs.use('question/questionStatis/adminIndex');
</script>

</body>
</html>