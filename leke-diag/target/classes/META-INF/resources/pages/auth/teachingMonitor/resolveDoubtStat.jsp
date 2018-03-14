<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/common/global.css">
<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">

<title>乐答</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<form id="form" action="/auth/provost/teachingMonitor/doubt/exportResolveDoubtDtlData.htm">
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
					<div class="title">解答数统计</div>
					<div class="c-piechart">
						<div class="horizontal-chart">
							<div id="statInfo" style="height: 320px;"></div>
						</div>
						<div class="legend" id="statInfoBody"></div>
					</div>
				</div>
				<div class="c-zoom">
					<div class="title">解答数走势
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
						解答数横向对比
						<div class="c-analyse__operate">
							<div class="c-timeswitch c-timeswitch--double" id="compAll">
								<a class="c-timeswitch__item c-timeswitch__item--cur" id="grade">年级</a>
								<a class="c-timeswitch__item" id="all_subject">学科</a>
							</div>
							<div class="c-timeswitch c-timeswitch--double" id="compGrade">
							</div>
						</div>
					</div>

					<div class="c-piechart">
						<div class="horizontal-chart">
							<div id="compInfo"  style="height: 320px; width: 1170px;"></div>
						</div>
					</div>
				</div>
				<div class="c-zoom">
					<div class="title">解答数排行</div>
					<div class="c-anarank" style="width:100%">
						<div class="c-anarank__sort">
							<div class="c-anarank__title c-anarank__title--top">解答数前5名</div>
							<ul class="c-anarank__list" id="topFiveBody">
							</ul>
						</div>
						<div class="c-anarank__sort">
							<div class="c-anarank__title c-anarank__title--last">解答数后5名</div>
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
								<th width="4%">序号</th>
								<th width="8%">姓名 </th>
								<th width="8%">学科</th>
								<th width="10%" id="resolveRate" class="m-sorting m-sorting-desc">解答率<i></i></th>
								<th width="14%" id="resolveDoubt" class="m-sorting">已解答（24小时内/外） <i></i></th>
								<th width="10%" id="notResolveDoubt" class="m-sorting">待解答 <i></i></th>
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
	<input type="hidden" id="queryType" name="queryType" value="doubt"/>
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
		学生提问题<span class="s-green">{{totalDoubt}}</span>个，解答问题<span
			class="s-green">{{resolveDoubt}}</span>个，解答率</br><span class="s-green">{{resolveRate}}%</span>，共<span class="s-green">{{teacherNum}}</span>名老师，师均解答问题
            <span class="s-green">{{teacherAvgResolveRate}}</span>个
	</p>
	<p>
		<i style="background-color:#619eed"></i>24小时内解答<span class="s-green">{{in24HourResolveDoubt}}</span>个，占比<span
			class="s-green">{{in24HourResolveRate}}%</span>
	</p>
	<p>
		<i style="background-color:#ffcb6b"></i>24小时外解答<span class="s-green">{{out24HourResolveDoubt}}</span>个，占比<span
			class="s-green">{{out24HourResolveRate}}%</span>
	</p>
	<p>
		<i style="background-color:#ff6666"></i>待解答<span class="s-green">{{notResolveDoubt}}</span>个，占比<span
			class="s-green">{{notResolveRate}}%</span>
	</p>
{{/statInfo}}
</script>

<script id="topFiveTpl" type="x-mustache">
{{#topFiveList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">{{resolveDoubt}}</div>
		<div>{{teacherName}}</div>
	</li>
{{/topFiveList}}
</script>

<script id="lastFiveTpl" type="x-mustache">
{{#lastFiveList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">{{resolveDoubt}}</div>
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
    	<td>{{resolveRate}}%</td>
    	<td>{{resolveDoubt}}（{{in24HourResolveDoubt}}/{{out24HourResolveDoubt}}）</td>
    	<td>{{notResolveDoubt}}</td>
    </tr>
{{/dataList}}
</script>

<script src="/scripts/diag/common/echart3.5.js"></script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/teachingMonitor/resolveDoubtStat');
</script>
</html>