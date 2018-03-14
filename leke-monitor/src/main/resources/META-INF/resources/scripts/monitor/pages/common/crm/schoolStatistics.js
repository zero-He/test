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
	require('common/ui/ui-autocomplete/ui-autocomplete');

	// 定义学校课堂数据对象
	function schoolClassViewModel(sellerId, sellerName, schoolId, schoolName,
			schoolStageId, schoolStageName, endClassNum, beikeClassNum,
			classNum, beikeRate, classRate) {
		var self = this;
		self.sellerId = sellerId;
		self.sellerName = sellerName;
		self.schoolId = schoolId;
		self.schoolName = schoolName;
		self.schoolStageId = schoolStageId;
		self.schoolStageName = schoolStageName;
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
		self.schoolClass = ko.observableArray();
		self.schoolStageData = ko.observableArray(window.schoolStageStr);

		// 明细验证

		self.schoolId = ko.observable();
		self.schoolStageId = ko.observable();

		// 查询条件
		self.findSellerName = ko.observable();
		self.findSchoolStageId = ko.observable();
		self.findSchoolId = ko.observable();
		self.findStatisticsTimeStart = ko.observable();
		self.findStatisticsTimeEnd = ko.observable();

		self.$sch = $('#schoolName');
		self.$sch.autocomplete({
					url : Leke.ctx + '/auth/common/data/querySchoolLike.htm',
					nameKey : 'schoolName',
					onChange : function(school) {
						if (school != null) {
							$("#schoolId").val(school.schoolId);
						} else {
							$("#schoolId").val("");
						}
					}
				});

		// 导出
		self.classExport = function() {
			var condition = new Object();
			if (self.findSellerName() != "") {
				condition.sellerName = self.findSellerName();
			}

			if (self.findSchoolStageId() != "") {
				condition.schoolStageId = self.findSchoolStageId();
			}

			if (self.findSchoolId() != "") {
				condition.schoolId = self.findSchoolId();
			}

			if (self.findStatisticsTimeStart() != "") {
				condition.statisticsTimeStart = self.findStatisticsTimeStart();
			}

			if (self.findStatisticsTimeEnd() != "") {
				condition.statisticsTimeEnd = self.findStatisticsTimeEnd();
			}

			var data = JSON.stringify(condition || {});

			window.location.href = Leke.ctx
					+ '/auth/common/crm/classStatisticsExport.htm?dataJson='
					+ data;
		}

		// 考勤
		self.attendance = function(schoolClass) {
			window.open(Leke.ctx
					+ "/auth/common/crm/attendance/list.htm?schoolId="
					+ schoolClass.schoolId + "&schoolStageId="
					+ schoolClass.schoolStageId + "&schoolName="
					+ schoolClass.schoolName + "&fromDate="
					+ self.findStatisticsTimeStart() + "&endDate="
					+ self.findStatisticsTimeEnd());
		}

		// 详情
		self.details = function(schoolClass) {
			window
					.open(Leke.ctx
							+ "/auth/common/crm/teacherStatisticsList.htm?spm=112005&schoolId="
							+ schoolClass.schoolId + "&schoolStageId="
							+ schoolClass.schoolStageId + "&sellerId="
							+ schoolClass.sellerId + "&statisticsTimeStart="
							+ self.findStatisticsTimeStart()
							+ "&statisticsTimeEnd="
							+ self.findStatisticsTimeEnd());
		}
		// 课堂明细
		self.classDetails = function(schoolClass) {
			var url = Leke.ctx + '/auth/common/crm/classDetails.htm';
			var datas = {
				schoolId : schoolClass.schoolId,
				schoolName : schoolClass.schoolName,
				schoolStageId : schoolClass.schoolStageId,
				schoolStageName : schoolClass.schoolStageName,
				beikeClassNum : schoolClass.beikeClassNum,
				classNum : schoolClass.classNum,
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
			if (self.schoolId() != schoolClass.schoolId
					|| self.schoolStageId() != schoolClass.schoolStageId) {
				$("#beikeDetails").show();
			} else {
				$("#beikeDetails").toggle();
			}

			self.schoolId(schoolClass.schoolId);
			self.schoolStageId(schoolClass.schoolStageId);
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
			this.findClick();
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
		findClick : function() {
			$("#find").click(function() {
						$("#isFind").val("1");
					})
		},
		loadData : function() {
			var _this = this;
			_this.page = Page.create({
				id : 'divPage',
				url : '/auth/common/crm/schoolStatisticsPage.htm',
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

					vm.schoolClass.removeAll();

					vm.findSellerName($("#sellerName").val());
					vm.findSchoolStageId($("#schoolStageId").val());
					vm.findSchoolId($("#schoolId").val());
					vm.findStatisticsTimeStart($("#statisticsTimeStart").val());
					vm.findStatisticsTimeEnd($("#statisticsTimeEnd").val());

					var dataList = page.dataList;

					for (var i = 0; i < dataList.length; i++) {
						vm.schoolClass.push(new schoolClassViewModel(
								dataList[i].sellerId, dataList[i].sellerName,
								dataList[i].schoolId, dataList[i].schoolName,
								dataList[i].schoolStageId,
								dataList[i].schoolStageName,
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