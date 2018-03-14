define(function(require, exports, module) {
	var $ = require('jquery');
	var ko = require('knockout');
	var http = require('common/base/http');
	var utils = require('utils');
	
	var SWFUpload = require('fileupload');
	var fileProgress = require('fileprogress');
	var UP_BTN_IMG_URL = Leke.domain.staticServerName + '/images/mobile/ele-learning-material/push-course.png';
	
	function MaterialVM(){
		var self = this;
		self.materialId = window.materialCtx.materialId;
		self.materialFile = ko.observable();
		self.pending = ko.observable(true);
		self._init();
	};
	
	MaterialVM.prototype._init = function(){
		var self = this;
		self.bindEvent();
	};
	
	MaterialVM.prototype.bindEvent = function(){
		var self = this;
		SWFUpload.createFileUpload({
			upload_url : Leke.ctx + '/auth/admin/material/uploadFile.htm?ticket=' + Leke.ticket,
			button_placeholder_id : 'jSpanButtonPlaceholder',
			button_width : 170,
			button_height : 160,
			button_image_url : UP_BTN_IMG_URL,
			file_types : '*.pdf',
			file_upload_limit: 1,
			file_post_name : 'file',
			file_size_limit : '300MB',
			custom_settings : {
				progressTarget : 'jMaterialUpload'
			},
			canEdit: false
		}, fileProgress, {
			onError : function(msg) {
				utils.Notice.alert(msg);
			},
			deleteFile : function(datas) {
				
			},
			modifyFileName:function(datas,fileName){
				self.materialFile(datas.materialFile);
			},
			uploadSuccess : function(datas) {
				self.materialFile(datas.materialFile);
			}
		});
	};
	
	MaterialVM.prototype.toJS = function(){
		var self = this;
		var result = self.materialFile();
		result.materialId = self.materialId;
		return result;
	};
	
	var SAVE_DIR = Leke.ctx + '/auth/admin/material/addMaterialFile.htm';
	MaterialVM.prototype.doSave = function(){
		var self = this;
		var dataJson = self.toJS();
		self.pending(false);
		var req = http.post(SAVE_DIR,{dataJson: JSON.stringify(dataJson)});
		req.then(function(datas){
			utils.Notice.alert('上传成功！');
			window.location.href = Leke.ctx + '/auth/admin/material/materialFileView.htm?materialId=' + self.materialId;
		},function(msg){
			self.pending(true);
			utils.Notice.alert(msg || '保存错误！');
		});
	};
	
	MaterialVM.prototype.doCancel = function(){
		var self = this;
		window.close();
	};
	
	ko.applyBindings(new MaterialVM());

});
