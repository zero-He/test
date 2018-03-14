<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>作业管家</title>
    <meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="${assets}/styles/mobile/v1.3.css?_t=20171115">
	<link rel="stylesheet" href="${assets}/styles/mobile/global.css?_t=20171115">
	<link rel="stylesheet" href="${assets}/styles/mobile/homework/homeworkphone.css?_t=${_ts}">
	<style>
		html, body {
			width: auto;
			height: 100%;
		}
		.c-homeworklist {
			box-sizing: border-box;
			height: 100%;
			overflow-y: auto;
		}
		.c-search-box i.delete {
			right: 50px;
		}
	</style>
    <leke:context />
</head>

<body id="jDroploadId">
	<form id="jForm">
		<%--<input type="hidden" id="jNav1" name="homeworkType"/>
		<input type="hidden" id="jNav2" name="isClose"/>--%>
		<input type="hidden" id="jClassId" name="classId"/>
		<input type="hidden" id="jSubjectId" name="subjectId"/>
		<input type="hidden" id="jKeyWords" name="keywords"/>
	</form>
	<article class="c-homeworklist" id="jScrollContEle">
		<nav class="c-search-box">
			<div class="input-wrapper sift-input-wrapper">
				<input id="text" type="search" onkeyup="handleKeyUps(event)" placeholder="请输入作业标题"/>
			</div>
			<i class="search"></i>
			<i class="delete"></i>
			<span class="c-icon-sift"></span>
		</nav>
		<ul class="list-box" id="jScrollArea">

		</ul>
	</article>

    <script id="jListTpl" type="text/x-handlebars-template">
		{{#dataList}}
		<section class="c-workitem">
                <div class="c-workitem-head">
                    <span class="teacher">{{teacherName}}</span>
                    <span class="teacher">{{className}}</span>
					<span class="close-time">{{closeTimeStr}}截止</span>
                </div>
                <div class="c-workitem-body">
	                <div class="icon">
	                    <div class="
						{{#cif 'this.resType == 1'}}
							type-courseware
						{{/cif}}
						{{#cif 'this.resType == 2'}}
							type-microgram
						{{/cif}}
						{{#cif 'this.resType == 3'}}
							type-test-paper
						{{/cif}}"></div>
                    </div>

		            <div class="middle">
		                <div class="name">{{homeworkName}}</div>
			            <div class="flags">
				            <span class="flag-box">{{rawTypeName}}</span>
				            {{#cif 'this.isSelfCheck == true'}}
				            <span class="flag-box">不做批改</span>
				            {{/cif}}
				            {{#cif 'this.isOpenAnswer == true'}}
				            <span class="flag-box">公布答案</span>
				            {{/cif}}
			            </div>
			            {{#cif 'this.resType == 3'}}
				            <div class="submit-time">
					            <span>上交：</span><span>{{finishNum}}/{{totalNum}}</span>&nbsp;&nbsp;&nbsp;
					            <span>批改：</span><span>{{correctNum}}/{{finishNum}}</span>&nbsp;&nbsp;&nbsp;
					            <span>平均分：</span>
					            {{#cif 'this.avgScore == null'}}
					            <span>--</span>
					            {{else}}
					            <span>{{avgScore}}</span>
					            {{/cif}}
				            </div>
			            {{else}}
				            <div class="submit-time">
					            <span>已查看：</span>
					            <span>{{finishNum}}/{{totalNum}}</span>
				            </div>
			            {{/cif}}
		            </div>
                </div>
        </section>
		{{/dataList}}
    </script>
	<script src="${assets}/scripts/common/mobile/zepto.min.js?_t=20171115"></script>
	<script src="/scripts/m/homework/common/dropload.min.js?_t=20171115"></script>
	<script src="${assets}/scripts/common/mobile/common.js"></script>
	<script src="${assets}/scripts/common/handlebars.min.js"></script>
    <script src="/scripts/m/homework/school/classTeacher-worklist.js?_tt=20171115"></script>
	<!--模态层-->
	<article class="modal-body">
		<article class="modal-cover"></article>
		<article class="modal-box">
			<section class="main">
				<!--班级-->
				<ul class="main-list main-list-auto main-list-class">
					<h3 class="tit">班级<i class="pull-icon"></i></h3>
					<div class="class-context"></div>
					<script id="myClassContext_tpl" type="text/x-handlebars-template">
						{{#classList}}
						<li class="item" data-classid = "{{classId}}">
							<p>
								{{className}}
							</p>
						</li>
						{{/classList}}
					</script>
				</ul>
				<!--学科-->
				<ul class="main-list main-list-auto main-list-subject">
					<h3 class="tit">学科<i class="pull-icon"></i></h3>
					<div class="subject-context"></div>
					<script id="mySubjectContext_tpl" type="text/x-handlebars-template">
						{{#subjectList}}
						<li class="item" data-subjectid = "{{subjectId}}">
							<p>
								{{subjectName}}
							</p>
						</li>
						{{/subjectList}}
					</script>
				</ul>
			</section>
		</article>
		<section class="bottom-btn">
			<span class="c-btn c-btn-gray reset-btn">重置</span>
			<span class="c-btn c-btn-green sub-btn">确定</span>
		</section>
	</article>
</body>
</html>