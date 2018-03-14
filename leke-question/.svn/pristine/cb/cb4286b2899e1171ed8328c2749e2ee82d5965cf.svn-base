<#assign que = get_que(questionId)>
<#assign stage = get_stg(que.schoolStageId)>
<#assign subject = get_sbj(que.subjectId)>
<div class="exercises">
	<div class="c-exerciseslist">
		<ul>
			<li class="item">
				<div class="c-questiontype">
					<div class="content">
						<@p_stem que=que/>
					</div>
                </div>
            </li>
        </ul>
	</div>
</div>
<div class="analysis">
	<div class="info">
		<label class="title">答案：</label>
		<div class="list">
            <div class="crosshead">
                <@p_answer que=que/>
			</div>
		</div>
	</div>
	<div class="info">
		<label class="title">解析：</label>
		<div class="text"> 
			<@p_analysis que=que/>
		</div>
	</div>
	<div class="info">
		<#if que.questionTypeId??>
		<span class="label">${get_que_type(que.questionTypeId).questionTypeName}</span>
		</#if>
		<#if que.questionTypeId == 3>
		<span class="label">${que.isFbUnord ? string('无序批改',que.subjective ? string('手动批改','自动批改'))}</span>
		</#if>
	</div>
	<div class="info">
		<label class="title">学段学科：</label>${stage.schoolStageName} ${subject.subjectName}
	</div>
	<div class="info">
		<label class="title">知识点：</label>
		<div class="list">
			<#list que.knowledges as knowledge>
			<span class="point" title="${knowledge.path}">${knowledge.path}</span>
			</#list>
		</div>
	</div>
	<div class="info">
		<label class="title">教材章节：</label>
		<div class="list"> 
			<#list que.sections as section>
			<span class="point" title="${section.path}">${section.path}</span>
			</#list>
		</div>
	</div>
	<div class="info">
		<label class="title">标签：</label>
		<div class="list"> 
		<#list que.tags as tag>
			<span class="point" title="${tag.officialTagName}">${tag.officialTagName}</span>
		</#list>
		</div>
	</div>
	<div class="info facility">
		<label class="title">难易度：</label><span class="c-facility">
			<i class="<#if (que.difficultyInt > 0)>cur</#if>"></i>
			<i class="<#if (que.difficultyInt > 1)>cur</#if>"></i>
			<i class="<#if (que.difficultyInt > 2)>cur</#if>"></i>
			<i class="<#if (que.difficultyInt > 3)>cur</#if>"></i>
			<i class="<#if (que.difficultyInt > 4)>cur</#if>"></i>
		</span>
	</div>
</div>