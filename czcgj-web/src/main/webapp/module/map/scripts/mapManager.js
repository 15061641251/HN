var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
/*缩放控件type有四种类型:
BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/

var map;

var styleOptions = {
    strokeColor: "red",    //边线颜色。
    strokeWeight: 3,       //边线的宽度，以像素为单位。
    strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
    fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
    strokeStyle: 'solid' //边线的样式，solid或dashed。
}

$(function () {
    map = new BMap.Map('map');
    map.centerAndZoom(new BMap.Point(119.968328, 31.779459), 13);

    map.addControl(top_left_control);
    map.addControl(top_left_navigation);

    map.enableScrollWheelZoom(true);

    //实例化鼠标绘制工具
    var drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: false, //是否开启绘制模式
        enableDrawingTool: true, //是否显示工具栏
        drawingToolOptions: {
            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
            offset: new BMap.Size(5, 5), //偏离值
            drawingModes: [BMAP_DRAWING_MARKER, BMAP_DRAWING_CIRCLE, BMAP_DRAWING_POLYGON, BMAP_DRAWING_RECTANGLE]
        },
        circleOptions: styleOptions, //圆的样式
        polylineOptions: styleOptions, //线的样式
        polygonOptions: styleOptions, //多边形的样式
        rectangleOptions: styleOptions //矩形的样式
    });

    //添加鼠标绘制工具监听事件，用于获取绘制结果
    drawingManager.addEventListener('overlaycomplete', overlaycomplete);

    map_search();
})

var overlays = [];
var overlaycomplete = function (e) {
    overlays.push(e.overlay);
};

var map_search = function () {

    var device_name = $('#device_name').val();
    var device_id = $("#device_id").val();
    var company_name = $("#company_name").val();
    var area_name = $("#area_name").val();

    layer.load(2);

    $.get(contextpath + '/device/getDeviceListByPage', {
        "pageIndex": "1",
        "pageSize": "99999",
        "device_name": device_name,
        "area_name": area_name,
        "company_name": company_name,
        "device_id": device_id
    }, function (data) {
        var returnCode = data.returnCode;
        layer.closeAll('loading');
        if (returnCode == 1) {

            remove_overlay();
            var points = data.result.list;

            for (var i = 0; i < points.length; i++) {
                var point = points[i];

                var lng = point.lng;
                var lat = point.lat;

                if(lng == undefined || lat == undefined){
                    continue;
                }
                var marker_point = new BMap.Point(lat, lng);
                addMarker(marker_point, point.last_warn_status, point.device_name + "&nbsp;&nbsp;<a href='javascript:showDevice(" + point.int_id + ")'>查看详情</a>");
            }
        } else {
            layer.msg(data.description, {icon: 5});
        }
    });
}

//添加覆盖物
var addMarker = function (point, is_warn, content) {

    if (is_warn == 0) {

        var vectorMarker = new BMap.Marker(new BMap.Point(point.lng, point.lat - 0.03), {
            // 指定Marker的icon属性为Symbol
            icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
                scale: 1,//图标缩放大小
                fillColor: "orange",//填充颜色
                fillOpacity: 0.8//填充透明度
            })
        });
        map.addOverlay(vectorMarker);
        addClickHandler(content, vectorMarker);
    } else {

        var vectorMarker = new BMap.Marker(new BMap.Point(point.lng, point.lat - 0.03), {
            // 指定Marker的icon属性为Symbol
            icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
                scale: 1,//图标缩放大小
                fillColor: "red",//填充颜色
                fillOpacity: 0.8//填充透明度
            })
        });
        vectorMarker.setAnimation(BMAP_ANIMATION_BOUNCE);
        map.addOverlay(vectorMarker);
        addClickHandler(content, vectorMarker);
    }

}

//清除覆盖物
function remove_overlay() {
    map.clearOverlays();
}

function addClickHandler(content, marker) {
    marker.addEventListener("click", function (e) {
            openInfo(content, e)
        }
    );
}

var showDevice = function (deviceId) {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: ['设备详情', 'text-align:center;font-size:16px;font-weight:bold;font-family:微软雅黑;'],
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/device/deviceDetail?deviceId=' + deviceId]
        });
    layer.full(index);
}

var opts = {
    width: 50,     // 信息窗口宽度
    height: 50,     // 信息窗口高度
    title: "<span style='font-size:16px'>设备名称</span>" // 信息窗口标题
};


function openInfo(content, e) {
    var p = e.target;
    var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
    var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
    map.openInfoWindow(infoWindow, point); //开启信息窗口
}

var clear_overlay = function () {
    for (var i = 0; i < overlays.length; i++) {
        map.removeOverlay(overlays[i]);
    }
}