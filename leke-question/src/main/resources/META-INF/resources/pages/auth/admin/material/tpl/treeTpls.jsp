<%@ page pageEncoding="UTF-8"%>
<script id="edit_tpl" type="x-mustache">
<form style="margin-top:10px;text-align: left;">
	<div class="pf_item">
		<div class="label">上级节点名称：</div>
		<div class="con">
			<input name="parentName" type="text" class="ipt_text" readonly="readonly"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label"><span class="tip">*</span>章节名称：</div>
		<div class="con">
			<input name="materialNodeName" type="text" class="ipt_text"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label">章节编号：</div>
		<div class="con">
			<input name="materialNodeCode" maxlength="16" type="text" class="ipt_text"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label">排序：</div>
		<div class="con">
			<input name="ord" type="text" class="ipt_text" maxlength="3"/>
		</div>
	</div>
</form>
</script>

<script id="knowledges_tpl" type="x-mustache">
{{#knowledges}}
<div class="knowledgeNode" data-kid="{{knowledgeId}}" data-mid="{{materialNodeId}}" >
	<em>{{materialNodeName}}</em>
	<a href="javascript:void(0);" class="del btnDelKlg">x 删除</a>
</div>
{{/knowledges}}
</script>