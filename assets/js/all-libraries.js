$(function() {

  var _log = function(msg) {
    if(window.console && console.log) {
      console.log(msg);
    }
  };

  var RobotLibraries = function() {
    var $keywords = $("#robot-library-keywords");
    var $shortcuts = $("#robot-library-shortcuts");
    var $filter = $("#search-form");
    var $input = $filter.find("input");
    var $filterCount = $("#robot-library-filter-count");

    var _libraries = {};
    var _keywordNames = [];

    var _prettyPrint = function() {
      $("pre").each(function(index, el) {
        $(el).addClass("prettyprint");
      });

      window.prettyPrint && prettyPrint();
    };

    var _createTooltip = function(keyword) {
      var $dummy = $("#dummy");

      if(!$dummy.length) {
        $(document.body).append("<div id='dummy' class='hide'></div>");
        $dummy = $("#dummy");
      }

      $dummy.html(keyword.doc);
      return $dummy.find("p").first().text();
    };

    var _initShortcuts = function(search) {
      if(!$shortcuts.length) {
        return;
      }

      var titles = [];
      var match = 0;
      var buf = "<ul class='all'>";
      for(var prop in _libraries) {
        var data = _libraries[prop];
        var withHeader = false;

        for(var i = 0; i < data.keywords.length; i++) {
          var keyword = data.keywords[i];
          if(!search || keyword.name.toLowerCase().indexOf(search.toLowerCase()) != -1) {
            if(!withHeader) {
              buf += "<li class='nav-header quick-link'>" + prop + "</li>";
              withHeader = true;
            }

            buf += "<li><a id='shortcut-" + match + "' href='#" + keyword.name + "' data-placement='left' rel='tooltip'>" + keyword.name + "</a></li>";
            titles[match] = _createTooltip(keyword);
            match++;
          }
        }
      }
      buf += "</ul>";

      if($filterCount.length) {
        $filterCount.text(match);
      }

      $shortcuts.html(buf);

      // add tooltips
      for(i = 0; i < titles.length; i++) {
        var $shortcut = $('#shortcut-' + i);
        $shortcut.attr("title", titles[i]);
        $shortcut.tooltip();
      }
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
      for(var prop in _libraries) {
        var data = _libraries[prop];

        for(var i = 0; i < data.keywords.length; i++) {
          var keyword = data.keywords[i];


          if(!search || keyword.name.toLowerCase().indexOf(search.toLowerCase()) != -1) {
            buf += "<tr>";
            buf += "<td class='kw'><a name='" + keyword.name + "'></a>" + keyword.name + "</a></td>";
            buf += "<td class='arg'>" + keyword.args + "</a></td>";
            buf += "<td class='doc'><ul class='post-meta pull-right post-meta-rose'><li><i class='icon-tag'></i> " + prop + "</li></ul> " + keyword.doc + "</a></td>";
            buf += "</tr>";
          }
        }
      }
      buf += "</tbody>";
      buf += "</table>";
      $keywords.html(buf);
    };

    var _filterSubmit = function(event) {
      var $input = $filter.find("input");

      var value = $.trim($input.val());
      _initKeywords(value);
      _initShortcuts(value);

      if(value) {
        $(".robot-library-filter-show").show();
        $(".robot-library-filter-hide").hide();
      } else {
        $(".robot-library-filter-hide").show();
        $(".robot-library-filter-show").hide();
      }

      _prettyPrint();

      if(event) {
        event.preventDefault();
        event.stopPropagation();
      }
    };

    return {
      add: function(name, data) {
        for(var i = 0; i < data.keywords.length; i++) {
          _keywordNames.push(data.keywords[i].name);
        }

        _libraries[name] = data;
      },

      init: function() {
        $input.typeahead({"items": 10, "source": _keywordNames, "updater": function(val) {
          $input.val(val).change();
          _filterSubmit();
          return val;
        }});

        $("span[robot-library-filter]").click(function() {
          var $el = $(this);
          $input.val($el.attr("robot-library-filter"));
          _filterSubmit();
        });

        $filter.submit(_filterSubmit);
      }
    };
  };

  var _all = new RobotLibraries();

  var LibraryInitializer = function(name, url, callback) {
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
            _all.add(name, $.parseJSON(libdocStatement.substring(startIndex, endIndex + 1)));

            if(callback) {
              callback(name);
            }

            return;
          }
        }
      }

      _log("Error loading robot library from url " + url);

      if(callback) {
        callback(name);
      }
    };

    return {
      init: function() {
        $.ajax({"url": url}).done(_initializeLibrary);
      }
    };
  };

  var _init = function() {
    $("#search-bar").removeClass("hide");
    $("#progress-bar").addClass("hide");

    _all.init();
  };

  var $checkboxes = $(".checkbox-library");
  var total = $checkboxes.length;
  var percent = 1/total * 100;
  var loaded = 0;

  var _callback = function(name) {
    _log("Loaded " + name);

    loaded++;

    $("#progress-bar").append("<div class='bar' style='width: "+ percent +"%;'></div>");

    if(loaded == total) {
      _init();
    }
  };

  $checkboxes.each(function(index, el) {
    var $el = $(el);
    var label = $("label[for='" + el.id + "']").text();

    new LibraryInitializer(label, $el.attr("robot-url"), _callback).init();
  });
});
