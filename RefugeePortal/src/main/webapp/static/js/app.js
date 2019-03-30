/*!
 * Global Scripts
 */

/*  Common Variables
 --------------------------------------*/
var dataLang = $('html').attr('dir');

$(document).ready(function () {
    helpers();
    trimmer();
    mobileMenus();
    carousels();
    bulletSlider();
    tinytooltip();
    //formValidation();
    targetByHash();
    tempFunctions(); // only for front-end dev
    wilayaCommune();
    if ($(window).width() > 992) {
        smoothscroll();
    }
    if ($(".stickyMenu").length > 0) {
        stickyMenu()
    }
    if ($(".onepage-menu").length > 0) {
        onePageMenu()
    }
    slider3GBg();
});


/*  Common Helpers
 ------------------------------------------------------------------------------*/
function hexToRgb(hex) {
    // Expand shorthand form (e.g. "03F") to full form (e.g. "0033FF")
    var shorthandRegex = /^#?([a-f\d])([a-f\d])([a-f\d])$/i;
    hex = hex.replace(shorthandRegex, function (m, r, g, b) {
        return r + r + g + g + b + b;
    });

    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
    } : null;
}

function helpers() {

    // Detect Page Language
    var filterDataLang = $('html').attr('dir');

    // Call me back form
    (function cmb() {
        var callmeback = $('#callmebackForm');
        var callmebackBtn = $('.btn-callmeback');
        callmebackBtn.on('click', function (e) {

            callmebackBtn.removeClass('open');
            $(this).addClass('open');
            callmeback.find('form').get(0).reset();

            var cmbForm = $(this).closest('.row').parent().parent().parent();
            callmeback.slideUp(400, function () {
                callmeback.appendTo(cmbForm).slideUp(400).slideDown(400, function () {
                    $('html, body').animate({
                        scrollTop: callmeback.offset().top - 95
                    }, 500);
                });
            });

        });
        $('.close-cmb').on('click', function () {
            $('.callmeback').select2('val', '');
            $(this).closest(callmeback).slideUp(400, function () {
                $(this).closest(callmeback).find('form').get(0).reset();
            });
            $(this).closest('#callmeback').find('form').get(0).reset();
            callmebackBtn.removeClass('open');
        });
    })();


    // Small texts locale
    if (dataLang !== 'rtl') {
        var of = "of";
        var noResults = "Aucun résultat trouvé"
    } else {
        var of = "من";
        var noResults = "لا يوجد نتائج"
    }

    // Close container form
    (function closeContainer() {
        $('[data-action="close-container"]').click(function (e) {
            e.preventDefault();
            $(this).parent().slideUp(200);
        });
    })();

    $('.image-popup').magnificPopup({
        type: 'image',
        gallery: {
            enabled: true,
            tCounter: '%curr% ' + of + ' %total%'
        },
        mainClass: 'mfp-with-zoom',
        zoom: {
            enabled: true,
            duration: 300,
            easing: 'ease-in-out',
            opener: function (openerElement) {
                return openerElement.is('img') ? openerElement : openerElement.find('img');
            }
        }
    });

    $('.image-popup-gallery').magnificPopup({
        type: 'image',
        gallery: {
            enabled: true,
            tCounter: '%curr% ' + of + ' %total%'
        },
        mainClass: 'mfp-with-zoom',

    });

    $('.image-popup-gallery-videos').magnificPopup({
        type: 'iframe',
        iframe: {
            markup: '<div class="mfp-iframe-scaler">' +
            '<div class="mfp-close"></div>' +
            '<iframe class="mfp-iframe" frameborder="0" allowfullscreen></iframe>' +
            '</div>', // HTML markup of popup, `mfp-close` will be replaced by the close button

            patterns: {
                youtube: {
                    index: 'youtube.com/', // String that detects type of video (in this case YouTube). Simply via url.indexOf(index).

                    id: 'v=', // String that splits URL in a two parts, second part should be %id%
                    // Or null - full URL will be returned
                    // Or a function that should return %id%, for example:
                    // id: function(url) { return 'parsed id'; }

                    src: 'https://www.youtube.com/embed/%id%?autoplay=1' // URL that will be set as a source for iframe.
                },
                facebook: {
                    index: 'facebook.com',
                    id: 'videos/',
                    src: 'http://www.facebook.com/video/embed?video_id=%id%'
                }

                // you may add here more sources

            },

            srcAction: 'iframe_src', // Templating object key. First part defines CSS selector, second attribute. "iframe_src" means: find "iframe" and set attribute "src".
        }

    });

    $('.image-popup-gallery-logos').magnificPopup({
        type: 'image',
        gallery: {
            enabled: true,
            tCounter: '%curr% ' + of + ' %total%'
        },
        mainClass: 'mfp-with-zoom',

    });

    /* AutoTrigger - Set trigger to any item using:
     ( data-trigger="$the Trigger item" )
     ------------------------------*/
    (function autoTrigger() {
        $('[data-trigger]').each(function () {
            var setSelector = $(this).attr('data-trigger');
            var that = $(this);
            var parent = $(this).attr('data-parent');

            $("." + setSelector + "").bind('click', function (e) {
                e.preventDefault();
                that.toggleClass('expanded');
                $(this).toggleClass('expanded');

                if (parent != undefined) {
                    that.parents('.' + parent + '').addClass('expanded');
                }
            });

            $(document).on('tap mousedown', function (e) {
                if (!that.is(e.target) && that.has(e.target).length === 0 && !$("." + setSelector + "").is(e.target) && $("." + setSelector + "").has(e.target).length === 0 && that.parents('.' + parent + '').has(e.target).length === 0) {
                    if (that.hasClass('expanded')) {
                        that.removeClass('expanded');
                        $("." + setSelector + "").removeClass('expanded');
                    }
                    if (parent != undefined) {
                        that.parents('.' + parent + '').removeClass('expanded');
                    }
                }
            });
        });
    })();

    /* Mega Overlay
     ------------------------------*/
    (function megaMenuOverlay() {
        // check childs to set width
        $('.mega-menu').each(function () {
            if ($(this).find('> div').length == 3) {
                $(this).addClass('cols-3');
            }
            if ($(this).find('> div').length == 2) {
                $(this).addClass('cols-2');
            }
            if ($(this).find('> div').length == 1) {
                $(this).addClass('cols-1');
                $(this).parent().addClass('relative');
            }
        });
        $('.mega-menu').parent().hover(function () {
            $('.navbar').addClass('mega-menu-overlay');
        }, function () {
            $('.navbar').removeClass('mega-menu-overlay');
        });
    })();

    /* Titles Colors
     ------------------------------*/
    (function titleBgColor() {
        $('[data-background-color]').each(function () {
            hex = $(this).data('background-color');
            rgbObject = hexToRgb(hex);
            if (rgbObject != null) {
                rgb = rgbObject.r + ',' + rgbObject.g + ',' + rgbObject.b;
                opacity = $(this).data('opacity') / 100;
                $(this).css({
                    'background-color': 'rgba(' + rgb + ',' + opacity + ')'
                });
            }
        });
    })();


    /* Collapsing menu
     ------------------------------*/
    $('.collapsing-menu a').click(function () {
        $(this).parent().parent().find('ul')
            .not($(this).parent().children('ul'))
            .slideUp('fast')
            .parent('.expanded').toggleClass('expanded', 'collapsed');
        $(this).parent().children('ul')
            .slideToggle('fast')
            .parent()
            .toggleClass('expanded', 'collapsed');
    });

    /* Header Slider Initialize
     ------------------------------*/
    function doAnimations(elems) {
        var animEndEv = 'webkitAnimationEnd animationend';

        elems.each(function () {
            var $this = $(this),
                $animationType = $this.data('animation');
            $this.addClass($animationType).one(animEndEv, function () {
                $this.removeClass($animationType);
            });
        });
    }

    var $myCarousel = $('.header-slider'),
        $firstAnimatingElems = $myCarousel.find('.item:first').find("[data-animation ^= 'animated']");

    $myCarousel.carousel({
        interval: 10000
    });

    doAnimations($firstAnimatingElems);

    $myCarousel.carousel('pause');

    $myCarousel.on('slide.bs.carousel', function (e) {
        var $animatingElems = $(e.relatedTarget).find("[data-animation ^= 'animated']");
        doAnimations($animatingElems);
    });

    $('.header-slide--pp').on('click', function () {
        $this = $('.header-slide--pp');
        if ($(this).hasClass('active')) {
            $('.header-slider').carousel('cycle');
            $(this).removeClass('active').find('span').removeClass('icon-play').addClass('icon-pause');
        } else {
            $('.header-slider').carousel('pause');
            $(this).addClass('active').find('span').removeClass('icon-pause').addClass('icon-play');
        }
    });

    /* Shadow on header
     ------------------------------*/
    $(window).scroll(function () {
        var window_top = $(window).scrollTop();
        if (window_top > 0) {
            $('header').addClass('shadow');
        } else {
            $('header').removeClass('shadow');
        }
    });

    /* Match Height
     ------------------------------*/
    $('.match-height').matchHeight();
    $('a[data-toggle="tab"]').on('shown.bs.tab', function () {
        $.fn.matchHeight._update();
    });


    /* Price tag sizing
     ------------------------------*/
    (function priceDigitSize() {
        $('.priceDigits').each(function () {
            var $his = $(this);
            var priceCount = $his.text().length;
            switch (priceCount) {
                case 4:
                    $his.parent().addClass('resize small-4');
                    break;
                case 5:
                    $his.parent().addClass('resize small-5');
                    break;
                case 6:
                    $his.parent().addClass('resize small-6');
                    break;
                case 7:
                    $his.parent().addClass('resize small-7');
                    break;
                case 8:
                    $his.parent().addClass('resize small-8');
                    break;
                case 9:
                    $his.parent().addClass('resize small-9');
                    break;
            }
        })
    })();


    // Price tag sizing
    (function priceTagSizing() {
        var priceTag = $('span.price-tag');
        var countPriceTag = priceTag.text().length;
        if (countPriceTag == 5) {
            priceTag.parent().addClass('small');
        } else if (countPriceTag == 6) {
            priceTag.parent().addClass('x-small');
        }
    })();


    /* Parallax on class
     ------------------------------*/
    $('.parallax').parallax();

    /* Masonry Grid
     ------------------------------*/
    if (dataLang !== 'rtl') {
        var gridDirection = true;
    } else {
        var gridDirection = false;
    }
    $('.grid').masonry({
        itemSelector: '.grid-item',
        columnWidth: '.grid-sizer',
        percentPosition: true,
        originLeft: gridDirection
    });

    /* Automatically duplicate content for hover
     ------------------------------*/
    $('[class*="hover-content"]').each(function () {
        var container = $(this).append('<div class="duplicated"><div class="duplicated__content">' + $(this).html() + '</div></div>')
        container.find('.duplicated [class*="__image"],.duplicated [class*="__price"]').remove()
        container.find('.duplicated .btn').removeAttr('class').addClass('btn btn-transparent');
    });

    /* SVG HTML injector
     ------------------------------*/
    SVGInjector($('.svg'));


    /* FAQs Accordion Initilize */
    $('[data-accordion="faqs"]').each(function () {
        $(this).on('show.bs.collapse', function (e) {
            $(this).find('.in').collapse('hide');
            $(e.target).siblings().addClass('active').parent().addClass('active');
        });
        $(this).on('hide.bs.collapse', function (e) {
            $(this).find('.panel-heading').not($(e.target)).removeClass('active').parent().removeClass('active');
        });
        $(this).children().children().click(function (e) {
            if ($(e.currentTarget).siblings().children(".collapsing").length > 0) {
                return false;
            }
        });
    });


    /* Scroll Initilize */
    $('.block__propos--word').perfectScrollbar({
        theme: 'main-word-scroll'
    });

    /* Add IE classes
     ------------------------------*/
    (function addIeClass() {
        if (BrowserDetect.browser == 'Explorer') {
            $('html').addClass('ie');
        }
    })();

    /* Dropdown list Styling
     ------------------------------*/
    if (filterDataLang !== 'rtl') {
        var gridDirection = "ltr";
    } else {
        var gridDirection = "rtl";
    }

    $(".select2").select2({
        minimumResultsForSearch: -1,
        width: 200
    });

    $(".press-filter__select").select2({
        dropdownCssClass: 'press-filter__dropdown',
        dir: gridDirection
    });

    $(".dynamic-fields__select").select2({
        dropdownCssClass: 'dynamic-fields__dropdown',
        dir: gridDirection,
        minimumResultsForSearch: -1
    });

    $(".archive-years").select2({
        dropdownCssClass: 'ay-dropdown',
        minimumResultsForSearch: -1,
        width: 110,
        dir: gridDirection
    });

    $(".wilaya").select2({
        dropdownCssClass: 'select-choose-wilaya',
        dropdownParent: $('.select-choose-wilaya'),
        dir: gridDirection
    });

    $(".callmeback").select2({
        minimumResultsForSearch: -1,
        dropdownCssClass: 'callmeback-dropdown',
        dir: gridDirection,
        "language": {
            "noResults": function () {
                return noResults;
            }
        }
    });

    $(".contactform").select2({
        minimumResultsForSearch: -1,
        dropdownCssClass: 'callmeback-dropdown',
        dir: gridDirection
    });

    $('.eticketing-select').select2({
        minimumResultsForSearch: -1,
        dir: gridDirection,
        dropdownCssClass: 'eticketing-dropdown'
    });

    $(".couverture-selector").select2({
        dropdownCssClass: 'couverture-select2',
        width: '188',
		"language": {
       "noResults": function(){
            return "<span style='color:#d1dbe3;'></span>";
       }
   },
    escapeMarkup: function (markup) {
        return markup;
    }
    });

  	    $(".dropdown-navy").select2({

          dropdownCssClass: 'dropDownList-navy',
          width: 200,
  				"language": {
         "noResults": function(){
              return "<span style='color:#d1dbe3;'></span>";
         }
     },
      escapeMarkup: function (markup) {
          return markup;
      }
      });

  	    $(".dropdown-navy1").select2({

          dropdownCssClass: 'dropDownList-navy',
          width: 200,
  				"language": {
         "noResults": function(){
             return "<span style='color:#d1dbe3;'></span>";
         }
     },
      escapeMarkup: function (markup) {
          return markup;
      }
      });

    $('[data-toggle="tooltip"]').tooltip();
    $('[lang="fr"] [data-toggle="tooltip-directed"]').tooltip({
        placement: 'left'
    });
    $('[lang="ar"] [data-toggle="tooltip-directed"]').tooltip({
        placement: 'right'
    });


    /* Allow stores list DIV to scroll and prevent main page scroll */
    $('.stores-list__stores').on('mousewheel DOMMouseScroll', function (e) {
        var delta = -e.originalEvent.wheelDelta || e.originalEvent.detail;
        var scrollTop = this.scrollTop;
        if ((delta < 0 && scrollTop === 0) || (delta > 0 && this.scrollHeight - this.clientHeight - scrollTop === 0)) {
            e.preventDefault();
        }
    });

    // Filter .active class toggle in (List Slider Filter)
    var filterItem = $('.list--slider-filter li');
    filterItem.each(function () {
        $(this).on('click', function (e) {
            e.preventDefault();
            filterItem.removeClass('active');
            $(this).not('.active').addClass('active');
        });
    });

} // END of Helpers

