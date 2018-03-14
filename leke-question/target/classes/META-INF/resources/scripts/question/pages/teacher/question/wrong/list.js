define(function(require, exports, module) {
	var ko = require("knockout");
	require('./question-wrong-list');
	
	ko.applyBindings({
		config: {
			loadUrl: Leke.ctx + '/auth/teacher/question/wrong/details.htm',
			clazzs: window.Ctx.clazzs ||[],
			subjects: window.Ctx.subjects || []
		}
	});

});