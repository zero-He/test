function map() {
	emit(this.gradeId, {
		statNum : 1,
		submitRate : (this.normalNum + this.delayNum) * 100 / this.totalNum,
		delayRate : this.delayNum * 100 / this.totalNum
	});
}
