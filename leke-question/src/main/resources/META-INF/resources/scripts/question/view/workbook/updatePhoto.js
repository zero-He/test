define(function(require,exports,module){
	var $ = require('jquery');
	var Dialog = require('dialog');
	var Utils = require('utils');
	var SWFUpload = require('fileupload');
	var FileProgress = require('fileprogress');
	
	
	var Photo = {
			fInit : function() {
				var _this = this;
				_this.fBindEvent();
			},
			
			fBindEvent : function() {
				var _this =this;
				
				var ticket = $('#jTicket').val();
				self._fu = SWFUpload.createFileUpload({
					context: ctx, 
					upload_url: window.ctx + '/auth/common/upload/fileUpload.htm?ticket=' + ticket,
					button_placeholder_id: 'jUploadPhoto',
					file_size_limit: '5MB',
					file_types: '*.jpg;*.jpeg;*.png;*.bmp;',
					file_types_description: 'image文件',
					file_post_name: 'file',
					file_upload_limit: 1,
					file_queue_limit: 1,
					custom_settings : {
						progressTarget : 'progressFile'
					}
				}, FileProgress, {
					deleteFile: function(datas) {
					},
					uploadSuccess: function(datas) {
						var stats = self._fu.getStats(); 
						stats.successful_uploads = 0; 
						self._fu.setStats(stats);
						$('#jPreview').attr('src',datas.urls[1]);
						$('#jPhotoUrl').val(datas.urls[0]);
					},
					onError: function(msg) {
						Utils.Notice.alert(msg);
					}
				});
				
				$('#jUpdatePhoto').click(function(){
					_this.fUpdatePhoto();
				});
				
				$('#jCancelPhoto').click(function(){
					Dialog.close();
				});
			},
			
			
			fUpdatePhoto : function() {
				var photoUrl = $('#jPhotoUrl').val();
				var workbookId = $('#jWorkbookId').val();
				var allPhotoUrl = $('#jPreview').attr('src');
				$.ajax({
					type : 'post',
					url : ctx + '/auth/common/workbook/updatePhoto.htm',
					data : {
						'photoUrl' : photoUrl,
						'workbookId' : workbookId
					},
					dataType : 'json',
					success : function(json) {
						if (json.success) {
							Dialog.close(null, photoUrl);
						}
					}
				});
			}
	};
	
	Photo.fInit();
	
});