/* tooltip on click
 ------------------------------*/
function tinytooltip() {
    $('.phones-slider img').each(function () {
        if ($(this).attr('data-details') != undefined && $(this).attr('data-details') != "") {
            $(this).tinytooltip({
                message: function (tip) {
                    return $(this).attr('data-url') + "<span>" + $(this).attr('data-details') + "</span>";
                },
                hover: false,
                classes: "phone-details"
            });
        }
    });


    $('.phones-slider img').mouseover(function () {
        $('.phones-slider img').not($(this)).trigger('hidetooltip'); // hide the tooltip
        $(this).trigger('showtooltip'); // show the tooltip
    });

    // $('body').click(function() {
    //     $('.phones-slider img').trigger('hidetooltip'); // hide the tooltip
    // });

    $('body').on({

        mouseleave: function (e) {
            if ($(e.target).attr('class') != "phone-pic" && $('.phone-details').has(e.target).length === 0) {
                $('.phones-slider img').trigger('hidetooltip'); // hide the tooltip
            }
        }
    }, '.phones-slider');

    $('body').click(function (e) {
        if ($(e.target).attr('class') != "phone-pic" && $('.phone-details').has(e.target).length === 0) {
            $('.phones-slider img').trigger('hidetooltip'); // hide the tooltip
        }
    });

}

