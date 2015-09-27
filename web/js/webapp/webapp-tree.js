$(document).ready(function () {

    // 6 create an instance when the DOM is ready
    $('#jstree').jstree(
            {
                'core': {
                    'check_callback': true
                }
            });

    // 7 bind to events triggered on the tree
    $('#jstree').on("changed.jstree", function (e, data) {
        console.log(data.selected);
    });



});

function refreshTree() {
    $('#jstree').jstree().delete_node();
    $.ajax({
        url: 'webresources/webapp/data/refreshSchemaTree',
        type: 'get',
        contentType: 'application/json',
        dataType: "json",
        success: function (data, textStatus, jQxhr) {
            console.log("Tree refreshed!");
           // alert(data[0][1].tables[0]); //Nombre de tabla
            //alert(data[0][1].tables.length); //tama;o abla interna
            
             for(var i = 0; i < data.length; i ++){
               addDBNode(data[i][0].name);
                for(var j =0; j < data[0][1].tables.length; j++){
                    addTableNode(data[i][1].tables[j], data[i][0].name);
                    
                }
            }
        },
        error: function (jqXhr, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

function addTableNode(name, parent){    
    
    $("#jstree").jstree(true).delete_node(name + "_node");
    
    var nodeID = $("#jstree").jstree(true).create_node(parent + "_node", name);
    $('#jstree').jstree(true).set_id(nodeID, name + "_node");
    
    
}

function addDBNode(name) {
    
     $("#jstree").jstree(true).delete_node(name + "_node");

    var nodeID = $("#jstree").jstree(true).create_node($("#root")[0], name);
    $('#jstree').jstree(true).set_id(nodeID, name + "_node");
    


}