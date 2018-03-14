define(function(require, exports, module) {
	var $ = require('jquery');
	var utils = require('utils');
	var dialog = require('dialog');
	var O = {
			init : function(page){
				this.page = page;
				this.bindEvent();
			},
			bindEvent : function(){
				var _this = this;
				$(document).on("click",".j-delete",function(){
					var i = $(this).data("i");
					dialog.confirm("确定要删除该条提问？").done(function(sure) {
						if (sure) {
							$.post("deleteMySelfDoubt.htm",{doubtIds:i},function(datas){
								if(datas.success){
									_this.page.loadData();
									utils.Notice.alert("删除成功");
								}
							});
						}
					});
				}).on("click",".j-mark",function(){
					var i = $(this).data("i");
					$.post("markMySelfDoubt.htm",{doubtId:i},function(datas){
						if(datas.success){
							_this.page.loadData();
						}
					});
				});
			}
	}
	module.exports = O;
})