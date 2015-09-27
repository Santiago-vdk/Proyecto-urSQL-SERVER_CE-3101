$(document).ready(function () {

    //Window behavior
    //Main Window
    $('.main-content').click(function () {
        if ($('.query-content').height() !== 180) {
            $('.query-content').animate({height: 180}, 600);
        }
        if ($('.data-content').height() !== 180) {
            $('.data-content').animate({height: 200}, 600);
        }
        if ($('.log-content').height() !== 180) {
            $('.log-content').animate({height: 180}, 600);
        }
    });


    //Sidebar
    $('.side-bar').click(function (e) {
        //var toggleHeight = $(this).height === 260 ? "180px" : "260px";
        $('.side-bar').animate({width: 250}, 600);
        if ($('.main').margin !== "0px") {
            $('.main').animate({marginLeft: '0px'}, 600);
        }
    });

    //Splitbar
    var min = 211;
    var max = 900;
    var mainmin = 180;
    $('#split-bar').mousedown(function (e) {
        e.stopPropagation();
        e.preventDefault();
        $(document).mousemove(function (e) {
            e.stopPropagation();
            e.preventDefault();
            var x = e.pageX - $('#sidebar').offset().left;
            if (x > min && x < max && e.pageX < ($(window).width() - mainmin)) {
                $('#sidebar').css("width", x);
                $('.main').css("margin-left", x);
            }
        });
    });
    $(document).mouseup(function (e) {
        e.stopPropagation();
        $(document).unbind('mousemove');
    });

    //Content Window Behavior
    $('.query-content').click(function (e) {
        e.stopPropagation();
    });
    $('.data-content').click(function (e) {
        e.stopPropagation();
    });
    $('.logcontent').click(function (e) {
        e.stopPropagation();
    });

    //Button Behavior
    $('#execute-query').popover({container: 'body'});
    $('#executing-query').popover({container: 'body'});
    $('#plan-log').popover({container: 'body'});

    //Button Actions
    //Expand Query Box
    $('#expand-query').click(function (e) {
        e.stopPropagation();
        var toggleHeight;
        if ($('.query-content').height() === 180) {
            toggleHeight = "100%";
            $('#button-down-query').attr('class', 'fa fa-caret-up');
        } else {
            toggleHeight = "180px";
            $('#button-down-query').attr('class', 'fa fa-caret-down');
        }
        $('.query-content').animate({height: toggleHeight}, 600);
    });

    //Expand Data Box
    $('#expand-data').click(function (e) {
        e.stopPropagation();
        var toggleHeight;
        if ($('.data-content').height() === 200) {
            toggleHeight = "100%";
            $('#button-down-data').attr('class', 'fa fa-caret-up');
        } else {
            toggleHeight = "200px";
            $('#button-down-data').attr('class', 'fa fa-caret-down');
        }
        $('.data-content').animate({height: toggleHeight}, 600);
        $('.main-content').animate({scrollTop: $("#data").offset().top}, 600);
    });

    //Expand Log Box
    $('#expand-log').click(function (e) {
        e.stopPropagation();
        var toggleHeight;
        if ($('.log-content').height() === 180) {
            toggleHeight = "100%";
            $('#button-down-log').attr('class', 'fa fa-caret-up');
        } else {
            toggleHeight = "180px";
            $('#button-down-log').attr('class', 'fa fa-caret-down');
        }
        $('.log-content').animate({height: toggleHeight});
        if ($('.data-content').height() !== 180) {
            $('.data-content').animate({height: 180}, 600);
        }
        $('.main-content').animate({scrollTop: $("#log").offset().top}, 600);
    });

    //Expand Log Box
    $('#errase-log').click(function (e) {
        e.stopPropagation();
        clearTable();
    });


    //Sidebar button

    $('#button-start').click(function (e) {
        e.stopPropagation();
        swal({title: "Starting urSQL, Stand by...",
            text: '<i class="fa fa-refresh fa-spin fa-5x"></i>',
            html: true,
            timer: 2000,
            showConfirmButton: false
        });
        $('#button-start').prop("disabled", true);
        serverVerify();
        sendQuery("START");
    });

    $('#button-stop').click(function (e) {
        e.stopPropagation();

        swal({title: "Are you sure?",
            text: "You are stopping urSQL Systems!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, stop it!",
            cancelButtonText: "No, keep rocking!",
            closeOnConfirm: false,
            closeOnCancel: false
        }, function (isConfirm) {
            if (isConfirm) {
                swal({title: "Stopping urSQL, Stand by...",
                    text: '<i class="fa fa-refresh fa-spin fa-5x"></i>',
                    html: true,
                    timer: 2000,
                    showConfirmButton: false
                });
                sendQuery("START");
        } else {
                swal("Cancelled", "you are safe :)", "error");
            }
        });
    });


    $('#button-createdb').click(function (e) {
        e.stopPropagation();
        swal({title: "Create Database",
            text: "Database Name:",
            type: "input",
            showCancelButton: true,
            closeOnConfirm: false,
            animation: "slide-from-top",
            inputPlaceholder: ""},
        function (inputValue) {
            if (inputValue === false)
                return false;
            if (inputValue === "") {
                swal.showInputError("Please specify a DB name!");
                return false
            }
            swal("Nice!", "New database: " + "'" + inputValue + "'" + " created!", "success");
            sendQuery("CREATEDATABASE " + inputValue);
        });
    });

    $('#button-dropdb').click(function (e) {
        e.stopPropagation();
        swal({title: "Drop Database",
            text: "Database Name:",
            type: "input",
            showCancelButton: true,
            closeOnConfirm: false,
            animation: "slide-from-top",
            inputPlaceholder: ""},
        function (inputValue) {
            if (inputValue === false)
                return false;
            if (inputValue === "") {
                swal.showInputError("Please specify a DB name!");
                return false;
            }
            swal("Done!", "Database: " + "'" + inputValue + "'" + " dropped!", "success");
            sendQuery("DROPDATABASE " + inputValue);
        });
    });
    
    $('#button-statusdb').click(function (e) {
        e.stopPropagation();
        var statusJSON = getDBStatus();
    });

    $('#button-listdb').click(function (e) {
        e.stopPropagation();
        var databases = getDataBases();
    });
    
    
    $('#button-displaydb').click(function (e) {
        e.stopPropagation();
        bootbox.alert('<html><h1>Display DB</h1>');
    });
    
    $('#button-refresh').click(function (e) {
        e.stopPropagation();
        refreshTree();
        
    });

    $('#plan-log').click(function (e) {
        e.stopPropagation();
        //ExecuteGetExecutionPlan
        var exec_plan = getExecPlan();
    });
});


function showStatus(RuntimeDBProcessor,SystemCatalog,StoredDataManager,StoredData) {
    var message =  '<html><h2>System Components:</h2><ul>' ;
    
    message = message + '<li>RunTime DB Processor, '+ RuntimeDBProcessor + '</li>';
    message = message + '<li>System Catalog, '+ SystemCatalog + '</li>';
    message = message + '<li>Stored Data Manager, '+ StoredDataManager + '</li>';
    message = message + '<li>Stored Data, '+ StoredData + '</li>';
    
    message = message + '</ul></html>';
    bootbox.alert(message);
}


function showDataBases(databases){
    //bootbox.alert('<html><h1>' + databases + '</h1>');
    var message =  '<html><h2>Database List:</h2><ul>' ;
    for(var i = 0; i < databases.length; i++){
       message =  message  + '<li>' + databases[i] + '</li>';
    }
    message = message + '</ul></html>';
    bootbox.alert(message);
}



function showExecPlan(exec_plan){
    bootbox.alert('<html><h1>' + exec_plan + '</h1>');
}