/* Trimmer:Read more content
 ------------------------------------------------*/
function trimmer() {
    $('[data-content="trimmed"]').each(function () {
        var content = $(this);

        //content.after(readMoreLnk);
        function showText(event) {
            content.addClass('expanded').fadeIn(400);
            $(event.target).css('display', 'none');

            $(window).trigger('resize').trigger('scroll'); // Fix parallax after dom change
        }

        // check whether to trim or not
        //it was 70
        if (content.height() > 90) {
            content.addClass('true');
            $(this).next().css({
                'display': 'block'
            });
        } else {
            $(this).next().css({
                'display': 'none'
            });
        }

        // Bind click events
        $(this).next().on("click", "a", function (e) {
            e.preventDefault();
            showText(e)
        });
    });
}


/* Sticky menu
 ------------------------------------------------*/
function stickyMenu() {

    // Stick menu on scroll
    $(window).scroll(function () {
        var window_top = $(window).scrollTop() + 0; // the "12" should equal the margin-top value for nav.stick
        var div_top = $('.stickyMenu-fixer').offset().top;
        if (window_top > div_top) {
            $('.stickyMenu').addClass('stick');
            $('.stickyMenu-fixer').css({
                'padding-top': '' + $(".stickyMenu").outerHeight() + 'px'
            })
        } else {
            $('.stickyMenu').removeClass('stick');
            $('.stickyMenu-fixer').css({
                'padding-top': '0px'
            })
        }
    });

};

