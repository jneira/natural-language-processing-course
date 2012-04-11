$(document).ready(function(){
    $('.modal').unbind('show.modalfocus').unbind('hide.modalfocus');
    $('.modal').bind('show.modalfocus', function(){
        var activeModalElem = $(this);
        ModalFocus.enable(this);
        var iframeElem = $(this).find('iframe');
        
        if(iframeElem.length > 0){
            iframeElem.bind('load', function(){
                iframeElem.contents().find(ModalFocus.selector).not('.close, [disabled=disabled], [readonly=readonly]').first().focus();
                iframeElem.contents().keydown(function(event){
                    var ESC_KEY = 27;
                    if(event.which === ESC_KEY){
                        activeModalElem.modal('hide');
                    }
                })
            })

        }
    });
     $('.modal').bind('hide.modalfocus', function(){
        ModalFocus.disable();
    });
})
