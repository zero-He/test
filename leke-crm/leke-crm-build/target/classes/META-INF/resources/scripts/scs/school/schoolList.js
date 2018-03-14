define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	var Mustache = require('mustache');
	var Utils = require('utils');
	var dialog = require('dialog');

	var Pages = {
		init : function() {
			this.fLoadData();
		},
		fLoadData : function() {
			Page.create({
				id : 'divPage',
				url : ctx + '/auth/scs/customer/schoolListDate.htm',
				pageSize : 10,
				buttonId : 'btn_search',
				formId : 'jPageForm',
				callback : function(datas) {
					//数据处理，渲染表格
					$('#tbodySh').html("");

					var coursePageView = datas.page;
					if (coursePageView.totalSize != 0) {
						coursePageView.isWebStr = function() {
							if (this.isWeb == 1) {
								return '是';
							} else {
								return '否';
							}
						}
						coursePageView.statusStr = function() {
							if (this.status == 1) {
								return '已关联';
							} else {
								return '未关联';
							}
						}
						coursePageView.operate = function() {
							return '<a class="aDltSchool" data-id="'+this.customerId+'">取消关联</a>';
						}
						
						var output = Mustache.render($("#schoolList").val(), coursePageView);
						$("#tbodySh").html(output);
						Pages.bindDeleteSchool();
						
					} else {
						$("#tbodySh").html("");
					}
				}
			});
		},
		bindDeleteSchool : function() {
			var _this = this;
			$(".aDltSchool").each(function() {
				$(this).bind("click", function() {
					var customerId = $(this).data("id");
					dialog.confirm("确定删除?").done(function(sure) {
						if (sure == true) {
							_this.deleteSchoolCustomer(customerId);
						}
					});
				});
			});
		},
		deleteSchoolCustomer : function(customerId) {
			$.post(ctx + '/auth/scs/customer/delSchoolCustomer.htm', {
				"customerId" : customerId
			}, function(data, status) {
				if (data.success) {
					Utils.Notice.alert("学校删除成功!");
					$("#schoolCustomer" + customerId).remove();
				}
			}, "json");
		},
			
	};

	Pages.init();

});
