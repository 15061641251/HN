<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>常州市户外广告倾覆预警平台</title>

    <jsp:include page="../../common/header.jsp"/>

    <link rel="stylesheet" href="${ctx}/module/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/module/css/page7.css">
    <link rel="stylesheet" href="${ctx}/module/css/weather.css">
    <link href="${ctx}/module/css/style.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/common/layui/css/layui.css">
    <script type="text/javascript" src="${ctx}/module/js/echarts.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/module/js/WdatePicker.js"></script>
    <%--<script type="text/javascript" src="${ctx}/module/js/jquery.leoweather.min.js"></script>--%>

    <script type="text/javascript" src="${ctx}/common/layui/layui.js"></script>


    <style>
        * {
            margin: 0;
            padding: 0;

        }

        html, body {
            width: 100%;
            height: 100%;
            background: #ffffff;
            background: url("${ctx}/module/img/bg.png");
            background-size: 100% 100%;
        }

        #main1 canvas {
            /*width: 382px !important;*/
            padding-top: 20px;
            color: white;
            background: url('${ctx}/module/img/bg.png');
            background-size: 100% 100%;
        }

        #main2 canvas {
            width: 392px !important;
            padding-top: 20px;
            color: white;
            background: url('${ctx}/module/img/bg.png');
            background-size: 100% 100%;
        }

        #main5 canvas {
            width: 100%;
            padding-top: 20px;
            color: white;
        }

        .main5 {
            background: url('${ctx}/module/img/bg.png');
            background-size: 100% 100%;
        }

        #main3 {
            background: url('${ctx}/module/img/bg.png');
            background-size: 100% 100%;
        }

        #main3 .left_botom1 {
            width: 65%;
            height: 100%;
            float: left;
            background: url("${ctx}/module/img/pingmu5.png") center center no-repeat;
            background-size: cover;
            position: absolute;
        }

        div.number2 {
            margin-left: -11px;
            float: left
        }

        li {
            letter-spacing: 2px;
            /*border-bottom: 1px solid #14f0f1;*/
            height: 26px;
            line-height: 26px;
            color: #14f0f1;
            margin-top: 8px;
            text-align: right;
        }

        ul.list2 li {
            letter-spacing: 2px;
            border-bottom: none;

            color: #14f0f1;
            text-align: right;
        }

        li a {
            width: 48%;
            float: left;
            color: #14f0f1 !important;
            text-align: left;
            text-decoration: none;
            border-bottom: 1px solid #14f0f1;
        }

        li span {
            display: inline-block;
            border-bottom: 1px solid #14f0f1;
            padding: 0 5px 0 0;
        }

        /*第一张*/
        li.grad1 {
            border-radius: 4px;
            height: 6px;
            margin-bottom: 10px;
            background: -webkit-linear-gradient(left, #36de24, #1ed8c7); /* Safari 5.1 - 6.0 */
            background: -o-linear-gradient(right, #36de24, #1ed8c7); /* Opera 11.1 - 12.0 */
            background: -moz-linear-gradient(right, #36de24, #1ed8c7); /* Firefox 3.6 - 15 */
            background: linear-gradient(to right, #36de24, #1ed8c7);
        }

        li.grad2 {
            border-radius: 4px;
            height: 6px;
            margin-bottom: 10px;
            background: -webkit-linear-gradient(left, #16dce9, #3078c0); /* Safari 5.1 - 6.0 */
            background: -o-linear-gradient(right, #16dce9, #3078c0); /* Opera 11.1 - 12.0 */
            background: -moz-linear-gradient(right, #16dce9, #3078c0); /* Firefox 3.6 - 15 */
            background: linear-gradient(to right, #16dce9, #3078c0);
        }

        li.grad3 {
            border-radius: 4px;
            height: 6px;
            margin-bottom: 10px;
            background: -webkit-linear-gradient(left, #16dce9, #d4d735); /* Safari 5.1 - 6.0 */
            background: -o-linear-gradient(right, #16dce9, #d4d735); /* Opera 11.1 - 12.0 */
            background: -moz-linear-gradient(right, #16dce9, #d4d735); /* Firefox 3.6 - 15 */
            background: linear-gradient(to right, #16dce9, #d4d735);
        }

        /*-------------------天气预报-------------------------*/

        /*body  { font:14px/1.5 微软雅黑; text-align:center; }*/
        /*table {*/
        /*border: 1px solid #DDD;*/
        /*border-collapse: collapse;*/
        /*width: 980px;*/
        /*margin-top: 20px;*/
        /*}*/

        /*thead,*/
        /*tfoot {*/
        /*background: #FAFAFA;*/
        /*}*/

        /*td, th {*/
        /*border: 1px solid #DDD;*/
        /*text-align: left;*/
        /*font-weight: normal;*/
        /*padding: 15px;*/
        /*}*/

        /*th,*/
        /*.demo {*/
        /*width: 100px;*/
        /*min-width: 100px;*/
        /*max-width: 100px;*/
        /*}*/

        /*td {*/
        /*width: 680px;*/
        /*min-width: 680px;*/
        /*max-width: 680px;*/
        /*}*/

        #demo {
            width: 390px;
            /*height: 233px;*/
            height: 100%;
            /*background: #72af3c;*/
            color: #FFF;
            background: url(${ctx}/module/img/bg.png);
            background-size: 100% 100%;
            /*padding: 50px 0 100px 0;*/
            overflow: hidden;
            /*border-radius: 5000px;*/
        }

        #demo i {
            background: no-repeat top left;
            display: inline-block;
            height: 128px;
            line-height: 128px;
            margin: 41px auto 20px 35px;
            font-size: 70px;
            padding-left: 170px;
            font-style: normal;
            text-align: center;
            font-weight: bold;
        }

        #demo i.icon-xiaoyu {
            background-image: url(${ctx}/module/img/icon/xiaoyu.png);
        }

        #demo i.icon-zhongyu {
            background-image: url(${ctx}/module/img/icon/zhongyu.png);
        }

        #demo i.icon-dayu {
            background-image: url(${ctx}/module/img/icon/dayu.png);
        }

        #demo i.icon-qing {
            background-image: url(${ctx}/module/img/icon/qing.png);
        }

        #demo i.icon-duoyun {
            background-image: url(${ctx}/module/img/icon/duoyun.png);
        }

        #demo i.icon-yin {
            background-image: url(${ctx}/module/img/icon/yin.png);
        }

        #demo i.icon-zhenyu {
            background-image: url(${ctx}/module/img/icon/zhenyu.png);
        }

        #demo p {
            /*background: rgba(0, 0, 0, .1);*/
            /* margin: 45px 247px 0px 0px; */
            width: 344px;
            padding: 18px 0 18px 0;
            text-align: center;
            margin: 0 auto;
            border-radius: 1000px;
            font-size: 16px;
        }

        #demo p span {
            margin: 0 15px;
        }

        #demo2 {
            width: 980px;
            margin: 0 auto;
            margin-top: 20px;
            background: #fafafa;
            border: 1px solid #ddd;
            padding: 30px 0;
            overflow: hidden;
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
            <img onclick="window.location='${ctx}/czcg'" style="cursor: pointer;" class="head_left"
                 src="${ctx}/module/img/slicing/bb.png">
            <img onclick="window.location='${ctx}/czcg'" style="cursor: pointer;" class="head_rotate" id="indexHead"
                 src="${ctx}/module/img/round.png">
            <span onclick="window.location='${ctx}/czcg'" style="cursor: pointer;z-index:600">首页</span>
        </div>
        <div>
            <!--<img src="${ctx}/module/img/head_tit_l.png">-->
            <span>常州市户外广告倾覆预警平台</span>
            <img class="img_content" src="${ctx}/module/img/slicing/headline_bg.png" alt="##">
            <!--<img src="${ctx}/module/img/head_tit_r.png">-->
        </div>
        <div>
            <img onclick="window.location='${ctx}/czcg/list'" style="cursor: pointer;" class="head_right"
                 src="${ctx}/module/img/slicing/aa.png">
            <img onclick="window.location='${ctx}/czcg/list'" style="cursor: pointer;" class="headRotate_right"
                 id="right_animation" src="${ctx}/module/img/round.png">
            <span onclick="window.location='${ctx}/czcg/list'" style="cursor: pointer;z-index:99999">大屏信息查询</span>
        </div>
        <img class="work_cont" src="${ctx}/module/img/slicing/form_bg.png" alt="###">
        <div class="clear"></div>
    </div>
    <div class="page-content">
        <span class="bigMsg">大屏信息详情</span>

        <div class="top1">
            <div class="top_left1">
                <!--main5-->
                <div style="width: 100%;height: 100%;;float: left;position: relative" class="main5">
                    <div id="main5" class="" style="width:58%;height: 100%;;float: left">

                    </div>
                    <div class="stand">
                        <p style="position: absolute;left:5%;top:72%;color:#2ced0f">建议关注</p>
                        <p style="position: absolute;left: 1%;top: 21%;color:#1aa8ec">建议检修</p>
                        <p style="position: absolute;left:42%;top:72%;color:#fadf08">建议拆除</p>
                        <p style="position: absolute;left: 11%;top: 50%;;font-size: 16px;font-weight:bold;color: #347cd2">
                            5</p>
                        <p style="position: absolute;left: 15%;top: 31%;font-size: 16px;font-weight: bold;color: #347cd2">
                            10</p>
                        <p style="position: absolute;left:26%;top:82%;font-size: 28px;color: #14f0f1" id="content1">
                            X</p>


                        <!--<p style="position: absolute;left:26%;top:79%;font-size: 38px;color: #14f0f1;display: none" id="content2"  class="content">Y</p>-->
                        <!--<p style="position: absolute;left:26%;top:79%;font-size: 38px;color: #14f0f1;display: none" id="content3"  class="content">Z</p>-->
                        <p style="position: absolute;left:12%;top:74%;font-size: 48px;font-weight: bold;color: #14f0f1"
                           id="zuo">
                            <img src="${ctx}/module/img/zuo.png" alt="" style="cursor: pointer;width: 22px"></p>
                        <p style="position: absolute;left:39%;top:74%;font-size: 48px;font-weight: bold;color: #14f0f1">
                            <img src="${ctx}/module/img/you.png" alt="" style="cursor: pointer;width: 22px" id="you">
                        </p>

                    </div>
                    <div class="right_content"
                         style="position: relative;width: 40%;margin: 0 1%;padding:8% 0 0 0;float: left;font-size: 14px;">
                        <p style="color:white;text-align: center;">倾斜角度(Tilt Angle)</p>
                        <ul class="list2"
                            style="width: 94%;margin: 0 3%;float: left;list-style-type: none;font-size: 14px">
                            <li><a class="jiyi" style="margin-bottom: 4px;border-bottom: none;width: 49%;">建议关注</a>(0~5°)
                            </li>
                            <li class="grad1"></li>
                            <li><a class="jiyi" style="margin-bottom: 4px;border-bottom: none;width: 49%;">建议检修</a>(5~10°)
                            </li>
                            <li class="grad2"></li>
                            <li><a class="jiyi" style="margin-bottom: 4px;border-bottom: none;width: 49%;">建议拆除</a>(10~90°)
                            </li>
                            <li class="grad3"></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="top_center1">
                <section>
                    <div id="today_container">
                        <div>
                            <img />
                        </div>
                        <div>
                            <div class="main_info"><span class="info">城市</span><span
                                    class="info"></span><span class="info"
                                                                         style="margin-left: 0px;"></span><span
                                    class="info"></span></div>
                            <div class="more_info">今日温度：<span class="info"></span>风力：<span
                                    class="info"></span></div>
                        </div>
                    </div>
                </section>
            </div>
            <div class="top_right1">
                <!--main2-->
                <div class="main4" style="width: 100%;height: 100%;float: left;position: relative">
                    <div id="main2" class="" style="width:100%;height: 100%;"></div>
                    <p style="position: absolute;left:4%;top:16%;color: #14f0f1">(角度)</p>
                    <div style="background: #244463;border-radius: 20px;position: absolute;top:4%;left:4%;float: left;height: 24px">
                        <p id="select1"
                           style="width:93px;height: 24px;font-size: 14px;line-height: 24px;float: left;border-radius: 20px;cursor: pointer;text-align: center;;color: white;background: #347cd2">
                            角度实时统计</p>
                        <%--<p id="select2"--%>
                           <%--style="width:95px;color: #347cd2;height: 24px;;font-size: 14px;line-height: 24px;float: left;border-radius: 20px;;cursor: pointer;text-align: center;">--%>
                            <%--历史危险查询</p>--%>
                    </div>
                    <%--<div style="position: absolute;top:4%;left:56%;float: left;">--%>
                        <%--<span style="color:#fff;float:left;;display: block;height: 26px;line-height: 26px ">时间选择：</span>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<input type="text"--%>
                                   <%--style="width:93px;height: 26px;border: none;background: #204267;color: white;line-height: 20px; float: left;margin-left: 4%;margin-left:3%\9"--%>
                                   <%--class="layui-input" id="test30"--%>
                                   <%--value="">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                </div>
            </div>
        </div>
        <div class="bottom" style="position: relative">
            <!--main3-->
            <div id="main3" class="" style="width: 100%;height: 100%;;float: left;">
                <div class="left_botom1" style="width: 80%;position: relative">
                    <!--第一个大屏-->
                    <div style="width: 160px;height:70px;background: rgba(255,255,255,0.3);border: 1px solid white;position: absolute;left:11%;top:19%; position: relative"></div>
                    <!--<p style="position: absolute;left:26%;top:74%;font-size: 48px;font-weight: bold;color: #14f0f1">y</p>-->
                    <div style="width: 20px;height:100px;background: rgba(255,255,255,0.3);border: 1px solid white;position: absolute;left:18%;top: 47%;"></div>
                    <div style="position: absolute;top: 25%;left: 30%;">
                        <img src="${ctx}/module/img/shi_y.png" alt="">
                    </div>
                    <!--角度-->
                    <!--<p style="width: 150px;border: 1px dashed white;position: absolute;left:22%;top:41%;border-radius: 40px"></p>-->
                    <!--<p style="width: 145px;border: 1px dashed white;position: absolute;left:22%;top:41%;transform:rotate(-10deg); transform-origin:0px 0px; -ms-transform: rotate(-10deg);"></p>-->
                    <!--圆弧    -->
                    <!--<canvas id="myCanvas" width="400px" height="200px"-->
                    <!--style="margin-top: 100px;position: absolute;left:-11%;top:-41%;transform:rotate(180deg)"></canvas>-->

                    <!--度数    -->
                    <p style="position: absolute;left:16.7%;top:85%;font-size: 30px;color: #14f0f1">X轴</p>
                    <div id="clock" class="light"
                         style="position: absolute;top: 24%;left: 15%;margin: 10px 0 0 0;width: 50%;">
                        <div class="display">
                            <div class="digits" id="axis_x"
                                 style="height:28px;width: 100%;margin-bottom: 10px;margin-left: 20px;">
                            </div>
                        </div>
                    </div>

                    <!--第二个大屏-->
                    <div style="width: 125px;height:70px;background: rgba(255,255,255,0.3);border: 1px solid white;position: absolute;left:44%;top:19%;"></div>
                    <!--<p style="position: absolute;left:26%;top:74%;font-size: 48px;font-weight: bold;color: #14f0f1">y</p>-->
                    <div style="width: 20px;height:100px;background: rgba(255,255,255,0.3);border: 1px solid white;position: absolute;left:49%;top:47%;"></div>
                    <div style="position: absolute;top:28%;left: 59%;">
                        <img src="${ctx}/module/img/shi_x.png" alt="">
                    </div>
                    <!--角度-->
                    <!--<p style="width: 150px;border: 1px dashed white;position: absolute;left:53%;top:41%;border-radius: 40px"></p>-->
                    <!--<p style="width: 145px;border: 1px dashed white;position: absolute;left:53%;top:41%;transform:rotate(-10deg);transform-origin:0px 0px"></p>-->
                    <!--<p style="width:10px;height: 10px;position: absolute;left:58%;top:37%;border-radius:  0 100% 0 0;border: 2px dashed white;border-left:none;border-bottom: none">-->
                    <!--圆弧    -->
                    <!--<canvas id="myCanvas1" width="400px" height="200px"-->
                    <!--style="margin-top: 100px;position: absolute;left:20%;top:-41%;transform:rotate(180deg)">-->
                    <!--</canvas>-->
                    <!--度数    -->
                    <p style="position: absolute;left:47.7%;top:85%;font-size: 30px;color: #14f0f1">Y轴</p>
                    <div id="clock" class="light"
                         style="position: absolute;top: 24%;left: 45.2%;margin: 10px 0 0 0;width: 50%;">
                        <div class="display">

                            <div class="digits" id="axis_y"
                                 style="height:28px;width: 150px;margin-bottom: 10px;margin-left: 20px;">
                            </div>
                        </div>
                    </div>

                    <!--第三个大屏-->
                    <div style="width: 130px;height:78px;background: rgba(255,255,255,0.3);border: 1px solid white;position: absolute;left:73%;top:17%;transform:rotate(9deg);-ms-transform: rotate(9deg);"></div>
                    <!--<p style="position: absolute;left:26%;top:74%;font-size: 48px;font-weight: bold;color: #14f0f1">y</p>-->
                    <div style="width: 20px;height:100px;background: rgba(255,255,255,0.3);border: 1px solid white;position: absolute;left:78%;top:47%;">

                    </div>
                    <div style="position: absolute;top:25%;left: 89%;">
                        <img src="${ctx}/module/img/duobian.png" alt="">
                    </div>

                    <!--角度-->
                    <!--<p style="width: 150px;border: 1px dashed white;position: absolute;left:82%;top:41%;border-radius: 40px"></p>-->
                    <!--<p style="width: 145px;border: 1px dashed white;position: absolute;left:82%;top:41%;transform:rotate(-10deg);transform-origin:0px 0px"></p>-->
                    <!--<p style="width:10px;height: 10px;position: absolute;left:87%;top:37%;border-radius:  0 100% 0 0;border: 2px dashed white;border-left:none;border-bottom: none">-->
                    <!--<canvas id="myCanvas2" width="400px" height="200px"-->
                    <!--style="margin-top: 100px;position: absolute;left:49%;top:-41%;transform:rotate(180deg)">-->
                    <!--</canvas>-->
                    <!--度数    -->
                    <p style="position: absolute;left:76.8%;top:85%;font-size: 30px;color: #14f0f1">Z轴</p>
                    <div id="clock" class="light"
                         style="position: absolute;top: 24%;left: 75.5%;margin: 10px 0 0 0;width: 50%;">
                        <div class="display">

                            <div class="digits" id="axis_z"
                                 style="height:28px;width: 100%;margin-bottom: 10px;margin-left: 10px;">
                            </div>
                        </div>
                    </div>
                    <p style="position: absolute;left:98%;top:66%;font-size: 12px;color: #fff"></p>
                    <p style="position: absolute;left:98%;top:44%;font-size: 12px;color: #fff"></p>
                    <p style="position: absolute;left:98%;top:22%;font-size:12px;color: #fff"></p>
                    <p style="position: absolute;left:98%;top:0%;font-size: 12px;color: #fff"></p>
                    <p style="position: absolute;left:1%;top:3%;font-size: 12px;color: #fff"> 倾斜角度(Tilt Angle)</p>
                </div>
                <div class="right_content"
                     style="width: 16%;margin: 0 2%;padding:35px 0 0 0;float: left;font-size: 14px;">
                    <!--<p style="color:white;text-align: center">倾斜角度(Tilt Angle)</p>-->
                    <ul class="list" style="width: 94%;margin: 0 3%;float: left;list-style-type: none;font-size: 12px">
                        <li style="text-align: center;border-bottom: none;color: white;font-size: 14px;margin: 6px 0 0  0">
                            大屏基本信息(Info)
                        </li>
                        <li style="text-align: left"><a>区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域:</a><span
                                id="info_area"></span></li>
                        <br>
                        <li style="text-align: left"><a>位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置:</a><span
                                style="" id="info_address"></span></li>
                        <br>
                        <br>
                        <li style="text-align: left"><a>广告牌类型:</a><span id="type_name"></span></li>
                        <br>
                        <%--<li style="text-align: left"><a>屏幕尺寸:</a><span>10x10(m)</span></li>--%>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<!--正负数-->
<script type="text/javascript" language="javascript">
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


    var num = 0, size = 1;

    function addNumber() {
        var clock = document.getElementById("mydemo").children[0];
        clock.className = "clock c" + (++num % 10);
        return false;
    }

    function resize() {
        var clock = document.getElementById("mydemo").children[0];
        clock.style.fontSize = (++size % 20) + "px";
        return false;
    }

    //    var aaa=$('#b').css('display');
    ////    alert(aaa)
    //    if(aaa ==='block'){
    //        $('#one').css('margin-left','0%')
    //
    //    }else{
    //        $('#one').css('margin-left','8%')
    //    }
    //    var bbb=$('#b1').css('display');
    //    //    alert(aaa)
    //    if(bbb ==='block'){
    //        $('#one1').css('margin-left','0%')
    //
    //    }else{
    //        $('#one1').css('margin-left','0%')
    //    }
</script>
<!--4切换-->

<script type="text/javascript" src="${ctx}/module/js/index.js?v=1.1"></script>
<script>

    //今天的时间
    var day2 = new Date();
    day2.setTime(day2.getTime());
    var s2 = day2.getFullYear() + "年" + (day2.getMonth() + 1) + "月" + day2.getDate() + "日";
    var s22 = day2.getFullYear() + "-" + (day2.getMonth() + 1) + "-" + day2.getDate();
    //拼接时间
    var str1 = s2
    var strtoday = s22;
    //赋值doubleDate
    // document.getElementById("test30").value = strtoday;
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //自定义颜色
        laydate.render({
            elem: '#test30'
            , theme: '#393D49'
        });
    });

    function GetDateStr(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
        var y = dd.getFullYear();
        var m = dd.getMonth() + 1;//获取当前月份的日期
        var d = dd.getDate();
        return y + "-" + m + "-" + d;
    }

    $('#date').html("" + GetDateStr(0));

    $('#select1').click(function () {
        $(this).css({'background': '#347cd2', 'color': '#fff'})
        $(this).siblings('#select2').css({'background': '#244463', 'color': '#347cd2'})
    })
    $('#select2').click(function () {
        $(this).css({'background': '#347cd2', 'color': '#fff'})
        $(this).siblings('#select1').css({'background': '#244463', 'color': '#347cd2'})
    })

    $('#you').click(function () {
        var aaa = $('#content1').text().trim();
        if (aaa == "X") {
            $('#content1').html('Y')
        }
        if (aaa == "Y") {
            $('#content1').html('Z')
        }
        if (aaa == "Z") {
            $('#content1').html('X')
        }
        refresh_table();
    })
    $('#zuo').click(function () {
        var aaa = $('#content1').text().trim();
        if (aaa == "X") {
            $('#content1').html('Z')
        }
        if (aaa == "Y") {
            $('#content1').html('X')
        }
        if (aaa == "Z") {
            $('#content1').html('Y')
        }
        refresh_table();

    })
