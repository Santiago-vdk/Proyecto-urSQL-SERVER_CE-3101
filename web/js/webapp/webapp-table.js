var logTabla;
var dataTabla;
var counter = 0;
$(document).ready(function () {
    /*$('#example').DataTable();
     $('#example')
     .removeClass('display')
     .addClass('table table-striped table-bordered');   */

    $('#data-table').DataTable({
        'bSort': false,
        "scrollY": "100%",
        "scrollCollapse": false,
        "info": true,
        "paging": true,
    
        "bAutoWidth": false

    });

    logTabla = $('#log-tabla').DataTable({
         "order": [[ 3, "desc" ]],
        aoColumns: [
            {mData: 'Check'},
            {mData: '#'},
            {mData: 'Time'},
            {mData: 'Action'},
            {mData: 'Message'},
            {mData: 'Duration'}
        ],
        "columnDefs": [
            {"width": "5%", "targets": 0}
        ],
        "stateSave": true,
        "scrollCollapse": false,
        "info": false,
        "paging": false,
        "ordering": true,
        "sDom": '<"top"i>rt<"hidden"flp><"clear">',
        "bAutoWidth": false
    });

});

function addRow(error, time, action, status, duration) {
    var pError;
    
    if(error === "true"){
        pError = '<i class="fa fa-exclamation-circle"></i>';
    } else {
        pError = '<i class="fa fa-check"></i>';
    }
    
    logTabla.row.add({
        "Check": pError,
        "#": counter,
        "Time": time,
        "Action": action,
        "Message": status,
        "Duration": duration

    })
    .draw(false);
    counter++;

}

function clearTable(){
    logTabla.clear();
    logTabla.draw();
}