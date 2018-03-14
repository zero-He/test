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
<div class="queOperation" id="j-operation">显示答案解析</div>
<div class="analysis" style="display: none" id="j-question-analysis">
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
</div>
