(function ($) {

    $.fn.hashColored = function () {
        function getColorByBgColor(bgColor) {
            if (!bgColor) {
                return '';
            }
            return (parseInt(bgColor.replace('#', ''), 16) > 0xffffff / 1.01) ? '#000' : '#fff';
        }

        function stringToColour(str) {
            var hash = 0;
            for (var i = 0; i < str.length; i++) {
                hash = str.charCodeAt(i) + ((hash << 5) - hash);
            }
            var colour = '#';
            for (var i = 0; i < 3; i++) {
                var value = (hash >> (i * 8)) & 0xFF;
                colour += ('00' + value.toString(16)).substr(-2);
            }
            return colour;
        }
        return this.each(function () {
            // Do something to each element here.
            var text = $(this).text();
            var bgColor = stringToColour(text);
            var textColor = getColorByBgColor(bgColor);
            $(this).css('background-color', bgColor)
                .css('color', textColor);
            return $(this);
        });

    };

}(jQuery));