/* Target by #hash
 ------------------------------------------------*/
function targetByHash() {
    var hashVal = window.location.hash;
    $('.offer-bar').each(function () {
        if ('#' + $(this).attr('id') == hashVal) {
            $(this).find('a').trigger('click');
        }
    });

    if (hashVal) {
        var scrollLength = $(hashVal).offset().top - 120; //- $(".onepage-menu").outerHeight()
        if ($(hashVal).length) {
            $('html,body').animate({
                scrollTop: scrollLength
            }, 300);
            return false;
        }
    }

};

/* Onepage : menu scroll
 ------------------------------------------------*/
function onePageMenu() {
    // Clicks on links
    $(".onepage-menu a").click(function (e) {
        e.preventDefault();
        var target = $(this.hash);
        var scrollLength = target.offset().top - 140; //- $(".onepage-menu").outerHeight()
        if (target.length) {
            $('html,body').animate({
                scrollTop: scrollLength
            }, 450);
            return false;
        }
    });

    // Change links highlighting state on scroll
    var aChildren = $(".onepage-menu li").children(); // find the a children of the list items
    var aArray = []; // create the empty aArray
    for (var i = 0; i < aChildren.length; i++) {
        var aChild = aChildren[i];
        var ahref = $(aChild).attr('href');
        aArray.push(ahref);
    } // this for loop fills the aArray with attribute href values

    $(window).bind('scroll load', function () {
        var windowPos = $(window).scrollTop(); // get the offset of the window from the top of page
        var windowHeight = $(window).height(); // get the height of the window
        var docHeight = $(document).height();

        for (var i = 0; i < aArray.length; i++) {
            var theID = aArray[i];
            var divPos = $(theID).offset().top - 150; // get the offset of the div from the top of page
            var divHeight = $(theID).height(); // get the height of the div in question
            if (windowPos >= divPos && windowPos < (divPos + divHeight)) {
                $("a[href='" + theID + "']").addClass("nav-active");
            } else {
                $("a[href='" + theID + "']").removeClass("nav-active");
            }
        }

        if (windowPos + windowHeight == docHeight) {
            if (!$(".onepage-menu li:last-child a").hasClass("nav-active")) {
                var navActiveCurrent = $(".nav-active").attr("href");
                $("a[href='" + navActiveCurrent + "']").removeClass("nav-active");
                $(".onepage-menu li:last-child a").addClass("nav-active");
            }
        }
    });
}


/* Smooth scroll - Scroll made Elastice :)
 ------------------------------------------------*/
function smoothscroll() {
    var storepage = $('.stores-list__stores');
    var couverturePage = $('.couverture-selector');
    var roamingPage = $('.map-selector');
    if (storepage.length == 0 && couverturePage.length == 0 && roamingPage.length == 0 && $('.select2').length == 0) {
        $(function () {
            $.srSmoothscroll({
                // defaults
                step: 100,
                speed: 500,
                ease: 'swing',
                target: $('body'),
                container: $(window)
            })
        });
    }
}


/* Form validation */
function formValidation(formID) {
    $.validate({
        form: "#" + formID,
        validateOnBlur: true,
        scrollToTopOnError: false,
        borderColorOnError: "",
        inputParentClassOnSuccess: "",
        onError: function ($form) {
            console.log("On Error");
            $('.has-error').each(function () {
                $(this).find('.form-error').stop().addClass('animated shake');
            });
        },
        onElementValidate: function ($form) {
            $('.has-error').each(function () {
                console.log("Has Errors");
                $(this).find('.form-error').stop().removeClass('animated shake');
            });
        },
        onSuccess: function ($form) {
            var formType = $("#" + formID).find("input[name=formType]").val();

            if (formType == "dynamicForm") {

                (function tempDisableBtn(){
                    $("#" + formID).find('.dynamic-field-submit').attr('disabled', 'true');
                })();


                if ($("#" + formID).find("#has-captcha").val() && captchaResponse == "")
                    return false;
                else
                    submitDynamicForm(formID, successMessage, failMessage);
                return false;
            }
            else if (formType == "callBackForm") {
                if (captchaResponse == "")
                    return false;
                else
                    submitMailForm(formID, successMessage, failMessage);
                return false;
            }
        },
    });
}

