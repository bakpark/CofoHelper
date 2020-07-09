/* eslint-disable vue/no-parsing-error */
<style scoped>
h1 i{
  cursor: pointer;
}
.problem_table table {
  border-collapse: collapse;
  text-align: left;
  margin: 3px 5px;
}
.problem_table table th {
  padding: 10px;
  font-weight: bold;
  border: 1px solid #ccc;
}
.problem_table table td {
  position: relative;
  border: 1px solid #ccc;
  text-align: center;
  padding: 10px;
}
.problem_table table td.correct {
  background-color: rgb(157, 201, 157);
}
.problem_table table td.not-correct {
  background-color: rgb(228, 110, 110);
}
.problem_table table td.user-name {
  background-color: rgb(153, 140, 140);
}
.problem_table table td.empty {
  background-color: rgb(255, 255, 255);
}
.problem_table .arrow_box {
  display: none;
  position: absolute;
  padding: 8px;
  top: 120%;
  -webkit-border-radius: 8px;
  -moz-border-radius: 8px;
  border-radius: 8px;
  background: #333;
  color: #fff;
  font-size: 14px;
}
.problem_table .arrow_box:after {
  position: absolute;
  bottom: 100%;
  left: 50%;
  width: 0;
  height: 0;
  margin-left: -10px;
  border: solid transparent;
  border-color: rgba(51, 51, 51, 0);
  border-bottom-color: #333;
  border-width: 10px;
  pointer-events: none;
  content: " ";
}
.problem_table td span {
  cursor: pointer;
  display: block;
}
.problem_table td div{
    display: flex;
    flex-direction: column;
    align-items: center;
}
.problem_table .arrow_box div{
    white-space:nowrap;
}
.problem_table span:hover + div.arrow_box {
  display: block;
  z-index: 40;
}
</style>
<template>
  <div class="problem_table" style="z-index:90; display:flex; flex-direction:column; align-items:center;">
    <h1 style="margin:auto;">
      {{contestId}}
    </h1>
    <table >
      <tr>
        <th v-for="(column, index) in columns" :key="index">{{ column }}</th>
      </tr>
      <tr v-for="(row, index) in rows" :key="index">
        <td
          v-for="(column, index) in columns"
          :key="index"
          v-bind:class="getTableClass(column, row)"
        >
          <div>
            <span v-if="column == 'name'">{{ row[column] }}</span>
            <span v-else v-on:click="click(row[column].submission_url)">{{ row[column].result }}</span>
            <div class="arrow_box">
                <div v-for="(item, index) in row[column].notes" :key="index">
                  {{item.result}} {{item.time}}
                </div>
            </div>
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>
<script>
/* eslint-disable */
export default {
  data() {
    return {
    }
  },
  props: {
    contestId: Number,
    columns: Array, // ['name', 'A', 'B', ,,, 'F']
    rows: Array // [{name: 'bakpark', A: Accepted, ...}, {...}, {...}]
  },
  methods: {
    click: function(url) {
      window.open(url, "_blank");
    },
    getTableClass: function(column, row) {
      if (row[column].result == "OK") return "correct";
      else if (row[column].result == '') return "empty";
      else if (column == "name") return "user-name";
      else return "not-correct";
    }
  }
};
</script>

