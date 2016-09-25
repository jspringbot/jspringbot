(function() {
  $.getJSON('metadata/app-metadata.json', function(data) {
    $(".navbar-fixed-top .container p.navbar-text").html('<span class="hidden-phone">Version</span> ' + data.version);
  });
})();