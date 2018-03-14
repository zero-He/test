function reduce(key, items) {
	var rdv = {
		name : items[0].name,
		totalNum : 0,
		rightNum : 0
	};
	items.forEach(function(item) {
		rdv.totalNum += item.totalNum;
		rdv.rightNum += item.rightNum;
	});
	return rdv;
}
