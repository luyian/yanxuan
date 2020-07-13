var E = window.wangEditor;
var editor = new E('#editor');
editor.customConfig.uploadImgServer = '/upload';
editor.customConfig.showLinkImg = false;
editor.customConfig.uploadFileName = 'file';
editor.customConfig.uploadImgTimeout = 30000;
editor.create();