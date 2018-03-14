<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>查看应用</title>
    <%@ include file="/pages/common/meta.jsp"%>
    <link rel="stylesheet" type="text/css" href="${assets}/styles/common/global.css">
    <link rel="stylesheet" href="${assets}/styles/application/application.css">
	<!-- <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=07d2dfee1f14ebc75d76681336c676b6">
	</script> -->
</head>
<body>
<%@ include file="/pages/header/header.jsp" %>
    <div class="g-bd">
        <div class="g-mn">
		    <div class="c-application">
		    	 <div class="whitelist">
                    <div class="header">
                        <span class="title">查看应用</span>
                    </div>
                    <form class="m-form" action="" method="post">
			   			<ul>
			   				<li class="form-item">
			   					<label class="title">姓名：</label>
			   					<div class="con">
			   						${p.userName }
			   					</div>
			   				</li>
			   				<li class="form-item">
			   					<label class="title">班级：</label>
			   					<div class="con">
			   						${p.className }
			   					</div>
			   				</li>
			   				<li class="form-item">
			   					<label class="title">设备IMEI：</label>
			   					<div class="con">
			   						${p.imei }
			   					</div>
			   				</li>
			   				<%-- <li class="form-item">
			   					<label class="title">大致位置：</label>
			   					<div class="con">
			   						经度 ${p.longitude }度   纬度 ${p.latitude } 度   最后获取时间 <fmt:formatDate value="${p.ts}" type="Date" pattern="yyyy-MM-dd"/>
			   						<div id="container" style="width: 500px;height: 300px"></div>
			   					</div>
			   				</li> --%>
			   				<li class="form-item">
			   					<label class="title">应用列表：</label>
			   					<div class="con">
			   					</div>
			   				</li>
			   			</ul>
			   			<div class="m-table m-table-center">
		                    <table>
		                        <tr>
		                            <th>包名</th>
		                            <th>应用名</th>
		                        </tr>
		                        <tbody>
		                        	<c:forEach var="item" items="${p.apps }">
			                        	<tr>
			                        		<td>${item.pkgName }</td>
			                        		<td>${item.appName }</td>
			                        	</tr>
		                        	</c:forEach>
								</tbody>
		                    </table>
		                </div>
					</form>
                 </div>
		    </div>
        </div>
    </div>
	    <!-- <script type="text/javascript">
		    var map = new BMap.Map("container");    
		    var point = new BMap.Point(${p.longitude },${p.latitude });    
		    map.centerAndZoom(point, 15);    
	    </script> -->
</body>
</html>
