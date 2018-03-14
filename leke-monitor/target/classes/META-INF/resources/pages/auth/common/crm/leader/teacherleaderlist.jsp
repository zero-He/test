<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>老师课堂统计-乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/gili/gili.css?t=20171115" />
<link rel="stylesheet" href="${assets}/scripts/common/ui/ui-scrollbar/skin/default/jquery-scrollbar.css?t=20171115" />
<link rel="stylesheet" href="${assets}/styles/tutor/common/inputDisabledChange.css?t=20171115" type="text/css" />
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css" type="text/css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="z-statisticalcenter">
				<div class="m-search-box f-pt15">
					<form id="form">
						<div class="f-mb20 borderbottm">
							<!-- 营销处经理 -->
							<c:if test="${roleId ==603 }">
								<label for="" class="title">乐号：</label>
								<label for="" class="title">${loginName }</label>
								<label for="" class="title">客户经理：</label>
								<label for="" class="title">${sellerName }</label>
								<label for="" class="title f-ml25">学校名称：</label>
								<label for="" class="title">${schoolName }</label>
							</c:if>

							<!-- 区域经理/市场经理 -->
							<c:if test="${roleId ==601 || roleId ==602}">
								<label for="" class="title">客户经理：</label>
								<label for="" class="title">${sellerName }</label>
								<label for="" class="title">营销处：</label>
								<label for="" class="title">${marketName }</label>
								<label for="" class="title f-ml25">学校名称：</label>
								<label for="" class="title">${schoolName }</label>
							</c:if>

							<!-- 助理总经理 -->
							<c:if test="${roleId ==600}">
								<label for="" class="title">客户经理：</label>
								<label for="" class="title">${sellerName }</label>
								<label for="" class="title">营销处：</label>
								<label for="" class="title">${marketName }</label>
								<label for="" class="title f-ml25">学校名称：</label>
								<label for="" class="title">${schoolName }</label>
								<label for="" class="title f-ml25">学段：</label>
								<label for="" class="title">${schoolStageName }</label>
							</c:if>

							<!-- 营销中心总经理 -->
							<c:if test="${roleId ==604}">
								<label for="" class="title">客户经理：</label>
								<label for="" class="title">${sellerName }</label>
								<label for="" class="title">营销处：</label>
								<label for="" class="title">${marketName }</label>
								<label for="" class="title f-ml25">学校名称：</label>
								<label for="" class="title">${schoolName }</label>
							</c:if>
						</div>
						<input type="hidden" name="sellerName" id="sellerName" value="${sellerName }" />
						<input type="hidden" name="sellerId" id="sellerId" value="${sellerId }" />
						<input type="hidden" name="schoolName" id="schoolName" value="${schoolName }" />
						<input type="hidden" name="schoolId" id="schoolId" value="${schoolId }" />
						<input type="hidden" name="schoolStageId" id="schoolStageId" value="${schoolStageId }" />

						<label for="" class="title f-ml0">学科：</label>
						<select name="subjectId" class="u-select u-select-nm" id="subjectId"
							data-bind="options:subjectData,
                                                       optionsText:'subjectName',
                                                       optionsValue:'subjectId',
                                                       optionsCaption:'请选择'">
						</select>
						<label class="title" for="">年级：</label>
						<select name="gradeId" class="u-select u-select-nm" id="gradeId"
							data-bind="options:gradeData,
                                                       optionsText:'gradeName',
                                                       optionsValue:'gradeId',
                                                       optionsCaption:'请选择'">
						</select>
						<label class="title" for="">老师姓名：</label>
						<input class="u-ipt u-ipt-nm" type="text" name="teacherName" id="teacherName" />
						<div class="operation">
							<input class="u-btn u-btn-nm u-btn-bg-turquoise f-mr10" value="查询" type="submit">
							<a class="f-fr u-btn u-btn-nm u-btn-bg-orange f-mr15" data-bind="click:classExport">导出</a>
						</div>
						<div class="f-mt15">
							<label class="title" for="">统计时间：</label>
							<input class="u-ipt u-ipt-nm Wdate" type="text" id="statisticsTimeStart" name="statisticsTimeStart" value="${dateStart }" />
							<label for="" class="title f-ml5 f-mr5">至</label>
							<input class="u-ipt u-ipt-nm Wdate" type="text" id="statisticsTimeEnd" name="statisticsTimeEnd" value="${dateEnd }" />
						</div>
					</form>
				</div>
				<div class="f-mt20 bgwhite">
					<h3 class="tabtitle">老师课堂统计</h3>
					<div class="m-table m-table-center f-mb0 m-fixedtable">
						<table>
							<thead>
								<tr>
									<th>学科</th>
									<th>年级</th>
									<th>老师姓名</th>
									<th>已结束课堂数</th>
									<th>备课课堂数</th>
									<th>上课课堂数</th>
									<th>备课率</th>
									<th>上课率</th>
									<th width="20%">操作区</th>
								</tr>
							</thead>
							<tbody data-bind="foreach: { data: teacherClass }">
								<tr>
									<td data-bind="text:subjectName"></td>
									<td data-bind="text:gradeName"></td>
									<td data-bind="text:teacherName"></td>
									<td data-bind="text:endClassNum"></td>
									<td data-bind="text:beikeClassNum"></td>
									<td data-bind="text:classNum"></td>
									<td data-bind="text:beikeRate"></td>
									<td data-bind="text:classRate"></td>
									<td class="operation blocka">
										<a data-bind="click:$root.attendance">考勤统计</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="page" id="divPage"></div>
				</div>
				<div class="f-mt20 bgwhite" id="beikeDetails" style="display: none;">
					<h3 class="tabtitle">
						课堂明细 <i class="queries"
							title="1，预习作业绑定率：备有预习作业的&#13;
