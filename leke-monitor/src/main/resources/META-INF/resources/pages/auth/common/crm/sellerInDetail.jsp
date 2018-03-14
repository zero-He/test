<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>个人入驻客户经理明细-乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet"
      type="text/css"
      href="https://static.leke.cn/styles/common/global.css">
<link rel="stylesheet"
      href="https://static.leke.cn/styles/lesson-monitor/lesson-monitor-pc.css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
                   <div class="m-search-box">
                        <div class="item">
                            <p class="lm-textpane">
                              <span>统计时间：<em id="month">${query.month}</em></span>
                              <span>营销处：<em id="marketName">${query.marketName}</em></span>
                            </p>
                        </div>
                        <div class="item">
                          <label>乐号：</label>
                            <input type="text" class="u-ipt u-ipt-nm" id="loginName" autocomplete="off">

                            <label class="f-ml10">客户经理：</label>
                            <input type="text" class="u-ipt u-ipt-nm" id="sellerName">
                            <input type="hidden" id="jPageOrder" name="order" value="" />
							<input type="hidden" id="jPageSort" name="sort" value="" />
						<input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button"
							value="查询" data-bind="click:loaddata" id="jPageSearch">
						<input class="u-btn u-btn-nm u-btn-bg-orange f-ml10 f-fr" type="button"
							value="导出" data-bind="click:exportExcel" >
                        </div>
                    </div>
                    <div class="tabs-bg">
                      <div class="tabs-monitor-tip">
                        	客户经理统计
                      </div>
                        <div class="m-table m-table-center">
                            <table>
                            	<thead id="jPageHead">
                                    <tr>
                                        <th width="14.2%">乐号</th>
                                        <th width="14.2%">客户经理</th>
                                        <th width="14.2%">新增老师数</th>
                                        <th width="14.2%" class="m-sort-leke" data-column="tradeAmount">充值金额</th>
                                        <th width="14.2%">提成金额</th>
                                        <th width="14.2%">操作区</th>
                                    </tr>
                                 </thead>
								<tbody id="papersPage" data-bind="foreach:listdata">
									<tr>
										<td><em data-bind="text:loginName"></em></td>
										<td><em data-bind="text:sellerName"></em></td>
										<td><em data-bind="text:newTeacherNum"></em></td>
										<td><em data-bind="text:tradeAmount"></em></td>
										<td><em data-bind="text:commAmount"></em></td>
										<td class="operation">
											<a class="f-csp s-c-turquoise" data-bind="click:$parent.opendetail.bind($data)">详情</a> 
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
					}}"></div>
                  </div>
		</div>
	</div>
</body>

<script type="text/javascript">
	seajs.use('monitor/pages/common/crm/sellerInDetail');
</script>
</html>