<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>寒暑假作业 - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" href="${assets}/styles/common/global.css">
<link rel="stylesheet" href="${assets}/styles/homework/homework.css">
</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="m-bg"></div>
	<div class="g-bd">
		<%@ include file="/pages/navigate/navigate.jsp"%>
	 <div class="g-mn">
	 	<input type="hidden" name="isTeacherHeader" value="${isTeacherHeader}"/> 
		<input type="hidden" name="isTeacher" value="${isTeacher}"/> 
		<input type="hidden" name="isParent" value="${isParent}"/> 
		<input type="hidden" name="isStudent" value="${isStudent}"/> 
			
		 	
		<!-- 作业进度 -->
           <div class="c-homework-detailprogress" id="homeworkMainInfo" style="display:none">
              <div class="work  cover cover{{subjectId}}">
	        	
	            <div class="work-info">
	            	<h2 title="{{title}}">{{title}}</h2>
	                <div class="tips"><span class="time ">作业数：{{total}}</span></div>
	                <div class="btns"><a href="/auth/{{roleName}}/homework/vacationHomeworkList.htm?subjectId={{subjectId}}&classId={{classId}}&studentId={{userId}}&type={{type}}&spm=<c:if test="${currentRoleId eq 100 }">100014</c:if><c:if test="${currentRoleId eq 102 }">102021</c:if><c:if test="${currentRoleId eq 101 }">101003</c:if><c:if test="${currentRoleId eq 103 }">103022</c:if>" class="u-btn u-btn-nm u-btn-bg-gray">返回</a></div>
	            </div>
	        </div>
		

		
		
	        <div class="infomation">
	        	<div class="canvas"></div>
	        	<div class="start-time">开始答题时间<span class="num s-c-turquoise">{{firstTime}}</span></div>
	        	<div class="total">
	            	<div class="total-sum">作业总数<span class="num s-c-turquoise">{{total}}</span></div>
	            	</div>
	           		<div class="rest">
	           		<div class="total-rest">剩余未答作业数<span class="num s-c-orange">{{remain}}</span></div>
	           		</div>
	            
	        </div>
          </div>
       
           
          
          <!-- 我的作业 -->
          <form id="myHomeworkListForm" method="post" autocomplete="off">
		    <input type="hidden" name="id" value="${homeworkId}"/> 
		 	<div class="z-homework-detaillists">
                <ul id="myHomeworkList" style="display:none">
                	{{#dataList}} 
						<li class="item">
					         <div class="title">
					              <span class="name" title="{{homeworkName}}">{{homeworkName}}</span>
					             {{{homeworkStatus}}}
					             <div class="c-btns">
					                 <div class="m-btns m-btns-one">
					                     <div class="init-btn">
					                     	<a href="{{btnUrl}}" target="_blank" class="link">{{btnName}}</a>
										  </div>
					                 </div>
					             </div>
					         </div>
					     
					         <div class="tips">
					             <span class="tips-origin">来源：<b class="cur">乐课网</b></span>
					             <span class="tips-grade">分数：<b class="cur">{{score}}</b></span>
					             <p class="time">提交/截止时间：{{submitTime}} / {{closeTimeStr}}</p>
					         </div>
					     </li>
					{{/dataList}}
                </ul>
               <div class="page" id="page">
					<div class="f-hide tips f-tac" id="emptyTip">对不起，没有您要查询的数据</div>
                </div>
            </div>
	  	</form>
	 </div>

	</div>
<leke:pref/>
<script>
	seajs.use('homework/homework/vacationHomeworkDetail');
</script>

</body>
</html>
