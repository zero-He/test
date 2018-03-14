<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>资源统计</title>
<%@ include file="/pages/common/meta.jsp"%>
<meta charset="utf-8">
<link rel="stylesheet" href="${assets}/styles/crm/ruzhu.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="https://static.leke.cn/styles/common/global.css">
<link rel="stylesheet"
	href="https://static.leke.cn/styles/lesson-monitor/lesson-monitor-pc.css">
<link rel="stylesheet"
	href="../../../../scripts/monitor/component/weekpicker.css">
</head>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="m-search-box m-search-box_nomar">
			<div class="item">
				<label for="" class="title"> 资源类型： </label> 
				<select id="resType" class="u-select u-select-nm">
					<option value="1">习题</option>
					<option value="2">试卷</option>
					<option value="3">课件</option>
					<option value="4">微课</option>
					<option value="5">习题册</option>
					<option value="6">备课包</option>
				</select>
			</div>
		</div>
		<div class="m-search-box">
			<div class="item">
				<label>统计时间：</label> 
				<input type="text" id="startDay" name="startDay" class="Wdate u-ipt u-ipt-nm" />
				<label >至</label>
				<input type="text" id="endDay" name="endDay" class="Wdate u-ipt u-ipt-nm" />

			    <label for="" class="title"> 资源来源： </label> 
				<select name="" id="resourceSource" class="u-select u-select-nm">
					<option value="0">全部</option>
					<option value="1">原创资源</option>
					<option value="2">外部资源</option>
					<option value="3">老师分享</option>
				</select>
				<div class="operation">
					<input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button"
						value="查询" data-bind="click:loadPage"> 
						
						<input class="u-btn u-btn-nm u-btn-bg-orange f-ml10 f-fr" type="button"
						value="Top50" data-bind="click:openTopDetail"> 
				</div>
			</div>
			<div class="item">
				<label for="" class="title"> 学段： </label> 
				<select  id="schoolStage" class="u-select u-select-sm">
 					<c:if test="${roleId!=402 }">
					   <option value="">全部</option>
					   <c:forEach var="item" items="${schoolStage }">
					   	<option value="${item.schoolStageId }">${item.schoolStageName }</option>
					   </c:forEach>
					</c:if> 
					<c:if test="${roleId==402 }">
					   <option value="">全部</option>
					   <c:forEach var="item" items="${schoolStage }" >
 							<c:choose>
								<c:when test="${item.schoolStageId ==stage.schoolStageId}">
							   		<option value="${item.schoolStageId }" selected="selected">${item.schoolStageName }</option>
								</c:when>
								<c:otherwise>
									<option value="${item.schoolStageId }" >${item.schoolStageName }</option>
								</c:otherwise>
							</c:choose> 
					   </c:forEach>
					</c:if>
			    </select> 
			    <label for="" class="title"> 学科： </label> 
			    <select  id="subject" class="u-select u-select-sm">
			    	<c:if test="${roleId!=402 }">
						<option value="">全部</option>
						<c:forEach var="item" items="${subjects }">
					   		<option value="${item.subjectId }">${item.subjectName }</option>
					   	</c:forEach>
			    	</c:if>
			    	<c:if test="${roleId==402 }" >
						<option value="">全部</option>
						<c:forEach var="item" items="${subjects }">
							<c:choose>
								<c:when test="${item.subjectId==subject.subjectId }">
							   		<option value="${item.subjectId }" selected="selected">${item.subjectName }</option>
								</c:when>
								<c:otherwise>
									<option value="${item.subjectId }">${item.subjectName }</option>
								</c:otherwise>
							</c:choose>
					   	</c:forEach>
			    	</c:if>
			    </select>
			</div>
		</div>
		<div class="c-monitor-con">
			<div class="copiesOf4">
				<ul>
					<li>
						<div class="tableCell">
							<h5 id="resouceNum"></h5>
							<p>累计创建资源数（个）</p>
						</div>
					</li>
					<li>
						<div class="tableCell">
							<h5 id="newResourceNum"></h5>
							<p>新创建资源数（个）</p>
						</div>
					</li>
					<li>
						<div class="tableCell">
							<h5 id="usedResourceNum"></h5>
							<p>使用资源数（个）</p>
						</div>
					</li>
					<li>
						<div class="tableCell">
							<h5 id="usedRescourceCount"></h5>
							<p>使用资源量（次数和）</p>
						</div> <i class="i-question" title="1.累计创建资源数：截止到统计时间区间最后一天平台累计创建的资源量。&#13;
