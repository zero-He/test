<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/common/global.css">
<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">

<title>作业</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<form id="form" action="/auth/provost/teachingMonitor/homework/exportCorrectHWDtlData.htm">
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
					<div class="title">作业布置与批改统计</div>
					<div class="c-piechart">
						<div class="horizontal-chart">
							<div id="assignInfo" style="height: 320px;"></div>
						</div>
						<div class="legend" id="assignInfoBody"></div>
						<div class="horizontal-chart">
							<div id="correctInfo" style="height: 320px;"></div>
						</div>
						<div class="legend" id="correctInfoBody"></div>
					</div>
				</div>
					<div class="c-zoom">
						<div class="title">老师批改走势
						<div class="c-analyse__operate">
							<div class="c-timeswitch c-timeswitch--triple" id="trend">
	                            <a id="byDay" class="c-timeswitch__item c-timeswitch__item--cur">日</a>
	                            <a id="byWeek" class="c-timeswitch__item">周</a>
	                            <a id="byMonth" class="c-timeswitch__item">月</a>
	                        </div>
                        </div>
						</div>

						<div class="c-piechart">
							<div class="horizontal-chart" style="height: 400px !important">
								<div id="trendInfo" style="height: 400px !important; width: 1170px"></div>
							</div>
						</div>
					</div>
					<div class="c-zoom">
						<div class="title">
							老师批改横向对比
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
							<div class="horizontal-chart" style="height: 400px !important">
								<div id="compInfo" style="height: 400px !important; width: 1170px"></div>
							</div>
						</div>
					</div>
					<div class="c-zoom">
						<div class="title">老师批改排行</div>
						<div class="c-anarank" style="width:100%">
							<div class="c-anarank__sort">
								<div class="c-anarank__title c-anarank__title--top">老师批改率前5名</div>
								<ul class="c-anarank__list" id="topFiveBody">
								</ul>
							</div>
							<div class="c-anarank__sort">
								<div class="c-anarank__title c-anarank__title--last">老师批改率后5名</div>
								<ul class="c-anarank__list" id="lastFiveBody">
								</ul>
							</div>
						</div>
						<div class="c-anarank" style="width:100%">
							<div class="c-anarank__sort">
								<div class="c-anarank__title c-anarank__title--top">老师批改人份前5名</div>
								<ul class="c-anarank__list" id="topFiveNumBody">
								</ul>
							</div>
							<div class="c-anarank__sort">
								<div class="c-anarank__title c-anarank__title--last">老师批改人份后5名</div>
								<ul class="c-anarank__list" id="lastFiveNumBody">
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
								<th width="15%" id="assignNum" class="m-sorting">布置作业份数（纯客观/主观）<i></i></th>
								<th width="15%" id="autoCorrectNum" class="m-sorting m-sorting-desc">系统批改人份（占比） <i></i></th>
								<th width="15%" id="teacherCorrectNum" class="m-sorting">老师批改人份（占比） <i></i></th>
								<th width="15%" id="studentCorrectNum" class="m-sorting">学生互批人份（占比） <i></i></th>
								<th width="15%" id="notCorrectNumRate" class="m-sorting">未批改人份（占比） <i></i></th>
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
	<input type="hidden" id="queryType" name="queryType" value="homework"/>
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

