<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>老师课堂功能统计- 乐课网</title>
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

                    <div class="m-search-box">
                        <div class="item">
                            <p class="lm-textpane">
                               <span>学校名称：<em id="schoolName">${query.schoolName}</em></span>
					           <span>学段：<em id="stage" >${query.schoolStageName}</em></span>
                            </p>
                        </div>
                        <div class="item">
                            <label>事件：</label>
                            <select class="u-select u-select-nm" id="test">
                                <option value="1">快速问答</option>
                                <option value="2">随堂测试</option>
								<option value="3">点名-自动</option>
								<option value="4">点名-手动</option>
								<option value="5">息屏</option>
								<option value="6">授权</option>
								<option value="7">举手</option>
								<option value="8">分组讨论</option>
								<option value="9">评价</option>
								<option value="10">献花</option>
								<option value="11">作业讲解</option>
                            </select>
                            <label class="f-ml10">年级：</label>
                            <select class="u-select u-select-nm" id="gradeName">
                                <option value="">全部</option>
                            	<c:forEach items="${gradeNames }" var="gradeList">
                            		<option value="">${gradeList.gradeName}</option>
                            	</c:forEach>
                            </select>
                            <label class="f-ml10">学科：</label>
                            <select class="u-select u-select-nm" id="subjectName">
                                <option value="">全部</option>
                                <c:forEach items="${subjectNames }" var="subjectList">
                            		<option value="">${subjectList.subjectName}</option>
                            	</c:forEach>
                            </select>
                            <label class="f-ml10">老师姓名：</label>
                            <input type="text" value="" class="u-ipt u-ipt-nm" id="teacherName">
                        </div>
						<div class="item">
							<label >统计时间：</label> 
							<input type="text" id="startDay" name="startDay" class="Wdate u-ipt u-ipt-nm" 
							value="<fmt:formatDate value="${query.fromDate}" type="Date" pattern="yyyy-MM-dd"/>"/>
							<label >至</label>
							<input type="text" id="endDay" name="endDay" class="Wdate u-ipt u-ipt-nm" 
							value="<fmt:formatDate value="${query.endDate}" type="Date" pattern="yyyy-MM-dd"/>"/>
							<input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button"
								value="查询" data-bind="click:querydata">
							<input class="u-btn u-btn-nm u-btn-bg-orange f-ml10 f-fr" type="button"
								value="导出" data-bind="click:exportExcel">
						</div>
                    </div>
                    <div class="tabs-bg">
                        <div class="m-table m-table-center">
                            <table>
                                    <tr>
                                        <th width="10%">乐号</th>
                                        <th width="11%">老师姓名</th>
                                        <th width="10%">年级</th>
                                        <th width="11%">学科</th>
                                        <th width="11%">上课课堂数</th>
                                        <th width="11%">发起次数</th>
                                        <th>平均发起次数/课</th>
                                        <th width="11%">发起率</th>
                                        <th width="11%">操作区</th>
                                    </tr>
								<tbody id="papersPage" data-bind="foreach:listdata">
									<tr>
										<td><em data-bind="text:loginName"></em></td>
										<td><em data-bind="text:teacherName"></em></td>
										<td><em data-bind="text:gradeName"></em></td>
										<td><em data-bind="text:subjectName"></em></td>
										<td><em data-bind="text:lessonNum"></em></td>
										<td><em data-bind="text:sponsorNum"></em></td>
										<td><em data-bind="text:average"></em></td>
										<td><em data-bind="text:sponsorRate"></em></td>
		
										<td class="operation">
										<a class="f-csp s-c-turquoise" data-bind="click:$parent.opendetail.bind($data)">事件明细</a> 
										</td>
									</tr>
								</tbody>
                            </table>
                            <!-- ko if: listdata().length == 0 -->
							<div class="eval-rate-pictab m-tips f-block">
								<i></i> <span>对不起，没有您要查询的数据</span>
							</div>
							<!-- /ko -->
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
	</div>
</div>
<script>
	seajs.use('monitor/pages/common/lessonhavior/teacherLessonStatistics');
</script>

</body>

</html>
