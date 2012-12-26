// Style Switcher http://sam.zoy.org/wtfpl/
/*global Cookies:false */

(function($) {
  'use strict';

  // If IE7, return
  if (document.all && !document.querySelector) {
    return;
  }
  // Not works under file://
  if (location.protocol === 'file:') {
    return;
  }

  var $colors, $navbarFixed, $noNavbarInverse;
  var $navbar = $('body > .navbar');
  var $CSSLinks = $('link[href*="assets/css/bootstrap"]');
  var rootPath = $navbar.find('.brand')[0].href
                   .replace('index.html', '')
                   .replace(location.protocol + '//' + location.host, '');
  var cookieOptions = {path: rootPath, expires: 2592000 /* 30 days*/};
  var CSSFiles = ['bootstrap.css', 'bootstrap-responsive.css'];
  var navbarFixedTooltipA = 'Switch to .navbar-fixed-top';
  var navbarFixedTooltipB = 'Switch to .navbar-static-top';
  var noNavbarInverseTooltipA = 'Add .navbar-inverse';
  var noNavbarInverseTooltipB = 'Remove .navbar-inverse';

  $.get(rootPath + 'assets/js/switcher.html', init);

  function init(html) {
    $(html).insertBefore($navbar.find('.nav-collapse'));

    $colors = $('#switcher-colors');
    $navbarFixed = $('#switcher-navbar-fixed');
    $noNavbarInverse = $('#switcher-no-navbar-inverse');

    addTooltip($navbarFixed, navbarFixedTooltipA);
    addTooltip($noNavbarInverse, noNavbarInverseTooltipA);

    setupEvents();
    restoreSettings();
  }

  function addTooltip($el, title) {
    $el.tooltip({title: title, placement: 'bottom', delay: 200});
  }

  function setupEvents() {

    $('#switcher-settings').on('click', '> a', function() {
      $(this).toggleClass('active');
    });

    $('#switcher-switcher').click(function() {
      $('#switcher').toggleClass('active');
    });

    $colors.on('click', '> li', function() {
      var cssDir;
      var $this = $(this);
      var newColor = $this.data('color');

      if ($this.hasClass('active')) {
        return;
      } else {
        $this.addClass('active').siblings('.active').removeClass('active');
      }

      if (newColor === 'default') {
        cssDir = 'css';
      } else {
        cssDir = 'css-' + newColor;
      }
      $.each(CSSFiles, function(index, file) {
        $CSSLinks.eq(index).attr(
          'href', rootPath + 'assets/' + cssDir + '/' + file
        );
      });

      $colors.find('i.icon-ok-sign').attr('class', 'icon-sign-blank');
      $this.find('i').attr('class', 'icon-ok-sign');
      Cookies.set('color', newColor, cookieOptions);
    });

    $navbarFixed.click(function() {
      var tooltipTitle, navbarFixed;

      $('body').toggleClass('has-navbar-fixed-top');
      $navbar.toggleClass('navbar-static-top').toggleClass('navbar-fixed-top');
      if ($navbar.hasClass('navbar-static-top')) {
        tooltipTitle = navbarFixedTooltipA;
        navbarFixed = 'n';
      } else {
        tooltipTitle = navbarFixedTooltipB;
        navbarFixed = 'y';
      }
      $(this).attr('data-original-title', tooltipTitle);
      Cookies.set('navbarFixed', navbarFixed, cookieOptions);
    });

    $noNavbarInverse.click(function() {
      var tooltipTitle, navbarInverse;

      $navbar.toggleClass('navbar-inverse');
      if (!$navbar.hasClass('navbar-inverse')) {
        tooltipTitle = noNavbarInverseTooltipA;
        navbarInverse = 'n';
      } else {
        tooltipTitle = noNavbarInverseTooltipB;
        navbarInverse = 'y';
      }
      $(this).attr('data-original-title', tooltipTitle);
      Cookies.set('navbarInverse', navbarInverse, cookieOptions);
    });
  }

  function restoreSettings() {
    var defaults = {
      navbarFixed: 'n',
      navbarInverse: 'n',
      color: 'default'
    };
    var navbarFixed = Cookies.get('navbarFixed') || defaults.navbarFixed;
    var navbarInverse = Cookies.get('navbarInverse') || defaults.navbarInverse;
    var color = Cookies.get('color') || defaults.color;

    if (navbarFixed !== defaults.navbarFixed) {
      $navbarFixed.click();
    }
    if (navbarInverse !== defaults.navbarInverse) {
      $noNavbarInverse.click();
    }
    if (color !== defaults.color) {
      $('#switcher-colors-' + color).click();
    }
  }
})(jQuery);
