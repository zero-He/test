<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>自主练习-<locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp" %>

<link rel="stylesheet" href="${assets }/styles/learn/learn.css">
<link rel="stylesheet" type="text/css" href="${assets}/styles/resource/resource.css?t=${_t}">
</head>
<body>
	 <%@ include file="/pages/header/header.jsp"%>
    <div class="m-bg"></div>
 	 <div class="g-bd">
           <div class="z-modeselection">
               <div class="c-crumbs">
				<span>当前位置：</span>
				<a href="#"  data-bind="text: '首页', attr: {href : 'https://homework.leke.cn/auth/student/exercise/list.htm'}"></a>
				<span>-</span>
				<span>模式选择</span>
			</div>
               <div class="modecon c-shadow">
                   <div class="study">
                       <i class="studyico"></i>
                       <h3>学习模式</h3>
                       <p>观看微课视频全面掌握知识点内容
                           <br> 培养学习方式是成功的前提哦！
                       </p>
                        <a href="#" class="c-btn" data-bind="click: study"> 开始学习</a>
<!--                         <a class="c-btn c-btn-bg-gray" style="background: #ccc"> 暂未开放</a> -->
                   </div>
                   <div class="practice">
                       <i class="studyico"></i>
                       <h3>练习模式</h3>
                       <p>自由组合章节或知识点出卷，检验你的学习成果
                           <br> 实践是检验真理的唯一标准，快来练习吧！
                       </p>
                       <a href="#" class="c-btn" data-bind="click: exercise"> 开始练习</a>
                   </div>
               </div>
           </div>
       </div>
        <leke:pref />
       <div class="c-microlearningbg"></div>


<script>
seajs.use('homework/exercise/index');
</script>
</body>

</html>