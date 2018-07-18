<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页大屏信息</title>

    <jsp:include page="../../common/header.jsp"/>

    <link href="${ctx}/module/css/style.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/module/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/module/css/page8.css">
    <link rel="stylesheet" href="${ctx}/common/layui/css/layui.css">

    <script type="text/javascript" src="${ctx}/module/js/echarts.js"></script>
    <link href="${ctx}/module/css/style.css" rel="stylesheet"/>
    <script language="javascript" type="text/javascript" src="${ctx}/module/js/WdatePicker.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;

        }

        body, html {
            /*height: 100%;*/
            background: url("${ctx}/module/img/bg.png") center center no-repeat;
            background-size: cover;
        }

        #main1 canvas {
            width: 100%;
            padding-top: 20px;
            color: white;

        }

        #main2 canvas {
            width: 100%;
            padding-top: 20px;
            color: white;

        }

        #main5 canvas {
            width: 100%;
            margin-top: 20px;
            color: white;

        }

        .main5 {
            background: url('${ctx}/module/img/bg.png');
            background-size: 100% 100%;
            padding-top: 5%;

        }

        #main3 {
            background: url('${ctx}/module/img/bg.png');
            background-size: 100% 100%;
        }

        #main3 .left_botom1 {
            width: 65%;
            height: 100%;
            float: left;
            background: url("${ctx}/module/img/4.png") center center no-repeat;
            background-size: cover;
        }

        li {
            letter-spacing: 2px;
            /*border-bottom: 1px solid #14f0f1;*/
            height: 26px;
            line-height: 26px;
            color: black;
            text-align: right;
        }

        ul.list2 li {
            letter-spacing: 2px;
            border-bottom: none;

            color: #14f0f1;
            text-align: right;
        }

        li a {
            width: 38%;
            float: left;
            color: #14f0f1 !important;
            text-align: left;
            text-decoration: none;
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

        ::-webkit-input-placeholder { /* WebKit browsers */
            color: #fff;
        }

        :-moz-placeholder { /* Mozilla Firefox 4 to 18 */
            color: #fff;
        }

        ::-moz-placeholder { /* Mozilla Firefox 19+ */
            color: #fff;
        }

        :-ms-input-placeholder { /* Internet Explorer 10+ */
            color: #fff;
        }

        .right_top_top {
            width: 100%;
            margin: 5px 0;
        }

        .right_top_top ul {
            list-style: none;
            width: 50%;
            margin: 0 auto;
            /*overflow:hidden;*/
            padding-top: 0px;
        }

        .right_top_top ul li {
            border-bottom: none;
            float: left
        }

        .layui-form-select .layui-input {
            background: #204267;
            border: none;
        }

        .layui-form-select dl dd {
            color: #26aac3;
        }

        .layui-form-select dl dd.layui-select-tips {
            color: #26aac3;
        }

        .layui-form-select .layui-input {
            width: 210px;
            color: white;
        }
    </style>
</head>
<body>
<div class="wrap">

    <div class="top">
        <div class="top_left">
            <img class="top_img" src="${ctx}/module/img/top1.gif" alt="">
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

            <img onclick="window.location='${ctx}/czcg'" style="cursor: pointer;z-index:500;" class="head_left"
                 src="${ctx}/module/img/slicing/bb.png">
            <img onclick="window.location='${ctx}/czcg'" class="head_rotate" id="indexHead"
                 src="${ctx}/module/img/round.png">
            <span onclick="window.location='${ctx}/czcg'" style="cursor: pointer;z-index:500;">首页</span>
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
            <span onclick="window.location='${ctx}/czcg/list'" style="cursor: pointer;z-index:99999;">大屏信息查询</span>
        </div>
        <img class="work_cont" src="${ctx}/module/img/slicing/form_bg.png" alt="###">
        <div class="clear"></div>
    </div>
    <div class="page-content">
        <div class="allbotom">
            <span class="bigMsg">统计信息 </span>
            <%--<form class="layui-form" action="">--%>
                <%--<div class="layui-inline" style="float: left;margin-left: 16%;">--%>
                    <%--<label class="layui-form-label" style="width: 130px;font-size: 16px;">广告牌类型：</label>--%>
                    <%--<div class="layui-input-inline">--%>
                        <%--<select name="modules" style="background:#204267;border: none;height: 36px;; ">--%>
                            <%--<option value="1">全部</option>--%>
                            <%--<option value="2">建筑物上的户外广告设施</option>--%>
                            <%--<option value="3">公共设施上的户外广告设施</option>--%>
                            <%--<option value="4">地面上的户外广告设施</option>--%>
                            <%--<option value="5">其他类型户外广告设施</option>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</form>--%>
            <%--<span class="bigMsg_cont" style="float: left;margin-left: 30px;">--%>
                <%--<span style="font-size: 16px;display: block;float: left;line-height: 35px;margin-left:8%\9">选择日期：</span>--%>
                    <%--<div class="layui-input-inline">--%>
                        <%--<input type="text" style="height: 36px;border: none;background: #204267;color: white;line-height: 20px;--%>
                        <%--float: left;margin-left: 4%;margin-left:3%\9"--%>
                               <%--class="layui-input" id="test30"--%>
                               <%--value="">--%>
                    <%--</div>--%>
                    <%--<button type="button" class="but">查询</button>--%>
               <%--</span>--%>
            <%--<span class="bigMsg_right"></span>--%>
        </div>
        <%--<div class="right_top_top">--%>
            <%--<ul>--%>
                <%--<li class="listDetails">当天天气:晴</li>--%>
                <%--<li class="listDetails" style="margin-left: 75px;">X轴倾斜角度平均值:02.73</li>--%>
                <%--<li class="listDetails" style="margin-left: 50px;">X轴倾斜角度最大值:01.32</li>--%>
                <%--<li class="listDetails">当天风力:2-3级</li>--%>
                <%--<li class="listDetails" style="margin-left: 50px;">Y轴倾斜角度平均值:03.43</li>--%>
                <%--<li class="listDetails" style="margin-left: 50px;">Y轴倾斜角度最大值:02.89</li>--%>
                <%--<li class="listDetails" style="margin-left: 156px;">Z轴倾斜角度平均值:02.23</li>--%>
                <%--<li class="listDetails" style="margin-left: 51px;">Z轴倾斜角度最大值:03.66</li>--%>
            <%--</ul>--%>
        <%--</div>--%>

        <div class="right" style="position: relative;margin-top: 1%;">
            <div class="right_top">
                <!--main1-->
                <div id="main1" class="" style="width:100%;height: 100%;;float: left"></div>
                <p style="position: absolute;left:7%;top:9%;color: #14f0f1">(台数)</p>
            </div>

        </div>

    </div>
</div>
</body>
<script type="text/javascript" src="${ctx}/common/layui/layui.js"></script>

<script type="text/javascript">
    //今天的时间
    var day2 = new Date();
    day2.setTime(day2.getTime());
    var s2 = day2.getFullYear() + "年" + (day2.getMonth() + 1) + "月" + day2.getDate() + "日";
    var s22 = day2.getFullYear() + "-" + (day2.getMonth() + 1) + "-" + day2.getDate();
    //拼接时间
    var str1 = s2
    var strtoday = s22;
    //赋值doubleDate
    document.getElementById("test30").value = strtoday;
</script>
<!------------------------------------------------->
<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;
    });
