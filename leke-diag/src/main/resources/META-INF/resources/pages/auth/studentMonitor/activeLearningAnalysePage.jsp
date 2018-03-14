<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>自主学习</title>
	<%@ include file="/pages/common/meta.jsp" %>
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="m-bg"></div>
<div class="g-bd">
	<div class="g-mn">
		<div class="m-search-box c-analyse15__homework-search">
			<div>
				<select class="u-select u-select-sm c-analyse15__homework-select" id="gradeId">
				</select>
				<select class="u-select u-select-sm c-analyse15__homework-select" id="classId">
				</select>
				<select class="u-select u-select-sm c-analyse15__homework-select" id="subjectId">
				</select>
				<div class="c-timerange">
					<span id="dateText"></span>
					<ul class="c-timerange__list" id="ulDate">
						<li class="c-timerange__item" id="term"></li>
						<li class="c-timerange__item" id="month"></li>
						<li class="c-timerange__item" id="week"></li>
						<li class="c-timerange__item" id="custom">
							<input type="text" id="customStart" class="u-ipt u-ipt-mn Wdate"/>~<input type="text" id="customEnd" class="u-ipt u-ipt-mn Wdate"/>
							<input type="button" value="确定" id="submit" class="u-btn u-btn-at u-btn-bg-turquoise">
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="m-search-box">
			<div class="c-typeswitch">
				<ul id="ulSwitch15">
					<li class="c-typeswitch__item"><a href="/auth/provost/studentMonitor/homeworkAnalyse/homeworkAnalysePage.htm">作业</a></li>
					<li class="c-typeswitch__item"><a href="/auth/provost/studentMonitor/readyAnalyse/readyAnalysePage.htm">预习</a></li>
					<li class="c-typeswitch__item active"><a href="/auth/provost/studentMonitor/activeLearningAnalyse/activeLearningAnalysePage.htm">自主学习</a></li>
					<li class="c-typeswitch__item"><a href="/auth/provost/studentMonitor/otherAnalyse/otherAnalysePage.htm">其它</a></li>
				</ul>
			</div>
		</div>

		<div class="c-analyse__switcher">
			<div class="c-analyse__sitem c-analyse__sitem--cur" id="stat">
				<a> 概览 </a>
			</div>
			<div class="c-analyse__sitem" id="detail">
				<a> 明细 </a>
			</div>
		</div>

		<div class="c-analyse__con" id="statData">
			<div class="c-zoom">
				<div class="title">自主学习统计</div>
				<div class="c-piechart">
					<div class="c-analyse15__table-maincon c-analyse15__walkerclearfix" id="statInfoBody"></div>
				</div>
			</div>

			<div class="c-zoom">
				<div class="title">自主学习走势
					<div class="c-analyse__operate">
						<select class="u-select u-select-nm" id="trendSelect">
							<option value="1">人均学习知识点数</option>
							<option value="2">人均刷题数</option>
						</select>

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
				<div class="title">自主学习对比
					<div class="c-analyse__operate">
						<select class="u-select u-select-nm" id="compareSelect">
							<option value="1">人均学习知识点数</option>
							<option value="2">人均刷题数</option>
						</select>
						<div class="c-timeswitch c-timeswitch--double" id="compGrade">
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
				<div class="title">自主学习排行</div>
				<div class="c-anarank" style="width:100%">
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--top">学习知识点数前5名</div>
						<ul class="c-anarank__list" id="topFiveBody">
						</ul>
					</div>
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--last">学习知识点数后5名</div>
						<ul class="c-anarank__list" id="lastFiveBody">
						</ul>
					</div>
				</div>
				<div class="c-anarank" style="width:100%">
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--top">刷题数前5名</div>
						<ul class="c-anarank__list" id="topFiveBodyByQuestion">
						</ul>
					</div>
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--last">刷题数后5名</div>
						<ul class="c-anarank__list" id="lastFiveBodyByQuestion">
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
						<th width="8%">班级</th>
						<th width="6%">姓名</th>
						<th width="10%" id="learningNum" class="m-sorting m-sorting-asc">学习知识点数<i></i></th>
						<th width="12%" id="questionNum" class="m-sorting">刷题数<i></i></th>
						<th width="12%" id="rightPro" class="m-sorting">刷题正确率<i></i></th>
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
	<input type="hidden" id="claId" name="classId"/>
	<input type="hidden" id="graId" name="gradeId"/>
	<input type="hidden" id="startDate" name="startDate"/>
	<input type="hidden" id="endDate" name="endDate"/>
	<input type="hidden" id="trendType" name="trendType" value="day"/>
	<input type="hidden" id="compType" name="compType" value="clazz"/>
	<input type="hidden" id="orderAttr" name="orderAttr"/>
	<input type="hidden" id="orderType" name="orderType"/>
