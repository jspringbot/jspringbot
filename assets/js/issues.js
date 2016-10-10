(function() {
  $.ajax({
    url: 'metadata/robot-metadata.json',
    contentType: 'application/json',
    method: 'get',
    dataType: 'json',
    async: window.ROBOT_ASYNC,
    success: function (data) {
      var libSets = data['library-set'][1];
      var $tbody = $("table tbody");
      var $link = $("#quick_link ul");

      for(var shortname in libSets.libraries) {
        var library = libSets.libraries[shortname];
        $link.append("<li><a href='" + library.issue + "'>" + library.name + "</a></li>");
        $tbody.append("<tr><td>" + library.name + "</td><td><a href='" + library.issue + "'>" + library.issue + "</a></td></tr>");
      }
    }
  });
})();