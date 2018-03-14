function reduce(classId, items) {
	var reduceVal = {
		statNum : 0,
		maxScore : 0,
		avgScore : 0
	}
	for (var i = 0; i < items.length; i++) {
		reduceVal.statNum += items[i].statNum;
		reduceVal.maxScore = Math.max(reduceVal.maxScore, items[i].maxScore);
		reduceVal.avgScore += items[i].avgScore;
	}
	return reduceVal;
}
