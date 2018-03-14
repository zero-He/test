<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>学生端-作业列表</title>
	<meta name="viewport" content="width=1920">
	<script src="${assets}/scripts/common/mobile/rem.js"></script>
	<link rel="stylesheet" href="${assets}/styles/mobile/global.css"/>
	<link rel="stylesheet" href="${assets}/styles/mobile/homework/homeworkpad.css"/>
	<style>
		html, body{
			height:100%;
		}
		.c-student-work-list{
			box-sizing: border-box;
			height: 100%;
			overflow-y: auto;
		}
	</style>
</head>
<body>
<!--tab页切换-->
<nav class="c-tab-nav">
	<div class="box">
		<section class="item" data-i="all">
			全部
		</section>
		<section class="item item-on" data-i="todoing">
			待完成（<span class="num">${doingTotal}</span>）
		</section>
		<section class="item" data-i="bugfix">
			待订正（<span class="num">${bugfixTotal}</span>）
		</section>
	</div>
</nav>
<form id="jForm">
	<input type="hidden" id="jHomeFinishFlag" name="homeworkFinishFlag" value="todoing"/>
	<input type="hidden" name="subjectId" id="j_subjectId" value="${subjectId}"/>
</form>
<!--作业列表详情-->
<article class="c-teacher-work-list c-student-work-list" id="jScrollContEle">
	<ul class="list-box" id="jScrollArea">

	</ul>
</article>
<script type="text/x-handlebars-template" id="jTpl">
	{{#dataList}}
	<li class="list-item">
		<div class="c-work-type
				{{#if 'this.resType == 1'}}
					c-courseware
				{{/if}}
				{{#if 'this.resType == 2'}}
					c-microgramme
				{{/if}}
				{{#if 'this.resType == 3'}}
					c-text-paper
				{{/if}}
				item"></div>
		<div class="work-name item">
			<p class="name item-p">
				{{homeworkName}}
			</p>
			<p class="item-note-p color-9">
				{{#cif 'this.resType == 3'}}
					<span class="c-work-tips">{{paperTypeStr}}</span>
					{{#cif 'this.status != 2'}}
						{{#cif '!this.openAnswer'}}
						<span class="c-work-tips">公布答案</span>
						{{else}}
						<span class="c-work-tips color-orange border-color-orange">已公布答案</span>
						{{/cif}}
						{{#cif 'this.subjective'}}
							{{#cif '!this.selfCheck' }}
							<span class="c-work-tips">不做批改</span>
							{{else}}
							<span class="c-work-tips color-orange border-color-orange">不做批改</span>
							{{/cif}}
						{{/cif}}
					{{/cif}}
				{{else}}
				<span class="c-work-tips">{{paperTypeStr}}</span>
				{{/cif}}
			</p>
		</div>
		<div class="work-time item">
			<p class="item-p">
				{{teacherName}}
			</p>
			<%--<p class="item-note-p color-9">
				<i class="fix-up-time">{{startTime}}</i>布置
			</p>--%>
		</div>
		<div class="work-num item">
			<p class="item-p">
				提交时间：<i class="study-time">{{submitTime}}</i>
			</p>
			<p class="item-note-p color-9">
				离截止时间还有：<i class="end-time">{{closeTimeStr}}</i>
			</p>
		</div>
		<div class="work-correct item">
			{{#cif 'this.resType==3'}}
				{{#cif 'this.submitTime == null'}}
				<p class="item-p">待批改</p>
				{{else}}
					{{#cif 'this.bugFixStage != null && this.bugFixStage >=1 && this.bugFixStage <=3'}}
						{{#cif 'this.bugFixStage == 1'}}待订正{{/cif}}
						{{#cif 'this.bugFixStage == 2'}}已订正{{/cif}}
						{{#cif 'this.bugFixStage == 3'}}订正通过{{/cif}}
					{{/cif}}
					{{#cif 'this.bugFixStage == null || this.bugFixStage == 0'}}
						{{#cif 'this.correctTime != null'}}已批改{{/cif}}
						{{#cif 'this.correctTime == null'}}待批改{{/cif}}
					{{/cif}}
				{{/cif}}
			{{else}}
				{{#cif 'this.submitStatus==0'}}
				<p class="item-p color-red">未学习</p>
				{{else}}
				<p class="item-p color-green">已学习</p>
				{{/cif}}
			{{/cif}}
			<p class="item-note-p">
				{{#cif 'this.scoreRate==1'}}
				<span class="c-work-tips color-orange border-color-orange">满分</span>
				{{/cif}}
				{{#cif 'this.subTag==1'}}
				<span class="c-work-tips color-orange border-color-orange">迟交</span>
				{{/cif}}
			</p>
		</div>
		<div class="grades-collection">
			<i class="{{cn0}}"></i>
			<i class="{{cn1}}"></i>
			<i class="{{cn2}}"></i>
			<i class="{{cn3}}"></i>
			<i class="{{cn4}}"></i>
			<i class="holiday-work-grade-num holiday-work-grade-grade"></i>
		</div>
		{{#cif 'this.resType==3'}}
				{{#cif 'this.submitTime == null'}}
					<input class="task-li-btn" value="答题" type="button">
				{{else}}
					{{#cif 'this.bugFixStage != null && this.bugFixStage >=1 && this.bugFixStage <=3'}}
						<input type="button" class="task-li-btn get-more" data-o="{{openAnswer}}" data-s="{{subjective}}" data-c="{{selfCheck}}" data-i="{{homeworkId}}" data-dtlid="{{homeworkDtlId}}" data-t="{{resType}}" value="更多">
						{{#cif 'this.bugFixStage == 1'}}
						<input type="button" class="task-li-btn" value="订正">
						{{/cif}}
					{{/cif}}
				{{/cif}}
		{{else}}
			{{#cif 'this.submitStatus==0'}}
				<input class="task-li-btn" value="学习" type="button">
			{{else}}
				<input class="task-li-btn" value="复习" type="button">
			{{/cif}}
		{{/cif}}
	</li>
	{{/dataList}}
</script>
<!--模态窗口-->
<article class="cover"></article>
<article class="modal-box">
	<ul class="btns-list">
		<li class="item" data-m="homeworkReport">
			作业报告
		</li>
		<li class="item item-bottom-radius" data-m="scoreRate">
			得分率
		</li>
		<li class="item">
			取消
		</li>
	</ul>
</article>
</body>
<leke:pref />
<script src="${assets}/scripts/common/mobile/common.js"></script>
<script src="${assets }/scripts/common/handlebars.min.js"></script>
<script src="/scripts/homework/homework/student/studentHomeworkList.js"></script>

<script>

</script>
</html>