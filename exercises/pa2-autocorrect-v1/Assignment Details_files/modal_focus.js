var ModalFocus = {};

ModalFocus.selector = 'a, input, textarea, select, iframe';

ModalFocus.modalElem = $();
ModalFocus.foregroundElems = $();
ModalFocus.backgroundElems = $();
ModalFocus.focusIndex = 0;
    
ModalFocus.enable = function(modalElem){
    
    ModalFocus.disable();
    
    ModalFocus.modalElem = $(modalElem);
    ModalFocus.foregroundElems = ModalFocus.modalElem.find(ModalFocus.selector);
    if(ModalFocus.foregroundElems.length == 0) return;
    
    ModalFocus.backgroundElems = $(ModalFocus.selector).not(ModalFocus.foregroundElems);
    
    ModalFocus.foregroundElems.add(ModalFocus.backgroundElems).each(function(index, el){
        var elem = $(el);
        var tabIndex = elem.attr('tabindex');
        if(typeof(tabIndex) !== 'undefined'){
            elem.data('tabindex', null);
        }
        else{
            elem.data('tabindex', tabIndex);
        }
    });
    
    ModalFocus.foregroundElems.each(function(index, el){
        var foregroundElem = $(el);
        foregroundElem.attr('tabindex', index + 1);
    });

    ModalFocus.backgroundElems.attr('tabindex', -1);
    
    ModalFocus.foregroundElems.bind('focus.modalfocus', function(){
        ModalFocus.focusIndex = $(this).attr('tabindex');
    })
    
    ModalFocus.backgroundElems.bind('focus.modalfocus', function(){
        if(ModalFocus.focusIndex > 1){
            ModalFocus.foregroundElems.first().focus();
        }
        else{
            ModalFocus.foregroundElems.last().focus();
        }
    });
    
    ModalFocus.foregroundElems.not('.close').not('[disabled=disabled], [readonly=readonly]').first().focus();
    
};

ModalFocus.disable = function(){
    
    ModalFocus.foregroundElems.unbind('focus.modalfocus');
    ModalFocus.backgroundElems.unbind('focus.modalfocus');

    ModalFocus.foregroundElems.add(ModalFocus.backgroundElems).each(function(index, el){
        var elem = $(el);
        var tabIndex = elem.data('tabindex');
        if(tabIndex === null){
            elem.removeAttr('tabindex');
        }
        else{
            elem.attr('tabindex', tabIndex);
        }
    });
    
    ModalFocus.modalElem = $();
    ModalFocus.foregroundElems = $();
    ModalFocus.backgroundElems = $();
    ModalFocus.focusIndex = 0;
}
