<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>历史数据统计- 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/monitor/monitor.css" type="text/css">
</head>

<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd"> 
<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
			<form action="" method="post" id="jFormPage">
				<div class="m-search-box">
					<label for class="title">开始日期：</label>
					<input type="text" id="startDay" name="startDay" class="u-ipt u-ipt-nm"/>
					<label for class="title">结束日期：</label>
					<input type="text" id="endDay" name="endDay" class="u-ipt u-ipt-nm"/>
					<input type="button" id="btn_search" class="u-btn u-btn-nm u-btn-bg-turquoise" value="查询">
				</div>
				
				<div class="m-table">
					<table>
							<tr>
								<th width="10%">日期</th>
								<th width="20%">当日上课学校数</th>
								<th width="10%">当日课堂数</th>
								<th width="20%">当日高峰人数</th>
								<th width="20%">当日课堂总人次</th>
								<th width="20%">当日课堂总人数</th>
							</tr>
						<tbody id="jSuListTbody">
							
						</tbody>
					</table>
					<div class="m-page" id="jDivPage"></div>
				</div>
			</form>
	</div>
</div>

<textarea id="jSuListTpl" class="f-dn">
	{{#stats}} 
		<tr> 
			<td>{{day}}</td> 
			<td>{{schoolCount}}</td>
			<td>{{lessonCount}}</td>
			<td>{{maxActualStuCount}}</td> 
			<td>{{actualStuTimes}}</td> 
			<td>{{actualStuCount}}</td>  
		</tr> 
	{{/stats}}
</textarea>
<script>
	seajs.use('monitor/pages/technicalSupport/course/historyCourse');
</script>

</body>
</html>