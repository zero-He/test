function finalize(questionId, reduceVal) {
	if (reduceVal.totalNum > 0) {
		reduceVal.rightRate = reduceVal.rightNum / reduceVal.totalNum;
		reduceVal.scoreRate = reduceVal.scoreRate / reduceVal.totalNum;
	} else {
		reduceVal.rightRate = 0;
		reduceVal.scoreRate = 0;
	}
	return reduceVal;
}
