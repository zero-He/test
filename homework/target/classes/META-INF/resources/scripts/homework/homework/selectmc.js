define(function(require, exports, module) {
	var $ = require('jquery');
	var Utils = require("utils");
	var Page = require('page');
	var Dialog = require('dialog');
	var Handlebars = require('common/handlebars');
	var I18n = require('i18n');

	var Microcourse = {
		init : function() {
			Page.create({
				jsonp : true,
				id : 'pagination',
				pageSize : 9,
				url : Leke.domain.beikeServerName + '/auth/teacher/prepare/queryMicrocourse.htm',
				buttonId : 'jMicrocourseSearch',
				formId : 'jMicrocourseForm',
				callback : function(data) {
					Handlebars.render('#jMicrocourseListItemTpl', data.page, '#jMicrocourseBody');
				}
			});
			$('#fBtnSave').on('click', function() {
				var microcourseEls = $('.j-microcourseId:checked');
				if (microcourseEls.length == 0) {
					Utils.Notice.alert('请选择微课');
					return false;
				}
				var resources = $.map(microcourseEls, function(el) {
					return {
						type : 'micro',
						link : $(el).val(),
						text : $(el).attr('data-name')
					};
				});
				Dialog.close("bindmc", resources);
			});
			$('#fBtnCancel').on('click', function() {
				Dialog.close();
			});
		}
	};

	Microcourse.init();
});
