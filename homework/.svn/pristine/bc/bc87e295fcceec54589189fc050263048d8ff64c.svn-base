<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
 <link rel="stylesheet" type="text/css" href="${assets}/styles/homework/homework.css?t=${_t}">
<title>回收站 - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>

</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form action="" method="post" id="formPage" autocomplete="off">
	        <!-- 作业批改 -->
	        <div class="m-search-box">
	        	<!-- 班级 -->
	            <c:if test="${ classId eq null}">
	            <label for="" class="title">班级：</label>
	            <input type="text" name="className"  class="u-ipt u-ipt-nm" />
	            </c:if>
	            <c:if test="${classId != null }">
	             <label for="" class="title">班级：${className}</label>
	             <input type="hidden" name="classId" value="${classId }" />
	            </c:if>
	            <label for="" class="title"><locale:message code="homework.homework.homeworktype" />：</label>
	            <select id="jHomeworkType" name="homeworkType" class="u-select u-select-nm"></select>
				<label for="" class="title">资源类型：</label>
				<select id="jResType" name="resType" class="u-select u-select-nm">
	            	<option value="0">所有类型</option>
	            	<option value="1">课件</option>
	            	<option value="2">微课</option>
	            	<option value="3">试卷</option>
	            </select>
	            <label for="" class="title"><locale:message code="homework.homework.homeworktitle" />：</label>
	            <input type="text" name="homeworkName" class="u-ipt u-ipt-nm">
	
	            <button class="u-btn u-btn-nm u-btn-bg-turquoise" id="bHomeworkList"><locale:message code="homework.common.search" /></button>
	        </div>
	
	        <div class="c-recyclebins">
                <div class="z-homework-grade z-homework-grade-teach">
                  <ul>
                      <li class="item choseall">
                          <span class="left">
                              <input id="j-chk-all" type="checkbox" class="selected">
                              <label for="j-chk-all">全选</label>
                          </span>
                          <div class="right">
                              <input type="button" value="恢复" id="j-btn-recover" class="u-btn u-btn-nm u-btn-bg-turquoise">
                              <input type="button" value="永久删除" id="j-btn-del" class="u-btn u-btn-nm u-btn-bg-orange">
                          </div>
                      </li>
                    </ul>
                    <ul id="j-body">
                    
                    </ul>
                    <div class="page" id="divPage">
                    <div class="m-tips" id="j-no-data"><i></i><span>对不起，没有您要查询的数据</span></div>
                </div>
            </div>
		</form>
	</div>
</div>

<script id="j-tmpl" type="text/handlebars">
{{#dataList}} 
<li class="item">
	<div class="title">
		<input type="checkbox" data-hwid="{{homeworkId}}">
		<a class="name">
{{#cif 'this.paperId == null'}}
[{{homeworkTypeStr}}]
{{/cif}}
{{#cif 'this.paperId != null'}}
[{{resTypeStr}}]
{{/cif}}
{{homeworkName}}</a>
		<span class="class-name">{{className}}</span>
	</div>
	<div class="tips">
		{{#cif 'this.resType == 3'}}
		<span>已上交：<b class="cur">{{finishNum}}</b>/{{totalNum}}</span>
		<span>已批改：<b class="cur">{{correctNum}}</b>/{{finishNum}}</span>
		<span>平均分：{{avgScore}}</span>
		{{/cif}}
		{{#cif 'this.resType != 3'}}
		<span>已查看：<b class="cur">{{finishNum}}</b>/{{totalNum}}</span>
		{{/cif}}
		<p class="time">开始/截止时间：{{fmtTime startTime}} / {{fmtTime closeTime}}</p>
	</div>
</li>
{{/dataList}}
</script>

<leke:pref />
<script type="text/javascript">
	seajs.use('homework/homework/dumpedHomeworkList');
</script>

</body>
</html>