2.新创建资源数：在统计时间内创建的资源量。&#13;
3.使用资源数：在统计时间内使用资源个数（单个资源为单位）。&#13;
4.使用资源量：在统计时间内用户利用过的资源（下载，引用，编辑），数据不去重（一个资源多次利用，数据累加）。&#13;"></i>
					</li>
				</ul>
			</div>
			<div class="chartWrap">
				<h3 class="c-monitor-tit">资源使用趋势</h3>
				<div class="chartComt" id="barSta"></div>
			</div>
		</div>
		<div class="m-search-box f-pt15" style="overflow: visible;">
			<div class="item">
				<label for="" class="title">学校名称：</label> 
				<div class="m-autocomplete" style="width: 180px;">
					<input type="text" name="school" class="u-ipt u-ipt-nm j-school-select" id="schoolId">
				</div>
				<div class="operation">
					<input class="u-btn u-btn-nm u-btn-bg-turquoise f-ml10" type="button" 
					value="查询" data-bind="click:loaddata">
					<input class="u-btn u-btn-nm u-btn-bg-orange f-ml10 f-fr" type="button" 
					value="导出" data-bind="click:exportExcel">
				</div>
			</div>
		</div>
		<div class="c-monitor-con">
			<div class="m-table m-table-center">
				<h3 class="c-monitor-tit">
					学校使用明细 <i class="i-question" title="总使用量：下载量、引用量、编辑量的总和"></i>
				</h3>
                <table>
                    <tr>
                       <th width="16.6%">学校</th>
                       <th width="16.6%">使用总量</th>
                       <th width="16.6%" >下载量</th>
                       <th width="16.6%">引用量</th>
                       <th width="16.6%">编辑量</th>
                       <th width="16.6%">操作区</th>
                    </tr>
					<tbody id="papersPage" data-bind="foreach:listdata">
						<tr>
							<td><em data-bind="text:schoolName"></em></td>
							<td><em data-bind="text:usedNum"></em></td>
							<td data-bind = "visible: $root.ResourceSelected() ==2|| $root.ResourceSelected() ==3"><em data-bind="text:downloadNum"></em></td>
							<td data-bind = "visible: $root.ResourceSelected() !=2&& $root.ResourceSelected() !=3"><em >-</em></td>
							<td><em data-bind="text:quoteNum"></em></td>
							<td data-bind = "visible:  $root.ResourceSelected() !=4&& $root.ResourceSelected() !=5"><em data-bind="text:compileNum"></em></td>
							<td data-bind = "visible:  $root.ResourceSelected() ==4|| $root.ResourceSelected() ==5"><em >-</em></td>
							<td class="operation">
								<a class="f-csp s-c-turquoise" data-bind="click:$parent.opendetail.bind($data)">使用趋势</a>
							</td>
						</tr>
					</tbody>
              </table>
				<!-- ko if: listdata().length == 0 -->
				<div class="eval-rate-pictab m-tips f-block">
					<i></i> <span>对不起，没有您要查询的数据</span>
				</div>
				<!-- /ko -->
			</div>
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
<script src="${ctx}/scripts/common/ui/ui-echarts/echarts-plain.js"></script>

<script>
	seajs.use('monitor/pages/common/resource/resourcedetail');
</script>
</body>
</html>
