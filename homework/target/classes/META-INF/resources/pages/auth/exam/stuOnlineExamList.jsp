<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>考试列表 - <locale:message code="common.page.header.leke"/></title>
	<%@ include file="/pages/common/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=20171115">
	<style>
		.s-c-yellow {
			color: #FDB314 !important;
		}

		.submitTips {
			display: inline;
		}

		.submitTips .m-tippop {
			display: inline-block;
			margin-left: 8px;
			border: 1px solid #f8785f;
			color: #f8785f !important;
		}

		.submitTips .m-tippop .arrow {
			top: 6px;
		}

		.submitTips .m-tippop em {
			border-right-color: #f8785f;
		}

		.submitTips .m-tippop .msg {
			padding: 2px 5px;
		}
	</style>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp" %>
	<div class="g-mn">
		<c:set var="SUBNAV" value="homework"/>
		<form id="myHomeworkForm" method="post">
			<div class="z-homework-grade z-homework-grade-stu">
				<ul id="myHomeworkContext"></ul>
				<div class="page" id="page"></div>
			</div>
		</form>
	</div>
</div>

<script id="myHomeworkContext_tpl" type="x-handlebars">
	{{#dataList}}
	<li class="item paper-item">
		<div class="title">
			<a data-id="{{homeworkDtlId}}" data-submittime="{{submitTime}}" data-starttime="{{startTime}}" data-closetime="{{closeTime}}" target="_blank" class="name jHomeworkTitle toExam">
				[{{resTypeStr}}] {{homeworkName}}
			</a>
			{{! 学生考试状态--------------------------}}
			{{#cif 'this.submitTime == null'}}
				<div class="paper-state">{{{examStatusStr this}}}</div>
			{{else}}
				{{#cif 'this.bugFixStage == null || this.bugFixStage == 0'}}
					{{#cif 'this.correctTime != null'}}
						<div class="paper-state">已批改</div>
					{{/cif}}
					{{#cif 'this.correctTime == null'}}
						<div class="paper-state">待批改</div>
					{{/cif}}
				{{else}}
					<div class="paper-state">
						{{#cif 'this.bugFixStage == 1'}}待订正{{/cif}}
						{{#cif 'this.bugFixStage == 2'}}已订正{{/cif}}
						{{#cif 'this.bugFixStage == 3'}}订正通过{{/cif}}
					</div>
				{{/cif}}
			{{/cif}}
			<div class="paper-duration">时长：{{{examTime this}}}分钟</div>

			{{! 操作按钮---------------- }}
			{{! 已作废}}
			{{#cif 'this.status == 2'}}
				<div class="c-btns">已作废</div>
			{{else}}
				<div class="c-btns">
					{{#cif 'this.submitTime == null'}}
						<%--<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/homework/doWork.htm?homeworkDtlId={{homeworkDtlId}}">进入考试</a>--%>
						{{#cif 'this.examStatus == 2'}}
							<%--<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/exam/viewWork.htm?homeworkDtlId={{homeworkDtlId}}">查看</a>--%>
							<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/homework/viewWork.htm?homeworkDtlId={{homeworkDtlId}}">查看</a>
						{{else}}
							<a class="u-btn u-btn-nm u-btn-bg-white toExam" data-id="{{homeworkDtlId}}" data-submittime="{{submitTime}}" data-starttime="{{startTime}}" data-closetime="{{closeTime}}">进入考试</a>
						{{/cif}}
					{{else}}
						{{#cif 'this.bugFixStage == 1'}}
							<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/homework/doBugFix.htm?homeworkDtlId={{homeworkDtlId}}">订正</a>
						{{else}}
							<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="/auth/student/homework/viewWork.htm?homeworkDtlId={{homeworkDtlId}}">查看</a>
						{{/cif}}
						{{#cif 'this.correctTime != null && this.homeworkType == 4'}}
							<%--<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="${initParam.diagServerName}/auth/student/homework/analysis2.htm?homeworkId={{homeworkId}}">分析</a>--%>
							<a class="u-btn u-btn-nm u-btn-bg-white" target="_blank" href="${initParam.diagServerName}/auth/common/report/homework/person/{{homeworkId}}-{{homeworkDtlId}}.htm">报告</a>
						{{/cif}}
					{{/cif}}
				</div>
			{{/cif}}
		</div>

		<div class="tips">
			<span><i class="iconfont">&#xf00ca;</i> {{subjectName}}</span>
			<span><i class="iconfont">&#xf00ec;</i> 老师：{{{teacherName}}}</span>
			{{#cif 'this.scoreRate == 1'}}
				<span class="full-paper-marks"></span>
			{{else}}
				<span>
	                <i class="iconfont"></i>
		            {{#cif 'this.correctTime != null'}}
			            分数：{{num this.score '#0.0' '--'}}
		                {{#cif 'this.score != null'}}
							{{correctStatusStr}}
		                {{/cif}}
		            {{/cif}}
	            </span>
			{{/cif}}
			<p class="time">考试开始时间：{{startTimeStr}}</p>
			<div class="state">
				<%--{{#cif 'this.isOpenAnswer'}} <span class="u-state-label">已公布答案</span> {{/cif}}
				{{#cif 'this.isSelfCheck'}} <span class="u-state-label">不作批改</span> {{/cif}}--%>
			</div>
		</div>
	</li>
	{{/dataList}}


</script>
<leke:pref/>
<script>
	seajs.use('homework/exam/stuOnlineExamList');
</script>
</body>
</html>
