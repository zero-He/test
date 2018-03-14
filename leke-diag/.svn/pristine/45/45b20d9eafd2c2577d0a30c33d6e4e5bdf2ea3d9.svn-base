define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");
	var utils = require('utils');

	var TeaDiligent = {
		init : function() {
			this.loadData();
		},
		loadData : function() {
			$.post(ctx + '/auth/provost/diligent/gradeStuWorkDiligent.htm', function(json) {
				Handlebars.render("#jStudentsTemplate", json.datas.list, "#jPageBody");
			});
		}
	};

	TeaDiligent.init();

});
