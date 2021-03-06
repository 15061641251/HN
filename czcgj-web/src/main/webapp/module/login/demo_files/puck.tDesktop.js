// "/static/js/tDesktop/tDesktop.js","/static/js/tDesktop/tDesktop.IM.js","/static/js/tDesktop/tDesktop.Layout.js","/static/js/tDesktop/tDesktop.Menu.js","/static/js/tDesktop/tDesktop.Nocbox.js","/static/js/tDesktop/tDesktop.Notification.js","/static/js/tDesktop/tDesktop.Pulse.js","/static/js/tDesktop/tDesktop.Search.js","/static/js/tDesktop/tDesktop.Tabs.js","/static/js/tDesktop/tDesktop.Theme.js","/static/js/tDesktop/tDesktop.Today.js"
/* "/static/js/tDesktop/tDesktop.js" */
define(
    'tDesktop',
    function (require, exports, module) {
        var $ = window.jQuery;
        var JSON = window.JSON;
        var TabObj = require('tDesktop/tDesktop.Tabs');
        var tab = TabObj.Tabs;
        var LayoutObj = require('tDesktop/tDesktop.Layout');
        var MenuObj = require('tDesktop/tDesktop.Menu');
        var ThemeObj = require('tDesktop/tDesktop.Theme');
        var theme = ThemeObj.Theme;
        var TodayObj = require('tDesktop/tDesktop.Today');
        var IMObj = require('tDesktop/tDesktop.IM');
        var NocboxObj = require('tDesktop/tDesktop.Nocbox');
        var nocbox = NocboxObj.Nocbox;
        var Search = require('tDesktop/tDesktop.Search');
        var Notification = require('tDesktop/tDesktop.Notification');
        var hasSearchModuleBeenInit;
        window.createTab = function (id, name, code, open_window) {
            tab.createTab(id, name, code, open_window);
            $('body').removeClass('showSearch').removeClass('right-mini');
            $('#eastbar,#searchbar').removeClass('on');
            if (tDesktop.isTouchDevice()) {
                $('#east').addClass('mobile-east').hide();
                $('#first_menu li div')
                    .removeClass('first-menu-item-hover');
                $('#menu-panel,#mask').hide();
            }
        };
        window.selectTab = function (id) {
            tab.selectTab(id);
        };
        window.closeTab = function (id) {
            id = (typeof (id) != 'string') ? tab.getSelected() : id;
            tab.closeTab(id);
        };
        window.IframeLoaded = function (id) {
            tab.IframeLoaded(id);
        };
        window.send_msg = function (uid, title) {
            IMObj.IM.Org.nodeclick(uid, title);
        }
        window.fixAvatar = function (uid) {
            IMObj.IM.RecentList.fixAvatar(uid);
        }
        var nextTabId = 10000;
        window.openURL = function (id, name, url, open_window, width,
                                   height, left, top) {
            id = !id ? ('w' + (nextTabId++)) : id;
            if (open_window != "1") {
                window.setTimeout(function () {
                    jQuery().addTab(id, name, url, true)
                }, 1);
            } else {
                width = typeof (width) == "undefined" ? 780 : width;
                height = typeof (height) == "undefined" ? 550 : height;
                left = typeof (left) == "undefined" ? (screen.availWidth - width) / 2
                    : left;
                top = typeof (top) == "undefined" ? (screen.availHeight - height) / 2 - 30
                    : top;
                window
                    .open(
                        url,
                        id,
                        "height="
                        + height
                        + ",width="
                        + width
                        + ",status=0,toolbar=no,menubar=yes,location=no,scrollbars=yes,top="
                        + top + ",left=" + left
                        + ",resizable=yes");
            }
            jQuery(document).trigger('click');
        }
        $.fn.addTab = function (id, title, url, closable, selected) {
            tab.addTab(id, title, url, closable, selected);
            $('body').removeClass('showSearch').removeClass('right-mini');
            $('#eastbar,#searchbar').removeClass('on');
            if (tDesktop.isTouchDevice()) {
                $('#east').addClass('mobile-east').hide();
            }
        };
        $.fn.selectTab = function (id) {
            tab.selectTab(id);
        };
        $.fn.closeTab = function (id) {
            id = (typeof (id) != 'string') ? tab.getSelected() : id;
            tab.closeTab(id);
        };
        $.fn.getSelected = function () {
            return $('#tabs_container').tabs('selected');
        };
        var Iscroll;
        var TDesktop = Backbone.View
            .extend({
                el: $('body'),
                iscroller: {},
                events: {
                    'click a#person_info': 'initPersonInfo',
                    'click a#logout': 'initLogout',
                    'click a#searchbar': 'initSearch',
                    'click a#totaskbar': 'initTaskCenter',
                    'click a#tosns': 'initSns',
                    'click a#eastbar': 'initEast',
                    'click ul#first_menu': 'touchDevicePanel'
                },
                initialize: function () {
                    if (TDesktop._instance) {
                        return TDesktop._instance;
                    }
                    _.bindAll(this, 'initPersonInfo', 'initLogout',
                        'initSearch', 'initEast', 'initTaskCenter',
                        'initSns');
                    var self = this;
                    self.EventManager = {};
                    _.extend(self.EventManager, Backbone.Events);
                    self.initIscrollEvent();
                    self.initIscrollRefresh();
                    self.initLayout();
                    self.initMenu();
                    self.initTabs();
                    self.initPortal();
                    self.initTheme();
                    self.initTip();
                    self.initToday();
                    self.initOnline();
                    self.initNotify();
                    self.initNocbox();
                    self.initIM();
                    self.initNotification();
                    self.initHeroBar();
                    TDesktop._instance = this;
                    if (self.isTouchDevice()) {
                        Iscroll = require('iscroll.js');
                        $('#east').hide();
                        $('body').addClass('mobile-body');
                        $('#center').addClass('mobile-center');
                        $('#west')
                            .prepend(
                                '<div id="menu-panel"><div id="menu-panel-scroller"></div></div>');
                        self.EventManager.trigger('iscroller:create',
                            'menu-panel');
                        $('#new_noc_list_wrapper').css({
                            "overflow": "hidden"
                        });
                        self.EventManager.trigger('iscroller:create',
                            "new_noc_list_wrapper");
                        $(window).resize(
                            function () {
                                var iscrollh = $('#west').height();
                                $('#west-body-wrapper').css({
                                    "height": iscrollh,
                                    "overflow": "hidden"
                                });
                                self.EventManager.trigger(
                                    'iscroller:refresh',
                                    'west-body-wrapper');
                            });
                        $("body")
                            .delegate(
                                '.first-menu li',
                                'click',
                                function () {
                                    $('#first_menu li div')
                                        .removeClass(
                                            'first-menu-item-hover');
                                    $(this)
                                        .children('div')
                                        .addClass(
                                            'first-menu-item-hover');
                                    var id = $(this).attr(
                                        'data-submenu-id');
                                    if ($('#first_menu li div')
                                            .hasClass(
                                                'first-menu-item-hover')) {
                                        $('#menu-panel').show();
                                        var menu_content = $(
                                            '#' + id)
                                            .html();
                                        $(
                                            '#menu-panel-scroller')
                                            .html(
                                                "<div>"
                                                + menu_content
                                                + "<div>");
                                        self.EventManager
                                            .trigger(
                                                'iscroller:refresh',
                                                'menu-panel');
                                        $('#mask').show();
                                    } else {
                                        $('#menu-panel,#mask')
                                            .hide();
                                    }
                                });
                        $('body')
                            .delegate(
                                '#mask',
                                'touchend',
                                function () {
                                    $('#first_menu li div')
                                        .removeClass(
                                            'first-menu-item-hover');
                                    $('#menu-panel,#mask')
                                        .hide();
                                });
                        document.addEventListener('touchmove',
                            function (e) {
                                e.preventDefault();
                            }, false);
                    }
                },
                initIscrollEvent: function () {
                    var self = this;
                    self.EventManager.on('iscroller:create', function (id) {
                        var myScroll = new IScroll('#' + id, {
                            scrollbars: false,
                            mouseWheel: true,
                            interactiveScrollbars: true,
                            shrinkScrollbars: 'scale',
                            preventDefault: false,
                            fadeScrollbars: true
                        });
                        self.iscroller[id] = myScroll;
                    });
                },
                initIscrollRefresh: function () {
                    var self = this;
                    self.EventManager.on('iscroller:refresh', function (id) {
                        var myScroll = self.iscroller[id];
                        myScroll && myScroll.refresh();
                    });
                },
                initTip: function () {
                    var self = this;
                    if (self.isTouchDevice()) {
                        return;
                    } else {
                        if (jQuery && jQuery.fn.tooltip) {
                            jQuery('[data-toggle="tooltip"]').tooltip({
                                container: 'body'
                            });
                        }
                    }
                },
                isTouchDevice: function () {
                    try {
                        document.createEvent("TouchEvent");
                        return userAgent.indexOf("mobile") >= 0
                            || userAgent.indexOf("maxthon") < 0;
                    } catch (e) {
                        return false;
                    }
                },
                initLayout: function () {
                    var layout = new LayoutObj.Layout({
                        tDesktop: this
                    });
                },
                initMenu: function () {
                    var self = this;
                    if (self.isTouchDevice()) {
                        $('.menu-scroll').remove();
                        var menu = new MenuObj.Menu.MenuInit({
                            tDesktop: this
                        });
                        var iscrollh = $('#west').height() - 40;
                        $('#west-body-wrapper').css({
                            "height": iscrollh,
                            "overflow": "hidden"
                        });
                        self.EventManager.trigger('iscroller:create',
                            'west-body-wrapper');
                        this.menu = menu;
                        this.EventManager.on('createTab', function () {
                            menu.hideActiveMenu();
                        });
                        $("#first_menu").resize();
                    } else {
                        var menu = new MenuObj.Menu.MenuInit({
                            tDesktop: this
                        });
                        var menuscroll = new MenuObj.Menu.MenuScroll({
                            tDesktop: this
                        });
                        menu.menuHover();
                        this.menu = menu;
                        this.EventManager.on('createTab', function () {
                            menu.hideActiveMenu();
                        });
                        $("#first_menu").resize();
                    }
                },
                initTabs: function () {
                    var self = this;
                    tab.init();
                    tab._createTab = tab.createTab;
                    tab.createTab = function () {
                        tab._createTab.apply(tab, arguments);
                        self.EventManager.trigger('createTab');
                    }
                },
                initPortal: function () {
                    for (var i = 0; i < portalLoadArray.length; i++) {
                        tab.addTab('portal_' + portalLoadArray[i].id,
                            portalLoadArray[i].title,
                            portalLoadArray[i].url,
                            portalLoadArray[i].closable, (i == 0));
                    }
                },
                initTheme: function () {
                    theme.init();
                },
                initSearch: function () {
                    if (!hasSearchModuleBeenInit) {
                        hasSearchModuleBeenInit = Search.init();
                    }
                    $("#eastbar").removeClass('on');
                    var self = this
                    if (self.isTouchDevice()) {
                        $('#east').addClass('mobile-east').hide();
                    }
                    $("#searchbar").toggleClass('on');
                    $('body').removeClass('right-mini').toggleClass(
                        'showSearch');
                },
                initToday: function () {
                    var deskdate = new TodayObj.Today.Deskdate({
                        tDesktop: this
                    });
                    var calendar = new TodayObj.Today.Calendar({
                        tDesktop: this
                    });
                    var reminder = new TodayObj.Today.Reminder({
                        tDesktop: this
                    });
                },
                initOnline: function () {
                    $('#online_flag').click(function () {
                        if ($('#on_status:visible').length > 0) {
                            $('#on_status').hide();
                        } else {
                            $('#on_status').show();
                        }
                    });
                    $('#on_status > a').click(
                        function () {
                            var status = $(this).attr('status');
                            if (status < "1" || status > "4")
                                return;
                            $.get("ipanel/ispirit_api.php", {
                                API: "on_status",
                                CONTENT: status
                            });
                            var target = $(this).find('i').text();
                            var targetClass = $(this).find('i')
                                .attr('class');
                            $('#online_flag').text(target).attr(
                                'class', targetClass);
                            $('#on_status').fadeOut(300);
                        });
                },
                initPersonInfo: function () {

                },
                initLogout: function () {
                    var relogin = 0;
                    var msg = sprintf(td_lang.inc.msg_109,
                            loginUser.user_name)
                        + "\n"
                        + sprintf(td_lang.inc.msg_110, logoutText
                            + "\n\n");
                    if (window.confirm(msg)) {
                        relogin = 1;
                        window.location = "/general/relogin.php";
                    }
                },
                initTaskCenter: function () {
                    $('body').addClass('left-mini');
                    window.createTab('taskcenter',
                        td_lang.general.project.msg_3,
                        'task_center', false);
                },
                initSns: function () {
                    window.createTab(73, func_array["f73"][0], 'sns',
                        '');
                },
                initEast: function () {
                    var self = this;
                    $("#searchbar").removeClass('on');
                    $("#eastbar").toggleClass('on');
                    $('body').removeClass('showSearch');
                    if (self.isTouchDevice()) {
                        $('#east').css('right', '0');
                        if ($("#eastbar").hasClass('on')) {
                            $('#east').addClass('mobile-east').show();
                        } else {
                            $('#east').addClass('mobile-east').hide();
                        }
                    } else {
                        $('body').toggleClass('right-mini');
                    }
                },
                initNocbox: function () {
                    nocbox.init({
                        tDesktop: this
                    });
                },
                formatTime: function (c) {
                    var c = parseInt(c) * 1000;
                    var a = new Date();
                    var g = new Date(c);
                    var b = new Date(a);
                    var f = a - c;
                    var e = "";

                    function d(h) {
                        return h < 10 ? ("0" + h) : h
                    }

                    if (f < 0) {
                        e = ""
                    } else {
                        if (f < (60 * 1000)) {
                            e = parseInt(f / 1000)
                                + td_lang.inc.msg_169
                        } else {
                            if (f < (60 * 60 * 1000)) {
                                e = parseInt(f / 60 / 1000)
                                    + td_lang.inc.msg_170
                            } else {
                                if (f < (24 * 60 * 60 * 1000)) {
                                    if (g.getDate() == b.getDate()) {
                                        e = td_lang.inc.msg_182
                                            + d(g.getHours()) + ":"
                                            + d(g.getMinutes())
                                    } else {
                                        e = (g.getMonth() + 1)
                                            + td_lang.inc.msg_171
                                            + g.getDate()
                                            + td_lang.inc.msg_172
                                            + d(g.getHours()) + ":"
                                            + d(g.getMinutes())
                                    }
                                } else {
                                    if (f < (365 * 24 * 60 * 60 * 1000)) {
                                        if (g.getFullYear() == b
                                                .getFullYear()) {
                                            e = (g.getMonth() + 1)
                                                + td_lang.inc.msg_171
                                                + g.getDate()
                                                + td_lang.inc.msg_172
                                                + d(g.getHours())
                                                + ":"
                                                + d(g.getMinutes())
                                        } else {
                                            e = g.getFullYear()
                                                + "-"
                                                + (g.getMonth() + 1)
                                                + "-" + g.getDate()
                                                + " "
                                                + d(g.getHours())
                                                + ":"
                                                + d(g.getMinutes())
                                        }
                                    } else {
                                        e = g.getFullYear() + "-"
                                            + (g.getMonth() + 1)
                                            + "-" + g.getDate()
                                            + " " + d(g.getHours())
                                            + ":"
                                            + d(g.getMinutes())
                                    }
                                }
                            }
                        }
                    }
                    return e;
                },
                initIM: function () {
                    var im = IMObj.IM;
                    im.init({
                        tDesktop: this
                    });
                },
                initNotify: function () {
                    if ($('#notify_panel').length > 0) {
                        $('#overlay').show();
                        $('#notify_panel').show();
                        $('#notify_panel .btn-ok').click(
                            function () {
                                var cookie_name = $(this).attr(
                                    "cookie_name");
                                var cookie_value = $(this).attr(
                                    "cookie_value");
                                document.cookie = cookie_name + "="
                                    + cookie_value;
                                path = "/";
                                expires = 1000;
                                $('#notify_panel .btn-close')
                                    .click();
                            });
                        $('#notify_panel .btn-close').click(function () {
                            $('#overlay').hide();
                            $('#notify_panel').hide();
                        });
                        $('#notify_panel .head-close').click(
                            function () {
                                $('#notify_panel .btn-close')
                                    .click();
                            });
                    }
                },
                initNotification: function () {
                    Notification.init(this);
                },
                initHeroBar: function () {
                    if ($('#tongdainfo').length <= 0)
                        return;
                    $
                        .get(
                            'tongda.php',
                            function (data) {
                                if (data.code == '1'
                                    && typeof (data.data) == 'object'
                                    && data.data.length > 0) {
                                    $('#tongdainfo a').remove();
                                    for (var i = 0; i < data.data.length; i++) {
                                        if (data.data[i].type == '1') {
                                            if (jQuery
                                                    .cookie('hide_notice_'
                                                        + data.data[i].id) != '1'
                                                && (data.data[i].flag != '1' || cur_login_user_priv == '1')) {
                                                jQuery(
                                                    '#notice_tip')
                                                    .show();
                                                jQuery(
                                                    '#notice_head')
                                                    .html(
                                                        data.data[i].text);
                                                jQuery(
                                                    '#notice_body')
                                                    .html(
                                                        data.data[i].content);
                                                jQuery(
                                                    '#notice_foot')
                                                    .html(
                                                        '<a onclick="jQuery(\'#notice_tip\').hide();jQuery.cookie(\'hide_notice_'
                                                        + data.data[i].id
                                                        + '\', 1, {expires:1000, path:\'/\'});" href="'
                                                        + data.data[i].href
                                                        + '" class="btn btn-small btn-primary" target="_blank">'
                                                        + td_lang.inc.msg_146
                                                        + '</a>&nbsp;<a href="javascript:;" class="btn btn-small" onclick="jQuery(\'#notice_tip\').hide();jQuery.cookie(\'hide_notice_'
                                                        + data.data[i].id
                                                        + '\', 1, {expires:1000, path:\'/\'});">'
                                                        + td_lang.global.close
                                                        + '</a>');
                                            }
                                        } else {
                                            $('#tongdainfo')
                                                .append(
                                                    '<a href="'
                                                    + data.data[i].href
                                                    + '" class="'
                                                    + data.data[i].class_name
                                                    + '" target="'
                                                    + data.data[i].target
                                                    + '" style="'
                                                    + data.data[i].style
                                                    + '"><span>'
                                                    + data.data[i].text
                                                    + '</span></a>');
                                        }
                                    }
                                }
                            }, 'json');
                }
            });
        TDesktop.getInstance = function () {
            return TDesktop._instance;
        };
        exports.TDesktop = TDesktop;
        window.TDesktop = TDesktop;
    });
