<template>
  <div class="col-md-6">
    <div class="block">
      <div class="block_head">
        <h2>Outputs</h2>
        <small>These are the outputs of your stack.</small>
      </div>
      <table
        v-if="Object.keys(outputs).length !== 0"
        class="table"
      >
        <thead class="thead-light">
          <tr>
            <th>Variable</th>
            <th>Value</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(output, key) in outputs"
            :key="key"
          >
            <td>{{ key }}</td>
            <td>{{ output.value }}</td>
          </tr>
        </tbody>
      </table>
      <div
        v-else
        class="block_content"
      >
        <img
          src="@/assets/images/ghost.png"
          width="32"
        >
        No outputs found !
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'AppStackOutputs',
    props: {
      outputs: { type: Object, default: () => {} },
    },
  };
</script>
