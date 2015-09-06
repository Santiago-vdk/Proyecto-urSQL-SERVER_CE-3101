$(document).ready(function () {

var mime = 'text/x-sql';
                        editor = CodeMirror(query, {
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


});