<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>知识点设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="detail">
			<div class="m-search-box">
			<form action="listDatas.htm" id="formSearch">
    			<label class="title">学段：</label>
    			<select name="schoolStageId" class="u-select u-select-nm">
    			</select>
    			<span class="title">科目：</span>
    			<select name="subjectId" class="u-select u-select-nm">
    			</select>
    			<a class="u-btn u-btn-nm u-btn-bg-turquoise btnSearch">查询</a>
    			<div class="operation"><a class="u-btn u-btn-nm u-btn-bg-orange u-btn-auto btnAdd">添加知识点</a></div>
    		</form>
    		</div>
    		<div class="base_view1">
    			<div class="m-table">
					<table>
						<thead>
							<tr>
								<th width="80%" align="center">学段学科</th>
								<th width="20%" align="center">操作</th>
							</tr>
						</thead>
						<tbody id="tbRoots">
						</tbody>
					</table>
				</div>
    		</div>
		</div>
	</div>
</div>

<textarea id="row_tpl" style="display:none;">
	{{#knowledges}}
	<tr> 
		<td>{{knowledgeName}}</td>
		<td class="operation"> 
			<a class="link" target="_blank"
				href="${ctx}/auth/admin/knowledge/knowledgeTree.htm?knowledgeId={{knowledgeId}}&schoolStageId={{schoolStageId}}&subjectId={{subjectId}}">知识点设置</a>
			<a href="javascript:void(0)" data-knowledge-id="{{knowledgeId}}" class="link btnDel">删除</a>
		</td>
	</tr> 
	{{/knowledges}}
</textarea>

<script>
seajs.use('question/view/knowledge/knowledgeList');
</script>
</body>
</html>