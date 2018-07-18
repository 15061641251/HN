<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html ng-app="editDeviceApp">
<head>
    <base href="/"/>
    <title>修改设备信息</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/form.jsp"/>

    <link href="${ctx}/common/style/table.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>

</head>
<body ng-controller="editDeviceController">
<div class="submenu"
     style="width:95%; padding-top: 10px;  padding-left:10px; padding-right:10px;padding-bottom:10px;overflow: auto;">
    <div class="main-div">
        <table>
            <tbody>
            <tr class="labeltr">
                <th colspan="4" style="text-align: center;"><span
                        style="margin-left: 10px; margin-top: 10px; font-size: 16px;">设备信息</span>
                </th>
            </tr>
            <tr style="height:41px;display: none" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>设备主键</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="int_id" ng-model="int_id" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>设备名称</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="device_name" ng-model="device_name" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>设备编号</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="device_id" ng-model="device_id" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>授权信息</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="auth_id" ng-model="auth_id" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>接入方式</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="access_type" ng-model="access_type" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>API-KEY</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="api_key" ng-model="api_key" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>API-URL</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="api_url" ng-model="api_url" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">正常抓取周期(分)</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="period" ng-model="period" placeholder="例如:60,默认60" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">不重复告警周期(分)</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="no_tip_minute" ng-model="no_tip_minute" placeholder="例如:60,默认60" class="form-control"/>
                </td>
            </tr>
            <tr>
                <td class="labeltd"><label class="control-label">一般告警倾斜角度</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="warn_angle" ng-model="warn_angle" placeholder="例如:5,默认5" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">一般告警上报周期(分)</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="warn_period" ng-model="warn_period" placeholder="例如:10,默认10" class="form-control"/>
                </td>
            </tr>
            <tr>
                <td class="labeltd"><label class="control-label">严重告警倾斜角度</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="serious_warn_angle" ng-model="serious_warn_angle" placeholder="例如:10,默认10" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">严重告警上报周期(分)</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="serious_warn_period" ng-model="serious_warn_period" placeholder="例如:5,默认5" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">纬度</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="lat" ng-model="lat" placeholder="例如:39.915156" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">经度</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="lng" ng-model="lng" placeholder="例如:116.404844" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;display: none;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;区域主键</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="area_id" ng-model="area_id" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;区域名称</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="area_name" ng-model="area_name" ng-click="chooseArea()" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>地址</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="address" ng-model="address" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;设备厂家</label>
                </td>
                <td class="labeltd" style="text-align:left;" colspan="3">
                    <select id="device_type" name="device_type" ng-model="device_type"  style="width: 150px;">
                        <option value="">请选择</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                </td>

            </tr>
            </tbody>
        </table>
        <br/>
        <center>
            <input type="button" class="btn btn-large btn-primary" value="保  存"
                   ng-click="editDevice()"/>
        </center>
    </div>
</div>
<script type="text/javascript" src="${ctx }/common/scripts/angularjs/underscore.js"></script>
<script type="text/javascript"
        src="${ctx }/module/device/scripts/editDevice.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript"
        src="${ctx }/common/scripts/angularjs/angularFormPost.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>