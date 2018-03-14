<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/globalnote.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=20171115">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/book.css?t=20171115">
<title><locale:message code="common.page.nav.ac.myassignment" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>

</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="m-counthomework">
			<div class="title">
            	<span>作业批改</span>
            	<button type="button" class="u-btn u-btn-nm u-btn-bg-turquoise j_allHomework">全部作业</button>
            	<c:if test="${isOfflineSheet}">
            	<button type="button" class="u-btn u-btn-nm u-btn-bg-turquoise f-mr10"
            		onclick="javascript:location.href='${ctx}/auth/teacher/sheet/taskList.htm';">上传记录</button>
            	</c:if>
            </div>
	        <div class="con f-bfc">
	        	<ul id="myHomeworkContext">
	        	</ul>
	        	<div class="m-tips f-block f-hide" id="j_emptyData">
	        		<i></i>
	        		<span>对不起，暂无待批改的作业，点击<a class="s-c-turquoise j_allHomework"  href="javascript:;">全部作业</a>，可查看布置的所有作业。</span>
				</div>
			</div>
			<div class="m-tiptext m-tiptext-text m-tiptext-warning">
				<i class="iconfont icon">󰅂 </i> <div class="msg">小乐提醒您：此处仅显示待批改作业的班级，如需查看不要批改的作业 ，请点击右上角的全部作业。</div>
			</div>
		</div>
	</div>
</div>


<script id="myHomeworkContext_tpl" type="text/handlebars">
{{#.}}
	<li class="{{classTypeStr}}">
		<a data-href="/auth/teacher/homework/homeworkList.htm?classId={{classId}}" data-clazz="{{className}}" class="j_detail" >
			<h5>{{className}}</h5>
			<div class="wait-num">待批改：{{toCorrectNum}}份</div>
			<i></i>
		</a>
	</li>
{{/.}}
</script>

<script type="text/javascript">
	seajs.use('homework/homework/classHomeworks');
</script>

</body>
</html>