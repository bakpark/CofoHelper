<template>
  <div class="body-wrapper">
    <div class="table-wrapper">
      <Table
        v-for="handle in $store.state.members"
        :key="handle"
        :tableKey="handle"
      ></Table>
    </div>
  </div>
</template>
<script>
import Table from '@/components/testTable.vue'
export default {
  name: 'Test',
  components: {
    Table: Table
  },
  props: {
  },
  data () {
    return {
    }
  },
  created () {
    let vm = this
    this.$store.state.members.forEach(handle => {
      vm.$api.user.status(handle, 1, 25).then(result => {
        vm.$store.commit('CHANGE_TABLE_DATA', [handle, result])
      })
    })
  }
}
</script>
<style>
.body-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin-top: 10%;
}
.table-wrapper {
  width: 90%;
  display: flex;
  flex-direction: row;
}
</style>
