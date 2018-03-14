<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>班级作业 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/homework/homework.css?t=20171115">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
		<c:if test="${empty clazzs}">
			<div class="m-tips"><i></i><span>您暂无行政班级，此处仅显示行政班级数据！</span></div>
		</c:if>
		<c:if test="${not empty clazzs}">
			<div>
				<ul id="jULid" class="m-tab ">
					<c:forEach items="${clazzs }" var ="item" varStatus="status">
						<li <c:if test="${status.index eq 0 }">class="active"</c:if> data-id="${item.classId }" data-cname="${item.className }"><a>${item.className }</a></li>
					</c:forEach>
				</ul>
			</div>
			<form action="" method="post" id="formPage" autocomplete="off">
			<div class="m-search-box">
				<div class="item">
					<!-- 课后作业 -->
					<label class="title">作业类型：</label>
					<select id="jHomeworkType" name="homeworkType" class="u-select u-select-nm"></select>
					<label for="" class="title">资源类型：</label>
		            <select id="jResType" name="resType" class="u-select u-select-nm">
		            	<option value="0">所有类型</option>
		            	<option value="1">课件</option>
		            	<option value="2">微课</option>
		            	<option value="3">试卷</option>
		            </select>
					<label class="title">学科：</label>
					<select id="jSubject" class="u-select  u-select-nm"></select>
					<label class="title">老师：</label>
					<input type="text" name="teacherName" class="u-ipt u-ipt-sm">
					<!-- 行政班级 -->
					<input type="hidden" id="jClassType" name="classType" value="1" />
					<input type="hidden" id="jSubjectId" name="subjectId" value="0" />
					<input type="hidden" name="classId" id="jClassId" />
					<input type="hidden" name="fileName" id="jClassName" value="班级作业" />
				</div>
				<div class="item">
					<label class="title">作业标题：</label>
					<input type="text" name="homeworkName" class="u-ipt u-ipt-nm">
					<label class="title">作业截止时间：</label>
					<input name="startTime" id="jStartTime" class="u-ipt u-ipt-sm Wdate" />
					至
					<input name="closeTime" id="jSubmitTime" class="u-ipt u-ipt-sm Wdate" />
					<input class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询" id="bHomeworkList" />
					<div class="operation">
						<a class="u-btn u-btn-at u-btn-bg-orange" target="_blank" id="jExportStatusData">导出上交详情</a>
						<a class="u-btn u-btn-at u-btn-bg-orange" target="_blank" id="jExportData">导出作业详情</a>
					</div>
				</div>
				</div>
				<div class="z-homework-grade z-homework-grade-teach">
					<ul id="homeworkListContext">
					</ul>
				</div>
				<div class="page" id="divPage"></div>
			</form>
		</c:if>
		</div>
	</div>
<c:if test="${not empty clazzs}">
<script id="homeworkContext_tpl" type="text/handlebars">
{{#dataList}} 
	<li class="item">
		<div class="title">
			{{^isPushWork}}
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
			{{/isPushWork}}
			{{#isPushWork}}
			<span class="name-nolink">[{{resTypeStr}}]{{homeworkName}}</span>
			{{/isPushWork}}
			<span title="{{className}}" class="class-name">{{className}}</span>
			<div class="m-btns c-btns {{^isShowAnaly}} m-btns-one {{/isShowAnaly}}">
			{{^isInvalid}}
				<div class="init-btn">
					<a href="${ctx}/auth/provost/homework/homeworkDetail.htm?homeworkId={{homeworkId}}" target="_blank" class="link">查看</a>  <b><i></i></b>
				</div>
				{{#cif 'this.resType == 3'}}
					{{#isShowAnaly}}
					<menu>
						<li>
						<a href="${initParam.diagServerName}/auth/common/report/homework/{{homeworkId}}.htm" target="_blank" class="link">报告</a>
						</li>
						<li>
						<a href="${initParam.diagServerName}/auth/classTeacher/homework/analysis2.htm?homeworkId={{homeworkId}}" target="_blank" class="link">分析</a>
						</li>
					</menu>
					{{/isShowAnaly}}
				{{/cif}}
			{{/isInvalid}}
			{{#isInvalid}}<locale:message code="homework.homework.correct.already"/>{{/isInvalid}}
			</div>
		</div>
	
		<div class="tips">
			<span>学科：{{subjectName}}</span>
			{{#cif 'this.resType != 3'}}
	        	<span>已查看：<b class="cur">{{finishNumStr}}</b>/{{totalNum}}</span>
	        	<p class="time">开始/截止时间：{{startTimeStr}} / {{closeTimeStr}}</p>
			{{/cif}}
			{{#cif 'this.resType == 3'}}
			<span>
				已上交： <b class="cur">{{finishNumStr}}</b>
				/{{totalNum}}
			</span>
			<span>
				已批改：
				<b class="cur">{{correctNumStr}}</b>
				/{{finishNumStr}}
			</span>
			<span>平均分：{{avgScore}}</span>
			<p class="time">开始/截止时间：{{{startCloseTime}}}</p>
			{{/cif}}
			<div class="state classhomeworktype">
			 <span>{{teacherName}}</span>
			</div>
		</div>
	</li> 
	{{/dataList}}
</script>
<leke:pref/>
<script type="text/javascript">
	seajs.use('homework/homework/classTeacher/homeworkList');
</script>
</c:if>
</body>
</html>