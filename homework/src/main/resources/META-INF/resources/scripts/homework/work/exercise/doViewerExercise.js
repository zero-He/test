define(function(require, exports, module) {
	var SheetFlash = require('../sheet/sheet.flash');
	var PaperStruct = require('../sheet/paper.struct');
	var PaperViewer = require('../sheet/paper.viewer');
	var PaperExtend = require('../sheet/paper.extend');

	var PanelService = require('./PanelService');

	// 页面结构初始化
	PaperStruct.init();
	// 扩展按钮初始化
	PaperExtend.bind();
	// 页面Flash初始化
	SheetFlash.scan();
	// 试卷答题区的渲染
	PaperViewer.renderSheet();
	// 页面服务绑定
	PanelService.bind();
});
