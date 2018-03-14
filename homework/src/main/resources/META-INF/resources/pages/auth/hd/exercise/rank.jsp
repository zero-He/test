<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<title>练习列表</title>
<link rel="stylesheet" href="${assets }/styles/mobile/global.css">
<link rel="stylesheet" href="${assets }/styles/mobile/homework.css">
<style>
.content {
  height: 360px;
  overflow: hidden;
}
</style>
<leke:context />
</head>
<body>
	<article class="z-mobile-homework">
		<section class="c-selftrain">
			<div class="weekrank">
				<div class="title">
					<p>第<span>${weekOfYear}</span>周前50名排行榜，排行数据截止到周日晚12点</p>
				</div>
				<div class="switch-tab">
					<ul class="j-ul-week">
						 <li class="active"><a data-id="${weekOfYear}"  class="hasdivision">本周</a></li>
                                <li><a data-id="${weekOfYear -1 }">上周</a></li>
                                <input type="hidden" id="j-weekOfYear" value="${weekOfYear}" />
                    </ul>
				</div>
				<div class="mit-info j-show-container f-hide">
					<div class="dynamic-info">
						 <span class="inform"></span>
	                     <span id="j-self-rank-tips"></span>
					</div>
					<div class="ranklist">
						<div class="header">
							<span>排名</span> <span>姓名</span> <span>学校</span> <span>正确数</span> 
						</div>
						<ul id="j-body-week-rank">
							
						</ul>
					</div>
				</div>
			    <div class="ohne-infom" id="j-no-week-rank">
                     <img src="${assets }/images/mobile/homework/wait4u.gif" alt="">
                </div>
			</div>
			<div class="todaytrain">
				<div class="title"></div>
					<div class="content lists j-show-container f-hide">
						<ul id="j-body-today-rank" >
						
						</ul>
					</div>
					<div class="tips" id="j-no-today-rank">
	                    <i></i>
	                    <p>今天的动态榜等着你的到来~</p>
	                </div>

			</div>
			<div class="btns">
                <span class="btn"><a href="index.htm" >开始练习</a></span>
                <span class="btn"><a href="list.htm">练习列表</a></span>
            </div>
		</section>
	</article>


<script type="x-mustache" id="j-tmpl-today-rank">
{{#.}}
<li>
	<img src="{{photo}}" alt="" class="head-pic">
	<p>
		<span>{{userName}}</span>
		<span class="f-grey">{{submitTime}}</span>
	</p>
	<p>
		<span class="f-grey">{{schoolName}}</span>
		<span>
			【{{subjectName}}】答对<i>{{rightNum}}</i>题
		</span>
	</p>
</li>
{{/.}}
</script>
<script type="x-mustache" id="j-tmpl-week-rank">
{{#.}}
<li class="rank{{rank}}">
	<span>{{rank}}</span>
	<span>
	 	<img src="{{photo}}" alt="" class="j-user-photo user-detail-show f-csp" data-uid= {{userId}}>
     	{{userName}}
	</span>
	<span>{{schoolName}}</span>
	<span>{{total}}</span>
</li>
{{/.}}
</script>
<script src="${assets}/scripts/common/mobile/common.js?_t=${_t}"></script>
<script src="/scripts/hd/exercise/rank.js?_t=${_t}"></script>
<script src="/scripts/hd/exercise/scrolling.js?_t=${_t}"></script>
</body>
</html>