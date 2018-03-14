/*define(function(require, exports, module) {
	var $ = require('jquery');
	
	var ResearcherIndex = {
			fInit : function() {
				this.fLoad();
			},
			
			fLoad : function() {
				$.ajax({
					type : 'post',
					url : ctx + '/auth/researcher/ajax/findResearcherIndex.htm',
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
						$("#unProofreadingAmount").text(inputStatis.unProofreadingAmount || 0);
						$("#unCheckAmount").text(inputStatis.unCheckAmount || 0);
					}
				});
			}
	};
	
	ResearcherIndex.fInit();
});
*/

define(function(require, exports, module) {
	var ko = require('knockout');
	require('./component/que-researcher-home-amount-list');
	
	ko.applyBindings();
});