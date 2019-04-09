$(document).ready(function () {
    $("#button1").click(function () {
        /*
		let mydata = new FormData();
        mydata.append("username", "testuser");
        mydata.append("password", "123456");
        $.ajax({
            url: 'http://cccbd.top:8080/RESTHello/login/send',
            data: urlencodeFormData(mydata),
            type: 'POST',
            xhrFields: {
                withCredentials: true
                //这个很重要,否则无法设置cookie
            },
            complete: function (jqXHR) {
                $("#result").html(jqXHR.responseText);
            }
        });
		*/
        //上下两段的效果一样
        //但是上面部分需要jQuery
        let mydata = new FormData();
        mydata.append("username", $("#username").val());
        mydata.append("password", $("#password").val());
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://cccbd.top:8080/RESTHello/login/send', true);
        xhr.withCredentials = true;	//!!
        //xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.onload = function () {
            console.log(this.responseText);
            $("#result").html(this.responseText);
        };
        xhr.send(urlencodeFormData(mydata));
    });
});

function urlencodeFormData(fd) {
    var params = new URLSearchParams();
    for (var pair of fd.entries()) {
        typeof pair[1] == 'string' && params.append(pair[0], pair[1]);
    }
    return params.toString();
}