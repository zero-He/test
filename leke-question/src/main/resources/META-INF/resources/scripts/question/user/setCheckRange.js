define(function(require, exports, module) {
	var $ = require('jquery');
	var Mustache = require('mustache');
	var utils = require('utils');
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	var SetCheckRange = {
			fInit : function() {
				var self = this;
				self.$g = $('#gradeId');
				self.$s = $('#subjectId');
				var $gs = $('#gradeSubject');
				$gs.stgGrdSbjSelect({
					type: 'grd_sbj',
					onChange: function(item) {
						self.$g.val(item.gradeId);
						self.$s.val(item.subjectId);
					}
				});
				this.fBindEvent();
				this.loadList();
			},
			
			fBindEvent:function() {
				var self = this;
				$('#bSearch').on('click', function(){
					self.loadList();
				});
			},
			loadList:function() {
				var self = this;
				var userName = $('#userName').val();
				var gradeId = self.$g.val();
				var subjectId = self.$s.val();
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/user/ajax/findQuestionCheckerList.htm',
					data : {
						userName : userName,
						gradeId : gradeId,
						subjectId : subjectId
					},
					dataType : 'json',
					success : function(json) {
						var listjson = json.datas;
						
						var output = Mustache.render($('#listContext_tpl').val(),listjson);
						$('#listContext').html(output);
						
					}
				});
			}
	};
	
	SetCheckRange.fInit();
});
