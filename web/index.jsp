<%-- 
    Document   : index
    Created on : Aug 29, 2015, 10:51:26 PM
    Author     : Shagy
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>urSQL • WebApp</title>

        <!-- style -->
        <link href="css/style.css" rel="stylesheet"/>
        <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="js/webapp/webapp-style.js"></script>
        <script src="js/sweetalert.min.js"></script>
        <link href="css/sweetalert.css" rel="stylesheet">

        <!-- Buttons -->
        <link rel="stylesheet" href="css/buttons.css">
        <link href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.css" rel="stylesheet">
        <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

        <script src="js/bootbox.min.js"></script>

        <!-- Sessions -->
        <script src="js/webapp/session.js"></script>

        <!-- CodeMirror -->
        <script src="js/codemirror.js"></script>
        <link rel="stylesheet" href="css/codemirror.css">
        <link rel="stylesheet" href="css/base16-light.css">
        <link rel="stylesheet" href="css/show-hint.css" />
        <script src="js/sql.js"></script>
        <script src="js/show-hint.js"></script>
        <script src="js/sql-hint.js"></script>
        <script src="js/panel.js"></script>
        <script src="js/webapp/webapp-code.js"></script> 

        <!-- DataTables -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.9/css/jquery.dataTables.min.css"/>
        <script type="text/javascript" src="https://cdn.datatables.net/1.10.9/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/r/bs-3.3.5/jqc-1.11.3,dt-1.10.8/datatables.min.js"></script>
        <!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.9/css/dataTables.bootstrap.min.css"/>-->
        <script src="js/webapp/webapp-table.js"></script> 

        <!-- Backbone -->
        <script type="text/javascript" src="js/underscore.js"></script>
        <script type="text/javascript" src="js/backbone.js"></script>
        <script type="text/javascript" src="js/webapp/webapp-log.js"></script>

        <!-- Backgrid -->
        <script type="text/javascript" src="js/backgrid.js"></script>
        <link rel="stylesheet" href="css/backgrid.css"/>

        <!-- jsTree -->
        <link rel="stylesheet" href="css/style.min.css" />
        <script src="js/jstree.min.js"></script>
        <script src="js/webapp/webapp-tree.js"></script> 

        <!-- Tourist -->
        <script src="js/tourist.js"></script>
        <link rel="stylesheet" href="css/tourist.css" type="text/css" media="screen">

        <script src="js/webapp/DataIO.js"></script>

    </head>
    <body>
<!--
        <div class="page-cover" id="page" >

        </div>

        <div class="progress mine" style=" background-color:#8C8C8C;border-radius: 0px;">
            <div class="progress-bar progress-bar-warning progress-bar-striped active prog" id="barra" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:0%;">

            </div>
        </div> 
