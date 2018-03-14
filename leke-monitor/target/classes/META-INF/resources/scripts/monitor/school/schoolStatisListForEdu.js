define(function(require,exports,module){
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	var WdatePicker = require('date');
	
	var SsList = {
			
			/**
			 * @description 初始化
			 * @param 
			 * @return 
			 */
			fInit : function() {
				this.fLoadSsList();
				this.fBindEvent();
			},
		
			/**
			 * @description 绑事件
			 * @param 
			 * @return 
			 */
			fBindEvent : function() {
				var _this = this;
				
				$('#jStartTime').click(function(){
					WdatePicker({
						dateFmt:'yyyy-MM-dd',
						maxDate:$('#jEndTime').val() || '%y-%M-%d',
						readOnly:true
					});
				});
				
				$('#jEndTime').click(function(){
					WdatePicker({
						dateFmt:'yyyy-MM-dd',
						minDate:$('#jStartTime').val(),
						maxDate:'%y-%M-%d',
						readOnly:true
					});
				});
				
				$('#jTchSort').click(function(){
					_this.fChangeSort($(this), 0);
					$('#jStuSort').find('i').attr('class', 'm-sort-default');
				});
				
				$('#jStuSort').click(function(){
					_this.fChangeSort($(this), 2);
					$('#jTchSort').find('i').attr('class', 'm-sort-default');
				});
			},
			
			fChangeSort : function($this, sort) {
				var $i = $this.find('i');
				var className = $i.attr('class');
				if (className == 'm-sort-asc') { 
					$i.attr('class', 'm-sort-desc');
					sort = sort + 1;
				} else {
					$i.attr('class', 'm-sort-asc');
				}
				$('#jSort').val(sort);
				$('#jSsListSelect').click();
			},
			
			/**
			 * @description 查询加载入住学校列表信息
			 * @param 
			 * @return 
			 */
			fLoadSsList : function() {
				Page.create({
					id : 'jDivPage',
					url : ctx + '/auth/educationDirector/school/loadSchoolStatisList.htm',
					buttonId : 'jSsListSelect',
					formId : 'jFormPage',
					callback : function(datas) {
						var sOutput = Mustache.render($('#jSsListTpl').val(), datas.page);
						$('#jSsListTbody').html(sOutput);
					}
				});
				
			}
	};

	SsList.fInit();
});
