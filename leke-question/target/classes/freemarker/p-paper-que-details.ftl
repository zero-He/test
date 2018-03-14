<#list records as record>
<#assign que = get_que(record.questionId)>
<li class="item" data-qid="${record.questionId?c}" data-qtid="${record.questionTypeId?c}">
	<@p_stem que=que/>
	
	<div class="paperinfo">
		<#if que.questionTypeId??>
		<label class="label">${get_que_type(que.questionTypeId).questionTypeName}</label>
		</#if>
		<#if que.questionTypeId == 3>
		<label class="label">${que.isFbUnord ? string('无序批改',que.subjective ? string('手动批改','自动批改'))}</label>
		</#if>
		<#if que.subs??>
		<label class="label">${que.subs?size}小题</label>
		</#if>
		<span class="count">
			组卷数：
			<em>${que.usedCount!0}</em>
		</span>
		<span class="facility">
			难易度：
			<span class="c-facility">
			<#list [0, 0.2, 0.4, 0.6, 0.8] as diff>
				<i<#if que.difficulty gt diff> class="cur"</#if>></i>
			</#list>
			</span>
		</span>
		<span class="checkbox" data-qid="${record.questionId?c}" data-qtid="${record.questionTypeId?c}"></span>
	</div>
	<div class="exercisesoperation">
		<span class="oper j-btn-praise"><i class="praise"></i>点赞</span>
		<span class="oper j-btn-fav"><i class="nocollect"></i>收藏</span>
		<span class="oper j-btn-correct"><i class="errorcorrection"></i>纠错</span>
	</div>
</li>
</#list>