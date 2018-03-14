<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>选择学校 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/pay/common/layout.css?t=${_t}">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-sd">
			<%@ include file="/pages/navigate/navigate.jsp"%>
		</div>
		
		<div class="g-mn">
			<form id="jPageForm" class="m-search-box">
				<div class="m-box-title j-search-box">
<!-- 				urlType: 1课程发布、2排课、3上课名单 -->
					<input type="hidden" id="urlType" value="${type}">
					<input type="hidden" id="tutorServerName" value="${initParam.tutorServerName}">
					<input type="hidden" id="schoolId" value="">
					
					<span class="name">学校名称：</span>
					<input type="text" id="schoolName" placeholder="请输入学校名称" class="u-ipt u-ipt-lg f-mr15 j-school-select" style="width:240px">
					<input type="button" id="btn_search" class="u-btn u-btn-nm u-btn-bg-turquoise" value="查询">
					
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('scs/school/selectSLForTechSupport');
	</script>
</body>

</html>