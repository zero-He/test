import Modal from './modal';

export const files = {};

export const imageUploadUrl = 'https://homework.leke.cn/auth/m/upload/imageUpload.htm';
export const audioUploadUrl = 'https://homework.leke.cn/auth/m/upload/audioUpload.htm';
export const audioUploadFileUrl = 'https://homework.leke.cn/auth/m/upload/audio.htm';

export const CACHE_URLS = {};
export const CDVFILE_PLAYER_PREFIX  = 'cdvfile://localhost/temporary/';

export function parseFileName(url) {
    if (url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
    return url;
}

export function parseFileDir(url) {
    return url.substring(0, url.lastIndexOf('/'));
}

export function downloadAsCached(url) {
    const fileName = parseFileName(url);
    const filePath = CDVFILE_PLAYER_PREFIX + fileName;
    const dfd = $.Deferred();
    if (CACHE_URLS[fileName]) {
        console.log('download from cache: ' + url);
        dfd.resolve(filePath, null);
    } else {
        console.log('download from remote: ' + url);
        var ft = new FileTransfer();
        ft.onprogress = dfd.notify;
        ft.download(encodeURI(url), filePath, function(data) {
            CACHE_URLS[fileName] = filePath;
            dfd.resolve(filePath, data);
        }, dfd.reject, false, {});
    }
    return dfd.promise();
}

export function download(url, filePath, options) {
    const dfd = $.Deferred(), ft = new FileTransfer();
    ft.onprogress = function(pevt) {
        dfd.notify(pevt);
    }
    ft.download(encodeURI(url), filePath, function(data) {
        dfd.resolve(data);
    }, dfd.reject, false, options);
    return dfd.promise();
}

function compressImg(dataUrl, callback, maxWidth = 800) {
    const image = new Image(), imgType = 'data:image/jpeg;base64,';
    image.onload = function() {
        let w = image.naturalWidth, h = image.naturalHeight;
        if (w > maxWidth) {
            h = h * (maxWidth / w), w = maxWidth;
            const cvs = document.createElement('canvas'), ctx = cvs.getContext('2d');
            cvs.width = w;
            cvs.height = h;
            ctx.clearRect(0, 0, w, h);
            ctx.drawImage(image, 0, 0, w, h);
            dataUrl = cvs.toDataURL('image/jpeg', 0.2);
            dataUrl = dataUrl.substring(imgType.length);
        }
        callback(dataUrl);
    }
    image.src = imgType + dataUrl;
}

export function uploadImg(dataUrl, compress = true) {
    let data = {file: dataUrl};
    const dfd = $.Deferred();
    if (compress) {
        compressImg(dataUrl, dataUrl => {
            data = {file: dataUrl};
            $.post(imageUploadUrl, data).done(json => dfd.resolve(json.p)).fail(dfd.reject);
        });
    } else {
        $.post(imageUploadUrl, data).done(json => dfd.resolve(json.p)).fail(dfd.reject);
    }
    return dfd;
}

export function uploadAudio(dataUrl) {
    const dfd = $.Deferred();
    const data = {file: dataUrl};
    $.post(audioUploadUrl, data).done(json => dfd.resolve(json.p)).fail(dfd.reject);
    return dfd;
}

export function uploadAudioFile(filePath) {
    const options = new FileUploadOptions();
    options.fileKey = "file";
    options.fileName = "audio.mp3";
    options.mimeType = "audio/wav";
    options.params = {
        ticket: Leke.ticket
    };
    const dfd = $.Deferred();
    const ft = new FileTransfer();
    ft.onprogress = dfd.notify;
    ft.upload(filePath, encodeURI(audioUploadFileUrl), resp => {
        if (resp.responseCode === 200) {
            dfd.resolve(JSON.parse(resp.response));
        } else {
            dfd.reject(resp);
        }
    }, dfd.reject, options);
    return dfd.promise();
}

export function uploadAudioFile2(filePath) {
    const dfd = $.Deferred();
    readFileAsB64(filePath).done(dataUrl => {
        dataUrl = dataUrl.substring(dataUrl.indexOf('base64') + 7);
        let data = {file: dataUrl};
        $.post(audioUploadUrl, data).done(json => dfd.resolve(json.p)).fail(dfd.reject);
    }).fail(dfd.reject);
    return dfd;
}

export function readFileAsB64(path, callback) {
    const dfd = $.Deferred();
    window.resolveLocalFileSystemURL(path, function(fileEntry) {
        fileEntry.file(function(file) {
            var reader = new FileReader();
            reader.onloadend = function() {
                dfd.resolve(this.result);
            }
            reader.readAsDataURL(file);
        });
    }, dfd.reject);
    return dfd.promise();
}

export function copyFileToCache(path, fileUrl) {
    const fileName = parseFileName(fileUrl);
    const dfd = $.Deferred();
    function success() {
        CACHE_URLS[fileName] = CDVFILE_PLAYER_PREFIX + fileName;
        dfd.resolve();
    }
    function failed(err) {
        dfd.reject(err);
    }
    window.resolveLocalFileSystemURL(path, function(fileEntry) {
        window.resolveLocalFileSystemURL(CDVFILE_PLAYER_PREFIX, function(dirEntry) {
            fileEntry.copyTo(dirEntry, fileName, success, dfd.reject);
        }, dfd.reject);
    }, dfd.reject);
    return dfd;
}
