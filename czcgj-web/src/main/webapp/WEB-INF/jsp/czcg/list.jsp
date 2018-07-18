<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>常州市户外广告倾覆预警平台</title>

    <jsp:include page="../../common/header.jsp"/>

    <script src="${ctx}/module/js/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" href="${ctx}/module/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/module/css/page2.css">
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
            <img onclick="window.location='${ctx}/czcg'" style="cursor: pointer;z-index:300" class="head_left"
                 src="${ctx}/module/img/slicing/bb.png">
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

            <img onclick="window.location='${ctx}/czcg/tongji'" class="head_right"
                 src="${ctx}/module/img/slicing/aa.png">
            <img onclick="window.location='${ctx}/czcg/tongji'" class="headRotate_right" id="right_animation"
                 src="${ctx}/module/img/round.png">
            <span onclick="window.location='${ctx}/czcg/tongji'" style="cursor: pointer;z-index:99999">统计信息查询</span>
        </div>
        <img class="work_cont" src="${ctx}/module/img/slicing/form_bg.png" alt="###">
        <div class="clear"></div>
    </div>
    <div class="work" style="position: relative">


        <div class="periphery">
            <span class="bigMsg">大屏信息 </span>
            <div class="page-content">
                <%--<form class="layui-form" action="">--%>
                    <%--<div class="layui-inline">--%>
                        <%--<label class="layui-form-label" style="width: 100px;margin-top: 5%;">区域选择：</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="modules" lay-verify="required" lay-search="">--%>
                                <%--<option value="0">全部</option>--%>
                                <%--<option value="1">钟楼区</option>--%>
                                <%--<option value="2">新北区</option>--%>
                                <%--<option value="3">天宁区</option>--%>
                                <%--<option value="4">金坛区</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<div class="layui-inline">--%>
                        <%--<label class="layui-form-label" style="width: 100px;margin-top: 5%;">监督单位：</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="modules" lay-verify="required" lay-search="">--%>
                                <%--<option value="0">全部</option>--%>
                                <%--<option value="1">灯光办</option>--%>
                                <%--<option value="2">城管局</option>--%>
                                <%--<option value="3">考评处</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<div class="layui-inline">--%>
                        <%--<label class="layui-form-label" style="width: 100px;margin-top: 5%;">广告牌类型：</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="modules" lay-verify="required" lay-search="">--%>
                                <%--<option value="1">全部</option>--%>
                                <%--<option value="2">立柱式</option>--%>

                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<div class="layui-inline">--%>
                        <%--<label class="layui-form-label" style="width: 100px;margin-top: 5%;">倾斜等级：</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="modules" lay-verify="required" lay-search="">--%>
                                <%--<option value="0">全部</option>--%>
                                <%--<option value="1">建议整改或拆除</option>--%>
                                <%--<option value="2">建议关注</option>--%>
                                <%--<option value="3">建议检修</option>--%>

                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<button class="but" type="button">查询</button>--%>
                <%--</form>--%>
                    <br>

                <div class="title">
                    <ul>
                        <li>序号</li>
                        <li>区域</li>
                        <li>位置</li>
                        <li>监管单位</li>
                        <li>广告牌类型</li>
                        <!--<li>屏幕尺寸</li>-->
                        <li>x轴倾斜数值</li>
                        <li>y轴倾斜数值</li>
                        <li>z轴倾斜数值</li>
                        <li>倾斜等级</li>
                        <li>详情查询</li>
                    </ul>
                </div>
                <div id="list">
                    <%--<div class="cont">--%>
                    <%--<ul>--%>
                    <%--<li>1</li>--%>
                    <%--<li>天宁区</li>--%>
                    <%--<li>青龙高速交叉口</li>--%>
                    <%--<li>城管局</li>--%>
                    <%--<li>立柱式</li>--%>
                    <%--<!--<li>10*10（m）</li>-->--%>
                    <%--<c:forEach var="dto" items="${dtoList}">--%>
                    <%--<c:if test="${dto.device_id == '10826030'}">--%>
                    <%--<li class="color">${dto.x}°</li>--%>
                    <%--<li class="color">${dto.y}°</li>--%>
                    <%--<li class="color">${dto.z}°</li>--%>
                    <%--</c:if>--%>
                    <%--</c:forEach>--%>
                    <%--<li class="color">建议关注</li>--%>
                    <%--<li class="examine" deviceId="10826030">查询</li>--%>
                    <%--<li class="examine_1" deviceId="10826030">日志--%>
                    <%--</li>--%>
                    <%--</ul>--%>
                    <%--</div>--%>
                </div>
            </div>
            <div id="test1" style="text-align: center;">

            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${ctx}/common/layui/layui.js"></script>

<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate


    });
</script>

