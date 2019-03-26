function search(ele) {
    if (event.key === 'Enter') {
        var val = ele.value;
        console.log(val);
        exec(val);
    }
}

function exec(cmd) {
    $.ajax({
        "url": "./exec",
        "method": "POST",
        data: JSON.stringify({
            cmd: cmd
        }),
        success: function (result) {
            console.log(result);
            $("#content").append(result.text);
        },
        contentType: "application/json",
        dataType: 'json'
    });
}