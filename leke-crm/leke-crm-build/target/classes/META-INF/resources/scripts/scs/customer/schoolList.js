
	
define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	var Mustache = require('mustache');
	var Utils = require('utils');
	var dialog = require('dialog');
	var http = require('common/base/http');
	//为了测试金额，目前支持小数点后两位，测试结束之后 改回
	var reg= /^\+?[1-9][0-9]*$/;
	//var reg= /^(([0-9]+)|([0-9]+\.[0-9]{1,2}))$/;
	
	$("input[name='code']").live("blur", function (){
		if(!/^\d{0,10}$/.test($(this).val())){
			$(this).val("");
			Utils.Notice.alert('学校编码是最多10位的数字');
			
		}
	});
	
	//关闭按钮
	$("input[class='u-btn u-btn-nm u-btn-bg-gray']").live("click", function (){dialog.close(false)});
	//直接支付按钮
	$("input[class='u-btn u-btn-nm u-btn-bg-turquoise f-mr50 f-ml10']").live("click", function (){
		var id = $(this).attr("data-id");
		var amount = $("#amount").val();
		
		var id = $(this).attr("data-id");
		var amount = $("#amount").val();
		var id = $(this).attr("data-id");
		var amount = $("#amount").val();
		
		if (!amount || amount == '') {
			Utils.Notice.alert('请输入金额！');
			return;
		}
		if(!reg.test(amount)){
			Utils.Notice.alert('金额必须是非0整数！');
			return;
		}
		if(amount>990000){
			dialog.alert('一笔交易最多99万！');
			return;
		}
		dialog.confirm('确定要直接支付吗？').done(function(sure){
			if(sure){
				window.location = 
					Leke.domain.payServerName + "/auth/seller/order/rechargeLekeAccount.htm?money="+amount+"&schoolId="+id;
			}
		});
	});
	//线下支付按钮
	$("input[class='u-btn u-btn-nm u-btn-bg-turquoise f-mr50']").live("click", function (){
		
		var id = $(this).attr("data-id");
		var amount = $("#amount").val();
		var schoolName = $(this).attr("data-name");
		
		if (!amount || amount == '') {
			Utils.Notice.alert('请输入金额！');
			return;
		}
		if(!reg.test(amount)){
			Utils.Notice.alert('金额必须是非0整数！');
			return;
		}
		if(amount>10000000){
			Utils.Notice.alert('一笔交易最多1000万！');
			return;
		}
		
		dialog.confirm('确定要线下支付吗？').done(function(sure){
			if(sure){
				var query = {};
				query.schoolId = id;
				query.amount = amount;
				query.schoolName = schoolName;
				
				http.post('/auth/scs/customer/addOnlineApply.htm',query).then(function(datas) {
					if(!datas.f)
					{
						Utils.Notice.alert('线上申请操作失败！');
					}
					else
					{
						Utils.Notice.alert('操作成功!正在等待财务审核...',8000);
						dialog.close(false);
						$("#isApplyStr").html("申请中");
					}
				}, function(msg) {
					Utils.Notice.alert(msg || '操作失败！');
				});
			}
		});
	});
	
	$(".aSchoolcurrency").live("click", function openCZ(){
		var dataName = $(this).attr("data-name");
		var schoolId = $(this).attr("data-id");
		var output = Mustache.render($('#presenterTemp').html(), {schoolName:dataName,schoolId:schoolId});
		dialog.open({
			title:"充值",
			tpl:output,
			onClose:function(){
				click = true;
			},
			btns: [],
			size:'sm'	
		});
		
	});
	
	
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
						coursePageView._statusStr = function() {

							var detailURL = encodeURI(Leke.domain.payServerName + "/auth/seller/virtualCurrency/virtualCurrencyDetail.htm?schoolId="+this.lekeId+"&schoolName="+this.customerName+"&virtualAccountType=1");
							return '<a target="_blank" href="'+detailURL+'" class="aSchoolVirtual" data-id="'+this.lekeId+'">账户详情</a>'+
							'&nbsp;&nbsp;<a class="aSchoolcurrency" data-name="'+this.customerName+'" data-id="'+this.lekeId+'">充值</a>';
						}
						coursePageView.statusStrs = function() {

							var detailURL = encodeURI(Leke.domain.payServerName + "/auth/seller/virtualCurrency/accountedDetail.htm?schoolId="+this.lekeId+"&schoolName="+this.customerName);
							return '<a target="_blank" href="'+detailURL+'" >入账统计</a>';
						}
					    coursePageView.operate = function() {
							return '<a class="aDltSchool" data-id="'+this.customerId+'">解除</a>';
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

	//关联学校点击事件，验证市场绑定情况(170407取消，改成登录进行强制绑定)
	$('#relaSchool').click(function() {
		$.post(ctx + '/auth/scs/customer/checkMarket.htm', {}, function(data) {
			if (data.success) {
				if (data.datas.state) {
					window.location.href = '/auth/scs/customer/addSchoolCustomer.htm';
				}
				else {

					var url = Leke.domain.tutorServerName + "/auth/common/user/advance.htm";
					dialog.open({
						title : '',
						size : 'mini',
						tpl : '您尚未绑定市场归属<br />请在“个人中心-高级信息”绑定市场<br><a href="' + url + '" style="color: #1fb5ab;" target="_blank">>>去绑定</a>',
						onClose : function(data) {
							// window.location.href = "home.htm";
						},
						btns : [ {
							className : 'u-btn u-btn-nm u-btn-bg-turquoise',
							text : '我知道了',
							cb : function() {
								this.close(false);
							}
						} ]
					});
				}
			}
		});
	});
	
});
