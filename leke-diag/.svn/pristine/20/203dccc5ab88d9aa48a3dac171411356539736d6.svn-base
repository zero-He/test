window.webapi = {
	getInitDatas : function(callback) {
		callback(Csts);
	},
	fetchRptView : function(query) {
		if (query.klassId) {
			var arr = query.klassId.match(/(\d+)-(\d+)/);
			if (arr.length == 3) {
				query.classId = arr[1];
				query.subjectId = arr[2];
			}
		}
		return $.post(ctx + '/auth/m/report/tchanaly/data.htm', query);
	}
}
