(function ($) {
    $.fn.focusTextToEnd = function () {
        this.focus();
        var $thisVal = this.val();
        this.val('').val($thisVal);
        return this;
    }
}(jQuery));

$(document).ready(function () {
    $(document).click(function (event) {
        if ($(event.target).closest("#musicfile-name-text").length > 0) {
            $('#musicfile-name-text').addClass('hidden');
            $('#musicfile-name-form').removeClass('hidden');
            $('#musicfile-name-form\\:musicfile-name-input').focusTextToEnd();
        } else if (!$(event.target).closest("#musicfile-name-form\\:musicfile-name-input").length > 0) {
            $('#musicfile-name-form').addClass('hidden');
            $('#musicfile-name-text').removeClass('hidden');
        }
    });
});
