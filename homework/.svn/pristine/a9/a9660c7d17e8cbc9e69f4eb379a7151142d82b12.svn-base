function reduce(questionId, items) {
	var reduceVal = {
		totalNum : 0,
		rightNum : 0,
		scoreRate : 0
	};
	items.forEach(function(item) {
		reduceVal.totalNum += item.totalNum;
		reduceVal.rightNum += item.rightNum;
		reduceVal.scoreRate += item.scoreRate;
	});
	return reduceVal;
}
