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

function refreshTree(){
    console.log("refresh");
}

function addNode(name) {

    var nodeID = $("#jstree").jstree(true).create_node($("#root")[0], name);
    $('#jstree').jstree(true).set_id(nodeID, name + "_node");
 
}