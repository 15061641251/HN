<%--
  Created by IntelliJ IDEA.
  User: STAR
  Date: 2016/11/14
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>


    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/form.jsp"/>

    <link
            href="${ctx}/common/style/table.css?v=<%=System.currentTimeMillis()%>"
            rel="stylesheet"/>

    <script src="${ctx}/common/js/md5.js" type="text/javascript"></script>
    <script type="text/javascript">
        function check() {
            var pwd = $("#newPwd").val();
            var pwd2 = $("#newPwdtow").val();
            var oldPwd = $("#oldPwd").val();
            var re = /[0-9]{6,12}$/;
            if (pwd == "" || pwd2 == "" || oldPwd == "") {
                layer.msg('填写完整信息', {
                    icon: 2
                });
            } else if (pwd != pwd2) {
                layer.msg('新密码不一致', {
                    icon: 2
                });
            } else if (!re.test(pwd)) {
                layer.msg('密码必须由长度为6～10位的数字组成', {
                    icon: 2
                });
            } else {
                var mdOldPwd = hex_md5(oldPwd);
                var mdNewPwd = hex_md5(pwd);

                $.ajax({
                    type: 'post',
                    async: false,
                    url: '${ctx}/member/editPassWord',
                    data: "oldPwd=" + mdOldPwd + "&newPwd=" + mdNewPwd,
                    success: function (msg) {
                        if (msg == "{success}") {
                            layer.msg('修改成功', {
                                icon: 1
                            });
                            setTimeout(
                                function () {
                                    window.close();
                                }, 1000);
                        } else {
                            layer.msg('原密码输入错误', {
                                icon: 2
                            });
                        }
                    }
                })
            }
        }

        function closeWin() {
            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
        }
    </script>
</head>

<body>
<div id="addMember" style="width:95%; padding-top: 10px;  padding-left:10px; padding-right:10px;padding-bottom:10px;">
    <form method="post" id="addMemberForm" name="showDetailForm">
        <div class="main-div">
            <table>
                <tbody>
                <tr style="height:41px;">
                    <td class="labeltd" style="width:150px;"><label class="control-label">&nbsp;&nbsp;原密码：</label>
                    </td>
                    <td class="labeltd" style="text-align:left;"><input id="oldPwd" name="oldPwd" type="password"/>
                    </td>
                </tr>
                <tr style="height:41px;">
                    <td class="labeltd" style="width:150px;"><label class="control-label">&nbsp;&nbsp;新密码：</label>
                    </td>
                    <td class="labeltd" style="text-align:left;"><input id="newPwd" name="newPwd"
                                                                        type="password"/>
                        <span>Tip：新密码必须为6～10位数字。</span>
                    </td>
                </tr>
                <tr style="height:41px;">
                    <td class="labeltd" style="width:150px;"><label class="control-label">&nbsp;&nbsp;确认新密码：</label>
                    </td>
                    <td class="labeltd" style="text-align:left;"><input id="newPwdtow" name="newPwdtow"
                                                                        type="password"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <br/>
        <center style="width: 100%;">
            <a class="btn btn-default" onclick="check()">提交</a>
        </center>
    </form>
</div>
</body>
</html>