/* Carousels
 ------------------------------------------------*/
var createGroupedArray = function(arr, chunkSize) {
    var groups = [], i;
    for (i = 0; i < arr.length; i += chunkSize) {
        groups.push(arr.slice(i, i + chunkSize));
    }
    return groups;
};

function dynmCarousel() { // Dynamic Carousel function
    windowMedia = $(window).width();
    if( windowMedia > 1200){
        var itemsPerGroup = 8;
    }
    if( windowMedia > 992 && windowMedia < 1200){
        var itemsPerGroup = 6;
    }
    if( windowMedia > 768 && windowMedia < 992){
        var itemsPerGroup = 6;
    }
    if(windowMedia < 768 ){
        var itemsPerGroup = 2;
    }
    var container = $('[data-slider="dynamic"]');

    container.each(function () {
        var $this = $(this);
        var allItems = $this.find('> div');
        var divsGroups = createGroupedArray(allItems, itemsPerGroup);

        $this.html(''); // Start manipulation
        $.each(divsGroups, function( index, group ) {
            var innerGroup = "";
            $.each(group,function (idx,item) {
                innerGroup = innerGroup+("<div class='col-xxs-12 col-xs-6 col-sm-4 col-md-4 col-lg-3'><div class='"+$(item).attr('class')+"'>"+$(item).html()+"</div></div>");
            });
            console.log(innerGroup);
            $this.append("<div class='item-media item-media-fixed clearfix'>"+innerGroup+"</div>");
        });

        // Run carousel func
        $this.owlCarousel({
            autoPlay: false,
            navigation: false,
            items: 1,
            itemsDesktop: [1199, 1],
            itemsDesktopSmall: [768, 1],
            itemsTablet: [640, 1],
            itemsMobile: [0, 1],
            touchDrag: false,
            mouseDrag: false
        });

        var arrowContainer = $this.parent().parent().prev();
        arrowContainer.find('.arrow-gallery.icon-circle-right').click(function () {
            $this.trigger('owl.next');
        });
        arrowContainer.find('.arrow-gallery.icon-circle-left').click(function () {
            $this.trigger('owl.prev');
        });

    });

}dynmCarousel();

// $(window).resize(function () {
//     $('[data-slider="dynamic"]').each(function () {
//         $(this).owlCarousel('destroy');
//     });
//     dynmCarousel()
// });

function carousels() {
    $(".owl-carousel-4").owlCarousel({
        autoPlay: 3000,
        navigation: true,
        items: 4,
        itemsDesktop: [1199, 3],
        itemsDesktopSmall: [979, 4],
        itemsTablet: [768, 3],
        itemsMobile: [479, 2],
        navigationText: ["", ""],
        stopOnHover: true
    });

    $(".owl-carousel-1").owlCarousel({
        //autoPlay: 3000,
        navigation: true,
        items: 1,
        itemsDesktop: [1199, 1],
        itemsTablet: [992, 1],
        itemsMobile: [640, 1],
        navigationText: ["", ""],
        stopOnHover: true,
        mouseDrag: false
    });


    $(".owl-carousel-2").owlCarousel({
        autoPlay: 3000,
        navigation: true,
        items: 2,
        itemsDesktop: [1199, 2],
        navigationText: ["", ""],
        stopOnHover: true
    });

    $(".owl-carousel-3").owlCarousel({
        autoPlay: 3000,
        navigation: true,
        items: 3,
        itemsDesktop: [1199, 3],
        itemsTablet: [992, 2],
        itemsMobile: [640, 1],
        navigationText: ["", ""],
        stopOnHover: true
    });


    $('.owl-carousel--floating').owlCarousel({
        center: true,
        items: 5,
        loop: true,
        margin: 10,
        URLhashListener: true,
        responsive: {
            900: {
                items: 5
            },
            768: {
                items: 3
            },
            421: {
                items: 2
            },
            0: {
                items: 1
            }

        },
        nav: true,
        navText: ["", ""],
        autoplay:true,
        autoplayHoverPause: true,
        startPosition: 'URLHash'

    });

    $('.owl-carousel-5').owlCarousel({
        autoPlay: 3000,
        navigation: true,
        items: 5,
        itemsDesktop: [1199, 4],
        itemsTablet: [768, 3],
        itemsMobile: [479, 2],
        navigationText: ["", ""],
        stopOnHover: true
    });

    $('.owl-carousel--floating .item').hover(function () {
        $(this).parent().next().addClass('closest');
        $(this).parent().prev().addClass('closest');
    }, function () {
        $(this).parent().next().removeClass('closest');
        $(this).parent().prev().removeClass('closest');
    });

    var pressPhotos = $(".block__press-photos");
    pressPhotos.owlCarousel({
        autoPlay: 3000,
        navigation: false,
        items: 4,
        itemsDesktop: [1199, 3],
        itemsDesktopSmall: [768, 2],
        itemsTablet: [640, 1],
        itemsMobile: [0, 1],
        stopOnHover: true
    });
    $(".arrow-press-photos.icon-circle-right").click(function () {
        pressPhotos.trigger('owl.next');
    });
    $(".arrow-press-photos.icon-circle-left").click(function () {
        pressPhotos.trigger('owl.prev');
    });


    var sonnerieGallery = $(".block__sonnerie-gallery");
    sonnerieGallery.owlCarousel({
        autoPlay: false,
        navigation: false,
        items: 1,
        itemsDesktop: false,
        itemsDesktopSmall: false,
        itemsTablet: false,
        itemsMobile: false,
        touchDrag: false,
        mouseDrag: false
    });
    $(".arrow-sonnerie-gallery.icon-circle-right").click(function () {
        sonnerieGallery.trigger('owl.next');
    });
    $(".arrow-sonnerie-gallery.icon-circle-left").click(function () {
        sonnerieGallery.trigger('owl.prev');
    });

    var logosGallery = $(".block__logos-gallery");
    logosGallery.owlCarousel({
        autoPlay: false,
        navigation: false,
        items: 2,
        itemsDesktop: [1199, 3],
        itemsDesktopSmall: [768, 3],
        itemsTablet: [640, 2],
        itemsMobile: [0, 2],
        touchDrag: false,
        mouseDrag: false
    });
    $(".arrow-logos-gallery.icon-circle-right").click(function () {
        logosGallery.trigger('owl.next');
    });
    $(".arrow-logos-gallery.icon-circle-left").click(function () {
        logosGallery.trigger('owl.prev');
    });


    $('.block__sonnerie-gallery').find('.owl-item').each(function () {
        $(this).find('.item-sonnerie').each(function () {
            var fileID = $(this).data('id');
            var fileMP3 = $(this).data('file');
            $(this).find('button.mp3-player__info--play').on('click', function () {
                $('.item-sonnerie').removeClass('item-sonnerie--active');
                //$(this).closest('.item-sonnerie').addClass('item-sonnerie--active');
                $("#player-" + fileID).jPlayer({
                    ready: function () {
                        $(this).jPlayer("setMedia", {
                            mp3: fileMP3
                        }).jPlayer("play");
                    },
                    play: function () {
                        $(this).jPlayer("pauseOthers", 0);
                    },
                    cssSelectorAncestor: "#player-container-" + fileID,
                    cssSelector: {
                        play: ".mp3-player__info--play",
                        seekBar: ".mp3-player__progress--seek-bar",
                        playBar: ".mp3-player__progress--play-bar"
                    },
                    swfPath: "/",
                    supplied: "mp3",
                    useStateClassSkin: true,
                    autoBlur: false,
                    smoothPlayBar: true,
                    keyEnabled: true,
                    remainingDuration: true,
                    toggleDuration: true
                });
            });
        });
    });
}

