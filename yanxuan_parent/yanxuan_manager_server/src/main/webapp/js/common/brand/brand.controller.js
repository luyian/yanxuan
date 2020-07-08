//定义brandController
angular.module("brand").controller("brandController", function ($scope, restService, $controller) {
    //监听视图内容是否加载完毕，加载完毕后触发回调函数
    $scope.$on("$viewContentLoaded", function (event) {
       $scope.pageQuery();
    });

    //继承其它的控制器
    $controller("baseController", {$scope : $scope});

    //定义品牌状态数组
    $scope.statusArray = ["正常", "停用"];

    //修改信息初始化
    $scope.update = function(value) {
        $scope.brand = value;
    };

    //定义请求路径
    var baseUrl = "../../brand";
    //发送分页的请求
    $scope.pageQuery = function(){
        if ($scope.searchName === undefined) {
            $scope.searchName = "";
        }
        //定义查询参数
        var queryParams = {
            pageNum : $scope.pageOption.currentPage,
                pageSize : $scope.pageOption.pageSize,
                name : $scope.searchName
        };

        //$http.get("../../brand?pageNum=" + $scope.pageOption.currentPage + "&pageSize=" + $scope.pageOption.pageSize + "&name=" + $scope.searchName)
        // $http.get("../../brand", {params : queryParams})
        restService.get(baseUrl, queryParams)
            .then(
                function (value) {
                    $scope.pageOption.total = value.data.total;
                    $scope.brandList = value.data.result;
                }
            );
    };
    //保存商品品牌信息
    $scope.save = function() {
        var response = null;
        //判断操作的类型
        if ($scope.brand.id === undefined) {
            //执行保存操作
            response = restService.post(baseUrl, $scope.brand);
        } else {
            //执行修改操作
            response = restService.put(baseUrl, $scope.brand);
        }
        //发送请求
        response.then(
            function (value) {
                //关闭模态框
                $("#newModal").modal("hide");
                //清除brand数据
                $scope.brand = null;
                //刷新
                $scope.pageQuery();
            },
            function (reason) {
                console.log(reason);
            }
        );
    };

    //删除
    $scope.delete = function(id) {
        // $http.delete("../../brand/" + id)
        restService.delete(baseUrl, id)
            .then(
                function (value) {
                    //刷新
                    $scope.pageQuery();
                },
                function (reason) {
                    console.log(reason);
                }
            );
    };

    //第一次加载时调用，分页查询
    // $scope.pageQuery();
});