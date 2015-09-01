<%-- 
    Document   : index
    Created on : Aug 29, 2015, 10:51:26 PM
    Author     : Shagy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <!-- style -->
        <link href="css/style.css" rel="stylesheet"/>
        <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>

        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

        <!-- Buttons -->
        <link rel="stylesheet" href="css/buttons.css">
        <link href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.css" rel="stylesheet">


        <!-- CodeMirror -->
        <script src="js/codemirror.js"></script>
        <link rel="stylesheet" href="css/codemirror.css">
        <link rel="stylesheet" href="css/base16-light.css">
        <link rel="stylesheet" href="css/show-hint.css" />
        <script src="js/sql.js"></script>
        <script src="js/show-hint.js"></script>
        <script src="js/sql-hint.js"></script>
        <script src="js/panel.js"></script>

        <!-- DataTables -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.8/datatables.min.css"/>
        <script type="text/javascript" src="https://cdn.datatables.net/r/bs-3.3.5/jqc-1.11.3,dt-1.10.8/datatables.min.js"></script>
        <script type="text/javascript" charset="utf-8">
            $(document).ready(function () {
                $('#example').DataTable();
            });

        </script>

        <!-- jsTree -->
        <link rel="stylesheet" href="css/style.min.css" />


    </head>
    <body>
        <div class="wrapper">
            <div class="side-bar" id="sidebar">
                <div class="side-box">


                    <a href="#" class="button button-action" style="width:100%">START <i class="fa fa-play"></i></a>
                    <a href="#" class="button button-caution" style="width:100%">STOP <i class="fa fa-stop"></i></i></a>


                    <a href="#" class="button button-primary" style="width:100%">CREATE DB <i class="fa fa-database"></i></a>
                    <a href="#" class="button button-highlight" style="width:100%">DROP DB <i class="fa fa-trash-o"></i></a>

                    <a href="#" class="button button-normal" style="width:100%">STATUS <i class="fa fa-bug"></i></i></a>
                    <a href="#" class="button button-normal" style="width:100%">LIST DB <i class="fa fa-list"></i></a>

                    <a href="#" class="button button-normal" style="width:100%">DISPLAY DB <i class="fa fa-desktop"></i></a>




                </div>
                <div class="vertical-separator-sidebar"></div>
                <div class="side-box" id="tree-box">

                    <div id="jstree">
                        <!-- in this example the tree is populated from inline HTML -->
                        <ul>
                            <li>Root node 1
                                <ul>
                                    <li id="child_node_1">Child node 1</li>
                                    <li>Child node 2</li>
                                </ul>
                            </li>
                            <li>Root node 2</li>
                        </ul>
                    </div>
                    <button>demo button</button>


                    <!-- 4 include the jQuery library -->
                    
                    <!-- 5 include the minified jstree source -->
                    <script src="js/jstree.min.js"></script>
                    <script>
                              $(function () {
                                  // 6 create an instance when the DOM is ready
                                  $('#jstree').jstree();
                                  // 7 bind to events triggered on the tree
                                  $('#jstree').on("changed.jstree", function (e, data) {
                                      console.log(data.selected);
                                  });
                                  // 8 interact with the tree - either way is OK
                                  $('button').on('click', function () {
                                      $('#jstree').jstree(true).select_node('child_node_1');
                                      $('#jstree').jstree('select_node', 'child_node_1');
                                      $.jstree.reference('#jstree').select_node('child_node_1');
                                  });
                              });
                    </script>



                </div>
            </div>
            <div id="split-bar"></div>
            <div class="main-content" id="main">

                <div class="query-content" id="query">
                    <div class="query-content-toolbar">
                        <button class="button button-square button-normal button-border button-square" id="expand-query"><i id="button-down-query" class="fa fa-caret-down"></i></i></button>
                    </div>
                    <script>
                        var mime = 'text/x-sql';
                        var editor = CodeMirror(query, {
                            mode: mime,
                            theme: "base16-light",
                            styleActiveLine: true,
                            indentWithTabs: true,
                            smartIndent: true,
                            lineNumbers: true,
                            matchBrackets: true,
                            autofocus: true,
                            extraKeys: {"Ctrl-Space": "autocomplete"}
                        });
                        /*   
                         var textarea = document.getElementById("code1");
                         
                         var numPanels = 0;
                         var panels = {};
                         
                         
                         function makePanel(where) {
                         var node = document.createElement("div");
                         var id = ++numPanels;
                         var widget, close, label;
                         
                         node.id = "pane-" + id;
                         node.className = "pane " + top;
                         close = node.appendChild(document.createElement("a"));
                         close.setAttribute("title", "Remove me!");
                         close.setAttribute("class", "remove-panel");
                         close.textContent = "✖";
                         CodeMirror.on(close, "click", function () {
                         panels[node.id].clear();
                         });
                         label = node.appendChild(document.createElement("span"));
                         label.textContent = "I'm pane n°" + id;
                         return node;
                         }
                         
                         
                         var node = makePanel("top");
                         panels[node.id] = editor.addPanel(node, {position: "top"});
                         
                         */
                    </script>


                </div>
                <div class="vertical-separator-content"></div>
                <div class="data-content" id="data" style="overflow: auto; height: 100%;">

                    <div class="data-content-toolbar">
                        <button class="button button-square button-normal button-border button-square" id="expand-data"><i id="button-down-data" class="fa fa-caret-down"></i></i></button>
                    </div>

                    <table id="example" class="display" cellspacing="0" width="100%" ><thead><tr><th>Name</th><th>Position</th><th>Office</th><th>Salary</th></tr></thead><tbody><tr><td>Tiger Nixon</td><td>System Architect</td><td>Edinburgh</td><td>$320,800</td></tr><tr><td>Garrett Winters</td><td>Accountant</td><td>Tokyo</td><td>$170,750</td></tr><tr><td>Ashton Cox</td><td>Junior Technical Author</td><td>San Francisco</td><td>$86,000</td></tr><tr><td>Cedric Kelly</td><td>Senior Javascript Developer</td><td>Edinburgh</td><td>$433,060</td></tr><tr><td>Airi Satou</td><td>Accountant</td><td>Tokyo</td><td>$162,700</td></tr><tr><td>Brielle Williamson</td><td>Integration Specialist</td><td>New York</td><td>$372,000</td></tr><tr><td>Herrod Chandler</td><td>Sales Assistant</td><td>San Francisco</td><td>$137,500</td></tr><tr><td>Rhona Davidson</td><td>Integration Specialist</td><td>Tokyo</td><td>$327,900</td></tr><tr><td>Colleen Hurst</td><td>Javascript Developer</td><td>San Francisco</td><td>$205,500</td></tr><tr><td>Sonya Frost</td><td>Software Engineer</td><td>Edinburgh</td><td>$103,600</td></tr><tr><td>Jena Gaines</td><td>Office Manager</td><td>London</td><td>$90,560</td></tr><tr><td>Quinn Flynn</td><td>Support Lead</td><td>Edinburgh</td><td>$342,000</td></tr><tr><td>Charde Marshall</td><td>Regional Director</td><td>San Francisco</td><td>$470,600</td></tr><tr><td>Haley Kennedy</td><td>Senior Marketing Designer</td><td>London</td><td>$313,500</td></tr><tr><td>Tatyana Fitzpatrick</td><td>Regional Director</td><td>London</td><td>$385,750</td></tr><tr><td>Michael Silva</td><td>Marketing Designer</td><td>London</td><td>$198,500</td></tr><tr><td>Paul Byrd</td><td>Chief Financial Officer (CFO)</td><td>New York</td><td>$725,000</td></tr><tr><td>Gloria Little</td><td>Systems Administrator</td><td>New York</td><td>$237,500</td></tr><tr><td>Bradley Greer</td><td>Software Engineer</td><td>London</td><td>$132,000</td></tr><tr><td>Dai Rios</td><td>Personnel Lead</td><td>Edinburgh</td><td>$217,500</td></tr><tr><td>Jenette Caldwell</td><td>Development Lead</td><td>New York</td><td>$345,000</td></tr><tr><td>Yuri Berry</td><td>Chief Marketing Officer (CMO)</td><td>New York</td><td>$675,000</td></tr><tr><td>Caesar Vance</td><td>Pre-Sales Support</td><td>New York</td><td>$106,450</td></tr><tr><td>Doris Wilder</td><td>Sales Assistant</td><td>Sidney</td><td>$85,600</td></tr><tr><td>Angelica Ramos</td><td>Chief Executive Officer (CEO)</td><td>London</td><td>$1,200,000</td></tr><tr><td>Gavin Joyce</td><td>Developer</td><td>Edinburgh</td><td>$92,575</td></tr><tr><td>Jennifer Chang</td><td>Regional Director</td><td>Singapore</td><td>$357,650</td></tr><tr><td>Brenden Wagner</td><td>Software Engineer</td><td>San Francisco</td><td>$206,850</td></tr><tr><td>Fiona Green</td><td>Chief Operating Officer (COO)</td><td>San Francisco</td><td>$850,000</td></tr><tr><td>Shou Itou</td><td>Regional Marketing</td><td>Tokyo</td><td>$163,000</td></tr><tr><td>Michelle House</td><td>Integration Specialist</td><td>Sidney</td><td>$95,400</td></tr><tr><td>Suki Burks</td><td>Developer</td><td>London</td><td>$114,500</td></tr><tr><td>Prescott Bartlett</td><td>Technical Author</td><td>London</td><td>$145,000</td></tr><tr><td>Gavin Cortez</td><td>Team Leader</td><td>San Francisco</td><td>$235,500</td></tr><tr><td>Martena Mccray</td><td>Post-Sales support</td><td>Edinburgh</td><td>$324,050</td></tr><tr><td>Unity Butler</td><td>Marketing Designer</td><td>San Francisco</td><td>$85,675</td></tr><tr><td>Howard Hatfield</td><td>Office Manager</td><td>San Francisco</td><td>$164,500</td></tr><tr><td>Hope Fuentes</td><td>Secretary</td><td>San Francisco</td><td>$109,850</td></tr><tr><td>Vivian Harrell</td><td>Financial Controller</td><td>San Francisco</td><td>$452,500</td></tr><tr><td>Timothy Mooney</td><td>Office Manager</td><td>London</td><td>$136,200</td></tr><tr><td>Jackson Bradshaw</td><td>Director</td><td>New York</td><td>$645,750</td></tr><tr><td>Olivia Liang</td><td>Support Engineer</td><td>Singapore</td><td>$234,500</td></tr><tr><td>Bruno Nash</td><td>Software Engineer</td><td>London</td><td>$163,500</td></tr><tr><td>Sakura Yamamoto</td><td>Support Engineer</td><td>Tokyo</td><td>$139,575</td></tr><tr><td>Thor Walton</td><td>Developer</td><td>New York</td><td>$98,540</td></tr><tr><td>Finn Camacho</td><td>Support Engineer</td><td>San Francisco</td><td>$87,500</td></tr><tr><td>Serge Baldwin</td><td>Data Coordinator</td><td>Singapore</td><td>$138,575</td></tr><tr><td>Zenaida Frank</td><td>Software Engineer</td><td>New York</td><td>$125,250</td></tr><tr><td>Zorita Serrano</td><td>Software Engineer</td><td>San Francisco</td><td>$115,000</td></tr><tr><td>Jennifer Acosta</td><td>Junior Javascript Developer</td><td>Edinburgh</td><td>$75,650</td></tr><tr><td>Cara Stevens</td><td>Sales Assistant</td><td>New York</td><td>$145,600</td></tr><tr><td>Hermione Butler</td><td>Regional Director</td><td>London</td><td>$356,250</td></tr><tr><td>Lael Greer</td><td>Systems Administrator</td><td>London</td><td>$103,500</td></tr><tr><td>Jonas Alexander</td><td>Developer</td><td>San Francisco</td><td>$86,500</td></tr><tr><td>Shad Decker</td><td>Regional Director</td><td>Edinburgh</td><td>$183,000</td></tr><tr><td>Michael Bruce</td><td>Javascript Developer</td><td>Singapore</td><td>$183,000</td></tr><tr><td>Donna Snider</td><td>Customer Support</td><td>New York</td><td>$112,000</td></tr></tbody></table>
                </div>
                <script type="text/javascript">
                    // For demo to fit into DataTables site builder...
                    $('#example')
                            .removeClass('display')
                            .addClass('table table-striped table-bordered');</script>
                <div class="vertical-separator-content"></div>


                <div class="log-content" id="log">

                    <div class="log-content-toolbar">
                        <button class="button button-square button-normal button-border button-square" id="expand-log"><i id="button-down-log" class="fa fa-caret-down"></i></i></button>
                    </div>
                    <!--
                    <table id="message-log" class="display" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th>Time</th>
                                <th>Action</th>
                                <th>Message</th>
                                <th>Duration</th>
                            </tr>
                        </thead>
                    </table> -->

                </div>

            </div>
        </div>

        <script>
            $('.main-content').click(function () {
                //var toggleHeight = $(this).height === 260 ? "180px" : "260px";

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
            $('.side-bar').click(function (e) {
                //var toggleHeight = $(this).height === 260 ? "180px" : "260px";
                $('.side-bar').animate({width: 250}, 600);
                if ($('.main').margin !== "0px") {

                    $('.main').animate({marginLeft: '0px'}, 600);
                }
            });
            $('.query-content').click(function (e) {
                e.stopPropagation();
            });
            $('.data-content').click(function (e) {
                e.stopPropagation();
            });
            $('.logcontent').click(function (e) {
                e.stopPropagation();
            });

            $('.side-box').click(function (e) {
                e.stopPropagation();
            });


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
            $('#expand-log').click(function (e) {
                e.stopPropagation();

                var toggleHeight;
                //var toggleHeight = $(this).height() === 260 ? "180px" : "260px";
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


            var min = 210;
            var max = 900;
            var mainmin = 200;
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

        </script>
    </body>
</html>
