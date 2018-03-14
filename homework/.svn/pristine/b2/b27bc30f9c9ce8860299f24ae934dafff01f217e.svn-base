define(function (require, exports, module) {
    var $ = require('jquery');
    var I18n = require('homework/common/i18n');

    var s_alltype = $.i18n.prop('homework.homework.type.alltype');
    var s_paper = $.i18n.prop('试卷');
    var s_courseware = $.i18n.prop('课件');
    var s_microcourse = $.i18n.prop('微课');

    var ResType = {
        data: [{
            id: 3,
            name: s_paper
        }, {
            id: 1,
            name: s_courseware
        }, {
            id: 2,
            name: s_microcourse
        }],
        render: function (target) {
            $(target).empty();
            $(target).append('<option value="0">' + s_alltype + '</option>');
            $.each(ResType.data, function () {
                $(target).append($('<option>').val(this.id).text(this.name));
            });
        }
    };

    module.exports = ResType;
});
