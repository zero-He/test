define(function(require, exports, module) {
	var ko = require("knockout");
	var I18n = require('i18n');
	I18n.init('queJS');
	require('common/knockout/bindings/i18n');
	require('question/component/workbook/favorite-list');
	var DIR = Leke.ctx + '/auth/teacher/workbook/';
	ko.applyBindings({
		config: {
			urlFavoriteDetails: DIR + 'favorite/details.htm',
			urlPersonalList: DIR + 'personal/list.htm?spm=101026'
		}
	});
});