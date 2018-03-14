<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pages/common/meta.jsp"%>
<link rel='stylesheet' href='${ctx}/scripts/common/ui/ui-tree/skin/default/ui-tree.css?t=20171115' type='text/css'>
<link rel="stylesheet" type="text/css" href="${assets }/styles/homework/homework.css">
</head>
<body class="c-selftrain">
	<div class="m-form c-form f-mt10" >
	<div class="con">
		 <span class=" pattern pchosen"><a >章节模式</a></span>
		 <span class=" pattern"><a href="${initParam.homeworkService }/auth/student/exercise/page/knowledgeTree.htm">知识点模式</a></span>
	</div>
	<form>
		<ul>
			<!-- <li class="form-item">
                  <label for="" class="title">
                      <span class="require">*</span>习题难度：
                  </label>
                  <div class="con">
                  	  <input type="hidden" id="j_diffLevel" value="3"> 
                      <ul class="difficulty">
                          <li class="full"><i></i></li>
                          <li class="full"><i></i></li>
                          <li class="full"><i></i></li>
                          <li ><i></i></li>
                          <li ><i></i></li>
                      </ul>
                      <span class="diffclass"></span>
                  </div>
             </li> -->
			<li class="form-item">
				<label class="title"><span class="require">*</span><locale:message code="homework.homework.schoolstagesubject" />：</label>
				<div class="con">
					<select id="jStageSubject" class="u-select u-select-nm"></select>
				</div>
			</li>
			<li class="form-item">
				<label class="title"><span class="require">*</span><locale:message code="homework.homework.materialnode" />：</label>
				<div class="con">
					<input type="text" id="jMaterialTree" style="width: 350px;" class="u-ipt u-ipt-nm f-vam" readonly="readonly" />
					<a id="jMaterialDel" class="s-c-r f-csp f-fs22 f-vam"> x </a>
				</div>
			</li>

		</ul>
		<div class="submit">
			<input class="u-btn u-btn-nm u-btn-bg-turquoise u-btn-auto" type="button" id="jBtnStartTest" value="<locale:message code="homework.homework.form.search.starttest" />">
		</div>
	</form>
	</div>
	<leke:pref />
	<script>
		seajs.use('homework/exercise/chapterTree');
	</script>

</body>
</html>
