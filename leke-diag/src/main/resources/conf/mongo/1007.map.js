function map() {
	if (this.knoScores != null) {
		var userId = this.studentId;
		this.knoScores.forEach(function(kno) {
			var key = {
				id : kno.id,
				userId : userId
			};
			var item = {
				name : kno.name,
				totalNum : kno.totalNum,
				rightNum : kno.rightNum
			};
			emit(key, item);
		});
	}
}
