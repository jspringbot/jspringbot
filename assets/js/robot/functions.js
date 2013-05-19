(function() {
  var _log = function(msg) {
    if(window.console && console.log) {
      console.log(msg);
    }
  };

  var FunctionUtils = function() {
    var FUNCTION_CLASSES = $(document.body).attr("function-classes").split(",");

    var metadata;
    var ready = false;
    var functions = [];
    var fns = [];

    $.getJSON('docs/functions.json', function(data) {
      metadata = data;

      var i = 0;
      for(i = 0; i < data.length;i++) {
        if(isFunctionClass(data[i].functionClass)) {
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
    });

    var isFunctionClass = function(functionClass) {
      for(var i = 0; i < FUNCTION_CLASSES.length; i++) {
        if(FUNCTION_CLASSES[i] == functionClass) {
          return true;
        }
      }

      return false;
    };

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

    var _prettyPrint = function() {
      $("pre").each(function(index, el) {
        $(el).addClass("prettyprint");
      });

      window.prettyPrint && prettyPrint();
    };

    var _isMatch = function(keyword, search) {
      return true;
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

          if(search && search.toLowerCase() == keyword.functionSignature.toLowerCase()) {
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

    return {
      init: function() {
        $length.text(data.length);
        _initKeywords();
        _prettyPrint();
      }
    };
  };

  FunctionUtils.onReady(function() {
      new RobotFunction(FunctionUtils.getFunctions()).init();
  });
})();