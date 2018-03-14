define(function(require, exports, module) {
	var $ = require('jquery');
	var swfobject = require('common/swfobject');
	var RepoQS = require('repository/service/RepoQueryString');
	var utils = require("utils");
	
	var config = window.materialFileViwCtx || {};
	var Preview = {
		loadOnlineSchool : function() {
			var flashvars = {
				index : 1,
				"transType" : config.transType || '',
				"serverIp" : Leke.domain.onlineServerName,
				"fileId" : "",
				"subName" : "",
				"count" : config.pageCount,
				"name" : config.fileName,
				"sections" : "",
				"filePath" : config.filePath,
				"userId" : config.userId,
				"taskId" : config.taskId
			};

			var swfUrl = Leke.assets + "/flashs/common/ui/ui-videoclass/preview/OnlineSchool.swf";
			swfobject.embedSWF(swfUrl, config.id, "720", "640", "10.2.0", "playerProductInstall.swf", flashvars, {
				quality : "high",
				bgcolor : "#ffffff",
				allowscriptaccess : "sameDomain",
				allowfullscreen : "true",
				wmode: "transparent"
			}, {
				id : 'OnlineSchool',
				name : 'OnlineSchool',
				align : "middle"
			});
			swfobject.createCSS("#" + config.id, "display:block; text-align:left;");
		}
	};
	Preview.loadOnlineSchool();
});