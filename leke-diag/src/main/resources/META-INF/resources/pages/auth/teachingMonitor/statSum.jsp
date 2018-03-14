<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/common/global.css">
<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">

<title>统计总表</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
		<%@ include file="/pages/header/header.jsp"%>
		<div class="m-bg"></div>
		<div class="g-bd">
			<div class="g-mn">
				<div id="jTopNav" class="m-tab">
					<ul>
						<li><a href="/auth/provost/teachingMonitor/lessonAttend/toShowLessonAttendRateStat.htm">教学常规</a></li>
						<li class="active"><a href="/auth/provost/teachingMonitor/statsum/toShowStatSum.htm">统计总表</a></li>
					</ul>
				</div>
				<div class="m-search-box">
					<div>
						<select class="u-select u-select-nm" id="gradeId">
							<c:forEach var="grade" items="${gradeList}">
								<option value="${grade.gradeId}">${grade.gradeName}</option>
	                        </c:forEach>
						</select> 
						<select class="u-select u-select-nm" id="subjectId">
							<c:forEach var="subject" items="${subjectList}">
								<option value="${subject.subjectId}">${subject.subjectName}</option>
	                        </c:forEach>
						</select>
						<div class="c-timerange">
							<span id="dateText"></span>
							<ul class="c-timerange__list" id="ulDate">
								<li class="c-timerange__item" id="term"></li>
								<li class="c-timerange__item" id="month"></li>
								<li class="c-timerange__item" id="week"></li>
								<li class="c-timerange__item" id="custom">
								<input type="text" id="customStart" class="u-ipt u-ipt-mn Wdate" />~<input type="text" id="customEnd" class="u-ipt u-ipt-mn Wdate" />
                                <input type="button" value="确定" id="submit" class="u-btn u-btn-at u-btn-bg-turquoise">
								</li>
							</ul>
						</div>&nbsp;&nbsp;
						<input type="text" id="teacherName" placeholder="输入教师姓名" class="u-ipt u-ipt-nm"/>
						<input type="button" value="搜索" id="search" name="search" class="u-btn u-btn-at u-btn-bg-turquoise"/>
						
						<input type="button" id="exportBase" name="exportBase" value="导出基础数据"
							class="u-btn u-btn-at u-btn-bg-orange" style="margin-left: 170px"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" id="exportAll" name="exportAll" value="导出全部数据"
							class="u-btn u-btn-at u-btn-bg-orange" />
					</div>
				</div>

				<div class="c-analyse__con">
					
					<div class="m-table m-table-center m-table-scroll">
						<table>
							<thead>
								<tr>
									<th>序号</th>
									<th>姓名</th>
									<th>学科</th>
									<th id="attendLessonRate" class="m-sorting">上课率（实上/应上）<i></i></th>
									<th id="preparedLessonRate"
										class="m-sorting m-sorting-desc">备课率（实备/应备）<i></i></th>
									<th id="assignNum" class="m-sorting">布置作业（纯客观/含主观）<i></i></th>
									<th id="teacherCorrectNum" class="m-sorting">老师批改人份（占比）
										<i></i>
									</th>
									<th id="resolveRate" class="m-sorting">解答率（已答/待答）
										<i></i>
									</th>
									<th id="createCount" class="m-sorting">创建/分享资源
										<i></i>
									</th>
									<th id="allOnPro" class="m-sorting">全勤率 <i></i></th>
									<th id="avgTotalCount" class="m-sorting">课堂互动 <i></i></th>
									<th id="totalLevel" class="m-sorting">评分/好评率<i></i></th>
									<th>操作</th>
									</th>
								</tr>
							</thead>
							<tbody id="jtbodyData"></tbody>
						</table>
					</div>
				</div>
				<div id="jTablePage" />
			</div>
		</div>
	<form id="form" action="/auth/provost/teachingMonitor/statsum/exportStatSumData.htm">
		<input type="hidden" id="startDate" name="startDate" /> 
		<input type="hidden" id="endDate" name="endDate" /> 
		<input type="hidden" id="subId" name="subjectId"/>
	    <input type="hidden" id="graId" name="gradeId"/>
		<input type="hidden" id="orderAttr" name="orderAttr" value=""/> 
		<input type="hidden" id="orderType" name="orderType" value=""/>
		<input type="hidden" id="exportType" name="exportType" value="base"/>
		<input type="hidden" id="teaName" name="teacherName"/>
	</form>
</body>

<script id="dtlTpl" type="x-handlebars">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td>{{teacherName}}</td>
    	<td>{{subjectName}}</td>
        {{#if lessonAttendInfo.totalLesson}}
    	<td>{{lessonAttendInfo.attendLessonRate}}%（{{lessonAttendInfo.attendLesson}}/{{lessonAttendInfo.totalLesson}}）</td>
        {{else}}
    	<td>0（0/0）</td>
        {{/if}}

        {{#if beikeRate.actualLesson}}
    	<td>{{beikeRate.preparedLessonRate}}%（{{beikeRate.preparedLesson}}/{{beikeRate.actualLesson}}）</td>
        {{else}}
    	<td>0（0/0）</td>
        {{/if}}

        {{#if correctHW.assignNum}}
    	<td>{{correctHW.assignNum}}（{{correctHW.objectiveNum}}/{{correctHW.subjectiveNum}}）</td>
        {{else}}
    	<td>0（0/0）</td>
        {{/if}}

    	<td>{{correctHW.teacherCorrectNum}}（{{correctHW.teacherCorrectNumRate}}%）</td>

        {{#if resolveDoubt.totalDoubt}}
    	<td>{{resolveDoubt.resolveRate}}%（{{resolveDoubt.resolveDoubt}}/{{resolveDoubt.totalDoubt}}）</td>
        {{else}}
    	<td>0（0/0）</td>
        {{/if}}

    	<td>{{resourceDetail.createCount}}/{{resourceDetail.shareCount}}</td>
    	<td>{{attendancePro.allOnPro}}%</td>
		
		{{#cif 'this.interactDetail.avgTotalCount != 0'}}
    	<td>{{interactDetail.avgTotalCount}}</td>
		{{else}}
    	<td>-</td>
        {{/cif}}
		
		{{#if evaluateDetail.totalLevel}}
    	<td>{{evaluateDetail.totalLevel}}/{{evaluateDetail.goodPro}}%</td>
		{{else}}
    	<td>-/{{evaluateDetail.goodPro}}%</td>
        {{/if}}		

    	<td class="operation"><a href="/auth/provost/teachingMonitor/statsum/findStatSumDtl.htm?startDate={{startDate}}&endDate={{endDate}}&gradeId={{gradeId}}&subjectId={{subjectId}}&teacherId={{teacherId}}" target="_blank">详情</a></td>
    </tr>
{{/dataList}}
</script>

<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/teachingMonitor/statSum');
</script>
</html>