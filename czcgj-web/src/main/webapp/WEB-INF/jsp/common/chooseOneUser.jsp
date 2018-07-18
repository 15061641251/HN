<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <jsp:include page="../../common/header.jsp"/>
    <script type="text/javascript"
            src="${ctx}/common/ztree3.5/jquery.ztree.all-3.5.js"></script>
    <link rel="stylesheet"
          href="${ctx}/common/ztree3.5/zTreeStyle.css?v=<%=System.currentTimeMillis()%>"
          type="text/css"/>
    <link rel="stylesheet"
          href="${ctx}/common/style/style.css?v=<%=System.currentTimeMillis()%>"
          type="text/css"/>

    <script type="text/javascript">
        var zTree;

        var ids = "${ids}";

        $(function () {
            $.ajax({
                type: "post",
                url: "${ctx}/dept/getDeptMemListTree",
                data: "selectIds=" + ids,
                success: function (msg) {
                    var settings = {
                        check: {
                            enable: true,
                            chkStyle: "radio",
                            radioType: "all"
                        },
                        callback: {}
                    };
                    var nodes = eval('(' + msg + ')');

                    zTree = $.fn.zTree.init($("#groupTree"), settings, nodes);
                    var allNodes = zTree.getNodes();
                    var firstNode = allNodes[0];
                    zTree.expandNode(firstNode, true);
                }
            });
        });

        /**
         * 名称模糊搜索
         */
        function search(obj) {
            var name = obj.val();
            if ("" != name) {
                var nodes = zTree.getNodesByParamFuzzy("name", name);
                var searchNodes = "";

                if (nodes.length != 0) {
                    for (var i = 0; i < nodes.length; i++) {
                        if (nodes[i].flag == 1) {
                            searchNodes = nodes[i];
                            zTree.selectNode(searchNodes, true);
                        }
                    }
                } else {
                    layer.msg('搜索结果为空!', {
                        icon: 2
                    });
                    return false;
                }

            } else {
                layer.msg('请输入关键字,再搜索!', {
                    icon: 2
                });
                return false;
            }
        }

        /**
         * 保存
         */
        function saveMember() {

            var nodes = zTree.getCheckedNodes(true);
            var ids = "";
            var names = "";

            if (nodes.length !== 0) {
                for (var i = 0; i < nodes.length; i++) {
                    if (nodes[i].flag == 1) {
                        ids = ids + nodes[i].id + ",";
                        names = names + nodes[i].name + ",";
                    }
                }
                ids = ids.substr(0, ids.length - 1);
                names = names.substr(0, names.length - 1);
            }
            if (isEmpty(ids)) {
                layer.msg('请选择人员', {icon: 2});
            } else {
                var date = ids + "<split>" + names;
                parent.vm.setValue(date);
                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);
            }
        }

        function closeWin() {
            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
        }
    </script>

    <style type="text/css">
        .ztree li span.button.icon03_ico_docu {
            background: url(${ctx}/common/ztree3.5/img/people.gif);
        }

        .ztree li span.button.ico_docu {
            background: url(${ctx}/common/ztree3.5/img/folder_Close.gif);
        }

        #search {
            height: 30px;
        }

        #search_botton {
            border: none;
            background: url(${ctx}/common/images/table/btnbg.png) no-repeat 0 -185px;
            font-size: 14px;
            text-align: center;
            height: 30px;
            width: 60px;
            color: #fff;
            font-weight: bold;
            line-height: 30px;
            cursor: pointer;
            padding: 0;
        }

        #sure input {

        }

        #sure label {
            width: 25px;
        }
    </style>
</head>
<body>


<div style="width:100%;">
    <div style="padding-top: 8px;">
        <span style="padding-left: 15px;">姓名：</span>
        <input id="search" type="text" placeholder="全局搜索"/>
        <input id="search_botton" class="ManagerButton"
               type="button" value="搜索" onclick="search($('#search'))">
    </div>
    <%--<span>${formtype}</span>--%>
    <ul id="groupTree" class="ztree"
        style="overflow:auto;margin-buttom:50px;height:375px;"></ul>
</div>

<div id="sure"
     style="width:100%;height:40px;position:fixed;top:420px;text-align:center;z-index:999;">
    <input type="button" value="确定" class="ManagerButton"
           onclick="saveMember()">
    &nbsp;&nbsp; <input type="button" value="关闭" class="ManagerButtonNo" onclick="closeWin()"/>
</div>


</body>
</html>
