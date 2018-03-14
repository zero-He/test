define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var utils = require('utils');
					
	function setEmpty(data){
		if(data.length == 0){
			$('#jEmptyData').show();
		}else{
			$('#jEmptyData').hide();
		}
	}
	
	var win = {
		init : function() {
			var $firstClass = $('.jtabClass li a').first();
			if($firstClass){
				$('#jClassId').val($firstClass.data('classid'));
			}
			this.bindEvent();
			this.loadData();
		},
		bindEvent : function() {
			$('#jSearch').click(function(){
				var jsonStr=$('#jDataList').val();
				if(jsonStr.length > 0){
					var json =JSON.parse(jsonStr);
					var stuName =$.trim($('#jStudentName').val());
					//根据 学生姓名 过滤 json.datas.dataList
					var newJson=[];
					if(stuName.length > 0){
						var delIndexs=[];
						for (var i = 0; i < json.length; i++) {
							var item =json[i];
							if (item.userName.indexOf(stuName) > -1) {
								newJson.push(item);
							}
							Handlebars.render("#jStudentsTemplate", newJson, "#jtbodyData");
							setEmpty(newJson);
						}
					}else{
						Handlebars.render("#jStudentsTemplate", json, "#jtbodyData");
						setEmpty(json);
					}
				}
			});
			
			$(document).on('click','.jtabClass li a',function(){
				$('.jtabClass li a').removeClass('active');
				$(this).addClass('active');
				$('#jClassId').val($(this).data('classid'));
				win.loadData();
			});
			$(document).on('click','.jDetailsStudent',function(){
				var href = $(this).data('href');
				var className =$.trim($('.jtabClass li a.active').first().text());
				href += '&className='+ encodeURIComponent(encodeURIComponent(className));
				window.open(href);
			});
		},
		loadData : function() {
			var data = {
					classId : $('#jClassId').val()
				};
			$.post(ctx + '/auth/classTeacher/diligent/classStuWorkDiligent.htm',data, function(json) {
				var dataList = json.datas.dataList;
				Handlebars.render("#jStudentsTemplate",dataList,"#jtbodyData");
				setEmpty(dataList)
				$('#jDataList').val(JSON.stringify(dataList));
			});
		}
	};

	win.init();

});
