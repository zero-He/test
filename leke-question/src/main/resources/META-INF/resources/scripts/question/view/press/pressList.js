define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var utils = require('utils');
	var dialog = require('dialog');
	var http = require('common/base/http');
	
	var PressList = {
			fInit : function() {
				this.fBindEvent();
				this.loadList();
			},
			
			fBindEvent:function() {
				var _this = this;
				$('#bPreAdd').on('click', function(){
					_this.preAddPress();
				});
				var $cont = $('#pressListContext');
				$cont.on('click', '.aEdit', $.proxy(_this, 'editPress'));
				$cont.on('click', '.aUpdate', $.proxy(_this, 'updatePress'));
				$cont.on('click', '.aDelete', $.proxy(_this, 'deletePress'));
				$cont.on('click', '.aAdd', $.proxy(_this, 'addPress'));
			},
			
			preAddPress:function() {
				var output = Mustache.render($('#pressInput_tpl').val());
				$('#pressListContext').prepend(output);
			},

			addPress: function(evt) {
				var _this = this;
				var $a = $(evt.target);
				var $row = $a.closest('tr');
				var pressName = $row.find('[name=pressName]').val();
				var nickName = $row.find('[name=nickName]').val();
				if(!pressName || '' === pressName){
					utils.Notice.alert("请输入教材版本名称");
					return false;
				}
				http.post(Leke.ctx + "/auth/admin/press/addPress.htm", {
					pressName : pressName,
					nickName : nickName
				}).then(function(datas) {
					var pressId = datas.pressId;
					$row.find('[name=pressName]').val(pressName).addClass('u-bd-none').removeClass('u-bd-color-green').prop('disabled', true);
					$row.find('[name=nickName]').val(nickName).addClass('u-bd-none').removeClass('u-bd-color-green').prop("disabled", true);
					$row.find('[name=aAdd]').removeClass('link aAdd').addClass('link aEdit').text('编辑').data('pressId', pressId);
					$row.find('[name=aDelete]').data('pressId', pressId);
					utils.Notice.alert(datas.resultMessage || '教材版本添加成功');
				}, function(msg) {
					utils.Notice.alert(msg || '教材版本添加失败');
				});
			},
			
			editPress: function(evt) {
				var _this = this, $a = $(evt.target);
				var $row = $a.closest('tr');
				var pressId = $a.data('pressId');
				$row.find('input').prop("disabled", false);
				$row.find('[name=pressName]').removeClass('u-bd-none').addClass('u-bd-color-green');
				$row.find('[name=nickName]').removeClass('u-bd-none').addClass('u-bd-color-green');
				$a.closest('td').prepend('<a href="javascript:void(0)" data-press-id="'+pressId+'"name="aUpdate" class="link aUpdate">保存</a>');
				$a.remove();
			},
			
			updatePress: function(evt) {
				var _this = this, $a = $(evt.target);
				var $row = $a.closest('tr');
				dialog.confirm('确定要修改该教材版本吗？').done(function(sure){
					if(sure) {
						var pressId = $a.data('pressId');
						var pressName = $row.find('[name=pressName]').val();
						var nickName = $row.find('[name=nickName]').val();
						http.post(Leke.ctx + "/auth/admin/press/updatePress.htm", {
							pressId : pressId,
							pressName : pressName,
							nickName : nickName
						}).then(function(datas) {
							$row.find('[name=pressName]').val(pressName).removeClass('u-bd-color-green').addClass('u-bd-none').prop('disabled', true);
							$row.find('[name=nickName]').val(nickName).removeClass('u-bd-color-green').addClass('u-bd-none').prop("disabled", true);
							$row.find('[name=aUpdate]').attr('name','aEdit');
							$row.find('[name=aEdit]').attr('class','link aEdit');
							$row.find('[name=aEdit]').text('编辑');
							utils.Notice.alert(datas.resultMessage || '教材版本添加成功');
						}, function(msg) {
							utils.Notice.alert(msg || '教材版本更新失败');
						});
					}
				});
			},
			deletePress: function(evt) {
				var $a = $(evt.target);
				var $row = $a.closest('tr');
				if(confirm('确定要删除该教材版本吗？')){
					var _this = this;
					var pressId = $(evt.target).data('pressId');
					http.post(Leke.ctx + "/auth/admin/press/deletePress.htm", {
						pressId : pressId
					}).then(function(datas) {
						$row.remove();
						utils.Notice.alert(datas.resultMessage || '教材版本删除成功');
					}, function(msg) {
						utils.Notice.alert(msg || '教材版本删除失败');
					});
				}
			},
			loadList:function() {
				http.post(Leke.ctx + "/auth/admin/press/loadPressList.htm", {}).then(function(datas) {
					var output = Mustache.render($('#pressContext_tpl').val(), datas);
					$('#pressListContext').html(output);
				}, function(msg) {
					utils.Notice.alert(msg || '教材版本数据加载失败');
				});
			}
	};
	
	PressList.fInit();
});
