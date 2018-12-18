var nodes = null;
var edges = null;
var network = null;
// randomly create some nodes and edges
var data = { "edges": [{ "from": "3a0f37ec-7b17-4cf5-964c-ebe8fc11b742", "to": "90192d1e-3c8f-4da9-be71-d8c1343c6416", "label": "", "id": "20b35d90-96e4-4d35-b802-f59d8c3fb7ac" }, { "from": "90192d1e-3c8f-4da9-be71-d8c1343c6416", "to": "41f5adae-b659-4bad-9f07-1363ae64bbe0", "label": "n-1", "id": "c72e2886-0589-4852-80b3-bce51534f351" }, { "from": "912eca75-8b69-4567-b5ba-2587a45293a5", "to": "41f5adae-b659-4bad-9f07-1363ae64bbe0", "label": "", "id": "0fa55875-deec-4d22-a407-9957bb8a337d" }, { "from": "8f1d5e78-5090-42b2-be9e-2b439b139666", "to": "41f5adae-b659-4bad-9f07-1363ae64bbe0", "label": "", "id": "de79f4fa-c532-4be0-b5ec-cfec9532ec21" }, { "from": "41f5adae-b659-4bad-9f07-1363ae64bbe0", "to": "9aa8f4b4-1c59-400e-b118-b4f3ac88844d", "label": "", "id": "45fc780f-9a03-4219-9fbb-41ff08765120" }], "nodes": [{ "id": "41f5adae-b659-4bad-9f07-1363ae64bbe0", "x": -73.75, "y": -3.5, "label": "User", "type": "Entity", "shape": "box" }, { "id": "90192d1e-3c8f-4da9-be71-d8c1343c6416", "x": 3.25, "y": -175.5, "label": "Note", "type": "Entity", "shape": "box" }, { "id": "3a0f37ec-7b17-4cf5-964c-ebe8fc11b742", "x": -75.75, "y": -103.5, "label": "Text", "type": "String", "color": "#EC7063", "shape": "ellipse" }, { "id": "8f1d5e78-5090-42b2-be9e-2b439b139666", "x": -109.75, "y": 127.5, "label": "Name", "type": "String", "color": "#EC7063", "shape": "ellipse" }, { "id": "9aa8f4b4-1c59-400e-b118-b4f3ac88844d", "x": -7.75, "y": 72.5, "label": "Smart", "type": "Boolean", "color": "#C39BD3", "shape": "ellipse" }, { "id": "912eca75-8b69-4567-b5ba-2587a45293a5", "x": -175.75, "y": 59.5, "label": "Age", "type": "Integer", "color": "#7BE141", "shape": "ellipse" }] };
var seed = 2;

function destroy() {
    if (network !== null) {
        network.destroy();
        network = null;
    }
}

$(document).ready(function() {
    $("#saveButton").on("click", function() {
        save();
    })
})

function save() {
    var srcNodes = network.body.data.nodes._data;
    var srcEdges = network.body.data.edges._data;
    var nodes = [];
    var edges = [];
    for (const key in srcNodes) {
        if (srcNodes.hasOwnProperty(key)) {
            const element = srcNodes[key];
            nodes.push(element);
        }
    }
    for (const key in srcEdges) {
        if (srcEdges.hasOwnProperty(key)) {
            const element = srcEdges[key];
            edges.push(element);
        }
    }
    var saveData = {
        edges: edges,
        nodes: nodes
    }
    console.log(saveData);
}

function draw() {
    destroy();
    nodes = [];
    edges = [];

    // create a network
    var container = document.getElementById('mynetwork');
    var options = {
        layout: {
            randomSeed: seed
        }, // just to make sure the layout is the same when the locale is changed
        locale: "en",
        manipulation: {
            addNode: function(data, callback) {
                // filling in the popup DOM elements
                document.getElementById('node-operation').innerHTML = "Add Node";
                editNode(data, clearNodePopUp, callback);
            },
            editNode: function(data, callback) {
                // filling in the popup DOM elements
                document.getElementById('node-operation').innerHTML = "Edit Node";
                editNode(data, cancelNodeEdit, callback);
            },
            addEdge: function(data, callback) {
                let fromType = network.body.data.nodes._data[data.from].type;
                let toType = network.body.data.nodes._data[data.to].type;
                if (data.from == data.to) {
                    return;
                }
                if (fromType === "Entity" || toType === "Entity") {
                    if (fromType === toType) {
                        document.getElementById('edge-operation').innerHTML = "Add Edge";
                        editEdgeWithoutDrag(data, callback);
                    } else {
                        saveEdgeData(data, callback);
                    }
                }
            },
            editEdge: {
                editWithoutDrag: function(data, callback) {
                    document.getElementById('edge-operation').innerHTML = "Edit Edge";
                    editEdgeWithoutDrag(data, callback);
                }
            }
        }
    };
    network = new vis.Network(container, data, options);
}

function editNode(data, cancelAction, callback) {
    document.getElementById('nlabel').value = data.label;
    document.getElementById('nsave').onclick = saveNodeData.bind(this, data, callback);
    $("#ntype").val(data.type);
    $('.ncancel').on("click", cancelAction.bind(this, callback));
    document.getElementById('nodeEdit').style.display = 'block';
}

// Callback passed as parameter is ignored
function clearNodePopUp() {
    document.getElementById('nsave').onclick = null;
    document.getElementById('nodeEdit').style.display = 'none';
}

function cancelNodeEdit(callback) {
    clearNodePopUp();
    callback(null);
}

function saveNodeData(data, callback) {
    data.label = $('#nlabel').val();
    data.type = $("#ntype").val();
    switch (data.type) {
        case "Entity":
            data.shape = 'box';
            break;
        case "String":
            data.color = '#EC7063'
            data.shape = 'ellipse';
            break;
        case "Boolean":
            data.color = '#C39BD3'
            data.shape = 'ellipse';
            break;
        case "Integer":
            data.color = '#7BE141'
            data.shape = 'ellipse';
            break;
        case "Float":
            data.color = '#F1C40F'
            data.shape = 'ellipse';
            break;
        default:
            throw new Error(data.type + "isnt supported");
    }
    clearNodePopUp();
    callback(data);
}

function editEdgeWithoutDrag(data, callback) {
    // filling in the popup DOM elements
    document.getElementById('elabel').value = data.label;
    document.getElementById('esave').onclick = saveEdgeData.bind(this, data, callback);
    $('.ecancel').on("click", cancelEdgeEdit.bind(this, callback));
    document.getElementById('edgeEdit').style.display = 'block';
}

function clearEdgePopUp() {
    document.getElementById('elabel').value = "";
    document.getElementById('esave').onclick = null;
    document.getElementById('edgeEdit').style.display = 'none';
}

function cancelEdgeEdit(callback) {
    clearEdgePopUp();
    callback(null);
}

function saveEdgeData(data, callback) {
    if (typeof data.to === 'object')
        data.to = data.to.id
    if (typeof data.from === 'object')
        data.from = data.from.id
    data.label = document.getElementById('elabel').value;
    clearEdgePopUp();
    callback(data);
}

function init() {
    draw();
}