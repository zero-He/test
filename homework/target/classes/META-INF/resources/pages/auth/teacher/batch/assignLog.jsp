<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>代人布置作业 - <locale:message code="common.page.header.leke" /></title>
<%@ include file="/pages/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/scripts/common/ui/ui-tree/skin/default/ui-tree.css?t=20171115" type="text/css">
<link rel="stylesheet" href="${assets}/styles/homework/homework.css?t=20171115" type="text/css">
<link rel="stylesheet" type="text/css" href="${assets}/styles/beike/beike.css?t=20171115" />
<link rel="stylesheet" href="${assets }/styles/homework/assign.css?t=20171115">

</head>
<body>
	<%@ include file="/pages/header/header.jsp"%>
	<div class="g-bd">
		<div class="g-mn">
			<!-- 布置作业 -->
			<div class="m-arrangehomework">
				<div class="m-table">
					<table>
						<tr>
							<th colspan="4">
							          布置作业
							</th>
						</tr>
						<tr>
							<td align="center"></td>
							<td colspan="3" >
								<ul class="select">
									<li class="select-work">
										<label>选择作业：
											<button class="u-btn u-btn-sm u-btn-bg-orange" 
												data-bind="click: function($data){$root.openPaper($data);}">请选择
											</button>
										</label>
										<div class="con">
										<!-- ko foreach: papers -->
											<span class="item"><b data-bind="text: resName"></b>
											<i data-bind="click: function($data){
												$root.rmPaper($data);
											}">&times;</i></span>
										<!-- /ko -->
										</div>
									</li>
								</ul>
							</td>
						</tr>
						<!-- ko foreach: list -->
						<tr>
							<td align="center"  data-bind="text: $index() + 1"></td>
							<td >
								<ul class="select">
									<li class="select-student">
										<label>选择学生：
											<button class="u-btn u-btn-sm u-btn-bg-orange" data-bind="click: function(){
												$root.openStudentGroup($index,$data);
											}">请选择</button>
										</label>
										<div class="con">
										<!-- ko foreach: studentGroups -->
											<span class="item"><b data-bind="text: str,attr:{title: str + (courseSetId ? '(' + gradeName + ')' : '')}"></b><i data-bind="click: function($data){
												$root.rmStudentGroup($parentContext.$index,$data);
											}">&times;</i></span>
										<!-- /ko -->
										</div>
									</li>
						
								</ul>
							</td>
							<td>
								<ul class="select">
									<li class="homeworkcorrecting">
									<label>批改老师：</label>
									<div class="con">
										<input autocomplete="off" style="width: 100px" type="text" class="item-teacher u-ipt u-ipt-nm j-dis" 
											name="teacherName" placeholder="请输入老师姓名" data-bind="value: teacherName,attr: {'data-index': $index()}" >
									</div>
									</li>
								</ul>
							</td>
							<td align="center">
								<span class="del" data-bind="click: function($data){ $root.rmHomework($data);}">删除</span>
							</td>
						</tr>
					<!-- /ko -->
					</table>
				</div>
				<div class="c-assign-date z-clear c-assign-bottom-date">
                    <div class="c-assign-picker">
                        <input id="startTime" type="text" class="u-ipt u-ipt-nm c-assign-date-container Wdate" placeholder="作业开始时间" 
                        	data-bind="datepicker: {value: startTime,dateFmt: 'yyyy-MM-dd HH:mm',minDate: '%y-%M-%d %h:%M:%d',maxDate: '#F{$dp.$D(\'closeTime\');}'}"
                        />
                        <p class="z-tips">作业开始时间</p>
                    </div>
                    <span class="c-assign-line"></span>
                    <div class="c-assign-picker">
                        <input id="closeTime" type="text" class="u-ipt u-ipt-nm c-assign-date-container Wdate" placeholder="作业截止时间"
                        	data-bind="datepicker: {value: closeTime,dateFmt: 'yyyy-MM-dd HH:mm',minDate: '#F{$dp.$D(\'startTime\');}'}"
                         />
                        <p class="z-tips">作业截止时间</p>
                    </div>
                    <div class="c-assign-picker c-assign-publish-picker">
                        <span class="c-assign-single-checkbox " data-bind="click: function(){doOpenAnswerTime();}, css: checkOpenAnswer() ? 'c-assign-single-checkbox-true' : ''; "> </span><span class="z-inline-zips" data-bind="click: function(){doOpenAnswerTime();} ">答案公布时间<i class="question-mark" title="公布答案后，学生可以看到正确答案和解析"></i></span>
                        <input  type="text" class="u-ipt u-ipt-nm c-assign-date-container Wdate" placeholder="公布答案时间"
                        	id="openAnswerTime" name="openAnswerTime" data-bind="visible: checkOpenAnswer, datepicker: {
						value: openAnswerTime, dateFmt: 'yyyy-MM-dd HH:mm',minDate: '#F{$dp.$D(\'startTime\');}'}"
                        >
                        <p class="z-tips"></p>
                    </div>
                </div>
				<div class="add-work"><span class="add" data-bind="click: addHomework">+增加</span></div>	
			<div class="submit">
				<button class="u-btn u-btn-nm" id="jSubmit" data-bind="click: saveHomework,css: $root.pending() ? 'u-btn-bg-gray' : 'u-btn-bg-turquoise',
   					disable: $root.pending" ><locale:message code="homework.homework.distribute.submit" /></button>
			</div>
		</div>
	</div>
</div>
	<script type="text/javascript">
		window.CST = {
				papers: []
		}
		seajs.use('homework/batch/assignLog.js?_t=20171115');
	</script>
</body>
</html>