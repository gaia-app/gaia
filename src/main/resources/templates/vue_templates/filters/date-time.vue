<script>
    function formatDate(value, options) {
        if (!value) return '';
        return new Intl.DateTimeFormat(undefined, options).format(Date.parse(value));
    }

    // display date like "05/04/2020, 4:20:20 AM"
    Vue.filter('dateTime', function (value) {
        return formatDate(value, {
            "day": "2-digit",
            "month": "2-digit",
            "year": "numeric",
            "hour": "numeric",
            "minute": "2-digit",
            "second": "2-digit"
        });
    });
    // display date like "May 04, 2020, 4:20:20 AM"
    Vue.filter('dateTimeLong', function (value) {
        return formatDate(value, {
            "day": "2-digit",
            "month": "long",
            "year": "numeric",
            "hour": "numeric",
            "minute": "2-digit",
            "second": "2-digit"
        });
    });
</script>