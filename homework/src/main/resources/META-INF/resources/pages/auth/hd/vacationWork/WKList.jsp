<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${title}</title>
<meta id="homework-meta" content="width=1920px, initial-scale=0.5, maximum-scale=0.5, user-scalable=0.5" name="viewport">

<link rel="stylesheet" href="${assets}/styles/mobile/global.css" />
<link rel="stylesheet"
	href="${assets}/styles/mobile/homework/homeworkpad.css" />
	<style type="text/css">
 		.workhome-body-bc{position:static;}
 		.work-bd{position:static;}
 	</style> 
</head>
<body>
<div id="jwk" class="workhome-body-bc">
	<input name="id" type="hidden" value="${workId}" />
	<input name="subjectId" type="hidden" value="${subjectId}" />
	<input name="subjectName" type="hidden" value="${subjectName}" />
	<nav class="work-nav">
	
		<div id="jLstbody" class="work-hd">
			<!--汇总-->
		</div>
		</nav>
		<div id="jbody" class="work-bd">
			<ul id="jDLstbody" class="work-bd-ul">
			</ul>
		</div>
	</div>
	<textarea id="jLsttpl" class="f-dn" style="display: none">
	   	<div class="work-hd-progress">
			<div class="circleProgress_wrapper">
				<div class="work-wrapper work-wrapper-left">
					<div class="work-wrapper-circleProgress work-wrapper-leftcircle"></div>
				</div>
				<div class="work-wrapper work-wrapper-right">
					<div class="work-wrapper-circleProgress work-wrapper-rightcircle"></div>
				</div>
				<div class="work-wrapper-mask">
					<span class="work-hd-progress-name">学习进度</span> <span
						class="circlePercent work-hd-progress-detail">{{finishRate}}</span>%
				</div>
			</div>
		</div>

    <ul class="work-hd-content">
        <li class="work-hd-content-item">
            <p class="work-hd-content-count">
                课时总数
            </p>
            <p class="work-hd-content-num">
                {{total}}
            </p>
        </li>
        <li class="work-hd-content-item">
            <p class="work-hd-content-count">
                剩余未学课时
            </p>
            <p class="work-hd-content-num work-hd-content-surplus">
                {{relay}}
            </p>
        </li>
        <li class="work-hd-content-item">
            <p class="work-hd-content-count">
                开始学习时间
            </p>
            <p class="work-hd-content-num">
               {{firstTime}}
            </p>
        </li>
    </ul>
</textarea>

	<textarea id="jDLsttpl" class="f-dn" style="display: none">
	     {{#dataList}}
	       
			 <li class="work-bd-item">
            <div class="micro-lesson-name">
                <h3>
                   {{microName}}
                </h3>
               <!--  <span class="micro-lesson-time">
                
                   {{time}} 
                </span> -->
                <span class="micro-lesson-grade">
                      {{accuracy}}
                </span>
            </div>
            <div class="micro-lesson-detail">
                <h3>
                   {{matNodeName}}·{{kName}}
                </h3>
            </div>
            <div>
            {{{btns}}} 
            </div>
        </li>
			{{/dataList}}
		
</textarea>

</body>
<script src="${assets}/scripts/common/mobile/common.js"></script>
<script src="/scripts/hd/homework/vacationWork/trans.js"></script>
<script src="/scripts/hd/homework/vacationWork/dropBind.js"></script>
<script src="/scripts/hd/homework/vacationWork/WKList.js"></script>
</html>