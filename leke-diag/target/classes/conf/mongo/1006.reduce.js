function reduce(key, items) {
	var rdv = {
		queNum : 0,
		errNum : 0
	};
	items.forEach(function(item) {
		rdv.queNum += item.queNum;
		rdv.errNum += item.errNum;
	});
	return rdv;
}
