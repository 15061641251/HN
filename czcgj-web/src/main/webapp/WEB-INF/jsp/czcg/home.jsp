<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../../common/header.jsp"/>

    <meta charset="UTF-8">
    <title>常州市户外广告倾覆预警平台</title>
    <link rel="stylesheet" href="${ctx}/module/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/module/css/page1.css">
    <link rel="stylesheet" href="${ctx}/module/css/iconfont.css">
    <script type="text/javascript" src="${ctx}/module/js/echarts.js"></script>
    <script src="${ctx}/module/js/changzhoushi.js"></script>
    <script src="${ctx}/module/js/jquery-1.9.1.min.js"></script>
    <script src="${ctx}/module/js/bootstrap.min.js"></script>
    <script src="${ctx}/module/js/iconfont.js"></script>

</head>
<style type="text/css">
    .icon {
        width: 1em;
        height: 1em;
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;
    }

</style>
<body>
<div class="wrap">
    <div class="top">
        <div class="top_left">
            <img class="top_img" src="${ctx}/module/img/top1.gif" alt="">
            <span class="textnum">今日访问人数 / 总数</span>
            <span class="text_1"><span id="today_visit">0</span> / <span id="all_visit">0</span></span>
        </div>
        <div class="top_right">
            <img src="${ctx}/module/img/top2.gif" alt="">
            <span class="text">${user.username}</span>
            <span class="text_1">所属部门：</span>
            <span class="text">${user.company_name}</span>
            <span class="text_1" style="cursor: pointer;" onclick="window.location='${ctx}/user/main'">【后台】</span>
            <span class="text_1" style="cursor: pointer;" onclick="window.location='${ctx}/user/logout'">【退出】</span>
        </div>
    </div>
    <div class="head">
        <div>
            <img class="head_left" style="cursor: pointer;z-index:99;" src="${ctx}/module/img/slicing/bb.png"
                 onclick="window.location='${ctx}/czcg'">
            <img class="head_rotate" style="cursor: pointer;" id="indexHead" src="${ctx}/module/img/round.png"
                 onclick="window.location='${ctx}/czcg'">
            <span class="hvr-grow" onclick="window.location='${ctx}/czcg'"
                  style="cursor: pointer;z-index:99;left:314px">首页</span>
        </div>
        <div>
            <!--<img src="img/head_tit_l.png">-->
            <span>常州市户外广告倾覆预警平台</span>
            <img class="img_content" src="${ctx}/module/img/slicing/headline_bg.png" alt="##">
            <!--<img src="img/head_tit_r.png">-->
        </div>
        <div>
            <img class="head_right" style="cursor: pointer;" src="${ctx}/module/img/slicing/aa.png"
                 onclick="window.location='${ctx}/czcg/tongji'">
            <img class="headRotate_right" style="cursor: pointer;" id="right_animation"
                 src="${ctx}/module/img/round.png"
                 onclick="window.location='${ctx}/czcg/tongji'">
            <span class="hvr-grow" onclick="window.location='${ctx}/czcg/tongji'"
                  style="cursor: pointer;z-index:99">统计信息查询</span>
        </div>
        <img class="work_cont" src="${ctx}/module/img/slicing/form_bg.png" alt="###">
        <div class="clear"></div>
    </div>
    <div class="work" style="position: relative">
        <div class="page-content">
            <div class="cont">
                <div class="mid">
                    <div class="mid_titl">
                        <img src="${ctx}/module/img/bg_left_top.png" id="m_b_l_t">
                        <img src="${ctx}/module/img/bg_left_bottom.png" id="m_b_l_b">
                        <img src="${ctx}/module/img/bg_right_top.png" id="m_b_r_t">
                        <img src="${ctx}/module/img/bg_right_bottom.png" id="m_b_r_b">
                    </div>
                    <div id="Changzhou_1" class="onLineNumber">
                        <div style="position: relative;">
                            <img class="img_msg" src="${ctx}/module/img/11.png" style="display: none" alt="###">
                            <img class="img_msg1" src="${ctx}/module/img/12.png">
                            <div id="Changzhou" class="department_use hvr-grow">常州市</div>
                        </div>
                    </div>
                    <div id="wujin_1" class="onLineNumber">
                        <div style="position: relative;">
                            <img class="img_msg" src="${ctx}/module/img/11.png" alt="###">
                            <img class="img_msg1" src="${ctx}/module/img/12.png" style="display: none">
                            <div id="wujin" class="department hvr-grow">武进区</div>
                        </div>
                    </div>
                    <div id="zhonglou_1" class="onLineNumber">
                        <div style="position: relative;">
                            <img class="img_msg" src="${ctx}/module/img/11.png" alt="###">
                            <img class="img_msg1" src="${ctx}/module/img/12.png" style="display: none">
                            <div id="zhonglou" class="department hvr-grow">钟楼区</div>
                        </div>
                    </div>
                    <div id="xinbei_1" class="onLineNumber">
                        <div style="position: relative;">
                            <img class="img_msg" src="${ctx}/module/img/11.png" alt="###">
                            <img class="img_msg1" src="${ctx}/module/img/12.png" style="display: none">
                            <div id="xinbei" class="department hvr-grow">新北区</div>
                        </div>
                    </div>
                    <div id="tianning_1" class="onLineNumber">
                        <div style="position: relative;">
                            <img class="img_msg" src="${ctx}/module/img/11.png" alt="###">
                            <img class="img_msg1" src="${ctx}/module/img/12.png" style="display: none">
                            <div id="tianning" class="department hvr-grow">天宁区</div>
                        </div>
                    </div>
                    <div id="jintan_1" class="onLineNumber">
                        <div style="position: relative;">
                            <img class="img_msg" src="${ctx}/module/img/11.png" alt="###">
                            <img class="img_msg1" src="${ctx}/module/img/12.png" style="display: none">
                            <div id="jintan" class="department hvr-grow">金坛区</div>
                        </div>
                    </div>
                    <div id="niyang_1" class="onLineNumber">
                        <div style="position: relative;">
                            <img class="img_msg" src="${ctx}/module/img/11.png" alt="###">
                            <img class="img_msg1" src="${ctx}/module/img/12.png" style="display: none">
                            <div id="niyang" class="department hvr-grow">溧阳市</div>

                        </div>
                    </div>
                </div>
                <div class="mian_1">
                    <div class="textMsg">
                        <span id="site">常州市</span>：共

                        <text id="num" style="color: #13d8fa">${result.all_num}</text>
                        个
                        大屏设备
                    </div>
                    <div id="main" class="map">
                        <!--<img class="img_msg4" src="${ctx}/module/img/img1.png" alt="##">-->
                    </div>
                </div>
                <!--底部显示-->
                <div class="botom">
                    <div class="onLineNumber_Msg">
                        <div class="num_Msg">
                        <span class="msg">
                            <span class="tittle">建议关注  </span>
                            <span class="tittle_botom">${result.jygz}
                         </span>
                            <a style="font-weight: normal;font-size: 20px;color: #48f53b;margin-top: -2px;display: block;margin-left: -27px;float: left">台</a>
                        </span>
                            <div class="line"></div>
                            <span class="msg">
                             <span class="tittle">建议检修 </span>
                            <span class="tittle_center">${result.jyjx}</span>
                                                                 <a style="font-weight: normal;font-size: 20px;color: #1dd9ff;margin-top: -2px;display: block;margin-left: -17px;float: left">台</a>
                        </span>
                            <div class="line_1"></div>
                            <span class="msg">
                             <span class="tittle">建议整改或拆除</span>
                            <span class="tittle_right">${result.jyzg}
                           </span>
                                      <a style="font-weight: normal;font-size: 20px;color: #ffd118;margin-top: -2px;display: block;margin-left: -27px;float: left">台</a>
                        </span>
                            <img src="${ctx}/module/img/left_top.png" id="l_t_nn">
                            <img src="${ctx}/module/img/left_bottom.png" id="l_b_nn">
                            <img src="${ctx}/module/img/right_top.png" id="r_t_nn">
                            <img src="${ctx}/module/img/right_bottom.png" id="r_b_nn">
                        </div>

                    </div>
                </div>
            </div>
            <div class="right">
            <span class="top1">
                <img class="top1_img" src="${ctx}/module/img/slicing/001.png" alt="">
                <span class="top_text">建议整改或拆除倾斜大屏信息</span>
            </span>

                <table class="content-title" style="cursor: pointer;width:96%;margin:10px 2%;background: none">
                    <thead>
                    <tr style="height: 46px;background: #2f4461;color: white;font-weight:bold;text-align: center ">
                        <th>区域</th>
                        <th>位置</th>
                        <th>监管单位</th>
                        <th>广告牌类型</th>
                        <th>x轴倾斜数值</th>
                        <th>y轴倾斜数值</th>
                        <th>z轴倾斜数值</th>
                    </tr>
                    </thead>
                    <tbody style="text-align: center">
                    <c:if test="${dtoList== null || fn:length(dtoList) == 0}">
                        <tr>
                            <td colspan="7"><center style="margin-top: 10px;">暂无相关记录</center></td>
                        </tr>
                    </c:if>

                    <c:forEach var="dto" items="${dtoList}">
                        <tr onclick="javascript:device_detail(${dto.device_id});">
                            <th>${dto.area_name}</th>
                            <th title="${dto.address}">
                                <c:if test="${fn:length(dto.address) > 10 }">
                                    ${fn:substring(dto.address,0,20)}...
                                </c:if>
                                <c:if test="${fn:length(dto.address) <= 10 }">
                                    ${fn:substring(dto.address,0,20)}
                                </c:if>
                            </th>
                            <th>城管局</th>
                            <th>
                                <c:if test="${dto.type == 0}">
                                    测试
                                </c:if>
                                <c:if test="${dto.type == 1}">
                                    铁路立交
                                </c:if>
                                <c:if test="${dto.type == 2}">
                                    楼顶
                                </c:if>
                                <c:if test="${dto.type == 3}">
                                    高炮
                                </c:if>
                                <c:if test="${dto.type == 4}">
                                    外墙
                                </c:if>
                                <c:if test="${dto.type == 5}">
                                    地面LED
                                </c:if>
                            </th>
                            <th class="fist_aclMsg">${dto.x}°</th>
                            <th class="fist_aclMsg">${dto.y}°</th>
                            <th class="fist_aclMsg">${dto.z}°</th>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
                <img class="top2_img hvr-grow" src="${ctx}/module/img/slicing/002.png" alt=""
                     style="top:510px;cursor: pointer;z-index:80;">
            </div>
        </div>
    </div>
    <div class="loginmask"></div>
    <!--登录框-->
    <div class="login" style="display: none">
        <div class="login_tite">常州市户外广告倾覆预警平台

            <i class="iconfont icon-guanbi"
               style="position: absolute;right: 20px;bottom: -1px;font-size: 26px;cursor: pointer;"
            ></i>
            <!--<svg style="position: absolute;right: 20px;bottom: 27px;font-size: 26px;cursor: pointer;" class="icon"-->
            <!--aria-hidden="true">-->
            <!--<use xlink:href="#icon-guanbi"></use>-->
            <!--</svg>-->
        </div>
        <div style="width:100%;text-align:center">

            <form>
                <input class="input_1" type="text" id="username" value="用户名"
                       onFocus="if(this.value=='用户名') this.value = ''"
                       onBlur="if(this.value=='') this.value='用户名'">
                <input class="input_1" type="text" id="password" value="密码"
                       onFocus="if(this.value=='密码') this.value = ''"
                       onBlur="if(this.value=='') this.value='密码'">
            </form>
        </div>
        <label class="middle">
            <input class="check_1" type="checkbox" checked="checked" value="记住密码">
            <span class="pwd"> 记住密码</span>

            <span class="hint">忘记密码？</span>
        </label>


        <button class="but" onclick="doSubmitFrom()" type="button">登录</button>


    </div>


