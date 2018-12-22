$(document).ready(function() {
    const site = window.location.hash.substring(1);
    console.log("edit" + site)

    editor = grapesjs.init({
        height: '100%',
        noticeOnUnload: 0,
        storageManager: { autoload: 0 },
        container: '#gjs',
        fromElement: true,

        // plugins: ['gjs-navbar', 'grapesjs-touch', 'gjs-blocks-basic'],
        // pluginsOpts: {
        //     'gjs-navbar': {

        //     },
        //     'gjs-blocks-basic': { /* ...options */ }
        // },
    });


    var domComps = editor.DomComponents;
    var dType = domComps.getType('default');
    var dModel = dType.model;
    var dView = dType.view;

    domComps.addType('input', {
        model: dModel.extend({
            defaults: Object.assign({}, dModel.prototype.defaults, {
                traits: [
                    // strings are automatically converted to text types
                    'name',
                    'placeholder',
                    {
                        type: 'select',
                        label: 'Type',
                        name: 'type',
                        options: [
                            { value: 'text', name: 'Text' },
                            { value: 'email', name: 'Email' },
                            { value: 'password', name: 'Password' },
                            { value: 'number', name: 'Number' },
                        ]
                    }, {
                        type: 'checkbox',
                        label: 'Required',
                        name: 'required',
                    }
                ],
            }),
        }, {
            isComponent: function(el) {
                if (el.tagName == 'INPUT') {
                    return { type: 'input' };
                }
            },
        }),

        view: dView,
    });

    var blockManager = editor.BlockManager;

    blockManager.add('my-first-input', {
        label: 'Simple input',
        content: '<input class="my-input">',
    });


    // 'my-first-block' is the ID of the block
    blockManager.add('my-first-block', {
        label: 'Simple block',
        content: '<div class="my-block">This is a simple block</div>',
    });

    blockManager.add('my-map-block', {
        label: 'Simple map block',
        content: {
            type: 'map', // Built-in 'map' component
            style: {
                height: '350px'
            },
            removable: true, // Once inserted it can't be removed
        }
    });
    blockManager.add('the-row-block', {
        label: '2 Columns',
        content: {
            value: '<div class="row" data-gjs-droppable=".row-cell" data-gjs-custom-name="Row">' +
                '<div class="row-cell" data-gjs-draggable=".row"></div>' +
                '<div class="row-cell" data-gjs-draggable=".row"></div>' +
                '</div>',
            style: {
                height: '350px'
            }
        },
    });

})