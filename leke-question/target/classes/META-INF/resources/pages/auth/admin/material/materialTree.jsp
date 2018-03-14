<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>教材章节设置 - 乐课网</title>
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
    		<div class="base_view1 examination" style="margin:0;">
    			<div class="quesTreeleft">
    				<div class="treeCtrl">
		 				<button class="u-btn u-btn-auto u-btn-bg-turquoise btnAdd">添加</button>
		 				<button class="u-btn u-btn-auto u-btn-bg-turquoise btnModify">修改</button>
		 				<button class="u-btn u-btn-auto u-btn-bg-turquoise btnDel">删除</button>
		 				<button class="u-btn u-btn-auto u-btn-bg-turquoise btnUp">上移</button>
				 		<button class="u-btn u-btn-auto u-btn-bg-turquoise btnDown">下移</button>
		 			</div>
    				<div class="treeContainer j-book">
    					<ul id="materialTree" class="ztree"
    						data-material-id="${query.materialId}"></ul>
    				</div>
    			</div>
    			
    			<div class="quesContent">
	    			<div class="quesContentlt sectionView">
						<div class="pf_item">
							<div class="label">上级节点名称：</div>
							<div class="con">
								<input name="parentName" type="text" class="ipt_text" readonly="readonly"/>
							</div>
						</div>
						<div class="pf_item">
							<div class="label"><span class="tip">*</span>章节名称：</div>
							<div class="con">
								<input name="materialNodeName" type="text" class="ipt_text" readonly="readonly"/>
							</div>
						</div>
						<div class="pf_item">
							<div class="label">章节编号：</div>
							<div class="con">
								<input name="materialNodeCode" type="text" class="ipt_text" maxlength="16" readonly="readonly"/>
							</div>
						</div>
						<div class="pf_item">
							<div class="label">排序：</div>
							<div class="con">
								<input name="ord" type="text" class="ipt_text" size="3" readonly="readonly"/>
							</div>
						</div>
						<div class="pf_item" style="display:none;">
							<div class="label">关联知识点：</div>
							<div class="con knowledgeContainer">
							</div>
						</div>
	    			</div>
	    			<div class="quesContentrt" style="display:none;">
	    				<div class="treeCtrl">
			 				<button class="u-btn u-btn-nm u-btn-bg-turquoise btnAddKlg">绑定</button>
			 				<button class="u-btn u-btn-auto u-btn-bg-turquoise btnUpKlg">上移</button>
				 			<button class="u-btn u-btn-auto u-btn-bg-turquoise btnDownKlg">下移</button>
			 			</div>
	    				<div class="treeContainer j-book">
		    				<ul id="knowledgeTree" class="ztree"
		    					data-school-stage-id="${query.schoolStageId}" 
				 				data-subject-id="${query.subjectId}"></ul>
			 			</div>
	    			</div>
    			</div>
    		</div>
		</div>
	</div>
</div>

<%@ include file="tpl/treeTpls.jsp" %>

<script>
seajs.use('question/view/material/materialTree');
</script>
</body>
</html>