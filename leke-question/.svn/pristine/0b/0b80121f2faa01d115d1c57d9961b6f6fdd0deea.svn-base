define(function(require, exports, module) {
	var $ = require('jquery');
	
	var InputerIndex = {
			fInit : function() {
				this.fLoad();
			},
			
			fLoad : function() {
				$.ajax({
					type : 'post',
					url : ctx + '/auth/inputer/ajax/findInputerIndex.htm',
					data : {},
					dataType : 'json',
					success : function(json) {
						var user = json.datas.user;
						$("#userName").text(user.userName);
						$("#roleName").text(user.currentRole.name);
						$("#userId").text(user.userId);
						if(user.photo != null) {
							$("#photo").attr("src", user.photo);
						} else {
							$("#photo").attr("src", ctx+'/images/question/userpic.png');
						}
						var inputStatis = json.datas.inputStatis;
						$("#inputAmount").text(inputStatis.inputAmount || 0);
					}
				});
			}
	};
	
	InputerIndex.fInit();
});
