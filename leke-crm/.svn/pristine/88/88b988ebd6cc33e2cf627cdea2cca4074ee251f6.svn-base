define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Utils = require('utils');
	var Dialog = require('dialog');
	var KindEditor = require('editor');
	var WdatePicker = require('date');
	var Mustache = require('mustache');

	var Pages = {
		init : function() {
			var self = this;
			this.fLoadData();

			$(document).on('click', '.refuse', function() {
				self.doBind($(this).parent('td').find('input[name="customerId"]').val(), 2);
			});
			$(document).on('click', '.confrim', function() {
				self.openCheck($(this).parent('td').find('input[name="customerId"]').val(), $(this).parent('td').find('input[name="lekeId"]').val());
			});
		},
		fLoadData : function() {
			var self = this;
			self.page = Page.create({
				id : 'divPage',
				url : Leke.ctx + '/auth/scs/customer/getApproveList.htm',
				curPage : 1,
				pageSize : 10,
				pageCount : 10,// 分页栏上显示的分页数
				formId : 'form',
				tipsId : "jTipsId",
				callback : function(datas) {
					// 数据处理，渲染表格
					var page = datas.page;
					var output = Mustache.render($("#j_tempComments").html(), page);
					$('#j_bodyComments').html(output);
				}
			});
		},
		doBind : function(id, state, relationSale) {
			var self = this;
			var str = '';
			if (state == '1') {
				str = '确认';
			}
			else {
				str = '拒绝';
			}
			$.post('/auth/scs/customer/doBind.htm', {
				customerId : id,
				status : state,
				relationSale : relationSale || ''
			}, function(datas) {
				if (datas.success) {
					self.fLoadData();
					Utils.Notice.alert(str + '绑定成功');
				}
				else {
					Utils.Notice.alert(str + '绑定失败');
				}
			});
		},
		openCheck : function(customerId, lekeId) {
			var self = this; 
			$.post('/auth/scs/customer/checkHasBind.htm', {
				lekeId : lekeId
			}, function(datas) {
				if (datas.success) {
					var relationSale = datas.datas.relationSale || 0;
					var str = '';
					var isHas = '';
					if (!datas.datas.f) {
						str = '个人老师首次绑定客户经理，系统可赠送<input type="text" class="u-ipt u-ipt-sm" style="width:50px;" name="relationSale" value="" />个体验点数（最大为'
								+ relationSale + '点）。是否绑定？';
						isHas = '0';
					}
					else {
						str = '该老师多次绑定客户经理，系统已赠送过体验点数，是否绑定？';
						isHas = '1';
					}
					Dialog.open({
						title : '',
						size : 'mini',
						tpl : str,
						onClose : function(data) {
							// window.location.href = "home.htm";
						},
						btns : [ {
							className : 'btn-base btn-green',
							text : '确定',
							cb : function() {
								if (isHas == '0') {
									if ($('input[name="relationSale"]').val() == "") {
										Utils.Notice.alert("请输入赠送点数！");
										return;
									}

									var reg = /^\d+$/;
									if (!reg.test($('input[name="relationSale"]').val())) {
										Utils.Notice.alert("请输入整数！");
										return;
									}

									if (parseInt($('input[name="relationSale"]').val() || 0) > relationSale) {
										Utils.Notice.alert("赠送点数不能超过" + relationSale + "点！");
										return;
									}
									self.doBind(customerId, 1, $('input[name="relationSale"]').val());
								}
								else{					
									self.doBind(customerId, 1, '');
								}				
								this.close(false);

							}
						}, {
							className : 'btn-base btn-gray',
							text : '取消',
							cb : function() {
								this.close(false);
							}
						} ]
					});

				}
			});
		}
	};

	 Pages.init();

});
