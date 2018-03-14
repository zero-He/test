define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Utils = require('utils');
	var Dialog = require('dialog');
	var KindEditor = require('editor');
	var WdatePicker = require('date');
	var Handlebars = require("common/handlebars");
	var PageSort = require("scs/common/pagesort");

	var Pages = {
		init : function() {
			$('#jStartTime').click(function() {
				WdatePicker({
					maxDate : $('#jEndTime').val(),
					dateFmt : 'yyyy-MM-dd',
					readOnly : true
				});
			});
			$('#jEndTime').click(function() {
				WdatePicker({
					minDate : $('#jStartTime').val(),
					dateFmt : 'yyyy-MM-dd',
					readOnly : true
				});
			});
			Page.create({
				id : 'jPageFoot',
				url : 'list.htm',
				pageSize : 10,
				buttonId : 'jPageSearch',
				formId : 'jPageForm',
				callback : function(datas) {
					Handlebars.render('#jPageTpl', datas.page, '#jPageBody');
				}
			});
			PageSort.init({
				headId : '#jPageHead',
				buttonId : '#jPageSearch',
				orderId : '#jPageOrder',
				sortId : '#jPageSort'
			});

			$('#jPageBody').on('click', '.j-edit', function() {
				var editor, $this = $(this), customerId = $this.data('id'), remark = $this.data('remark');
				var $text = $('<textarea></textarea>').val(remark);
				Dialog.open({
					title : '备注',
					tpl : $text,
					size : 'sm',
					btns : [{
						className : 'btn-ok',
						text : '确定',
						cb : function() {
							editor.sync();
							Pages.updateRemark($this, $text, customerId, $text.val());
						}
					}]
				});
				editor = KindEditor.create($text, {
					pasteType : 1,
					width : '99%',
					height : '200px',
					resizeType : 0,
					items : ['forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'removeformat']
				});
			}).on('mouseover', '.jRemarks', function() {
				if ($(this).find('.con').text() != '') {
					$(this).find('.m-tippop').show();
				}
			}).on('mouseout', '.jRemarks', function() {
				$(this).find('.m-tippop').hide();
			});
		},
		updateRemark : function($this, $text, customerId, remark) {
			$.post('updateRemark.htm', {
				customerId : customerId,
				remark : remark
			}, function(json) {
				if (json.success) {
					$this.data('remark', remark).siblings('.con').html(remark);
					$this.siblings('.m-tippop').find('.msg').html(remark);
					Dialog.close();
					Utils.Notice.alert('备注修改成功');
				}
			})
		}
	};

	Pages.init();

});
