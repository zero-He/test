<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>课时统计-入驻老师</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css" type="text/css">
<style>
	.m-table th{white-space:nowrap;}
</style>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	
	
	<div class="g-mn">             
         <form action=""  method="post">
             <div class="m-search-box ">
              	<span>乐号：<em id="loginName">${query.loginName}</em></span>&nbsp;&nbsp;&nbsp;&nbsp;
                <span>老师名称：<em id="teacherName">${query.teacherName}</em></span>&nbsp;&nbsp;&nbsp;&nbsp;
                <span>统计时间：<em id="month">${query.month}</em></span>
               
				<div class="operation">
					<input type="button" class="f-fr u-btn u-btn-nm u-btn-bg-orange" value="导出" data-bind="click:exportExcel">
				</div>
			 </div>
             <div class="m-search-box f-pt15">
                      <div class="tabs-monitor-tip">
                       		<b>课时统计</b>
                      </div>
                      <br>
                      <div class="m-table m-table-center">
                            <table>
                                    <tr>
                                        <th width="12.5%">已结束课堂数</th>
                                        <th width="12.5%">上课课堂数</th>
                                        <th>上课率</th>
                                        <th width="12.5%">应上课人次</th>
                                        <th width="12.5%">实上课人次</th>
                                        <th width="12.5%">到课率</th>
                                        <th width="12.5%">应上课学生数</th>
                                        <th width="12.5%">实上课学生数</th>
                                    </tr>
                                <tbody id="papersPage" data-bind="foreach:listdata"> 
                                    <tr>
		                                <td><em data-bind="text:finishedClassNum"></em></td>
										<td><em data-bind="text:attendedClassNum"></em></td>
										<td><em data-bind="text:classRate"></em></td>
										<td><em data-bind="text:mustClassTimes"></em></td>
										<td><em data-bind="text:actualClassTimes"></em></td>
										<td><em data-bind="text:attendanceRate"></em></td>
										<td><em data-bind="text:mustClassNum"></em></td>
										<td><em data-bind="text:actualClassNum"></em></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
             </div>
         </form>
     </div>
</div>
  </body>
<script type="text/javascript">
	seajs.use('monitor/pages/common/crm/indiClassStatistics');
</script>
  
</html>
