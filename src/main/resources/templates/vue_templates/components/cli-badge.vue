<template id="cli-badge">
    <img :src="'https://img.shields.io/badge/terraform-' + version + '-blue.svg?style=' + badgeStyle"
         :alt="version" :title="version"/>
</template>

<script>
    Vue.component('cli-badge', {
        template: '#cli-badge',
        props: ['cli', 'badgeStyle'],
        computed: {
            version: function () {
                return this.cli.repository === 'hashicorp/terraform' ? this.cli.tag : 'custom';
            }
        }
    });
</script>