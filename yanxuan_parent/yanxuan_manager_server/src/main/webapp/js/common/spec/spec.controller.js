//定义specController
angular.module("spec").controller("specController", function ($scope, specService, $controller) {
    //监听视图内容是否加载完毕，加载完毕后触发回调函数
    $scope.$on("$viewContentLoaded", function (event) {
       $scope.pageQuery();
    });

    //继承其它的控制器
    $controller("baseController", {$scope : $scope});

    //定义品牌状态数组
    $scope.statusArray = ["正常", "停用"];

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
        specService.get(queryParams)
            .then(
                function (value) {
                    $scope.pageOption.total = value.data.total;
                    $scope.specList = value.data.result;
                }
            );
    };

    //初始化规格信息
    $scope.initSpec = function(id) {
        if (id !== undefined) {
            //进行更新操作,需要查询规格选项
            specService.get(id)
                .then(function (value) {
                    $scope.spec = value.data;
                })
        } else {
            $scope.spec = {
                name : "",
                optionList : []
            }
        }
    };
    //新增选项
    $scope.addSpecOption = function() {
        //初始化时设置排序序号
        $scope.spec.optionList.push({
            name : '',
            sortNo : $scope.spec.optionList.length + 1
        });
    };
    //删除选项
    $scope.delSpecOption = function(index) {
        $scope.spec.optionList.splice(index, 1);
        //更新序号
        $scope.spec.optionList.forEach(function (value, index1) {
            value.sortNo = index1 + 1;
        });
    };

    //保存商品规格信息
    $scope.save = function() {
        var response = null;
        if ($scope.spec.id === undefined) {
            response = specService.post($scope.spec);
        } else {
            response = specService.put($scope.spec);
        }
        //执行操作
        response.then(
            function (value) {
                //关闭模态框
                $("#newModal").modal("hide");
                //清除spec数据
                $scope.spec = null;
                //刷新
                $scope.pageQuery();
            },
            function (reason) {
                console.log(reason);
            }
        )
    };

    //删除
    $scope.delete = function(id) {
        specService.delete(id)
            .then(function (value) {
                $scope.pageQuery();
            })
    };
});