<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="${assets}/styles/common/global.css">
	<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">
	<title>课堂评价</title>
	<%@ include file="/pages/common/meta.jsp" %>
	<style type="text/css">
		.totalLessionNum{
			height: 30px;
			line-height: 30px;
			text-align: center;
			width: 100%;
			font-size: 18px;
		}
	</style>
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
				<div class="title">评价统计</div>
				<div id="statInfoBody" class="c-piechart">

				</div>
			</div>
			<div class="c-zoom">
				<div class="title">评分走势
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
				<div class="title">评分对比
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
				<div class="title">评分排行</div>
				<div class="c-anarank" style="width:100%">
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--top">评分前5名</div>
						<ul class="c-anarank__list" id="topFiveBody">
						</ul>
					</div>
					<div class="c-anarank__sort">
						<div class="c-anarank__title c-anarank__title--last">评分后5名</div>
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
						<th width="10%" id="lessonNum" class="m-sorting m-sorting-asc">上课堂数<i></i></th>
						<th width="10%" id="totalLevel" class="m-sorting">评价得分<i></i></th>
						<th width="10%" id="good" class="m-sorting">好评（率）<i></i></th>
						<th width="10%" id="center" class="m-sorting">中评（率）<i></i></th>
						<th width="10%" id="poor" class="m-sorting">差评（率）<i></i></th>
						<th width="10%" id="flowerNum" class="m-sorting">鲜花<i></i></th>
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
	<input type="hidden" id="queryType" name="queryType" value="evaluate"/>
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
    <div class="m-evaluation">
        <div class="totalLessionNum">有评价课堂数：<span style="color:#0ba29a;">{{totalLessionNum}}</span></div>
        <div class="ev-scores">
            <div class="sc-sum">
                <p><span>{{avgTotal}}</span>分</p>
                <div class="stars"><em><i style="width:{{avgTotalPro}}%"></i></em></div>
            </div>
            <div class="sc-items">
                <div class="classify">
                    教学效果：
                    <div class="stars"><em><i style="width:{{avgRhythmLevelPro}}%"></i></em></div><span class="fc-orange">{{avgRhythmLevel}}</span>
                </div>
                <div class="classify">
                    教学态度：
                    <div class="stars"><em><i style="width:{{avgProfessionalPro}}%"></i></em></div><span class="fc-orange">{{avgProfessional}}</span>
                </div>
                <div class="classify">
                    课堂互动：
                    <div class="stars"><em><i style="width:{{avgInteractionLevelPro}}%"></i></em></div><span class="fc-orange">{{avgInteractionLevel}}</span>
                </div>
            </div>
        </div>
        <div class="ev-data">
            <div class="data-con">
                <div class="describ"><div>好评率：</div></div>
				<div class="data">{{avgGoodPro}}%</div>
            </div>
			<div class="data-con">
				<div class="describ"><div>每堂鲜花：</div></div>
				<div class="data">{{avgFlowerNum}}</div>
			</div>
        </div>
    </div>
{{/countBean}}
</script>

<script id="topFiveTpl" type="x-handlebars">
{{#rankFrontBeanList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">
			{{#cif 'this.totalLevel == 0 && this.teacherName == ""'}}
				/
			{{else}}
				{{totalLevel}}
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
			{{#cif 'this.totalLevel == 0 && this.teacherName == ""'}}
				/
			{{else}}
				{{totalLevel}}
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
    	<td>{{lessonNum}}</td>
    	<td>{{totalLevel}}</td>
    	<td>{{good}}（{{goodPro}}%）</td>
    	<td>{{center}}（{{centerPro}}%）</td>
    	<td>{{poor}}（{{poorPro}}%）</td>
    	<td>{{flowerNum}}</td>
    </tr>
{{/dataList}}
</script>
<script src="/scripts/diag/common/echart3.5.js"></script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/teachingMonitor/evaluatePage');
</script>
</html>