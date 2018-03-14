define(function(require,exports,module){
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Page = require('page');
	var WdatePicker = require('date');
	var Utils = require('utils');
	var usreServerName = $('#jUserServerName').val();
	
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
				
				_this.fSchoolSelect();
				
				$('#jSchoolSelect').click(function(){
					var areas = $('#areas').val();
					if(!areas) {
						Utils.Notice.alert('请先选择地区');
						return false;
					}
					var bIsChanged = $('#jIsChanged').val();
					if (bIsChanged == "true") {
						$.getJSON(usreServerName + '/auth/common/school/findSchoolListByAreas.htm?areas=' + areas + '&jsoncallback=?', function(data){//验证是否已登入
							if (data.success) {
								var sOutput = Mustache.render($('#jSListTpl').val(), data.datas);
								$('#jSchoolSelect').html(sOutput);
								$('#jIsChanged').val(false);
							}
						});
					}
				});
				
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
			
			fSchoolSelect : function() {
	        	var _this = this;
	            var $wrap = $('.j-school-select'),
	                $btn = $wrap.find('i'),
	                $options = $wrap.find('ul'),
	                $input = $wrap.find('input');

	            $input.on({
	                'focus': function(){
						_this.fLoadSchoolList($options, $btn);
	                },
	                'click': function(e){
	                    e.stopPropagation();
	                }
	            });
	            
	            $btn.on({
	                'click': function(e){
	                	_this.fLoadSchoolList($options, $btn);
	                	e.stopPropagation();
	                }
	            });
	            
	            $(document).on({
	                'click': function(e){
	                	$btn.removeClass('arrow-up');
	                    $options.slideUp();
	                }
	            });
	            
	            $('.j-school-select').on('click', 'li', function() {
	            	$input.val($(this).text());
                	$('#jSchoolId').val($(this).data('id'));
				});
	        },
			
			fLoadSchoolList : function($options,$btn) {
	        	var _this =this;
	        	var areas = $('#areas').val();
				if(!areas) {
					Utils.Notice.alert('请先选择地区');
					return false;
				}
				var bIsChanged = $('#jIsChanged').val();
				if (bIsChanged == "true") {
					$.getJSON(usreServerName + '/auth/common/school/findSchoolListByAreas.htm?areas=' + areas + '&jsoncallback=?', function(data){//验证是否已登入
						if (data.success) {
							var sOutput = Mustache.render($('#jSListTpl').val(), data.datas);
							$options.html(sOutput);
							$('#jIsChanged').val(false);
							$options.slideDown();
							$btn.addClass('arrow-up');
						}
					});
				} else {
					var index = $btn.attr('class').indexOf('arrow-up');
					if (index != -1) {
						$options.slideUp();
						$btn.removeClass('arrow-up');
					} else {
						$options.slideDown();
						$btn.addClass('arrow-up');
					}
				}
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
					url : ctx + '/auth/platformAdmin/school/loadSchoolStatisList.htm',
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
