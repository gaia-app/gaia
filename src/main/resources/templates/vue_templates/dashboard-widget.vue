<template id="dashboard-widget">
    <div class="col-md-6 col-lg-4">
        <a :href="link">
            <div class="full counter_section margin_bottom_30" :class="variant">
                <div class="couter_icon">
                    <div>
                        <i class="fas" :class="icon"></i>
                    </div>
                </div>
                <div class="counter_no">
                    <div>
                        <p class="total_no">{{value}}</p>
                        <p class="head_couter">{{text}}</p>
                    </div>
                </div>
            </div>
        </a>
    </div>
</template>

<script>
    Vue.component('dashboard-widget', {
        template: '#dashboard-widget',
        props: ['text', 'value', 'variant', 'icon', 'link'],
    });
</script>