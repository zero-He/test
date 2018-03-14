<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>考勤统计(班级) - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css" type="text/css">
</head>
<style>
.m-table th {
	white-space: nowrap;
}
</style>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="z-statisticalcenter">
			<div class="m-search-box f-pt15">

				<label for="" class="title">学科：</label>
				<label for="" class="title">${query.subjectName}</label>
				<label for="" class="title f-ml25">年级：</label>
				<label for="" class="title">${query.gradeName}</label>
				<label for="" class="title f-ml25">老师：</label>
				<label for="" class="title">${query.teacherName}</label>
				<label for="" class="title f-ml25">统计时间：</label>
				<label for="" class="title">
					<fmt:formatDate value="${query.fromDate}" type="Date" pattern="yyyy-MM-dd" />
					~
					<fmt:formatDate value="${query.endDate}" type="Date" pattern="yyyy-MM-dd" />
				</label>

				<div class="operation">
					<input type="button" class="f-fr u-btn u-btn-nm u-btn-bg-orange" value="导出" data-bind="click:exportExcel">
				</div>

				<!-- <div class="f-mt15"></div> -->
			</div>
			<div class="f-mt20 bgwhite">
				<h3 class="tabtitle">
					考勤统计(班级)
						<!--ko if:stateChange()=='1'  -->
						<i class="queries"
						title="1.应上课人数：统计时间内，计划上课的人数（以上课课堂数为主统计）。&#13;
2.应上课人次：统计时间内，计划上课的人次（以上课课堂数为主统计）。&#13;
3.实上课人次：统计时间内，实际上课的人次（以上课课堂数为主统计）。&#13;
4.到课率：实上课人次/应上课人次*100%。&#13;
								"></i>		
						<!-- /ko -->
						<!--ko if:stateChange()=='2'  -->
							<i class="queries"
						title="1.计划旁听课堂数：统计时间内，应该旁听的课堂数。&#13;
2.实际旁听课堂数：统计时间内，实际旁听的课堂数。&#13;
3.旁听总时长：统计时间内，累计的旁听总时长。&#13;
4.旁听次数：统计时间内，旁听的总次数。&#13;
5.旁听率：实际旁听课堂数/计划旁听课堂数*100%。&#13;
								"></i>	
						<!-- /ko -->
					<div class="f-fr m-btns f-mt5 f-mr10">
						<div class="init-btn">
							<a href="javascript:void(0);">学生</a><b><i></i></b>
						</div>
						<menu style="">
							<li class="init-menu0" style="display: none">
								<a href="javascript:void(0);" data-bind="click:changeSel.bind($data,1)">学生</a>
							</li>
							<li class="init-menu1">
								<a href="javascript:void(0);" data-bind="click:changeSel.bind($data,2)">班主任</a>
							</li>
						</menu>
					</div>

				</h3>
				<!--ko if:stateChange()=='1'  -->
				<div class="m-table m-table-center f-mb0 classdetail">
					<table>
						<tr>
							<th>班级</th>
							<th>应上课人数</th>
							<th>应上课人次</th>
							<th>实上课人次</th>
							<th>到课率</th>
							<th>操作</th>
						</tr>
						<tbody id="papersPage" data-bind="foreach:listdata">
							<tr>
								<td><em data-bind="text:className"></em></td>
								<td><em data-bind="text:mustClassNum"></em></td>
								<td><em data-bind="text:mustClassTimes"></em></td>
								<td><em data-bind="text:actualClassTimes"></em></td>
								<td><em data-bind="text:attendanceRate"></em></td>
								<td class="operation"><a data-bind="attr:{href:url}" target="_blank">详细</td>
							</tr>
						</tbody>
					</table>
					<!-- ko if: listdata().length == 0 -->
					<div class="eval-rate-pictab m-tips f-block">
						<i></i> <span>对不起，没有您要查询的数据</span>
					</div>
					<!-- /ko -->
					<div
						data-bind="component: {
										name: 'leke-page',
										params: {
											curPage: curPage,
											totalSize: totalSize
					}}"></div>
				</div>
				<!-- /ko -->
				<!--ko if:stateChange()=='2'  -->
				<div class="m-table m-table-center f-mb0 classdetail">
					<table>
						<tr>
							<th>班级</th>
							<th>班主任</th>
							<th>计划旁听课堂数</th>
							<th>实际旁听课堂数</th>
							<th>旁听率</th>
							<th>旁听总时长min</th>
							<th>旁听次数</th>
							<th>操作</th>
						</tr>
						<tbody id="papersPage" data-bind="foreach:listdata2">
							<tr>
								<td><em data-bind="text:className"></em></td>
								<td><em data-bind="text:headTeacherName"></em></td>
								<td><em data-bind="text:mustAttendLessonNum"></em></td>
								<td><em data-bind="text:actualLessonTimes"></em></td>
								<td><em data-bind="text:attendanceRate"></em></td>
								<td><em data-bind="text:attendHours"></em></td>
								<td><em data-bind="text:attendTimes"></em></td>
								<td class="operation"><a data-bind="attr:{href:url}" target="_blank">详细</td>
							</tr>
						</tbody>
					</table>
					<!-- ko if: listdata2().length == 0 -->
					<div class="eval-rate-pictab m-tips f-block">
						<i></i> <span>对不起，没有您要查询的数据</span>
					</div>
					<!-- /ko -->
					<div
						data-bind="component: {
										name: 'leke-page',
										params: {
											curPage: curPage2,
											totalSize: totalSize2
					}}"></div>
				</div>
				<!-- /ko -->
			</div>
		</div>
	</div>
</div>

<script>
	window.data={query:${queryJson}}
	seajs.use('monitor/pages/common/crm/attendance/classlist');
</script>

</body>
</html>