<script id="assignInfoTpl" type="x-mustache">
{{#statInfo}}
    <div class="header">作业布置</div>
    <p class="disc">
		<span class="s-green">{{teacherNum}}</span>名老师，共布置班级作业<span class="s-green">{{assignNum}}</span>份，平均每堂课<span class="s-green">{{avgHWPerLesson}}</span>份，
			每师<span class="s-green">{{avgHWPerTeacher}}</span>份
	</p>
	<p>
		<i style="background-color:#1fb5ab"></i>纯客观题作业<span class="s-green">{{objectiveNum}}</span>份，占比<span
			class="s-green">{{objectiveNumRate}}%</span>
	</p>
	<p>
		<i style="background-color:#24bdee"></i>含主观题作业<span class="s-green">{{subjectiveNum}}</span>份，占比<span
			class="s-green">{{subjectiveNumRate}}%</span>
	</p>
{{/statInfo}}
</script>

<script id="correctInfoTpl" type="x-mustache">
{{#statInfo}}
    <div class="header">作业批改</div>
    <p class="disc">
		应批改作业<span class="s-green">{{shouldCorrectNum}}</span>人份，实批改<span
			class="s-green">{{actualCorrectNum}}</span>人份，批改率<span class="s-green">{{correctNumRate}}%</span>&nbsp;&nbsp;<i class="iconfont" id="j_iconfont" style="cursor:pointer;">&#xf00b8;</i>
	</p>
	<p>
		<i style="background-color:#619eed"></i>老师批改<span class="s-green">{{teacherCorrectNum}}</span>人份，占比<span
			class="s-green">{{teacherCorrectNumRate}}%</span>
	</p>
	<p>
		<i style="background-color:#ffcb6b"></i>学生互批<span class="s-green">{{studentCorrectNum}}</span>人份，占比<span
			class="s-green">{{studentCorrectNumRate}}%</span>
	</p>
	<p>
		<i style="background-color:#1fb5ab"></i>系统批改<span class="s-green">{{autoCorrectNum}}</span>人份，占比<span
			class="s-green">{{autoCorrectNumRate}}%</span>
	</p>
	<p>
		<i style="background-color:#ff6666"></i>未批改<span class="s-green">{{notCorrectNum}}</span>人份，占比<span
			class="s-green">{{notCorrectNumRate}}%</span>
	</p>
{{/statInfo}}
</script>

<script id="topFiveTpl" type="x-handlebars">
{{#topFiveList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">{{correctNumRate}}</div>
		<div>{{teacherName}}</div>
	</li>
{{/topFiveList}}
</script>

<script id="lastFiveTpl" type="x-handlebars">
{{#lastFiveList}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">{{correctNumRate}}</div>
		<div>{{teacherName}}</div>
	</li>
{{/lastFiveList}}
</script>

<script id="topFiveNumTpl" type="x-handlebars">
{{#topFiveNumList}}
    {{#cif 'this.teacherName == "--"'}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">/</div>
		<div>{{teacherName}}</div>
	</li>
    {{else}}
	<li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--top">{{teacherCorrectNum}}</div>
		<div>{{teacherName}}</div>
	</li>
    {{/cif}}
{{/topFiveNumList}}
</script>

<script id="lastFiveNumTpl" type="x-handlebars">
{{#lastFiveNumList}}
    {{#cif 'this.teacherName == "--"'}}
    <li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">/</div>
		<div>{{teacherName}}</div>
	</li>
    {{else}}
	<li class="c-anarank__item">
		<div class="c-anarank__score c-anarank__score--last">{{teacherCorrectNum}}</div>
		<div>{{teacherName}}</div>
	</li>
    {{/cif}}
{{/lastFiveNumList}}
</script>

<script id="dtlTpl" type="x-mustache">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td>{{teacherName}}</td>
    	<td>{{subjectName}}</td>
    	<td>{{assignNum}}（{{objectiveNum}}/{{subjectiveNum}}）</td>
    	<td>{{autoCorrectNum}}（{{autoCorrectNumRate}}%）</td>
    	<td>{{teacherCorrectNum}}（{{teacherCorrectNumRate}}%）</td>
    	<td>{{studentCorrectNum}}（{{studentCorrectNumRate}}%）</td>
    	<td>{{notCorrectNum}}（{{notCorrectNumRate}}%）</td>
    </tr>
{{/dataList}}
</script>

<script src="/scripts/diag/common/echart3.5.js"></script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/teachingMonitor/correctHWStat');
</script>
</html>