<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/cordova.jsp"%>
<link rel="stylesheet" href="${assets }/styles/mobile/global.css">
<link rel='stylesheet'
	href='${ctx}/scripts/common/ui/ui-tree/skin/default/ui-tree.css?t=${_t}'
	type='text/css'>
<link rel="stylesheet" type="text/css"
	href="${assets }/styles/homework/homework.css">
<leke:context />
</head>
<body class="c-selftrain c-starttrain ">
	<div class="m-form c-form f-mt10">
		<div class="con">
			<span class=" pattern pchosen"><a>章节模式</a></span> <span
				class=" pattern"><a
				href="knowledgeTree.htm">知识点模式</a></span>
		</div>
		<table border="1" bordercolor="#0ba299"
			style="margin-top: 5px; width: 100%; height: 80%;">
			<tr>
				<td valign="top" style="width: 31%;">&nbsp;
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
						<li class="form-item"><label class="title"><span
								class="require">*</span>
							<locale:message code="homework.homework.schoolstagesubject" />：</label>
							<div class="con">
								<select id="jStageSubject" class="u-select u-select-nm"></select>
							</div></li>
					</ul>
					<div class="submit">
						<input class="u-btn u-btn-nm u-btn-bg-turquoise u-btn-auto"
							type="button" id="jBtnStartTest"
							value="<locale:message code="homework.homework.form.search.starttest" />">
					</div>
				</td>
				<td valign="top">
					<ul>
						<li class="form-item"><label class="title"><span
								class="require">*</span>
							<locale:message code="homework.homework.materialnode" />：</label>
							<div class="con">
								<input type="text" id="jMaterialTree" style="width: 350px;"
									class="u-ipt u-ipt-nm f-vam" readonly="readonly" /> <a
									id="jMaterialDel" class="s-c-r f-csp f-fs22 f-vam"> x </a>
							</div></li>
					</ul>
				</td>
			</tr>
		</table>

	</div>
	<leke:pref />
	<script>
		seajs.use('hd/exercise/chapterTree.js?_t=${_t}');
	</script>

</body>
</html>
