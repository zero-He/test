<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>知识点设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/examination.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/question/material.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
    		<div class="base_view1 examination" style="clear:both;margin:0;">
    			<div style="float:left;width: 50%;">
    				<div class="treeCtrl">
		 				<button class="u-btn u-btn-nm u-btn-bg-turquoise btnAdd">添加</button>
		 				<button class="u-btn u-btn-nm u-btn-bg-turquoise btnModify">修改</button>
		 				<button class="u-btn u-btn-nm u-btn-bg-turquoise btnDel">删除</button>
		 			</div>
    				<div class="treeContainer j-book">
	    				<ul id="knowledgeTree" class="ztree"
	    					data-knowledge-id="${query.knowledgeId}" 
	    					data-school-stage-id="${query.schoolStageId}" 
			 				data-subject-id="${query.subjectId}"></ul>
		 			</div>
    			</div>
    			<div style="margin-left:50%;width: 50%;" id="knowledge_view">
    			</div>
    		</div>
		</div>
	</div>
</div>

<textarea id="edit_tpl" style="display:none;">
<form class="f-mt10 f-tal">
	<div class="pf_item">
		<div class="label">上级知识名称：</div>
		<div class="con">
			<input name="parentName" type="text" class="ipt_text" value="{{parentName}}" readonly="readonly"/>
			<input name="parentId" type="hidden" class="ipt_text" value="{{parentId}}"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label"><span class="tip">*</span>知识点名称：</div>
		<div class="con">
			<input name="knowledgeName" type="text" class="ipt_text" value="{{knowledgeName}}"/>
			<input name="knowledgeId" type="hidden" class="ipt_text" value="{{knowledgeId}}"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label">知识点编号：</div>
		<div class="con">
			<input name="knowledgeCode" type="text" class="ipt_text" maxlength="30" value="{{knowledgeCode}}"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label">排序：</div>
		<div class="con">
			<input name="ord" type="text" class="ipt_text" value="{{ord}}"  maxlength="3"/>
		</div>
	</div>
</form>
</textarea>
<textarea id="view_tpl" style="display:none;">
<div class="f-mt10 f-tal">
	<div class="pf_item">
		<div class="label">上级知识名称：</div>
		<div class="con">
			<input name="parentName" type="text" class="ipt_text" value="{{parentName}}" readonly="readonly"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label"><span class="tip">*</span>知识点名称：</div>
		<div class="con">
			<input name="knowledgeName" type="text" class="ipt_text" value="{{knowledgeName}}" readonly="readonly"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label">知识点编号：</div>
		<div class="con">
			<input name="knowledgeCode" type="text" class="ipt_text" value="{{knowledgeCode}}" readonly="readonly"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label">排序：</div>
		<div class="con">
			<input name="ord" type="text" class="ipt_text" value="{{ord}}" size="3" readonly="readonly"/>
		</div>
	</div>
</div>
</textarea>

<script>
seajs.use('question/view/knowledge/knowledgeTree');
</script>
</body>
</html>