(function ($) {
    function parseParams() {
        var params = {},
            e,
            a = /\+/g,  // Regex for replacing addition symbol with a space
            r = /([^&=]+)=?([^&]*)/g,
            d = function (s) { return decodeURIComponent(s.replace(a, " ")); },
            q = window.location.search.substring(1);

        while (e = r.exec(q))
            params[d(e[1])] = d(e[2]);

        return params;
    }
    $.extend({
        getQueryString: function (name) {
            if (!this.queryStringParams)
                this.queryStringParams = parseParams();
            return this.queryStringParams[name];
        },
        getQueryParams: function () {
            if (!this.queryStringParams)
                this.queryStringParams = parseParams();
            return this.queryStringParams;
        }
    });
})(jQuery);  