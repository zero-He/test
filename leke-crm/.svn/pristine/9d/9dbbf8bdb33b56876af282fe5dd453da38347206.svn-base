<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>关联新学校</title>
	<%@ include file="/pages/common/meta.jsp" %>
</head>
<body>
	<%@ include file="/pages/header/header.jsp" %>
	<div class="g-bd"> <%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<h1 class="f-fs22 f-mb20">关联新学校</h1>
			<div class="m-form m-form-right">
				<form id="jAddForm">
					<ul>
						<li class="form-item">
							<input type="hidden" id="schoolId" value="">
							<label class="title">
								学校名称：
							</label>
							<div class="con">
								<input type="text" placeholder="请输入学校名称" id="schoolName" class="u-ipt u-ipt-lg f-mr15 j-school-select"  autocomplete="off"  style="width:240px">
							</div>
						</li>
					</ul>
					<div class="submit">
						<input id="jSave" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="关联" />
						<a href="${ctx}/auth/scs/customer/schoolList.htm"  class="u-btn u-btn-nm u-btn-bg-gray" type="button"  >取消</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	seajs.use("scs/customer/addSchoolCustomer");
</script>
</html>