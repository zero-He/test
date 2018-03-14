define(function(require, exports, module) {
	var $ = require('jquery');
	var Page = require('page');
	var Utils = require('utils');
	var Mustache = require('mustache');
	require('common/ui/ui-autocomplete/ui-autocomplete');
	require('jquery-cookie');
	
	
	var School = {
		init : function() {
			/**
			 * 学校下拉框查询
			 */
			$('.j-school-select').autocomplete({
				url: Leke.ctx + '/auth/scs/school/queryOwnSchoolName.htm',
				nameKey: 'schoolName',
				itemsFn: function(datas) {
					return datas.items;
				},
				onChange: function(item) {
					$('#schoolId').val('');
					if(item != null){
						$('#schoolId').val(item.schoolId);
					}
					/*$.cookie('l_s', $('#schoolId').val(), {
						domain: 'leke.cn',
						path: '/'
					}); */
				}
			});
			
			/**
			 * 为空的话填入cookie中的值
			 */
			/*var schoolId=$.cookie('l_s'); 
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
				
			}*/
			
//			this.bindEvents();
			this.fLoadData();
		},
		fLoadData : function() {
			Page.create({
				id : 'divPage',
				url : ctx + '/auth/scs/customer/schoolListDate.htm',
				pageSize : 10,
				buttonId : 'btn_search',
				formId : 'jPageForm',
				callback : function(datas) {
					//数据处理，渲染表格
					$('#tbodySh').html("");
					var urlType = $('#urlType').val();
					var coursePageView = datas.page;
					if (coursePageView.totalSize != 0) {
						
						coursePageView.operate = function() {
							if(urlType == 4){
								return '<a href="'+$('#payServerName').val()+'/auth/seller/order/orderListForSeller.htm?schoolId='+this.lekeId+'" class="aDltSchool" data-id="'+this.lekeId+'">查看订单</a>';
							}else{
								return '<a href="'+Leke.domain.courseServerName+'/auth/provost/course/courseList.htm?schoolId='+this.lekeId+'" class="aDltSchool" data-id="'+this.lekeId+'">课程管理</a>';
							}
						}
						
						var output = Mustache.render($("#schoolList").val(), coursePageView);
						$("#tbodySh").html(output);
						
					} else {
						$("#tbodySh").html("");
					}
				}
			});
		},
		bindEvents : function() {
			var urlType = $('#urlType').val();
			var tutor = $('#tutorServerName').val();
			var pay = $('#payServerName').val();
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
				if(urlType == 1){
					//课程发布
					newUrl = tutor+'/auth/provost/question/course/curriculum/courseList.htm?schoolId='+$('#schoolId').val();
				}else if(urlType == 2){
					//排课管理
					newUrl = tutor +'/auth/provost/question/course/arrange/arrangeCourseList.htm?schoolId='+$('#schoolId').val();
				}else if(urlType == 3){
					//上课名单
					newUrl = tutor +'/auth/provost/question/course/roster/courseSetListForUser.htm?schoolId='+$('#schoolId').val();
				}else{
					//客户经理 学校订单
					newUrl = pay +'/auth/seller/order/orderListForSeller.htm?schoolId='+$('#schoolId').val();
				}
				
				window.location.href = newUrl;
			});
		}
	};
	
	School.init();
	
})