/* "/static/js/tDesktop/tDesktop.IM.js" */
var formatTime = function (c) {
    var c = parseInt(c) * 1000;
    var a = new Date();
    a = a.getTime();
    var g = new Date(c);
    var b = new Date(a);
    var f = a - c;
    var e = "";

    function d(h) {
        return h < 10 ? ("0" + h) : h
    }

    if (f < 0) {
        e = "";
    } else {
        if (f < (60 * 1000)) {
            e = parseInt(f / 1000) + td_lang.inc.msg_169
        } else {
            if (f < (60 * 60 * 1000)) {
                e = parseInt(f / 60 / 1000) + td_lang.inc.msg_170
            } else {
                if (f < (24 * 60 * 60 * 1000)) {
                    if (g.getDate() == b.getDate()) {
                        e = td_lang.inc.msg_182 + d(g.getHours()) + ":"
                            + d(g.getMinutes())
                    } else {
                        e = (g.getMonth() + 1) + td_lang.inc.msg_171
                            + g.getDate() + td_lang.inc.msg_172
                            + d(g.getHours()) + ":" + d(g.getMinutes())
                    }
                } else {
                    if (f < (365 * 24 * 60 * 60 * 1000)) {
                        if (g.getFullYear() == b.getFullYear()) {
                            e = (g.getMonth() + 1) + td_lang.inc.msg_171
                                + g.getDate() + td_lang.inc.msg_172
                                + d(g.getHours()) + ":" + d(g.getMinutes())
                        } else {
                            e = g.getFullYear() + "-" + (g.getMonth() + 1)
                                + "-" + g.getDate() + " " + d(g.getHours())
                                + ":" + d(g.getMinutes())
                        }
                    } else {
                        e = g.getFullYear() + "-" + (g.getMonth() + 1) + "-"
                            + g.getDate() + " " + d(g.getHours()) + ":"
                            + d(g.getMinutes())
                    }
                }
            }
        }
    }
    return e
};
window.formatTime = formatTime;
define(
    'tDesktop/tDesktop.IM',
    function (require, exports, module) {
        var $ = window.jQuery;
        var Comet = {
            init: function (c) {
                var self = this;
                self.tDesktop = c.tDesktop;
                $("#msg-tool .btn").click(function () {
                    $(".msg-tool .btn").removeClass("btn-primary");
                    $(this).addClass("btn-primary");
                    var target = $(this).attr("msg-panel");
                    $(".msg-panel").removeClass("active");
                    $("#" + target).addClass("active");
                });
            },
            noc_mon: function () {
                var self = this;
                self.tDesktop.EventManager.trigger('loadNoc:load');
            },
        };
        var Chat = {
            init: function (c) {
                this.tDesktop = c.tDesktop;
                var self = this;
                self.hideChat();
                this.bindEvent();
            },
            uploader: {},
            bindEvent: function () {
                var self = this;
                self.tDesktop.EventManager.on('LoadSms:load', function () {
                    self.loadSms();
                });
                self.tDesktop.EventManager.on('msg:read', function (sms) {
                    var online = null;
                    $.ajax({
                        type: 'GET',
                        url: '/general/userinfo.php',
                        data: {
                            'UID': sms.from_id
                        },
                        success: function (d) {
                            online = d.data.online_flag;
                            sms.online_status = online;
                            sms.avatar = d.data.avatar;
                            self.render(sms);
                            self.showChat();
                            $('#smsbox_textarea').focus();
                        }
                    });
                });

                //TODO
                $('#smsbox_list_container')
                    .delegate(
                        '.chatuser',
                        'click',
                        function () {
                            //点击触发该方法
                            $(this).show();

                            //将聊天记录窗口所有记录信息去除
                            $('#smsbox_msg_container').find(
                                '.chatmsg').remove();

                            //选中人的id
                            var id = $(this).attr('from');

                            //设置编辑消息textarea获取焦点
                            $('#smsbox_textarea').html("").focus();

                            //左侧所有正在对话的人员去掉选中样式
                            $('.chatuser').removeClass(
                                'active unread');

                            //左侧选择需要聊天的人选中
                            $(this).addClass('active');

                            //去掉左侧选中人的未读消息数
                            $(this).children(".count").text("");

                            //右侧主聊天窗口标题,显示对方名称
                            $('#chat-title').text(
                                $(this).find('span').text());

                            //根据元素属性获取人员状态显示对面图标
                            var online = $(this).attr("online");
                            if (online == 1) {
                                $('#chat-status').attr("class",
                                    "chat-status chat-online");
                            } else if (online == 2) {
                                $('#chat-status').attr("class",
                                    "chat-status chat-busy");
                            } else if (online == 3) {
                                $('#chat-status').attr("class",
                                    "chat-status chat-leave");
                            } else {
                                $('#chat-status').attr("class",
                                    "chat-status chat-offline");
                            }

                            //显示历史记录
                            // if (self.historyMsg.users[id]
                            //     && self.historyMsg.users[id].curPage !== 1) {
                            //     self.historyMsg
                            //         .render(self.historyMsg.users[id].historyMessage);
                            // }
                            //
                            // self.historyMsg.setHref(id);

                            //遍历获取和左侧选中人的会话消息
                            for (var i = 0; i < newSmsArray.length; i++) {
                                if (newSmsArray[i].type == 'chat') {
                                    if (!(newSmsArray[i].from == id) && !(newSmsArray[i].to == id))
                                        continue;
                                } else if (newSmsArray[i].type == 'groupchat') {
                                    if (newSmsArray[i].to != id)
                                        continue;
                                }


                                var data = newSmsArray[i];
                                // data.data = newSmsArray[i].data;
                                // data.from = newSmsArray[i].from;
                                // data.id = newSmsArray[i].id;
                                // data.to = newSmsArray[i].to;
                                // data.type = newSmsArray[i].type;
                                // data.time = newSmsArray[i].time;
                                // data.mytype = newSmsArray[i].mytype;
                                newSmsArray[i].isread = 1;

                                //通过数据模型和模板返回最终的html

                                var html = getIMMsgHTML(data);

                                $('#smsbox_msg_container').append(
                                    html);

                                $("#smsbox_msg_container")
                                    .scrollTop(
                                        $("#smsbox_msg_container")[0].scrollHeight);
                            }


                        });

                //左侧联系人关闭按钮点击事件
                $("#smsbox_list_container")
                    .delegate(
                        ".chatuser i",
                        "click",
                        function () {
                            var target = $(this)
                                .parent(".chatuser");
                            var id = target.attr("group_id");
                            var prev = target.prev();
                            var next = target.next();
                            target.remove();
                            if (next.length > 0) {
                                next.trigger('click');
                            } else if (prev.length > 0) {
                                prev.trigger('click');
                            } else {
                                self.hideChat();
                            }
                            $('#chat-delete').hide();
                            if (self.historyMsg.users[id]) {
                                self.historyMsg.users[id].curPage = 1;
                                self.historyMsg.users[id].historyMessage = [];
                            }
                            return false;
                        });
                $("#smsbox_msg_container")
                    .delegate(
                        ".imPanel[node-type='tMImage']",
                        "click",
                        function (e) {
                            var _href = $(this).attr('node-data');
                            e.stopPropagation();
                            if (typeof (window.external.PlayVoiceMsg) == "undefined") {
                                mytop = (screen.availHeight - 510) / 2;
                                myleft = (screen.availWidth - 660) / 2;
                                window
                                    .open(
                                        _href,
                                        "imageShow",
                                        "height=510, width=660, top="
                                        + mytop
                                        + ", left="
                                        + myleft
                                        + ",toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
                            } else {
                                if (parent && parent.openURL) {
                                    parent.openURL(_href);
                                }
                            }
                            return;
                        });
                $('#smsbox_toolbar_del').click(
                    function () {
                        if ($('.chatuser:visible').length > 1) {
                            $('#chat-delete').show();
                        } else {
                            $('#smsbox_list_container').html("");
                            $('#smsbox_msg_container').find('.chatmsg')
                                .remove();
                            self.hideChat();
                        }
                        self.historyMsg.users = [];
                    });

                $('#smsbox_toolbar_shrink').click(function () {
                    if ($(this).text() === "-") {
                        self.minimize();
                    } else {
                        self.maximize();
                        //去掉提示框
                        $(".msg-wrapper").animate({top: "0px"}, "100");
                    }
                });
                $("#quickBar").click(
                    function () {
                        $(".quickReply").toggleClass("active");
                        $("#smsbox_rapid_reply option:first").attr(
                            'selected', 'selected');
                    });
                $('#closeAllChat').click(function () {
                    $('#smsbox_list_container').html("");
                    $('#smsbox_msg_container').find('.chatmsg').remove();
                    $('#chat-delete').hide();
                    self.hideChat();
                });
                $('#closeActiveChat').click(function () {
                    var active = $('.chatuser.active');
                    var prev = $('.chatuser.active').prev();
                    var next = $('.chatuser.active').next();
                    active.remove();
                    if (next.length > 0 && next.is(":visible")) {
                        next.trigger('click');
                    } else if (prev.length > 0 && prev.is(":visible")) {
                        prev.trigger('click');
                    } else {
                        self.hideChat();
                    }
                    $('#chat-delete').hide();
                });
                $('#chatDeleteClose').click(function () {
                    $('#chat-delete').hide();
                });
                $('#smsbox_rapid_reply').change(
                    function () {
                        if (this.selectedIndex == 0)
                            return;
                        $('#smsbox_textarea').val(
                            this.options[this.selectedIndex].text)
                    });
                $('#moreHistoryMsg').click(
                    function () {
                        var currentUser = $('.chatuser').filter(
                            function (index, item) {
                                if ($(item).hasClass('active'))
                                    return true;
                            });
                        var currentUid = $(currentUser)
                            .attr('group_id');
                        self.historyMsg.loadMore(currentUid);
                    })
            },
            showChat: function () {
                this.maximize();
                $('#chat-wrapper').show();
                $('#chat-wrapper').addClass("active");
            },
            hideChat: function () {
                $('#chat-wrapper').removeClass("active");
                $('#chat-wrapper').hide();
            },
            render: function (sms) {
                var self = this;
                sms.count = 0;
                if ($('.chatuser[user="' + sms.from_id + '"]').length <= 0) {
                    $('#smsbox_list_container').append(
                        $("#chatUser-template").tmpl(sms));
                    $(
                        '#smsbox_list_container .chatuser[user='
                        + sms.from_id + ']').trigger("click");
                } else {
                    $('.chatuser[user="' + sms.from_id + '"]').attr(
                        "online", sms.online_status);
                    $('.chatuser[user="' + sms.from_id + '"]').trigger(
                        'click');
                }
            },
            loadSms: function (flag) {
                var self = this;
                flag = typeof (flag) == "undefined" ? "1" : "0";
            },
            strlen: function (str) {
                return str.replace(/[^\x00-\xff]/g, 'xx').length;
            },
            getSmsIds: function () {
                var recvIds = sendIds = '';
                for (var i = newSmsArray.length - 1; i >= 0; i--) {
                    if (newSmsArray[i].sms_id == '')
                        continue;
                    if (newSmsArray[i].receive == '1')
                        recvIds += newSmsArray[i].sms_id + ',';
                    else
                        sendIds += newSmsArray[i].sms_id + ',';
                }
                return {
                    recv: recvIds,
                    send: sendIds
                };
            },
            historyMsg: {
                users: [],
                curPage: 1,
                maxPageLimit: 10,
                itemsPerPage: 6,
                $el: $('#moreHistoryMsg'),
                init: function () {
                },
                loadMore: function (currentUid) {
                    var self = this;
                    var uid = currentUid;
                    if (self.users[uid] === undefined) {
                        self.users[uid] = {
                            curPage: 1,
                            lastId: '',
                            historyMessage: []
                        };
                    }
                    self.users[uid].lastId = $('#moreHistoryMsg').next()
                            .attr('sms_id')
                        || '';
                    if (self.users[uid].curPage < self.maxPageLimit) {
                        self.$el.find('a').hide();
                        self.$el.find('img').show();
                        self.fetchData(uid, self.users[uid]);
                    } else {
                        self.setHref(uid);
                    }
                },
                fetchData: function (uid, user) {
                    var self = this;
                },
                render: function (data) {
                    data.sort(function (a, b) {
                        return a.sms_id - b.sms_id;
                    });
                    $
                        .each(
                            data,
                            function (index, item) {
                                item.content = decodeURIComponent(item.content);
                                item.time = decodeURIComponent(item.send_time);
                                if (item.avatar.indexOf(".") != -1
                                    && item.avatar.indexOf('/') == -1) {
                                    item.avatar = '/inc/attach_old.php?ATTACHMENT_ID=avatar&ATTACHMENT_NAME='
                                        + item.avatar
                                        + '&DIRECT_VIEW=1';
                                } else if (item.avatar.indexOf('/') != -1) {
                                    item.avatar = item.avatar;
                                } else {
                                    item.avatar = static_server
                                        + '/static/images/avatar/avatar_'
                                        + item.avatar + '.jpg';
                                }
                            })
                    $("#chatMsg-template").tmpl(data).insertAfter(
                        '#moreHistoryMsg');
                },
                setHref: function (id) {
                    if (!this.users[id]) {
                        $('#moreHistoryMsg').find('a').removeAttr('href');
                        return;
                    }
                    if (this.users[id].curPage < this.maxPageLimit) {
                        $('#moreHistoryMsg').find('a').removeAttr('href');
                    } else {
                        $('#moreHistoryMsg').find('a').attr(
                            'href',
                            '/general/sms/msg_center/user_msg.php?UID='
                            + id);
                    }
                }
            },
            minimize: function () {
                $('#chat-wrapper').addClass('mini');
                $('#smsbox_toolbar_shrink').html(
                    '<i class="iconfont">&#xe680;</i>');
            },
            maximize: function () {
                $('#chat-wrapper').removeClass('mini');
                $('#smsbox_toolbar_shrink').html('-');
            }
        };
        var RecentList = {
            init: function (c) {
                this.tDesktop = c.tDesktop;
                this.render();
                this.bindEvent();
            },
            HTML2Text: function (html) {
                var div = document.createElement('div');
                div.innerHTML = html;
                return div.innerText;
            },
            fixAvatar: function (uid) {
                var avatar = "";
                $
                    .ajax({
                        type: 'GET',
                        url: '/general/userinfo.php',
                        data: {
                            'UID': uid
                        },
                        success: function (d) {
                            avatar = d.data.avatar;
                            $('#recentlist [fromid="' + uid + '"]')
                                .find('img').attr('src', avatar);
                            if (window.localStorage
                                || typeof window.localStorage != 'undefined') {
                                for (var i = 0; i < localStorage.length; i++) {
                                    var key = localStorage.key(i);
                                    if (key == ("tdesktop_" + uid)) {
                                        var value = localStorage
                                            .getItem(key);
                                        value = Base64.decode(value);
                                        try {
                                            var data = JSON
                                                .parse(value);
                                            data.avatar = avatar;
                                            str = Base64.encode(JSON
                                                .stringify(data));
                                            localStorage.setItem(key,
                                                str);
                                        } catch (e) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    });
            },
            render: function () {
                var count = 0;
                var self = this;
                if (window.localStorage
                    || typeof window.localStorage != 'undefined') {
                    for (var i = 0; i < localStorage.length; i++) {
                        var key = localStorage.key(i);
                        if (key.indexOf("tdesktop_") != -1) {
                            var value = localStorage.getItem(key);
                            value = Base64.decode(value);
                            try {
                                var data = JSON.parse(value);
                            } catch (e) {
                                break;
                            }
                            data.from_name = decodeURIComponent(data.from_name);
                            data.content = decodeURIComponent(data.content);
                            var pattern = /<br \/>/g;
                            data.content = data.content.replace(pattern,
                                '\n');
                            data.content = self.HTML2Text(data.content);
                            if (data.from_id == loginUser.uid
                                || data.to_id != loginUser.uid) {
                                continue;
                            } else {
                                count++;
                            }
                            data.time_stamp = data.send_time_stamp ? formatTime(data.send_time_stamp)
                                : data.send_time;
                            if ($("a[fromid='" + data.from_id + "']").length > 0) {
                                $("a[fromid='" + data.from_id + "']").find(
                                    'img').attr('src', data.avatar);
                                $("a[fromid='" + data.from_id + "']").find(
                                    '.rec_time').text(data.time_stamp);
                                $("a[fromid='" + data.from_id + "']").find(
                                    '.rec_content').text(data.content);
                            } else {
                                var element = $("#recentList-template")
                                    .tmpl(data);
                                $('.recentlist').prepend(element);
                            }
                        }
                    }
                    if (count > 0) {
                        $("#rec_tips").hide();
                        $('.recentlist').show();
                    } else {
                        $("#rec_tips").show();
                        $('.recentlist').hide();
                    }
                }
                this.getCount();
            },
            openSend: function () {
                $(".weixun-panel").removeClass("active");
                $("#send").addClass("active");
            },
            bindEvent: function () {
                var self = this;
                self.tDesktop.EventManager.on('msg:read', function (sms) {
                    self.updateCount(sms.from_id);
                });
                self.tDesktop.EventManager.on('msg:update', function (sms) {
                    self.render();
                });
                $("#tosend").click(
                    function () {
                        self.openSend();
                        self.tDesktop.EventManager
                            .trigger('multisend:in');
                        self.tDesktop.EventManager
                            .trigger('MultiSend:initUpload');
                    });
                $(".recentlist")
                    .delegate(
                        ".recentItem",
                        "click",
                        function () {
                            $(".recentItem").removeClass("active");
                            $(this).addClass("active");
                            var fromid = $(this).attr('fromid');
                            var fromname = $(this)
                                .find('.rec_name').text();
                            var avatar = $(this).find('img').attr(
                                'src');
                            var sms = {
                                sms_id: "",
                                avatar: avatar,
                                to_id: "",
                                from_id: fromid,
                                from_name: fromname,
                                from_type_name: "",
                                type_id: "",
                                type_name: "",
                                send_time: "",
                                unread: null,
                                content: "",
                                receive: ""
                            };
                            self.tDesktop.EventManager.trigger(
                                'msg:read', sms);
                        });
                $("#refresh-rec").click(function () {
                    self.render();
                });
                $("#clear-rec").click(function () {
                    self.clearAll();
                });
            },
            clearAll: function () {
                var self = this;
                if (window.localStorage
                    || typeof window.localStorage != 'undefined') {
                    for (var i = 0; i < localStorage.length; i++) {
                        var key = localStorage.key(i);
                        if (key.indexOf("tdesktop_") != -1) {
                            var value = localStorage.getItem(key);
                            value = Base64.decode(value);
                            try {
                                var data = JSON.parse(value);
                            } catch (e) {
                                break;
                            }
                            data.from_name = decodeURIComponent(data.from_name);
                            data.content = decodeURIComponent(data.content);
                            if (data.from_id == loginUser.uid
                                || data.to_id == loginUser.uid) {
                                localStorage.removeItem("tdesktop_"
                                    + data.from_id);
                                $("#recentlist").html("").hide();
                                $("#rec_tips").show();
                            }
                        }
                    }
                }
            },
            getCount: function () {
            },
            updateCount: function (id) {
            }
        };
        var MultiSend = {
            init: function (c) {
                this.tDesktop = c.tDesktop;
                this.bindEvent();
                this.initAutocomplete();
            },
            bindEvent: function () {
                var self = this;
                self.tDesktop.EventManager.on('MultiSend:initUpload',
                    function () {
                        self.initUpload();
                    });
                self.usertags = $('#user-tags')
                    .tags(
                        {
                            callbacks: {
                                remove: function () {
                                    var c = self.usertags
                                        .serialize();
                                    $('#userid').val(
                                        c.value ? c.value + ','
                                            : c.value);
                                    $('#username').val(
                                        c.text ? c.text + ','
                                            : c.text);
                                    self.setTagControl("del");
                                },
                                clear: function () {
                                    $("#userid").val("");
                                    $("#username").val("");
                                    $("#clearall").hide();
                                    $("#getmore").hide();
                                    $("#no-tag-tips").show();
                                }
                            }
                        }).data('tags');
                $(window).resize(function () {
                    var count = $("#user-tags .tag").length;
                    if (count >= 1) {
                        $("#no-tag-tips").hide();
                        $("#clearall").show();
                    } else {
                        $("#no-tag-tips").show();
                        $("#clearall").hide();
                    }
                    self.showGetMore();
                });
                $("#torecent").click(function () {
                    $(".weixun-panel").removeClass("active");
                    $("#recent").addClass("active");
                });
                $('#adduser')
                    .click(
                        function (event) {
                            var module_id = 'tdesktop', to_id = "TO_UID", to_name = "TO_NAME", manage_flag, form_name = "";
                            window.org_select_callbacks = window.org_select_callbacks
                                || {};
                            window.org_select_callbacks.add = function (item_id, item_name) {
                                self.usertags.add({
                                    value: item_id,
                                    text: item_name
                                });
                                self.setTagControl();
                            };
                            window.org_select_callbacks.remove = function (item_id, item_name) {
                                self.usertags.remove(item_id);
                            };
                            window.org_select_callbacks.clear = function () {
                                self.usertags.clear();
                            };
                            SelectUser('', module_id, to_id,
                                to_name, manage_flag,
                                form_name, '', event);
                            return false;
                        });
                $("#getmore").click(function () {
                    $(".sendwrapper").addClass("on");
                    $("#user-tags").css("height", "auto");
                    $("#getmore").hide();
                });
                $("#clearall").click(function () {
                    self.usertags.clear();
                });
                $("#sms_send")
                    .click(
                        function () {
                            var userid = $("#userid").val();
                            var username = $("#username").val();
                            var content = $("#sms_content").val();
                            var attachidstr = "", attachnamestr = "", attachstr = "";
                            $("#uploadlist .item")
                                .each(
                                    function () {
                                        attachnamestr += $(
                                                this).attr(
                                                "title")
                                            + ",";
                                        attachidstr += $(
                                            this).find(
                                            ".fileid")
                                            .val();
                                        var field = $(this)
                                            .find(
                                                ".fileid")
                                            .val();
                                        attachstr += field
                                                .substr(
                                                    0,
                                                    field.length - 1)
                                            + "."
                                            + $(this)
                                                .attr(
                                                    "title")
                                            + ",";
                                    });
                            self.send(userid, username, content,
                                attachidstr, attachnamestr,
                                attachstr);
                        });
            },
            send: function (userid, username, content, attachidstr,
                            attachnamestr, attachstr) {
                if ($.trim(userid) == "") {
                    alert(unselectuser);
                    return;
                }
                if ($.trim(content) == "" && attachidstr == "") {
                    alert(no_content_tip);
                    return;
                }
                var self = this;
                var $button = $("#sms_send");
                $button.button('loading');
                $.ajax({
                    url: 'index_simple_submit.inc.php',
                    data: {
                        useridstr: userid,
                        username: username,
                        content: content,
                        attachidstr: attachidstr,
                        attachnamestr: attachnamestr,
                        attachstr: attachstr,
                        flag: 1,
                        'charset': 'utf-8'
                    },
                    async: true,
                    type: 'get',
                    success: function (d) {
                        if (d = "ok") {
                            self.usertags.clear();
                            $("#userid,#username,#sms_content").val("");
                            $("#no-tag-tips").show();
                            $("#clearall").hide();
                            $("#uploadlist").html("");
                            self.uploader.reset();
                            $button.button('reset');
                            alert(sendsuccess);
                        }
                    },
                    error: function () {
                        $button.button('reset');
                    }
                });
            },
            initAutocomplete: function () {
                var self = this;
                $.fn.typeahead.Constructor.prototype.blur = function () {
                    var that = this;
                    setTimeout(function () {
                        that.hide()
                    }, 250);
                };
                var that = this;
                var userlist = {};
                $('#search').typeahead({
                    source: function (query, process) {
                        $.ajax({
                            url: 'search.php',
                            data: {
                                term: query
                            },
                            async: true,
                            type: 'get',
                            success: function (d) {
                                d = JSON.parse(d);
                                userlist = d;
                                var results = _.map(d, function (user) {
                                    if (user.id !== "__SEARCH_HELP__")
                                        return user.id + "";
                                });
                                process(results);
                            }
                        });
                    },
                    matcher: function (item) {
                        return true;
                    },
                    highlighter: function (id) {
                        var user = _.find(userlist, function (p) {
                            return p.id == id;
                        });
                        return user.value;
                    },
                    updater: function (id) {
                        var user = _.find(userlist, function (p) {
                            return p.id == id;
                        });
                        that.setSelectedUser(user);
                    }
                });
                this.setSelectedUser = function (user) {
                    self.usertags.add({
                        value: user.id,
                        text: user.value
                    });
                    var namestr = $("#userid").val();
                    var textstr = $("#username").val();
                    var namearr = namestr.split(",");
                    var textarr = textstr.split(",");
                    if (namestr.indexOf(user.id) >= 0) {
                        return;
                    } else {
                        namestr += user.id + ",";
                        textstr += user.value + ",";
                        $("#userid").val(namestr);
                        $("#username").val(textstr);
                        self.setTagControl();
                    }
                }
            },
            setTagControl: function (del) {
                var count = $("#user-tags .tag").length;
                if (del == "del") {
                    if (count > 1) {
                        $("#no-tag-tips").hide();
                        $("#clearall").show();
                    } else {
                        $("#no-tag-tips").show();
                        $("#clearall").hide();
                    }
                } else {
                    if (count >= 0) {
                        $("#no-tag-tips").hide();
                        $("#clearall").show();
                    } else {
                        $("#no-tag-tips").show();
                        $("#clearall").hide();
                    }
                }
                this.showGetMore();
            },
            showGetMore: function () {
                $("#user-tags").css("height", "auto");

                var scrollheight = 0;

                if($("#user-tags")[0] != undefined && $("#user-tags")[0].hasOwnProperty("scrollHeight")){
                    scrollheight = $("#user-tags")[0].scrollHeight;
                }

                var height = $("#user-tags").height();
                if (scrollheight > height) {
                    var tag_h = $("#user-tags .tag").outerHeight(true);
                    var availnum = Math.floor(height / tag_h);
                    availnum = parseInt(availnum);
                    height = tag_h * (availnum);
                    $("#user-tags").height(height);
                    var topValue = $("#user-tags").position().top + height;
                    $("#getmore").show();
                    $("#getmore").css("top", topValue + "px");
                } else {
                    $("#getmore").hide();
                }
            },
            initUpload: function () {
                var self = this, $list = $('#uploadlist');
                if (self.uploader) {
                    return;
                }
                var uploader = new WebUploader.Uploader({
                    resize: false,
                    auto: true,
                    swf: '/static/js/webuploader/Uploader.swf',
                    server: '/module/upload/upload.php',
                    pick: '#picker',
                    fileNumLimit: 5
                });
                self.uploader = uploader;
                self.uploader.on('fileQueued', function (file) {
                    self.addFile(file);
                });
                self.uploader.on('error', function (handler) {
                    if (handler == "Q_EXCEED_NUM_LIMIT") {
                        alert(uploadnumlimit);
                    }
                    if (handler == "F_DUPLICATE") {
                        alert(uploadduplicate);
                    }
                });
                self.uploader
                    .on(
                        'uploadProgress',
                        function (file, percentage) {
                            var $li = $('#' + file.id), $percent = $li
                                .find('.progress .bar');
                            if (!$percent.length) {
                                $percent = $(
                                    '<div class="progress progress-striped active">'
                                    + '<div class="bar" role="progressbar" style="width: 0%">'
                                    + '</div>'
                                    + '</div>')
                                    .appendTo($li).find('.bar');
                            }
                            $percent.css('width', percentage * 100
                                + '%');
                        });
                self.uploader.on('uploadComplete', function (file) {
                    $('#' + file.id).find('.progress').fadeOut();
                });
                self.uploader.on('uploadSuccess', function (file) {
                    $('#' + file.id + " img")
                        .attr("src", arguments[1].icon);
                    $('#' + file.id + " .fileid").val(arguments[1].id);
                });
                self.uploader.on('uploadError', function (file) {
                    alert(file.name + uploaderror);
                });
                self.tDesktop.EventManager.on('multisend:in', function () {
                    self.uploader.refresh();
                });
            },
            addFile: function (file) {
                var self = this, $list = $('#uploadlist');
                $list
                    .append('<div id="'
                        + file.id
                        + '" class="item" title="'
                        + file.name
                        + '">'
                        + '<span class="filename"></span><img src="" /><input type="hidden" class="fileid" /><div class="close"></div>'
                        + '</div>');
                var $li = $('#' + file.id);
                $li.on('click', '.close', function () {
                    self.removeFile(file);
                });
            },
            removeFile: function (file) {
                var self = this;
                var fileid = $('#' + file.id + " .fileid").val();
                $.ajax({
                    url: '/module/upload/delete.php',
                    data: {
                        fileid: fileid,
                        filename: file.name
                    },
                    async: true,
                    type: 'get',
                    success: function (d) {
                        self.uploader.removeFile(file);
                        $("#" + file.id).remove();
                    }
                });
            }
        };
        var Org = {
            init: function (c) {
                this.tDesktop = c.tDesktop;
                this.bindEvent();
                this.getOnlineCount();
            },
            bindEvent: function () {
                var self = this;
                $('#user_online')
                    .bind(
                        '_show',
                        function () {
                            var ipanel_org = frames['orgTree0_iframe'];
                            if (ipanel_org) {
                                if (ipanel_org.contentWindow
                                    && typeof (ipanel_org.contentWindow.tree) == "object") {
                                    ipanel_org.contentWindow.tree
                                        .reload();
                                } else {
                                    ipanel_org.tree.reload();
                                }
                                if (timer_online_tree_ref) {
                                    window
                                        .clearInterval(timer_online_tree_ref);
                                }
                                timer_online_tree_ref = window
                                    .setInterval(
                                        function () {
                                            var ipanel_org1 = frames['orgTree0_iframe'];
                                            if (ipanel_org1.contentWindow
                                                && typeof (ipanel_org1.contentWindow.tree) == "object") {
                                                ipanel_org1.contentWindow.tree
                                                    .reload();
                                            } else {
                                                ipanel_org1.tree
                                                    .reload();
                                            }
                                        },
                                        monInterval.online * 5 * 1000);
                            } else {
                                self.getOrg();
                            }
                        });
                $('#user_online').bind('_hide', function () {
                    if (timer_online_tree_ref)
                        window.clearInterval(timer_online_tree_ref);
                });
                self.tDesktop.EventManager.on('online:comet', function () {
                    $('#user_online').triggerHandler('_show');
                    self.getOnlineCount();
                });
                self.tDesktop.EventManager.on('online:stopcomet',
                    function () {
                        $('#user_online').triggerHandler('_hide');
                    });
                $('#org_tool .btn').click(function () {
                    var target = $(this).attr('user-type');
                    $('#org_tool .btn').removeClass('btn-primary');
                    $(this).addClass('btn-primary');
                    $('.online-panel').hide();
                    $('#' + target).show();
                });
            },
            getOnlineCount: function () {
//					$
//							.ajax({
//								async : true,
//								url : 'ipanel/user/user_count.php',
//								dataType : 'text',
//								success : function(data) {
//									try {
//										eval(data);
//									} catch (ex) {
//									}
//									online_count = typeof (online_array) == 'object' ? online_array[0]
//											: 0;
//									$(".user_online").text(online_count);
//								},
//								error : function(request, textStatus,
//										errorThrown) {
//								}
//							});
            },
            getOrg: function () {
                if ($("#orgTree0").html() == "") {
                    $("#orgTree0")
                        .html(
                            '<iframe id="orgTree0_iframe" allowTransparency= "true" src="" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:98%;"></iframe>');
                    $("#orgTree0 iframe")
                        .attr(
                            'src',
                            ("/inc/user_tree.php?FROM=WebOS&TREE_ID=orgTree0&SHOW_IP="
                            + show_ip
                            + "&SHOW_BUTTON="
                            + show_button + "&JSON_URL=" + jsonURL0));
                }
                if ($("#orgTree1").html() == "") {
                    $("#orgTree1")
                        .html(
                            '<iframe id="orgTree1_iframe" allowTransparency= "true" src="" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:98%;"></iframe>');
                    $("#orgTree1 iframe")
                        .attr(
                            'src',
                            ("/inc/user_tree.php?FROM=WebOS&TREE_ID=orgTree1&SHOW_IP="
                            + show_ip
                            + "&SHOW_BUTTON="
                            + show_button + "&JSON_URL=" + jsonURL1));
                }
            },
            nodeclick: function (uid, title) {
                var self = this;
                var avatar = "";
                $
                    .ajax({
                        type: 'GET',
                        url: '/general/userinfo.php',
                        data: {
                            'UID': uid
                        },
                        success: function (d) {
                            avatar = d.data.avatar;
                            var sms = {
                                sms_id: "",
                                avatar: avatar,
                                to_id: "",
                                from_id: uid,
                                from_name: title,
                                from_type_name: "",
                                type_id: "",
                                type_name: "",
                                send_time: "",
                                unread: null,
                                content: "",
                                receive: ""
                            };
                            self.tDesktop.EventManager.trigger(
                                'msg:read', sms);
                        }
                    });
            }
        };
        exports.IM = {
            init: function (c) {
                this.tDesktop = c.tDesktop;
                Comet.init({
                    tDesktop: this.tDesktop
                });
                MultiSend.init({
                    tDesktop: this.tDesktop
                });
                Chat.init({
                    tDesktop: this.tDesktop
                });
                RecentList.init({
                    tDesktop: this.tDesktop
                });
                Org.init({
                    tDesktop: this.tDesktop
                });
            },
            Comet: Comet,
            Chat: Chat,
            RecentList: RecentList,
            MultiSend: MultiSend,
            Org: Org
        }
    });
/* "/static/js/tDesktop/tDesktop.Layout.js" */
define('tDesktop/tDesktop.Layout', function (require, exports, module) {
    var $ = window.jQuery;
    var Layout = Backbone.View.extend({
        el: 'body',
        events: {
            'click a.west-handle': 'leftHandle',
            'click a.east-handle': 'rightHandle',
            'click #funcmenu_switcher': 'toggleMenu'
        },
        initialize: function (c) {
            _.bindAll(this, 'leftHandle', 'rightHandle', 'toggleMenu');
            this.initEastPanel();
            this.initRegPanel();
            this.tDesktop = c.tDesktop;
            if (localStorage.open_menu == 'open-menu') {
                $(this.el).addClass('open-menu');
                $('.funcmenu_switcher').addClass('active');
            } else {
                $(this.el).removeClass('open-menu');
                $('.funcmenu_switcher').removeClass('active');
            }
            if (localStorage.left_mini == 'left-mini') {
                $(this.el).addClass('left-mini');
            } else {
                $(this.el).removeClass('left-mini');
            }
        },
        initRegPanel: function () {
            if ($('#hero_bar').length > 0) {
                $('#east,#west,#center').css('bottom', '61px');
            } else {
                $('#east,#west,#center').css('bottom', '0');
            }
        },
        leftHandle: function () {
            $(this.el).toggleClass('left-mini');
            if (window.localStorage
                || typeof window.localStorage != 'undefined') {
                if (localStorage.left_mini == ''
                    || localStorage.left_mini == undefined) {
                    localStorage.left_mini = 'left-mini';
                } else {
                    localStorage.left_mini = '';
                }
            }
        },
        rightHandle: function () {
            $(this.el).toggleClass('right-mini');
            $('#eastbar').toggleClass('on');
        },
        toggleMenu: function () {
            $(this.el).toggleClass('open-menu');
            $('.funcmenu_switcher').toggleClass('active');
            if (window.localStorage
                || typeof window.localStorage != 'undefined') {
                if (localStorage.open_menu == ''
                    || localStorage.open_menu == undefined) {
                    localStorage.open_menu = 'open-menu';
                } else {
                    localStorage.open_menu = '';
                }
            }
        },
        initEastPanel: function () {
            var self = this;
            $('.js-inOrg').click(function () {
                $('#eastbar').addClass('on');
                $('body').addClass('right-mini');
                $('.nav-pill').removeClass('active');
                $('.tab-pane').removeClass('active');
                $('[panelType="org"]').addClass('active');
                $('.pane-org').addClass('active');
                self.tDesktop.EventManager.trigger('online:comet');
            });
            $('.js-inNocbox').click(function () {
                $('#eastbar').addClass('on');
                $('body').addClass('right-mini');
                $('.nav-pill').removeClass('active');
                $('.tab-pane').removeClass('active');
                $('[panelType="msg"]').addClass('active');
                $('.pane-msg').addClass('active');
                $(".msg-tool .btn").removeClass("btn-primary");
                $('[msg-panel="nocbox"]').addClass("btn-primary");
                $(".msg-panel").removeClass("active");
                $("#nocbox").addClass("active");
                self.tDesktop.EventManager.trigger('online:stopcomet');
            });
            $('.js-inWeixun').click(function () {
                $('#eastbar').addClass('on');
                $('body').addClass('right-mini');
                $('.nav-pill').removeClass('active');
                $('.tab-pane').removeClass('active');
                $('[panelType="msg"]').addClass('active');
                $('.pane-msg').addClass('active');
                $(".msg-tool .btn").removeClass("btn-primary");
                $('[msg-panel="weixun"]').addClass("btn-primary");
                $(".msg-panel").removeClass("active");
                $("#weixun").addClass("active");
            });
            $('.nav-pill').click(function () {
                $('.nav-pill').removeClass('active');
                $('.tab-pane').removeClass('active');
                $(this).addClass('active');
                var target = $(this).attr('panelType');
                $('.pane-' + target).addClass('active');
                if (target == "org") {
                    self.tDesktop.EventManager.trigger('online:comet');
                } else {
                    self.tDesktop.EventManager.trigger('online:stopcomet');
                }
            });
            $("#msg-tool .btn").click(
                function () {
                    $(".msg-tool .btn").removeClass("btn-primary");
                    $(this).addClass("btn-primary");
                    var target = $(this).attr("msg-panel");
                    $(".msg-panel").removeClass("active");
                    $("#" + target).addClass("active");
                    if (target == "nocbox" && tDesktop.isTouchDevice()) {
                        tDesktop.EventManager.trigger('iscroller:refresh',
                            'new_noc_list_wrapper');
                    }
                });
        }
    });
    exports.Layout = Layout;
});
/* "/static/js/tDesktop/tDesktop.Menu.js" */
define(
    'tDesktop/tDesktop.Menu',
    function (require, exports, module) {
        var $ = window.jQuery;
        require('menu-aim');
        require('backbone');
        var Menu = Backbone.Model.extend({
            defaults: {
                menuId: null,
                menuText: "",
                iconfont: ""
            }
        });
        var MenuList = Backbone.Collection.extend({
            model: Menu,
            url: '',
            sync: function () {
            }
        });
        var secondMenu = Backbone.Model.extend({
            defaults: {
                parentId: null,
                menuId: null,
                menuText: "",
                expand: null,
                actionType: ""
            }
        });
        var secondMenuList = Backbone.Collection.extend({
            model: secondMenu,
            url: '',
            sync: function () {
            }
        });
        var thirdMenuList = Backbone.Collection.extend({
            model: secondMenu,
            url: '',
            sync: function () {
            }
        });
        var menulist = new MenuList;
        var secondmenulist = new secondMenuList;
        var thirdmenulist = new thirdMenuList;
        var menuItemView = Backbone.View.extend({
            el: 'ul.first-menu',
            template: $("#menuTmpl").template(),
            initialize: function () {
                _.bindAll(this, 'render');
                this.model.bind('change', this.render);
            },
            render: function () {
                var element = jQuery.tmpl(this.template, this.model
                    .toJSON());
                $(this.el).append(element);
                return this;
            }
        });
        var secondMenuView = Backbone.View.extend({
            el: 'ul.second-menu',
            template: $("#secondMenuTmpl").template(),
            initialize: function () {
                _.bindAll(this, 'render');
                this.model.bind('change', this.render);
            },
            render: function () {
                var element = jQuery.tmpl(this.template, this.model
                    .toJSON());
                var menu_id = this.model.toJSON().parentId;
                $('#second-menulist-' + menu_id).append(element);
                return this;
            }
        });
        var thirdMenuView = Backbone.View.extend({
            el: 'ul.third-menu',
            template: $("#thirdMenuTmpl").template(),
            initialize: function () {
                _.bindAll(this, 'render');
                this.model.bind('change', this.render);
            },
            render: function () {
                var element = jQuery.tmpl(this.template, this.model
                    .toJSON());
                var menu_id = this.model.toJSON().parentId;
                $('#third-menulist-' + menu_id).append(element);
                return this;
            }
        });
        var MenuView = Backbone.View
            .extend({
                el: '.west-body',
                initialize: function () {
                    var self = this;
                    _.bindAll(this, 'createMenu', 'menuHover',
                        'createSecondMenu', 'createThirdMenu',
                        'showSubmenu', 'hideSubmenu',
                        'hideAllSubmenu');
                    menulist.bind('add', self.createMenu);
                    menulist.fetch();
                    secondmenulist.bind('add', self.createSecondMenu);
                    secondmenulist.fetch();
                    thirdmenulist.bind('add', self.createThirdMenu);
                    thirdmenulist.fetch();
                    for (var i = 0; i < shortcutArray.length; i++) {
                        if (typeof (func_array['f' + shortcutArray[i]]) != "object")
                            continue;
                        var func_id = 'f' + shortcutArray[i];
                        var func_name = func_array[func_id][0];
                        var func_code = func_array[func_id][1];
                        var open_window = func_array[func_id][4] ? func_array[func_id][4]
                            : "";
                        if (func_code.substr(0, 1) == "@")
                            continue;
                        var json = {};
                        var onclick = "createTab(" + func_id.substr(1)
                            + ",'" + func_name.replace("'", "\'")
                            + "','" + func_code.replace("'", "\'")
                            + "','" + open_window + "');";
                        json["parentId"] = 'shortcut';
                        json["menuId"] = func_id;
                        json["menuText"] = func_name;
                        json["expand"] = false;
                        json["actionType"] = onclick;
                        secondmenulist.add(json);
                    }
                    for (var i = 0; i < first_array.length; i++) {
                        var json = {};
                        var menu_id = first_array[i];
                        var menu_module = first_array[i][2] || '';
                        if (typeof (func_array['m' + menu_id]) != "object") {
                            continue;
                        }
                        var image = !func_array['m' + menu_id][1] ? 'oa'
                            : func_array['m' + menu_id][1];
                        json["menuId"] = first_array[i];
                        json['module'] = menu_module;
                        json["menuText"] = func_array['m' + menu_id][0];
                        json["iconfont"] = module2iconfont[first_array[i]]
                            || module2iconfont['default'];
                        menulist.add(json);
                    }
                    var extend_menus = [[], []];
                    for (var i = 0; i < first_array.length; i++) {
                        var menu_id = first_array[i];
                        if (typeof (func_array['m' + menu_id]) != "object")
                            continue;
                        if (menu_id == "shortcut") {
                            $('#second-menu-shortcut h4')
                                .append(
                                    '<a href="javascript:;" onclick="window.createTab(\'fshortcut\', func_array[\'fshortcut\'][0], func_array[\'fshortcut\'][1], func_array[\'fshortcut\'][4]);" class="set-shortcut" id="set-shortcut"><i class="iconfont">&#xe63f;</i></a>');
                        }
                        for (var j = 0; j < second_array['m' + menu_id].length; j++) {
                            var json = {}
                            var func_id = 'f'
                                + second_array['m' + menu_id][j];
                            if (!func_array[func_id])
                                continue;
                            var func_name = func_array[func_id][0];
                            var func_code = func_array[func_id][1];
                            var open_window = func_array[func_id][4] ? func_array[func_id][4]
                                : '';
                            var bExpand = func_code.substr(0, 1) == "@"
                                && third_array[func_id];
                            func_code = func_code.replace(
                                "LOGIN_USER_ID", loginUser.user_id)
                            var onclick = bExpand ? "" : "createTab("
                            + func_id.substr(1) + ",'"
                            + func_name.replace("'", "\'")
                            + "','"
                            + func_code.replace("'", "\'")
                            + "','" + open_window + "');";
                            json["parentId"] = menu_id;
                            json["menuId"] = func_id;
                            json["menuText"] = func_name;
                            json["expand"] = bExpand;
                            json["actionType"] = onclick;
                            if (bExpand == false) {
                                secondmenulist.add(json);
                            } else {
                                extend_menus[0].push(json);
                            }
                            if (bExpand) {
                                for (var k = 0; k < third_array[func_id].length; k++) {
                                    var json = {};
                                    var third_id = 'f'
                                        + third_array[func_id][k];
                                    if (!third_id)
                                        continue;
                                    try {
                                        var third_name = func_array[third_id][0];
                                        var third_code = func_array[third_id][1];
                                        var open_window1 = func_array[third_id][4] ? func_array[third_id][4]
                                            : '';
                                        var onclick1 = "createTab("
                                            + third_id.substr(1)
                                            + ",'"
                                            + third_name.replace(
                                                "'", "\'")
                                            + "','"
                                            + third_code.replace(
                                                "'", "\'")
                                            + "','" + open_window1
                                            + "');";
                                        json["parentId"] = func_id;
                                        json["menuId"] = third_id;
                                        json["menuText"] = third_name;
                                        json["expand"] = false;
                                        json["actionType"] = onclick1;
                                        extend_menus[1].push(json);
                                    } catch (e) {
                                    }
                                }
                            }
                        }
                    }
                    $.each(extend_menus[0], function () {
                        secondmenulist.add(this);
                    })
                    $.each(extend_menus[1], function () {
                        thirdmenulist.add(this);
                    })
                },
                menuHover: function () {
                    var self = this;
                    var $menu = jQuery(".first-menu");
                    $menu.menuAim({
                        activate: self.showSubmenu,
                        deactivate: self.hideSubmenu,
                        exitMenu: self.hideAllSubmenu
                    });
                    $(".first-menu li a").click(function (e) {
                        e.stopPropagation();
                    });
                },
                createMenu: function (menuitem) {
                    var view = new menuItemView({
                        model: menuitem
                    });
                    $('.first-menu').prepend(view.render().el);
                },
                createSecondMenu: function (secondmenuitem) {
                    var secondview = new secondMenuView({
                        model: secondmenuitem
                    });
                    $('.second-menu').prepend(secondview.render().el);
                },
                createThirdMenu: function (thirdmenuitem) {
                    var thirdview = new thirdMenuView({
                        model: thirdmenuitem
                    });
                    $('.third-menu').prepend(thirdview.render().el);
                },
                // showSubmenu: function (row) {
                //     var $menu = $(".first-menu"), $row = $(row), submenuId = $row
                //         .data("submenuId"), $submenu = $("#"
                //         + submenuId), width = $menu.outerWidth();
                //     this.$active = $row;
                //     $submenu.css({
                //         display: "block",
                //         top: -22,
                //         left: width
                //     });
                //     $submenu.animate('width', '400');
                //     $row.find("div.first-menu-item").addClass(
                //         "first-menu-item-hover");
                // },

                showSubmenu: function (row) {
                        var mouseClientY = event.clientY + document.body.scrollTop;
                        var $menu = $(".first-menu"), $row = $(row), submenuId = $row.data("submenuId"), $submenu = $("#"+ submenuId), width = $menu.outerWidth();
                        this.$active = $row;
                        var length = $("#" + submenuId + " li").length;
                        var height;
                        if(length == 2){
                            height = 130 ;
                        }else{
                            height = 150 ;
                        }
                        var divh = $("#2").height();
                        var custom_height = mouseClientY - $('#north').height() - $("#funcbar").height();
                        var thisY = 0.0000;
                        if ($('.scroll-up').is(':hidden')) {
                            var num = parseInt(custom_height / divh);
                            thisY = num * divh;
                        } else {
                            custom_height -= $('.scroll-up').height();
                            var num = parseInt(custom_height / divh);
                            thisY = num * divh + $('.scroll-up').height();
                        }
                        $submenu.css({
                                display: "block",
                                top: thisY,
                                left: width,
                                height: height
                        });
                        $submenu.animate('width', '400');
                        $row.find("div.first-menu-item").addClass(
                            "first-menu-item-hover");
                },//         二级菜单样式
                // },

                hideAllSubmenu: function () {
                    $(".second-panel").css("display", "none");
                    $("div.first-menu-item").removeClass(
                        "first-menu-item-hover");
                    return true;
                },
                hideSubmenu: function (row) {
                    var $row = $(row), submenuId = $row
                        .data("submenuId"), $submenu = $("#"
                        + submenuId);
                    $submenu.css("display", "none");
                    $row.find("div.first-menu-item").removeClass(
                        "first-menu-item-hover");
                },
                hideActiveMenu: function () {
                    if (this.$active) {
                        this.hideSubmenu(this.$active);
                    }
                }
            });
        var MenuScrollView = Backbone.View.extend({
            el: $('.west'),
            events: {
                'click div.scroll-up': 'scrollUp',
                'click div.scroll-down': 'scrollDown'
            },
            initialize: function () {
                var self = this;
                _.bindAll(this, 'scrollUp', 'scrollDown');
                $(window).resize(function () {
                    self.initMenuHeight();
                });
                this.initMenuHeight();
                $('#first_menu').mousewheel(function (event, delta) {
                    var ul = $('#first_menu');
                    if (delta > 0) {
                        ul.scrollTop(ul.scrollTop() - 46);
                    } else {
                        ul.scrollTop(ul.scrollTop() + 46);
                    }
                    return false;
                });
                $(".second-panel").mousewheel(function (e) {
                    e.stopPropagation();
                });
            },
            initMenuHeight: function () {
                var availheight = $('.west-footer').offset().top
                    - $('#north').height() - $("#funcbar").height();
                var scrollheight = $("#first_menu")[0].scrollHeight;
                if (availheight < scrollheight) {
                    availheight = availheight - 40;
                    $("#first_menu").height(availheight);
                    $('.scroll-up').show();
                    $('.scroll-down').show();
                } else {
                    $("#first_menu").height(availheight);
                    $('.scroll-up').hide();
                    $('.scroll-down').hide();
                }
            },
            scrollUp: function () {
                var ul = $('#first_menu');
                ul.animate({
                    'scrollTop': (ul.scrollTop() - 144)
                }, 600);
            },
            scrollDown: function () {
                var ul = $('#first_menu');
                ul.animate({
                    'scrollTop': (ul.scrollTop() + 144)
                }, 600);
            }
        });
        exports.Menu = {
            MenuInit: MenuView,
            MenuScroll: MenuScrollView
        };
    });
/* "/static/js/tDesktop/tDesktop.Nocbox.js" */
define(
    'tDesktop/tDesktop.Nocbox',
    function (require, exports, module) {
        var $ = window.jQuery;
        var Nocbox = {
            init: function (c) {
                this.tDesktop = c.tDesktop;
                this.loadNoc();
                this.bindEvent();
            },
            bindEvent: function () {
                var self = this;
                self.tDesktop.EventManager.on('loadNoc:load', function () {
                    self.loadNoc();
                });
                self.tDesktop.EventManager.on('notify:read',
                    function (item) {
                        var $target = $('#new_noc_list li[sms_id="'
                            + item.sms_id + '"]');
                        var url = item.url;
                        var sms_id = item.sms_id;
                        var type_id = item.type_id;
                        self.removeNoc($target, sms_id, 0);
                        if (url != "") {
                            self.openURL('', item.type_name, url);
                        }
                    });
                self.tDesktop.EventManager.on('nocbox:markAll', function (ids) {
                    self.removeNoc($('.noc_item_cancel'), ids, 0);
                })
                $('#check_remind_histroy,#tohistory').click(
                    function () {
                        self.openURL('', td_lang.inc.msg_23,
                            "sms/remind_center");
                    });
                $('#ViewAllNoc').click(function () {
                    var idstr = self.get_noc_idstr();
                    $.ajax({
                        type: 'POST',
                        url: 'status_bar/sms_submit.php',
                        data: {
                            'SMS_ID': idstr
                        },
                        cache: false,
                        success: function () {
                            $('#new_noc_list').empty();
                            var datanum = self.get_noc_num();
                            $("#noc_item_num").html(datanum);
                            $('#nodata_tips').show();
                            $('.noc-nav-bar').hide();
                        }
                    });
                });
                $('#new_noc_list').delegate('.noc_item_cancel', 'click',
                    function () {
                        var type_id = $(this).attr('type_id');
                        var idstr_all = self.get_noc_idstr(type_id);
                        self.removeNoc($(this), idstr_all, 0);
                    });
            },
            loadNoc: function (flag) {
                var self = this;
                var isTOS = true;
                flag = typeof (flag) == "undefined" ? "1" : "0";
//					$.ajax({
//						type : 'GET',
//						url : 'status_bar/get_noc.php',
//						data : {
//							'FLAG' : flag,
//							'isTos' : isTOS
//						},
//						dataType : "json",
//						cache : false,
//						success : function(data) {
//							$('#nocbox_tips').hide();
//							if (data == null) {
//								$('#nodata_tips').show();
//								$('.noc-nav-bar').hide();
//								$('#new_noc_list_wrapper').empty();
//							} else {
//								$("#nodata_tips").hide();
//								$('.noc-nav-bar').show();
//								self.formatNoc(data);
//							}
//						}
//					});
            },
            formatNoc: function (data) {
                var totalnum = "", self = this;
                $('#new_noc_list').empty();
                $
                    .each(
                        data,
                        function (key, item) {
                            if (item.type_id == "") {
                                return false;
                            }
                            item.content = decodeURIComponent(item.content);
                            item.type_name = decodeURIComponent(item.type_name);
                            item.send_time = decodeURIComponent(item.send_time);
                            item.from_name = decodeURIComponent(item.from_name);
                            var tmpl = item.from_id;
                            item.from_id = item.uid;
                            item.uid = item.from_id;
                            self.tDesktop.EventManager.trigger(
                                'message:create', item);
                            if ($('#new_noc_list').find(
                                    '.noc_item_' + item.type_id)
                                    .size() != 0) {
                                $(
                                    '.noc_item_' + item.type_id
                                    + ' > ul').append(
                                    $("#nocitem-template")
                                        .tmpl(item));
                            } else {
                                $('#new_noc_list').append(
                                    $("#noc-template").tmpl(
                                        item));
                                $(
                                    '.noc_item_' + item.type_id
                                    + ' > ul').append(
                                    $("#nocitem-template")
                                        .tmpl(item));
                            }
                        });
                $('.noc').addClass("on");
                var num = self.get_noc_num();
                $("#noc_item_num").html(num);
            },
            get_noc_num: function () {
                var totalnum = '';
                totalnum = $("#new_noc_list > .noc_item > .noc_item_data > li").length;
                return totalnum;
            },
            get_noc_idstr: function (type_id) {
                var idstr = '';
                var separator = '';
                if (type_id != "" && typeof (type_id) !== "undefined") {
                    var idsobj = $("#new_noc_list > .noc_item_" + type_id
                        + " > .noc_item_data > li");
                } else {
                    var idsobj = $("#new_noc_list > .noc_item > .noc_item_data > li");
                }
                $.each(idsobj, function () {
                    idstr += separator + $(this).attr("sms_id");
                    separator = ",";
                });
                return idstr;
            },
            removeNoc: function (obj, recvIdStr, del) {
                var self = this;
                if (!recvIdStr) {
                    return
                }
                $.ajax({
                    type: 'POST',
                    url: 'status_bar/sms_submit.php',
                    data: {
                        'SMS_ID': recvIdStr
                    },
                    success: function (data) {
                        var lis = obj.parents(".noc_item").find("li")
                            .size();
                        if (recvIdStr.indexOf(",") != '-1') {
                            obj.parents(".noc_item").remove()
                        } else {
                            lis == 1 ? obj.parents(".noc_item").remove()
                                : obj.remove();
                        }
                        var num = self.get_noc_num();
                        $("#noc_item_num").html(num);
                        if (num < 1) {
                            $('#nodata_tips').show();
                            $('.noc-nav-bar').hide();
                        }
                    }
                });
            },
            openURL: function (id, name, url, open_window, width, height,
                               left, top) {
                id = !id ? ('w' + (nextTabId++)) : id;
                if (open_window != "1") {
                    window.setTimeout(function () {
                        $().addTab(id, name, url, true)
                    }, 1);
                } else {
                    width = typeof (width) == "undefined" ? 780 : width;
                    height = typeof (height) == "undefined" ? 550 : height;
                    left = typeof (left) == "undefined" ? (screen.availWidth - width) / 2
                        : left;
                    top = typeof (top) == "undefined" ? (screen.availHeight - height) / 2 - 30
                        : top;
                    window
                        .open(
                            url,
                            id,
                            "height="
                            + height
                            + ",width="
                            + width
                            + ",status=0,toolbar=no,menubar=yes,location=no,scrollbars=yes,top="
                            + top + ",left=" + left
                            + ",resizable=yes");
                }
            }
        };
        exports.Nocbox = Nocbox;
    });
/* "/static/js/tDesktop/tDesktop.Notification.js" */
define(
    'tDesktop/tDesktop.Notification',
    function (require, exports, module) {
        var $ = window.jQuery;
        require('miniNotification');
        require('backbone');
        var tDesktop = window.tDesktop;


        module.exports = {
            init: function (tDesktop) {
            }
        };
    });

/* "/static/js/tDesktop/tDesktop.Pulse.js" */
define('tDesktop/tDesktop.Pulse', function (require, exports, module) {
    var $ = window.jQuery;
    exports.pulseFormer = function () {
        $('#progressBar').removeClass('done');
        $({
            property: 7
        }).animate({
            property: 60
        }, {
            duration: 5000,
            step: function () {
                var percentage = Math.round(this.property);
                $('#progressBar').css('width', percentage + '%');
            }
        });
        setTimeout(function () {
            exports.pulseLater();
        }, 3000);
    };
    exports.pulseLater = function () {
        $({
            property: 60
        }).animate({
            property: 100
        }, {
            duration: 500,
            step: function () {
                var percentage = Math.round(this.property);
                $('#progressBar').css('width', percentage + '%');
                if (percentage == 100) {
                    $('#progressBar').addClass('done');
                }
            }
        });
    }
});
/* "/static/js/tDesktop/tDesktop.Search.js" */
define(
    'tDesktop/tDesktop.Search',
    function (require, exports, module) {
        var $ = window.jQuery;
        require('backbone');
        require('bootstrap.paginator.min.js');
        var searchModule = {
            $el: $('.search-container'),
            paginators: {},
            templates: []
        };
        searchModule.init = function () {
            this.initBind();
            this.initTemplates();
            return 'search module has been initialized.';
        };
        searchModule.initTemplates = function () {
            this.templates["user"] = $('#search-template-user').template();
            this.templates["menu"] = $('#search-template-menu').template();
            this.templates["workflow"] = $('#search-template-workflow')
                .template();
            this.templates["contacts"] = $('#search-template-contacts')
                .template();
            this.templates["calendar"] = $('#search-template-calendar')
                .template();
        };
        searchModule.setItemsNum = function (type, num) {
            var _counter = $('.search-counter-' + type);
            _counter.text("(" + num + ")");
            _counter.show();
        };
        searchModule.initPagination = function (keyword, type, curPage,
                                                totalPages) {
            $('#pagination-' + type).bootstrapPaginator({
                currentPage: curPage,
                totalPages: totalPages,
                onPageChanged: function (e, oldPage, newPage) {
                    var self = this;
                    $(this).hide();
                    setTimeout(function () {
                        $(self).show();
                    }, 1000);
                    $('.search-container').animate({
                        scrollTop: 0
                    }, 300, function () {
                        $('.search-results-' + type + '>li').remove();
                        search(keyword, type, newPage);
                    });
                }
            });
        };
        searchModule.initBind = function () {
            var self = this;
            $(".search-container")
                .delegate(
                    ".user-body-follow",
                    "click",
                    function () {
                        var $this = $(this), uid = $this.parents(
                            "li").attr("u_id"), userid = $this
                            .parents("li").attr("userid");
                        $
                            .get(
                                "/general/person_info/concern_user/concern_function.php",
                                {
                                    load: "concern",
                                    concern_content: "COMMUNITY,",
                                    group_id: 0,
                                    user_id: userid
                                },
                                function (d) {
                                    if (d == "ok") {
                                        $this
                                            .parents(
                                                "li")
                                            .addClass(
                                                "unconcern");
                                    }
                                });
                    });
            $('#search-input').one('keydown', function (e) {
                $('.search-box').css({
                    'margin-top': '15px',
                    'margin-left': '0px'
                });
            });
            if (tDesktop.isTouchDevice()) {
                $("#search-results-container").css({
                    "heigth": "100%",
                    "overflow": "hidden"
                });
                tDesktop.EventManager.trigger('iscroller:create',
                    'search-results-container');
            }
            $('#search-btn')
                .click(
                    function (e) {
                        var $value = $('#search-input').val();
                        if (!$value
                            || $value.lastIndexOf(' ') == ($value.length - 1))
                            return false;
                        var $searchType = $('#search-btn').attr(
                            'search-type');
                        $('.search-results-tabs li').attr(
                            'searched', '');
                        $('.' + $searchType + '-tab').attr(
                            'searched', 'searched');
                        $('.search-results-iscroll>ul>li').remove();
                        $('.search-counter').hide();
                        if (!$.isEmptyObject(self.paginators)) {
                            _.each(self.paginators, function (item) {
                                item.destroy();
                            });
                            self.paginators = {};
                        }
                        search($value, $searchType);
                        $('.search-results-wrapper').show();
                    });
            $('.search-results-tabs li')
                .click(
                    function () {
                        var $value = $('#search-input').val();
                        var $searchType = $(this).attr(
                            'search-type');
                        if ($(this).hasClass('active')) {
                            return false;
                        }
                        var $tabName = $(this)[0].className
                            .split('-')[0];
                        if ($tabName != 'helper') {
                            var $resultsTabclassName = '.search-results-'
                                + $tabName;
                            $('.search-results-container ul')
                                .hide();
                            $($resultsTabclassName).show();
                        } else {
                            return;
                        }
                        $('.search-results-tabs li').removeClass(
                            'active');
                        $(this).addClass('active');
                        $('.search-results-container ul')
                            .removeClass('animated fadeInLeft');
                        $('.search-results-' + $tabName).addClass(
                            'animated fadeInLeft');
                        $('#search-btn').attr('search-type',
                            $(this).attr('search-type'));
                        if ($(this).attr('searched') == ''
                            && $value != '') {
                            search($value, $searchType);
                            $(this).attr('searched', 'searched');
                        }
                        if ($('#pagination-' + $tabName).html() == " ") {
                            $('#pagination-' + $tabName + ' ul')
                                .hide();
                        } else {
                            $('#pagination-' + $tabName + ' ul')
                                .show();
                        }
                        tDesktop.EventManager.trigger(
                            'iscroller:refresh',
                            'search-results-container');
                    });
            var searchTimer;
            $('#search-input').keyup(function (e) {
                searchTimer && clearTimeout(searchTimer);
                searchTimer = setTimeout(function () {
                    $('#search-btn').trigger('click');
                }, 500);
            });
            $('.search-results-workflow').bind(
                'ajaxDataReady',
                function () {
                    $.each($('.search-results-workflow>li'), function (index, item) {
                        var url = $(item).attr('url');
                        var newUrl = url.slice(8);
                        $(item).find('a').attr({
                            'href': newUrl,
                            'target': '_blank'
                        });
                    })
                });
            this.$el.find('.search-close-btn').click(function () {
                $('body').removeClass('showSearch');
                $('#searchbar').removeClass('on');
            })
        };
        searchModule.open = function () {
        };
        searchModule.close = function () {
        };
        function search(keyword, searchType, curPage, pageLimit) {
            var self = searchModule;
            if (searchType == 'menu') {
                var count = 0, menus = [];
                for (var menuId in func_array) {
                    var func = func_array[menuId], func_id = menuId
                        .substr(1);
                    if (menuId.substr(0, 1) != 'f'
                        || func[1].substr(0, 1) == '@') {
                        continue;
                    }
                    var title = func[0] || "", abbr = func[3] || "";
                    if (title.toLowerCase().indexOf(keyword) >= 0
                        || abbr.toLowerCase().indexOf(keyword) >= 0) {
                        count++;
                        var singleMenu = {};
                        singleMenu.menuId = func_id;
                        singleMenu.menuTitle = title;
                        singleMenu.menuUrl = func[1];
                        menus.push(singleMenu);
                    }
                }
                searchRender(searchType, count, menus);
                return;
            }
            var _curPage = curPage || 1, _pageLimit = pageLimit || 10;
            $.ajax({
                type: "GET",
                url: "/general/person_info/search/get_search.func.php",
                data: {
                    curPage: _curPage,
                    pageLimit: _pageLimit,
                    type: searchType,
                    keyword: keyword
                },
                dataType: "json",
                success: function (data) {
                    if (!self.paginators[searchType]) {
                        if (data.curPage > 0 && data.totalpage > 1) {
                            searchModule.initPagination(keyword,
                                searchType, data.curPage,
                                data.totalpage);
                            self.paginators[searchType] = $(
                                '#pagination-' + searchType).data(
                                'bootstrapPaginator');
                        }
                    }
                    self.$el.animate({
                        height: '85%'
                    }, 300);
                    searchRender(searchType, data.numCount, data.datalist);
                    if (searchType == 'workflow')
                        $('.search-results-workflow').trigger(
                            'ajaxDataReady');
                },
                error: function (error) {
                    console
                        .error('some error happened:'
                            + error.statusText);
                }
            })
        }

        function searchRender(type, num, data) {
            var self = searchModule;
            self.setItemsNum(type, num);
            $.each(data, function (index, item) {
                var element = $.tmpl(self.templates[type], item);
                element.insertBefore('#pagination-' + type);
            });
            var iscrollh = $('.search-results-' + type).height();
            if ($('#pagination-' + type).html().length == 0) {
                $('.search-results-' + type).css({
                    "height": iscrollh
                });
                tDesktop.EventManager.trigger('iscroller:refresh',
                    'search-results-container');
                $('.search-results-' + type).css({
                    "height": "auto"
                });
            } else {
                var newiscrollh = iscrollh + 75;
                $('.search-results-' + type).css({
                    "height": newiscrollh
                });
                tDesktop.EventManager.trigger('iscroller:refresh',
                    'search-results-container');
                $('.search-results-' + type).css({
                    "height": "auto"
                });
            }
        }

        function funLog() {
            console
                .log(
                    "%c  ",
                    "font-size:350px; background: url(http://img3.douban.com/lpic/s10299848.jpg) no-repeat");
        }

        module.exports = searchModule;
    });
/* "/static/js/tDesktop/tDesktop.Tabs.js" */
define(
    'tDesktop/tDesktop.Tabs',
    function (require, exports, module) {
        var $ = window.jQuery;
        var pulser = require('tDesktop/tDesktop.Pulse');
        var Tabs = {
            init: function () {
                this.initTabs();
            },
            initTabs: function () {
                var self = this;
                $(window).resize(function () {
                    self.resizeLayout();
                });
                self.resizeLayout();
                $('#tabs_container').tabs({
                    tabsLeftScroll: 'tabs_left_scroll',
                    tabsRightScroll: 'tabs_right_scroll',
                    panelsContainer: 'center',
                    secondTabsContainer: 'funcbar_left'
                });
                $('#funcbar_left').delegate('.second-tab-item', 'click',
                    function () {
                        var self = $(this);
                        var url = self.attr('action');
                        var id = self.attr('secondTabId');
                        $("#tabs_" + id + "_iframe").attr('src', url);
                    });
            },
            resizeLayout: function () {
                var wWidth = (window.document.documentElement.clientWidth
                || window.document.body.clientWidth || window.innerHeight);
                var width = wWidth - $('#logo').outerWidth()
                    - $('#infobar').outerWidth();
                $('#tabs_container').width(
                    width - $('#tabs_left_scroll').outerWidth()
                    - $('#tabs_right_scroll').outerWidth() - 2);
                $('#taskbar').width(width - 2);
                $('#tabs_container').triggerHandler('_resize');
            },
            createTab: function (id, name, code, open_window) {
                var self = this;
                jQuery('#funcbar_left > div.second-tabs-container').hide();
                if (id.toString().indexOf('portal_') == 0
                    || code.indexOf('http://') == 0
                    || code.indexOf('https://') == 0
                    || code.indexOf('ftp://') == 0) {
                    self.openURL(id, name, code, open_window);
                    return;
                } else if (code.indexOf('file://') == 0) {
                    winexe(name, code.substr(7));
                    return;
                }
                var url = "";
                if (id >= 10000 && id <= 14999)
                    url = '/fis/' + code;
                else if (id >= 15000 && id <= 15499)
                    url = '/hr/' + code;
                else if (id >= 650
                    && id <= 1000
                    || code.length > 4
                    && code.substr(code.length - 4).toLowerCase() == ".jsp")
                    url = '/app/' + code;
                else
                    url = code;
                if (url.indexOf(".") < 0 && url.indexOf("?") < 0
                    && url.indexOf("#") < 0
                    && url.substring(url.length - 1) != "/")
                    url += "/";
                if (open_window == "1" || url.indexOf('/general/') != 0) {
                    self.openURL(id, name, url, open_window);
                    return;
                }
                var url2 = 'http://www.tongda2000.com' + url;
                var parse = url2
                    .match(/^(([a-z]+):\/\/)?([^\/\?#]+)\/*([^\?#]*)\??([^#]*)#?(\w*)$/i);
                var path = parse[4];
                var query = parse[5];
                var pos = path.lastIndexOf('/');
                if (pos > 0 && path.substr(pos + 1).indexOf('.') > 0
                    || query != "") {
                    self.openURL(id, name, url, open_window);
                    return;
                }
                jQuery
                    .ajax({
                        type: 'GET',
                        url: './demo_files/demo.json',
                        data: {
                            'FUNC_CODE': escape(url)
                        },
                        dataType: 'text',
                        success: function (data) {
                            var array = self.Text2Object(data);
                            if (typeof (array) != "object"
                                || typeof (array.length) != "number"
                                || array.length <= 0) {
                                self
                                    .openURL(id, name, url,
                                        open_window);
                                return;
                            }
                            var index = 0;
                            var html = '';
                            for (var i = 0; i < array.length; i++) {
                                index = (array[i].active == "1") ? i
                                    : index;
                                var className = (array[i].active == "1") ? ' class="second-tab-item active"'
                                    : 'class="second-tab-item"';
                                var href = (url.substr(url.length - 1) != "/" && array[i].href
                                    .substr(0, 1) != "/") ? (url
                                + '/' + array[i].href)
                                    : (url + array[i].href);
                                html += '<a title="'
                                    + array[i].title
                                    + '" action="'
                                    + href
                                    + '" secondTabId="'
                                    + id
                                    + '"'
                                    + className
                                    + ' hidefocus="hidefocus"><span>'
                                    + array[i].text + '</span></a>';
                            }
                            html = '<div id="second_tabs_'
                                + id
                                + '" class="second-tabs-container">'
                                + html + '</div>';
                            jQuery(html).appendTo('#funcbar_left');
                            var secondTabs = jQuery('#second_tabs_'
                                + id);
                            jQuery('a', secondTabs)
                                .click(
                                    function () {
                                        jQuery('a.active',
                                            secondTabs)
                                            .removeClass(
                                                'active');
                                        jQuery(this).addClass(
                                            'active');
                                    });
                            if (jQuery('a.active', secondTabs).length <= 0)
                                jQuery('a:first', secondTabs).addClass(
                                    'active');
                            jQuery('a:last', secondTabs).addClass(
                                'last');
                            url += url.substr(url.length - 1) != "/" ? ("/" + array[index].href)
                                : array[index].href;
                            self.addTab(id, name, url, true);
                        },
                        error: function (request, textStatus,
                                         errorThrown) {
                            self.openURL(id, name, url, open_window);
                        }
                    });
            },
            addTab: function (id, title, url, closable, selected, callback) {
                var self = this;
                if (!id)
                    return;
                closable = (typeof (closable) == 'undefined') ? true
                    : closable;
                selected = (typeof (selected) == 'undefined') ? true
                    : selected;
                var height = '100%';
                jQuery('#tabs_container')
                    .tabs(
                        'add',
                        {
                            id: id,
                            title: title,
                            closable: closable,
                            selected: selected,
                            style: 'height:' + height + ';',
                            content: '<iframe id="tabs_'
                            + id
                            + '_iframe" name="tabs_'
                            + id
                            + '_iframe" allowTransparency= "true"'
                            + (!selected ? (' _src="' + url + '"')
                                : '')
                            + ' src="'
                            + (selected ? url : '')
                            + '"'
                            + (selected ? (' onload="IframeLoaded(\''
                            + id + '\');"')
                                : '')
                            + ' border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:'
                            + height + ';"></iframe>',
                            callback: function () {
                                pulser.pulseFormer();
                                callback && callback();
                            }
                        });
            },
            selectTab: function (id) {
                $('#tabs_container').tabs('select', id);
            },
            closeTab: function (id) {
                $('#tabs_container').tabs('close', id);
            },
            getSelected: function () {
                return $('#tabs_container').tabs('selected');
            },
            isTouchDevice: function () {
                try {
                    document.createEvent("TouchEvent");
                    return userAgent.indexOf("mobile") >= 0
                        || userAgent.indexOf("maxthon") < 0;
                } catch (e) {
                    return false;
                }
            },
            openURL: function (id, name, url, open_window, width, height,
                               left, top) {
                var self = this;
                id = !id ? ('w' + (nextTabId++)) : id;
                if (open_window != "1") {
                    window.setTimeout(function () {
                        self.addTab(id, name, url, true)
                    }, 1);
                } else {
                    width = typeof (width) == "undefined" ? 780 : width;
                    height = typeof (height) == "undefined" ? 550 : height;
                    left = typeof (left) == "undefined" ? (screen.availWidth - width) / 2
                        : left;
                    top = typeof (top) == "undefined" ? (screen.availHeight - height) / 2 - 30
                        : top;
                    window
                        .open(
                            url,
                            id,
                            "height="
                            + height
                            + ",width="
                            + width
                            + ",status=0,toolbar=no,menubar=yes,location=no,scrollbars=yes,top="
                            + top + ",left=" + left
                            + ",resizable=yes");
                }
            },
            Text2Object: function (data) {
                var self = this;
                try {
                    var func = new Function("return " + data);
                    return func();
                } catch (ex) {
                    return '<b>' + ex.description + '</b><br /><br />'
                        + self.HTML2Text(data) + '';
                }
            },
            HTML2Text: function (html) {
                var div = document.createElement('div');
                div.innerHTML = html;
                return div.innerText;
            },
            IframeLoaded: function (id) {
                var iframe = window.frames['tabs_' + id + '_iframe'];
                if (iframe && $('#tabs_link_' + id)
                    && $('#tabs_link_' + id).innerText == '') {
                    $('#tabs_link_' + id).innerText = !iframe.document.title ? td_lang.inc.msg_27
                        : iframe.document.title;
                }
                pulser.pulseLater();
            }
        };
        exports.Tabs = Tabs;
    });
/* "/static/js/tDesktop/tDesktop.Theme.js" */
define(
    'tDesktop/tDesktop.Theme',
    function (require, exports, module) {
        var $ = window.jQuery;
        var Theme = {
            init: function () {
                this.bindEvent();
            },
            bindEvent: function () {
                var self = this;
                $("#theme")
                    .click(
                        function () {
                            if (theme_select_priv == 0) {
                                alert(theme_select_tip);
                                return;
                            }
                            $('#theme').toggleClass('on');
                            if ($("#theme_panel:visible").length) {
                                $("#theme_panel").slideUp();
                                $('#overlay_theme').hide();
                                return;
                            }
                            if ($('#theme_slider').text() == "") {
                                for (var id in themeArray) {
                                    if (themeArray[id].src == "")
                                        return;
                                    var aobj = $('<a class="theme_thumb" hidefocus="hidefocus"><img src="'
                                        + themeArray[id].src
                                        + '" /><span>'
                                        + themeArray[id].title
                                        + '</span></a>');
                                    aobj.attr("index", id);
                                    $('#theme_slider').append(aobj);
                                }
                                $('#theme_slider a.theme_thumb')
                                    .each(
                                        function () {
                                            var index = $(
                                                this)
                                                .attr(
                                                    "index");
                                            if (ostheme == index) {
                                                $(this)
                                                    .find(
                                                        "span")
                                                    .addClass(
                                                        "focus");
                                            }
                                        });
                                $("#theme_slider")
                                    .delegate(
                                        'a.theme_thumb',
                                        'click',
                                        function () {
                                            var index = $(
                                                this)
                                                .attr(
                                                    "index");
                                            if (ostheme == index) {
                                                return;
                                            }
                                            self
                                                .setTheme(index);
                                            $(
                                                '#theme_slider a.theme_thumb span')
                                                .removeClass(
                                                    "focus");
                                            $(this)
                                                .find(
                                                    "span")
                                                .addClass(
                                                    "focus");
                                        });
                            }
                            $('.over-mask-layer').hide();
                            $('#overlay_theme').show();
                            $('#theme_panel').slideDown();
                        });
                $('#overlay_theme').click(function () {
                    $('#theme').trigger('click');
                });
            },
            setTheme: function (themeid) {
                var flag = false;
                $.ajax({
                    async: false,
                    data: {
                        "themeid": themeid
                    },
                    url: '/general/person_info/theme/switch.php',
                    success: function (r) {
                        if (r == "+ok") {
                            flag = true;
                            window.location.reload();
                        }
                    }
                });
                return flag;
            }
        };
        exports.Theme = Theme;
    });
/* "/static/js/tDesktop/tDesktop.Today.js" */
define(
    'tDesktop/tDesktop.Today',
    function (require, exports, module) {
        var $ = window.jQuery;
        require('backbone');
        var Deskdate = Backbone.View.extend({
            el: $('.dateArea'),
            events: {
                'click div#date': 'openCal',
                'click div#mdate': 'openCal',
                'click div#time_area': 'openTime'
            },
            initialize: function () {
                _.bindAll(this, 'openCal', 'openTime');
                var solarTerm = sTerm(OA_TIME.getFullYear(), OA_TIME
                    .getMonth(), OA_TIME.getDate());
                if (solarTerm != "")
                    $('#mdate').text(solarTerm);
            },
            openCal: function () {
                // $().addTab('dt_date', td_lang.inc.msg_25,
                //     "/module/calendar2/", true);
            },
            openTime: function () {
                // $().addTab('dt_time_area', td_lang.inc.msg_26,
                //     "/module/world_time/", true);
            }
        });
        var Calendar = Backbone.View.extend({
            initialize: function () {
                this.render();
            },
            render: function () {
                var now = new Date();
//					$.ajax({
//						url : '/general/calendar/arrange/get_cal_list.php',
//						data : {
//							starttime : now.getTime() / 1000,
//							endtime : now.getTime() / 1000,
//							view : "agendaDay"
//						},
//						async : true,
//						type : 'get',
//						success : function(d) {
//							if (d.length > 0) {
//								$.each(d, function(k, v) {
//									$("#calendar-template").tmpl(v).appendTo(
//											'#cal_list');
//								});
//							} else {
//								$("#caltip").show();
//							}
//						}
//					});
            }
        });
        var Reminder = Backbone.View.extend({
            initialize: function () {
                this.render();
            },
            render: function () {
//					$.ajax({
//						url : '/general/task_center/getReminder.php',
//						data : "",
//						async : true,
//						type : 'get',
//						success : function(d) {
//							if (d.length > 0) {
//								$.each(d, function(k, v) {
//									$("#reminder-template").tmpl(v).appendTo(
//											'#remind_list');
//								});
//							} else {
//								$("#remindtip").show();
//							}
//						}
//					});
            }
        });
        exports.Today = {
            Deskdate: Deskdate,
            Calendar: Calendar,
            Reminder: Reminder
        };
    });
