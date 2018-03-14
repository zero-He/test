<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>入驻学校统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" href="${assets}/styles/monitor/monitor.css" type="text/css">
</head>

<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd"> 
<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
			<div class="att-rate-box">
				<div class="att-rate att-rate-border">
					<a href="${ctx}/auth/educationDirector/school/schoolStatisList.htm"><p>学校数量</p><p class="att-rate-amount">${ss.schoolCount}</p></a>
				</div>
				<div class="att-rate att-rate-border">
					<a href="${ctx}/auth/educationDirector/school/schoolStatisList.htm"><p>老师数量</p><p class="att-rate-amount">${ss.tchCount}</p></a>
				</div>
				<div class="att-rate att-rate-noborder">
					<a href="${ctx}/auth/educationDirector/school/schoolStatisList.htm"><p>学生数量</p><p class="att-rate-amount">${ss.stuCount}</p></a>
				</div>
			</div>
			
			<div>
				<ul class="m-tab j-type-tab">
					<li data-id="jTimeDiv" class="active">按时间统计</li>
					<li data-id="jAreaDiv" class="">按区域统计</li>
				</ul>
			</div>
			
			<div id="jTabDiv">
				<input type="hidden" id="jSchoolIds" value="${schoolIds}">
				<input type="hidden" id="jAreas" value='${areas}'>
				<input type="hidden" id="jIsLoadForArea" value="0">
				<div id="jTimeDiv" >
					<div class="m-search-box">
						<label>入驻时间：</label>
						<input type="text" id="jStartTime" name="startTime" class="u-ipt u-ipt-sm Wdate" value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM"/>"><label> - </label>
					    <input type="text" id="jEndTime" name="endTime" class="u-ipt u-ipt-sm Wdate" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM"/>">
						<input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button" value="查询" id="jTimeChartSelect" />
					</div>
					<div id="jTimeSchool" style="height: 400px;"></div>
					<div id="jTimeTeacher" style="height: 400px;"></div>
					<div id="jTimeStudent" style="height: 400px;"></div>
				</div>
				<div id="jAreaDiv" class="f-db">
					<div id="jAreaSchool" style="height: 400px;"></div>
					<div id="jAreaTeacher" style="height: 400px;"></div>
					<div id="jAreaStudent" style="height: 400px;"></div>
				</div>
			</div>
				
		</div>
	</div>
</div>


<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>
<script>
	seajs.use("monitor/school/schoolStatisForEduChart");
	seajs.use(['jquery', 'json', 'common/ui/ui-address/ui-market'], function($, json) {
		$('#jMarket').market({
			onSelect : function(obj) {
				var $area = $('#jAreas');
				$area.val(json.stringify(obj));
			}
		});
	});
</script>

</body>
</html>