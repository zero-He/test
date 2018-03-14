define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var Dialog = require('dialog');

	var Helper = {
		confirm : function(flag, message) {
			var dfd = $.Deferred();
			if (flag) {
				Dialog.confirm(message).done(function(sure) {
					if (sure) {
						dfd.resolve();
					} else {
						dfd.reject();
					}
				});
			} else {
				dfd.resolve();
			}
			return dfd.promise();
		},
		confirm2 : function(message,btnName) {
			var dfd = $.Deferred();
			Dialog.open({
				title: '确认信息',
				tpl: '<div class="confirm-msg">' + message + '</div>',
				size: 'mini',
				onClose: function(sure){
					/*if(sure == null || sure == undefined){
						return;
					}*/
//					if (sure) {
						return dfd.resolve(sure);
					/*} else {
						return dfd.reject();
					}*/
				},
				btns: [{
					className: 'btn-green',
					text: btnName.ok,
					cb: function() {
						this.close(true);
					}
				}, {
					className: 'btn-gray',
					text: btnName.cancel,
					cb: function() {
						this.close(false);
					}
				}]
			});
			return dfd.promise();
		},
		jumpQuestion:function(firstUnDoQueId){
			if(firstUnDoQueId != null){
				location.hash = '';
				location.hash='#que_link_'+ firstUnDoQueId;
			}
		},
		commentary : function() {
			var dfd = $.Deferred();
			Dialog.open({
				title : '完成批阅',
				tpl : $('#jCommentary'),
				keepTpl : true,
				size : 'sm',
				btns : [{
					className : 'btn-ok',
					text : '确定',
					cb : function() {
						var text = $.trim($('#jCommentary textarea').val());
						if (text.length > 256) {
							Utils.Notice.alert('作业评语最多256个字符');
							return;
						}
						dfd.resolve(text);
						this.close(false);
					}
				}, {
					className : 'btn-cancel',
					text : '取消',
					cb : function() {
						dfd.reject();
						this.close(false);
					}
				}]
			});
			$('#jCommentary').on('change', '.constant', function() {
				$('#jCommentary textarea').val($(this).val());
			});
			return dfd.promise();
		}
	};

	module.exports = Helper;
});
