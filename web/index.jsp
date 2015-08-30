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
        <link href="css/style.css" rel="stylesheet"/>

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.js"></script>

        <script src="js/jquery.toolbar.js"></script>
        <link href="css/jquery.toolbar.css" rel="stylesheet" />
        <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
        <script src="js/codemirror.js"></script>
        <link rel="stylesheet" href="css/codemirror.css">
        <link rel="stylesheet" href="css/base16-light.css">

        <script src="js/sql.js"></script>
        <link rel="stylesheet" href="css/show-hint.css" />

        <script src="js/show-hint.js"></script>
        <script src="js/sql-hint.js"></script>


        <title>JSP Page</title>



    </head>


    <body>
        <div class="wrapper">
            <div class="sidebar">

                <div class="side-box">
                    <h1>CPL</h1>
                </div>

                <div class="vertical-separator"></div>

                <div class="side-box">
                    <h1>TREE</h1>
                </div>
            </div>
            <div class="main-content">

                <div class="data-content" id="query">
                    <script>
                        var mime = 'text/x-sql';
                        var myCodeMirror = CodeMirror(query, {
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


                    </script>



                </div>
                <div class="vertical-separator"></div>
                <div class="data-content" id="data">Data</div>
                <div class="vertical-separator"></div>
                <div class="data-content" id="log">messages</div>

            </div>
        </div>


        <script>
            $('.query-content').click(function () {
                var toggleHeight = $(this).height() == 260 ? "180px" : "260px";
                $(this).animate({height: toggleHeight});
            });

            $('.data-content').click(function () {
                var toggleHeight = $(this).height() == 260 ? "180px" : "260px";
                $(this).animate({height: toggleHeight});
            });

            $('.messages-content').click(function () {
                var toggleHeight = $(this).height() == 260 ? "180px" : "260px";
                $(this).animate({height: toggleHeight});
            });
        </script>

    </body>
</html>
