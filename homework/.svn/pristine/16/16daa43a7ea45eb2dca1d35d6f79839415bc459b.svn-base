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
	 	
	
			<input type="hidden" name="isParent" value="${isParent}"/> 
			<div class="g-mn">
			<c:if test="${isParent}">
				<div class="m-tab">
	             	<ul class="corrs">
	                 <c:forEach var="student" items="${studentList}" >
	                  
					 	<c:choose>

							   <c:when test="${student.id==studentId}">
							   	<li class="active" data-studentId="${student.id}"><a >${student.userName }</a></li>
					           </c:when>
							   
							   <c:otherwise>  
							   	<li data-studentId="${student.id}"><a >${student.userName }</a></li>
					           </c:otherwise>
						  
						</c:choose>
					 	
					 </c:forEach> 
		            </ul> 
		        </div>
	        </c:if>
			<div class="m-tab">
             	<ul class="corr">
                 <c:choose>

				   <c:when test="${type==1}">
				   	<li class="active" data-type="1"><a >作业</a></li>
		            <li data-type="2"><a >微课</a></li>
		           </c:when>
				   
				   <c:otherwise>  
				   	<li data-type="1"><a >作业</a></li>
		            <li class="active" data-type="2"><a >微课</a></li>
		           </c:otherwise>
				  
				</c:choose>
             	</ul> 
        	</div>
			<form id="myHomeworkForm" method="post" autocomplete="off">
			   <input type="hidden" name="type" value="${type}"/> 
			   <input type="hidden" name="studentId" value="${studentId}"/> 
	           <!-- 我的作业 -->
	           <div class="c-homework-worklists">
	                <ul id="myHomeworkList" style="display:none">
	                {{#dataList}} 
						<li class="item">
							<div class="cover cover{{subjectId}}"><span class="cover-name">{{typeName}}</span></div>
						        <div class="info">
						            <h5 title="{{title}}">{{title}}</h5>
						            <div class="num">{{typeName}}数：{{total}}</div>
						            <div class="state">{{dtTime}}</div>
						            <div class="publish f-ellipsis"  title="{{bookName}}{{matVersionName}}">
						                <span class="publish-name">
						                    {{bookName}}
						                </span>
						                <span class="publish-icon">
						                    {{matVersionName}}
						                </span>
						        	</div>
						        </div>
						        <div class="progress">
						            <div class="c-progress">
						                <span class="now" {{{width}}}></span>
						            </div>
						            <div class="percent">{{p}}</div>
						            
						        </div>
						        <div class="operate">
						          	<a href="{{viewUrl}}" class="u-btn u-btn-nm {{btnClass}}">{{btnkName}}</a>
						           
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
	seajs.use('homework/homework/vacationHomeworkList');
</script>
</body>
</html>
