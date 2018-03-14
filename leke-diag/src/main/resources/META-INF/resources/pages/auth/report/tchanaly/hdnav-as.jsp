<%@ page pageEncoding="UTF-8"%>
<c:if test="${device == 'hd'}">
<div class="m-tab">
<ul>
<li class="${SUBNAV == 'klass-score' ? 'active' : ''}"><a href="${ctx}/auth/hd/report/tchanaly/klass-score.htm">班级成绩分析</a></li>
<li class="${SUBNAV == 'klass-behavior' ? 'active' : ''}"><a href="${ctx}/auth/hd/report/tchanaly/klass-behavior.htm">班级行为分析</a></li>
<li class="${SUBNAV == 'teach-behavior' ? 'active' : ''}"><a href="${ctx}/auth/hd/report/tchanaly/teach-behavior.htm">教学行为分析</a></li>
</ul>
</div>
</c:if>