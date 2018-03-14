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

	// 定义ViewModel
	function ViewModel() {
		var self = this;

		// 添加动态监视数组对象
		self.schoolClass = ko.observableArray();
		self.schoolStageData = ko.observableArray(window.data.schoolStageStr);
		self.areas = ko.observableArray(window.data.areas);

		// 营销处
		self.dptData = ko.observableArray(window.data.areas);

		// 明细验证
		self.schoolId = ko.observable();
		self.schoolStageId = ko.observable();

		// 查询条件
		self.findmarket = ko.observable();
		self.findmarketId = ko.observable();
		self.findLoginName = ko.observable();
		self.findSellerName = ko.observable();
		self.findSchoolStageId = ko.observable();
		self.findSchoolId = ko.observable();
		self.findStatisticsTimeStart = ko.observable();
		self.findStatisticsTimeEnd = ko.observable();

		// 学校自动完成控件
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
			if (self.findmarket() != "") {
				condition.market = self.findmarket();
			}

			if (self.findmarketId() != "") {
				condition.marketId = self.findmarketId();
			}

			if (self.findLoginName() != "") {
				condition.loginName = self.findLoginName();
			}

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
					+ '/auth/common/crm/leader/classleaderexport.htm?dataJson='
					+ data;
		}

		// 详情
		self.details = function(schoolClass) {
			window
					.open(Leke.ctx
							+ "/auth/common/crm/leader/teacherleaderlist.htm?spm=112005&schoolId="
							+ schoolClass.schoolId + "&schoolStageId="
							+ schoolClass.schoolStageId + "&sellerId="
							+ schoolClass.sellerId + "&statisticsTimeStart="
							+ self.findStatisticsTimeStart()
							+ "&statisticsTimeEnd="
							+ self.findStatisticsTimeEnd() + "&loginName="
							+ schoolClass.loginName + "&marketName="
							+ schoolClass.marketName);
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

		// 营销部绑定营销处
		self.marketChange = function() {
			var marketId = $("#market").val();

			var area = self.areas();

			var market = [];

			_.each(area, function(n) {
						if (marketId.length <= 0
								|| parseInt(n.pId) == parseInt(marketId)) {
							market.push(n);
						}
					});

			self.dptData(market);
		}

		// 分页查询
		self.findPage = function() {
			var _this = this;
			_this.page = Page.create({
						id : 'divPage',
						url : '/auth/common/crm/leader/schoolleaderpage.htm',
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

							self.findmarket = ko.observable($("#market").val());
							self.findmarketId = ko.observable($("#marketId").val());
							self.findLoginName($("#loginName").val());
							self.findSellerName($("#sellerName").val());
							self.findSchoolStageId($("#schoolStageId").val());
							self.findSchoolId($("#schoolId").val());
							self
									.findStatisticsTimeStart($("#statisticsTimeStart")
											.val());
							self.findStatisticsTimeEnd($("#statisticsTimeEnd")
									.val());

							var dataList = page.dataList;

							self.schoolClass(dataList);
						}
					});
		}

		// 默认执行方法
		self.init = function() {
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

			$("#find").click(function() {
						$("#isFind").val("1");
					})

			self.findPage();
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