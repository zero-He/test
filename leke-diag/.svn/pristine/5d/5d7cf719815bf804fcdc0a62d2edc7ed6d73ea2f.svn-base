<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/common/global.css">
<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">

<title>查看详情</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<div class="m-bg"></div>
	<div class="g-bd">
        <div class="g-mn">
            <div class="c-dataoverview">
                <div class="c-dataoverview__title">教学数据一览表</div>
                <div class="c-dataoverview__info">
                    <div class="c-dataoverview__selfinfo">
                        <img src="${statSum.imgSrc}" alt="" class="c-dataoverview__avatar">
                        <div class="c-dataoverview__text">
                            <div class="c-dataoverview__name">${statSum.teacherName}</div>
                            <div class="c-dataoverview__class">${statSum.subjectName}</div>
                        </div>
                    </div>
                    <span class="c-dataoverview__time">${statSum.startDate}—${statSum.endDate}</sp>
                </div>
                <div class="c-dataoverview__table c-dataoverview__table--even">
                    <div class="c-dataoverview__ttitle">备课</div>
                    <div class="c-dataoverview__mtable m-table m-table-center m-table-fixed">
                        <table>
                            <tr>
                                <th>备课率<p>（实备/应备）</p></th>
                                <th>提前备课率/数</th>
                                <th>临时备课率/数</th>
                                <th>备课件率/数</th>
                                <th>备微课率/数</th>
                                <th>备作业率/数</th>
                                <th>备教案率/数</th>
                            </tr>
                            <tr>
                            <c:choose>
	                            <c:when test="${statSum.beikeRate.actualLesson != null}">
	                                <td>${statSum.beikeRate.preparedLessonRate}%（${statSum.beikeRate.preparedLesson}/${statSum.beikeRate.actualLesson}）</td>
	                                <td>${statSum.beikeRate.earlyPreparedLessonRate}%/${statSum.beikeRate.earlyPreparedLesson}</td>
	                                <td>${statSum.beikeRate.tempPreparedLessonRate}%/${statSum.beikeRate.tempPreparedLesson}</td>
	                                <td>${statSum.beikeRate.cwLessonRate}%/${statSum.beikeRate.cwLesson}</td>
	                                <td>${statSum.beikeRate.mcLessonRate}%/${statSum.beikeRate.mcLesson}</td>
	                                <td>${statSum.beikeRate.hwLessonRate}%/${statSum.beikeRate.hwLesson}</td>
	                                <td>${statSum.beikeRate.teachPlanLessonRate}%/${statSum.beikeRate.teachPlanLesson}</td>
	                            </c:when>
	                            <c:otherwise>
	                            	<td>-</td> <td>-</td> <td>-</td> <td>-</td> <td>-</td> <td>-</td> <td>-</td>
	                            </c:otherwise>
                            </c:choose>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="c-dataoverview__table c-dataoverview__table--odd">
                    <div class="c-dataoverview__ttitle">作业</div>
                    <div class="c-dataoverview__mtable m-table m-table-center m-table-fixed">
                        <table>
                            <tr>
                                <th width="22%">布置作业份数（纯客观/主观）</th>
                                <th>系统批改人份（占比）</th>
                                <th>老师批改人份（占比）</th>
                                <th>学生互批人份（占比）</th>
                                <th>未批改人份（占比）</th>
                            </tr>
                            <tr>
                                <td>${statSum.correctHW.assignNum}（${statSum.correctHW.objectiveNum}/${statSum.correctHW.subjectiveNum}）</td>
                                <td>${statSum.correctHW.autoCorrectNum}（<fmt:formatNumber value="${statSum.correctHW.autoCorrectNumRate}" pattern="0.#" />%）</td>
                                <td>${statSum.correctHW.teacherCorrectNum}（<fmt:formatNumber value="${statSum.correctHW.teacherCorrectNumRate}" pattern="0.#" />%）</td>
                                <td>${statSum.correctHW.studentCorrectNum}（<fmt:formatNumber value="${statSum.correctHW.studentCorrectNumRate}" pattern="0.#" />%）</td>
                                <td>${statSum.correctHW.notCorrectNum}（<fmt:formatNumber value="${statSum.correctHW.notCorrectNumRate}" pattern="0.#" />%）</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="c-dataoverview__table c-dataoverview__table--even c-dataoverview__table--leda">
                    <div class="c-dataoverview__ttitle">上课</div>
                    <div class="c-dataoverview__mtable m-table m-table-center m-table-fixed">
                        <table>
                            <tr>
                                <th width="40%">上课率（实上/应上）</th>
                            </tr>
                            <tr>
	                        <c:choose>
	                            <c:when test="${statSum.lessonAttendInfo.totalLesson != null}">
	                                <td>${statSum.lessonAttendInfo.attendLessonRate}（${statSum.lessonAttendInfo.attendLesson}/${statSum.lessonAttendInfo.totalLesson}）</td>
                                </c:when>
                                <c:otherwise>
	                            	<td>-</td>
	                            </c:otherwise>
	                        </c:choose>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="c-dataoverview__table c-dataoverview__table--even c-dataoverview__table--leda">
                    <div class="c-dataoverview__ttitle">乐答</div>
                    <div class="c-dataoverview__mtable m-table m-table-center m-table-fixed">
                        <table>
                            <tr>
                                <th width="42%">已解答（24小时内/外） </th>
                                <th>待解答</th>
                            </tr>
                            <tr>
                            <c:choose>
	                            <c:when test="${statSum.resolveDoubt.totalDoubt != null}">
	                                <td>${statSum.resolveDoubt.resolveDoubt}（${statSum.resolveDoubt.in24HourResolveDoubt}/${statSum.resolveDoubt.out24HourResolveDoubt}）</td>
	                                <td>${statSum.resolveDoubt.notResolveDoubt}</td>
                                </c:when>
                                <c:otherwise>
	                            	<td>0</td> <td>0</td>
	                            </c:otherwise>
	                        </c:choose>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="c-dataoverview__table c-dataoverview__table--odd">
                    <div class="c-dataoverview__ttitle">考勤</div>
                    <div class="c-dataoverview__mtable m-table m-table-center m-table-fixed">
                        <table>
                            <tr>
                                <th>全勤</th>
                                <th>迟到</th>
                                <th>早退</th>
                                <th>迟到且早退</th>
                                <th>缺勤</th>
                            </tr>
                            <tr>
                                <td>${statSum.attendancePro.allOn}</td>
                                <td>${statSum.attendancePro.late}</td>
                                <td>${statSum.attendancePro.early}</td>
                                <td>${statSum.attendancePro.lateAndEarly}</td>
                                <td>${statSum.attendancePro.notClassNum}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="c-dataoverview__table c-dataoverview__table--even">
                    <div class="c-dataoverview__ttitle">资源</div>
                    <div class="c-dataoverview__mtable m-table m-table-center m-table-fixed">
                        <table>
                            <tr>
                                <th>创建/分享资源数</th>
                                <th>课件</th>
                                <th>微课</th>
                                <th>试卷</th>
                                <th>习题</th>
                                <th>备课包</th>
                            </tr>
                            <tr>
                                <td>${statSum.resourceDetail.createCount}/${statSum.resourceDetail.shareCount}</td>
                                <td>${statSum.resourceDetail.createCoursewareCount}/${statSum.resourceDetail.shareCoursewareCount}</td>
                                <td>${statSum.resourceDetail.createMicrocourseCount}/${statSum.resourceDetail.shareMicrocourseCount}</td>
                                <td>${statSum.resourceDetail.createPaperCount}/${statSum.resourceDetail.sharePaperCount}</td>
                                <td>${statSum.resourceDetail.createQuestionCount}/${statSum.resourceDetail.shareQuestionCount}</td>
                                <td>${statSum.resourceDetail.createBeikePkgCount}/${statSum.resourceDetail.shareBeikePkgCount}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="c-dataoverview__table c-dataoverview__table--odd">
                    <div class="c-dataoverview__ttitle">课堂互动</div>
                    <div class="c-dataoverview__mtable m-table m-table-center m-table-fixed">
                        <table>
                            <tr>
                                <th>总次数</th>
                                <th>点名</th>
                                <th>快速问答</th>
                                <th>随堂作业</th>
                                <th>分组讨论</th>
                                <th>授权</th>
                            </tr>
                            <tr>
                            <c:choose>
	                            <c:when test="${statSum.interactDetail.avgTotalCount > 0}">
	                                <td><fmt:formatNumber value="${statSum.interactDetail.avgTotalCount}" pattern="0.#" /></td>
	                                <td><fmt:formatNumber value="${statSum.interactDetail.avgCallNum}" pattern="0.#" /></td>
	                                <td><fmt:formatNumber value="${statSum.interactDetail.avgQuickNum}" pattern="0.#" /></td>
	                                <td><fmt:formatNumber value="${statSum.interactDetail.avgExamNum}" pattern="0.#" /></td>
	                                <td><fmt:formatNumber value="${statSum.interactDetail.avgDiscuNum}" pattern="0.#" /></td>
	                                <td><fmt:formatNumber value="${statSum.interactDetail.avgAuthedNum}" pattern="0.#" /></td>
                                </c:when>
                                <c:otherwise>
	                            	<td>-</td> <td>-</td> <td>-</td> <td>-</td> <td>-</td> <td>-</td>
	                            </c:otherwise>
	                        </c:choose>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="c-dataoverview__table c-dataoverview__table--even">
                    <div class="c-dataoverview__ttitle">评价</div>
                    <div class="c-dataoverview__mtable m-table m-table-center m-table-fixed">
                        <table>
                            <tr>
                                <th>评价得分</th>
                                <th>好评（率）</th>
                                <th>中评（率）</th>
                                <th>差评（率）</th>
                                <th>鲜花</th>
                            </tr>
                            <tr>
                            <c:choose>
	                            <c:when test="${statSum.evaluateDetail.lessonNum != null}">
	                                <td><fmt:formatNumber value="${statSum.evaluateDetail.totalLevel}" pattern="0.#" /></td>
	                                <td>${statSum.evaluateDetail.good}/（<fmt:formatNumber value="${statSum.evaluateDetail.goodPro}" pattern="0.#" />%）</td>
	                                <td>${statSum.evaluateDetail.center}/（<fmt:formatNumber value="${statSum.evaluateDetail.centerPro}" pattern="0.#" />%）</td>
	                                <td>${statSum.evaluateDetail.poor}/（<fmt:formatNumber value="${statSum.evaluateDetail.poorPro}" pattern="0.#" />%）</td>
	                                <td>${statSum.evaluateDetail.flowerNum}</td>
                                </c:when>
                                <c:otherwise>
	                            	<td>-</td> <td>-</td> <td>-</td> <td>-</td> <td>-</td>
	                            </c:otherwise>
	                        </c:choose>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>