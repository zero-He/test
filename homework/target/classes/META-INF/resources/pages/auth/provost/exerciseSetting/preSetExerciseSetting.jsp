<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>自主测试设置 - 乐课网</title>
<%@ include file="/pages/common/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${assets}/styles/homework/exerciseSetting/exerciseSetting.css?t=20171115">
</head>
<body>
<%@ include file="/pages/header/header.jsp"%>
<div class="g-bd">
<%@ include file="/pages/navigate/navigate.jsp"%>
	<div class="g-mn">
		<form action="" id="dataForm">
			<h1 >同步作业题量设置：</h1>
			<br>
			<span>注：在默认题量一列中输入数字即表明所有学科都以默认题量为准，若某一学科题量输入了数字，则该学科以当前列设置题量为准，其余未设置题量的学科以默认题量为准。</span>
			<br><br>
			<div class="quesTotal">
				<ul id="cutsub">
					<c:forEach items="${gradeList}" var="g" varStatus="gStatus">
						<c:if test="${gStatus.count == 1}">
							<li class="quesTotalon"><a href="javascript:void(0);">${g.gradeName}</a></li>
						</c:if>
						<c:if test="${gStatus.count > 1}">
							<li><a href="javascript:void(0);">${g.gradeName}</a></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<div class="quesCutContent">
				<c:forEach items="${gradeList}" var="g" varStatus="gStatus">
					<c:if test="${gStatus.count == 1}">
						<div id="cutsubCon${gStatus.count-1}" class="quesTotalCon" style="display: block;">
							<div class="m-table">
								<table id="schoolStageStat">
									<thead>
										<tr id="subjectContext">
											<th>设置各题型题量</th>
											<th>默认题量</th>
											<c:forEach items="${subjectList}" var="s">
												<th id="${s.subjectId}">${s.subjectName}</th>
											</c:forEach>
										</tr>
									</thead>
									<tbody class="synchronize">
										<input type="hidden" class="iGradeId" value="${g.gradeId}">
										<c:forEach items="${kgqtList}" var="qt">
											<tr>
												<td>${qt.questionTypeName}</td>
												<td>
													<input type="text" class="stat-data" 
														questionTypeId="${qt.questionTypeId}" subjectId="0" 
														value="5" style="width: 40px; height: 16px" />
												</td>
												<c:forEach items="${subjectList}" var="s">
													<td><input type="text" class="stat-data" questionTypeId="${qt.questionTypeId}" subjectId="${s.subjectId}" style="width: 40px; height: 16px"></td>
												</c:forEach>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
					<c:if test="${gStatus.count > 1}">
						<div id="cutsubCon${gStatus.count-1}" class="quesTotalCon">
							<div class="m-table">
								<div class="m-table">
									<table id="schoolStageStat">
										<thead>
											<tr id="subjectContext">
												<th>设置各题型题量</th>
												<th>默认题量</th>
												<c:forEach items="${subjectList}" var="s">
													<th id="${s.subjectId}">${s.subjectName}</th>
												</c:forEach>

											</tr>
										</thead>
										<tbody class="synchronize">
											<input type="hidden" class="iGradeId" value="${g.gradeId}">
											<c:forEach items="${kgqtList}" var="qt">
												<tr>
													<td>${qt.questionTypeName}</td>
													<td><input type="text" class="stat-data" questionTypeId="${qt.questionTypeId}" subjectId="0" dataId=""
															value="5" style="width: 40px; height: 16px" /></td>
													<c:forEach items="${subjectList}" var="s">
														<td><input type="text" class="stat-data" questionTypeId="${qt.questionTypeId}" subjectId="${s.subjectId}" dataId=""
																style="width: 40px; height: 16px"></td>
													</c:forEach>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>

			</div>

			<br>
			<!-- ------------------知识点检测开始----------------	 -->


			<h1 >知识点检测题量设置：</h1>
			<br>
			<div class="quesTotal">
				<ul id="cutsub1">
					<c:forEach items="${gradeList}" var="g" varStatus="gStatus">
						<c:if test="${gStatus.count == 1}">
							<li class="quesTotalon"><a href="javascript:void(0);">${g.gradeName}</a></li>
						</c:if>
						<c:if test="${gStatus.count > 1}">
							<li><a href="javascript:void(0);">${g.gradeName}</a></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<div class="quesCutContent">
				<c:forEach items="${gradeList}" var="g" varStatus="gStatus">
					<c:if test="${gStatus.count == 1}">
						<div id="cutsub1Con${gStatus.count-1}" class="quesTotalCon" style="display: block;">
							<div class="m-table">
								<table id="schoolStageStat">
									<thead>
										<tr id="subjectContext">
											<th>设置各题型题量</th>
											<th>默认题量</th>
											<c:forEach items="${subjectList}" var="s">
												<th id="${s.subjectId}">${s.subjectName}</th>
											</c:forEach>
										</tr>
									</thead>
									<tbody class="knowledge">
										<input type="hidden" class="iGradeId" value="${g.gradeId}">
										<c:forEach items="${kgqtList}" var="qt">
											<tr>
												<td>${qt.questionTypeName}</td>
												<td><input type="text" class="stat-data" questionTypeId="${qt.questionTypeId}" subjectId="0" value="5" dataId=""
														style="width: 40px; height: 16px" /></td>
												<c:forEach items="${subjectList}" var="s">
													<td><input type="text" class="stat-data" questionTypeId="${qt.questionTypeId}" subjectId="${s.subjectId}" dataId="" 
															style="width: 40px; height: 16px"></td>
												</c:forEach>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
					<c:if test="${gStatus.count > 1}">
						<div id="cutsub1Con${gStatus.count-1}" class="quesTotalCon">
							<div class="m-table">
								<div class="m-table">
									<table id="schoolStageStat">
										<thead>
											<tr id="subjectContext">
												<th>设置各题型题量</th>
												<th>默认题量</th>
												<c:forEach items="${subjectList}" var="s">
													<th id="${s.subjectId}">${s.subjectName}</th>
												</c:forEach>

											</tr>
										</thead>
										<tbody class="knowledge">
											<input type="hidden" class="iGradeId" value="${g.gradeId}">
											<c:forEach items="${kgqtList}" var="qt">
												<tr>
													<td>${qt.questionTypeName}</td>
													<td><input type="text" class="stat-data" questionTypeId="${qt.questionTypeId}" subjectId="0" value="5"
														style="width: 40px; height: 16px" /></td>
													<c:forEach items="${subjectList}" var="s">
														<td><input type="text" class="stat-data" questionTypeId="${qt.questionTypeId}" subjectId="${s.subjectId}"
															style="width: 40px; height: 16px"></td>
													</c:forEach>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>

			</div>
			<div class="btnSave">
				<input class="u-btn u-btn-nm u-btn-bg-turquoise" type="button" value="保存" id="bSave">
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
	seajs.use('homework/exerciseSetting/exerciseSetting');
</script>

</body>
</html>