-->
        <div class="wrapper" id="wrapper">
            <div class="side-bar" id="sidebar">
                <div class="side-box" id="sidebox">
                    <a href="#" class="button button-action disabled" style="width:100%;" id="button-start">START <i class="fa fa-play"></i></a>
                    <hr class="hr"></hr>              
                    <a href="#" class="button button-caution disabled" style="width:100%;" id="button-stop">STOP <i class="fa fa-stop"></i></i></a>
                    <hr class="hr"></hr>   
                    <a href="#" class="button button-primary disabled" style="width:100%;" id="button-createdb">CREATE DB <i class="fa fa-database"></i></a>
                    <hr class="hr"></hr>   
                    <a href="#" class="button button-highlight disabled" style="width:100%;" id="button-dropdb">DROP DB <i class="fa fa-trash-o"></i></a>
                    <hr class="hr"></hr>   
                    <a href="#" class="button button-normal" style="width:100%;" id="button-statusdb">STATUS <i class="fa fa-bug" style="color: #008F00"></i></i></a>
                    <hr class="hr"></hr>   
                    <a href="#" class="button button-normal disabled" style="width:100%;" id="button-listdb">LIST DB <i class="fa fa-list"></i></a>
                    <hr class="hr"></hr>   
                    <a href="#" class="button button-normal disabled" style="width:100%;" id="button-displaydb">DISPLAY DB <i class="fa fa-desktop"></i></a>
                </div>
                <div class="vertical-separator-sidebar" id="side-horizontal-separator"></div>
                <div class="side-box" id="tree-box">
                    <div id="jstree">
                        <ul>
                            <li id="root" data-jstree='{"icon":"//jstree.com/tree.png"}'><a>Schemas</a></li>
                        </ul>
                    </div>
                    <div id="tree-refresh" style="  position: absolute;bottom: 0;left: 0; width: 100%;">
                        <a href="#" class="button button-action" style="width:100%;height:32px;background-color: #268C90;" id="button-refresh">Refresh <i class="fa fa-refresh"></i></a>

                    </div>
                </div>

            </div>
            <div id="split-bar"></div>
            <div class="main-content" id="main">

                <div class="query-content" id="query">
                    <div class="query-content-toolbar">
                        <button class="btn btn-sq-lg btn-primary" id="expand-query"><i id="button-down-query" class="fa fa-caret-down "></i></button>
                        <hr class="hr"></hr>   
                        <button class="btn btn-sq-lg btn-success disabled" id="execute-query"  data-content="Run Query!" rel="popover" data-placement="left" data-trigger="hover"><i class="fa fa-check" id="execute-query-icon"></i></button>
                        <!-- <hr class="hr"></hr>   
                         <button class="btn btn-sq-lg btn-warning" id="executing-query" data-content="Executing Query!" rel="popover" data-placement="left" data-trigger="hover" hidden><i class="fa fa-cog fa-spin"></i></button> -->
                    </div>

                </div>
                <div class="vertical-separator-content" id="main-horizontal-separator"></div>
                <div class="data-content" id="data" style="overflow-x: scroll;overflow-x: hidden ">

                    <div class="data-content-toolbar">
                        <button class="btn btn-sq-lg btn-primary" id="expand-data"><i id="button-down-data" class="fa fa-caret-down"></i></button>
                    </div>

                    <div class="data-content-table">
                        <table id="data-table" class="display cell-border" cellspacing="0">
                          <!--  <thead><tr><th>Name</th><th>Position</th><th>Office</th><th>Salary</th></tr></thead><tbody></tbody> -->
                        </table>

                        <!--                          <table id="data-table" class="display cell-border" cellspacing="0"><thead><tr><th>Name</th><th>Position</th><th>Office</th><th>Salary</th></tr></thead><tbody><tr><td>Tiger Nixon</td><td>System Architect</td><td>Edinburgh</td><td>$320,800</td></tr><tr><td>Garrett Winters</td><td>Accountant</td><td>Tokyo</td><td>$170,750</td></tr><tr><td>Ashton Cox</td><td>Junior Technical Author</td><td>San Francisco</td><td>$86,000</td></tr><tr><td>Cedric Kelly</td><td>Senior Javascript Developer</td><td>Edinburgh</td><td>$433,060</td></tr><tr><td>Airi Satou</td><td>Accountant</td><td>Tokyo</td><td>$162,700</td></tr><tr><td>Brielle Williamson</td><td>Integration Specialist</td><td>New York</td><td>$372,000</td></tr><tr><td>Herrod Chandler</td><td>Sales Assistant</td><td>San Francisco</td><td>$137,500</td></tr><tr><td>Rhona Davidson</td><td>Integration Specialist</td><td>Tokyo</td><td>$327,900</td></tr><tr><td>Colleen Hurst</td><td>Javascript Developer</td><td>San Francisco</td><td>$205,500</td></tr><tr><td>Sonya Frost</td><td>Software Engineer</td><td>Edinburgh</td><td>$103,600</td></tr><tr><td>Jena Gaines</td><td>Office Manager</td><td>London</td><td>$90,560</td></tr><tr><td>Quinn Flynn</td><td>Support Lead</td><td>Edinburgh</td><td>$342,000</td></tr><tr><td>Charde Marshall</td><td>Regional Director</td><td>San Francisco</td><td>$470,600</td></tr><tr><td>Haley Kennedy</td><td>Senior Marketing Designer</td><td>London</td><td>$313,500</td></tr><tr><td>Tatyana Fitzpatrick</td><td>Regional Director</td><td>London</td><td>$385,750</td></tr><tr><td>Michael Silva</td><td>Marketing Designer</td><td>London</td><td>$198,500</td></tr><tr><td>Paul Byrd</td><td>Chief Financial Officer (CFO)</td><td>New York</td><td>$725,000</td></tr><tr><td>Gloria Little</td><td>Systems Administrator</td><td>New York</td><td>$237,500</td></tr><tr><td>Bradley Greer</td><td>Software Engineer</td><td>London</td><td>$132,000</td></tr><tr><td>Dai Rios</td><td>Personnel Lead</td><td>Edinburgh</td><td>$217,500</td></tr><tr><td>Jenette Caldwell</td><td>Development Lead</td><td>New York</td><td>$345,000</td></tr><tr><td>Yuri Berry</td><td>Chief Marketing Officer (CMO)</td><td>New York</td><td>$675,000</td></tr><tr><td>Caesar Vance</td><td>Pre-Sales Support</td><td>New York</td><td>$106,450</td></tr><tr><td>Doris Wilder</td><td>Sales Assistant</td><td>Sidney</td><td>$85,600</td></tr><tr><td>Angelica Ramos</td><td>Chief Executive Officer (CEO)</td><td>London</td><td>$1,180,000</td></tr><tr><td>Gavin Joyce</td><td>Developer</td><td>Edinburgh</td><td>$92,575</td></tr><tr><td>Jennifer Chang</td><td>Regional Director</td><td>Singapore</td><td>$357,650</td></tr><tr><td>Brenden Wagner</td><td>Software Engineer</td><td>San Francisco</td><td>$206,850</td></tr><tr><td>Fiona Green</td><td>Chief Operating Officer (COO)</td><td>San Francisco</td><td>$850,000</td></tr><tr><td>Shou Itou</td><td>Regional Marketing</td><td>Tokyo</td><td>$163,000</td></tr><tr><td>Michelle House</td><td>Integration Specialist</td><td>Sidney</td><td>$95,400</td></tr><tr><td>Suki Burks</td><td>Developer</td><td>London</td><td>$114,500</td></tr><tr><td>Prescott Bartlett</td><td>Technical Author</td><td>London</td><td>$145,000</td></tr><tr><td>Gavin Cortez</td><td>Team Leader</td><td>San Francisco</td><td>$235,500</td></tr><tr><td>Martena Mccray</td><td>Post-Sales support</td><td>Edinburgh</td><td>$324,050</td></tr><tr><td>Unity Butler</td><td>Marketing Designer</td><td>San Francisco</td><td>$85,675</td></tr><tr><td>Howard Hatfield</td><td>Office Manager</td><td>San Francisco</td><td>$164,500</td></tr><tr><td>Hope Fuentes</td><td>Secretary</td><td>San Francisco</td><td>$109,850</td></tr><tr><td>Vivian Harrell</td><td>Financial Controller</td><td>San Francisco</td><td>$452,500</td></tr><tr><td>Timothy Mooney</td><td>Office Manager</td><td>London</td><td>$136,180</td></tr><tr><td>Jackson Bradshaw</td><td>Director</td><td>New York</td><td>$645,750</td></tr><tr><td>Olivia Liang</td><td>Support Engineer</td><td>Singapore</td><td>$234,500</td></tr><tr><td>Bruno Nash</td><td>Software Engineer</td><td>London</td><td>$163,500</td></tr><tr><td>Sakura Yamamoto</td><td>Support Engineer</td><td>Tokyo</td><td>$139,575</td></tr><tr><td>Thor Walton</td><td>Developer</td><td>New York</td><td>$98,540</td></tr><tr><td>Finn Camacho</td><td>Support Engineer</td><td>San Francisco</td><td>$87,500</td></tr><tr><td>Serge Baldwin</td><td>Data Coordinator</td><td>Singapore</td><td>$138,575</td></tr><tr><td>Zenaida Frank</td><td>Software Engineer</td><td>New York</td><td>$125,250</td></tr><tr><td>Zorita Serrano</td><td>Software Engineer</td><td>San Francisco</td><td>$115,000</td></tr><tr><td>Jennifer Acosta</td><td>Junior Javascript Developer</td><td>Edinburgh</td><td>$75,650</td></tr><tr><td>Cara Stevens</td><td>Sales Assistant</td><td>New York</td><td>$145,600</td></tr><tr><td>Hermione Butler</td><td>Regional Director</td><td>London</td><td>$356,250</td></tr><tr><td>Lael Greer</td><td>Systems Administrator</td><td>London</td><td>$103,500</td></tr><tr><td>Jonas Alexander</td><td>Developer</td><td>San Francisco</td><td>$86,500</td></tr><tr><td>Shad Decker</td><td>Regional Director</td><td>Edinburgh</td><td>$183,000</td></tr><tr><td>Michael Bruce</td><td>Javascript Developer</td><td>Singapore</td><td>$183,000</td></tr><tr><td>Donna Snider</td><td>Customer Support</td><td>New York</td><td>$112,000</td></tr></tbody></table> -->

                    </div>

                </div>

                <div class="vertical-separator-content"></div>
                <div class="log-content" id="log" >

                    <div class="log-content-toolbar">
                        <button class="btn btn-sq-lg btn-primary" id="expand-log"><i id="button-down-log" class="fa fa-caret-down"></i></button>
                        <hr class="hr"></hr>   
                        <button class="btn btn-sq-lg btn-default disabled" id="plan-log" data-content="Execution plan" rel="popover" data-placement="left" data-trigger="hover"><i class="fa fa-history"></i></button>
                        <hr class="hr"></hr>
                        <button class="btn btn-sq-lg btn-danger" id="errase-log"><i class="fa fa-eraser"></i></button>
                    </div>
                    <div class="log-content-table">
                        <table id="log-tabla" class="display cell-border" cellspacing="0">
                            <thead>
                                <tr>
                                    <th> </th>
                                    <th>#</th>
                                    <th>Time</th>
                                    <th>Action</th>
                                    <th>Message</th>
                                    <th>Duration</th>
                                </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table> 

                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
