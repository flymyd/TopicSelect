//上传课题时间
var nowTemp = new Date();
var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
var checkin = $('#upsta').fdatepicker({
    format: 'yyyy-mm-dd hh:ii',
    pickTime: true,
    onRender: function (date) {
        return date.valueOf() < now.valueOf() ? 'disabled' : '';
    }
}).on('changeDate', function (ev) {
    if (ev.date.valueOf() > checkout.date.valueOf()) {
        var newDate = new Date(ev.date)
        newDate.setDate(newDate.getDate() + 1);
        checkout.update(newDate);
    }
    checkin.hide();
}).data('datepicker');
var checkout = $('#upend').fdatepicker({
    format: 'yyyy-mm-dd hh:ii',
    pickTime: true,
    onRender: function (date) {
        return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
    }
}).on('changeDate', function (ev) {
    checkout.hide();
}).data('datepicker');

//选题时间
var checkin1 = $('#chosta').fdatepicker({
    format: 'yyyy-mm-dd hh:ii',
    pickTime: true,
    onRender: function (date) {
        return date.valueOf() < now.valueOf() ? 'disabled' : '';
    }
}).on('changeDate', function (ev) {
    if (ev.date.valueOf() > checkout1.date.valueOf()) {
        var newDate1 = new Date(ev.date)
        newDate1.setDate(newDate1.getDate() + 1);
        checkout1.update(newDate1);
    }
    checkin1.hide();
}).data('datepicker');
var checkout1 = $('#choend').fdatepicker({
    format: 'yyyy-mm-dd hh:ii',
    pickTime: true,
    onRender: function (date) {
        return date.valueOf() <= checkin1.date.valueOf() ? 'disabled' : '';
    }
}).on('changeDate', function (ev) {
    checkout1.hide();
}).data('datepicker');