<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>考勤统计 - 乐课网</title>
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

				<label for="" class="title">角色：</label>
				<label for="" class="title">学生 </label>
				<label for="" class="title f-ml25">学校名称：</label>
				<label for="" class="title">${query.schoolName} </label>
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
					考勤统计<i class="queries"
						title="1.应上课人数：统计时间内，计划上课的人数（以上课课堂数为主统计）。&#13;
2.应上课人次：统计时间内，计划上课的人次（以上课课堂数为主统计）。&#13;
3.实上课人次：统计时间内，实际上课的人次（以上课课堂数为主统计）。&#13;
4.到课率：实上课人次/应上课人次*100%。&#13;
"></i>
					<!-- <div class="f-fr m-btns f-mt5 f-mr10">
						<div class="init-btn">
							<a href="javascript:void(0);">学生</a><b><i></i></b>
						</div>
						<menu style="">
							<li class="init-menu0" style="display:none">
								<a href="javascript:void(0);" data-bind="click:changeSel.bind($data,1)">学生</a>
							</li>
							<li class="init-menu1">
								<a href="javascript:void(0);" data-bind="click:changeSel.bind($data,2)">班主任</a>
							</li>
						</menu>
					</div> -->

				</h3>
				<div class="m-table m-table-center f-mb0 classdetail">
					<table>
						<tr>
							<th>学段</th>
							<th>应上课人数</th>
							<th>应上课人次</th>
							<th>实上课人次</th>
							<th>到课率</th>
						</tr>
						<tbody id="papersPage" data-bind="foreach:listdata">
							<tr>
								<td><em data-bind="text:schoolStageName"></em></td>
								<td><em data-bind="text:mustClassNum"></em></td>
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
	seajs.use('monitor/pages/common/crm/attendance/list');
</script>

</body>
</html>