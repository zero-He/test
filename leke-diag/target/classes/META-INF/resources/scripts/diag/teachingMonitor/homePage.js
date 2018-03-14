define(function(require, exports, module) {
	var $ = require('jquery');
	var JSON = require('json');
	var Handlebars = require('common/handlebars');
	var RenderEchart = require("./RenderEchart");
	var HandleCommParam = require("./HandleCommParam");
	var WdatePicker = require('date');
	var Dialog = require('dialog');
	var Utils = require('utils');
	var Base64 = require("base64");
	
	var dialogTemp = ['<div class="m-form">',
						'<ul>',
							'<li class="form-item">',
								'<label class="title">时间：</label>',
								'<span id="jParentEnabledTime"></span>',
							'</li>',
							'<li class="form-item">',
								'<label class="title">班级名称：</label>',
								'<span id="jParentEnableClassName"></span>',
							'</li>',
							'<li class="form-item">',
								'<label class="title">学科：</label>',
								'<span id="jParentEnableSubject"></span>',
							'</li>',
							'<li class="form-item">',
								'<label class="title">选项：</label>',
								'<div class="con">',
									'<input type="checkbox" id="jParentAuditEnable"/>允许家长旁听',
									'<input type="checkbox" id="jParentVideoEnable"/>允许家长查看录像',
								'</div>',
							'</li>',
						'</ul>',
					'</div>'].join("");
	
	var dialogHelp = ['<h3 style="font-weight:bold;">老师动态</h3>',
	                  '<p>课堂——“未上课”指的是当天已结束且老师缺课没上的课堂。</p>',
	                  '<p>备课——“已备课/未备课”数统计的是当天课堂数。</p>',
	                  '<p>作业——“布置”数指老师当天布置的班级作业份数，“批改”数指老师当天已全部批改完全的班级作业（不含系统批改）份数，“待批改”数指累计到当前时间未批改完成的班级作业份数。</p>',
	                  '<p>答疑——“解答数”是指当天解答数，“待解答数”是指截止到当前时间累计待解答数。</p>',
	                  '<h3 style="font-weight:bold;">学生动态</h3>',
	                  '<p>考勤——“缺课”、“迟到”、“早退”单位均是“人次”。</p>',
	                  '<p>提问——“提问题数”和“提问人数”均指当天的数目。</p>'
	                  ].join("");
	
	var Statistics = {

			init : function() {
				this.fLoadLastNotice();
				HandleCommParam.loadQueryParam();
				this.initDate();
				$("#isPrepared").val([0,1]);
				$("#lessonStatus").val([1,2,3,4])
				this.loadScheduleData(HandleCommParam.getParam());
				this.loadDynamicData(HandleCommParam.getParam());
				this.bindEvent();
			},
			
			
			fLoadLastNotice : function() {
				$.getJSON(Leke.domain.noticeServerName+"/unauth/notice/nowNomalNotice.htm?jsoncallback=?&schoolId="+Leke.user.currentSchoolId, function(data){
					if(data.success&&data.datas&&data.datas.lastNotice&&data.datas.lastNotice.title){
						var content = data.datas.lastNotice.title;
						content = content.replace(/\n/g, "");
						content = content.replace(/<[^>]+>/g, "");
						content = content.replace(/&nbsp;/g, "");
						content = $("<div><div>").html(content).text();
						var $lastNotice = $('#lastNotice');
						$lastNotice.append($("<a style='color:#f7785f;' href='"+Leke.domain.noticeServerName+"/auth/common/notice/list.htm'></a>").append(content));
						$lastNotice.attr('title', content);
					}
				});
			},
			
			bindEvent : function(){
				
				$("#gradeId").change(function(){
					Statistics.loadClassByGrade($(this).val());
					Statistics.loadScheduleData(HandleCommParam.getParam());
				});
				
				$("#subjectId").change(function(){
					Statistics.loadScheduleData(HandleCommParam.getParam());
				});
				
				$("#classId").change(function(){
					Statistics.loadScheduleData(HandleCommParam.getParam());
				});
				
				$("#scheduleDate").on("click",function(){
					WdatePicker({
						dateFmt : 'yyyy-MM-dd',
						// maxDate : HandleCommParam.getCurrentDate(),
						readOnly : true,
						onpicked: function() {
							var newDate = $dp.cal.getP("y")+"-"+$dp.cal.getP("M")+"-"+$dp.cal.getP("d");
							$("#startDate").val(newDate);
							$("#endDate").val(newDate);
							if(newDate == HandleCommParam.getCurrentDate()){
								$("#scheduleToday").removeClass("c-dateswitch__gotoday--not");
							}else{
								$("#scheduleToday").addClass("c-dateswitch__gotoday--not");
							}
							Statistics.loadScheduleData(HandleCommParam.getParam());
						}
					});
				});
				
				$("#scheduleToday").on("click", function(){
					$("#startDate").val(HandleCommParam.getCurrentDate());
					$("#endDate").val(HandleCommParam.getCurrentDate());
					$("#scheduleDate").html(HandleCommParam.getCurrentDate());
					$(this).removeClass("c-dateswitch__gotoday--not");
					Statistics.loadScheduleData(HandleCommParam.getParam());
				});
				
				$("#scheduleDatelt").on("click", function(){
					$("#scheduleDate").html(HandleCommParam.getDateByDelta($("#scheduleDate").html(), -1));
					$("#scheduleToday").addClass("c-dateswitch__gotoday--not");
					$("#startDate").val($("#scheduleDate").html());
					$("#endDate").val($("#scheduleDate").html());
					Statistics.loadScheduleData(HandleCommParam.getParam());
				});
				
				$("#scheduleDategt").on("click", function(){
					/*if($("#scheduleDate").html() == HandleCommParam.getCurrentDate()){
						return false;
					}*/
					$("#scheduleDate").html(HandleCommParam.getDateByDelta($("#scheduleDate").html(), 1));
					
					if($("#scheduleDate").html() == HandleCommParam.getCurrentDate()){
						$("#scheduleToday").removeClass("c-dateswitch__gotoday--not");
					}else{
						$("#scheduleToday").addClass("c-dateswitch__gotoday--not");
					}
					$("#startDate").val($("#scheduleDate").html());
					$("#endDate").val($("#scheduleDate").html());
					Statistics.loadScheduleData(HandleCommParam.getParam());
				});
				
				
				$("#dynamicDate").on("click",function(){
					WdatePicker({
						dateFmt : 'yyyy-MM-dd',
						maxDate : HandleCommParam.getCurrentDate(),
						readOnly : true,
						onpicked: function() {
							var newDate = $dp.cal.getP("y")+"-"+$dp.cal.getP("M")+"-"+$dp.cal.getP("d");
							if(newDate == HandleCommParam.getCurrentDate()){
								$("#dynamicToday").removeClass("c-dateswitch__gotoday--not");
							}else{
								$("#dynamicToday").addClass("c-dateswitch__gotoday--not");
							}
							Statistics.loadDynamicData({startDate:newDate, endDate:newDate});
						}
					});
				});
				
				
				$("#dynamicToday").on("click", function(){
					$("#dynamicDate").html(HandleCommParam.getCurrentDate());
					$(this).removeClass("c-dateswitch__gotoday--not");
					Statistics.loadDynamicData({startDate:$("#dynamicDate").html(), endDate:$("#dynamicDate").html()});
				});
				
				$("#dynamicDatelt").on("click", function(){
					$("#dynamicDate").html(HandleCommParam.getDateByDelta($("#dynamicDate").html(), -1));
					$("#dynamicToday").addClass("c-dateswitch__gotoday--not");
					Statistics.loadDynamicData({startDate:$("#dynamicDate").html(), endDate:$("#dynamicDate").html()});
				});
				
				$("#dynamicDategt").on("click", function(){
					if($("#dynamicDate").html() == HandleCommParam.getCurrentDate()){
						return false;
					}
					$("#dynamicDate").html(HandleCommParam.getDateByDelta($("#dynamicDate").html(), 1));
					if($("#dynamicDate").html() == HandleCommParam.getCurrentDate()){
						$("#dynamicToday").removeClass("c-dateswitch__gotoday--not");
					}else{
						$("#dynamicToday").addClass("c-dateswitch__gotoday--not");
					}
					Statistics.loadDynamicData({startDate:$("#dynamicDate").html(), endDate:$("#dynamicDate").html()});
				});
				
				
				$("[name='lessonStatusChk']").on("click",function(){
					var arr = [];
					$("[name='lessonStatusChk']:checked").each(function(){
						arr.push($(this).val());
					});
					$("#lessonStatus").val(arr);
					Statistics.loadScheduleData(HandleCommParam.getParam());
				});
				
				$("[name='isPreparedChk']").on("click",function(){
					var arr = [];
					$("[name='isPreparedChk']:checked").each(function(){
						arr.push($(this).val());
					});
					$("#isPrepared").val(arr);
					Statistics.loadScheduleData(HandleCommParam.getParam());
				});
				
				$(".iconfont").on("click", function(){
					Dialog.open({
						title: "帮助",
						tpl: dialogHelp,
						size: 'nm',
						btns: [{
							className: 'btn-green',
							text: "关闭",
							cb: function() {
								Dialog.close();
							}
						}]
					});
				});
				
			},
			
			initDate : function(){
				var now = HandleCommParam.getCurrentDate();
				$("#scheduleDate").html(now);
				$("#dynamicDate").html(now);
				$("#startDate").val(now);
				$("#endDate").val(now);
			},
			
			loadClassByGrade : function(gradeId){
				$.post("/auth/provost/teachingMonitor/comm/loadClassByGrade.htm", {gradeId:gradeId}, function(json){
					var data = json.datas;
					if (data) {
						Handlebars.render("#classTpl", data, "#classId");
					}
				});
			},
			
			loadDynamicData : function(param){
				$.post("/auth/provost/teachingMonitor/homepage/findHomePageDynamic.htm", param, function(json){
					var dynamic = json.datas.dynamic;
					if (dynamic) {
						$("#onlineTeacher").html(dynamic.onlineTeacher);
						$("#totalTeacher").html(dynamic.totalTeacher);
						$("#onlineStudent").html(dynamic.onlineStudent);
						$("#totalStudent").html(dynamic.totalStudent);
						$("#onlineParent").html(dynamic.onlineParent);
						$("#totalParent").html(dynamic.totalParent);
						
						$("#notAttendStu").html(dynamic.notAttendStu);
						$("#lateStu").html(dynamic.lateStu);
						$("#earlyStu").html(dynamic.earlyStu);
						$("#doubt").html(dynamic.doubt);
						$("#doubtStu").html(dynamic.doubtStu);
						$("#notAttendLesson").html(dynamic.notAttendLesson);
						$("#endLesson").html(dynamic.endLesson);
						$("#onLesson").html(dynamic.onLesson);
						$("#awaitingLesson").html(dynamic.awaitingLesson);
						$("#assignHW").html(dynamic.assignHW);
						$("#correctHW").html(dynamic.correctHW);
						$("#awaitingCorrectHW").html(dynamic.awaitingCorrectHW > 1000 ? "999+" : dynamic.awaitingCorrectHW);
						$("#prepareLesson").html(dynamic.prepareLesson);
						$("#notPrepareLesson").html(dynamic.notPrepareLesson);
						$("#resolveDoubt").html(dynamic.resolveDoubt);
						$("#notResolveDoubt").html(dynamic.notResolveDoubt > 1000 ? "999+" : dynamic.notResolveDoubt);
					}
				});
			},


		loadScheduleData: function (param) {
			$.post("/auth/provost/teachingMonitor/homepage/findHomePageSchedule.htm", param, function (json) {
				var data = json.datas;
				if (data) {
					if (data.scheduleList.length <= 0) {
						$("div.c-searchnull").show();
						$("div.c-timetable").hide();
					} else {
						$("div.c-searchnull").hide();
						$("div.c-timetable").show();

						Handlebars.render("#dtlTpl", data, "#jtbodyData");

						$(document).on("click", ".js-listen", function () {
							var jsonDate = {};
							jsonDate.courseSingleId = $(this).data("id");
							jsonDate.roleId = Leke.user.currentRoleId;
							jsonDate.userId = Leke.user.userId;
							jsonDate.ticket = Leke.ticket;
							jsonDate.courseType = '';
							var desktopUrl = Leke.domain.lessonServerName + '/auth/common/lesson/play.htm?udata='
								+ Base64.enc.Base64.stringify(Base64.enc.Utf8.parse(JSON.stringify(jsonDate)));
							window.open(desktopUrl);
						});


						$(document).on("click", ".j-parent-enable", function () {
							var lessonId = $(this).data("id");
							$.getJSON(
								Leke.domain.lessonServerName + "/auth/common/jsonp/lesson/getLesson.htm?jsoncallback=?",
								{lessonId: lessonId},
								function (datas) {
									if (datas.success) {
										var $dialogTemp = $(dialogTemp);
										$dialogTemp.find("#jParentEnabledTime").html(datas.datas.time);
										$dialogTemp.find("#jParentEnableClassName").html(datas.datas.className);
										$dialogTemp.find("#jParentEnableSubject").html(datas.datas.subjectName);
										$dialogTemp.find("#jParentAuditEnable").attr("checked", datas.datas.parentAuditEnable);
										$dialogTemp.find("#jParentVideoEnable").attr("checked", datas.datas.parentVideoEnable);
										if (datas.datas.isEnd) {
											$dialogTemp.find("#jParentAuditEnable").attr("disabled", "disabled");
										}
										Dialog.open({
											title: "家长听课设置",
											tpl: $dialogTemp,
											size: 'nm',
											btns: [{
												className: 'btn-green',
												text: "确定",
												cb: function () {
													var auditEnable = $("#jParentAuditEnable").prop("checked");
													var videoEnable = $("#jParentVideoEnable").prop("checked");
													$.getJSON(
														Leke.domain.lessonServerName + "/auth/common/jsonp/lesson/parentEnable.htm?jsoncallback=?",
														{lessonId: lessonId, auditEnable: auditEnable, videoEnable: videoEnable}, function (datas) {
															if (datas.success) {
																Utils.Notice.alert("修改成功");
																Dialog.close();
															} else {
																Utils.Notice.alert(datas.message);
															}
														}
													);
												}
											}, {
												className: 'btn-gray',
												text: "取消",
												cb: function () {
													Dialog.close();
												}
											}]
										});
									} else {
										Utils.Notice.alert(datas.message);
									}
								}
							);
						});
					}

				}
			});
		}

	};

		Statistics.init();
});
