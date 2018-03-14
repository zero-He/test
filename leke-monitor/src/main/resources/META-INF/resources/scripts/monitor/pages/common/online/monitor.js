define(function(require, exports, module) {
	var ko = require('knockout');
	var hasher = require('hasher');
	var crossroads = require('crossroads');
	var store = require('monitor/component/online/stat-store');
	
	ko.components.register('stat-total', {
		template: '<div class="c-countlists">' +
			'<ul data-bind="with: total">' +
		        '<li><span>累计注册用户数</span><span data-bind="text: $data.registered">0</span></li>' +
		        '<li><span>在线用户数</span><span data-bind="text: $data.platform">0</span></li>' +
		        '<li><span>实时课堂人数</span><span data-bind="text: $data.lesson">0</span></li>' +
		        '<li><span>网站在线人数</span><span data-bind="text: $data.web">0</span></li>' +
		        '<li><span>设备在线人数</span><span data-bind="text: $data.device">0</span></li>' +
		    '</ul>' +
	    '</div>'
	});
	
	var COMS = ['country-map', 'country-details', 'province-map', 'province-details', 'city-details'];
	
	ko.components.loaders.unshift({
		getConfig: function(name, callback) {
			if(COMS.indexOf(name) >= 0) {
				require.async('monitor/component/online/' + name, function(mod) {
					callback(mod);
				});
			} else {
				callback(null);
			}
		}
	});
	
	var entry = {
		route : ko.observable(),
		dispose: function() {
			store.dispose();
		}
	};
	
	function registerRoute(route) {
		function toHome() {
			route({ view: 'country-map' })
		}
		
		crossroads.addRoute('', toHome);
		crossroads.addRoute('country/map', toHome);
		crossroads.addRoute('country/details', function() {
			route({ view: 'country-details' });
		});
		crossroads.addRoute('province/{provinceId}/map', function(provinceId) {
			route({ view: 'province-map', params: {
				provinceId: provinceId
			}})
		});
		crossroads.addRoute('province/{provinceId}/details', function(provinceId) {
			route({ view: 'province-details', params: {
				provinceId: provinceId
			}})
		});
		crossroads.addRoute('province/{provinceId}/city/{cityId}/details', function(provinceId, cityId) {
			route({ view: 'city-details', params: {
				provinceId: provinceId,
				cityId: cityId
			}})
		});
	}
	
	function initHasher() {
		function parseHash(newHash, oldHash){
			crossroads.parse(newHash);
		}
		hasher.initialized.add(parseHash);
		hasher.changed.add(parseHash);
		hasher.init();
	}
	
	registerRoute(entry.route);
	initHasher();
	
	ko.applyBindings(entry);
});