</script>

<!--main2 3-->
<script type="text/javascript">

    //左侧码表
    var myChart5 = echarts.init(document.getElementById('main5'));
    //左侧码表配置
    option5 = {
        tooltip: {
            formatter: "{a} <br/>角度 : {c}°"
        },
        series: [
            {
                name: '指标',
                type: 'gauge',
                min: 0,
                max: 20,
                splitNumber: 1,

                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: [[0.15, '#2ced0f'], [0.35, '#1aa8ec'], [1, '#fadf08']],
                        width: 8,

//                        shadowColor : '#fff', //默认透明
//                        shadowBlur: 10

                    }
                },
                axisLabel: {            // 坐标轴小标记
                    textStyle: {       // 属性lineStyle控制线条样式
                        fontWeight: 'bolder',
                        color: '#347cd2',
                        fontSize: 14,
                        shadowColor: '#fff', //默认透明
                        shadowBlur: 10
                    }
                },
                axisTick: {            // 坐标轴小标记
                    length: 15,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto',
                        shadowColor: '', //默认透明

                    }
                },
                splitLine: {           // 分隔线
                    length: 10,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        width: 1,
                        color: '',
                        shadowColor: '', //默认透明
                    }
                },
                pointer: {           // 分隔线
                    shadowColor: '', //默认透明
                    shadowBlur: 5
                },
                title: {
//                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
//                        fontWeight: 'bolder',
//                        fontSize: 20,
//                        fontStyle: 'italic',
//                        color: '#000',
//                        shadowColor : '#fff', //默认透明
//                        shadowBlur: 10
//                    }
                },

                detail: {
//                    shadowColor : '#000', //默认透明
                    offsetCenter: [0, '40%'],       // x, y，单位px
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        color: '#347cd2',
                        fontSize: 20,
                    }
                },
                data: [{value: 0, name: ''}]
            }
        ]
    };
    myChart5.setOption(option5, true);
    // 基于准备好的dom，初始化echarts实例

    //右侧实时角度统计
    var angle_statistics = function () {


        var xAxis_data = [];

        var series_x = [];
        var series_y = [];
        var series_z = [];


        var device_id = getQueryString("deviceId");

        $.ajax({
            url: '${ctx}/czcg/getBrokenLineDeviceLogByTime?deviceId=' + device_id,
            success: function (data) {
                if (data.returnCode == 1) {

                    xAxis_data = data.result.xAxis_data;

                    series_x = data.result.series_x;
                    series_y = data.result.series_y;
                    series_z = data.result.series_z;

                    var myChart2 = echarts.init(document.getElementById('main2'));

                    // 指定图表的配置项和数据

                    option = {

                        color: ['dark'],

                        title: {
//            backgroundColor: '#ff0000',
                            //背景
                            text: '倾斜角度统计',
                            textStyle: {
                                fontWeight: 'normal',
                                fontSize: 14,
                                //标题颜色
                                color: '#fff',

                            },
//            subtext: '纯属虚构'
                            y: '17%',
                            x: '35%',
                        },
//        tooltip : {
//            trigger: 'axis',
//            axisPointer : {
//                // 坐标轴指示器，坐标轴触发有效
//                type : 'line',
//                color: ['#fff'],
//                // 默认为直线，可选为：'line' | 'shadow'
//            }
//        },
                        // 提示框
                        tooltip: {
                            trigger: 'axis',           // 触发类型，默认数据触发，见下图，可选为：'item' ¦ 'axis'
                            showDelay: 20,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                            hideDelay: 100,            // 隐藏延迟，单位ms
                            transitionDuration: 0.4,  // 动画变换时间，单位s
                            backgroundColor: 'rgba(0,0,0,0.7)',     // 提示背景颜色，默认为透明度为0.7的黑色
                            borderColor: '#333',       // 提示边框颜色
                            borderRadius: 4,           // 提示边框圆角，单位px，默认为4
                            borderWidth: 0,            // 提示边框线宽，单位px，默认为0（无边框）
                            padding: 5,                // 提示内边距，单位px，默认各方向内边距为5，
                                                       // 接受数组分别设定上右下左边距，同css
                            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                                type: 'line',         // 默认为直线，可选为：'line' | 'shadow'
                                lineStyle: {          // 直线指示器样式设置
                                    color: 'rgba(48,126,211,0.5)',
                                    width: 1,
                                    type: 'solid'
                                },
                                shadowStyle: {                       // 阴影指示器样式设置
                                    width: 'auto',                   // 阴影大小
                                    color: 'rgba(48,126,211,0.5)'  // 阴影颜色
                                }
                            },
                            textStyle: {
                                color: '#fff'
                            }
                        },
                        legend: {
//           data:systemName,
                            itemWidth: 40,
                            itemHeight: 20,
                            textStyle: {
                                fontSize: 15,
                                color: '#fff'
                            }
                        },
                        toolbox: {
                            show: true,
                            feature: {
//                mark : {show: true},
////                dataView : {show: true, readOnly: false},
//                magicType : {show: true, type: ['line', 'bar']},
//                restore : {show: true},
//                saveAsImage : {show: true}
                            }
                        },
                        // 网格
                        grid: {
                            x: 40,
                            y: 70,
                            x2: 20,
                            y2: 25,
                            // width: {totalWidth} - x - x2,
                            // height: {totalHeight} - y - y2,

                        },
                        calculable: true,
                        xAxis: [
                            {
//                axisLine: {
//                    show: false
//                },
                                axisLine: {
                                    lineStyle: {
                                        color: '#347cd2',
//                            width:8,//这里是为了突出显示加上的，可以去掉
                                    }
                                },
                                axisLabel: {
                                    textStyle: {
                                        color: '#fff',
                                        x: '15%'

                                    },

                                },
                                y: '25%',
                                x: '145px',
                                axisTick: {show: false, length: 21},

//                type : 'value',
                                splitLine: {
                                    lineStyle: {
                                        color: ['#fff'],
                                        type: 'line'
                                    }
                                },
                                type: 'category',
                                boundaryGap: true,

                                data: xAxis_data

                            },


                        ],
                        yAxis: [
                            {
                                axisLine: {
                                    lineStyle: {
                                        color: '#347cd2',
//                            width:8,//这里是为了突出显示加上的，可以去掉
                                    }
                                },
//                axisLabel: {show: false},
                                axisTick: {show: false},
                                //标题颜色
                                textStyle: {
                                    color: '#fff'
                                },

                                'type': 'value',

                                color: '#14f0f1',
                                boundaryGap: true,
                                splitLine: {
                                    lineStyle: {
                                        color: 'rgba(49,126,208,0.8)',

                                    }
                                },
                                min: 0,
                                max: 10,
                                interval: 5,
                                axisLabel: {
                                    textStyle: {
                                        color: function (value) {
//                            //Echarts对数据做了处理, 1000 => 1,000
                                            var number = Number(value.replace(",", ""));
                                            return number >= 0 ? "white" : (number < 0) ? "green" : "#333";
                                        }
                                    }
                                }
                            }
                        ],

//        series : [
//            {
//                name:'数据1',
//                type:'line',
//                smooth:true,
//                data:[5, 10,6,7,10,8,5, 10,7,6,10,],
//
//            },
//
//        ]
                        series: [
                            {
                                name: 'X轴',
                                type: 'line',
                                data: series_x,
                            },
                            {
                                name: 'Y轴',
                                type: 'line',
                                data: series_y,
                            },
                            {
                                name: 'Z轴',
                                type: 'line',
                                data: series_z,
                            },

                        ]
                    };


                    myChart2.setOption(option);
                }
            }
        });


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

        var device_id = getQueryString("deviceId");

        getDeviceData(device_id);

        getLogData(device_id);

        angle_statistics();
    })

    var x = '0.00';
    var y = '0.00';
    var z = '0.00';

    //获取最新数据
    var getLogData = function (device_id) {
        $.ajax({
            url: '${ctx}/czcg/getDeviceLogListByDeviceId?pageIndex=1&pageSize=1&deviceId=' + device_id,
            success: function (data) {
                if (data.returnCode == 1) {

                    var page = data.result;
                    var result = page.list[0];

                    x = Math.abs(result.x).toFixed(2).toString();
                    y = Math.abs(result.y).toFixed(2).toString();
                    z = Math.abs(result.z).toFixed(2).toString();

                    refresh_axis_view('axis_x', x);
                    refresh_axis_view('axis_y', y);
                    refresh_axis_view('axis_z', z);

                    refresh_table();
                }
            }
        });
    }

    //根据轴渲染码表
    var refresh_table = function () {

        var selected_table_axis = $('#content1').text().trim();

        if (selected_table_axis == 'X') {
            option5.series[0].data[0].value = x;
        } else if (selected_table_axis == 'Y') {
            option5.series[0].data[0].value = y;
        } else if (selected_table_axis == 'Z') {
            option5.series[0].data[0].value = z;
        }

        myChart5.setOption(option5, true);
    }

    //根据轴的html的id和小数字符串,进行相应轴的界面渲染
    var refresh_axis_view = function (axis_id, number_str) {

        var $axis = $("#" + axis_id);

        var total_div_html = '';

        for (var i = 0; i < number_str.length; i++) {

            var c = number_str.charAt(i);

            total_div_html += getAxis_template(c);

        }

        total_div_html += '<div style="width:8px;height:8px;border:2px solid #14f0f1;float:left;border-radius:6px;margin-top: 1px;margin-left:-9px"></div>';

        $axis.html(total_div_html);

    }

    //根据小数拆分后的数值返回对应的html模块
    var getAxis_template = function (c) {

        if (c == '.') {
            return '<div style="width: 4px;height: 4px;background:#14f0f1;float: left;margin-top: 19px;margin-left: -6px;margin-right: 15px;"></div>';
        } else if (c == '0') {
            return '<div class="zero number2"><span class="d1"></span><span class="d2"></span><span class="d3"></span><span class="d4"></span><span class="d5"></span><span class="d6"></span><span class="d7"></span></div>';
        } else if (c == '1') {
            return '<div class="one number2"><span class="d1"></span><span class="d2"></span><span class="d3"></span><span class="d4"></span><span class="d5"></span><span class="d6"></span><span class="d7"></span></div>';
        } else if (c == '2') {
            return '<div class="two number2"><span class="d1"></span><span class="d2"></span><span class="d3"></span><span class="d4"></span><span class="d5"></span><span class="d6"></span><span class="d7"></span></div>';
        } else if (c == '3') {
            return '<div class="three number2"><span class="d1"></span><span class="d2"></span><span class="d3"></span><span class="d4"></span><span class="d5"></span><span class="d6"></span><span class="d7"></span></div>';
        } else if (c == '4') {
            return '<div class="four number2"><span class="d1"></span><span class="d2"></span><span class="d3"></span><span class="d4"></span><span class="d5"></span><span class="d6"></span><span class="d7"></span></div>';
        } else if (c == '5') {
            return '<div class="five number2"><span class="d1"></span><span class="d2"></span><span class="d3"></span><span class="d4"></span><span class="d5"></span><span class="d6"></span><span class="d7"></span></div>';
        } else if (c == '6') {
            return '<div class="six number2"><span class="d1"></span><span class="d2"></span><span class="d3"></span><span class="d4"></span><span class="d5"></span><span class="d6"></span><span class="d7"></span></div>';
        } else if (c == '7') {
            return '<div class="seven number2"><span class="d1"></span><span class="d2"></span><span class="d3"></span><span class="d4"></span><span class="d5"></span><span class="d6"></span><span class="d7"></span></div>';
        } else if (c == '8') {
            return '<div class="eight number2"><span class="d1"></span><span class="d2"></span><span class="d3"></span><span class="d4"></span><span class="d5"></span><span class="d6"></span><span class="d7"></span></div>';
        } else if (c == '9') {
            return '<div class="nine number2"><span class="d1"></span><span class="d2"></span><span class="d3"></span><span class="d4"></span><span class="d5"></span><span class="d6"></span><span class="d7"></span></div>';
        }
    }

    //获取设备数据
    var getDeviceData = function (device_id) {
        $.ajax({
            url: '${ctx}/device/getDeviceDetailByDeviceId?deviceId=' + device_id,
            success: function (data) {
                if (data.returnCode == 1) {

                    var result = data.result;
                    var area_name = result.area_name;
                    var address = result.address;

                    $("#info_area").html(area_name);
                    $("#info_address").html(address);

                    var type_name = '';
                    if(result.type == 0){

                        type_name = '测试';

                    }else if(result.type == 1){

                        type_name = '铁路立交';

                    }else  if(result.type == 2){

                        type_name = '楼顶';

                    }else  if(result.type == 3){

                        type_name = '高炮';

                    }else  if(result.type == 4){

                        type_name = '外墙';

                    }else  if(result.type == 5){

                        type_name = '地面LED';

                    }

                    $("#type_name").html(type_name);
                }
            }
        });
    }

</script>
</html>