课堂数/备课课堂数。&#13;
2，课件绑定率：备有课件的课堂数/&#13;
备课课堂数。&#13;
3，随堂作业绑定率：备有随堂作&#13;
业的课堂数/备课课堂数。&#13;
4，作业布置数：布置给学生的作业&#13;
数之和。&#13;
5，随堂测试发起率：发起随堂测试&#13;
课堂数/上课课堂数。&#13;
6，快速问答发起率：发起快速问答&#13;
课堂数/上课课堂数。"></i>
					</h3>
					<div class="m-table m-table-center f-mb0 classdetail">
						<table>
							<tbody>
								<tr>
									<th>学科</th>
									<th>年级</th>
									<th>老师姓名</th>
									<th colspan="2">课前</th>
									<th colspan="8">课中</th>
									<th colspan="6">课后</th>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td>
										预习作业 <br>布置份数
									</td>
									<td>
										预习作业 <br>绑定率
									</td>
									<td>课件数</td>
									<td>
										课件 <br>绑定率
									</td>
									<td>
										随堂作业 <br>布置份数
									</td>
									<td>
										随堂作业 <br>绑定率
									</td>
									<td>
										随堂测试 <br>次数
									</td>
									<td>
										随堂测试 <br>发起率
									</td>
									<td>
										快速问答 <br>次数
									</td>
									<td>
										快速问答 <br>发起率
									</td>
									<td>
										课后作业 <br>布置份数
									</td>
									<td>
										作业 <br>布置数
									</td>
									<td>
										作业 <br>提交数
									</td>
									<td>
										作业 <br>批改数
									</td>
									<td>
										作业 <br>提交率
									</td>
									<td>
										作业 <br>批改率
									</td>
								</tr>
								<tr>
									<td data-bind="text:subjectName"></td>
									<td data-bind="text:gradeName"></td>
									<td data-bind="text:teacherName"></td>
									<td data-bind="text:previewWorkNum"></td>
									<td data-bind="text:previewWorkRate"></td>
									<td data-bind="text:coursewareNum"></td>
									<td data-bind="text:coursewareRate"></td>
									<td data-bind="text:classWorkNum"></td>
									<td data-bind="text:classWorkRate"></td>
									<td data-bind="text:classTestNum"></td>
									<td data-bind="text:classTestRate"></td>
									<td data-bind="text:questionsAnswersNum"></td>
									<td data-bind="text:questionsAnswersRate"></td>
									<td data-bind="text:afterClassWorkNum"></td>
									<td data-bind="text:classWorkTotalNum"></td>
									<td data-bind="text:workFinishNum"></td>
									<td data-bind="text:workcorrectNum"></td>
									<td data-bind="text:workFinishRate"></td>
									<td data-bind="text:workcorrectRate"></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	window.data={
			subjectStr:${subjectStr},
			gradeStr:${gradeStr},
			classStatistics:${classStatistics}
	}
	seajs.use('monitor/pages/common/crm/leader/teacherleaderlist');
</script>
</html>