</script>
<!------------------------------------------------->
<script>
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //自定义颜色
        laydate.render({
            elem: '#test30'
            , theme: '#393D49'
        });
    });

</script>
<!-------------------------------------------------------->
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

</script>
<script type="text/javascript">


    //    setTimeout(function(){ window.location.reload();},2000);//每隔1秒刷新1次页面
    function f() {
        var arr = [];
        for (var i = 0; i < 10; i++) {
            arr[i] = parseInt(Math.random() * 10);
//        alert(arr[i])
        }
        if (arr[0] === 0) {
            $("#one").attr("class", "zero");
        }
        if (arr[0] === 1) {
            $("#one").attr("class", "one");
        }
        if (arr[0] === 2) {
            $("#one").attr("class", "two");
        }
        if (arr[0] === 3) {
            $("#one").attr("class", "three");
        }
        if (arr[0] === 4) {
            $("#one").attr("class", "four");
        }
        if (arr[0] === 5) {
            $("#one").attr("class", "five");
        }
        if (arr[0] === 6) {
            $("#one").attr("class", "six");
        }
        if (arr[0] === 7) {
            $("#one").attr("class", "seven");
        }
        if (arr[0] === 8) {
            $("#one").attr("class", "eight");
        }
        if (arr[0] === 9) {
            $("#one").attr("class", "nine");
        }

        if (arr[1] === 0) {
            $("#two").attr("class", "zero");
        }
        if (arr[1] === 1) {
            $("#two").attr("class", "one");
        }
        if (arr[1] === 2) {
            $("#two").attr("class", "two");
        }
        if (arr[1] === 3) {
            $("#two").attr("class", "three");
        }
        if (arr[1] === 4) {
            $("#two").attr("class", "four");
        }
        if (arr[1] === 5) {
            $("#two").attr("class", "five");
        }
        if (arr[1] === 6) {
            $("#two").attr("class", "six");
        }
        if (arr[1] === 7) {
            $("#two").attr("class", "seven");
        }
        if (arr[1] === 8) {
            $("#two").attr("class", "eight");
        }
        if (arr[1] === 9) {
            $("#two").attr("class", "nine");
        }

        if (arr[2] === 0) {
            $("#three").attr("class", "zero");
        }
        if (arr[2] === 1) {
            $("#three").attr("class", "one");
        }
        if (arr[2] === 2) {
            $("#three").attr("class", "two");
        }
        if (arr[2] === 3) {
            $("#three").attr("class", "three");
        }
        if (arr[2] === 4) {
            $("#three").attr("class", "four");
        }
        if (arr[2] === 5) {
            $("#three").attr("class", "five");
        }
        if (arr[2] === 6) {
            $("#three").attr("class", "six");
        }
        if (arr[2] === 7) {
            $("#three").attr("class", "seven");
        }
        if (arr[2] === 8) {
            $("#three").attr("class", "eight");
        }
        if (arr[2] === 9) {
            $("#three").attr("class", "nine");
        }
        if (arr[3] === 0) {
            $("#four").attr("class", "zero");
        }
        if (arr[3] === 1) {
            $("#four").attr("class", "one");
        }
        if (arr[3] === 2) {
            $("#four").attr("class", "two");
        }
        if (arr[3] === 3) {
            $("#four").attr("class", "three");
        }
        if (arr[3] === 4) {
            $("#four").attr("class", "four");
        }
        if (arr[3] === 5) {
            $("#four").attr("class", "five");
        }
        if (arr[3] === 6) {
            $("#four").attr("class", "six");
        }
        if (arr[3] === 7) {
            $("#four").attr("class", "seven");
        }
        if (arr[3] === 8) {
            $("#four").attr("class", "eight");
        }
        if (arr[3] === 9) {
            $("#four").attr("class", "nine");
        }
    }

    f();
    console.log($('#one').attr('class'))
    setInterval("f() ", 2000);
