<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ include file="/pages/common/cordova.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>练习报告查看页</title>
    <link rel="stylesheet" href="${assets }/styles/mobile/global.css">
    <link rel="stylesheet" href="${assets }/styles/mobile/homework.css">
    <leke:context />
</head>
<body>
    <article class="z-mobile-homework">
        <section class="c-trainreport">
            <div class="summary">
                	<c:choose>
                		<c:when test="${exercise.accuracy ==100 }">
			                <div class="tips1"></div>
			                <div class="describe">
			                	<c:if test="${!firstExercise && exercise.growth > 0 }">
			                	<div class="trend trend1"></div>
			                	</c:if>
			                	<c:if test="${!firstExercise && exercise.growth < 0 }">
			                	<div class="trend trend2"></div>
			                	</c:if>
	                		  	<p>
	                		  		<c:if test="${firstExercise }">
			                        <span class="check check1"></span>
	                		  		</c:if>
			                        <span class="f-turquoise">
			                            	正确率<em>${exercise.accuracy }%</em>
			                        </span>
			                    </p>
			                    <div>
			                       	 此次练习你答对
			                        <span class="f-turquoise">${exercise.rightNum }</span>题，答错
			                        <span class="f-turquoise">${exercise.totalNum - exercise.rightNum }</span>题，正确率
			                        <span class="f-turquoise">${exercise.accuracy }%</span>。
			                    </div>
			                    <div>
			                    	<c:if test="${exercise.exerciseType eq 1 }">章节</c:if><c:if test="${exercise.exerciseType eq 2 }">知识点</c:if>掌握的很棒，加油！
			                    </div>
		               		</div>
                		</c:when>
                		<c:when test="${exercise.accuracy < 60 }">
                			<c:if test="${exercise.growth eq 0 || exercise.growth == null || exercise.growth > 0 }">
            			    <div class="tips3"></div>
                			</c:if>
                			<c:if test="${ firstExercise || exercise.growth < 0 }">
            			    <div class="tips2"></div>
                			</c:if>
			                <div class="describe">
			                	<c:if test="${!firstExercise && exercise.growth > 0 }">
			                	<div class="trend trend1"></div>
			                	</c:if>
			                	<c:if test="${!firstExercise && exercise.growth < 0 }">
			                	<div class="trend trend2"></div>
			                	</c:if>
	                		  	<p>
			                        <span class="f-orange">
			                            	正确率<em>${exercise.accuracy }%</em>
			                        </span>
			                    </p>
			                    <div>
			                       	 此次练习你答对
			                        <span class="f-turquoise">${exercise.rightNum }</span>题，答错
			                        <span class="f-orange">${exercise.totalNum - exercise.rightNum }</span>题，正确率
			                        <span class="f-orange">${exercise.accuracy }%</span>。
			                    </div>
			                    <div>
			                    	<c:if test="${exercise.growth > 0 }">小乐为不懈努力的你点赞，再接再厉哦~</c:if>
			                    	<c:if test="${exercise.growth == null || exercise.growth <= 0 || firstExercise }">
			                    	你的练习结果不佳，需要多多练习提升自己哦~
			                    	</c:if>
			                    </div>
		               		</div>
                		</c:when>
                		<c:otherwise>
                			<c:if test="${exercise.growth > 0 || firstExercise }">
            			    <div class="tips1"></div>
                			</c:if>
                			<c:if test="${exercise.growth <= 0 || exercise.growth == null}">
            			    <div class="tips3"></div>
                			</c:if>
			                <div class="describe">
			                	<c:if test="${!firstExercise && exercise.growth > 0 }">
			                	<div class="trend trend1"></div>
			                	</c:if>
			                	<c:if test="${!firstExercise && exercise.growth < 0 }">
			                	<div class="trend trend2"></div>
			                	</c:if>
	                		  	<p>
	                		  		<c:if test="${firstExercise }">
			                        <span class="check check2"></span>
	                		  		</c:if>
			                        <span class="f-turquoise">
			                            	正确率<em>${exercise.accuracy }%</em>
			                        </span>
			                    </p>
			                    <div>
			                       	 此次练习你答对
			                        <span class="f-turquoise">${exercise.rightNum }</span>题，答错
			                        <span class="f-turquoise">${exercise.totalNum - exercise.rightNum }</span>题，正确率
			                        <span class="f-turquoise">${exercise.accuracy }%</span>。
			                    </div>
			                    <div>
			                    	<c:if test="${exercise.growth > 0 || firstExercise }">小乐为不懈努力的你点赞，再接再厉哦~</c:if>
			                    	<c:if test="${exercise.growth <= 0 }">
			                    	练习时需要专心仔细，才能更好的掌握知识点哦~
			                    	</c:if>
			                    </div>
		               		</div>
                		</c:otherwise>
                	</c:choose>
            </div>
			<c:if test="${directReport != null && directReport.size() > 0}">
				<c:forEach  items="${directReport }" var="item" varStatus="status" >
					<ul class="aslip">
					    <li>
					        <p class="f-grey">知识点${status.index+1 }</p>
					        <p>${item.knowledgeName}</p>
					    </li>
					    <li>
					        <p class="f-grey">正确率</p>
					        <p>${item.accuracy }%</p>
					    </li>
					    <li>
					      	<c:if test="${item.accuracy < 50}"><span class="master master3"></span></c:if>
							<c:if test="${item.accuracy >= 50 && item.accuracy < 80}"><span class="master master2"></span></c:if>
							<c:if test="${item.accuracy >= 80}"><span class="master master1"></span></c:if>
					    </li>
					    <li data-kid="${item.knowledgeId }" >
					        <input type="button" class="btn btn-sm btn-bg-turquoise j-knowledge-detail" value="详情">
					        <input type="button" class="btn btn-nm btn-bg-turquoise j-knowledge-exercise" value="强化练习">
					    </li>
					</ul>
				</c:forEach>
			</c:if>
            <c:if test="${relationReport != null && relationReport.size() > 0}">
	            <p class="guess">根据练习情况，系统推测出相关知识点掌握情况如下：</p>
	            <c:forEach  items="${relationReport }" var="item" varStatus="status" >
		            <ul class="aslip">
		                <li>
		                    <p class="f-grey">相关知识点${status.index+1 }</p>
		                    <p>${item.knowledgeName}</p>
		                </li>
		                <li>
			                <c:if test="${item.accuracy < 50}"><span class="master master3"></span></c:if>
							<c:if test="${item.accuracy >= 50 && item.accuracy < 85}"><span class="master master2"></span></c:if>
							<c:if test="${item.accuracy >= 85}"><span class="master master1"></span></c:if>
		                </li>
		                <li data-kid="${item.knowledgeId }" >
		                    <input type="button" class="btn btn-sm btn-bg-turquoise j-knowledge-detail" value="详情">
		                    <input type="button" class="btn btn-nm btn-bg-turquoise j-knowledge-exercise" value="强化练习">
		                </li>
		            </ul>
	            </c:forEach>
            </c:if>
            <p class="w-tips">
                <i></i>
               	 备注：报告仅针对本次练习
            </p>
        </section>
    </article>
    <input type="hidden" id="j_exerciseId" value="${exercise.exerciseId }" /> 
	<script src="${assets}/scripts/common/mobile/common.js?_t=${_t}"></script>
	<script src="/scripts/hd/exercise/report.js?_t=${_t}"></script>
</body>
</html>