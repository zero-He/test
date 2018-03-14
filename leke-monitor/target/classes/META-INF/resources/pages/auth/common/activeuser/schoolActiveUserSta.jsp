<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>学校用户统计</title>
<%@ include file="/pages/common/meta.jsp"%>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css" type="text/css">
<link rel="stylesheet"
      type="text/css"
      href="https://static.leke.cn/styles/common/global.css">
<link rel="stylesheet"
      href="https://static.leke.cn/styles/lesson-monitor/lesson-monitor-pc.css">
<link rel="stylesheet" href="../../../../scripts/monitor/component/weekpicker.css">
</head>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
<div class="g-mn">


                    <div class="m-search-box">
                      <label class="f-ml10">选择终端：</label>
                      <select class="u-select u-select-nm" name="" id="leke_device">
                        <option value="10">全部</option>
                        <option value="5">乐课web</option>
                        <option value="4">乐课app</option>
                        <option value="2">乐课pad</option>
                        <option value="3">乐课客户端</option>
                      </select>
                    </div>
                    <div class="m-search-box">
                      <div class="item">
                        <label>周期：</label>
                        <select class="u-select u-select-mn f-mr10" name="" id="cycle_sel">
                          <option value="day_show">日</option>
                          <option value="week_show">周</option>
                          <option value="month_show">月</option>
                        </select>

                        <!-- 日选择器 -->
                        <span id="day_sel">
		                    <input type="text" id="startDay" name="startDay" class="Wdate u-ipt u-ipt-nm" />
							<label >至</label>
							<input type="text" id="endDay" name="endDay" class="Wdate u-ipt u-ipt-nm" />
                        </span>

                        <!-- 周选择器 -->
                        <span id="week_sel">
                          <input type="text" class="Wdate u-ipt u-ipt-nm"   id="startWeek">
                          <label> 至 </label>
                          <input type="text" class="Wdate u-ipt u-ipt-nm"   id="endWeek">
                        </span>

                        <!-- 月选择器 -->
                        <span id="month_sel">
						<input type="text" id="jStartTime" name="startTime" class="u-ipt u-ipt-sm Wdate" >
						<label> 至 </label>
					    <input type="text" id="jEndTime" name="endTime" class="u-ipt u-ipt-sm Wdate">
                        </span>
						<div class="operation">
							<input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button"
								value="查询" data-bind="click:loaddata">
							<input class="u-btn u-btn-nm u-btn-bg-orange f-ml10 f-fr" type="button"
								value="导出" data-bind="click:exportExcel">
						</div>
                      </div>
                      <div class="item">
                        <span id="sys_type" style="display: none;">
                          <label>设备：</label>
                          <select class="u-select u-select-nm" name="" id="os">
                            <option value="">全部</option>
                            <option value="">IOS</option>
                            <option value="">Android</option>
                          </select>
                        </span>

                        <span id="role_type">
                          <label>角色：</label>
                          <select class="u-select u-select-nm" id="roleId">
                            <option value="100">学生</option>
                            <option value="101">老师</option>
                            <option value="102">家长</option>
                          </select>
                        </span>
						<c:if test="${roleId!=112 && roleId != 603 && roleId != 604}">
							<label for="" class="title f-ml0">营销处：</label>
							<select name="marketId" class="u-select u-select-nm" id="marketId">
								<option value="">全部</option>
								<c:forEach var="item" items="${secondDpt }">
									<option value="${item.id }">${item.name }</option>
								</c:forEach>
							</select>
						</c:if>
						<c:if test="${roleId==604 }">
							<label for="" class="title f-ml0">营销部：</label>
							<select name="market" class="u-select u-select-nm" id="market" data-bind="event:{change:marketChange}">
								<option value="">全部</option>
								<c:forEach var="item" items="${firstDpt }">
									<option value="${item.id }">${item.name }</option>
								</c:forEach>
							</select>

							<label for="" class="title f-ml0">营销处：</label>
							<select name="marketId" class="u-select u-select-nm" id="marketId"
								data-bind="options:dptData,
                                                       optionsText:'name',
                                                       optionsValue:'id', optionsCaption:'全部'">
							</select>
						</c:if>
                        <span id="school_">
                          <label>学校：</label>
                          <input type="text" class="u-ipt u-ipt-nm" id="schoolName">
                        </span>
                      </div>
                      <div class="item">
                       <c:if test="${roleId!=112}">
	                        <span >
	                          <label>乐号：</label>
	                          <input type="text" class="u-ipt u-ipt-nm" id="loginName">
	                        </span>
	                        <span >
	                          <label>客户经理：</label>
	                          <input type="text" class="u-ipt u-ipt-nm" id="sellerName">
	                        </span>
                        </c:if>

                     </div>

                    </div>
                    <div class="tabs-bg f-mt20 bgwhite">
                        <div class="tabs-monitor-tip">
                          	用户活跃统计 <i class="i-question" title="1.累计注册用户：截止当前日期该学校的注册用户数。&#13;
