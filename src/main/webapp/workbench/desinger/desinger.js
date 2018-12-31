

(function (old) {
    $.fn.attr = function () {
        if (arguments.length === 0) {
            if (this.length === 0) {
                return null;
            }

            var obj = {};
            $.each(this[0].attributes, function () {
                if (this.specified) {
                    obj[this.name] = this.value;
                }
            });
            return obj;
        }

        return old.apply(this, arguments);
    };
})($.fn.attr);

jQuery.fn.outerHTML = function () {
    return jQuery('<div />').append(this.eq(0).clone()).html();
};

var site;
$(document).ready(function () {
    site = window.location.hash.substring(1);
    $.ajax({
        "async": true,
        "url": "http://localhost:9090/page?page=" + site,
        "method": "GET",
        "headers": {
            "content-type": "application/json",
            "cache-control": "no-cache",
        }
    }).done(function (response) {
        init();
        editor.addComponents(response);
    });
});

function save() {
    var data = {
        name: "name",
        body: editor.getHtml()
    }
    $.ajax({
        "async": true,
        "url": "http://localhost:9090/page?page=" + site,
        "method": "POST",
        "headers": {
            "content-type": "application/json",
        },
        "data": JSON.stringify(data)
    }).done(function (response) {
        console.log(response);
    });
}

function init() {
    editor = grapesjs.init({
        height: '100%',
        noticeOnUnload: 0,
        storageManager: {
            autoload: 0
        },
        container: '#gjs',
        fromElement: true,
        canvas: {
            styles: [
                'https://www.w3schools.com/w3css/4/w3.css',
                'https://www.w3schools.com/lib/w3-theme-blue.css',
                'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'
            ]
        }

        // plugins: ['gjs-navbar', 'grapesjs-touch', 'gjs-blocks-basic'],
        // pluginsOpts: {
        //     'gjs-navbar': {

        //     },
        //     'gjs-blocks-basic': { /* ...options */ }
        // },
    });

    editor.Panels.addButton('options', [{
        id: 'save',
        className: 'fa fa-floppy-o icon-blank',
        command: function () {
            save();
        },
        attributes: {
            title: 'Save Template'
        }
    }]);

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
                    'rv-value',
                    'placeholder',
                    {
                        type: 'select',
                        label: 'Type',
                        name: 'type',
                        options: [{
                            value: 'text',
                            name: 'Text'
                        }, {
                            value: 'email',
                            name: 'Email'
                        }, {
                            value: 'password',
                            name: 'Password'
                        }, {
                            value: 'number',
                            name: 'Number'
                        }, ]
                    }, {
                        type: 'checkbox',
                        label: 'Required',
                        name: 'required',
                    }
                ],
            }),
        }, {
            isComponent: function (el) {
                var tagName = new String(el.tagName).toLowerCase();
                if (tagName == 'input') {
                    return {
                        type: 'input'
                    };
                }
            },
        }),

        view: dView,
    });

    const headTypes = [{
        value: 'p',
        name: 'Paragraph'
    }, {
        value: 'span',
        name: 'Span'
    }, {
        value: 'h1',
        name: 'Headline 1'
    }, {
        value: 'h2',
        name: 'Headline 2'
    }, {
        value: 'h3',
        name: 'Headline 3'
    }, {
        value: 'h4',
        name: 'Headline 4'
    }, {
        value: 'h5',
        name: 'Headline 5'
    }, {
        value: 'h6',
        name: 'Headline 6'
    }, ];

    var txtType = domComps.getType('text');
    var txtModel = txtType.model;
    var txtView = txtType.view;
    domComps.addType('head', {
        model: txtModel.extend({
            defaults: Object.assign({}, txtModel.prototype.defaults, {
                tagName: 'h1',
                traits: [
                    'rv-text'
                    ,{
                        type: 'select',
                        label: 'Type',
                        name: 'header-type',
                        changeProp: 1,
                        options: headTypes
                    },
                    'title'
                ]
            }),
            init: function () {
                this.listenTo(this, 'change:header-type', this.updElem);
            },
            updElem: function () {
                //todo remove or do it right (make the Text Tags cangeable)
                // var newElem = $('<' + this.changed['header-type'] + '>');
                // const attrs = $(this.view.el.outerHTML).attr();
                // for (var attr in attrs) {
                //     newElem.attr(attr, attrs[attr]);
                // }
                // newElem.html(this.view.el.innerHTML);
                // this.view.model.set('content', newElem.outerHTML());
                // this.view.el.outerHTML = newElem.outerHTML();
                // this.attributes.tagName = this.changed['header-type'];
                // editor.store();
            }
        }, {
            isComponent: function (el) {
                var tagName = new String(el.tagName).toLowerCase();
                for (let i = 0; i < headTypes.length; i++) {
                    const hType = headTypes[i];
                    if (hType.value == tagName)
                        return {
                            type: 'head'
                        };
                }
            },
        }),

        view: txtView,
    });

    domComps.addType('container', {
        model: dModel.extend({
            defaults: Object.assign({}, dModel.prototype.defaults, {
                traits: [
                    // strings are automatically converted to text types
                    'rv-each-item',
                    'rv-show',
                ],
            }),
        }, {
            isComponent: function (el) {
                var tagName = new String(el.tagName).toLowerCase();
                if (tagName == 'div') {
                    return {
                        type: 'container'
                    };
                }
            },
        }),

        view: dView,
    });


    domComps.addType('button', {
        model: txtModel.extend({
            defaults: Object.assign({}, txtModel.prototype.defaults, {
                traits: [
                    // strings are automatically converted to text types
                    'rv-enabled',
                    'rv-disabled',
                    'rv-on-click'
                ],
            }),
        }, {
            isComponent: function (el) {
                var tagName = new String(el.tagName).toLowerCase();
                if (tagName == 'button') {
                    return {
                        type: 'button'
                    };
                }
            },
        }),

        view: txtView,
    });



    var blockManager = editor.BlockManager;

    blockManager.add('simple-input', {
        label: 'Simple input',
        content: '<input class="w3-input w3-round-xxlarge">',
    });

    blockManager.add('simple-button', {
        label: 'Simple button',
        content: '<button class="w3-button w3-theme w3-round-xxlarge">Button</button>',
    });

    blockManager.add('simple-card', {
        label: 'Simple card',
        content: '<div style="min-height: 50px;" class="w3-card-4 w3-margin"><span></span> </div>',
    });

    blockManager.add('simple-text', {
        label: 'Simple text',
        content: '<h1 class="w3-opacity">hallo</h1>',
    });

}