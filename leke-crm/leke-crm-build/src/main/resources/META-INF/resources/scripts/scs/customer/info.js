define(function(require, exports, module) {
	var $ = require('jquery');
	var PointModule = require('./info.point');
	var AttendModule = require('./info.attend');

	var modules = {
		'PointModule' : PointModule,
		'AttendModule' : AttendModule
	};

	var Pages = {
		init : function() {
			var container = $('#jModuleContainer');
			$('#jModuleList li').click(function() {
				var moduleName = $(this).data('module');
				modules[moduleName].init(container);
				$(this).addClass('active').siblings().removeClass('active');
			});
			$('#jModuleList li:first').trigger('click');
		}
	};

	Pages.init();

});
