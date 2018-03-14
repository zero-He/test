<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/common/global.css">
<link rel="stylesheet" href="${assets}/styles/home/personal-center.css">

<title>教务首页</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="m-bg"></div>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
		    <div class="z-percenter-mn-notification f-bfc">
                <span class="system-notifications"><label class="f-vam">学校公告：</label><p class="f-dib f-vam" title="" id="lastNotice"></p></span>
            </div>
		    <div class="z-percenter-mn-wrap">
                <ul class="z-percenter-mn-module f-bfc z-percenter-mn-module--jiaowu">
                    <li>
                        <a href="${initParam.courseServerName}/auth/provost/course/courseList.htm">
                            <div class="font">
                                <p class="big">教务</p>

                                <p class="small">课程发布</p>
                            </div>
                            <div class="module-img1"></div>
                        </a>
                    </li>
                    <li>
                        <a href="${initParam.lessonServerName}/auth/provost/plan/list.htm">
                            <div class="font">
                                <p class="big">教务</p>

                                <p class="small">排课</p>
                            </div>
                            <div class="module-img2"></div>
                        </a>
                    </li>
                    <li>
                        <a href="${initParam.diagServerName}/auth/provost/teachingMonitor/beike/toShowBeikeRateStatInfo.htm?spm=104007">
                            <div class="font">
                                <p class="big">备课</p>

                                <p class="small">备课检查</p>
                            </div>
                            <div class="module-img3"></div>
                        </a>
                    </li>
                    <li class="special">
                        <a href="${initParam.lessonServerName}/auth/provost/attendance/viewStuAttendStatis.htm">
                            <div class="font">
                                <p class="big">学情</p>

                                <p class="small">学生考勤</p>
                            </div>
                            <div class="module-img4"></div>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="c-registrant">
                <div class="c-registrant__item">
                    <div class="c-registrant__avatar">
                        <span class="c-registrant__aimg"></span>
                        <p class="c-registrant__atext">老师</p>
                    </div>
                    <div class="c-registrant__main">
                        <div class="c-registrant__num">
                            <span class="c-registrant__cur" id="onlineTeacher"></span>
                            /
                            <span class="c-registrant__total" id="totalTeacher"></span>
                        </div>
                        <div>
                                                              当前人数/总人数
                        </div>
                    </div>
                </div>
                <div class="c-registrant__item c-registrant__item--stu">
                    <div class="c-registrant__avatar">
                        <span class="c-registrant__aimg"></span>
                        <p class="c-registrant__atext">学生</p>
                    </div>
                    <div class="c-registrant__main">
                        <div class="c-registrant__num">
                            <span class="c-registrant__cur" id="onlineStudent"></span>
                            /
                            <span class="c-registrant__total" id="totalStudent"></span>
                        </div>
                        <div>
                                                             当前人数/总人数
                        </div>
                    </div>
                </div>
                <div class="c-registrant__item c-registrant__item--par">
                    <div class="c-registrant__avatar">
                        <span class="c-registrant__aimg"></span>
                        <p class="c-registrant__atext">家长</p>
                    </div>
                    <div class="c-registrant__main">
                        <div class="c-registrant__num">
                            <span class="c-registrant__cur" id="onlineParent"></span>
                            /
                            <span class="c-registrant__total" id="totalParent"></span>
                        </div>
                        <div>
                                                              当前人数/总人数
                        </div>
                    </div>
                </div>
            </div>
            <div class="c-feature-block">
                <div class="c-feature-block__header">
                    <span class="c-feature-block__name">教学动态</span>
                    <i class="iconfont iconItem" style="cursor:pointer;">&#xf0031;</i>
                    <div class="c-feature-block__operation">
                        <div class="c-dateswitch">
                            <a class="c-dateswitch__pre" id="dynamicDatelt">&lt;</a>
                            <span class="c-dateswitch__today" id="dynamicDate"></span>
                            <a class="c-dateswitch__next c-dateswitch--forbiden" id="dynamicDategt">&gt;</a>
                            <a class="c-dateswitch__gotoday" id="dynamicToday">今天</a>
                        </div>
                    </div>
                </div>
                <div class="c-feature-block__con">
                    <div class="c-teastatus">
                        <div class="c-teastatus__item c-teastatus__item--stu">
                            <div class="c-teastatus__con">
                                <div class="c-teastatus__sort">
                                    <div class="c-teastatus__img c-teastatus__img--attendance">
                                        <p>考勤</p>
                                    </div>
                                    <div class="c-teastatus__stats">
                                        <div class="c-teastatus__statsitem">
                                            <div id="notAttendStu"></div>
                                            <p>缺课</p>
                                        </div>
                                        <div class="c-teastatus__statsitem">
                                            <div id="lateStu"></div>
                                            <p>迟到</p>
                                        </div>
                                        <div class="c-teastatus__statsitem">
                                            <div id="earlyStu"></div>
                                            <p>早退</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="c-teastatus__sort">
                                    <div class="c-teastatus__img c-teastatus__img--quest">
                                        <p>提问</p>
                                    </div>
                                    <div class="c-teastatus__stats">
                                        <div class="c-teastatus__statsitem">
                                            <div id="doubt"></div>
                                            <p>提问题数</p>
                                        </div>
                                        <div class="c-teastatus__statsitem">
                                            <div id="doubtStu"></div>
                                            <p>提问人数</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="c-teastatus__item c-teastatus__item--tea">
                            <div class="c-teastatus__con">
                                <div class="c-teastatus__con--left">
                                    <div class="c-teastatus__sort">
                                        <div class="c-teastatus__img c-teastatus__img--class">
                                            <p>课堂</p>
                                        </div>
                                        <div class="c-teastatus__stats">
                                            <div class="c-teastatus__statsitem">
                                                <div id="notAttendLesson"></div>
                                                <p>未上课</p>
                                            </div>
                                            <div class="c-teastatus__statsitem">
                                                <div id="endLesson"></div>
                                                <p>已结束</p>
                                            </div>
                                            <div class="c-teastatus__statsitem">
                                                <div id="onLesson"></div>
                                                <p>上课中</p>
                                            </div>
                                            <div class="c-teastatus__statsitem">
                                                <div id="awaitingLesson"></div>
                                                <p>待上课</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="c-teastatus__sort">
                                        <div class="c-teastatus__img c-teastatus__img--work">
                                            <p>作业</p>
                                        </div>
                                        <div class="c-teastatus__stats">
                                            <div class="c-teastatus__statsitem">
                                                <div id="assignHW"></div>
                                                <p>布置</p>
                                            </div>
                                            <div class="c-teastatus__statsitem">
                                                <div id="correctHW"></div>
                                                <p>批改</p>
                                            </div>
                                            <div class="c-teastatus__statsitem">
                                                <div id="awaitingCorrectHW"></div>
                                                <p>待批改</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="c-teastatus__con--right">
                                    <div class="c-teastatus__sort">
                                        <div class="c-teastatus__img c-teastatus__img--prepare">
                                            <p>备课</p>
                                        </div>
                                        <div class="c-teastatus__stats">
                                            <div class="c-teastatus__statsitem">
                                                <div id="prepareLesson"></div>
                                                <p>已备课</p>
                                            </div>
                                            <div class="c-teastatus__statsitem">
                                                <div id="notPrepareLesson"></div>
                                                <p>未备课</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="c-teastatus__sort">
                                        <div class="c-teastatus__img c-teastatus__img--answer">
                                            <p>答疑</p>
                                        </div>
                                        <div class="c-teastatus__stats">
                                            <div class="c-teastatus__statsitem">
                                                <div id="resolveDoubt"></div>
                                                <p>当日解答</p>
                                            </div>
                                            <div class="c-teastatus__statsitem">
                                                <div id="notResolveDoubt"></div>
                                                <p>总未解答</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="c-feature-block">
                <div class="c-feature-block__header">
                    <span class="c-feature-block__name">日课表</span>
                    <div class="c-feature-block__operation">
                        <div class="c-dateswitch">
                            <a class="c-dateswitch__pre" id="scheduleDatelt">&lt;</a>
                            <span class="c-dateswitch__today" id="scheduleDate"></span>
                            <a class="c-dateswitch__next" style="color:#0ba29a" id="scheduleDategt">&gt;</a>
                            <a class="c-dateswitch__gotoday" id="scheduleToday">今天</a>
                        </div>
                    </div>
                </div>
                <div class="c-feature-block__con">
                    <div class="c-filtcon">
                        <div class="c-filtcon__item">
                            <select class="u-select u-select-sm" id="gradeId" name="gradeId">
                            </select>
                            <select class="u-select u-select-sm" id="subjectId" name="subjectId">
                            </select>
                            <select class="u-select u-select-sm" id="classId" name="classId">
                            </select>
                        </div>
                        <div class="c-filtcon__item">
                            <label class="c-filtcon__checks">
                                <input class="c-checkbox__core" type="checkbox" name="lessonStatusChk" value="2" checked="true">
                                <span class="c-checkbox__desc">
                                                                                    未上课
                                </span>
                            </label>
                            <label class="c-filtcon__checks">
                                <input class="c-checkbox__core" type="checkbox" name="lessonStatusChk" value="1" checked="true">
                                <span class="c-checkbox__desc">
                                                                                    已结束
                                </span>
                            </label>
                            <label class="c-filtcon__checks">
                                <input class="c-checkbox__core" type="checkbox" name="lessonStatusChk" value="3" checked="true">
                                <span class="c-checkbox__desc">
                                                                                    待上课
                                </span>
                            </label>
                            <label class="c-filtcon__checks">
                                <input class="c-checkbox__core" type="checkbox" name="lessonStatusChk" value="4" checked="true">
                                <span class="c-checkbox__desc">
                                                                                   上课中
                                </span>
                            </label>
                            <label class="c-filtcon__checks">
                                <input class="c-checkbox__core" type="checkbox" name="isPreparedChk" value="1" checked="true">
                                <span class="c-checkbox__desc">
                                                                                   已备课
                                </span>
                            </label>
                            <label class="c-filtcon__checks">
                                <input class="c-checkbox__core" type="checkbox" name="isPreparedChk" value="0" checked="true">
                                <span class="c-checkbox__desc">
                                                                                  未备课
                                </span>
                            </label>
                        </div>
                    </div>
                    <div class="c-timetable" id="jtbodyData">
                        
                    </div>
                    <div class="c-searchnull" style="display:none"><i></i><p>暂无课程</p></div>
                </div>
            </div>
        </div>
		</div>
	</div>
	<input type="hidden" id="startDate" name="startDate" />
	<input type="hidden" id="endDate" name="endDate" />
	<input type="hidden" id="isPrepared" name="isPrepared" />
	<input type="hidden" id="lessonStatus" name="lessonStatus" />
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