<script>

    var refresh_page = function (currentPage, pageSize) {

        var area_name = getQueryString2('area_name');

        $.ajax({
            url: '${ctx}/czcg/getDeviceListAndLastLog?pageIndex=' + currentPage + '&pageSize=' + pageSize + '&area_name='+ encodeURI(encodeURI(area_name)),
            success: function (data) {
                if (data.returnCode == 1) {

                    var pageInfo = data.result;
                    var count = pageInfo.total;

                    layui.use(['laypage', 'layer'], function () {
                        var laypage = layui.laypage
                            , layer = layui.layer;

                        laypage.render({
                            elem: 'test1',
                            limits: [10, 20]
                            , count: count
                            , layout: ['prev', 'page', 'next', 'limit',]
                            , theme: '#4184d4'
                            , jump: function (obj) {

                                var currentPage = obj.curr;
                                var pageSize = obj.limit;

                                getDeviceListAndLastLog(currentPage, pageSize);
                            }
                        });
                    })
                }
            }
        });
    }

    refresh_page(1, 10);

    var getDeviceListAndLastLog = function (currentPage, pageSize) {

        var area_name = getQueryString2('area_name');

        $.ajax({
            url: '${ctx}/czcg/getDeviceListAndLastLog?pageIndex=' + currentPage + '&pageSize=' + pageSize +'&area_name='+ encodeURI(encodeURI(area_name)),
            success: function (data) {
                if (data.returnCode == 1) {

                    var pageInfo = data.result;

                    var list = pageInfo.list;

                    if(list.length == 0){

                        $("#list").html('<center style="height: 30px;margin-top:10px;">暂无相关记录</center>');

                    }else{

                        var html_list = get_html(currentPage, pageSize, list);

                        $("#list").html(html_list);

                        bind_click();
                    }





                }
            }
        });
    }

    var get_html = function (current_page, pageSize, list) {

        var html_list = '';

        for (var i = 0; i < list.length; i++) {

            var log = list[i];

            var temp_html = '<div class="cont"><ul>';
            temp_html += '<li>' + ((current_page - 1) * pageSize + (i + 1)) + '</li>';
            temp_html += '<li>' + log.area_name + '</li>';
            temp_html += '<li title="' + log.address + '">' + log.address + '</li>';
            temp_html += '<li style="padding-left: 20px;">城管局</li>';

            var type_name = '';

            if(log.type == 0){

                type_name = '测试';

            }else if(log.type == 1){

                type_name = '铁路立交';

            }else  if(log.type == 2){

                type_name = '楼顶';

            }else  if(log.type == 3){

                type_name = '高炮';

            }else  if(log.type == 4){

                type_name = '外墙';

            }else  if(log.type == 5){

                type_name = '地面LED';

            }

            temp_html += '<li style="padding-left: 30px;">' + type_name + '</li>';

            temp_html += '<li style="padding-left: 30px;" class="' + (is_alarm(log.x.toFixed(2), log.y.toFixed(2), log.z.toFixed(2)) == 1 ? 'color_1' : 'color') + '">' + log.x.toFixed(2) + '°</li>';
            temp_html += '<li style="padding-left: 30px;" class="' + (is_alarm(log.x.toFixed(2), log.y.toFixed(2), log.z.toFixed(2)) == 1 ? 'color_1' : 'color') + '">' + log.y.toFixed(2) + '°</li>';
            temp_html += '<li style="padding-left: 30px;" class="' + (is_alarm(log.x.toFixed(2), log.y.toFixed(2), log.z.toFixed(2)) == 1 ? 'color_1' : 'color') + '">' + log.z.toFixed(2) + '°</li>';

            temp_html += '<li style="padding-left: 50px;" class="' + (is_alarm(log.x.toFixed(2), log.y.toFixed(2), log.z.toFixed(2)) == 1 ? 'color_1' : 'color') + '">' + (is_alarm(log.x.toFixed(2), log.y.toFixed(2), log.z.toFixed(2)) == 1 ? '建议整改' : '建议关注') + '</li>';
            temp_html += '<li style="padding-left: 30px;" class="examine" deviceId="' + log.device_id + '">详情</li>';
            temp_html += '&nbsp;&nbsp;<li class="examine_1" deviceId="' + log.device_id + '">日志</li>';
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

    //--------------------------------------

    var bind_click = function () {

        $('li.examine').click(function () {

            var deviceId = $(this).attr('deviceId');
            if (!isEmpty(deviceId))
                new_tab("${ctx}/czcg/detail?deviceId=" + deviceId);
        })

        $('li.examine_1').click(function () {

            var deviceId = $(this).attr('deviceId');
            if (!isEmpty(deviceId))
                new_tab("${ctx}/czcg/getDeviceListByDeviceId?deviceId=" + deviceId);
        })

    }

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
    })
</script>
</body>
</html>
