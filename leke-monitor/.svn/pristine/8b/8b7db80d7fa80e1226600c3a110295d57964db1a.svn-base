define(function(require,exports,module){
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	
	var SuList = {
			
			fInit : function() {
				this.fLoadSchoolUserList();
			},
			
			fLoadSchoolUserList : function() {
				Page.create({
					id : 'jDivPage',
					url : ctx + '/auth/platformAdmin/school/loadSchoolUserListByTime.htm',
					buttonId : '',
					formId : 'jFormPage',
					callback : function(datas) {
						var sOutput = Mustache.render($('#jSuListTpl').val(), datas.page);
						$('#jSuListTbody').html(sOutput);
					}
				});
			}
	};

	SuList.fInit();
});
