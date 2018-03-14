define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Utils = require('utils');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	require('jquery-cookie');
	
	
	var School = {
		init : function() {
			/**
			 * 学校下拉框查询
			 */
			$('.j-school-select').autocomplete({
				url: Leke.ctx + '/auth/scs/school/querySchoolName.htm',
				nameKey: 'schoolName',
				onChange: function(item) {
					$('#schoolId').val('');
					if(item != null){
						$('#schoolId').val(item.schoolId);
					}
					$.cookie('l_s', $('#schoolId').val(),{
						domain: 'leke.cn',
						path: '/'
					}); 
				}
			});
			
			/**
			 * 为空的话填入cookie中的值
			 */
			var schoolId=$.cookie('l_s'); 
			if(schoolId != null && schoolId != '' && $('#schoolId').val() == ''){
				$('#schoolId').val(schoolId);
				$.ajax({
	                cache: true,
	                type: "POST",
	                url: ctx + '/auth/scs/school/getSchoolNameFromCookie.htm',
	                data:{schoolId : schoolId},
	                async: false,
	                success: function(data) {
	                	$('#schoolName').val(data);
	                }
			    });
			}
			
			this.bindEvents();
		},
		
		bindEvents : function() {
			var urlType = $('#urlType').val();
			var tutor = $('#tutorServerName').val();
			var newUrl = "";
			
			$('#btn_search').click(function(){
				var schoolName = $('#schoolName').val();
				var schoolId = $('#schoolId').val();
				if(schoolName == '' && schoolId == ''){
					Utils.Notice.alert('请先选择一个学校');
					return;
				}
				if(schoolId == ''){
					Utils.Notice.alert('学校不存在，或选择有误哦');
					return;
				}
				window.location.href = Leke.domain.courseServerName+'/auth/provost/course/courseList.htm?schoolId='+$('#schoolId').val();
			});
		}
	};
	
	School.init();
	
})

