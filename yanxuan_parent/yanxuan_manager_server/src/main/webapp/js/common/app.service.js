angular.module("yanxuan").service("restService", function ($http) {
    /**
     * get请求
     * @param url 请求路径
     * @param options 请求参数
     * @returns {*}
     */
    //定义当前service的get方法
    this.get = function (url, options) {
        //如果是根据主键id进行查询，需要修改资源路径
        if (options !== undefined && typeof options !== "object") {
            url = url + "/" + options;
        }
        //分页条件查询
        return $http.get(url, {params : options});
    };

    /**
     * post请求
     * @param url
     * @param entity 实体
     * @returns {*|void|undefined}
     */
    this.post = function (url, entity) {
        return $http.post(url, entity);
    };

    /**
     * put请求
     * @param url
     * @param entity
     * @returns {*|void|IDBRequest<IDBValidKey>|Promise<void>}
     */
    this.put = function (url, entity) {
        return $http.put(url, entity);
    };

    /**
     * delete请求
     * @param url
     * @param id
     * @returns {void|Promise<boolean>|IDBRequest<undefined>}
     */
    this.delete = function (url, id) {
        if (id !== undefined) {
            url = url + "/" + id;
        }
        return $http.delete(url);
    }
});