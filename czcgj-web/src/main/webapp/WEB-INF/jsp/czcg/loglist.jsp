<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>报警日志</title>
    <jsp:include page="../../common/header.jsp"/>

    <link rel="stylesheet" href="${ctx}/module/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/module/css/page3.css">
    <link rel="stylesheet" href="${ctx}/common/layui/css/layui.css">
    <script src="${ctx}/module/js/bootstrap.min.js"></script>

    <style>
        /*分页*/
        .layui-laypage a, .layui-laypage span {
            background-color: #22446C;
            color: #fff;
            border: none;
        }

        .layui-laypage select {
            background-color: #22446C;
            color: #fff;
            border: none;

        }

        /*分页*/
        .layui-laypage a, .layui-laypage span {
            background-color: #22446C;
            color: #fff;
            border: none;
        }

        .layui-laypage select {
            background-color: #22446C;
            color: #fff;
            border: none;

        }

        /*---------下拉------------*/
        .layui-form-select .layui-input {
            width: 148px;
            height: 36px;
            margin-top: 9%;
            background: #204267;
            border: none;
            color: white;
        }


    </style>
</head>
<body>
<div class="wrap">
    <div class="top">
        <div class="top_left">
            <img class="top_img" src="${ctx}/module/img/top1.gif" alt="#">
            <span class="text">今日访问人数 / 总数</span>
            <span class="text_1"><span id="today_visit">0</span> / <span id="all_visit">0</span></span>
        </div>
        <div class="top_right">
            <img src="${ctx}/module/img/top2.gif" alt="">
            <span class="text">${user.username}</span>
            <span class="text_1">所属部门：</span>
            <span class="text">江苏省常州市城管局</span>
            <span class="text_1" style="cursor: pointer;" onclick="window.location='${ctx}/user/main'">【后台】</span>
            <span class="text_1" style="cursor: pointer;" onclick="window.location='${ctx}/user/logout'">【退出】</span>
        </div>
    </div>
    <div class="head">
        <div>

            <img onclick="window.location='${ctx}/czcg'" style="cursor: pointer;z-index:300" style="cursor: pointer;"
                 class="head_left" src="${ctx}/module/img/slicing/bb.png">
            <img onclick="window.location='${ctx}/czcg'" style="cursor: pointer;" class="head_rotate" id="indexHead"
                 src="${ctx}/module/img/round.png">
            <span onclick="window.location='${ctx}/czcg'" style="cursor: pointer;z-index:300">首页</span>
        </div>
        <div>
            <!--<img src="${ctx}/module/img/head_tit_l.png">-->
            <span>常州市户外广告倾覆预警平台</span>
            <img class="img_content" src="${ctx}/module/img/slicing/headline_bg.png" alt="##">
            <!--<img src="${ctx}/module/img/head_tit_r.png">-->
        </div>
        <div>

            <img onclick="go_detail()" style="cursor: pointer;z-index:300"
                 style="cursor: pointer;"
                 class="head_right" src="${ctx}/module/img/slicing/aa.png">
            <img onclick="go_detail()" style="cursor: pointer;" class="headRotate_right"
                 id="right_animation" src="${ctx}/module/img/round.png">
            <span onclick="go_detail()" style="cursor: pointer;z-index:300">详情页查询</span>
        </div>
        <img class="work_cont" src="${ctx}/module/img/slicing/form_bg.png" alt="###">
        <div class="clear"></div>
    </div>
    <div class="work" style="position: relative">

        <div class="periphery">
            <span class="bigMsg">警报日志信息</span>

            <div class="page-content">

                <%--<form class="layui-form" action="">--%>
                    <%--<div class="layui-inline">--%>
                        <%--<label class="layui-form-label" style="width:100px;margin-top: 5%;">报警类型：</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="modules" lay-verify="required" lay---%>
                                    <%--search="">--%>
                                <%--<option value="1">全部</option>--%>
                                <%--<option value="2">移动超限</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-inline">--%>
                        <%--<label class="layui-form-label" style="margin-top: 5%;">时间</label>--%>
                        <%--<div class="layui-inline">--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" class="layui-input"--%>
                                       <%--style="margin-top: 8%;height: 36px;background: #204267;border: none;text-align: center;padding: 0;color: white;"--%>
                                       <%--id="test_go" placeholder="请输入时间">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-inline">--%>
                        <%--~--%>
                        <%--<div class="layui-inline">--%>
                            <%--<div class="layui-inline">--%>
                                <%--<div class="layui-input-inline">--%>
                                    <%--<input type="text" class="layui-input"--%>
                                           <%--style="margin-top: 5%;height: 36px;background: #204267;border: none;text-align: center;padding: 0;color: white;"--%>
                                           <%--id="test_new" placeholder="请输入时间">--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<div class="layui-inline" style="margin-left: 4%">--%>
                        <%--<button class="but" type="button">查询</button>--%>
                    <%--</div>--%>
                    <%--<!--<div class="de" style="margin-top:0px;width: 500px">-->--%>
                    <%--<!--时间：-->--%>
                    <%--<!--<input type="text" value="2017-10-12 00:00"-->--%>
                    <%--<!--onFocus="if(this.value=='2017-10-12 00:00') this.value = ''"-->--%>
                    <%--<!--onBlur="if(this.value=='') this.value='2017-10-12 00:00'"-->--%>
                    <%--<!--style="text-align: center"> ~-->--%>
                    <%--<!---->--%>
                    <%--<!--<input type="text" value="2017-10-12 12:00"-->--%>
                    <%--<!--onFocus="if(this.value=='2017-10-12 12:00') this.value = ''"-->--%>
                    <%--<!--onBlur="if(this.value=='') this.value='2017-10-12 12:00'"-->--%>
                    <%--<!--style="text-align: center">-->--%>
                    <%--<!--</div>-->--%>
                    <%--<!--<button type="button">查询</button>-->--%>
                <%--</form>--%>
                    <br>

                <div id="log_list">
                    <div class="title" style="margin-top: 1%">
                        <ul>
                            <li>序号</li>
                            <li>时间</li>
                            <li>移动超限报警</li>
                            <li>x轴倾斜数值</li>
                            <li>y轴倾斜数值</li>
                            <li>z轴倾斜数值</li>

                        </ul>
                    </div>
                </div>

            </div>
            <div id="test1" style="text-align: center;">

            </div>
            <!--<form class="layui-form" action="" style="position: relative">-->
            <!--<div class="layui-input-inline" style="left: -323px;top: -7px;">-->

            <!--<select name="quiz3" style="width: 100px">-->
            <!--<option value="">10条/页</option>-->
            <!--<option value="西湖区">20条/页</option>-->
            <!--<option value="余杭区">30条/页</option>-->
            <!--<option value="拱墅区">40条/页</option>-->
            <!--</select>-->
            <!--</div>-->
            <!--</form>-->

        </div>
    </div>
