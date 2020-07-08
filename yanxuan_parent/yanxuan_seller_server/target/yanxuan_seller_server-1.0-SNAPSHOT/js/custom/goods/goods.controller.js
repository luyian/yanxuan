//定义brandController
angular.module("goods").controller("goodsController", function ($scope, goodsService,categoryService, $controller) {
    //监听视图内容是否加载完毕，加载完毕后触发回调函数
    $scope.$on("$viewContentLoaded", function (event) {
        $scope.queryCategory1();
    });

    //继承其它的控制器
    $controller("baseController", {$scope : $scope});

    //加载一级类目，需要依赖categoryService
    $scope.queryCategory1 = function () {
        categoryService.get({parentId : 0}).then(function (value) {
            $scope.category1List = value.data.result;
        });
    };

    //监听一级类目值是否发生变化，如果发生变化，就加载二级类目
    $scope.$watch("entity.category1Id", function (newVal) {
        if (newVal == undefined) {
            $scope.category2List = [];
            return null;
        }
        //查询二级类目
        categoryService.get({parentId : newVal}).then(function (value) {
            $scope.category2List = value.data.result;
        });
    });

    $scope.$watch("entity.category2Id", function (newVal) {
        if (newVal == undefined) {
            $scope.category3List = [];
            return null;
        }
        //查询二级类目
        categoryService.get({parentId : newVal}).then(function (value) {
            $scope.category3List = value.data.result;
        });
    });

    //监听三级类目的选择，并且加载关联的品牌信息
    $scope.$watch("entity.category3Id", function (newVal) {
        if (newVal == undefined) {
            return null;
        }
        //查询三级类目的所有信息
        categoryService.get(newVal).then(function (value) {
            //拿到所有的品牌信息
            $scope.brandList = JSON.parse(value.data.relation.brandIds);
        });
    });

    //保存商品信息
    $scope.save = function () {
        //发送请求，传递entity
        goodsService.post($scope.entity).then(function (value) {
            alert("商品上架成功");
        });
    };
});