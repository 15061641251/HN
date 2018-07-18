$(function () {

    var index = layer.load(2);

    $.get(contextpath + '/company/getCompanyDetail?companyId=' + getQueryString("companyId"), function (response) {

        layer.close(index);

        if (response.returnCode == 1) {

            var company_name = response.result.company_name;
            var address = response.result.address;
            var create_time = response.result.create_time;
            var person_name = response.result.person_name;
            var person_phone = response.result.person_phone;
            var remark = response.result.remark;

            $("#company_name").html(company_name);
            $("#address").html(address);
            $("#create_time").html(create_time);
            $("#person_name").html(person_name);
            $("#person_phone").html(person_phone);
            $("#remark").html(remark);


        } else {
            layer.msg(response.description, {icon: 5});
        }
    });
})