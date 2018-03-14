<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html class="f-h100pre">
<head>
<title><locale:message code="homework.homework.selectpapers" />
	- <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
 <link rel="stylesheet" href="${assets}/styles/homework/homework.css?t=${_t}" type="text/css">

</head>
<body class="m-arrangehomework-body">
	<!-- 布置作业-选择学生 -->
	<div class="m-arrangehomework-choosestudent commission-select-students m-dialog-iframe-conlg">
		<div class="m-table">
			<table>
				<tr>
					<th>班级</th>
					<th>分组</span>:
					</th>
				</tr>

				<!-- ko foreach: groups -->
				<tr>
					<td><label class="type pop-groups"
						data-bind="value: gradeName,click: function($data){  return $root.checkClasses($data.gradeName); }">
							<!-- <input type="checkbox" name="gradeName"
							data-bind="attr:{'data-grade':gradeName,
							id: gradeName}"> -->
							<b data-bind="text: gradeName "></b>
					</label> <i class="suf-ass-icon"></i>
					</td>
					<td></td>
				</tr>
				<!-- ko foreach: grades -->
				<tr class="grade-class">
					<td><label class="type pop-grades"
						data-bind="value: classId,click: function($data){ return $root.unChoGroup($data.classId); }">
							<input type="checkbox" name="classId"
							data-bind="attr:{'data-class': classId,'data-gradeName':gradeName,
							id: 'classId_' + classId +(courseSetId ? '_' + courseSetId : '')}">
							<b
							data-bind="text: className + (courseSetId ? '(' + gradeName + ')' : '')"></b>
					</label></td>
					<td data-bind="foreach: studentGroupList">
						<!-- ko if: groupName != null --> <input type="hidden"
						name="groupId" id="groupId" data-bind="value: groupId" /> <input
						type="hidden" name="groupName" id="groupName"
						data-bind="value: groupName" /> <label class="item"
						data-bind="click: function($data){ return $root.choGroup($data.classId); }">
							<input type="checkbox" name="groupId" id="groupId"
							data-bind="attr:{'data-group': classId,id: 'groupId_' + groupId}">
							<b data-bind="text: groupName"></b>
					    </label>
					 <!-- /ko -->
					</td>
				</tr>
				<!-- /ko -->
				<!-- /ko -->
			</table>

			<!-- ko if: groups().length == 0 -->
			<div class="dPageStyle">
				<div class="m-page" id="pageStyle">
					<div class="tips f-tac" data-bind="text: loadMsg"></div>
				</div>
			</div>
			<!-- /ko -->

		</div>
		<c:if test="${bsType eq 1 }">
			<div class="m-tiptext m-tiptext-text m-tiptext-warning">
				<i class="iconfont icon">󰅂 </i>
				<div class="msg">如需针对分层布置作业进行个性化教学，可进行教学分层，教学分层权限由教务开放。</div>
			</div>
		</c:if>
	</div>
	<div class="f-tac">
		<input type="button" class="u-btn u-btn-nm u-btn-bg-turquoise"
			id="jConfirm" data-bind="click: saveStudentGroup"
			value="<locale:message code="homework.common.confirm" />"> <input
			type="button" class="u-btn u-btn-nm u-btn-bg-gray" id="jCancel"
			data-bind="click: close"
			value="<locale:message code="homework.common.cancel" />">
	</div>
	<script type="text/javascript">
		window.BACKINFO = ${studentGroupIds};
		window.bsType = ${bsType == null ? 0 : bsType };
		seajs.use('homework/batch/popStudentGroup');
			
	
	</script>
</body>
</html>