</form>
</body>

<script id="gradeTpl" type="x-handlebars">
{{#gradeList}}
	<option value="{{gradeId}}">{{gradeName}}</option>
{{/gradeList}}
</script>

<script id="classTpl" type="x-handlebars">
{{#classList}}
	<option value="{{classId}}">{{className}}</option>
{{/classList}}
</script>

<script id="subjectTpl" type="x-handlebars">
{{#subjectList}}
	<option value="{{subjectId}}">{{subjectName}}</option>
{{/subjectList}}
</script>

<script id="statInfoTpl" type="x-handlebars">
{{#countBean}}
	<div class="c-analyse15__learnoverview-student">
		<div class="c-analyse15__learnoverview-type">
			<span class="c-analyse15__learnoverview-typeicon c-analyse15__learnoverview-studenticon"></span>学生
		</div>
		<div class="c-analyse15__learnoverview-numwrap">
			<span class="c-analyse15__learnoverview-num">{{studentTotalNum}}</span><br>
			<span class="c-analyse15__learnoverview-numwrod">总人数</span>
		</div>
	</div>
	<div class="c-analyse15__learnoverview-weiketong">
		<div class="c-analyse15__learnoverview-type">
			<span class="c-analyse15__learnoverview-typeicon c-analyse15__learnoverview-weiketongicon"></span>微课通
		</div>
		<div class="c-analyse15__learnoverview-numwrap">
			<div class="c-analyse15__learnoverview-weiketongline">
				<span class="c-analyse15__learnoverview-num">{{learningNum}}</span><br>
				<span class="c-analyse15__learnoverview-numwrod">学习人数</span>
			</div>
			<div>
				<span class="c-analyse15__learnoverview-num">{{avgLearningNum}}</span><br>
				<span class="c-analyse15__learnoverview-numwrod">人均学习知识点数</span>
			</div>
		</div>
	</div>
	<div class="c-analyse15__learnoverview-brush">
		<div class="c-analyse15__learnoverview-type">
			<span class="c-analyse15__learnoverview-typeicon c-analyse15__learnoverview-brushicon"></span>刷题王
		</div>
		<div class="c-analyse15__learnoverview-numwrap">
			<div class="c-analyse15__learnoverview-brushline">
				<span class="c-analyse15__learnoverview-num">{{avgQuestionNum}}</span><br>
				<span class="c-analyse15__learnoverview-numwrod">人均刷题数</span>
			</div>
			<div>
				<span class="c-analyse15__learnoverview-num">{{rightPro}}%</span><br>
				<span class="c-analyse15__learnoverview-numwrod">平均正确率</span>
			</div>
		</div>
	</div>
{{/countBean}}
</script>

<script id="topFiveTpl" type="x-handlebars">
{{#rankFrontBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">
			{{#cif 'this.totalCount == 0'}}
				/
			{{else}}
				{{totalCount}}
			{{/cif}}
		</div>
		<div>
			{{#cif 'this.totalCount == 0'}}
				--
			{{else}}
				{{studentName}}
			{{/cif}}
		</div>
	</li>
{{/rankFrontBeanList}}
</script>

<script id="lastFiveTpl" type="x-handlebars">
{{#rankBackBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">
			{{#cif 'this.totalCount == 0 && this.studentName == ""'}}
				/
			{{else}}
				{{totalCount}}
			{{/cif}}
		</div>
		<div>
			{{#cif 'this.studentName != ""'}}
				{{studentName}}
			{{else}}
				--
			{{/cif}}
		</div>
	</li>
{{/rankBackBeanList}}
</script>

<script id="topFiveTplByQuestion" type="x-handlebars">
{{#shareRankFrontBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">
			{{#cif 'this.totalCount == 0'}}
				/
			{{else}}
				{{totalCount}}
			{{/cif}}
		</div>
		<div>
			{{#cif 'this.totalCount == 0'}}
				--
			{{else}}
				{{studentName}}
			{{/cif}}
		</div>
	</li>
{{/shareRankFrontBeanList}}
</script>

<script id="lastFiveTplByQuestion" type="x-handlebars">
{{#shareRankBackBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">
			{{#cif 'this.totalCount == 0 && this.studentName == ""'}}
				/
			{{else}}
				{{totalCount}}
			{{/cif}}
		</div>
		<div>
			{{#cif 'this.studentName != ""'}}
				{{studentName}}
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
    	<td>{{className}}</td>
    	<td>{{studentName}}</td>
    	<td>{{learningNum}}</td>
    	<td>{{questionNum}}</td>
    	<td>{{rightPro}}%</td>
    </tr>
{{/dataList}}
</script>

<script src="/scripts/diag/common/echart3.5.js"></script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/studentMonitor/activeLearningAnalysePage');
</script>
</html>