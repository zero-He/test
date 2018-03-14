<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>题库统计 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/question/global.css?t=${_t}">
<link rel="stylesheet" type="text/css" href="${assets}/styles/beike/beike.css?t=${_t}">

</head>
<body>
<form>
<%@ include file="/pages/header/header.jsp" %>
<div class="g-bd">
	<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<div class="m-tab">
			<ul id="cutsub">
				<li class="active" id="aSchoolStageList"><a href="##">按教材</a></li>
				<li id="aKnowledegeList"><a href="##">按知识点</a></li>
				<li id="aOfficialTag"><a href="##">按标签</a></li>
			</ul>
		</div>
			<div class="quesCutContent">
				<!-- 按教材统计 begin -->
				<div id="cutsubCon0" class="quesTotalCon" style="display:block;">
					<div class="m-search-box">
						
						<label class="title">学段：</label>
						<select id="stage" class="u-select u-select-nm"></select>
						<input type="hidden" id="schoolStageId" name="schoolStageId" value=""/>
						<select id="jPressId" name="pressId" class="u-select u-select-nm">
							
						</select>
						<select id="jType" name="checked" class="u-select u-select-nm">
							<option value="1" selected="selected">已审核</option>
							<option value="2">未审核</option>
						</select>
						<input id="jSearch" class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="查询">
					</div>
					<div>
						<span id="jTotalAmount"></span>
					</div>
					<div class="z-lesson-plan z-lesson-plan-teach" id="materialContext">
					
					</div>
				</div>
				<!-- 按教材统计 end -->
				<!-- 按知识点统计 begin -->
				<div id="cutsubCon1" class="quesTotalCon">
					<div class="z-lesson-plan z-lesson-plan-teach" id="knowledgeAmountContext">
						
					</div>
				</div>
				<!-- 按知识点统计 end -->
				<!-- 按标签统计 begin -->
				<div id="cutsubCon2" class="quesTotalCon">
					<div class="m-table">
						<table>
							<thead>
								<tr>
									<th width="80%">题库标签</th>
									<th width="20%">习题量</th>
								</tr>
							</thead>
							<tbody id="officialTagContext">
								
							</tbody>
							
						</table>
					</div>
				</div>
				<!-- 按标签统计 end -->
			</div>
	</div>
</div>

<!-- 加载出版社 -->
<textarea id="pressId_tpl" style="display:none;">
	<option value="0">==所有出版社==</option>
	{{#presses}}
	<option value="{{pressId}}">{{pressName}}</option>
	{{/presses}}
</textarea>

<!-- 按教材统计 begin -->
<textarea id="materialContext_tpl" style="display:none;">
	{{#inputStatisList}}
		<div class="item {{sscolour}}">
			<div class="knowledge-bg-color">
				<span class="title">
					{{subjectName}}<i class="circle"></i>
				</span>
				<div class="table">
				<table class="tab">
					<thead>
						<tr>
							{{#materialQuestionAmounts}}
							<th id={{materialId}}>{{materialName}}</th>
							{{/materialQuestionAmounts}}
							<th width="10%">总计</th>
						</tr>
					</thead>
					<tbody>
						<tr> 
							{{#materialQuestionAmounts}}
							<td class="stat-data" subjectId="{{subjectId}}" materialId="{{materialId}}">
								
							</td>
							{{/materialQuestionAmounts}}
							<td class="mqa-data" subjectId="{{subjectId}}" materialId="0">
							
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
		</div>
	{{/inputStatisList}}
</textarea>

<textarea id="subjectContext_tpl" style="display:none;">
	<th >年级</th>
	{{#subjectList}}
	<th id={{subjectId}}>{{subjectName}}</th>
	{{/subjectList}}
	<th>总计</th>
</textarea>
<!-- 按教材统计 end -->

<!-- 按知识点统计 begin -->
<textarea id="knowledgeAmountContext_tpl" style="display:none;">
	{{#schoolStageList}}
		<div class="item {{sscolour}}">
			<div class="knowledge-bg-color">
				<span class="title">
					{{schoolStageName}}<i class="circle"></i>
				</span>
				<div class="table">
				<table class="tab">
					<thead>
						<tr>
							{{#subjects}}
							<th id={{subjectId}}>{{subjectName}}</th>
							{{/subjects}}
						</tr>
					</thead>
					<tbody>
						<tr> 
							{{#subjects}}
							<td class="stat-data" subjectId="{{subjectId}}" schoolStageId="{{schoolStageId}}">
								
							</td>
							{{/subjects}}
						</tr> 
					</tbody>
				</table>
				</div>
			</div>
		</div>
	{{/schoolStageList}}
	
</textarea>
<!-- 按知识点统计 end -->

<!-- 按标签统计 begin -->
<textarea id="officialTagContext_tpl" style="display:none;">
	{{#inputStatisList}}
	<tr> 
		 <td>{{officialTagName}}</td> 
		 <td class="operation"><a href="${ctx}/auth/admin/questionStatis/gradeOfOfficialTagStatis.htm?officialTagId={{officialTagId}}">{{amount}}</a></td> 
		 
	</tr> 
	{{/inputStatisList}}
</textarea>
<!-- 按标签统计 end -->
</form>


<leke:pref />
<script type="text/javascript">
	seajs.use('question/questionStatis/totalStatis');
</script>
</body>
</html>