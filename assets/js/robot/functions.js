(function() {
  var _log = function(msg) {
    if(window.console && console.log) {
      console.log(msg);
    }
  };

  var FunctionUtils = function() {
    var FUNCTION_CLASSES = $(document.body).attr("function-classes").split(",");

    var _isFunctionClass = function(functionClass) {
      for(var i = 0; i < FUNCTION_CLASSES.length; i++) {
        if(FUNCTION_CLASSES[i] == functionClass) {
          return true;
        }
      }

      return false;
    };

    var metadata;
    var ready = false;
    var functions = [];
    var fns = [];

    $.ajax({
      url: 'docs/functions.json',
      contentType: 'application/json',
      method: 'get',
      dataType: 'json',
      async: window.ROBOT_ASYNC,
      success: function (data) {
        metadata = data;

        var i;
        for(i = 0; i < data.length;i++) {
          if(_isFunctionClass(data[i].functionClass)) {
            var regex = /([a-z\.\[\]]+)\s+([a-z0-9$_]+)\s*\(\s*(([a-z0-9$_\[\]\._$]+(\s*,\s*)?)+)\)/gi;
            var matches = regex.exec(data[i].functionSignature);

            data[i].name = matches[2];

            if(data[i].prefix != null) {
              data[i].longName = data[i].prefix + ":" + data[i].name;
            } else {
              data[i].longName = data[i].name;
            }

            if(matches.length > 2) {
              data[i].params = matches[3];
            }

            functions.push(data[i]);
          }
        }

        functions.sort(function(a,b){
          if(a.name.toLowerCase() > b.name.toLowerCase()) {
            return 1;
          } else if (a.name.toLowerCase() == b.name.toLowerCase()) {
            return 0;
          } else {
            return -1;
          }
        });

        ready = true;
        for(i = 0; i < fns.length; i++) {
          if(fns[i]) {
            fns[i]();
          }
        }
      }
    });


    return {
      onReady: function(fn) {
        if(ready && fn) {
          fn();
          return;
        }

        fns.push(fn);
      },

      getFunctions: function() {
        return functions;
      }
    };
  }();

  var RobotFunction = function(data) {
    var $keywords = $("#robot-library-keywords");
    var $length = $("#function-length");
    var $filter = $("#robot-library-filter");
    var $filterCount = $("#robot-library-filter-count");
    var $shortcuts = $("#robot-library-shortcuts");

    var _prettyPrint = function() {
      $("pre").each(function(index, el) {
        $(el).addClass("prettyprint");
      });

      window.prettyPrint && prettyPrint();
    };

    var _getShortDescription = function(keyword) {
      var $dummy = $("#dummy");

      if(!$dummy.length) {
        $(document.body).append("<div id='dummy' class='hide'></div>");
        $dummy = $("#dummy");
      }

      $dummy.html(keyword.description);
      return $dummy.find("p").first().text();
    };

    var _initShortcuts = function(search) {
      if(!$shortcuts.length) {
        return;
      }

      var titles = [];

      var match = 0;
      var i = 0;
      var buf = "<ul>";
      for(i = 0; i < data.length; i++) {
        var keyword = data[i];
        if(_isMatch(keyword, search)) {
          buf += "<li><a id='shortcut-" + match + "' href='#" + keyword.name + "' data-placement='left' rel='tooltip'>" + keyword.name + "</a></li>";
          titles[match] = _getShortDescription(keyword);
          match++;
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


    var _isMatch = function(keyword, search) {
      return !search || keyword.name.toLowerCase().indexOf(search.toLowerCase()) != -1;
    };

    var _initKeywords = function(search) {
      if(!$keywords.length) {
        return;
      }

      var buf = [];
      var ctr = 0;
      var exact = -1;
      buf.push('<div class="accordion" id="accordion-keywords">');

      for(var i = 0; i < data.length; i++) {
        var keyword = data[i];

        if(_isMatch(keyword, search)) {
          buf.push('<a class="anchor" name="' + keyword.name + '"></a>');
          buf.push('<div class="accordion-group">');
          buf.push('<div class="accordion-heading">');
          buf.push('<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse-' + ctr + '">');

          buf.push("<span class='clearfix'>");
          buf.push(keyword.name);
          if(keyword.params) {
            buf.push(' <small>');
            buf.push('[ ');
            buf.push(keyword.longName);
            buf.push("(");
            buf.push(keyword.params);
            buf.push(")");
            buf.push(' ]');
            buf.push('</small>');
          }

          buf.push('</a>');
          buf.push('</div>');
          buf.push('<div id="collapse-' + ctr + '" class="accordion-body collapse">');
          buf.push('<div class="accordion-inner">');
          buf.push(keyword.description);
          buf.push('</div>');
          buf.push('</div>');
          buf.push('</div>');

          if(search && search.toLowerCase() == keyword.name.toLowerCase()) {
            exact = ctr;
          }

          ctr++;
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

    var _initFilter = function() {
      if(!$filter.length) {
        return;
      }

      var keywordNames = [];
      for(var i = 0; i < data.length; i++) {
        keywordNames.push(data[i].name);
      }

      var $input = $filter.find("input");
      $input.typeahead({"source": keywordNames, "showOffsetHeight": -100, "updater": function(val) {
        $input.val(val).change();
        _filterSubmit();
        return val;
      }});

      $("*[robot-library-filter]").click(function() {
        var $el = $(this);
        $input.val($el.attr("robot-library-filter"));
        _filterSubmit();
      });

      $filter.submit(_filterSubmit);
    };


    return {
      init: function() {
        $length.text(data.length);

        var search = "";
        if($filter.length) {
          var $input = $filter.find("input");

          var q = RobotUtils.getQuery();
          if(q) {
            $input.val(q);
          }

          search = $input.val();
        }

        if(!search) {
          _initKeywords(search);
          _initShortcuts(search);
          _initFilter();
          _prettyPrint();
        } else {
          _initFilter();
          _filterSubmit();
        }
      }
    };
  };

  FunctionUtils.onReady(function() {
      new RobotFunction(FunctionUtils.getFunctions()).init();
  });
})();