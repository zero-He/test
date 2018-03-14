function reduce(subjectId, items) {
	var reduceVal = {
		statNum : 0,
		maxScore : 0,
		avgScore : 0
	};
	for (var i = 0; i < items.length; i++) {
		reduceVal.statNum += items[i].statNum;
		reduceVal.avgScore += items[i].avgScore;
		reduceVal.maxScore = Math.max(reduceVal.maxScore, items[i].maxScore);
	}
	return reduceVal;
}
