<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">
	<title>资源</title>
	<%@ include file="/pages/common/meta.jsp" %>
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="m-bg"></div>
<div class="g-bd">
	<div class="g-mn">
		<%@ include file="./teachingMonitorComm.jsp"%>

		<div class="c-analyse__switcher">
			<div class="c-analyse__sitem c-analyse__sitem--cur" id="stat">
				<a href="#"> 概览 </a>
			</div>
			<div class="c-analyse__sitem" id="detail">
				<a href="#"> 明细 </a>
			</div>
		</div>

		<div class="c-analyse__con" id="statData">
			<div class="c-zoom">
				<div class="title">资源统计</div>
				<div class="c-piechart">
					<div class="horizontal-chart">
						<div id="statInfo" style="height: 320px; width: 1170px;"></div>
					</div>
				</div>
			</div>
			<div class="c-zoom">
				<div class="title">资源走势
					<div class="c-analyse__operate">
						<div class="c-timeswitch c-timeswitch--triple" id="trend">
							<a id="byDay" class="c-timeswitch__item c-timeswitch__item--cur">日</a>
							<a id="byWeek" class="c-timeswitch__item">周</a>
							<a id="byMonth" class="c-timeswitch__item">月</a>
						</div>
					</div>
				</div>
				<div class="c-piechart">
					<div class="horizontal-chart">
						<div id="trendInfo" style="height: 320px; width: 1170px;"></div>
					</div>
				</div>
			</div>
			<div class="c-zoom">
				<div class="title">资源对比
					<div class="c-analyse__operate">
						<div class="c-timeswitch c-timeswitch--double" id="compAll">
							<a class="c-timeswitch__item c-timeswitch__item--cur" id="grade">年级</a>
							<a class="c-timeswitch__item" id="all_subject">学科</a>
						</div>
						<div class="c-timeswitch c-timeswitch--double" id="compGrade" style="display:none">
							<%--<a class="c-timeswitch__item c-timeswitch__item--cur" id="clazz">班级</a>--%>
							<%--<a class="c-timeswitch__item c-timeswitch__item--cur" id="grade_subject">学科</a>--%>
						</div>
					</div>
				</div>
				<div class="c-piechart">
					<div class="horizontal-chart">
						<div id="compInfo" style="height: 320px; width: 1170px;"></div>
					</div>
				</div>
			</div>
			<div class="c-zoom">
				<div class="title">资源排行</div>
				<div class="c-anarank" style="width:100%">
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--top">创建资源数前5名</div>
						<ul class="c-anarank__list" id="topFiveBodyByCreate">
						</ul>
					</div>
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--last">创建资源数后5名</div>
						<ul class="c-anarank__list" id="lastFiveBodyByCreate">
						</ul>
					</div>
				</div>

				<div class="c-anarank" style="width:100%">
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--top">分享资源数前5名</div>
						<ul class="c-anarank__list" id="topFiveBodyByShare">
						</ul>
					</div>
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--last">分享资源数后5名</div>
						<ul class="c-anarank__list" id="lastFiveBodyByShare">
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="c-analyse__con" id="dtlData" style="display: none">
			<div class="title">
				<div class="c-analyse__operate">
					<input type="button" id="export" value="导出" class="u-btn u-btn-nm u-btn-bg-orange"/>
				</div>
			</div>
			<div class="m-table m-table-center m-table-fixed">
				<table>
					<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="8%">姓名</th>
						<th width="8%">学科</th>
						<th width="10%" id="createCount" class="m-sorting m-sorting-asc">创建总数（课件/微课/试卷/习题/备课包）<i></i></th>
						<th width="10%" id="shareCount" class="m-sorting">分享总数（课件/微课/试卷/习题/备课包）<i></i></th>
					</tr>
					</thead>
					<tbody id="jtbodyData"></tbody>
				</table>
			</div>
			<div id="jTablePage"></div>
		</div>
	</div>
</div>
<form id="form">
	<input type="hidden" id="subId" name="subjectId"/>
	<input type="hidden" id="graId" name="gradeId"/>
	<input type="hidden" id="startDate" name="startDate"/>
	<input type="hidden" id="endDate" name="endDate"/>
	<input type="hidden" id="trendType" name="trendType" value="day"/>
	<input type="hidden" id="compType" name="compType" value="grade"/>
	<input type="hidden" id="orderAttr" name="orderAttr"/>
	<input type="hidden" id="orderType" name="orderType"/>
	<input type="hidden" id="queryType" name="queryType" value="resource"/>
</form>
</body>

<script id="gradeTpl" type="x-handlebars">
{{#gradeList}}
	<option value="{{gradeId}}">{{gradeName}}</option>
{{/gradeList}}
</script>

<script id="subjectTpl" type="x-handlebars">
{{#subjectList}}
	<option value="{{subjectId}}">{{subjectName}}</option>
{{/subjectList}}
</script>

<script id="topFiveTplByCreate" type="x-handlebars">
{{#rankFrontBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">
			{{#cif 'this.createCount == 0 && this.teacherName == ""'}}
				/
			{{else}}
				{{createCount}}
			{{/cif}}
		</div>
		<div>
			{{#cif 'this.teacherName != ""'}}
				{{teacherName}}
			{{else}}
				--
			{{/cif}}
		</div>
	</li>
{{/rankFrontBeanList}}
</script>

<script id="lastFiveTplByCreate" type="x-handlebars">
{{#rankBackBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">
			{{#cif 'this.createCount == 0 && this.teacherName == ""'}}
				/
			{{else}}
				{{createCount}}
			{{/cif}}
		</div>
		<div>
			{{#cif 'this.teacherName != ""'}}
				{{teacherName}}
			{{else}}
				--
			{{/cif}}
		</div>
	</li>
{{/rankBackBeanList}}
</script>

<script id="topFiveTplByShare" type="x-handlebars">
{{#shareRankFrontBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">
			{{#cif 'this.shareCount == 0 && this.teacherName == ""'}}
				/
			{{else}}
				{{shareCount}}
			{{/cif}}
		</div>
		<div>
			{{#cif 'this.teacherName != ""'}}
				{{teacherName}}
			{{else}}
				--
			{{/cif}}
		</div>
	</li>
{{/shareRankFrontBeanList}}
</script>

<script id="lastFiveTplByShare" type="x-handlebars">
{{#shareRankBackBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">
			{{#cif 'this.shareCount == 0 && this.teacherName == ""'}}
				/
			{{else}}
				{{shareCount}}
			{{/cif}}
		</div>
		<div>
			{{#cif 'this.teacherName != ""'}}
				{{teacherName}}
			{{else}}
				--
			{{/cif}}
		</div>
	</li>
{{/shareRankBackBeanList}}
</script>

<script id="dtlTpl" type="x-handlebars">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td>{{teacherName}}</td>
    	<td>{{subjectName}}</td>
    	<td>{{createCount}}（{{createCoursewareCount}}/{{createMicrocourseCount}}/{{createPaperCount}}/{{createQuestionCount}}/{{createBeikePkgCount}}）</td>
    	<td>{{shareCount}}（{{shareCoursewareCount}}/{{shareMicrocourseCount}}/{{sharePaperCount}}/{{shareQuestionCount}}/{{shareBeikePkgCount}}）</td>
    </tr>
{{/dataList}}
</script>

<script src="/scripts/diag/common/echart3.5.js"></script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/teachingMonitor/resourcePage');
</script>
</html>