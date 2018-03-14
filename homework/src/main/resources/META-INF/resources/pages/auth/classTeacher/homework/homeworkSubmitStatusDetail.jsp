<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学生作业 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet"
	href="${assets}/styles/homework/homework.css?t=${_t}">
<link rel="stylesheet" type="text/css"
	href="${assets}/styles/tutor/clazz/select.css?t=${_t}">
<style>
	.m-table table{min-width:100%; width:auto;}
	.m-table table th{overflow:hidden; white-space:nowrap; text-overflow:ellipsis;}
	.m-table table th span, 
	.m-table table td span{display:block; width:120px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;}
</style>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<form action="" method="post" id="formPage" autocomplete="off">
				<div class="detail">
					<div class="m-search-box">
						<label for="" class="title">班级类型：</label> <select id="jClassType"
							class="u-select u-select-nm">
							<option value="2">选修班</option>
							<option value="1">行政班</option>
						</select>
						<label class="title">班级名称：</label>
						<div class="z-select-pd-menu">
							<input type="text" value="全部课程" id="jClazzSelect"
								readonly="readonly"> <input type="hidden" value=""
								name="courseId" id="jClazzId">
							<div class="pd-con" class="f-dn" id="jClazzOption">
								<ul></ul>
								<p>
									<a href="javascript:;" class="en-pd-lt" id="jPre"></a><a href="javascript:;"
										class="pd-rt" id="jNext"></a>
								</p>
							</div>
						</div>
						<input class="u-btn u-btn-nm u-btn-bg-turquoise" type="button"
							value="查询" id="bHomeworkList" />
						<div class="operation">
							<input class="u-btn u-btn-nm u-btn-bg-orange" type="button"
								id="jExportData" value="导出">
						</div>
					</div>
				</div>
			</form>
			<div class="m-table" style="overflow-x: auto;">
				<table>
					<thead id="jThead">
						
					</thead>
					<tbody id="jTbody">
						
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<textarea id="jClazz_tpl" class="f-dn">
		{{#dataList}}
			<li data-id="{{classId}}">{{className}}</li>
		{{/dataList}}
	</textarea>
	<script type="text/javascript">
		seajs.use('homework/homework/classTeacher/homeworkSubmitStatusDetail');
	</script>
</body>
</html>