var logTabla;
var dataTabla =$('#data-table').DataTable({"bDestroy": true});
var counter = 0;
   var aryJSONColTable = [];
       var arregloColumnas = [];
$(document).ready(function () {
    
    
    
    /*$('#example').DataTable();
     $('#example')
     .removeClass('display')
     .addClass('table table-striped table-bordered');   */
/*
   dataTabla = $('#data-table').DataTable({
        'bSort': false,
        "scrollY": "100%",
        "scrollCollapse": false,
        "info": true,
        "paging": true,
    
        "bAutoWidth": false

    });*/

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

function updateDataTable(data){

    if(data.table === false){

    }
    else {
        
    console.log(data.Valores);
    
    
    //dataTabla.destroy();
    arregloColumnas = [];
    aryJSONColTable = [];
    
    for(var i = 0; i < data.Columnas.length; i++){
        arregloColumnas[i] = data.Columnas[i];
    }


    for (var i = 0; i < arregloColumnas.length; i++) {
        aryJSONColTable.push({
            "sTitle": arregloColumnas[i],
            "aTargets": [i]
        });
    }
    ;
    
    dataTabla.destroy();
    $('#data-table').empty();
        
        
    dataTabla = $('#data-table').DataTable({
        "aoColumnDefs": aryJSONColTable,
        "aaData":data.Valores,
        "bProcessing": false,
        "bLengthChange": true,
        "bFilter": true,
        "aaSorting": [[1, "desc"]],
        "sScrollX": "100%",
        "bScrollCollapse": true,
        "bDestroy": true
        
    });
  
  }
  }
    

    /*
    dataTabla.row.add({//Quitar
        "col1": "1",
        "col2": "2",
        "col3": "3"
    }).draw(false);*/
    
    


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
    logTabla.state.save();

}

function clearTable(){
    logTabla.clear();
    logTabla.draw();
}