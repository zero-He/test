define(function(require, exports, module) {
	require('common/forbidpaste');
	var SheetFlash = require('../sheet/sheet.flash');
	var PaperStruct = require('../sheet/paper.struct');
	var PaperAnswer = require('../sheet/paper.answer');
	var PaperExtend = require('../sheet/paper.extend');

	var PanelService = require('./PanelService');
	var AnswerService = require('./AnswerService');

	// 页面结构初始化
	PaperStruct.init();
	// 扩展按钮初始化
	PaperExtend.bind();
	// 页面Flash初始化
	SheetFlash.scan();
	// 试卷答题区的渲染
	PaperAnswer.renderSheet();
	// 页面服务绑定
	PanelService.bind();
	PanelService.startRefreshState(PaperAnswer);
	// 后台服务绑定
	AnswerService.bind();
});
