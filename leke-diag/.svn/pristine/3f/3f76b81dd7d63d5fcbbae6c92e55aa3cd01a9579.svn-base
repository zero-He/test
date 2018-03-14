function map() {
	var score = this.scores.filter(function(score) {
		return score.subjectId == ##subjectId##;
	})[0];
	var avgScore = score.totalScore / score.totalNum;
	emit(this.classId, {
		statNum : 1,
		maxScore : avgScore,
		avgScore : avgScore
	});
}
