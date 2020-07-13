//定义specController
angular.module("adInfo").controller("adInfoController", function ($scope, adInfoService, $controller) {
    //监听视图内容是否加载完毕，加载完毕后触发回调函数
    $scope.$on("$viewContentLoaded", function (event) {
        $scope.pageQuery();
    });

    //继承其它的控制器
    $controller("baseController", {$scope : $scope});

    //发送分页的请求
    $scope.pageQuery = function(entity) {
        if ($scope.name === undefined) {
            $scope.name = "";
        }
        //定义查询参数
        var queryParams = {
            pageNum: $scope.pageOption.currentPage,
            pageSize: $scope.pageOption.pageSize,
            name: $scope.name
        };
        adInfoService.get(queryParams)
            .then(
                function (value) {
                    $scope.pageOption.total = value.data.total;
                    $scope.dataList = value.data.result;
                }
            );
    };

    //删除
    $scope.delete = function(id) {
        adInfoService.delete(id)
            .then(function (value) {
                //刷新
                $scope.pageQuery();
            })
    };

    //状态数组
    $scope.statusArr = ["正常", "停用"];
});