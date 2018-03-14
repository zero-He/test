<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>作业系统新增明细-乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${assets}/styles/gili/gili.css?t=${_t}" />
<link rel="stylesheet" href="${assets}/scripts/common/ui/ui-scrollbar/skin/default/jquery-scrollbar.css?t=${_t}" />
<link rel="stylesheet" href="${assets}/styles/tutor/common/inputDisabledChange.css?t=${_t}" type="text/css" />
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css" type="text/css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<div class="z-statisticalcenter">
				<div class="m-search-box f-pt15">
					<form id="form">
						<div class="f-mb20 borderbottm">
							<label for="" class="title">学段：</label> <label for="" class="title">${schoolStageName }</label> <label for="" class="title f-ml25">学校名称：</label>
							<label for="" class="title">${schoolName }</label>
						</div>
						<input type="hidden" name="schoolId" id="schoolId" value="${schoolId }" />
						<input type="hidden" name="schoolStageId" id="schoolStageId" value="${schoolStageId }" />
						<input type="hidden" name="statisticsTimeStart" id="statisticsTimeStart" value="${statisticsTimeStart }" />
						<input type="hidden" name="statisticsTimeEnd" id="statisticsTimeEnd" value="${statisticsTimeEnd }" />
						<label for="" class="title f-ml0">年级：</label>
						<select name="gradeId" class="u-select u-select-nm" id="gradeId"
							data-bind="options:gradeData,
                                                       optionsText:'gradeName',
                                                       optionsValue:'gradeId',
                                                       optionsCaption:'请选择'">
						</select>
						<label for="" class="title">乐号：</label>
						<input class="u-ipt u-ipt-nm" type="text" name="lekeSn" id="lekeSn" />
						<label for="" class="title">姓名：</label>
						<input class="u-ipt u-ipt-nm" type="text" name="studentName" id="studentName" />
						<div class="operation">
							<input class="u-btn u-btn-nm u-btn-bg-turquoise f-mr10" value="查询" type="submit" id="find" />
							<input class="f-fr u-btn u-btn-nm u-btn-bg-orange" value="导出" data-bind="click:studentExport">
						</div>
					</form>
				</div>
				<div class="f-mt20 bgwhite">
					<h3 class="tabtitle">作业系统新增明细</h3>
					<div class="m-table m-table-center f-mb0">
						<table>
							<thead>
								<tr>
									<th>乐号</th>
									<th>姓名</th>
									<th>年级</th>
									<th>首次使用时间</th>
								</tr>
							</thead>
							<tbody id="j_bodyComments">
							</tbody>
						</table>
					</div>
					<div class="page" id="divPage"></div>
				</div>
			</div>
		</div>
	</div>
</body>
<script id="j_tempComments" type="x-mustache">
{{#dataList}}
<tr>
<td>{{lekeSn}}</td>
<td>{{studentName}}</td>
<td>{{gradeName}}</td>
<td>{{useDateStr}}</td>
</tr>
{{/dataList}}
</script>
<script type="text/javascript">
	window.data={gradeStr : ${gradeStr}};
	seajs.use('monitor/pages/common/crm/workNewStudent');
</script>
</html>