define(function(require, exports, module) {
	var $ = require('jquery');
	var Handlebars = require("common/handlebars");

	var Pages = {
		init : function() {
			Pages.search();
			$('#jPageSearch').click(function() {
				Pages.search();
			});
		},
		search : function() {
			var lekeNo = $.trim($('#jLekeNo').val());
			var userName = $.trim($('#jUserName').val());
			var list = Pages.filter(lekeNo, userName);
			if (list.length > 0) {
				$('#jNoDataMsg').hide();
			} else {
				$('#jNoDataMsg').show();
			}
			Handlebars.render('#jPageTpl', list, '#jPageBody');
		},
		filter : function(lekeNo, userName) {
			return $.grep(bookList, function(book, i) {
				if (userName != "" && book.userName.indexOf(userName) < 0) {
					return false;
				}
				if (lekeNo != "") {
					if (book.lekeNo != null && book.lekeNo != "-1" && book.lekeNo.indexOf(lekeNo) >= 0) {
						return true;
					}
					if (book.stuno != null && book.stuno != "-1" && book.stuno.indexOf(lekeNo) >= 0) {
						return true;
					}
					return false;
				}
				return true;
			});
		}
	};

	Pages.init();
});
