<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>活跃用户统计</title>
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
					<!-- <div class="m-tab">
			            <ul>
			                <li class="active">
			                    <a href="">活跃用户统计</a>
			                </li>
			            </ul>
			        </div> -->

                    <div class="m-search-box">
                      <label class="f-ml10">终端：</label>
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
                         &nbsp
                         <span id="role_type">
                          <label>角色：</label>
                          <select class="u-select u-select-nm" id="roleId">
                            <option value="100">学生</option>
                            <option value="101">老师</option>
                            <option value="102">家长</option>
                          </select>
                        </span>			
                        
						<div class="operation">
							<input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button"
								value="查询" data-bind="click:loadList">
							<input class="u-btn u-btn-nm u-btn-bg-orange f-ml10 f-fr" type="button"
								value="导出" data-bind="click:exportExcel">
						</div>
						
                      </div>
                                   
                    </div>
                    
				     <br>
				       <div class="chartWrap">
                     
	                       <div class="chartNav" style="top:-46px;">
	                          <button type="button" class="u-btn u-btn-bg-turquoise u-btn-at" id="but"> 活跃用户</button>
	                          <button type="button" class="u-btn u-btn-bg-gray u-btn-at" id="but1">活跃率</button>
	                        </div>
                       <div class="chartComt" id="barSta" style="width:958px;background:#fff;" >

                       </div>
                       <div class="chartComt" id="barSta1" style="width:958px;background:#fff;">

                       </div>
                       
                     </div>        
	                          
                    <div class="tabs-bg f-mt20 bgwhite">
                        <div class="tabs-monitor-tip">
                          	数据明细 <i class="i-question" title="1 累计有效用户数：截止到统计时间的最后一天，平台总的有效用户数（除去已毕业学生，无效用户）。&#13;
2 活跃用户：统计时间内登陆过平台的用户。&#13;
3 活跃率：活跃用户/累计有效用户，衡量市场的活跃程度。&#13;"></i>
                        </div>
                        
	                  <div class="m-table m-table-center">
		                <table>
		                    <tr>
		                        <th width="25%">日期</th>
		                        <th width="25%">累计有效用户</th>
		                        <th width="25%">活跃用户</th>
		                        <th width="25%">活跃率</th>
		                    </tr>
		                    <tbody id="papersPage" data-bind="foreach:listdata">
		                    <tr>
		                        <td><em data-bind="text:ts"></em></td>
		                       	<td><em data-bind="text:allValidUser"></em></td>
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
              }
              else if (selVal == '5') {
                $('#sys_type').hide();
                $('#role_type option[value=102]').show();
                $('#role_type option[value=101]').show();
                /* $('#role_type').show(); */
              }
              else if (selVal == '4') {
                $('#sys_type').show();
                $('#role_type option[value=102]').show();
                $('#role_type option[value=101]').show();
                /* $('#role_type').show(); */
              }
              else if (selVal == '2') {
                $('#sys_type').hide();
               /*  $('#role_type').hide(); */
               $('#role_type option[value=102]').hide();
               $('#role_type option[value=101]').show();
              }
              else if (selVal == '3') {
                $('#sys_type').hide();
                /* $('#role_type').hide(); */
                $('#role_type option[value=102]').hide();
                $('#role_type option[value=101]').hide();
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

         /*  window.data={areas:${areas}}; */
          seajs.use('monitor/pages/common/activeuser/platformActiveUserSta');
        </script>
    </body>
    
    <script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
    <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.js" charset="utf-8"></script>
    <script type="text/javascript">

          $('.chartNav').on('click', '.u-btn-bg-gray', function () {
            $(this).siblings().removeClass('u-btn-bg-turquoise');
            $(this).siblings().addClass('u-btn-bg-gray');
            $(this).removeClass('u-btn-bg-gray');
            $(this).addClass('u-btn-bg-turquoise');
            if($(this).text().indexOf("活跃率") === -1) {
                $('#barSta').show();
                $('#barSta1').hide();
            }
            else{
                $('#barSta').hide();
                $('#barSta1').show();
            }
          });
	</script>
</html>

	