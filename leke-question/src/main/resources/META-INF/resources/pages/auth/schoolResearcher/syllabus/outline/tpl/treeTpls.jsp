<%@ page pageEncoding="UTF-8"%>
<script id="edit_tpl" type="x-mustache">
<form style="margin-top:10px;text-align: left;">
	<div class="pf_item">
		<div class="label">上级节点名称：</div>
		<div class="con">
			<input name="parentName" type="text" class="ipt_text" disabled="disabled" readonly="readonly"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label"><span class="s-c-r">*</span>章节名称：</div>
		<div class="con">
			<input name="outlineNodeName" type="text" class="ipt_text"/>
		</div>
	</div>
	<div class="pf_item">
		<div class="label">排序：</div>
		<div class="con">
			<input name="ord" type="text" class="ipt_text" maxlength="3" disabled="disabled" readonly="readonly"/>
		</div>
	</div>
</form>
</script>
