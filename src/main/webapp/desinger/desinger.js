$(document).ready(function() {
    editor = grapesjs.init({
        height: '100%',
        noticeOnUnload: 0,
        storageManager: { autoload: 0 },
        container: '#gjs',
        fromElement: true,

        plugins: ['gjs-navbar', 'grapesjs-touch', 'gjs-blocks-basic'],
        pluginsOpts: {
            'gjs-navbar': {

            },
            'gjs-blocks-basic': { /* ...options */ }
        },
    });
})