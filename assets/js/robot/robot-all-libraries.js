$(function() {
  var RobotUtils = window.RobotUtils;

  var RobotLibraries = function() {
    var $keywords = $("#robot-library-keywords");
    var $shortcuts = $("#robot-library-shortcuts");
    var $filter = $("#search-form");
    var $input = $filter.find("input");
    var $filterCount = $("#robot-library-filter-count");
    var $quickFilter = $("#robot-quick-filter");
    var $featured = $("#robot-featured-keywords");

    var _libraries = {};
    var _keywordNames = [];

    var _prettyPrint = function() {
      $("pre").each(function(index, el) {
        $(el).addClass("prettyprint");
      });

      window.prettyPrint && prettyPrint();
    };

    var _isMatch = function(keyword, search) {
      return !search || keyword.name.toLowerCase().indexOf(search.toLowerCase()) != -1;
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
          if(_isMatch(keyword, search)) {
            if(!withHeader) {
              buf += "<li class='nav-header quick-link'>" + RobotUtils.getName(prop) + "</li>";
              withHeader = true;
            }

            buf += "<li><a id='shortcut-" + match + "' href='#" + keyword.name + "' data-placement='left' rel='tooltip'>" + keyword.name + "</a></li>";
            titles[match] = RobotUtils.getKeywordShortDescription(keyword);
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

    var _initTableKeywords = function(search) {
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

          if(_isMatch(keyword, search)) {
            buf += "<tr>";
            buf += "<td class='kw'><a name='" + keyword.name + "'></a>" + keyword.name + "</a></td>";
            buf += "<td class='arg'>" + keyword.args + "</a></td>";
            buf += "<td class='doc'><ul class='post-meta pull-right post-meta-rose'><li><i class='icon-tag'></i> " + RobotUtils.getName(prop) + "</li></ul> " + keyword.doc + "</a></td>";
            buf += "</tr>";
          }
        }
      }
      buf += "</tbody>";
      buf += "</table>";
      $keywords.html(buf);
    };

    var _initKeywords = function(search) {
      if(!$keywords.length) {
        return;
      }

      var buf = [];
      var ctr = 0;
      var exact = -1;
      buf.push('<div class="accordion" id="accordion-keywords">');

      for(var prop in _libraries) {
        var data = _libraries[prop];

        for(var i = 0; i < data.keywords.length; i++) {
          var keyword = data.keywords[i];

          if(_isMatch(keyword, search)) {
            buf.push('<a class="anchor" name="' + keyword.name + '"></a>');
            buf.push('<div class="accordion-group">');
            buf.push('<div class="accordion-heading">');
            buf.push('<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse-' + ctr + '">');
            buf.push("<ul class='post-meta pull-right post-meta-rose'>");

            if(RobotUtils.isFeatured(prop, keyword.name)) {
              buf.push("<li class='label-featured'><i class='icon-star'></i> Featured</li>");
            }
            buf.push("<li class='label-library'><i class='icon-tag'></i> " + RobotUtils.getName(prop) + "</li>");
            buf.push("</ul>");


            buf.push("<span class='clearfix'>");
            buf.push(keyword.name);
            if(keyword.args) {
              buf.push(' <small>');
              buf.push('[ ');
              buf.push(keyword.args);
              buf.push(' ]');
              buf.push('</small>');
            }
            buf.push("</span>");
            buf.push('</a>');
            buf.push('</div>');
            buf.push('<div id="collapse-' + ctr + '" class="accordion-body collapse">');
            buf.push('<div class="accordion-inner">');
            buf.push('<div class="pull-right"><button title="Click to copy keyword to clipboard." data-clipboard-text="');
            buf.push(keyword.name);
            buf.push('" class="btn-copy btn btn-small"><i class="icon-copy"></i></button></div>');
            buf.push('<div class="clearfix">');
            buf.push(RobotUtils.jSpringBotMarkup(keyword.doc));
            buf.push('</div>');
            buf.push('</div>');
            buf.push('</div>');
            buf.push('</div>');

            if(search && search.toLowerCase() == keyword.name.toLowerCase()) {
              exact = ctr;
            }

            ctr++;
          }
        }
      }

      buf.push('</div>');

      $keywords.html(buf.join(''));

      if(ctr == 1) {
        $("#collapse-0").addClass("in");
      }
      if(exact != -1 && ctr > 1) {
        $("#collapse-" + exact).addClass("in");
      }

      var $accordion = $("#accordion-keywords");
      $accordion.collapse();
      $accordion.removeAttr("style");

      new ZeroClipboard($(".btn-copy"));
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

        var quickFilterHTML = RobotUtils.quickFilterHtml();
        var featuredHTML = RobotUtils.featuredHtml(true);

        if(quickFilterHTML) {
          $quickFilter.append(quickFilterHTML);
          $quickFilter.removeClass("hide");
        }

        if(featuredHTML) {
          $featured.append(featuredHTML);
          $featured.removeClass("hide");
        }

        $("span[robot-library-filter]").click(function() {
          var $el = $(this);
          $input.val($el.attr("robot-library-filter"));
          _filterSubmit();
        });

        $filter.submit(_filterSubmit);

        if($filter.length) {
          var q = RobotUtils.getQuery();
          if(q) {
            $input.val(q);
            _filterSubmit();
          }
        }
      }
    };
  };

  var _all = new RobotLibraries();

  var _init = function() {
    $("#search-bar").removeClass("hide");
    $("#progress-bar").addClass("hide");

    _all.init();
  };

  RobotUtils.onReady(function() {
    var total = RobotUtils.librarySize();
    var percent = 1/total * 100;
    var loaded = 0;

    RobotUtils.loadAll(function(name, data) {
      loaded++;

      _all.add(name, data);

      $("#progress-bar").append("<div class='bar bar-danger' style='width: "+ percent +"%;'></div>");

      if(loaded == total) {
        _init();
      }
    });
  });
});
