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
    //初始化entity
    $scope.entity = {
        specCheckedList : []
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
                //所有的规格信息
                $scope.specList = res.data.specList;
            }
        );
    });

    //商品保存
    $scope.save = function () {
        //设置富文本编辑器中的内容到entity
        $scope.entity.detail = editor.txt.html();

        // 把$scope.entity.picUrl 转换成字符串进行传递
        $scope.entity.picUrl = JSON.stringify($scope.entity.picUrl);

        //将skuList中的picUrl转化成字符串
        $scope.entity.skuList.forEach(function (sku) {
            sku.picUrl = JSON.stringify(sku.picUrl);
            sku.specs = JSON.stringify(sku.specs);
        });

        //转换specCheckedList
        $scope.entity.specCheckedList = JSON.stringify($scope.entity.specCheckedList);

        // 发送请求，传递entity
        goodsService.post($scope.entity).then(
            function (res) {
                alert("商品增加成功");
                // 跳转到商品列表页面
                window.location="#/goods/manage";
            }
        );
    };

    //spu图片上传
    $scope.uploadSpuPic = function () {
        $.Tupload.init({
			url: "/upload",
            title	  : "商品的主图片",
            fileNum: 5, // 上传文件数量
            divId: "goodsPic", // div  id
            accept: "image/jpeg,image/x-png", // 上传文件的类型
            preViewData : $scope.entity.picUrl,
            onSuccess: function(data, i) {
                $scope.entity.picUrl = data.data;
            },
            onDelete: function(i) {

            }
        });
    };
    //sku图片上传
    $scope.uploadSkuPic = function (index) {
        $.Tupload.init({
            url: "/upload",
            title	  : "商品的主图片",
            fileNum: 5, // 上传文件数量
            divId: "skuPic", // div  id
            accept: "image/jpeg,image/x-png", // 上传文件的类型
            //设置图片回显
            preViewData : $scope.entity.skuList[index].picUrl,
            onSuccess: function(data, i) {
                $scope.entity.skuList[index].picUrl = data.data;
            },
            onDelete: function(i) {

            }
        });
    };
    //销毁图片上传的插件
    $scope.destroyUpload = function(id){
        $.Tupload.destroy({divId : id});
    };

    //添加选中信息的方法
    $scope.getSpecCheckedList = function (event, specName, optionValue) {

        //判断对象是否已经创建
        var obj = queryObject($scope.entity.specCheckedList, specName);
        if (obj === null) {
            $scope.entity.specCheckedList.push({
                specName : specName,
                optionValue : [
                    optionValue
                ]
            })
        } else {
            //判断是勾选还是取消勾选
            if (event.target.checked) {
                obj.optionValue.push(optionValue);
            } else {
                //取消勾选，删除相应的属性
                obj.optionValue.splice(obj.optionValue.indexOf(optionValue), 1);
                //如果为空，删除父项
                if (obj.optionValue.length === 0) {
                    $scope.entity.specCheckedList.splice($scope.entity.specCheckedList.indexOf(obj), 1);
                }
            }
        }
        //调用生成sku信息的方法
        createSkuList();
    };

    //从集合中查找对应的对象
    var queryObject = function (list, specName) {
        var result = null;
        list.forEach(function (element) {
            if (element.specName === specName) {
                result = element;
            }
        });
        return result;
    };

    //根据选择的规格项，生成sku的信息
    var createSkuList = function () {
        //初始化skuList
        $scope.entity.skuList = [{specs:{}, price:0, stockCount:999, picUrl:[]}];

        //获取规格名称
        $scope.entity.specCheckedList.forEach(function (checkedSpec) {
            var specName = checkedSpec.specName;

            //定义一个临时的集合
            var tempSkuList = [];
            //获取skuList中的元素
            checkedSpec.optionValue.forEach(function (optionValue) {
                //遍历skuList
                $scope.entity.skuList.forEach(function (sku) {
                    // var sku = $scope.entity.skuList[0];
                    //每次设置的值必须为新的对象
                    var newSku = JSON.parse(JSON.stringify(sku));
                    newSku.specs[specName] = optionValue;
                    //新的对象放到临时的集合中
                    tempSkuList.push(newSku);
                });
            });
            //把临时的集合设置给skuList
            $scope.entity.skuList = tempSkuList;
        });
    };
});