var request;
function sendInfo()
{
    var v = document.getElementById('signupform:user-search-input').value;
    var url = "jsp/usersearch.jsp?value=" + v;

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
        var users = JSON.parse(val);

        if (users.length > 0) {
            html = "<ul>";

            users.forEach(function (user) {
                html += `<li class="user-search-choice" onclick="setIdTarget(${user.id}, '${user.pseudo}`;
                if (user.publicEmail) {
                    html += `(${user.email})`;
                }
                html += `')">${user.pseudo}`;
                if (user.publicEmail) {
                    html += `(${user.email})`;
                }
                html += `</li>`;
            });

            html += "</ul>";

            document.getElementById('user-search-result').innerHTML = html;
        }
    }
}

function setIdTarget(id, value) {
    document.getElementById('signupform:mailtarget').value = id;
    document.getElementById('signupform:user-search-input').value = value;
    document.getElementById('signupform:user-search-input').readOnly = true;
    document.getElementById('user-search-result').innerHTML = "";
}

function clearField() {
    document.getElementById('signupform:user-search-input').value = "";
    document.getElementById('signupform:user-search-input').readOnly = false;
    document.getElementById('user-search-result').innerHTML = "";
    document.getElementById('clear-cross').style.color = "";
}

$(document).ready(function () {
    $(".btn-pref .btn").click(function () {
        $(".btn-pref .btn").removeClass("btn-primary").addClass("btn-default");
        // $(".tab").addClass("active"); // instead of this do the below
        $(this).removeClass("btn-default").addClass("btn-primary");
        document.getElementById('email-content').style.visibility = "visible";
    });
});

function displayEmail(email, author, target) {
    var html = '<pre>' + email.content + '</pre>';

    document.getElementById('email-content').innerHTML = html;

    displayUser(email.idAuthor, 'email-author', 'From :');
    displayUser(email.idTarget, 'email-target', 'To :');
}

function displayUser(id, div, head) {
    var user;
    var req = getXhr();
    req.open('GET', 'jsp/user.jsp?id=' + id, false);
    req.onreadystatechange = function () {
        if (req.readyState === 4 && req.status == 200) {
            user = JSON.parse(req.responseText);

            var html = head + ' ' + user.pseudo;

            if (user.publicEmail) {
                html += ' (' + user.email + ')';
            }

            document.getElementById(div).innerHTML = html;
        }
    };
    req.send();
}
