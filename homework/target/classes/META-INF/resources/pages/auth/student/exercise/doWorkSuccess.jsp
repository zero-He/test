<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>练习提交成功 - 乐课</title>
<%@ include file="/pages/common/meta.jsp"%>
</head>
<body class="m-middlepage">
<div class="g-head">
		<%@ include file="/pages/header/header.jsp"%>
	</div>
	<div class="con">
		<div class="msg">
			<div class="m-tiptext m-tiptext-block m-tiptext-success">
				<i class="iconfont icon">󰅂 </i>
				<div class="msg">
					<h6>练习提交成功！</h6>
					<p> <c:if test="${isExercise }">您是今天第${todayNum}位自主练习的学生,</c:if>此次练习正确率为 ${ accuracy }%。</p>
		           		 <p class="special">
				            	<c:if test="${lebi!=null}">乐豆+${lebi}</c:if><c:if test="${exp != null && exp gt 0}"> 经验+${exp}</c:if>
	            		 </p>
	            		 <c:if test="${isExercise }">
		            		 <p>
								<a href="${initParam.homeworkServerName }/auth/student/exercise/report.htm?id=${exerciseId}">查看练习报告</a>
								| <a href="${initParam.homeworkServerName }/auth/student/exercise/list.htm">返回自主练习</a>
						    </p>
	            		 </c:if>
				</div>
			</div>
		</div>
	</div>
    <div class="g-foot">
	<%@ include file="/pages/common/foot.jsp"%>
	</div>

</body>
</html>