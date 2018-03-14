<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<html>
<head>
<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/cordova.jsp"%>
<title>布置作业</title>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="${assets }/styles/mobile/global.css" />
<link rel="stylesheet" href="${assets}/styles/mobile/homework/assignpad.css">
<style>
.j-content-paper:{
position:static !important;}
</style>
</head>	
<body>
	<%--<jsp:include page="/pages/common/hd-header.jsp">
		<jsp:param value="布置作业" name="headerTitle"/>
	</jsp:include>--%>
	<div class="top">
		<span class="close j-window-close">关闭</span>
		<div class="title">布置作业</div>
	</div>
	<div class="assign-flow">
		<div class="assign-hd">
			<ul>
				<li class="assign-hd-item assign-hd-item-on test-paper">选择作业</li>
				<li class="assign-hd-item-line"></li>
				<li class="assign-hd-item test-student">选择学生</li>
				<li class="assign-hd-item-line"></li>
				<li class="assign-hd-item test-time">选择时间</li>
			</ul>
		</div>
		<div class="assign-bd ">
			<!--选择试卷-->
			<div class="assign-bd-testpaper assign-bd-li">
	            <section class="assign-resource resource-paper">
	                <ul class="resource-list resource-list-null j-resources j-resources3">
	                	<p class="resource-state j-btn-paper"></p>
	                </ul>
	                <div class="add-btn j-btn-paper"><span>+</span>添加</div>
	            </section>
	            <section class="assign-resource resource-courseware">
	                <ul class="resource-list resource-list-null j-resources j-resources1">
	                	<p class="resource-state j-btn-courseware"></p>
	                </ul>
	                <div class="add-btn j-btn-courseware"><span>+</span>添加</div>
	            </section>
	            <section class="assign-resource resource-microgramme">
	                <ul class="resource-list resource-list-null j-resources j-resources2">
	                	<p class="resource-state j-btn-mic"></p>
	                </ul>
	                <div class="add-btn j-btn-mic"><span>+</span>添加</div>
	            </section>
	        </div>
			<!--选择学生-->
			<div class="assign-bd-student assign-bd-li">
				<div class="assign-bd-student-left">
					<ul class="assign-bd-student-classe j-ul-clazz">
						<c:forEach items="${classes }" var="item">
							<li class="assign-bd-student-classe-li checkall j-chk-clazz" data-classid="${item.classId }" data-classname="${item.className }" data-classtype="${item.classType }">
								<label class="assign-bd-item-checkbox assign-bd-item-checkbox-off " ></label>
								<span class="classe-name " >${item.className }</span>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="assign-bd-student-right">
					<ul class="assign-bd-student-group-check j-ul-clazz-group">
						
					</ul>
					<ul class="assign-bd-student-group-check j-ul-clazz-user">

					</ul>
					<div class="m-tips j-empty-students f-hide"><i></i><span>该班级没有学生，请核实后再布置</span></div>
				</div>
			</div>
			<!--选择时间-->
			<div class="assign-bd-time assign-bd-li">
            <dl class="assign-bd-time-item assign-calendar">
                <dt class="assign-bd-time-tit f-fl">
                    作业开始时间:
                </dt>
                <dd class="assign-bd-time-start f-fl">
                    <form action="">
                        <input placeholder="作业开始时间" data-showdefault="true" class="assign-bd-time-start-date assign-time-selects j-time-start" onclick="laydate({start:laydate.now(0,'YYYY.MM.DD hh:mm'),istime:true, format: 'YYYY.MM.DD hh:mm'})" readonly="">
                        <label class="laydate-icon"></label>
                    </form>
                </dd>
            </dl>
            <dl class="assign-bd-time-item assign-calendar">
                <dt class="assign-bd-time-tit f-fl">
                    作业截止时间:
                </dt>
                <dd class="assign-bd-time-end assign-bd-time-start f-fl">
                    <form action="">
                        <input placeholder="作业截止时间" class="assign-bd-time-start-date assign-time-selects j-time-end" onclick="laydate({start:laydate.now(0,'YYYY.MM.DD hh:mm'),istime: true, format: 'YYYY.MM.DD hh:mm'})" readonly="">
                        <label class="laydate-icon"></label>
                    </form>
                </dd>
            </dl>
            <dl class="assign-bd-time-item assign-calendar">
                <dt class="assign-bd-time-tit f-fl">
                    <label for="" title="学生提交作业的最晚时间"><span class="c-assign-single-checkbox j-chk-openAnswerTime"></span>设置公布答案时间</label>
                </dt>
                <dd class="assign-bd-time-start f-fl f-hide j-openAnswer">
                    <form action="">
                        <input placeholder="公布答案时间" class="assign-bd-time-start-date assign-time-selects j-time-openAnswer" onclick="laydate({start:laydate.now(0,'YYYY.MM.DD hh:mm'),istime:true, format: 'YYYY.MM.DD hh:mm'})" readonly="">
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
				<li class="assign-bottom-btn-item">确认布置</li>
			</ul>
		</div>
	</div>
	<input type="hidden" id="j_Def_Resource" value='${resourceList}'>
	<leke:pref />
<script  type="x-mustache" id="j-tmpl-resource">
{{#.}}
<li class="item item-{{suffix}}" data-id="{{resId}}" data-type="{{resType}}">
{{resName}}
<div class="delete-btn j-resource-del">×</div>
</li>
{{/.}}
</script>
<script  type="x-mustache"  id="j-tmpl-clazz-group" >
{{#.}}
<li class="assign-bd-student-group-item j-chk-clazz-group" data-groupid="{{groupId}}" data-groupname="{{groupName}}">
<label class="assign-bd-item-checkbox assign-bd-item-checkbox-off " data-groupid="{{groupId}}" data-groupname="{{groupName}}"></label>
<span class="classe-name ">{{groupName}}</span></li>
{{/.}}
</script>
<script  type="x-mustache"  id="j-tmpl-clazz-user" >
{{#.}}
<li class="assign-bd-student-group-item j-chk-clazz-user" data-userid="{{userId}}" data-username="{{userName}}">
<label class="assign-bd-item-checkbox assign-bd-item-checkbox-off " data-userid="{{userId}}" data-username="{{userName}}" ></label>
<span class="classe-name">{{userName}}</span></li>
{{/.}}
</script>

</body>
<script src="${assets }/scripts/common/mobile/common.js"></script>
<script src="${assets }/scripts/common/laydate-rem/laydate.js"></script>
<script type="text/javascript" src="/scripts/repository/service/HistoryServicePad.js"></script>
<script type="text/javascript" src="/scripts/beike/coursewareSearch/pad/coursewareDialog.js"></script>
<script type="text/javascript" src="/scripts/repository/hd/microcourse/microcourse-select.js"></script>
<script type="text/javascript" src="/scripts/paper/pad/leke.control.paper.js"></script> 
<script src="/scripts/hd/homework/assignHomework.js"></script>
</html>