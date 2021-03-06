// 设置系统公共的内容
angular.module("yanxuan").controller("baseController", function ($scope) {

    // 定义分页参数
    $scope.pageOption  = {
        total : 0 , // 总记录数
        currentPage : 1 , // 当前页码值，初始值为1
        pageSize : 10, // 每页显示的记录数，初始值为10
        pageSizeArr : [10, 20 ,30, 40, 50] , // 每页显示记录的选择数组
        onChange : function () {
            // 页码和每页显示记录数发生变化执行触发的业务逻辑，，可以用来请求数据查询的操作
            $scope.pageQuery();
        }
    };

    // 状态数组
    $scope.statusArray = ["正常","停用"];
});

//获取springsecurity中登录的用户名
angular.module("yanxuan").controller("loginUserController", function ($scope, $http) {
    //发送请求，获取数据
    $http.get("/loginUser").then(function (value) {
        $scope.username = value.data.username;
    });
});