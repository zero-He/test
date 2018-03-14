define(function(require, exports, module){
	var $ = require('jquery');
	var Calendar = require('common/ui/ui-calendar/ui-calendar.js');
	var Dialog = require('dialog');
	var Utils = require('utils');
	
	 // 用于存储数据的JSON对象，格式 {"specialDayOff": [xxxx.x.x, xxxx.x.x ...], "specialDayWork": [xxxx.x.x, xxxx.x.x ...]}
    var storage = {"specialDayOff": [], "specialDayWork": []};
    
	var settingForTheHoliday = {
		init: function () {
			this.createCalendar();
			this.fBindEvent();
		},
		fBindEvent: function() {
			
	    	//提交保存
			$('#jSave').click(function(){
				var specialDayOffStr = '';
				var specialDayWorkStr = '';
	    		for(var i = 0; i< storage.specialDayOff.length ; i++){
	    			specialDayOffStr = specialDayOffStr +  storage.specialDayOff[i] + ',';
		    	}
		    	
		    	for(var i = 0; i< storage.specialDayWork.length ; i++){
		    		specialDayWorkStr = specialDayWorkStr + storage.specialDayWork[i] + ',';
		    	}
		    	
				$.ajax({
					cache: true,
		    		type: "POST",
		    		url: ctx + '/auth/scs/specialDate/addSpecialDate.htm',
		    		data: {
		    			'specialDayOff' : specialDayOffStr,
		    			'specialDayWork' : specialDayWorkStr
		    		},
		    		async: false,
		    		success: function(data){
		    			 Utils.Notice.alert("修改成功！");
		    		}
		    	});
			});
		},
		createCalendar: function () {
			var $this = this;
			//加载年份下拉框
		    
			var calendarElement = document.getElementById('jCalendar');
			
			var currentTime = new Date(currentSystemTime) || new Date();
			
			var year=currentTime.getFullYear(); 
			$("#selMonth option[value="+(currentTime.getMonth() + 1)+"] ").prop("selected",true);
//			$(".j-curpanelyear").append($("<option value="+year+">"+year+"</option><option value="+(year-1)+">"+(year-1)+"</option><option value="+(year-2)+">"+(year-2)+"</option>"));   
			for(var i = -10; i < 10; i++){
				$(".j-curpanelyear").append($("<option value="+(year+i)+">"+(year+i)+"</option>"));
			}
			$("#selYear option[value="+year+"] ").prop("selected",true);
		   
		   
		    // 创建日历
			var calendar = new Calendar({
				wrapElement: calendarElement, 
				year: currentTime.getFullYear(), 
				month: currentTime.getMonth() + 1,
				buildCompleteCallback: buildCompleteCallback,
				reBuildCompleteCallback: reBuildCompleteCallback,
				tdEventCallback: tdListener
			});
			
			// 自定义年月的方法
			$('.j-curpanelmonth').change(function(){
				var year = $('.j-curpanelyear').val();
				var month = $('.j-curpanelmonth').val()
				calendar.reBuildCalendar({year: year,month:month});
			});
			$('.j-curpanelyear').change(function(){
				var year = $('.j-curpanelyear').val();
				var month = $('.j-curpanelmonth').val()
				calendar.reBuildCalendar({year: year,month:month});
			});
			
			//返回当前月
		    $('#jReturnNow').click(function(){
				var date=new Date(); 
				calendar.reBuildCalendar({year: date.getFullYear(),month:date.getMonth() + 1});
		    });
			
			// 所有的回调
			
			// 填充
			function fill(storage){
		 		var tdElement = document.querySelector('.m-calendar').querySelectorAll('td');
				// 当前日历面板的年月
				var curPanelYear = document.querySelector('.curpanelyear').innerHTML;
				var curPanelMonth = document.querySelector('.curpanelmonth').innerHTML;
				
				// 遍历mark的日期
				// arrMark格式 [[xxxx, x, x], [xxxx, x, x]...]
				var arrMark = [];  
				for (var attr in storage) {
					for (var i = 0, storageLength = storage[attr].length; i < storageLength; i ++) {
						arrMark.push(storage[attr][i].split('.'));  
					} 
				}

				// 周六、日，默认放假（高亮）
				$('td[data-isweekend]').each(function (idx, item) {
					if (item.innerHTML) {
						$(this).addClass('active');
					}
				});
				
				// 回填mark的日期
				for (var k = 0, markLength = arrMark.length; k < markLength; k ++) {
					var markYear = arrMark[k][0];
					var markMonth = arrMark[k][1];
					var markDay = arrMark[k][2];
					
					if (markYear == curPanelYear && parseInt(markMonth, 10) == curPanelMonth) {
						for (var j = 0, tdLength = tdElement.length; j < tdLength; j ++) {
							if (tdElement[j].innerHTML == parseInt(markDay, 10)) {
								
								if(tdElement[j].getAttribute('data-isWeekend')){
									$(tdElement[j]).removeClass('active');
								}else{
									tdElement[j].className += ' active';
								}
							}
						}
					} 
				}
				
				
			}

		    // 初始化完成后的回调(异步数据合并到本地中)
		    function buildCompleteCallback() {
		    	// ajax 请求
		    	$.ajax({
		    		type: "POST",
		    		url: ctx + '/auth/scs/specialDate/specialDateSettingData.htm',
		    		async: false,
		    		success: function(data){
		    			//反填数据
		    			fill(data.datas.specialDateList);
		    			if(storage.specialDayOff.length == 0 && storage.specialDayWork.length == 0){
		    				storage = data.datas.specialDateList;
		    			}
		    		}
		    	});
		    	
		    }

		    // 重绘后的回调(静态数据回填)
		 	function reBuildCompleteCallback() {
		 		//切换月份 修改 头部的下拉框中的值
		 		if($('#selYear option:selected').val() != $('.curpanelyear').text()){
//		 			$(".j-curpanelyear").append("<option value="+$('.curpanelyear').text()+" selected>"+$('.curpanelyear').text()+"</option>");    
		 			$("#selYear option[value="+$('.curpanelyear').text()+"] ").prop("selected",true);
		 			
		 		}
		 		$("#selMonth option[value="+$('.curpanelmonth').text()+"] ").prop("selected",true);
		 		
				fill(storage);
			}

			// td点击事件的回调(存储日期)
		    function tdListener(targetElement) {
		    	var curPanelYear = document.querySelector('.c').querySelectorAll('span')[0].innerHTML; 
		    	var curPanelMonth = document.querySelector('.c').querySelectorAll('span')[1].innerHTML; 
		    	var curPanelSelectDate = targetElement.innerHTML;
		    	// 当前选中的日期
		    	if(curPanelMonth < 10){
		    		curPanelMonth = '0' + curPanelMonth;
		    	}
		    	if(curPanelSelectDate < 10){
		    		curPanelSelectDate = '0' + curPanelSelectDate;
		    	}
		    	var time = curPanelYear +'.'+ curPanelMonth + '.' + curPanelSelectDate;

		    	if (curPanelSelectDate) {
		    		//非周末日期选中操作
		    		if (targetElement.className.search('active') < 0 && !targetElement.getAttribute('data-isWeekend')) {
		    			targetElement.className += ' active';
		    			storage.specialDayOff.push(time);// 选中日期存入storage
		    			
		    		//默认选中的周末操作
		    		}else if($(targetElement).hasClass('active') && targetElement.getAttribute('data-isWeekend')){
		    			$(targetElement).removeClass('active');
		    			storage.specialDayWork.push(time);
		    			
		    		//其他
		    		} else {
		    			var resultClassAfterRemoveTargetClass = targetElement.className.replace('active', '');
		    			targetElement.className = resultClassAfterRemoveTargetClass;

						// 删除数组某一项
					    var remove = function (arr, value) { 
					        var length = arr.length;

					        while(length--){
					            if(value === arr[length]){
					                arr.splice(length, 1);
					            }
					        }

					        return arr;
					    };

		    			// 删除已选中日期
		    			if (targetElement.getAttribute('data-isWeekend')) {
		    				targetElement.className += ' active';
		    				remove(storage.specialDayWork, time);
		    				
		    			} else {
		    				remove(storage.specialDayOff, time);
		    			}
		    		}
		    	}
		    }
		}
	};
	
	settingForTheHoliday.init();

});