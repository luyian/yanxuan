//定义brandController
angular.module("seller").controller("sellerController", function ($scope, sellerService, $controller) {
    //监听视图内容是否加载完毕，加载完毕后触发回调函数
    $scope.$on("$viewContentLoaded", function (event) {
        $scope.pageQuery();
    });

    //继承其它的控制器
    $controller("baseController", {$scope : $scope});

    $scope.pageQuery = function(entity){
        if ($scope.name === undefined) {
            $scope.name = "";
        }
        //定义查询参数
        var queryParams = {
            pageNum : $scope.pageOption.currentPage,
            pageSize : $scope.pageOption.pageSize,
            name : $scope.name,
            status : "0"
        };
        //发送请求
        sellerService.get(queryParams).then(function (value) {
            //总记录数
            $scope.pageOption.total = value.data.total;
            //显示数据
            $scope.sellerList = value.data.result;
        });
    };

    //初始化查看详情的数据
    $scope.initEntity = function (seller) {
        $scope.entity = seller;
    };

    //状态修改
    $scope.updateStatus = function (id, status) {
        //请求参数
        var entity = {
            id : id,
            status : status
        };
        sellerService.put(entity).then(function (value) {
            alert("状态修改成功！");
            //关闭模态框
            $("#newModal").modal("hide");
            //刷新
            $scope.pageQuery();
        });
    };
});