(function() {

  var _initDocumentationMenu = function() {
    var data = window.RobotUtils.getMetaData();
    var items = [];

    var libSets = data['library-set'];
    for(var i = 0; i < libSets.length; i++) {
      var libSet = libSets[i];
      var libs = libSet['libraries'];

      items.push("<li class='nav-header'>" + libSet.name + "</li>");
      for(var shortname in libs) {
        var lib = libs[shortname];
        items.push("<li><a href='./library-" + shortname + ".html'>" + lib.name + "</a></li>");
      }
    }

    $("#robot-docs-menu-items").append(items.join(''));
  };

  var _initTutorialsMenu = function() {
    $.getJSON('metadata/tutorials-metadata.json', function(data) {
      var items = [];

      var tutorialSets = data['tutorial-set'];
      for(var i = 0; i < tutorialSets.length; i++) {
        var tutorialSet = tutorialSets[i];
        var tutorials = tutorialSet['tutorials'];

        items.push("<li class='nav-header'>" + tutorialSet.name + "</li>");
        for(var shortname in tutorials) {
          var name = tutorials[shortname];
          items.push("<li><a href='./tutorials-" + shortname + ".html'>" + name + "</a></li>");
        }
      }

      $("#tutorials-menu-items").append(items.join(''));
    });
  };

  var _initializeMenu = function(doc, status, result) {
    if(status == "success") {
      $("#main-menu").append(result.responseText);

      var selectedMenu = $(document.body).attr("selected-menu");
      $("#" + selectedMenu).addClass("active");

      _initDocumentationMenu();
      _initTutorialsMenu();
    }
  };

  var _initializeFooter = function(doc, status, result) {
    if(status == "success") {
      $("#container").append(result.responseText);
    }
  };

  $.ajax({url: "template/menu.html", cache: false}).done(_initializeMenu);
  $.ajax({url: "template/footer.html", cache: false}).done(_initializeFooter);
})();