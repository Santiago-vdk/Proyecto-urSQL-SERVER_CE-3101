$(document).ready(function () {

    $('#execute-query').click(function (e) {
        console.log("yep");
        $('#execute-query').removeClass("btn btn-sq-lg btn-success").addClass("btn btn-sq-lg btn-warning");
        $('#execute-query-icon').removeClass("fa fa-check").addClass("fa fa-cog fa-spin");
        sendQuery();

    });
});

function sendQuery() {
    var code = getCode();
    $.ajax({
        url: 'webresources/webapp/ExecuteQuery',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({"source-code": code}),
        processData: false,
        success: function (data, textStatus, jQxhr) {
            alert("rekt");
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
};