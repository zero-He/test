function map() {
	for (var i = 0; i < this.scores.length; i++) {
		var score = this.scores[i];
		var avgScore = score.totalScore / score.totalNum;
		emit(score.subjectId, {
			maxScore : avgScore,
			avgScore : avgScore,
			statNum : 1
		});
	}
}
