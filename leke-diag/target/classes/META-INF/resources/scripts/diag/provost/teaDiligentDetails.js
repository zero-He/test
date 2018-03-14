define(function(require, exports, module) {
	var $ = require('jquery');
	var StuSubmitState = require('diag/common/GradeStatesBar');
	var win = {
		init : function() {
			this.bindEvent();
			StuSubmitState.init();
		},
		bindEvent : function() {
		}
	};

	win.init();

});
