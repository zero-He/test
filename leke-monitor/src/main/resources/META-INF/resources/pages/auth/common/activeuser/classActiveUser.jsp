<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>班级用户统计</title>
<%@ include file="/pages/common/meta.jsp"%>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css" type="text/css">
<link rel="stylesheet"
      type="text/css"
      href="https://static.leke.cn/styles/common/global.css">
<link rel="stylesheet"
      href="https://static.leke.cn/styles/lesson-monitor/lesson-monitor-pc.css">
  <link rel="stylesheet" href="https://sma2lbao.github.io/component/weekpicker/src/css/weekpicker.css">
</head>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
                <form action=""
                      method="post">
                    <div class="m-search-box">
                        <div class="item">
                            <p class="lm-textpane" >
                              <span>终端：<em id="terminal" title="${query.d}">${query.terminal}</em></span>
                              <c:if test="${roleId != 112 && roleId != 603}">
                              	<span>营销处：<em id="marketName" >${query.marketName}</em></span>
                              </c:if>
                              <c:if test="${roleId != 112 }">
                              	<span>客户经理：<em id="sellerName" >${query.sellerName}</em></span>
                              </c:if>
                              <span>学校：<em id="schoolName">${query.schoolName}</em></span>
                              <span>统计时间(${query.cycle})：<em id="ts" title="${query.cycle}">${query.ts}</em></span>
                            </p>
                        </div>
                        <div class="item">
                            <label>角色：</label>
                            <select class="u-select u-select-nm" id="roleId" data-bind="event:{change:roleChange}">
                            	<c:if test="${id ==100 }">
	                                <option value="100" selected="selected">学生</option>
	                                <option value="101">老师</option>
	                                <option value="102">家长</option>
                            	</c:if>
                            	<c:if test="${id ==102 }">
                            		<option value="100">学生</option>
                            		<option value="101">老师</option>
	                                <option value="102" selected="selected">家长</option>
                            	</c:if>
                            	<c:if test="${id ==101 }">
                            		<option value="100">学生</option>
	                                <option value="101" selected="selected">老师</option>
	                                <option value="102">家长</option>
                            	</c:if>
                            </select>
                            <label class="f-ml10">年级：</label>
                            <select name="gradeName" class="u-select u-select-nm" id="gradeName"
								data-bind="options:gradesData,
                                                       optionsText:'name',optionsCaption:'全部'">
							</select> 
                            <label class="f-ml10">班级：</label>
                            <select name="className" class="u-select u-select-nm" id="className"
								data-bind="options:classesData,
                                                       optionsText:'name',optionsCaption:'全部'">
							</select> 
						<div class="operation">
	                        <input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button"
								value="查询" data-bind="click:loaddata">
							<input class="u-btn u-btn-nm u-btn-bg-orange f-ml10 f-fr" type="button"
								value="导出" data-bind="click:exportExcel">
						</div>
                        </div>
                    </div>
                    <div class="tabs-bg">
                        <div class="m-table m-table-center">
                            <table>
                                    <tr>
                                        <th width="20%">年级</th>
                                        <th width="20%">班级</th>
                                        <th width="20%">累计有效用户</th>
                                        <th width="20%">活跃用户</th>
                                        <th width="20%">活跃率</th>
                                    </tr>
								<tbody id="papersPage" data-bind="foreach:listdata">
									<tr>
										<td><em data-bind="text:gradeName"></em></td>
										<td><em data-bind="text:className"></em></td>
										<td><em data-bind="text:validUsers"></em></td>
										<td><em data-bind="text:activeUsersCount"></em></td>
										<td><em data-bind="text:activePro"></em></td>
									</tr>
								</tbody>
                            </table>
                           <!-- ko if: listdata().length == 0 -->
							<div class="eval-rate-pictab m-tips f-block">
								<i></i> <span>对不起，没有您要查询的数据</span>
							</div>
							<!-- /ko -->
                        </div>
                        <!-- 用JS控件完成 这里只做样式 -->
                        						<div
							data-bind="component: {
										name: 'leke-page',
										params: {
											curPage: curPage,
											totalSize: totalSize
					}}"></div>
                    </div>
                </form>
            </div>
        </div>
<script>
	window.data={activeInfo:${activeInfo}};
	seajs.use('monitor/pages/common/activeuser/classActiveUser');
</script>
    </body>
</html>
	