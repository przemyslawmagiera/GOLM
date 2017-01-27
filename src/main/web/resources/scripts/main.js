/**
 * Created by Emilia on 2016-07-25.
 */
////////////////VALIDATION////////////////

$.fn.ownFormValidate = function (options) {
    options = $.extend({
        classError: 'invalid',
        classOk: 'valid'
    }, options);

    var $form = $(this);

    var prepareElements = function () {
        $form.find('input[required]').each(function () {
            var $t = $(this);

            if ($t.is('input')) {
                var type = $t.attr('type').toLowerCase();

                $t.on('blur keyup', function () {
                    testInputText($t)
                });
            }
        });
    }
    prepareElements();

    var testInputText = function ($input) {

        if ($input.attr('pattern') != undefined) {
            var reg = new RegExp($input.attr('pattern'), 'gi');
            if (!reg.test($input.val())) {
                $input.removeClass(options.classOk).addClass(options.classError);
                return false;
            } else {
                $input.removeClass(options.classError).addClass(options.classOk);
                return true;
            }
        } else {
            if ($input.val() == '') {
                $object.removeClass(options.classOk).addClass(options.classError);
                options.isInvalid($input);
                return false;
            } else {
                $object.removeClass(options.classError).addClass(options.classOk);
                options.isValid($input);
                return true;
            }
        }
    }
}
$(function () {
    $('form').ownFormValidate()
});

////////////////SCROLL////////////////

jQuery(document).ready(function ($) {
    $(".scroll").click(function (event) {
        event.preventDefault();
        $('html,body').animate({scrollTop: $(this.hash).offset().top}, 1200);
    });
});

///////////PRODUCT LIST SHADOWS ON HOVER///////////////////
//
//$(".product-container").hover(function () {
//    $(this).toggleClass('container-shadow');
//});