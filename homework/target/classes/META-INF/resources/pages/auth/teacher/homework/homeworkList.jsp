<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
 <link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=20171115">
<title>作业 - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<style type="text/css">
.c-btns *{
    vertical-align: middle;
}
</style>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="m-tab">
			<ul>
				<li class="active"><a>作业</a></li>
				<li><a href="${initParam.homeworkServerName}/auth/teacher/exam/teaOnlineExamList.htm?spm=101003">考试列表</a></li>
				<c:if test="${currentSchoolNature eq 1 }">
				<li><a href="${initParam.homeworkServerName}/auth/teacher/homework/vacationHomeworkList.htm?spm=101003">寒暑假作业</a></li>
				</c:if>
                <c:if test="${!empty isEnglish}">
                     <li><a href="${initParam.voiceServerName}/auth/teacher/pc/successAssignedHomworkList.htm?spm=101003">英语口语</a></li>
                </c:if>
			</ul>
			<div class="operation">
				<a class="u-btn u-btn-nm u-btn-bg-orange" target="_blank" href="${initParam.homeworkServerName}/auth/teacher/assign/index.htm">布置作业</a>
				<a class="u-btn u-btn-nm u-btn-bg-orange" target="_blank" href="${ctx}/auth/teacher/homework/dumpedHomeworkList.htm?spm=101003">回收站</a>
				<c:if test="${isOfflineSheet}">
				<a class="u-btn u-btn-nm u-btn-bg-orange" target="_blank" href="${initParam.homeworkServerName}/auth/teacher/sheet/taskList.htm">上传记录</a>
				</c:if>
			</div>
		</div>

		<form action="" method="post" id="formPage" autocomplete="off">
			<!-- 隐藏域 -->
			<input type="hidden" name="classId" id="j_classId" value="${classId }" />
			<input type="hidden" name="homeworkFinishFlag" id="f_homeFinishFlag"
			<c:if test="${ord eq 1 }"> value="all"</c:if>
			<c:if test="${ord eq null }">value="unfinished"</c:if>
			<c:if test="${ord eq 2 }">value="refinished"</c:if>
			 />
	        <!-- 作业批改 -->
	        <div class="m-search-box">
	        	<div class="tab">
	                <ul>
						<li <c:if test="${ord eq null }"> class="cur"</c:if> ><a href="javascript:;" class="J_homeFinish" data-flag="unfinished">待批改</a></li>
						<li <c:if test="${ord eq 2 }"> class="cur"</c:if> ><a href="javascript:;" class="J_homeFinish" data-flag="refinished">复批作业</a></li>
						<li <c:if test="${ord eq 1 }"> class="cur"</c:if> ><a href="javascript:;" class="J_homeFinish " data-flag="all">全部作业</a></li>
	                </ul>
	            </div>
				<div class="operation">
					<span class="u-retake-homework-li">
						<c:if test="${currentSchoolNature != 3}">
						<a href="${ctx}/auth/teacher/assignLogList.htm?spm=101003" target="_blank" class="u-retake-homework-btn">代人布置</a>
						</c:if>
					</span>
				</div>
	        	<!-- 班级 -->
	            <c:if test="${ classId eq null}">
	            <label for="" class="title">班级：</label>
	            	<input type="text" name="className"  class="u-ipt u-ipt-mn" />
	            </c:if>
	            <c:if test="${classId != null }">
	             	<label for="" class="title">班级：${className}</label>
					<input type="hidden" id="j_className" value="${className }" />
	            </c:if>
	            <label for="" class="title"><locale:message code="homework.homework.homeworktype" />：</label>
	            <select id="jHomeworkType" name="homeworkType" class="u-select u-select-mn"></select>

	            <label for="" class="title">资源类型：</label>
	            <select id="jResType" name="resType" class="u-select u-select-mn">
	            	<option value="0">所有类型</option>
	            	<option value="1">课件</option>
	            	<option value="2">微课</option>
	            	<option value="3">试卷</option>
	            </select>

	            <label for="" class="title"><locale:message code="homework.homework.homeworktitle" />：</label>
	            <input type="text" name="homeworkName" class="u-ipt u-ipt-mn">

	            <button class="u-btn u-btn-nm u-btn-bg-turquoise" id="bHomeworkList"><locale:message code="homework.common.search" /></button>
	        </div>

	        <div class="z-homework-grade z-homework-grade-teach">
				<ul id="homeworkListContext"></ul>
				<div class="page" id="divPage">
				<div class="f-hide tips f-tac " id="f_emptyDataContainer">未能查询到相关作业~</div>
				</div>
	        </div>
		</form>
	</div>
</div>

