define(function(require, exposts, module) {
	var ko = require('knockout');
	var $ = require('jquery');
	var _ = require('common/base/helper');
	var utils = require('utils');
	var SWFUpload = require('fileupload');
	var fileProgress = require('fileprogress');
	
	var RecordService = require('question/service/wordImportRecordService');
	var BaseInfoService = require('core-business/service/basicInfoService');
	
	require('core-business/widget/jquery.leke.stgGrdSbjSelect');
	
	function FormVM(params, element) {
		var self = this;
		self.$el = $(element);
		
		self.ticket = params.ticket;
		self.refreshList = params.refreshList;
		
		self.seed = 0;
		
		self.stageSubject = ko.observable();
		self.files = ko.observableArray();
		
		self.$el.find('[name=stageSubject]').stgGrdSbjSelect({
			onChange: function(item) {
				self.stageSubject(item);
			}
		});
		
		self.processing = ko.observable(false);
		self.isValid = ko.pureComputed(function() {
			var ss = self.stageSubject();
			return ss && ss.schoolStageId && ss.subjectId && self.files().length > 0;
		});
		
		self._initUploader();
	}
	
	FormVM.prototype._initUploader = function() {
		var self = this;
		var $btn = self.$el.find('.j-uploader');
		var $pro = self.$pro = self.$el.find('.j-process');
		var _btnId = _ensureId($btn, 'j_uploader_');
		var _proId = _ensureId($pro, 'j_process_');
		
		var dfd = $.Deferred();
		
		self._fu = SWFUpload.createFileUpload({
			context: window.ctx, 
			upload_url: window.ctx + '/auth/common/upload/fileUpload.htm?ticket=' + self.ticket,
			button_placeholder_id: _btnId,
			file_size_limit : '10MB',
			file_types: '*.docx',
			file_types_description: 'Word 文档(*.docx)',
			file_post_name: 'file',
			swfupload_loaded_handler: function() {
				dfd.resolve(self._fu);
			},
			custom_settings : {
				progressTarget : _proId
			}
		}, fileProgress, {
			deleteFile: function(datas) {
				self._resetUploader();
			},
			uploadSuccess: function(datas) {
				var fileName = datas.fileName || '';
				var note = fileName.substring(0, fileName.lastIndexOf('.'));
				self.files.push({
					recordId: self._allocRecordId(),
					fileId: datas.urls[0],
					fileName: fileName,
					note: note
				});
			}
		});
		
		self._dfdSwf = dfd.promise();
	};
	
	FormVM.prototype._allocRecordId = function() {
		var self = this;
		self.seed++;
		return self.ticket + _.now() + '_' + self.seed;
	};
	
	FormVM.prototype._resetUploader = function() {
		var self = this;
		self.files.removeAll();
		self.$pro.empty();
		self._dfdSwf.done(function(swf) {
			var stats = swf.getStats();
			stats.successful_uploads = 0;
			swf.setStats(stats);
		});
	};
	
	function _ensureId($el, prefix) {
		var id = $el.attr('id');
		if(!id) {
			id = _.uniqueId(prefix);
			$el.attr('id', id);
		}
		return id;
	}
	
	FormVM.prototype.onSubmit = function() {
		var self = this;
		if(!self.isValid()) {
			utils.Notice.alert('请选择要导入的文档！');
			return false;
		}
		var ss = self.stageSubject();
		
		var params = _.map(self.files(), function(file) {
			return _.extend({}, file, ss);
		});
		
		self.processing(true);
		var request = RecordService.wordImport(params);
		request.done(function() {
			self.processing(false);
			self._resetUploader();
			self.refreshList();
		});
		request.fail(function(msg) {
			utils.Notice.alert(msg || '习题导入操作失败！');
			self.processing(false);
			self._resetUploader();
		});
	};
	
	FormVM.prototype.dispose = function() {
	};
	
	ko.components.register('que-word-form', {
	    template: require('./que-word-form.html'),
	    viewModel: {
	    	createViewModel: function(params, componentInfo) {
	    		return new FormVM(params, componentInfo.element);
	    	}
	    }
	});
});