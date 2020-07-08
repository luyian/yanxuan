// angular路由设置
app.config(["$routeProvider",function($routeProvider){
  $routeProvider.when("/",
    {
      templateUrl: "home.html"
    })
  .when("/goods/edit",
    {
      templateUrl: "pages/goods/edit.html"
    })
  .when("/goods/manage",
    {
      templateUrl: "pages/goods/manage.html"
    })
  .when("/account/detail",
    {
      templateUrl: "pages/account/detail.html"
    })
  .when("/account/security",
    {
      templateUrl: "pages/account/security.html"
    })
  .otherwise({redirectTo:'/'});
}])