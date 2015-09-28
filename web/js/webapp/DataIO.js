$(document).ready(function () {
    getServerState();

    $('#execute-query').click(function (e) {
        if ($('#execute-query').hasClass('disabled')) {
            e.preventDefault();
        } else {
            $('#execute-query').removeClass("btn btn-sq-lg btn-success").addClass("btn btn-sq-lg btn-warning");
            $('#execute-query-icon').removeClass("fa fa-check").addClass("fa fa-cog fa-spin");
            var code = getCode();
            sendQuery(code);
        }


    });
});

function sendQuery(code) {
    /* $('.progress-bar').text("0%");
     $('.progress-bar').css({
     width: "0%"});
     $('.progress').fadeIn('fast');*/
    $.ajax({
        /* xhr: function ()
         {
         var xhr = new window.XMLHttpRequest();
         xhr.addEventListener("progress", function (evt) {
         if (evt.lengthComputable) {
         var percentComplete = evt.loaded / evt.total;
         //Do something with download progress
         $('.progress-bar').css({
         width: percentComplete * 100 + '%'
         });
         $('.progress-bar').text(percentComplete * 100 + '%');
         }
         }, false);
         return xhr;
         },*/
        url: 'webresources/webapp/ExecuteQuery',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({"source-code": code}),
        success: function (data, textStatus, jQxhr) {
            //updateTable(json_example);
            //Actualizo tabla de informacion

            updateDataTable(data);
        },
        complete: function () {
            /*  $('.progress').delay(1000).fadeOut('slow');
             $('#execute-query').removeClass("btn btn-sq-lg btn-warning").addClass("btn btn-sq-lg btn-success");
             $('#execute-query-icon').removeClass("fa fa-cog fa-spin").addClass("fa fa-check");*/
            updateLog();
            getServerState();
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}
;

function serverVerify() {
    $.ajax({
        url: 'webresources/webapp/initialSetup',
        type: 'get',
        contentType: 'application/json',
        //async:false, FRAGIL
        success: function (data, textStatus, jQxhr) {
            console.log("urSQL Context Verified!");
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });

}

function updateLog() {
    $.ajax({
        url: 'webresources/webapp/data/log',
        type: 'get',
        contentType: 'application/json',
        success: function (data, textStatus, jQxhr) {
            addRow(data.error, data.time, data.action, data.status, data.duration);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

/*
 function updateTable(table) {
 var oldTable = document.getElementById('data-table'),
 newTable = oldTable.cloneNode();
 for (var i = 0; i < table.length; i++) {
 var tr = document.createElement('tr');
 for (var j = 0; j < table[i].length; j++) {
 var td = document.createElement('td');
 td.appendChild(document.createTextNode(table[i][j]));
 tr.appendChild(td);
 }
 newTable.appendChild(tr);
 }
 oldTable.parentNode.replaceChild(newTable, oldTable);
 }
 */

function getDBStatus() {
    $.ajax({
        url: 'webresources/webapp/data/status',
        type: 'get',
        contentType: 'application/json',
        success: function (data, textStatus, jQxhr) {
            showStatus(data);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

function getDataBases() {
    $.ajax({
        url: 'webresources/webapp/data/dataBases',
        type: 'get',
        contentType: 'application/json',
        success: function (data, textStatus, jQxhr) {
            console.log("executionPlan Found!");
            showDataBases(data.schemas);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

function getServerState() {
    $.ajax({
        url: 'webresources/webapp/server_state',
        type: 'get',
        contentType: 'application/json',
        success: function (data, textStatus, jQxhr) {
            changeServerState(data);
        },
        error: function (jqXhr, textStatus, errorThrown) {

        }
    });
}

function getExecPlan() {
    $.ajax({
        url: 'webresources/webapp/data/executionPlan',
        type: 'get',
        contentType: 'application/json',
        success: function (data, textStatus, jQxhr) {
            console.log("executionPlan Found!");
            showExecPlan(data.execplan);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

