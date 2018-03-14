<%@ page pageEncoding="UTF-8" %>
<div class="m-search-box c-analyse15__homework-search">
	<select class="u-select u-select-nm c-analyse15__homework-select" id="gradeId" name="gradeId">
	</select>
	<select class="u-select u-select-nm c-analyse15__homework-select" id="classId" name="classId">
	</select>
	<div class="c-analyse15__attendance-time">
		<span class="quickSelect" id="today">今日</span>
		<span class="quickSelect" id="yesterday">昨日</span>
		<span class="quickSelect" id="thisWeek">本周</span>
		<span class="quickSelect" id="thisMonth">本月</span>
	</div>
	<input id="startDate" name="startDate" type="text" class="u-ipt u-ipt-mn Wdate" readonly="">至<input id="endDate" name="endDate" type="text" class="u-ipt u-ipt-mn Wdate" readonly="">
          <input id="query" type="button" value="查询" class="u-btn u-btn-at u-btn-bg-turquoise">
          <span id="icon" class="iconfont c-analyse15__attendance-help">&#xf00b8;</span>
</div>
<div class="c-analyse15__homework-list c-analyse15__walkerclearfix">
	<a class="c-analyse15__homework-item c-analyse15__homework-border lessonItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=lesson">课堂维度</a>
	<a class="c-analyse15__homework-item c-analyse15__homework-border studentItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=student">学生维度</a>
	<a class="c-analyse15__homework-item c-analyse15__homework-border teacherItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=teacher">老师维度</a>
	<a class="c-analyse15__homework-item c-analyse15__homework-border classItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=class">班级维度</a>
	<a class="c-analyse15__homework-item c-analyse15__homework-border subjectItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=subject">学科维度</a>
	<a class="c-analyse15__homework-item dayItem" href="/auth/provost/studentMonitor/studentAttend/toShowStudentAttend.htm?queryType=day">每日缺勤</a>
</div>
