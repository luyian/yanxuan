// 定义brandController
angular.module("goods").controller("goodsController", function ( $scope, goodsService, $controller, categoryService) {

    // 监听视图内容是否加载完毕，加载完毕后触发回调函数
    $scope.$on("$viewContentLoaded", function (event) {
        $scope.querycategory1();
    });

    // 继承其他的controller， baseController
    $controller("baseController", {$scope : $scope});

    // 加载一级类目
    $scope.querycategory1 = function () {
        categoryService.get({parentId: 0}).then(
            function (res) {
                $scope.category1List = res.data.result;
            }
        );
    };

    // 监听一级类目的选择
    $scope.$watch("entity.category1Id", function (newVal) {
        if(newVal === undefined){
            $scope.category2List = [];
            return null;
        }
        // 查询二级类目
        categoryService.get({parentId: newVal}).then(
            function (res) {
                $scope.category2List = res.data.result;
            }
        );
    });

    // 监听二级类目的选择
    $scope.$watch("entity.category2Id", function (newVal) {
        if(newVal === undefined){
            $scope.category3List = [];
            return null;
        }
        // 查询三级类目
        categoryService.get({parentId: newVal}).then(
            function (res) {
                $scope.category3List = res.data.result;
            }
        );
    });

    // 监听三级类目选择，并且加载关联的品牌信息
    $scope.$watch("entity.category3Id", function (newVal) {
        if(newVal === undefined){
            return null;
        }
        // 查询三级类目的所有的信息
        categoryService.get(newVal).then(
            function (res) {
                // 所有的品牌信息
                $scope.brandList = JSON.parse(res.data.relation.brandIds);
            }
        );
    });

    $scope.save = function () {
        // 把$scope.entity.picUrl 转换成字符串进行传递
        $scope.entity.picUrl = JSON.stringify($scope.entity.picUrl);
        // 发送请求，传递entity
        goodsService.post($scope.entity).then(
            function (res) {
                alert("商品增加成功");
            }
        );
    };
    
    $scope.uploadSpuPic = function () {
        $.Tupload.init({
			url: "/upload",
            title	  : "商品的主图片",
            fileNum: 5, // 上传文件数量
            divId: "goodsPic", // div  id
            accept: "image/jpeg,image/x-png", // 上传文件的类型
            onSuccess: function(data, i) {
                console.log(data);
                $scope.entity.picUrl = data.data;
            },
            onDelete: function(i) {

            }
        });
    }
});