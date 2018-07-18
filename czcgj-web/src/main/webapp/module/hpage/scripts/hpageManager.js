var app = angular.module('HpageApp', ['tm.pagination', 'myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');
}])

var vm;
app.controller('HpageController', ['$scope', '$http', 'HpageService', function ($scope, $http, HpageService) {
    vm = $scope;

    var today = getFormatCurrentDay();

    /**
     * 当天日期显示时分，之前日期显示到天
     * @param timeStr
     * @returns {*}
     */
    var timeCheck = function (timeStr) {

        if (isEmpty(timeStr)) {
            return "";
        } else {
            if (timeStr.substr(0, 10) == today) {
                return timeStr.substr(11, 16);
            } else {
                return timeStr.substr(0, 10);
            }
        }
    }

    var getHpageEmailList = function () {

        HpageService.getNoReadEamilCount().success(function (response, data, header, config, status) {
            //debugger;
            $scope.total = response;

        }).error(function (data, header, config, status) {
            layer.msg('获取邮件出错了,请重试');
        });

        HpageService.getEamilList().success(function (response, data, header, config, status) {
            var list = response;
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    list[i].create_time = timeCheck(list[i].create_time)
                }
            }
            $scope.Emails = list;

        }).error(function (data, header, config, status) {
            layer.msg('获取邮件出错了,请重试');
        });
    };

    var getHpageInfoList = function () {
        HpageService.getInfoList().success(function (response, data, header, config, status) {
            var list = response;
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    list[i].create_time = timeCheck(list[i].create_time)
                }
            }
            $scope.Infos = list;

        }).error(function (data, header, config, status) {
            layer.msg('获取信息出错了,请重试');
        });
    };

    var getHpageOtherTodoList = function () {
        HpageService.getOtherTodoList().success(function (response, data, header, config, status) {
            var list = response;
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    list[i].create_time = timeCheck(list[i].create_time)
                }
            }
            $scope.carTodos = list;

        }).error(function (data, header, config, status) {
            layer.msg('获取信息出错了,请重试');
        });
    };

    var getHpageBulletinList = function () {
        HpageService.getNoReadBulletinCount().success(function (response, data, header, config, status) {
            //debugger;
            $scope.count = response;

        }).error(function (data, header, config, status) {
            layer.msg('获取信息出错了,请重试');
        });
        HpageService.getBulletinList().success(function (response, data, header, config, status) {
            var list = response;
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    list[i].create_time = timeCheck(list[i].create_time)
                }
            }
            $scope.Bulletins = list;

        }).error(function (data, header, config, status) {
            layer.msg('获取信息出错了,请重试');
        });
    };

    var getHpageTodoList = function () {
        HpageService.getTodoList().success(function (response, data, header, config, status) {
            //debugger;
            var todoList = response;
            if (todoList.length > 0) {
                $('#calendar').datepicker({
                    keyboardNavigation: false,
                    language: "zh-CN",
                    todayHighlight: true,
                    beforeShowDay: function (date) {
                        var year = date.getYear() + 1900 + "";
                        var month = date.getMonth() + 1;
                        if (month < 10) {
                            month = "0" + month;
                        } else {
                            month = "" + month;
                        }
                        var day = date.getDate();
                        if (day < 10) {
                            day = "0" + day;
                        } else {
                            day = "" + day;
                        }
                        for (var i = 0; i < todoList.length; i++) {
                            var temp = todoList[i].start.substr(0, 10);
                            if (year == temp.substr(0, 4) && month == temp.substr(5, 2) && day == temp.substr(8, 2))
                                return "red";
                        }
                    }
                }).on('changeDate', function (e) {
                    var date = e.format(0, 'yyyy-mm-dd') + "";
                    var tab = '';
                    var count = 0;
                    for (var i = 0; i < todoList.length; i++) {
                        var temp = todoList[i].start.substr(0, 10);
                        if (date.substr(0, 4) == temp.substr(0, 4) && date.substr(5, 2) == temp.substr(5, 2) && date.substr(8, 2) == temp.substr(8, 2)) {
                            tab += "<p style='color:#7f7f7f;font-size:14px;line-height:20px;border-bottom:1px dotted #7f7f7f;margin:0px;margin-left:15px;padding-bottom:3px;display:inline-block;width:90%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;'>" + todoList[i].start.substr(11, 5) + ' ' + todoList[i].title + "<p>";
                            count++;
                            if (count == 2) {
                                break;
                            }
                        }
                    }
                    if (tab == '') {
                        tab += "<p style='color:#7f7f7f;font-size:14px;line-height:20px;border-bottom:1px dotted #7f7f7f;margin:0;margin-left:15px;margin-top:10px;padding-bottom:3px;display:inline-block;width:90%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;'>暂无日程<p>";
                    }
                    $("#todo_div").html(tab);
                }).on('changeMonth', function (e) {

                });
            } else {
                $('#calendar').datepicker({
                    keyboardNavigation: false,
                    language: "zh-CN",
                    todayHighlight: true,
                })
            }

        }).error(function (data, header, config, status) {
            layer.msg('获取待办出错了,请重试');
            $('#calendar').datepicker({
                keyboardNavigation: false,
                language: "zh-CN",
                todayHighlight: true,
            })
        });
    }
    getHpageTodoList();


    $scope.showInfo = function (info) {
        var index = layer.open(
            {
                type: 2,
                area: ['80%', '90%'],
                maxmin: true,
                title: '信息详情',
                skin: 'layui-layer-rim', // 加上边框
                content: [contextpath + '/info/InfoDetail?int_id=' + info.int_id],
                end: function (index) {
                    getHpageInfoList();
                }
            });
        layer.full(index);
    };

    // 邮件详情
    $scope.showEmail = function (Email) {
        var index = layer.open(
            {
                type: 2,
                area: ['80%', '90%'],
                maxmin: true,
                title: '邮件详情',
                skin: 'layui-layer-rim', // 加上边框
                content: [contextpath + '/email/emailDetail?uuid=' + Email.uuid],
                end: function (index) {
                    getHpageEmailList();
                }
            });
        layer.full(index);
    };
    
    // 用车审核详情
    $scope.showCarInfo = function (car) {
        var index = layer.open(
            {
                type: 2,
                area: ['80%', '90%'],
                maxmin: true,
                title: '用车审核',
                skin: 'layui-layer-rim', // 加上边框
                content: [contextpath + '/car/user/exDetail?int_id=' + car.foreign_id],
                end: function (index) {
                	getHpageOtherTodoList();
                }
            });
        layer.full(index);
    };
    $scope.showBulletin = function (bulletin) {

        var index = layer.open(
            {
                type: 2,
                area: ['80%', '90%'],
                maxmin: true,
                title: '公告详情',
                skin: 'layui-layer-rim', // 加上边框
                content: [contextpath + '/bulletin/bulletinDetail?int_id=' + bulletin.int_id],
                end: function (index) {
                    getHpageBulletinList();
                }
            });
        layer.full(index);
    };

}]);

app.factory('HpageService', ['$http', function ($http) {
    var factory = {};

    factory.getNoReadEamilCount = function () {
        return $http.get(contextpath + '/hpage/getNoReadEamilCount', {});
    }

    factory.getEamilList = function () {
        return $http.get(contextpath + '/hpage/getEmailList', {});
    }

    factory.getNoReadBulletinCount = function () {
        return $http.get(contextpath + '/hpage/getNoReadBulletinCount', {});
    }

    factory.getBulletinList = function () {
        return $http.get(contextpath + '/hpage/getBulletinList', {});
    }

    factory.getTodoList = function () {
        return $http.post(contextpath + '/todo/getTodoListByMouth', {});
    }
    
    factory.getOtherTodoList = function () {
        return $http.post(contextpath + '/todo/getOtherTodoList', {});
    }

    factory.getInfoList = function () {
        return $http.post(contextpath + '/hpage/getInfoList', {});
    }

    return factory;
}])
