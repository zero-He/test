define(function(require,exports,module){
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	var usersOnline = {
			init : function() {
				this.loadSchoolList();
				this.bindEvents();
			},
			bindEvents:function(){
				$('#numberType').on('change',function(){
					var _this=this;
					if($(_this).val()=='0'){
						$('#loginName').show();
						$('#loginName').val($('#oldLoginName').val());
						$('#oldLoginName').hide();
						$('#oldLoginName').val('');
					}else{
						$('#oldLoginName').show();
						$('#oldLoginName').val($('#loginName').val());
						$('#loginName').hide();
						$('#loginName').val('');
					}
				});
			},
			loadSchoolList : function() {
				Page.create({
					id : 'jDivPage',
					url : ctx + '/auth/technicalSupport/online/users.htm',
					pageSize:100,
					buttonId : '',
					formId : 'jFormPage',
					callback : function(datas) {
						$('#total').html(datas.total);
						$('#count').html(datas.count);
						var sOutput = Mustache.render($('#jSuListTpl').val(), datas.page);
						$('#jSuListTbody').html(sOutput);
					}
				});
			}
	};
	usersOnline.init();
});

