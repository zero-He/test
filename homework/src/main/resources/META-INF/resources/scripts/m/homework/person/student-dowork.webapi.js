(function() {

	window.webapi = {
		getInitDatas : function(callback) {
			callback(Csts);
		},
		heartbeat : function(answerJson) {
			return $.post('heartbeat.htm', {
				homeworkId : Csts.workInfo.homeworkId,
				homeworkDtlId : Csts.workInfo.homeworkDtlId,
				answerJson : answerJson
			});
		},
		save : function(answerJson) {
			return $.post("save.htm", {
				homeworkId : Csts.workInfo.homeworkId,
				homeworkDtlId : Csts.workInfo.homeworkDtlId,
				answerJson : answerJson
			});
		},
		submit : function(answerJson) {
			return $.post("submit.htm", {
				homeworkId : Csts.workInfo.homeworkId,
				homeworkDtlId : Csts.workInfo.homeworkDtlId,
				answerJson : answerJson
			});
		}
	};

})();
