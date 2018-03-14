function reduce(classId, items) {
	var reduceVal = {
		statNum : 0,
		submitRate : 0,
		delayRate : 0
	};
	for (var i = 0; i < items.length; i++) {
		reduceVal.statNum += items[i].statNum;
		reduceVal.submitRate += items[i].submitRate;
		reduceVal.delayRate += items[i].delayRate;
	}
	return reduceVal;
}
