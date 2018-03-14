function finalize(gradeId, reduceVal) {
	reduceVal.submitRate = reduceVal.submitRate / reduceVal.statNum;
	reduceVal.delayRate = reduceVal.delayRate / reduceVal.statNum;
	return reduceVal;
}
