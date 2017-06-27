$(document).ready(function () {
    $('#panelRightButton').click(function (event) {
        event.preventDefault();
        if ($('#panelRight').hasClass('switch')) {
            $('#panelRight').removeClass('switch');
            $('#content').removeClass('switch');
            $('#panelRightButton').removeClass('switch');
        } else {
            $('#panelRight').addClass('switch');
            $('#content').addClass('switch');
            $('#panelRightButton').addClass('switch');
        }
    });
});

function getXhr() {
    var xhr;
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xhr = new XMLHttpRequest();
    } else {
        // code for IE6, IE5
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xhr;
}
