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

	// 定义ViewModel
	function ViewModel() {
		var self = this;

		// 添加动态监视数组对象
		self.teacherClass = ko.observableArray();
		self.subjectData = ko.observableArray(window.data.subjectStr);
		self.gradeData = ko.observableArray(window.data.gradeStr);

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

		// 分页查询
		self.findPage = function() {
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

							self.findSubjectId($("#subjectId").val());
							self.findGradeId($("#gradeId").val());
							self.findTeacherName($("#teacherName").val());
							self
									.findStatisticsTimeStart($("#statisticsTimeStart")
											.val());
							self.findStatisticsTimeEnd($("#statisticsTimeEnd")
									.val());

							var dataList = page.dataList;

							self.teacherClass(dataList);
						}
					});
		}

		// 默认执行方法
		self.init = function() {
			self.findPage();

			$('#statisticsTimeStart').focus(function() {
						WdatePicker({
									dateFmt : 'yyyy-MM-dd',
									maxDate : '#F{$dp.$D(\'statisticsTimeEnd\')}',
									readOnly : true
								});
					})

			$('#statisticsTimeEnd').focus(function() {
						WdatePicker({
									dateFmt : 'yyyy-MM-dd',
									minDate : '#F{$dp.$D(\'statisticsTimeStart\')}',
									readOnly : true
								});
					})
		}
	}

	var vm = new ViewModel();

	// 课堂明细默认绑定字段
	var classStatistics = window.data.classStatistics;
	for (var item in classStatistics) {
		vm[item] = ko.observable(classStatistics[item]);
	}

	ko.applyBindings(vm);
	vm.init();
});