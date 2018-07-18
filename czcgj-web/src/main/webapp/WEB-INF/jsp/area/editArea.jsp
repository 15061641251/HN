<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html ng-app="editAreaApp">
<head>
    <base href="/"/>
    <title>修改区域信息</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/form.jsp"/>

    <link href="${ctx}/common/style/table.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>

</head>
<body ng-controller="editAreaController">
<div class="submenu"
     style="width:95%; padding-top: 10px;  padding-left:10px; padding-right:10px;padding-bottom:10px;overflow: auto;">
    <div class="main-div">
        <table>
            <tbody>
            <tr class="labeltr">
                <th colspan="4" style="text-align: left;"><span
                        style="margin-left: 10px; margin-top: 10px; font-size: 14px;">区域信息</span>
                </th>
            </tr>
            <tr style="height:41px;display: none" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>区域主键</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="int_id" ng-model="int_id" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>公司主键</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="company_id" ng-model="company_id" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>区域名称</label>
                </td>
                <td class="labeltd" style="text-align:left;" colspan="3"><input
                        id="area_name" ng-model="area_name" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>负责人</label>
                </td>
                <td class="labeltd" style="text-align:left;" colspan="3"><input
                        id="leader_name" ng-model="leader_name" class="form-control"/>
                </td>

            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>负责人电话</label>
                </td>
                <td class="labeltd" style="text-align:left;" colspan="3"><input
                        id="leader_phone" ng-model="leader_phone" class="form-control"/>
                </td>
            </tr>
            </tbody>
        </table>
        <br/>
        <center>
            <input type="button" class="btn btn-large btn-primary" value="保  存"
                   ng-click="editArea()"/>
        </center>
    </div>
</div>
<script type="text/javascript" src="${ctx }/common/scripts/angularjs/underscore.js"></script>
<script type="text/javascript"
        src="${ctx }/module/area/scripts/editArea.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript"
        src="${ctx }/common/scripts/angularjs/angularFormPost.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>