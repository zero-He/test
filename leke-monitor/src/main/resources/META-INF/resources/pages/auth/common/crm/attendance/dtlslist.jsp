<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>考勤统计明细 - 乐课网</title>
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
				<div class="f-mb20 borderbottm">
					<label for="" class="title">角色：</label>
					<label for="" class="title">学生</label>
					<label for="" class="title f-ml25">学科：</label>
					<label for="" class="title">${query.subjectName}</label>
					<label for="" class="title f-ml25">班级：</label>
					<label for="" class="title">${query.className}</label>
					<label for="" class="title f-ml25">老师：</label>
					<label for="" class="title">${query.teacherName}</label>
					<label for="" class="title f-ml25">统计时间：</label>
					<label for="" class="title">
						<fmt:formatDate value="${query.fromDate}" type="Date" pattern="yyyy-MM-dd" />
						~
						<fmt:formatDate value="${query.endDate}" type="Date" pattern="yyyy-MM-dd" />
					</label>
				</div>

				<label for class="title">计划上课时间：</label>
				<input type="text" id="startDay" name="startDay" class="Wdate u-ipt u-ipt-nm" />
				<label for="" class="title f-ml5 f-mr5">至</label>
				<input type="text" id="endDay" name="endDay" class="Wdate u-ipt u-ipt-nm" />
				<label for class="title">课堂名称：</label>
				<input type="text" id="lessonName" name="lessonName" value="" />
				<div class="operation">
					<input type="button" id="btn_search" class="u-btn u-btn-nm u-btn-bg-turquoise f-mr10" value="查询" data-bind="click:loaddata">
					<input type="button" class="f-fr u-btn u-btn-nm u-btn-bg-orange" value="导出" data-bind="click:exportExcel">
				</div>

				<!-- <div class="f-mt15"></div> -->
			</div>
			<div class="f-mt20 bgwhite">
				<h3 class="tabtitle">
					考勤统计明细
				</h3>
				<div class="m-table m-table-center f-mb0 classdetail">
					<table>
						<tr>
							<th>计划上课时间</th>
							<th>课堂名称</th>
							<th>应上课人数</th>
							<th>实上课人数</th>
							<th>到课率</th>
						</tr>
						<tbody id="papersPage" data-bind="foreach:listdata">
							<tr>
								<td><em data-bind="text:lessonTimeStr"></em></td>
								<td><em data-bind="text:lessonName"></em></td>
								<td><em data-bind="text:mustClassTimes"></em></td>
								<td><em data-bind="text:actualClassTimes"></em></td>
								<td><em data-bind="text:attendanceRate"></em></td>
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
			</div>
		</div>
	</div>
</div>

<script>
	window.data={query:${queryJson}}
	seajs.use('monitor/pages/common/crm/attendance/dtlslist');
</script>

</body>
</html>