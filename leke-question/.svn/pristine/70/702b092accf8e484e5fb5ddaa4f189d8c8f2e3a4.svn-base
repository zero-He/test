<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>我的习题</title>
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
                        <div class="text j-scope-cur">我的资源</div>
                        <div class="c-droplist">
                            <ul class="j-scope" style="display: none;">
                                <li class="item cur" data-share-scope="1">我的资源</li>
                                <li class="item" data-share-scope="4">我的收藏</li>
                                <li class="item" data-share-scope="10">我的分享</li>
                            </ul>
                        </div>
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
                    <div class="setup j-setup">
                        <span class="searchico j-searchico"></span>
                        <span class="search j-search-setting"></span>
                    </div>
                </div>
                <div class="conditionlist j-conditionlist">
                   
                </div>
            </div>
            <div class="resourcecon c-exerciseslist exerciseslist conheight j-question-content">
            	<ul class="j-question-ul"></ul>
            </div>
            <div class="exercisesoperationlist">
	            <span class="btn"><span class="exercisebasket"><i></i> 习题篮<em id="j-question-cart-count">0</em></span></span>
	            <span class="btn testassembly" id="j-to-paper-add">去组卷</span>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript" src="${assets}/scripts/common/mobile/zepto.min.js"></script>
<script type="text/javascript" src="${assets}/scripts/common/knockout/knockout-3.4.0.js"></script>
<script type="text/javascript" src="${assets}/scripts/common/mobile/common.js"></script>
<script type="text/javascript" src="/scripts/repository/filter/pad/repo-filters.min.js"></script>
<script type="text/javascript" src="/scripts/repository/service/RepoCst-pad.js"></script>
<script type="text/javascript" src="/scripts/repository/service/HistoryServicePad.js"></script>
<script type="text/javascript" src="/scripts/question/pad/questionService.js"></script>
<script type="text/javascript" src="/scripts/question/pad/question.cart.js"></script>
<script type="text/javascript" src="/scripts/core-business-pad/widget/jquery.leke.resShare.js"></script>
<script type="text/javascript" src="${ctx}/scripts/common/mobile/ui/ui-coursewarePreview/ui-coursewarePreview.js"></script>
<script type="text/javascript" src="/scripts/core-business-pad/question/que-audio.js"></script>
<script type="text/javascript" src="/scripts/question/pages/teacher/question/personalPad/list.js"></script>
</html>