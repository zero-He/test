var MediaData = "";
var browser = {
        versions: function() {
            var u = navigator.userAgent,
                app = navigator.appVersion;
            return { //移动终端浏览器版本信息
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
            };
        }(),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    }

function MediaRecord() {
	this.media = null;
}
// 录音
MediaRecord.prototype.record = function(src) {
	if (!src) {
		return;
	}

	if (this.media) {
		return;
	}

	try{
		this.media = new Media(src, onSuccess, onError);
	}catch(e){
		console.log(e);
	}
	function onSuccess() {
        var getFileContentAsBase64 = function (path, callback) {
            window.resolveLocalFileSystemURL(path, gotFile, null);
            function gotFile(fileEntry) {
                fileEntry.file(function (file) {
                    var reader = new FileReader();
                    reader.onloadend = function (e) {
                        var content = this.result;
                        callback(content);
                    };
                    reader.readAsDataURL(file);
                });
            }
        };
        var path;
        var filename = src;

        var isIos = /iphone|ipod|ipad/gi.test(navigator.userAgent);
        if (isIos) {
            path = cordova.file.tempDirectory + filename;
        } else {
            path = cordova.file.externalRootDirectory + filename;
        }
        getFileContentAsBase64(path, function (base64) {
            MediaData = base64;
        });
    }

	function onError(err) {
		Utils.Notice.alert("录音失败");
	}
	try{
		this.media.startRecord();
	}catch(e){
		console.log(e);
	}
};

// 停止
MediaRecord.prototype.stop = function() {
	if (this.media) {
		try{
			this.media.stopRecord();
		}catch(e){
			console.log(e);
		}
	}

	this.media = null;
};

function onUploadFile() {
    window.requestFileSystem(window.TEMPORARY, 5 * 1024 * 1024, function (fs) {

        console.log('file system open: ' + fs.name);
        var fileName = "uploadSource.txt";
        var dirEntry = fs.root;
        dirEntry.getFile(fileName, { create: true, exclusive: false }, function (fileEntry) {

            // Write something to the file before uploading it.
            writeFile(fileEntry);

        }, onErrorCreateFile);

    }, onErrorLoadFs);
}

function MediaPlay() {
	this.media = null;
	this.timer = null;
}

/* 播放 */
MediaPlay.prototype.play = function(src, time, progressCallback,
		completeCallback) {
	var that = this;

	if (!src||this.media) {
		return;
	}

	try{
		this.media = new Media(src, null, null);
		this.media.play();
	}catch(e){
		console.log(e);
	}
	var click = 0;
	var inter = setInterval(function() {
		click+=100;
		if(time*1000>click){
			progressCallback(click);
		}else{
			completeCallback();
            clearInterval(inter);
            try{
            	that.media.release();
            }catch(e){
        		console.log(e);
        	}
		}
    }, 100);
};

/* 停止 */
MediaPlay.prototype.stop = function() {
	if (this.media) {
		try{
			this.media.stop();
			this.media.release();
		}catch(e){
    		console.log(e);
    	}
	}
	this.media = null;
};

/* 暂停 */
MediaPlay.prototype.pause = function() {
	try{
		this.media.pause();
	}catch(e){
		console.log(e);
	}
};

/* 继续 */
MediaPlay.prototype.goon = function() {
	try{
		this.media.play();
	}catch(e){
		console.log(e);
	}
};