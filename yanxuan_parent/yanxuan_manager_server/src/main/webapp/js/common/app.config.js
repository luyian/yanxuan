// angular路由设置
angular.module("yanxuan").config(["$routeProvider",function($routeProvider){
    $routeProvider.when("/",{
        templateUrl: "home.html"
    }).when("/goods/brand",{
        templateUrl: "pages/goods/brand.html",
        controller : "brandController"
    }).when("/goods/spec",{
        templateUrl: "pages/goods/spec.html",
        controller : "specController"
    }).when("/goods/category/:pId",{
        templateUrl: "pages/goods/category.html",
        controller : "categoryController"
    }).when("/goods/audit",{
        templateUrl: "pages/goods/audit.html"
    }).when("/seller/audit",{
        templateUrl: "pages/seller/audit.html",
        controller : "sellerController"
    }).when("/seller/manage",{
        templateUrl: "pages/seller/manage.html"
    }).when("/ad/type",{
        templateUrl: "pages/ad/type.html"
    }).when("/ad/content",{
        templateUrl: "pages/ad/content.html"
    }).when("/ad/edit/",{
        templateUrl: "pages/ad/edit.html"
    }).when("/ad/edit/:id",{
        templateUrl: "pages/ad/edit.html"
    }).otherwise({redirectTo:'/'});
}]);