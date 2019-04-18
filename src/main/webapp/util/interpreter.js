let oVal = jQuery.fn.val;
jQuery.fn.val = function(a) {
    let value = oVal.apply(this, arguments);

    if (a === undefined) { //read val
        if (this.attr("type") === "checkbox") {
            return this.prop("checked");
        }
    } else { //write val
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
    "java.lang.String": "<input class='w3-input w3-border' type='text'\>",
    "java.util.List": function(className, type, card, name, listvalue) {
        $.ajax({
            "url": "/listfield",
            "method": "POST",
            data: JSON.stringify({
                className: className,
                fieldName: name
            }),
            success: function(data) {
                for (let i = 0; i < listvalue.length; i++) {
                    const listElement = listvalue[i];
                    let card2 = $("<div class='w3-panel w3-card w3-light-grey'>")
                        .attr("className", className)
                        .append($("<h3>").text(className));
                    addFields(card2, className, data, listElement);
                    card.append(
                        $("<div>")
                        .addClass("w3-container")
                        .addClass("w3-margin")
                        .addClass("field")
                        .append(card2)
                        .append(
                            $("<label>")
                            .text(name)));
                }
                window.parent.resizeIframe();


            },
            contentType: "application/json",
            dataType: 'json'
        });
    }
};


function addFunctions(card, className) {
    $.ajax({
        "url": "/methode?class=" + className,
        "method": "GET",
    }).done(function(data) {
        let tabReiter = $("<div class='w3-bar w3-black'>");
        let tabs = [];
        for (let i in data) {
            let func = data[i];
            if (func.className === className) {
                let mehtodeName = func.mehtodeName;
                tabReiter.append(
                    $("<button class='w3-bar-item w3-button tablink'>" + mehtodeName + "</button>").on("click", function(e) {
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
                tab.append($("<button class='w3-button w3-margin-bottom w3-green'>").text("doit").on("click", function() {
                    console.log(mehtodeName);
                    let params = getParams(mehtodeName);
                    let object = getObject(className);

                    let toExecute = {
                        executable: mehtodeName,
                        clazz: className,
                        object: JSON.stringify(object),
                        paramsClasses: paramsClasses,
                        params: params
                    };
                    console.log(toExecute);

                    execute(toExecute, function(data) {
                        $("#out").text(JSON.stringify(data));
                        console.log(data);
                        let className = data.type;
                        buildUi($("#interpreter"), className, data.state);
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
    });
}

function execute(toExecute, than, error) {
    $.ajax({
        type: 'POST',
        url: "/call",
        data: JSON.stringify(toExecute),
        success: than,
        error: error,
        contentType: "application/json",
        dataType: 'json'
    });
}

function getParams(mehtodeName) {
    let params = [];
    $("#" + mehtodeName + ">.field").each(function(i, element) {
        params.push($($(element).find("input")[0]).val())
    });
    return params;
}

function getObject(className) {
    let object = {};
    $("[className='" + className + "']>.field").each(function(i, element) {
        let field = $($(element).find("input")[0]);
        object[field.attr("name")] = field.val();
    });
    return object;
}

function addField(className, type, card, name, value) {
    let htmlType = typeToHtmlMapping[type];
    if (htmlType === undefined)
        console.warn(type + " kann noch nicht abgebildet werden.");
    if (typeof htmlType == "string") {
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
    if (typeof htmlType == "function") {
        htmlType = htmlType(className, type, card, name, value);
    }
}

function addFields(card, className, structure, data) {
    let htmlForType = typeToHtmlMapping[className];
    if (htmlForType === undefined)
        for (let name in structure) {
            let type = structure[name];
            let value = data === undefined ? undefined : data[name];
            console.log(type)
            addField(className, type, card, name, value);
        }
    else {
        addField(className, className, card, "value", data);
    }
}

function buildUi($toInsert, className, data) {
    getCtClass(className, function(structure) {
        let card = $("<div class='w3-panel w3-card w3-light-grey'>").attr("className", className).append($("<h3>").text(className));
        addFields(card, className, structure, data);
        $toInsert.append(card);
        addFunctions(card, className);
    });
}

function addRow($toInsert, data) {
    var fields = $toInsert.children().first().children();
    var row = $("<tr>");
    for (let i = 0; i < fields.length; i++) {
        const field = $(fields[i]);
        row.append($("<td>").text(data[field.attr("name")]));
    }
    $toInsert.append(row);
}

function buildTable($toInsert, className, callback) {
    getCtClass(className, function(structure) {
        var headerRow = $("<tr>");
        for (const field in structure) {
            if (structure.hasOwnProperty(field)) {
                const fieldType = structure[field];
                headerRow.append($("<th>").attr("name", field).text(field))
            }
        }
        $toInsert.append(headerRow);
        callback($toInsert, structure)
    })
}


function getCtClass(className, callback) {
    $.ajax({
        type: 'POST',
        url: "/getCtClass",
        data: className,
        success: callback,
        contentType: "application/json",
        dataType: 'json'
    });
}