</script>
<!--正负数-->
<script type="text/javascript" language="javascript">
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

    var aaa = $('#b').css('display');
    console.log(aaa)
    $('#b').show().delay(3000).hide(0);
    if (aaa === 'block') {
        $('.digits').css({'width': '80%', 'margin': '0 10% 10px 10%'})

    } else if (aaa === 'none') {
        $('.digits').css({'width': '60%', 'margin': '0 20% 10px 20%'})
    }
    //    var bbb =$('#one').attr('class');
    //    alert(bbb)
    //    if( bbb  === two){
    //    $('#b').css('display','block');
    //    }else {
    //        $('#b').css('display','none');
    //    }
</script>
<!--4切换-->
<script>
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

    $(function () {
        console.log($('.fu').css('display'));

    })
</script>
<!--main1  2-->
<script type="text/javascript">
    var myChart1 = echarts.init(document.getElementById('main1'));
    option = {
        title: {
//            backgroundColor: '#ff0000',
            //背景
            text: '当日不同级别倾斜次数统计图',
            textStyle: {
                fontWeight: 'normal',
                fontSize: 14,
                //标题颜色
                color: '#fff',

            },
//            subtext: '纯属虚构'
            y: '5%',
            x: '27%'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['建议关注', '建议检修', '建议整改或拆除'],
            textStyle: {
                color: '#14f0f1'
            },
            y: '5%',
            x: '62%',

        },

        toolbox: {
            show: false,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
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

                axisTick: {show: false},
//                type : 'value',
                splitLine: {
                    lineStyle: {
                        color: ['#347cd2'],
                        type: 'line'
                    }
                },
                type: 'category',
//                type : 'value'
                data: ['武进区', '钟楼区', '新北区', '天宁区', '金坛区', '溧阳市']
            }
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
                    color: '#14f0f1'
                },

                'type': 'value',

                color: '#14f0f1',
//                boundaryGap: true,
                splitLine: {
                    lineStyle: {
                        color: 'rgba(48,126,211,0.5)',

                    }
                },
                min: 0,
                max: 30,
                interval: 2,
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
        grid: {
            x: 70,
            y: 70,
            x2: 50,
            y2: 40,
            // width: {totalWidth} - x - x2,
            // height: {totalHeight} - y - y2,

        },
        series: [
            {
                name: '建议关注',
                type: 'bar',
                stack: '总量',
                barWidth: 20,
                itemStyle: {
                    normal: {color: '#2ced0f'}
                },
//                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data: ${result.jygz}
            },
            {
                name: '建议检修',
                type: 'bar',
                stack: '总量',
                barWidth: 20,
                itemStyle: {
                    normal: {color: '#1aa8ec'}
                },
//                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data: ${result.jyjx}
            },
            {
                name: '建议整改或拆除',
                type: 'bar',
                stack: '总量',
                barWidth: 20,
                itemStyle: {
                    normal: {color: '#fadf08'}
                },
//                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data: [0, 0, 0, 0, 0, 0]
            }

        ]
    }

    myChart1.setOption(option);

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