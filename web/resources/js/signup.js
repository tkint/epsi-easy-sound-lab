
window.onload = function () {
    document.getElementById("signupform:password").onchange = validatePassword;
    document.getElementById("signupform:passwordConfirm").onchange = validatePassword;
}
function validatePassword() {
    var password = document.getElementById("signupform:password").value;
    var confirm = document.getElementById("signupform:passwordConfirm").value;
    if (password != confirm)
        document.getElementById("signupform:passwordConfirm").setCustomValidity("Les mots de passe doivent correspondre");
    else
        document.getElementById("signupform:passwordConfirm").setCustomValidity('');
}
