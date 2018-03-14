<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">
	<title>考勤</title>
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
				<a> 概览 </a>
			</div>
			<div class="c-analyse__sitem" id="detail">
				<a> 明细 </a>
			</div>
		</div>

		<div class="c-analyse__con" id="statData">
			<div class="c-zoom">
				<div class="title">考勤统计</div>
				<div class="c-piechart">
					<div class="horizontal-chart">
						<div id="statInfo" style="height: 320px;"></div>
					</div>
					<div class="legend" id="statInfoBody"></div>
				</div>
			</div>
			<div class="c-zoom">
				<div class="title">考勤走势
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
				<div class="title">考勤对比
					<div class="c-analyse__operate">
						<div class="c-timeswitch c-timeswitch--double" id="compAll">
							<a class="c-timeswitch__item c-timeswitch__item--cur" id="grade">年级</a>
							<a class="c-timeswitch__item" id="all_subject">学科</a>
						</div>
						<div class="c-timeswitch c-timeswitch--double" id="compGrade" style="display:none">
							<a class="c-timeswitch__item c-timeswitch__item--cur" id="clazz">班级</a>
							<a class="c-timeswitch__item" id="grade_subject">学科</a>
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
				<div class="title">考勤排行</div>
				<div class="c-anarank" style="width:100%">
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--top">全勤前5名</div>
						<ul class="c-anarank__list" id="topFiveBody">
						</ul>
					</div>
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--last">全勤后5名</div>
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
						<th width="8%">姓名</th>
						<th width="8%">学科</th>
						<th width="10%" id="allOn" class="m-sorting m-sorting-asc">全勤<i></i></th>
						<th width="10%" id="late" class="m-sorting">迟到<i></i></th>
						<th width="10%" id="early" class="m-sorting">早退<i></i></th>
						<th width="10%" id="lateAndEarly" class="m-sorting">迟到且早退<i></i></th>
						<th width="10%" id="notClassNum" class="m-sorting">缺勤<i></i></th>
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
	<input type="hidden" id="queryType" name="queryType" value="attendance"/>
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

<script id="statInfoTpl" type="x-handlebars">
{{#countBean}}
    <p class="disc">
		<span class="s-green">{{teacherTotalNum}}</span>名老师，应上课<span class="s-green">{{totalClassNum}}</span>堂，
		实上课<span class="s-green">{{realClassNum}}</span>堂，上课率<span class="s-green">
		{{#cif 'this.classPro == 100'}}
			100%
		{{else}}
			{{classPro}}%
		{{/cif}}
		</span>
	</p>
	<p>
		<i style="background-color:#1fb5ab"></i>全勤<span class="s-green">{{allOn}}</span>堂，占比<span class="s-green">
		{{#cif 'this.allOnPro == 100'}}
			100%
		{{else}}
			{{allOnPro}}%
		{{/cif}}
		</span>;
	</p>
	<p>
		<i style="background-color:#ff9900"></i>迟到<span class="s-green">{{late}}</span>堂，占比<span class="s-green">
		{{#cif 'this.latePro == 100'}}
			100%
		{{else}}
			{{latePro}}%
		{{/cif}}
		</span>;
	</p>
	<p>
		<i style="background-color:#619eed"></i>早退<span class="s-green">{{early}}</span>堂，占比<span class="s-green">
		{{#cif 'this.earlyPro == 100'}}
			100%
		{{else}}
			{{earlyPro}}%
		{{/cif}}
		</span>;
	</p>
	<p>
		<i style="background-color:#ffcb6b"></i>迟到且早退<span class="s-green">{{lateAndEarly}}</span>堂，占比<span class="s-green">
		{{#cif 'this.lateAndEarlyPro == 100'}}
			100%
		{{else}}
			{{lateAndEarlyPro}}%
		{{/cif}}
		</span>;
	</p>
	<p>
		<i style="background-color:#ff6666"></i>缺勤<span class="s-green">{{notClassNum}}</span>堂，占比<span class="s-green">
		{{#cif 'this.notClassNumPro == 100'}}
			100%
		{{else}}
			{{notClassNumPro}}%
		{{/cif}}
		</span>;
	</p>
{{/countBean}}
</script>

<script id="topFiveTpl" type="x-handlebars">
{{#rankFrontBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">
			{{#cif 'this.totalCount == 0 && this.teacherName == ""'}}
				/
			{{else}}
				{{totalCount}}
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

<script id="lastFiveTpl" type="x-handlebars">
{{#rankBackBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">
			{{#cif 'this.totalCount == 0 && this.teacherName == ""'}}
				/
			{{else}}
				{{totalCount}}
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

<script id="dtlTpl" type="x-handlebars">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td>{{teacherName}}</td>
    	<td>{{subjectName}}</td>
    	<td>{{allOn}}</td>
    	<td>{{late}}</td>
    	<td>{{early}}</td>
    	<td>{{lateAndEarly}}</td>
    	<td>{{notClassNum}}</td>
    </tr>
{{/dataList}}
</script>

<script src="/scripts/diag/common/echart3.5.js"></script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/teachingMonitor/attendancePage');
</script>
</html>