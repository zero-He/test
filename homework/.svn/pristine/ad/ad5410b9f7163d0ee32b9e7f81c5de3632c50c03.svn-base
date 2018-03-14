<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>乐答</title>
    <link rel="stylesheet" href="${assets}/styles/mobile/global.css">
    <link rel="stylesheet" href="${assets}/styles/mobile/doubt-pad.css">
    <link rel="stylesheet" href="${assets}/scripts/common/mobile/ui/photoswipe/default-skin/photoswipe.css"> 
	<link rel="stylesheet" href="${assets}/scripts/common/mobile/ui/photoswipe/default-skin/default-skin.css"> 
    <%@ include file="/pages/common/cordova.jsp"%>
</head>
<body>
	<div class="z-doubt">
        <section class="z-doubt__header">
            <div class="c-header">
                <div class="c-header__side">
                    <a class="c-header__back"></a>
                    <div class="c-header__nav">
                        <a href="list.htm?resolved=0" class="c-header__switch <c:if test="${resolved eq 0}">c-header__switch--active</c:if>">
                            	未解答
                            <c:if test="${count!=0}">
			            		<span class="c-badge">${count}</span>
		            		</c:if>
                        </a>
                        <a href="list.htm?resolved=1" class="c-header__switch <c:if test="${resolved eq 1}">c-header__switch--active</c:if>">已解答</a>
                    </div>
                </div>
                <div class="c-header__main">
                    <span>乐答</span>
                    <a id="jTopSubmit" class="c-header__answer" data-w="${word}">${word}</a>
                </div>
            </div>
        </section>
        <!-- 左侧列表 -->
        <div class="z-doubt__side">
        	<%@ include file="/pages/auth/common/doubt/searchForPad.jsp"%>
        	<%@ include file="/pages/auth/common/doubt/listForPad.jsp"%>
        </div>
        
        <!-- 右侧详情 -->
        <section class="z-doubt__main">
            <div id="jDoubtDetail" class="z-doubt__maincon"></div>
        </section>
        <c:if test="${currentRoleId==100}">
			<a id="jAsking" class="c-asking"></a>
		</c:if>
    </div>
    <!-- Root element of PhotoSwipe. Must have class pswp. -->
	<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">
	    <!-- Background of PhotoSwipe. 
	         It's a separate element as animating opacity is faster than rgba(). -->
	    <div class="pswp__bg"></div>
	    <!-- Slides wrapper with overflow:hidden. -->
	    <div class="pswp__scroll-wrap">
	        <!-- Container that holds slides. 
	            PhotoSwipe keeps only 3 of them in the DOM to save memory.
	            Don't modify these 3 pswp__item elements, data is added later on. -->
	        <div class="pswp__container">
	            <div class="pswp__item"></div>
	            <div class="pswp__item"></div>
	            <div class="pswp__item"></div>
	        </div>
	        <!-- Default (PhotoSwipeUI_Default) interface on top of sliding area. Can be changed. -->
	        <div class="pswp__ui pswp__ui--hidden">
	            <div class="pswp__top-bar">
	                <!--  Controls are self-explanatory. Order can be changed. -->
	                <div class="pswp__counter"></div>
	                <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>
	                <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>
	                <!-- Preloader demo http://codepen.io/dimsemenov/pen/yyBWoR -->
	                <!-- element will get class pswp__preloader--active when preloader is running -->
	                <div class="pswp__preloader">
	                    <div class="pswp__preloader__icn">
	                      <div class="pswp__preloader__cut">
	                        <div class="pswp__preloader__donut"></div>
	                      </div>
	                    </div>
	                </div>
	            </div>
	            <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
	                <div class="pswp__share-tooltip"></div> 
	            </div>
	            <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
	            </button>
	            <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
	            </button>
	            <div class="pswp__caption">
	                <div class="pswp__caption__center"></div>
	            </div>
	        </div>
	    </div>
	</div>
    
    <script id="jVersionMaterialsListTpl" type="x-mustache">
		{{#materials}}
			<li data-i="{{materialId}}">{{materialName}}</li>
		{{/materials}}
	</script>
	
	<script id="jVersionPressesListTpl" type="x-mustache">
		{{#presses}}
			<li data-i="{{pressId}}">{{pressName}}</li>
		{{/presses}}
	</script>
	
	<script id="jVersionSectionListTpl" type="x-mustache">
		{{#section}}
			<li data-i="{{materialNodeId}}">{{materialNodeName}}</li>
		{{/section}}
	</script>
	
    <script id="jListTpl" type="x-mustache">
		<div class="subject">
            <div class="subjectcon">
				{{#resultList}}
                <span data-subjectid="{{subjectId}}" data-subjectname="{{subjectName}}" class="item">{{subjectName}}</span>
				{{/resultList}}
            </div>
            <i class="arrow"></i>
        </div>
        <div class="teacher">
			{{#resultList}}
            <div class="teachercon" style="display:none;" id="subjectTeacher_{{subjectId}}">
				{{#teacherList}}
                <span data-teacherid="{{teacherId}}" data-teachername="{{teacherName}}" class="item">{{teacherName}}</span>
				{{/teacherList}}
            </div>
			{{/resultList}}
        </div>
	</script>
	
    <leke:pref/>
    <script src="${assets}/scripts/common/mobile/common.js?_t=${_t}"></script>
    <script src="${assets}/scripts/common/mobile/ui/photoswipe/photoswipe.min.js"></script> 
	<script src="${assets}/scripts/common/mobile/ui/photoswipe/photoswipe-ui-default.min.js"></script> 
    <script src="/scripts/m/common/nativePlugins.js?_tt=${_t}"></script>
    <script src="/scripts/m/common/tools.js?_tt=${_t}"></script>
    <script src="/scripts/m/p/doubt/swipeRemove.js?_tt=${_t}"></script>
    <script src="/scripts/m/p/doubt/list.js?_tt=${_t}"></script>
    <script src="/scripts/m/p/doubt/handboard.js?_tt=${_t}"></script>
    <script src="/scripts/m/p/doubt/ask.js?_tt=${_t}"></script>
    <script src="/scripts/m/p/doubt/explain.js?_tt=${_t}"></script>
</body>
</html>