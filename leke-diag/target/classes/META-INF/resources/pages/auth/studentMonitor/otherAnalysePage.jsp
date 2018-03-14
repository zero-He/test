<%@ page pageEncoding="UTF-8" %>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>其它</title>
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
				<select style="display:none" id="subjectId"></select>
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
					<li class="c-typeswitch__item"><a href="/auth/provost/studentMonitor/activeLearningAnalyse/activeLearningAnalysePage.htm">自主学习</a></li>
					<li class="c-typeswitch__item active"><a href="/auth/provost/studentMonitor/otherAnalyse/otherAnalysePage.htm">其它</a></li>
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
			<div class="c-analyse15__table-listcon" id="oneFiveBody">

			</div>
		</div>

		<div class="c-analyse__con" id="dtlData" style="display: none">
			<div class="title">
				<div class="c-analyse__operate">
					<input type="button" id="export" value="导出" class="u-btn u-btn-nm u-btn-bg-orange"/>
				</div>
			</div>
			<div class="m-table m-table-center m-table-scroll">
				<table>
					<thead>
						<tr>
							<th width="7%">序号</th>
							<th width="12%">班级</th>
							<th width="10%">姓名</th>
							<th width="12%">消灭/新增错题数<i></i></th>
							<th width="10%" id="graspPro" class="m-sorting m-sorting-asc">消灭错题率<i></i></th>
							<th width="12%">提问数/解答数<i></i></th>
							<th width="10%" id="askSolvePro" class="m-sorting">问题解答率<i></i></th>
							<th width="20%">查看课堂录像数/每次查看录像时长<i></i></th>
							<th width="10%" id="viewSeePro" class="m-sorting">录像查看率<i></i></th>
							<th width="10%" id="createBookNum" class="m-sorting">创建笔记数<i></i></th>
							<th width="15%" id="avgReadNum" class="m-sorting">平均每份笔记查看次数<i></i></th>
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
	<input type="hidden" id="claId" name="classId"/>
	<input type="hidden" id="graId" name="gradeId"/>
	<input type="hidden" id="startDate" name="startDate"/>
	<input type="hidden" id="endDate" name="endDate"/>
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

