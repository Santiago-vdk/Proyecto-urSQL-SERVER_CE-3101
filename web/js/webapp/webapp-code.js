var editor;

$(document).ready(function () {
    var mime = 'text/x-sql';
    editor = CodeMirror(document.getElementById("query"), {
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

function getCode() {
    return editor.getValue();
};