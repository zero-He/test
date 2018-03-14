define(function(require, exports, module) {
	
	var CST = {
		diffLevels: [{
			diffLevel: 1, 
			levelName: '容易'
		}, {
			diffLevel: 2, 
			levelName: '较易'
		}, {
			diffLevel: 3, 
			levelName: '一般'
		}, {
			diffLevel: 4, 
			levelName: '较难'
		}, {
			diffLevel: 5, 
			levelName: '困难'
		}],
		queShareScopes: [{
			scopeId: 1, 
			scopeName: '我的习题'
		}, {
			scopeId: 2, 
			scopeName: '学校习题'
		}, {
			scopeId: 3, 
			scopeName: '乐课网习题'
		}]
	};
	
	module.exports = CST;
});