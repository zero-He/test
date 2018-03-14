<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ include file="/pages/common/cordova.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<title>练习列表</title>
<link rel="stylesheet" href="${assets }/styles/mobile/global.css">
<link rel="stylesheet" href="${assets }/styles/mobile/homework.css">

<style>
.z-mobile-homework{position:static !important;}
</style>
<leke:context />
</head>
<body>					
	<article class="z-mobile-homework">
		<section class="c-headsearch">
			<div class="all j-query-reset">全部</div>
			<div class="gosearch">
				<span class="name" data-defaulted="学科">学科</span>
				<span class="formore"></span>
				<ul class="proviso j-query j-ul-subjectId">
				</ul>
			</div>
			<div class="gosearch">
				<span class="name" data-defaulted="练习时间">练习时间</span>
				<span class="formore"></span>
				<ul class="proviso j-query j-query-time">
					 <li><a data-param="timeRangeDays" data-v="1">最近一天</a></li>
                     <li><a data-param="timeRangeDays" data-v="3">最近三天</a></li>
                     <li><a data-param="timeRangeDays" data-v="7">最近一周 </a></li>
                     <li><a data-param="timeRangeDays" data-v="30">最近一个月</a></li>
				</ul>
			</div>
		</section>
		<section class="c-trainlist"  >
			<div class="j-content">
				<div id="j-body-exercises"></div>
			</div>
		</section>
	</article>

	<leke:pref />
<script type="x-mustache" id="j-tmpl-exercises">
{{#.}}
<ul class="aslip">
	<li><i class="course-sorts course-{{subjectId}}"></i> {{subjectName}}</li>
	<li>
		<p class="f-grey">提交时间</p>
		<p>{{fmtSubmitTime}}</p>
	</li>
	<li>
		<p class="f-grey">
			{{fmtExerciseType}}
		</p>
		<p>{{exerciseName}}</p>
	</li>
	<li>
		<p class="f-grey">正确率</p>
		<p>
			{{{fmtAccuracy}}}
		</p>
	</li>
	<li data-exerciseid="{{exerciseId}}" data-title="{{exerciseName}}" data-type="{{exerciseType}}" >
{{^submitTime}}
<a target="_blank" class="j-exercise-do">继续练习</a>
{{/submitTime}}
{{#submitTime}}
<a target="_blank" class="j-view-report" href="/auth/hd/student/exercise/report.htm?id={{exerciseId}}">练习报告</a>
{{/submitTime}}

	</li>
</ul>
{{/.}}
</script>
	<script src="${assets}/scripts/common/mobile/common.js?_t=20171115"></script>
	<script src="/scripts/hd/exercise/list.js?_t=20171115"></script>
</body>
</html>