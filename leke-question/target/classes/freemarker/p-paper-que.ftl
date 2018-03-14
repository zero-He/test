<@p_stem que=que seq='0.'; curQue>
	<div class="paperinfo" data-qid="${curQue.questionId?c}">
		<#if curQue.questionTypeId??>
		<label class="label">${get_que_type(curQue.questionTypeId).questionTypeName}</label>
		</#if>
		<#if curQue.subjective?? && curQue.subjective>
		<label class="label">手动批改</label>
		</#if>
		<#if curQue.isFbUnord?? && curQue.isFbUnord>
		<label class="label">无序批改</label>
		</#if>
		<#if curQue.subs??>
		<label class="label">${curQue.subs?size}小题</label>
		</#if>
		<#if !(curQue.parentId??)>
		<span class="count">
			组卷数：
			<em>${curQue.usedCount!0}</em>
		</span>
		<span class="facility">
			难易度：
			<span class="c-facility">
			<#list [0, 0.2, 0.4, 0.6, 0.8] as diff>
				<i<#if que.difficulty gt diff> class="cur"</#if>></i>
			</#list>
			</span>
		</span>
		</#if>
		<div class="setscore" data-qid="${curQue.questionId?c}">
		</div>
	</div>
</@p_stem>