<script id="classTpl" type="x-mustache">
{{#classList}}
	<option value="{{classId}}">{{className}}</option>
{{/classList}}
</script>

<script id="dtlTpl" type="x-handlebars">
{{#scheduleList}}
    <div class="c-timetable__item">
         <div class="c-timetable__time">
             <div>{{startTime}}</div>
             <div class="c-timetable__order">{{lessonIndex}}</div>
         </div>
         <div class="c-timetable__classlist">
             {{#lessonList}}
             <div class="c-timetable__class">
                 <div class="c-timetable__names">
                     <div class="c-timetable__namestxt">
                         <div>{{teacherName}}</div>
                         <div title="{{className}}">{{className}}</div>
                         <div>{{subjectName}}</div>
                     </div>
                 </div>
                 <div class="c-timetable__infos">
                     <div class="c-timetable__infoline">
                         <span class="c-timetable__timerange">{{lessonTimeStr}}</span>
                         
                         {{#cif 'this.lessonStatus == 1'}}
                         <span class="c-timetable__classstatus c-timetable__classstatus--end"></span>
                         {{/cif}}

                         {{#cif 'this.lessonStatus == 2'}}
                         <span class="c-timetable__classstatus c-timetable__classstatus--pre"></span>
                         {{/cif}}

                         {{#cif 'this.lessonStatus == 3'}}
                         <span class="c-timetable__classstatus c-timetable__classstatus--ready"></span>
                         {{/cif}}

                         {{#cif 'this.lessonStatus == 4'}}
                         <span class="c-timetable__classstatus c-timetable__classstatus--during"></span>
                         {{/cif}}

                         <div class="c-timetable__operation">
							 {{#cif 'this.cwCount != 0 || this.mcCount != 0 || this.hwCount != 0'}}
                                 <a href="${initParam.beikeServerName}/auth/common/beikepkg/beikePkgTemplateLessonEdit.htm?lessonId={{courseSingleId}}" target="_blank" class="u-btn u-btn-at u-btn-bg-white">检查备课</a>
							 {{/cif}}

				             {{#cif '(this.cwCount != 0 || this.mcCount != 0 || this.hwCount != 0) && this.lessonStatus == 2'}}
                             <div class="c-btns" style="display:inline-block; vertical-align:middle;">
							     <div class="m-btns">
								    <div class="init-btn">
									    <a href="${initParam.diagServerName}/auth/common/report/lesson/main/{{courseSingleId}}-1.htm" target="_blank" class="link">预习报告</a>
                                        <b><i></i></b>
								    </div>
							     </div>
	                         </div>
                             {{/cif}}
							 
                             {{#cif 'this.lessonStatus != 2'}}
		                     {{#cif 'this.isEnd == 1 && this.isRecord == 1'}}
							 <div class="c-btns" style="display:inline-block; vertical-align:middle;">
							 <div class="m-btns">
								<div class="init-btn">
									<a href="${initParam.lessonServerName}/api/w/tutor/replay.htm?courseSingleId={{courseSingleId}}&userId=${userId}&roleId=${currentRoleId}" target="_blank" class="link">查看录像 </a>
                                    <b><i></i></b>
								</div>
								<menu>
									<li><a class="j-parent-enable" data-id="{{courseSingleId}}">家长权限</a></li>
									<li><a href="${initParam.diagServerName}/auth/common/report/lesson/main/{{courseSingleId}}-2.htm" target="_blank" class="link">课堂报告</a></li>
									<li><a href="${initParam.diagServerName}/auth/common/report/lesson/main/{{courseSingleId}}-3.htm" target="_blank" class="link">复习检查</a></li>
									<li><a href="${initParam.diagServerName}/auth/common/report/lesson/main/{{courseSingleId}}-1.htm" target="_blank" class="link">预习报告</a></li>
								</menu>
							 </div>
	                         </div>

                             {{/cif}}

							 {{#cif 'this.isEnd == 1 && this.isRecord == 0'}}
                             <div class="c-btns" style="display:inline-block; vertical-align:middle;">
							 <div class="m-btns">
								<div class="init-btn">
									<a href="${initParam.diagServerName}/auth/common/report/lesson/main/{{courseSingleId}}-2.htm" target="_blank" class="link">课堂报告</a>
                                    <b><i></i></b>
								</div>
								<menu>
									<li><a href="${initParam.diagServerName}/auth/common/report/lesson/main/{{courseSingleId}}-3.htm" target="_blank" class="link">复习检查</a></li>
									<li><a href="${initParam.diagServerName}/auth/common/report/lesson/main/{{courseSingleId}}-1.htm" target="_blank" class="link">预习报告</a></li>
								</menu>
							 </div>
	                         </div>
                             {{/cif}}
							
							 {{#cif 'this.lessonStatus == 4'}}
							 <div class="c-btns" style="display:inline-block; vertical-align:middle;">
							 <div class="m-btns">
								<div class="init-btn">
									<a class="js-listen" data-id="{{courseSingleId}}">监听课堂</a>
                                    <b><i></i></b>
								</div>
								<menu>
									<li><a class="j-parent-enable" data-id="{{courseSingleId}}">家长权限</a></li>
									<li><a href="${initParam.diagServerName}/auth/common/report/lesson/main/{{courseSingleId}}-1.htm" target="_blank" class="link">预习报告</a></li>
								</menu>
							 </div>
	                         </div>
                             {{/cif}}

							 {{#cif 'this.lessonStatus == 3'}}
							 <div class="c-btns" style="display:inline-block; vertical-align:middle;">
							 <div class="m-btns">
								<div class="init-btn">
									<a class="j-parent-enable" data-id="{{courseSingleId}}">家长权限</a>
                                    <b><i></i></b>
								</div>
								<menu>
									<li><a href="${initParam.diagServerName}/auth/common/report/lesson/main/{{courseSingleId}}-1.htm" target="_blank" class="link">预习报告</a></li>
								</menu>
							 </div>
	                         </div>
                             {{/cif}}
                             {{/cif}}
                         </div>
                     </div>
                     <div class="c-timetable__infoline">
						 {{#if lessonPlan}}
                         	<span class="c-timetable__resource c-timetable__resource--plan ishave">
                                                                                           教案
                         	</span>
						 {{else}}
    						<span class="c-timetable__resource c-timetable__resource--plan">
                                                                                           教案
                         	</span>
						 {{/if}}

                         {{#cif 'this.mcCount != 0'}}
                         <span class="c-timetable__resource c-timetable__resource--microlesson ishave">
                                                                                            微课({{mcCount}})
                         </span>
                         {{else}}
                         <span class="c-timetable__resource c-timetable__resource--microlesson">
                                                                                            微课
                         </span>
                         {{/cif}}

                         {{#cif 'this.cwCount != 0'}}
                         <span class="c-timetable__resource c-timetable__resource--coursewave ishave">
                                                                                           课件({{cwCount}})
                         </span>
                         {{else}}
                         <span class="c-timetable__resource c-timetable__resource--coursewave">
                                                                                           课件
                         </span>
                         {{/cif}}

                         {{#cif 'this.hwCount != 0'}}
                         <span class="c-timetable__resource c-timetable__resource--homework ishave">
                                                                                           作业({{hwCount}})
                         </span>
                         {{else}}
                         <span class="c-timetable__resource c-timetable__resource--homework">
                                                                                           作业
                         </span>
                         {{/cif}}
                     </div>
                 </div>
             </div>
         {{/lessonList}}
         </div>
     </div>
{{/scheduleList}}
</script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/teachingMonitor/homePage');
</script>
</html>