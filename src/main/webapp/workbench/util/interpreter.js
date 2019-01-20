let oVal = jQuery.fn.val;
jQuery.fn.val = function (a) {
    let value = oVal.apply(this, arguments);

    if (a === undefined) {//read val
        if (this.attr("type") === "checkbox") {
            return this.prop("checked");
        }
    } else {//write val
        if (this.attr("type") === "checkbox") {
            this.prop("checked", a === "true")
        }
    }
    return value;
};

const typeToHtmlMapping = {
    "int": "<input class='w3-input w3-border' type='number'>",
    "java.lang.Integer": "<input class='w3-input w3-border' type='number'>",
    "boolean": "<input class='w3-check' type='checkbox'\>",
    "java.lang.Boolean": "<input class='w3-check' type='checkbox'\>",
    "java.lang.String": "<input class='w3-input w3-border' type='text'\>"
};


function addFunctions(data, card, className) {
    let tabReiter = $("<div class='w3-bar w3-black'>");
    let tabs = [];
    for (let i in data) {
        let func = data[i];
        if (func.className === className) {
            let mehtodeName = func.mehtodeName;
            tabReiter.append(
                $("<button class='w3-bar-item w3-button tablink'>" + mehtodeName + "</button>").on("click", function (e) {
                    $(".function").hide();
                    $(".tablink").removeClass("w3-red");
                    let functionName = $(e.currentTarget).addClass("w3-red").text();
                    $("#" + functionName).show();
                })
            );
            let tab = $("<div id='" + mehtodeName + "' class='w3-container w3-white w3-margin-bottom w3-border function' style='display:none'>")
                .append(
                    $("<h2>")
                        .text(mehtodeName));
            let paramsClasses = [];
            for (let num in func.params) {
                let param = func.params[num];
                addField(param.value.name, tab, param.key);
                paramsClasses.push(param.value.name);
            }
            tab.append($("<button class='w3-button w3-margin-bottom w3-green'>").text("doit").on("click", function () {
                console.log(mehtodeName);
                let params = [];
                $("#" + mehtodeName + ">.field").each(function (i, element) {
                    params.push($($(element).find("input")[0]).val())
                });

                let object = {};
                $("[className='" + className + "']>.field").each(function (i, element) {
                    let field = $($(element).find("input")[0]);
                    object[field.attr("name")] = field.val();
                });

                let toExecute = {
                    executable: mehtodeName,
                    clazz: className,
                    object: JSON.stringify(object),
                    paramsClasses: paramsClasses,
                    params: params
                };
                console.log(toExecute);

                $.ajax({
                    type: 'POST',
                    url: "/call",
                    data: JSON.stringify(toExecute),
                    success: function (data) {
                        $("#out").text(JSON.stringify(data));
                        console.log(data);
                        let className = data.type;
                        buildUi($("#interpreter"), className, data.state);
                    },
                    contentType: "application/json",
                    dataType: 'json'
                });


            }));
            tabs.push(tab);
        }
    }
    card.append(tabReiter);
    for (let i in tabs) {
        let tab = tabs[i];
        card.append(tab)
    }
}

function addField(type, card, name, value) {
    let htmlType = typeToHtmlMapping[type];
    if (htmlType === undefined) {
        console.warn(type + " kann noch nicht abgebildet werden.")
    }

    htmlType = $(htmlType).attr("name", name);
    $(htmlType).val(value);
    card.append(
        $("<div>")
            .addClass("w3-container")
            .addClass("w3-margin")
            .addClass("field")
            .append(htmlType)
            .append(
                $("<label>")
                    .text(name)));
}

function addFields(card, className, structure, data) {
    let htmlForType = typeToHtmlMapping[className];
    if (htmlForType === undefined)
        for (let name in structure) {
            let type = structure[name];
            let value = data === undefined ? undefined : data[name];
            addField(type, card, name, value);
        }
    else {
        addField(className, card, "value", data);
    }
}

function buildUi($toInsert, className, data) {
    $.ajax({
        type: 'POST',
        url: "/getClass",
        data: className,
        success: function (structure) {
            let card = $("<div class='w3-panel w3-card w3-light-grey'>").attr("className", className).append($("<h3>").text(className));
            addFields(card, className, structure, data);
            $toInsert.append(card);
            $.ajax({
                "url": "/methode?class=" + className,
                "method": "GET",
            }).done(function (data) {
                console.log(data);
                addFunctions(data, card, className);
            });
        },
        contentType: "application/json",
        dataType: 'json'
    });

}