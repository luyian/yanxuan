//定义specController
angular.module("adType").controller("adTypeController", function ($scope, adTypeService, $controller) {
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
        adTypeService.get(queryParams)
            .then(
                function (value) {
                    $scope.pageOption.total = value.data.total;
                    $scope.dataList = value.data.result;
                }
            );
    };

    //保存商品规格信息
    $scope.save = function() {
        var response = null;
        if ($scope.entity.id === undefined) {
            response = adTypeService.post($scope.entity);
        } else {
            response = adTypeService.put($scope.entity);
        }
        //执行操作
        response.then(
            function (value) {
                //关闭模态框
                $("#newModal").modal("hide");
                //清除spec数据
                $scope.entity = null;
                //刷新
                $scope.pageQuery();
            },
            function (reason) {
                console.log(reason);
            }
        )
    };
    //修改初始化
    $scope.initData = function(entity) {
      $scope.entity = entity;
    };

    //删除
    $scope.delete = function(id) {
        adTypeService.delete(id)
            .then(function (value) {
                //刷新
                $scope.pageQuery();
            })
    };

    //定义媒体类型
    $scope.mediaTypeArr = ["", "图片", "文字", "代码"];
});