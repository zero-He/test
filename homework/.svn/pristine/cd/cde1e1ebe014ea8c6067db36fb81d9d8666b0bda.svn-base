<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>练习列表</title>

 <link rel="stylesheet" href="https://static.leke.cn/styles/mobile/global.css">
    
<link rel="stylesheet" type="text/css" href="${assets}/styles/mobile/learn-pad.css?t=${_t}">
<style>
.content {
  height: 360px;
  overflow: hidden;
}
</style>
<leke:context />
</head>
<body>
	<div class="main">
        <div class=""></div>
        <div class="z-modeselection">
            <div class="modecon c-shadow">
                <div class="study">
                    <div class="studyico">
                        <i></i>
                    </div>
                    <h3>学习模式</h3>
                    <p>观看微课视频全面掌握知识点内容
                        <br> 培养学习方式是成功的前提哦！
                    </p>
                </div>
                <div class="practice">
                    <div class="practiceico">
                        <i></i></div>
                    <h3>练习模式</h3>
                    <p>自由组合章节或知识点出卷，检验你的学习成果
                        <br> 实践是检验真理的唯一标准，快来练习吧！
                    </p>
                </div>
            </div>
        </div>
    </div>
		



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
	<span>{{totalVideo}}</span>
</li>
{{/.}}
</script>
<script src="${assets}/scripts/common/mobile/common.js?_t=${_t}"></script>
<script src="/scripts/hd/exercise/index.js?_t=${_t}"></script>
<script src="/scripts/hd/exercise/scrolling.js?_t=${_t}"></script>
</body>
</html>