//创建wangEdit
var E = window.wangEditor;
var editor = new E('#editor');
//自定义内容
//设置服务器的请求路径，用来完成图片的上传
editor.customConfig.uploadImgServer = '/upload';
//设置filename
editor.customConfig.uploadFileName = "file";
editor.customConfig.uploadImgTimeout = 30000;
//不显示网络图片的选项
editor.customConfig.showLinkImg = false;

editor.create();