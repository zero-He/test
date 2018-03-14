<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>错题本</title>
<%@ include file="/pages/common/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/question-show.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css">
<style type="text/css">
.poptitle{position:relative; z-index:10;}
.poptitle .m-tippop{display:none; position:absolute; width:365px; left:24px; top:-8px; text-align:left; font-weight:normal;}
.poptitle:hover .m-tippop{display:block;}

.history-tip{
	height: 30px;   
 	font-weight: normal;
	margin-top: 15px;
	margin-bottom: 15px;
}
.history-tip>span{display: inline-block;line-height: 20px;border: 1px solid #ccc;position: relative;margin-right: 5px;}
.cross{width: 22px;height: 22px;float: right;}
.history-tip .cross::after{content: "";border-left: 1px solid red;position: absolute;right: 10px;display: inline-block;width: 1px;height: 12px;margin-top: 4px;transform: rotate(45deg);text-align: center;}
.history-tip .cross::before{content: "";border-right: 1px solid red;position: absolute;right: 10px;display: inline-block;width: 1px;height: 12px;margin-top: 4px;transform: rotate(-45deg);text-align: center;}

</style>

</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<div data-bind="component: {
		name: 'question-wrong-list',
		params: config
	}"></div>
</div>
<div data-bind="component: 'que-cart'"></div>
<leke:pref/>
<script>
window.Ctx = {
		clazzs: ${clazzs},
		subjects: ${subjects}
};
seajs.use('question/pages/teacher/question/wrong/list');

</script>
</body>
</html>