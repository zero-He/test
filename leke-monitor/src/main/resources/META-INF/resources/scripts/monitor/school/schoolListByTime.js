define(function(require,exports,module){
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	
	var SchoolList = {
			
			fInit : function() {
				this.fLoadSchoolList();
				//this.fBindEvent();
			},
			
			fLoadSchoolList : function() {
				Page.create({
					id : 'jDivPage',
					url : ctx + '/auth/platformAdmin/school/loadSchoolListByTime.htm',
					buttonId : '',
					formId : 'jFormPage',
					callback : function(datas) {
						var sOutput = Mustache.render($('#jSchoolListTpl').val(), datas.page);
						$('#jSchoolListTbody').html(sOutput);
					}
				});
			}
	};

	SchoolList.fInit();
});