<script id="oneFiveTpl" type="x-handlebars">
{{#resultBean}}
	<div class="c-analyse15__listtitle-wrap c-analyse15__walkerclearfix">
		<h3 class="c-analyse15__listtitle-word c-analyse15__listtitle-studentnum">学生总人数：<span>{{studentTotalNum}}</span></h3>
		<span class="iconfont c-analyse15__attendance-help" id="j_iconfont">󰂸</span>
	</div>
	<div class="c-analyse15__table-otherlist">
		<div class="c-analyse15__table-otheritem c-analyse15__table-otherproblembg c-analyse15__walkerclearfix">
			<div class="c-analyse15__table-othertype">
				<span class="c-analyse15__table-otherproblemicon"></span>错题本
			</div>
			<div class="c-analyse15__otherlist-con">
				<div class="c-analyse15__learnoverview-numwrap c-analyse15__other-numwrap c-analyse15__walkerclearfix">
					<div class="c-analyse15__learnoverview-weiketongline">
						<span class="c-analyse15__learnoverview-num">{{avgAddWrongSum}}</span><br>
						<span class="c-analyse15__learnoverview-numwrod">人均新增错题</span>
					</div>
					<div class="c-analyse15__learnoverview-weiketongline">
						<span class="c-analyse15__learnoverview-num">{{avgXmWrongSum}}</span><br>
						<span class="c-analyse15__learnoverview-numwrod">人均消灭错题数</span>
					</div>
					<div>
						<span class="c-analyse15__learnoverview-num">{{graspPro}}%</span><br>
						<span class="c-analyse15__learnoverview-numwrod">错题消灭率</span>
					</div>
				</div>
			</div>
		</div>
		<div class="c-analyse15__table-otheritem c-analyse15__table-otherledabg">
			<div class="c-analyse15__table-othertype">
				<span class="c-analyse15__table-otherledaicon"></span>乐答
			</div>
			<div class="c-analyse15__otherlist-con">
				<div class="c-analyse15__learnoverview-numwrap c-analyse15__other-numwrap c-analyse15__walkerclearfix">
					<div class="c-analyse15__overview-blueline">
						<span class="c-analyse15__learnoverview-num">{{askNum}}</span><br>
						<span class="c-analyse15__learnoverview-numwrod">提问人数</span>
					</div>
					<div class="c-analyse15__overview-blueline">
						<span class="c-analyse15__learnoverview-num">{{avgAskNum}}</span><br>
						<span class="c-analyse15__learnoverview-numwrod">人均提问数</span>
					</div>
					<div>
						<span class="c-analyse15__learnoverview-num">{{askSolvePro}}%</span><br>
						<span class="c-analyse15__learnoverview-numwrod">问题解答率</span>
					</div>
				</div>
			</div>
		</div>
		<div class="c-analyse15__table-otheritem c-analyse15__table-otherclassvideobg">
			<div class="c-analyse15__table-othertype">
				<span class="c-analyse15__table-otherclassvideoicon"></span>课堂录像
			</div>
			<div class="c-analyse15__otherlist-con">
				<div class="c-analyse15__learnoverview-numwrap c-analyse15__other-numwrap c-analyse15__walkerclearfix">
					<div class="c-analyse15__learnoverview-brushline">
						<span class="c-analyse15__learnoverview-num">{{avgLessonNum}}</span><br>
						<span class="c-analyse15__learnoverview-numwrod">人均查看课堂录像数</span>
					</div>
					<div class="c-analyse15__learnoverview-brushline">
						<%--<span class="c-analyse15__learnoverview-num">{{avgTimeLong}}</span><br>--%>
						<span class="c-analyse15__learnoverview-num">--</span><br>
						<span class="c-analyse15__learnoverview-numwrod">人均每次查看课堂录像时长</span>
					</div>
					<div>
						<span class="c-analyse15__learnoverview-num">{{viewSeePro}}%</span><br>
						<span class="c-analyse15__learnoverview-numwrod">人均课堂录像查看率</span>
					</div>
				</div>
			</div>
		</div>
		<div class="c-analyse15__table-otheritem c-analyse15__table-othernotesbg">
			<div class="c-analyse15__table-othertype">
				<span class="c-analyse15__table-othernotesicon"></span>笔记
			</div>
			<div class="c-analyse15__otherlist-con">
				<div class="c-analyse15__learnoverview-numwrap c-analyse15__other-numwrap c-analyse15__walkerclearfix">
					<div class="c-analyse15__overview-greenline">
						<span class="c-analyse15__learnoverview-num">{{avgCreateNum}}</span><br>
						<span class="c-analyse15__learnoverview-numwrod">人均创建份数</span>
					</div>
					<div class="c-analyse15__overview-greenline">
						<span class="c-analyse15__learnoverview-num">{{avgSeeNum}}</span><br>
						<span class="c-analyse15__learnoverview-numwrod">人均查看份数</span>
					</div>
					<div>
						<span class="c-analyse15__learnoverview-num">{{avgReadNum}}</span><br>
						<span class="c-analyse15__learnoverview-numwrod">平均每份查看次数</span>
					</div>
				</div>
			</div>
		</div>
	</div>
{{/resultBean}}
</script>


<script id="dtlTpl" type="x-handlebars">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td>{{className}}</td>
    	<td>{{studentName}}</td>
    	<td>{{graspNum}}</td>
    	<td>{{graspPro}}%</td>
    	<td>{{askSolveSum}}</td>
    	<td>{{askSolvePro}}%</td>
    	<%--<td>{{lessonNum}}/{{avgTimeLong}}</td>--%>
    	<td>{{lessonNum}}/--</td>
    	<td>{{viewSeePro}}%</td>
		<td>{{createBookNum}}</td>
    	<td>{{avgReadNum}}</td>
    </tr>
{{/dataList}}
</script>

<script src="/scripts/diag/common/echart3.5.js"></script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/studentMonitor/otherAnalysePage');
</script>
</html>