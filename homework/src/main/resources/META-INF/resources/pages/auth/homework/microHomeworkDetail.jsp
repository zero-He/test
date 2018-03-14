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
		
        <input type="hidden" name="subjectId" id="subjectId" /> 
		<input type="hidden" name="subjectName" id="subjectName" /> 
		
         <!-- 学习进度 --> 
           <div class="c-homework-detailprogress" id="homeworkMainInfo" style="display:none">
              <div class="work cover cover{{subjectId}}">
	        	
	            <div class="work-info">
	            	<h2 class="f-ellipsis" title="{{title}}">{{title}}</h2>
	            	<div class="publish f-ellipsis" title="{{bookName}}{{matVersionName}}">
		                <span class="publish-name">
		                    {{bookName}}
		                </span>
		                <span class="publish-icon">
		                    {{matVersionName}}
		                </span>
		        	</div>
	                <div class="tips"><span class="time ">总课时：{{total}}课时</span></div>
	                <div class="btns"><a href="/auth/{{roleName}}/homework/vacationHomeworkList.htm?subjectId={{subjectId}}&classId={{classId}}&studentId={{userId}}&type={{type}}&spm=<c:if test="${currentRoleId eq 100 }">100014</c:if><c:if test="${currentRoleId eq 102 }">102021</c:if><c:if test="${currentRoleId eq 101 }">101003</c:if><c:if test="${currentRoleId eq 103 }">103022</c:if>" class="u-btn u-btn-nm u-btn-bg-gray">返回</a></div>
	            </div>
	        </div>
	
	        <div class="infomation">
	        	<div class="canvas"></div>
	        	<div class="start-time">开始学习时间<span class="num s-c-turquoise">{{firstTime}}</span></div>
	        	<div class="total">
	            	<div class="total-sum">总课时<span class="num s-c-turquoise">{{total}}</span></div>
	            	</div>
	            	<div class="rest">
	            	
	            	<div class="total-rest">剩余未学课时<span class="num s-c-orange">{{remain}}</span></div>
	           		</div>
	           
	        </div>
          </div>
          
          <!-- 列表 -->
          <form id="myHomeworkListForm" method="post" autocomplete="off">
		    <input type="hidden" name="id" value="${homeworkId}"/> 
		 	<div class="z-homework-detaillists">
                <ul id="myHomeworkList" style="display:none">
                	{{#dataList}} 
						<li class="item">
					         <div class="title">
					              <a href="##" class="name" title="{{microName}}">{{microName}}</a>
					             <span class="status course-name" title="{{matNodeName}} • {{kName}}">{{matNodeName}} • {{kName}}</span>
					             </div>
					             <div class="tips">
					             <!-- <span class="tips-time"><i class="homework-icon "></i>5分20秒</span> -->
                                 <span class="tips-state"><i class="homework-icon "></i>配套作业正确率：<b class="cur">{{accuracy}}</b></span>
					             </div>
					             
					             <div class="btn">
					                     <c:choose>

										   <c:when test="${isStudent}">
										   	<a data-type="learn"  data-microId="{{microId}}" data-id="${homeworkId}"  class="u-btn" >{{btnName}}</a>
										   	<a class="u-btn {{workClass}}" data-type="work" data-knowledgeId="{{kId}}" data-knowledgeName="{{kName}}" date-microstate="{{microState}}" data-exerciseId="{{exerciseId}}">{{btnWorkName}}</a>
										   </c:when>
										   
										   <c:otherwise>  
										   	<a class="u-btn" data-type="learn" data-microId="{{microId}}" data-id="${homeworkId}" >查看微课</a>
										    <a class="u-btn" data-type="view" data-id="{{exerciseId}}" data-microHwState="{{microHwState}}" >查看练习</a>
										   </c:otherwise>
										</c:choose>
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

<script>
	seajs.use('homework/homework/microHomeworkDetail');
</script>

</body>
</html>