/*  Wilaya and Commune filling function
 ------------------------------------------------------------------------------*/
function wilayaCommune() {
    var wilayasInput = $('[data-fill="wilayas"]');
    var communesInput = $('[data-fill="communes"]');
    var cache = {};
    if (location.host == "i4.io")
        assets_home = "/odz-revamp/assets";

    if (location.hostname == "localhost")
        assets_home = "/assets";

    $.getJSON('' + assets_home + '/js/wilayas-communes/wilayas.json', function (json) {
        $.each(json.wilayas, function (index, wilayas) {
            if (dataLang == "ltr") {
                wilayasInput.append('<option value="' + wilayas.file + '">' + wilayas.name + '</option>');
            } else {
                wilayasInput.append('<option value="' + wilayas.file + '">' + wilayas.nameAr + '</option>');
            }
        });
        //updateStyles()
    });

    // Wilaya input bind change
    wilayasInput.bind('change', function () {
        var wilayaFile = $(this).val();
        fillCommunes(wilayaFile);
        updateStyles()
    });

    // Fill Communes from Wilayas files
    function fillCommunes(wilaya) {
        communesInput.html('<option></option>');
        if (wilaya != 0) {
            if (cache[wilaya] == undefined) {
                $.getJSON('' + assets_home + '/js/wilayas-communes/' + encodeURI(wilaya) + '.json', function (json) {
                    loopCom(json);
                    cache[wilaya] = json;
                });
            } else {
                loopCom(cache[wilaya]);
            }
        }
    }

    // Loop communes object
    function loopCom(json) {
        $.each(json.communes, function (index, communes) {
            if (dataLang == "ltr") {
                communesInput.append('<option>' + communes.name + '</option>');
            } else {
                communesInput.append('<option>' + communes.nameAr + '</option>');
            }
        });
    }

    // Apply select style
    function updateStyles() {
        wilayasInput.trigger('change.select2');
        communesInput.trigger('change.select2');
    }

}

/*  Gifs Animator
 ------------------------------------------------------------------------------*/
var animating = new Array();

function gifAnimate(obj, button) {
    if (animating[obj] !== true) {
        $(button).text($(button).attr('data-pause-word'));
        $('.' + obj + '[data-gif]').each(function () {
            $(this).attr('src', $(this).attr('data-gif'));
        });
        animating[obj] = true;
    } else {
        $(button).text($(button).attr('data-test-word'));
        $('.' + obj + '[data-gif]').each(function () {
            $(this).attr('src', $(this).attr('data-src'));
        });
        animating[obj] = false;
    }
}

/*  Bullets-slider
 ------------------------------------------------------------------------------*/
