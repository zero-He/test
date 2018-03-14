function finalize(subjectId, reduceVal) {
	reduceVal.avgScore = reduceVal.avgScore / reduceVal.statNum;
	return reduceVal;
}
