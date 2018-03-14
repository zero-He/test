/*define(function(require, exports, module) {
	var $ = require('jquery');
	
	var AdminIndex = {
			fInit : function() {
				this.fLoad();
			},
			
			fLoad : function() {
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/ajax/findAdminIndex.htm',
					data : {},
					dataType : 'json',
					success : function(json) {
						var user = json.datas.user;
						$("#userName").text(json.datas.userName);
						$("#roleName").text(user.currentRole.name);
						$("#userId").text(user.loginName);
						var imgSrc = user.photo || (window.ctx + '/images/question/userpic.png');
						$("#photo").attr("src", imgSrc);
						var inputStatis = json.datas.inputStatis;
						$("#draftAmount").text(inputStatis.draftAmount || 0);
						$("#formalAmount").text(inputStatis.formalAmount || 0);
					}
				});
			}
	};
	
	AdminIndex.fInit();
});
*/

define(function(require, exports, module) {
	var ko = require('knockout');
	require('./component/que-home-amount-list');
	
	ko.applyBindings();
});