</div>
<script src="${ctx}/common/layui/layui.js"></script>
<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;
    });
</script>
<script type="text/javascript">
    //--------------------------------------

    var refresh_page = function (currentPage, pageSize, deviceId) {

        $.ajax({
            url: '${ctx}/czcg/getDeviceLogListByDeviceId?pageIndex=' + currentPage + '&pageSize=' + pageSize + "&deviceId=" + deviceId,
            success: function (data) {
                if (data.returnCode == 1) {

                    var pageInfo = data.result;
                    count = pageInfo.total;

                    layui.use(['laypage', 'layer'], function () {
                        laypage = layui.laypage
                            , layer = layui.layer;

                        laypage.render({
                            elem: 'test1',
                            limits: [10, 20, 50]
                            , count: count
                            , layout: ['prev', 'page', 'next', 'limit',]
                            , theme: '#4184d4'
                            , jump: function (obj) {

                                var currentPage = obj.curr;
                                var pageSize = obj.limit;

                                var deviceId = getQueryString("deviceId");

                                getDeviceLogList(currentPage, pageSize, deviceId);

                            }
                        });
                    })

                }
            }
        });

    }

    var laypage;
    var layer;

    var deviceId = getQueryString("deviceId");

    refresh_page(1, 10, deviceId);

    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //日期时间选择器
        laydate.render({
            elem: '#test_go'
            , type: 'datetime'
            , theme: '#393D49'
        });
    });

    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //日期时间选择器
        laydate.render({
            elem: '#test_new'
            , type: 'datetime'
            , theme: '#393D49'
        });
    });

    //---------------------------------------

    //顶部图片旋转
    window.onload = function () {
        var n = 0;
        setInterval(function () {
            n++;
            var deg = 5 * n;

            document.getElementById('indexHead').style.transform = "rotate(" + deg + "deg)";
            //兼容ie
            document.getElementById('indexHead').style.msTransform = "rotate(" + deg + "deg)";

        }, 50);

        var m = 0;
        setInterval(function () {
            m++;
            var de = 5 * m;
            document.getElementById('right_animation').style.transform = "rotate(" + de + "deg)";
            //兼容ie
            document.getElementById('right_animation').style.msTransform = "rotate(" + de + "deg)";
        }, 50);
    }

    $(function () {

        $.ajax({
            url: '${ctx}/log/getModuleLog?moduleName=城管门户页面',
            success: function (data) {
                if (data.returnCode == 1) {

                    var totalVisited = data.result.totalVisited;
                    var todayVisited = data.result.todayVisited;

                    $("#today_visit").html(todayVisited);
                    $("#all_visit").html(totalVisited);
                }
            }
        });

        var deviceId = getQueryString("deviceId");

        getDeviceLogList(1, 10, deviceId);

    })

    var go_detail = function () {

        var deviceId = getQueryString("deviceId");
        window.location = '${ctx}/czcg/detail?deviceId=' + deviceId;
    }


    var getDeviceLogList = function (currentPage, pageSize, deviceId) {

        $.ajax({
            url: '${ctx}/czcg/getDeviceLogListByDeviceId?pageIndex=' + currentPage + '&pageSize=' + pageSize + "&deviceId=" + deviceId,
            success: function (data) {
                if (data.returnCode == 1) {

                    var pageInfo = data.result;

                    var list = pageInfo.list;

                    var html_list = get_log_html(currentPage, pageSize, list);

                    $("#log_list").html(html_list);
                }
            }
        });
    }

