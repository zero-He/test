<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/common/global.css">
<link rel="stylesheet" href="${assets}/styles/diag/analyse-1.2.css">

<title>备课</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body>
	<form id="form">
	<%@ include file="/pages/header/header.jsp"%>
	<div class="m-bg"></div>
	<div class="g-bd">
		<div class="g-mn">
			<div class="c-analyse__con">
				<div class="title">
				    ${startDate}——${endDate}${teacherName}老师上课课堂详情
					<div class="c-analyse__operate">
							<input type="button" id="export" value="导出"
								class="u-btn u-btn-nm u-btn-bg-orange" />
					</div>
				</div>
				<div class="m-table m-table-center m-table-fixed">
					<table>
						<thead>
							<tr>
								<th width="8%">序号</th>
								<th width="10%">课堂名称</th>
								<th width="22%" id="lessonTime" class="m-sorting m-sorting-desc">上课时间<i></i></th>
								<th width="13%">授课班级 <i></i></th>
								<th width="20%">
									教案/微课/课件/作业
									<i></i>
								</th>
								<th width="20%" id="ratio" class="m-sorting">
									到课率（实到/应到）
									<i></i>
								</th>
								<th width="10%">
									未上课学生数
									<i></i>
								</th>
								<th width="12%">操作 <i></i>
								</th>
							</tr>
						</thead>
						<tbody id="jtbodyData"></tbody>
					</table>
				</div>
				<div id="jTablePage" />
			</div>

		</div>
	</div>
	</div>
	<input type="hidden" id="startDate" name="startDate" value="${startDate}"/>
	<input type="hidden" id="endDate" name="endDate" value="${endDate}"/>
	<input type="hidden" id="teacherId" name="teacherId" value="${teacherId}"/>
	<input type="hidden" id="teacherId" name="teacherName" value="${teacherName}"/>
	<input type="hidden" id="orderAttr" name="orderAttr" />
	<input type="hidden" id="orderType" name="orderType" />
	</form>
</body>

<script id="dtlTpl" type="x-mustache">
{{#dataList}}
    <tr>
    	<td>{{index}}</td>
    	<td title="{{courseSingleName}}">{{courseSingleName}}</td>
    	<td>{{lessonTimeStr}}</td>
    	<td>{{className}}</td>
    	<td>{{teachPlan}}/{{mcCount}}/{{cwCount}}/{{hwCount}}</td>
    	<td>{{ratio}}%（{{realCount}}/{{totalCount}}）</td>
    	<td>{{absentCount}}</td>
        <td class="operation">
           <a href="${initParam.beikeServerName}/auth/common/beikepkg/beikePkgTemplateLessonEdit.htm?lessonId={{courseSingleId}}" target="blank">检查备课</a>
        </td>
    </tr>
{{/dataList}}
</script>
<script src="/scripts/diag/common/store.legacy.min.js"></script>
<script type="text/javascript">
	seajs.use('diag/teachingMonitor/beikeLessonDtl');
</script>
</html>