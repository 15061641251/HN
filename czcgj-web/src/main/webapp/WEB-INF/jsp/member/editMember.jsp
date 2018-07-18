<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html ng-app="editMemberApp">
<head>
    <base href="/"/>
    <title>修改用户信息</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/form.jsp"/>

    <link href="${ctx}/common/style/table.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>

</head>
<body ng-controller="editMemberController">
<div class="submenu"
     style="width:95%; padding-top: 10px;  padding-left:10px; padding-right:10px;padding-bottom:10px;overflow: auto;">
    <div class="main-div">
        <table>
            <tbody>
            <tr class="labeltr">
                <th colspan="2" style="text-align: left;"><span
                        style="margin-left: 10px; margin-top: 10px; font-size: 14px;">用户信息</span>
                </th>
            </tr>
            <tr style="height:41px;display: none" class="labeltr">
                <td class="labeltd"><label class="control-label">机构主键</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="company_id" ng-model="company_id" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;机构名称</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="company_name" ng-model="company_name" ng-click="chooseCompany()" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;display: none" class="labeltr">
                <td class="labeltd"><label class="control-label">区域主键</label>
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
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>用户姓名</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="username" ng-model="username" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd" style="width:150px"><label
                        class="control-label">&nbsp;&nbsp;<span style="color:red">*</span>用户账户</label>
                </td>
                <td class="labeltd" style="text-align:left;" ng-click="remove()"><input
                        id="account" ng-model="account" class="form-control" readonly="readonly" disabled="disabled"/>
                </td>

            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;用户邮箱</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="email" ng-model="email" class="form-control"/>
                </td>
            </tr>
            <tr class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;用户性别</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        type="radio" name="sex" value="男" ng-model="sex"
                        style="width:16px;">男
                    <input type="radio" name="sex" value="女" ng-model="sex"
                           style="width:16px;">女
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;工作手机</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input class="form-control"
                                                                    ng-model="mobile"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;固定电话</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input class="form-control"
                                                                    ng-model="tel"/>
                </td>
            </tr>

            <tr class="valuetr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span style="color: red;"></span>是否管理员
                </label></td>
                <td class="valuetd">
                    <input type="radio" name="is_admin" ng-value="1" ng-model="is_admin">是
                    <input type="radio" name="is_admin" ng-value="0" ng-model="is_admin">否
                </td>
            </tr>

            <tr class="valuetr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span style="color: red;"></span>可用状态
                </label></td>
                <td class="valuetd">
                    <input type="radio" name="is_enabled" ng-value="1" ng-model="is_enabled">启用
                    <input type="radio" name="is_enabled" ng-value="0" ng-model="is_enabled">禁用
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>角色(可多选)</label>
                </td>
                <td class="labeltd" style="text-align:left;"><label
                        ng-repeat="auth in auths" ng-cloak style="font-weight:normal;">
                    <input style="width: auto;height: auto"
                           ng-model="auth.is_checked" type="checkbox"
                           id="auth_{{auth.int_id}}" name="auth" value="{{auth.int_id}}"/>
                    &nbsp;{{auth.name}} &nbsp;&nbsp;&nbsp; </label></td>
            </tr>
            </tbody>
        </table>
        <br/>
        <center>
            <input type="button" class="btn btn-large btn-primary" value="保  存"
                   ng-click="editMember()"/>
        </center>
    </div>
</div>
<script type="text/javascript" src="${ctx }/common/scripts/angularjs/underscore.js"></script>
<script type="text/javascript"
        src="${ctx }/module/member/scripts/editMember.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>