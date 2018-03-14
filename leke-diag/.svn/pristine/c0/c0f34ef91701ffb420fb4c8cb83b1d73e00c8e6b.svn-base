<%@ page pageEncoding="UTF-8" %>
<div id="jTopNav" class="m-tab">
	<ul>
		<li class="active"><a href="/auth/provost/teachingMonitor/lessonAttend/toShowLessonAttendRateStat.htm">教学常规</a></li>
		<li><a href="/auth/provost/teachingMonitor/statsum/toShowStatSum.htm">统计总表</a></li>
	</ul>
</div>
<div class="m-search-box">
	<div>
		<select class="u-select u-select-nm" id="gradeId" name="gradeId">
		</select> 
		<select class="u-select u-select-nm" id="subjectId" name="subjectId">
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
        </div>
	</div>
</div>
<div class="m-search-box">
	<div class="c-typeswitch">
		<ul id="ulSwitch">
			<c:if test="${currentRoleId == 104}">
				<li class="c-typeswitch__item lessonAttend"><a href="/auth/provost/teachingMonitor/lessonAttend/toShowLessonAttendRateStat.htm?spm=104007">上课</a></li>
				<li class="c-typeswitch__item beike"><a href="/auth/provost/teachingMonitor/beike/toShowBeikeRateStatInfo.htm?spm=104007">备课</a></li>
				<li class="c-typeswitch__item homework"><a href="/auth/provost/teachingMonitor/homework/toShowCorrectHWRateStat.htm?spm=104007">作业</a></li>
				<li class="c-typeswitch__item doubt"><a href="/auth/provost/teachingMonitor/doubt/toShowResolveDoubtStat.htm?spm=104007">乐答</a></li>
				<li class="c-typeswitch__item resource"><a href="/auth/provost/teachingMonitor/resource/resourcePage.htm?spm=104007">资源</a></li>
				<li class="c-typeswitch__item attendance"><a href="/auth/provost/teachingMonitor/attendance/attendancePage.htm?spm=104007">考勤</a></li>
				<li class="c-typeswitch__item interact"><a href="/auth/provost/teachingMonitor/interact/interactPage.htm?spm=104007">课堂互动</a></li>
				<li class="c-typeswitch__item evaluate"><a href="/auth/provost/teachingMonitor/evaluate/evaluatePage.htm?spm=104007">课堂评价</a></li>
			</c:if>
			<c:if test="${currentRoleId == 105}">
				<li class="c-typeswitch__item lessonAttend"><a href="/auth/provost/teachingMonitor/lessonAttend/toShowLessonAttendRateStat.htm?spm=105007">上课</a></li>
				<li class="c-typeswitch__item beike"><a href="/auth/provost/teachingMonitor/beike/toShowBeikeRateStatInfo.htm?spm=105007">备课</a></li>
				<li class="c-typeswitch__item homework"><a href="/auth/provost/teachingMonitor/homework/toShowCorrectHWRateStat.htm?spm=105007">作业</a></li>
				<li class="c-typeswitch__item doubt"><a href="/auth/provost/teachingMonitor/doubt/toShowResolveDoubtStat.htm?spm=105007">乐答</a></li>
				<li class="c-typeswitch__item resource"><a href="/auth/provost/teachingMonitor/resource/resourcePage.htm?spm=105007">资源</a></li>
				<li class="c-typeswitch__item attendance"><a href="/auth/provost/teachingMonitor/attendance/attendancePage.htm?spm=105007">考勤</a></li>
				<li class="c-typeswitch__item interact"><a href="/auth/provost/teachingMonitor/interact/interactPage.htm?spm=105007">课堂互动</a></li>
				<li class="c-typeswitch__item evaluate"><a href="/auth/provost/teachingMonitor/evaluate/evaluatePage.htm?spm=105007">课堂评价</a></li>
			</c:if>
		</ul>
	</div>
</div>