2.累计有效用户：截止当前日期该学校除去毕业用户，失效用户外的有效用户。&#13;
3.活跃用户：指定时间内登录过乐课网的用户。&#13;
4.活跃率：指定时间内登录过乐课网的用户在有效用户中的占比。&#13;"></i>
                        </div>
                        <div class="m-table m-table-center">
                            <table>
                                    <tr>
                                        <th width="8.2%">日期</th>
	                                    <c:if test="${roleId!=112 }">
											<th width="7.2%">乐号</th>
	                                        <th width="8%">客户经理</th>
										</c:if>
										<c:if test="${roleId ==604 }">
											<th width="7.2%">营销部</th>
										</c:if>
										<c:if test="${roleId!=112 && roleId != 603}">
											 <th width="8%">营销处</th>
										</c:if>
                                        <th width="8%">学校名称</th>
                                        <th width="10.5%">累计注册用户</th>
                                        <th width="10.5%">累计有效用户</th>
                                        <th width="8%">活跃用户</th>
                                        <th width="7.2%">活跃率</th>
                                        <c:if test="${roleId !=604 && roleId !=600}">
                                        	<th width="11%.2">操作</th>
                                        </c:if>
                                    </tr>
								<tbody id="papersPage" data-bind="foreach:listdata">
									<tr>
										<td><em data-bind="text:ts"></em></td>
										<c:if test="${roleId!=112 }">
											<td><em data-bind="text:loginName"></em></td>
											<td><em data-bind="text:sellerName"></em></td>
										</c:if>
										<c:if test="${roleId ==604 }">
											<td><em data-bind="text:departName"></em></td>
										</c:if>
										<c:if test="${roleId!=112 && roleId != 603}">
											<td><em data-bind="text:marketName"></em></td>
										</c:if>
										<td><em data-bind="text:schoolName"></em></td>
										<td><em data-bind="text:registered"></em></td>
										<td><em data-bind="text:validUsers"></em></td>
										<td><em data-bind="text:activeUsersCount"></em></td>
										<td><em data-bind="text:activePro"></em></td>
										<c:if test="${roleId !=604 && roleId !=600}">
											<td><a class="f-csp s-c-turquoise" data-bind="click:$parent.opendetail.bind($data)">班级用户统计</a></td>
                                        </c:if>
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
        <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.js" charset="utf-8"></script>
        <script src="../../../../scripts/monitor/component/weekpicker.js" charset="utf-8"></script>
        <script type="text/javascript">
          function handleDevice() {
            $('#leke_device').change(function () {
              var selVal = $(this).val();
              if (selVal == '10') {
                $('#sys_type').hide();
                $('#role_type option[value=102]').show();
                $('#role_type option[value=101]').show();
                /* $('#role_type').show(); */
                $('#school_').show();
              }
              else if (selVal == '5') {
                $('#sys_type').hide();
                $('#role_type option[value=102]').show();
                $('#role_type option[value=101]').show();
                /* $('#role_type').show(); */
                $('#school_').show();
              }
              else if (selVal == '4') {
                $('#sys_type').show();
                $('#role_type option[value=102]').show();
                $('#role_type option[value=101]').show();
                /* $('#role_type').show(); */
                $('#school_').show();
              }
              else if (selVal == '2') {
                $('#sys_type').hide();
               /*  $('#role_type').hide(); */
               $('#role_type option[value=102]').hide();
               $('#role_type option[value=101]').show();
               $('#school_').show();
              }
              else if (selVal == '3') {
                $('#sys_type').hide();
                /* $('#role_type').hide(); */
                $('#role_type option[value=102]').hide();
                $('#role_type option[value=101]').hide();
                $('#school_').show();
              }
            });
          }

          function cycleSelect() {
            // 默认
            {
              $('#day_sel').show();
              $('#week_sel').hide();
              $('#month_sel').hide();
            }

            $('#cycle_sel').change(function () {
              var cycle_val = $(this).val();
              if(cycle_val == 'day_show') {
                $('#day_sel').show();
                $('#week_sel').hide();
                $('#month_sel').hide();
              } else if (cycle_val == 'week_show') {
                $('#day_sel').hide();
                $('#week_sel').show();
                $('#month_sel').hide();
              } else if (cycle_val == 'month_show') {
                $('#day_sel').hide();
                $('#week_sel').hide();
                $('#month_sel').show();
              }
            });
          }
          $(function () {
            handleDevice();
            cycleSelect();
          })
        </script>
        <script type="text/javascript">
          $('#startWeek').on('click', function () {
            var srcDom = $('#endWeek'); // 需要比对的dom
            var srcDomArray = srcDom.val().split('-');
            var domYear, domWeek;
            if (srcDomArray[0]) {
              domYear = srcDomArray[0].replace(/[^0-9]/ig, '');
            }
            if (srcDomArray[1]) {
              domWeek = srcDomArray[1].replace(/[^0-9]/ig, '');
            }

            new Weekpicker({
              output: '#startWeek', // input的id 跟jquery选择器一样
              year: 2000, // 初始年份
              week: 4, // 初始第几周
              minYear: 1990, // 最小年份
              maxYear: 2999, // 最大年份
              endYear: domYear, // 限制最大选择年份
              endWeek: domWeek, // 限制最大选择周
            }).init();
          })

          $('#endWeek').on('click', function () {
            var srcDom = $('#startWeek');
            var srcDomArray = srcDom.val().split('-');
            var domYear, domWeek;
            if (srcDomArray[0]) {
              domYear = srcDomArray[0].replace(/[^0-9]/ig, '');
            }
            if (srcDomArray[1]) {
              domWeek = srcDomArray[1].replace(/[^0-9]/ig, '');
            }
            new Weekpicker({
              output: '#endWeek', // input的id 跟jquery选择器一样
              year: 2000, // 初始年份
              week: 4, // 初始第几周
              minYear: 1990, // 最小年份，默认1900
              maxYear: 2999, // 最大年份，默认2099
              beginYear: domYear, //限制选择最小年份
              beginWeek: domWeek, //限制最小周
            }).init();
          })

          window.data={areas:${areas}};
          seajs.use('monitor/pages/common/activeuser/schoolActiveUserSta');
        </script>
    </body>
</html>
	