<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>资源Top50统计</title>
<%@ include file="/pages/common/meta.jsp"%>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css" type="text/css">
<link rel="stylesheet"
      type="text/css"
      href="https://static.leke.cn/styles/common/global.css">
<link rel="stylesheet"
      href="https://static.leke.cn/styles/lesson-monitor/lesson-monitor-pc.css">
<link rel="stylesheet" href="../../../../scripts/monitor/component/weekpicker.css">
</head>
<%@ include file="/pages/header/header.jsp"%>
<body>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
                    <div class="m-search-box">
                        <div class="item" >
                               <label>统计时间：</label> 
								<input type="text" id="startDay" name="startDay" class="Wdate u-ipt u-ipt-nm" />
								<label >至</label>
								<input type="text" id="endDay" name="endDay" class="Wdate u-ipt u-ipt-nm" />
                               <label for="" class="title">资源类型：</label>
                            	<select class="u-select u-select-nm" name="resType" id="resType"  >
                            		 <!-- 1-习题 2-试卷 3-课件 4-微课 5-习题册 6-备课包 -->
                            		 <%String m=String.valueOf(request.getParameter("resType")); %>
			                        <option value="1" <%="1".equals(m)?"selected":"" %>>习题</option>
			                        <option value="2" <%="2".equals(m)?"selected":"" %>>试卷</option>
			                        <option value="3" <%="3".equals(m)?"selected":"" %>>课件</option>
			                        <option value="4" <%="4".equals(m)?"selected":"" %>>微课</option>
			                        <option value="5" <%="5".equals(m)?"selected":"" %>>习题册</option>
			                        <option value="6" <%="6".equals(m)?"selected":"" %>>备课包</option> 
                     			</select>
								<div class="operation">
			                        <input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button"
										value="查询" data-bind="click:loaddata">
									<input class="u-btn u-btn-nm u-btn-bg-orange f-ml10 f-fr" type="button"
										value="导出" data-bind="click:exportExcel">
								</div>
								<br></br>
								<div>
								<label class="title">学段:</label>
		                            <select  id="schoolStage" class="u-select u-select-sm">
									   <option value="">全部</option>
									   <c:forEach var="item" items="${schoolStage }">
									   	<option value="${item.schoolStageId }">${item.schoolStageName }</option>
									   </c:forEach>
								    </select> 
								<label class="title">学科：</label>
		                            <select  id="subject" class="u-select u-select-sm">
										<option value="">全部</option>
										<c:forEach var="item" items="${subjects }">
									   		<option value="${item.subjectId }">${item.subjectName }</option>
									   	</c:forEach>
								    </select>
								</div>
								
                        </div>
                   	 </div>
                    <div class="tabs-bg">
                    	<div class="tabs-monitor-tip">
                          	资源使用Top50统计
                        </div>
                        <div class="m-table m-table-center">
                            <table>
                                     <tr>
                                        <th>序号</th>
                                        <th>资源ID</th>
	                                    <th id="resName1" data-bind = "visible: $root.ResourceSelected() !=1">资源名称</th>
                                        <th>使用量</th>
                                        <th>上传人</th> 
                                        <th>学段</th>
                                        <th>学科</th>
                                    </tr>
								<tbody id="papersPage" data-bind="foreach:listdata">
									<tr>
										<td><em data-bind="text:rank"></em></td>
										<td><a data-bind="attr:{href:href}" class="f-csp s-c-turquoise" target="_blank"><em data-bind="text:resId"></em></a></td>
										<td data-bind = "visible: $root.ResourceSelected() !=1" class="c-monitor-con_td"><p class="c-monitor-con_p"><em data-bind="text:resourceName"></em></p></td>
										<td><em data-bind="text:usedNum"></em></td>
										<td><em data-bind="text:uploadName"></em></td>
										<td><em data-bind="text:stageName"></em></td>
										<td><em data-bind="text:subjectName"></em></td>
									</tr>
								</tbody>
                            </table>
                           <!-- ko if: listdata().length == 0 -->
							 <div class="eval-rate-pictab m-tips f-block">
								<i></i> <span>对不起，没有您要查询的数据</span>
							 </div>  
							<!-- /ko -->
                        </div>
                        <!-- 用JS控件完成 这里只做样式 -->
                        						<div
							data-bind="component: {
										name: 'leke-page',
										params: {
											curPage: curPage,
											totalSize: totalSize
					}}"></div>
                    </div>
            </div>
</div>
<script>
	seajs.use('monitor/pages/common/resource/resourceTopFifty');
</script>
    </body>
<script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.js" charset="utf-8"></script>
</html>
	