(function ($) {
    $.fn.focusTextToEnd = function () {
        this.focus();
        var $thisVal = this.val();
        this.val('').val($thisVal);
        return this;
    }
}(jQuery));

$(document).ready(function () {
    $('#tool-list').click(function (event) {
        event.preventDefault();
        $('.grid-item').addClass('list-item');
        $('.grid-item').removeClass('grid-item');

        $('#tool-grid').removeClass('active');
        $('#tool-list').addClass('active');
    });
    $('#tool-grid').click(function (event) {
        event.preventDefault();
        $('.list-item').addClass('grid-item');
        $('.list-item').removeClass('list-item');

        $('#tool-list').removeClass('active');
        $('#tool-grid').addClass('active');
    });

    $(document).click(function (event) {
        if ($(event.target).closest("#folder-name-text").length > 0) {
            $('#folder-name-text').addClass('hidden');
            $('#folder-name-form').removeClass('hidden');
            $('#folder-name-form\\:folder-name-input').focusTextToEnd();
        } else if (!$(event.target).closest("#folder-name-form\\:folder-name-input").length > 0) {
            $('#folder-name-form').addClass('hidden');
            $('#folder-name-text').removeClass('hidden');
        }
    });
});