<script id="homeworkContext_tpl" type="text/x-handlebars-template">
	{{#dataList}}
	<li class="item">
	    <div class="title">
				{{#cif 'this.paperId != null'}}
	       		 <a
					{{#cif 'this.resType == 1'}}
						href="${initParam.beikeServerName}/auth/common/courseware/preview.htm?coursewareId={{paperId}}"
					{{/cif}}
					{{#cif 'this.resType == 2'}}
						href="${initParam.beikeServerName}/auth/common/microcourse/preview.htm?microcourseId={{paperId}}"
					{{/cif}}
					{{#cif 'this.resType == 3'}}
						href="${initParam.paperServerName}/auth/common/paper/view.htm?paperId={{paperId}}"
					{{/cif}}
					target="_blank"  class="name">[{{resTypeStr}}] {{homeworkName}}
	 			 </a>
				{{/cif}}
				{{#cif 'this.paperId == null'}}
	       		 <span class="name-nolink">[{{homeworkTypeStr}}] {{homeworkName}}</span>
				{{/cif}}

			{{#cif 'this.flag != "refinished"'}}
		    <span class="class-name" title="{{className}}">{{classNameStr}}</span>
		    {{/cif}}
	        <div class="c-btns">
		        {{#cif 'this.flag != "refinished"'}}
			        {{#cif 'this.resType != 3'}}
				        <div class="m-btns">
				        <div class="init-btn">
					        <a href="${ctx}/auth/teacher/homework/homeworkDetail.htm?homeworkId={{homeworkId}}" target="_blank" class="link">查看</a><b><i></i></b>
				        </div>
						{{^isBeikeRes}}
				        <menu>
						<li><a class="jCancel" data-id="{{homeworkId}}"><locale:message code="homework.homework.correct.invalid"/></a></li>
						</menu>
						{{/isBeikeRes}}
						</div>
					{{/cif}}
					{{#cif 'this.resType == 3'}}
						{{#cif 'this.status != 2'}}
							{{#cif 'this.correctNum > 0 && this.homeworkType != 6'}}
								<a class="u-btn u-btn-nm u-btn-bg-white" href="${initParam.diagServerName}/auth/common/report/homework/{{homeworkId}}.htm" target="_blank">查看报告</a>
							{{/cif}}
							<div class="m-btns">
								<div class="init-btn">
									<a href="${ctx}/auth/teacher/homework/homeworkDetail.htm?homeworkId={{homeworkId}}" target="_blank" class="link">
										{{#cif '!this.isSelfCheck && this.correctNum < this.finishNum'}} 批改 {{else}} 查看 {{/cif}}
									</a><b><i></i></b>
								</div>
								<menu>
									{{#cif 'this.finishNum > 0 && this.homeworkType != 6'}}
									<li><a href="${initParam.diagServerName}/auth/teacher/homework/analysis2.htm?homeworkId={{homeworkId}}" target="_blank" class="link"><locale:message code="homework.common.analysis" /></a></li>

									{{/cif}}
									{{^isBeikeRes}}
									<li><a class="jEdit" data-id="{{homeworkId}}" data-starttime="{{startTime}}" data-closetime="{{closeTime}}"><locale:message code="homework.homework.correct.modification"/></a></li>
									<li><a class="jCancel" data-id="{{homeworkId}}"><locale:message code="homework.homework.correct.invalid"/></a></li>
									{{/isBeikeRes}}
									<li><a class="j-btn-cuijiao" data-id="{{homeworkId}}" >催交作业</a></li>
								</menu>
							</div>
						{{/cif}}
						<!-- 作废作业的情况 -->
						{{#cif 'this.status == 2'}}
						<locale:message code="homework.homework.correct.already"/>
						{{/cif}}
					{{/cif}}
		        {{/cif}}
				{{#cif 'this.flag == "refinished"'}}
					<div class="m-btns m-btns-one c-btns">
						<div class="init-btn">
							 <a href="${ctx}/auth/teacher/homework/reCorrectHomeworkDetail.htm?homeworkId={{homeworkId}}&isExam=false" target="_blank" class="link">详情</a><b><i></i></b>
						</div>
					</div>
				{{/cif}}
	        </div>
	    </div>

	    <div class="tips">
		    {{#cif 'this.flag != "refinished"'}}
				{{#cif 'this.resType != 3'}}
		            <span>已查看：<b class="cur">{{finishNumStr}}</b>/{{totalNum}}</span>
		            <p class="time">开始/截止时间：{{startTimeStr}} / {{closeTimeStr}}</p>
				{{/cif}}
				{{#cif 'this.resType == 3'}}
		            <span>已上交：<b class="cur">{{finishNumStr}}</b>/{{totalNum}}</span>
		            <span>已批改：
						<b class="cur">{{correctNumStr}}</b>/{{finishNumStr}}
		            </span>
		            <span>平均分：{{avgScore}}</span>
		            <p class="time">开始/截止时间：{{startTimeStr}} / {{closeTimeStr}}</p>
		            <div class="state">
					{{#cif 'this.status != 2'}}
		                {{#cif '!this.isOpenAnswer'}}
		                <a class="u-state-label j_openAnswer" data-homeworkid="{{homeworkId}}">公布答案</a>
						{{else}}<span class="u-state-label clicked">已公布答案</span>
		                {{/cif}}
					{{/cif}}
		            </div>
				{{/cif}}
		    {{/cif}}
		    {{#cif 'this.flag == "refinished"'}}
			    <span>待复批：<b class="cur">{{corrects}}</b></span>
		        <p class="time">开始/截止时间：{{startTimeStr}} / {{closeTimeStr}}</p>
		    {{/cif}}
	    </div>
	</li>
	{{/dataList}}
</script>

<script id="jHomeworkEditTpl" type="x-mustache">
<div class="m-form f-ml80">
	<ul>
		<li>
			<label class="title"><locale:message code="homework.homework.startTime" />：</label>
			<div class="con">
				<input type="text" id="jEditStartTime" class="u-ipt u-ipt-lg" readonly="readonly">
			</div>
		</li>
		<li>
			<label class="title"><locale:message code="homework.homework.closeTime" />：</label>
			<div class="con">
				<input type="text" id="jEditCloseTime" class="u-ipt u-ipt-lg" readonly="readonly">
			</div>
		</li>
	</ul>
</div>
</script>

<script type="text/javascript">
	seajs.use('homework/homework/homeworkList');
</script>

</body>
</html>