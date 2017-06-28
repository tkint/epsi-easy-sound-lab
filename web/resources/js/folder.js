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

var request;
function sendInfo(idUser)
{
    var v = document.getElementById('playlistform:playlist-search-input').value;
    var url = "jsp/playlistsearch.jsp?idUser=" + idUser + "&value=" + v;

    if (window.XMLHttpRequest) {
        request = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        request = new ActiveXObject("Microsoft.XMLHTTP");
    }

    try {
        request.onreadystatechange = getInfo;
        request.open("GET", url, true);
        request.send();
    } catch (e) {
        alert("Unable to connect to server");
    }
    document.getElementById('clear-cross').style.color = "red";
}

function getInfo() {
    if (request.readyState == 4) {
        var val = request.responseText;
        var playlists = JSON.parse(val);

        if (playlists.length > 0) {
            html = "<ul>";

            playlists.forEach(function (playlist) {
                html += `<li class="playlist-search-choice" onclick="setIdTarget(${playlist.id}, '${playlist.name}')">${playlist.name}</ul>`;
            });

            html += "</ul>";

            document.getElementById('playlist-search-result').innerHTML = html;
        }
    }
}

function setIdTarget(id, value) {
    document.getElementById('playlistform:playlisttarget').value = id;
    document.getElementById('playlistform:playlist-search-input').value = value;
    document.getElementById('playlistform:playlist-search-input').readOnly = true;
    document.getElementById('playlist-search-result').innerHTML = "";
}

function clearField() {
    document.getElementById('playlistform:playlist-search-input').value = "";
    document.getElementById('playlistform:playlist-search-input').readOnly = false;
    document.getElementById('playlist-search-result').innerHTML = "";
    document.getElementById('clear-cross').style.color = "";
}
