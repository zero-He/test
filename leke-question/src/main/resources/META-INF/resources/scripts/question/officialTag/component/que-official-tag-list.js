define(function(require, exports, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var dialog = require('dialog');
	var utils = require('utils');

	function ListVM(params, element) {
		var self = this;
		var data = self.data= _.extend({}, params.data);
		self.officialTagName = ko.observable();
		self.officialTagList = ko.observableArray();
		self.doLoad();
	};
	/**
	 * 
	 */
	ListVM.prototype.doLoad = function() {
		var self = this;
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/officialTag/ajax/findOfficialTagList.htm',
			data : {},
			dataType : 'json',
			success : function(json) {
				var ots = json.datas.officialTagList;
				_.each(ots, function(ot) {
					ot.flag = ko.observable(true);
				});
				self.officialTagList(ots);
			}
		});
	};
	ListVM.prototype.doEdit = function(data) {
		var self = this;
		
		self.save(data).done(function(resp) {
			var msg = resp && resp.message;
			if(resp.success) {
				if(resp.success) {
					utils.Notice.alert('修改成功');
					self.doLoad();
				} else {
					utils.Notice.alert(msg || '修改失败');
				}
			}
		});
	};
	ListVM.prototype.save = function(data) {
		return $.ajax({
			type : 'post',
			url : ctx + '/auth/admin/officialTag/ajax/updateOfficialTag.htm',
			data : data,
			dataType : 'json'
		});
	};
	ListVM.prototype.doDel = function(data) {
		var self = this;
		var officialTagId = data.officialTagId;
		dialog.confirm('确定要删除吗？').done(function(sure){
			if(sure) {
				$.ajax({
					type : 'post',
					url : ctx + '/auth/admin/officialTag/ajax/deleteOfficialTag.htm',
					data : {
						officialTagId: officialTagId
					},
					dataType : 'json',
					success : function(resp) {
						var msg = resp && resp.message;
						if(resp && resp.success) {
							utils.Notice.alert('删除成功！');
							self.doLoad();
						} else {
							utils.Notice.alert(msg || '删除失败！');
							return;
						}
					}
				});
			}
		});
	};
	
	ListVM.prototype.doAdd = function() {
		var self = this;
		var officialTagName = self.officialTagName();
		if(!officialTagName) {
			utils.Notice.alert('请输入标签');
			return false;
		}
		$.ajax({
			type : 'post',
			url : ctx + '/auth/admin/officialTag/ajax/addOfficialTag.htm',
			data : {
				officialTagName: officialTagName
			},
			dataType : 'json',
			success : function(json) {
				var msg = json && json.message;
				if(json.success) {
					utils.Notice.alert('添加成功！');
					self.officialTagName('');
					self.doLoad();
				} else {
					utils.Notice.alert(msg || '添加失败！');
				}
			}
		});
	};
	
	ko.components.register('que-official-tag-list', {
	    template: require('./que-official-tag-list.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new ListVM(params, componentInfo.element);
	    	}
	    }
	});
});