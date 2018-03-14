<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title><locale:message code="homework.doubt.questionAnswer" /> - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/doubt/doubt.css?t=${_t}">
<style type="text/css">
	.icon-star-orange-i{width: 15px;height: 15px;background: url('${assets}/images/homework/doubt/icon-star-orange.png') no-repeat center center;display: inline-block;vertical-align: sub;background-size: 15px 15px;}
</style>
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
		<div class="g-mn">
			<form id="doubtListForm" method="post" autocomplete="off">
				<div class="examination" id="divPageParent">
						<ul class="m-tab">
							<li class="active aIsSolve" data-value="false">
								<a>
									<locale:message code="homework.doubt.unAnswer"/>
								</a>
								<c:if test="${count!=0}">
									<span class="c-badge">
			                            ${count}
			                        </span>
		                        </c:if>
							</li>
							<li class="aIsSolve" data-value="true"><a><locale:message code="homework.doubt.doAnswer"/></a></li>
						</ul>
						<div class="m-search-box">
							<div class="item">
								<label class="title"><locale:message code="homework.doubt.askTime"/>：</label>
								<input type="text" id="tStartTime" name="startTime" class="Wdate u-ipt u-ipt-nm">
								<locale:message code="homework.homework.form.search.to"/>
								<input type="text" id="tEndTime" name="endTime" class="Wdate u-ipt u-ipt-nm">
								<label class="title">来源：</label>
								<select class="u-select u-select-mn" name="source">
									<option value="0">全部</span>
				                    <option value="1">课外</option>
				                    <option value="3">课堂</option>
				                    <option value="2">题目</option>
				                    <option value="4">点播</option>
								</select>
							</div>
							<div class="item">
								<label class="title"><locale:message code="homework.doubt.asker"/>：</label>
								<input type="text" id="iUserName" name="userName" class="u-ipt u-ipt-nm">
								<label class="title"><locale:message code="homework.doubt.keyWord"/>：</label>
								<input type="text" id="iDoubtTitle" name="doubtTitle" class="u-ipt u-ipt-nm">
								<input type="hidden" name="resolved" id="hIsSolve" value="false">
								<label class="title">范围：</label>
								<select class="u-select u-select-mn" name="teacherCollect">
									<option value="0">全部</option>
									<option value="1">收藏</option>
								</select>
								<input class="u-btn u-btn-nm u-btn-bg-turquoise" id="bSeachDoubt" type="button" value="<locale:message code="homework.common.search"/>">
							</div>
						</div>
						<div class="z-doubtlist">
							<div class="m-tiptext m-tiptext-text m-tiptext-normal">
		                        <i class="iconfont icon">󰅂 </i> 
		                        <div class="msg">
		                            <a href="https://static.leke.cn/pages/help/help.html?class=how-to-title" target="_blank">韩愈：师者，所以传道受业解惑也。及时解答学生提问不但可以帮助学生还有乐豆奖励哦！</a>
		                        </div>
		                    </div>
					    	<ul id="jDoubtBody">
					    	</ul>
							<div id="page" class="f-fr"></div>
					    </div>
				</div>
			</form>
		</div>
	</div>
	<script id="jDoubtListTpl"  type="x-mustache">
	{{#dataList}}
	<li>
    	<p class="z-question-dec">
			<a href="${ctx}/auth/teacher/myDoubt/getDoubtDetail.htm?doubtId={{doubtId}}" 
			target="_blank" data-id="{{doubtId}}">【{{sourceStr}}】{{#doubtContent}}{{doubtContent}}{{/doubtContent}}{{^doubtContent}}请点击查看详情{{/doubtContent}}</a>
		</p>
        <p>
	        <span class="doubt-info info-class">
	            <i></i>
					{{subjectName}}  {{doubtTitle}}
	        </span>
	        <span class="doubt-info info-person">
	            <i></i>
					提问者：
	            <span>{{userName}}</span>
	        </span>
	        <span class="doubt-info info-time">
	            <i></i>
					时间：
	            <span>{{explainTimeOnString}}</span>
	        </span>
			<span class="tarsh-doubt j-delete" data-i="{{doubtId}}"><i class="icon-trash"></i>删除</span>
            <span class="mark-doubt j-mark" data-i="{{doubtId}}">
				<i class="{{^teacherCollect}}icon-star{{/teacherCollect}}  {{#teacherCollect}}icon-star-orange-i{{/teacherCollect}}"></i>收藏			
			</span>
		</p>
  	</li>
	{{/dataList}}
	</script>
	<script>
		seajs.use("homework/doubt/teacherDoubtList");
	</script>

</body>
</html>