function bulletSlider() {
    $('.bullets-slider').each(function () {
        // Initialize
        var slider = $(this);
        var slides = slider.find('.slides > div');
        var bullets = slider.find('.bullets');
        var slidesCount = slides.length;
        var activeSlide = 1;


        // Fill Bullets list & bind clicks
        bullets.html('<ol />');
        var bulletsList = bullets.find('ol');
        slides.each(function () {
            bulletsList.append('<li class="bullet">' + ($(this).index() + 1) + '</li><li class="bridge" />');
        });
        bulletsList.find('li:last-child').remove(); // Remove last bridge
        if (slides.length > 1) {
            bulletsList.find('li.bullet').bind('click', function () {
                showSlide(parseInt($(this).text()));
            });
        }

        // Function : Show slide by index
        function showSlide(idx) {
            slides.eq(activeSlide - 1).fadeTo("slow", 0).hide();
            slides.eq(idx - 1).addClass('active').fadeTo("slow", 1.0);

            bullets.find('li.bullet').removeClass('active');
            bullets.find('li.bullet').eq(idx - 1).addClass('active');
            activeSlide = idx;
            //for mobile specification slider
            if ($('.message').length > 0) {
                $('.phones-slider img').trigger('hidetooltip'); // hide the tooltip
            }
            if ($('.middle-index-btn').length > 0) {
                $('.middle-index-btn').each(function () {
                    var $this = $(this);
                    $this.css('margin-top', $this.parent().height() - $this.height())
                });
            }
            trimmer();
        }


        // Show first slide
        showSlide(activeSlide);

        // Auto Rotating
        function rotate() {
            if (activeSlide < slidesCount) {
                showSlide(activeSlide + 1);
            } else {
                showSlide(1);
            }
        }

if($('#isAutoScroll').length >= 1){
        if (slider.data('slide') == "auto" && $('#isAutoScroll').val() == "true") {
            if (slides.length > 1) {
                var autoRotate = setInterval(rotate, 3000);
                $(this).hover(function () {
                    clearInterval(autoRotate);
                    //alert();
                }, function () {

                    autoRotate = setInterval(rotate, 3000);
                });
            }
        }
      }else{
        if (slider.data('slide') == "auto") {
            if (slides.length > 1) {
                var autoRotate = setInterval(rotate, 3000);
                $(this).hover(function () {
                    clearInterval(autoRotate);
                    //alert();
                }, function () {

                    autoRotate = setInterval(rotate, 3000);
                });
            }
        }

      }

    });
}

/*  Change 3G Slider background-color for each slide
 ------------------------------------------------------------------------------*/
function slider3GBg() {
    var firstSlider = $('#3gSliderHours .item:first').data('bgcolor');
    $('#3gSliderHours').css('background-color', firstSlider);
    $('.carousel-indicators li').on('click', function () {
        var nextSliderBg = $(this).data('bgcolor');
        $('#3gSliderHours').css({
            'background-color': nextSliderBg,
            '-webkit-transition': 'all 0.75s ease-in-out',
            '-moz-transition': 'all 0.75s ease-in-out',
            '-o-transition': 'all 0.75s ease-in-out',
            'transition': 'all 0.75s ease-in-out'
        });
        var currentSlider = $(this).index();
        $('.sliderHours__navigation li').removeClass('active');
        $('.sliderHours__navigation li[data-slide-to="' + currentSlider + '"]').addClass('active');
    });
    $('#3gSliderHours').carousel({
        interval: 7500
    })
    $('#3gSliderHours').on('slid.bs.carousel', function (event) {
        var currentSlider = $('#3gSliderHours .item.active').data('bgcolor');
        $('#3gSliderHours').css({
            'background-color': currentSlider,
            '-webkit-transition': 'all 0.75s ease-in-out',
            '-moz-transition': 'all 0.75s ease-in-out',
            '-o-transition': 'all 0.75s ease-in-out',
            'transition': 'all 0.75s ease-in-out'
        });
        var currentSlider = $('.carousel-indicators li.active').index();
        $('.sliderHours__navigation li').removeClass('active');
        $('.sliderHours__navigation li[data-slide-to="' + currentSlider + '"]').addClass('active');
    });
    $('.sliderHours__navigation li:first').addClass('active');
    $('.sliderHours__navigation li').on('click', function () {
        $('.sliderHours__navigation li').removeClass('active');
        $(this).addClass('active');
    });
}


/*  Mobile menu events / Class Triggers and body overlays
 ------------------------------------------------------------------------------*/
function mobileMenus() {
    function checkMenuActivity() { // Check for overlay
        setTimeout(function () {
            if ($('.mobile-menu').hasClass('expanded')) {
                $('body').addClass('menu-out');
            } else {
                $('body').removeClass('menu-out');
            }
        }, 1)
    }

    $('body').bind('tap click', function () {
        checkMenuActivity()
    });


    /* Touch only
     $('.touch .document').not($('[lang="ar"] .document')).bind("swiperight", function() {
     $('.mobile-menu--main').addClass('expanded');
     checkMenuActivity()
     });
     // $('.touch .document').not($('[lang="ar"] .document')).bind("swipeleft", function() {
     //     $('.mobile-menu--user').addClass('expanded');
     //     checkMenuActivity()
     // });
     $('.touch[lang="ar"] .document').bind("swipeleft", function() {
     $('.mobile-menu--main').addClass('expanded');
     checkMenuActivity()
     });
     // $('.touch[lang="ar"] .document').bind("swiperight", function() {
     //     $('.mobile-menu--user').addClass('expanded');
     //     checkMenuActivity()
     // });


     $('.touch .mobile-menu--main').not($('[lang="ar"] .mobile-menu')).bind("swipeleft", function() {
     $(this).removeClass('expanded');
     checkMenuActivity()
     });
     // $('.touch .mobile-menu--user').not($('[lang="ar"] .mobile-menu')).bind("swiperight", function() {
     //     $(this).removeClass('expanded');
     //     checkMenuActivity()
     // });

     $('.touch[lang="ar"] .mobile-menu--main').bind("swiperight", function() {
     $(this).removeClass('expanded');
     checkMenuActivity()
     });
     // $('.touch[lang="ar"] .mobile-menu--user').bind("swipeleft", function() {
     //     $(this).removeClass('expanded');
     //     checkMenuActivity()
     // });
     */

}


