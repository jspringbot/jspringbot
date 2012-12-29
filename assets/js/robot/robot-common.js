(function() {
  var _log = function(msg) {
    if(window.console && console.log) {
      console.log(msg);
    }
  };

  var _startsWith = function(source, str) {
    return source.match("^"+str) == str;
  };

  var _getQueryStringParameters = function (paramName, url) {
    var i, len, idx, queryString, params, tokens;

    url = url || top.location.href;

    idx = url.indexOf("?");
    queryString = idx >= 0 ? url.substr(idx + 1) : url;

    // Remove the hash if any
    idx = queryString.lastIndexOf("#");
    queryString = idx >= 0 ? queryString.substr(0, idx) : queryString;

    params = queryString.split("&");

    var values = [];
    for (i = 0, len = params.length; i < len; i++) {
      tokens = params[i].split("=");
      if (tokens.length >= 2) {
        if (tokens[0] === paramName) {
          values.push(decodeURIComponent(tokens[1]));
        }
      }
    }

    if(values.length == 0) return null;

    return values;
  };

  var _getQueryStringParameter = function (paramName, url) {
    var values = _getQueryStringParameters(paramName, url);

    if(values === null) {
      return null;
    }

    return values[0];
  };

  var LibraryInitializer = function(name, url, callback) {

    var _libraryData;

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
            _libraryData = $.parseJSON(libdocStatement.substring(startIndex, endIndex + 1));

            if(callback) {
              callback(name, _libraryData);
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

  window.RobotUtils = function() {

    var total = 0;
    var metadata;
    var metaDataCache = {};
    var dataCache = {};
    var loaded = [];
    var shortnames = [];
    var ready = false;
    var fns = [];

    $.getJSON('metadata/robot-metadata.json', function(data) {
      metadata = data;
      var libSets = data['library-set'];

      total = 0;
      var i = 0;

      for(i = 0; i < libSets.length; i++) {
        var libSet = libSets[i];
        var libs = libSet['libraries'];

        for(var shortname in libs) {
          shortnames.push(shortname);
          if(!libs[shortname].version) {
            libs[shortname].version = libSet.version;
          }
          metaDataCache[shortname] = libs[shortname];
          total += 1;
        }
      }

      ready = true;
      for(i = 0; i < fns.length; i++) {
        if(fns[i]) {
          fns[i]();
        }
      }
    });

    var LibraryLoadedCallbackWrapper = function(callbackFn) {
      return function(name, data) {
        _log("Loaded '" + name + "'");

        loaded.push(name);
        dataCache[name] = data;

        if(callbackFn) {
          callbackFn(name, data);
        }
      };
    };

    var _load = function(shortname, callbackFn) {
      if(dataCache[shortname]) {
        callbackFn(shortname, dataCache[shortname]);

        return;
      }

      var lib = metaDataCache[shortname];

      new LibraryInitializer(shortname, lib.src, new LibraryLoadedCallbackWrapper(callbackFn)).init();
    };

    var _quickFilterHTML = function() {
      var filters = [];

      for(var i = 0; i < loaded.length; i++) {
        var lib = metaDataCache[loaded[i]];

        if(lib.hasOwnProperty('quick-filter')) {
          var libFilter = lib['quick-filter'];

          for(var j = 0; j < libFilter.length; j++) {
            if(filters.indexOf(libFilter[j]) == -1) {
              filters.push(libFilter[j]);
            }
          }
        }
      }

      if(!filters.length) {
        return "";
      }

      filters.sort();

      var buf = [];
      for(var l = 0; l < filters.length; l++) {
        var filter = filters[l];

        buf.push('<span robot-library-filter="' + filter.toLowerCase() + '" class="label label-rose quick-filter">' +  filter + '</span>');

        if(l + 1 < filters.length) {
          buf.push("\n");
        }
      }

      return buf.join('');
    };

    var _featuredHtml = function() {
      var features = [];

      for(var i = 0; i < loaded.length; i++) {
        var lib = metaDataCache[loaded[i]];

        if(lib.hasOwnProperty('featured')) {
          var libFilter = lib['featured'];

          for(var j = 0; j < libFilter.length; j++) {
            if(features.indexOf(libFilter[j]) == -1) {
              features.push(libFilter[j]);
            }
          }
        }
      }

      if(!features.length) {
        return "";
      }

      var buf = [];
      for(var l = 0; l < features.length; l++) {
        var feature = features[l];

        buf.push('<span robot-library-filter="' + feature.toLowerCase() + '" class="label label-rose-featured quick-filter">' +  feature + '</span>');

        if(l + 1 < features.length) {
          buf.push("\n");
        }
      }

      return buf.join('');
    };

    var _jSpringBotMarkup = function(html) {
      var pattern = /\{\{.+}}/g;
      return html.replace(pattern,
        function(capture) {
          var content = capture.substring(2, capture.length -2);

          if(_startsWith(content, 'jspringbot-github:')) {
            var project = content.substring(18);
            var label = project;
            if(project.indexOf("|")) {
              var split = project.split("|");
              project = split[0];
              label = split[1];
            }

            return '<a target="_blank" href="https://github.com/jspringbot/' + project + '" class="btn btn-primary"><i class="icon-github"></i> ' + label + ' &raquo;</a>';
          }

          return "<code>" + content + "</code>";
        });
    };


    return {
      onReady: function(fn) {
        if(ready && fn) {
          fn();
          return;
        }

        fns.push(fn);
      },

      getQuery: function(param) {
        param = param || "q";
        return _getQueryStringParameter(param);
      },

      jSpringBotMarkup: function(html) {
        return _jSpringBotMarkup(html);
      },

      loadAll: function(callbackFn) {
        for(var i = 0; i < shortnames.length; i++) {
          _load(shortnames[i], callbackFn);
        }
      },

      load: function(shortname, callbackFn) {
        _load(shortname, callbackFn);
      },

      quickFilterHtml: function() {
        return _quickFilterHTML();
      },

      featuredHtml: function() {
        return _featuredHtml();
      },

      isFeatured: function(shortname, keyword) {
        var lib = metaDataCache[shortname];

        if(!lib.hasOwnProperty('featured')) {
          return false;
        }

        var featured = lib['featured'];

        return featured.indexOf(keyword) != -1;
      },

      getName: function(shortname) {
        var lib = metaDataCache[shortname];

        return lib.name;
      },

      getVersion: function(shortname) {
        var lib = metaDataCache[shortname];

        return lib.version;
      },

      librarySize: function() {
        return total;
      },

      eachLibrary: function(fn) {
        var libSets = metadata['library-set'];
        for(var i = 0; i < libSets.length; i++) {
          var libSet = libSets[i];
          var libs = libSet['libraries'];

          for(var shortname in libs) {
            var lib = libs[shortname];

            fn(lib);
          }
        }
      },

      getMetaData: function() {
        return metadata;
      }
    };
  }();
})();