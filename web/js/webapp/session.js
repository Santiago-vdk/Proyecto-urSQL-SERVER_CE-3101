window.onload = function () {

    var generateUid = function (separator) {
        /// <summary>
        ///    Creates a unique id for identification purposes.
        /// </summary>
        /// <param name="separator" type="String" optional="true">
        /// The optional separator for grouping the generated segmants: default "-".    
        /// </param>

        var delim = separator || "-";

        function S4() {
            return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
        }

        return (S4() + S4() + delim + S4() + delim + S4() + delim + S4() + delim + S4() + S4() + S4());
    };

    function getCookie(c_name)
    {
        var i, x, y, ARRcookies = document.cookie.split(";");
        for (i = 0; i < ARRcookies.length; i++)
        {
            x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
            y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
            x = x.replace(/^\s+|\s+$/g, "");
            if (x == c_name)
            {
                return unescape(y);
            }
        }
    }

    var createCookie = function (name, value, days) {
        var expires;
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toGMTString();
        }
        else {
            expires = "";
        }
        document.cookie = name + "=" + value + expires + "; path=/";
    };

    if (getCookie("WEBAPP_SESSION")) {
        console.log("Hi again :)!");
        
    } else {
        createCookie("WEBAPP_SESSION", generateUid(), 0);
        var steps = [{
                content: '<p>Auto adjust sidebar.</p>',
                highlightTarget: true,
                nextButton: true,
                container: $('#main'),
                target: $('#side-horizontal-separator')
            }, {
                content: '<p>Expand sidebar area.</p>',
                container: $('#wrapper'),
                highlightTarget: true,
                nextButton: true,
                target: $('#sidebar'),
                my: 'right center',
                at: 'right center'
            },
            {
                content: '<p>Auto adjust main area.</p>',
                highlightTarget: true,
                nextButton: true,
                container: $('#main'),
                target: $('#main-horizontal-separator'),
                my: 'right center',
                at: 'left center'
            },
            {
                content: '<p>Expand individually any main block.</p>',
                highlightTarget: false,
                nextButton: true,
                container: $('#main'),
                target: $('#expand-data'),
                my: 'right center',
                at: 'left center'
            }, ];

        var tour = new Tourist.Tour({
            steps: steps,
            tipClass: 'Bootstrap',
            tipOptions: {showEffect: 'slidein'}
        });
        tour.start();
    }
};