/*  Dev Temp Helpers
 ------------------------------------------------------------------------------*/

function tempFunctions() {

    /* Language change
     ------------------------------*/
    var strURL = location.href;
    $('.lang-selector ul a.ltr').click(function () {
        newURL = strURL.replace("-rtl.html", ".html");
        location = newURL;
    });
    $('.lang-selector ul a.rtl').click(function () {
        newURL = strURL.replace(".html", "-rtl.html");
        location = newURL;
    });

    /* Show/hide Support menu link
     ------------------------------*/
    if (strURL.indexOf('ntreprise') >= 0) {
        $('.search-bar .support-link').show();
    }

}


$(window).load(function () {
    jQuery(window).trigger('resize').trigger('scroll');
});


jQuery(document).ready(function ($) {
    // browser window scroll (in pixels) after which the "back to top" link is shown
    var offset = 300,
        //browser window scroll (in pixels) after which the "back to top" link opacity is reduced
        offset_opacity = 1200,
        //duration of the top scrolling animation (in ms)
        scroll_top_duration = 700,
        //grab the "back to top" link
        $back_to_top = $('.back-top');

    //hide or show the "back to top" link
    $(window).scroll(function () {
        ($(this).scrollTop() > offset) ? $back_to_top.addClass('back-top--is-visible') : $back_to_top.removeClass('back-top--is-visible back-top--fade-out');
        if ($(this).scrollTop() > offset_opacity) {
            $back_to_top.addClass('back-top--fade-out');
        }
    });

    //smooth scroll to top
    $back_to_top.on('click', function (event) {
        event.preventDefault();
        $('body,html').animate({
            scrollTop: 0,
        }, scroll_top_duration);
    });
});


$.fn.matchHeight._afterUpdate = function (event, groups) {
    jQuery(window).trigger('resize').trigger('scroll');
}


///////////////////////////////////////////////////////////////


$('.owl-carousel--floating').show();
//$('.owl-carousel--floating').css('overflow','hidden');

var owl = $('.owl-carousel--floating');
owl.on('initialized.owl.carousel', function (e) {
//  $('.owl-carousel--floating .owl-item:first').addClass('owl-up');
    $('.owl-carousel--floating .item').each(function () {
        if ($(this).attr('data-hash') == window.location.hash.substring(1)) {
            $(this).parent('.owl-item').addClass('owl-up');
        }

    });

    $('.owl-carousel--floating .item').each(function () {
        if ($(this).attr('data-hash') == '?' + window.location.search.substring(1)) {
            //$(this).parent('.owl-item').addClass('owl-up');
            window.location.hash = '?' + window.location.search.substring(1);
        }
    });


    // $('.owl-carousel--floating .owl-next,.owl-carousel--floating .owl-prev').click(function () {
    //     $('.owl-carousel--floating .item').each(function () {
    //         if ($(this).attr('data-hash') == window.location.hash.substring(1)) {
    //             $(this).find('img').click();
    //         }
    //     });
    // });
});
function callFloating(param) {

    var n = $(param).parent('.item').parent('.owl-item').index();
    //$('.owl-carousel--floating').trigger('to.owl.carousel', n);

    $('.owl-item.owl-up').removeClass('owl-up');

    $(param).parent('.item').parent('.owl-item').addClass('owl-up');
    window.location.hash = $(param).parent('.item').attr('data-hash');
}

$(window).on('hashchange', function () {
    $('.owl-item.owl-up').removeClass('owl-up');
    $('.owl-carousel--floating .item').each(function () {
        if ($(this).attr('data-hash') == window.location.hash.substring(1)) {
            $(this).parent('.owl-item').addClass('owl-up');
        }
    });
});


var isSmall = false;
$(window).scroll(function () {

    var leftVal = $('.moveBox img').css('margin-left');
    $('.moveBox img').css('margin-left', leftVal);
    var rightVal = $('.moveBox img').css('margin-right');
    $('.moveBox img').css('margin-right', rightVal);

    if ($(window).width() > 996) {
        if (!isSmall) {

            $('.moveBox img').css('width', $('.moveBox img').width() - 20);
            isSmall = true;
        }
//  var heroH = $('.block--hero-content').height();
//  if(heroH == null){heroH = 0;}
        if ($('.moveBox').parent('.row').height() > ($('.moveBox img').height())) {
            if ($(window).scrollTop() > $('header').height() && !inViewport($('.block--hero-content'))) {
                $('.moveBox img').css('width', $('.moveBox img').width());
                $('.moveBox img').css('position', 'fixed');
                $('.moveBox img').css('top', $('header').height() + 25);

            }
            else {
                $('.moveBox img').css('position', '');
                $('.moveBox img').css('top', '');
                $('.moveBox').parent('.row').css('position', '');

            }
            if (inViewport($('.stopBox'))) {

                $('.moveBox img').css('position', 'absolute');
                $('.moveBox img').css('top', 'auto');
                $('.moveBox img').css('width', $('.moveBox img').width());
                $('.moveBox').parent('.row').css('position', 'relative');
                $('.moveBox').css('position', 'initial')
                $('.moveBox img').css('bottom', '10px');
            }
        }

    }
});


function inViewport($ele) {
    try {
        var lBound = $(window).scrollTop(),
            uBound = lBound + $(window).height(),
            top = $ele.offset().top,
            bottom = top + $ele.outerHeight(true);

        return (top > lBound && top < uBound)
            || (bottom > lBound && bottom < uBound)
            || (lBound >= top && lBound <= bottom)
            || (uBound >= top && uBound <= bottom);
    }
    catch (err) {
    }
}


///////////////////////////////////////////////////////////
