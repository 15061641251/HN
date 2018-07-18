var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
var app = angular.module('authEditApp', ['myHttpInterceptor']).config(
    ['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('myHttpInterceptor');
    }]).config(['$locationProvider', function ($locationProvider) {
    $locationProvider.html5Mode(true);
}]);
app.controller('authEditController', [
    '$scope',
    '$http',
    '$timeout',
    '$location',
    function ($scope, $http, $timeout, $location) {

        $scope.resCheck = function (res, reslist) {
            // 如果有任何一个子节点被选中，则让上级节点也选中
            // 由于主菜单没有传过来，需要后台每次主动添加，这边只有两级
            reslist.is_checked = !!_.findWhere(reslist.children, {
                is_checked: true
            });
        };

        $scope.reslistCheck = function (reslist) {
            // 自动选中所有下级，取消的时候，子也取消
            _.each(reslist.children, function (res) {
                if(reslist.is_checked == false){
                    res.is_checked = reslist.is_checked;
                }

            });
        };

        $scope.postItems = [];
        $scope.editAuth = function ($timeout) {
            // 找出所有已选中的复选框
            // 先找第二级的被选中的
        	
            postItems = [];
            _.each($scope.resources, function (reslist) {
                if (reslist.is_checked) {
                    postItems.push(reslist.int_id);
                    _.each(reslist.children, function (res) {
                        if (res.is_checked) {
                            postItems.push(res.int_id);
                        }
                    })
                }

            });

            if (postItems.length == 0) {

                layer.msg('请选择权限');

            } else {

                layer.load(2);

            	var authId=$location.search()["authId"];
                $http.post(contextpath + '/auth/editAuthPost' ,{
                	authId:authId,
                	int_ids: postItems.join(","),
                    name: $scope.name,
                    description: $scope.description
                }).success(
                    function (response, data, header, config, status,$timeout) {

                        layer.closeAll('loading');

                        var success = response.success;
                        if (success == 0) {
                            layer.msg('错误的请求');
                        } else if (success == 1) {
                            // 关闭页面，刷新父界面
                            layer.msg("修改成功", {
                                time: 2000
                            }, function () {

                                parent.layer.close(index);
                            })
                            parent.vm.search();
                        }
                    }).error(
                    function (response, data, header, config, status) {
                        layer.closeAll('loading');
                        layer.msg('出错了,请重试');
                    });
            }
        };

        $http.get(contextpath + '/auth/getAllAuthInfo?authId=' + $location.search()["authId"]).success(
            function (response) {
                $scope.name = response.authority_name;
                $scope.resources = response.res_detail;
                $scope.description = response.authority_description;
            })

    }]);