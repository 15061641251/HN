<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>实时工况</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/bootstrap-table.jsp"/>

    <jsp:include page="../../common/form.jsp"/>

    <script src="http://api.map.baidu.com/api?v=2.0&ak=Dxwdb4m56dSlOkE67ZQgtFGD" type="text/javascript"></script>
    <script src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"
            type="text/javascript"></script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css"/>
    <!--加载检索信息窗口-->
    <script type="text/javascript"
            src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css"/>

    <style type="text/css">


        .main-div {
            height: 100%;
            padding: 60px 0 0;
            box-sizing: border-box;
        }

        .center-Panel {
            height: 60px;
            margin: -60px 0 0;
        }

        #map {
            width: 100%;
            height: 100%;
        }
    </style>

</head>

<body>
<div>
    <div class="main-div">
        <div class="center-Panel" style="overflow-x: hidden">
            <div class="titlePanel">
                <div class="title-search">
                    <table>
                        <tbody>
                        <tr>
                            <td>机构名称</td>
                            <td>
                                <div class="pull-right search">
                                    <input class="form-control input_small_width" type="text" placeholder="搜索" id="company_name"></div>
                            </td>
                            <td>区域名称</td>
                            <td>
                                <div class="pull-right search">
                                    <input class="form-control input_small_width" type="text" placeholder="搜索" id="area_name"></div>
                            </td>
                            <td>设备名称</td>
                            <td>
                                <div class="pull-right search">
                                    <input class="form-control input_small_width" type="text" placeholder="搜索" id="device_name"></div>
                            </td>
                            <td>设备编号</td>
                            <td>
                                <div class="pull-right search">
                                    <input class="form-control input_small_width" type="text" placeholder="搜索" id="device_id"></div>
                            </td>
                            <td style="padding-left: 20px;">
                                <a class="btn btn-primary lr-search" onclick="javascript:map_search()">
                                    <i class="fa fa-search"></i>&nbsp;查询</a>
                            </td>
                            <td style="padding-left: 20px;">
                                <a class="btn btn-primary" onclick="javascript:clear_overlay()">
                                    <i class="fa fa-remove"></i>&nbsp;清空填充物</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="map">
        </div>
    </div>

</div>
<script src="${ctx}/module/map/scripts/mapManager.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
