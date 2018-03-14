<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<title>学科优劣分析 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="m-table">
			<table >
				<thead>
					<tr>
						<th width="65%" >年级</th>
						<th width="35%" >操作</th>
					</tr>
				</thead>
				<tbody id="jtbodyData">

				</tbody>
			</table>
			<div class="m-tips" style="display: none;" id="jEmptyData"><i></i><span>对不起，没有您要查询的数据</span></div>
		</div>
	</div>

	<script id="jStudentsTemplate" type="text/handlebars">
		{{#.}}
			<tr>
				<td>{{gradeName}}</td>
				<td class="operation">
					<a class="detailsStudent" target="_blank"  
						href="${ctx}/auth/provost/swot/stuAnalysisDetails.htm?gradeId={{gradeId}}">查看详情</a>
				</td>
			</tr>
		{{/.}}
	</script>

</div>
<script type="text/javascript">
	seajs.use('diag/provost/stuAnalysis');
</script>
</body>
</html>