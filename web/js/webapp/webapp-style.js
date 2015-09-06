$(document).ready(function () {

    //Window behavior
        //Main Window
        $('.main-content').click(function () {
            if ($('.query-content').height() !== 180) {
                $('.query-content').animate({height: 180}, 600);
            }
            if ($('.data-content').height() !== 180) {
                $('.data-content').animate({height: 180}, 600);
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
    $('#run-query').popover({container: 'body'});
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
            if ($('.data-content').height() === 180) {
                toggleHeight = "100%";
                $('#button-down-data').attr('class', 'fa fa-caret-up');
            } else {
                toggleHeight = "180px";
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
});