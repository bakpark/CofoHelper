<template>
  <div class="Main">
    <div class="header">COFO HELPER</div>
    <router-link to="/contest/1353/A">go 1353-A</router-link>
    <router-link to="/contest/1353/B">go 1353-B</router-link>
    <div class="body-wrapper">
      <div class="table-wrapper">
        <Table v-for="handle in $store.state.members" :key="handle" :tableKey="handle"></Table>
      </div>
    </div>
  </div>
</template>

<script>
/*****************************************************************
 ************************** import *******************************
 *****************************************************************/
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
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
  },
  watch: {
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    let vm = this
    this.$store.state.members.forEach((handle) => {
      vm.$api.user.status(handle, 1, 25).then((result) => {
        vm.$store.commit('CHANGE_TABLE_DATA', [handle, result])
      })
    })
  },
  mounted () {
  },
  beforeDestroy () {
  },
  destroyed () {
  },
  /*****************************************************************
   ********************** User-Defined Methods *********************
   *****************************************************************/
  methods: {
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.Test{
  display: flex;
  width: 100%;
  height: 100%;
  flex-direction: column;
  align-items: center;
}
.header{
  height:20%;
  font-size: 5em;
  font-weight: 700;
  color: aqua;
}
.body-wrapper{
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin-top: 10%;
}
.table-wrapper{
  width: 90%;
  display: flex;
  flex-direction: row;
}
</style>
