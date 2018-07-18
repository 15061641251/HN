var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

var app = angular.module('editMemberApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');

}]).config(['$locationProvider', function ($locationProvider)// 配置路由模式
{
    $locationProvider.html5Mode(true);
}]);

var vm;
app.controller('editMemberController', ['$scope', '$http', '$location', function ($scope, $http, $location) {

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

    // 选择部门存储及展示
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
    };

    $scope.editMember = function () {
        var auths_ids = [];
        _.each($scope.auths, function (item) {
            if (item.is_checked) {
                auths_ids.push(item.int_id);
            }
        });

        if (auths_ids.length == 0) {
            layer.msg('请选择角色');
            return false;
        }

        if (isEmpty($scope.dept_id)) {
            layer.msg('请选择部门');
            return false;
        } else {
            var id = $scope.dept_id;
            if (id.split(",").length > 1) {
                layer.msg('只能选择一个部门');
                return false;
            }
        }

        if (isEmpty($scope.username) || isEmpty($scope.account)) {
            layer.msg('请填写完整');
            return false;
        }
        //debugger;
        if ($scope.password !== $scope.password2) {
            $scope.password = hex_md5($scope.password);
        }


        layer.load(2);
        $http.post(getlocalhost() + '/member/editMemberById?userId=' + $location.search()["userId"], {
            dept_id: $scope.dept_id,
            username: $scope.username,
            account: $scope.account,
            password: $scope.password,
            sex: $scope.sex,
            email: $scope.email,
            mobile: $scope.mobile,
            mobile2: $scope.mobile2,
            tel: $scope.tel,
            auth_ids: auths_ids.join(","),
            data_auth: $scope.data_auth,
            is_enabled: $scope.is_enabled,
            is_admin: $scope.is_admin,
            company_id: $scope.company_id,
            area_id: $scope.area_id

        }).success(function (response, data, header, config, status, $timeout) {
            layer.closeAll('loading');
            var success = response.success;
            if (success == 0) {
                layer.msg(response.description);
            }
            else if (success == 1) {
                // 关闭页面，刷新父界面
                layer.msg('修改成功', {
                    time: 2000
                }, function () {
                    parent.layer.close(index);
                });
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

    $http.post(getlocalhost() + '/member/getAuthList').success(function (response, data, header, config, status) {
        var success = response.success;
        if (success == 0) {
            layer.msg('错误的请求');
        }
        else if (success == 1) {
            $scope.auths = response.list;

            $http.post(getlocalhost() + '/member/getMemberByUserId?userId=' + $location.search()["userId"]).success(function (response, data, header, config, status) {
                $scope.dept_id = response.dept_id + "";
                $scope.dept_name = response.dept_name;
                $scope.username = response.username;
                $scope.password = response.password;
                $scope.password2 = response.password;
                $scope.account = response.account;
                $scope.sex = response.sex;
                $scope.email = response.email;
                $scope.mobile = response.mobile;
                $scope.mobile2 = response.mobile2;
                $scope.tel = response.tel;
                $scope.is_enabled = response.is_enabled;
                $scope.is_admin = response.is_admin;
                $scope.company_id = response.company_id;
                $scope.company_name = response.company_name;
                $scope.area_id = response.area_id;
                $scope.area_name = response.area_name;


                if (!isEmpty(response.auth_ids)) {
                    var list = response.auth_ids.split(",");
                    _.each(list, function (id) {
                        _.each($scope.auths, function (item) {
                            if (item.int_id == Number(id)) {
                                item.is_checked = true;
                                return false;
                            }
                        });
                    });
                }
            })
        }
    }).error(function (response, data, header, config, status) {
        layer.msg('出错了,请重试');
    });

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