//    var get_log_html = function (current_page, pageSize, list) {
//
//        var html_list = '<div class="title" style="margin-top: 1%"><ul><li>序号</li><li>时间</li><li>移动超限报警</li><li>x轴倾斜数值</li><li>y轴倾斜数值</li><li>z轴倾斜数值</li></ul>';
//        for (i = 0; i < list.length; i++) {
//
//            var log = list[i];
//
//            var temp_html = '<div class="cont"> <ul><li>' + ((current_page - 1) * pageSize + (i + 1)) + '</li>';
//            temp_html += '<li>' + log.time + '</li>';
////            temp_html += '<li class="' + (log.move == 1 ? 'warn' : 'alarm') + '">' + (log.move == 1 ? '报警' : '无报警') + '</li>';
//
//            temp_html += '<li class="' + (is_alarm(log.x.toFixed(2), log.y.toFixed(2), log.z.toFixed(2)) == 1 ? 'warn' : 'alarm') + '">' + (is_alarm(log.x.toFixed(2), log.y.toFixed(2), log.z.toFixed(2)) == 1 ? '报警' : '无报警') + '</li>';
//
//            temp_html += '<li class="color">' + log.x.toFixed(2) + '</li>';
//            temp_html += '<li class="color">' + log.y.toFixed(2) + '</li>';
//            temp_html += '<li class="color">' + log.z.toFixed(2) + '</li>';
//            temp_html += '</ul></div>';
//
//            html_list += temp_html;
//        }
//
//        return html_list;
//
//    }

    var get_log_html = function (current_page, pageSize, list) {

        var html_list = '<div class="title" style="margin-top: 1%"><ul><li>序号</li><li>时间</li><li>移动超限报警</li><li>x轴倾斜数值</li><li>y轴倾斜数值</li><li>z轴倾斜数值</li></ul>';
        for (i = 0; i < list.length; i++) {

            var log = list[i];

            var temp_html = '<div class="cont"> <ul><li>' + ((current_page - 1) * pageSize + (i + 1)) + '</li>';
            temp_html += '<li>' + log.time + '</li>';
//            temp_html += '<li class="' + (log.move == 1 ? 'warn' : 'alarm') + '">' + (log.move == 1 ? '报警' : '无报警') + '</li>';

//            if (getQueryString("deviceId") == '10826047') {
//                log.x = parseFloat(log.x.toFixed(2)) + 4;
//                log.y = parseFloat(log.y.toFixed(2)) + 4;
//                log.z = parseFloat(log.z.toFixed(2)) + 4;
//            }else{
//                log.x = log.x.toFixed(2);
//                log.y = log.y.toFixed(2);
//                log.z = log.z.toFixed(2);
//            }

            temp_html += '<li class="' + (is_alarm(log.x.toFixed(2), log.y.toFixed(2), log.z.toFixed(2)) == 1 ? 'warn' : 'alarm') + '">' + (is_alarm(log.x.toFixed(2), log.y.toFixed(2), log.z.toFixed(2)) == 1 ? '报警' : '无报警') + '</li>';

            temp_html += '<li class="color">' + log.x.toFixed(2)  + '</li>';
            temp_html += '<li class="color">' + log.y.toFixed(2) + '</li>';
            temp_html += '<li class="color">' + log.z.toFixed(2) + '</li>';
            temp_html += '</ul></div>';

            html_list += temp_html;
        }

        return html_list;

    }

    var is_alarm = function (x, y, z) {

        if (Math.abs(x) > 5 || Math.abs(y) > 5 || Math.abs(z) > 5) {
            return 1;
        } else {
            return 0;
        }

    }

</script>
</body>
</html>
