//完成请求的发送，也就是对http请求进行再封装
//参数一：service的名称，参数二：回调函数
angular.module("seller").service("sellerService", function ($http) {
    //定义请求的资源路径
    var baseUrl = "../../seller";

    //get请求
    //定义当前service的get方法
    this.get = function (options) {
        var targetUrl = baseUrl;
        //如果是根据主键id进行查询，需要修改资源路径
        if (options !== undefined && typeof options !== "object") {
            targetUrl = targetUrl + "/" + options;
        }
        //分页条件查询
        return $http.get(targetUrl, {params : options});
    };

    //post请求
    this.post = function (entity) {
        return $http.post(baseUrl, entity);
    };

    //put请求
    this.put = function (entity) {
        return $http.put(baseUrl, entity);
    };

    //delete请求
    this.delete = function (id) {
        var targetUrl = baseUrl;
        if (id !== undefined) {
            targetUrl = targetUrl + "/" + id;
        }
        return $http.delete(targetUrl);
    }
});