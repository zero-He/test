define(function(require, exports, module) {
	var $ = require('jquery');
	var StuSubmitState = require('diag/common/StuGradeStatesBar');
	var win = {
		init : function() {
			StuSubmitState.init();
		}
	};

	win.init();

});
