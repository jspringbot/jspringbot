$(function() {
  var LIBRARY_URL = $(document.body).attr("robot-library-url");

  var RobotLibrary = function(data) {
    var $generated = $("#robot-library-generated");
    var $shortcuts = $("#robot-library-shortcuts");
    var $length = $("#robot-library-keywords-length");
    var $docs = $("#robot-library-docs");
    var $keywords = $("#robot-library-keywords");
    var $version = $("#robot-library-version");
    var $name = $("#robot-library-name");
    var $filter = $("#robot-library-filter");

    var _initShortcuts = function(search) {
      if(!$shortcuts.length) {
        return;
      }

      var buf = "<ul>";
      for(var i = 0; i < data.keywords.length; i++) {
        var keyword = data.keywords[i];
        if(!search || keyword.name.toLowerCase().indexOf(search.toLowerCase()) != -1) {
          buf += "<li><a href='#" + keyword.name + "'>" + keyword.name + "</a></li>";
        }
      }
      buf += "</ul>";
      $shortcuts.html(buf);
    };

    var _initKeywords = function(search) {
      if(!$keywords.length) {
        return;
      }

      var buf = "";
      buf += "<table class='table table-hover keywords'>";
      buf += "<thead>";
      buf += "<tr>";
      buf += "<th class='kw'>Keyword</th>";
      buf += "<th class='arg'>Arguments</th>";
      buf += "<th class='doc'>Documentation</th>";
      buf += "</tr>";
      buf += "</thead>";
      buf += "<tbody>";
      for(var i = 0; i < data.keywords.length; i++) {
        var keyword = data.keywords[i];

        if(!search || keyword.name.toLowerCase().indexOf(search.toLowerCase()) != -1) {
          buf += "<tr>";
          buf += "<td class='kw'><a name='" + keyword.name + "'></a>" + keyword.name + "</a></td>";
          buf += "<td class='arg'>" + keyword.args + "</a></td>";
          buf += "<td class='doc'>" + keyword.doc + "</a></td>";
          buf += "</tr>";
        }
      }
      buf += "</tbody>";
      buf += "</table>";
      $keywords.html(buf);
    };

    var _filterSubmit = function(event) {
      var $input = $filter.find("input");

      var value = $input.val();
      _initKeywords(value);
      _initShortcuts(value);

      if(value) {
        $(".robot-library-filter-show").show();
        $(".robot-library-filter-hide").hide();
      } else {
        $(".robot-library-filter-hide").show();
        $(".robot-library-filter-show").hide();
      }

      if(event) {
        event.preventDefault();
        event.stopPropagation();
      }
    };

    var _initFilter = function() {
      if(!$filter.length) {
        return;
      }

      var keywordNames = [];
      for(var i = 0; i < data.keywords.length; i++) {
        keywordNames.push(data.keywords[i].name);
      }

      var $input = $filter.find("input");
      $input.typeahead({"source": keywordNames, "showOffsetHeight": -100, "updater": function(val) {
        $input.val(val).change();
        _filterSubmit();
        return val;
      }});

      $filter.submit(_filterSubmit);
    };

    return {
      init: function() {
        $generated.text(data.generated);
        $length.text(data.keywords.length);
        $version.text(data.version);
        $name.text(data.name);
        $docs.html(data.doc);

        var search = "";
        if($filter.length) {
          search = $filter.find("input").val();
        }

        if(!search) {
          _initShortcuts();
          _initKeywords();
          _initFilter();
        } else {
          _initFilter();
          _filterSubmit();
        }
      }
    };
  };

  var _initializeLibrary = function(doc, status, result) {
    if(status == "success") {
      var responseText = result.responseText;

      var startIndex = responseText.indexOf("libdoc =");
      if(startIndex != -1) {
        var endIndex = responseText.indexOf("</" + "script>", startIndex);

        if(endIndex != -1) {
          var libdocStatement = responseText.substring(startIndex, endIndex);
          startIndex = libdocStatement.indexOf("{");
          endIndex = libdocStatement.lastIndexOf("}");

          // initialize robot library
          new RobotLibrary($.parseJSON(libdocStatement.substring(startIndex, endIndex + 1))).init();
          return;
        }
      }
    }

    if(window.console && console.log) {
      console.log("Error loading robot library from url " + LIBRARY_URL);
    }
  };

  // start loading the library
  $.ajax({url: LIBRARY_URL}).done(_initializeLibrary);
});
