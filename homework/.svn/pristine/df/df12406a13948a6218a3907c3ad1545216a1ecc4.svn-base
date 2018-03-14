<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<html>
<head>
	<%@ include file="/pages/common/meta.jsp" %>
	<%@ include file="/pages/common/cordova.jsp"%>
	<title>发布考试</title>
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<link rel="stylesheet" href="${assets}/styles/mobile/global.css"/>
	<link rel="stylesheet" href="${assets}/styles/mobile/homework/assignpad.css">
	<style>
		.j-content-paper:{
			position: static !important;
		}
	</style>
</head>
<body>
	<div class="top">
		<span class="close j-window-close">关闭</span>
		<div class="title">发布考试</div>
	</div>
<div class="assign-flow">
	<div class="assign-hd">
		<ul>
			<li class="assign-hd-item assign-hd-item-on test-paper">选择资源</li>
			<li class="assign-hd-item-line"></li>
			<li class="assign-hd-item test-student">选择学生</li>
			<li class="assign-hd-item-line"></li>
			<li class="assign-hd-item test-time">选择时间</li>
		</ul>
	</div>
	<div class="assign-bd">
		<!--选择试卷-->
		<div class="assign-bd-testpaper assign-bd-li">
			<section class="assign-resource resource-paper">
				<ul class="resource-list resource-list-null j-resources j-resources3">

				</ul>
				<div class="add-btn j-btn-paper"><span>+</span>添加</div>
			</section>
		</div>
		<!--选择学生-->
		<div class="assign-bd-student assign-bd-li">
			<div class="assign-bd-student-left">
				<ul class="assign-bd-student-classe j-ul-clazz">
					<c:forEach items="${classes }" var="item">
						<li class="assign-bd-student-classe-li checkall j-chk-clazz" data-classid="${item.classId }" data-classname="${item.className }" data-classtype="${item.classType }">
							<label class="assign-bd-item-checkbox assign-bd-item-checkbox-off "></label>
							<span class="classe-name ">${item.className }</span>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="assign-bd-student-right">
				<ul class="assign-bd-student-group-check j-ul-clazz-group">

				</ul>
				<ul class="assign-bd-student-group-check j-ul-clazz-user">

				</ul>
			</div>
		</div>
		<!--选择时间-->
		<div class="assign-bd-time assign-bd-li">
			<dl class="assign-bd-time-item assign-calendar">
				<dt class="assign-bd-time-tit f-fl">
					考试开始时间:
				</dt>
				<dd class="assign-bd-time-start f-fl">
					<form action="">
						<input placeholder="考试开始时间" data-showdefault="true" class="assign-bd-time-start-date assign-time-selects j-time-start"
						       onclick="laydate({start:laydate.now(0,'YYYY.MM.DD hh:mm'),istime:true, format: 'YYYY.MM.DD hh:mm'})" readonly="">
						<label class="laydate-icon"></label>
					</form>
				</dd>
			</dl>
			<dl class="assign-bd-time-item assign-calendar">
				<dt class="assign-bd-time-tit f-fl">
					考试时长：
					<label class="paper-duration"><span data-examtime="60" class="c-assign-single-checkbox exam-time"></span>60分钟</label>
					<label class="paper-duration"><span data-examtime="90" class="c-assign-single-checkbox c-assign-single-checkbox-false exam-time"></span>90分钟</label>
					<label class="paper-duration"><span data-examtime="120" class="c-assign-single-checkbox exam-time"></span>120分钟</label>
				</dt>
			</dl>
			<dl class="assign-bd-time-item assign-calendar">
				<dt class="assign-bd-time-tit f-fl">
					<label for="" title="学生提交考试的最晚时间"><span class="c-assign-single-checkbox openAnswer"></span>设置公布答案时间</label>
				</dt>
				<dd class="assign-bd-time-start f-fl f-hide j-openAnswer">
					<form action="">
						<input placeholder="公布答案时间" class="assign-bd-time-start-date assign-time-selects j-time-openAnswer"
						       onclick="laydate({start:laydate.now(0,'YYYY.MM.DD hh:mm'),istime:true, format: 'YYYY.MM.DD hh:mm'})" readonly="">
						<label class="laydate-icon"></label>
					</form>
				</dd>
			</dl>
		</div>
	</div>
	<div class="assign-bottom-btn">
		<ul>
			<li class="assign-bottom-btn-item assign-bottom-btn-item-on">下一步</li>
			<li class="assign-bottom-btn-item">下一步</li>
			<li class="assign-bottom-btn-item">确认发布</li>
		</ul>
	</div>
</div>
<leke:pref/>
<script type="text/x-handlebars" id="j-tmpl-resource">
	{{#.}}
	<li class="item item-{{suffix}}" data-id="{{resId}}" data-type="{{resType}}">
		{{resName}}
		<div class="delete-btn j-resource-del">×</div>
	</li>
	{{/.}}
</script>

<script type="text/x-handlebars" id="j-tmpl-clazz-group">
	{{#.}}
	<li class="assign-bd-student-group-item j-chk-clazz-group" data-groupid="{{groupId}}" data-groupname="{{groupName}}">
		<label class="assign-bd-item-checkbox assign-bd-item-checkbox-off " data-groupid="{{groupId}}" data-groupname="{{groupName}}"></label>
		<span class="classe-name ">{{groupName}}</span></li>
	{{/.}}
</script>

<script type="text/x-handlebars" id="j-tmpl-clazz-user">
	{{#.}}
	<li class="assign-bd-student-group-item j-chk-clazz-user" data-userid="{{userId}}" data-username="{{userName}}">
		<label class="assign-bd-item-checkbox assign-bd-item-checkbox-off " data-userid="{{userId}}" data-username="{{userName}}"></label>
		<span class="classe-name">{{userName}}</span></li>
	{{/.}}
</script>

</body>

<script src="${assets }/scripts/common/mobile/common.js"></script>
<script src="${assets }/scripts/common/laydate-rem/laydate.js"></script>
<script type="text/javascript" src="/scripts/paper/pad/leke.control.paper.js"></script>
<script src="/scripts/hd/exam/examHomework.js"></script>
</html>