define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var Page = require('page');
	var utils = require('utils');
	var win = {
		init : function() {
			this.bindEvent();
			this.loadData();
		},
		bindEvent : function() {
		},
		loadData : function() {
			var url= ctx + '/auth/provost/swot/findStuAnalysis.htm';
			$.post(url,function(json){
				var dataList = json.datas.dataList;
				if(dataList != null) {
					var output = Handlebars.render("#jStudentsTemplate",dataList,"#jtbodyData");
				}else{
					$('#jEmptyData').show();
				}
			});
		}
	};

	win.init();

});
