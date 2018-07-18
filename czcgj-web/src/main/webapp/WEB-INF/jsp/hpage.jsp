<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="HpageApp" style="height: 96%;">
<head>
    <title>首页</title>
    <jsp:include page="../common/header.jsp"/>
    <jsp:include page="../common/angular.jsp"/>

    <link href="${ctx}/module/hpage/style/hpage.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>
    <link href="${ctx}/common/style/bootstrap.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>
    <link href="${ctx}/common/datepicker/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/common/datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet"/>


</head>

<body ng-controller="HpageController" style="background:#f9f9f7;">
<div class="left_div" style="height: 100%; width: 55%; margin:10px 1% 0 2%;float: left;">
    <div class="left_1">
        <div class="div-head" style="border-bottom: 4px solid #f08003;">
            <span class="h_title" href="#">通知公告</span>
            <a class="h_title" href="${ctx}/bulletin/toBulletinListPage/" target="_blank"
               style="display: block; float: right;color:#f08003;">更多</a>
        </div>
        <div style="width:100%;" ng-controller="BulletinsCtrl">
            <table class="table ">
                <thead>
                <th style="font-weight:normal;text-align: left;">标题</th>
                <th style="width:35%;font-weight:normal;text-align: center">来自</th>
                <th style="width:30%;font-weight:normal;text-align: center;">接收时间</th>
                </thead>
                <tbody>
                <tr ng-repeat="item in Bulletins" ng-cloak>
                    <td style="cursor:pointer;" ng-cloak ng-click="showBulletin(item)">
                        <a><span class="theme">{{item.bulletin_title}}</span></a>
                    </td>
                    <td style="width:35%;text-align: center;" ng-cloak>{{item.username}}</td>
                    <td style="width:35%;text-align: center;" ng-cloak>{{item.create_time | limitTo:16 }}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="left_2" ng-controller="TodoMangerCtrl">
        <div class="div-head" style="border-bottom: 4px solid #5ac597;">
            <span class="h_title" href="#">待办事项</span>
        </div>
        <table class="table ">
            <thead>
            <tr>
                <th style="font-weight:normal;text-align: left;">标题</th>
                <th style="width:35%;font-weight:normal;text-align:center;">类型</th>
                <th style="width:35%;font-weight:normal;text-align:center;">接收时间</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="item in carTodos" ng-cloak>
                <td style="font-weight:normal;text-align:left;" ng-click="showCarInfo(item)"><a style="cursor:pointer;"><span id="todo">{{item.todo_title}}</span></a>
                </td>
                <td style="width:35%;text-align:center;" ng-cloak ng-switch="item.todo_type">
                    <span ng-switch-when="1008">用车申请</span>
                    <span ng-switch-when="1010">物资申请</span>
                    <span ng-switch-when="1012">请假申请</span>
                    <span ng-switch-when="1016">加班申请</span>
                </td>
                <td style="width:35%;text-align:center;" ng-cloak>{{item.create_time | limitTo:16 }}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<div class="rigth_div">
    <div class="right_1">
        <div class="div-body" style="height: 100%;">

            <div id="calendar" style="width:100%;margin-bottom:15px;"></div>
            <div id="todo_div" ng-cloak>
                <p style='color:#7f7f7f;font-size:14px;line-height:20px;border-bottom:1px dotted #7f7f7f;margin:0;margin-left:15px;margin-top:10px;padding-bottom:3px;display:inline-block;width:90%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;'>
                    暂无日程
                <p>
            </div>
            <%--<div style="width:30px;height:30px;position:absolute;top:240px;right:20px;">--%>
            <a target="_blank" href='${ctx}/todo/todoManager'>
                <img src='${ctx}/login/more.png' style="width:5%;"/></a>
            <%--</div>--%>
        </div>
    </div>
    <div class="right_2">
        <div class="div-head" style="border-bottom: 4px solid #7c9bd4;">
            <span class="h_title" href="#">会议通知</span>
            <a class="h_title" href="${ctx}/meeting/list/" target="_blank"
               style="display: block; float: right;color:#7c9bd4;">更多</a>
        </div>
        <div ng-controller="MeetingManagerCtrl">
            <table class="table ">
                <thead>
                <th style="width:30%;font-weight:normal;text-align:left;">标题</th>
                <th style="width:35%;font-weight:normal;text-align:center;">来自</th>
                <th style="width:35%;font-weight:normal;text-align:center;">接收时间</th>
                </thead>
                <tbody>
                <tr ng-repeat="item in Infos" ng-cloak>
                    <td style="width:30%;cursor:pointer;text-align:left;" ng-cloak ng-click="showInfo(item)">
                        <a><span class="theme">{{item.meeting_name}}</span>
                        </a>
                    </td>
                    <td style="width:35%;text-align:center;" ng-cloak>{{item.meeting_user_name}}</td>
                    <td style="width:35%;text-align:center;" ng-cloak>{{item.create_time | limitTo:16}}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/common/datepicker/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/common/datepicker/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript" src="${ctx}/module/hpage/scripts/hpageManager.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="${ctx}/module/hpage/scripts/hpageBulletin.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="${ctx}/module/hpage/scripts/hpageMeeting.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="${ctx}/module/hpage/scripts/hpageTodo.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
