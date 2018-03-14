define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var utils = require('utils');

	var win = {
		init : function() {
			this.bindEvent();
			this.loadData();
		},
		loadData : function() {
			var data = {
				classId : $('#jClassId').val(),
				subjectId : $('#jSubjectId').val()
			};
			$.post(ctx + '/auth/classTeacher/achievement/findStudentAchievement.htm',data, function(json) {
				$('#jDataJson').val(JSON.stringify(json));
				var data = json.datas.dataList;
				if(data == undefined || data.length ==0){
					$('#jEmptyData').show();
				}
				Handlebars.render("#jTemplate", data, "#jBody");
			});
		},
		bindEvent : function() {
			$('#jExportData').click(function(){
				var form = document.forms[0];
				var params= $(form).serialize();
				var url=Leke.domain.diagServerName + '/auth/classTeacher/homework/exportHwGrade.htm?'+ params;
				location.href= url;
			});
			$('#jSearch').click(function(){
				var jsonStr=$('#jDataJson').val();
				if(jsonStr.length > 0){
					var json =JSON.parse(jsonStr);
					var stuName =$.trim($('#jStudentName').val());
					//根据 学生姓名 过滤 json.datas.dataList
					var newJson=[];
					if(stuName.length > 0){
						var delIndexs=[];
						for (var i = 0; i < json.datas.dataList.length; i++) {
							var item =json.datas.dataList[i];
							if (item.userName.indexOf(stuName) > -1) {
								newJson.push(item);
							}
							Handlebars.render("#jTemplate", newJson, "#jBody");
							if(newJson.length > 0){
								$('#jEmptyData').hide();
							}else{
								$('#jEmptyData').show();
							}
						}
					}else{
						Handlebars.render("#jTemplate", json.datas.dataList, "#jBody");
						if(json.datas.dataList == undefined || json.datas.dataList.length == 0){
							$('#jEmptyData').show();
						}else{
							$('#jEmptyData').hide();
						}
					}
				}
			});
			$(document).on('click','.detailsStudent',function(){
				var href=$(this).data('href');
				href +='&className=' +encodeURIComponent(encodeURIComponent($.trim($('#jClassName').text())));
				window.open(href);
			});
		}
	};

	win.init();

});

