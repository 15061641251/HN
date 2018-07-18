<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="AreaAddApp">
<head>
    <title>添加区域</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/form.jsp"/>

    <link href="${ctx}/common/style/table.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>

</head>
<body ng-controller="areaAddController">
<div class="submenu"
     style="width:95%; padding-top: 10px;  padding-left:10px; padding-right:10px;padding-bottom:10px;overflow: auto;">
    <div class="main-div">
        <table>
            <tbody>
            <tr class="labeltr">
                <th colspan="4" style="text-align: center;"><span
                        style="margin-left: 10px; margin-top: 10px; font-size: 16px;">区域信息</span>
                </th>
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
        </table>
        <br/>
        <center>
            <input type="button" class="btn btn-large btn-primary" value="添  加"
                   ng-click="addArea()"/>
        </center>
    </div>
</div>

<script type="text/javascript"
        src="${ctx }/common/scripts/angularjs/underscore.js"></script>
<script type="text/javascript"
        src="${ctx }/module/area/scripts/addArea.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript"
        src="${ctx }/common/scripts/angularjs/angularFormPost.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
