define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	var Utils = require('utils');
	var Dialog = require('dialog');
	var LekeDate = require('common/base/date');
	var http = require('common/base/http');
	var utils = require('utils');
	var ko = require('knockout');
	var WdatePicker = require('date');

	// 定义学校课堂数据对象
	function teacherClassViewModel(subjectId, subjectName, gradeId, gradeName,
			teacherId, teacherName, endClassNum, beikeClassNum, classNum,
			beikeRate, classRate) {
		var self = this;
		self.subjectId = subjectId;
		self.subjectName = subjectName;
		self.gradeId = gradeId;
		self.gradeName = gradeName;
		self.teacherId = teacherId;
		self.teacherName = teacherName;
		self.endClassNum = endClassNum;
		self.beikeClassNum = beikeClassNum;
		self.classNum = classNum;
		self.beikeRate = beikeRate;
		self.classRate = classRate;
	}

	// 定义ViewModel
	function ViewModel() {
		var self = this;

		// 添加动态监视数组对象
		self.teacherClass = ko.observableArray();
		self.subjectData = ko.observableArray(window.subjectStr);
		self.gradeData = ko.observableArray(window.gradeStr);

		// 明细验证
		self.subjectId = ko.observable();
		self.gradeId = ko.observable();
		self.teacherId = ko.observable();

		// 查询条件
		self.findSubjectId = ko.observable();
		self.findGradeId = ko.observable();
		self.findTeacherName = ko.observable();
		self.findStatisticsTimeStart = ko.observable();
		self.findStatisticsTimeEnd = ko.observable();

		// 导出
		self.classExport = function() {
			var condition = new Object();
			if (self.findSubjectId() != "") {
				condition.subjectId = self.findSubjectId();
			}

			if (self.findGradeId() != "") {
				condition.gradeId = self.findGradeId();
			}

			if (self.findTeacherName() != "") {
				condition.teacherName = self.findTeacherName();
			}

			if (self.findStatisticsTimeStart() != "") {
				condition.statisticsTimeStart = self.findStatisticsTimeStart();
			}

			if (self.findStatisticsTimeEnd() != "") {
				condition.statisticsTimeEnd = self.findStatisticsTimeEnd();
			}

			condition.sellerId = $("#sellerId").val();
			condition.schoolId = $("#schoolId").val();
			condition.schoolStageId = $("#schoolStageId").val();

			var data = JSON.stringify(condition || {});

			window.location.href = Leke.ctx
					+ '/auth/common/crm/teacherStatisticsExport.htm?dataJson='
					+ data;
		}

		// 考勤统计
		self.attendance = function(teacherClass) {
			window.open(Leke.ctx
					+ "/auth/common/crm/attendance/classlist.htm?schoolId="
					+ $("#schoolId").val() + "&gradeId=" + teacherClass.gradeId
					+ "&subjectId=" + teacherClass.subjectId + "&subjectName="
					+ teacherClass.subjectName + "&schoolName="
					+ $("#schoolName").val() + "&gradeName="
					+ teacherClass.gradeName + "&teacherId="
					+ teacherClass.teacherId + "&teacherName="
					+ teacherClass.teacherName + "&fromDate="
					+ self.findStatisticsTimeStart() + "&endDate="
					+ self.findStatisticsTimeEnd());
		}

		// 课堂明细
		self.classDetails = function(teacherClass) {
			var url = Leke.ctx + '/auth/common/crm/teacherClassDetails.htm';
			var datas = {
				sellerId : $("#sellerId").val(),
				schoolId : $("#schoolId").val(),
				schoolStageId : $("#schoolStageId").val(),
				subjectId : teacherClass.subjectId,
				subjectName : teacherClass.subjectName,
				gradeId : teacherClass.gradeId,
				gradeName : teacherClass.gradeName,
				teacherId : teacherClass.teacherId,
				teacherName : teacherClass.teacherName,
				beikeClassNum : teacherClass.beikeClassNum,
				classNum : teacherClass.classNum,
				statisticsTimeStart : self.findStatisticsTimeStart(),
				statisticsTimeEnd : self.findStatisticsTimeEnd()
			};

			$.post(url, datas, function(json) {
						var classDetails = eval("(" + json.datas.classDetails
								+ ")");
						for (var item in classDetails) {
							if (classDetails[item] == null) {
								self[item]("0");
							} else {
								self[item](classDetails[item]);
							}
						}
					});
			if (self.subjectId() != teacherClass.subjectId
					|| self.gradeId() != teacherClass.gradeId
					|| self.teacherId() != teacherClass.teacherId) {
				$("#beikeDetails").show();
			} else {
				$("#beikeDetails").toggle();
			}

			self.subjectId(teacherClass.subjectId);
			self.gradeId(teacherClass.gradeId);
			self.teacherId(teacherClass.teacherId);
		}
	}

	var vm = new ViewModel();

	// 课堂明细默认绑定字段
	var classStatistics = window.classStatistics;
	for (var item in classStatistics) {
		vm[item] = ko.observable(classStatistics[item]);
	}

	var win = {
		init : function() {
			this.statisticsTimeStartBind();
			this.statisticsTimeEndBind();
			this.loadData();
		},
		statisticsTimeStartBind : function() {
			$('#statisticsTimeStart').focus(function() {
						WdatePicker({
									dateFmt : 'yyyy-MM-dd',
									maxDate : '#F{$dp.$D(\'statisticsTimeEnd\')}',
									readOnly : true
								});
					})
		},
		statisticsTimeEndBind : function() {
			$('#statisticsTimeEnd').focus(function() {
						WdatePicker({
									dateFmt : 'yyyy-MM-dd',
									minDate : '#F{$dp.$D(\'statisticsTimeStart\')}',
									readOnly : true
								});
					})
		},
		loadData : function() {
			var _this = this;
			_this.page = Page.create({
				id : 'divPage',
				url : '/auth/common/crm/teacherStatisticsPage.htm',
				curPage : 1,
				pageSize : 10,
				pageCount : 10,// 分页栏上显示的分页数
				formId : 'form',
				tipsId : "jTipsId",
				callback : function(datas) {
					// 数据处理，渲染表格
					var page = datas.page;
					page.starStr = function() {
						return _this.getStarHtml(this.star + 0.0);
					}
					page.starInfo = function() {
						return _this.getStarInfo(this.star);
					}

					$("#beikeDetails").hide();

					vm.teacherClass.removeAll();

					vm.findSubjectId($("#subjectId").val());
					vm.findGradeId($("#gradeId").val());
					vm.findTeacherName($("#teacherName").val());
					vm.findStatisticsTimeStart($("#statisticsTimeStart").val());
					vm.findStatisticsTimeEnd($("#statisticsTimeEnd").val());

					var dataList = page.dataList;

					for (var i = 0; i < dataList.length; i++) {
						vm.teacherClass.push(new teacherClassViewModel(
								dataList[i].subjectId, dataList[i].subjectName,
								dataList[i].gradeId, dataList[i].gradeName,
								dataList[i].teacherId, dataList[i].teacherName,
								dataList[i].endClassNum,
								dataList[i].beikeClassNum,
								dataList[i].classNum, dataList[i].beikeRate,
								dataList[i].classRate));
					}

				}
			});
		}
	}

	win.init();
	ko.applyBindings(vm);
});