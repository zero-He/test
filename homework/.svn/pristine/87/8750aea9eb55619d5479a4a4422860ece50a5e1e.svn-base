<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=${_t}">
	<title>考试列表 - <locale:message code="common.page.header.leke"/></title>
	<%@ include file="/pages/common/meta.jsp" %>
	<style type="text/css">
		.c-btns * {
			vertical-align: middle;
		}
	</style>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp" %>
	<div class="g-mn">
		<div class="m-tab">
			<ul>
				<li><a href="${initParam.homeworkServerName}/auth/teacher/homework/homeworkList.htm">作业</a></li>
				<li class="active"><a href="${initParam.homeworkServerName}/auth/teacher/exam/teaOnlineExamList.htm?spm=101003">考试列表</a></li>
				<c:if test="${currentSchoolNature eq 1 }">
				<li><a href="${initParam.homeworkServerName}/auth/teacher/homework/vacationHomeworkList.htm?spm=101003">寒暑假作业</a></li>
				</c:if>
				<c:if test="${!empty isEnglish}">
					<li><a href="${initParam.voiceServerName}/auth/teacher/pc/successAssignedHomworkList.htm?spm=101003">英语口语</a></li>
				</c:if>
			</ul>
			<div class="operation">
				<a class="u-btn u-btn-nm u-btn-bg-orange" target="_blank" href="${initParam.homeworkServerName}/auth/teacher/exam/assign/index.htm">发布考试</a>
				<a class="u-btn u-btn-nm u-btn-bg-orange" target="_blank" href="${ctx}/auth/teacher/homework/dumpedHomeworkList.htm?spm=101003">回收站</a>
				<c:if test="${isOfflineSheet}">
					<a class="u-btn u-btn-nm u-btn-bg-orange" target="_blank" href="${initParam.homeworkServerName}/auth/teacher/sheet/taskList.htm">上传记录</a>
				</c:if>
			</div>
		</div>

		<form action="" method="post" id="formPage">
			<!-- 作业批改 -->
			<div class="m-search-box">
				<!-- 班级 -->
				<label class="title">班级：</label>
				<input type="text" name="className" class="u-ipt u-ipt-mn"/>
				<label class="title">试卷标题：</label>
				<input type="text" name="homeworkName" class="u-ipt u-ipt-mn">

				<button class="u-btn u-btn-nm u-btn-bg-turquoise" id="bHomeworkList">查询</button>
			</div>

			<div class="z-homework-grade z-homework-grade-teach">
				<ul id="homeworkListContext"></ul>
				<div class="page" id="divPage">
					<div class="f-hide tips f-tac " id="f_emptyDataContainer">未能查询到相关考试~</div>
				</div>
			</div>
		</form>
	</div>
</div>

<script id="homeworkContext_tpl" type="x-handlebars">
	{{#dataList}}
	<li class="item paper-item">
	    <div class="title">
			{{#cif 'this.paperId != null'}}
             <a href="${initParam.paperServerName}/auth/common/paper/view.htm?paperId={{paperId}}" target="_blank"  class="name">[{{resTypeStr}}] {{homeworkName}}</a>
			{{/cif}}
			{{#cif 'this.paperId == null'}}
             <span class="name">[{{resTypeStr}}] {{homeworkName}}</span>
			{{/cif}}
		    <span class="class-name" title="{{className}}">{{classNameStr}}</span>
		    <span class="class-time" >时长：{{examTime}}分钟</span>

	        <div class="c-btns">
				{{#cif 'this.status != 2'}}
						<%--1.未开始、2.已结束、3.正在考试--%>
						{{#cif 'this.examStatus == 1'}}
							<div class="m-btns">
								<div class="init-btn">
									<a href="${ctx}/auth/teacher/exam/teaOnlineExamDetail.htm?homeworkId={{homeworkId}}" target="_blank" class="link">查看</a><b><i></i></b>
								</div>
								<menu>
									<li><a class="jEdit" data-id="{{homeworkId}}" data-starttime="{{startTime}}">修改时间</a></li>
									<li><a class="jCancel" data-id="{{homeworkId}}">作废</a></li>
								</menu>
							</div>
						{{/cif}}
						{{#cif 'this.examStatus == 2'}}
							{{#cif 'this.correctNum > 0 && this.homeworkType != 6'}}
								<a class="u-btn u-btn-nm u-btn-bg-white" href="${initParam.diagServerName}/auth/common/report/homework/{{homeworkId}}.htm" target="_blank">查看报告</a>
							{{/cif}}
							<div class="m-btns">
								<div class="init-btn">
									<a href="${ctx}/auth/teacher/exam/teaOnlineExamDetail.htm?homeworkId={{homeworkId}}" target="_blank" class="link">
										<%--{{#cif 'this.correctNum < this.finishNum'}} 批改 {{else}} 查看 {{/cif}}--%>
										{{#cif 'this.finishNum > 0'}} 批改 {{else}} 查看 {{/cif}}
									</a><b><i></i></b>
								</div>
								{{#cif 'this.bugFixNum > this.reviewNum'}}
									<menu>
										<li>
											<a href="${ctx}/auth/teacher/homework/reCorrectHomeworkDetail.htm?homeworkId={{homeworkId}}&isExam=true" target="_blank" class="link">复批</a>
										</li>
									</menu>
								{{/cif}}
							</div>
						{{/cif}}
						{{#cif 'this.examStatus == 3'}}
							<div class="m-btns">
								<div class="init-btn">
									<a href="${ctx}/auth/teacher/exam/teaOnlineExamDetail.htm?homeworkId={{homeworkId}}" target="_blank" class="link">
										{{#cif 'this.finishNum > 0'}} 批改 {{else}} 查看 {{/cif}}
									</a><b><i></i></b>
								</div>
							</div>
						{{/cif}}
				{{/cif}}
				<!-- 作废作业的情况 -->
				{{#cif 'this.status == 2'}}已作废{{/cif}}
	        </div>
			<%--考试状态--%>
			<div class="onorno">
				{{#cif 'this.examStatus == 1'}}未开始{{/cif}}
				{{#cif 'this.examStatus == 2'}}已结束{{/cif}}
				{{#cif 'this.examStatus == 3'}}正在考试{{/cif}}
			</div>
		</div>

	    <div class="tips">
			<span>批改进度：<b class="cur">{{correctNumStr}}</b>/{{finishNumStr}}</span>
			<span>平均分：{{avgScore}}</span>
			<p class="time">考试开始时间：{{startTimeStr}}</p>
			<div class="state">
				{{#cif 'this.status != 2'}}
				    {{#cif '!this.isOpenAnswer'}}
				        <a class="u-state-label j_openAnswer" data-homeworkid="{{homeworkId}}">公布答案</a>
					{{else}}
						<span class="u-state-label clicked">已公布答案</span>
				    {{/cif}}
				{{/cif}}
			</div>
	    </div>
	</li>
	{{/dataList}}
</script>

<script id="jHomeworkEditTpl" type="text/x-handlebars">
	<div class="m-form f-ml80">
		<ul>
			<li>
				<label class="title">开始时间：</label>
				<div class="con">
					<input type="text" id="jEditStartTime" class="u-ipt u-ipt-lg" readonly="readonly">
				</div>
			</li>
		</ul>
	</div>
</script>

<script type="text/javascript">
	seajs.use('homework/exam/teaOnlineExamList');
</script>

</body>
</html>