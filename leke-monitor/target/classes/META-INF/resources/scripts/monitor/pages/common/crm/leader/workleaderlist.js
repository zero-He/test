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
		self.schoolStageData = ko.observableArray(window.data.schoolStageStr);
		self.areas = ko.observableArray(window.data.areas);

		// 营销处
		self.dptData = ko.observableArray(window.data.areas);

		// 查询条件
		self.market = ko.observable();
		self.marketId = ko.observable();
		self.lekeSn = ko.observable();
		self.findSellerName = ko.observable();
		self.findSchoolId = ko.observable();
		self.schoolStageId = ko.observable();
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
			if (self.market() != "") {
				condition.market = self.market();
			}

			if (self.marketId() != "") {
				condition.marketId = self.marketId();
			}

			if (self.lekeSn() != "") {
				condition.lekeSn = self.lekeSn();
			}

			if (self.findSellerName() != "") {
				condition.sellerName = self.findSellerName();
			}

			if (self.findSchoolId() != "") {
				condition.schoolId = self.findSchoolId();
			}

			if (self.schoolStageId() != "") {
				condition.schoolStageId = self.schoolStageId();
			}

			if (self.findStatisticsTimeStart() != "") {
				condition.statisticsTimeStart = self.findStatisticsTimeStart();
			}

			if (self.findStatisticsTimeEnd() != "") {
				condition.statisticsTimeEnd = self.findStatisticsTimeEnd();
			}

			var data = JSON.stringify(condition || {});

			window.location.href = Leke.ctx
					+ '/auth/common/crm/leader/workleaderexport.htm?dataJson='
					+ data;
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
						url : '/auth/common/crm/leader/workleaderlistPage.htm',
						curPage : 1,
						pageSize : 10,
						pageCount : 10,// 分页栏上显示的分页数
						formId : 'form',
						tipsId : "jTipsId",
						callback : function(datas) {
							utils.Notice.mask.close();
							// 数据处理，渲染表格
							var page = datas.page;
							page.starStr = function() {
								return _this.getStarHtml(this.star + 0.0);
							}
							page.starInfo = function() {
								return _this.getStarInfo(this.star);
							}

							self.market($("#market").val());
							self.marketId($("#marketId").val());
							self.lekeSn($("#lekeSn").val());
							self.findSellerName($("#sellerName").val());
							self.findSchoolId($("#schoolId").val());
							self.schoolStageId($("#schoolStageId").val());
							self
									.findStatisticsTimeStart($("#statisticsTimeStart")
											.val());
							self.findStatisticsTimeEnd($("#statisticsTimeEnd")
									.val());

							page.statisticsTimeStart = $("#statisticsTimeStart")
									.val();
							page.statisticsTimeEnd = $("#statisticsTimeEnd")
									.val();

							var output = Mustache.render($("#j_tempComments")
											.html(), page);
							$('#j_bodyComments').html(output);
						}
					});
		}

		// 默认执行方法
		self.init = function() {
			utils.Notice.mask.create("正在查询请稍等");
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

			$("#find").click(function() {
						$("#isFind").val("1");
						utils.Notice.mask.create("正在查询请稍等");
					})
		}
	}

	var vm = new ViewModel();
	ko.applyBindings(vm);
	vm.init();
});