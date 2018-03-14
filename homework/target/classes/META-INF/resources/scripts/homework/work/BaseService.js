define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require('utils');
	var Dialog = require('dialog');

	function doConfirm(message, btnOk, btnCancel) {
		var dfd = $.Deferred();
		Dialog.open({
			title : '确认信息',
			tpl : '<div class="confirm-msg">' + message + '</div>',
			size : 'mini',
			onClose : function(sure) {
				return dfd.resolve(sure);
			},
			btns : [{
				className : 'btn-green',
				text : btnOk,
				cb : function() {
					this.close(true);
				}
			}, {
				className : 'btn-gray',
				text : btnCancel,
				cb : function() {
					this.close(false);
				}
			}]
		});
		return dfd.promise();
	}
	
	var remarkTpl = '';
	remarkTpl += '<div id="jCommentary" class="p-remark-edit">';
	remarkTpl += '<div class="message">你确定要完成批阅吗？</div>';
	remarkTpl += '<div class="comment"><textarea placeholder="请输入试卷评语"></textarea></div>';
	remarkTpl += '<div>';
	remarkTpl += '<select class="u-select u-select-lg constant">';
	remarkTpl += '<option value="">---常用评语---</option>';
	remarkTpl += '<option value="做得很认真，完成的非常好！">做得很认真，完成的非常好！</option>';
	remarkTpl += '<option value="完成的不错，继续努力！">完成的不错，继续努力！</option>';
	remarkTpl += '<option value="做得不够认真，要加油！">做得不够认真，要加油！</option>';
	remarkTpl += '<option value="发挥不够理想，继续努力！">发挥不够理想，继续努力！</option>';
	remarkTpl += '<option value="做得很好，继续努力，不要骄傲！">做得很好，继续努力，不要骄傲！</option>';
	remarkTpl += '</select>';
	remarkTpl += '</div>';
	remarkTpl += '</div>';

	function doRemark() {
		var dfd = $.Deferred();
		Dialog.open({
			title : '完成批阅',
			tpl : remarkTpl,
			keepTpl : true,
			size : 'sm',
			btns : [{
				className : 'btn-ok',
				text : '确定',
				cb : function() {
					var text = $.trim($('.p-remark-edit textarea').val());
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
		$('.p-remark-edit textarea').val(Csts.soundFile || '');
		$('.p-remark-edit').on('change', '.constant', function() {
			$('.p-remark-edit textarea').val($(this).val());
		});
		return dfd.promise();
	}

	function goQueId(queId) {
		if (queId != null) {
			location.hash = '';
			location.hash = '#que_link_' + queId;
		}
	}

	var Helper = {
		doConfirm : doConfirm,
		doRemark : doRemark,
		goQueId : goQueId
	};

	module.exports = Helper;
});
