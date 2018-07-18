var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

var app = angular.module('memberAddApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');

}]).config(['$locationProvider', function ($locationProvider)// 配置路由模式
{
    $locationProvider.html5Mode(true);
}]);

var vm;
app.controller('memberAddController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    // console.log("dept_id:" + $location.search()["dept_id"]);
    $scope.is_enabled = 1;
    //选中角色ids
    $scope.sex = '男';
    $scope.auths_ids = [];
    $scope.password = '123456';
    //选中数据权限ids
    $scope.data_auth = "";
    $scope.data_auth_names = "";
    $scope.is_admin = 0;

    vm = $scope;

    // 选择注册机构
    $scope.chooseCompany = function () {
        var id = "";
        layer.open({
            type: 2,
            area: ['80%', '80%'],
            title: '选择注册机构',
            content: [contextpath + '/company/chooseOneCompany']
        });
    };

    // 选择区域
    $scope.chooseArea = function () {
        var id = "";
        layer.open({
            type: 2,
            area: ['80%', '80%'],
            title: '选择区域',
            content: [contextpath + '/area/chooseOneArea']
        });
    };

    // 已选择部门存储及展示
    $scope.setValue = function (data) {
        var flag = data.split("<split>")[0];
        var ids = data.split("<split>")[1];
        var names = data.split("<split>")[2];

        if (flag == "1") {
            $scope.dept_id = ids;
            $scope.dept_name = names;
        }
        else if (flag == "2") {
            $scope.data_auth = ids;
            $scope.data_auth_names = names;
        }
        $scope.$apply();//强制刷新model
    }

    $scope.addMember = function () {
        auths_ids = [];
        _.each($scope.auths, function (item) {
            if (item.is_checked) {
                auths_ids.push(item.int_id);
            }
        });

        // if (isEmpty($scope.data_auth)) {
        // 	layer.msg('请选择数据权限');
        // 	return false;
        // }
        if (auths_ids.length == 0) {

            layer.msg('请选择角色');
            return false;
        }
        if (isEmpty($scope.account) || isEmpty($scope.username)) {
            layer.msg('请填写完整');
            return false;
        }
        layer.load(2);
        $scope.password = hex_md5($scope.password);
        $http.post(getlocalhost() + '/member/addMemberPost', {
            account: $scope.account,
            dept_id: $location.search()["dept_id"],
            username: $scope.username,
            password: $scope.password,
            email: $scope.email,
            sex: $scope.sex,
            mobile: $scope.mobile,
            mobile2: $scope.mobile2,
            tel: $scope.tel,
            auth_ids: auths_ids.join(","),
            // data_auth : $scope.data_auth,
            is_enabled: $scope.is_enabled,
            is_admin: $scope.is_admin,
            company_id: $scope.company_id,
            area_id: $scope.area_id

        }).success(function (response, data, header, config, status) {
            layer.closeAll('loading');
            var success = response.success;
            if (success == 0) {
                layer.msg(response.description);
            }
            else if (success == 1) {
                // 关闭页面，刷新父界面
                layer.msg("添加成功", {
                    time: 2000
                }, function () {
                    parent.layer.close(index);
                })
                parent.list_search();
            }
        }).error(function (response, data, header, config, status) {
            layer.closeAll('loading');
            layer.msg('出错了,请重试');
        });
    };

    /**
     * 点击移除账号的readonly属性，屏蔽浏览器自动填充
     */
    $scope.remove = function () {
        document.getElementById("account").removeAttribute("readonly");
    };

    $http.get(contextpath + '/member/getAuthList').success(function (response, data, header, config, status) {
        var success = response.success;
        if (success == 0) {
            layer.msg('错误的请求');
        }
        else if (success == 1) {
            $scope.auths = response.list;
        }
    }).error(function (response, data, header, config, status) {
        layer.msg('出错了,请重试');
    });

    $http.get(contextpath + '/member/getMyInfo').success(function (response, data, header, config, status) {

        var returnCode = response.returnCode;
        if (returnCode == 1) {
            $scope.company_id = response.result.company_id;
            $scope.company_name = response.result.company_name;
        }
    }).error(function (response, data, header, config, status) {

    })


    $scope.selectCompany = function (company_id, company_name) {

        $scope.company_id = company_id;
        $scope.company_name = company_name;

        $scope.$apply();

    }

    $scope.selectArea = function (area_id, area_name) {
        $scope.area_id = area_id;
        $scope.area_name = area_name;

        $scope.$apply();
    }

}]);
