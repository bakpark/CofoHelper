<template>
  <div class="Main">
    <div class="header">COFO HELPER</div>
    <router-link to="/problem/1353-A">go 1353-A</router-link>
    <router-link to="/problem/1353-B">go 1353-B</router-link>
    <div class="body-wrapper">
      <div class="table-wrapper">
        <Table :tableKey="resultTableKey"></Table>
      </div>
      <div class="table-wrapper">
        <Table :tableKey="wResultTableKey"></table>
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
      resultTableKey: String('resultTable'),
      wResultTableKey: String('wResultTable')
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
    // by axios
    this.$axios.get(
      '/user.status',
      {
        params: {
          handle: 'bakpark',
          from: 1,
          count: 25
        }
      }
    ).then((response) => {
      vm.$store.commit('CHANGE_TABLE_DATA', [vm.resultTableKey, response.data.result])
    }).catch((err) => {
      console.error(err)
    })
    // same way by wrapping
    this.$api.user.status('bakpark', 1, 25).then((result) => {
      vm.$store.commit('CHANGE_TABLE_DATA', [vm.wResultTableKey, result])
    }).catch((err) => {
      console.error(err)
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
  flex-direction: row;
  width: 100%;
  margin-top: 10%;
}
.table-wrapper{
  width: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
