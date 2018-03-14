function map() {
	this.questions.forEach(function(question) {
		if (question.totalIsRight != null) {
			emit(question.questionId, {
				totalNum : 1,
				rightNum : question.totalIsRight ? 1 : 0,
				scoreRate : question.totalScoreRate
			});
		} else {
			emit(question.questionId, {
				totalNum : 0,
				rightNum : 0,
				scoreRate : 0
			});
		}
	});
}
