define(function(require,exports,module){
	var $ = require('jquery');
	var Mustache = require('mustache');
	var Dialog = require('dialog');
	var Page = require('page');
	
	Page.create({
		id: 'divPage',
		url:"listPage.htm",
		curPage:1,
		pageSize:50,
		pageCount:10,//分页栏上显示的分页数
		buttonId: 'jButtonId',
		formId:'form',
		callback:function(datas){
			var page = datas.page;
			var dataList = page.dataList;
			var output = Mustache.render($("#table_tpl").val(),page);
			$('#page').html(output);
		}
	});
		
	$(document).on("click",".j-imei",function(){
		var i = $(this).data("i");
		if(i){
			$.post("userList.htm",{imei:i},function(data){
				var output = Mustache.render($("#userList_tpl").val(),data.datas);
				Dialog.open({
					size: 'nm',
					title: '使用该平板的用户',
					tpl: output
				})
			});
		}
	});
});
