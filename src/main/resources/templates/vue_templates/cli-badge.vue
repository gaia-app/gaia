<template id="cli-badge">
    <img :src="'https://img.shields.io/badge/terraform-' + cliVersion + '-blue.svg?style=' + badgeStyle"
         :alt="cliVersion" :title="cliVersion"/>
</template>

<script>
    Vue.component('cli-badge', {
        template: '#cli-badge',
        props: ['cliVersion', 'badgeStyle'],
    });
</script>