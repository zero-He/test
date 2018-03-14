<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学校课堂功能统计详情- 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css"
	type="text/css">
<link rel="stylesheet"
      type="text/css"
      href="https://static.leke.cn/styles/common/global.css">
<link rel="stylesheet"
      href="https://static.leke.cn/styles/lesson-monitor/lesson-monitor-pc.css">
<style>
.sel {
	color: #0BA29A;
	border: solid 1px #0BA29A;
}

.m-table th {
	white-space: nowrap;
}
</style>
</head>

<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form action=""
                      method="post">
                    <div class="m-search-box">
                        <div class="item">
                            <p class="lm-textpane">
					           <span>学校名称：<em id="schoolName">${query.schoolName}</em></span>
					           <span>学段：<em id="stage" >${query.schoolStageName}</em></span>
                            </p>
                        </div>
                   
                   
					<div class="item">
						<label >统计时间：</label> 
						<input type="text" id="startDay" name="startDay" class="Wdate u-ipt u-ipt-nm" 
						value="<fmt:formatDate value="${query.fromDate}" type="Date" pattern="yyyy-MM-dd"/>"/>
						<label>至</label>
						<input type="text" id="endDay" name="endDay" class="Wdate u-ipt u-ipt-nm" 
						value="<fmt:formatDate value="${query.endDate}" type="Date" pattern="yyyy-MM-dd"/>"/>
						<input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button"
							value="查询" data-bind="click:loaddata">
						<input class="u-btn u-btn-nm u-btn-bg-orange f-ml10 f-fr" type="button"
							value="导出" data-bind="click:exportExcel">
					</div>
                    </div>
                    <div class="tabs-bg">

                        <div class="m-table m-table-center">
                            <table>
                                    <tr>
                                        <th width="20%">事件</th>
                                        <th width="20%">上课课堂数</th>
                                        <th width="20%">发起次数</th>
                                        <th width="20%">平均发起次数/课</th>
                                        <th width="20%">发起率</th>
                                    </tr>
								<tbody id="papersPage" data-bind="foreach:listdata">
									<tr>
										<td><em data-bind="text:type"></em></td>
										<td><em data-bind="text:lessonNum"></em></td>
										<td><em data-bind="text:sponsorNum"></em></td>
										<td><em data-bind="text:average"></em></td>
										<td><em data-bind="text:sponsorRate"></em></td>
									</tr>
								</tbody>
                            </table>
                        </div>
                        <div
							data-bind="component: {
									name: 'leke-page',
									params: {
										curPage: curPage,
										totalSize: totalSize
						}}">
				  		</div>
                    </div>
                </form>
	</div>
</div>
<script>
	seajs.use('monitor/pages/common/lessonhavior/schoolLessondetail');
</script>

</body>

</html>
