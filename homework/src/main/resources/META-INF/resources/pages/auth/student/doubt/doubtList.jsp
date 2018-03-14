<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="leke" uri="http://www.leke.cn/plugins"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title><locale:message code="homework.doubt.list.title" /> - <locale:message code="common.page.header.leke" /></title>
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
			<div>
				<ul id="jResolvedTab" class="m-tab">
					<li data-resolved="1" class="active"><a><locale:message code="homework.doubt.doAnswer" /></a></li>
					<li data-resolved="0">
						<a><locale:message code="homework.doubt.unAnswer" /></a>
						<c:if test="${count!=0}">
							<span class="c-badge">
	                            ${count}
	                        </span>
                        </c:if>
					</li>
				</ul>
			</div>
			<form id="doubtListForm" method="post" autocomplete="off">
				<div class="m-search-box">
					<div class="item">
						<label class="title"><locale:message code="homework.homework.subject" />：</label>
						<input type="hidden" id="jSubjectId" name="subjectId" value="0" />
						<select id="jSubject" class="u-select u-select-nm"></select>
						<input type="hidden" id="jResolved" name="resolved" value="1" />
						<label class="title"><locale:message code="homework.doubt.askTime" />：</label>
						<input type="text" id="tStartTime" name="startTime" class="Wdate u-ipt u-ipt-nm">
						<locale:message code="homework.homework.form.search.to" />
						<input type="text" id="tEndTime" name="endTime" class="Wdate u-ipt u-ipt-nm">
						<div class="operation">
							<input class="u-btn u-btn-nm u-btn-bg-orange f-ml10" id="jButDoubt" type="button" value="我要提问">
						</div>
					</div>
					<div class="item">
						<label class="title"><locale:message code="homework.doubt.keyWord" />：</label>
						<input type="text" id="iDoubtTitle" name="doubtTitle" class="u-ipt u-ipt-nm">
						<label class="title">范围：</label>
						<select class="u-select u-select-mn" name="studentCollect">
							<option value="0">全部</option>
							<option value="1">收藏</option>
						</select>
						<label class="title">来源：</label>
						<select class="u-select u-select-mn" name="source">
							<option value="0">全部</span>
		                    <option value="1">课外</option>
		                    <option value="3">课堂</option>
		                    <option value="2">题目</option>
		                    <option value="4">点播</option>
						</select>
						<input class="u-btn u-btn-nm u-btn-bg-turquoise" id="bSeachDoubt" type="button" value="<locale:message code="homework.common.search" />">
					</div>
				</div>
				<div id="divPageParent">
					<div class="z-doubtlist">
				    	<ul id="jDoubtBody">
				    	</ul>
				    </div>
					<div id="page" class="f-fr"></div>
				</div>
			</form>
		</div>
	</div>
	
	<script id="jDoubtListTpl"  type="x-mustache">
	{{#dataList}}
	<li>
    	<p class="z-question-dec">
			<a href="${ctx}/auth/student/myDoubt/doubtList/getDoubtDetail.htm?doubtId={{doubtId}}" 
			target="_blank" data-id="{{doubtId}}">【{{sourceStr}}】{{#doubtContent}}{{doubtContent}}{{/doubtContent}}{{^doubtContent}}请点击查看详情{{/doubtContent}}</a>
		</p>
        <p>
	        <span class="doubt-info info-class">
	            <i></i>
					{{subjectName}} {{doubtTitle}}
	        </span>
	        <span class="doubt-info info-person">
	            <i></i>
					老师：
	            <span>{{teacherName}}</span>
	        </span>
	        <span class="doubt-info info-time">
	            <i></i>
					时间：
	            <span>{{explainTimeOnString}}</span>
	        </span>
			<span class="tarsh-doubt j-delete" data-i="{{doubtId}}"><i class="icon-trash"></i>删除</span>
            <span class="mark-doubt j-mark" data-i="{{doubtId}}">
				<i class="{{^studentCollect}}icon-star{{/studentCollect}}  {{#studentCollect}}icon-star-orange-i{{/studentCollect}}"></i>收藏
			</span>
		</p>
  	</li>
	{{/dataList}}
	</script>
	<leke:pref/>
	<script>
		seajs.use("homework/doubt/doubtList");
	</script>

</body>
</html>