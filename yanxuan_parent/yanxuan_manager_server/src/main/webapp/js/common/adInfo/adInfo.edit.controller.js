//定义specController
angular.module("adInfo").controller("adInfoEditController", function ($scope, adInfoService, $controller, $routeParams, adTypeService) {
    //监听视图内容是否加载完毕，加载完毕后触发回调函数
    $scope.$on("$viewContentLoaded", function (event) {
        var id = $routeParams.id;
        if (id !== undefined) {
            $scope.queryById(id);
        }
        $scope.queryType();
    });

    //广告类型数据的查询
    $scope.queryType = function() {
        adTypeService.get().then(function (value) {
            $scope.typeList = value.data.result;
        })
    };

    //继承其它的控制器
    $controller("baseController", {$scope : $scope});

    //保存商品规格信息
    $scope.save = function() {
        //获取富文本编辑器中的信息
        $scope.entity.content = editor.txt.html();

        var response = null;
        if ($scope.entity.id === undefined) {
            response = adInfoService.post($scope.entity);
        } else {
            response = adInfoService.put($scope.entity);
        }
        //执行操作
        response.then(
            function (value) {
                //清除spec数据
                $scope.entity = null;
                window.location = "#/ad/content";
            },
            function (reason) {
                console.log(reason);
            }
        )
    };
    //修改初始化
    $scope.queryById = function(id) {
      adInfoService.get(id).then(function (value) {
          $scope.entity = value.data;
          editor.txt.html($scope.entity.content);
      })
    };
});