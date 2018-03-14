define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var utils = require('utils');
	Handlebars.registerHelper({
		ordNum : function(v){
			return v + 1;
		}
	});
	var win = {
		init : function() {
			this.bindEvent();
			this.loadData();
		},
		bindEvent : function() {
			$('#jExportData').click(function(){
				$('#j_fileName').val($('#jClassId').find("option:selected").text());
				var form = document.forms[0];
				var params= $(form).serialize();
				var url=Leke.domain.diagServerName + '/auth/teacher/homework/exportHwGrade.htm?'+ params;
				location.href= url;
			});
			$('.jtabItem').click(function(){
				$('.jtabItem').removeClass('cur');
				$(this).addClass('cur');
				if($(this).data('id') == 1){
					$('#jClassId').html($('.jClasses').html());
					$('.jClassLabel').text('班级');
				}else{
					$('.jClassLabel').text('课程');
					$('#jClassId').html($('.jCourseSets').html());
				}
				win.loadData();
			});
			$('#jSearch').click(function(){
				if($('#jClassId').length == 0 || $('#jClassId').val() == ''){
					return;
				}else{
					win.loadData();
				}
			});
		},
		loadData:function(){
			var url = ctx + '/auth/teacher/achievement/findStudentList.htm';
			var classId = $('#jClassId').val();
			var type = $('#jULid .cur').data('id');
			var dataList = null;
			if(classId == null || classId == ''){ 
				var txtMsg ='没有您的相关课程';
				if(type == 1){
					txtMsg = '您的任教班级尚未设置';
				}
				$('#jClassId').empty().append('<option value="-1" >'+ txtMsg +'</option>');
			}else{
				var data = { type: type,classId : classId, userName : $.trim($('#jUserName').val())};
				$.ajax({
					url : url,
					data :data,
					async :false, //必须同步，否则 renderTable 无法获取到数据
					success : function(data){
						dataList = data.datas.dataList;
					}
				});
			}
			//加载数据
			win.renderTable(dataList);
		},
       renderTable : function(dataList){
    		if(dataList != null && dataList != undefined && dataList.length >0 ){
				Handlebars.render("#jtemplateData",dataList,"#jtbodyData");
				$('#jEmptyData').hide();
			}else{
				$('#jtbodyData').empty();
				$('#jEmptyData').show();
			}
       }
	};

	win.init();

});
