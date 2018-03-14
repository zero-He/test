define(function(require, exports, module) {
	var ko = require('knockout');
	var _ = require('underscore');
	
	function CarouselVM(params, element) {
		var self = this;
		self.items = params.items;
		self.columns = params.columns || [];
		self.options = _.extend({
			pageSize: 10,
			pageCount: 10,
			interval: 5000
		}, params.options);
		
		self.itemPages = ko.observableArray();
		self.curPage = ko.observable(0);
		
		self.totalPage = ko.pureComputed(function() {
			return self.itemPages().length;
		});
		self.endPage = ko.pureComputed(function() {
			var cur = self.curPage();
			var count = self.options.pageCount;
			var total = self.totalPage();
			var end = cur + Math.floor(count / 2);
			if(end < count) {
				end = count;
			}
			if(end > total - 1) {
				end = total - 1;
			}
			return end;
		});
		self.startPage = ko.pureComputed(function() {
			var end = self.endPage();
			var count = self.options.pageCount;
			var start = end - count + 1;
			if(start < 0) {
				start = 0;
			}
			return start;
		});
		self.midPages = ko.pureComputed(function() {
			var end = self.endPage();
			var start = self.startPage();
			var result = [];
			for(var i = start; i <= end; i++) {
				result.push(i);
			}
			return result;
		});
		
		self._d1 = ko.computed(function() {
			var items = params.items();
			var totalSize = items.length;
			var pageSize = self.options.pageSize;
			var itemPages = [];
			for(var idx = 0; idx < totalSize; idx += pageSize) {
				itemPages.push(items.slice(idx, idx + pageSize));
			}
			self.itemPages(itemPages);
			self.curPage(0);
		});
		
//		self._d2 = ko.computed(function() {
//			var curPage = self.curPage();
//			if(self._t) {
//				clearTimeout(self._t);
//				self._t == null;
//			}
//			
//			self._t = setTimeout(function() {
//				self.toNext();
//			}, self.options.interval);
//		});
	}
	
	CarouselVM.prototype.toPrev = function() {
		var self = this, cur = self.curPage();
		if(cur > 0) {
			self.curPage(cur - 1);
		}
	};
	
	CarouselVM.prototype.toNext = function() {
		var self = this;
		var curPage = self.curPage();
		var total = self.totalPage();
		var nextPage = total > 0 ? (curPage + 1) % total : 0;
		self.curPage(nextPage);
	};
	
	CarouselVM.prototype.toFirst = function() {
		var self = this;
		self.curPage(0);
	};
	
	CarouselVM.prototype.toLast = function() {
		var self = this;
		var total = self.totalPage();
		self.curPage(total > 0 ? total - 1: 0);
	};
	
	CarouselVM.prototype.dispose = function() {
		var self = this;
		self._d1.dispose();
//		self._d2.dispose();
		if(self._t) {
			clearTimeout(self._t);
			self._t == null;
		}
	};
	
	ko.components.register('carousel', {
	    template: require('./carousel.html'),
	    viewModel: CarouselVM
	});
});