<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>作业</title>
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
					<li class="c-typeswitch__item active"><a href="/auth/provost/studentMonitor/homeworkAnalyse/homeworkAnalysePage.htm">作业</a></li>
					<li class="c-typeswitch__item"><a href="/auth/provost/studentMonitor/readyAnalyse/readyAnalysePage.htm">预习</a></li>
					<li class="c-typeswitch__item"><a href="/auth/provost/studentMonitor/activeLearningAnalyse/activeLearningAnalysePage.htm">自主学习</a></li>
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
				<div class="title">作业提交统计</div>
				<div class="c-piechart">
					<div class="horizontal-chart">
						<div id="statInfo" style="height: 320px;"></div>
					</div>
					<div class="legend" id="statInfoBody"></div>
				</div>
			</div>
			<div class="c-zoom">
				<div class="title">按时提交率走势
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
				<div class="title">按时提交率对比
					<div class="c-analyse__operate">
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
				<div class="title">按时提交率排行</div>
				<div class="c-anarank" style="width:100%">
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--top">按时提交率前5名</div>
						<ul class="c-anarank__list" id="topFiveBody">
						</ul>
					</div>
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--last">按时提交率后5名</div>
						<ul class="c-anarank__list" id="lastFiveBody">
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
						<th width="10%" id="stuHomeworkNum" class="m-sorting">应提交作业份数<i></i></th>
						<th width="12%" id="submitNum" class="m-sorting m-sorting-asc">按时提交率（份）<i></i></th>
						<th width="12%" id="lateSubmitNum" class="m-sorting">延后补交率（份）<i></i></th>
						<th width="10%" id="noSubmitNum" class="m-sorting">未提交率（份）<i></i></th>
						<th width="8%">操作<i></i></th>
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
    <p class="disc">
		共学生<span class="s-green">{{studentTotalNum}}</span>人，共需完成班级作业<span class="s-green">{{classHomeworkNum}}</span>份
	</p>
	<p>
		<i style="background-color:#619eed"></i>按时提交<span class="s-green">{{submitNum}}</span>人份，占比<span class="s-green">
		{{#cif 'this.submitPro == 100'}}
			100%
		{{else}}
			{{submitPro}}%
		{{/cif}}
		</span>;
	</p>
	<p>
		<i style="background-color:#ffcb6b"></i>延后补交<span class="s-green">{{lateSubmitNum}}</span>人份，占比<span class="s-green">
		{{#cif 'this.lateSubmitPro == 100'}}
			100%
		{{else}}
			{{lateSubmitPro}}%
		{{/cif}}
		</span>;
	</p>
	<p>
		<i style="background-color:#ff6666"></i>未提交<span class="s-green">{{noSubmitNum}}</span>人份，占比<span class="s-green">
		{{#cif 'this.noSubmitPro == 100'}}
			100%
		{{else}}
			{{noSubmitPro}}%
		{{/cif}}
		</span>;
	</p>
{{/countBean}}
</script>

<script id="topFiveTpl" type="x-handlebars">
{{#rankFrontBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">
			{{#cif 'this.studentName != "" && this.studentName != null'}}
				{{totalLevel}}%
			{{else}}
				/
			{{/cif}}
		</div>
		<div>
			{{#cif 'this.studentName != "" && this.studentName != null'}}
				{{studentName}}
			{{else}}
				--
			{{/cif}}
		</div>
	</li>
{{/rankFrontBeanList}}
</script>

<script id="lastFiveTpl" type="x-handlebars">
{{#rankBackBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">
			{{#cif 'this.studentName != "" && this.studentName != null'}}
				{{totalLevel}}%
			{{else}}
				/
			{{/cif}}
		</div>
		<div>
			{{#cif 'this.studentName != "" && this.studentName != null'}}
				{{studentName}}
			{{else}}
				--
			{{/cif}}
		</div>
	</li>
{{/rankBackBeanList}}
</script>

<script id="dtlTpl" type="x-handlebars">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td>{{className}}</td>
    	<td>{{studentName}}</td>
    	<td>{{stuHomeworkNum}}</td>
    	<td>{{submitPro}}%（{{submitNum}}）</td>
    	<td>{{lateSubmitPro}}%（{{lateSubmitNum}}）</td>
    	<td>{{noSubmitPro}}%（{{noSubmitNum}}）</td>
    	<td class="operation">
    	    <a id="operation" data-id="{{studentId}}" data-name="{{studentName}}" target="_blank">查看详情</a>
        </td>
    </tr>
{{/dataList}}
</script>

<script src="/scripts/diag/common/echart3.5.js"></script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/studentMonitor/homeworkAnalysePage');
</script>
</html>