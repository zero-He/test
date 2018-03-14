define(function(require,exports,module){
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	var Dialog = require('dialog');
	var utils = require('utils');
	var ThSelect = require('common/ui/ui-thselect/ui-thselect');
	
	var UserListForQuestion = {
		fInit:function(){
			this.fBindEvent();
			this.loadList();
		},
		
		fBindEvent:function() {
			var _this = this;
			$('#bAdd').on('click', function(){
				_this.doAdd();
			});
			$('#userListContext').on('click', '.aReset', $.proxy(_this, 'resetPassword'));
			$('#userListContext').on('click', '.aUpdate', $.proxy(_this, 'preUpdateUser'));
			$('#userListContext').on('click', '.aUnfreezeuser', $.proxy(_this, 'unfreezeUser'));
			$('#userListContext').on('click', '.aFreezeUser', $.proxy(_this, 'freezeUser'));
			
			var roles = [{'roleId':400, 'roleName':'题库录入员'},
			             {'roleId':401, 'roleName':'题库审核员'},
			             {'roleId':402, 'roleName':'教研员'},
			             {'roleId':403, 'roleName':'教研主管（题库管理员）'}];
			
			var _roleSelect = new ThSelect({
				attach: $('.j-userlist-roleselect'), // 父容器
				optionItems: roles, // 可选项数组
				idKey: 'roleId', // ID 键
				nameKey: 'roleName', // 名称键
				caption: '所有角色', // 默认标题
				onChange: function(roleId, role) { // 值变更回调，一般是给表单设值并触发查询
					$('#currentRoleId').val(roleId);
					$('#iUserList').trigger('click');
				}
			});
			
		},
		
		//获取列表信息
		loadList:function(){
			Page.create({
				id: 'divPage',
				url:ctx+'tutor.leke.cn/auth/user/ajax/findUserListForQuestion.htm',
				curPage:1,
				pageSize:10,
				pageCount:10,//分页栏上显示的分页数
				buttonId: 'iUserList',
				formId:'form',
				callback:function(datas){
					var pvUser = datas.page;
					
					var frozenRender = function(){
						if(!this.isEnabled){
							return '<a href="javascript:void(0)" data-id="' + this.id + '" class="link aUnfreezeuser">启用</a>';
						}else{
							return '<a href="javascript:void(0)" data-id="' + this.id + '" class="link aFreezeUser">禁用</a>';
						}
					};
					var rolesStr = function() {
						if(this.roleListStr.length > 25) {
							return this.roleListStr.substr(0, 25) + '...';
						} else {
							return this.roleListStr;
						}
					}
					
					pvUser.frozenRender=frozenRender;
					pvUser.rolesStr = rolesStr;
					var output = Mustache.render($('#userListContext_tpl').val(),pvUser);
					$('#userListContext').html(output);
				}
			});
		},
		
		resetPassword:function(evt) {
			if(confirm('确定要重置该用户密码吗？')) {
				var _this = this;
				var userId = $(evt.target).data('id');
				$.ajax({
					type : 'post',
					url : ctx + '/auth/schoolAdmin/user/resetPassword.htm',
					data : {
						userId : userId
					},
					dataType : 'json',
					async : true,
					success : function(json) {
						utils.Notice.alert(json.datas.resultMessage);
					}
				});
			}
		},
		
		freezeUser:function(evt) {
			if(confirm('确定要禁用该用户吗？')) {
				var _this = this, $a = $(evt.target);
				var userId = $a.data('id');
				$.ajax({
					type : 'post',
					url : ctx + '/auth/schoolAdmin/user/freezeUser.htm',
					data : {
						userId : userId
					},
					dataType : 'json',
					async : true,
					success : function(json) {
						$a.closest('td').prev().text('禁用');
						$a.closest('td').append('<a href="javascript:void(0)" data-id="' + userId + '" class="link aUnfreezeuser">启用</a>');
						$a.remove();
						utils.Notice.alert(json.datas.resultMessage);
					}
				});
			}
		},
		
		unfreezeUser:function(evt) {
			if(confirm('确定要启用该用户吗？')) {
				var _this = this, $a = $(evt.target);
				var userId = $a.data('id');
				$.ajax({
					type : 'post',
					url : '/auth/schoolAdmin/user/unfreezeUser.htm',
					data : {
						userId : userId
					},
					dataType : 'json',
					async : true,
					success : function(json) {
						$a.closest('td').prev().text('启用');
						$a.closest('td').append('<a href="javascript:void(0)" data-id="' + userId + '" class="link aFreezeUser"">禁用</a>');
						$a.remove();
						alert(json.datas.resultMessage);
					}
				});
			}
		},
		
		doAdd:function() {
			Dialog.open({
				title : '新增用户',
				url : ctx + '/auth/user/preAddUserForQuestion.htm',
				size : 'nm',
				id : 'addUserForQuestion',
				onClose : function(message) {
					if (message != undefined && message != '') {
						utils.Notice.alert(message);
					}
				}
			});
		},
		
		preUpdateUser:function(evt) {
			var _this = this;
			var userId = $(evt.target).data('id');
			Dialog.open({
				title : '修改用户',
				url : ctx + '/auth/user/preUpdateUserForQuestion.htm?userId='+userId,
				size : 'nm',
				id : 'updateUserForQuestion',
				onClose : function(message) {
					if (message != undefined && message != '') {
						utils.Notice.alert(message);
					}
				}
			});
		}
	};

	UserListForQuestion.fInit();
});

