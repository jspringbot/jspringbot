(function() {
  $.getJSON('metadata/app-metadata.json', function(data) {
    $(".mainmenu .container p.navbar-text").html('<span class="hidden-phone">Version</span> ' + data.version);
  });
})();