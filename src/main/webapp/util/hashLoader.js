$(document).ready(function () {
    $("body").append('<form class="w3-container" id="loader"><div class="w3-section"><label>Clazz</label><input class="w3-input w3-border w3-margin-bottom" id="clazz" placeholder="Enter Clazz" required type="text"><label>File</label><input class="w3-input w3-border" id="file" placeholder="Enter File" required type="text"><button class="w3-button w3-block w3-green w3-section w3-padding" id="load" type="submit">Load</button></div></form>');
    let hash = window.location.hash.substring(1);

    if (hash !== "") {
        $("#loader").hide();
        let urlData = JSON.parse(decodeURI(hash));
        init(urlData);
        $("#clazz").val(urlData.clazz);
        $("#file").val(urlData.file);
    }

    $("#load").on("click", function () {
        $("#loader").hide();
        init({
            clazz: $("#clazz").val(),
            file: $("#file").val()
        })
    })
})