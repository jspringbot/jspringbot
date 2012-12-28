(function() {
  /*
   * Date Format 1.2.3
   * (c) 2007-2009 Steven Levithan <stevenlevithan.com>
   * MIT license
   *
   * Includes enhancements by Scott Trenda <scott.trenda.net>
   * and Kris Kowal <cixar.com/~kris.kowal/>
   *
   * Accepts a date, a mask, or a date and a mask.
   * Returns a formatted version of the given date.
   * The date defaults to the current date/time.
   * The mask defaults to dateFormat.masks.default.
   */

  var dateFormat = function () {
    var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
      timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
      timezoneClip = /[^-+\dA-Z]/g,
      pad = function (val, len) {
        val = String(val);
        len = len || 2;
        while (val.length < len) val = "0" + val;
        return val;
      };

    // Regexes and supporting functions are cached through closure
    return function (date, mask, utc) {
      var dF = dateFormat;

      // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
      if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
        mask = date;
        date = undefined;
      }

      // Passing date through Date applies Date.parse, if necessary
      date = date ? new Date(date) : new Date;
      if (isNaN(date)) throw SyntaxError("invalid date");

      mask = String(dF.masks[mask] || mask || dF.masks["default"]);

      // Allow setting the utc argument via the mask
      if (mask.slice(0, 4) == "UTC:") {
        mask = mask.slice(4);
        utc = true;
      }

      var	_ = utc ? "getUTC" : "get",
        d = date[_ + "Date"](),
        D = date[_ + "Day"](),
        m = date[_ + "Month"](),
        y = date[_ + "FullYear"](),
        H = date[_ + "Hours"](),
        M = date[_ + "Minutes"](),
        s = date[_ + "Seconds"](),
        L = date[_ + "Milliseconds"](),
        o = utc ? 0 : date.getTimezoneOffset(),
        flags = {
          d:    d,
          dd:   pad(d),
          ddd:  dF.i18n.dayNames[D],
          dddd: dF.i18n.dayNames[D + 7],
          m:    m + 1,
          mm:   pad(m + 1),
          mmm:  dF.i18n.monthNames[m],
          mmmm: dF.i18n.monthNames[m + 12],
          yy:   String(y).slice(2),
          yyyy: y,
          h:    H % 12 || 12,
          hh:   pad(H % 12 || 12),
          H:    H,
          HH:   pad(H),
          M:    M,
          MM:   pad(M),
          s:    s,
          ss:   pad(s),
          l:    pad(L, 3),
          L:    pad(L > 99 ? Math.round(L / 10) : L),
          t:    H < 12 ? "a"  : "p",
          tt:   H < 12 ? "am" : "pm",
          T:    H < 12 ? "A"  : "P",
          TT:   H < 12 ? "AM" : "PM",
          Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
          o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
          S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
        };

      return mask.replace(token, function ($0) {
        return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
      });
    };
  }();

// Some common format strings
  dateFormat.masks = {
    "default":      "ddd mmm dd yyyy HH:MM:ss",
    shortDate:      "m/d/yy",
    mediumDate:     "mmm d, yyyy",
    longDate:       "mmmm d, yyyy",
    fullDate:       "dddd, mmmm d, yyyy",
    shortTime:      "h:MM TT",
    mediumTime:     "h:MM:ss TT",
    longTime:       "h:MM:ss TT Z",
    isoDate:        "yyyy-mm-dd",
    isoTime:        "HH:MM:ss",
    isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
    isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
  };

// Internationalization strings
  dateFormat.i18n = {
    dayNames: [
      "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
      "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    ],
    monthNames: [
      "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
      "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    ]
  };

// For convenience...
  Date.prototype.format = function (mask, utc) {
    return dateFormat(this, mask, utc);
  };
})();

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
    var $filterCount = $("#robot-library-filter-count");

    var _prettyPrint = function() {
      $docs.html(data.doc);

      $("pre").each(function(index, el) {
        var $el = $(el);
        $el.addClass("prettyprint linenums");
      });

      window.prettyPrint && prettyPrint();
    };

    var _initShortcuts = function(search) {
      if(!$shortcuts.length) {
        return;
      }

      var match = 0;
      var buf = "<ul>";
      for(var i = 0; i < data.keywords.length; i++) {
        var keyword = data.keywords[i];
        if(!search || keyword.name.toLowerCase().indexOf(search.toLowerCase()) != -1) {
          match++;
          buf += "<li><a href='#" + keyword.name + "'>" + keyword.name + "</a></li>";
        }
      }
      buf += "</ul>";

      if($filterCount.length) {
        $filterCount.text(match);
      }

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
      for(var i = 0; i < data.keywords.length; i++) {
        keywordNames.push(data.keywords[i].name);
      }

      var $input = $filter.find("input");
      $input.typeahead({"source": keywordNames, "showOffsetHeight": -100, "updater": function(val) {
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
    };

    // parse a date in yyyy-mm-dd format
    var _parseDate = function(input) {
      var parts = input.match(/(\d+)/g);
      // new Date(year, month [, date [, hours[, minutes[, seconds[, ms]]]]])
      return new Date(parts[0], parts[1]-1, parts[2]); // months are 0-based
    };

    return {
      init: function() {
        $generated.text(_parseDate(data.generated).format("mmmm d, yyyy"));
        $length.text(data.keywords.length);
        $version.text(data.version);
        $name.text(data.name);

        var search = "";
        if($filter.length) {
          search = $filter.find("input").val();
        }

        if(!search) {
          _initShortcuts();
          _initKeywords();
          _initFilter();
          _prettyPrint();
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
  $.ajax({url: LIBRARY_URL, async: false}).done(_initializeLibrary);
});
