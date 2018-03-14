<%@ page pageEncoding="UTF-8"%>
<c:if test="${device == 'hd'}">
<div class="m-tab">
<ul>
<li class="${SUBNAV == 'person-combined' ? 'active' : ''}"><a href="${ctx}/auth/hd/report/stuanaly/person-combined.htm">综合分析报告</a></li>
<li class="${SUBNAV == 'person-subject' ? 'active' : ''}"><a href="${ctx}/auth/hd/report/stuanaly/person-subject.htm">学科分析报告</a></li>
<li class="${SUBNAV == 'person-behavior' ? 'active' : ''}"><a href="${ctx}/auth/hd/report/stuanaly/person-behavior.htm">行为分析报告</a></li>
</ul>
</div>
</c:if>