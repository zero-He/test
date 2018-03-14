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

				self.gradeData = ko.observableArray(window.data.gradeStr);

				// 查询条件
				self.findGradeId = ko.observable();
				self.findLekeSn = ko.observable();
				self.findStudentName = ko.observable();

				// 导出
				self.studentExport = function() {
					var condition = new Object();
					if (self.findGradeId() != "") {
						condition.gradeId = self.findGradeId();
					}

					if (self.findLekeSn() != "") {
						condition.lekeSn = self.findLekeSn();
					}

					if (self.findStudentName() != "") {
						condition.studentName = self.findStudentName();
					}

					condition.schoolStageId = $("#schoolStageId").val();
					condition.schoolId = $("#schoolId").val();
					condition.statisticsTimeStart = $("#statisticsTimeStart")
							.val();
					condition.statisticsTimeEnd = $("#statisticsTimeEnd").val();

					var data = JSON.stringify(condition || {});

					window.location.href = Leke.ctx
							+ '/auth/common/crm/workNewStudentExport.htm?dataJson='
							+ data;
				}
			}

			var vm = new ViewModel();

			var win = {
				init : function() {
					this.findClick();
					utils.Notice.mask.create("正在查询请稍等");
					this.loadData();
				},
				findClick : function() {
					$("#find").click(function() {
								utils.Notice.mask.create("正在查询请稍等");
							})
				},
				loadData : function() {
					var _this = this;
					_this.page = Page.create({
								id : 'divPage',
								url : '/auth/common/crm/workNewStudentPage.htm',
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
										return _this.getStarHtml(this.star
												+ 0.0);
									}
									page.starInfo = function() {
										return _this.getStarInfo(this.star);
									}

									vm.findGradeId($("#gradeId").val());
									vm.findLekeSn($("#lekeSn").val());
									vm.findStudentName($("#studentName").val());

									var output = Mustache.render(
											$("#j_tempComments").html(), page);
									$('#j_bodyComments').html(output);

								}
							});
				}
			}

			win.init();
			ko.applyBindings(vm);
		});