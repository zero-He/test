(function() {
	window.webapi = {
		fetchDatas : function(query) {
			return $.post('worklist.htm', query);
		}
	}
})();