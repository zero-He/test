<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>习题-区域资源库</title>
<leke:context />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="${assets}/styles/mobile/global.css">
<link rel="stylesheet" href="${assets}/styles/mobile/resource-pad.css">
<link rel="stylesheet" href="${ctx}/scripts/common/mobile/ui/ui-coursewarePreview/skin/ui-coursewarePreview.css">
</head>
<body>
	<div class="main">
        <div class="z-resource">
            <div class="menu">
                <div id="divChoseMenu" class="menu"></div>
            </div>
            <div class="c-condition">
           		<div class="condition">
                    <div class="resouceselect">
                        <div class="text j-cur-league">${leagueName}</div>
                        <c:if test="${leagues.size() > 0}">
	                        <div class="c-droplist j-league-ul" style="display: none">
	                            <ul>
	                            	<c:forEach items="${leagues}" var="league">
	                            		<li class="item j-league-li" data-League-id="${league.leagueId}" data-league-name="${league.leagueName}">
	                            			${league.leagueName}
	                            		</li>
	                            	</c:forEach>
	                            </ul>
	                        </div>
                        </c:if>
                    </div>
                    <div class="searchcon">
                        <div id="search" class="search j-search-content"  style="display: none;">
		                    <div class="c-search">
		                        <input type="text" placeholder="请输入名称或内容" name="content" id="j-content" maxlength="128">
		                        <span class="del j-content-del">×</span>
		                        <span class="searchbtn j-search"> <i></i></span>
	                    	</div>
	                    	<em class="cancel j-search-cancel">取消</em>
	                    </div>
                    </div>
                    <div class="setup">
                        <span class="searchico j-searchico"></span>
                        <span class="search j-search-setting"></span>
                        <span class="sort j-order">
                            <div class="c-droplist">
	                            <ul class="j-order-by" style="display: none;">
	                                <li class="item cur" data-order-by="1">最新</li>
	                                <li class="item" data-order-by="2">引用最多</li>
	                                <li class="item" data-order-by="4">收藏最多</li>
	                                <li class="item" data-order-by="3">点赞最多</li>
	                            </ul>
                        	</div>
                        </span>
                    </div>
                </div>
                <div class="conditionlist j-conditionlist">
                   
                </div>
            </div>
            <div class="resourcecon c-exerciseslist exerciseslist conheight j-question-content">
            	<ul class="j-question-ul"></ul>
            </div>
            <div class="exercisesoperationlist">
            	<span class="btn"><span class="exercisebasket"><i></i>习题篮<em id="j-question-cart-count"></em></span></span>
            	<span class="btn testassembly" id="j-to-paper-add">去组卷</span>
            </div>
        </div>
    </div>
    <input type="hidden" name="leagueId" id="leagueId" value="${leagueId}" />
</body>
<script type="text/javascript" src="${assets}/scripts/common/mobile/zepto.min.js"></script>
<script type="text/javascript" src="${assets}/scripts/common/knockout/knockout-3.4.0.js"></script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/common.js"></script>
<script type="text/javascript" src="/scripts/repository/filter/pad/repo-filters.min.js"></script>
<script type="text/javascript" src="/scripts/repository/service/RepoCst-pad.js"></script>
<script type="text/javascript" src="/scripts/repository/service/HistoryServicePad.js"></script>
<script type="text/javascript" src="/scripts/question/pad/questionService.js"></script>
<script type="text/javascript" src="/scripts/question/pad/question.cart.js"></script>
<script type="text/javascript" src="${ctx}/scripts/common/mobile/ui/ui-coursewarePreview/ui-coursewarePreview.js"></script>
<script type="text/javascript" src="/scripts/core-business-pad/question/que-audio.js"></script>
<script type="text/javascript" src="/scripts/question/pages/teacher/question/leaguePad/list.js"></script>
</html>