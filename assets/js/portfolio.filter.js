// http://sam.zoy.org/wtfpl/
(function($) {
  'use strict';

  $(document).ready(function() {
    var $items = $('#portfolio-items li');

    // If not IE7
    if (!(document.all && !document.querySelector)) {
      on_resize(function() {
        fitRows($items.filter(':visible'));
      })();
    }

    $('#portfolio-filter li').click(function(event) {
      event.preventDefault();
      var $this = $(this);
      if ($this.hasClass('active')) {
        return;
      }
      var $window = $(window);
      var filterType = $this.data('filter');
      var scrollTop = $window.scrollTop();

      $this.siblings('.active').removeClass('active');
      $this.addClass('active');
      $items.stop().hide();
      if (filterType === 'all') {
        $items.fadeIn('slow');
      } else {
        $items.each(function() {
          var $this = $(this);
          if ($.inArray(filterType, $this.data('tags').split(' ')) !== -1) {
            $this.fadeIn('slow');
          }
        });
      }
      fitRows($items.filter(':visible'));
      $window.scrollTop(scrollTop);
    });
  });

  // https://github.com/louisremi/jquery-smartresize
  function on_resize(c,t){window.onresize=function(){clearTimeout(t);t=setTimeout(c,150);};return c;}

  function fitRows($els) {
    var topPos = 0;
    $els.filter('.clear').removeClass('clear');
    $els.each(function() {
      var $this = $(this);
      if (topPos < $this.position().top) {
        $this.addClass('clear');
        topPos = $this.position().top;
      }
    });
  }
})(jQuery);
