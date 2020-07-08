//定义specController
angular.module("category").controller("categoryController", function ($scope, categoryService, $controller, brandService, specService) {
    //监听视图内容是否加载完毕，加载完毕后触发回调函数
    $scope.$on("$viewContentLoaded", function (event) {
        $scope.pageQuery();

        //查询所有的品牌信息
        $scope.queryBrand();
        //查询所有规格信息
        $scope.querySpec();
    });

    //继承其它的控制器
    $controller("baseController", {$scope : $scope});

    //定义品牌状态数组
    $scope.statusArray = ["正常", "停用"];

    //定义面包屑菜单
    $scope.breadMenu = [];
    //所有品牌信息
    $scope.queryBrand = function() {
        brandService.get()
            .then(function (value) {
                //初始化
                $scope.brandList = [];
                //只保存id和name属性
                value.data.result.forEach(function (element) {
                    $scope.brandList.push({
                        id : element.id,
                        name : element.name
                    })
                })
            });

    };
    //所有规格信息
    $scope.querySpec = function() {
        specService.get()
            .then(function (value) {
                $scope.specList = [];
                value.data.result.forEach(function (element) {
                    $scope.specList.push({
                        id : element.id,
                        name : element.name
                    })
                })
            })
    };


    //发送分页的请求
    $scope.pageQuery = function(entity){
        if (entity === undefined) {
            $scope.parentId = 0;
        } else {
            $scope.parentId = entity.id;
            $scope.pageOption.pageNum = 1;
            $scope.searchName = "";
        }
        if ($scope.searchName === undefined) {
            $scope.searchName = "";
        }
        //定义查询参数
        var queryParams = {
            pageNum : $scope.pageOption.currentPage,
            pageSize : $scope.pageOption.pageSize,
            name : $scope.searchName,
            parentId : $scope.parentId
        };
        categoryService.get(queryParams)
            .then(
                function (value) {
                    $scope.pageOption.total = value.data.total;
                    $scope.categoryList = value.data.result;
                }
            );

        //设置面包屑菜单
        if (entity !== undefined) {
            var index = $scope.breadMenu.indexOf(entity);
            if (index === -1) {
                //不存在，添加
                $scope.breadMenu.push(entity);
            } else {
                //存在，删除之后的
                $scope.breadMenu.splice(index + 1, $scope.breadMenu.length - index - 1);
            }

        } else {
            $scope.breadMenu = [];
        }
    };


    //窗口数据的初始化
    $scope.initCategory = function(entity) {
        if (entity === undefined) {
            //说明是添加顶级类目
            $scope.category = {
                structName : "-",
                level : 1,
                parentId : 0,
                relation : {
                    brandIds : [],
                    specIds : []
                }
            }
        } else {
            $scope.category = {
                structName : (entity.structName + ">" + entity.name).replace("->", ""),
                level : entity.level + 1,
                parentId : entity.id,
                relation : {
                    brandIds : [],
                    specIds : []
                }
            }
        }
    };

    //修改参数准备
    $scope.getCategory = function(id) {
        //请求服务器，获取类目信息
        categoryService.get(id)
            .then(function (value) {
                $scope.category = value.data;
                $scope.category.relation.specIds = JSON.parse($scope.category.relation.specIds);
                $scope.category.relation.brandIds = JSON.parse($scope.category.relation.brandIds);
            })
    };



    //保存商品规格信息
    $scope.save = function() {
        //关闭模态框
        $("#newModal").modal("hide");

        var response = null;
        //删除多余的属性
        $scope.category.relation.brandIds.forEach(function (value) {
            delete value["$$hashKey"];
        });
        $scope.category.relation.specIds.forEach(function (value) {
            delete value["$$hashKey"];
        });
        //把关联的品牌信息和规格信息，转化成字符串
        $scope.category.relation.brandIds = JSON.stringify($scope.category.relation.brandIds);
        $scope.category.relation.specIds = JSON.stringify($scope.category.relation.specIds);

        if ($scope.category.id === undefined) {
            response = categoryService.post($scope.category);
        } else {
            response = categoryService.put($scope.category);
        }
        //执行操作
        response.then(
            function (value) {
                //清除spec数据
                $scope.category = null;
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
        categoryService.delete(id)
            .then(function (value) {
                //刷新
                $scope.pageQuery();
            })
    };
});