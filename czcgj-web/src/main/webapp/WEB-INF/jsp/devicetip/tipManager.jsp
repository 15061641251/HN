<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="deviceTipApp">
<head>
    <base href="/"/>
    <title>告警提醒设置</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/bootstrap-table.jsp"/>

    <jsp:include page="../../common/form.jsp"/>

    <style type="text/css">
        tr > th {
            font-size: 15px;
            font-weight: normal;
        }
    </style>
</head>

<body ng-controller="deviceTipAppController">
<div>
    <div class="main-div">
        <div class="center-Panel" style="overflow-x: hidden">
            <div class="titlePanel">
                <div class="title-search">
                    <table>
                        <tbody>
                        <tr>
                            <td>不重复提醒时间</td>
                            <td>
                                <div class="pull-right search">
                                    <input class="form-control" type="text" placeholder="默认0分钟"
                                           id="no_tip_minute">
                                </div>
                            </td>
                            <td><span>分</span></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="toolbar">
                    <div class="btn-group">
                        <a class="btn btn-default lr-add" ng-click="addTip()"><i class="fa fa-plus"></i>&nbsp;新增</a>
                    </div>
                </div>
            </div>
            <div class="bootstrap-table">
                <div class="fixed-table-container" style="padding-bottom: 0px;">
                    <div class="fixed-table-body">
                        <table id="table" class="table table-hover table-striped JColResizer">
                            <thead>
                            <tr>
                                <th style="text-align: center; width: 50px; ">序号</th>
                                <th style="text-align: center; width: 50px; ">手机号</th>
                                <th style="text-align: center; width: 50px; ">状态</th>
                                <th style="text-align: center; width: 50px; ">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="tip in tips">
                                <td style="text-align: center; " ng-cloak>{{$index + 1}}</td>
                                <td style="text-align: center; " ng-cloak><input ng-model="tip.phone_num"
                                                                                 type="text"/><span
                                        style="color:red">*</span>
                                </td>
                                <td style="text-align: center; " ng-cloak>
                                    <select ng-model="tip.show_status">
                                        <option value="{{show_status}}" ng-repeat="show_status in statuss"
                                                ng-selected="show_status == tip.show_status">{{show_status}}
                                        </option>
                                    </select>
                                    <span style="color:red">*</span>
                                </td>
                                <td style="text-align: center; " ng-cloak><a ng-click="delTip(tip)">删除</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <br>
            <center>
                <input type="button" class="btn btn-large btn-primary" value="保  存"
                       ng-click="saveTip()"/>
            </center>
        </div>
    </div>

</div>
<script src="${ctx}/module/devicetip/scripts/deviceTip.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