</div>
</body>


<script type="text/javascript">

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


    $('.login').hide();
    //    $('.text_1').click(function () {
    //        $('.loginmask').css('display', 'block')
    //        $('.login').show()
    //        $('.login').animate({top: '550px'}, 100).animate({"top": "250px",}, 200)
    //
    //
    //    });
    $('.loginmask').click(function () {
        $('.loginmask').css('display', 'none')
//        $('.login').hide()
        $('.login').animate({"top": "0px",}, 200).slideUp(150)
    })
    $('i.iconfont').click(function () {
        $('.loginmask').css('display', 'none')
//        $('.login').hide()
        $('.login').animate({"top": "0px",}, 200).slideUp(150)
    })


    // 地图JS
    var chart = echarts.init(document.getElementById('main'));
    chart.setOption({
        series: [{
            type: 'map',
            map: 'changzhou',
            zoom: 0.98,// 改变这个值的大小就可以了
            itemStyle: {
                normal: {
                    label: {
                        show: true, textStyle: {
                            color: "#fff",
                        },
                    },
//                    areaStyle: {color: 'rgba(255,255,255,0.5)'},
                    borderWidth: 1,//省份的边框宽度
                    borderColor: '#01f5f5',//省份的边框颜色
                    areaColor: '#24456e',//背景颜色
//                    shadowColor: '#fff',
//                    shadowBlur: 'rgba(600,0,0,10)',
                    backgroundColor: 'rgba(255,255,255,0.5)',
                    markLine: {
                        smooth: true,
                        effect: {
                            show: true,
                            scaleSize: 1,
                            period: 30,
                            color: '#fff',
                            shadowBlur: 10
                        }
                    }

                },
                emphasis: {// 也是选中样式
                    borderWidth: 1,
                    borderColor: '#01f5f5',
                    areaColor: '#2c9caa',
//                    shadowBlur:10,
                    label: {
                        show: true,
                        textStyle: {
                            color: '#fff'
                        }
                    }
                },

            }
        }]

    });

    chart.on('click', function (params) {
        var area_name = params.data.name;

        window.location.href = "${ctx}/czcg/list?area_name="+encodeURI(area_name)
        <%--new_tab("${ctx}/czcg/list?area_name="+encodeURI(area_name));--%>

    });
    //    ---------------------------------
    //    $('.onLineNumber').mousemove(function () {
    //        $(this).find('.department').attr("class", " department_use");
    //        $(this).find('.img_msg').css('display','none');
    //        $(this).find('.img_msg1').css('display','block');
    //    });
    //    $('.onLineNumber').mouseout(function () {
    //        $(this).find('.department_use').attr("class", " department");
    //        $(this).find('.img_msg').css('display','block');
    //        $(this).find('.img_msg1').css('display','none');
    //    })
    $('.onLineNumber').click(function () {
        $(this).find('.department').attr("class", " department_use");
        $(this).find('.img_msg').css('display', 'none');
        $(this).find('.img_msg1').css('display', 'block');
        $(this).siblings('.onLineNumber').find('.department_use').attr("class", " department");
        $(this).siblings('.onLineNumber').find('.img_msg').css('display', 'block');
        $(this).siblings('.onLineNumber').find('.img_msg1').css('display', 'none');


        $(".img_msg1").animate({'margin-left': '30px'}, 100);
//            $(".img_msg1").animate({'margin-left':'+=2px'},200);
        $(".img_msg1").animate({'margin-left': '0px'}, 200);
        $(".department_use").animate({'margin-left': '30px'}, 100);
//        $(".department_use").animate({'margin-left':'+=2px'},200);
        $(".department_use").animate({'margin-left': '0px'}, 200);


    });
    //    常州
    $('#Changzhou').click(function () {
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '武进区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '钟楼区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '新北区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '天宁区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '金坛区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '溧阳市'
        });
    });
    //武进区
    $('#wujin').click(function () {
        chart.dispatchAction({
            type: 'mapSelect',
            name: '武进区'
        });
//        取消选定
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '钟楼区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '新北区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '天宁区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '金坛区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '溧阳市'
        });
    });
    //  钟楼区
    $('#zhonglou').click(function () {
        chart.dispatchAction({
            type: 'mapSelect',
            name: '钟楼区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '武进区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '新北区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '天宁区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '金坛区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '溧阳市'
        });
    });
    //  新北区
    $('#xinbei').click(function () {
        chart.dispatchAction({
            type: 'mapSelect',
            name: '新北区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '武进区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '钟楼区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '天宁区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '金坛区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '溧阳市'
        });
    });

    //  天宁区
    $('#tianning').click(function () {
        chart.dispatchAction({
            type: 'mapSelect',
            name: '天宁区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '武进区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '钟楼区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '新北区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '金坛区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '溧阳市'
        });
    });

    //  金坛区
    $('#jintan').click(function () {
        chart.dispatchAction({
            type: 'mapSelect',
            name: '金坛区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '武进区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '钟楼区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '新北区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '天宁区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '溧阳市'
        });
    });

    //  溧阳市
    $('#niyang').click(function () {
        chart.dispatchAction({
            type: 'mapSelect',
            name: '溧阳市'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '武进区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '钟楼区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '新北区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '天宁区'
        });
        chart.dispatchAction({
            type: 'mapUnSelect',
            name: '金坛区'
        });
    });

    //    跳转
    $('img.top2_img').click(function () {
        window.location.href = "${ctx}/czcg/list";
    });

    var imgid = null;
    var imgid_1 = null;

    $('.onLineNumber').click(function () {
        imgid_1 = $(this).attr("id");
        console.log(imgid_1)
        if (imgid_1 == 'Changzhou_1') {
            $('#site').html('常州市')
            $('#num').html('${result.all_num}')
        }


    });
    $('.onLineNumber').click(function () {
        imgid = $(this).attr("id");
        console.log(imgid)
        if (imgid == 'wujin_1') {
            $('#site').html('武进区')
            $('#num').html('${result.wj_num}')

        }
        else if (imgid == 'zhonglou_1') {
            $('#site').html('钟楼区')
            $('#num').html('${result.zl_num}')

        }
        else if (imgid == 'xinbei_1') {
            $('#site').html('新北区')
            $('#num').html('${result.xb_num}')

        }
        else if (imgid == 'tianning_1') {
            $('#site').html('天宁区')
            $('#num').html('${result.tn_num}')

        }
        else if (imgid == 'jintan_1') {
            $('#site').html('金坛区')
            $('#num').html('${result.jt_num}')

        }
        else if (imgid == 'niyang_1') {
            $('#site').html('溧阳市')
            $('#num').html('${result.ly_num}')
        }

    });
    //    // 登录注销的骚操作
    //
    //    $('#test_link1').css('display', 'none');
    //    $('#bumen').css('display', 'none');
    //    $('#bureau').css('display', 'none');
    //
    //    function doSubmitFrom() {
    //
    //        var username_input = document.getElementById('username').value.trim();
    //        var password_input = document.getElementById('password').value.trim();
    //
    //        //判断
    //        if (!username_input) {
    //            alert('用户名未填写')
    //            return;
    //        }
    //        if (!password_input) {
    //            alert('密码不得为空')
    //            return;
    //        }
    //        var obj = {};
    //        obj.username = username_input;
    //        obj.password = password_input;
    //        //数组转字符串 JSON.stringify从数组解析字符串
    //        var obj_string = JSON.stringify(obj);
    //        console.log(obj_string);
    //        //存入浏览器
    //        localStorage.setItem('userinfo', obj_string);
    //        $('.login').hide();
    //        $('.loginmask').hide();
    //        $('#test_link2').css('display', 'none');
    //        $('#test_link1').show();
    //        $('#test_link').css('display', 'none');
    //        $('#bumen').show();
    //        $('#bureau').show();
    //
    //
    //        //获取登录按钮
    //        var login = document.getElementById('test_link');
    //        //获取注销按钮)
    //        var login1 = document.getElementById('test_link1');
    //        // 查找localStorage有没有存入的userinfo
    //        var userinfo = localStorage.getItem('userinfo')
    //        console.log(userinfo);
    //        if (userinfo) {
    //            //把登录按钮隐藏
    //            login.style.display = 'none';
    //            //JSON.parse从字符串解析数组
    //            var string_obj = JSON.parse(userinfo);
    //            console.log(string_obj);
    //            // 放入document里面
    ////        document.getElementById('objMsg').getElementsByTagName('span')[0].innerText = string_obj.username;
    //            $('span.text').html(string_obj.username)
    //            $('span.text').show();
    //            $('#test_link2').css('display', 'none');
    //            $('#test_link1').show();
    //            $('#bumen').show();
    //            $('#bureau').show();
    //        }
    //    }
    //
    //
    //    //获取登录按钮
    //    var login = document.getElementById('test_link');
    //    //获取注销按钮)
    //        var login1 = document.getElementById('test_link1');
    //    // 查找localStorage有没有存入的userinfo
    //    var userinfo = localStorage.getItem('userinfo')
    //    console.log(userinfo);
    //    if (userinfo) {
    //        //把登录按钮隐藏
    //        login.style.display = 'none';
    //        //JSON.parse从字符串解析数组
    //        var string_obj = JSON.parse(userinfo);
    //        console.log(string_obj);
    //        // 放入document里面
    ////        document.getElementById('objMsg').getElementsByTagName('span')[0].innerText = string_obj.username;
    //        $('span.text').html(string_obj.username)
    //        $('span.text').show();
    //        $('#test_link2').css('display', 'none');
    //        $('#test_link1').show();
    //        $('#bumen').show();
    //        $('#bureau').show();
    //    }
    //    //    else {
    //    //        //把隐藏按钮隐藏
    //    //        login1.style.display = 'none';
    //    //    }
    //    //注销
    //    function logout() {
    //        //删除localSorage
    //        localStorage.removeItem('userinfo');
    //        //移除p标签的文字
    //        document.getElementById('objMsg').getElementsByTagName('span')[0].innerText = '';
    //        $('#test_link').show();
    //        $('#test_link2').show();
    //        $('#test_link1').hide()
    //        $('#bumen').hide();
    //        $('#bureau').hide();
    //
    //    }

    var device_detail = function(device_id){
        window.location.href = '${ctx}/czcg/getDeviceListByDeviceId?deviceId='+device_id;
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
</html>