<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>乐答</title>
    <link rel="stylesheet" href="${assets}/styles/mobile/global.css">
    <link rel="stylesheet" href="${assets}/styles/mobile/doubt.css">
    <%@ include file="/pages/common/cordova.jsp"%>
    <style type="text/css">
    	.c-content .c-doubter__select{
    		display: none;
    	}
    	.c-doubter__con--edit .c-doubter__select{
    		display: block;
    	}
    	.c-content--fixed .ctrl-con{
    		top: 0px;
    	}
    </style>
</head>
<body onload="onLoad();">
	<!-- <header class="c-header">
        <span class="back"></span>
        	乐答
        <span class="more j-defult-top"></span>
        <div class="c-hidden" id="more-pane">
            <div class="more-alert j-defult-top">
                <div class="nav-arrow"></div>
                <ul>
                    <li class="j-collect active" data-i="0">全部</li>
                    <li class="j-collect" data-i="1">收藏</li>
                    <li id="edit">编辑</li>
                </ul>
            </div>
            <div class="bg-alert"></div>
        </div>
       	<a id="jCancel" class="operation" style="display: none;">取消</a>
    </header> -->
    <section id="jDroploadId" class="c-content" style="top:0px">
		<div class="ctrl-con">
			<nav class="c-nav">
		        <ul>
		            <li data-i="0" class="<c:if test="${resolved eq 0}">cur</c:if> j-resolved">
		            	<a>未解答
		            	<c:if test="${count!=0}">
			            	<span class="c-badge">${count}</span>
		            	</c:if>
		            	</a>
		            </li>
		            <li class="<c:if test="${resolved eq 1}">cur</c:if> j-resolved" data-i="1"><a>已解答</a></li>
		        </ul>
		    </nav>
			<%@ include file="/pages/auth/common/doubt/search.jsp"%>
		</div>
		<%@ include file="/pages/auth/common/doubt/list.jsp"%>
	</section> 
    <script src="/scripts/m/doubt/list.js?_tt=20171115"></script>
</body>
</html>