<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/common/global.css">
<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">

<title>备课</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<form id="form" action="/auth/provost/teachingMonitor/beike/exportBeikeDtlData.htm">
	<%@ include file="/pages/header/header.jsp"%>
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
					<div class="title">备课统计<i class="iconfont" id="j_iconfont" style="float:right;cursor:pointer;">&#xf00b8;</i></div>
					<div class="c-piechart">
						<div class="horizontal-chart">
							<div id="statInfo" style="height: 320px;"></div>
						</div>
						<div class="legend" id="statInfoBody"></div>
					</div>
				</div>
				<div class="c-zoom">
					<div class="title">备课率走势
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
					<div class="title">
						备课率横向对比
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
					<div class="title">备课率排行</div>
					<div class="c-anarank" style="width:100%">
						<div class="c-anarank__sort">
							<div class="c-anarank__title c-anarank__title--top">备课率前5名</div>
							<ul class="c-anarank__list" id="topFiveBody">
							</ul>
						</div>
						<div class="c-anarank__sort">
							<div class="c-anarank__title c-anarank__title--last">备课率后5名</div>
							<ul class="c-anarank__list" id="lastFiveBody">
							</ul>
						</div>
					</div>
				</div>
			</div>
			
			<div class="c-analyse__con" id="dtlData" style="display: none">
				<div class="title">
                    <div class="c-analyse__operate">
                       	<input type="submit" id="export" value="导出" class="u-btn u-btn-nm u-btn-bg-orange"/>
                    </div>
                </div>
				<div class="m-table m-table-center m-table-fixed">
					<table>
						<thead>
							<tr>
								<th width="6%">序号</th>
								<th width="8%">姓名 </th>
								<th width="8%">学科</th>
								<th width="13%" id="preparedLessonRate" class="m-sorting m-sorting-desc">备课率（实备/应备） <i></i></th>
								<th width="10%" id="earlyPreparedLessonRate" class="m-sorting">提前备课率/数 <i></i></th>
								<th width="10%" id="tempPreparedLessonRate" class="m-sorting">临时备课率/数 <i></i></th>
								<th width="10%" id="teachPlanLessonRate" class="m-sorting">备教案率/数 <i></i></th>
								<th width="10%" id="cwLessonRate" class="m-sorting">备课件率/数 <i></i></th>
								<th width="10%" id="mcLessonRate" class="m-sorting">备微课率/数 <i></i> </th>
								<th width="10%" id="hwLessonRate" class="m-sorting">备作业率/数 <i></i></th>
								<th width="8%">操作 <i></i>
								</th>
							</tr>
						</thead>
						<tbody id="jtbodyData"></tbody>
					</table>
				</div>
				<div id="jTablePage"/>
				</div>
				
			</div>
		</div>
	</div>
	<input type="hidden" id="startDate" name="startDate" />
	<input type="hidden" id="endDate" name="endDate" />
	<input type="hidden" id="trendType" name="trendType" value="day"/>
	<input type="hidden" id="compType" name="compType" value="grade"/>
	<input type="hidden" id="orderAttr" name="orderAttr"/>
	<input type="hidden" id="orderType" name="orderType"/>
	<input type="hidden" id="queryType" name="queryType" value="beike"/>
	</form>
</body>

<script id="gradeTpl" type="x-mustache">
{{#gradeList}}
	<option value="{{gradeId}}">{{gradeName}}</option>
{{/gradeList}}
</script>

<script id="subjectTpl" type="x-mustache">
{{#subjectList}}
	<option value="{{subjectId}}">{{subjectName}}</option>
{{/subjectList}}
</script>

<script id="statInfoTpl" type="x-mustache">
{{#statInfo}}
    <p class="disc">
		实上课<span class="s-green">{{actualLesson}}</span>堂，实备课<span
			class="s-green">{{preparedLesson}}</span>堂，备课率<span class="s-green">{{preparedLessonRate}}%</span>
	</p>
	<p>
		<i style="background-color:#619eed"></i>提前备课<span class="s-green">{{earlyPreparedLesson}}</span>堂，占比<span
			class="s-green">{{earlyPreparedLessonRate}}%</span>
	</p>
	<p>
		<i style="background-color:#ffcb6b"></i>临时备课<span class="s-green">{{tempPreparedLesson}}</span>堂，占比<span
			class="s-green">{{tempPreparedLessonRate}}%</span>
	</p>
	<p>
		<i style="background-color:#ff6666"></i>未备课<span class="s-green">{{notPreparedLesson}}</span>堂，占比<span
			class="s-green">{{notPreparedLessonRate}}%</span>
	</p>
{{/statInfo}}
</script>

<script id="topFiveTpl" type="x-mustache">
{{#topFiveList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">{{preparedLessonRate}}</div>
		<div>{{teacherName}}</div>
	</li>
{{/topFiveList}}
</script>

<script id="lastFiveTpl" type="x-mustache">
{{#lastFiveList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">{{preparedLessonRate}}</div>
		<div>{{teacherName}}</div>
	</li>
{{/lastFiveList}}
</script>

<script id="dtlTpl" type="x-mustache">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td>{{teacherName}}</td>
    	<td>{{subjectName}}</td>
    	<td>{{preparedLessonRate}}%（{{preparedLesson}}/{{actualLesson}}）</td>
    	<td>{{earlyPreparedLessonRate}}%/{{earlyPreparedLesson}}</td>
    	<td>{{tempPreparedLessonRate}}%/{{tempPreparedLesson}}</td>
    	<td>{{teachPlanLessonRate}}%/{{teachPlanLesson}}</td>
    	<td>{{cwLessonRate}}%/{{cwLesson}}</td>
    	<td>{{mcLessonRate}}%/{{mcLesson}}</td>
    	<td>{{hwLessonRate}}%/{{hwLesson}}</td>
        <td class="operation">
           <a href="/auth/provost/teachingMonitor/beike/toShowLessonDtlOfTeacher.htm?startDate={{startDate}}&endDate={{endDate}}&teacherId={{teacherId}}&teacherName={{teacherName}}" target="_blank">查看</a>
        </td>
    </tr>
{{/dataList}}
</script>

<script src="/scripts/diag/common/echart3.5.js"></script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/teachingMonitor/beikeRateStat');
</script>
</html>