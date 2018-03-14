function map() {
	this.questions.forEach(function(question) {
		emit(null, {
			queNum: 1,
			errNum: 1 - question.totalScoreRate
		});
	});
}
