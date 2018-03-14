define(function(require, exports, module){
	var $ = require('jquery');
	var WdatePicker = require('date');
	var Dialog = require('dialog');
	var LekeDate = require('common/base/date');
	var Utils = require('utils');
	var Page = require('page');
	var I18n = require('homework/common/i18n');
	var Handlebars = require("common/handlebars");
	var CST = {
			list : ctx + '/auth/teacher/loadAssignLogList.htm',
			modify : ctx+ '/auth/teacher/assignLog/modify.htm',
			invalid : ctx+ '/auth/teacher/assignLog/invalid.htm'
	}
	var win = {
		init : function(){
			this.bindEvent();
			this.loadData();
		},
		bindEvent : function (){
			var _this = this;
			//作废
			$('#j_homework_body').on('click','.invalid',function(){
				var $el = this;
				Dialog.confirm('确定作废吗？').done(function(sure) {
					if(sure){
						var assignId = $($el).parents('.item').data('id');
						$.post(CST.invalid,{assignId:assignId},function(data){
							if(data.success){
								Utils.Notice.alert('操作成功');
								_this.page.loadData();
							}else{
								Utils.Notice.alert(data.message);
							}
						});
					}
				});
			});
			//修改截止日期
			$('#j_homework_body').on('click','.modify',function(){
				var $li = $(this).parents('.item');
				var assignId = $li.data('id');
				var closeTime = $li.data('close'); 
				var startTime = $li.data('start');
				AssignLogEdit.edit(assignId,startTime,closeTime);
			});
		},
		loadData : function (){
			this.page = Page.create({
				id : 'divPage',
				url : CST.list ,
				buttonId : 'j_btnSearch',
				formId : 'formPage',
				tipsId: 'j_emptyData',
				callback : function(datas) {
					var page = datas.page;
					Handlebars.render('#j_homework_templ',page,'#j_homework_body');
				}
			});
		}
	};
	var s_noedit = $.i18n.prop('homework.homework.message.noedit');
	var AssignLogEdit = {
			assignId : null,
			startTime : null,
			closeTime : null,
			edit : function(assignId, startTime, closeTime) {
				if (LekeDate.of(closeTime).toDate() <= new Date()) {
					Utils.Notice.alert(s_noedit);
					return;
				}

				var _this = this;
				_this.assignId = assignId;
				_this.startTime = startTime;
				_this.closeTime = closeTime;
				Dialog.open({
					title : '修改作业',
					tpl : $('#j_assignEditTpl').html(),
					size : 'nm',
					btns : [{
						className : 'btn-ok',
						text : '确定',
						cb : function() {
							if (_this.doComfirm()) {
								this.close(false);
							}
						}
					}, {
						className : 'btn-cancel',
						text : '取消',
						cb : function() {
							this.close(false);
						}
					}]
				});
				_this.bindEvent();
			},
			bindEvent : function() {
				var FMT = 'yyyy-MM-dd HH:mm:ss';
				$('#jEditStartTime').val(LekeDate.format(this.startTime, FMT));
				$('#jEditCloseTime').val(LekeDate.format(this.closeTime, FMT));

				if (LekeDate.of(this.startTime).toDate() > new Date()) {
					$('#jEditStartTime').addClass('Wdata').on('click', function() {
						WdatePicker({
							dateFmt : 'yyyy-MM-dd HH:mm:00',
							maxDate : $('#jEditCloseTime').val(),
							readOnly : true
						});
					});
				} else {
					$('#jEditStartTime').prop('disabled', true);
				}
				$('#jEditCloseTime').addClass('Wdata').on('click', function() {
					WdatePicker({
						dateFmt : 'yyyy-MM-dd HH:mm:00',
						minDate : $('#jEditStartTime').val(),
						readOnly : true
					});
				});
			},
			doComfirm : function() {
				var _this = this;
				var FMT = 'yyyy-MM-dd HH:mm:ss';
				var startTime = $('#jEditStartTime').val();
				if (startTime == '') {
					Utils.Notice.alert($.i18n.prop('homework.distribute.startTime.isnull'));
					return false;
				}
				var closeTime = $('#jEditCloseTime').val();
				if (closeTime == '') {
					Utils.Notice.alert($.i18n.prop('homework.distribute.closeTime.isnull'));
					return false;
				}
				var data = {
					assignId : this.assignId,
					closeTime : $('#jEditCloseTime').val(),
					oldCloseTime : LekeDate.format(_this.closeTime, FMT)
				};
				$.post(CST.modify, data, function(json) {
					if (json.success) {
						Utils.Notice.alert(json.message);
						Dialog.close();
						win.page.loadData();
					}
				}, 'json');

			}
		};
	
	win.init();
	
});